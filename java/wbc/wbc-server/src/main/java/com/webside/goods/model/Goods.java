/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.goods.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * entity
 * 
 * @author zhangfei
 * @date 2016-11-23 11:24:23
 */
public class Goods implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;
	private String goodsName;// 商品名称
	private String goodsImg;// 商品图片
	private Long goodsGold;// 商品金币
	private Long goodsNum;// 商品数量
	private Long exchangeCount;// 兑换数量
	private String addUserId;// 创建人
	private String addUserName;
	private Long createTime;// 添加时间
	private Long updateTime;// 修改时间
	private String updateUserId;// 修改人
	private String updateUserName;
	private String desc;// 商品描述
	private String type;// 类型 1：实物 2：虚拟物品
	private String isMax;// 1：无限库存
	private String status;// 状态 1: 有效 0 无效

	public Goods() {
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public String getGoodsImg() {
		return this.goodsImg;
	}

	public void setGoodsGold(Long goodsGold) {
		this.goodsGold = goodsGold;
	}

	public Long getGoodsGold() {
		return this.goodsGold;
	}

	public void setGoodsNum(Long goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Long getGoodsNum() {
		return this.goodsNum;
	}

	public Long getExchangeCount() {
		return exchangeCount;
	}

	public void setExchangeCount(Long exchangeCount) {
		this.exchangeCount = exchangeCount;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}

	public String getAddUserId() {
		return this.addUserId;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public String getIsMax() {
		return isMax;
	}

	public void setIsMax(String isMax) {
		this.isMax = isMax;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof Goods)) {
			return false;
		}
		Goods castOther = (Goods) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}

}