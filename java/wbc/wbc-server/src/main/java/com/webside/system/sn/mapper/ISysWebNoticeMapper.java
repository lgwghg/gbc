/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sn.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.system.sn.entities.SysWebNotice;

/**
 * 网站公告数据访问接口
 * 
 * @author zhangfei
 * @date 2016-12-22 18:11:32
 */
@Repository
public interface ISysWebNoticeMapper extends BaseMapper<SysWebNotice, String> {

}
