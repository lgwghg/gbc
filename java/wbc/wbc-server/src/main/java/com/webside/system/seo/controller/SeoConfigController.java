/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.seo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import com.webside.system.dict.service.DictService;
import com.webside.system.seo.entities.SeoConfig;
import com.webside.system.seo.service.ISeoConfigService;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;

/**
 * SEO配置Controller
 *
 * @author zengxn
 * @date 2016-10-19 17:36:48
 */
 
@Controller
@RequestMapping("/system/sc/")
public class SeoConfigController extends BaseController
{
	/**
	 * 跳转查询SEO配置页面
	 * @param Model model
	 * 		  HttpServletRequest request
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("listUI.html")
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
			//查询选项数据
			model.addAttribute("seoConfigTypeList", dictService.getDictType(GlobalConstant.SEO_CONFIG_TYPE));
			return Common.BACKGROUND_PATH + "/business/system/sc/list";
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
	@RequestMapping(value = "list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception
	{
		Map<String, Object> parameters = null;
		//1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		//2、设置查询参数
		parameters = pager.getParameters();
		
		//3、判断是否是导出操作
		if(pager.getIsExport())
		{
			if(pager.getExportAllData())
			{
				//3.1、导出全部数据
				List<SeoConfig> list = seoConfigService.queryListByPage(parameters);
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
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "type");
			List<SeoConfig> list = seoConfigService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());

			//权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("sc:edit");
			boolean del = subject.isPermitted("sc:del");
			//列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list,edit?"edit":"",del?"del":""));
			return parameters;
		}
	}
	
	/**
	 * 跳转新增SEO配置页面
	 * @param Model model
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) 
	{
		try
		{
			//查询选项数据
			model.addAttribute("seoConfigTypeList", dictService.getDictType(GlobalConstant.SEO_CONFIG_TYPE));
			return Common.BACKGROUND_PATH + "/business/system/sc/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 新增SEO配置 ajax
	 * @param SeoConfig
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(SeoConfig entity)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			if(seoConfigService.findEntityByType(entity.getType(),null)==0){
				int result = seoConfigService.insert(entity);
				
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
			}else{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "该类别已存在!");
			}
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
		
		return map;
	}
	
	/**
	 * 跳转编辑SEO配置页面
	 * @param Model model
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) 
	{
		SeoConfig entity = null;
		PageUtil page = null;
	
		try
		{
			entity = seoConfigService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			
			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			//查询选项数据
			model.addAttribute("seoConfigTypeList", dictService.getDictType(GlobalConstant.SEO_CONFIG_TYPE));
			return Common.BACKGROUND_PATH + "/business/system/sc/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * 修改SEO配置
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(SeoConfig entity) throws AjaxException
	{
		Map<String, Object> map = null;
		
		try
		{
			map = new HashMap<String, Object>();
			if(seoConfigService.findEntityByType(entity.getType(),entity.getId())==0){
				//设置创建者姓名
				int result = seoConfigService.update(entity);
				
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
			}else{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "该类别已存在!");
			}
			
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
		
		return map;
	}
	
	/**
	 * 根据ID删除SEO配置
	 * @param SeoConfig
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("delete.html")
	@ResponseBody
	public Object delete(String id)
	{
		Map<String, Object> result = null;
		
		try
		{
			result = new HashMap<String, Object>();
			
			int cnt = seoConfigService.deleteById(id);
			
			if(cnt == 1)
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
	
	/**
	 * SEO配置 Service定义
	 */
	@Autowired
	private ISeoConfigService seoConfigService;
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;
}
