package com.webside.pay.alipay.util;

import java.util.HashMap;
import java.util.Map;

import com.webside.common.GlobalConstant;
import com.webside.pay.alipay.config.AlipayConfig;

public class AliPayUtil {
    
	/**
     * 构建web支付参数
     */
    public static Map<String, String> buildWebPayParams(String id,String orderName,String udGold,String body){

    	Map<String, String> payParams = new HashMap<>();

    	payParams.put("service", AlipayConfig.Service.WEB_PAY);
    	payParams.put("partner", AlipayConfig.PARTNER);
    	payParams.put("seller_id", AlipayConfig.SELLER_ID);
    	payParams.put("_input_charset", AlipayConfig.InputCharset.UTF8);
        payParams.put("payment_type", AlipayConfig.PAYMENT_TYPE);
        
        //根据环境不同取不同的值
        if (GlobalConstant.ENV.equals(GlobalConstant.EnvConstant.DEV)) {
        	payParams.put("notify_url", AlipayConfig.NotifyUrl.WEB_DEV);
            payParams.put("return_url", AlipayConfig.ReturnUrl.WEB_DEV);
        } else if (GlobalConstant.ENV.equals(GlobalConstant.EnvConstant.TEST)) {
        	payParams.put("notify_url", AlipayConfig.NotifyUrl.WEB_TEST);
            payParams.put("return_url", AlipayConfig.ReturnUrl.WEB_TEST);
        } else {
        	payParams.put("notify_url", AlipayConfig.NotifyUrl.WEB_PRD);
            payParams.put("return_url", AlipayConfig.ReturnUrl.WEB_PRD);
        }
        
        payParams.put("anti_phishing_key", AlipayConfig.Fishing.ANTI_PHISHING_KEY);
        payParams.put("exter_invoke_ip", AlipayConfig.Fishing.EXTER_INVOKE_IP);
		//商户订单号，商户网站订单系统中唯一订单号，必填
        payParams.put("out_trade_no", id);
		//订单名称，必填
        payParams.put("subject", orderName);
		//付款金额，必填
        payParams.put("total_fee", udGold);
		//商品描述，可空
        payParams.put("body", body);

        return payParams;
        
    }
}
