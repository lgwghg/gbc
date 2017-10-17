/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.task.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.task.entities.Task;

/**
 * 任务数据访问接口
 *
 * @author tianguifang
 * @date 2017-04-14 16:27:23
 */
@Repository
public interface ITaskMapper extends BaseMapper<Task, String> 
{

	Task queryTaskByTaskType(Integer taskType);
	
}
