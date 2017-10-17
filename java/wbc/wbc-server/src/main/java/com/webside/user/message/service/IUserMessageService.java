/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.message.service;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.webside.coinflip.model.CoinflipUser;

/**
 * 用户消息服务接口
 *
 * @author zengxn
 * @date 2016-11-25 17:43:42
 */
public interface IUserMessageService 
{
	/**
	 * 按条件查询用户消息，并改成已阅读，减去相应的未读量
	 * @throws Exception
	 * @author zengxn
	 */
	public JSONArray updateListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增评论消息，并修改用户增量的未读量+1
	 * @param userId		用户id
	 * @param businessId	评论id
	 * @param content		评论内容
	 * @param nickName		评论者名字
	 * @param gbId			对战id
	 * @param gbName		对战名称
	 * @author zengxn
	 */
	public void addMessageForComment(String userId,String businessId,String content,String nickName,String gbId,String gbName,String typeId);
	
	/**
	 * 新增充值消息，并修改用户增量的未读量+1
	 * @param userId		用户id
	 * @param businessId	充值记录id
	 * @param num			充值金币数量
	 * @author zengxn
	 */
	public void addMessageForDeposit(String userId,String businessId,Long ...num);
	
	/**
	 * 新增竞猜获得消息，并修改用户增量的未读量+1
	 * @param userId		用户id
	 * @param businessId	用户竞猜id
	 * @param num			赢取金币数量
	 * @param gbId			对战id
	 * @param gbName		对战名称
	 * @author zengxn
	 */
	public void addMessageForUserJc(String userId,String businessId,Integer num,String gbId,String gbName);
	
	/**
	 * 新增竞猜取消返回消息，并修改用户增量的未读量+1
	 * @param userId		用户id
	 * @param businessId	用户竞猜id
	 * @param num			返回金币数量
	 * @param gbId			对战id
	 * @param gbName		对战名称
	 * @author zengxn
	 */
	public void addMessageForUserJcReturn(String userId,String businessId,Integer num,String gbId,String gbName);
	
	/**
	 * 新增意见反馈消息，并修改用户增量的未读量+1
	 * @param userId		用户id
	 * @param businessId	意见反馈记录id
	 * @param num			奖励G币数量
	 * @author zengxn
	 */
	public void addMessageForFeedBack(String userId,String businessId,Integer num);
	
	/**
	 * 新增推荐好友消息，并修改用户增量的未读量+1
	 * @param userId		用户id
	 * @param businessId	推荐好友记录id
	 * @param nickName		好友名字
	 * @param num			奖励G币数量
	 * @author zengxn
	 */
	public void addMessageForUserFriends(String userId,String businessId,String nickName,Integer num);
	
	/**
	 * 新增注册送G币消息，并修改用户增量的未读量+1
	 * @param userId		用户id
	 * @param businessId	注册记录id
	 * @param num			奖励G币数量
	 * @author zengxn
	 */
	public void addMessageForUserRegister(String userId,String businessId,Integer num);
	
	/**
	 * 新增兑换cdkey消息，并修改用户增量的未读量+1
	 * @param userId		用户id
	 * @param businessId	兑换cdkey记录id
	 * @param code			cdkey兑换码
	 * @param type			类型 1：金币 2：G币 3：商品
	 * @param obj			对应的金币数量、G币数量、商品名称
	 * @author zengxn
	 */
	public void addMessageForCdKey(String userId,String businessId,String code,int type,Object obj);
	
	/**
	 * 新增提现消息，并修改用户增量的未读量+1
	 * @param userId		用户id
	 * @param businessId	提现记录id
	 * @param status		体现状态 1：成功 2：失败
	 * @param num			提现数量
	 * @param msg			失败原因，非必填，成功传null
	 * @author zengxn
	 */
	public void addMessageForWithdrawals(String userId,String businessId,String status,Long num,String msg);
	
	/**
	 * roll获奖通知
	 * @param userId 用户id
	 * @param businessId 获奖ID
	 * @param gold 金币
	 */
	public void addMessageForRoll(String userId, String businessId, Long gold);
	
	/**
	 * roll活动结束，发送通知
	 * @param userId
	 * @param businessId
	 * @param type 1退回，2发放
	 */
	public void addMessageForRollEnd(String userId, String businessId, Integer type);
	
	/**
	 * 翻硬币游戏获胜用户消息
	 * @param  joinner
	 * @author tianguifang
	 */
	public void addMessageForCoinflipWinner(CoinflipUser joinner);
	/**
	 * 翻硬币游戏取消，退回用户金币消息
	 * @param joinner
	 * @author tianguifang
	 */
	public void addMessageForCoinflipReback(CoinflipUser joinner);
}
