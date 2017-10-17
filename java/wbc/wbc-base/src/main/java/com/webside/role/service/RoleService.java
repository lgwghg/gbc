package com.webside.role.service;

import java.util.List;
import java.util.Map;

import com.webside.role.model.RoleEntity;
import com.webside.user.model.vo.UserRoleVo;

public interface RoleService {

	public List<RoleEntity> queryListByPage(Map<String, Object> parameter);

	public RoleEntity findByName(String name);
	
	public int insert(RoleEntity roleEntity);
	
	public RoleEntity findById(String roleId);

	public int update(RoleEntity roleEntity);
    
	public int deleteBatchById(List<String> roleIds);
    
	public boolean deleteRoleById(String roleId);
    
	public boolean addRolePermBatch(String roleId, List<String> ids);
    
	public boolean addRolePerm(String roleId, String resourceId);

	/**
	 * 删除角色与资源的关联关系
	 * @param resourceId
	 * @return
	 */
	public boolean deleteRolePermByResource(String resourceId);

	public int findRoleUserById(String roleId);

	/**
	 * 根据用户id查询用户角色
	 * 
	 * @param userId
	 * @return
	 */
	public RoleEntity findUserRoleByUserId(String userId);

	/**
	 * 根据用户id list查询这批用户的角色
	 * 
	 * @param userIdList
	 * @return key:userId, value:UserRoleVo
	 */
	public Map<String, UserRoleVo> findUserRoleByUserIds(List<String> userIdList);
}