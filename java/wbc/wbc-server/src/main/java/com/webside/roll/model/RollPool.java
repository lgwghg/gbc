/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * entity
 * 
 * @author zhangfei
 * @date 2017-04-18 11:05:16
 */
public class RollPool implements Serializable {
	private static final long serialVersionUID = 1L;

	public RollPool() {
	}

	private String id;// 主键ID
	private String roomId;// 房间ID
	private Long gold;// 金币
	private String exchangeId;// CD_KEY兑换码
	private String addTime;// 创建时间
	
	private String goodsName;// cdk商品
	private String userId;// 创建人

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}

	public Long getGold() {
		return this.gold;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof RollPool)) {
			return false;
		}
		RollPool castOther = (RollPool) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}

}