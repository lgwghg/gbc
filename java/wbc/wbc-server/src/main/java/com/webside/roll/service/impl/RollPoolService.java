/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.roll.mapper.IRollPoolMapper;
import com.webside.roll.model.RollPool;
import com.webside.roll.service.IRollPoolService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.exchange.service.IUserExchangeLogService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 奖品池服务实现类
 * 
 * @author zhangfei
 * @date 2017-04-18 11:05:18
 */
@Service("rollPoolService")
public class RollPoolService extends AbstractService<RollPool, String> implements IRollPoolService {
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(rollPoolMapper);
	}

	/**
	 * 奖品池 DAO定义
	 */
	@Autowired
	private IRollPoolMapper rollPoolMapper;
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
	@Autowired
	private IUserExchangeLogService exchangeLogService;

	public void insertBatch(String roomId, Long gold, int num, String exchangeIds) {
		if (StringUtils.isBlank(roomId)) {
			throw new RuntimeException("请先确定房间");
		}
		if (gold != null && gold > 100) {// 金币最少100
			num = num < 1 ? 1 : num;
			for (int i = 0; i < num; i++) {
				RollPool entity = saveRollPool(roomId, gold, "");
				// 钱包扣款
				String userId = ShiroAuthenticationManager.getUserId();
				JSONObject jsonObject = userWalletService.deductWallet(userId, gold, GlobalConstant.DEDUCT_TYPE_EXCHANGE);
				int count = jsonObject.getIntValue("result");
				if (1 != count) {
					throw new RuntimeException("金币生成礼品冻结金币失败：" + count);
				}
				// 交易记录
				UserTransactionLog utl = new UserTransactionLog();
				utl.setId(IdGen.uuid());
				utl.setUserId(userId);
				utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_12);// roll奖品
				utl.setDataId(entity.getId());
				utl.setGoldNum(-1 * gold);
				utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
				utl.setUtTime(StringUtils.toString(System.currentTimeMillis()));
				utl.setRemarks("roll奖品" + gold + "金币");
				userTransactionLogService.insert(utl);
			}
		} else {// CD-KEY
			if (StringUtils.isBlank(exchangeIds)) {
				throw new RuntimeException("奖品不能为空");
			}
			List<String> exchangeList = StringUtils.StringToList(exchangeIds, ",");
			for (String exchangeId : exchangeList) {
				// 验证是否已放入过奖池
				if(isValid(exchangeId)) {
					saveRollPool(roomId, null, exchangeId);
				}
			}
			// 更新状态
			exchangeLogService.updateStatusForPool(exchangeIds);
		}
	}

	private RollPool saveRollPool(String roomId, Long gold, String exchangeId) {
		RollPool entity = new RollPool();
		entity.setId(IdGen.uuid());// 设置ID 生成 UUID
		entity.setRoomId(roomId);
		entity.setGold(gold);
		entity.setExchangeId(exchangeId);
		entity.setAddTime(StringUtils.toString(new Date().getTime()));
		rollPoolMapper.insert(entity);

		return entity;
	}
	
	private boolean isValid(String exchangeId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("exchangeId", exchangeId);

		// 判断是否已经添加
		List<RollPool> list = rollPoolMapper.queryListByPage(paramMap);
		if (list != null && !list.isEmpty()) {
			return false;
		}

		// 判断状态是否有效
		return exchangeLogService.isCdkeyValid(exchangeId);
	}
	
	public void deleteBatchById(String ids) {
		if(StringUtils.isBlank(ids)) {
			throw new RuntimeException("请先确定要删除的奖品");
		}
		String exchangeIds = "";
		List<String> list = StringUtils.StringToList(ids, ",");
		for (String poolId : list) {
			RollPool entity = rollPoolMapper.findById(poolId.trim());
			Long gold = entity.getGold();
			if(gold != null) {
				// 返还钱包
				String userId = entity.getUserId();
				int count = userWalletService.rechargeWallet(userId, gold, GlobalConstant.RECHARGE_TYPE_JC);
				if (1 != count) {
					throw new RuntimeException("删除奖品返还金币失败：" + count);
				}
				// 交易记录
				UserTransactionLog utl = new UserTransactionLog();
				utl.setId(IdGen.uuid());
				utl.setUserId(userId);
				utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_12);// roll奖品
				utl.setDataId(poolId.trim());
				utl.setGoldNum(gold);
				utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
				utl.setUtTime(StringUtils.toString(System.currentTimeMillis()));
				utl.setRemarks("roll删除奖品返还" + gold + "金币");
				userTransactionLogService.insert(utl);
			} else {
				if(StringUtils.isBlank(exchangeIds)) {
					exchangeIds = entity.getExchangeId();
				} else {
					exchangeIds = exchangeIds + "," + entity.getExchangeId();
				}
			}
		}
		if(StringUtils.isNotBlank(exchangeIds)) {
			// 更新状态
			exchangeLogService.updateStatusForRoll(exchangeIds);
		}
		// 删除奖品
		this.deleteBatchById(list);
	}
	
	public List<String> getIdByRoomId(String roomId) {
		
		return rollPoolMapper.getIdByRoomId(roomId);
	}

}
