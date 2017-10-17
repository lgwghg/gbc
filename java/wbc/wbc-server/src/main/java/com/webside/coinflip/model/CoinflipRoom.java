/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.coinflip.model;

import java.io.Serializable;
import java.util.List;

/**
 * entity
 *
 * @author tianguifang
 * @date 2017-07-26 14:42:45
 */
public class CoinflipRoom implements Serializable {
	private static final long serialVersionUID = 1L;
	private String event;

	public CoinflipRoom() {
		this.event = "coinfliproom";
	}

	public CoinflipRoom(String id, String event) {
		this.event = event;
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 房间创建者，拥有者
	 */
	private CoinflipUser roomOwner;

	/**
	 * 房间参与者
	 */
	private List<CoinflipUser> roomJoinners;

	// 1是正面赢，0是反面赢
	private Integer winner;

	private List<CoinflipUser> winners;

	/**
	 * 随机数，小于0.5正方赢，否则反方赢
	 */
	private Double random;

	/**
	 * key
	 */
	private String key;

	/**
	 * 由random和key通过MD5加密获得的hash值
	 */
	private String hashCode;

	/**
	 * 比赛下注总金额
	 */
	private Integer totalAmount;

	/**
	 * 比赛下注总金币数
	 */
	private Integer totalGoldNum;

	// 房主下注金币数
	private Integer ownerGoldNum;

	/**
	 * 房间密码
	 */
	private String password;

	// 是否有密码
	private Integer hasPassword;

	/**
	 * 状态：0初始状态 1：已加入 2：未开奖 3：已开奖
	 */
	private Integer status;

	/**
	 * 房间创建时间
	 */
	private Long createTime;

	/**
	 * 更新时间
	 */
	private Long updateTime;

	/**
	 * 创建人id
	 */
	private String creatorId;

	/**
	 * 更新人id
	 */
	private String updatorId;

	private Long joinTime;

	private Long openTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CoinflipUser getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(CoinflipUser roomOwner) {
		this.roomOwner = roomOwner;
	}

	public List<CoinflipUser> getRoomJoinners() {
		return roomJoinners;
	}

	public void setRoomJoinners(List<CoinflipUser> roomJoinners) {
		this.roomJoinners = roomJoinners;
	}

	public Integer getWinner() {
		return winner;
	}

	public void setWinner(Integer winner) {
		this.winner = winner;
	}

	public List<CoinflipUser> getWinners() {
		return winners;
	}

	public void setWinners(List<CoinflipUser> winners) {
		this.winners = winners;
	}

	public Double getRandom() {
		return random;
	}

	public void setRandom(Double random) {
		this.random = random;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalGoldNum() {
		return totalGoldNum;
	}

	public void setTotalGoldNum(Integer totalGoldNum) {
		this.totalGoldNum = totalGoldNum;
	}

	public Integer getOwnerGoldNum() {
		return ownerGoldNum;
	}

	public void setOwnerGoldNum(Integer ownerGoldNum) {
		this.ownerGoldNum = ownerGoldNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getHasPassword() {
		return hasPassword;
	}

	public void setHasPassword(Integer hasPassword) {
		this.hasPassword = hasPassword;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getUpdatorId() {
		return updatorId;
	}

	public void setUpdatorId(String updatorId) {
		this.updatorId = updatorId;
	}

	public Long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Long joinTime) {
		this.joinTime = joinTime;
	}

	public Long getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Long openTime) {
		this.openTime = openTime;
	}
}