/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.cdkey.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.goods.model.Goods;
import com.webside.goods.service.IGoodsService;
import com.webside.goods.service.IGoodsStockService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.cdkey.entities.CdKey;
import com.webside.user.cdkey.mapper.ICdKeyMapper;
import com.webside.user.cdkey.service.ICdKeyService;
import com.webside.user.exchange.service.IUserExchangeLogService;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.service.UserService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.RandomDataUtil;

/**
 * cd-key兑换服务实现类
 *
 * @author tianguifang
 * @date 2017-04-07 11:47:48
 */
@Service("cdKeyService")
public class CdKeyService extends AbstractService<CdKey, String> implements ICdKeyService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(cdKeyMapper);
	}

	/**
	 * cd-key兑换 DAO定义
	 */
	@Autowired
	private ICdKeyMapper cdKeyMapper;
	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	@Autowired
	private IGoodsStockService goodsStockService;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IUserCacheService userCacheService;
	@Autowired
	private IUserMessageService userMessageService;
	@Autowired
	private IUserExchangeLogService userExchangeLogService;
	@Autowired
	private UserService userService;
	
	public List<CdKey> queryListByPage(Map<String, Object> parameter) {
		List<CdKey> cdkeyList = cdKeyMapper.queryListByPage(parameter);
		if (!CollectionUtils.isEmpty(cdkeyList)) {
			for (CdKey cdkey : cdkeyList) {
				if (cdkey.getType() == 0) {
					Goods goods = goodsService.findById(cdkey.getGoodsId());
					cdkey.setGoodsName(goods.getGoodsName());
					if (StringUtils.isNotBlank(cdkey.getExchangeUserId())) {
						UserEntity user = userCacheService.getUserEntityByUserId(cdkey.getExchangeUserId());
						cdkey.setExchangeUserNick(user.getNickName());
					}
				}
			}
		}
		return cdkeyList;
	}
	
	/**
	 * cdkey兑换
	 * @param code
	 * @return
	 * @author tianguifang
	 */
	@Override
	public Map<String, Object> updateExchange(String code, String addressId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cdkeyCode", code);
		List<CdKey> cdKeyList = cdKeyMapper.queryListByPage(param);
		if (!CollectionUtils.isEmpty(cdKeyList)) {
			CdKey cdKey = cdKeyList.get(0);
			if (cdKey.getState() != null && cdKey.getState() == 0) {
				// 验证时间在活动期间
				long currentTime = System.currentTimeMillis();
				if (currentTime < Long.valueOf(cdKey.getStartTime())) {
					result.put("state", "fail");
					result.put("msg", "该兑换码还未到兑换时间");
					return result;
				}
				if (currentTime > Long.valueOf(cdKey.getEndTime())) {
					result.put("state", "fail");
					result.put("msg", "该兑换码已过期，换个试试吧");
					return result;
				}
				
				// 按类别兑换
				Integer rechargeType = null; 
				Object messageRemarkObj = null;
				if (cdKey.getType() == 0) {//商品
					// 商品走商品流程，传code
					if (StringUtils.isBlank(addressId)) {
						result.put("state", "success");
						result.put("msg", "SP");
						return result;
					} else {
						Goods goods = goodsService.findById(cdKey.getGoodsId());
						messageRemarkObj = goods.getGoodsName();
						userExchangeLogService.insertForCDK(cdKey.getCdkeyCode(), addressId, "");
					}
				} else if (cdKey.getType() == 1) {// 金币
					rechargeType = 2;
					messageRemarkObj = cdKey.getGold();
				} else if (cdKey.getType() == 2) {// G币(体验币)
					messageRemarkObj = cdKey.getGold();
					rechargeType = 1;
				}
				CdKey key = new CdKey();
				key.setId(cdKey.getId());
				key.setExchangeUserId(ShiroAuthenticationManager.getUserId());
				key.setExchangeTime(System.currentTimeMillis());
				key.setState(GlobalConstant.CD_KEY_STATE_1);
				int res = cdKeyMapper.update(key);
				if (res > 0) {
					String userId = ShiroAuthenticationManager.getUserId();
					if (rechargeType != null) {
						// 充值
						userWalletService.rechargeWallet(userId, cdKey.getGold().longValue(), rechargeType);
						//新增成功插入用户交易记录
						UserTransactionLog utl = new UserTransactionLog();
						utl.setId(IdGen.uuid());
						utl.setUserId(userId);
						utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_10);
						utl.setDataId(cdKey.getId());
						utl.setGoldNum(cdKey.getGold().longValue());
						utl.setGoldType(cdKey.getType() + "");
						utl.setUtTime(System.currentTimeMillis() + "");
						utl.setRemarks("兑换码为"+code);
						userTransactionLogService.insert(utl);
					}
					// 发消息
					userMessageService.addMessageForCdKey(userId, cdKey.getId(), cdKey.getCdkeyCode(), cdKey.getType(), messageRemarkObj);
				}
				
			} else {
				result.put("state", "fail");
				result.put("msg", "该兑换码已经兑换过啦");
				return result;
			}
		}
		result.put("state", "success");
		result.put("msg", "兑换成功");
		return result;
	}

	/**
	 * 生成cd-key
	 */
	public List<String> insertCdkey(CdKey cdkey) {
		List<String> cdkeyIdList = new ArrayList<String>();
		if (cdkey.getCodeNum() != null && cdkey.getCodeNum() > 0) {
			cdkey.setCreateTime(System.currentTimeMillis());
			cdkey.setState(GlobalConstant.CD_KEY_STATE_0);
			boolean flag = false;// 是否保存兑换记录
			if (StringUtils.isNotBlank(cdkey.getUserMobile())) {
				UserEntity user = userService.findByMobile(cdkey.getUserMobile());
				cdkey.setUserId(user.getId());
				flag = true;
			} else {
				cdkey.setUserId(ShiroAuthenticationManager.getUserId());//
			}
			for (int i = 0; i < cdkey.getCodeNum(); i++) {
				String id = IdGen.uuid();
				cdkey.setId(id);//设置ID 生成 UUID
				String cdkeyCode = generateCdKeyCode(cdkey);
				cdkey.setCdkeyCode(cdkeyCode);
				cdKeyMapper.insert(cdkey);
				cdkeyIdList.add(id);
				if (flag) {
					// 有归属人，则放入兑换表
					userExchangeLogService.insertForCDK(cdkey.getCdkeyCode(), "", cdkey.getUserId());
				} 
			}
		}
		return cdkeyIdList;
	}
	/**
	 * 生成cdkey code
	 * @param cdkey
	 * @return
	 */
	private String generateCdKeyCode(CdKey cdkey) {
		String code = "";
		if (cdkey.getType() == 0) {//商品
			code = "SP";
		} else if (cdkey.getType() == 1) {// 金币
			code = "JB";
			code = code + cdkey.getGold()/100;
		} else if (cdkey.getType() == 2) {// G币， 体验币
			code = "GB";
			code = code + cdkey.getGold()/100;
		}
		code = code + RandomDataUtil.generateStringRandom(12-code.length());
		return code;
	}
	
	public CdKey findByCode(String cdkeyCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cdkeyCode", cdkeyCode);
		List<CdKey> cdKeyList = cdKeyMapper.queryListByPage(param);
		if(cdKeyList != null) {
			return cdKeyList.get(0);
		}
		return null;
	}
	
	
	@Override
	public List<Map<String, Object>> queryCdkeyListByIds(List<String> idList) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("list", idList);
		List<CdKey> cdkeyList = cdKeyMapper.queryCdkeyListByIds(paramMap);
		if (!CollectionUtils.isEmpty(cdkeyList)) {
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (CdKey cdkey : cdkeyList) {
				Map<String, Object> cdkeyMap = new HashMap<>();
				cdkeyMap.put("CDK", cdkey.getCdkeyCode());
				cdkeyMap.put("startTime", DateUtils.formatDate(new Date(Long.valueOf(cdkey.getStartTime())), "yyyy-MM-dd"));
				cdkeyMap.put("endTime", DateUtils.formatDate(new Date(Long.valueOf(cdkey.getEndTime())), "yyyy-MM-dd"));
				if (cdkey.getType() == 0) {
					cdkeyMap.put("type", "商品");
					Goods goods = goodsService.findById(cdkey.getGoodsId());
					cdkeyMap.put("remark", goods.getGoodsName());
				} else if (cdkey.getType() == 1) {
					cdkeyMap.put("type", "金币");
					cdkeyMap.put("remark", cdkey.getGold() + "金币");
				} else if (cdkey.getType() == 2) {
					cdkeyMap.put("type", "G币");
					cdkeyMap.put("remark", cdkey.getGold() + "G币");
				}
				mapList.add(cdkeyMap);
			}
			return mapList;
		}
		return null;
	}

}
