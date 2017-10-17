/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.transaction.service;

import java.util.List;
import java.util.Map;

import com.webside.user.transaction.entities.UserTransactionLog;

/**
 * 用户交易记录(钱包明细) 业务接口
 *
 * @author tianguifang
 * @date 2016-11-24 15:54:19
 */
public interface IUserTransactionLogService 
{
	/**
	 * @title : 根据条件查询用户交易记录
	 * @param : String userId
	 * */
	public List<UserTransactionLog> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * @title : 新增用户交易记录
	 * @param UserTransactionLog utl
	 * */
	public int insert(UserTransactionLog utl);
	
	/**
	 * 获取用户七天前的收益，key 是日期， value 是收益G币
	 * @param userId 用户ID
	 * @return
	 * @author zhangfei
	 */
	public Map<String, Long> getMapForWeekJc(String userId);

}
