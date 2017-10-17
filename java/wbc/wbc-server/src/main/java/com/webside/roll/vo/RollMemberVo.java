package com.webside.roll.vo;

import java.io.Serializable;

public class RollMemberVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // 主键
	private String roomId; // 房间号
	private String memberId; // 参与人员
	private String memberName; // 人员昵称
	private String photo; // 头像

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
