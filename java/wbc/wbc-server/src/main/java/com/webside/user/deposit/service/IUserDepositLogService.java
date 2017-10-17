/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.deposit.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.webside.user.deposit.entities.UserDepositLog;

/**
 * 充值记录服务接口
 * 
 * @author tianguifang
 * @date 2016-11-24 16:21:01
 */
public interface IUserDepositLogService 
{
	/**
	 * 按条件查询充值记录
	 * 
	 * @throws Exception
	 * 
	 * @author tianguifang
	 */
	public List<UserDepositLog> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增充值记录
	 * 
	 * @param UserDepositLog
	 * 
	 * @throws Exception
	 * 
	 * @author tianguifang
	 */
	public int insert(final UserDepositLog entity);
	
	/**
	 * 修改充值记录
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int update(final UserDepositLog entity);
	
	/**
	 * 根据ID获取充值记录
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserDepositLog findById(String id);
	
	/**
	 * 根据对象删除充值记录
	 * @param UserDepositLog
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	
	/**
	 * 任务调度执行清除15天前的未付款充值记录
	 * @author zengxn
	 */
	public void executeClearLog();
	
	/**
	 * 更新提现数据
	 * @param entity
	 * @author zhangfei
	 */
	public int updateForWithdraw(UserDepositLog entity);
	
	/**
	 * 保存提现数据
	 * @param userId 用户id
	 * @param gold 提现金币
	 * @param factorage 手续费
	 * @author zhangfei
	 */
	public int insertForWithdraw(UserDepositLog entity, Long factorage);
	
	/**
	 * 获取用户的提现金额
	 * @author zhangfei
	 */
	public String getUserWdGold(String userId);
	
	/**
	 * 只修改充值记录
	 * @param entity
	 * @author zengxiangneng
	 */
	public int updateEntityById(UserDepositLog entity);
	
}
