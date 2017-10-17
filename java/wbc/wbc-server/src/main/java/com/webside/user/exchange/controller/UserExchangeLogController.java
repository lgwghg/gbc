/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.user.exchange.entities.UserExchangeLog;
import com.webside.user.exchange.service.IUserExchangeLogService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.DateUtils;
import com.webside.util.PageUtil;

/**
 * 用户兑换Controller
 * 
 * @author tianguifang
 * @date 2016-11-24 16:19:27
 */

@Controller
@RequestMapping("/userExchangeLog/")
public class UserExchangeLogController extends BaseController {
	@Autowired
	private UserService userService;

	/**
	 * 跳转查询商品兑换页面
	 * @param Model model HttpServletRequest request
	 * @throws Exception
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
			return Common.BACKGROUND_PATH + "/business/user/exchange/list";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * ajax分页动态加载模式
	 * @param dtGridPager Pager对象
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
			if (parameters.get("exchangeTimeStart") != null && StringUtils.isNotBlank(parameters.get("exchangeTimeStart").toString())) {
				parameters.put("exchangeTimeStart", DateUtils.parseDate(parameters.get("exchangeTimeStart")).getTime());
			}
			if (parameters.get("exchangeTimeEnd") != null && StringUtils.isNotBlank(parameters.get("exchangeTimeEnd").toString())) {
				parameters.put("exchangeTimeEnd", DateUtils.parseDate(parameters.get("exchangeTimeEnd")).getTime());
			}
		}
		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<UserExchangeLog> list = userExchangeLogService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "exchange_time DESC");
			List<UserExchangeLog> list = userExchangeLogService.queryListByPage(parameters);
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

	/**
	 * 跳转新增商品兑换页面
	 * @param Model model
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			return Common.BACKGROUND_PATH + "/userExchangeLog/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 跳转编辑商品兑换页面
	 * @param Model model
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		UserExchangeLog entity = null;
		PageUtil page = null;

		try {
			entity = userExchangeLogService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/userExchangeLog/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改商品兑换
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(UserExchangeLog entity) throws AjaxException {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			// 设置创建者姓名
			int result = userExchangeLogService.update(entity);

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
			throw new AjaxException(e);
		}

		return map;
	}

	/**
	 * 根据ID列表删除商品兑换
	 * @param UserExchangeLog
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("deleteBatch.html")
	@ResponseBody
	public Object deleteBatch(String ids) {
		Map<String, Object> result = null;

		try {
			result = new HashMap<String, Object>();

			String[] userIds = ids.split(",");
			List<String> list = new ArrayList<String>();

			for (String string : userIds) {
				list.add(string);
			}

			int cnt = userExchangeLogService.deleteBatchById(list);

			if (cnt == list.size()) {
				result.put("success", true);
				result.put("data", null);
				result.put("message", "删除成功");
			} else {
				result.put("success", false);
				result.put("data", null);
				result.put("message", "删除失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return result;
	}

	/**
	 * 根据ID 查询商品兑换
	 * @param String id
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("infoUI.html")
	public String infoUI(Model model, String id) {
		try {
			UserExchangeLog entity = userExchangeLogService.findById(id);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/user/info";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 商品兑换 Service定义
	 */
	@Autowired
	private IUserExchangeLogService userExchangeLogService;

	/**
	 * 管理员操作点卡出售完成
	 * @param exchangeId
	 * @return
	 */
	@RequestMapping("saledCard")
	@ResponseBody
	public Object saledCard(String exchangeId) {
		Integer result = 0;
		try {
			result = userExchangeLogService.updateSaledCard(exchangeId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result = 0;
		}
		return result;
	}

	/**
	 * 管理员录入快递单号
	 * @return
	 */
	@RequestMapping("editTrackerNo")
	@ResponseBody
	public Object editTrackerNo(String exchangeId, String trackerNo) {
		UserExchangeLog exchange = new UserExchangeLog();
		exchange.setId(exchangeId);
		exchange.setTrackerNo(trackerNo);
		return userExchangeLogService.update(exchange);
	}
}
