/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.task.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.webside.util.IdGen;
import com.webside.util.PageUtil;
import com.webside.user.task.service.ITaskService;
import com.webside.user.task.entities.Task;

/**
 * 任务Controller
 *
 * @author tianguifang
 * @date 2017-04-14 16:27:23
 */
 
@Controller
@RequestMapping("/task/")
public class TaskController extends BaseController
{
	@Autowired
	private DictService dictService;
	/*
	 * 跳转查询任务页面
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
			return Common.BACKGROUND_PATH + "/business/user/task/list";
		}
		catch(Exception e)
		{
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
		//parameters.put("creatorName", ShiroAuthenticationManager.getUserAccountName());
		
		//3、判断是否是导出操作
		if(pager.getIsExport())
		{
			if(pager.getExportAllData())
			{
				//3.1、导出全部数据
				List<Task> list = taskService.queryListByPage(parameters);
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
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "task_type asc");
			List<Task> list = taskService.queryListByPage(parameters);
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
	 * 跳转新增任务页面
	 * @param Model model
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("addUI")
	public String addUI(Model model) 
	{
		try
		{
			List<Dict> dictList = dictService.getDictType(GlobalConstant.USER_TASK_TYPE);
			model.addAttribute("dictList", dictList);
			return Common.BACKGROUND_PATH + "/business/user/task/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/*
	 * 新增任务 ajax
	 * @param Task
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(Task entity)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			entity.setId(IdGen.uuid());//设置ID 生成 UUID 
			int result = taskService.insert(entity);
			
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
	
	/*
	 * 跳转编辑任务页面
	 * @param Model model
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("editUI")
	public String editUI(Model model, HttpServletRequest request, String id) 
	{
		Task entity = null;
		PageUtil page = null;
	
		try
		{
			entity = taskService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			
			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			List<Dict> dictList = dictService.getDictType(GlobalConstant.USER_TASK_TYPE);
			model.addAttribute("dictList", dictList);
			return Common.BACKGROUND_PATH + "/business/user/task/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/*
	 * 修改任务
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Object update(Task entity) throws AjaxException
	{
		Map<String, Object> map = null;
		
		try
		{
			map = new HashMap<String, Object>();
			//设置创建者姓名
			int result = taskService.update(entity);
			
			if(result > 0)
			{
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "编辑成功");
			}
			else
			{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "编辑失败");
			}
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
		
		return map;
	}
	
	/*
	 * 根据ID列表删除任务
	 * @param Task
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public Object deleteBatch(String id)
	{
		Map<String, Object> result = null;
		
		try
		{
			result = new HashMap<String, Object>();
		
			String[] userIds = id.split(",");
			List<String> list = new ArrayList<String>();
			
			for (String string : userIds) 
			{
				list.add(string);
			}
			
			int cnt = taskService.deleteBatchById(list);
			
			if(cnt == list.size())
			{
				result.put("success", true);
				result.put("data", null);
				result.put("message", "删除成功");
			}
			else
			{
				result.put("success", false);
				result.put("data", null);
				result.put("message", "删除失败");
			}
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
		
		return result;
	}
	
	/*
	 * 根据ID 查询任务
	 * @param String id
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("infoUI.html")
	public String infoUI(Model model, String id) 
	{
		try
		{
			Task entity = taskService.findById(id);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/user/info";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 任务 Service定义
	 */
	@Autowired
	private ITaskService taskService;
}
