/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.roll.model.RollRoom;
import com.webside.roll.vo.RollRoomVo;

/**
 * 房间数据访问接口
 * 
 * @author zhangfei
 * @date 2017-04-18 11:04:32
 */
@Repository
public interface IRollRoomMapper extends BaseMapper<RollRoom, String> {

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
