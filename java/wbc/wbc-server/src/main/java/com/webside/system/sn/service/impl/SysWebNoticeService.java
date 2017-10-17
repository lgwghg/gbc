/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.sn.service.ISysWebNoticeService;
import com.webside.system.sn.mapper.ISysWebNoticeMapper;
import com.webside.system.sn.entities.SysWebNotice;
import com.webside.util.IdGen;

/**
 * 网站公告服务实现类
 * 
 * @author zhangfei
 * @date 2016-12-22 18:11:32
 */
@Service("sysWebNoticeService")
public class SysWebNoticeService extends AbstractService<SysWebNotice, String> implements ISysWebNoticeService {
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(sysWebNoticeMapper);
	}

	/**
	 * 网站公告 DAO定义
	 */
	@Autowired
	private ISysWebNoticeMapper sysWebNoticeMapper;

	public int insert(SysWebNotice webNotice) {
		webNotice.setId(IdGen.uuid());// 设置ID 生成 UUID
		webNotice.setAddTime(System.currentTimeMillis());
		webNotice.setSysUserId(ShiroAuthenticationManager.getUserId());

		return sysWebNoticeMapper.insert(webNotice);
	}
}
