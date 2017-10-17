/*******************************************************************************
 * All rights reserved. 
 * 
 * @author LiGan
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.jc.mapper.ICommentMapper;
import com.webside.jc.model.Comment;
import com.webside.jc.service.ICommentService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.dict.service.DictService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.task.service.IUserTaskService;
import com.webside.util.DateUtils;
import com.webside.util.StringUtils;


/**
 * 用户评论服务实现类
 *
 * @author LiGan
 * @date 2016-11-29 14:18:52
 */
@Service("commentService")
public class CommentService extends AbstractService<Comment, String> implements ICommentService 
{
	@Autowired
	private IUserTaskService userTaskService;
	/**
	 * 新增用户评论
	 * @param Comment
	 * @throws Exception
	 * @author LiGan
	 */
	@Override
	public int insert(Comment t) {
		int result = 0;
		try {
			t.setCreateTime((System.currentTimeMillis()+""));
			t.setIsDeleted(StringUtils.toInteger(GlobalConstant.DICTTYPE_IS_DELETE_0));
			result = super.insert(t);
			// 评论任务
			userTaskService.updateUserTask(t.getUserId(), GlobalConstant.USER_TASK_TYPE_COMMENT_2, null);
		} catch (Exception e) {
			logger.error("新增评论出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	/**
	 * 修改用户评论
	 * @param entity
	 * @throws Exception
	 * @author LiGan
	 */
	@Override
	public int update(Comment t) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setUpdateTime(System.currentTimeMillis());
			t.setUpdateOperatorId(user.getId());
			result = super.update(t);
		} catch (Exception e) {
			logger.error("修改评论出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	/**
	 * 按条件查询用户评论
	 * @param : String gbId
	 * @author LiGan
	 */
	public List<Comment> queryListByPage(Map<String, Object> parameter){
		List<Comment> list = null;
		try{
			parameter.put("isDeleted", GlobalConstant.DICTTYPE_IS_DELETE_0);
			list = commentMapper.queryListByPage(parameter);
			if(list!=null && list.size()>0){
				for(Comment c:list){
					c.setUser(userCacheService.getUserEntityByUserId(c.getUserId()));
					c.setCreateTimeD(DateUtils.getTimeDifferenceFront(System.currentTimeMillis(), Long.parseLong(c.getCreateTime())));
					c.setIsDeletedName(dictService.getDict(GlobalConstant.DICTTYPE_IS_DELETE, String.valueOf(c.getIsDeleted())).getLabel());
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("按条件查询用户评论出错：", e);
		}
		return list;  
	}

	/**
	 * 按条件查询评论部分字段，自定义分页
	 * @throws Exception
	 * @author zengxn
	 */
	public JSONArray queryPartListByPage(Map<String, Object> parameter) {
		JSONArray jsonArray = new JSONArray();
		JSONObject commentJsonObject = null;
		JSONObject userJsonObject = null;
		JSONObject answerJsonObject = null;
		JSONArray answerJsonArray = null;
		if(parameter==null || !parameter.containsKey("isDeleted")){
			if(parameter==null){
				parameter = new HashMap<String, Object>();
			}
			parameter.put("isDeleted", GlobalConstant.DICTTYPE_IS_DELETE_0);
		}
		List<Comment> list = commentMapper.queryPartListByPage(parameter);
		String tmpPid = "";
		int i = 0,tmpI = 0;
		boolean last = false;
		long nowTime = System.currentTimeMillis();
		String userId = ShiroAuthenticationManager.getUserId();
		while (!last && i < list.size()) {
			Comment comment = list.get(i);
			//临时评论id
			tmpPid = comment.getId();
			
			commentJsonObject = new JSONObject();
			commentJsonObject.put("id", comment.getId());
			commentJsonObject.put("content", comment.getContent());
			if(StringUtils.isNotEmpty(userId) && comment.getUserId().equals(userId)){
				commentJsonObject.put("surplusTime", nowTime-Long.parseLong(comment.getCreateTime()));
			}
			commentJsonObject.put("createTime", DateUtils.getTimeDifferenceFront(nowTime, Long.parseLong(comment.getCreateTime())));
			userJsonObject = new JSONObject();
			comment.setUser(userCacheService.getUserEntityByUserId(comment.getUserId()));
			userJsonObject.put("nickName", comment.getUser().getNickName());
			userJsonObject.put("userPhoto_65", comment.getUser().getPhoto_65());
			userJsonObject.put("rankLevel", comment.getUser().getRankLevel());
			commentJsonObject.put("user", userJsonObject);
			
			tmpI = i;
			//回复集合
			answerJsonArray = new JSONArray();
			for (int j = tmpI; j < list.size(); j++) {
				i = j;
				Comment answerComment = list.get(j);
				if(answerComment.getAnswerComment()!=null && StringUtils.isNotEmpty(answerComment.getAnswerComment().getPcommentId()) && answerComment.getAnswerComment().getPcommentId().equals(tmpPid)){
					answerJsonObject = new JSONObject();
					answerJsonObject.put("id", answerComment.getAnswerComment().getId());
					answerJsonObject.put("content", answerComment.getAnswerComment().getContent());
					if(StringUtils.isNotEmpty(userId) && answerComment.getAnswerComment().getUserId().equals(userId)){
						answerJsonObject.put("surplusTime", nowTime-Long.parseLong(answerComment.getAnswerComment().getCreateTime()));
					}
					answerJsonObject.put("createTime", DateUtils.getTimeDifferenceFront(nowTime, Long.parseLong(answerComment.getAnswerComment().getCreateTime())));
					userJsonObject = new JSONObject();
					answerComment.getAnswerComment().setUser(userCacheService.getUserEntityByUserId(answerComment.getAnswerComment().getUserId()));
					userJsonObject.put("nickName", answerComment.getAnswerComment().getUser().getNickName());
					userJsonObject.put("userPhoto_35", answerComment.getAnswerComment().getUser().getPhoto_35());
					userJsonObject.put("rankLevel", answerComment.getAnswerComment().getUser().getRankLevel());
					answerJsonObject.put("user", userJsonObject);
					answerJsonArray.add(answerJsonObject);
					//判断是否最后一条带有回复的数据
					if(j==list.size()-1){
						last = true;
					}
				}else{
					//没有回复
					if(j==tmpI){
						i++;
					}
					break;
				}
			}
			
			commentJsonObject.put("answerArray", answerJsonArray);
			jsonArray.add(commentJsonObject);
		}
		return jsonArray;
	}
	
	/**
	 * 根据id删除评论，硬删
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(String id) {
		int result = 0;
		try {
			result =super.deleteById(id);
		} catch (Exception e) {
			logger.error("根据id删除评论出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}

	/**
	 * 根据上级id删除评论，硬删
	 * @param id
	 * @return
	 */
	@Override
	public int deleteByPid(String pid) {
		int result = 0;
		try {
			result =commentMapper.deleteByPid(pid);
		} catch (Exception e) {
			logger.error("根据上级id删除评论出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(commentMapper);
	}
	/**
	 * 用户评论 DAO定义
	 */
	@Autowired
	private ICommentMapper commentMapper;
	
	/**
	 * 用户缓存接口
	 * */
	@Autowired
	private IUserCacheService userCacheService;
	
	@Autowired
	private DictService dictService;
	
}
