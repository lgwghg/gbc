/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.friends.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.friends.entities.UserFriends;
import com.webside.user.friends.mapper.IUserFriendsMapper;
import com.webside.user.friends.service.IUserFriendsService;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserIncrement;
import com.webside.user.service.IUserCacheService;
import com.webside.user.service.UserIncrementService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 推荐好友服务实现类
 *
 * @author zengxn
 * @date 2016-11-28 11:05:22
 */
@Service("userFriendsService")
public class UserFriendsService extends AbstractService<UserFriends, String> implements IUserFriendsService 
{
	private Logger logger = Logger.getLogger(this.getClass());
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userFriendsMapper);
	}

	/**
	 * 推荐好友 DAO定义
	 */
	@Autowired
	private IUserFriendsMapper userFriendsMapper;
	
	/**
	 * 用户消息 SERVICE定义
	 */
	@Autowired
	private IUserMessageService userMessageService;
	
	/**
	 * 用户交易记录 SERVICE定义
	 */
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	
	/**
	 * 用户钱包 SERVICE定义
	 */
	@Autowired
	private IUserWalletService userWalletService;
	
	/**
	 * 用户增量 SERVICE定义
	 */
	@Autowired
	private UserIncrementService userIncrementService;
	
	/***
	 * 用户缓存SERVICE定义
	 */
	@Autowired
	private IUserCacheService userCacheService;

	@Override
	public JSONArray queryPartListByPage(Map<String, Object> parameter) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		List<UserFriends> list = userFriendsMapper.queryPartListByPage(parameter);
		for (UserFriends userFriends : list) {
			jsonObject = new JSONObject();
			jsonObject.put("createTime", DateUtils.longToString(StringUtils.toLong(userFriends.getCreateTime())));
			jsonObject.put("friendNickName", userFriends.getFriendNickName());
			jsonObject.put("rewardGoldNum", userFriends.getRewardGoldNum());
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	@Override
	public int addRegister(String userId,String friendId,String friendNickName) {
		int result = 0;
		try {
			//赠送G币
			Integer num = GlobalConstant.REWARD_GOLD_NUM_100;
			UserFriends t = new UserFriends();
			t.setId(IdGen.uuid());
			t.setUserId(userId);
			t.setFriendId(friendId);
			t.setRewardGoldNum(num);
			t.setCreateTime((System.currentTimeMillis()+""));
			result = super.insert(t);
			if(result==1){
				//用户钱包增加奖励值
				userWalletService.rechargeWallet(t.getUserId(), t.getRewardGoldNum().longValue(), GlobalConstant.RECHARGE_TYPE_GIFT);
				
				//新增成功插入用户交易记录
				UserTransactionLog utl = new UserTransactionLog();
				utl.setId(IdGen.uuid());
				utl.setUserId(t.getUserId());
				utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_4);
				utl.setDataId(t.getId());
				utl.setGoldNum(t.getRewardGoldNum().longValue());
				utl.setGoldType(GlobalConstant.GOLD_TYPE_2);
				utl.setUtTime(t.getCreateTime());
				utl.setRemarks("推荐好友"+friendNickName+"注册");
				userTransactionLogService.insert(utl);
				
				//新增成功插入通知
				userMessageService.addMessageForUserFriends(t.getUserId(), t.getId(), friendNickName, num);
			}
		} catch (Exception e) {
			logger.error("插入推荐好友记录出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public void addJc() {
		int result = 0;
		try {
			//当前登陆用户，好友
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			user = userCacheService.getUserEntityByUserId(user.getId());
			if(user.getUserIncrement()!=null && user.getUserIncrement().getIsInvolvementInJc()!=null && user.getUserIncrement().getIsInvolvementInJc().intValue()==StringUtils.toInteger(GlobalConstant.DICTTYPE_YES_NO_0).intValue()){
				//赠送G币
				Integer num = GlobalConstant.REWARD_GOLD_NUM_200;
				//查询注册赠送时记录
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("friendId", user.getId());
				parameter.put("rewardGoldNum", GlobalConstant.REWARD_GOLD_NUM_100);
				List<UserFriends> list = userFriendsMapper.findByFriendId(parameter);
				if(list!=null && list.size()>0){
					//注册赠送时的记录
					UserFriends tmpFriends = list.get(0);
					
					UserFriends t = new UserFriends();
					t.setId(IdGen.uuid());
					t.setUserId(tmpFriends.getUserId());
					t.setFriendId(user.getId());
					t.setRewardGoldNum(num);
					t.setCreateTime((System.currentTimeMillis()+""));
					result = super.insert(t);
					if(result==1){
						//用户钱包增加奖励值
						userWalletService.rechargeWallet(t.getUserId(), t.getRewardGoldNum().longValue(), GlobalConstant.RECHARGE_TYPE_GIFT);
						
						//新增成功插入用户交易记录
						UserTransactionLog utl = new UserTransactionLog();
						utl.setId(IdGen.uuid());
						utl.setUserId(t.getUserId());
						utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_4);
						utl.setDataId(t.getId());
						utl.setGoldNum(t.getRewardGoldNum().longValue());
						utl.setGoldType(GlobalConstant.GOLD_TYPE_2);
						utl.setUtTime(t.getCreateTime());
						utl.setRemarks("推荐好友"+user.getNickName()+"参与竞猜");
						userTransactionLogService.insert(utl);
						
						//新增成功插入通知
						userMessageService.addMessageForUserFriends(t.getUserId(), t.getId(), user.getNickName(), num);
					}
				}
				//修改用户缓存
				UserIncrement userIncrement = new UserIncrement();
				userIncrement.setUserId(user.getId());
				userIncrement.setIsInvolvementInJc(StringUtils.toInteger(GlobalConstant.DICTTYPE_YES_NO_1));
				userIncrementService.updateUserIncrement(userIncrement);
			}
		} catch (Exception e) {
			logger.error("插入第一次竞猜记录出错：", e);
			throw new ServiceException(e);
		}
	}
}
