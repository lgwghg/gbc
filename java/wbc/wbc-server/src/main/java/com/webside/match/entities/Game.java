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
 * @date 2016-11-23 11:31:32
 */
public class Game implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;

	/**
	 * 主题名称
	 */
	private String gameName;
	
	/**
	 * 大图片
	 */
	private String bigImg;

	/**
	 * 小图片
	 */
	private String littleImg;
	
	/**
	 * 背景图片
	 */
	private String bgImg;

	/**
	 * 排序
	 */
	private Integer sortNum;

	/**
	 * 游戏状态   默认值1	0：无效 1 有效
	 */
	private String gameStatus;
	
	/**
	 * 游戏状态显示值
	 */
	private String gameStatusName;
	
	/**
	 * 游戏状态样式值
	 */
	private String gameStatusClass;

	/**
	 * 英文简称
	 */
	private String englishName;

	/**
	 * 创建者
	 */
	private String createOperatorId;
	
	/**
	 * 创建者名称
	 */
	private String createOperatorName;

	/**
	 * 添加时间
	 */
	private String createTime;

	/**
	 * 修改时间
	 */
	private String updateTime;

	/**
	 * 修改人
	 */
	private String updateOperatorId;
	
	/**
	 * 修改名称
	 */
	private String updateOperatorName;
	
	private String beginCreateTime;		// 开始 创建时间
	private String endCreateTime;		// 结束 创建时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getBigImg() {
		return bigImg;
	}
	public void setBigImg(String bigImg) {
		this.bigImg = bigImg;
	}
	public String getLittleImg() {
		return littleImg;
	}
	public void setLittleImg(String littleImg) {
		this.littleImg = littleImg;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public String getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}
	public String getGameStatusName() {
		return gameStatusName;
	}
	public void setGameStatusName(String gameStatusName) {
		this.gameStatusName = gameStatusName;
	}
	public String getGameStatusClass() {
		return gameStatusClass;
	}
	public void setGameStatusClass(String gameStatusClass) {
		this.gameStatusClass = gameStatusClass;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateOperatorId() {
		return updateOperatorId;
	}
	public void setUpdateOperatorId(String updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
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
	public String getUpdateOperatorName() {
		return updateOperatorName;
	}
	public void setUpdateOperatorName(String updateOperatorName) {
		this.updateOperatorName = updateOperatorName;
	}
	public String getBgImg() {
		return bgImg;
	}
	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}
}