package com.webside.address.entities;

import java.io.Serializable;

/**
 * 城市
 * 
 * @author tianguifang
 * 
 */
public class City implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6371028161478157011L;
	private Long id;
	/**
	 * 省份id
	 */
	private String provinceId;
	/**
	 * 城市id
	 */
	private String cityId;
	/**
	 * 市名称
	 */
	private String city;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}
