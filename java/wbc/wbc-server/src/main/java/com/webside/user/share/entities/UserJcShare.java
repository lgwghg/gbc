/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.share.entities;

import java.io.Serializable;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2017-01-11 14:33:38
 */
public class UserJcShare implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UserJcShare(){}
	

	/**
	 * 唯一标识
	 */
	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/** 比赛id */
	private String gbId;

	/**
	 * 菠菜竞猜分享人id
	 */
	private String shareUserId;

	public String getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}

	/**
	 * 领取G币的菠菜用户id
	 */
	private String userId;
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	/**
	 * 领取G币的手机号
	 */
	private String userMobile;

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * 领取的G币数量
	 */
	private Long gold;
	
	public void setGold(Long gold){
		this.gold = gold;
	}
	
	public Long getGold(){
		return this.gold;
	}

	/**
	 * 领取G币的时间
	 */
	private Long createTime;
	
	public void setCreateTime(Long createTime){
		this.createTime = createTime;
	}
	
	public Long getCreateTime(){
		return this.createTime;
	}

	/**
	 * 第三方用户昵称
	 */
	private String userNick;
	
	/**
	 * 第三方用户头像
	 */
	private String userPhoto = "/resources/wbc/images/denglu_n_65.png";

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	/**
	 * 用户抢到红包的，简单描述
	 */
	private String userDesc;

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	private Integer lucky = 0;

	public Integer getLucky() {
		return lucky;
	}

	public void setLucky(Integer lucky) {
		this.lucky = lucky;
	}

	public String getGbId() {
		return gbId;
	}

	public void setGbId(String gbId) {
		this.gbId = gbId;
	}

	private String userCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}