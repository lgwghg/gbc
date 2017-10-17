package com.webside.jc.service;

import java.util.List;
import java.util.Map;

import com.webside.jc.model.UserJc;

/**
 * @title : 用户竞猜业务接口
 * @author LiGan
 * */
public interface IUserJcService {
	
	/**
	 * @title ： 根据条件查询用户竞猜记录
	 * @param : String userId  用户ID
	 * @param : String gbId	   对战比赛ID
	 * @param : List gbIds 对战比赛ID（多条 ）
	 * @param : String gameResult  0:输 1：赢  2 ：进行中 3：已取消
	 * */
	public List<UserJc> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * @title : 根据ID查询用户竞猜记录
	 * @param String ID
	 * */
	public UserJc findById(String id);
	
	/**
	 * @title : 根据条件查询用户竞猜记录
	 * @param String ID
	 * */
	public List<UserJc> findByMap(Map<String, Object> param);
	
	/**
	 * @title : 新增用户竞猜记录
	 * @param UserJc uj
	 * */
	public int insert(UserJc uj);
	
	/**
	 * @title : 修改用户竞猜记录
	 * @param UserJc uj
	 * */
	public int update(UserJc uj);
	
	/**
	 * @title : 更换下注战队
	 * */
	public void updateUserJcTeam(UserJc uj);
	
	/**
	 * @title : 统计用户参与竞猜G币量
	 * @param : String userId
	 * */
	public String getUserJcGold(String userId);
	
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

}
