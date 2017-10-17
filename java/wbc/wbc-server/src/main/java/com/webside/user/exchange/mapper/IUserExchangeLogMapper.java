/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.exchange.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.exchange.entities.UserExchangeLog;

/**
 * 用户兑换数据访问接口
 * 
 * @author tianguifang
 * @date 2016-11-24 16:19:27
 */
@Repository
public interface IUserExchangeLogMapper extends BaseMapper<UserExchangeLog, String> {

	/**
	 * 左连接查询数据列表
	 * 
	 * @param paramMap.userId 用户
	 * @return
	 * @author zhangfei
	 */
	public List<UserExchangeLog> findListByPage(Map<String, Object> paramMap);

	/**
	 * 商品售出，更新状态
	 * @param paramMap.ids 兑换记录id list
	 * @author zhangfei
	 */
	public int updateStatusForSales(Map<String, Object> paramMap);
	
	/**
	 * 放入奖池之后，更新状态
	 * @param list
	 */
	public void updateStatusForPool(List<String> list);
	
	/**
	 * roll之后，更新状态
	 * @param list
	 */
	public void updateStatusForRoll(List<String> list);
}
