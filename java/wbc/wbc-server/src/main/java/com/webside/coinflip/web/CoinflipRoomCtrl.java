package com.webside.coinflip.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.coinflip.model.CoinflipRoom;
import com.webside.coinflip.model.CoinflipUser;
import com.webside.coinflip.model.JoinBtnVo;
import com.webside.coinflip.service.ICoinflipRoomService;
import com.webside.coinflip.service.ICoinflipUserService;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.common.redis.listener.SendMessage;
import com.webside.dtgrid.model.Pager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.shiro.cache.redis.VCache;

/**
 * 翻硬币游戏房间 前台Controller
 *
 * @author tianguifang
 * @date 2017-07-26 14:42:49
 */
@Controller
@RequestMapping("/coinflip")
public class CoinflipRoomCtrl {
	public static final Logger logger = LoggerFactory.getLogger(CoinflipRoomCtrl.class);
	@Autowired
	private ICoinflipRoomService coinflipRoomService;
	@Autowired
	private ICoinflipUserService coinflipUserService;

	/**
	 * 消息队列 定义
	 */
	@Autowired
	private SendMessage sendMessage;

	/**
	 * 打开翻硬币游戏页面
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String coinflip() {
		return Common.BACKGROUND_PATH_WEB + "/coinflip/coinflip";
	}

	/**
	 * 活动中的游戏数据,不分页，只展示未结束的游戏房间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/playing", method = RequestMethod.POST)
	@ResponseBody
	public Object playingCoinflipList() {
		JSONObject result = new JSONObject();
		result = coinflipRoomService.queryPlayingCoinflip();
		result.put("now", System.currentTimeMillis());
		return result;
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String historyCoinflip() {
		return Common.BACKGROUND_PATH_WEB + "/coinflip/history";
	}

	@RequestMapping(value = "/u/history", method = RequestMethod.GET)
	public String myhistoryCoinflip() {
		return Common.BACKGROUND_PATH_WEB + "/coinflip/myHistory";
	}

	/**
	 * 历史活动的游戏数据,分页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/historyData", method = RequestMethod.POST)
	@ResponseBody
	public Object historyCoinflipPg(String gridPager, HttpServletResponse response) {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		// parameters.put("userId", ShiroAuthenticationManager.getUserId());
		parameters.put("status", GlobalConstant.COINFLIP_ROOM_STATUS_OPENED_3);
		Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "create_time desc");
		JSONArray list = coinflipRoomService.queryHistoryListByPage(parameters);
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
		parameters.put("nowPage", pager.getNowPage());
		parameters.put("pageSize", pager.getPageSize());
		parameters.put("pageCount", page.getPages());
		parameters.put("recordCount", page.getTotal());
		parameters.put("startRecord", page.getStartRow());
		// 列表展示数据
		parameters.put("list", list);
		return parameters;
	}

	/**
	 * 用户参与的活动的游戏数据,分页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/u/historyData", method = RequestMethod.POST)
	@ResponseBody
	public Object userCoinflipPg(String gridPager, HttpServletResponse response) {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		parameters.put("userId", ShiroAuthenticationManager.getUserId());
		Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "create_time desc");
		JSONArray list = coinflipRoomService.queryMyJoinListByPage(parameters);
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
		parameters.put("nowPage", pager.getNowPage());
		parameters.put("pageSize", pager.getPageSize());
		parameters.put("pageCount", page.getPages());
		parameters.put("recordCount", page.getTotal());
		parameters.put("startRecord", page.getStartRow());
		// 列表展示数据
		parameters.put("list", list);
		return parameters;
	}
	
	/**
	 * 用户参与的活动的游戏数据，统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "/u/historyStatistics", method = RequestMethod.POST)
	@ResponseBody
	public Object userHistoryStatistics(HttpServletResponse response) {
		JSONObject result = new JSONObject();
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			result.put("success", false);
			result.put("msg", "请先登录");
			
		}else{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userId);
			JSONObject allJsonObject = coinflipRoomService.findUserHistoryStatistics(param);
			param.put("today", "true");
			JSONObject todayJsonObject = coinflipRoomService.findUserHistoryStatistics(param);
			result.put("success", Boolean.TRUE);
			result.put("all", allJsonObject);
			result.put("today", todayJsonObject);
		}
		return result;
	}

	/**
	 * 用户创建房间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/u/createRoom", method = RequestMethod.POST)
	@ResponseBody
	public Object userCreateRoom(CoinflipRoom room) {
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(ShiroAuthenticationManager.getUserId())) {
			result.put("success", false);
			result.put("msg", "请先登录");
			return result;
		}
		String key = "coinCreateRoom_" + ShiroAuthenticationManager.getUserId();
		if (VCache.get(key) != null) {
			result.put("success", false);
			result.put("msg", "游戏创建太频繁，稍候再建");
			return result;
		}
		VCache.set(key, 1, 3);// 3秒
		if (room == null || room.getOwnerGoldNum() == null || room.getOwnerGoldNum() <=0 
				|| room.getRoomOwner() == null || room.getRoomOwner().getIsCoinFront() == null
				|| (room.getRoomOwner().getIsCoinFront() != 0 && room.getRoomOwner().getIsCoinFront() != 1)) {
			result.put("success", false);
			result.put("msg", "参数错误");
			return result;
		}
		result = coinflipRoomService.insertCoinflipRoom(room);
		return result;
	}

	/**
	 * 用户加入房间密码验证
	 * 
	 * @return
	 */
	@RequestMapping(value = "/u/checkRoomPwd", method = RequestMethod.POST)
	@ResponseBody
	public Object checkRoomPwd(String roomId, String password) {
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(ShiroAuthenticationManager.getUserId())) {
			result.put("success", false);
			result.put("msg", "请先登录");
			return result;
		}
		if (StringUtils.isBlank(roomId) || StringUtils.isBlank(password)) {
			result.put("success", false);
			result.put("msg", "参数错误");
			return result;
		}
		CoinflipRoom room = coinflipRoomService.findById(roomId);
		if (room != null) {
			if (StringUtils.isBlank(room.getPassword()) || room.getPassword().equals(password)) {
				result.put("success", true);
				result.put("msg", "success");
			} else {
				result.put("success", false);
				result.put("msg", "请输入正确的房间密码");
			}
		} else {
			result.put("success", false);
			result.put("msg", "游戏房间不存在");
		}
		return result;
	}

	/**
	 * 用户加入房间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/u/joinRoom", method = RequestMethod.POST)
	@ResponseBody
	public Object userJoinRoom(CoinflipUser joinUser) {
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(ShiroAuthenticationManager.getUserId())) {
			result.put("success", false);
			result.put("msg", "请先登录");
			return result;
		}
		if (joinUser == null || StringUtils.isBlank(joinUser.getRoomId()) || joinUser.getGoldNum() == null || joinUser.getGoldNum() <= 0) {
			result.put("success", false);
			result.put("msg", "参数错误");
			return result;
		}
		result = coinflipUserService.insertCoinflipUser(joinUser);
		return result;
	}

	/**
	 * 用户查看房间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/viewRoom", method = RequestMethod.POST)
	@ResponseBody
	public Object userViewRoom(String roomId) {
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(roomId)) {
			result.put("success", false);
			result.put("msg", "参数错误");
			return result;
		}
		JSONObject room = coinflipRoomService.findRoomDetailById(roomId);
		if (room != null) {
			result.put("success", true);
			result.put("msg", "success");
			result.put("room", room);
			result.put("now", System.currentTimeMillis());
		} else {
			result.put("success", false);
			result.put("msg", "游戏房间不存在");
		}
		return result;
	}

	@RequestMapping(value = "/openRoom", method = RequestMethod.POST)
	@ResponseBody
	public Object openRoom(String roomId) {
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(roomId)) {
			result.put("success", false);
			result.put("msg", "参数错误");
			return result;
		}
		CoinflipRoom room = coinflipRoomService.updateOpenRoom(roomId);
		if (room != null) {
			result.put("success", true);
			result.put("msg", "success");
			result.put("room", room);

		} else {
			result.put("success", false);
			result.put("msg", "游戏还未结束或房间不存在");
		}

		return result;
	}

	@RequestMapping(value = "/noticeJoin", method = RequestMethod.POST)
	@ResponseBody
	public void noticeJoin(String roomId, String joinBtn) {
		CoinflipRoom roomUpdate = new CoinflipRoom();
		roomUpdate.setId(roomId);
		CoinflipRoom room = coinflipRoomService.findById(roomId);
		if (room != null) {
			if ("show".equals(joinBtn)) {
				if (room.getStatus() == 1) {
					sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new JoinBtnVo(roomId, joinBtn, "joinBtn"));
					roomUpdate.setStatus(GlobalConstant.CD_KEY_STATE_0);
					roomUpdate.setUpdateTime(System.currentTimeMillis());
					roomUpdate.setJoinTime(null);
					coinflipRoomService.updateById(roomUpdate);
				}
				if (room.getStatus() == 0) {
					sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new JoinBtnVo(roomId, joinBtn, "joinBtn"));
				}
			} else if ("hide".equals(joinBtn)) {
				sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new JoinBtnVo(roomId, joinBtn, "joinBtn"));
				if (room.getStatus() == 0) {
					roomUpdate.setStatus(GlobalConstant.CD_KEY_STATE_1);
					roomUpdate.setJoinTime(System.currentTimeMillis());
					coinflipRoomService.updateById(roomUpdate);
				}
			}
		}
	}
}
