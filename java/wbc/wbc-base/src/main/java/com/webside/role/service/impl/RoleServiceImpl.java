package com.webside.role.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.exception.ServiceException;
import com.webside.role.mapper.RoleMapper;
import com.webside.role.model.RoleEntity;
import com.webside.role.service.RoleService;
import com.webside.user.model.vo.UserRoleVo;
import com.webside.util.IdGen;

@Service("roleService")
public class RoleServiceImpl extends AbstractService<RoleEntity, String>
		implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(roleMapper);
	}

	@Override
	public boolean addRolePermBatch(String roleId, List<String> ids) {
		boolean flag = false;
		try {
			int permCount = roleMapper.findRoleResourceById(roleId);
			boolean delFlag = true;
			if (permCount > 0) {
				int delResult = roleMapper.deleteRoleResource(roleId);
				if (permCount != delResult) {
					delFlag = false;
				}
			}

			if (delFlag) {
				if (ids.size() > 0) {
					if (CollectionUtils.isNotEmpty(ids)) {
						int addResult = 0;
						for (String resourceId : ids) {
							Map<String, Object> parameter = new HashMap<String, Object>();
							parameter.put("id", IdGen.uuid());
							parameter.put("roleId", roleId);
							parameter.put("resourceId", resourceId);
							int result = roleMapper.addRoleResource(parameter);
							addResult += result;
						}
//						parameter.put("roleResourcesList", roleResourcesList);
//						int addResult = roleMapper.addRoleResourceBatch(parameter);
						if (addResult == ids.size()) {
							flag = true;
						}
					}
				} else {
					flag = true;
				}
			}
			return flag;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean deleteRoleById(String id) {
		try {
			// 1、删除该角色的权限信息
			roleMapper.deleteRoleResource(id);
			// 2、删除该角色
			if (roleMapper.deleteById(id) > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int findRoleUserById(String roleId) {
		return roleMapper.findRoleUserById(roleId);
	}

	@Override
	public boolean addRolePerm(String roleId, String resourceId) {
		try {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("id", IdGen.uuid());
			parameter.put("roleId", roleId);
			parameter.put("resourceId", resourceId);
			return roleMapper.addRoleResource(parameter) > 0 ? true : false;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean deleteRolePermByResource(String resourceId) {
		try {
			return roleMapper.deleteRoleResourceBysId(resourceId) > 0 ? true : false;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public RoleEntity findUserRoleByUserId(String userId) {
		// VCache.delByKey(CacheConstant.UserConstant.USER_ROLE_REDIS_CACHE_KEY
		// + userId);
		// RoleEntity role =
		// VCache.get(CacheConstant.UserConstant.USER_ROLE_REDIS_CACHE_KEY +
		// userId, RoleEntity.class);
		// if (role == null) {
		RoleEntity role = roleMapper.findUserRoleByUserId(userId);
		// if(role!=null){
		// VCache.set(CacheConstant.UserConstant.USER_ROLE_REDIS_CACHE_KEY +
		// userId, role, 0);
		// }
		// }
		return role;
	}

	/**
	 * 根据用户id list 查询用户角色信息 返回map
	 */
	@Override
	public Map<String, UserRoleVo> findUserRoleByUserIds(List<String> userIdList) {
		if (CollectionUtils.isEmpty(userIdList)) {
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userIdList", userIdList);
		List<UserRoleVo> userRoleVoList = roleMapper.findUserRoleByUserIds(paramMap);
		Map<String, UserRoleVo> userRoleVoMap = null;
		if (CollectionUtils.isNotEmpty(userRoleVoList)) {
			userRoleVoMap = new HashMap<>();
			for (UserRoleVo vo : userRoleVoList) {
				userRoleVoMap.put(vo.getUserId(), vo);
			}
			
		}
		return userRoleVoMap;
	}

}
