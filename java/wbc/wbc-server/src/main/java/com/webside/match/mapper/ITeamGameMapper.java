/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.match.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.match.entities.TeamGame;

/**
 * 战队游戏关联数据访问接口
 *
 * @author zengxn
 * @date 2016-11-24 11:48:49
 */
@Repository
public interface ITeamGameMapper extends BaseMapper<TeamGame, String> 
{
	/**
	 * 根据战队ID删除t_team_game
	 * @throws Exception
	 * @author zengxn
	 */
	public void deleteByTeamId(String teamId);
	
	/**
	 * 根据游戏ID查询战队列表
	 * @throws Exception
	 * @author zengxn
	 */
	public List<TeamGame> findListByGameId(String gameId);
}
