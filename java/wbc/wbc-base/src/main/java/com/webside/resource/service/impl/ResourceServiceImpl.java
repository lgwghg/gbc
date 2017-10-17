package com.webside.resource.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.exception.ServiceException;
import com.webside.resource.mapper.ResourceMapper;
import com.webside.resource.model.ResourceEntity;
import com.webside.resource.service.ResourceService;
import com.webside.role.service.RoleService;

@Service("resourceService")
public class ResourceServiceImpl extends AbstractService<ResourceEntity, String>
		implements ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private RoleService roleService;

	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(resourceMapper);
	}

	@Override
	public List<ResourceEntity> findResourcesByUserId(String userId) {
		return resourceMapper.findResourcesByUserId(userId);
	}

	@Override
	public List<ResourceEntity> queryResourceList(Map<String, Object> parameter) {
		return resourceMapper.queryResourceList(parameter);
	}

	@Override
	public List<ResourceEntity> findResourcesMenuByUserId(String userId) {
		return resourceMapper.findResourcesMenuByUserId(userId);
	}

	@Override
	public int deleteBatchById(List<String> resourceIds) {
		try {
			for (String resourceId : resourceIds) {
				roleService.deleteRolePermByResource(resourceId);
			}
			return resourceMapper.deleteBatchById(resourceIds);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
