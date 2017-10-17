package com.webside.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.exception.ServiceException;
import com.webside.user.mapper.UserIncrementMapper;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserIncrement;
import com.webside.user.service.IUserCacheService;
import com.webside.user.service.UserIncrementService;
@Service("userIncrementService")
public class UserIncrementServiceImpl extends AbstractService<UserIncrement, String> implements UserIncrementService{
	@Autowired
	private UserIncrementMapper userIncrementMapper;
	@Autowired
	private IUserCacheService userCacheService;
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(userIncrementMapper);
	}
	
	// @Override
	// public int insert(UserIncrement t) {
	// int result = 0;
	// try {
	// t.setId(IdGen.uuid());
	// t.setCreateOperatorId(t.getUserId());
	// t.setCreateTime(System.currentTimeMillis());
	// result = super.insert(t);
	// } catch (Exception e) {
	// throw new ServiceException(e);
	// }
	// return result;
	// }
	
	@Override
	public int update(UserIncrement t) {
		int result = 0;
		try {
			t.setUpdateTime(System.currentTimeMillis());
			t.setUpdateOperatorId(t.getUserId());
			result = super.update(t);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public UserIncrement queryUserIncrementByUserId(String userId) {
		return userIncrementMapper.queryUserIncrementByUserId(userId);
	}

	/**
	 * 更新用户增量信息
	 * 
	 * @author tianguifang
	 */
	public int updateUserIncrement(UserIncrement userIncrement) {
		if (userIncrement == null || userIncrement.getUserId() == null) {
			return 0;
		}
		int result = 0;
		result = update(userIncrement);
		UserIncrement increment = userIncrementMapper.queryUserIncrementByUserId(userIncrement.getUserId());
		UserEntity user = new UserEntity();
		user.setId(increment.getUserId());
		user.setUserIncrement(increment);
		userCacheService.updateUserToRedis(user);
		return result;
	}

	@Override
	public List<String> profitTop(Integer count) {
		return userIncrementMapper.profitTop(count);
	}

	@Override
	public List<String> profitRateTop(Integer count) {
		return userIncrementMapper.profitRateTop(count);
	}
}
