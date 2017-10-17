/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.exchange.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.webside.address.entities.Address;
import com.webside.address.service.IAddressService;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.goods.model.Goods;
import com.webside.goods.model.GoodsStock;
import com.webside.goods.model.vo.GoodsStockVo;
import com.webside.goods.service.IGoodsService;
import com.webside.goods.service.IGoodsStockService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.cdkey.entities.CdKey;
import com.webside.user.cdkey.service.ICdKeyService;
import com.webside.user.exchange.entities.UserExchangeLog;
import com.webside.user.exchange.mapper.IUserExchangeLogMapper;
import com.webside.user.exchange.service.IUserExchangeLogService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.entities.UserWallet;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 用户兑换服务实现类
 * 
 * @author tianguifang
 * @date 2016-11-24 16:19:27
 */
@Service("userExchangeLogService")
public class UserExchangeLogService extends AbstractService<UserExchangeLog, String> implements IUserExchangeLogService {
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(userExchangeLogMapper);
	}

	/**
	 * 用户兑换 DAO定义
	 */
	@Autowired
	private IUserExchangeLogMapper userExchangeLogMapper;
	@Autowired
	private IUserCacheService userCacheService;
	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IGoodsStockService goodsStockService;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private ICdKeyService cdKeyService;

	/**
	 * 查询用户兑换记录列表
	 */
	@Override
	public List<UserExchangeLog> queryListByPage(Map<String, Object> parameter) {
		List<UserExchangeLog> exchangeList = userExchangeLogMapper.queryListByPage(parameter);
		List<String> stockIdList = new ArrayList<String>();
		for (UserExchangeLog exchange : exchangeList) {
			UserEntity user = userCacheService.getUserEntityByUserId(exchange.getUserId());
			exchange.setUserNick(user.getNickName());
			stockIdList.add(exchange.getStockId());
		}
		List<GoodsStockVo> goodsList = goodsStockService.findListByIds(stockIdList);
		if (CollectionUtils.isNotEmpty(goodsList)) {
			Map<String, GoodsStockVo> goodsMap = new HashMap<String, GoodsStockVo>();
			for (GoodsStockVo goods : goodsList) {
				goodsMap.put(goods.getStockId(), goods);
			}
			for (UserExchangeLog exchange : exchangeList) {
				if (StringUtils.isNotBlank(exchange.getStockId())) {
					GoodsStockVo goods = goodsMap.get(exchange.getStockId());
					if (goods != null) {
						exchange.setGoodsVo(goods);
					}
				}

			}
		}
		return exchangeList;
	}

	public void insert(String goodsId, int salesNum, String addressId) {
		if (salesNum <= 0) {
			salesNum = 1;
		}

		for (int i = 0; i < salesNum; i++) {
			insert(goodsId, addressId);
		}
	}
	
	public void insertForCDK(String cdkCode, String addressId, String userId) {
		CdKey cdKey = cdKeyService.findByCode(cdkCode);
		if (cdKey == null) {
			throw new RuntimeException("兑换码无效：" + cdkCode);
		}
		Long gold = StringUtils.toLong(cdKey.getGold());
		String stockId = "";
		String goodsId = cdKey.getGoodsId();
		if(StringUtils.isNotBlank(goodsId)) {
			Goods goods = goodsService.findById(goodsId);
			if (goods == null) {
				throw new RuntimeException("请确定兑换的商品");
			}
			gold = goods.getGoodsGold();
			// 新增库存, 已兑换
			GoodsStock goodsStock = new GoodsStock();
			goodsStock.setGoodsId(goodsId);
			goodsStock.setStatus(GlobalConstant.DICTTYPE_STOCK_STATUS_2);
			goodsStock.setCardNo(cdkCode);
			goodsStock.setCardPass(cdkCode);
			goodsStockService.insert(goodsStock);
			stockId = goodsStock.getId();
		}
		
		String status = "";
		if (StringUtils.isBlank(userId)) {
			userId = ShiroAuthenticationManager.getUserId();
			status = GlobalConstant.DICTTYPE_EXCHANGE_STATUS_5;
		}

		// 生成兑换记录
		saveUserExchangeLog(status, gold, userId, stockId, addressId, cdkCode);
	}

	public int insert(String goodsId, String addressId) {
		Goods goods = goodsService.findById(goodsId);
		String isMax = goods.getIsMax();
		Long gold = goods.getGoodsGold();
		int count = 0;
		String stockId = "";
		String status = "";
		String userId = ShiroAuthenticationManager.getUserId();
		if ("1".equals(isMax)) {// CD-KEY商品
			// 生成CD-KEY
			CdKey cdKey = new CdKey();
			cdKey.setCodeNum(1);
			cdKey.setType(0);
			cdKey.setGoodsId(goodsId);
			cdKey.setCreateUserId(userId);
			cdKey.setStartTime(StringUtils.toString(System.currentTimeMillis()));
			cdKey.setEndTime(DateUtils.getBeforeDay("", -30));// 有效期30天
			
			cdKeyService.insertCdkey(cdKey);
			String cdKeyCode = cdKey.getCdkeyCode();
			
			// 新增库存, 已兑换
			GoodsStock goodsStock = new GoodsStock();
			goodsStock.setCardNo(cdKeyCode);
			goodsStock.setCardPass(cdKeyCode);
			goodsStock.setGoodsId(goodsId);
			goodsStock.setStatus(GlobalConstant.DICTTYPE_STOCK_STATUS_2);
			goodsStockService.insert(goodsStock);
			
			stockId = goodsStock.getId();
			status = GlobalConstant.DICTTYPE_EXCHANGE_STATUS_5;
		} else {
			// 通过商品查找库存
			GoodsStock goodsStock = goodsStockService.findByGoodsId(goodsId);
			if (goodsStock == null || StringUtils.isBlank(goodsStock.getId())) {
				throw new RuntimeException("库存不足");
			}
			stockId = goodsStock.getId();
			// 修改库存状态
			goodsStock.setStatus(GlobalConstant.DICTTYPE_STOCK_STATUS_2);
			count = goodsStockService.update(goodsStock);
			if (count != 1) {
				throw new RuntimeException("更新库存失败");
			}

		}
		// 兑换之前，先扣款
		count = userWalletService.deductWallet(userId, gold, GlobalConstant.DEDUCT_TYPE_EXCHANGE).getIntValue("result");
		if (count <= 0) {
			throw new RuntimeException("金币不足~");
		}

		// 扣款之后，生成兑换记录
		UserExchangeLog exchangeLog = saveUserExchangeLog(status, gold, userId, stockId, addressId, "");

		// 最后生成交易记录
		count = saveUserTransactionLog(gold, userId, exchangeLog.getId(), goods.getGoodsName());

		return count;
	}

	// 生成兑换记录
	private UserExchangeLog saveUserExchangeLog(String status, Long gold, String userId, String stockId, String addressId, String cdkCode) {
		UserExchangeLog exchangeLog = new UserExchangeLog();
		exchangeLog.setId(IdGen.uuid());// 设置ID 生成 UUID
		exchangeLog.setStockId(stockId);
		exchangeLog.setExchangeGold(gold);
		exchangeLog.setUeOrderNo(IdGen.genOrderNo());
		exchangeLog.setUserId(userId);
		exchangeLog.setCdkCode(cdkCode);
		exchangeLog.setExchangeTime(System.currentTimeMillis());
		if(StringUtils.isBlank(status)) {// 默认 未领取
			status = GlobalConstant.DICTTYPE_EXCHANGE_STATUS_1;
		}
		exchangeLog.setExchangeStatus(status);
		
		if(StringUtils.isNotBlank(addressId)) {
			Address address = addressService.findById(addressId);
			if(address != null) {
				exchangeLog.setReceiverAddress(address.getAddressDetail());
				exchangeLog.setReceiverMobile(address.getReceiverMobile());
				exchangeLog.setReceiverName(address.getReceiverName());
			}
		}
		
		try {
			userExchangeLogMapper.insert(exchangeLog);
		} catch (DuplicateKeyException e) {// 单号冲突
			exchangeLog.setUeOrderNo(IdGen.genOrderNo());
			userExchangeLogMapper.insert(exchangeLog);
		}

		return exchangeLog;
	}

	// 生成交易记录
	private int saveUserTransactionLog(Long gold, String userId, String logId, String goodsName) {
		UserTransactionLog utl = new UserTransactionLog();
		utl.setId(IdGen.uuid());// 设置ID 生成 UUID
		utl.setGoldNum(-1 * gold);
		utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
		utl.setUtTime(StringUtils.toString(System.currentTimeMillis()));
		utl.setUserId(userId);
		utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_3);// 兑换
		utl.setDataId(logId);
		utl.setRemarks("兑换 " + goodsName);

		return userTransactionLogService.insert(utl);
	}

	public List<UserExchangeLog> findListByPage(Map<String, Object> paramMap) {

		return userExchangeLogMapper.findListByPage(paramMap);
	}

	public int updateStatusForSales(String ids) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", StringUtils.StringToList(ids, ","));
		paramMap.put("userId", ShiroAuthenticationManager.getUserId());
		return userExchangeLogMapper.updateStatusForSales(paramMap);
	}

	/**
	 * 后台管理员，同意
	 * 
	 * @return
	 */
	public Integer updateSaledCard(String exchangeId) throws Exception {
		UserExchangeLog exchangeLog = userExchangeLogMapper.findById(exchangeId);
		if (exchangeLog != null && GlobalConstant.DICTTYPE_EXCHANGE_STATUS_3.equals(exchangeLog.getExchangeStatus())) {
			UserExchangeLog updateLog = new UserExchangeLog();
			updateLog.setId(exchangeId);
			updateLog.setExchangeStatus(GlobalConstant.DICTTYPE_EXCHANGE_STATUS_4);
			// 更新交易记录
			Integer state = userExchangeLogMapper.update(updateLog);
			if (state != null && state > 0) {
				GoodsStock stock = new GoodsStock();
				stock.setId(exchangeLog.getStockId());
				stock.setStatus(GlobalConstant.DICTTYPE_STOCK_STATUS_1);
				// 更新库存
				Integer stockState = goodsStockService.update(stock);
				if (stockState != null && stockState > 0) {
					return 1;
				} else {
					throw new Exception("状态更新失败");
				}
			}
		}
		return 0;
	}

	public Map<String, Object> insertForbuyAll() {
		String userId = ShiroAuthenticationManager.getUserId();
		UserWallet userWallet = userWalletService.findWalletByUserId(userId);
		Long wallet = 0L;// 总G币
		if (userWallet != null) {
			wallet = userWallet.getGold();
			wallet = wallet == null ? 0 : wallet;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("deductGold", "0");
		result.put("message", "购买成功");

		return saveExchangeLog(wallet, true, false, result);
	}

	private Map<String, Object> saveExchangeLog(Long wallet, boolean first, boolean deal, Map<String, Object> result) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (!first) {
			paramMap.put("wallet", wallet);// 钱包
		}
		paramMap.put("status", 1);// 有效
		paramMap.put("minNum", "0");// 库存大于0
		paramMap.put("type", 2);// 虚拟物品

		PageHelper.startPage(0, 1000, "t.goods_gold DESC");
		List<Goods> list = goodsService.queryListByPage(paramMap);
		if (list != null && !list.isEmpty()) {
			String userId = ShiroAuthenticationManager.getUserId();
			Address address = addressService.findDefaultMyAddressByUserId(userId);
			String addressId = address.getId();
			Double deductGold = MapUtils.getDouble(result, "deductGold");
			for (Goods goods : list) {
				Long gold = goods.getGoodsGold();
				Long num = goods.getGoodsNum();
				for (int i = 0; i < num; i++) {
					if (wallet >= gold) {
						deal = true;
						insert(goods.getId(), addressId);
						wallet = wallet - gold;
						deductGold = deductGold + gold;
						result.put("deductGold", deductGold);
					} else {
						saveExchangeLog(wallet, false, deal, result);
						break;
					}
				}
			}
		} else if (first) {
			// 第一次查询，没有查到商品，库存不足
			result.put("success", false);
			result.put("message", "库存不足");
		} else if (!deal) {
			// 第一次/第二次都没成交， 金币不足
			result.put("success", false);
			result.put("message", "金币不足");
		}

		return result;
	}
	
	public void updateStatusForPool(String ids) {
		if(StringUtils.isBlank(ids)) {
			throw new RuntimeException("请确定要更新的数据");
		}
		List<String> list = StringUtils.StringToList(ids, ",");
		
		userExchangeLogMapper.updateStatusForPool(list);
	}
	
	public void updateStatusForRoll(String ids) {
		if(StringUtils.isBlank(ids)) {
			throw new RuntimeException("请确定要更新的数据");
		}
		List<String> list = StringUtils.StringToList(ids, ",");
		
		userExchangeLogMapper.updateStatusForRoll(list);
	}
	
	public boolean isCdkeyValid(String exchangeId) {
		if (StringUtils.isBlank(exchangeId)) {
			throw new RuntimeException("请确定要验证的数据");
		}
		UserExchangeLog entity = this.findById(exchangeId);
		if (entity != null && GlobalConstant.DICTTYPE_EXCHANGE_STATUS_5.equals(entity.getExchangeStatus())) {
			return true;
		}

		return false;
	}
	
	public UserExchangeLog updateStatus(String id, String exchangeStatus) {
		if(StringUtils.isBlank(id)) {
			throw new RuntimeException("请确定要更新的数据");
		}
		if(StringUtils.isBlank(exchangeStatus)) {
			throw new RuntimeException("请确定要更新的状态");
		}
		String userId = ShiroAuthenticationManager.getUserId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("userId", userId);
		List<UserExchangeLog> list = this.findListByPage(paramMap);
		if(list != null && !list.isEmpty()) {
			UserExchangeLog entity = list.get(0);
			entity.setExchangeStatus(exchangeStatus);
			
			userExchangeLogMapper.update(entity);
			
			return entity;
		}
		
		return null;
	}

}
