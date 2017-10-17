/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.task.service.ITaskService;
import com.webside.user.task.service.IUserTaskService;
import com.webside.user.task.mapper.ITaskMapper;
import com.webside.user.task.entities.Task;
import com.webside.user.task.entities.UserTask;
import com.webside.user.task.entities.UserTaskVo;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;

/**
 * 任务服务实现类
 *
 * @author tianguifang
 * @date 2017-04-14 16:27:23
 */
@Service("taskService")
public class TaskService extends AbstractService<Task, String> implements ITaskService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(taskMapper);
	}

	/**
	 * 任务 DAO定义
	 */
	@Autowired
	private ITaskMapper taskMapper;
	@Autowired
	private IUserTaskService userTaskService;
	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	
	/**
	 * 任务完成更新
	 */
	@Override
	public Map<String, Object> updateCompletedTask(Map<String, Object> param) {
		List<UserTaskVo> taskVoList = userTaskService.queryUserTaskVoListByPg(param);
		Map<String, Object> resultMap = new HashMap<>();
		if (!CollectionUtils.isEmpty(taskVoList)) {
			Integer taskType = (Integer)param.get("taskType");
			String userTaskId = "";
			UserTaskVo vo = taskVoList.get(0);
			int result = 0;
			if (vo.getUserTask() != null) {
				UserTask ut = new UserTask();
				userTaskId = vo.getUserTask().getId();
				ut.setId(userTaskId);
				ut.setCompletedState(2);
				ut.setGold(vo.getTask().getGold());
				result = userTaskService.update(ut);
				resultMap.put("gold", vo.getUserTask().getGold());
			} else {
				UserTask ut = new UserTask();
				userTaskId = IdGen.uuid();
				ut.setId(userTaskId);
				ut.setCompletedState(2);
				ut.setGold(vo.getTask().getGold());
				ut.setUserId(ShiroAuthenticationManager.getUserId());
				ut.setTaskId(vo.getTask().getId());
				ut.setCompleteNum(vo.getTask().getNum());
				ut.setCreateTime(System.currentTimeMillis());
				result = userTaskService.insert(ut);
				resultMap.put("gold", ut.getGold());
			}
			if (result > 0) {
				userWalletService.updateRechargeWallet(ShiroAuthenticationManager.getUserId(), Long.valueOf(vo.getTask().getGold()), GlobalConstant.RECHARGE_TYPE_GIFT);
				// 更新交易记录
				UserTransactionLog transactionLog = new UserTransactionLog();
				transactionLog.setId(IdGen.uuid());
				transactionLog.setDataId(userTaskId);
				transactionLog.setGoldNum(Long.valueOf(vo.getTask().getGold()));
				transactionLog.setGoldType(GlobalConstant.GOLD_TYPE_2);
				transactionLog.setUserId(ShiroAuthenticationManager.getUserId());
				transactionLog.setUtTime(System.currentTimeMillis() + "");
				transactionLog.setRemarks(vo.getTask().getTaskName());
				transactionLog.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_11);
				transactionLog.setUserNick(ShiroAuthenticationManager.getUserNickName());
				userTransactionLogService.insert(transactionLog);
			}
			resultMap.put("result", result);
		}
		return resultMap;
	}
}
