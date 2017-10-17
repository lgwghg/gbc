package com.webside.roll.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.dtgrid.model.Pager;
import com.webside.roll.service.IRollMemberService;
import com.webside.roll.service.IRollRoomService;
import com.webside.roll.service.IRollWinnerService;
import com.webside.roll.vo.RollMemberVo;
import com.webside.roll.vo.RollRoomVo;
import com.webside.roll.vo.RollWinnerVo;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.service.IUserCacheService;
import com.webside.util.DateUtils;
import com.webside.util.StringUtils;

/**
 * @title : 比赛对战业务处理类
 * @author LiGan
 * */
@Controller
@RequestMapping("/roll")
public class RollRoomCtrl extends BaseController {

	/**
	 * 比赛对战 Service定义
	 */
	@Autowired
	private IRollRoomService rollRoomService;
	@Autowired
	private IRollMemberService rollMemberService;
	@Autowired
	private IRollWinnerService rollWinnerService;
	@Autowired
	private IUserCacheService userCacheService;
	
	@RequestMapping("")
	public String toRollIndex() {
		return Common.BACKGROUND_PATH_WEB + "/roll/rollIndex";
	}
	/**
	 * @title : 比赛对战详情页
	 * */
	@RequestMapping(value = "/{roomCode}")
	public String gameBattleInfo(@PathVariable String roomCode, Model model) {
		try {
			RollRoomVo rollRoom = rollRoomService.findByRoomCode(roomCode);
			if(rollRoom == null) {
				throw new RuntimeException("房间号错误：" + roomCode);
			}
			String userId = ShiroAuthenticationManager.getUserId();
			if (StringUtils.isNotBlank(userId)) {// 是否参与
				model.addAttribute("isJoin", rollMemberService.isJoinRoll(rollRoom.getId(), userId));
				// 头像
				model.addAttribute("userPhoto_65", userCacheService.getUserEntityByUserId(userId).getPhoto_65());
			}
			String endTime = rollRoom.getEndTime();
			if(StringUtils.isNotBlank(endTime)) {
				if(System.currentTimeMillis() >= StringUtils.toLong(endTime)) {// 已结束
					model.addAttribute("isEnd", true);
				}
				rollRoom.setEndTime(DateUtils.longToString(StringUtils.toLong(endTime)));
			}
			if(StringUtils.isNotBlank(rollRoom.getStartTime())) {
				rollRoom.setStartTime(DateUtils.longToString(StringUtils.toLong(rollRoom.getStartTime())));
			}
			model.addAttribute("rollRoom", rollRoom);
		} catch (Exception e) {
			logger.error("房间详情页跳转出错：", e);
			return Common.BACKGROUND_PATH + "/error";
		}
		return Common.BACKGROUND_PATH_WEB + "/roll/rollRoomDetails";
	}
	
	/**
	 * 
	 * @param gridPager
	 * @param typeId 1:最新；2：最热；3：我参与的
	 * @return
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public Object getList(String gridPager) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			// 1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			jsonMap = pager.getParameters();
			jsonMap.put("status", "1");// 有效
			String orderBy = "";
			String typeId = MapUtils.getString(jsonMap, "typeId");
			if ("1".equals(typeId)) {// 最新
				orderBy = "endState desc,t.START_TIME DESC";
			} else if("2".equals(typeId)) {// 最热
				orderBy = "endState desc,count DESC,sum desc";
			} else if("3".equals(typeId)) {// 我参与的
				orderBy = "endState desc,t.START_TIME DESC";
				jsonMap.put("memberId", ShiroAuthenticationManager.getUserId());
			}
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), orderBy);

			List<RollRoomVo> list = rollRoomService.getRollRoomListByPage(jsonMap);

			jsonMap.clear();
			jsonMap.put("isSuccess", Boolean.TRUE);
			jsonMap.put("nowPage", pager.getNowPage());
			jsonMap.put("pageSize", pager.getPageSize());
			jsonMap.put("pageCount", page.getPages());
			jsonMap.put("recordCount", page.getTotal());
			jsonMap.put("startRecord", page.getStartRow());

			jsonMap.put("list", list);
		} catch (Exception e) {
			logger.error("房间列表页出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
		}
		return jsonMap;
	}
	
	/**
	 * 热门推荐
	 * @param gridPager
	 * @return
	 */
	@RequestMapping("/getRollRoomListForHot")
	@ResponseBody
	public Object getRollRoomListForHot(String gridPager) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			// 1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			jsonMap = pager.getParameters();
			jsonMap.put("status", "1");// 有效
			jsonMap.put("currentTime", System.currentTimeMillis());// 当前时间
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize());
			
			List<RollRoomVo> list = rollRoomService.getRollRoomListForHot(jsonMap);
			
			jsonMap.clear();
			jsonMap.put("isSuccess", Boolean.TRUE);
			jsonMap.put("nowPage", pager.getNowPage());
			jsonMap.put("pageSize", pager.getPageSize());
			jsonMap.put("pageCount", page.getPages());
			jsonMap.put("recordCount", page.getTotal());
			jsonMap.put("startRecord", page.getStartRow());
			
			jsonMap.put("list", list);
		} catch (Exception e) {
			logger.error("房间列表页出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
		}
		return jsonMap;
	}
	
	/**
	 * 获取参与人员列表
	 * @param gridPager
	 * @return
	 */
	@RequestMapping("/getMemberList")
	@ResponseBody
	public Object getMemberList(String gridPager) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			// 1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			jsonMap = pager.getParameters();
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize());
			
			List<RollMemberVo> list = rollMemberService.getVoListByMap(jsonMap);
			
			jsonMap.clear();
			jsonMap.put("isSuccess", Boolean.TRUE);
			jsonMap.put("nowPage", pager.getNowPage());
			jsonMap.put("pageSize", pager.getPageSize());
			jsonMap.put("pageCount", page.getPages());
			jsonMap.put("recordCount", page.getTotal());
			jsonMap.put("startRecord", page.getStartRow());
			
			jsonMap.put("list", list);
		} catch (Exception e) {
			logger.error("参与人员名单出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
		}
		return jsonMap;
	}
	
	/**
	 * 获取参与人员列表
	 * @param gridPager
	 * @return
	 */
	@RequestMapping("/getWinnerList")
	@ResponseBody
	public Object getWinnerList(String gridPager) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			// 1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			if(MapUtils.isNotEmpty(pager.getParameters())) {
				jsonMap = pager.getParameters();
			}
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "t.WIN_TIME DESC");
			
			List<RollWinnerVo> list = rollWinnerService.getRollWinnerListByPage(jsonMap);
			
			jsonMap.clear();
			jsonMap.put("isSuccess", Boolean.TRUE);
			jsonMap.put("nowPage", pager.getNowPage());
			jsonMap.put("pageSize", pager.getPageSize());
			jsonMap.put("pageCount", page.getPages());
			jsonMap.put("recordCount", page.getTotal());
			jsonMap.put("startRecord", page.getStartRow());
			
			jsonMap.put("list", list);
		} catch (Exception e) {
			logger.error("获奖人员名单出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
		}
		return jsonMap;
	}
	
	@RequestMapping("addRollWinner")
	@ResponseBody
	public Object addRollWinner(String roomId, String poolIds, int num, String unique, String clear) {
		Map<String, Object> result = null;
		try {
			result = new HashMap<String, Object>();
			rollWinnerService.addRollWinner(roomId, poolIds, num, unique, clear);
			result.put("success", true);
			result.put("data", null);
			result.put("message", "roll成功");
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("data", null);
			result.put("message", "roll失败：" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
}
