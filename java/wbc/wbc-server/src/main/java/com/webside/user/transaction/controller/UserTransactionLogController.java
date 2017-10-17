/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.transaction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import com.webside.common.GlobalConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.system.dict.model.Dict;
import com.webside.system.dict.service.DictService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.PageUtil;

/**
 * 用户交易记录Controller
 *
 * @author tianguifang
 * @date 2016-11-24 15:54:19
 */
 
@Controller
@RequestMapping("/userTransactionLog/")
public class UserTransactionLogController extends BaseController
{
	
	/**
	 * 用户交易记录 Service定义
	 */
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	@Autowired
	private DictService dictService;
	@Autowired
	private UserService userService;
	/*
	 * 跳转查询用户钱包页面
	 * @param Model model
	 * 		  HttpServletRequest request
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("listUI")
	public String listUI(Model model, HttpServletRequest request) 
	{
		PageUtil page = null;
	
		try
		{
			page = new PageUtil();
			
			if(request.getParameterMap().containsKey("page"))
			{
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}
			
			model.addAttribute("page", page);

			// 查询交易类型字典
			List<Dict> tranTypeDict = dictService.getDictType(GlobalConstant.USER_TRANSACTION_TYPE);
			if (CollectionUtils.isNotEmpty(tranTypeDict)) {
				model.addAttribute("tranTypeDict", tranTypeDict);
			}
			return Common.BACKGROUND_PATH + "/business/user/transaction/list";
		}
		catch(Exception e) {
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
	public Object list(String gridPager, HttpServletResponse response) throws Exception
	{
		Map<String, Object> parameters = null;
		//1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		//2、设置查询参数
		parameters = pager.getParameters();
		String userNick = (String) parameters.get("userNick");
		if (userNick != null && StringUtils.isNotBlank(userNick.trim())) {
			UserEntity user = userService.findByNickName(userNick.trim());
			if (user != null) {
				parameters.put("userId", user.getId());
			}
		}
		String beginUtTime = (String) parameters.get("beginUtTime");
		String endUtTime = (String) parameters.get("endUtTime");
		if (StringUtils.isNotBlank(beginUtTime)) {
			parameters.put("beginUtTime", DateUtils.parseDate(beginUtTime).getTime());
		}
		if (StringUtils.isNotBlank(endUtTime)) {
			parameters.put("endUtTime", DateUtils.parseDate(endUtTime).getTime());
		}
		//3、判断是否是导出操作
		if(pager.getIsExport())
		{
			if(pager.getExportAllData())
			{
				//3.1、导出全部数据
				List<UserTransactionLog> list = userTransactionLogService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			}
			else
			{
				//3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		}
		else
		{
			//设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "ut_time DESC");
			List<UserTransactionLog> list = userTransactionLogService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			//列表展示数据
			parameters.put("exhibitDatas", list);
			return parameters;
		}
	}
	
	/*
	 * 跳转新增用户钱包页面
	 * @param Model model
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("addUI")
	public String addUI(Model model) 
	{
		try
		{
			return Common.BACKGROUND_PATH + "/userTransactionLog/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/*
	 * 新增用户钱包 ajax
	 * @param UserTransactionLog
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(UserTransactionLog entity)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			entity.setId(IdGen.uuid());//设置ID 生成 UUID 
			int result = userTransactionLogService.insert(entity);
			
			if(result > 0)
			{
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
			}
			else
			{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败");
			}
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
		
		return map;
	}
	
	
}
