/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.transaction.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.transaction.entities.UserTransactionLog;

/**
 * 用户交易记录数据访问接口
 * 
 * @author tianguifang
 * @date 2016-11-24 15:54:19
 */
@Repository
public interface IUserTransactionLogMapper extends BaseMapper<UserTransactionLog, String> {

	/**
	 * 获取竞猜相关数据
	 * @param paramMap
	 * @return
	 * @author zhangfei
	 */
	public List<UserTransactionLog> findListForJc(Map<String, Object> paramMap);
	
}
