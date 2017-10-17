/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.match.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.match.entities.Team;

/**
 * 战队数据访问接口
 *
 * @author zengxn
 * @date 2016-11-23 16:32:39
 */
@Repository
public interface ITeamMapper extends BaseMapper<Team, String> 
{
	/**
	 * 根据名称查询战队，如id不为空且需要排除id
	 * @param parameter
	 * @author zengxn
	 */
	public int findByTeamName(Map<String, Object> parameter);
}
