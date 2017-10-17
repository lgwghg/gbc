package com.webside.user.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 
 * @ClassName: UserInfoEntity
 * @Description: 用户基本信息
 * @author gaogang
 * @date 2016年7月12日 下午2:38:43
 *
 */
@Alias("userInfoEntity")
public class UserInfoEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	private String id;
	/* 
	 * 性别
	 */
	private Integer sex;
	/*
	 * 出生日期
	 */
	private Date birthday;
	/*
	 * 手机
	 */
	private String telephone;
	/*
	 * 邮箱
	 */
	private String email;
	/*
	 * 联系地址
	 */
	private String address;
	/*
	 * 添加日期时间
	 */
	private Long createTime;

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", sex=" + sex + ", birthday=" + birthday
				+ ", telephone=" + telephone + ", email=" + email
				+ ", address=" + address + ", createTime=" + createTime + "]";
	}
	
	
	
}
