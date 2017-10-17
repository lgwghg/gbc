/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.deposit.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.deposit.entities.UserDepositLog;

/**
 * 充值记录数据访问接口
 * 
 * @author tianguifang
 * @date 2016-11-24 16:21:01
 */
@Repository
public interface IUserDepositLogMapper extends BaseMapper<UserDepositLog, String> {
	/**
	 * 任务调度执行清除15天前的未付款充值记录
	 * @author zengxn
	 */
	public void executeClearLog();
	
	/**
	 * 获取用户的提现金额
	 * @author zhangfei
	 */
	public String getUserWdGold(String userId);
}
