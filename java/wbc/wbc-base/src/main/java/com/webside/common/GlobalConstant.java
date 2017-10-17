package com.webside.common;

import com.webside.util.PropertyUtil;

/**
 * 全局常量/参数类
 * 
 * @author: LiGan
 * @data: 2016-8-15
 */
public class GlobalConstant {

	/**
	 * 用户令牌属性
	 */
	public static final String USER_TOKEN = "token";

	public static final String USER_LOGIN_MSG_COOKIES = "wodota_user";

	public static final String COOKIE_FOLDER_NAME = "wodota";

	public static final String COOKIE_NICK_NAME = "cookie_nick_name";

	public static final String COOKIE_LOGIN_NAME = "cookie_login_name";

	public static final String COOKIE_USER_PHOTO = "cookie_user_photo";

	// 切换下注战队 扣除费用100G币
	public static final long UPDATE_UESR_JCTEAM_GOLD = 100;
	/**
	 * 游戏英文名称cookie key
	 */
	public static final String COOKIE_GAME_ENGLISH_NAME = "cookie_game_english_name";

	/**
	 * @title : 是否删除分类
	 */
	public static final String DICTTYPE_IS_DELETE = "is_delete"; // 是否删除分类 0：正常
	public static final String DICTTYPE_IS_DELETE_0 = "0"; // 正常
	public static final String DICTTYPE_IS_DELETE_1 = "1"; // 删除

	/**
	 * @title : 有效状态
	 */
	public static final String DICTTYPE_EFFECTIVE_STATUS = "effective_status"; // 有效状态
																				// 0：无效
																				// 1
																				// 有效
	public static final String DICTTYPE_EFFECTIVE_STATUS_0 = "0"; // 0：无效
	public static final String DICTTYPE_EFFECTIVE_STATUS_1 = "1"; // 1 有效

	/**
	 * @title :商品类型
	 */
	public static final String DICTTYPE_GOODS_TYPE = "goods_type";
	public static final String DICTTYPE_GOODS_TYPE_1 = "1"; // 实物
	public static final String DICTTYPE_GOODS_TYPE_2 = "2"; // 虚拟物品

	/**
	 * @title :库存状态
	 */
	public static final String DICTTYPE_STOCK_STATUS = "stock_status";
	public static final String DICTTYPE_STOCK_STATUS_0 = "0"; // 无效
	public static final String DICTTYPE_STOCK_STATUS_1 = "1"; // 未兑换
	public static final String DICTTYPE_STOCK_STATUS_2 = "2"; // 已兑换
	public static final String DICTTYPE_STOCK_STATUS_3 = "3"; // 已过期

	/**
	 * @title :兑换状态
	 */
	public static final String DICTTYPE_EXCHANGE_STATUS = "exchange_status";
	public static final String DICTTYPE_EXCHANGE_STATUS_1 = "1"; // 未领取
	public static final String DICTTYPE_EXCHANGE_STATUS_2 = "2"; // 已领取
	public static final String DICTTYPE_EXCHANGE_STATUS_3 = "3"; // 出售中
	public static final String DICTTYPE_EXCHANGE_STATUS_4 = "4"; // 已售出
	public static final String DICTTYPE_EXCHANGE_STATUS_5 = "-1"; // CD-KEY形式，等待领取

	/**
	 * @title : 是/否
	 */
	public static final String DICTTYPE_YES_NO = "yes_no"; // 是/否 1 是 0 否
	public static final String DICTTYPE_YES_NO_0 = "0";
	public static final String DICTTYPE_YES_NO_1 = "1";

	/**
	 * @title : 敏感词内容所属类型
	 */
	public static final String SW_CONTENT_TYPE = "sw_content_type"; // 敏感词内容所属类型
	public static final String SW_CONTENT_TYPE_1 = "1"; // 1 不文明语
	public static final String SW_CONTENT_TYPE_2 = "2"; // 2暴力倾向
	public static final String SW_CONTENT_TYPE_3 = "3"; // 3不健康色彩
	public static final String SW_CONTENT_TYPE_4 = "4"; // 4敏感政治倾向
	public static final String SW_CONTENT_TYPE_5 = "5"; // 5邪教欺骗

	/**
	 * @title : 敏感词作用域类型
	 */
	public static final String SW_USE_TYPE = "sw_use_type"; // 敏感词作用域类型
	public static final String SW_USE_TYPE_1 = "1"; // 1 全局

	/**
	 * @title: 阅读状态
	 */
	public static final String MESSAGE_STATE = "message_state"; // 1：已读 0：未读
	public static final String MESSAGE_STATE_1 = "1";
	public static final String MESSAGE_STATE_0 = "0";

	/**
	 * 网站公告
	 */
	public static final String WEB_NOTICE_STATUS = "web_notice_status"; // 网站公告状态
	public static final String WEB_NOTICE_STATUS_0 = "0"; // 无效
	public static final String WEB_NOTICE_STATUS_1 = "1"; // 有效
	public static final String WEB_NOTICE_STATUS_2 = "2"; // 注册
	public static final String WEB_NOTICE_STATUS_3 = "3"; // 底部

	public static final String WEB_NOTICE_TYPE = "web_notice_type"; // 网站公告类型
	public static final String WEB_NOTICE_TYPE_1 = "1"; // 网站公告
	public static final String WEB_NOTICE_TYPE_2 = "2"; // 网站更新
	public static final String WEB_NOTICE_TYPE_3 = "3"; // 网站介绍
	public static final String WEB_NOTICE_TYPE_4 = "4"; // 使用帮助
	public static final String WEB_NOTICE_TYPE_5 = "5"; // 用户须知
	public static final String WEB_NOTICE_TYPE_6 = "6"; // 用户反馈

	/**
	 * @title: SEO类型
	 */
	public static final String SEO_CONFIG_TYPE = "seo_config_type";

	/**
	 * @title: 验证码相关key
	 */
	public static final String CAPTCHA_COUNTDOWN = "captcha_countdown_";
	public static final String CAPTCHA_MOBILE = "captcha_mobile_";
	public static final String CAPTCHA_EMAIL = "captcha_email_";

	/**
	 * @title:默认头像
	 */
	public static final String DEFAULT_PHOTO_URL = "/resources/wbc/images/denglu_n.png";

	/**
	 * @title: 用户消息业务类型
	 */
	public static final String MESSAGE_BUSINESS_TYPE = "message_business_type"; // 1:评论，2：充值成功
																				// 3：竞猜获得
																				// 4：意见反馈
																				// 5：推荐好友奖励
																				// 6：竞猜取消返回
																				// 7：注册成功赠送G币
																				// 8：兑换cdkey
																				// 9：提现通知
	public static final String MESSAGE_BUSINESS_TYPE_1 = "1";
	public static final String MESSAGE_BUSINESS_TYPE_2 = "2";
	public static final String MESSAGE_BUSINESS_TYPE_3 = "3";
	public static final String MESSAGE_BUSINESS_TYPE_4 = "4";
	public static final String MESSAGE_BUSINESS_TYPE_5 = "5";
	public static final String MESSAGE_BUSINESS_TYPE_6 = "6";
	public static final String MESSAGE_BUSINESS_TYPE_7 = "7";
	public static final String MESSAGE_BUSINESS_TYPE_8 = "8";
	public static final String MESSAGE_BUSINESS_TYPE_9 = "9";
	public static final String MESSAGE_BUSINESS_TYPE_10 = "10";// roll奖品
	public static final String MESSAGE_BUSINESS_TYPE_COIN_11 = "11";// 翻硬币中奖
	public static final String MESSAGE_BUSINESS_TYPE_COIN_REBACK_12 = "12";// 翻硬币取消返回

	/**
	 * @title: 赛事状态
	 */
	public static final String GAME_EVENT_STATUS = "game_event_status"; // 1：开始
																		// 0：结束
	public static final String GAME_EVENT_STATUS_1 = "1";
	public static final String GAME_EVENT_STATUS_0 = "0";

	/**
	 * @title : 比赛对战规则 1：BO1 2:BO2 3:BO3 5:BO5 7:BO7
	 */
	public static final String GAME_RULE = "game_rule";
	public static final String GAME_RULE_1 = "1";
	public static final String GAME_RULE_2 = "2";
	public static final String GAME_RULE_3 = "3";
	public static final String GAME_RULE_5 = "5";
	public static final String GAME_RULE_7 = "7";

	/**
	 * @title : 比赛对战状态 1：未开始 2：进行中 3：已结束 4:已取消
	 */
	public static final String GB_STATUS = "gb_status";
	public static final String GB_STATUS_1 = "1";
	public static final String GB_STATUS_2 = "2";
	public static final String GB_STATUS_3 = "3";
	public static final String GB_STATUS_4 = "4";

	/**
	 * @title : 盘口状态 0：未开始 1：进行中 2：已结束 3:已取消
	 */
	public static final String PK_STATUS = "pk_status";
	public static final String PK_STATUS_0 = "0";
	public static final String PK_STATUS_1 = "1";
	public static final String PK_STATUS_2 = "2";
	public static final String PK_STATUS_3 = "3";

	/**
	 * @title : 玩法类型 0：独赢 1：让分 2：其它
	 */
	public static final String PANKOU_TYPE = "pankou_type";
	public static final String PANKOU_TYPE_0 = "0";
	public static final String PANKOU_TYPE_1 = "1";
	public static final String PANKOU_TYPE_2 = "2";

	/**
	 * @title : 比赛对战战队 1:主战队，2：客场战队
	 */
	public static final String JC_TEAM_TYPE = "jc_team_type";
	public static final String JC_TEAM_TYPE_1 = "1";
	public static final String JC_TEAM_TYPE_2 = "2";

	/**
	 * @title : 用户竞猜结果 0:输 1：赢 2 ：进行中 3：已取消
	 */
	public static final String USER_JC_GAME_RESULT = "USER_JC_GAME_RESULT";
	public static final String USER_JC_GAME_RESULT_0 = "0";
	public static final String USER_JC_GAME_RESULT_1 = "1";
	public static final String USER_JC_GAME_RESULT_2 = "2";
	public static final String USER_JC_GAME_RESULT_3 = "3";

	// 第三方用户绑定类型
	public static final String THIRD_LOGIN_TYPE_STEAM = "steam";
	public static final String THIRD_LOGIN_TYPE_QQ = "qq";
	public static final String THIRD_LOGIN_TYPE_WEIBO = "weibo";
	public static final String THIRD_LOGIN_TYPE_WECHAT = "wechat";

	// 交易类型
	public static final String USER_TRANSACTION_TYPE = "transaction_type";
	public static final String USER_TRANSACTION_TYPE_1 = "1";// 竞猜
	public static final String USER_TRANSACTION_TYPE_2 = "2";// 充值
	public static final String USER_TRANSACTION_TYPE_3 = "3";// 兑换
	public static final String USER_TRANSACTION_TYPE_4 = "4";// 推荐好友奖励
	public static final String USER_TRANSACTION_TYPE_5 = "5";// 签到
	public static final String USER_TRANSACTION_TYPE_6 = "6";// 充值奖励
	public static final String USER_TRANSACTION_TYPE_7 = "7";// 注册赠送G币
	public static final String USER_TRANSACTION_TYPE_8 = "8";// 竞猜分享领取
	public static final String USER_TRANSACTION_TYPE_9 = "9";// 提现
	public static final String USER_TRANSACTION_TYPE_10 = "10";// CD-KEY兑换
	public static final String USER_TRANSACTION_TYPE_11 = "11";// 完成任务领取G币
	public static final String USER_TRANSACTION_TYPE_12 = "12";// roll奖品
	public static final String USER_TRANSACTION_TYPE_13 = "13";// 翻硬币

	/** 竞猜扣款 */
	public static final Integer DEDUCT_TYPE_JC = 0;
	/** 兑换扣款 */
	public static final Integer DEDUCT_TYPE_EXCHANGE = 1;
	/** 提现扣款 */
	public static final Integer DEDUCT_TYPE_WITHDRAW = 2;
	/** 充值G币 */
	public static final Integer RECHARGE_TYPE_RECHARGE = 0;
	/** 赠送G币 */
	public static final Integer RECHARGE_TYPE_GIFT = 1;
	/** 竞猜获取/退回G币 */
	public static final Integer RECHARGE_TYPE_JC = 2;

	/**
	 * 推荐好友赠送G币
	 * 
	 */
	public static final Integer REWARD_GOLD_NUM_100 = 100;
	public static final Integer REWARD_GOLD_NUM_200 = 200;

	/**
	 * 支付类型
	 */
	public static final String PAY_TYPE = "pay_type";
	public static final String PAY_TYPE_0 = "0";// 提现
	public static final String PAY_TYPE_1 = "1";// 支付宝
	public static final String PAY_TYPE_2 = "2";// 微信

	/**
	 * 货币类型
	 */
	public static final String GOLD_TYPE = "gold_type";
	public static final String GOLD_TYPE_1 = "1";// 金币
	public static final String GOLD_TYPE_2 = "2";// G币

	/**
	 * G币现金比例
	 */
	public static final Integer GOLD_CASH_RATIO = 100;
	/**
	 * 注册赠送G币
	 */
	public static final Long REGISTER_GIVE_GOLD = 100L;
	public static final Long REGISTER_GIVE_GOLD_RECOMMEND = 300L;

	/**
	 * 发送消息通道值
	 */
	public static final String SEND_MESSAGE_PARAM = "console";
	/**
	 * 竞猜分享开关，config表中的key
	 */
	public static final String JC_SHARE_KEY = "jc_share_key";
	/**
	 * cd-key兑换状态
	 */
	public static final String CD_KEY_STATE = "cd_key_state";
	public static final Integer CD_KEY_STATE_0 = 0;// 未兑换
	public static final Integer CD_KEY_STATE_1 = 1;// 已兑换
	public static final Integer CD_KEY_STATE_2 = 2;// 冻结

	/**
	 * 用户任务状态
	 */
	public static final String USER_TASK_TYPE = "user_task_type";
	public static final Integer USER_TASK_TYPE_SIGN_0 = 0;// 签到
	public static final Integer USER_TASK_TYPE_JC_1 = 1;//
	public static final Integer USER_TASK_TYPE_COMMENT_2 = 2;
	public static final Integer USER_TASK_TYPE_FRIENDS_3 = 3;
	public static final Integer USER_TASK_TYPE_SET_NICK_4 = 4;
	public static final Integer USER_TASK_TYPE_SET_PHOTO_5 = 5;
	public static final Integer USER_TASK_TYPE_SET_EMAIL_6 = 6;
	public static final Integer USER_TASK_TYPE_SET_PAY_PWD_7 = 7;
	public static final Integer USER_TASK_TYPE_CHARGE_8 = 8;

	/**
	 * 环境标识：常量
	 */
	public interface EnvConstant {
		String DEV = "DEV";// 开发环境
		String TEST = "TEST";// 测试环境
		String PRD = "PRD";// 生产环境
	}

	/**
	 * 环境标识
	 */
	public static final String ENV = PropertyUtil.getProInfo("webside.env");

	/**
	 * socket地址
	 */
	public interface SocketAddressConstant {
		String DEV = "192.168.10.180:5678";// 测试环境
		String TEST = "192.168.1.3:6678";// 测试环境
		String PRD = "socket.gbocai.com";// 生产环境
	}

	public static final String VERSION = PropertyUtil.getProInfo("webside.version");

	/**
	 * 翻硬币房间状态
	 */
	public static final String COINFLIP_ROOM_STATUS = "coinflip_room_status";
	public static final Integer COINFLIP_ROOM_STATUS_START_0 = 0;// 等待加入
	public static final Integer COINFLIP_ROOM_STATUS_JOINED_1 = 1;// 加入中
	public static final Integer COINFLIP_ROOM_STATUS_UNOPEN_2 = 2;// 等待开奖
	public static final Integer COINFLIP_ROOM_STATUS_OPENED_3 = 3;// 已结束
	public static final Integer COINFLIP_ROOM_STATUS_CLOSED_4 = 4;// 已取消
}
