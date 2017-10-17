package com.webside.pay.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.common.GlobalConstant;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.deposit.entities.UserDepositLog;
import com.webside.user.deposit.service.IUserDepositLogService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/pay")
public class PayCtrl extends BaseController{

	/**
	 * 充值记录 Service定义
	 */
	@Autowired
	private IUserDepositLogService userDepositLogService;
	
	/**
	 * web支付
	 * @author zengxn
	 */
	@RequestMapping(value="/web")
	@ResponseBody
	public Object web(@RequestParam("money")Long money,@RequestParam("type")String type){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			//初始化充值记录对象
			UserDepositLog entity = new UserDepositLog();
			entity.setId(IdGen.uuid());
			entity.setUserId(ShiroAuthenticationManager.getUserId());
			entity.setOrderName("充值"+money+"元金币");
			entity.setType(GlobalConstant.PAY_TYPE_1);
			if(type.equals("2")){
				entity.setType(GlobalConstant.PAY_TYPE_2);	
			}
			entity.setUdGold(money);
			entity.setOrderTime(StringUtils.toString(System.currentTimeMillis()));
			entity.setIsPaySuccess(GlobalConstant.DICTTYPE_YES_NO_0);
			
			int insert = userDepositLogService.insert(entity );
			if(insert==1){
				parameters.put("isSuccess", Boolean.TRUE);
				parameters.put("i", entity.getId());
				parameters.put("o", entity.getOrderName());
				
				//----------根据全局变量区分生产和测试环境----start-----
				if(GlobalConstant.ENV.equals(GlobalConstant.EnvConstant.PRD)){
					//生产环境
					parameters.put("u", entity.getUdGold());
				}else{
					//其它环境
					if(type.equals("2")){
						parameters.put("u", 1);
					}else{
						parameters.put("u", 0.01);
					}
				}
				//----------根据全局变量区分生产和测试环境----end-----
				
				parameters.put("b", entity.getOrderName());
				if(type.equals("2")){
					parameters.put("url", "/pay/wechatpay/pay_web");
				}else{
					parameters.put("url", "/pay/alipay/pay_web");
				}
			}
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			parameters.put("message", "支付操作出错，请联系客服人员");
			logger.error("ajax跳转支付出错：", e);
		}
		return parameters;
	}
}
