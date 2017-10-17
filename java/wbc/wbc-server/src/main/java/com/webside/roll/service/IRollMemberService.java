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

import com.webside.roll.model.RollMember;
import com.webside.roll.vo.RollMemberVo;

/**
 * 参与人员服务接口
 * 
 * @author zhangfei
 * @date 2017-04-18 11:02:41
 */
public interface IRollMemberService {
	/**
	 * 按条件查询参与人员
	 * 
	 * @throws Exception
	 * @author zhangfei
	 */
	public List<RollMember> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增参与人员
	 * 
	 * @param RollMember
	 * @throws Exception
	 * @author zhangfei
	 */
	public int insert(final RollMember entity);

	/**
	 * 修改参与人员
	 * 
	 * @param entity
	 * @throws Exception
	 * @author zhangfei
	 */
	public int update(final RollMember entity);

	/**
	 * 根据ID获取参与人员
	 * 
	 * @param ID
	 * @throws Exception
	 * @author zhangfei
	 */
	public RollMember findById(String id);

	/**
	 * 根据对象删除参与人员
	 * 
	 * @param RollMember
	 * @throws Exception
	 * @author zhangfei
	 */
	public int deleteBatchById(List<String> ids);
	
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
	
	
	/**
	 * 是否已参与活动
	 * @param roomId 房间ID
	 * @param userId 用户ID
	 * @return
	 */
	public boolean isJoinRoll(String roomId, String userId);
}
