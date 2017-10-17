package com.webside.address.entities;

import java.io.Serializable;

/**
 * 区、县
 * 
 * @author tianguifang
 * 
 */
public class Area implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3885026728728971856L;
	private Long id;
	/**
	 * 城市id
	 */
	private String cityId;
	/**
	 * 区、县id
	 */
	private String areaId;
	/**
	 * 区、县名称
	 */
	private String area;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
