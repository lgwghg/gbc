/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.friends.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.friends.entities.UserFriends;

/**
 * 推荐好友数据访问接口
 *
 * @author zengxn
 * @date 2016-11-28 11:05:22
 */
@Repository
public interface IUserFriendsMapper extends BaseMapper<UserFriends, String> 
{
	/**
	 * 按条件查询推荐好友部分字段
	 * @throws Exception
	 * @author zengxn
	 */
	public List<UserFriends> queryPartListByPage(Map<String, Object> parameter);
	
	/**
	 * 根据好友id查询数据
	 * @throws Exception
	 * @author zengxn
	 */
	public List<UserFriends> findByFriendId(Map<String, Object> parameter);
}
