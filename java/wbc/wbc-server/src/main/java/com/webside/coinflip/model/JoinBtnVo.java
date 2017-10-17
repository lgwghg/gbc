package com.webside.coinflip.model;

import java.io.Serializable;

public class JoinBtnVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5017212905523431897L;
	private String roomId;
	private String joinBtn;
	private String event;
	
	public JoinBtnVo(String roomId, String joinBtn, String event) {
		this.roomId = roomId;
		this.joinBtn = joinBtn;
		this.event = event;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getJoinBtn() {
		return joinBtn;
	}
	public void setJoinBtn(String joinBtn) {
		this.joinBtn = joinBtn;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	
}
