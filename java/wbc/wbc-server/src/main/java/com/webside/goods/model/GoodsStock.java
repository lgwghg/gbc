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
 * @date 2016-11-23 11:25:20
 */
public class GoodsStock implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;// 唯一标识
	private String goodsId;// 所属商品
	private String goodsName;
	private String cardNo;// 卡号
	private String cardPass;// 卡密
	private String goodsNo;// 编号
	private String effectiveTime;// 有效期
	private String status;// 状态 1: 未兑换，2：已兑换 , 3：已使用

	public GoodsStock() {
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardPass(String cardPass) {
		this.cardPass = cardPass;
	}

	public String getCardPass() {
		return this.cardPass;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getGoodsNo() {
		return this.goodsNo;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof GoodsStock)) {
			return false;
		}
		GoodsStock castOther = (GoodsStock) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}

}