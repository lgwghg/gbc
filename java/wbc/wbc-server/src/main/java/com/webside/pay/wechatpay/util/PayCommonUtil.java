package com.webside.pay.wechatpay.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.webside.util.StringUtils;

public class PayCommonUtil {
	/**
	 * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @return boolean
	 */
	public static boolean isTenpaySign(String characterEncoding, SortedMap<String, String> packageParams, String API_KEY) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = StringUtils.toString(entry.getKey());
			String v = StringUtils.toString(entry.getValue());
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		
		sb.append("key=" + API_KEY);
		
		//算出摘要
		String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
		String tenpaySign = packageParams.get("sign").toLowerCase();
		
		return tenpaySign.equals(mysign);
	}

	/**
	 * @Description：sign签名
	 * @param characterEncoding
	 *            编码格式
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	public static String createSign(String characterEncoding, SortedMap<String, String> packageParams, String API_KEY) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = StringUtils.toString(entry.getKey());
			String v = StringUtils.toString(entry.getValue());
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + API_KEY);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	/**
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	public static String getRequestXml(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = StringUtils.toString(entry.getKey());
			String v = StringUtils.toString(entry.getValue());
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
}
