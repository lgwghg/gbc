package com.webside.user.model;

import java.io.Serializable;
import java.util.Date;

public class UserIncrement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 用户Id
	 */
	private String userId;
	/** 竞猜量 */
	private Integer jcNum;
	/** 胜利量 */
	private Integer victoryNum;
	/** 胜率 */
	private Double victoryRate;
	/** 累计盈利数量 */
	private Integer totalProfitGoldNum;
	/**
	 * 是否参与过竞猜
	 */
	private Integer isInvolvementInJc;
	/**
	 * 消息未读量
	 */
	private Integer unreadNum;
	/**
	 * 用户签到总数
	 */
	private Integer signNum;
	/**
	 * 
	 */
	private Long createTime;
	private Long updateTime;
	private String createOperatorId;
	private String updateOperatorId;
	private Integer isDeleted;
	private String nickName;
	private String mobile;
	private String email;
	private Date dateUpdateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getUnreadNum() {
		return unreadNum;
	}

	public void setUnreadNum(Integer unreadNum) {
		this.unreadNum = unreadNum;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateOperatorId() {
		return createOperatorId;
	}

	public void setCreateOperatorId(String createOperatorId) {
		this.createOperatorId = createOperatorId;
	}

	public String getUpdateOperatorId() {
		return updateOperatorId;
	}

	public void setUpdateOperatorId(String updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getDateUpdateTime() {
		if (this.updateTime != null) {
			return new Date(updateTime);
		}
		return dateUpdateTime;
	}

	public void setDateUpdateTime(Date dateUpdateTime) {
		this.dateUpdateTime = dateUpdateTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getJcNum() {
		return jcNum;
	}

	public void setJcNum(Integer jcNum) {
		this.jcNum = jcNum;
	}

	public Integer getVictoryNum() {
		return victoryNum;
	}

	public void setVictoryNum(Integer victoryNum) {
		this.victoryNum = victoryNum;
	}

	public Double getVictoryRate() {
		return victoryRate;
	}

	public void setVictoryRate(Double victoryRate) {
		this.victoryRate = victoryRate;
	}

	public Integer getTotalProfitGoldNum() {
		return totalProfitGoldNum;
	}

	public void setTotalProfitGoldNum(Integer totalProfitGoldNum) {
		this.totalProfitGoldNum = totalProfitGoldNum;
	}

	public Integer getSignNum() {
		return signNum;
	}

	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}

	public Integer getIsInvolvementInJc() {
		return isInvolvementInJc;
	}

	public void setIsInvolvementInJc(Integer isInvolvementInJc) {
		this.isInvolvementInJc = isInvolvementInJc;
	}
}
