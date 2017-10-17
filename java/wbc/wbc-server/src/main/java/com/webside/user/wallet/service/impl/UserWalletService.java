/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.wallet.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.wallet.entities.UserWallet;
import com.webside.user.wallet.mapper.IUserWalletMapper;
import com.webside.user.wallet.service.IGiveGoldRateService;
import com.webside.user.wallet.service.IUserWalletService;

/**
 * 用户钱包服务实现类
 *
 * @author tianguifang
 * @date 2016-11-24 15:27:02
 */
@Service("userWalletService")
public class UserWalletService extends AbstractService<UserWallet, String> implements IUserWalletService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userWalletMapper);
	}

	/**
	 * 用户钱包 DAO定义
	 */
	@Autowired
	private IUserWalletMapper userWalletMapper;
	@Autowired
	private IUserCacheService userCacheService;
	@Autowired
	private IGiveGoldRateService giveGoldRateService;
	/**
	 * 分页查询用户钱包数据
	 */
	public List<UserWallet> queryListByPage(Map<String, Object> parameter) {
		List<UserWallet> walletList = userWalletMapper.queryListByPage(parameter);
		if (CollectionUtils.isNotEmpty(walletList)) {
			for (UserWallet wallet : walletList) {
				UserEntity user = userCacheService.getUserEntityByUserId(wallet.getUserId());
				if (user != null) {
					wallet.setUserNick(user.getNickName());
				} else {
					wallet.setUserNick("");
				}
			}
		}
		return walletList;
	}
	
	/**
	 * 钱包充值或奖励赠送
	 * 
	 * @param type
	 *            进账类型 0：充值 1：赠送 2:竞猜赢得/系统取消退回G币
	 * @param amount
	 *            进账金额,如果type=0，amount是充值的金额；如果type=1，amount是赠送的G币数；如果type=2，amount是赢得的G币数
	 * 
	 * @return 进账状态 0：进账失败 1：进账成功 -1：参数错误
	 * @author tianguifang
	 * @date 2016-11-29
	 */
	@Override
	public Integer rechargeWallet(String userId, Long amount, Integer type) {
		if (type != null && amount != null && StringUtils.isNotBlank(userId)) {
			// 表中的钱包数据
			UserWallet userWallet = userWalletMapper.findWalletByUserId(userId);
			Long updateT = userWallet.getUpdateTime();
			long uGold = userWallet.getGold() != null ? userWallet.getGold() : 0L;
			long uSysGold = userWallet.getSysGoldNum() != null ? userWallet.getSysGoldNum() : 0L;
			UserWallet editWallet = new UserWallet();
			editWallet.setUserId(userId);
			editWallet.setUpdateTime(System.currentTimeMillis());
			editWallet.setUpdateT(updateT);
			if (type == 0) { // 充值
				Long gold = amount * GlobalConstant.GOLD_CASH_RATIO;
				editWallet.setGold(uGold + gold.longValue());
				// 根据充值金额获取赠送G币的比例
				Integer rate = giveGoldRateService.queryGiveRateByAmount(amount);
				// 计算赠送G币数
				if (rate != null && rate > 0) {
					Double sysGold = gold * (rate * 0.01);
					editWallet.setSysGoldNum(uSysGold + sysGold.longValue());
				}
			} else if (type == 1) { // 赠送
				editWallet.setSysGoldNum(uSysGold + amount.longValue());
			} else if (type == 2) {
				editWallet.setGold(uGold + amount.longValue());
			}
			return userWalletMapper.update(editWallet);
		}
		return -1;
	}

	/**
	 * 钱包扣款
	 * 
	 * @param gold 扣款金额
	 * @param type 扣款类型 0：竞猜 1：兑换
	 * 
	 * @return 
	 * 	jsonObject.getIntValue("result") 扣款状态 0：扣款失败 1：扣款成功 -1：参数错误 -2：G币不足
		jsonObject.getIntValue("sysGold");// G币数量
		jsonObject.getIntValue("gold");// 金币数量
	 * 
	 * @author zengxn
	 * @date 2017年4月7日 18:43:47
	 */
	@Override
	public JSONObject deductWallet(String userId, Long gold, Integer type) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", -1);// 参数不正确
		jsonObject.put("sysGold", 0);// G币数量
		jsonObject.put("gold", 0);// 金币数量
		if (type != null && gold != null && StringUtils.isNotBlank(userId)) {
			// 表中的钱包数据
			UserWallet userWallet = userWalletMapper.findWalletByUserId(userId);
			Long updateT = userWallet.getUpdateTime();
			long uGold = userWallet.getGold() != null ? userWallet.getGold() : 0L;
			long uSysGold = userWallet.getSysGoldNum() != null ? userWallet.getSysGoldNum() : 0L;
			UserWallet editWallet = new UserWallet();
			editWallet.setUserId(userId);
			editWallet.setUpdateTime(System.currentTimeMillis());
			editWallet.setUpdateT(updateT);
			if (type == 0) {// 竞猜,优先扣赠送G币
				if (uGold + uSysGold < gold.longValue()) {
					jsonObject.put("result", -2);// G币不足
					return jsonObject;
				}
				if (uSysGold >= gold.longValue()) {
					editWallet.setSysGoldNum(uSysGold - gold.longValue());
					jsonObject.put("sysGold", gold.longValue());// G币数量
				} else {
					editWallet.setSysGoldNum(0L);
					editWallet.setGold(uGold - (gold.longValue() - uSysGold));
					jsonObject.put("sysGold", uSysGold);// G币数量
					jsonObject.put("gold", gold.longValue() - uSysGold);// 金币币数量
				}
			} else if (type == 1) { // 兑换，只能扣非赠送G币，表中gold字段
				if (uGold < gold.longValue()) {
					jsonObject.put("result", -2);// G币不足
					return jsonObject;
				}
				editWallet.setGold(uGold - gold.longValue());
			}
			jsonObject.put("result", userWalletMapper.update(editWallet));
		}
		return jsonObject;
	}

	@Override
	public UserWallet findWalletByUserId(String userId) {
		return userWalletMapper.findWalletByUserId(userId);
	}

	@Override
	public Integer updateRechargeWallet(String userId, Long amount, Integer type) {
		return this.rechargeWallet(userId, amount, type);
	}
}
