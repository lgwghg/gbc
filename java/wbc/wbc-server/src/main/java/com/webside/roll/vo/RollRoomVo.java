package com.webside.roll.vo;

import java.io.Serializable;
import java.util.List;

public class RollRoomVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // 主键
	private String roomCode; // 自定义房间号
	private String userId; // 房主
	private String userName; // 房主昵称
	private String userPhoto; // 房主头像
	private String sign; // 简介
	private String startTime; // 开始时间
	private String endTime; // 结束时间
	private String remarks; // 简介
	private String roomUrl; 
	private String sinaUrl; 
	private String qqNum; 
	private String status; 
	
	private List<RollMemberVo> memberList; // 参与人员

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRoomUrl() {
		return roomUrl;
	}

	public void setRoomUrl(String roomUrl) {
		this.roomUrl = roomUrl;
	}

	public String getSinaUrl() {
		return sinaUrl;
	}

	public void setSinaUrl(String sinaUrl) {
		this.sinaUrl = sinaUrl;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RollMemberVo> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<RollMemberVo> memberList) {
		this.memberList = memberList;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	
}
