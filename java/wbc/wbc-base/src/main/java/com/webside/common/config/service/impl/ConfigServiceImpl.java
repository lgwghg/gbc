package com.webside.common.config.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.config.mapper.ConfigMapper;
import com.webside.common.config.model.Config;
import com.webside.common.config.service.ConfigService;
import com.webside.shiro.cache.ICache;
import com.webside.shiro.cache.redis.RedisCache;

@Service("configService")
public class ConfigServiceImpl extends AbstractService<Config, String> implements ConfigService {
	private static final Log LOG = LogFactory.getLog(ConfigServiceImpl.class);
	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private ICache redisCache;
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(configMapper);
	}
	
	/**
	 * 根据配置key获取value
	 * @param key
	 * @return
	 * @author tianguifang
	 * @date 2016-08-29
	 */
	@Override
	public String findConfigValueByKey(String key) {
		String redisKey = "wbc:config:";
		redisKey = redisKey + key;
		// 先从缓存获取
		String configValue = redisCache.get(redisKey);
		if (configValue == null) {
			// 缓存获取不到从数据库获取
			configValue = configMapper.findConfigValueByKey(key);
			if (configValue != null) {
				redisCache.setEx(redisKey, configValue, RedisCache.TEN_MINITES);// 缓存10分钟
			} else {
				// TODO 发邮件告知开发者配置
				LOG.error("获取配置未找到，请尽快处理，配置key=" + key);
			}
		}
		return configValue;
	}
	
	@Override
	public int updateByKeyValue(String key, String value) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("key", key);
		param.put("value", value);
		param.put("date", System.currentTimeMillis());
		return configMapper.updateByKeyValue(param);
	}
	
}
