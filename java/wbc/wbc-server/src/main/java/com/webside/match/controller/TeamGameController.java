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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.base.basecontroller.BaseController;
import com.webside.match.entities.TeamGame;
import com.webside.match.service.impl.TeamGameService;
import com.webside.util.StringUtils;

/**
 * 游戏Controller
 *
 * @author zengxn
 * @date 2016-11-25 11:31:27
 */
 
@Controller
@RequestMapping("/match/teamGame/")
public class TeamGameController extends BaseController
{
	
	/**
	 * 根据游戏id获取战队列表
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value="findTeamList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findTeamList(@RequestParam(required=true) String gameId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		try {
			if(StringUtils.isNotBlank(gameId) && !gameId.equals("undefined")){
				map.put("result", true);
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonObject = null;
				List<TeamGame> list = teamGameService.findListByGameId(gameId);
				for (TeamGame teamGame : list) {
					jsonObject = new JSONObject();
					jsonObject.put("teamId", teamGame.getTeamId());
					jsonObject.put("teamName", teamGame.getTeamName());
					jsonArray.add(jsonObject);
				}
				map.put("data", jsonArray);
			}
		} catch (Exception e) {
			map.put("errorMsg", "服务器异常");
			logger.error("ajax根据游戏id获取战队列表出错：", e);
		}
		return map;
	}
	
	/**
	 * 战队游戏关联管理 Service定义
	 */
	@Autowired
	private TeamGameService teamGameService;
}
