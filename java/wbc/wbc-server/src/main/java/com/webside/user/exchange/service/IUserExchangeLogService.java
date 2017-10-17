/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.exchange.service;

import java.util.List;
import java.util.Map;

import com.webside.user.exchange.entities.UserExchangeLog;

/**
 * 用户兑换服务接口
 * 
 * @author tianguifang
 * @date 2016-11-24 16:19:27
 */
public interface IUserExchangeLogService {
	
	/**
	 * 按条件查询商品兑换
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserExchangeLog> queryListByPage(Map<String, Object> parameter);

	/**
	 * 修改商品兑换
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int update(final UserExchangeLog entity);

	/**
	 * 根据ID获取商品兑换
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserExchangeLog findById(String id);

	/**
	 * 根据对象删除商品兑换
	 * @param UserExchangeLog
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);

	/**
	 * 商品兑换
	 * @param goodsId商品
	 * @return 1 插入成功
	 * @author zhangfei
	 */
	public int insert(String goodsId, String addressId);
	
	/**
	 * 左连接查询数据列表
	 * @param paramMap.userId 用户
	 * @return
	 * @author zhangfei
	 */
	public List<UserExchangeLog> findListByPage(Map<String, Object> paramMap);
	
	/**
	 * 商品批量兑换
	 * @param gold 金币
	 * @param goodsId 商品
	 * @param salesNum 数量
	 * @author zhangfei
	 */
	public void insert(String goodsId, int salesNum, String addressId);

	/**
	 * 点卡出售完成
	 * @param exchangeId
	 * @return
	 * @throws Exception
	 * @author tianguifang
	 */
	public Integer updateSaledCard(String exchangeId) throws Exception;
	
	/**
	 * 商品售出，更新状态
	 * @param ids 兑换记录id 
	 * @author zhangfei
	 */
	public int updateStatusForSales(String ids);
	
	/**
	 * 全部换成点卡
	 * @author zhangfei
	 */
	public Map<String, Object> insertForbuyAll();
	
	/**
	 * 
	 * @param cdkCode cdk兑换码
	 * @param addressId 物流地址
	 * @author zhangfei
	 */
	public void insertForCDK(String cdkCode, String addressId, String userId);
	
	/**
	 * 放入奖池之后，更新状态
	 * @param list
	 */
	public void updateStatusForPool(String ids);
	

	/**
	 * roll之后，更新状态
	 * @param list
	 */
	public void updateStatusForRoll(String list);
	
	/**
	 * 验证ckd是否有效，可以放入奖池
	 * @param exchangeId 主键ID
	 * @return
	 */
	public boolean isCdkeyValid(String exchangeId);
	
	/**
	 * 更新状态
	 * @param id 主键ID
 	 * @param updateStatus 需要更新的状态
	 */
	public UserExchangeLog updateStatus(String id, String updateStatus);

}
