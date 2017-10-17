package com.webside.pay.wechatpay.util;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.JDOMException;

import com.alibaba.fastjson.JSONObject;
import com.webside.common.GlobalConstant;
import com.webside.pay.wechatpay.config.WechatPayConfig;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

public class WechatPayUtil {
	
	/**
     * 获取扫码支付支付路径
	 * @throws IOException 
	 * @throws JDOMException 
     */
    public static JSONObject buildWebPayParams(String id,String udGold,String body,String ip) throws JDOMException, IOException{

    	JSONObject jsonObject = new JSONObject();
    	
    	SortedMap<String, String> payParams = new TreeMap<String,String>();
          
        payParams.put("appid", WechatPayConfig.APP_ID);  
        payParams.put("mch_id", WechatPayConfig.MCH_ID); 
        payParams.put("trade_type", WechatPayConfig.TRADE_TYPE);
        
        // 回调接口，目前觉得由微信端设置为准  
        //根据环境不同取不同的值
        if (GlobalConstant.ENV.equals(GlobalConstant.EnvConstant.DEV)) {
        	payParams.put("notify_url", WechatPayConfig.NotifyUrl.WEB_DEV);
        } else if (GlobalConstant.ENV.equals(GlobalConstant.EnvConstant.TEST)) {
        	payParams.put("notify_url", WechatPayConfig.NotifyUrl.WEB_TEST);
        } else {
        	payParams.put("notify_url", WechatPayConfig.NotifyUrl.WEB_PRD);
        }
        
        // 随机字符串
        payParams.put("nonce_str", IdGen.uuid());  
        // 商品描述
        payParams.put("body", body);  
        // 订单号  
        payParams.put("out_trade_no", id);  
        // 金额，分为单位
        payParams.put("total_fee", udGold);  
        // 发起电脑 ip  
        payParams.put("spbill_create_ip", ip);  
        // 签名
        String sign = PayCommonUtil.createSign("UTF-8", payParams,WechatPayConfig.API_KEY);  
        payParams.put("sign", sign);
        
        String requestXML = PayCommonUtil.getRequestXml(payParams);  
   
        String resXml = HttpUtil.postData(WechatPayConfig.UFDODER_URL, requestXML);  
          
        Map map = XMLUtil.doXMLParse(resXml);  
        String return_code = StringUtils.toString(map.get("return_code"));
        
        if(WechatPayConfig.ReturnCode.SUCCESS.equals(return_code)){
        	String result_code = StringUtils.toString(map.get("result_code"));
        	if(WechatPayConfig.ReturnCode.SUCCESS.equals(result_code)){
        		String urlCode = StringUtils.toString(map.get("code_url"));
            	jsonObject.put("result", true);
            	jsonObject.put("urlCode", urlCode);
        	}else{
        		String err_code_des = StringUtils.toString(map.get("err_code_des"));
        		jsonObject.put("result", false);
            	jsonObject.put("returnMsg", err_code_des);
        	}
        }else{
        	String returnMsg = StringUtils.toString(map.get("return_msg"));
        	jsonObject.put("result", false);
        	jsonObject.put("returnMsg", returnMsg);
        }
        return jsonObject;
    }
}
