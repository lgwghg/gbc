package com.webside.user.task.entities;

import java.io.Serializable;

public class UserTaskVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7282742796825856627L;
	private Task task;
	private UserTask userTask;
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public UserTask getUserTask() {
		return userTask;
	}
	public void setUserTask(UserTask userTask) {
		this.userTask = userTask;
	}
	
	
}
