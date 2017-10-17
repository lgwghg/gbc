/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.friends.service;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;

/**
 * 推荐好友服务接口
 *
 * @author zengxn
 * @date 2016-11-28 11:05:22
 */
public interface IUserFriendsService 
{
	/**
	 * 按条件查询推荐好友部分字段
	 * @throws Exception
	 * @author zengxn
	 */
	public JSONArray queryPartListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增推荐好友
	 * @param userId			旧用户id
	 * @param friendId			新用户id
	 * @param friendNickName	新用户昵称
	 * @throws Exception
	 * @author zengxn
	 */
	public int addRegister(String userId,String friendId,String friendNickName);
	
	/**
	 * 第一次竞猜
	 * @throws Exception
	 * @author zengxn
	 */
	public void addJc();
}
