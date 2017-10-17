/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.message.entities;

import java.io.Serializable;

/**
 *  用户通知entity
 *
 * @author zengxn
 * @date 2016-11-25 17:43:44
 */
public class UserMessage implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 业务类型 1:评论，2：充值成功 3：竞猜获得 4：意见反馈 5：推荐好友奖励 6：竞猜取消返回 7：注册成功赠送G币 8：兑换cdkey 9：提现通知
	 */
	private String businessType;
	
	/**
	 * 业务ID
	 */
	private String businessId;
	
	/**
	 * 消息描述
	 */
	private String content;
	
	/**
	 * 阅读状态 1：已读，0：未读
	 */
	private String state;
	
	/**
	 * 默认0     1：已删除   0：正常
	 */
	private String isDeleted;
	
	/**
	 * 消息创建时间
	 */
	private String createTime;
	
	/**
	 * 更新已读状态时，更新时间
	 */
	private String updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}