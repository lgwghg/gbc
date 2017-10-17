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
import com.webside.roll.mapper.IRollMemberMapper;
import com.webside.roll.model.RollMember;
import com.webside.roll.service.IRollMemberService;
import com.webside.roll.vo.RollMemberVo;
import com.webside.util.StringUtils;

/**
 * 参与人员服务实现类
 * 
 * @author zhangfei
 * @date 2017-04-18 11:02:41
 */
@Service("rollMemberService")
public class RollMemberService extends AbstractService<RollMember, String> implements IRollMemberService {
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(rollMemberMapper);
	}

	/**
	 * 参与人员 DAO定义
	 */
	@Autowired
	private IRollMemberMapper rollMemberMapper;
	
	public List<RollMemberVo> getVoListByMap(Map<String, Object> paramMap) {
		
		return rollMemberMapper.getVoListByMap(paramMap);
	}
	
	public boolean isJoinRoll(String roomId, String userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roomId", roomId);
		paramMap.put("userId", userId);
		List<RollMemberVo> list = getVoListByMap(paramMap);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	public int deleteByRoomId(String roomId) {
		if(StringUtils.isBlank(roomId)) {
			throw new RuntimeException("请确定要清除的放假~");
		}
		return rollMemberMapper.deleteByRoomId(roomId);
	}
}
