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

import com.webside.common.CacheConstant;
import com.webside.shiro.cache.redis.RedisCache;
import com.webside.user.deposit.service.IUserDepositLogService;
import com.webside.util.StringUtils;

/**
 * 清除15天前的未付款充值记录任务
 * @author zengxn
 * @date 2016年12月30日 11:12:04
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class UserDepositLogJob implements InterruptableJob {

	private static Logger logger = Logger.getLogger(UserDepositLogJob.class);

	@Autowired
	IUserDepositLogService userDepositLogService;
	/**
	 * reids 定义
	 */
	@Autowired
	private RedisCache redisCache;

	private boolean interrupted = false;

	private JobKey jobKey = null;

	public UserDepositLogJob() {

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//查询缓存状态
		String config = redisCache.get(CacheConstant.QUARTZ_USER_DEPOSITLOG_JOB_STATUS);
		if(StringUtils.isNotBlank(config) && config.equals("false")){//还未执行
			//设置为执行状态
			redisCache.set(CacheConstant.QUARTZ_USER_DEPOSITLOG_JOB_STATUS, "true");
			jobKey = context.getJobDetail().getKey();
			if (context.isRecovering()) {
				logger.info("清除15天前的未付款充值记录job 已恢复");
			} else {
				logger.info("清除15天前的未付款充值记录job 开始执行");
				try {
					if (!interrupted) {
						userDepositLogService.executeClearLog();
						logger.info("清除15天前的未付款充值记录成功");
					} else {
						logger.info("用户停止了清除15天前的未付款充值记录任务job: " + jobKey);
						return;
					}
				} catch (Exception e) {
					logger.error("清除15天前的未付款充值记录异常", e);
				}finally{
					redisCache.set(CacheConstant.QUARTZ_USER_DEPOSITLOG_JOB_STATUS,"false");
					logger.info("清除15天前的未付款充值记录job 执行完毕");
				}
			}
		}
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		interrupted = true;
		logger.info("用户停止了清除15天前的未付款充值记录任务job: " + jobKey);
	}

}
