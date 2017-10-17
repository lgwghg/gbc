package com.webside.jc.vo;

import java.io.Serializable;

import com.webside.jc.model.UserJc;

public class PankouVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 比赛对战ID
	 */
	private String gbId;
	
	/**
	 * 玩法类型 0：独赢  1：让分2：其他
	 */
	private String pankouType;
	
	/**
	 * 玩法名称
	 */
	private String pkName;
	
	/**
	 * 比赛赔率（主战队）
	 */
	private String pkHomeRule;
	
	/**
	 * 比赛赔率（副战队）
	 */
	private String pkAwayRule;
	
	/**
	 * 参与人数（主战队）
	 */
	private Integer pkHomePrt;
	
	/**
	 * 参与人数（副战队）
	 */
	private Integer pkAwayPrt;
	
	/**
	 * 下注比例
	 * */
	private String pkHomePrtNum;
	private String pkAwayPrtNum;
	
	/**
	 * 当前比赛平台收益
	 */
	private Long thisPkProfit;
	
	/**
	 * 主战队下注金额
	 */
	private Long pkHomePrtGold;
	
	/**
	 * 副战队下注金额
	 */
	private Long pkAwayPrtGold;
	
	/**
	 * 胜方战队ID
	 */
	private String pkVictory;
	
	/**
	 * 0：正常 1：取消
	 */
	private String pkStatus;
	
	/**
	 * 开始时间
	 */
	private String pkStartTime;
	
	/**
	 * 结束时间
	 */
	private String pkEndTime;
	
	/**
	 * 让分战队 1:主战队，2：客场战队
	 */
	private String pkRangFenTeam;
	
	/**
	 * 让分数
	 */
	private String pkRangfenNum;
	
	/**
	 * 局数 根据比赛BO几， 如果比赛bo3，此选项为 1，2，3，  整场比赛盘口为0
	 */
	private Integer inningNum;
	
	private String gbstate;	//lwh 盘口当前状态，0未开始，1进行中，2已结束，3已取消
	
	private UserJc userJc;	//lwh 用户下注信息
	
	private String currentUser;	//lwh当前用户

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGbId() {
		return gbId;
	}

	public void setGbId(String gbId) {
		this.gbId = gbId;
	}

	public String getPankouType() {
		return pankouType;
	}

	public void setPankouType(String pankouType) {
		this.pankouType = pankouType;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getPkHomeRule() {
		return pkHomeRule;
	}

	public void setPkHomeRule(String pkHomeRule) {
		this.pkHomeRule = pkHomeRule;
	}

	public String getPkAwayRule() {
		return pkAwayRule;
	}

	public void setPkAwayRule(String pkAwayRule) {
		this.pkAwayRule = pkAwayRule;
	}

	public Integer getPkHomePrt() {
		return pkHomePrt;
	}

	public void setPkHomePrt(Integer pkHomePrt) {
		this.pkHomePrt = pkHomePrt;
	}

	public Integer getPkAwayPrt() {
		return pkAwayPrt;
	}

	public void setPkAwayPrt(Integer pkAwayPrt) {
		this.pkAwayPrt = pkAwayPrt;
	}

	public Long getThisPkProfit() {
		return thisPkProfit;
	}

	public void setThisPkProfit(Long thisPkProfit) {
		this.thisPkProfit = thisPkProfit;
	}

	public Long getPkHomePrtGold() {
		return pkHomePrtGold;
	}

	public void setPkHomePrtGold(Long pkHomePrtGold) {
		this.pkHomePrtGold = pkHomePrtGold;
	}

	public Long getPkAwayPrtGold() {
		return pkAwayPrtGold;
	}

	public void setPkAwayPrtGold(Long pkAwayPrtGold) {
		this.pkAwayPrtGold = pkAwayPrtGold;
	}

	public String getPkVictory() {
		return pkVictory;
	}

	public void setPkVictory(String pkVictory) {
		this.pkVictory = pkVictory;
	}

	public String getPkStatus() {
		return pkStatus;
	}

	public void setPkStatus(String pkStatus) {
		this.pkStatus = pkStatus;
	}

	public String getPkStartTime() {
		return pkStartTime;
	}

	public void setPkStartTime(String pkStartTime) {
		this.pkStartTime = pkStartTime;
	}

	public String getPkEndTime() {
		return pkEndTime;
	}

	public void setPkEndTime(String pkEndTime) {
		this.pkEndTime = pkEndTime;
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

	public Integer getInningNum() {
		return inningNum;
	}

	public void setInningNum(Integer inningNum) {
		this.inningNum = inningNum;
	}

	public String getGbstate() {
		return gbstate;
	}

	public void setGbstate(String gbstate) {
		this.gbstate = gbstate;
	}

	public UserJc getUserJc() {
		return userJc;
	}

	public void setUserJc(UserJc userJc) {
		this.userJc = userJc;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public String getPkHomePrtNum() {
		return pkHomePrtNum;
	}

	public void setPkHomePrtNum(String pkHomePrtNum) {
		this.pkHomePrtNum = pkHomePrtNum;
	}

	public String getPkAwayPrtNum() {
		return pkAwayPrtNum;
	}

	public void setPkAwayPrtNum(String pkAwayPrtNum) {
		this.pkAwayPrtNum = pkAwayPrtNum;
	}
}
