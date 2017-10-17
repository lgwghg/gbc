package com.webside.user.friends.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;

/**
 * 分享好友
 * 
 * @author tianguifang
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/share/f")
public class ShareFriendsCtrl {
	
	private static final Log log = LogFactory.getLog(ShareFriendsCtrl.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 打开分享页面
	 * @param model
	 * @param c
	 * @return
	 */
	@RequestMapping("")
	public String fShareSelect(Model model, String c) {
		model.addAttribute("type", "f");
		try {
			// 验证参数
			if (StringUtils.isBlank(c)) {
				return Common.BACKGROUND_PATH_WEB + "/error/error-404";
			}
			// 验证邀请码是否存在
			UserEntity user = userService.findByCampaignKey(c);
			if (user == null) {
				return Common.BACKGROUND_PATH_WEB + "/error/error-404";
			}
			model.addAttribute("user", user);
			model.addAttribute("c", c);
		} catch (Exception e) {
			log.error("打开好友推荐分享页出错：", e);
		}
		return Common.BACKGROUND_PATH_WEB + "/H5/share/index";
	}
	
	/**
	 * 打开邀请页面
	 * @param model
	 * @param inviteCode
	 * @return
	 */
	@RequestMapping("/show")
	public String shareFriend(Model model, String c) {
		if (StringUtils.isNotBlank(c)) {
			UserEntity user = userService.findByCampaignKey(c);
			if (user != null) {
				model.addAttribute("userPhoto", user.getPhoto_65());
				model.addAttribute("userNick", user.getNickName());
				model.addAttribute("inviteCode", c);// 邀请码
			} else {
				model.addAttribute("userPhoto", GlobalConstant.DEFAULT_PHOTO_URL);
			}
		} else {
			model.addAttribute("userPhoto", GlobalConstant.DEFAULT_PHOTO_URL);
		}

		return Common.BACKGROUND_PATH_WEB + "/H5/recommed/show";
	}

	@RequestMapping("/reg")
	public String shareRegister(Model model, String c) {
		model.addAttribute("inviteCode", c);// 邀请码
		return Common.BACKGROUND_PATH_WEB + "/H5/recommed/shareRegister";
	}

	@RequestMapping("/regSuccess")
	public String shareRegisterSuccess(Model model) {
		return Common.BACKGROUND_PATH_WEB + "/H5/recommed/shareRegisterSuccess";
	}
}
