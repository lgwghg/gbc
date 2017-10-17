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

import com.webside.match.entities.GameEvent;

/**
 * 游戏赛事服务接口
 *
 * @author zengxn
 * @date 2016-11-23 17:36:43
 */
public interface IGameEventService 
{
	/**
	 * 按条件查询游戏赛事
	 * @throws Exception
	 * @author zengxn
	 */
	public List<GameEvent> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增游戏赛事
	 * @param GameEvent
	 * @throws Exception
	 * @author zengxn
	 */
	public int insert(final GameEvent entity);
	
	/**
	 * 修改游戏赛事
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	public int update(final GameEvent entity);
	
	/**
	 * 根据ID获取游戏赛事
	 * @param ID
	 * @throws Exception
	 * @author zengxn
	 */
	public GameEvent findById(String id);
	
	/**
	 * 根据名称查询赛事，如id不为空且需要排除id
	 * @param eventName
	 * @param id
	 * @author zengxn
	 */
	public int findByEventName(String eventName,String id);
	
	/**
	 * 按条件查询赛事部分字段
	 * @throws Exception
	 * @author zengxn
	 */
	public List<GameEvent> queryPartListByPage(Map<String, Object> parameter);
	
	/**
	 * 按游戏id查询赛事
	 * @throws Exception
	 * @author zengxn
	 */
	public List<GameEvent> findListByGameId(String gameId);
	
	/**
	 * 竞猜页面赛事列表
	 * @throws Exception
	 * @author zengxn
	 */
	public List<GameEvent> gbGameEventList(Map<String, Object> parameter);
}
