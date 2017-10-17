package com.webside.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.role.model.RoleUser;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserRole;
import com.webside.user.model.UserWallet;

@Repository
public interface UserMapper extends BaseMapper<UserEntity, String> {

	/**
	 * 添加用户和角色对应关系
	 * 
	 * @param userEntity
	 * @return
	 */
	public int insertUserRole(RoleUser roleUser);

	/**
	 * 更新用户和角色对应关系
	 * 
	 * @param userEntity
	 * @return
	 */
	public int updateUserRole(UserEntity userEntity);

	/**
	 * 删除用户和角色对应关系
	 * 
	 * @param userEntity
	 * @return
	 */
	public int deleteBatchUserRole(List<String> userIds);

	public UserEntity findByMobile(String mobile);

	public List<UserEntity> findRoleUserByRoleId(Map<String, Object> parameter);

	/**
	 * 获取所有用户数
	 * 
	 * @return
	 */
	public Integer countAllUser();

	/**
	 * 根据用户昵称获取用户信息
	 * 
	 * @param nickName
	 * @return
	 */
	public UserEntity findByNickName(@Param("nickName") String nickName);

	public UserEntity findByEmail(@Param("email") String email);

	/**
	 * 根据用户id查询用户的角色
	 * 
	 * @param id
	 * @return
	 */
	public UserRole findUserRoleByUserId(@Param("id") String id);

	public int deleteUserRoleById(@Param("id") String id);

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
	 * 根据用户id，获取支付宝账号
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

	/**
	 * 取消第三方绑定
	 * 
	 * @param userId
	 * @param thirdType
	 * @return
	 */
	public int updateCancalBind(Map<String, Object> param);

	/**
	 * 创建用户钱包数据
	 * 
	 * @param wallet
	 * @return
	 * @author tianguifang
	 */
	public int insertUserWallet(UserWallet wallet);
	
	/**
	 * 根据推广key获取用户信息
	 * 
	 * @param nickName
	 * @return
	 */
	public UserEntity findByCampaignKey(String campaignKey);

}
