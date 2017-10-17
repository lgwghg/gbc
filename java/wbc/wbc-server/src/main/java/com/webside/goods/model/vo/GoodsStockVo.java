package com.webside.goods.model.vo;

import java.io.Serializable;

public class GoodsStockVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7084574309015249561L;
	private String stockId;// 唯一标识
	private String cardNo;// 卡号
	private String cardPass;// 卡密
	private String goodsNo;// 编号
	private String effectiveTime;// 有效期
	private String stockStatus;// 状态 1: 未兑换，2：已兑换 , 3：已使用

	private String goodsId;// 所属商品
	private String goodsName;// 商品名称
	private String goodsImg;// 商品图片
	private Long goodsGold;// 商品金币
	private String goodsDesc;// 商品描述
	private String goodsType;// 类型 1：实物 2：虚拟物品
	private String goodsStatus;// 状态 1: 有效 0 无效

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPass() {
		return cardPass;
	}

	public void setCardPass(String cardPass) {
		this.cardPass = cardPass;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public Long getGoodsGold() {
		return goodsGold;
	}

	public void setGoodsGold(Long goodsGold) {
		this.goodsGold = goodsGold;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}


}
