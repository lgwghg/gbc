package com.webside.user.wallet.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.common.GlobalConstant;
import com.webside.jc.service.IUserJcService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.deposit.service.IUserDepositLogService;
import com.webside.user.wallet.entities.GiveGoldRate;
import com.webside.user.wallet.entities.UserWallet;
import com.webside.user.wallet.service.IGiveGoldRateService;
import com.webside.user.wallet.service.IUserWalletService;

/**
 * 用户钱包Controller
 * 
 * @author tianguifang
 * @date 2016-11-24 15:27:02
 */

@Controller
@RequestMapping("/my/wallet")
public class UserWalletCtr {
	
	public static final Log log = LogFactory.getLog(UserWalletCtr.class);
	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	private IUserJcService userJcService;
	@Autowired
	private IGiveGoldRateService giveGoldRateService;
	@Autowired
	private IUserDepositLogService userDepositLogService;
	/**
	 * 你的钱包金额
	 * @return
	 */
	@RequestMapping(value = "/gold", method = RequestMethod.POST)
	@ResponseBody
	public Object wallet() {
		Map<String, Object> param = new HashMap<String, Object>();
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		try {
			UserWallet wallet = userWalletService.findWalletByUserId(userId);
			if (wallet != null) {
				Long gold = wallet.getGold() == null ? 0L : wallet.getGold();
				Long sysGold = wallet.getSysGoldNum() == null ? 0L : wallet.getSysGoldNum();
				param.put("gold", gold);
				param.put("sysGold", sysGold);
				param.put("totalGold", gold + sysGold);
			}
		} catch (Exception e) {
			log.error("获取用户钱包金币出错", e);
		}
		try {
			String jcGold = userJcService.getUserJcGold(userId);
			if (StringUtils.isBlank(jcGold)) {
				jcGold = "0";
			}
			param.put("jcGold", jcGold);
		} catch (Exception e) {
			log.error("获取用户正在竞猜的金币出错", e);
		}
		try {
			String wdGold = userDepositLogService.getUserWdGold(userId);
			if (StringUtils.isBlank(wdGold)) {
				wdGold = "0";
			}
			param.put("wdGold", wdGold);
		} catch (Exception e) {
			log.error("获取用户提现的金币出错", e);
		}
		return param;
	}

	/**
	 * 充值,传入参数，充值金额和支付类型 0：微信支付，，1：支付宝支付
	 * 
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	@ResponseBody
	public Object recharge(Long amount, Integer payType) {
		// Map<String, Object> param = new HashMap<String, Object>();
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		Integer result = userWalletService.rechargeWallet(userId, amount, GlobalConstant.RECHARGE_TYPE_RECHARGE);
		return result;
	}

	@RequestMapping(value = "/nl/rechargeAmount", method = RequestMethod.POST)
	@ResponseBody
	public Object rechargeAmountList() {
		List<GiveGoldRate> goldRateList = giveGoldRateService.queryListByPage(null);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("goldRateList", goldRateList);
		return resultMap;
	}

	/**
	 * 充值赠送G币数量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/giveGold", method = RequestMethod.POST)
	@ResponseBody
	public Object giveGold(Long amount) {
		Long giveGold = 0L;
		if (amount != null && amount > 10) {
			Integer goldRate = giveGoldRateService.queryGiveRateByAmount(amount);
			if (goldRate != null && goldRate > 0) {
				Double give = (amount.longValue() * GlobalConstant.GOLD_CASH_RATIO * (goldRate.intValue() * 0.01));
				giveGold = give.longValue();
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("giveGold", giveGold);
		return resultMap;
	}
}
