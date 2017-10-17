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

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.roll.model.RollPool;

/**
 * 奖品池数据访问接口
 * 
 * @author zhangfei
 * @date 2017-04-18 11:05:18
 */
@Repository
public interface IRollPoolMapper extends BaseMapper<RollPool, String> {

	/**
	 * 通过房间ID获取未roll出去的奖品
	 * @param roomId
	 * @return
	 */
	public List<String> getIdByRoomId(String roomId);
}
