/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.system.seo.entities;

import java.io.Serializable;

/**
 *  entity
 *
 * @author zengxn
 * @date 2016-10-19 17:36:52
 */
public class SeoConfig implements Serializable
{
	private static final long serialVersionUID = 1L;

	public SeoConfig(){}
	

	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * keywords
	 */
	private String keywords;
	
	/**
	 * description
	 */
	private String description;
	
	/**
	 * title
	 */
	private String title;
	
	/**
	 * 类别: 1通用 2首页
	 */
	private String type;
	
	private String typeName;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 修改时间
	 */
	private String updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
}