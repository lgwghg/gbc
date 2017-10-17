/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.controller;

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
import com.webside.roll.model.RollPool;
import com.webside.roll.service.IRollPoolService;
import com.webside.util.IdGen;
import com.webside.util.PageUtil;

/**
 * 奖品池Controller
 * 
 * @author zhangfei
 * @date 2017-04-18 11:05:18
 */

@Controller
@RequestMapping("/rollPool/")
public class RollPoolController extends BaseController {
	/**
	 * 跳转查询奖品池页面
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
			return Common.BACKGROUND_PATH + "/rollPool/list";
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
				List<RollPool> list = rollPoolService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "add_time DESC");
			List<RollPool> list = rollPoolService.queryListByPage(parameters);
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
	 * 跳转新增奖品池页面
	 * 
	 * @param Model
	 *            model
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			return Common.BACKGROUND_PATH + "/rollPool/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 新增奖品池 ajax
	 * 
	 * @param RollPool
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(RollPool entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			entity.setId(IdGen.uuid());// 设置ID 生成 UUID
			int result = rollPoolService.insert(entity);
			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
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
	 * 跳转编辑奖品池页面
	 * 
	 * @param Model
	 *            model
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		RollPool entity = null;
		PageUtil page = null;

		try {
			entity = rollPoolService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/rollPool/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改奖品池
	 * 
	 * @param entity
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(RollPool entity) throws AjaxException {
		Map<String, Object> map = null;

		try {
			map = new HashMap<String, Object>();
			// 设置创建者姓名
			int result = rollPoolService.update(entity);

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
	 * 根据ID列表删除奖品池
	 * 
	 * @param RollPool
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("deleteBatch.html")
	@ResponseBody
	public Object deleteBatch(String ids) {
		Map<String, Object> result = null;
		try {
			result = new HashMap<String, Object>();
			rollPoolService.deleteBatchById(ids);
			result.put("success", true);
			result.put("data", null);
			result.put("message", "删除成功");
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("data", null);
			result.put("message", "删除失败：" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 根据ID 查询奖品池
	 * 
	 * @param String
	 *            id
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("infoUI.html")
	public String infoUI(Model model, String id) {
		try {
			RollPool entity = rollPoolService.findById(id);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/user/info";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 批量保存
	 * 
	 * @return
	 */
	@RequestMapping(value = "addBatch.html", method = RequestMethod.POST)
	@ResponseBody
	public Object addBatch(String roomId, Long gold, int num, String exchangeIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			rollPoolService.insertBatch(roomId, gold, num, exchangeIds);
			map.put("success", Boolean.TRUE);
			map.put("data", null);
			map.put("message", "添加成功");
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "编辑失败：" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 奖品池 Service定义
	 */
	@Autowired
	private IRollPoolService rollPoolService;
}