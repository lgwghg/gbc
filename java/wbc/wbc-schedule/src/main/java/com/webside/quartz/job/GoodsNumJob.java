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

import com.webside.goods.service.IGoodsService;

/**
 * 清除15天前的未付款充值记录任务
 * @author zengxn
 * @date 2016年12月30日 11:12:04
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class GoodsNumJob implements InterruptableJob {

	private static Logger logger = Logger.getLogger(GoodsNumJob.class);

	@Autowired
	IGoodsService goodsService;

	private boolean interrupted = false;

	private JobKey jobKey = null;

	public GoodsNumJob() {

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
					logger.info("用户停止了刷新库存job: " + jobKey);
					return;
				} else {
					// 同步昨天的考勤状态
					goodsService.updateGoodsNumForJob();
				}
			} catch (Exception e) {
				logger.error("刷新库存异常", e);
			}
		}

		System.out.println("刷新库存任务成功运行");
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		interrupted = true;
		logger.info("用户停止了刷新库存job: " + jobKey);
	}

}
