package com.webside.address.entities;

import java.io.Serializable;

/**
 * 省份
 * 
 * @author tianguifang
 * 
 */
public class Province implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1088156768543484020L;
	private Long id;
	/**
	 * 省份id
	 */
	private String provinceId;
	/**
	 * 省份名称
	 */
	private String province;

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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

}
