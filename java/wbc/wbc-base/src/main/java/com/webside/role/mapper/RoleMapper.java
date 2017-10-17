package com.webside.role.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.role.model.RoleEntity;
import com.webside.user.model.vo.UserRoleVo;

@Repository
public interface RoleMapper extends BaseMapper<RoleEntity, String> {
	
	/**
	 * 查询该角色是否有权限信息
	 * @param roleId	角色id 
	 * 使用@Param注解主要是设置mapping中可以使用参数名进行取值
	 * @return
	 */
	public int findRoleResourceById(@Param(value = "roleId") String roleId);
	
	/**
	 * 查询该角色下是否有用户
	 * @param roleId	角色id 
	 * 使用@Param注解主要是设置mapping中可以使用参数名进行取值
	 * @return
	 */
	public int findRoleUserById(@Param(value = "roleId") String roleId);
	
	/**
	 * 删除角色的权限信息
	 * @param roleId	角色id 
	 * 使用@Param注解主要是设置mapping中可以使用参数名进行取值
	 * @return
	 */
	public int deleteRoleResource(@Param(value = "roleId") String roleId);
	/**
	 * 删除角色的权限信息
	 * @param resourceId	资源id 
	 * 使用@Param注解主要是设置mapping中可以使用参数名进行取值
	 * @return
	 */
	public int deleteRoleResourceBysId(@Param(value = "resourceId") String resourceId);
	
	/**
	 * 批量添加角色和权限映射信息
	 * @return
	 */
	public int addRoleResourceBatch(Map<String, Object> parameter);
	
	/**
	 * 添加角色和权限映射信息
	 * @return
	 */
	public int addRoleResource(Map<String, Object> parameter);

	public RoleEntity findUserRoleByUserId(@Param(value = "userId") String userId);

	/**
	 * 根据用户id List 查询用户的角色
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<UserRoleVo> findUserRoleByUserIds(Map<String, Object> paramMap);
}
