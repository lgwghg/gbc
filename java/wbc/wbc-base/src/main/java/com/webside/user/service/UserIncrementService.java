package com.webside.user.service;

import java.util.List;
import java.util.Map;

import com.webside.user.model.UserIncrement;

public interface UserIncrementService {

	List<UserIncrement> queryListByPage(Map<String, Object> parameters);

	UserIncrement queryUserIncrementByUserId(String userId);

	//
	// /**
	// * 新增
	// * @param userIncrement
	// * @throws Exception
	// */
	// public int insert(final UserIncrement userIncrement);
	
	/**
	 * 修改
	 * @param userIncrement
	 * @throws Exception
	 */
	public int update(final UserIncrement userIncrement);
	
	/**
	 * 更新用户增量信息
	 * 
	 * @param userIncrement
	 * @return
	 * @author tianguifang
	 */
	public int updateUserIncrement(UserIncrement userIncrement);

	/**
	 * 盈利top
	 * 
	 * @param count
	 * @return
	 */
	List<String> profitTop(Integer count);

	/**
	 * 盈率top
	 * 
	 * @param count
	 * @return
	 */
	List<String> profitRateTop(Integer count);
}
