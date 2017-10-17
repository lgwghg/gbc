/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.match.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.webside.match.entities.GameEvent;
import com.webside.match.service.IGameEventService;
import com.webside.match.service.impl.GameService;
import com.webside.system.dict.service.DictService;
import com.webside.util.DateUtils;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;

/**
 * 游戏赛事Controller
 *
 * @author zengxn
 * @date 2016-11-23 17:36:43
 */
 
@Controller
@RequestMapping("/match/gameEvent/")
public class GameEventController extends BaseController
{
	/**
	 * 跳转查询游戏赛事页面
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
			model.addAttribute("geStatus", dictService.getDictType(GlobalConstant.GAME_EVENT_STATUS));
			model.addAttribute("gameList", gameService.queryPartList(null));
			return Common.BACKGROUND_PATH + "/business/match/gameEvent/list";
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
		
		if(parameters.get("startTime")!=null && StringUtils.isNotBlank(parameters.get("startTime").toString())){
			parameters.put("startTime", DateUtils.parseDate(parameters.get("startTime")).getTime());
		}
		if(parameters.get("endTime")!=null && StringUtils.isNotBlank(parameters.get("endTime").toString())){
			parameters.put("endTime", DateUtils.parseDate(parameters.get("endTime")).getTime());
		}
		
		//3、判断是否是导出操作
		if(pager.getIsExport())
		{
			if(pager.getExportAllData())
			{
				//3.1、导出全部数据
				List<GameEvent> list = gameEventService.queryListByPage(parameters);
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
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "start_time");
			List<GameEvent> list = gameEventService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			
			//权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("gameEvent:edit");
			//列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list,edit?"edit":""));
			return parameters;
		}
	}
	
	/**
	 * 跳转新增游戏赛事页面
	 * @param Model model
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) 
	{
		try
		{
			model.addAttribute("geStatus", dictService.getDictType(GlobalConstant.GAME_EVENT_STATUS));
			model.addAttribute("gameList", gameService.queryPartList(null));
			return Common.BACKGROUND_PATH + "/business/match/gameEvent/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 新增游戏赛事 ajax
	 * @param GameEvent
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(GameEvent entity)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			boolean check = false;
			if(gameEventService.findByEventName(entity.getEventName(),null)==0){
				check = true;
			}else{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "赛事名称已存在!");
			}
			if(check){
				int result = gameEventService.insert(entity);
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
	 * 跳转编辑游戏赛事页面
	 * @param Model model
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) 
	{
		GameEvent entity = null;
		PageUtil page = null;
	
		try
		{
			entity = gameEventService.findById(id);
			GameEvent gameEvent = new GameEvent();
			BeanUtils.copyProperties(entity, gameEvent);
			
			if(StringUtils.isNotEmpty(entity.getStartTime())){
				gameEvent.setStartTime(DateUtils.longToString(StringUtils.toLong(entity.getStartTime())));
			}
			if(StringUtils.isNotEmpty(entity.getEndTime())){
				gameEvent.setEndTime(DateUtils.longToString(StringUtils.toLong(entity.getEndTime())));
			}
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			
			model.addAttribute("page", page);
			model.addAttribute("entity", gameEvent);
			model.addAttribute("geStatus", dictService.getDictType(GlobalConstant.GAME_EVENT_STATUS));
			model.addAttribute("gameList", gameService.queryPartList(null));
			return Common.BACKGROUND_PATH + "/business/match/gameEvent/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 修改游戏赛事
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(GameEvent entity) throws AjaxException
	{
		Map<String, Object> map = null;
		
		try
		{
			map = new HashMap<String, Object>();
			boolean check = false;
			if(gameEventService.findByEventName(entity.getEventName(),entity.getId())==0){
				check = true;
			}else{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "赛事名称已存在!");
			}
			if(check){
				int result = gameEventService.update(entity);
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
	 * 检索赛事名称是否存在
	 * @param eventName
	 * @param id
	 * @return
	 */
	@RequestMapping(value="checkEventName.json", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkEnglishName(@RequestParam(required=true) String eventName,@RequestParam(required=true) String id){
		if(id==null || id.equals("undefined")){
			id = null;
		}
		return gameEventService.findByEventName(eventName,id)==0?true:false;
	}
	
	/**
	 * 根据游戏id获取赛事列表
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value="findGameEventList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findGameEventList(@RequestParam(required=true) String gameId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		try {
			if(StringUtils.isNotBlank(gameId) && !gameId.equals("undefined")){
				map.put("result", true);
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonObject = null;
				List<GameEvent> list = gameEventService.findListByGameId(gameId);
				for (GameEvent gameEvent : list) {
					jsonObject = new JSONObject();
					jsonObject.put("eventId", gameEvent.getId());
					jsonObject.put("eventName", gameEvent.getEventName());
					jsonArray.add(jsonObject);
				}
				map.put("data", jsonArray);
			}
		} catch (Exception e) {
			map.put("errorMsg", "服务器异常");
			logger.error("ajax根据游戏id获取赛事列表出错：", e);
		}
		return map;
	}
	
	/**
	 * 游戏赛事 Service定义
	 */
	@Autowired
	private IGameEventService gameEventService;
	
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
}
