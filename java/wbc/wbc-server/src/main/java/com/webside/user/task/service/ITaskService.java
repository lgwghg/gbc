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
import com.webside.user.task.entities.Task;

/**
 * 任务服务接口
 *
 * @author tianguifang
 * @date 2017-04-14 16:27:23
 */
public interface ITaskService 
{
	/*
	 * 按条件查询任务
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<Task> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增任务
	 * @param Task
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final Task entity);
	
	/*
	 * 修改任务
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int update(final Task entity);
	
	/*
	 * 根据ID获取任务
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public Task findById(String id);
	
	/*
	 * 根据对象删除任务
	 * @param Task
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/**
	 * 任务完成更新
	 * @param param
	 * @return
	 */
	public Map<String, Object> updateCompletedTask(Map<String, Object> param);
}
