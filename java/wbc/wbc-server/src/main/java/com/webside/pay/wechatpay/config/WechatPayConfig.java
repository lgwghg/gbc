package com.webside.pay.wechatpay.config;



/**
 * wechatpay常量/参数类
 * 
 * @author: zengxn
 */
public class WechatPayConfig {
	
	/**
	 * 商户号
	 */
	public static final String MCH_ID = "1437186202";
	
	/**
	 * 公众账号ID
	 */
	public static final String APP_ID = "wx506ae5650c3b13f3";
	
	/**
	 * API密钥
	 */
	public static final String API_KEY = "V3tM1k2GlG9D3Uyr3WYrcnm6N1D7T38z";
	
	/**
	 * 设备号 ，PC网页或公众号内支付可以传"WEB"
	 */
	public static final String DEVICE_INFO = "WEB";
	
	/**
	 * 交易类型
	 */
	public static final String TRADE_TYPE = "NATIVE";
	
	
	/**
	 * 访问接口
	 */
	public static final String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 服务器异步通知页面路径
	 */
	public interface NotifyUrl{
		/**
		 * web-开发环境
		 */
		public static final String WEB_DEV = "http://59.172.188.85:8091/pay/wechatpay/notify_web";
		/**
		 * web-测试环境
		 */
		public static final String WEB_TEST = "http://test.gbocai.com/pay/wechatpay/notify_web";
		/**
		 * web-生产环境
		 */
		public static final String WEB_PRD = "http://www.gbocai.com/pay/wechatpay/notify_web";
	}
	
	/**
	 * 返回内容
	 */
	public interface ReturnXml{
		/**
		 * 成功
		 */
		public static final String OK = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		/**
		 * 订单不存在
		 */
		public static final String NOT_ORDER = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[订单不存在于本系统]]></return_msg></xml>";
		/**
		 * 系统出错
		 */
		public static final String ERRO = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[系统出错]]></return_msg></xml>";
		/**
		 * 自由拼接前部分
		 */
		public static final String NO_START = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[";
		/**
		 * 自由拼接后部分
		 */
		public static final String NO_END = "]]></return_msg></xml>";
		
	}
	
	/**
	 * 返回状态码
	 */
	public interface ReturnCode{
		/**
	     * 成功
	     */
		public static final String SUCCESS = "SUCCESS";
		
		/**
	     * 失败
	     */
		public static final String FAIL = "FAIL";
	}
}
