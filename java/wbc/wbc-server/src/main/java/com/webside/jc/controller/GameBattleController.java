/*******************************************************************************
 * All rights reserved. 
 * 
 * @author LiGan
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.jc.model.GameBattle;
import com.webside.jc.service.IGameBattleService;
import com.webside.match.entities.TeamGame;
import com.webside.match.service.IGameEventService;
import com.webside.match.service.IGameService;
import com.webside.match.service.ITeamService;
import com.webside.match.service.impl.TeamGameService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.dict.service.DictService;
import com.webside.user.model.UserEntity;
import com.webside.util.DateUtils;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;

/**
 * 比赛对战Controller
 * @author LiGan
 * @date 2016-11-23 14:57:30
 */
 
@Controller
@RequestMapping("/gameBattle/")
public class GameBattleController extends BaseController
{
	/**
	 * 比赛对战 Service定义
	 */
	@Autowired
	private IGameBattleService gameBattleService;
	
	@Autowired
	private DictService dictService;
	
	/**
	 * 游戏 Service定义
	 */
	@Autowired
	private IGameService gameService;
	
	/**
	 * 游戏赛事 Service定义
	 */
	@Autowired
	private IGameEventService gameEventService;
	
	/**
	 * 战队 Service定义
	 */
	@Autowired
	private ITeamService teamService;
	
	/**
	 * 战队游戏关联管理 Service定义
	 */
	@Autowired
	private TeamGameService teamGameService;
	
	/**
	 * 跳转查询比赛对战页面
	 * @param Model model
	 * 		  HttpServletRequest request
	 * @throws Exception
	 * @author LiGan
	 */
	@RequestMapping("listUI")
	public String listUI(Model model, HttpServletRequest request) 
	{
		PageUtil page = null;
	
		try{
			page = new PageUtil();
			
			if(request.getParameterMap().containsKey("page")){
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}
			model.addAttribute("page", page);
			//比赛规则
			model.addAttribute("gameRuleType", dictService.getDictType(GlobalConstant.GAME_RULE));
			//比赛分类
			model.addAttribute("gbStatusType", dictService.getDictType(GlobalConstant.GB_STATUS));
			//比赛状态
			model.addAttribute("isDelete", dictService.getDictType(GlobalConstant.DICTTYPE_IS_DELETE));
			//游戏
			Map<String,Object> parameter = new HashMap<String,Object>();
			parameter.put("gameStatus", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
			model.addAttribute("gameList", gameService.queryPartList(parameter));
			//赛事
			parameter.clear();
			model.addAttribute("gameEventList", gameEventService.queryListByPage(parameter));
			
			//战队
			model.addAttribute("teamList", teamService.queryListByPage(parameter));
			return Common.BACKGROUND_PATH + "/business/jc/list";
		}catch(Exception e){
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
		if(parameters.get("gbType")!=null && StringUtils.isNotBlank(parameters.get("gbType").toString())){
			parameters.put("currentTime", System.currentTimeMillis()+"");
			parameters.put("deleteStatus", GlobalConstant.DICTTYPE_IS_DELETE_1);
		}
		//3、判断是否是导出操作
		if(pager.getIsExport())
		{
			if(pager.getExportAllData())
			{
				//3.1、导出全部数据
				List<GameBattle> list = gameBattleService.queryListByPage(parameters);
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
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "startTime DESC");
			List<GameBattle> list = gameBattleService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			
			//权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("gameBattle:edit");
			//列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list,edit?"edit":""));
			return parameters;
		}
	}
	
	/**
	 * 跳转新增比赛对战页面
	 * @param Model model
	 * @throws Exception
	 * @author LiGan
	 */
	@RequestMapping("addUI")
	public String addUI(Model model) {
		try{
			//比赛规则
			model.addAttribute("gameRuleType", dictService.getDictType(GlobalConstant.GAME_RULE));
			//比赛状态
			model.addAttribute("isDelete", dictService.getDictType(GlobalConstant.DICTTYPE_IS_DELETE));
			//比赛对战战队 主场 客场
			model.addAttribute("jcTameType", dictService.getDictType(GlobalConstant.JC_TEAM_TYPE));
			Map<String,Object> parameter = new HashMap<String,Object>();
			parameter.put("gameStatus", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
			model.addAttribute("gameList", gameService.queryPartList(parameter));
			return Common.BACKGROUND_PATH + "/business/jc/addGb";
		}catch(Exception e){
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 新增比赛对战 ajax
	 * @param GameBattle
	 * @throws Exception
	 * @author LiGan
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(GameBattle entity)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			//比赛开始时间
			entity.setStartTime(DateUtils.getStringDate(entity.getStartTime(),DateUtils._DEFAULT1).getTime()+"");
			int result = gameBattleService.insert(entity);
			if(result > 0){
				map.put("success", Boolean.TRUE);
				map.put("data", entity.getId());
				map.put("message", "添加成功");
			}
			else{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败");
			}
		}
		catch(Exception e){
			throw new AjaxException(e);
		}
		return map;
	}
	
	/**
	 * 跳转编辑比赛对战页面
	 * @param Model model
	 * @throws Exception
	 * @author LiGan
	 */
	@RequestMapping("editUI")
	public String editUI(Model model, HttpServletRequest request, String id) 
	{
		GameBattle entity = null;
		PageUtil page = null;
	
		try{
			entity = gameBattleService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			
			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			
			//比赛规则
			model.addAttribute("gameRuleType", dictService.getDictType(GlobalConstant.GAME_RULE));
			//比赛状态
			model.addAttribute("isDelete", dictService.getDictType(GlobalConstant.DICTTYPE_IS_DELETE));
			
			//比赛对战战队 主场 客场
			model.addAttribute("jcTameType", dictService.getDictType(GlobalConstant.JC_TEAM_TYPE));
			
			//玩法类型
			model.addAttribute("pankouType", dictService.getDictType(GlobalConstant.PANKOU_TYPE));
			
			//盘口现状
			model.addAttribute("pkStatusType", dictService.getDictType(GlobalConstant.GB_STATUS));
			
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			List<TeamGame> list = teamGameService.findListByGameId(entity.getGameId());
			
			for (TeamGame teamGame : list) {
				jsonObject = new JSONObject();
				jsonObject.put("teamId", teamGame.getTeamId());
				jsonObject.put("teamName", teamGame.getTeamName());
				jsonArray.add(jsonObject);
			}
			model.addAttribute("gameTeamList", jsonArray);
			
			return Common.BACKGROUND_PATH + "/business/jc/formGb";
		}catch(Exception e){
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 修改比赛对战
	 * @param entity
	 * @throws Exception
	 * @author LiGan
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Object update(GameBattle entity) throws AjaxException
	{
		Map<String, Object> map = new HashMap<String, Object>();;
		
		try{
			
			if(!StringUtils.isEmpty(entity.getStartTime())){
				entity.setStartTime(DateUtils.getStringDate(entity.getStartTime(),DateUtils._DEFAULT1).getTime()+"");
			}
			//结束时间不为空时，需要判断 当前时间是否大于比赛开始时间
			if(!StringUtils.isEmpty(entity.getEndTime())){
				entity.setEndTime(DateUtils.getStringDate(entity.getEndTime(),DateUtils._DEFAULT1).getTime()+"");
				if(Long.parseLong(entity.getStartTime())>System.currentTimeMillis()){
					map.put("success", Boolean.FALSE);
					map.put("data", null);
					map.put("message", "比赛开始时间未到，不能结束比赛");
					return map;
				}
			}
			
			//修改人 修改时间
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			entity.setUpdateUser(user.getId());
			entity.setUpdateDate(System.currentTimeMillis()+"");
			
			if(StringUtils.isEmpty(entity.getEndTime())){
				entity.setEndTime(null);
			}
			int result = gameBattleService.update(entity);
			if(result > 0){
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "编辑成功");
			}else{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "编辑失败");
			}
		}catch(Exception e){
			throw new AjaxException(e);
		}
		return map;
	}
}
