/*******************************************************************************
 * All rights reserved. 
 * 
 * @author LiGan
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.jc.controller;

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
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.jc.model.Comment;
import com.webside.jc.service.ICommentService;
import com.webside.util.IdGen;
import com.webside.util.PageUtil;

/**
 * 用户评论Controller
 *
 * @author LiGan
 * @date 2016-11-29 14:18:52
 */
 
@Controller
@RequestMapping("/jc/comment/")
public class CommentController extends BaseController
{
	/**
	 * 用户评论 Service定义
	 */
	@Autowired
	private ICommentService commentService;
	
	/**
	 * 跳转查询用户评论页面
	 * @param Model model
	 * 		  HttpServletRequest request
	 * @throws Exception
	 * @author LiGan
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
			return Common.BACKGROUND_PATH + "/comment/list";
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
				List<Comment> list = commentService.queryListByPage(parameters);
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
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "createTime DESC");
			List<Comment> list = commentService.queryListByPage(parameters);
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
	
	/**
	 * 跳转新增用户评论页面
	 * @param Model model
	 * @throws Exception
	 * @author LiGan
	 */
	@RequestMapping("addUI")
	public String addUI(Model model) 
	{
		try
		{
			return Common.BACKGROUND_PATH + "/comment/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 新增用户评论 ajax
	 * @param Comment
	 * @throws Exception
	 * @author LiGan
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(Comment entity)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			entity.setId(IdGen.uuid());//设置ID 生成 UUID 
			int result = commentService.insert(entity);
			
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
	
	/**
	 * 跳转编辑用户评论页面
	 * @param Model model
	 * @throws Exception
	 * @author LiGan
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) 
	{
		Comment entity = null;
		PageUtil page = null;
	
		try
		{
			entity = commentService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			
			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/comment/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 修改用户评论
	 * @param entity
	 * @throws Exception
	 * @author LiGan
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(Comment entity) throws AjaxException
	{
		Map<String, Object> map = null;
		
		try
		{
			map = new HashMap<String, Object>();
			int result = commentService.update(entity);
			
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
}
