package com.webside.jc.vo;

import java.io.Serializable;
import java.util.List;

import com.webside.match.entities.Game;
import com.webside.match.entities.GameEvent;
import com.webside.match.entities.Team;

public class GameBattleVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String geId;			//赛事ID
	private GameEvent gevent;		
	private String gameId;			//游戏ID
	private Game game;
	private String gbName;			//对战名称
	private String gameRule;		//比赛规则 1：BO1 2:BO2 3:BO3 5:BO5 7:BO7
	private String gameRuleName;	//比赛规则名称
	private String homeTeam;		//主场战队ID
	private Team  ht;
	private String awayTeam;		//客场战队ID
	private Team  at;
	private String startTime;		//开始时间
	private String endTime;			//结束时间
	private String gbStatus;		//状态  0：正常 1：删除
	private String gbType;			//比赛对战状态  1：未开始 2：进行中 3：已结束 4:已取消
	private Long sid;			//比赛查询ID

	/**
	 * weimo
	 */
	private String videoUrl;		//直播视频地址
	private String createUser;		//创建人员
	private String createUserName;	//创建人员名称
	private String createDate;		//创建时间
	private String updateUser;		//修改人
	private String updateUserName;	//修改人员名称	
	private String updateDate;		//修改时间	
	private long thisGbProfit;		//当前比赛平台收益
	private long homePrtGold;		//主战队下注金额
	private long awayPrtGold;		//副战队下注金额
	private String gbstate;			//当前最新状态 0：未开始 1：进行中 2：已结束 3:已取消
	
	/**
	 * lwh
	 */
	private List<PankouVo> pankous;

	private String currentUser;	//lwh当前用户
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGeId() {
		return geId;
	}

	public void setGeId(String geId) {
		this.geId = geId;
	}

	public GameEvent getGevent() {
		return gevent;
	}

	public void setGevent(GameEvent gevent) {
		this.gevent = gevent;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getGbName() {
		return gbName;
	}

	public void setGbName(String gbName) {
		this.gbName = gbName;
	}

	public String getGameRule() {
		return gameRule;
	}

	public void setGameRule(String gameRule) {
		this.gameRule = gameRule;
	}

	public String getGameRuleName() {
		return gameRuleName;
	}

	public void setGameRuleName(String gameRuleName) {
		this.gameRuleName = gameRuleName;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getHt() {
		return ht;
	}

	public void setHt(Team ht) {
		this.ht = ht;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Team getAt() {
		return at;
	}

	public void setAt(Team at) {
		this.at = at;
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

	public String getGbStatus() {
		return gbStatus;
	}

	public void setGbStatus(String gbStatus) {
		this.gbStatus = gbStatus;
	}

	public String getGbType() {
		return gbType;
	}

	public void setGbType(String gbType) {
		this.gbType = gbType;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public long getThisGbProfit() {
		return thisGbProfit;
	}

	public void setThisGbProfit(long thisGbProfit) {
		this.thisGbProfit = thisGbProfit;
	}

	public long getHomePrtGold() {
		return homePrtGold;
	}

	public void setHomePrtGold(long homePrtGold) {
		this.homePrtGold = homePrtGold;
	}

	public long getAwayPrtGold() {
		return awayPrtGold;
	}

	public void setAwayPrtGold(long awayPrtGold) {
		this.awayPrtGold = awayPrtGold;
	}

	public List<PankouVo> getPankous() {
		return pankous;
	}

	public void setPankous(List<PankouVo> pankous) {
		this.pankous = pankous;
	}

	public String getGbstate() {
		return gbstate;
	}

	public void setGbstate(String gbstate) {
		this.gbstate = gbstate;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}
	
}
