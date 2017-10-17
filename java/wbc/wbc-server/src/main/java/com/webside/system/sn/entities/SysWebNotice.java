/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sn.entities;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * entity
 * 
 * @author zhangfei
 * @date 2016-12-22 18:11:20
 */
public class SysWebNotice implements Serializable {
	private static final long serialVersionUID = 1L;

	public SysWebNotice() {
	}

	/**
	 * 唯一标识
	 */
	private String id;
	private String code;// 编码

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 标题
	 */
	private String title;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	/**
	 * 通知内容
	 */
	private String content;

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	/**
	 * 创建时间
	 */
	private Long addTime;
	private String addTimeStr;

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public Long getAddTime() {
		return this.addTime;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	/**
	 * 添加人员
	 */
	private String sysUserId;
	private String sysUserName;

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getSysUserId() {
		return this.sysUserId;
	}

	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	/**
	 * 1.公告 2.更新 3.介绍 4.使用帮助 5.用户须知 6.反馈
	 */
	private String type;
	private Integer sequence; // 序列

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	/**
	 * 状态 1：有效 0 无效 默认1
	 */
	private String status;

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysWebNotice)) {
			return false;
		}
		SysWebNotice castOther = (SysWebNotice) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}

}