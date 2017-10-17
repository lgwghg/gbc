package com.webside.common;

import java.util.Date;
/**
 * @title : 缓存常量类
 * @author LiGan
 * */
public interface CacheConstant {

	
	
	// 缓存时长配置
	public static final Date CACHE_EXPIRE_2MIN= new Date(1000*60*2);// 有效期2分钟
	public static final Date CACHE_EXPIRE_10MIN= new Date(1000*60*10);// 有效期10分钟
	public static final Date CACHE_EXPIRE_15MIN= new Date(1000*60*15);// 有效期15分钟
	public static final Date CACHE_EXPIRE_20MIN= new Date(1000*60*20);// 有效期20分钟
	public static final Date CACHE_EXPIRE_30MIN= new Date(1000*60*30);// 有效期半个小时
	public static final Date CACHE_EXPIRE_ONEDAY= new Date(1000*60*60*24);//有效期一天
	public static final Date CACHE_EXPIRE_15DAY= new Date(1000*60*60*360);//有效期一天
	
	
	//模块名:缓存key
	public static final String CACHE_CODE_INFO = "system:code_info";			//标准代码缓存key前缀
	
	public static final String CACHE_GETREGIONCITY_LIST = "CACHE_GETREGIONCITY_LIST";		//获得所有区域列表
	
	//seo配置缓存key
	public static final String SEO_CONFIG_CODE_INFO = "seo:code_info";
	
	//评论时间戳缓存key前缀
	public static final String COMMENT_CODE_INFO = "comment:id:";
	
	//清除15天前的未付款充值记录任务标识
	public static final String QUARTZ_USER_DEPOSITLOG_JOB_STATUS = "quartz_udl_job_status";
	
	//敏感词集合
	public static final String SYSTEM_SENSITIVEWORDS_SET = "sw:set";

	/**
	 * 用户缓存常量，务占用
	 * 
	 * @author suyan
	 * 
	 */
	public interface UserConstant {
		String USER_REDIS_CACHE_KEY = "u:";
		String USER_ROLE_REDIS_CACHE_KEY = "u:r:";
	}
	
	/**
	 * 系统通知缓存常量
	 * @author zengxn
	 */
	public interface SNConstant {
		String SYSTEM_NOTICE_SET = "sn:set";
		String SYSTEM_NOTICE_GAME_BATTLE_SET = "sn:gb:set";
		String SYSTEM_NOTICE_COINFLIP_LIST = "sn:coin:list";
	}
}
