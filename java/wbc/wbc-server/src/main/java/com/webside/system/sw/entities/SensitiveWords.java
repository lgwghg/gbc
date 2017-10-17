/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sw.entities;

import java.io.Serializable;

/**
 *  entity
 *
 * @author zengxn
 * @date 2016-11-24 16:21:48
 */
public class SensitiveWords implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 所属类型
	 */
	private String contentType;
	
	/**
	 * 所属类型显示值
	 */
	private String contentTypeName;
	
	/**
	 * 所属类型样式值
	 */
	private String contentTypeClass;
	
	/**
	 * 作用域类型
	 */
	private String useType;
	
	/**
	 * 作用域类型显示值
	 */
	private String useTypeName;
	
	/**
	 * 作用域类型样式值
	 */
	private String useTypeClass;
	
	/**
	 * 创建者
	 */
	private String createOperatorId;
	
	/**
	 * 创建者名称
	 */
	private String createOperatorName;

	/**
	 * 添加时间
	 */
	private String createTime;

	/**
	 * 修改时间
	 */
	private String updateTime;

	/**
	 * 修改人
	 */
	private String updateOperatorId;
	
	/**
	 * 修改名称
	 */
	private String updateOperatorName;
	
	private String beginCreateTime;		// 开始 创建时间
	private String endCreateTime;		// 结束 创建时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentTypeName() {
		return contentTypeName;
	}
	public void setContentTypeName(String contentTypeName) {
		this.contentTypeName = contentTypeName;
	}
	public String getContentTypeClass() {
		return contentTypeClass;
	}
	public void setContentTypeClass(String contentTypeClass) {
		this.contentTypeClass = contentTypeClass;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getUseTypeName() {
		return useTypeName;
	}
	public void setUseTypeName(String useTypeName) {
		this.useTypeName = useTypeName;
	}
	public String getUseTypeClass() {
		return useTypeClass;
	}
	public void setUseTypeClass(String useTypeClass) {
		this.useTypeClass = useTypeClass;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateOperatorId() {
		return updateOperatorId;
	}
	public void setUpdateOperatorId(String updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}
	public String getUpdateOperatorName() {
		return updateOperatorName;
	}
	public void setUpdateOperatorName(String updateOperatorName) {
		this.updateOperatorName = updateOperatorName;
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