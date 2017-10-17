package com.webside.pay.alipay.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webside.base.basecontroller.BaseController;
import com.webside.common.GlobalConstant;
import com.webside.common.redis.listener.SendMessage;
import com.webside.pay.alipay.config.AlipayConfig;
import com.webside.pay.alipay.util.AliPayUtil;
import com.webside.pay.alipay.util.AlipayNotify;
import com.webside.pay.alipay.util.AlipaySubmit;
import com.webside.pay.vo.PayMessageVo;
import com.webside.user.deposit.entities.UserDepositLog;
import com.webside.user.deposit.service.IUserDepositLogService;
import com.webside.user.task.service.IUserTaskService;
import com.webside.user.wallet.entities.UserWallet;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.DateUtils;
import com.webside.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/pay/alipay")
public class AliPayCtrl extends BaseController{

	/**
	 * 充值记录 Service定义
	 */
	@Autowired
	private IUserDepositLogService userDepositLogService;
	
	/**
	 * 用户钱包 Service定义
	 */
	@Autowired
	private IUserWalletService userWalletService;
	
	/**
	 * 消息队列 定义
	 */
	@Autowired
	private SendMessage sendMessage;
	@Autowired
	private IUserTaskService userTaskService;
	
	/**
	 * 跳转支付页面
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/pay_web")
	public void webPay(HttpServletRequest request,HttpServletResponse response,@RequestParam("i")String id,@RequestParam("o")String orderName,@RequestParam("u")String udGold,@RequestParam("b")String body){
		try {
			Map<String, String> sParaTemp = AliPayUtil.buildWebPayParams(id,orderName,udGold,body);
			String form = AlipaySubmit.buildRequest(sParaTemp,"post","确认");
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write(form);
		} catch (Exception e) {
			logger.error("alipay:web支付:跳转支付页面出错：", e);
		}
	}
	
	/**
	 * alipay的回调
	 * @author zengxn
	 */
	@RequestMapping(value="/notify_web")
	public void notify(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		//商户订单号
		String outTradeNo = params.get("out_trade_no");
		//支付宝交易号
		String tradeNo = params.get("trade_no");
		//交易状态
		String tradeStatus = params.get("trade_status");
		//交易付款时间
		String gmtPayment = params.get("gmt_payment");
		//交易金额
		String totalFee = params.get("total_fee");
		
		try {
			if(AlipayNotify.verify(params)){
				//验证成功
				if(tradeStatus.equals(AlipayConfig.TradeStatus.SUCCESS)){
					UserDepositLog entity = userDepositLogService.findById(outTradeNo);
					if(entity!=null){
						if(!entity.getIsPaySuccess().equals(GlobalConstant.DICTTYPE_YES_NO_1)){
							//未完成支付
							
							//----------根据全局变量区分生产和测试环境----start-----
							if(GlobalConstant.ENV.equals(GlobalConstant.EnvConstant.PRD)){
								//判断金额是否一致，以回调金额为准
								if(entity.getUdGold().longValue()!=StringUtils.toLong(totalFee).longValue()){
									entity.setUdGold(StringUtils.toLong(totalFee));
								}
							}
							//----------根据全局变量区分生产和测试环境----end-----
							
							entity.setPayTime(DateUtils.getStringDate(gmtPayment).getTime());
							entity.setIsPaySuccess(GlobalConstant.DICTTYPE_YES_NO_1);
							entity.setOrderNo(tradeNo);
							userDepositLogService.update(entity);
							//查询最新钱包值
							UserWallet wallet = userWalletService.findWalletByUserId(entity.getUserId());
							//发送消息
							sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new PayMessageVo(outTradeNo, GlobalConstant.DICTTYPE_YES_NO_1,wallet!=null?wallet.getGold():0,wallet!=null?wallet.getSysGoldNum():0));
							// 充值任务
							userTaskService.updateUserTask(entity.getUserId(), GlobalConstant.USER_TASK_TYPE_CHARGE_8, null);
						}
					}else{
						//发送消息
						sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new PayMessageVo(outTradeNo, GlobalConstant.DICTTYPE_YES_NO_0));
						logger.error("alipay:web支付出错：订单["+outTradeNo+"]不存在");
						response.getWriter().write(AlipayConfig.Return.FAIL);
					}
				}
				response.getWriter().write(AlipayConfig.Return.SUCCESS);
			}else{
				//发送消息
				sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new PayMessageVo(outTradeNo, GlobalConstant.DICTTYPE_YES_NO_0));
				logger.error("alipay:web支付出错：订单["+outTradeNo+"]返回信息验证失败");
				response.getWriter().write(AlipayConfig.Return.FAIL);
			}
		} catch (Exception e) {
			logger.error("alipay:web支付出错：", e);
			//发送消息
			sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new PayMessageVo(outTradeNo, GlobalConstant.DICTTYPE_YES_NO_0));
			try {
				response.getWriter().write(AlipayConfig.Return.FAIL);
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
		}
	}
}
