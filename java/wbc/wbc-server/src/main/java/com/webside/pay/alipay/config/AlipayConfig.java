package com.webside.pay.alipay.config;



/**
 * alipay常量/参数类
 * 
 * @author: zengxn
 */
public class AlipayConfig {
	
	/**
	 * 合作身份者ID，签约账号
	 */
	public static final String PARTNER = "2088021351252982";
	
	/**
	 * 收款支付宝账号
	 */
	public static final String SELLER_ID = PARTNER;
	
	/**
	 * 支付类型 ，无需修改
	 */
	public static final String PAYMENT_TYPE = "1";
	
	/**
	 * 密钥，安全检验码
	 */
	public interface Key{
		/**
		 * MD5-key
		 */
		public static final String MD5_KEY = "bkarh2y1bu9ly9zi07wrvk1eqr9t4j87";
		
		/**
		 * RSA-私钥
		 */
		public static final String RSA_PRIVATE_KEY = "bkarh2y1bu9ly9zi07wrvk1eqr9t4j87";
	}
	
	/**
	 * 服务器异步通知页面路径
	 */
	public interface NotifyUrl{
		/**
		 * web-开发环境
		 */
		public static final String WEB_DEV = "http://59.172.188.85:8091/pay/alipay/notify_web";
		/**
		 * web-测试环境
		 */
		public static final String WEB_TEST = "http://test.gbocai.com/pay/alipay/notify_web";
		/**
		 * web-生产环境
		 */
		public static final String WEB_PRD = "http://www.gbocai.com/pay/alipay/notify_web";
	}
	
	
	/**
	 * 页面跳转同步通知页面路径
	 */
	public interface ReturnUrl{
		/**
		 * web-开发环境
		 */
		public static final String WEB_DEV = "http://59.172.188.85:8091/my/transactionLog";
		/**
		 * web-测试环境
		 */
		public static final String WEB_TEST = "http://test.gbocai.com/my/transactionLog";
		/**
		 * web-生产环境
		 */
		public static final String WEB_PRD = "http://www.gbocai.com/my/transactionLog";
	}
	
	/**
	 * 交易状态
	 */
	public interface TradeStatus{
		/**
		 * 交易完成
		 */
		public static final String FINISHED = "TRADE_FINISHED";
		/**
		 * 支付成功
		 */
		public static final String SUCCESS = "TRADE_SUCCESS";
	}
	
	/**
	 * 调用接口
	 */
	public interface Service{
		/**
	     * WEB支付
	     */
		public static final String WEB_PAY = "create_direct_pay_by_user";
	}
	
	/**
	 * 签名方式
	 */
	public interface SignType{
		/**
	     * MD5
	     */
		public static final String MD5 = "MD5";
	}
	
	/**
	 * 字符编码格式
	 */
	public interface InputCharset{
		/**
	     * utf-8
	     */
		public static final String UTF8 = "utf-8";
	}
	
	/**
	 * 返回结果
	 */
	public interface Return{
		/**
	     * 成功
	     */
		public static final String SUCCESS = "success";
		
		/**
	     * 失败
	     */
		public static final String FAIL = "fail";
	}
	
	/**
	 * 防钓鱼参数
	 */
	public interface Fishing{
		/**
		 * 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
		 */
		public static final String ANTI_PHISHING_KEY = "";
		/**
		 * 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
		 */
		public static final String EXTER_INVOKE_IP = "";
	}
}
