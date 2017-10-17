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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.jc.service.IGameBattleService;
import com.webside.match.entities.Game;
import com.webside.match.service.IGameService;

@Controller
@Scope("prototype")
@RequestMapping("/game/")
public class GameCtrl extends BaseController{

	/**
	 * 游戏 Service定义
	 */
	@Autowired
	private IGameService gameService;
	
	/**
	 * 比赛对战 Service定义
	 */
	@Autowired
	private IGameBattleService gameBattleService;
	
	/**
	 * 竞猜页面游戏列表
	 * @author zengxn
	 */
	@RequestMapping(value="gbGamelist", method = RequestMethod.POST)
	@ResponseBody
	public Object gbGamelist(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			List<Game> list = gameService.queryPartList(null);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			for (Game game : list) {
				jsonObject = new JSONObject();
				jsonObject.put("gameId", game.getId());
				jsonObject.put("gameName", game.getGameName());
				jsonObject.put("bgImg", game.getBgImg());
				jsonObject.put("littleImg", game.getLittleImg());
				jsonObject.put("englishName", game.getEnglishName());
				jsonObject.put("gbCount", gameBattleService.countGameBattleNum(game.getId()));
				jsonArray.add(jsonObject);
			}
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("list", jsonArray);
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			logger.error("ajax获取竞猜页面游戏列表数据出错：", e);
		}
		return parameters;
	}
	
	/**
	 * 首页热门游戏列表
	 * @author zengxn
	 */
	@RequestMapping(value="indexGamelist", method = RequestMethod.POST)
	@ResponseBody
	public Object indexGamelist(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			PageHelper.startPage(1,3, "sort_num desc");
			List<Game> list = gameService.queryPartListByPage(null);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			for (Game game : list) {
				jsonObject = new JSONObject();
				jsonObject.put("gameName", game.getGameName());
				jsonObject.put("bigImg", game.getBigImg());
				jsonObject.put("englishName", game.getEnglishName());
				jsonArray.add(jsonObject);
			}
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("list", jsonArray);
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			logger.error("ajax获取首页热门游戏列表数据出错：", e);
		}
		return parameters;
	}
}
