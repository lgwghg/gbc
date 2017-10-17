/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sn.controller;

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
import com.webside.system.sn.entities.SysWebNotice;
import com.webside.system.sn.service.ISysWebNoticeService;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.HtmlRegexpUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;

/**
 * 网站公告Controller
 * 
 * @author zhangfei
 * @date 2016-12-22 18:11:32
 */

@Controller
@RequestMapping("/system/webNotice/")
public class SysWebNoticeController extends BaseController {
	/**
	 * 跳转查询网站公告页面
	 * 
	 * @param Model
	 *            model HttpServletRequest request
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("listUI.html")
	public String listUI(Model model, HttpServletRequest request) {
		try {
			PageUtil page = new PageUtil();

			if (request.getParameterMap().containsKey("page")) {
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}

			model.addAttribute("webNoticeStatus", dictService.getDictType(GlobalConstant.WEB_NOTICE_STATUS));
			model.addAttribute("webNoticeType", dictService.getDictType(GlobalConstant.WEB_NOTICE_TYPE));
			model.addAttribute("page", page);
			return Common.BACKGROUND_PATH + "/business/system/sn/webNoticeList";
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
				List<SysWebNotice> list = sysWebNoticeService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "t.type");
			List<SysWebNotice> list = sysWebNoticeService.queryListByPage(parameters);
			if(list != null && !list.isEmpty()) {
				for (SysWebNotice sysWebNotice : list) {
					sysWebNotice.setContent(HtmlRegexpUtil.delHTMLTag(sysWebNotice.getContent()));
				}
			}
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			// 列表展示数据
			parameters.put("exhibitDatas", list);
			// 权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("webSN:edit");
			boolean del = subject.isPermitted("webSN:del");
			// 列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list, edit ? "edit" : "", del ? "del" : ""));
			return parameters;
		}
	}

	/**
	 * 跳转新增网站公告页面
	 * 
	 * @param Model
	 *            model
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			model.addAttribute("webNoticeStatus", dictService.getDictType(GlobalConstant.WEB_NOTICE_STATUS));
			model.addAttribute("webNoticeType", dictService.getDictType(GlobalConstant.WEB_NOTICE_TYPE));
			return Common.BACKGROUND_PATH + "/business/system/sn/webNoticeForm";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 新增网站公告 ajax
	 * 
	 * @param SysWebNotice
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(SysWebNotice entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = sysWebNoticeService.insert(entity);

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
	 * 跳转编辑网站公告页面
	 * 
	 * @param Model
	 *            model
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		try {
			SysWebNotice entity = sysWebNoticeService.findById(id);
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);
			model.addAttribute("entity", entity);
			model.addAttribute("webNoticeStatus", dictService.getDictType(GlobalConstant.WEB_NOTICE_STATUS));
			model.addAttribute("webNoticeType", dictService.getDictType(GlobalConstant.WEB_NOTICE_TYPE));
			return Common.BACKGROUND_PATH + "/business/system/sn/webNoticeForm";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改网站公告
	 * 
	 * @param entity
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(SysWebNotice entity) throws AjaxException {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			// 设置创建者姓名
			int result = sysWebNoticeService.update(entity);

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
	 * 根据ID列表删除网站公告
	 * 
	 * @param SysWebNotice
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("deleteBatch.html")
	@ResponseBody
	public Object deleteBatch(String ids) {
		Map<String, Object> result = null;
		try {
			result = new HashMap<String, Object>();
			
			List<String> list = StringUtils.StringToList(ids, ",");
			int cnt = sysWebNoticeService.deleteBatchById(list);
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
	
	@RequestMapping("delete.html")
	@ResponseBody
	public Object delete(String id) {

		return deleteBatch(id);
	}

	/**
	 * 根据ID 查询网站公告
	 * 
	 * @param String
	 *            id
	 * @throws Exception
	 * @author zhangfei
	 */
	@RequestMapping("infoUI.html")
	public String infoUI(Model model, String id) {
		try {
			SysWebNotice entity = sysWebNoticeService.findById(id);
			model.addAttribute("entity", entity);
			return Common.BACKGROUND_PATH + "/user/info";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 网站公告 Service定义
	 */
	@Autowired
	private ISysWebNoticeService sysWebNoticeService;
	@Autowired
	private DictService dictService;
}
