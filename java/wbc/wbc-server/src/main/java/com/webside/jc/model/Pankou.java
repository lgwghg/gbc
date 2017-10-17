/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.jc.model;

import java.io.Serializable;

/**
 *  entity
 *
 * @author zengxn
 * @date 2017-02-18 13:36:23
 */
public class Pankou implements Serializable
{
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
	
	//玩法类型显示值
	private String pankouTypeName;
	
	//玩法类型样式值
	private String pankouTypeClass;
	
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
	
	//参与人数（主战队）占比
	private String pkHomePrtNum;
	
	/**
	 * 参与人数（副战队）
	 */
	private Integer pkAwayPrt;
	
	//参与人数（副战队）占比
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
	 * 盘口状态 0：正常 1：取消
	 */
	private String pkStatus;
	
	//盘口现状  1：未开始 2：进行中 3：已结束 4:已取消
	private String pkStatusType;
	
	//盘口现状显示值
	private String pkStatusTypeName;
	
	//盘口现状样式值
	private String pkStatusTypeClass;
	
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
	private String inningNum;
	
	//主场战队id
	private String homeTeamId;
	
	//主场战队名称
	private String homeTeamName;
	
	//客场战队id
	private String awayTeamId;
	
	//客场战队名称
	private String awayTeamName;

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

	public String getInningNum() {
		return inningNum;
	}

	public void setInningNum(String inningNum) {
		this.inningNum = inningNum;
	}

	public String getPkStatusType() {
		return pkStatusType;
	}

	public void setPkStatusType(String pkStatusType) {
		this.pkStatusType = pkStatusType;
	}

	public String getPankouTypeName() {
		return pankouTypeName;
	}

	public void setPankouTypeName(String pankouTypeName) {
		this.pankouTypeName = pankouTypeName;
	}

	public String getPankouTypeClass() {
		return pankouTypeClass;
	}

	public void setPankouTypeClass(String pankouTypeClass) {
		this.pankouTypeClass = pankouTypeClass;
	}

	public String getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(String homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getAwayTeamId() {
		return awayTeamId;
	}

	public void setAwayTeamId(String awayTeamId) {
		this.awayTeamId = awayTeamId;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public String getPkStatusTypeName() {
		return pkStatusTypeName;
	}

	public void setPkStatusTypeName(String pkStatusTypeName) {
		this.pkStatusTypeName = pkStatusTypeName;
	}

	public String getPkStatusTypeClass() {
		return pkStatusTypeClass;
	}

	public void setPkStatusTypeClass(String pkStatusTypeClass) {
		this.pkStatusTypeClass = pkStatusTypeClass;
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