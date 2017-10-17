package com.webside.pay.vo;

import java.io.Serializable;

public class PayMessageVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public PayMessageVo(String event,String status,Long goldNum,Long sysGoldNum) {
		this.event = event;
		this.status = status;
		this.goldNum = goldNum;
		this.sysGoldNum = sysGoldNum;
	}
	
	public PayMessageVo(String event,String status) {
		this.event = event;
		this.status = status;
	}
	
	public PayMessageVo() {}

	private String event;
	
	private String status;
	
	private Long goldNum;
	
	private Long sysGoldNum;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getGoldNum() {
		return goldNum;
	}

	public void setGoldNum(Long goldNum) {
		this.goldNum = goldNum;
	}

	public Long getSysGoldNum() {
		return sysGoldNum;
	}

	public void setSysGoldNum(Long sysGoldNum) {
		this.sysGoldNum = sysGoldNum;
	}

}
