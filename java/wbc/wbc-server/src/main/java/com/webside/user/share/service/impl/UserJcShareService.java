/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.share.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.shiro.cache.redis.VCache;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.service.UserService;
import com.webside.user.share.entities.UserJcShare;
import com.webside.user.share.mapper.IUserJcShareMapper;
import com.webside.user.share.service.IUserJcShareService;
import com.webside.user.share.util.RedPurseUtil;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;

/**
 * 用户竞猜分享服务实现类
 *
 * @author tianguifang
 * @date 2017-01-11 14:34:07
 */
@Service("userJcShareService")
public class UserJcShareService extends AbstractService<UserJcShare, String> implements IUserJcShareService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userJcShareMapper);
	}

	/**
	 * 用户竞猜分享 DAO定义
	 */
	@Autowired
	private IUserJcShareMapper userJcShareMapper;
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	private IUserCacheService userCacheService;
	@Autowired
	private UserService userService;
	/**
	 * 获取红包
	 */
	@Override
	public JSONObject insertGetRedPurse(UserJcShare jcShare) {
		JSONObject json = new JSONObject();
		Integer times = todayGetRedPurseNum(jcShare.getUserMobile());
		if (times != null && times >= 4) {
			json.put("state", -1);
			json.put("msg", "今天您已经领过4次了哦，欢迎明天再来");
			return json;
		}
		jcShare.setId(IdGen.uuid());
		jcShare.setCreateTime(System.currentTimeMillis());
		String receivedKey = "get_redPurse:" + jcShare.getGbId() + "_" + jcShare.getUserCode();
		List<UserJcShare> jcShareList = findJcShareByGbId(jcShare);
		int rednum = 0;
		if (CollectionUtils.isNotEmpty(jcShareList)) {
			rednum = jcShareList.size();// 已领取的红包数
			if (rednum > 0) {
				Integer flag = 0;
				// 验证是否领过了
				for (UserJcShare share : jcShareList) {
					if (jcShare.getUserMobile().equals(share.getUserMobile())) {
						jcShare = share;
						flag = 1;
						break;
					}
				}
				if (flag == 1) {
					json.put("jcShareList", jcShareList);
					json.put("userJcShare", jcShare);
					json.put("state", 1);
					return json;
				}
			}
		}
		if (rednum == 10) {// 红包已领完
			json.put("jcShareList", jcShareList);
			json.put("state", 0);
			json.put("msg", "G币已领完，下次要加油了哦");
		} else { // 红包未领完
			UserJcShare goldShare = RedPurseUtil.getJcRedPurse(jcShare.getGbId(), jcShare.getUserCode(), rednum);
			if (goldShare != null && goldShare.getGold() > 0) {
				jcShare.setGold(goldShare.getGold());
				jcShare.setLucky(goldShare.getLucky());
				jcShare.setUserDesc(RedPurseUtil.getRandomUserDesc());
				UserEntity userParam = new UserEntity();
				userParam.setMobile(jcShare.getUserMobile());
				UserEntity user = userService.findUserByMobileOrEmailOrIdOrThirdKeyWithoutRole(userParam);
				if (user != null) {
					jcShare.setUserNick(user.getNickName());
					jcShare.setUserPhoto(user.getPhoto_65());
				}
				jcShareList.add(0, jcShare);
				json.put("jcShareList", jcShareList);
				json.put("userJcShare", jcShare);
				json.put("state", 1);
				json.put("msg", goldShare.getGold());
				// 异步保存数据
				insertRedPurse(jcShare);
				VCache.set(receivedKey, jcShareList, 60 * 60 * 24 * 7);// 缓存7天，过期后从再从数据库取
			} else {
				json.put("state", -1);
				json.put("msg", "G币红包已过期");
			}
		}

		return json;
	}

	@Override
	public void insertRedPurse(UserJcShare jcShare) {
		// 用户存在的保存数据库
		int result = userJcShareMapper.insert(jcShare);
		if (result > 0 && StringUtils.isNotBlank(jcShare.getUserId())) {
			// 更新钱包
			userWalletService.updateRechargeWallet(jcShare.getUserId(), jcShare.getGold(), GlobalConstant.RECHARGE_TYPE_GIFT);
			// 更新交易记录
			UserTransactionLog transactionLog = new UserTransactionLog();
			transactionLog.setId(IdGen.uuid());
			transactionLog.setDataId(jcShare.getId());
			transactionLog.setGoldNum(jcShare.getGold());
			transactionLog.setGoldType(GlobalConstant.GOLD_TYPE_2);
			transactionLog.setUserId(jcShare.getUserId());
			transactionLog.setUtTime(System.currentTimeMillis() + "");
			transactionLog.setRemarks("竞猜分享领取G币");
			transactionLog.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_8);
			UserEntity user = userCacheService.getUserEntityByUserId(jcShare.getUserId());
			transactionLog.setUserNick(user.getNickName());
			userTransactionLogService.insert(transactionLog);

		}
	}

	/**
	 * 今天已领取的红包数
	 * 
	 * @return
	 */
	@Override
	public Integer todayGetRedPurseNum(String userMobile) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userMobile", userMobile);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		param.put("time", calendar.getTimeInMillis());
		return userJcShareMapper.todayGetRedPurseNum(param);
	}

	/**
	 * 根据gbid + shareUserId查询竞猜分享
	 */
	@Override
	public List<UserJcShare> findJcShareByGbId(UserJcShare share) {
		// 已领红包list
		String receivedKey = "get_redPurse:" + share.getGbId() + "_" + share.getUserCode();
		List<UserJcShare> jcShares = VCache.get(receivedKey, List.class);
		if (CollectionUtils.isEmpty(jcShares)) {
			Map<String, Object> param = new HashMap<>();
			param.put("gbId", share.getGbId());
			param.put("shareUserId", share.getShareUserId());
			jcShares = userJcShareMapper.queryListByPage(param);
			if (CollectionUtils.isNotEmpty(jcShares)) {
				for (UserJcShare jcShare : jcShares) {
					UserEntity userParam = new UserEntity();
					userParam.setMobile(jcShare.getUserMobile());
					UserEntity user = userService.findUserByMobileOrEmailOrIdOrThirdKeyWithoutRole(userParam);
					if (user != null) {
						jcShare.setUserNick(user.getNickName());
						jcShare.setUserPhoto(user.getPhoto_65());
					}
				}
				VCache.set(receivedKey, jcShares, 60 * 60 * 24 * 7);// 缓存7天，过期后从再从数据库取
			}
		}
		return jcShares;
	}

	@Override
	public List<UserJcShare> queryJcShareByMobile(Map<String, Object> param) {
		return userJcShareMapper.queryJcShareByMobile(param);
	}
}
