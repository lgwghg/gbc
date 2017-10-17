/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.wallet.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.webside.user.wallet.entities.UserWallet;

/**
 * 用户钱包服务接口
 *
 * @author tianguifang
 * @date 2016-11-24 15:27:02
 */
public interface IUserWalletService 
{
	/*
	 * 按条件查询用户钱包
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserWallet> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增用户钱包
	 * @param UserWallet
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UserWallet entity);
	
	/*
	 * 修改用户钱包
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int update(final UserWallet entity);
	
	/*
	 * 根据ID获取用户钱包
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserWallet findById(String id);
	
	/*
	 * 根据对象删除用户钱包
	 * @param UserWallet
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);

	/**
	 * 钱包扣款 gold 扣款金额 type 扣款类型 0：竞猜 1：兑换
	 * 
	 * @return 
	 * 	jsonObject.getIntValue("result") 扣款状态 0：扣款失败 1：扣款成功 -1：参数错误 -2：G币不足
		jsonObject.getIntValue("sysGold");// G币数量
		jsonObject.getIntValue("gold");// 金币数量
	 * 
	 * @author zengxn
	 * @date 2017年4月7日 18:49:21
	 */
	public JSONObject deductWallet(String userId, Long gold, Integer type);
	/**
	 * 钱包充值或奖励赠送
	 * 
	 * @param gold 进账金额
	 * @param type 进账类型 0：充值 1：赠送
	 * 
	 * @return 进账状态 0：进账失败 1：进账成功 -1：参数错误
	 * @author tianguifang
	 * @date 2016-11-29
	 */
	public Integer rechargeWallet(String userId, Long gold, Integer type);

	/**
	 * 根据用户id，查询用户的钱包数据
	 * 
	 * @param userId
	 * @return
	 * @author tianguifang
	 * @date 2016-11-29
	 */
	UserWallet findWalletByUserId(String userId);

	public Integer updateRechargeWallet(String userId, Long gold, Integer type);
}
