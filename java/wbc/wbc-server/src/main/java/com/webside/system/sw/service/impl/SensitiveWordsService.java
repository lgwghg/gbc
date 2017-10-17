/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.CacheConstant;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.shiro.cache.redis.VCache;
import com.webside.system.dict.service.DictService;
import com.webside.system.sw.entities.SensitiveWords;
import com.webside.system.sw.mapper.ISensitiveWordsMapper;
import com.webside.system.sw.service.ISensitiveWordsService;
import com.webside.user.model.UserEntity;
import com.webside.util.IdGen;

/**
 * 敏感词服务实现类
 *
 * @author zengxn
 * @date 2016-11-24 16:21:45
 */
@Service("sensitiveWordsService")
public class SensitiveWordsService extends AbstractService<SensitiveWords, String> implements ISensitiveWordsService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(sensitiveWordsMapper);
	}

	/**
	 * 敏感词 DAO定义
	 */
	@Autowired
	private ISensitiveWordsMapper sensitiveWordsMapper;
	
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;
	
	// 通过线程池获取线程
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();// 启动线程池
	
	@Override
	public int insert(SensitiveWords t) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setId(IdGen.uuid());
			t.setCreateTime((System.currentTimeMillis()+""));
			t.setCreateOperatorId(user.getId());
			t.setCreateOperatorName(user.getNickName());
			result = super.insert(t);
			if(result==1){
				VCache.delByKey(CacheConstant.SYSTEM_SENSITIVEWORDS_SET);
				initSensitiveWordToRedis();
			}
		} catch (Exception e) {
			logger.error("新增敏感词出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public int update(SensitiveWords t) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setUpdateTime(System.currentTimeMillis()+"");
			t.setUpdateOperatorId(user.getId());
			t.setUpdateOperatorName(user.getNickName());
			result = super.update(t);
			if(result==1){
				VCache.delByKey(CacheConstant.SYSTEM_SENSITIVEWORDS_SET);
				initSensitiveWordToRedis();
			}
		} catch (Exception e) {
			logger.error("修改敏感词出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public List<SensitiveWords> queryListByPage(Map<String, Object> parameter) {
		List<SensitiveWords> list = new ArrayList<SensitiveWords>();
		try {
			list = sensitiveWordsMapper.queryListByPage(parameter);
			if(list!=null && list.size()>0){
				for (SensitiveWords sensitiveWords : list) {
					//作用范围
					sensitiveWords.setUseTypeName(dictService.getDict(GlobalConstant.SW_USE_TYPE, String.valueOf(sensitiveWords.getUseType())).getLabel());
					sensitiveWords.setUseTypeClass(dictService.getDict(GlobalConstant.SW_USE_TYPE, String.valueOf(sensitiveWords.getUseType())).getLabelClass());
					//所属类型
					sensitiveWords.setContentTypeName(dictService.getDict(GlobalConstant.SW_CONTENT_TYPE, String.valueOf(sensitiveWords.getContentType())).getLabel());
					sensitiveWords.setContentTypeClass(dictService.getDict(GlobalConstant.SW_CONTENT_TYPE, String.valueOf(sensitiveWords.getContentType())).getLabelClass());
				}
			}
		} catch (Exception e) {
			logger.error("查询敏感词出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}

	@Override
	public void initSensitiveWordToRedis() {
		cachedThreadPool.execute(new initSensitiveWordToRedisRunner());
	}
	
	/**
	 * 异步执行缓存插入
	 * @author zengxn
	 *
	 */
	class initSensitiveWordToRedisRunner implements Runnable {

		@Override
		public void run() {
			if(!VCache.exists(CacheConstant.SYSTEM_SENSITIVEWORDS_SET)){
				List<SensitiveWords> list = queryListByPage(null);
				// 初始化敏感词容器，减少扩容操作  
		        Map wordMap = new HashMap(list.size());  
		        for (SensitiveWords sensitiveWords : list) {  
		            Map nowMap = wordMap;  
		            String word = sensitiveWords.getContent();
		            for (int i = 0; i < word.length(); i++) {  
		                // 转换成char型  
		                char keyChar = word.charAt(i);  
		                // 获取  
		                Object tempMap = nowMap.get(keyChar);  
		                // 如果存在该key，直接赋值  
		                if (tempMap != null) {  
		                    nowMap = (Map) tempMap;  
		                }  
		                // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个  
		                else {  
		                    // 设置标志位  
		                    Map<String, String> newMap = new HashMap<String, String>();  
		                    newMap.put("e", "0");  
		                    // 添加到集合  
		                    nowMap.put(keyChar, newMap);  
		                    nowMap = newMap;  
		                }  
		                // 最后一个  
		                if (i == word.length() - 1) {  
		                    nowMap.put("e", "1");  
		                }
		            }  
		        }
		        //存放在redis
				VCache.set(CacheConstant.SYSTEM_SENSITIVEWORDS_SET, wordMap);
			}
		}
	}
}
