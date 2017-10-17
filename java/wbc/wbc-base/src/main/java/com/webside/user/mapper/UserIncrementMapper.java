package com.webside.user.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.model.UserIncrement;
@Repository
public interface UserIncrementMapper extends BaseMapper<UserIncrement, String>{

	public UserIncrement queryUserIncrementByUserId(@Param("userId")String userId);

	/**
	 * 盈利top user_id
	 * 
	 * @param count
	 * @return
	 */
	public List<String> profitTop(Integer count);

	/**
	 * 盈率top user_id
	 * 
	 * @param count
	 * @return
	 */
	public List<String> profitRateTop(Integer count);
	
}
