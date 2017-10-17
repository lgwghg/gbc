/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.transaction.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.system.dict.service.DictService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.mapper.IUserTransactionLogMapper;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.util.DateUtils;

/**
 * 用户交易记录(钱包明细) 业务接口
 * 
 * @author tianguifang
 * @date 2016-11-24 15:54:19
 */
@Service("userTransactionLogService")
public class UserTransactionLogService extends AbstractService<UserTransactionLog, String> implements IUserTransactionLogService {
	/**
	 * 用户交易记录 DAO定义
	 */
	@Autowired
	private IUserTransactionLogMapper userTransactionLogMapper;
	@Autowired
	private IUserCacheService userCacheService;
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(userTransactionLogMapper);
	}

	@Override
	public List<UserTransactionLog> queryListByPage(Map<String, Object> parameter) {
		List<UserTransactionLog> list = null;
		try {
			list = userTransactionLogMapper.queryListByPage(parameter);
			if (list != null && list.size() > 0) {
				for (UserTransactionLog transaction : list) {
					String userId = transaction.getUserId();
					if (StringUtils.isNotBlank(userId)) {
						UserEntity user = userCacheService.getUserEntityByUserId(userId);
						if (user != null) {
							transaction.setUserNick(user.getNickName());
						}
					}
					//货币类型显示名称
					transaction.setGoldTypeName(dictService.getDict(GlobalConstant.GOLD_TYPE, String.valueOf(transaction.getGoldType())).getLabel());
					//货币类型显示样式
					transaction.setGoldTypeClass(dictService.getDict(GlobalConstant.GOLD_TYPE, String.valueOf(transaction.getGoldType())).getLabelClass());
				}
			}
		} catch (Exception e) {
			logger.error("根据条件查询用户交易记录出错：", e);
		}
		return list;
	}

	public Map<String, Long> getMapForWeekJc(String userId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userId", userId);
		parameter.put("utType", GlobalConstant.USER_TRANSACTION_TYPE_1);// 竞猜
		// 七天前
		String beginUtTime = DateUtils.getBeforeDay("yyyy-MM-dd", 7);
		parameter.put("beginUtTime", beginUtTime);
		String endUtTime = DateUtils.getBeforeDay("yyyy-MM-dd", 0);
		parameter.put("endUtTime", endUtTime);

		List<UserTransactionLog> list = userTransactionLogMapper.findListForJc(parameter);
		if (list != null && !list.isEmpty()) {
			Long total = 0L;// 总金额
			String nowDate = "";// 当前日期
			Map<String, Long> map = new HashMap<String, Long>();
			for (UserTransactionLog userTransactionLog : list) {
				String utTime = userTransactionLog.getUtTime();
				Long goldNum = userTransactionLog.getGoldNum();
				goldNum = goldNum == null ? 0 : goldNum;

				String date = DateUtils.formatDate(DateUtils.parseDate(utTime), "MM-dd");
				Long gold = MapUtils.getLong(map, date);
				gold = gold == null ? 0 : gold;
				if(date.equals(nowDate)) {
					gold = gold + goldNum;// 本日金额
				} else {
					nowDate = date;
					gold = total + goldNum;// 本日金额
				}

				map.put(date, gold);
				total += goldNum;
			}
			map.put("total", total);
			
			return map;
		}

		return null;
	}

}
