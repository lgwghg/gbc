/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.jc.model;

import java.io.Serializable;

import com.webside.user.model.UserEntity;

/**
 *  用户评论实体类entity
 *
 * @author LiGan
 * @date 2016-11-29 14:18:45
 */
public class Comment implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String id;

	/**
	 * 比赛对战ID
	 * */
	private String gbId;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	private UserEntity user;

	/**
	 * 评论内容
	 */
	private String content;
	
	/**
	 * 评论时间
	 */
	private String createTime;
	
	/**
	 *  时间差 网站页面显示
	 * */
	private String createTimeD;
	
	/**
	 * 上级评论ID
	 * */
	private String pcommentId;
	
	/**
	 * 是否删除 默认0     1：已删除   0：未删除
	 */
	private Integer isDeleted;
	private String isDeletedName;
	
	/**
	 * 更新时间
	 */
	private Long updateTime;
	
	/**
	 * 更新人
	 */
	private String updateOperatorId;

	/**
	 * 更新人昵称
	 */
	private String updateOperatorNickName;
	
	/**
	 * 回复
	 */
	private Comment answerComment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGbId() {
		return gbId;
	}

	public void setGbId(String gbId) {
		this.gbId = gbId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPcommentId() {
		return pcommentId;
	}

	public void setPcommentId(String pcommentId) {
		this.pcommentId = pcommentId;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateOperatorId() {
		return updateOperatorId;
	}

	public void setUpdateOperatorId(String updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}

	public String getUpdateOperatorNickName() {
		return updateOperatorNickName;
	}

	public void setUpdateOperatorNickName(String updateOperatorNickName) {
		this.updateOperatorNickName = updateOperatorNickName;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getCreateTimeD() {
		return createTimeD;
	}

	public void setCreateTimeD(String createTimeD) {
		this.createTimeD = createTimeD;
	}

	public String getIsDeletedName() {
		return isDeletedName;
	}

	public void setIsDeletedName(String isDeletedName) {
		this.isDeletedName = isDeletedName;
	}

	public Comment getAnswerComment() {
		return answerComment;
	}

	public void setAnswerComment(Comment answerComment) {
		this.answerComment = answerComment;
	}
}