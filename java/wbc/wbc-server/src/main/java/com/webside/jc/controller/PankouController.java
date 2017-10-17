/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.jc.controller;

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
import com.webside.jc.model.GameBattle;
import com.webside.jc.model.Pankou;
import com.webside.jc.service.IGameBattleService;
import com.webside.jc.service.IPankouService;
import com.webside.system.dict.service.DictService;
import com.webside.util.DateUtils;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;

/**
 * 比赛盘口Controller
 *
 * @author zengxn
 * @date 2017-02-18 13:41:07
 */
 
@Controller
@RequestMapping("/jc/pankou/")
public class PankouController extends BaseController
{
	/**
	 * 跳转查询比赛盘口页面
	 * @param Model model
	 * 		  HttpServletRequest request
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("listUI")
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
			return Common.BACKGROUND_PATH + "/business/jc/pankou/list";
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
	@RequestMapping(value = "list", method = RequestMethod.POST)
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
		if(parameters.get("pkStatusType")!=null && StringUtils.isNotBlank(parameters.get("pkStatusType").toString())){
			parameters.put("currentTime", System.currentTimeMillis()+"");
			parameters.put("deleteStatus", GlobalConstant.DICTTYPE_IS_DELETE_1);
		}
		
		//3、判断是否是导出操作
		if(pager.getIsExport())
		{
			if(pager.getExportAllData())
			{
				//3.1、导出全部数据
				List<Pankou> list = pankouService.queryListByPage(parameters);
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
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "pkStartTime DESC");
			List<Pankou> list = pankouService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			
			//权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("pankou:edit");
			//列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list,edit?"edit":""));
			return parameters;
		}
	}
	
	/**
	 * 跳转新增比赛盘口页面
	 * @param Model model
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("addUI")
	public String addUI(Model model,String gbId) 
	{
		try
		{
			//玩法类型
			model.addAttribute("pankouTypeList", dictService.getDictType(GlobalConstant.PANKOU_TYPE));
			//比赛状态
			model.addAttribute("isDelete", dictService.getDictType(GlobalConstant.DICTTYPE_IS_DELETE));
			//比赛对战战队 主场 客场
			model.addAttribute("jcTameType", dictService.getDictType(GlobalConstant.JC_TEAM_TYPE));
			
			GameBattle gameBattle = gameBattleService.findById(gbId);
			if(gameBattle!=null){
				//比赛对战ID
				model.addAttribute("gbId", gbId);
				//比赛对战开始时间
				model.addAttribute("gbStartTime", gameBattle.getStartTime());
				//比赛对战规则
				model.addAttribute("gameRule", StringUtils.toInteger(gameBattle.getGameRule()));
			}
			return Common.BACKGROUND_PATH + "/business/jc/pankou/addPK";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 新增比赛盘口 ajax
	 * @param Pankou
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(Pankou entity)
	{
		Map<String, Object> map = null;
		try
		{
			map = new HashMap<String, Object>();
			
			//比赛开始时间
			entity.setPkStartTime(DateUtils.getStringDate(entity.getPkStartTime(),DateUtils._DEFAULT1).getTime()+"");
			//是否让分局
			if(!entity.getPankouType().equals(GlobalConstant.PANKOU_TYPE_1)){
				entity.setPkRangfenNum(null);
				entity.setPkRangFenTeam(null);
			}
			int result = pankouService.insert(entity);
			
			if(result > 0)
			{
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
			}
			else
			{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败");
			}
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
		
		return map;
	}
	
	/**
	 * 跳转编辑比赛盘口页面
	 * @param Model model
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("editUI")
	public String editUI(Model model, HttpServletRequest request, String id) 
	{
		Pankou entity = null;
		PageUtil page = null;
	
		try
		{
			entity = pankouService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			
			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			
			//玩法类型
			model.addAttribute("pankouTypeList", dictService.getDictType(GlobalConstant.PANKOU_TYPE));
			//比赛状态
			model.addAttribute("isDelete", dictService.getDictType(GlobalConstant.DICTTYPE_IS_DELETE));
			//比赛对战战队 主场 客场
			model.addAttribute("jcTameType", dictService.getDictType(GlobalConstant.JC_TEAM_TYPE));
			
			GameBattle gameBattle = gameBattleService.findById(entity.getGbId());
			if(gameBattle!=null){
				//比赛对战开始时间
				model.addAttribute("gbStartTime", gameBattle.getStartTime());
				//比赛对战规则
				model.addAttribute("gameRule", StringUtils.toInteger(gameBattle.getGameRule()));
				//主场战队id
				model.addAttribute("homeTeam", gameBattle.getHomeTeam());
				//主场战队名称
				model.addAttribute("homeTeamName", gameBattle.getHt().getTeamName());
				//客场战队id
				model.addAttribute("awayTeam", gameBattle.getAwayTeam());
				//客场战队名称
				model.addAttribute("awayTeamName", gameBattle.getAt().getTeamName());
			}
			
			return Common.BACKGROUND_PATH + "/business/jc/pankou/formPK";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 修改比赛盘口
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Object update(Pankou entity) throws AjaxException
	{
		Map<String, Object> map = null;
		
		try
		{
			map = new HashMap<String, Object>();
			
			//比赛开始时间
			if(!StringUtils.isEmpty(entity.getPkStartTime())){
				entity.setPkStartTime(DateUtils.getStringDate(entity.getPkStartTime(),DateUtils._DEFAULT1).getTime()+"");
			}
			
			//比赛结束时间
			if(StringUtils.isEmpty(entity.getPkEndTime())){
				entity.setPkEndTime(null);
			}
			
			//结束时间或者获胜战队两者其一有值就需要满足两者都有值
			if(!StringUtils.isEmpty(entity.getPkEndTime()) || !StringUtils.isEmpty(entity.getPkVictory())){
				if(StringUtils.isEmpty(entity.getPkEndTime()) || StringUtils.isEmpty(entity.getPkVictory())){
					map.put("success", Boolean.FALSE);
					map.put("data", null);
					map.put("message", "盘口结束时间与胜方战队必须同步存在或消失");
					return map;
				}else{
					entity.setPkEndTime(DateUtils.getStringDate(entity.getPkEndTime(),DateUtils._DEFAULT1).getTime()+"");
					//结束时间不为空时，需要判断 当前时间是否大于比赛开始时间
					if(StringUtils.toLong(entity.getPkStartTime())>System.currentTimeMillis()){
						map.put("success", Boolean.FALSE);
						map.put("data", null);
						map.put("message", "盘口开始时间未到，不能结束盘口");
						return map;
					}else{
						//判断结束时间是否大于开始时间
						if(StringUtils.toLong(entity.getPkStartTime())>=StringUtils.toLong(entity.getPkEndTime())){
							map.put("success", Boolean.FALSE);
							map.put("data", null);
							map.put("message", "盘口结束时间必须大于盘口开始时间");
							return map;
						}
					}
				}
			}
			
			//是否让分局
			if(!GlobalConstant.PANKOU_TYPE_1.equals(entity.getPankouType())){
				entity.setPkRangfenNum(null);
				entity.setPkRangFenTeam(null);
			}
			
			int result = pankouService.update(entity);
			if(result > 0)
			{
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "编辑成功");
			}
			else
			{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "编辑失败");
			}
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
		
		return map;
	}
	
	/**
	 * 根据比赛对战id以及盘口局数，查询已存在的同局数的开始时间，没有返回空
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping(value = "getSatrtTimeByInningNum", method = RequestMethod.POST)
	@ResponseBody
	public Object getSatrtTimeByInningNum(String gbId,String inningNum)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Pankou pankou = pankouService.findByGbIdInningNum(gbId, inningNum);
		if(pankou == null){
			map.put("success", Boolean.FALSE);
		}else{
			map.put("success", Boolean.TRUE);
			map.put("data", DateUtils.longToString(StringUtils.toLong(pankou.getPkStartTime())));
		}
		return map;
	}
	
	/**
	 * 比赛盘口 Service定义
	 */
	@Autowired
	private IPankouService pankouService;
	
	/**
	 * 字典 Service定义
	 */
	@Autowired
	private DictService dictService;
	
	/**
	 * 比赛 Service定义
	 */
	@Autowired
	private IGameBattleService gameBattleService;
}
