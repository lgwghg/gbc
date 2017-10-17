/*******************************************************************************
 * All rights reserved. 
 * 
 * @author LiGan
 *
 * Contributors:
 *     Info Corporation - POC 
 *******************************************************************************/
package com.webside.jc.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.jc.model.Comment;

/**
 * 用户评论数据访问接口
 *
 * @author LiGan
 * @date 2016-11-29 14:18:52
 */
@Repository
public interface ICommentMapper extends BaseMapper<Comment, String> 
{

	/**
	 * 按条件查询评论部分字段，自定义分页
	 * @throws Exception
	 * @author zengxn
	 */
	public List<Comment> queryPartListByPage(Map<String, Object> parameter);
	
	/**
	 * 根据上级id删除评论，硬删
	 * @param id
	 * @return
	 */
	public int deleteByPid(String pid);
}
