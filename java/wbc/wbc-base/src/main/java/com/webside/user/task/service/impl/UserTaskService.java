/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.task.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.task.service.IUserTaskService;
import com.webside.user.task.mapper.IUserTaskMapper;
import com.webside.user.task.entities.Task;
import com.webside.user.task.entities.UserTask;
import com.webside.user.task.entities.UserTaskVo;
import com.webside.util.IdGen;

/**
 * 任务服务实现类
 *
 * @author tianguifang
 * @date 2017-04-14 16:28:22
 */
@Service("userTaskService")
public class UserTaskService extends AbstractService<UserTask, String> implements IUserTaskService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userTaskMapper);
	}

	/**
	 * 任务 DAO定义
	 */
	@Autowired
	private IUserTaskMapper userTaskMapper;
	
	/**
	 * 查询用户的任务列表
	 * @param parameter
	 * @return
	 * @author tianguifang
	 */
	@Override
	public List<UserTaskVo> queryUserTaskVoListByPg(Map<String, Object> parameter) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		parameter.put("createTime", calendar.getTimeInMillis());
		return userTaskMapper.queryUserTaskVoListByPg(parameter);
	}
	
	@Override
	public int updateUserTask(String userId, Integer taskType, Integer signGold) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userId", userId);
		parameter.put("taskType", taskType);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		parameter.put("createTime", calendar.getTimeInMillis());
		List<UserTaskVo> taskVoList = userTaskMapper.queryUserTaskVoListByPg(parameter);
		if (!CollectionUtils.isEmpty(taskVoList)) {
			UserTaskVo vo = taskVoList.get(0);
			Task task = vo.getTask();
			UserTask userTask = vo.getUserTask();
			if (task != null) {
				if (userTask == null || StringUtils.isBlank(userTask.getId())) {
					// 新增
					userTask = new UserTask();
					userTask.setId(IdGen.uuid());
					userTask.setTaskId(task.getId());
					userTask.setUserId(userId);
					userTask.setCreateTime(System.currentTimeMillis());
					userTask.setCompleteNum(1);
					if (task.getNum() == userTask.getCompleteNum()) {
						if (task.getTaskType() == 0) {//签到状态直接是领取
							userTask.setCompletedState(2);
							userTask.setGold(signGold);
						} else {// 其他先是任务完成状态
							userTask.setCompletedState(1);
							userTask.setGold(task.getGold());
						}
					} else {
						userTask.setCompletedState(0);// 进行中
					}
					return userTaskMapper.insert(userTask);
				} else {
					// 编辑
					if (userTask.getCompletedState() == 0 && task.getType() == 0) {//每日任务，进行中的写数据
						UserTask uTask = new UserTask();
						uTask.setId(userTask.getId());
						uTask.setCompleteNum(userTask.getCompleteNum() + 1);
						if (task.getNum().equals(uTask.getCompleteNum())) {
							uTask.setCompletedState(1);
							uTask.setGold(task.getGold());
						} else {
							uTask.setCompletedState(0);
						}
						return userTaskMapper.update(uTask);
					}
				}
				
			}
			
		}
		return 0;
	}
}
