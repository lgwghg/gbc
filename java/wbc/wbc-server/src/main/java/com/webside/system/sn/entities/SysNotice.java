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

/**
 *  entity
 *
 * @author zengxn
 * @date 2016-11-25 16:27:21
 */
public class SysNotice implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 通知内容
	 */
	private String content;
	
	/**
	 * 添加时间
	 */
	private String createTime;
	
	/**
	 * 创建者
	 */
	private String createOperatorId;
	
	/**
	 * 创建者名称
	 */
	private String createOperatorName;
	
	/**
	 * 状态 1：有效 0 无效 默认1
	 */
	private String status;
	
	/**
	 * 状态显示值
	 */
	private String statusName;
	
	/**
	 * 游戏状态样式值
	 */
	private String statusClass;
	
	private String beginCreateTime;		// 开始 创建时间
	private String endCreateTime;		// 结束 创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusClass() {
		return statusClass;
	}

	public void setStatusClass(String statusClass) {
		this.statusClass = statusClass;
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