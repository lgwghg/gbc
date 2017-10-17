package com.webside.system.sn.vo;

import java.io.Serializable;

public class NoticeVo  implements Serializable{
	
	public NoticeVo() {
		// TODO Auto-generated constructor stub
	}
	
	public NoticeVo(String id,String status) {
		this.id = id;
		this.status = status;
	}

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String value;
	
	private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
