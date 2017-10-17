package com.webside.user.web;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.exception.AjaxException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.service.UserService;
import com.webside.util.CookieUtil;
import com.webside.util.EndecryptUtils;

@Controller
@Scope("prototype")
@RequestMapping("/my/")
public class UserCtrl extends BaseController{

	@Autowired
	private UserService userService;
	@Autowired
	private IUserCacheService userCacheService;

	/**
	 * 首页用户基本信息，ajax 返回 json数据
	 * 
	 * @param nickName
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "infoJson")
	@ResponseBody
	public Object userInfo(String search, HttpServletRequest request) {
		String userId = ShiroAuthenticationManager.getUserId();
		JSONObject json = new JSONObject();
		// 获取分享关系的数据
		if (StringUtils.isNotBlank(userId)) {
			UserEntity user = userCacheService.getUserEntityByUserId(userId);
			json.put("user", user);
		}
		return json;
	}

	@RequestMapping("info")
	public String infoUI(Model model) {
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			return "redirect:/index";
		}
		UserEntity user = userService.findById(userId);
		model.addAttribute("userEntity", user);
		return Common.BACKGROUND_PATH_WEB + "/my/account/account";
	}

	/**
	 * 编辑个人资料
	 * 
	 * @param nickName
	 * @return
	 */
	@RequestMapping("edit")
	@ResponseBody
	public Object editUser(UserEntity user, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (user == null) {
				return false;
			}
			String userId = ShiroAuthenticationManager.getUserId();
			if (StringUtils.isBlank(userId)) {
				return false;
			}
			user.setId(userId);
			if (StringUtils.isBlank(user.getNickName())) {
				return false;
			}
			if (!user.getNickName().equals(ShiroAuthenticationManager.getUserNickName())) {
				UserEntity userParam = new UserEntity();
				userParam.setNickName(user.getNickName());
				if (userService.findUserEntity(userParam) != null) {
					return false;
				}
			}
			if (StringUtils.isNotBlank(user.getSign())) {
				// 验证签名的长度
				if (user.getSign().length() > 140) {
					return false;
				}
			}
			if (StringUtils.isNotBlank(user.getEmail())) {
				// 验证邮箱是否已经存在
				UserEntity userParam = new UserEntity();
				userParam.setEmail(user.getEmail());
				if (userService.findUserEntity(userParam) != null) {
					return false;
				}
			}
			int count = 0;
			if (StringUtils.isNotBlank(user.getPassword())) {
				// 生成加密密码
				String password = user.getPassword();
				// 加密用户输入的密码，得到密码和加密盐，保存到数据库
				UserEntity userEntity = EndecryptUtils.md5Password(user.getId(), user.getPassword(), 2);
				// 设置添加用户的密码和加密盐
				user.setPassword(userEntity.getPassword());
				user.setCredentialsSalt(userEntity.getCredentialsSalt());
				count = userService.updateOnly(user, password);
			} else {
				count = userService.updateUserOnly(user);
			}
			if (count == 1) {
				CookieUtil.addCookie(response, GlobalConstant.COOKIE_NICK_NAME, URLEncoder.encode(user.getNickName(), "utf-8"), 60 * 60 * 24 * 7); // 存储昵称
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 编辑用户个人资料，编辑手机或邮箱
	 * 
	 * @param editType
	 * @param editValue
	 * @param request
	 * @return
	 */
	@RequestMapping("editMobileOrEmail")
	@ResponseBody
	public Object editMobileOrEmail(Integer editType, String editValue, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (editType == null || StringUtils.isBlank(editValue)) {
				return 0;
			}
			UserEntity userEntity = new UserEntity();
			userEntity.setId(ShiroAuthenticationManager.getUserId());
			if (editType == 0) {
				userEntity.setMobile(editValue);
			} else if (editType == 1) {
				userEntity.setEmail(editValue);
			} else {
				return 0;
			}
			int count = userService.updateUserOnly(userEntity);
			if (count == 1) {
				if (editType == 0) {
					CookieUtil.addCookie(response, GlobalConstant.COOKIE_LOGIN_NAME, URLEncoder.encode(userEntity.getMobile(), "utf-8"), 60 * 60 * 24 * 7); // 存储账号
				}
				return 1;
			} else {
				return 2;
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 验证输入的密码是否正确
	 * 
	 * @param editType
	 * @param editValue
	 * @param request
	 * @return
	 */
	@RequestMapping("checkPwd")
	@ResponseBody
	public Object checkPwd(String pwd, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			if (user == null) {
				resultMap.put("state", 1);
				resultMap.put("msg", "未登录");
				return resultMap;
			}
			if (StringUtils.isBlank(pwd)) {
				resultMap.put("state", 2);
				resultMap.put("msg", "请输入密码");
				return resultMap;
			}
			boolean result = EndecryptUtils.checkPassword(user, pwd);
			if (result) {
				resultMap.put("state", 0);
				resultMap.put("msg", "密码正确");
				return resultMap;
			} else {
				resultMap.put("state", 3);
				resultMap.put("msg", "密码不正确");
				return resultMap;
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}
	/**
	 * 验证输入的密码是否正确
	 * 
	 * @param editType
	 * @param editValue
	 * @param request
	 * @return
	 */
	@RequestMapping("checkPayPwd")
	@ResponseBody
	public Object checkPayPwd(String pwd, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String id = ShiroAuthenticationManager.getUserId();
			if (StringUtils.isBlank(id)) {
				resultMap.put("state", 1);
				resultMap.put("msg", "未登录");
				return resultMap;
			}
			UserEntity user = userService.findById(id);
			if (StringUtils.isBlank(pwd)) {
				resultMap.put("state", 2);
				resultMap.put("msg", "请输入支付密码");
				return resultMap;
			}
			boolean result = EndecryptUtils.checkPayPassword(user, pwd);
			if (result) {
				resultMap.put("state", 0);
				resultMap.put("msg", "支付密码正确");
				return resultMap;
			} else {
				resultMap.put("state", 3);
				resultMap.put("msg", "支付密码不正确");
				return resultMap;
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 编辑支付宝账号
	 * 
	 * @param model
	 * @param alipayAccount
	 * @return
	 */
	@RequestMapping("editAlipayAccount")
	@ResponseBody
	public Object editAlipayAccount(Model model, String alipayAccount) {
		UserEntity user = ShiroAuthenticationManager.getUserEntity();
		if (user != null) {
			UserEntity userEntity = new UserEntity();
			userEntity.setId(user.getId());
			userEntity.setAlipayAccount(alipayAccount);
			userEntity.setUpdateTime(System.currentTimeMillis());
			userEntity.setUpdatorName(user.getNickName());
			int result = userService.updateUserAlipayAccount(userEntity);
			if (result > 0) {
				return true;
			}

		}
		return false;
	}
	
	/**
	 * 编辑支付密码
	 * 
	 * @param model
	 * @param alipayAccount
	 * @return
	 */
	@RequestMapping("editPayPassword")
	@ResponseBody
	public Object editPayPassword(Model model, String payPassword) {
		UserEntity user = ShiroAuthenticationManager.getUserEntity();
		if (user != null) {
			UserEntity userEntity = new UserEntity();
			userEntity.setId(user.getId());
			// 加密用户输入的密码，得到密码和加密盐，保存到数据库
			UserEntity userPay = EndecryptUtils.md5PayPassword(user.getId(), payPassword, 2);
			// 设置添加用户的密码和加密盐
			userEntity.setPayPassword(userPay.getPayPassword());
			userEntity.setPayPasswordSalt(userPay.getPayPasswordSalt());
			userEntity.setUpdateTime(System.currentTimeMillis());
			userEntity.setUpdatorName(user.getNickName());
			int result = userService.updateUserAlipayAccount(userEntity);
			if (result > 0) {
				return true;
			}
		}
		return false;
	}
}
