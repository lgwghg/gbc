package com.webside.roll.vo;

import java.io.Serializable;
import java.util.List;

import com.webside.roll.model.RollPool;

public class RollWinnerVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // 主键
	private String roomId; // 房间号
	private String winnerId; // 获奖人员
	private String winnerName; // 人员昵称
	private String winTime; // 获奖时间
	private String createTime; // 参与时间
	private String photo; // 头像
	private Integer num;// 获奖次数

	private List<RollPool> poolList; // 获奖奖品

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

	public String getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}

	public String getWinTime() {
		return winTime;
	}

	public void setWinTime(String winTime) {
		this.winTime = winTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<RollPool> getPoolList() {
		return poolList;
	}

	public void setPoolList(List<RollPool> poolList) {
		this.poolList = poolList;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
