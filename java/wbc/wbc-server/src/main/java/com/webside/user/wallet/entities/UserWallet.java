/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.wallet.entities;

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
	/**
	 * 用户昵称
	 */
	private String userNick;
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
	/**
	 * 修改前的修改时间
	 */
	private Long updateT;
	public void setUpdateTime(Long updateTime){
		this.updateTime = updateTime;
	}
	
	public Long getUpdateTime(){
		return this.updateTime;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public Long getUpdateT() {
		return updateT;
	}

	public void setUpdateT(Long updateT) {
		this.updateT = updateT;
	}

}