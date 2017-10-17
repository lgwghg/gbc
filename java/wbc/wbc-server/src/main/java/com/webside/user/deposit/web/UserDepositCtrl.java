package com.webside.user.deposit.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.GlobalConstant;
import com.webside.pay.newalipay.NewAliPayUtil;
import com.webside.pay.newalipay.NewAlipayConfig;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.deposit.entities.UserDepositLog;
import com.webside.user.deposit.service.IUserDepositLogService;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.user.wallet.entities.UserWallet;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 充值、提现记录Controller
 * 
 * @author tianguifang
 * @date 2016-12-14
 */

@Controller
@RequestMapping("/depositLog/")
public class UserDepositCtrl extends BaseController {
	/**
	 * 充值记录 Service定义
	 */
	@Autowired
	private IUserDepositLogService userDepositLogService;

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 用户钱包 Service定义
	 */
	@Autowired
	private IUserWalletService userWalletService;

	/**
	 * 用户通知 Service定义
	 */
	@Autowired
	private IUserMessageService userMessageService;

	/**
	 * 用户提现申请
	 * 
	 * @return
	 */
	@RequestMapping(value = "addForWithdraw", method = RequestMethod.POST)
	@ResponseBody
	public Object addForWithdraw(Long gold) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", Boolean.FALSE);
		try {
			String userId = ShiroAuthenticationManager.getUserId();
			if (StringUtils.isEmpty(userId)) {
				map.put("message", "提现失败,请先登录");
				return map;
			}
			if (gold == null || gold.longValue() < 1000) {
				map.put("message", "提现失败,最低不得少于1000金币");
				return map;
			}
			// 提现金币数量
			gold = gold - gold % 100; // 保留整数
			// 提现手续费
			Long factorage = getFactorage(gold);
			UserWallet userWallet = userWalletService.findWalletByUserId(userId);
			long uGold = userWallet.getGold() != null ? userWallet.getGold() : 0L;
			if (uGold < (gold.longValue() + factorage.longValue())) {
				map.put("message", "提现失败,提现金币数量超出最大值");
				return map;
			}
			UserEntity user = userService.findById(userId);
			// 交易记录id，先转账后保存转账记录
			UserDepositLog entity = new UserDepositLog(IdGen.uuid());
			entity.setUserId(userId);
			entity.setUdGold(gold / GlobalConstant.GOLD_CASH_RATIO);
			entity.setOrderTime(StringUtils.toString(System.currentTimeMillis()));
			// 转账
			AlipayFundTransToaccountTransferResponse transferResult = NewAliPayUtil.getTransferResult(entity.getId(), user.getAlipayAccount(), StringUtils.toString(gold / GlobalConstant.GOLD_CASH_RATIO), "G菠菜提现" + gold + "金币");
			if (transferResult.isSuccess()) {
				entity.setIsPaySuccess("1");
				// 查询转账详情，存入支付宝订单号、支付时间
				// 无论是否查询到转账详情，只要转账成功，都属于提现成功
				AlipayFundTransOrderQueryResponse transferQueryResult = NewAliPayUtil.getTransferQueryResult(entity.getId());
				if (transferQueryResult.isSuccess()) {
					entity.setOrderNo(transferQueryResult.getOrderId());
					entity.setPayTime(DateUtils.getStringDate(transferQueryResult.getPayDate()).getTime());
				}
				save(gold, factorage, userId, entity);
				map.put("success", Boolean.TRUE);
				map.put("message", "提现成功");
			} else if (NewAlipayConfig.TRANSFER_SUB_CODE.SYSTEM_ERROR.equals(transferResult.getSubCode())) {
				// 系统繁忙，查询转账记录,如果成功代表前面的转账是成功的
				AlipayFundTransOrderQueryResponse transferQueryResult = NewAliPayUtil.getTransferQueryResult(entity.getId());
				if (transferQueryResult.isSuccess()) {
					entity.setIsPaySuccess("1");
					entity.setOrderNo(transferQueryResult.getOrderId());
					entity.setPayTime(DateUtils.getStringDate(transferQueryResult.getPayDate()).getTime());
					save(gold, factorage, userId, entity);
					map.put("success", Boolean.TRUE);
					map.put("message", "提现成功");
				} else {
					// 系统繁忙失败，由客服处理
					entity.setIsPaySuccess("-1");
					entity.setRemarks(transferQueryResult.getSubMsg());
					save(gold, factorage, userId, entity);
					map.put("message", "提现失败," + transferQueryResult.getSubMsg() + ",请联系客服");
				}
			} else {
				if (NewAlipayConfig.TRANSFER_SUB_CODE.PAYEE_NOT_EXIST.equals(transferResult.getSubCode()) || NewAlipayConfig.TRANSFER_SUB_CODE.EXCEED_LIMIT_UNRN_DM_AMOUNT.equals(transferResult.getSubCode())) {
					// 收款账号不存在/收款账户未实名，单日最多可收款1000元，需要做退回金币处理
					map.put("message", "提现失败," + transferResult.getSubMsg());
				} else if (NewAlipayConfig.TRANSFER_SUB_CODE.PAYER_BALANCE_NOT_ENOUGH.equals(transferResult.getSubCode())) {
					// 付款方余额不足
					map.put("message", "提现失败00,请联系客服");
				} else {
					// 其它失败原因，返回支付宝的错误描述
					map.put("message", "提现失败," + transferResult.getSubMsg() + ",请联系客服");
				}
			}
		} catch (Exception e) {
			map.put("message", "提现失败:" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 计算手续费
	 */
	private Long getFactorage(Long gold) {
		// 手续费
		long factorage = gold / 1000;
		if (factorage < 10) {
			factorage = 10;
		}
		return factorage;
	}

	/**
	 * 执行提现成功操作
	 */
	private void save(Long gold, Long factorage, String userId, UserDepositLog entity) {
		// 生成提现记录、更新钱包，生成交易记录
		int result = userDepositLogService.insertForWithdraw(entity, factorage);
		if (result > 0) {
			// 通知发送失败，不影响数据更新
			try {
				userMessageService.addMessageForWithdrawals(userId, entity.getId(), entity.getIsPaySuccess(), gold, entity.getRemarks());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// 手续费
		long factorage = 4842600 / 1000;
		if (factorage < 10) {
			factorage = 10;
		}
		System.out.println(factorage);
	}
}
