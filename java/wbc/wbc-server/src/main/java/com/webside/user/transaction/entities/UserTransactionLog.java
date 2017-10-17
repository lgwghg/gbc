/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.transaction.entities;

import java.io.Serializable;

/**
 *  用户交易记录实体类entity
 *
 * @author tianguifang
 * @date 2016-11-24 15:54:14
 */
public class UserTransactionLog implements Serializable
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
	 * 用户昵称
	 */
	private String userNick;

	/**
	 * 交易类型 1:竞猜 2：充值 3:兑换 4:推荐好友奖励 5：用户签到 6：充值奖励 7:注册赠送 8:竞猜分享领取 9:提现
	 */
	private String utType;

	/**
	 * 业务ID
	 */
	private String dataId;

	/**
	 * G币数量
	 */
	private Long goldNum;
	
	/**
	 * 货币类型，1金币 2G币
	 */
	private String goldType;
	
	/**
	 * 货币类型名称
	 */
	private String goldTypeName;
	
	/**
	 * 货币类型样式
	 */
	private String goldTypeClass;

	/**
	 * 交易时间
	 */
	private String utTime;

	/**
	 * 备注(信息)
	 */
	private String remarks;

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

	public String getUtType() {
		return utType;
	}

	public void setUtType(String utType) {
		this.utType = utType;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Long getGoldNum() {
		return goldNum;
	}

	public void setGoldNum(Long goldNum) {
		this.goldNum = goldNum;
	}

	public String getUtTime() {
		return utTime;
	}

	public void setUtTime(String utTime) {
		this.utTime = utTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getGoldType() {
		return goldType;
	}

	public void setGoldType(String goldType) {
		this.goldType = goldType;
	}

	public String getGoldTypeName() {
		return goldTypeName;
	}

	public void setGoldTypeName(String goldTypeName) {
		this.goldTypeName = goldTypeName;
	}

	public String getGoldTypeClass() {
		return goldTypeClass;
	}

	public void setGoldTypeClass(String goldTypeClass) {
		this.goldTypeClass = goldTypeClass;
	}

}