/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.roll.mapper.IRollRoomMapper;
import com.webside.roll.model.RollRoom;
import com.webside.roll.service.IRollRoomService;
import com.webside.roll.vo.RollRoomVo;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 房间服务实现类
 * 
 * @author zhangfei
 * @date 2017-04-18 11:04:32
 */
@Service("rollRoomService")
public class RollRoomService extends AbstractService<RollRoom, String> implements IRollRoomService {
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(rollRoomMapper);
	}

	/**
	 * 房间 DAO定义
	 */
	@Autowired
	private IRollRoomMapper rollRoomMapper;

	public List<RollRoomVo> getRollRoomListByPage(Map<String, Object> paramMap) {

		return rollRoomMapper.getRollRoomListByPage(paramMap);
	}
	
	public List<RollRoomVo> getRollRoomListForHot(Map<String, Object> paramMap) {
		
		return rollRoomMapper.getRollRoomListForHot(paramMap);
	}

	public RollRoomVo findByRoomCode(String roomCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roomCode", roomCode);
		
		List<RollRoomVo> list = getRollRoomListByPage(paramMap);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}
	
	public int insert(RollRoom entity) {
		entity.setId(IdGen.uuid());// 设置ID 生成 UUID
		entity.setUserId(ShiroAuthenticationManager.getUserId());
		entity.setStatus(1);
		if(StringUtils.isNotBlank(entity.getStartTime())) {
			entity.setStartTime(StringUtils.toString(DateUtils.parseDate(entity.getStartTime()).getTime()));
		}
		if(StringUtils.isNotBlank(entity.getEndTime())) {
			entity.setEndTime(StringUtils.toString(DateUtils.parseDate(entity.getEndTime()).getTime()));
		}
		
		return rollRoomMapper.insert(entity);
	}

	public int update(RollRoom entity) {
		if(StringUtils.isNotBlank(entity.getStartTime())) {
			entity.setStartTime(StringUtils.toString(DateUtils.parseDate(entity.getStartTime()).getTime()));
		}
		if(StringUtils.isNotBlank(entity.getEndTime())) {
			entity.setEndTime(StringUtils.toString(DateUtils.parseDate(entity.getEndTime()).getTime()));
		}
		
		return rollRoomMapper.update(entity);
	}
	
	public List<Integer> getRollNumByRoomId(String roomId) {
		
		return rollRoomMapper.getRollNumByRoomId(roomId);
	}
	
	public List<String> getIdForRollEnd(long endTime) {
		
		return rollRoomMapper.getIdForRollEnd(endTime);
	}
	
}
