package com.webside.user.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author tianguifang
 *
 */
public class UserRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5381799053007154413L;
	private String id;
	private String userId;
	private String roleId;
	private Date createTime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
