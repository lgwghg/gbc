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
import com.webside.match.entities.GameEvent;

/**
 * 游戏赛事数据访问接口
 *
 * @author zengxn
 * @date 2016-11-23 17:36:43
 */
@Repository
public interface IGameEventMapper extends BaseMapper<GameEvent, String> 
{

	/**
	 * 根据名称查询赛事，如id不为空且需要排除id
	 * @param parameter
	 * @author zengxn
	 */
	public int findByEventName(Map<String, Object> parameter);
	
	/**
	 * 按条件查询赛事部分字段
	 * @throws Exception
	 * @author zengxn
	 */
	public List<GameEvent> queryPartListByPage(Map<String, Object> parameter);
	
	/**
	 * 竞猜页面赛事列表
	 * @throws Exception
	 * @author zengxn
	 */
	public List<GameEvent> gbGameEventList(Map<String, Object> parameter);
}
