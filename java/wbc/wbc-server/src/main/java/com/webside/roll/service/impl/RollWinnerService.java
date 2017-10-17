/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.roll.mapper.IRollWinnerMapper;
import com.webside.roll.model.RollMember;
import com.webside.roll.model.RollPool;
import com.webside.roll.model.RollRoom;
import com.webside.roll.model.RollWinner;
import com.webside.roll.service.IRollMemberService;
import com.webside.roll.service.IRollPoolService;
import com.webside.roll.service.IRollRoomService;
import com.webside.roll.service.IRollWinnerService;
import com.webside.roll.vo.RollWinnerVo;
import com.webside.user.exchange.entities.UserExchangeLog;
import com.webside.user.exchange.service.IUserExchangeLogService;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 获奖人员服务实现类
 * 
 * @author zhangfei
 * @date 2017-04-18 13:51:53
 */
@Service("rollWinnerService")
public class RollWinnerService extends AbstractService<RollWinner, String> implements IRollWinnerService {
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(rollWinnerMapper);
	}

	/**
	 * 获奖人员 DAO定义
	 */
	@Autowired
	private IRollWinnerMapper rollWinnerMapper;
	@Autowired
	private IRollMemberService rollMemberService;
	@Autowired
	private IRollPoolService rollPoolService;
	@Autowired
	private IRollRoomService rollRoomService;
	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	@Autowired
	private IUserExchangeLogService exchangeLogService;
	@Autowired
	private IUserMessageService messageService;

	public List<RollWinnerVo> getRollWinnerListByPage(Map<String, Object> paramMap) {

		return rollWinnerMapper.getRollWinnerListByPage(paramMap);
	}

	// roll点
	public void addRollWinner(String roomId, String poolIds, int num, String unique, String clear) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roomId", roomId);
		if(StringUtils.isBlank(poolIds)) {
			throw new RuntimeException("没有选择奖品，请稍后再roll");
		}
		map.put("idList", StringUtils.StringToList(poolIds, ","));
		map.put("isCurrent", "true");
		List<RollPool> poolList = rollPoolService.queryListByPage(map);
		if (poolList == null || poolList.isEmpty()) {
			throw new RuntimeException("奖品池不能少，请先选择奖品");
		}
		
		if ("true".equals(unique)) {// 唯一
			map.put("isUnique", unique);
		}
		RollRoom rollRoom = rollRoomService.findById(roomId);
		String userId = rollRoom.getUserId();
		List<RollMember> memberList = rollMemberService.queryListByPage(map);
		if (memberList == null || memberList.isEmpty()) {
			if(num == -1) {// 所有
				// 退回奖品
				rollPoolService.deleteBatchById(poolIds);
				// 发送通知
				messageService.addMessageForRollEnd(userId, roomId, 1);
				return ;
			} else {
				throw new RuntimeException("没有满足条件的参与人员，请稍后再roll");
			}
		}
		
		int length = memberList.size();
		Random rand = new Random();
		RollMember rollMember = null;
		// 随机抽取num人数
		List<RollMember> list = new ArrayList<RollMember>();
		if(num == -1) {// 所有
			list.addAll(memberList);
		} else {
			if (num < 1) {
				num = 1;
			}
			for (int i = 0; i < num; i++) {
				int index = rand.nextInt(length);
				rollMember = memberList.get(index);
				list.add(rollMember);
			}
		}
		// 本次roll次数
		String winTime = StringUtils.toString(new Date().getTime());
		int rollNum = getMaxRollNum(roomId) + 1;
		for (RollPool rollPool : poolList) {
			length = list.size();
			if (length < 1) {
				break;
			}
			int index = rand.nextInt(length);// 再次随机
			rollMember = list.get(index);
			if ("true".equals(unique)) {// 唯一
				list.remove(rollMember);
			}

			saveRollWinner(rollPool.getId(), rollMember.getUserId(), rollNum, winTime);
		}

		// 发送奖品
		sendPrize(roomId, rollNum);

		// 清除人员
		if ("true".equals(clear)) {
			rollMemberService.deleteByRoomId(roomId);
		}
		
		if(num == -1) {
			// 发送通知
			messageService.addMessageForRollEnd(userId, roomId, 2);
		}
	}

	private RollWinner saveRollWinner(String poolId, String userId, int rollNum, String winTime) {
		RollWinner entity = new RollWinner();
		entity.setId(IdGen.uuid());// 设置ID 生成 UUID
		entity.setPoolId(poolId);
		entity.setUserId(userId);
		entity.setNum(rollNum);
		entity.setWinTime(winTime);
		entity.setStatus(1);
		rollWinnerMapper.insert(entity);

		return entity;
	}

	private void sendPrize(String roomId, int num) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roomId", roomId);
		paramMap.put("num", num);

		List<RollWinnerVo> list = this.getRollWinnerListByPage(paramMap);
		if (list != null && !list.isEmpty()) {
			for (RollWinnerVo rollWinnerVo : list) {
				String userId = rollWinnerVo.getWinnerId();
				Long gold = null;
				List<RollPool> poolList = rollWinnerVo.getPoolList();
				for (RollPool rollPool : poolList) {// 奖品
					String poolId = rollPool.getId();
					gold = rollPool.getGold();
					if (gold != null) {
						// 发送金币
						int count = userWalletService.rechargeWallet(userId, gold, GlobalConstant.RECHARGE_TYPE_JC);
						if (1 != count) {
							throw new RuntimeException("roll获奖发送金币失败：" + count);
						}
						// 交易记录
						UserTransactionLog utl = new UserTransactionLog();
						utl.setId(IdGen.uuid());
						utl.setUserId(userId);
						utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_12);// roll奖品
						utl.setDataId(poolId);
						utl.setGoldNum(gold);
						utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
						utl.setUtTime(StringUtils.toString(System.currentTimeMillis()));
						utl.setRemarks("roll获奖" + gold + "金币");
						userTransactionLogService.insert(utl);
					} else {// CD-KEY
						String exchangeId = rollPool.getExchangeId();
						UserExchangeLog exchangeLog = exchangeLogService.findById(exchangeId);
						if(exchangeLog != null) {// 发给获奖人, 还原状态
							exchangeLog.setUserId(userId);
							exchangeLog.setExchangeStatus(GlobalConstant.DICTTYPE_EXCHANGE_STATUS_5);
							exchangeLogService.update(exchangeLog);
						}
					}
					// 发送通知
					messageService.addMessageForRoll(userId, rollWinnerVo.getId(), gold);
				}
			}
		}
	}

	public int getMaxRollNum(String roomId) {

		return rollWinnerMapper.getMaxRollNum(roomId);
	}
	
	// roll结束
	public void addForRollEnd() {
		// 获取已结束，但还有奖品的房间
		Long endTime = System.currentTimeMillis() - 10 * 60 * 1000;// 10分钟之前

		List<String> list = rollRoomService.getIdForRollEnd(endTime);
		if (list == null || list.isEmpty()) {
			return;
		}
		int num = -1;
		for (String roomId : list) {
			// 获取房间里面剩余的奖品
			List<String> pollList = rollPoolService.getIdByRoomId(roomId);
			String poolIds = StringUtils.listToString(pollList);

			// roll 奖品
			addRollWinner(roomId, poolIds, num, "false", "false");
		}

	}

}
