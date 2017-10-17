/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.service;

import java.util.List;
import java.util.Map;

import com.webside.roll.model.RollPool;

/**
 * 奖品池服务接口
 *
 * @author zhangfei
 * @date 2017-04-18 11:05:18
 */
public interface IRollPoolService 
{
	/**
	 * 按条件查询奖品池
	 * @throws Exception
	 * @author zhangfei
	 */
	public List<RollPool> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增奖品池
	 * @param RollPool
	 * @throws Exception
	 * @author zhangfei
	 */
	public int insert(final RollPool entity);
	
	/**
	 * 修改奖品池
	 * @param entity
	 * @throws Exception
	 * @author zhangfei
	 */
	public int update(final RollPool entity);
	
	/**
	 * 根据ID获取奖品池
	 * @param ID
	 * @throws Exception
	 * @author zhangfei
	 */
	public RollPool findById(String id);
	
	/**
	 * 根据对象删除奖品池
	 * @param RollPool
	 * @throws Exception
	 * @author zhangfei
	 */
	public void deleteBatchById(String ids);
	
	/**
	 * 批量保存
	 * @param roomId 房间ID
	 * @param gold   金币
	 * @param num    插入数量
	 * @param stockIds 库存集合
	 */
	public void insertBatch(String roomId, Long gold, int num, String exchangeIds);
	
	/**
	 * 通过房间ID获取未roll出去的奖品
	 * @param roomId
	 * @return
	 */
	public List<String> getIdByRoomId(String roomId);
}
