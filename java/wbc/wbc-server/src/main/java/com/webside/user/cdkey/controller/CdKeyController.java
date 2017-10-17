/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.cdkey.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.webside.dtgrid.model.Column;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.goods.model.Goods;
import com.webside.goods.service.IGoodsService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.cdkey.entities.CdKey;
import com.webside.user.cdkey.service.ICdKeyService;
import com.webside.util.DateUtils;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;

/**
 * cd-key兑换Controller
 *
 * @author tianguifang
 * @date 2017-04-07 11:47:48
 */
 
@Controller
@RequestMapping("/cdkey/")
public class CdKeyController extends BaseController
{
	@Autowired
	private IGoodsService goodsService;
	/*
	 * 跳转查询cd-key兑换页面
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
			return Common.BACKGROUND_PATH + "/business/user/cdkey/list";
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
		if (parameters != null) {
			if (StringUtils.isNotBlank((String)parameters.get("beginCreateTime"))) {
				parameters.put("beginCreateTime", DateUtils.getStringDate((String)parameters.get("beginCreateTime")).getTime());
			}
			if (StringUtils.isNotBlank((String)parameters.get("endCreateTime"))) {
				parameters.put("endCreateTime", DateUtils.getStringDate((String)parameters.get("endCreateTime")).getTime());
			}
			if (StringUtils.isNotBlank((String)parameters.get("beginStartTime"))) {
				parameters.put("beginStartTime", DateUtils.getStringDate((String)parameters.get("beginStartTime")).getTime());
			}
			if (StringUtils.isNotBlank((String)parameters.get("endStartTime"))) {
				parameters.put("endStartTime", DateUtils.getStringDate((String)parameters.get("endStartTime")).getTime());
			}
			if (StringUtils.isNotBlank((String)parameters.get("beginEndTime"))) {
				parameters.put("beginEndTime", DateUtils.getStringDate((String)parameters.get("beginEndTime")).getTime());
			}
			if (StringUtils.isNotBlank((String)parameters.get("endEndTime"))) {
				parameters.put("endEndTime", DateUtils.getStringDate((String)parameters.get("endEndTime")).getTime());
			}
			if (StringUtils.isNotBlank((String)parameters.get("beginExchangeTime"))) {
				parameters.put("beginExchangeTime", DateUtils.getStringDate((String)parameters.get("beginExchangeTime")).getTime());
			}
			if (StringUtils.isNotBlank((String)parameters.get("endExchangeTime"))) {
				parameters.put("endExchangeTime", DateUtils.getStringDate((String)parameters.get("endExchangeTime")).getTime());
			}
			
		}
		//3、判断是否是导出操作
		if(pager.getIsExport())
		{
			if(pager.getExportAllData())
			{
				//3.1、导出全部数据
				List<CdKey> list = cdKeyService.queryListByPage(parameters);
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
			Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "create_time DESC");
			List<CdKey> list = cdKeyService.queryListByPage(parameters);
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
	 * 跳转新增cd-key兑换页面
	 * @param Model model
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("addUI")
	public String addUI(Model model) 
	{
		try
		{	
			Map<String, Object> paramMap= new HashMap<>();
			paramMap.put("isMax", 1);
			paramMap.put("nowPage", 1);
			paramMap.put("pageSize", 1000);
			List<Goods> goodsList = goodsService.queryListByPage(paramMap);
			model.addAttribute("goodsList", goodsList);
			return Common.BACKGROUND_PATH + "/business/user/cdkey/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/*
	 * 新增cd-key兑换 ajax
	 * @param CdKey
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(CdKey entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (entity.getType() != null && entity.getType() != 0) {
				entity.setGoodsId("");
			}
			entity.setCreateUserId(ShiroAuthenticationManager.getUserId());
			if (StringUtils.isBlank(entity.getStartTime())) {
				entity.setStartTime(System.currentTimeMillis() + "");
			} else {
				entity.setStartTime(DateUtils.getStringDate(entity.getStartTime()).getTime() + "");
			}
			if (StringUtils.isNotBlank(entity.getEndTime())) {
				entity.setEndTime(DateUtils.getStringDate(entity.getEndTime()).getTime() + "");
			}
			List<String> idList = cdKeyService.insertCdkey(entity);

			if (idList.size() > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
				map.put("idList", idList);
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}
	/**
	 * 导出
	 * 序列号，CDK，有效期，类型，信息
		1，GBKKKKKKKK，2017-04-31，G币，10000G币
		2，GBKKKKKKKK，2017-04-31，金币，10000金币
		3，GBKKKKKKKK，2017-04-31，商品，DOTA2至宝
	 */
	@RequestMapping(value = "export")
	@ResponseBody
	public void exportExcel(HttpServletResponse response, String idListStr) {
		Pager pager = new Pager();
		try {
			if (StringUtils.isBlank(idListStr)) {
				return;
			}
			String[] idArray = idListStr.split(",");
			List<String> idList = Arrays.asList(idArray);
			List<Map<String, Object>> exportDatas = cdKeyService.queryCdkeyListByIds(idList);
			pager.setExportDatas(exportDatas);
			pager.setExportType("excel");
			pager.setIsExport(true);
			List<Column> exportColumns = buildCdkayExcelColumn();
			
			pager.setExportColumns(exportColumns);
			pager.setExportFileName("CD-KEY兑换码");
			pager.setExportAllData(false);
			pager.setExportDataIsProcessed(true);
			ExportUtils.export(response, pager);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private List<Column> buildCdkayExcelColumn() {
		List<Column> list = new ArrayList<>();
		Column cdkCol = new Column();
		cdkCol.setId("CDK");
		cdkCol.setTitle("CDK");
		cdkCol.setType("string");
		list.add(cdkCol);
		
		Column startTimeCol = new Column();
		startTimeCol.setId("startTime");
		startTimeCol.setTitle("开始时间");
		startTimeCol.setType("string");
		list.add(startTimeCol);
		
		Column endTimeCol = new Column();
		endTimeCol.setId("endTime");
		endTimeCol.setTitle("结束时间");
		endTimeCol.setType("string");
		list.add(endTimeCol);
		
		Column typeCol = new Column();
		typeCol.setId("type");
		typeCol.setTitle("类型");
		typeCol.setType("string");
		list.add(typeCol);
		
		Column remarkCol = new Column();
		remarkCol.setId("remark");
		remarkCol.setTitle("信息");
		remarkCol.setType("string");
		list.add(remarkCol);
		
		return list;
	}
	/*
	 * 跳转编辑cd-key兑换页面
	 * @param Model model
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping("editUI")
	public String editUI(Model model, HttpServletRequest request, String id) 
	{
		CdKey entity = null;
		PageUtil page = null;
	
		try
		{
			Map<String, Object> paramMap= new HashMap<>();
			paramMap.put("isMax", 1);
			paramMap.put("nowPage", 1);
			paramMap.put("pageSize", 1000);
			List<Goods> goodsList = goodsService.queryListByPage(paramMap);
			model.addAttribute("goodsList", goodsList);
			
			entity = cdKeyService.findById(id);
			if(StringUtils.isNotBlank(entity.getEndTime())){
				entity.setEndTime(DateUtils.longToString(StringUtils.toLong(entity.getEndTime())));
			}
			if(StringUtils.isNotBlank(entity.getStartTime())){
				entity.setStartTime(DateUtils.longToString(StringUtils.toLong(entity.getStartTime())));
			}
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			
			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/business/user/cdkey/form";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	/*
	 * 修改cd-key兑换
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Object update(CdKey entity) throws AjaxException
	{
		Map<String, Object> map = null;
		
		try
		{
			map = new HashMap<String, Object>();
			entity.setStartTime(StringUtils.toString(DateUtils.parseDate(entity.getStartTime()).getTime()));
			entity.setEndTime(StringUtils.toString(DateUtils.parseDate(entity.getEndTime()).getTime()));
			//设置创建者姓名
			int result = cdKeyService.update(entity);
			
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
	 * 根据ID列表删除cd-key兑换
	 * @param CdKey
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
			
			int cnt = cdKeyService.deleteBatchById(list);
			
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
	 * 根据ID 查询cd-key兑换
	 * @param String id
	 * @throws Exception
	 * @author tianguifang
	 */
	/*@RequestMapping("infoUI.html")
	public String infoUI(Model model, String id) 
	{
		try
		{
			CdKey entity = cdKeyService.findById(id);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/user/info";
		}
		catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}*/
	
	/**
	 * cd-key兑换 Service定义
	 */
	@Autowired
	private ICdKeyService cdKeyService;
	
	
}
