package com.webside.system.dict.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.system.dict.model.Dict;

@Repository
public interface DictMapper extends BaseMapper<Dict, String>{
	
	/**
	 * @title  : 根据字典分类查询字典
	 * @param  : String type
	 * */
	public List<Dict> getDict_Type(Map<String, String> map);
	
	/**
	 * 根据条件查询所有字典
	 * */
	public List<Dict> queryList(Map<String, Object> map);
}
