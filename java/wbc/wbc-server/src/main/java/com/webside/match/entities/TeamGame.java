/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.match.entities;

import java.io.Serializable;

/**
 *  entity
 *
 * @author zengxn
 * @date 2016-11-24 11:48:54
 */
public class TeamGame implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * 战队ID
	 */
	private String teamId;
	
	/**
	 * 战队名称
	 */
	private String teamName;
	
	/**
	 * 游戏ID
	 */
	private String gameId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}