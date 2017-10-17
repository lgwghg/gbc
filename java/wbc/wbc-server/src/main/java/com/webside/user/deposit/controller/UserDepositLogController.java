/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.deposit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.pay.alipay.util.AliPayUtil;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.deposit.entities.UserDepositLog;
import com.webside.user.deposit.service.IUserDepositLogService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.DateUtils;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;

/**
 * 充值记录Controller
 * 
 * @author tianguifang
 * @date 2016-11-24 16:21:01
 */

@Controller
@RequestMapping("/userDepositLog/")
public class UserDepositLogController extends BaseController {
	@Autowired
	private UserService userService;

	/*
	 * 跳转查询用户钱包页面
	 * 
	 * @param Model model HttpServletRequest request
	 * 
	 * @throws Exception
	 * 
	 * @author tianguifang
	 */
	@RequestMapping("listUI")
	public String listUI(Model model, HttpServletRequest request) {
		PageUtil page = null;
		
		try {
			page = new PageUtil();
			
			if (request.getParameterMap().containsKey("page")) {
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}
			
			model.addAttribute("page", page);
			return Common.BACKGROUND_PATH + "/business/user/deposit/list";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 提现页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("withdrawUI")
	public String withdrawUI(Model model, HttpServletRequest request) {
		try {
			PageUtil page = new PageUtil();
			if (request.getParameterMap().containsKey("page")) {
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}
			model.addAttribute("page", page);
			return Common.BACKGROUND_PATH + "/business/user/withdraw/list";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @param dtGridPager
	 *            Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		if (parameters != null) {
			String userNick = (String) parameters.get("userNick");
			if (userNick != null && StringUtils.isNotBlank(userNick.trim())) {
				UserEntity user = userService.findByNickName(userNick.trim());
				if (user != null) {
					parameters.put("userId", user.getId());
				}
			}

		}
		
		String orderBy = MapUtils.getString(parameters, "orderBy");
		if(StringUtils.isBlank(orderBy)) {
			orderBy = "pay_time DESC";
		}
		String beginOrderTime = MapUtils.getString(parameters, "beginOrderTime");
		String endOrderTime = MapUtils.getString(parameters, "endOrderTime");
		if (StringUtils.isNotBlank(beginOrderTime)) {
			parameters.put("beginOrderTime", DateUtils.parseDate(beginOrderTime).getTime());
		}
		if (StringUtils.isNotBlank(endOrderTime)) {
			parameters.put("endOrderTime", DateUtils.parseDate(endOrderTime).getTime());
		}
		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<UserDepositLog> list = userDepositLogService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息   
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), orderBy);
			List<UserDepositLog> list = userDepositLogService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			// 列表展示数据
			parameters.put("exhibitDatas", list);
			return parameters;
		}
	}
	
	@RequestMapping("editUI.html")
	public Object editUI(Model model, HttpServletRequest request, String id) {
		try {
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("id", id);
			parameters.put("type", "0");
			List<UserDepositLog> list = userDepositLogService.queryListByPage(parameters);
			if(list != null && !list.isEmpty()) {
				UserDepositLog depositLog = list.get(0);
				if (StringUtils.isNoneBlank(depositLog.getOrderTime())) {
					depositLog.setOrderTime(DateUtils.longToString(StringUtils.toLong(depositLog.getOrderTime())));
				}
				model.addAttribute("entity", depositLog);
			}
			return Common.BACKGROUND_PATH + "/business/user/withdraw/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}
	
	@RequestMapping(value = "editForWithdraw.html", method = RequestMethod.POST)
	@ResponseBody
	public Object editForWithdraw(UserDepositLog entity) throws AjaxException {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			int result = userDepositLogService.updateForWithdraw(entity);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "编辑成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "编辑失败");
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "编辑失败:" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 充值记录 Service定义
	 */
	@Autowired
	private IUserDepositLogService userDepositLogService;
}
