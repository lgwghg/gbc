/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.exchange.entities;

import java.io.Serializable;

import com.webside.goods.model.vo.GoodsStockVo;

/**
 * 用户兑换 entity
 * 
 * @author tianguifang
 * @date 2016-11-24 16:19:22
 */
public class UserExchangeLog implements Serializable {
	private static final long serialVersionUID = 1L;

	public UserExchangeLog() {
	}

	/**
	 * 唯一标识
	 */
	private String id;
	private String trackerNo;// 物流单号
	private String cdkCode;// cd_key内码
	private String receiverName;// 收货人姓名
	private String receiverMobile;// 收货人联系电话
	private String receiverAddress;// 详细地址

	private GoodsStockVo goodsVo;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public String getCdkCode() {
		return cdkCode;
	}

	public void setCdkCode(String cdkCode) {
		this.cdkCode = cdkCode;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	/**
	 * 库存ID
	 */
	private String stockId;

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getStockId() {
		return this.stockId;
	}

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户昵称
	 */
	private String userNick;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	/**
	 * 兑换时间
	 */
	private Long exchangeTime;

	public void setExchangeTime(Long exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public Long getExchangeTime() {
		return this.exchangeTime;
	}

	/**
	 * 兑换金额
	 */
	private Long exchangeGold;

	public void setExchangeGold(Long exchangeGold) {
		this.exchangeGold = exchangeGold;
	}

	public Long getExchangeGold() {
		return this.exchangeGold;
	}

	/**
	 * 兑换状态 默认 1 1：有效 0：无效
	 */
	private String exchangeStatus;

	public void setExchangeStatus(String exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

	public String getExchangeStatus() {
		return this.exchangeStatus;
	}

	/**
	 * 数字订单编号
	 */
	private String ueOrderNo;

	public void setUeOrderNo(String ueOrderNo) {
		this.ueOrderNo = ueOrderNo;
	}

	public String getUeOrderNo() {
		return this.ueOrderNo;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public GoodsStockVo getGoodsVo() {
		return goodsVo;
	}

	public void setGoodsVo(GoodsStockVo goodsVo) {
		this.goodsVo = goodsVo;
	}

	public String getTrackerNo() {
		return trackerNo;
	}

	public void setTrackerNo(String trackerNo) {
		this.trackerNo = trackerNo;
	}

}