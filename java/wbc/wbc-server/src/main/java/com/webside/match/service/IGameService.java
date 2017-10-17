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

import com.webside.match.entities.Game;

/**
 * 游戏服务接口
 *
 * @author zengxn
 * @date 2016-11-23 11:31:27
 */
public interface IGameService 
{
	/**
	 * 按条件查询游戏
	 * @throws Exception
	 * @author zengxn
	 */
	public List<Game> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增游戏
	 * @param Game
	 * @throws Exception
	 * @author zengxn
	 */
	public int insert(final Game entity);
	
	/**
	 * 修改游戏
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	public int update(final Game entity);
	
	/**
	 * 根据ID获取游戏
	 * @param ID
	 * @throws Exception
	 * @author zengxn
	 */
	public Game findById(String id);
	
	/**
	 * 根据名称查询游戏，如id不为空且需要排除id
	 * @param gameName
	 * @param id
	 * @author zengxn
	 */
	public int findByGameName(String gameName,String id);

	/**
	 * 根据英文名称查询游戏，如id不为空且需要排除id
	 * @param gameName
	 * @param id
	 * @author zengxn
	 */
	public Game findByEnglishName(String englishName,String id);
	
	/**
	 * 按条件查询游戏部分字段
	 * @throws Exception
	 * @author zengxn
	 */
	public List<Game> queryPartList(Map<String, Object> parameter);
	
	/**
	 * 按条件查询游戏部分字段，自定义分页
	 * @throws Exception
	 * @author zengxn
	 */
	public List<Game> queryPartListByPage(Map<String, Object> parameter);
}
