/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.sign.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserIncrement;
import com.webside.user.service.UserIncrementService;
import com.webside.user.sign.entities.UserSign;
import com.webside.user.sign.mapper.IUserSignMapper;
import com.webside.user.sign.service.IUserSignService;
import com.webside.user.task.service.IUserTaskService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;

/**
 * 用户签到服务实现类
 *
 * @author tianguifang
 * @date 2016-12-06 15:04:37
 */
@Service("userSignService")
public class UserSignService extends AbstractService<UserSign, String> implements IUserSignService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userSignMapper);
	}

	/**
	 * 用户签到 DAO定义
	 */
	@Autowired
	private IUserSignMapper userSignMapper;
	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	@Autowired
	private UserIncrementService userIncrementService;
	@Autowired
	private IUserTaskService userTaskService;
	/**
	 * 用户签到
	 */
	@Override
	public int insert(UserSign userSign) {
		if (userSign == null) {
			return 0;
		}
		UserSign todaySign = this.querySignInToday(userSign.getUserId());
		if (todaySign != null) { // 判断今天是否已经签到过，已经签到过，则返回，签到失败
			return -1;
		}
		String signId = IdGen.uuid();
		userSign.setId(signId);
		userSign.setAddTime(System.currentTimeMillis());
		Long gold = Long.valueOf(getSignGiveGold());
		userSign.setGiveGoldNum(gold);
		int result = userSignMapper.insert(userSign);// 签到
		if (result > 0) {
			// 赠G币
			Integer rechargeState = userWalletService.rechargeWallet(userSign.getUserId(), gold, GlobalConstant.RECHARGE_TYPE_GIFT);
			if (rechargeState > 0) {
				// 存用户签到交易记录
				UserTransactionLog tranLog = new UserTransactionLog();
				tranLog.setId(IdGen.uuid());
				tranLog.setUserId(userSign.getUserId());
				tranLog.setUtTime(System.currentTimeMillis() + "");
				tranLog.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_5);
				tranLog.setRemarks("签到");
				tranLog.setGoldNum(gold);
				tranLog.setGoldType(GlobalConstant.GOLD_TYPE_2);
				tranLog.setDataId(signId);
				tranLog.setUserNick(ShiroAuthenticationManager.getUserNickName());
				userTransactionLogService.insert(tranLog);
				
				UserIncrement userIncrement = new UserIncrement();
				userIncrement.setUserId(userSign.getUserId());
				userIncrement.setSignNum(1);
				userIncrementService.updateUserIncrement(userIncrement);
				
				userTaskService.updateUserTask(userSign.getUserId(), GlobalConstant.USER_TASK_TYPE_SIGN_0, userSign.getGiveGoldNum().intValue());
				return gold.intValue();
			}
		}
		return 0;
	}

	/**
	 * 查询签到记录
	 * @param userId
	 * @return
	 */
	@Override
	public UserSign querySignInToday(String userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		param.put("addTime", calendar.getTimeInMillis());
		UserSign todaySign = userSignMapper.querySignInToday(param);
		return todaySign;
	}

	/**
	 * 获取1--30的随机数
	 * 
	 * @return
	 */
	private Integer getSignGiveGold() {
		int gold = (int) (Math.random() * 30);
		if (gold == 0) {
			gold = 1;
		}
		return gold;
	}

	/*public static void main(String[] args) {
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			int gold = (int) (Math.random() * 30);
			if (gold == 0) {
				System.out.println(i + "_" + gold);
				break;
			}
		}
	}*/
}
