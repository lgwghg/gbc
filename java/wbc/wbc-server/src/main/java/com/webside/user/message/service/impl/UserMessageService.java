/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.message.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.coinflip.model.CoinflipUser;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.user.message.entities.UserMessage;
import com.webside.user.message.mapper.IUserMessageMapper;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserIncrement;
import com.webside.user.service.UserIncrementService;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 用户消息服务实现类
 *
 * @author zengxn
 * @date 2016-11-25 17:43:42
 */
@Service("userMessageService")
public class UserMessageService extends AbstractService<UserMessage, String> implements IUserMessageService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userMessageMapper);
	}

	/**
	 * 用户消息 DAO定义
	 */
	@Autowired
	private IUserMessageMapper userMessageMapper;
	
	/**
	 * 用户增量 SERVICE定义
	 */
	@Autowired
	private UserIncrementService userIncrementService;
	
	// 通过线程池获取线程
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();// 启动线程池

	@Override
	public JSONArray updateListByPage(Map<String, Object> parameter) {
		List<String> ids = null;
		List<UserMessage> list = super.queryListByPage(parameter);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		if(list!=null && list.size()>0){
			ids = new ArrayList<String>();
			for (UserMessage userMessage : list) {
				jsonObject = new JSONObject();
				jsonObject.put("content", userMessage.getContent());
				jsonObject.put("status", userMessage.getState());
				jsonObject.put("createTime", DateUtils.longToString(StringUtils.toLong(userMessage.getCreateTime())));
				jsonArray.add(jsonObject);
				if(userMessage.getState().equals(GlobalConstant.MESSAGE_STATE_0)){
					ids.add(userMessage.getId());
				}
			}
			if(ids!=null && ids.size()>0){
				updateBatchById(StringUtils.toString(parameter.get("userId")),ids);
			}
		}
		return jsonArray;
	}
	
	public void addMessageForComment(String userId, String businessId,
			String content, String nickName, String gbId, String gbName, String typeId) {
		String message = "<a href=\"/gb/"+gbId+"\">" + nickName+" 在 "+gbName+" 中回复我："+content+"</a>";
		if("2".equals(typeId)) {
			message = "<a href=\"/roll/"+gbId+"\">" + nickName+" 在roll房间 "+gbName+" 中回复我："+content+"</a>";
		}
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_1,userId, businessId,message));
	}

	@Override
	public void addMessageForDeposit(String userId, String businessId,
			Long ...num) {
		String message = "充值"+(num.length==2?num[0]+"(+"+num[1]+")":num[0])+"金币成功";
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_2,userId, businessId,message));
	}

	@Override
	public void addMessageForUserJc(String userId, String businessId,
			Integer num, String gbId, String gbName) {
		String message = "<a href=\"/gb/"+gbId+"\">在 "+gbName+" 中竞猜赢了，奖励"+num+"金币</a>";
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_3,userId, businessId,message));
	}
	
	@Override
	public void addMessageForCoinflipWinner(CoinflipUser joinner) {
		String message = "恭喜" + joinner.getUserNick() + "，在翻硬币游戏中，赢得" + joinner.getWinGoldNum() + "金币";
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_COIN_11, joinner.getUserId(), joinner.getRoomId(), message));
	}
	
	@Override
	public void addMessageForCoinflipReback(CoinflipUser joinner) {
		String message = "翻硬币游戏取消，退回金币" + joinner.getGoldNum() + "个";
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_COIN_REBACK_12, joinner.getUserId(), joinner.getRoomId(), message));
	}
	
	@Override
	public void addMessageForFeedBack(String userId, String businessId,
			Integer num) {
		String message = "感谢您提出的宝贵意见，奖励"+num+"G币";
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_4,userId, businessId,message));
	}

	@Override
	public void addMessageForUserFriends(String userId, String businessId,
			String nickName, Integer num) {
		String message = "推荐"+nickName+"注册成功，奖励"+num+"G币";
		if(num==GlobalConstant.REWARD_GOLD_NUM_200){
			message = "推荐"+nickName+"参与竞猜，奖励"+num+"G币";
		}
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_5,userId, businessId,message));
	}
	
	@Override
	public void addMessageForUserJcReturn(String userId, String businessId, Integer num, String gbId, String gbName) {
		String message = "<a href=\"/gb/"+gbId+"\">由于赛事 "+gbName+" 被取消，退回"+num+"金币</a>";
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_6,userId, businessId,message));
	}
	
	@Override
	public void addMessageForUserRegister(String userId, String businessId, Integer num) {
		String message = "注册成功，赠送"+num+"G币";
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_7,userId, businessId,message));
	}
	
	@Override
	public void addMessageForCdKey(String userId, String businessId,String code, int type, Object obj) {
		String message = code+" - 成功兑换"+StringUtils.toString(obj);
		if(type==1){
			message += "金币";
		}else if(type==2){
			message += "G币";
		}else{
			message += "商品";
		}
		message += ",已发放至您的账户请查收";
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_8,userId, businessId,message));
	}

	@Override
	public void addMessageForWithdrawals(String userId,String businessId,String status,Long num,String msg) {
		String message = "提现"+num+"金币";
		if("1".equals(status)){
			message+= "成功";
		}else{
			message+= "失败,原因："+msg;
		}
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_9,userId, businessId,message));
	}
	
	public void addMessageForRoll(String userId, String businessId, Long gold) {
		String message = "roll获奖 ";
		if (gold != null) {
			message += gold + "金币";
		} else {
			message += "CD-KEY兑换码";
		}
		message += ",已发放至您的账户请查收";
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_10, userId, businessId, message));
	}
	
	public void addMessageForRollEnd(String userId, String businessId, Integer type) {
		String message = "roll活动结束，";
		if (1 == type) {
			message += "奖品已退回您的账户请查收";
		} else {
			message += "奖品已自动roll派发给玩家";
		}
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_10, userId, businessId, message));
	}
	
	/**
	 * 异步执行消息插入
	 * @author zengxn
	 *
	 */
	class AddUserMessageRunner implements Runnable {
		private String userId;
		private String businessType;
		private String businessId;
		private String message;

		public AddUserMessageRunner(String businessType,String userId,String businessId,String message) {
			this.userId = userId;
			this.businessType = businessType;
			this.businessId = businessId;
			this.message = message;
		}
		@Override
		public void run() {
			addMessage(businessType,userId, businessId,message);
		}
	}
	
	/**
	 * 新增用户消息
	 * @param userId
	 * @param businessType
	 * @param businessId
	 * @param message
	 * @return
	 */
	private int addMessage(String businessType,String userId,String businessId,String message){
		int result = 0;
		try {
			UserMessage t = new UserMessage();
			t.setId(IdGen.uuid());
			t.setUserId(userId);
			t.setBusinessType(businessType);
			t.setBusinessId(businessId);
			t.setCreateTime(System.currentTimeMillis()+"");
			t.setState(GlobalConstant.MESSAGE_STATE_0);
			t.setIsDeleted(GlobalConstant.DICTTYPE_IS_DELETE_0);
			t.setContent(message);
			result = super.insert(t);
			if(result==1){
				UserIncrement userIncrement = new UserIncrement();
				userIncrement.setUserId(userId);
				userIncrement.setUnreadNum(1);
				userIncrementService.updateUserIncrement(userIncrement);
			}
		} catch (Exception e) {
			logger.error("新增用户消息出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	/**
	 * 批量修改用户消息为已读
	 * @param ids
	 * @return
	 */
	private void updateBatchById(String userId,List<String> ids){
		try {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("ids", ids);
			parameter.put("state", GlobalConstant.MESSAGE_STATE_1);
			parameter.put("updateTime", System.currentTimeMillis()+"");
			int updateBatchById = userMessageMapper.updateBatchById(parameter);
			UserIncrement userIncrement = new UserIncrement();
			userIncrement.setUserId(userId);
			userIncrement.setUnreadNum(-updateBatchById);
			userIncrementService.updateUserIncrement(userIncrement);
		} catch (Exception e) {
			logger.error("批量修改用户消息为未读出错：", e);
			throw new ServiceException(e);
		}
	}
}
