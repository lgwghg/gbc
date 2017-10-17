/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sn.service;

import java.util.List;
import java.util.Map;
import com.webside.system.sn.entities.SysWebNotice;

/**
 * 网站公告服务接口
 * 
 * @author zhangfei
 * @date 2016-12-22 18:11:32
 */
public interface ISysWebNoticeService {
	/**
	 * 按条件查询网站公告
	 * 
	 * @throws Exception
	 * @author zhangfei
	 */
	public List<SysWebNotice> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增网站公告
	 * 
	 * @param SysWebNotice
	 * @throws Exception
	 * @author zhangfei
	 */
	public int insert(final SysWebNotice entity);

	/**
	 * 修改网站公告
	 * 
	 * @param entity
	 * @throws Exception
	 * @author zhangfei
	 */
	public int update(final SysWebNotice entity);

	/**
	 * 根据ID获取网站公告
	 * 
	 * @param ID
	 * @throws Exception
	 * @author zhangfei
	 */
	public SysWebNotice findById(String id);

	/**
	 * 根据对象删除网站公告
	 * 
	 * @param SysWebNotice
	 * @throws Exception
	 * @author zhangfei
	 */
	public int deleteBatchById(List<String> ids);
}
