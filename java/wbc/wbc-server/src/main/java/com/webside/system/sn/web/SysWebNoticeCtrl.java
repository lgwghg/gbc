package com.webside.system.sn.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.code.kaptcha.Constants;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.sn.entities.SysWebNotice;
import com.webside.system.sn.service.ISysWebNoticeService;
import com.webside.util.DateUtils;
import com.webside.util.StringUtils;

/**
 * 网站公告
 * 
 * @author zhangfei
 */
@Controller
@RequestMapping("/help")
public class SysWebNoticeCtrl extends BaseController {

	@Autowired
	private ISysWebNoticeService sysWebNoticeService;

	/**
	 * 使用帮助
	 * 
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping("")
	public String webNoticeUI() {
		return Common.BACKGROUND_PATH_WEB + "/system/webNotice";
	}

	@RequestMapping("{id}")
	public String webNoticeUI(@PathVariable String id, Model model) {
		SysWebNotice webNotice = sysWebNoticeService.findById(id);
		if (webNotice == null || webNotice.getId() == null) {
			return Common.BACKGROUND_PATH + "/error/error-404";
		}
		webNotice.setAddTimeStr(DateUtils.formatDate(DateUtils.parseDate(webNotice.getAddTime()), DateUtils._DEFAULT2));
		model.addAttribute("webNotice", webNotice);

		return Common.BACKGROUND_PATH_WEB + "/system/webNotice";
	}

	/**
	 * 公告列表
	 * 
	 * @param gridPager
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			// 1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			parameters = pager.getParameters();
			parameters.put("noStatus", "0");

			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "");

			List<SysWebNotice> list = sysWebNoticeService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			parameters.put("list", list);
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			parameters.put("message", e.getMessage());
			logger.error("ajax获取通知列表数据出错：", e);
		}
		return parameters;
	}

	/**
	 * 点击菜单，查询内容
	 * 
	 * @param id
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "findById", method = RequestMethod.POST)
	@ResponseBody
	public Object findById(String id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			SysWebNotice webNotice = sysWebNoticeService.findById(id);
			if (webNotice != null) {
				parameters.put("isSuccess", Boolean.TRUE);
				parameters.put("webNotice", webNotice);
			} else {
				parameters.put("isSuccess", Boolean.FALSE);
			}
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			parameters.put("message", e.getMessage());
			logger.error("ajax获取通知列表数据出错：", e);
		}
		return parameters;
	}

	/**
	 * 新增反馈意见
	 * 
	 * @param entity
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(SysWebNotice entity, String captcha) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", Boolean.FALSE);
		try {
			if (StringUtils.isBlank(captcha)) {
				throw new RuntimeException("请填写验证码");
			}
			String _captcha = (String) ShiroAuthenticationManager.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (!captcha.equals(_captcha)) {
				throw new RuntimeException("验证码错误，请重新填写");
			}
			entity.setType(GlobalConstant.WEB_NOTICE_TYPE_6);
			entity.setStatus(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
			int result = sysWebNoticeService.insert(entity);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("message", "添加成功");
			} else {
				throw new RuntimeException("添加失败");
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", e.getLocalizedMessage());
		}

		return map;
	}

	/**
	 * 注册协议
	 * 
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "findForRegist", method = RequestMethod.POST)
	@ResponseBody
	public Object findByRegist() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			parameters.put("status", "2");
			List<SysWebNotice> list = sysWebNoticeService.queryListByPage(parameters);
			if (list != null && !list.isEmpty()) {
				SysWebNotice webNotice = list.get(0);
				if (webNotice != null) {
					parameters.put("isSuccess", Boolean.TRUE);
					parameters.put("data", webNotice.getContent());
				}
			} else {
				parameters.put("isSuccess", Boolean.FALSE);
			}
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			parameters.put("message", e.getMessage());
			logger.error("ajax获取注册协议数据出错：", e);
		}
		return parameters;
	}

	/**
	 * 注册协议
	 * @param model
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "agreement", method = RequestMethod.GET)
	public Object agreement(Model model) {
		String code = "agreement";
		return findByCode(model, code);
	}
	
	/**
	 * H5页面注册协议
	 * @param model
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "agreementToH5", method = RequestMethod.GET)
	public Object agreementToH5(Model model) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", "agreement");
		List<SysWebNotice> list = sysWebNoticeService.queryListByPage(parameters);
		if (list != null && !list.isEmpty()) {
			SysWebNotice webNotice = list.get(0);
			if (webNotice == null || webNotice.getId() == null) {
				return Common.BACKGROUND_PATH + "/error/error-404";
			}
			webNotice.setAddTimeStr(DateUtils.formatDate(DateUtils.parseDate(webNotice.getAddTime()), DateUtils._DEFAULT2));
			model.addAttribute("webNotice", webNotice);
			
			return Common.BACKGROUND_PATH_WEB + "/H5/agreement/agreement";
		} else {
			return Common.BACKGROUND_PATH + "/error/error-404";
		}
	}
	
	/**
	 * 依据编码查找
	 * @param model
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "findByCode", method = RequestMethod.GET)
	public Object findByCode(Model model, String code) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", code);
		List<SysWebNotice> list = sysWebNoticeService.queryListByPage(parameters);
		if (list != null && !list.isEmpty()) {
			SysWebNotice webNotice = list.get(0);
			if (webNotice == null || webNotice.getId() == null) {
				return Common.BACKGROUND_PATH + "/error/error-404";
			}
			webNotice.setAddTimeStr(DateUtils.formatDate(DateUtils.parseDate(webNotice.getAddTime()), DateUtils._DEFAULT2));
			model.addAttribute("webNotice", webNotice);

			return Common.BACKGROUND_PATH_WEB + "/system/webNotice";
		} else {
			return Common.BACKGROUND_PATH + "/error/error-404";
		}
	}

	/**
	 * 底部公告
	 * 
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "findForFoot", method = RequestMethod.POST)
	@ResponseBody
	public Object findForFoot() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			parameters.put("status", "3");
			PageHelper.startPage(0, 5, "IFNULL(t.sequence, 100)");// 只加载5条
			List<SysWebNotice> list = sysWebNoticeService.queryListByPage(parameters);
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("list", list);
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			parameters.put("message", e.getMessage());
			logger.error("ajax获取注册协议数据出错：", e);
		}
		return parameters;
	}

}
