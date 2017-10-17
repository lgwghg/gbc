package com.webside.role.model;

import java.io.Serializable;
import java.sql.Date;

public class RoleUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6519812954425375306L;
	private String id;
	private String userId;
	private String roleId;
	private Date createTime;
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
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
