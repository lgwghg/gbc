package com.webside.user.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.common.CacheConstant;
import com.webside.shiro.cache.redis.VCache;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserIncrement;
import com.webside.user.model.vo.UserCacheVo;
import com.webside.user.service.IUserCacheService;
import com.webside.user.service.UserService;

/**
 * 用户信息存redis缓存
 * 
 * @author tianguifang
 * 
 */
@Service("userCacheService")
public class UserCacheService implements IUserCacheService {
	@Autowired
	private UserService userService;
	// 通过线程池获取线程
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();// 启动线程池


	/**
	 * 缓存中的用户信息，转换成userEntity 对象
	 * 
	 * @param vo
	 * @return
	 */
	private UserEntity exchangeUserCacheVoToUser(UserCacheVo vo) {
		UserEntity user = new UserEntity();
		user.setId(vo.getId());
		user.setNickName(vo.getN());
		user.setMobile(vo.getM());
		user.setPhoto(vo.getP());
		user.setEmail(vo.getE());
		user.setSign(vo.getS());
		user.setRankLevel(vo.getRl());
		user.setCampaignKey(vo.getCk());
		user.setAlipayAccount(vo.getPa());

		UserIncrement userIncrement = new UserIncrement();
		userIncrement.setUserId(vo.getId());
		userIncrement.setJcNum(vo.getJn());
		userIncrement.setVictoryNum(vo.getVn());
		userIncrement.setVictoryRate(vo.getVr());
		userIncrement.setTotalProfitGoldNum(vo.getPg());
		userIncrement.setIsInvolvementInJc((vo.getIj()));
		userIncrement.setUnreadNum(vo.getUn());
		userIncrement.setSignNum(vo.getSn());
		user.setUserIncrement(userIncrement);

		return user;
	}
	@Override
	public UserEntity getUserEntityByUserId(String userId) {
		UserCacheVo vo = VCache.get(CacheConstant.UserConstant.USER_REDIS_CACHE_KEY + userId, UserCacheVo.class);
		if (vo != null) {
			UserEntity user = exchangeUserCacheVoToUser(vo);
			return user;
		} else {
			UserEntity userEntity = userService.findUserById(userId);// 用户信息部分返回
			UserEntity user = null;
			if (userEntity != null) {
				user = new UserEntity();
				user.setId(userEntity.getId());
				user.setNickName(userEntity.getNickName());
				user.setMobile(userEntity.getMobile());
				user.setPhoto(userEntity.getPhoto());
				user.setEmail(userEntity.getEmail());
				user.setSign(userEntity.getSign());
				user.setRankLevel(userEntity.getRankLevel());
				user.setAlipayAccount(userEntity.getAlipayAccount());// 支付宝账号
				user.setCampaignKey(userEntity.getCampaignKey());
				userEntity.getUserIncrement().setUserId(userEntity.getId());
				user.setUserIncrement(userEntity.getUserIncrement());
				this.updateUserToRedis(userEntity);

			}
			return user;
		}
	}


	@Override
	public void updateUserToRedis(UserEntity userEntity) {
		cachedThreadPool.execute(new UpdateRedisUserRunner(userEntity, userService));
	}

	/**
	 * 更新redis中的user信息
	 * 
	 * @author suyan
	 * 
	 */
	class UpdateRedisUserRunner implements Runnable {
		private UserEntity userEntity;
		private UserService userService;

		public UpdateRedisUserRunner(UserEntity userEntity, UserService userService) {
			this.userEntity = userEntity;
			this.userService = userService;
		}

		@Override
		public synchronized void run() {
			if (userEntity == null || StringUtils.isBlank(userEntity.getId())) {
				return;
			}
			UserCacheVo vo = VCache.get(CacheConstant.UserConstant.USER_REDIS_CACHE_KEY + userEntity.getId(), UserCacheVo.class);
			if (vo == null) {
				userEntity = userService.findById(userEntity.getId());
				this.saveUserToRedis(userEntity);
			} else {
				vo = exchangeUserEntity(vo, userEntity);
				// 存缓存
				VCache.set(CacheConstant.UserConstant.USER_REDIS_CACHE_KEY + userEntity.getId(), vo, 0);
			}
		}

		private void saveUserToRedis(UserEntity userEntity) {
			if (userEntity != null) {
				if (userEntity.getId() != null) {
					UserCacheVo vo = new UserCacheVo();
					vo = this.exchangeUserEntity(vo, userEntity);
					// 存缓存
					VCache.set(CacheConstant.UserConstant.USER_REDIS_CACHE_KEY + userEntity.getId(), vo, 0);
				}
			}
		}

		/**
		 * userEntity对象转换成缓存中的userCacheVo对象
		 * 
		 * @param vo
		 * @param userEntity
		 * @return
		 */
		private UserCacheVo exchangeUserEntity(UserCacheVo vo, UserEntity userEntity) {
			if (StringUtils.isBlank(vo.getId())) {
				vo.setId(userEntity.getId());
			}

			if (StringUtils.isNotBlank(userEntity.getNickName())) {
				vo.setN(userEntity.getNickName());
			}
			if (StringUtils.isNotBlank(userEntity.getMobile())) {
				vo.setM(userEntity.getMobile());
			}
			if (StringUtils.isNotBlank(userEntity.getPhoto())) {
				vo.setP(userEntity.getPhoto());
			}
			if (StringUtils.isNotBlank(userEntity.getEmail())) {
				vo.setE(userEntity.getEmail());
			}
			if (userEntity.getSign() != null) {
				vo.setS(userEntity.getSign());
			}
			if (StringUtils.isNotBlank(userEntity.getRankLevel())) {
				vo.setRl(userEntity.getRankLevel());
			}
			if (StringUtils.isNotBlank(userEntity.getCampaignKey())) {
				vo.setCk(userEntity.getCampaignKey());
			}
			if (StringUtils.isNotBlank(userEntity.getAlipayAccount())) {
				vo.setPa(userEntity.getAlipayAccount());
			}

			// 用户增量信息
			if (userEntity.getUserIncrement() != null) {
				if (userEntity.getUserIncrement().getJcNum() != null) {
					vo.setJn(userEntity.getUserIncrement().getJcNum());
				}
				if (userEntity.getUserIncrement().getVictoryNum() != null) {
					vo.setVn(userEntity.getUserIncrement().getVictoryNum());
				}
				if (userEntity.getUserIncrement().getVictoryRate() != null) {
					vo.setVr(userEntity.getUserIncrement().getVictoryRate());
				}
				if (userEntity.getUserIncrement().getTotalProfitGoldNum() != null) {
					vo.setPg(userEntity.getUserIncrement().getTotalProfitGoldNum());
				}
				if (userEntity.getUserIncrement().getUnreadNum() != null) {
					vo.setUn(userEntity.getUserIncrement().getUnreadNum());
				}
				if (userEntity.getUserIncrement().getSignNum() != null) {
					vo.setSn(userEntity.getUserIncrement().getSignNum());
				}
				if (userEntity.getUserIncrement().getIsInvolvementInJc() != null) {
					vo.setIj(userEntity.getUserIncrement().getIsInvolvementInJc());
				}
			}
			return vo;
		}
	}

}
