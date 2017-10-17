package com.webside.user.service;

import com.webside.user.model.UserEntity;

/**
 * @title : 用户缓存接口服务
 * */
public interface IUserCacheService {
	
	/**
	 * 根据用户id，查询用户缓存信息
	 * @param userId
	 * @return
	 */
	public UserEntity getUserEntityByUserId(String userId);
	/**
	 * 更新用户缓存信息
	 * @param userEntity
	 */
	public void updateUserToRedis(UserEntity userEntity);
}
