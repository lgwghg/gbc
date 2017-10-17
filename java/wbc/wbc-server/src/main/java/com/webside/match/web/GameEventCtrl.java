package com.webside.match.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.dtgrid.model.Pager;
import com.webside.jc.service.IGameBattleService;
import com.webside.match.entities.GameEvent;
import com.webside.match.service.IGameEventService;
import com.webside.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/gameEvent/")
public class GameEventCtrl extends BaseController{

	/**
	 * 游戏 Service定义
	 */
	@Autowired
	private IGameEventService gameEventService;
	
	/**
	 * 比赛场次 Service定义
	 */
	@Autowired
	private IGameBattleService gameBattleService;
	
	/**
	 * 竞猜页面赛事列表
	 * @author zengxn
	 */
	@RequestMapping(value="gbGameEventlist", method = RequestMethod.POST)
	@ResponseBody
	public Object history(String gridPager,String gameId){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			//1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			
			//设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "distanceTime");
			
			//条件
			if(StringUtils.isNotBlank(gameId)){
				parameters.put("gameId", gameId);
			}
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			//JSONObject jsonGameBattle = null;
			List<GameEvent> list = gameEventService.gbGameEventList(parameters);
			for (GameEvent gameEvent : list) {
				jsonObject = new JSONObject();
				jsonObject.put("eventId", gameEvent.getId());
				jsonObject.put("eventName", gameEvent.getEventName());
				jsonObject.put("eventImg", gameEvent.getEventImg());
				//前台暂时不需要在赛事列表显示该内容
				/*GameBattle gameBattle = gameBattleService.getNewestGameBattle(gameEvent.getId());
				if(gameBattle!=null){
					jsonGameBattle = new JSONObject();
					jsonGameBattle.put("id", gameBattle.getId());
					if(StringUtils.toLong(gameBattle.getStartTime())-System.currentTimeMillis()<1000*60*10){
						jsonGameBattle.put("startTime", "即将开始");
					}else{
						jsonGameBattle.put("startTime", DateUtils.longToString(StringUtils.toLong(gameBattle.getStartTime())));
					}
					jsonGameBattle.put("vs", gameBattle.getHt().getTeamName() +" VS "+ gameBattle.getAt().getTeamName());
				}
				jsonObject.put("gameBattle", jsonGameBattle);*/
				jsonArray.add(jsonObject);
			}
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			parameters.put("list", jsonArray);
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			logger.error("ajax获取竞猜页面赛事列表数据出错：", e);
		}
		return parameters;
	}
}
