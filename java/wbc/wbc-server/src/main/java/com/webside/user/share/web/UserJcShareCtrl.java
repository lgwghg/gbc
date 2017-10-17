package com.webside.user.share.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.common.config.service.ConfigService;
import com.webside.jc.model.UserJc;
import com.webside.jc.service.IGameBattleService;
import com.webside.jc.service.IUserJcService;
import com.webside.jc.vo.GameBattleVo;
import com.webside.jc.vo.PankouVo;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.service.UserService;
import com.webside.user.share.entities.UserJcShare;
import com.webside.user.share.service.IUserJcShareService;
import com.webside.user.share.util.RedPurseUtil;
import com.webside.util.CookieUtil;

/**
 * 用户竞猜分享Controller
 * 
 * @author tianguifang
 * @date 2017-01-11 14:34:07
 */
@Controller
@RequestMapping("/share/jc")
public class UserJcShareCtrl {
	private static final Log log = LogFactory.getLog(UserJcShareCtrl.class);
	@Autowired
	private IUserJcService userJcService;
	@Autowired
	private UserService userService;
	@Autowired
	private IUserJcShareService userJcShareService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private IUserCacheService userCacheService;
	@Autowired
	private IGameBattleService gameBattleService;

	@RequestMapping("")
	public String jcShareSelect(Model model, String g, String u) {
		model.addAttribute("type", "jc");
		// 验证参数
		if (StringUtils.isBlank(g)) {
			return Common.BACKGROUND_PATH_WEB + "/error/error-404";
		}
		// 验证竞猜是否存在
		/*UserJc userJc = userJcService.findById(j);
		if (userJc == null || StringUtils.isBlank(userJc.getJcTime())) {
			return Common.BACKGROUND_PATH_WEB + "/error/error-404";
		}*/
		// 验证用户是否存在
		UserEntity shareUser = userService.findByCampaignKey(u);
		if (shareUser == null) {
			return Common.BACKGROUND_PATH_WEB + "/error/error-404";
		}
		model.addAttribute("gbId", g);
		model.addAttribute("userCode", u);
		try {
			Map<String, Object> gbMap = new HashMap<>();
			gbMap.put("gbId", g);
			gbMap.put("currentUser", shareUser.getId());
			// gbMap.put("gbStatus", GlobalConstant.DICTTYPE_IS_DELETE_0);

			List<GameBattleVo> gbList = gameBattleService.queryGbDetailsListByPage(gbMap);
			if (CollectionUtils.isEmpty(gbList)) {
				// 404
				return Common.BACKGROUND_PATH_WEB + "/error/error-404";
			}
			GameBattleVo gbInfo = gbList.get(0);
			List<PankouVo> pankous = gbInfo.getPankous();
			PankouVo pkVo = null;
			for (PankouVo vo : pankous) {
				if (vo.getUserJc() != null) {
					pkVo = vo;
					break;
				}
			}
			model.addAttribute("gbInfo", gbInfo);
			model.addAttribute("pkVo", pkVo);

			UserJc userJc = pkVo.getUserJc();
			userJc.setUser(shareUser);
			model.addAttribute("userJc", userJc);

		} catch (Exception e) {
			log.error("分享页比赛对战详情信息出错：", e);
		}

		return Common.BACKGROUND_PATH_WEB + "/H5/share/index";
	}

	/**
	 * 打开分享页面
	 * TODO 第三方用户信息的获取
	 * @return
	 */
	@RequestMapping("/show")
	public String toSharePg(Model model, String g, String u) {
		// 验证参数
		if (StringUtils.isBlank(g)) {
			return Common.BACKGROUND_PATH_WEB + "/error/error-404";
		}
		// 验证竞猜是否存在
		/*UserJc userJc = userJcService.findById(j);
		if (userJc == null || StringUtils.isBlank(userJc.getJcTime())) {
			return Common.BACKGROUND_PATH_WEB + "/error/error-404";
		}*/
		// 验证用户是否存在
		UserEntity shareUser = userService.findByCampaignKey(u);
		if (shareUser == null) {
			return Common.BACKGROUND_PATH_WEB + "/error/error-404";
		}
		model.addAttribute("gbId", g);
		model.addAttribute("userCode", u);
		try {
			Map<String, Object> gbMap = new HashMap<>();
			gbMap.put("gbId", g);
			gbMap.put("currentUser", shareUser.getId());
			// gbMap.put("gbStatus", GlobalConstant.DICTTYPE_IS_DELETE_0);

			List<GameBattleVo> gbList = gameBattleService.queryGbDetailsListByPage(gbMap);
			if (CollectionUtils.isEmpty(gbList)) {
				// 404
				return Common.BACKGROUND_PATH_WEB + "/error/error-404";
			}
			GameBattleVo gbInfo = gbList.get(0);
			List<PankouVo> pankous = gbInfo.getPankous();
			PankouVo pkVo = null;
			for (PankouVo vo : pankous) {
				if (vo.getUserJc() != null) {
					pkVo = vo;
					break;
				}
			}
			model.addAttribute("gbInfo", gbInfo);
			model.addAttribute("pkVo", pkVo);

			UserJc userJc = pkVo.getUserJc();
			userJc.setUser(shareUser);
			model.addAttribute("userJc", userJc);

		} catch (Exception e) {
			log.error("分享页比赛对战详情信息出错：", e);
		}

		return Common.BACKGROUND_PATH_WEB + "/H5/jcshare/show";
	}

	/**
	 * 验证是否可以领取红包 ajax页面数据展示
	 * 
	 * @return
	 */
	@RequestMapping("/checkCanGet")
	@ResponseBody
	public Object checkCanGetRedPurse(HttpServletRequest request, UserJcShare jcShare) {
		JSONObject json = new JSONObject();
		if (jcShare == null || StringUtils.isBlank(jcShare.getGbId()) || StringUtils.isBlank(jcShare.getUserCode())) {
			return json;
		}
		UserEntity shareUser = userService.findByCampaignKey(jcShare.getUserCode());// 获取分享人
		if (shareUser == null) {
			return json;
		}
		jcShare.setShareUserId(shareUser.getId());
		// 先验证是否有人领过，没人领过的，验证过期情况；已经有人领过的，验证当前用户是否领过红包，领过的展示全部已抢列表，未领验证过期情况
		List<UserJcShare> jcShareList = userJcShareService.findJcShareByGbId(jcShare);
		if (CollectionUtils.isEmpty(jcShareList)) {// 没人领过
			json.put("isShare", false);
			// 验证是否过期
			if (RedPurseUtil.isExistRedPurse(jcShare.getGbId(), jcShare.getUserCode())) {
				json.put("expire", false);// 未过期
			} else {
				json.put("expire", true);// 过期
			}
		} else { // 有人领过
			json.put("isShare", true);
			// 验证用户是否领过红包
			String key = "getedRed_" + jcShare.getGbId() + "_" + jcShare.getUserCode();
			String userMobile = CookieUtil.findCookieByName(request, key);// cookie中获取用户领取过的手机号
			Integer getFlag = 0;
			UserJcShare userJcShare = null;
			if (StringUtils.isNotBlank(userMobile)) {
				for (UserJcShare share : jcShareList) {
					if (userMobile.equals(share.getUserMobile())) {// 根据第三方用户id验证是否已经领过红包
						getFlag = 1;
						userJcShare = share;
						break;
					}
				}

			}

			if (getFlag == 1) {// 领过啦
				json.put("get", 1);
				json.put("userJcShare", userJcShare);
				json.put("jcShareList", jcShareList);
			} else { // 还没领
				// 验证过期了没有，没过期的话，是否已经领完了
				// 验证是否过期
				// 验证是否过期
				if (RedPurseUtil.isExistRedPurse(jcShare.getGbId(), jcShare.getUserCode())) {
					json.put("expire", false);// 未过期
				} else {
					json.put("expire", true);// 过期
				}
			}
		}
		
		return json;
	}
	
	/**
	 * 输入手机号领红包
	 * @param userMobile
	 */
	@RequestMapping("/getRedPurse")
	@ResponseBody
	public Object getRedPurse(HttpServletResponse response, UserJcShare jcShare, String userMobile) {
		JSONObject json = new JSONObject();
		try {
			if (jcShare != null && StringUtils.isNotBlank(userMobile)) {
				jcShare.setUserMobile(userMobile);
				// 验证手机号
				UserEntity userParam = new UserEntity();
				userParam.setMobile(userMobile);
				UserEntity user = userService.findUserEntity(userParam);
				if (user != null) { // 系统中该手机号已注册
					jcShare.setUserId(user.getId());
				}
				UserEntity shareUser = userService.findByCampaignKey(jcShare.getUserCode());// 获取分享人
				jcShare.setShareUserId(shareUser.getId());
				json = userJcShareService.insertGetRedPurse(jcShare);
				if (json.getInteger("state") == 1) {
					String key = "getedRed_" + jcShare.getGbId() + "_" + jcShare.getUserCode();
					CookieUtil.addCookie(response, key, userMobile, Integer.MAX_VALUE);// cookie中获取用户领取过的手机号
				}
			}
		} catch (Exception e) {
			log.error("获取红包出错：", e);
		}

		return json;
	}

	/**
	 * 展示分享二维码 返回1展示，0不展示
	 * 
	 * @param jcShare
	 * @param userMobile
	 * @return
	 */
	@RequestMapping("/showPrcode")
	@ResponseBody
	public Object showPrcode(String userId, String gbId) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(gbId)) {
				json.put("jcShareKey", 0);
				return json;
			}
			if (!userId.equals(ShiroAuthenticationManager.getUserId())) {
				json.put("isShow", 0);
				return json;
			}
			// 验证配置开关是否打开
			String jcShareKey = configService.findConfigValueByKey(GlobalConstant.JC_SHARE_KEY);
			if (StringUtils.isNotBlank(jcShareKey) && jcShareKey.equals("1")) {// 打开
				// 验证是否过期
				// UserJc userJc = userJcService.findById(jcId);
				Map<String, Object> param = new HashMap<>();
				param.put("gbId", gbId);
				param.put("userId", userId);
				UserEntity userEntity = userCacheService.getUserEntityByUserId(userId);
				// 判断红包是否存在
				if (RedPurseUtil.isExistRedPurse(gbId, userEntity.getCampaignKey())) {
					List<UserJc> jcList = userJcService.findByMap(param);// 根据比赛id，查询竞猜记录
					UserJc userJc = null;
					if (CollectionUtils.isNotEmpty(jcList)) {
						userJc = jcList.get(0);// 取最新竞猜
					}
					if (userJc != null) {
						json.put("userCode", userEntity.getCampaignKey());
						json.put("isShow", 1);
					} else {
						json.put("isShow", 0);
					}
				} else {
					json.put("isShow", 0);
				}
				
				/*
				if (userJc == null || StringUtils.isBlank(userJc.getJcTime())) {
					json.put("isShow", 0);
				} else {
					String jcTime = userJc.getJcTime();
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
					if (calendar.getTimeInMillis() > Long.valueOf(jcTime)) {
						json.put("isShow", 0);// 过期
					} else {
						// 验证该竞猜是否分享过
						List<UserJcShare> jcShareList = userJcShareService.findJcShareByJcId(userJc.getId(), userId);
						if (CollectionUtils.isNotEmpty(jcShareList) && jcShareList.size() == 10) {// 分享过,并且领完了
							json.put("isShow", 0);
						} else { // 未领完
							json.put("isShow", 1);// 未过期
							json.put("jcId", userJc.getId());
						}
					}
				}*/

			} else { // 未打开
				json.put("isShow", 0);
			}

		} catch (Exception e) {
			log.error("竞猜展示二维码出错：", e);
			json.put("isShow", 0);
		}
		return json;
	}
}
