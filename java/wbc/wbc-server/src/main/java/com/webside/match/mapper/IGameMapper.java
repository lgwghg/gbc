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
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.match.entities.Game;

/**
 * 游戏数据访问接口
 *
 * @author zengxn
 * @date 2016-11-23 11:31:27
 */
@Repository
public interface IGameMapper extends BaseMapper<Game, String> 
{
	/**
	 * 根据名称查询游戏，如id不为空且需要排除id
	 * @param parameter
	 * @author zengxn
	 */
	public int findByGameName(Map<String, Object> parameter);
	
	/**
	 * 根据英文名称查询游戏，如id不为空且需要排除id
	 * @param parameter
	 * @author zengxn
	 */
	public Game findByEnglishName(Map<String, Object> parameter);

	/**
	 * 按条件查询游戏部分字段
	 * @throws Exception
	 * @author zengxn
	 */
	public List<Game> queryPartListByPage(Map<String, Object> parameter);
}
