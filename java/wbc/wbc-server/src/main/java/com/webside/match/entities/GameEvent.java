/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.match.entities;

import java.io.Serializable;

/**
 *  entity
 *
 * @author zengxn
 * @date 2016-11-23 17:36:46
 */
public class GameEvent implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 游戏ID
	 */
	private String gameId;
	
	/**
	 * 游戏名称
	 */
	private String gameName;
	
	/**
	 * 赛事名称
	 */
	private String eventName;
	
	/**
	 * 赛事图片
	 */
	private String eventImg;
	
	/**
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 状态 1：开始 0：结束 默认1
	 */
	private String geStatus;
	
	/**
	 * 赛事状态显示值
	 */
	private String geStatusName;
	
	/**
	 * 赛事状态样式值
	 */
	private String geStatusClass;
	
	/**
	 * 创建者
	 */
	private String createOperatorId;
	
	/**
	 * 创建者名称
	 */
	private String createOperatorName;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 修改人
	 */
	private String updateOperatorId;
	
	/**
	 * 修改名称
	 */
	private String updateOperatorName;
	
	/**
	 * 修改时间
	 */
	private String updateTime;
	
	private String beginCreateTime;		// 开始 创建时间
	private String endCreateTime;		// 结束 创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventImg() {
		return eventImg;
	}

	public void setEventImg(String eventImg) {
		this.eventImg = eventImg;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGeStatus() {
		return geStatus;
	}

	public void setGeStatus(String geStatus) {
		this.geStatus = geStatus;
	}

	public String getGeStatusName() {
		return geStatusName;
	}

	public void setGeStatusName(String geStatusName) {
		this.geStatusName = geStatusName;
	}

	public String getGeStatusClass() {
		return geStatusClass;
	}

	public void setGeStatusClass(String geStatusClass) {
		this.geStatusClass = geStatusClass;
	}

	public String getCreateOperatorId() {
		return createOperatorId;
	}

	public void setCreateOperatorId(String createOperatorId) {
		this.createOperatorId = createOperatorId;
	}

	public String getCreateOperatorName() {
		return createOperatorName;
	}

	public void setCreateOperatorName(String createOperatorName) {
		this.createOperatorName = createOperatorName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateOperatorId() {
		return updateOperatorId;
	}

	public void setUpdateOperatorId(String updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}

	public String getUpdateOperatorName() {
		return updateOperatorName;
	}

	public void setUpdateOperatorName(String updateOperatorName) {
		this.updateOperatorName = updateOperatorName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
}