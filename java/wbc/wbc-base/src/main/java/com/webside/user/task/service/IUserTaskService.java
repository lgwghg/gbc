/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.task.service;

import java.util.List;
import java.util.Map;

import com.webside.user.task.entities.UserTask;
import com.webside.user.task.entities.UserTaskVo;

/**
 * 任务服务接口
 *
 * @author tianguifang
 * @date 2017-04-14 16:28:22
 */
public interface IUserTaskService 
{
	/*
	 * 按条件查询任务
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserTask> queryListByPage(Map<String, Object> parameter);
	/**
	 * 查询用户的任务列表
	 * @param parameter
	 * @return
	 * @author tianguifang
	 */
	List<UserTaskVo> queryUserTaskVoListByPg(Map<String, Object> parameter);
	/*
	 * 新增任务
	 * @param UserTask
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UserTask entity);
	
	/*
	 * 修改任务
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int update(final UserTask entity);
	
	/*
	 * 根据ID获取任务
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserTask findById(String id);
	
	/*
	 * 根据对象删除任务
	 * @param UserTask
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/**
	 * 用户任务
	 * @param userId
	 * @param taskType
	 * @return
	 */
	public int updateUserTask(String userId, Integer taskType, Integer signGold);
}
