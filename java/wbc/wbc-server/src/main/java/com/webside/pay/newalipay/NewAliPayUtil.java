package com.webside.pay.newalipay;

import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.webside.common.GlobalConstant;

/**
 * alipay工具类 对应2017-04-05版api
 * 
 * @author: zengxn
 */
public class NewAliPayUtil {
	
	private static Logger logger = Logger.getLogger(NewAliPayUtil.class);

	private static AlipayClient alipayClient;
	
	static{
		alipayClient = new DefaultAlipayClient(NewAlipayConfig.GATEWAYURL, NewAlipayConfig.APP_ID, NewAlipayConfig.MERCHANT_PRIVATE_KEY, NewAlipayConfig.FORMAT, NewAlipayConfig.CHARSET, NewAlipayConfig.ALIPAY_PUBLIC_KEY, NewAlipayConfig.SIGN_TYPE);
	}

	/**
	 * 发送转账请求，获得转账结果
	 * @param id	提现id
	 * @param payeeAccount	收款账号
	 * @param udGold	转账金额
	 * @param remark	转账备注
	 * @return
	 * @throws AlipayApiException 
	 */
	public static AlipayFundTransToaccountTransferResponse getTransferResult(String id, String payeeAccount, String udGold, String remark) throws AlipayApiException {
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		//非正是环境，金额都是0.1
		if(!GlobalConstant.EnvConstant.PRD.equals(GlobalConstant.ENV)){
			udGold = "0.1";
		}
		request.setBizContent("{" +
				"\"out_biz_no\":\""+id+"\"," +
				"\"payee_type\":\"ALIPAY_LOGONID\"," +
				"\"payee_account\":\""+payeeAccount+"\"," +
				"\"amount\":\""+udGold+"\"," +
				"\"remark\":\""+remark+"\"" +
				"  }");
		AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
		logger.info("转账结果：" + response.getBody());
		return response;

	}
	
	/**
	 * 发送查询转账请求，获得转账订单结果
	 * @param id	提现id
	 * @param payeeAccount	收款账号
	 * @param udGold	转账金额
	 * @param remark	转账备注
	 * @return
	 * @throws AlipayApiException 
	 */
	public static AlipayFundTransOrderQueryResponse getTransferQueryResult(String id) throws AlipayApiException {
		AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
		request.setBizContent("{" +
		"\"out_biz_no\":\""+id+"\"," +
		"  }");
		AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
		logger.info("转账详情结果：" + response.getBody());
		return response;
	}
	
	
	
	public static void main(String[] args) {
		/*try {
			AlipayFundTransToaccountTransferResponse transferResult = getTransferResult("1234", "13", "0.1", "123");
			System.out.println(transferResult.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		try {
			AlipayFundTransOrderQueryResponse transferResult = getTransferQueryResult("1234");
			System.out.println(transferResult.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
