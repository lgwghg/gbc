package com.webside.pay.newalipay;

/**
 * alipay常量/参数类 对应2017-04-05版api
 * 
 * @author: zengxn
 */
public class NewAlipayConfig {

	/**
	 * 应用ID
	 */
	public static String APP_ID = "2017011605125377";

	/**
	 * 商户私钥
	 */
	public static String MERCHANT_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDAXscHWehSDO9wi8C8imIoB7bDMIbQWTWciTleRPahKGbjYIXwTSEneNouwlulj2jIOwPJy/wGqt34DhmFWRdszE61Zk2u0LqgTm5htJP0LaJv74qt0EfCpOHXY1RJS+yuV1KiHHWfedZ3L3ZeNzO1JEywkMAj9uYWOcE7MbH9leeNYceNw/mtyC9xvgu2KzJ3ypHB93cJHdcEMkNC9Z5dFX6g6PlNqANS0qmt02tXwQ9bOGDdEiYichZZOXONtUnKzQUcRmJYt7wT+yceDCtXnczbP7ORUt3eIUKHueO+kvsAgaSSFdoI8EJB3KOUXDbISUogdHfqV4IbBhGbSHDdAgMBAAECggEAO1J+6eNt4y+d/wT50KqUDmgikhq62DFKeRVQHaQCsjv1TpULeMkwOi3oipbhEDMjSuv4BRjkhWHXxZEDib2pnaF7fDEqL4iqpJjLLVI0dg5Ek2Q5TdR27K1P/83fxiKlwDWpg9hm5WD7JBH83J0FUwz4oVJiDjUrSxtQXWK70skHZWqP0Mkb/MO1EY6KaqElAF5PDA74XAD7ewKfMZbHuXiWN3wXQHP5dSVVquFg1fdzOWegfrIy2T2SGet4a8wzD7iYet2cuBuZnqkZhSimxR+Z8TGxsFGcY0/+xljEX4wcq2LKu6jmqdjgmowlqoA4Po62MPtOFi22QEtCVrFdeQKBgQD9aZXikiKLHWSYmVrT5vZ3Ay17cttp6GZkPE67VItNBH9kSvvZE0AsDOYTTKUyjlIVzjuFhPrflwBX+dDT4WItv1Ov0T6ynpXLsz/AGWYkrK++XvmuWuRubreMmGQgtoJsiRG1QJG8BDJY9HnFzi4Eg0sTrpMi2bDMmiTkQuyQKwKBgQDCVaEDhMbxFhsPZMsjbRFR65c+DavxbOd89xWwkHopAtonaDapQLk9e0r4uTPzbkC5pcdnQnjkqUhOOb4SKq7ISqlJsmq45srYGsaZBjEV0f1/L7GG0jgPQXPEZXKop6MpXW0SEikQE0oUH8AKwlTFRdeemMY2HQrF2AJoRcr3FwKBgDqcjzRGRIMUm03IOn/EbcD9FSn2WNVYEUahxDwDw+xeW/Cgfzun/jGyEgzor1TQIiA1AQNSzIuma0ft2eUHviOCmyKRLQU3NMJWhSozNbLJ1iQb53fyQd9g8EcugAoo7GWaFmRruMlZ1yNH+FpN48rbayP0PsfSvde010+MQxbdAoGBAI2wbygonABVVL+Bhta6hAFNm0UkJLRu6czkPg0xGdBjAvnucsQuq6DXDw+PwQAA5/Izm9J+1bQqdMyMLzgBpNPmE83m5wcPRuAPjbMun4VASOp9tJsPFM6SszyDlvi+2SiktIyTbxsIZV+M0rFFhRccWByLWSFFRYCAgSjTkitbAoGBALCLcFuBs4aX692W8GOF1rHoSqT4zchTnwX/T898a1YVkibBzCbrEqepRcGmle6oSc1Qbtpe12yuieTHMCYPTHKyxXikQNR1goiNfko69Rrf1D1E2wFVan8PSbfWOsY2mnQJaPMeu5EQ8Cc7u6Fc55tYtleyidaQ0WBDbRQCHSgo";

	/**
	 * 支付宝公钥
	 */
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr1WIGLsATumLGOJlNGWSgr+AUqLEA/8NlapLNnJa/AbfsKJeB5ipXYh2n82iffzPxWySoqMN+LtADFp4QpYSARl8z1j+0uWb501zj/+F6Y7ruGhjnHoFG/7Q9E7yMyShpNFO/gFQSyH52xWao4SEgK+rVe4NqwJBDCPIFNyVtc8kp/lDHxG3sAYeMDZZ8q2/PxT78arOAQ8owclpZ0dxJNuOERKZWhMeXJ6jWbYaIUiUFQldzicROuAmijWZZM3g8+gYJ+lJqmPkbTkW4+/nFG+1/h4k+Ws8/0jDMJP5fOLSMlJBfnv9S4y2GtUFOFOgMZoQYSkSNDaKDcyVnn5ONQIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	// public static String notify_url =
	// "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	// public static String return_url =
	// "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	/**
	 * 签名方式
	 */
	public static String SIGN_TYPE = "RSA2";

	/**
	 * 字符编码格式
	 */
	public static String CHARSET = "utf-8";

	/**
	 * 参数格式
	 */
	public static String FORMAT = "json";

	/**
	 * 支付宝网关
	 */
	public static String GATEWAYURL = "https://openapi.alipay.com/gateway.do";

	/**
	 * 单笔转账业务错误码
	 */
	public interface TRANSFER_SUB_CODE {
		/**
		 * 系统繁忙 可能发生了网络或者系统异常，导致无法判定准确的转账结果。此时，商户不能直接当做转账成功或者失败处理，
		 * 可以考虑采用相同的out_biz_no重发请求，或者通过调用“(alipay.fund.trans.order.query)”
		 * 来查询该笔转账订单的最终状态。
		 */
		public static final String SYSTEM_ERROR = "SYSTEM_ERROR";

		/**
		 * 收款账号不存在
		 */
		public static final String PAYEE_NOT_EXIST = "PAYEE_NOT_EXIST";

		/**
		 * 付款方余额不足
		 */
		public static final String PAYER_BALANCE_NOT_ENOUGH = "PAYER_BALANCE_NOT_ENOUGH";

		/**
		 * 收款账户未实名，单日最多可收款1000元
		 */
		public static final String EXCEED_LIMIT_UNRN_DM_AMOUNT = "EXCEED_LIMIT_UNRN_DM_AMOUNT";
	}

	/**
	 * 单笔转账查询业务错误码
	 */
	public interface TRANSFER_QUERY_SUB_CODE {
		/**
		 * 系统繁忙 支付宝系统繁忙或者处理超时，请稍后重试。
		 */
		public static final String SYSTEM_ERROR = "SYSTEM_ERROR";

		/**
		 * 转账订单不存在
		 */
		public static final String ORDER_NOT_EXIST = "ORDER_NOT_EXIST";
	}
}
