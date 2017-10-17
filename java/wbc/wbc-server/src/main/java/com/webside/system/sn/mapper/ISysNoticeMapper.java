/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sn.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.system.sn.entities.SysNotice;

/**
 * 系统通知数据访问接口
 *
 * @author zengxn
 * @date 2016-11-25 16:27:18
 */
@Repository
public interface ISysNoticeMapper extends BaseMapper<SysNotice, String> 
{
	
}
