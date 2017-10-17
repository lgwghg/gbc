package com.webside.address.entities;

import java.io.Serializable;

/**
 * 邮编区号
 * 
 * @author tianguifang
 * 
 */
public class Zipcode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5936801976208309148L;
	private Long id;
	/**
	 * 区、县id
	 */
	private String areaId;
	/**
	 * 邮编
	 */
	private String zip;
	/**
	 * 区号
	 */
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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

}
