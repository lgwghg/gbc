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
import com.webside.roll.model.RollMember;
import com.webside.roll.vo.RollMemberVo;

/**
 * 参与人员数据访问接口
 * 
 * @author zhangfei
 * @date 2017-04-18 11:02:41
 */
@Repository
public interface IRollMemberMapper extends BaseMapper<RollMember, String> {

	/**
	 * 查找参与人员
	 * @param paramMap
	 * @return
	 */
	public List<RollMemberVo> getVoListByMap(Map<String, Object> paramMap);
	
	/**
	 * 依据房间清除参与人员
	 * @param roomId 房间ID
	 * @return
	 */
	public int deleteByRoomId(String roomId);
}
