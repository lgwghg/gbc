package com.webside.jc.model;

import java.io.Serializable;

import com.webside.user.model.UserEntity;

/**
 * @title : 用户竞猜实体类
 * @author LiGan
 * */

public class UserJc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String userId; // 用户ID
	private UserEntity user;
	private String gbId; // 比赛对战ID
	private String pankouId; //盘口ID
	private long jcGold; // 竞猜G币
	private String jcTeamType; // 竞猜战队分类 1:主战队，2：客场战队
	private String gameResult; // 比赛结果
	private long victoryGoldNum; // 用户赢得G币数量
	private String jcTime; // 竞猜时间

	private Integer inningNum; //盘口局数
	private String pkName; //盘口玩法名称
	private String pankouType; // 盘口玩法类型
	private String pkRangFenTeam; // 盘口让分战队
	private String pkRangfenNum; // 盘口让分数
	private long expectGold;// 预计收益
	private String gameImg;// 游戏小图片
	private String homeTeam;// 主场战队
	private String awayTeam;// 客场战队
	private String eventName;// 赛事
	private String gameRule;// 比赛规则
	private Long startTime;// 比赛开始时间
	private Long endTime;// 比赛结束时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGbId() {
		return gbId;
	}

	public void setGbId(String gbId) {
		this.gbId = gbId;
	}

	public long getJcGold() {
		return jcGold;
	}

	public void setJcGold(long jcGold) {
		this.jcGold = jcGold;
	}

	public String getJcTeamType() {
		return jcTeamType;
	}

	public void setJcTeamType(String jcTeamType) {
		this.jcTeamType = jcTeamType;
	}

	public String getGameResult() {
		return gameResult;
	}

	public void setGameResult(String gameResult) {
		this.gameResult = gameResult;
	}

	public long getVictoryGoldNum() {
		return victoryGoldNum;
	}

	public void setVictoryGoldNum(long victoryGoldNum) {
		this.victoryGoldNum = victoryGoldNum;
	}

	public String getJcTime() {
		return jcTime;
	}

	public void setJcTime(String jcTime) {
		this.jcTime = jcTime;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public long getExpectGold() {
		return expectGold;
	}

	public void setExpectGold(long expectGold) {
		this.expectGold = expectGold;
	}

	public String getGameImg() {
		return gameImg;
	}

	public void setGameImg(String gameImg) {
		this.gameImg = gameImg;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getGameRule() {
		return gameRule;
	}

	public void setGameRule(String gameRule) {
		this.gameRule = gameRule;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getPankouId() {
		return pankouId;
	}

	public void setPankouId(String pankouId) {
		this.pankouId = pankouId;
	}

	public Integer getInningNum() {
		return inningNum;
	}

	public void setInningNum(Integer inningNum) {
		this.inningNum = inningNum;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getPankouType() {
		return pankouType;
	}

	public void setPankouType(String pankouType) {
		this.pankouType = pankouType;
	}

	public String getPkRangFenTeam() {
		return pkRangFenTeam;
	}

	public void setPkRangFenTeam(String pkRangFenTeam) {
		this.pkRangFenTeam = pkRangFenTeam;
	}

	public String getPkRangfenNum() {
		return pkRangfenNum;
	}

	public void setPkRangfenNum(String pkRangfenNum) {
		this.pkRangfenNum = pkRangfenNum;
	}

}
