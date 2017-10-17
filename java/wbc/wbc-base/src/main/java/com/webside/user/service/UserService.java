package com.webside.user.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.webside.exception.ServiceException;
import com.webside.user.model.UserEntity;

/**
 * 用戶信息接口
 * 
 * @author tianguifang
 * 
 */
public interface UserService {

	public List<UserEntity> queryListByPage(Map<String, Object> parameter);

	public UserEntity findByName(String accountName);

	public int insert(UserEntity userEntity, String password);

	public UserEntity findById(String id);

	public int update(UserEntity userEntity);

	public int updateOnly(UserEntity userEntity, String password) throws ServiceException;

	public int deleteBatchById(List<String> userIds);

	public int updateUserOnly(UserEntity userEntity);
	
	public int updateUser(UserEntity userEntity);

	public int updateUserAlipayAccount(UserEntity userEntity);

	public UserEntity findByMobile(String mobile);

	/**
	 * 根据角色id 查询出该角色下的所有用户信息
	 * 
	 * @param roleId
	 * @return
	 */
	public List<UserEntity> findRoleUserByRoleId(Map<String, Object> parameter);

	/**
	 * 获取所有用户数
	 * 
	 * @return
	 */
	public Integer countAllUser();

	/**
	 * 根据用户昵称获取用户
	 * 
	 * @param nickName
	 * @return
	 */
	public UserEntity findByNickName(String nickName);

	/**
	 * 根据邮箱获取用户信息
	 * 
	 * @param email
	 * @return
	 */
	public UserEntity findByEmail(String email);

	/**
	 * 根据用戶id獲取用戶信息
	 * 
	 * @param id
	 * @return
	 */
	public UserEntity findUserById(String id);

	/**
	 * 单表查询用户信息
	 * 
	 * @param user
	 * @return
	 * @author tianguifang
	 */
	public UserEntity findUserEntity(UserEntity user);

	/**
	 * 根据用户id，昵称，手机号，邮箱，自定义url查询用户信息（带增量信息和角色信息）
	 * 
	 * @param user
	 * @return
	 * @author tianguifang
	 */
	public UserEntity findUserWithRoleAndIncrement(UserEntity user);

	/**
	 * 根据用户id，获取用户支付宝账号
	 * 
	 * @param userId
	 * @return
	 */
	public String findAlipayAccountByUserId(String userId);

	/**
	 * 该方法只用来登录获取用户信息，其他功能绕行
	 * 
	 * @param user
	 * @return
	 */
	public UserEntity findUserByMobileOrEmailOrIdOrThirdKey(UserEntity user);

	public UserEntity findUserByMobileOrEmailOrIdOrThirdKeyWithoutRole(UserEntity userParam);
	/**
	 * 取消第三方绑定
	 * 
	 * @param userId
	 * @param thirdType
	 * @return
	 */
	public int updateCancalBind(String userId, String thirdType);

	/**
	 * 盈利top
	 * 
	 * @param count
	 * @return
	 */
	public JSONArray profitTop(Integer count);

	/**
	 * 盈率top
	 * 
	 * @param count
	 * @return
	 */
	public JSONArray profitRateTop(Integer count);
	
	/**
	 * 根据推广key获取用户信息
	 * 
	 * @param email
	 * @return
	 */
	public UserEntity findByCampaignKey(String campaignKey);

}