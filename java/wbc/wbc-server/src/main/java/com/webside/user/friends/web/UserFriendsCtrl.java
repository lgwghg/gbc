package com.webside.user.friends.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.dtgrid.model.Pager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.friends.service.IUserFriendsService;

@Controller
@Scope("prototype")
@RequestMapping("/my/friends")
public class UserFriendsCtrl extends BaseController{

	@Autowired
	private IUserFriendsService userFriendsService;
	
	/**
	 * 进如推荐好友页面
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String addressUI() {
		return Common.BACKGROUND_PATH_WEB + "/my/friends/friends";
	}
	
	/**
	 * 推荐好友列表
	 * @param gridPager
	 * @author zengxn
	 */
	@RequestMapping(value="/list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			//1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			
			//设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "create_time DESC");
			
			//条件
			String userId = ShiroAuthenticationManager.getUserId();
			if(StringUtils.isNotBlank(userId)){
				parameters.put("userId", userId);
			}
			JSONArray list = userFriendsService.queryPartListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			parameters.put("list", list);
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			logger.error("ajax获取推荐好友列表查询数据出错：", e);
		}
		return parameters;
	}
}
