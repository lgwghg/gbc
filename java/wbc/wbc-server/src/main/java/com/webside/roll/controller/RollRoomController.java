/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.controller;

import java.util.ArrayList;
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
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.roll.model.RollRoom;
import com.webside.roll.service.IRollRoomService;
import com.webside.util.PageUtil;

/**
 * 房间Controller
 * 
 * @author zhangfei
 * @date 2017-04-18 11:04:32
 */

@Controller
@RequestMapping("/rollRoom/")
public class RollRoomController extends BaseController {
	/**
	 * 跳转查询房间页面
	 * 
	 * @param Model
	 *            model HttpServletRequest request
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("listUI.html")
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
			return Common.BACKGROUND_PATH + "/rollRoom/list";
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
	@RequestMapping(value = "list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();

		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<RollRoom> list = rollRoomService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "id DESC");
			List<RollRoom> list = rollRoomService.queryListByPage(parameters);
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
	 * 跳转新增房间页面
	 * 
	 * @param Model
	 *            model
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			return Common.BACKGROUND_PATH + "/rollRoom/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 新增房间 ajax
	 * 
	 * @param RollRoom
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(RollRoom entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = rollRoomService.insert(entity);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
			} else {
				throw new RuntimeException("数据库执行失败");
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "编辑失败：" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 跳转编辑房间页面
	 * 
	 * @param Model
	 *            model
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		RollRoom entity = null;
		PageUtil page = null;
		try {
			entity = rollRoomService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/rollRoom/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改房间
	 * 
	 * @param entity
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(RollRoom entity) throws AjaxException {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			// 设置创建者姓名
			int result = rollRoomService.update(entity);
			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "编辑成功");
			} else {
				throw new RuntimeException("数据库执行失败");
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "编辑失败：" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = "getRollNum.html", method = RequestMethod.POST)
	@ResponseBody
	public Object getRollNum(String gridPager) throws AjaxException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			// 1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);

			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "");
			parameters = pager.getParameters();
			String roomId = MapUtils.getString(parameters, "roomId");
			List<Integer> list = rollRoomService.getRollNumByRoomId(roomId);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			parameters.put("list", list);
		} catch (Exception e) {
			parameters.put("success", Boolean.FALSE);
			parameters.put("data", 0);
			parameters.put("message", "获取roll点次数失败：" + e.getMessage());
			e.printStackTrace();
		}

		return parameters;
	}

	/**
	 * 根据ID列表删除房间
	 * 
	 * @param RollRoom
	 * @throws Exception
	 * @author zhangfei
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

			int cnt = rollRoomService.deleteBatchById(list);

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
	 * 根据ID 查询房间
	 * 
	 * @param String
	 *            id
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("infoUI.html")
	public String infoUI(Model model, String id) {
		try {
			RollRoom entity = rollRoomService.findById(id);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/user/info";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 房间 Service定义
	 */
	@Autowired
	private IRollRoomService rollRoomService;
}
