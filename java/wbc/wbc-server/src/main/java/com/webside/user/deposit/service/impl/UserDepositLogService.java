/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.deposit.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.user.deposit.entities.UserDepositLog;
import com.webside.user.deposit.mapper.IUserDepositLogMapper;
import com.webside.user.deposit.service.IUserDepositLogService;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IGiveGoldRateService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 充值记录服务实现类
 * 
 * @author tianguifang
 * @date 2016-11-24 16:21:01
 */
@Service("userDepositLogService")
public class UserDepositLogService extends AbstractService<UserDepositLog, String> implements IUserDepositLogService {
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(userDepositLogMapper);
	}

	/**
	 * 充值记录 DAO定义
	 */
	@Autowired
	private IUserDepositLogMapper userDepositLogMapper;

	/**
	 * 交易记录 Service定义
	 */
	@Autowired
	private IUserTransactionLogService userTransactionLogService;

	/**
	 * 用户钱包 Service定义
	 */
	@Autowired
	private IUserWalletService userWalletService;

	/**
	 * 赠送G币比率 Service定义
	 */
	@Autowired
	private IGiveGoldRateService giveGoldRateService;

	/**
	 * 用户通知 Service定义
	 */
	@Autowired
	private IUserMessageService userMessageService;
	@Autowired
	private IUserCacheService userCacheService;

	@Override
	public List<UserDepositLog> queryListByPage(Map<String, Object> parameter) {
		List<UserDepositLog> logList = userDepositLogMapper.queryListByPage(parameter);
		if (CollectionUtils.isNotEmpty(logList)) {
			for (UserDepositLog log : logList) {
				UserEntity user = userCacheService.getUserEntityByUserId(log.getUserId());
				log.setNickName(user.getNickName());
			}
		}
		return logList;
	}

	@Override
	public int update(UserDepositLog entity) {
		int result = 0;
		try {
			result = super.update(entity);
			if (result == 1) {

				// 修改钱包
				userWalletService.rechargeWallet(entity.getUserId(), entity.getUdGold(), GlobalConstant.RECHARGE_TYPE_RECHARGE);

				// 查询奖励比例
				Integer num = giveGoldRateService.queryGiveRateByAmount(entity.getUdGold());

				// G币数量
				long money = entity.getUdGold() * GlobalConstant.GOLD_CASH_RATIO;
				// 赠送G币数量
				long goldMoney = 0;
				if (num != null && num.intValue() > 0) {
					Double d = money * ((double) num / 100);
					goldMoney = d.longValue();
				}

				// 交易记录
				UserTransactionLog utl = new UserTransactionLog();
				utl.setId(IdGen.uuid());
				utl.setUserId(entity.getUserId());
				utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_2);
				utl.setDataId(entity.getId());
				utl.setGoldNum(money);
				utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
				utl.setUtTime(System.currentTimeMillis() + "");
				utl.setRemarks("充值金币");
				userTransactionLogService.insert(utl);
				if (goldMoney > 0) {
					utl = new UserTransactionLog();
					utl.setId(IdGen.uuid());
					utl.setUserId(entity.getUserId());
					utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_6);
					utl.setDataId(entity.getId());
					utl.setGoldNum(goldMoney);
					utl.setGoldType(GlobalConstant.GOLD_TYPE_2);
					utl.setUtTime(System.currentTimeMillis() + "");
					utl.setRemarks("充值奖励");
					userTransactionLogService.insert(utl);
				}

				// 用户消息
				if (goldMoney > 0) {
					userMessageService.addMessageForDeposit(entity.getUserId(), entity.getId(), money, goldMoney);
				} else {
					userMessageService.addMessageForDeposit(entity.getUserId(), entity.getId(), money);
				}
			}
		} catch (Exception e) {
			logger.error("修改充值记录出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public void executeClearLog() {
		userDepositLogMapper.executeClearLog();
	}

	/**
	 * 提现更新
	 */
	public int updateForWithdraw(UserDepositLog entity) {
		if (entity == null || entity.getId() == null) {
			throw new RuntimeException("请确定ID不能为空");
		}
		UserDepositLog depositLog = this.findById(entity.getId());
		String status = depositLog.getIsPaySuccess();
		if ("1".equals(status)) {// 提现成功
			throw new RuntimeException("已提现成功不能修改~");
		}

		if (StringUtils.isNotBlank(entity.getOrderNo())) {// 有交易单号，就交易成功
			entity.setIsPaySuccess("1");
		} else if ("-1".equals(entity.getIsPaySuccess()) && StringUtils.isBlank(entity.getRemarks())) {// 提现失败
			throw new RuntimeException("请录入提现失败的原因");
		} else if ("1".equals(entity.getIsPaySuccess()) && StringUtils.isBlank(entity.getOrderNo())) {
			throw new RuntimeException("请录入交易单号");
		}
		entity.setPayTime(System.currentTimeMillis());
		int count = userDepositLogMapper.update(entity);

		status = entity.getIsPaySuccess();
		String userId = entity.getUserId();
		Long gold = entity.getUdGold();
		if ("1".equals(status)) {// 提现成功

		} else {// 提现失败
			// 退款
			count = userWalletService.rechargeWallet(userId, gold, GlobalConstant.RECHARGE_TYPE_JC);
			if (1 == count) {// 退款成功
				// 交易记录
				UserTransactionLog utl = new UserTransactionLog();
				utl.setId(IdGen.uuid());
				utl.setUserId(entity.getUserId());
				utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_9);
				utl.setDataId(entity.getId());
				utl.setGoldNum(entity.getUdGold());
				utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
				utl.setUtTime(StringUtils.toString(System.currentTimeMillis()));
				utl.setRemarks("提现失败：" + entity.getRemarks());
				userTransactionLogService.insert(utl);
			}
		}

		// 通知发送失败，不影响数据更新
		try {
			userMessageService.addMessageForWithdrawals(userId, entity.getId(), status, gold, entity.getRemarks());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	// 保存提现记录
	public int insertForWithdraw(UserDepositLog entity, Long factorage) {
		long gold = entity.getUdGold()*GlobalConstant.GOLD_CASH_RATIO;
		entity.setOrderName("提现 " + gold + " 金币");
		entity.setType(GlobalConstant.PAY_TYPE_0);

		int count = userDepositLogMapper.insert(entity);
		if (1 != count) {
			throw new RuntimeException("提现记录 保存失败");
		}
		// 扣款
		Long totalGold = factorage + gold;
		JSONObject jsonObject = userWalletService.deductWallet(entity.getUserId(), totalGold, GlobalConstant.DEDUCT_TYPE_EXCHANGE);
		count = jsonObject.getIntValue("result");
		if (1 != count) {
			throw new RuntimeException("提现冻结金币失败：" + count);
		}
		// 交易记录
		UserTransactionLog utl = new UserTransactionLog();
		utl.setId(IdGen.uuid());
		utl.setUserId(entity.getUserId());
		utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_9);
		utl.setDataId(entity.getId());
		utl.setGoldNum(-1 * gold);
		utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
		utl.setUtTime(StringUtils.toString(System.currentTimeMillis()));
		utl.setRemarks("提现" + gold + "金币");
		count = userTransactionLogService.insert(utl);

		utl = new UserTransactionLog();
		utl.setId(IdGen.uuid());
		utl.setUserId(entity.getUserId());
		utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_9);
		utl.setDataId(entity.getId());
		utl.setGoldNum(-1 * factorage);
		utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
		utl.setUtTime(StringUtils.toString(System.currentTimeMillis()));
		utl.setRemarks("手续费" + factorage + "金币");
		count = userTransactionLogService.insert(utl);

		return count;
	}

	public String getUserWdGold(String userId) {

		return userDepositLogMapper.getUserWdGold(userId);
	}

	/**
	 * 只修改充值记录
	 * @param entity
	 * @author zengxiangneng
	 */
	@Override
	public int updateEntityById(UserDepositLog entity) {
		entity.setPayTime(System.currentTimeMillis());
		return userDepositLogMapper.update(entity);
	}

}
