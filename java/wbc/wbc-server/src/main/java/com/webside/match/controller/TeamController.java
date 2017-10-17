/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.match.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.match.entities.Team;
import com.webside.match.service.ITeamService;
import com.webside.match.service.impl.GameService;
import com.webside.match.service.impl.TeamGameService;
import com.webside.system.dict.service.DictService;
import com.webside.util.DateUtils;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;

/**
 * 战队Controller
 *
 * @author zengxn
 * @date 2016-11-23 16:32:39
 */
 
@Controller
@RequestMapping("/match/team/")
public class TeamController extends BaseController
{
	/**
	 * 跳转查询战队页面
	 * @param Model model
	 * 		  HttpServletRequest request
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("listUI.html")
	public String listUI(Model model, HttpServletRequest request) 
	{
		PageUtil page = null;
	
		try
		{
			page = new PageUtil();
			
			if(request.getParameterMap().containsKey("page"))
			{
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}
			
			model.addAttribute("page", page);
			//查询选项数据
			model.addAttribute("teamStatus", dictService.getDictType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			return Common.BACKGROUND_PATH + "/business/match/team/list";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}

	/**
	 * ajax分页动态加载模式
	 * @param dtGridPager Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception
	{
		Map<String, Object> parameters = null;
		//1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		//2、设置查询参数
		parameters = pager.getParameters();
		if(parameters.get("beginCreateTime")!=null && StringUtils.isNotBlank(parameters.get("beginCreateTime").toString())){
			parameters.put("beginCreateTime", DateUtils.parseDate(parameters.get("beginCreateTime")).getTime());
		}
		if(parameters.get("endCreateTime")!=null && StringUtils.isNotBlank(parameters.get("endCreateTime").toString())){
			parameters.put("endCreateTime", DateUtils.parseDate(parameters.get("endCreateTime")).getTime());
		}
		
		//3、判断是否是导出操作
		if(pager.getIsExport())
		{
			if(pager.getExportAllData())
			{
				//3.1、导出全部数据
				List<Team> list = teamService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			}
			else
			{
				//3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		}
		else
		{
			//设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "sort_num");
			List<Team> list = teamService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());

			//权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("team:edit");
			//列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list,edit?"edit":""));
			return parameters;
		}
	}
	
	/**
	 * 跳转新增战队页面
	 * @param Model model
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) 
	{
		try
		{
			model.addAttribute("teamStatus", dictService.getDictType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			model.addAttribute("gameList",gameService.queryPartList(null));
			return Common.BACKGROUND_PATH + "/business/match/team/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 新增战队 ajax
	 * @param Team
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(Team entity, String []gameId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			boolean check = false;
			if(teamService.findByTeamName(entity.getTeamName(),null)==0){
				check = true;
			}else{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "战队名称已存在!");
			}
			if(check){
				List<String> list = new ArrayList<String>();
				if(gameId!=null && gameId.length>0)
				{
					for (String id : gameId) {
						list.add(id);
					}
				}
				int result = teamService.insert(entity,list);
				if(result == 1)
				{
					map.put("success", Boolean.TRUE);
					map.put("data", null);
					map.put("message", "添加成功");
				}else
				{
					map.put("success", Boolean.FALSE);
					map.put("data", null);
					map.put("message", "添加失败");
				}
			}
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
		
		return map;
	}
	
	/**
	 * 跳转编辑战队页面
	 * @param Model model
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) 
	{
		Team entity = null;
		PageUtil page = null;
	
		try
		{
			entity = teamService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			
			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			//战队状态列表
			model.addAttribute("teamStatus", dictService.getDictType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			//游戏列表
			model.addAttribute("gameList",gameService.queryPartList(null));
			//战队游戏关联列表
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("teamId", id);
			model.addAttribute("teamGameList",teamGameService.queryListByPage(parameter));
			return Common.BACKGROUND_PATH + "/business/match/team/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 修改战队
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(Team entity, String []gameId) throws AjaxException
	{
		Map<String, Object> map = null;
		
		try
		{
			map = new HashMap<String, Object>();
			boolean check = false;
			if(teamService.findByTeamName(entity.getTeamName(),entity.getId())==0){
				check = true;
			}else{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "战队名称已存在!");
			}
			if(check){
				List<String> list = new ArrayList<String>();
				if(gameId!=null && gameId.length>0)
				{
					for (String id : gameId) {
						list.add(id);
					}
				}
				int result = teamService.update(entity,list);
				if(result == 1)
				{
					map.put("success", Boolean.TRUE);
					map.put("data", null);
					map.put("message", "编辑成功");
				}else
				{
					map.put("success", Boolean.FALSE);
					map.put("data", null);
					map.put("message", "编辑失败");
				}
			}
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
		
		return map;
	}
	
	/**
	 * 检索战队名称是否存在
	 * @param themeName
	 * @param id
	 * @return
	 */
	@RequestMapping(value="checkTeamName.json", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkGameName(@RequestParam(required=true) String teamName,@RequestParam(required=true) String id){
		if(id==null || id.equals("undefined")){
			id = null;
		}
		return teamService.findByTeamName(teamName,id)==0?true:false;
	}
	
	/**
	 * 战队 Service定义
	 */
	@Autowired
	private ITeamService teamService;
	
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;
	
	/**
	 * 游戏管理 Service定义
	 */
	@Autowired
	private GameService gameService;	
	
	/**
	 * 战队游戏管理 Service定义
	 */
	@Autowired
	private TeamGameService teamGameService;
}
