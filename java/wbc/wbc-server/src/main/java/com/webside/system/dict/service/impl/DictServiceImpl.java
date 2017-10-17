package com.webside.system.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.CacheConstant;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.shiro.cache.ICache;
import com.webside.shiro.cache.redis.VCache;
import com.webside.system.dict.mapper.DictMapper;
import com.webside.system.dict.model.Dict;
import com.webside.system.dict.service.DictService;
import com.webside.util.SerializeUtil;

@Service("dictService")
public class DictServiceImpl extends AbstractService<Dict, String> implements DictService{

	@Autowired
	private DictMapper dictMapper;
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为dictMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(dictMapper);
	}
	
	/**
	 * @title  : 根据字典分类查询字典
	 * @param  : String type
	 * */
	public List<Dict> getDictType(String type){
		List<Dict> list = null;
		try{
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", type);
			map.put("isDelete", GlobalConstant.DICTTYPE_IS_DELETE_0);
			//list = dictMapper.getDict_Type(map);
			List<Dict> listDict = queryList();
			if(listDict!=null && listDict.size()>0){
				list = new ArrayList<Dict>();
				for(Dict d:listDict){
					if(d.getType().equals(type)){
						list.add(d);
					}
				}
			}
		}catch(Exception e){	
			throw new ServiceException(e);
		}
		return list;
	}
	
	public List<Dict> queryList(){
		List<Dict> list = null;
		try{
			//先查询缓存是否存在
			//如果存在获得数据直接返回 ，如果不存在查询数据库
			String key = CacheConstant.CACHE_CODE_INFO;
			//VCache.delByKey(key);
			if(VCache.exists(key)){
				list = VCache.get(key, List.class);
			}
			if(list == null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("delFlag", GlobalConstant.DICTTYPE_IS_DELETE_0);
				list = dictMapper.queryList(map);
				if(list!=null && list.size()>0){
					//添加缓存
					VCache.set(key, list);
				}
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
		return list;
	}
	/**
	 * 根据type 和 value 查询字典对象
	 * @param : String type
	 * @param : String value
	 * */
	public Dict getDict(String type,String value){
		Dict dict = null;
		try{
			//从缓存中查询所有字典
			List<Dict> list = queryList();
			if(list!=null && list.size()>0){
				for(Dict d:list){
					if(d.getType().equals(type) && d.getValue().equals(value)){
						dict = d;
						break;
					}
				}
			}
			if(dict==null){
				dict = new Dict();
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
		return dict;
	}
}
