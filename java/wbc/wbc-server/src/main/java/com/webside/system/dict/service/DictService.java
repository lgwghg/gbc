package com.webside.system.dict.service;

import java.util.List;
import java.util.Map;

import com.webside.system.dict.model.Dict;

public interface DictService {

	public List<Dict> queryListByPage(Map<String, Object> parameter);
	
	public int insert(Dict dict);
	
	public Dict findById(String id);
	
	public int update(Dict dict);
	
	/**
	 * @title  : 根据字典分类查询字典
	 * @param  : String type
	 * */
	public List<Dict> getDictType(String type);
    
	/**
	 * 查询所有字典
	 * */
	public List<Dict> queryList();
	
	/**
	 * 根据type 和 value 查询字典对象
	 * @param : String type
	 * @param : String value
	 * */
	public Dict getDict(String type,String value);
}