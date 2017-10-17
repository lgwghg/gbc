package com.webside.user.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.common.config.service.ConfigService;
import com.webside.exception.AjaxException;
import com.webside.shiro.cache.redis.RedisCache;
import com.webside.shiro.cache.redis.VCache;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.CaptchaUtil;
import com.webside.util.DateUtils;
import com.webside.util.EmailUtil;
import com.webside.util.EndecryptUtils;

/**
 * 登录，注册相关
 * 
 * @author suyan
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/fp/")
public class ForgetPasswordController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private Producer captchaProducer;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private ConfigService configService;
	/**
	 * 忘记密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "forgetPassword", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String forgetPassword(Model model) {
		model.addAttribute("loginButtonHide", true);
		return Common.BACKGROUND_PATH_WEB + "/login/forgetPassword";
	}

	/**
	 * 通过邮箱找回密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "findPasswordByEmail", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String findPasswordByEmailUI() {
		return Common.BACKGROUND_PATH_WEB + "/login/findPasswordByEmail";
	}

	/**
	 * 通过邮箱找回密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "findPasswordByEmail", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String findPasswordByEmail(String email, String captcha, HttpServletRequest request) {
		if (StringUtils.isBlank(email)) {
			return "redirect:/fp/findPasswordByEmail";
		}
		if (StringUtils.isBlank(captcha)) {
			return "redirect:/fp/findPasswordByEmail";
		} else {
			String captchaKey = GlobalConstant.CAPTCHA_EMAIL + email;
			String countDownKey = GlobalConstant.CAPTCHA_COUNTDOWN + email;
			// 从redis中取出验证码
			String capText = VCache.get(captchaKey);
			if (StringUtils.isBlank(capText)) {
				return "redirect:/fp/findPasswordByEmail";
			}
			if (!captcha.equals(capText)) {
				return "redirect:/fp/findPasswordByEmail";
			} else {
				VCache.delByKey(captchaKey);
				VCache.delByKey(countDownKey);
			}
		}
		UserEntity user = userService.findByEmail(email);
		if (user == null) {
			return "redirect:/fp/findPasswordByEmail";
		}
		request.setAttribute("userId", user.getId());

		return Common.BACKGROUND_PATH_WEB + "/login/setNewPassword";
	}

	/**
	 * 通过手机号找回密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "findPasswordByMobile", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String findPasswordByMobileUI() {
		return Common.BACKGROUND_PATH_WEB + "/login/findPasswordByMobile";
	}

	/**
	 * 通过手机号找回密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "findPasswordByMobile", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String findPasswordByMobile(String mobile, String captcha, HttpServletRequest request) {
		if (StringUtils.isBlank(mobile)) {
			return "redirect:/fp/findPasswordByMobile";
		}
		if (StringUtils.isBlank(captcha)) {
			return "redirect:/fp/findPasswordByMobile";
		} else {
			String captchaKey = GlobalConstant.CAPTCHA_MOBILE + mobile;
			String countDownKey = GlobalConstant.CAPTCHA_COUNTDOWN + mobile;
			// 从redis中取出验证码
			String capText = VCache.get(captchaKey);
			if (StringUtils.isBlank(capText)) {
				return "redirect:/fp/findPasswordByMobile";
			}
			if (!captcha.equals(capText)) {
				return "redirect:/fp/findPasswordByMobile";
			} else {
				VCache.delByKey(captchaKey);
				VCache.delByKey(countDownKey);
			}
		}
		UserEntity user = userService.findByMobile(mobile);
		if (user == null) {
			return "redirect:/fp/findPasswordByMobile";
		}
		request.setAttribute("userId", user.getId());
		return Common.BACKGROUND_PATH_WEB + "/login/setNewPassword";
	}

	@RequestMapping(value = "password", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public Object password(UserEntity userEntity) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String password = userEntity.getPassword();
			if (StringUtils.isBlank(password)) {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "密码不能为空");
				return result;
			}
			if (StringUtils.isBlank(userEntity.getMobile())) {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "手机号不能为空");
				return result;
			}
			UserEntity userQ = userService.findByMobile(userEntity.getMobile());
			if (userQ == null) {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "该手机号的用户不存在");
				return result;
			}
			// 加密用户输入的密码，得到密码和加密盐，保存到数据库
			UserEntity user = EndecryptUtils.md5Password(userQ.getId(), userEntity.getPassword(), 2);
			// 设置添加用户的密码和加密盐
			userEntity.setId(user.getId());
			userEntity.setPassword(user.getPassword());
			userEntity.setCredentialsSalt(user.getCredentialsSalt());
			int cnt = userService.updateOnly(userEntity, password);
			if (cnt > 0) {
				result.put("state", true);
				result.put("data", null);
				result.put("message", "密码修改成功");
			} else {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "密码修改失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return result;
	}

	/**
	 * 生成验证码，并发邮件
	 */
	@RequestMapping(value = "genEmailCaptcha", method = RequestMethod.POST)
	@ResponseBody
	public Object genEmailCaptcha(String email) {
		try {
			String countDownKey = GlobalConstant.CAPTCHA_COUNTDOWN + email;
			if (VCache.get(countDownKey) != null) {
				return -1; // 未过期，就是未超过1分钟
			}
			VCache.set(countDownKey, DateUtils.getDate("yyyy-MM-dd HH:mm:ss"), RedisCache.ONE_MINITES);// 两分钟倒计时

			// String capText = captchaProducer.createText();
			String capText = CaptchaUtil.getNumCaptcha();
			System.out.println(capText);
			// 将验证码存入shiro 登录用户的session
			String captchaKey = GlobalConstant.CAPTCHA_EMAIL + email;
			VCache.set(captchaKey, capText, RedisCache.FIVE_MINITES);// 邮件验证码缓存5分钟
			// 发送邮件
			String captchaEmailTemplate = configService.findConfigValueByKey(EmailUtil.EMAIL_CAPTCHA_TEMPLATE);
			if (StringUtils.isNotBlank(captchaEmailTemplate)) {
				JSONObject emailTemplate = JSONObject.parseObject(captchaEmailTemplate);
				if (StringUtils.isNotBlank(emailTemplate.getString("title")) && StringUtils.isNotBlank(emailTemplate.getString("content"))) {
					String date = DateUtils.formatDate(new Date(), "YYYY年MM月dd日");
					String fromName = emailTemplate.getString("fromName");
					emailUtil.send126Mail(email, fromName, emailTemplate.getString("title"), String.format(emailTemplate.getString("content"), capText, date));
				}
			}
			// emailUtil.send126Mail(email,EmailDescription.SEND_CAPTCHA.getSubject(),String.format(EmailDescription.SEND_CAPTCHA.getMessage(),capText));
			// Mail mail = new Mail(null, email,
			// EmailDescription.UPDATE_EMAIL.getSubject(),
			// String.format(EmailDescription.FIND_PASSWORD.getMessage(),
			// capText), null, null);
			// queueSender.send("q.mail", JSON.toJSONString(mail));
		} catch (Exception e) {
			logger.error("生成验证码出错", e);
			return 0;
		}
		return 1;
	}

	@RequestMapping(value = "checkEmailCaptcha", method = RequestMethod.POST)
	@ResponseBody
	public Object checkEmailCaptcha(String captcha, String email, Integer isClear) {
		try {
			if (StringUtils.isBlank(captcha)) {
				return 0;
			} else {
				String captchaKey = GlobalConstant.CAPTCHA_EMAIL + email;
				// 从redis中取出验证码
				String capText = VCache.get(captchaKey);
				if (StringUtils.isBlank(capText)) {
					return 0;
				}
				if (!captcha.equals(capText)) {
					return 0;
				} else {
					if (isClear != null && isClear == 1) { // 验证成功，需要清除缓存
						VCache.delByKey(captchaKey);
					}
				}
			}

		} catch (Exception e) {
			logger.error("检查验证码出错", e);
			return 0;
		}
		return 1;
	}

	@RequestMapping(value = "success", method = RequestMethod.GET)
	public String fpSuccess(Model model) {
		model.addAttribute("loginButtonHide", true);
		return Common.BACKGROUND_PATH_WEB + "/login/fpSuccess";
	}
}
