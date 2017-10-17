package com.webside.jc.mapper;

import java.util.List;
import java.util.Map;

import com.webside.base.basemapper.BaseMapper;
import com.webside.jc.model.UserJc;

/**
 * @title : 用户竞猜数据库接口
 * @author LiGan
 * */
public interface IUserJcMapper extends BaseMapper<UserJc, String> {

	/**
	 * @title : 统计用户参与竞猜G币量
	 * */
	public String getUserJcGold(Map<String, Object> map);
	
	/**
	 * 获取你的竞猜列表
	 * @param userId 用户ID 
	 * @return
	 * @author zhangfei
	 */
	public List<UserJc> findListByUserId(String userId);

	/**
	 * 后台用户竞猜管理用
	 * 
	 * @param param
	 * @return
	 */
	public List<UserJc> queryJcListByPage(Map<String, Object> param);
	
	/**
	 * @title : 根据条件查询用户竞猜记录
	 * @param String ID
	 * */
	public List<UserJc> findByMap(Map<String, Object> param);
}
