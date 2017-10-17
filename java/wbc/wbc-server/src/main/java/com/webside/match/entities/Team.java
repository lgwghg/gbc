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
 * @date 2016-11-23 16:32:42
 */
public class Team implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 团队名称
	 */
	private String teamName;
	
	/**
	 * 战队图标
	 */
	private String teamIcon;
	
	/**
	 * 团队说明
	 */
	private String teamDec;
	
	/**
	 * 团队状态: 1:有效 0：无效 默认1
	 */
	private String teamStatus;
	
	/**
	 * 团队状态显示值
	 */
	private String teamStatusName;
	
	/**
	 * 团队状态样式值
	 */
	private String teamStatusClass;
	
	/**
	 * 排序
	 */
	private Integer sortNum;
	
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
	
	public void setId(String id){
		this.id = id;
	}
	public String getId(){
		return this.id;
	}
	public void setTeamName(String teamName){
		this.teamName = teamName;
	}
	public String getTeamName(){
		return this.teamName;
	}
	public void setTeamIcon(String teamIcon){
		this.teamIcon = teamIcon;
	}
	public String getTeamIcon(){
		return this.teamIcon;
	}
	public void setTeamDec(String teamDec){
		this.teamDec = teamDec;
	}
	public String getTeamDec(){
		return this.teamDec;
	}
	public void setTeamStatus(String teamStatus){
		this.teamStatus = teamStatus;
	}
	public String getTeamStatus(){
		return this.teamStatus;
	}
	public void setSortNum(Integer sortNum){
		this.sortNum = sortNum;
	}
	public Integer getSortNum(){
		return this.sortNum;
	}
	public String getTeamStatusName() {
		return teamStatusName;
	}
	public void setTeamStatusName(String teamStatusName) {
		this.teamStatusName = teamStatusName;
	}
	public String getTeamStatusClass() {
		return teamStatusClass;
	}
	public void setTeamStatusClass(String teamStatusClass) {
		this.teamStatusClass = teamStatusClass;
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