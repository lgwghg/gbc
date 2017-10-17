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

import com.webside.match.entities.Team;

/**
 * 战队服务接口
 *
 * @author zengxn
 * @date 2016-11-23 16:32:39
 */
public interface ITeamService 
{
	/**
	 * 按条件查询战队
	 * @throws Exception
	 * @author zengxn
	 */
	public List<Team> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增战队
	 * @param Team
	 * @param ids
	 * @throws Exception
	 * @author zengxn
	 */
	public int insert(final Team entity,List<String> ids);
	
	/**
	 * 修改战队
	 * @param entity
	 * @param ids
	 * @throws Exception
	 * @author zengxn
	 */
	public int update(final Team entity,List<String> ids);
	
	/**
	 * 根据ID获取战队
	 * @param ID
	 * @throws Exception
	 * @author zengxn
	 */
	public Team findById(String id);
	
	/**
	 * 根据名称查询战队，如id不为空且需要排除id
	 * @param teamName
	 * @param id
	 * @author zengxn
	 */
	public int findByTeamName(String teamName,String id);
}
