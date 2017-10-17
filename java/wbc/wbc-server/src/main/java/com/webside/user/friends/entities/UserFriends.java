/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.friends.entities;

import java.io.Serializable;

/**
 *  entity
 *
 * @author zengxn
 * @date 2016-11-28 11:05:24
 */
public class UserFriends implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 好友ID
	 */
	private String friendId;
	
	/**
	 * 好友昵称
	 */
	private String friendNickName;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 新增时间
	 */
	private String createTime;
	
	/**
	 * 奖励G币数量
	 */
	private Integer rewardGoldNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getRewardGoldNum() {
		return rewardGoldNum;
	}

	public void setRewardGoldNum(Integer rewardGoldNum) {
		this.rewardGoldNum = rewardGoldNum;
	}

	public String getFriendNickName() {
		return friendNickName;
	}

	public void setFriendNickName(String friendNickName) {
		this.friendNickName = friendNickName;
	}
}