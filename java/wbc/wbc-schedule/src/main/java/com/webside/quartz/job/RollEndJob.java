package com.webside.quartz.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;

import com.webside.roll.service.IRollWinnerService;

/**
 * 清除15天前的未付款充值记录任务
 * @author zengxn
 * @date 2016年12月30日 11:12:04
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class RollEndJob implements InterruptableJob {

	private static Logger logger = Logger.getLogger(RollEndJob.class);

	@Autowired
	IRollWinnerService rollWinnerService;

	private boolean interrupted = false;

	private JobKey jobKey = null;

	public RollEndJob() {

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobKey = context.getJobDetail().getKey();
		if (context.isRecovering()) {// 判断是否在执行ing
			logger.info("job 已恢复");
		} else {
			logger.info("job 开始执行");
			try {
				if (interrupted) {// 中断
					logger.info("用户停止了roll结束机制job: " + jobKey);
					return;
				} else {
					// 业务处理
					rollWinnerService.addForRollEnd();
				}
			} catch (Exception e) {
				logger.error("roll结束机制异常", e);
			}
		}

		System.out.println("roll结束机制任务成功运行");
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		interrupted = true;
		logger.info("用户停止了roll结束机制job: " + jobKey);
	}

}
