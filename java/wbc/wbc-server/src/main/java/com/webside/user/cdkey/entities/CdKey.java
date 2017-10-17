/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.cdkey.entities;

import java.io.Serializable;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2017-04-07 11:47:42
 */
public class CdKey implements Serializable
{
	private static final long serialVersionUID = 1L;

	public CdKey(){}
	

	/**
	 * 主键
	 */
	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * cdkey类型0：商品 1：G币 2：体验币
	 */
	private Integer type;
	
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getType(){
		return this.type;
	}

	/**
	 * 商品id
	 */
	private String goodsId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	public void setGoodsId(String goodsId){
		this.goodsId = goodsId;
	}
	
	public String getGoodsId(){
		return this.goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	/**
	 * G币值
	 */
	private Integer gold;
	
	public void setGold(Integer gold){
		this.gold = gold;
	}
	
	public Integer getGold(){
		return this.gold;
	}

	/**
	 * cdkey兑换码
	 */
	private String cdkeyCode;
	
	private Integer codeNum;
	
	public void setCdkeyCode(String cdkeyCode){
		this.cdkeyCode = cdkeyCode;
	}
	
	public String getCdkeyCode(){
		return this.cdkeyCode;
	}

	/**
	 * 状态0：未兑换  1：已兑换   2:冻结  默认0
	 */
	private Integer state;
	
	public void setState(Integer state){
		this.state = state;
	}
	
	public Integer getState(){
		return this.state;
	}

	private String startTime;
	
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	
	public String getStartTime(){
		return this.startTime;
	}

	private String endTime;
	
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	
	public String getEndTime(){
		return this.endTime;
	}

	/**
	 * 生成时间
	 */
	private Long createTime;
	
	public void setCreateTime(Long createTime){
		this.createTime = createTime;
	}
	
	public Long getCreateTime(){
		return this.createTime;
	}

	/**
	 * 生成人id
	 */
	private String createUserId;
	
	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}
	
	public String getCreateUserId(){
		return this.createUserId;
	}
	/**
	 * 用户id，cdk给到的用户
	 */
	private String userId;
	
	private String userMobile;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}


	/**
	 * 兑换时间
	 */
	private Long exchangeTime;
	
	public void setExchangeTime(Long exchangeTime){
		this.exchangeTime = exchangeTime;
	}
	
	public Long getExchangeTime(){
		return this.exchangeTime;
	}

	/**
	 * cdk兑换人id
	 */
	private String exchangeUserId;
	/**
	 * 兑换人昵称
	 */
	private String exchangeUserNick;
	public void setExchangeUserId(String exchangeUserId){
		this.exchangeUserId = exchangeUserId;
	}
	
	public String getExchangeUserId(){
		return this.exchangeUserId;
	}

	public String getExchangeUserNick() {
		return exchangeUserNick;
	}

	public void setExchangeUserNick(String exchangeUserNick) {
		this.exchangeUserNick = exchangeUserNick;
	}

	public Integer getCodeNum() {
		return codeNum;
	}

	public void setCodeNum(Integer codeNum) {
		this.codeNum = codeNum;
	}


}