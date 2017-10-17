package com.webside.user.message.web;

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
import com.webside.common.GlobalConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.service.UserIncrementService;

@Controller
@Scope("prototype")
@RequestMapping("/my/message")
public class UserMessageCtrl extends BaseController{

	@Autowired
	private IUserMessageService userMessageService;
	
	@Autowired
	private UserIncrementService userIncrementService;
	
	@Autowired
	private IUserCacheService userCacheService;
	
	/**
	 * 进如你的通知页面
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String addressUI() {
		return Common.BACKGROUND_PATH_WEB + "/my/message/message";
	}
	
	/**
	 * 加载通知列表数据
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
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "state,createTime DESC");
			
			//条件
			String userId = ShiroAuthenticationManager.getUserId();
			if(StringUtils.isNotBlank(userId)){
				parameters.put("userId", userId);
				parameters.put("isDeleted", GlobalConstant.DICTTYPE_IS_DELETE_0);
				JSONArray list = userMessageService.updateListByPage(parameters);
				parameters.clear();
				parameters.put("isSuccess", Boolean.TRUE);
				parameters.put("nowPage", pager.getNowPage());
				parameters.put("pageSize", pager.getPageSize());
				parameters.put("pageCount", page.getPages());
				parameters.put("recordCount", page.getTotal());
				parameters.put("startRecord", page.getStartRow());
				parameters.put("list", list);
			}else{
				parameters.put("isSuccess", Boolean.FALSE);
			}
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			logger.error("ajax获取通知列表查询数据出错：", e);
		}
		return parameters;
	}
	
	/**
	 * 获取未读消息数量
	 * @param gridPager
	 * @return
	 */
	@RequestMapping(value="/getUnread", method = RequestMethod.POST)
	@ResponseBody
	public Object checkUnread(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			String userId = ShiroAuthenticationManager.getUserId();
			if(userId==null){
				parameters.put("isSuccess", Boolean.FALSE);
			}else{
				UserEntity cacheUser = userCacheService.getUserEntityByUserId(userId);
				int count = 0;
				if(cacheUser!=null && cacheUser.getUserIncrement()!=null){
					count = cacheUser.getUserIncrement().getUnreadNum();
				}
				parameters.put("isSuccess", Boolean.TRUE);
				parameters.put("count", count);
			}
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			logger.error("获取未读消息数量出错：", e);
		}
		return parameters;
	}
}
