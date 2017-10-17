/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.match.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.exception.ServiceException;
import com.webside.match.entities.TeamGame;
import com.webside.match.mapper.ITeamGameMapper;
import com.webside.match.service.ITeamGameService;
import com.webside.util.IdGen;

/**
 * 战队游戏关联服务实现类
 *
 * @author zengxn
 * @date 2016-11-24 11:48:49
 */
@Service("teamGameService")
public class TeamGameService extends AbstractService<TeamGame, String> implements ITeamGameService 
{
	private Logger logger = Logger.getLogger(this.getClass());
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(teamGameMapper);
	}

	/**
	 * 战队游戏关联 DAO定义
	 */
	@Autowired
	private ITeamGameMapper teamGameMapper;

	@Override
	public boolean addTeamGameBatch(String teamId, List<String> ids) {
		boolean flag = false;
		try {
			//清除记录
			teamGameMapper.deleteByTeamId(teamId);
			//新增记录
			if (ids.size() > 0) {
				if (CollectionUtils.isNotEmpty(ids)) {
					int addResult = 0;
					for (String gameId : ids) {
						TeamGame teamGame = new TeamGame();
						teamGame.setId(IdGen.uuid());
						teamGame.setGameId(gameId);
						teamGame.setTeamId(teamId);
						int result = super.insert(teamGame);
						addResult += result;
					}
					if (addResult == ids.size()) {
						flag = true;
					}
				}
			} else {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("新增战队游戏关联出错：", e);
			throw new ServiceException(e);
		}
		return flag;
	}

	@Override
	public List<TeamGame> findListByGameId(String gameId) {
		return teamGameMapper.findListByGameId(gameId);
	}
}
