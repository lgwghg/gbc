package com.webside.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import com.webside.exception.ServiceException;
import com.webside.role.model.RoleEntity;
import com.webside.role.service.RoleService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserIncrement;
import com.webside.user.service.UserIncrementService;
import com.webside.user.service.UserService;
import com.webside.util.DateUtils;
import com.webside.util.EndecryptUtils;
import com.webside.util.IdGen;
import com.webside.util.PageUtil;
import com.webside.util.RandomUtil;

@Controller
@Scope("prototype")
@RequestMapping("/user/")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserIncrementService userIncrementService;
	@Autowired
	private RoleService roleService;

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
			model.addAttribute("page", page);
			return Common.BACKGROUND_PATH + "/user/list";
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
	@RequestMapping(value = "/list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		// parameters.put("creatorName",
		// ShiroAuthenticationManager.getUserNickName());
		if (parameters.size() < 0) {
			parameters.put("nickName", null);
		}
		// 注册时间
		String beginCreateTime = (String) parameters.get("beginCreateTime");
		String endCreateTime = (String) parameters.get("endCreateTime");
		if (StringUtils.isNotBlank(beginCreateTime)) {
			parameters.put("beginCreateTime", DateUtils.parseDate(beginCreateTime).getTime());
		}
		if (StringUtils.isNotBlank(endCreateTime)) {
			parameters.put("endCreateTime", DateUtils.parseDate(endCreateTime).getTime());
		}
		// 更新时间
		String beginUpdateTime = (String) parameters.get("beginUpdateTime");
		String endUpdateTime = (String) parameters.get("endCreateTime");
		if (StringUtils.isNotBlank(beginUpdateTime)) {
			parameters.put("beginUpdateTime", DateUtils.parseDate(beginUpdateTime).getTime());
		}
		if (StringUtils.isNotBlank(endUpdateTime)) {
			parameters.put("endUpdateTime", DateUtils.parseDate(endUpdateTime).getTime());
		}
		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<UserEntity> list = userService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "u_id DESC");
			List<UserEntity> list = userService.queryListByPage(parameters);
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
	 * ajax分页动态加载模式
	 * 
	 * @param dtGridPager
	 *            Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/incrementList.html", method = RequestMethod.POST)
	@ResponseBody
	public Object incrementList(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		// parameters.put("creatorName",
		// ShiroAuthenticationManager.getUserNickName());
		if (parameters.size() < 0) {
			parameters.put("nickName", null);
		}
		// 更新时间
		String beginUpdateTime = (String) parameters.get("beginUpdateTime");
		String endUpdateTime = (String) parameters.get("endCreateTime");
		if (StringUtils.isNotBlank(beginUpdateTime)) {
			parameters.put("beginUpdateTime", DateUtils.parseDate(beginUpdateTime).getTime());
		}
		if (StringUtils.isNotBlank(endUpdateTime)) {
			parameters.put("endUpdateTime", DateUtils.parseDate(endUpdateTime).getTime());
		}
		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<UserIncrement> list = userIncrementService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "update_time DESC");
			List<UserIncrement> list = userIncrementService.queryListByPage(parameters);
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

	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			List<RoleEntity> list = roleService.queryListByPage(new HashMap<String, Object>());
			model.addAttribute("roleList", list);
			return Common.BACKGROUND_PATH + "/user/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}

	}

	@RequestMapping("add.html")
	@ResponseBody
	public Object add(UserEntity userEntity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String password = userEntity.getPassword();
			userEntity.setId(IdGen.uuid());
			// 加密用户输入的密码，得到密码和加密盐，保存到数据库
			UserEntity user = EndecryptUtils.md5Password(userEntity.getId(), userEntity.getPassword(), 2);
			// 设置添加用户的密码和加密盐
			userEntity.setPassword(user.getPassword());
			userEntity.setCredentialsSalt(user.getCredentialsSalt());
			// 设置创建者姓名
			userEntity.setCreatorName(ShiroAuthenticationManager.getUserNickName());
			userEntity.setCreateTime(System.currentTimeMillis());
			// 设置锁定状态：未锁定；删除状态：未删除
			userEntity.setIsDeleted(0);
			userEntity.setLocked(0);
			// 初始化用户增量信息
			UserIncrement userIncrement = new UserIncrement();
			userIncrement.setCreateOperatorId(ShiroAuthenticationManager.getUserId());
			userEntity.setUserIncrement(userIncrement);
			int result = userService.insert(userEntity, password);
			if (result == 1) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败");
			}
		} catch (ServiceException e) {
			throw new AjaxException(e);
		}
		return map;
	}

	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		try {
			UserEntity userEntity = userService.findUserById(id);
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			List<RoleEntity> list = roleService.queryListByPage(new HashMap<String, Object>());
			model.addAttribute("page", page);
			model.addAttribute("userEntity", userEntity);
			model.addAttribute("roleList", list);
			model.addAttribute("userSession", ShiroAuthenticationManager.getUserEntity());
			model.addAttribute("userIncrement", userIncrementService.queryUserIncrementByUserId(id));
			return Common.BACKGROUND_PATH + "/user/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	@RequestMapping("edit.html")
	@ResponseBody
	public Object update(UserEntity userEntity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = userService.update(userEntity);
			if (result == 1) {
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

	@RequestMapping("deleteBatch.html")
	@ResponseBody
	public Object deleteBatch(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] userIds = ids.split(",");
			List<String> list = new ArrayList<String>();
			for (String string : userIds) {
				list.add(string);
			}
			int cnt = userService.deleteBatchById(list);
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

	@RequestMapping("resetPassword")
	@ResponseBody
	public Object resetPassword(UserEntity userEntity) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 生成随机密码
			String password = RandomUtil.generateString(6);

			if (userEntity.getId() == null) {
				UserEntity userDb = userService.findByMobile(userEntity.getMobile());
				if (userDb != null) { // 加密用户输入的密码，得到密码和加密盐，保存到数据库
					UserEntity user = EndecryptUtils.md5Password(userDb.getId(), password, 2);
					// 设置添加用户的密码和加密盐
					userEntity.setPassword(user.getPassword());
					userEntity.setCredentialsSalt(user.getCredentialsSalt());

					userEntity.setId(userDb.getId());
					userEntity.setNickName(userDb.getNickName());
					int cnt = userService.updateOnly(userEntity, password);
					if (cnt > 0) {
						result.put("success", true);
						result.put("data", null);
						result.put("message", "密码重置成功");
					} else {
						result.put("success", false);
						result.put("data", null);
						result.put("message", "密码重置失败");
					}
				} else {
					result.put("success", false);
					result.put("data", null);
					result.put("message", "账户不存在");
				}
			} else {
				// 加密用户输入的密码，得到密码和加密盐，保存到数据库
				UserEntity user = EndecryptUtils.md5Password(userEntity.getId(), password, 2);
				// 设置添加用户的密码和加密盐
				userEntity.setPassword(user.getPassword());
				userEntity.setCredentialsSalt(user.getCredentialsSalt());

				int cnt = userService.updateOnly(userEntity, password);
				if (cnt > 0) {
					result.put("success", true);
					result.put("data", null);
					result.put("message", "密码重置成功");
				} else {
					result.put("success", false);
					result.put("data", null);
					result.put("message", "密码重置失败");
				}
			}

		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return result;
	}

	@RequestMapping("withoutAuth/resetPassWithoutAuthc")
	@ResponseBody
	public Object resetPassWithoutAuthc(UserEntity userEntity) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			UserEntity userDb = userService.findByMobile(userEntity.getMobile());
			if (userDb != null) {
				// 生成随机密码
				String password = RandomUtil.generateString(6);
				// 加密用户输入的密码，得到密码和加密盐，保存到数据库
				UserEntity user = EndecryptUtils.md5Password(userDb.getId(), password, 2);
				// 设置添加用户的密码和加密盐
				userEntity.setPassword(user.getPassword());
				userEntity.setCredentialsSalt(user.getCredentialsSalt());

				userEntity.setId(userDb.getId());
				userEntity.setNickName(userDb.getNickName());
				int cnt = userService.updateOnly(userEntity, password);
				if (cnt > 0) {
					result.put("success", true);
					result.put("data", null);
					result.put("message", "密码重置成功");
				} else {
					result.put("success", false);
					result.put("data", null);
					result.put("message", "密码重置失败");
				}
			} else {
				result.put("success", false);
				result.put("data", null);
				result.put("message", "账户不存在");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return result;
	}

	@RequestMapping("infoUI.html")
	public String infoUI(Model model, String id) {
		try {
			UserEntity userEntity = userService.findById(id);
			model.addAttribute("userEntity", userEntity);
			return Common.BACKGROUND_PATH + "/user/info";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	@RequestMapping("info.html")
	@ResponseBody
	public Object info(String id) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			UserEntity userEntity = userService.findById(id);
			map.put("success", Boolean.TRUE);
			map.put("data", userEntity);
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("data", null);
			map.put("message", "查询失败：" + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("passwordUI.html")
	public String passwordUI(Model model, UserEntity userEntity) {
		try {
			model.addAttribute("userEntity", userEntity);
			return Common.BACKGROUND_PATH + "/user/password";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	@RequestMapping("password.html")
	@ResponseBody
	public Object password(UserEntity userEntity) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String password = userEntity.getPassword();
			// userEntity.setUserName(new
			// String(userEntity.getUserName().getBytes("iso-8859-1"),"utf-8"));
			// 加密用户输入的密码，得到密码和加密盐，保存到数据库
			UserEntity user = EndecryptUtils.md5Password(userEntity.getId(), userEntity.getPassword(), 2);
			// 设置添加用户的密码和加密盐
			userEntity.setPassword(user.getPassword());
			userEntity.setCredentialsSalt(user.getCredentialsSalt());
			int cnt = userService.updateOnly(userEntity, password);
			if (cnt > 0) {
				result.put("success", true);
				result.put("data", null);
				result.put("message", "密码修改成功");
			} else {
				result.put("success", false);
				result.put("data", null);
				result.put("message", "密码修改失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return result;
	}

	@RequestMapping("avatarUI.html")
	public String avatarUI(Model model, UserEntity userEntity) {
		try {
			model.addAttribute("userEntity", userEntity);
			return Common.BACKGROUND_PATH + "/user/avatar";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	@RequestMapping("withoutAuth/validateEmail")
	@ResponseBody
	public Object validateEmail(String email) {
		try {
			if (StringUtils.isBlank(email)) {
				return false;
			}
			UserEntity userEntity = userService.findByEmail(email);
			if (userEntity == null) {
				return true;
			} else {
				String loginUserEmail = ShiroAuthenticationManager.getUserEmail();
				if (loginUserEmail.equals(userEntity.getEmail())) {
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	@RequestMapping("withoutAuth/validateUserMobile")
	@ResponseBody
	public Object validateUserMobile(String mobile) {
		try {
			if (StringUtils.isBlank(mobile)) {
				return false;
			}
			UserEntity userEntity = userService.findByMobile(mobile);
			if (userEntity == null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	@RequestMapping("withoutAuth/validateUserNick")
	@ResponseBody
	public Object validateUserNick(String nickName) {
		try {
			if (StringUtils.isBlank(nickName)) {
				return false;
			}
			UserEntity userEntity = userService.findByNickName(nickName);
			if (userEntity == null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 验证邮箱用户是否存在
	 * 
	 * @param mobile
	 * @return
	 */
	@RequestMapping("withoutAuth/validateUserEmail")
	@ResponseBody
	public Object validateUserEmail(String email) {
		try {
			if (StringUtils.isBlank(email)) {
				return false;
			}
			UserEntity userEntity = userService.findByEmail(email);
			if (userEntity == null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 验证用户是否已经绑定某账号
	 */
	@RequestMapping("withoutAuth/validateUserBindThird")
	@ResponseBody
	public Object validateUserBindThird(String mobile, String email, String thirdType) {
		try {
			if ((StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) || StringUtils.isBlank(thirdType)) {
				return "账号不能为空";
			}
			UserEntity userEntity = null;
			if (StringUtils.isNotBlank(mobile)) {
				userEntity = userService.findByMobile(mobile);
			} else if (StringUtils.isNotBlank(email)) {
				userEntity = userService.findByEmail(email);
			}
			if (userEntity == null) {
				return "账号不存在";
			} else {
				if (GlobalConstant.THIRD_LOGIN_TYPE_STEAM.equals(thirdType) && StringUtils.isNotBlank(userEntity.getSteamKey())) {
					return "该账号已绑定其他Steam账号";
				} else if (GlobalConstant.THIRD_LOGIN_TYPE_QQ.equals(thirdType) && StringUtils.isNotBlank(userEntity.getQqKey())) {
					return "该账号已绑定其他QQ账号";
				} else if (GlobalConstant.THIRD_LOGIN_TYPE_WEIBO.equals(thirdType) && StringUtils.isNotBlank(userEntity.getWeiboKey())) {
					return "该账号已绑定其他微博Q账号";
				} else if (GlobalConstant.THIRD_LOGIN_TYPE_WECHAT.equals(thirdType) && StringUtils.isNotBlank(userEntity.getWechatKey())) {
					return "该账号已绑定其他微信账号";
				}
				return "0";
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}
}
