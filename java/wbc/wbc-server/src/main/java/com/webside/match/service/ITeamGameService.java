/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.match.service;

import java.util.List;
import java.util.Map;

import com.webside.match.entities.TeamGame;

/**
 * 战队游戏关联服务接口
 *
 * @author zengxn
 * @date 2016-11-24 11:48:49
 */
public interface ITeamGameService 
{
	/**
	 * 按条件查询战队游戏关联
	 * @throws Exception
	 * @author zengxn
	 */
	public List<TeamGame> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增修改战队游戏关联
	 * @param teamId
	 * @param ids
	 * @author zengxn
	 */
	public boolean addTeamGameBatch(String teamId, List<String> ids);
	
	/**
	 * 按游戏id查询战队游戏关联
	 * @throws Exception
	 * @author zengxn
	 */
	public List<TeamGame> findListByGameId(String gameId);
}
