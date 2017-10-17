package com.webside.role.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 角色资源实体类
 * 
 * @author tianguifang
 * 
 */
public class RoleResource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7612797561170765444L;
	private String id;
	private String roleId;// 角色id
	private String resourceId; // 资源id
	private Date createTime;

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

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
