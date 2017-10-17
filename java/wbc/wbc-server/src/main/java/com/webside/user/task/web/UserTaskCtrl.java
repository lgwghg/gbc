package com.webside.user.task.web;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.task.entities.UserTask;
import com.webside.user.task.entities.UserTaskVo;
import com.webside.user.task.service.ITaskService;
import com.webside.user.task.service.IUserTaskService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;

@Controller
@RequestMapping("/my/userTask")
public class UserTaskCtrl extends BaseController{
	
	@Autowired
	private IUserTaskService userTaskService;
	@Autowired
	private ITaskService taskService;
	@RequestMapping("")
	public String taskCentral(Model model) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", ShiroAuthenticationManager.getUserId());
		List<UserTaskVo> taskVoList = userTaskService.queryUserTaskVoListByPg(param);
		model.addAttribute("taskVoList", taskVoList);
		return Common.BACKGROUND_PATH_WEB + "/my/task/usertask";
	}
	
	@RequestMapping("/right")
	@ResponseBody
	public Object rightTask(String source) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", ShiroAuthenticationManager.getUserId());
		List<UserTaskVo> taskVoList = userTaskService.queryUserTaskVoListByPg(param);
		Map<String, Object> resultMap = new HashMap<>();
		if ("task".equals(source)) {
			resultMap.put("taskVoList", taskVoList);
		} else if ("account".equals(source)) {
			int dailyTaskNum = 0;//每日任务的总数
			int completedDailyTaskNum = 0;//已经完成的每日任务数
			resultMap.put("dailyTask", false);
			for (UserTaskVo vo : taskVoList) {
				if (!(Boolean)resultMap.get("dailyTask")) {
					if (vo.getTask().getType() == 0 && (vo.getUserTask() == null || vo.getUserTask().getCompletedState() == 0)) {
						//有进行中的每日任务
						resultMap.put("dailyTask", true);
						resultMap.put("dailyTaskVo", vo);
					}
				}
				if (vo.getTask().getType() == 0) {
					dailyTaskNum = dailyTaskNum + 1;
					if (vo.getUserTask() != null && vo.getUserTask().getCompletedState() != null 
							&& (vo.getUserTask().getCompletedState() == 1 || vo.getUserTask().getCompletedState() == 2)) {
						completedDailyTaskNum = completedDailyTaskNum + 1;
					}
				}
			}
			resultMap.put("dailyTaskNum", dailyTaskNum);
			resultMap.put("completedDailyTaskNum", completedDailyTaskNum);
		}
		return resultMap;
	}
	/**
	 * 完成任务领G币
	 * @param userTaskId
	 * @param taskType
	 * @return
	 */
	@RequestMapping("/compeleted")
	@ResponseBody
	public Object compeleteTaskToGetGold(String userTaskId, Integer taskType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", ShiroAuthenticationManager.getUserId());
		param.put("taskType", taskType);
		param.put("userTaskId", userTaskId);
		Map<String, Object> resultMap = taskService.updateCompletedTask(param);
		return resultMap;
	}
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		System.out.println(calendar.getTimeInMillis());
	}
}
