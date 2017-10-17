package com.webside.enums;


public enum IntegralTypeEnum {
	
	/** 注册  */
	REGISTER_0("0", 30, "注册"),
	/** 完成邮箱验证  */
	COMPLETED_EMAIL_1("1", 20, "首次完成邮箱验证"),
	/** 完善头像上传  */
	COMPLETED_PHOTO_2("2", 20, "首次完成头像上传"),
	/** 每完成一个第三方账户绑定  */
	COMPLETED_STEAM_BIND_3("3", 10, "完成steam账号绑定"),
	COMPLETED_WEIBO_BIND_31("31", 10, "完成weibo账号绑定"),
	COMPLETED_QQ_BIND_32("32", 10, "完成qq账号绑定"),
	COMPLETED_WECHAT_BIND_33("33", 10, "完成wechat账号绑定"),
	
	/** 每日网站登录  */
	DAILY_WEB_LOGIN_4("4", 2, "每日网站登录"),
	/** 每日app登录  */
	DAILY_APP_LOGIN_5("5", 5, "每日app登录"),
	/** 每日视频点赞  */
	DAILY_VIDEO_LAUD_6("6", 2, "每日视频点赞"),
	/** 每日视频分享  */
	DAILY_VIDEO_SHARE_7("7", 5, "每日视频分享"),
	/** 每日视频观看  */
	DAILY_VIDEO_WATCH_8("8", 5, "每日视频观看"),
	
	/** 发布评论  */
	RELEASE_COMMENT_9("9", 1, "发布评论"),
	/** 发布视频  */
	RELEASE_VIDEO_10("10", 5, "发布视频"),
	/** 视频被推荐  */
	VIDEO_RECOMMENDED_11("11", 5, "视频被推荐"),
	/** 意见反馈  */
	FEEDBACK_BUG_12("12", 10, "意见反馈");
	
	private String type; // 需要加积分的类型
	private Integer integral; // 每个类型的积分
	private String remark; // 备注
	private IntegralTypeEnum(String type, Integer integral, String remark)
	{
		this.type = type;
		this.integral = integral;
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public Integer getIntegral() {
		return integral;
	}
	public String getRemark() {
		return remark;
	}
	
	
	
}
