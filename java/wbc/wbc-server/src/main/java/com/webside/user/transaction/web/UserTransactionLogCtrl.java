/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.transaction.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.dict.service.DictService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;

/**
 * 用户交易记录Controller
 * 
 * @author tianguifang
 * @date 2016-11-24 15:54:19
 */

@Controller
@Scope("prototype")
@RequestMapping("/my/transactionLog")
public class UserTransactionLogCtrl extends BaseController {

	/**
	 * 用户交易记录 Service定义
	 */
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;
	
	/**
	 * 交易记录进入页面
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping("")
	public String transactionUI(Model model) {
		model.addAttribute("typeList", dictService.getDictType(GlobalConstant.USER_TRANSACTION_TYPE));
		return Common.BACKGROUND_PATH_WEB + "/my/transactionLog/transactionLog";
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @param gridPager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			// 1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);

			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "utTime desc, goldNum desc");
			parameters = pager.getParameters();
			
			parameters.put("userId", ShiroAuthenticationManager.getUserId());
			List<UserTransactionLog> list = userTransactionLogService.queryListByPage(parameters);
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

}
