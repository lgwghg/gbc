/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.exchange.web;

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
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.dtgrid.model.Pager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.exchange.entities.UserExchangeLog;
import com.webside.user.exchange.service.IUserExchangeLogService;

/**
 * 用户兑换Controller
 * 
 * @author tianguifang
 * @date 2016-11-24 16:19:27
 */

@Controller
@Scope("prototype")
@RequestMapping("/my/exchangeLog")
public class UserExchangeLogCtrl extends BaseController {
	/**
	 * 商品兑换 Service定义
	 */
	@Autowired
	private IUserExchangeLogService userExchangeLogService;
	
	/**
	 * 进入兑换记录页面
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping("")
	public String exchangeUI() {
		return Common.BACKGROUND_PATH_WEB + "/my/exchangeLog/exchangeLog";
	}
	
	/**
	 * 加载兑换记录列表
	 * @param gridPager
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value="list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			//1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			
			//设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "");
			parameters = pager.getParameters();
			
			parameters.put("userId", ShiroAuthenticationManager.getUserId());
			parameters.put("exchangeStatus", 1);
			List<UserExchangeLog> list = userExchangeLogService.findListByPage(parameters);
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
			parameters.put("message", e.getMessage());
			logger.error("ajax获取兑换记录列表数据出错：", e);
		}
		return parameters;
	}
	
	/**
	 * 新增商品兑换 ajax
	 * @param UserExchangeLog
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(String goodsId, Integer salesNum, String addressId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userExchangeLogService.insert(goodsId, salesNum, addressId);
			map.put("success", Boolean.TRUE);
			map.put("data", null);
			map.put("message", "兑换成功");
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "兑换失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 修改兑换记录
	 * @param entity
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public Object updateStatus(String id, String exchangeStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserExchangeLog entity = userExchangeLogService.updateStatus(id, exchangeStatus);
			map.put("success", Boolean.TRUE);
			map.put("data", entity);
			map.put("message", "修改成功");
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "修改失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 商品售出，更新状态
	 * @param ids
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "updateStatusForSales", method = RequestMethod.POST)
	@ResponseBody
	public Object updateStatusForSales(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int count = userExchangeLogService.updateStatusForSales(ids);
			if(count > 0) {
				map.put("success", Boolean.TRUE);
				map.put("message", "修改成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "请先兑换商品");
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "修改失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 全部购买点卡
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "buyAll", method = RequestMethod.POST)
	@ResponseBody
	public Object buyAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = userExchangeLogService.insertForbuyAll();
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "购买失败:" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}
	
}
