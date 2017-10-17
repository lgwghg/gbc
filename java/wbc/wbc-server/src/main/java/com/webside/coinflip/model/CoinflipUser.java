/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.coinflip.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * entity
 *
 * @author tianguifang
 * @date 2017-07-26 14:41:58
 */
public class CoinflipUser implements Serializable {
	private static final long serialVersionUID = 1L;

	public CoinflipUser() {
		this.event = "coinflipuser";
	}

	private String event;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	private Integer winGoldNum;
	
	public Integer getWinGoldNum() {
		return winGoldNum;
	}

	public void setWinGoldNum(Integer winGoldNum) {
		this.winGoldNum = winGoldNum;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 用户id
	 */
	private String userId;

	// 用户昵称
	private String userNick;

	// 用户头像
	private String userPhoto;

	/**
	 * 房主 1：是房主 0：参与者 默认0
	 */
	private Integer roomOwner;

	/**
	 * 参与的金币数
	 */
	private Integer goldNum;

	/**
	 * 个人下注金币总额
	 */
	private Integer goldAmount;

	/**
	 * 用户正反面 0：正面 1：反面，房主可以选择任意一面，其他参与者则是另外一面
	 */
	private Integer isCoinFront;

	/**
	 * 状态 0：有效 1：无效
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 更新时间
	 */
	private Long updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserPhoto() {
		return getPhoto_65();
	}
	public String getPhoto_65() {
		String newPhoto = "";
		if (StringUtils.isNotBlank(userPhoto)) {
			if(userPhoto.lastIndexOf(".") < 0) return null;
			String name  = userPhoto.substring(0, userPhoto.lastIndexOf("."));
			String fix   = userPhoto.substring(userPhoto.lastIndexOf(".")+1, userPhoto.length());
			newPhoto = name + "_65." + fix;
		}
		return newPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public Integer getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(Integer roomOwner) {
		this.roomOwner = roomOwner;
	}

	public Integer getGoldNum() {
		return goldNum;
	}

	public void setGoldNum(Integer goldNum) {
		this.goldNum = goldNum;
	}

	public Integer getGoldAmount() {
		return goldAmount;
	}

	public void setGoldAmount(Integer goldAmount) {
		this.goldAmount = goldAmount;
	}

	public Integer getIsCoinFront() {
		return isCoinFront;
	}

	public void setIsCoinFront(Integer isCoinFront) {
		this.isCoinFront = isCoinFront;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

}