package com.webside.index.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.common.config.service.ConfigService;
import com.webside.common.exception.ForbiddenAccountException;
import com.webside.common.sms.SMS;
import com.webside.exception.SystemException;
import com.webside.frame.annotation.Token;
import com.webside.logininfo.model.LoginInfoEntity;
import com.webside.logininfo.service.LoginInfoService;
import com.webside.oauth.client.shiro.support.UsernamePasswordAndClientToken;
import com.webside.resource.model.ResourceEntity;
import com.webside.resource.service.ResourceService;
import com.webside.role.service.RoleService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.shiro.cache.redis.RedisCache;
import com.webside.shiro.cache.redis.VCache;
import com.webside.user.friends.service.IUserFriendsService;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.model.vo.ThirdUserInfo;
import com.webside.user.service.UserService;
import com.webside.user.share.entities.UserJcShare;
import com.webside.user.share.service.IUserJcShareService;
import com.webside.user.task.service.IUserTaskService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.CaptchaUtil;
import com.webside.util.CookieUtil;
import com.webside.util.DateUtils;
import com.webside.util.EndecryptUtils;
import com.webside.util.IdGen;
import com.webside.util.TreeUtil;

/**
 * 
 * @ClassName: IndexController
 * @Description: 登录、注册、退出、验证码
 * @author gaogang
 * @date 2016年7月12日 下午3:20:47
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/")
public class IndexController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private LoginInfoService loginInfoService;

	@Autowired
	private RoleService roleService;
	
	/**
	 * 推荐好友service
	 */
	@Autowired
	private IUserFriendsService userFriendsService;

	@Autowired
	private Producer captchaProducer;
	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	@Autowired
	private IUserMessageService userMessageService;
	@Autowired
	private IUserJcShareService userJcShareService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private IUserTaskService userTaskService;
	@RequestMapping(value = "login", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String login(Model model, HttpServletRequest request) {
		try {
			if (ShiroAuthenticationManager.getUserEntity() != null) 
			{
				return "redirect:/match";
			}
			request.removeAttribute("error");
			model.addAttribute("loginButtonHide", true);
			return Common.BACKGROUND_PATH_WEB + "/login/login";
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 用户登录 认证过程： 1、想要得到Subject对象,访问地址必须在shiro的拦截地址内,不然会报空指针
	 * 2、用户输入的账号和密码,存到UsernamePasswordAndClientToken对象中,然后由shiro内部认证对比
	 * 3、认证执行者交由ShiroDbRealm中doGetAuthenticationInfo处理 4、当以上认证成功后会向下执行,认证失败会抛出异常
	 * 
	 * @param accountName
	 *            账户名称
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public Object userLogin(UserEntity userEntity, String captcha, Boolean rememberMe, ThirdUserInfo thirdUser, HttpServletRequest request, HttpServletResponse response) {
		UsernamePasswordAndClientToken token = null;
		Map<String, Object> map = null;
		String userCookieMsg = "";
		SavedRequest savedRequest = null;

		try {
			map = new HashMap<String, Object>();
			map.put("code", 0);

			// 想要得到Subject对象,访问地址必须在shiro的拦截地址内,不然会报空指针
			Subject subject = SecurityUtils.getSubject();
			if (StringUtils.isNotBlank(userEntity.getMobile())) // 手机不为空，就用手机登录
			{
				token = new UsernamePasswordAndClientToken(userEntity.getMobile(), userEntity.getPassword());
				userCookieMsg = userCookieMsg + userEntity.getMobile();
			} else if (StringUtils.isNotBlank(userEntity.getEmail())) {
				token = new UsernamePasswordAndClientToken(userEntity.getEmail(), userEntity.getPassword());
				userCookieMsg = userCookieMsg + userEntity.getEmail();
			} else {
				map.put("msg", "账户不存在！");
			}
			// 记住用户登录状态
			if (rememberMe == null) {
				rememberMe = false;
			}
			token.setRememberMe(rememberMe);
			subject.login(token);

			if (subject.isAuthenticated()) {
				userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
				session.setAttribute("user", userEntity);
				if (StringUtils.isNotBlank(userEntity.getNickName())) {
					CookieUtil.addCookie(response, GlobalConstant.COOKIE_NICK_NAME, URLEncoder.encode(userEntity.getNickName(), "utf-8"), 60 * 60 * 24 * 7); // 存储昵称
				}
				if (StringUtils.isNotBlank(userEntity.getPhoto())) {
					CookieUtil.addCookie(response, GlobalConstant.COOKIE_USER_PHOTO, URLEncoder.encode(userEntity.getPhoto(), "utf-8").replaceAll("%2F", "/"), 60 * 60 * 24 * 7); // 存储头像
																																													// lwh:需要存储小规格的头像？
				} else {
					CookieUtil.clearCookie(request, response, GlobalConstant.COOKIE_USER_PHOTO);
				}

				if (StringUtils.isNotBlank(userEntity.getMobile())) {
					CookieUtil.addCookie(response, GlobalConstant.COOKIE_LOGIN_NAME, URLEncoder.encode(userEntity.getMobile(), "utf-8"), 60 * 60 * 24 * 7); // 存储账号
				}

				LoginInfoEntity loginInfo = new LoginInfoEntity();
				loginInfo.setUserId(userEntity.getId());
				loginInfo.setAccountName(userEntity.getNickName());
				loginInfo.setLoginIp(SecurityUtils.getSubject().getSession().getHost());
				loginInfoService.log(loginInfo);
				// 判断是否需要绑定第三方
				if (thirdUser != null) { // 如果通过第三方账户注册的，则加入第三方账号信息
					UserEntity userUpdate = new UserEntity();
					userUpdate.setId(userEntity.getId());
					if (GlobalConstant.THIRD_LOGIN_TYPE_STEAM.equals(thirdUser.getThirdType())) {
						userUpdate.setSteamKey(thirdUser.getThirdKey());
						userUpdate.setSteamNick(thirdUser.getThirdNick());
					}
					if (GlobalConstant.THIRD_LOGIN_TYPE_QQ.equals(thirdUser.getThirdType())) {
						userUpdate.setQqKey(thirdUser.getThirdKey());
						userUpdate.setQqNick(thirdUser.getThirdNick());
					}
					if (GlobalConstant.THIRD_LOGIN_TYPE_WEIBO.equals(thirdUser.getThirdType())) {
						userUpdate.setWeiboKey(thirdUser.getThirdKey());
						userUpdate.setWeiboNick(thirdUser.getThirdNick());
					}
					if (GlobalConstant.THIRD_LOGIN_TYPE_WECHAT.equals(thirdUser.getThirdType())) {
						userUpdate.setWechatKey(thirdUser.getThirdKey());
						userUpdate.setWechatNick(thirdUser.getThirdNick());
					}
					userService.updateUserOnly(userUpdate); // 更新绑定第三方信息
				}
				
				//获取上一次请求路径
				savedRequest = WebUtils.getSavedRequest(request);
				
				map.put("code", 1);
				map.put("msg", "success");
				map.put("url", savedRequest == null ? "" : savedRequest.getRequestURI());
			} else {
				token.clear();
				map.put("msg", "用户名或密码错误！");
			}
		} catch (UnknownAccountException uae) {
			token.clear();
			map.put("msg", "账户不存在！");
		} catch (IncorrectCredentialsException ice) {
			token.clear();
			map.put("msg", "用户名或密码错误！");
		} catch (LockedAccountException e) {
			token.clear();
			map.put("msg", "您的账户已被锁定,请稍后再试！");
		} catch (ExcessiveAttemptsException e) {
			token.clear();
			map.put("msg", "您连续输错5次,帐号将被锁定10分钟!");
		} catch (ExpiredCredentialsException eca) {
			token.clear();
			map.put("msg", "用户名或密码错误！");
		} catch (ForbiddenAccountException e) {
			token.clear();
			map.put("msg", "您的账号已被禁止登录");
		} catch (AuthenticationException e) {
			token.clear();
			map.put("msg", "用户名或密码错误！");
		} catch (Exception e) {
			logger.error("登录异常", e);
			token.clear();
			map.put("msg", "未知错误,请稍后！");
		}

		return map;
	}

	/***
	 * @title : 网站首页
	 * */
	@RequestMapping("index")
	@Token(save = true)
	public String index() 
	{
		if (ShiroAuthenticationManager.getUserEntity() != null) 
		{
			return "redirect:/match";
		}
		
		return Common.BACKGROUND_PATH_WEB + "/index/index";
	}

	/**
	 * @title : 商城
	 * */
	@RequestMapping("shoppingMall")
	public String shoppingMall() {
		return Common.BACKGROUND_PATH_WEB + "/goods/shoppingMall";
	}
	
	/**
	 * @title : 商城
	 * */
	@RequestMapping("my/jc")
	public String jcUi() {
		return Common.BACKGROUND_PATH_WEB + "/my/jc/jc";
	}

	/***
	 * 欢迎页面，登录会跳转到用户主页 未登录直接跳到网页首页。
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String main() 
	{
		if (ShiroAuthenticationManager.getUserEntity() != null) 
		{
			return "redirect:/match";
		}
		
		return "redirect:/index";
	}

	/**
	 * 
	 * @title : 后台首页
	 * @return
	 */
	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String admin(Model model) {
		try {
			// 获取登录的bean
			UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			List<ResourceEntity> list = resourceService.findResourcesMenuByUserId(userEntity.getId());
			List<ResourceEntity> treeList = new TreeUtil().getChildResourceEntitys(list, null);
			model.addAttribute("list", treeList);
			model.addAttribute("menu", JSON.toJSONString(treeList));
			// 登陆的信息回传页面
			model.addAttribute("userEntity", userEntity);
			return "/index";
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Token(save = true)
	@RequestMapping(value = "register", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String registerUI(Model model) {
		try {
			if (ShiroAuthenticationManager.getUserEntity() != null) 
			{
				return "redirect:/match";
			}
			model.addAttribute("loginButtonHide", true);
			return Common.BACKGROUND_PATH_WEB + "/login/register";
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param userEntity
	 * @return
	 */
	// @Token(remove = true)
	@RequestMapping(value = "register", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String register(UserEntity userEntity, ThirdUserInfo thirdUser,String fromuid) {
		try {

			// lwh:这里需要前后台双验证昵称和手机号码

			UserEntity userNick = userService.findByNickName(userEntity.getNickName());
			if (userNick == null) {
				UserEntity userMobile = userService.findByMobile(userEntity.getMobile());
				if (userMobile == null) {
					String password = userEntity.getPassword();
					userEntity.setId(IdGen.uuid());
					// 加密用户输入的密码，得到密码和加密盐，保存到数据库
					UserEntity user = EndecryptUtils.md5Password(userEntity.getId(), userEntity.getPassword(), 2);
					// 设置添加用户的密码和加密盐
					userEntity.setPassword(user.getPassword());
					userEntity.setCredentialsSalt(user.getCredentialsSalt());
					// 设置创建者姓名
					userEntity.setCreatorName(userEntity.getNickName());
					userEntity.setCreateTime(System.currentTimeMillis());
					// 设置锁定状态：未锁定；删除状态：未删除
					userEntity.setLocked(0);
					userEntity.setIsDeleted(0);
					// 通过注册页面注册的用户统一设置为普通用户,无角色
					// 普通用户不设角色
					// RoleEntity roleEntity = roleService.findByName("普通用户");
					// userEntity.setRole(roleEntity);
					// 保存用户注册信息
					if (thirdUser != null) { // 如果通过第三方账户注册的，则加入第三方账号信息
						if (GlobalConstant.THIRD_LOGIN_TYPE_STEAM.equals(thirdUser.getThirdType())) {
							userEntity.setSteamKey(thirdUser.getThirdKey());
							userEntity.setSteamNick(thirdUser.getThirdNick());
						}
						if (GlobalConstant.THIRD_LOGIN_TYPE_QQ.equals(thirdUser.getThirdType())) {
							userEntity.setQqKey(thirdUser.getThirdKey());
							userEntity.setQqNick(thirdUser.getThirdNick());
						}
						if (GlobalConstant.THIRD_LOGIN_TYPE_WEIBO.equals(thirdUser.getThirdType())) {
							userEntity.setWeiboKey(thirdUser.getThirdKey());
							userEntity.setWeiboNick(thirdUser.getThirdNick());
						}
						if (GlobalConstant.THIRD_LOGIN_TYPE_WECHAT.equals(thirdUser.getThirdType())) {
							userEntity.setWechatKey(thirdUser.getThirdKey());
							userEntity.setWechatNick(thirdUser.getThirdNick());
						}
					}
					int insert = userService.insert(userEntity, password);
					//新增成功，判断是否推荐注册。
					if (insert == 1) {
						Long giveGold = GlobalConstant.REGISTER_GIVE_GOLD;
						if (StringUtils.isNotBlank(fromuid)) {
							UserEntity findByCampaignKey = userService.findByCampaignKey(fromuid);
							if (findByCampaignKey != null && findByCampaignKey.getId() != null) {
								giveGold = GlobalConstant.REGISTER_GIVE_GOLD_RECOMMEND;
								userFriendsService.addRegister(findByCampaignKey.getId(), userEntity.getId(), userEntity.getNickName());
								userTaskService.updateUserTask(findByCampaignKey.getId(), GlobalConstant.USER_TASK_TYPE_FRIENDS_3, null);
							}
						}

						// 注册赠送G币发消息 （钱包数据，交易记录，消息）
						Integer rechargeResult = userWalletService.updateRechargeWallet(userEntity.getId(), giveGold, 1);
						if (rechargeResult > 0) {
							UserTransactionLog transaction = new UserTransactionLog();
							String transactionId = IdGen.uuid();
							transaction.setId(transactionId);
							transaction.setUserId(userEntity.getId());
							transaction.setGoldNum(giveGold);
							transaction.setGoldType(GlobalConstant.GOLD_TYPE_2);
							transaction.setUtTime(System.currentTimeMillis() + "");
							transaction.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_7);
							transaction.setUserNick(userEntity.getNickName());
							transaction.setDataId(userEntity.getId());
							transaction.setRemarks("注册赠送");
							userTransactionLogService.insert(transaction);
							userMessageService.addMessageForUserRegister(userEntity.getId(), userEntity.getId(), giveGold.intValue());
						}
						
						// 注册前领过的G币红包
						try {
							String jcShareKey = configService.findConfigValueByKey(GlobalConstant.JC_SHARE_KEY);
							if (StringUtils.isNotBlank(jcShareKey) && jcShareKey.equals("1")) {
								Map<String, Object> param = new HashMap<>();
								param.put("userMobile", userEntity.getMobile());
								Calendar calendar = Calendar.getInstance();
								calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);
								param.put("createTime", calendar.getTimeInMillis());
								List<UserJcShare> jcShareList = userJcShareService.queryJcShareByMobile(param);
								if (CollectionUtils.isNotEmpty(jcShareList)) {
									Long gold = 0L;
									for (UserJcShare jcShare : jcShareList) {
										jcShare.setUserId(userEntity.getId());
										// 更新交易记录
										UserTransactionLog transactionLog = new UserTransactionLog();
										transactionLog.setId(IdGen.uuid());
										transactionLog.setDataId(jcShare.getId());
										transactionLog.setGoldNum(jcShare.getGold());
										transactionLog.setUserId(jcShare.getUserId());
										transactionLog.setUtTime(System.currentTimeMillis() + "");
										transactionLog.setRemarks("竞猜分享领取G币");
										transactionLog.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_8);
										transactionLog.setUserNick(userEntity.getNickName());
										userTransactionLogService.insert(transactionLog);
										
										gold = gold + jcShare.getGold();
									}
									// 更新钱包
									userWalletService.updateRechargeWallet(userEntity.getId(), gold, GlobalConstant.RECHARGE_TYPE_GIFT);
									
								}

							}
						} catch (Exception e) {
							logger.error("用户补偿注册前领取的G币出错：",e);
						}
						
						userTaskService.updateUserTask(userEntity.getId(), GlobalConstant.USER_TASK_TYPE_SET_NICK_4, null);
					}
				} else {
					return "手机号已存在";
				}
			} else {
				return "昵称已存在";
			}
			return "success";
		} catch (Exception e) {
			logger.error("注册用户失败：", e);
			return "注册用户失败";
		}

	}

	/**
	 * 用户退出
	 * 
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) 
	{
		/**
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		String url = savedRequest == null ? null : savedRequest.getRequestURI();
		**/
		
		// 注销登录
		session.removeAttribute("user");
		// CookieUtil.clearCookie(request, response,
		// GlobalConstant.USER_LOGIN_MSG_COOKIES);
		CookieUtil.deleteAllCookie(request, response); // lwh:清楚所有cookie，不过要考虑搜索记录是否也要被清楚呢？
		ShiroAuthenticationManager.logout();
		
		//退出后跳转到退出之前的地址
		/**
		if(StringUtils.isNotBlank(url) && url.indexOf(".") < 0)
		{
			return "redirect:"+url;
		}
		**/
		
		return "redirect:index";
	}

	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void kaptcha(HttpServletResponse rsp) {
		ServletOutputStream out = null;
		try {
			rsp.setDateHeader("Expires", 0);
			rsp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			rsp.addHeader("Cache-Control", "post-check=0, pre-check=0");
			rsp.setHeader("Pragma", "no-cache");
			rsp.setContentType("image/jpeg");

			// String capText = captchaProducer.createText();
			String capText = CaptchaUtil.getNumCaptcha();
			System.out.println(capText);
			// 将验证码存入shiro 登录用户的session
			ShiroAuthenticationManager.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

			BufferedImage image = captchaProducer.createImage(capText);
			out = rsp.getOutputStream();
			ImageIO.write(image, "jpg", out);
			out.flush();

		} catch (IOException e) {
			throw new SystemException(e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				throw new SystemException(e);
			}
		}
	}

	/**
	 * 图片验证码,验证输入的正确性
	 * 
	 * @param rsp
	 */
	@RequestMapping(value = "/captcha/check", method = RequestMethod.POST)
	@ResponseBody
	public Object checkImgCaptcha(String imgCaptcha) {
		if (StringUtils.isBlank(imgCaptcha)) {
			return 0;
		}
		// 将验证码存入shiro 登录用户的session
		String sessionCa = (String) ShiroAuthenticationManager.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isNotBlank(sessionCa) && sessionCa.equals(imgCaptcha)) {
			return 1;
		} else {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "withoutAuth/genCaptcha", method = RequestMethod.POST)
	@ResponseBody
	public Object mobileKaptcha(String mobile, Integer type, String imgCaptcha) {
		try {
			if (StringUtils.isBlank(imgCaptcha)) {
				return -2;// 验证码不能为空
			}
			String textCa = (String) ShiroAuthenticationManager.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (StringUtils.isBlank(textCa) || !textCa.equals(imgCaptcha)) {
				return -3; // 验证码不相等
			}
			String countDownKey = GlobalConstant.CAPTCHA_COUNTDOWN + mobile;
			if (VCache.get(countDownKey) != null) {
				return -1; // 未过期，就是未超过2分钟
			}
			VCache.set(countDownKey, DateUtils.getDate("yyyy-MM-dd HH:mm:ss"), RedisCache.ONE_MINITES);// 1分钟倒计时

			// String capText = captchaProducer.createText();
			String capText = CaptchaUtil.getNumCaptcha();
			System.out.println(capText);
			// 将验证码存入shiro 登录用户的session
			// ShiroAuthenticationManager.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY,
			// capText);
			String kaptchaKey = GlobalConstant.CAPTCHA_MOBILE + mobile;
			VCache.set(kaptchaKey, capText, RedisCache.FIVE_MINITES);// 短信验证码缓存5分钟
			SMS.sendKaptcha(capText, mobile, SMS.MOBILE_AUTHENTICATION_KEY); // 统一用手机身份验证
			/*if (type == null || type == 0) { // 手机注册验证码
				SMS.sendKaptcha(capText, mobile, SMS.SMS_REGISTER_KEY);
			} else if (type == 1) {// 手机号身份验证
			} else if (type == 2) {// 修改密码
				SMS.sendKaptcha(capText, mobile, SMS.MODIFY_PASSWORD_KEY);
			}*/
			ShiroAuthenticationManager.delSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
		} catch (Exception e) {
			logger.error("生成短信验证码出错", e);
			return 0;
		}

		return 1;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/withoutAuth/checkCaptcha", method = RequestMethod.POST)
	@ResponseBody
	public Object checkCaptcha(String captcha, String mobile, Integer isClear) {
		try {
			if (StringUtils.isBlank(captcha)) {
				return 0;
			} else {
				String captchaKey = GlobalConstant.CAPTCHA_MOBILE + mobile;
				// 从redis中取出验证码
				String capText = VCache.get(captchaKey);
				if (StringUtils.isBlank(capText)) {
					if (isClear != null && isClear == 1) { // 清缓存
						VCache.delByKey(captchaKey);
					}
					return 0;
				}
				if (!captcha.equals(capText)) {
					return 0;
				}
			}

		} catch (Exception e) {
			logger.error("检查验证码出错", e);
			return 0;
		}
		return 1;
	}

	/**
	 * 胜率TOP列表
	 * @return
	 * @author zengxn
	 */
	@RequestMapping(value="profitRateTopList", method = RequestMethod.POST)
	@ResponseBody
	public Object profitRateTopList(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			JSONArray jsonArray = userService.profitRateTop(5);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("list", jsonArray);
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			parameters.put("message", e.getMessage());
			logger.error("ajax获取胜率TOP列表数据出错：", e);
		}
		return parameters;
	}
	
	/**
	 * 盈利TOP列表
	 * @return
	 * @author zengxn
	 */
	@RequestMapping(value="profitTopList", method = RequestMethod.POST)
	@ResponseBody
	public Object profitTopList(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			JSONArray jsonArray = userService.profitTop(5);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("list", jsonArray);
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			parameters.put("message", e.getMessage());
			logger.error("ajax获取盈利TOP列表数据出错：", e);
		}
		return parameters;
	}
}
