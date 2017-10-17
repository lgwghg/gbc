/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.share.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.webside.user.share.entities.UserJcShare;

/**
 * 用户竞猜分享服务接口
 *
 * @author tianguifang
 * @date 2017-01-11 14:34:07
 */
public interface IUserJcShareService 
{
	/*
	 * 按条件查询用户竞猜分享
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserJcShare> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增用户竞猜分享
	 * @param UserJcShare
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UserJcShare entity);
	
	/*
	 * 修改用户竞猜分享
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int update(final UserJcShare entity);
	
	/*
	 * 根据ID获取用户竞猜分享
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserJcShare findById(String id);
	
	/*
	 * 根据对象删除用户竞猜分享
	 * @param UserJcShare
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);

	/**
	 * 获取红包
	 * 
	 * @param jcShare
	 * @return
	 */
	public JSONObject insertGetRedPurse(UserJcShare jcShare);

	/**
	 * 保存领取G币的数据
	 * 
	 * @param jcShare
	 */
	public void insertRedPurse(UserJcShare jcShare);

	/**
	 * 今天已领取的红包数
	 * 
	 * @return
	 */
	public Integer todayGetRedPurseNum(String userId);

	/**
	 * 根据gbid + shareUserId查询竞猜分享
	 * 
	 * @param jcId
	 * @return
	 */
	List<UserJcShare> findJcShareByGbId(UserJcShare share);

	/**
	 * 根据手机号查询出用户7天内领取的G币红包
	 * @param param
	 * @return
	 */
	public List<UserJcShare> queryJcShareByMobile(Map<String, Object> param);
}
