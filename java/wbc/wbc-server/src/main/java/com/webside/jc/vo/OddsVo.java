package com.webside.jc.vo;

import java.io.Serializable;

public class OddsVo implements Serializable{
	public OddsVo() {
		this.event = "odds";
	}
	
	public OddsVo(String id,String homeRule,String awayRule) {
		this.id = id;
		this.homeRule = homeRule;
		this.awayRule = awayRule;
		this.event = "odds";
	}

	private static final long serialVersionUID = 1L;
	
	private String event;
	
	private String id;
	
	private String homeRule;
	
	private String awayRule;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHomeRule() {
		return homeRule;
	}

	public void setHomeRule(String homeRule) {
		this.homeRule = homeRule;
	}

	public String getAwayRule() {
		return awayRule;
	}

	public void setAwayRule(String awayRule) {
		this.awayRule = awayRule;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
