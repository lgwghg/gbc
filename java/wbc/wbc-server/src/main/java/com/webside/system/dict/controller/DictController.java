package com.webside.system.dict.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.CacheConstant;
import com.webside.common.Common;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.exception.ServiceException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.shiro.cache.redis.VCache;
import com.webside.system.dict.model.Dict;
import com.webside.system.dict.service.DictService;
import com.webside.user.model.UserEntity;
import com.webside.util.IdGen;
import com.webside.util.PageUtil;

@Controller
@RequestMapping("/system/dict/")
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;
	
	@RequestMapping("listUI.html")
	public String listUI(Model model, HttpServletRequest request) {
		try
		{
			PageUtil page = new PageUtil();
			if(request.getParameterMap().containsKey("page")){
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}
			model.addAttribute("page", page);
			return Common.BACKGROUND_PATH + "/business/system/dict/list";
		}catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/**
	 * ajax分页动态加载模式
	 * @param dtGridPager Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.html")
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception{
		Map<String, Object> parameters = null;
		//1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		//2、设置查询参数
		parameters = pager.getParameters();
		parameters.put("creatorName", ShiroAuthenticationManager.getUserNickName());
		if (parameters.size() < 0) {
			parameters.put("userName", null);
		}
		//3、判断是否是导出操作
		if(pager.getIsExport())
		{
			if(pager.getExportAllData())
			{
				//3.1、导出全部数据
				List<Dict> list = dictService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			}else
			{
				//3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		}else
		{
			//设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "type,sort");
			List<Dict> list = dictService.queryListByPage(parameters);
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
	
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		return Common.BACKGROUND_PATH + "/business/system/dict/form";
	}
	
	@RequestMapping(value = "add.html")
	@ResponseBody
	public Map<String, Object> add(Dict dict){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			dict.setId(IdGen.uuid());
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			dict.setCreateBy(user);
			dict.setCreateDate(System.currentTimeMillis()+"");
			int result = dictService.insert(dict);
			if(result == 1)
			{
				String key = CacheConstant.CACHE_CODE_INFO;
				//删除缓存
				VCache.delByKey(key);
				jsonMap.put("success", Boolean.TRUE);
				jsonMap.put("data", null);
				jsonMap.put("message", "添加成功");
			}else
			{
				jsonMap.put("success", Boolean.FALSE);
				jsonMap.put("data", null);
				jsonMap.put("message", "添加失败");
			}
		}catch(ServiceException e){
		}
		return jsonMap;
	}
	
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		try
		{
			Dict dictEntity = dictService.findById(id);
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			
//			List<Dict> list = dictService.getDict_Type(GlobalConstant.DICTTYPE_IS_DELETE);
//			model.addAttribute("dictType", list);
			
			model.addAttribute("page", page);
			model.addAttribute("dictEntity", dictEntity);
		}catch(Exception e){
		}
		return Common.BACKGROUND_PATH + "/business/system/dict/form";
	}
	
	@RequestMapping("edit.html")
	@ResponseBody
	public Object update(Dict dict) throws AjaxException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			dict.setUpdateBy(user);
			dict.setUpdateDate(System.currentTimeMillis()+"");
			int result = dictService.update(dict);
			if(result == 1){
				String key = CacheConstant.CACHE_CODE_INFO;
				//删除缓存
				VCache.delByKey(key);
				
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "编辑成功");
			}else{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "编辑失败");
			}
		}catch(Exception e){
			throw new AjaxException(e);
		}
		return map;
	}
	
	@RequestMapping("getDictType/{type}")
	@ResponseBody
	public Map<String, Object> getDict_type(@PathVariable("type") String type){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			List<Dict> list = dictService.getDictType(type);
			jsonMap.put("dictType", list);
		}catch(Exception e){
			throw new AjaxException(e);
		}
		return jsonMap;
	}
}
