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
 * @date 2017-04-14 16:27:18
 */
public class Task implements Serializable
{
	private static final long serialVersionUID = 1L;

	public Task(){}
	

	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	private String taskName;
	
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}
	
	public String getTaskName(){
		return this.taskName;
	}

	/**
	 * 类型：1：一次性任务   0：每日任务
	 */
	private Integer type;
	/**
	 * 任务类型
	 */
	private Integer taskType;
	
	public void setTaskType(Integer taskType){
		this.taskType = taskType;
	}
	
	public Integer getTaskType(){
		return this.taskType;
	}

	private String image;
	
	public void setImage(String image){
		this.image = image;
	}
	
	public String getImage(){
		return this.image;
	}

	private String description;
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return this.description;
	}

	/**
	 * 任务需要完成的次数
	 */
	private Integer num;
	
	public void setNum(Integer num){
		this.num = num;
	}
	
	public Integer getNum(){
		return this.num;
	}

	/**
	 * 完成任务的奖励金币
	 */
	private Integer gold;
	
	public void setGold(Integer gold){
		this.gold = gold;
	}
	
	public Integer getGold(){
		return this.gold;
	}

	/**
	 * 是否是签到  默认 0，0：非签到   1：是签到
	 */
	private Integer isSign;
	
	public void setIsSign(Integer isSign){
		this.isSign = isSign;
	}
	
	public Integer getIsSign(){
		return this.isSign;
	}

	/**
	 * 状态：0有效    1：无效   默认0
	 */
	private Integer state;
	
	public void setState(Integer state){
		this.state = state;
	}
	
	public Integer getState(){
		return this.state;
	}

	/**
	 * 创建时间
	 */
	private Long createTime;
	
	public void setCreateTime(Long createTime){
		this.createTime = createTime;
	}
	
	public Long getCreateTime(){
		return this.createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}