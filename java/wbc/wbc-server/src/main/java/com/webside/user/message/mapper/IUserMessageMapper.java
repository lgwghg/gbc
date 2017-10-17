/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.message.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.message.entities.UserMessage;

/**
 * 用户消息数据访问接口
 *
 * @author zengxn
 * @date 2016-11-25 17:43:42
 */
@Repository
public interface IUserMessageMapper extends BaseMapper<UserMessage, String> 
{
	/**
	 * 批量修改用户消息为已读
	 * @param ids
	 * @return
	 */
	public int updateBatchById(Map<String, Object> parameter);
}
