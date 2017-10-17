package com.webside.user.cdkey.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.common.Common;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.user.cdkey.entities.CdKey;
import com.webside.user.cdkey.service.ICdKeyService;

/**
 * cd-key兑换Controller
 *
 * @author tianguifang
 * @date 2017-04-07 11:47:48
 */
 
@Controller
@RequestMapping("/my/cdkey")
public class CdKeyCtrl {
	@Autowired
	private ICdKeyService cdKeyService;
	/**
	 * 进入cd-key 兑换页面
	 * @return
	 */
	@RequestMapping("")
	public String toCdkeyExchange() {
		return Common.BACKGROUND_PATH_WEB + "/my/cdkey/cdkey";
	}
	/**
	 * 进入cd-key 兑换页面
	 * @return
	 */
	@RequestMapping("/exchange")
	@ResponseBody
	public Object cdkeyExchange(String code, String addressId) {
		
		Map<String, Object> map = cdKeyService.updateExchange(code, addressId);
		return map;
	}
	/**
	 * ajax分页动态加载模式
	 * @param dtGridPager Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception
	{
		Map<String, Object> parameters = null;
		//1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		//2、设置查询参数
		parameters = pager.getParameters();
		//parameters.put("creatorName", ShiroAuthenticationManager.getUserAccountName());
		
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
		parameters.put("list", list);
		return parameters;
	}
}
