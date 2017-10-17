package com.webside.quartz.job;

import javax.mail.AuthenticationFailedException;

import jodd.mail.MailException;

import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;

import com.webside.coinflip.service.ICoinflipRoomService;

public class CoinflipClearJob implements InterruptableJob{
	private static Logger logger = Logger.getLogger(CoinflipClearJob.class);
	private boolean interrupted = false;
	private JobKey jobKey = null;
	@Autowired
	private ICoinflipRoomService coinflipRoomService;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobKey = context.getJobDetail().getKey();
		if (context.isRecovering()) {
			// job is recovering
			logger.info("翻硬币清理job 已恢复");
		} else {
			// job is start
			logger.info("翻硬币清理job 开始执行");
			try {
				if (!interrupted) {
					coinflipRoomService.updateOverdueGame();
				} else {
					logger.info("用户停止了这个翻硬币清理任务job: " + jobKey);
					return;
				}
			} catch (Exception e) {
				logger.error("翻硬币清理失败：", e);
			}
		}
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		interrupted = true;
		logger.info("用户停止了这个翻硬币清理任务job: " + jobKey);
	}


}
