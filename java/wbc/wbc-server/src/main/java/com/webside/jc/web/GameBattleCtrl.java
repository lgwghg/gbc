package com.webside.jc.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
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
import com.webside.common.GlobalConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.jc.model.GameBattle;
import com.webside.jc.model.UserJc;
import com.webside.jc.service.IGameBattleService;
import com.webside.jc.service.IUserJcService;
import com.webside.jc.vo.GameBattleVo;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.util.StringUtils;


/**
 * @title : 比赛对战业务处理类
 * @author LiGan
 * */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/gb/")
public class GameBattleCtrl extends BaseController {
	
	/**
	 * 比赛对战 Service定义
	 */
	@Autowired
	private IGameBattleService gameBattleService;
		
	/**
	 * 用户缓存 Service定义
	 */
	@Autowired
	private IUserCacheService userCacheService;
	
	@Autowired
	private IUserJcService userJcService;
	
	/**
	 * @title : 首页对战列表
	 * */
	@RequestMapping("homeGbList")
	@ResponseBody
	public Map<String,Object> homeGbList(String gridPager){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		try{
			//1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			//比赛现状
			jsonMap.put("gbType", GlobalConstant.GB_STATUS_1);
			jsonMap.put("currentTime", System.currentTimeMillis()+"");
			//设置比赛对战状态
			jsonMap.put("gbstate", GlobalConstant.DICTTYPE_IS_DELETE_0);
			//设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "startTime");
			List<GameBattleVo> list = gameBattleService.queryGbDetailsListByPage(jsonMap);
			jsonMap.put("isSuccess", Boolean.TRUE);
			jsonMap.put("gbList", list);
			jsonMap.put("recordCount", page.getTotal());
		}catch (Exception e) {
			logger.error("首页对战列表出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
		}
		return jsonMap;
	}
	
	
	
	/**
	 * @title: 比赛对战列表1.10
	 * @param : String gameId
	 * @param : String geId
	 * @param : String teamName
	 * @param : String gbstate
	 * */
	@RequestMapping("getPanKouList")
	@ResponseBody
	public Map<String,Object> getPanKouList(String gridPager,String gameId,String geId,String gbId,String gbstate,String teamName){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		try{
			//1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize());
			jsonMap.put("gameId", gameId);
			jsonMap.put("geId", geId);
			jsonMap.put("gbId", gbId);
			jsonMap.put("gbstate", gbstate);
			jsonMap.put("teamName", teamName);
			UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			if(userEntity!=null){
				jsonMap.put("currentUser", userEntity.getId());
			}
			
			List<GameBattleVo> gb = gameBattleService.queryGbDetailsListByPage(jsonMap);
			
			jsonMap.clear();
			jsonMap.put("isSuccess", Boolean.TRUE);
			jsonMap.put("nowPage", pager.getNowPage());
			jsonMap.put("pageSize", pager.getPageSize());
			jsonMap.put("pageCount", page.getPages());
			jsonMap.put("recordCount", page.getTotal());
			jsonMap.put("startRecord", page.getStartRow());
			
			jsonMap.put("gbList", gb);
		}catch (Exception e) {
			logger.error("比赛对战列表页出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
		}
		return jsonMap;
	}
	/**
	 * @title : 比赛对战列表页
	 * @param : String gameId
	 * @param : String geId
	 * @param : String gbStatus
	 * @param : String teamName
	 * @author LiGan
	 * */
	@RequestMapping("gbList")
	@ResponseBody
	public  Map<String,Object> gbList(String gridPager,String gameId,String geId,String gbType,String teamName){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		try{
			//1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			//设置比赛对战状态
			jsonMap.put("gbType", gbType);
			jsonMap.put("currentTime", System.currentTimeMillis()+"");
			String gbStatus = GlobalConstant.DICTTYPE_IS_DELETE_0;
			
			if(StringUtils.isEmpty(gbType)){
				//页面现在全部时 比赛状态判断去掉
				gbStatus = "";
			}
			
			jsonMap.put("gbStatus", gbStatus);
			jsonMap.put("gameId", gameId);
			jsonMap.put("geId", geId);
			jsonMap.put("teamName", teamName);
			
			//lwh:如果当前用户存在，吧当前用户存入进去
			UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			if(userEntity!=null){
				jsonMap.put("currentUser", userEntity.getId());
			}
			
			
			//设置分页，page里面包含了分页信息
			//Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "startTime");
			Page<Object> page = null;
			//查询比赛对战信息
			List<GameBattle> list = null;
			if(StringUtils.isEmpty(gbType)){
				page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize());
				list = gameBattleService.webQueryListByPage(jsonMap);
			}else{
				page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "startTime");
				list = gameBattleService.queryListByPage(jsonMap);
			}
			
			List<UserJc> ujList = null;
			if(list!=null && list.size()>0){
				//根据比赛现状排序
				DistanceSort ds = new DistanceSort();
				Collections.sort(list, ds);
				
				if(userEntity!=null){
					List<String> listString = new ArrayList<String>();
					for(GameBattle gb:list){
						listString.add(gb.getId());
					}
					Map<String,Object> parameter = new HashMap<String,Object>();
					parameter.put("userId", userEntity.getId());
					parameter.put("gbIds", listString);
					//根据比赛ID 和用户ID 查询当前用户竞猜信息
					ujList = userJcService.queryListByPage(parameter);
				}
			}
			jsonMap.clear();
			jsonMap.put("isSuccess", Boolean.TRUE);
			jsonMap.put("nowPage", pager.getNowPage());
			jsonMap.put("pageSize", pager.getPageSize());
			jsonMap.put("pageCount", page.getPages());
			jsonMap.put("recordCount", page.getTotal());
			jsonMap.put("startRecord", page.getStartRow());
			jsonMap.put("gbList", list);
			jsonMap.put("ujList", ujList);
		}catch (Exception e) {
			logger.error("比赛对战列表页出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
		}
		return jsonMap;
	}
	
	/**
	 * @title : 比赛对战详情页
	 * */
	@RequestMapping(value = "{gbId}")
	public String gameBattleInfo(@PathVariable String gbId, Model model) {
		try {
			Map<String, Object> gbMap = new HashMap<>();
			gbMap.put("sid", gbId);

			GameBattle gb = gameBattleService.find(gbMap);
			if(gb == null) {
				return Common.BACKGROUND_PATH + "/error/error";
			}
			if (StringUtils.isNotBlank(ShiroAuthenticationManager.getUserId())) {
				model.addAttribute("userPhoto_65", userCacheService.getUserEntityByUserId(ShiroAuthenticationManager.getUserId()).getPhoto_65());
			}
			model.addAttribute("gbInfo", gb);
		} catch (Exception e) {
			logger.error("比赛对战详情页跳转出错：", e);
		}
		return Common.BACKGROUND_PATH_WEB + "/match/details";
	}
}
