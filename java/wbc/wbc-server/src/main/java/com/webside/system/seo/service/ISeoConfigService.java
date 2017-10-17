/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.seo.service;

import java.util.List;
import java.util.Map;

import com.webside.system.seo.entities.SeoConfig;

/**
 * SEO配置服务接口
 *
 * @author zengxn
 * @date 2016-10-19 17:36:48
 */
public interface ISeoConfigService 
{
	/**
	 * 按条件查询SEO配置
	 * @throws Exception
	 * @author zengxn
	 */
	public List<SeoConfig> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增SEO配置
	 * @param SeoConfig
	 * @throws Exception
	 * @author zengxn
	 */
	public int insert(final SeoConfig entity);
	
	/**
	 * 修改SEO配置
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	public int update(final SeoConfig entity);
	
	/**
	 * 根据ID获取SEO配置
	 * @param ID
	 * @throws Exception
	 * @author zengxn
	 */
	public SeoConfig findById(String id);
	
	/**
	 * 根据id删除SEO配置
	 * @param SeoConfig
	 * @throws Exception
	 * @author zengxn
	 */
	public int deleteById(String id);
	
	/**
	 * 查询SEO配置
	 * @throws Exception
	 * @author zengxn
	 */
	public int findEntityByType(String type,String id);
	
	/**
	 * 查询全部SEO配置
	 * @throws Exception
	 * @author zengxn
	 */
	public Map<String,Map<String, String>> list();
}
