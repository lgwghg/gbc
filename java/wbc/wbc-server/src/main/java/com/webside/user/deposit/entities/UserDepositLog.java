/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.deposit.entities;

import java.io.Serializable;

/**
 * 充值记录 entity
 * 
 * @author tianguifang
 * @date 2016-11-24 16:20:57
 */
public class UserDepositLog implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UserDepositLog(){}
	
	public UserDepositLog(String id){
		this.id = id;
	}
	
	public UserDepositLog(String id,String orderName,Long udGold){
		this.id = id;
		this.orderName = orderName;
		this.udGold = udGold;
	}
	
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
	 * 用户昵称
	 */
	private String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 订单标题
	 */
	private String orderName;
	
	public void setOrderName(String orderName){
		this.orderName = orderName;
	}
	
	public String getOrderName(){
		return this.orderName;
	}

	/**
	 * 充值平台 1：支付宝，2：微信
	 */
	private String type;
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}

	/**
	 * 第三方交易编号
	 */
	private String orderNo;
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public String getOrderNo(){
		return this.orderNo;
	}

	/**
	 * 充值金额
	 */
	private Long udGold;
	
	public void setUdGold(Long udGold){
		this.udGold = udGold;
	}
	
	public Long getUdGold(){
		return this.udGold;
	}

	/**
	 * 下单时间
	 */
	private String orderTime;
	
	public void setOrderTime(String orderTime){
		this.orderTime = orderTime;
	}
	
	public String getOrderTime(){
		return this.orderTime;
	}

	/**
	 * 支付时间
	 */
	private Long payTime;
	
	public void setPayTime(Long payTime){
		this.payTime = payTime;
	}
	
	public Long getPayTime(){
		return this.payTime;
	}

	/**
	 * 是否支付成功 1:是 0：否　，默认初始化未0
	 */
	private String isPaySuccess;
	
	public void setIsPaySuccess(String isPaySuccess){
		this.isPaySuccess = isPaySuccess;
	}
	
	public String getIsPaySuccess(){
		return this.isPaySuccess;
	}
	
	/**
	 * 备注
	 */
	private String remarks;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}