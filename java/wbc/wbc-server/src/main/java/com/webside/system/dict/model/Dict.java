package com.webside.system.dict.model;

import java.io.Serializable;

import com.webside.user.model.UserEntity;

/**
 * 字典Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Dict implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String value;	// 数据值
	private String label;	// 标签名
	private String type;	// 类型
	private String description;// 描述
	private int sort;	// 排序
	private String parentId;//父Id
	private String labelClass;//文字列表样式
	private String delFlag;
	private UserEntity createBy;
	private String createDate;
	private UserEntity updateBy;
	private String updateDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public UserEntity getCreateBy() {
		return createBy;
	}
	public void setCreateBy(UserEntity createBy) {
		this.createBy = createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public UserEntity getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(UserEntity updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getLabelClass() {
		return labelClass;
	}
	public void setLabelClass(String labelClass) {
		this.labelClass = labelClass;
	}
}