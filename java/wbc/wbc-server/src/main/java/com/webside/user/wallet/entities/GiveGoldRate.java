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
 * @date 2016-12-07 18:31:55
 */
public class GiveGoldRate implements Serializable
{
	private static final long serialVersionUID = 1L;

	public GiveGoldRate(){}
	

	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * 充值金额
	 */
	private Long amount;
	
	public void setAmount(Long amount){
		this.amount = amount;
	}
	
	public Long getAmount(){
		return this.amount;
	}

	/**
	 * 赠送G币百分比，直接输入整数
	 */
	private Integer giveRate;
	
	public Integer getGiveRate() {
		return giveRate;
	}

	public void setGiveRate(Integer giveRate) {
		this.giveRate = giveRate;
	}


	/**
	 * 更新时间
	 */
	private Long updateTime;
	
	public void setUpdateTime(Long updateTime){
		this.updateTime = updateTime;
	}
	
	public Long getUpdateTime(){
		return this.updateTime;
	}


}