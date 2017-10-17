/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.task.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.task.entities.UserTask;
import com.webside.user.task.entities.UserTaskVo;

/**
 * 任务数据访问接口
 *
 * @author tianguifang
 * @date 2017-04-14 16:28:22
 */
@Repository
public interface IUserTaskMapper extends BaseMapper<UserTask, String> 
{

	List<UserTaskVo> queryUserTaskVoListByPg(Map<String, Object> parameter);
	
}
