/*******************************************************************************
 * All rights reserved. 
 * 
 * @author LiGan
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.jc.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.webside.jc.model.Comment;

/**
 * 用户评论服务接口
 *
 * @author LiGan
 * @date 2016-11-29 14:18:52
 */
public interface ICommentService 
{
	/**
	 * 按条件查询用户评论
	 * @param : String gbId
	 * @author LiGan
	 */
	public List<Comment> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增用户评论
	 * @param Comment
	 * @throws Exception
	 * @author LiGan
	 */
	public int insert(final Comment entity);
	
	/**
	 * 修改用户评论
	 * @param entity
	 * @throws Exception
	 * @author LiGan
	 */
	public int update(final Comment entity);
	
	/**
	 * 根据ID获取用户评论
	 * @param ID
	 * @throws Exception
	 * @author LiGan
	 */
	public Comment findById(String id);
	
	/**
	 * 按条件查询评论部分字段，自定义分页
	 * @throws Exception
	 * @author zengxn
	 */
	public JSONArray queryPartListByPage(Map<String, Object> parameter);
	
	/**
	 * 根据id删除评论，硬删
	 * @param id
	 * @return
	 */
	public int deleteById(String id);
	
	/**
	 * 根据上级id删除评论，硬删
	 * @param id
	 * @return
	 */
	public int deleteByPid(String pid);
}
