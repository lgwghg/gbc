package com.webside.address.entities;

import java.io.Serializable;

public class Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4472749763398008223L;
	/**
	 * 地址id
	 */
	private String id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 收货人姓名
	 */
	private String receiverName;
	/**
	 * 收货人联系电话
	 */
	private String receiverMobile;
	/**
	 * 省份id
	 */
	private String provinceId;
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 城市id
	 */
	private String cityId;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 区、县id
	 */
	private String areaId;
	/**
	 * 区、县名称
	 */
	private String areaName;
	/**
	 * 邮编
	 */
	private String zip;
	/**
	 * 区号
	 */
	private String code;
	/**
	 * 是否删除 默认0未删除；1已删除
	 */
	private Integer isDeleted;
	/**
	 * 是否是默认地址 默认0非默认地址；1是默认地址
	 */
	private Integer isDefault;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 更新时间
	 */
	private Long updateTime;

	private String addressDetail;

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

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

}
