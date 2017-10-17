/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.seo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.system.seo.entities.SeoConfig;
import com.webside.system.seo.mapper.ISeoConfigMapper;
import com.webside.system.seo.service.ISeoConfigService;
import com.webside.util.IdGen;
import com.webside.system.dict.service.DictService;

/**
 * SEO配置服务实现类
 *
 * @author zengxn
 * @date 2016-10-19 17:36:48
 */
@Service("seoConfigService")
public class SeoConfigService extends AbstractService<SeoConfig, String> implements ISeoConfigService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(seoConfigMapper);
	}

	/**
	 * SEO配置 DAO定义
	 */
	@Autowired
	private ISeoConfigMapper seoConfigMapper;
	
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;
	
	@Override
	public int insert(SeoConfig t) {
		int result = 0;
		try {
			t.setId(IdGen.uuid());
			t.setCreateTime(System.currentTimeMillis()+"");
			result = super.insert(t);
		} catch (Exception e) {
			logger.error("新增SEO配置出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public int update(SeoConfig t) {
		int result = 0;
		try {
			t.setUpdateTime(System.currentTimeMillis()+"");
			result = super.update(t);
		} catch (Exception e) {
			logger.error("修改SEO配置出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	public List<SeoConfig> queryListByPage(Map<String, Object> parameter) {
		List<SeoConfig> list = null;
		try {
			list = seoConfigMapper.queryListByPage(parameter);
			if(list!=null && list.size()>0){
				for (SeoConfig seoConfig : list) {
					//类别显示名称
					seoConfig.setTypeName(dictService.getDict(GlobalConstant.SEO_CONFIG_TYPE, String.valueOf(seoConfig.getType())).getLabel());
				}
			}
		} catch (Exception e) {
			logger.error("查询SEO配置出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	@Override
	public int findEntityByType(String type,String id) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("type", type);
		parameter.put("id", id);
		return seoConfigMapper.findByType(parameter);
	}

	@Override
	public Map<String,Map<String, String>> list() {
		List<SeoConfig> list = seoConfigMapper.queryListByPage(null);
		Map<String,Map<String, String>> dataMap = new HashMap<String,Map<String, String>>();
		Map<String, String> map = null;
		for (SeoConfig seoConfig : list) {
			map = new HashMap<String, String>();
			map.put("keywords", seoConfig.getKeywords());
			map.put("description", seoConfig.getDescription());
			map.put("title", seoConfig.getTitle());
			dataMap.put(seoConfig.getType(), map);
		}
		return dataMap;
	}
}
