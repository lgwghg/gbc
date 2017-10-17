package com.webside.pay.wechatpay.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.GlobalConstant;
import com.webside.common.redis.listener.SendMessage;
import com.webside.pay.vo.PayMessageVo;
import com.webside.pay.wechatpay.config.WechatPayConfig;
import com.webside.pay.wechatpay.util.PayCommonUtil;
import com.webside.pay.wechatpay.util.WechatPayUtil;
import com.webside.pay.wechatpay.util.XMLUtil;
import com.webside.user.deposit.entities.UserDepositLog;
import com.webside.user.deposit.service.IUserDepositLogService;
import com.webside.user.task.service.IUserTaskService;
import com.webside.user.wallet.entities.UserWallet;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.DateUtils;
import com.webside.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/pay/wechatpay")
public class WechatPayCtrl extends BaseController{

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
	 * 获取支付二维码路径
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/pay_web")
	@ResponseBody
	public String webPay(HttpServletRequest request,HttpServletResponse response,@RequestParam("i")String id,@RequestParam("u")String udGold,@RequestParam("b")String body){
		String result = null;
		try {
			String ip = SecurityUtils.getSubject().getSession().getHost();
			//----------根据全局变量区分生产和测试环境----start-----
			udGold = GlobalConstant.ENV.equals(GlobalConstant.EnvConstant.PRD)?StringUtils.toString(StringUtils.toLong(udGold)*100):udGold;
			//----------根据全局变量区分生产和测试环境----end-----
			JSONObject buildWebPayParams = WechatPayUtil.buildWebPayParams(id, udGold, body, ip);
			if(buildWebPayParams.getBooleanValue("result")){
				result = buildWebPayParams.getString("urlCode");
			}else{
				logger.error("wechatpay:web支付:获取支付二维码出错："+buildWebPayParams.getString("returnMsg"));
			}
		} catch (Exception e) {
			logger.error("wechatpay:web支付:获取支付二维码出错：", e);
		}
		return result;
	}
	
	/**
	 * wechatpay的回调
	 * @author zengxn
	 */
	@RequestMapping(value="/notify_web")
	public void notify(HttpServletRequest request,HttpServletResponse response){
		
		System.out.println(1234);
		
		//解析xml成map  
        Map<String, String> m = new HashMap<String, String>();
        
        //读取参数  
        try {
        	InputStream inputStream ;  
            StringBuffer sb = new StringBuffer();  
            inputStream = request.getInputStream();  
            String s ;  
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
            while ((s = in.readLine()) != null){  
                sb.append(s);  
            }  
            in.close();  
            inputStream.close();  
      
              
            m = XMLUtil.doXMLParse(sb.toString());  
		} catch (Exception e) {
			// TODO: handle exception
		}
          
        //过滤空 设置 TreeMap  
        SortedMap<String,String> packageParams = new TreeMap<String,String>();        
        Iterator it = m.keySet().iterator();  
        while (it.hasNext()) {  
            String parameter = StringUtils.toString(it.next()) ;  
            String parameterValue = m.get(parameter);  
              
            String v = "";  
            if(null != parameterValue) {  
                v = parameterValue.trim();  
            }  
            packageParams.put(parameter, v);  
        }  
          
        String timeEnd = packageParams.get("time_end");  
        String outTradeNo = packageParams.get("out_trade_no");  
        String transactionId = packageParams.get("transaction_id");   
        String totalFee = packageParams.get("total_fee");  
          
        String resXml = "";  
        
		try {
			//判断签名是否正确  
	        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams,WechatPayConfig.API_KEY)) {  
	            //------------------------------  
	            //处理业务开始  
	            //------------------------------  
	            if(WechatPayConfig.ReturnCode.SUCCESS.equals(StringUtils.toString(packageParams.get("result_code")))){  
	                // 这里是支付成功  
	                
	                UserDepositLog entity = userDepositLogService.findById(outTradeNo);
					if(entity!=null){
						if(!entity.getIsPaySuccess().equals(GlobalConstant.DICTTYPE_YES_NO_1)){
							//未完成支付
							
							//----------根据全局变量区分生产和测试环境----start-----
							if(GlobalConstant.ENV.equals(GlobalConstant.EnvConstant.PRD)){
								//判断金额是否一致，以回调金额为准
								if(entity.getUdGold().longValue()*100!=StringUtils.toLong(totalFee).longValue()){
									entity.setUdGold(StringUtils.toLong(totalFee).longValue()/100);
								}
							}
							//----------根据全局变量区分生产和测试环境----end-----
							
							entity.setPayTime(DateUtils.getStringDate(timeEnd,DateUtils._DEFAULT4).getTime());
							entity.setIsPaySuccess(GlobalConstant.DICTTYPE_YES_NO_1);
							entity.setOrderNo(transactionId);
							userDepositLogService.update(entity);
							//查询最新钱包值
							UserWallet wallet = userWalletService.findWalletByUserId(entity.getUserId());
							//发送消息
							sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new PayMessageVo(outTradeNo, GlobalConstant.DICTTYPE_YES_NO_1,wallet!=null?wallet.getGold():0,wallet!=null?wallet.getSysGoldNum():0));
							// 充值任务
							userTaskService.updateUserTask(entity.getUserId(), GlobalConstant.USER_TASK_TYPE_CHARGE_8, null);
						}
						//通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
		                resXml = WechatPayConfig.ReturnXml.OK;
					}else{
						//发送消息
						sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new PayMessageVo(outTradeNo, GlobalConstant.DICTTYPE_YES_NO_0));
						logger.error("wechatpay:web支付出错：订单["+outTradeNo+"]不存在");
						resXml = WechatPayConfig.ReturnXml.NOT_ORDER;
					}
	            } else {  
	            	//发送消息
					sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new PayMessageVo(outTradeNo, GlobalConstant.DICTTYPE_YES_NO_0));
	                String returnMsg = StringUtils.toString(packageParams.get("return_msg"));
	                logger.error("wechatpay:web支付出错：订单["+outTradeNo+"]"+returnMsg);
	                resXml = WechatPayConfig.ReturnXml.NO_START+returnMsg+WechatPayConfig.ReturnXml.NO_END;
	            }  
	            //------------------------------  
	            //处理业务完毕  
	            //------------------------------  
	            BufferedOutputStream out = new BufferedOutputStream(  
	                    response.getOutputStream());
				out.write(resXml.getBytes());
				out.flush();  
	            out.close();
	        } else{  
	        	logger.error("wechatpay:web支付出错：订单["+outTradeNo+"]返回信息验证失败");
	        }  
		} catch (Exception e) {
			logger.error("wechatpay:web支付出错：", e);
			//发送消息
			sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new PayMessageVo(outTradeNo, GlobalConstant.DICTTYPE_YES_NO_0));
			try {
				resXml = WechatPayConfig.ReturnXml.ERRO; 
				BufferedOutputStream out = new BufferedOutputStream(  
	                    response.getOutputStream());
				out.write(resXml.getBytes());
				out.flush();  
	            out.close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
		}
	}
}
