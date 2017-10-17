/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.model;

import java.io.Serializable;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2016-11-24 15:26:55
 */
public class UserWallet implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UserWallet(){}
	

	/**
	 * 唯一标识
	 */
	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * 用户ID
	 */
	private String userId;
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	/**
	 * G币
	 */
	private Long gold;
	
	public void setGold(Long gold){
		this.gold = gold;
	}
	
	public Long getGold(){
		return this.gold;
	}

	/**
	 * 赠送G币
	 */
	private Long sysGoldNum;
	
	public void setSysGoldNum(Long sysGoldNum){
		this.sysGoldNum = sysGoldNum;
	}
	
	public Long getSysGoldNum(){
		return this.sysGoldNum;
	}

	/**
	 * 修改时间
	 */
	private Long updateTime;
	
	public void setUpdateTime(Long updateTime){
		this.updateTime = updateTime;
	}
	
	public Long getUpdateTime(){
		return this.updateTime;
	}

	private Long createTime;

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	private String CreateOperatorId;
	private String UpdateOperatorId;

	public String getCreateOperatorId() {
		return CreateOperatorId;
	}

	public void setCreateOperatorId(String createOperatorId) {
		CreateOperatorId = createOperatorId;
	}

	public String getUpdateOperatorId() {
		return UpdateOperatorId;
	}

	public void setUpdateOperatorId(String updateOperatorId) {
		UpdateOperatorId = updateOperatorId;
	}

}