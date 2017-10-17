/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sw.service;

import java.util.List;
import java.util.Map;
import com.webside.system.sw.entities.SensitiveWords;

/**
 * 敏感词服务接口
 *
 * @author zengxn
 * @date 2016-11-24 16:21:45
 */
public interface ISensitiveWordsService 
{
	/**
	 * 按条件查询敏感词
	 * @throws Exception
	 * @author zengxn
	 */
	public List<SensitiveWords> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增敏感词
	 * @param SensitiveWords
	 * @throws Exception
	 * @author zengxn
	 */
	public int insert(final SensitiveWords entity);
	
	/**
	 * 修改敏感词
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	public int update(final SensitiveWords entity);
	
	/**
	 * 根据ID获取敏感词
	 * @param ID
	 * @throws Exception
	 * @author zengxn
	 */
	public SensitiveWords findById(String id);
	
	/**
	 * 根据对象删除敏感词
	 * @param id
	 * @throws Exception
	 * @author zengxn
	 */
	public int deleteById(String id);
	
	/**
	 * 初始化敏感词到redis
	 */
	public void initSensitiveWordToRedis();
}
