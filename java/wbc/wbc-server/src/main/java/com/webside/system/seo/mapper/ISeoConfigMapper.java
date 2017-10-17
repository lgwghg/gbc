/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.seo.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.system.seo.entities.SeoConfig;

/**
 * SEO配置数据访问接口
 *
 * @author zengxn
 * @date 2016-10-19 17:36:48
 */
@Repository
public interface ISeoConfigMapper extends BaseMapper<SeoConfig, String> 
{
	/**
	 * 查询SEO配置
	 * @throws Exception
	 * @author zengxn
	 */
	public int findByType(Map<String, Object> map);
}
