/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.task.entities;

import java.io.Serializable;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2017-04-14 16:28:17
 */
public class UserTask implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UserTask(){}
	

	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * 用户名称
	 */
	private String userId;
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	/**
	 * 任务id
	 */
	private String taskId;
	
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}
	
	public String getTaskId(){
		return this.taskId;
	}

	/**
	 * 任务完成次数
	 */
	private Integer completeNum;
	
	public void setCompleteNum(Integer completeNum){
		this.completeNum = completeNum;
	}
	
	public Integer getCompleteNum(){
		return this.completeNum;
	}

	/**
	 * 任务创建时间
	 */
	private Long createTime;
	
	public void setCreateTime(Long createTime){
		this.createTime = createTime;
	}
	
	public Long getCreateTime(){
		return this.createTime;
	}

	/**
	 * 奖励金币
	 */
	private Integer gold;
	
	public void setGold(Integer gold){
		this.gold = gold;
	}
	
	public Integer getGold(){
		return this.gold;
	}

	/**
	 * 任务完成的状态，0：未完成,1：已完成 ,2：已领取   默认0
	 */
	private Integer completedState;
	
	public void setCompletedState(Integer completedState){
		this.completedState = completedState;
	}
	
	public Integer getCompletedState(){
		return this.completedState;
	}

}