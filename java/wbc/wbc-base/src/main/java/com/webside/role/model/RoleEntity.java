package com.webside.role.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.webside.user.model.UserEntity;

/**
 * 
 * @ClassName: RoleEntity
 * @Description: 角色信息
 * @author gaogang
 * @date 2016年7月12日 下午2:39:54
 *
 */
@Alias("roleEntity")
public class RoleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 角色类型    0：官方网站组     1：普通用户组
	 */
	private Integer roleType;
	/*
	 * 角色名
	 */
	private String name;
	/*
	 * 角色key
	 */
	private String key;
	/*
	 * 角色状态
	 */
	private Integer status;
	/*
	 * 角色描述信息
	 */
	private String description;
	/*
	 * 角色创建时间
	 */
	private Date createTime;
	/*
	 * 角色更新时间
	 */
	private Date updateTime;
	/*
	 * 角色下所有用户列表结合
	 */
	private List<UserEntity> userList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<UserEntity> getUserList() {
		return userList;
	}

	public void setUserList(List<UserEntity> userList) {
		this.userList = userList;
	}
	
	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RoleEntity [id=" + id + ", roleType=" + roleType + ", name=" + name + ", key=" + key + ", status="
				+ status + ", description=" + description + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", userList="
				+ userList + "]";
	}

}