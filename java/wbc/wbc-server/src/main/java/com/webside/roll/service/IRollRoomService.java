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

import com.webside.roll.model.RollRoom;
import com.webside.roll.vo.RollRoomVo;

/**
 * 房间服务接口
 * 
 * @author zhangfei
 * @date 2017-04-18 11:04:32
 */
public interface IRollRoomService {
	/**
	 * 按条件查询房间
	 * 
	 * @throws Exception
	 * @author zhangfei
	 */
	public List<RollRoom> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增房间
	 * 
	 * @param RollRoom
	 * @throws Exception
	 * @author zhangfei
	 */
	public int insert(final RollRoom entity);

	/**
	 * 修改房间
	 * 
	 * @param entity
	 * @throws Exception
	 * @author zhangfei
	 */
	public int update(final RollRoom entity);

	/**
	 * 根据ID获取房间
	 * 
	 * @param ID
	 * @throws Exception
	 * @author zhangfei
	 */
	public RollRoom findById(String id);

	/**
	 * 根据对象删除房间
	 * 
	 * @param RollRoom
	 * @throws Exception
	 * @author zhangfei
	 */
	public int deleteBatchById(List<String> ids);
	
	/**
	 * 获取roll点房间列表
	 * @param paramMap
	 * @return
	 */
	public List<RollRoomVo> getRollRoomListByPage(Map<String, Object> paramMap);
	
	/**
	 * 获取热门推荐房间列表
	 * @param paramMap
	 * @return
	 */
	public List<RollRoomVo> getRollRoomListForHot(Map<String, Object> paramMap);
	
	/**
	 * 获取房间详情
	 * @param roomCode
	 * @return
	 */
	public RollRoomVo findByRoomCode(String roomCode);
	
	/**
	 * 获取一共roll了多少次
	 * @param roomId 房间ID
	 * @return
	 */
	public List<Integer> getRollNumByRoomId(String roomId);
	
	/**
	 * 获取roll结束之后还有奖品的房间ID
	 * @param endTime
	 * @return
	 */
	public List<String> getIdForRollEnd(long endTime);
}
