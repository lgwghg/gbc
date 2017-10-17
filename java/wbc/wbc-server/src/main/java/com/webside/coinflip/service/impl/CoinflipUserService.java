/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.coinflip.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.coinflip.service.ICoinflipRoomService;
import com.webside.coinflip.service.ICoinflipUserService;
import com.webside.coinflip.mapper.ICoinflipUserMapper;
import com.webside.coinflip.model.CoinflipRoom;
import com.webside.coinflip.model.CoinflipUser;
import com.webside.common.GlobalConstant;
import com.webside.common.redis.listener.SendMessage;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.wallet.entities.UserWallet;
import com.webside.user.service.impl.UserCacheService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;

/**
 * 翻硬币游戏参与者服务实现类
 *
 * @author tianguifang
 * @date 2017-07-26 14:42:04
 */
@Service("coinflipUserService")
public class CoinflipUserService extends AbstractService<CoinflipUser, String> implements ICoinflipUserService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(coinflipUserMapper);
	}

	/**
	 * 翻硬币游戏参与者 DAO定义
	 */
	@Autowired
	private ICoinflipUserMapper coinflipUserMapper;
	//@Autowired
	//private ICoinflipRoomMapper coinflipRoomMapper;
	@Autowired
	private ICoinflipRoomService coinflipRoomService;
	/**
	 * 消息队列 定义
	 */
	@Autowired
	private SendMessage sendMessage;
	@Autowired
	private UserCacheService userCacheService;
	
	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	
	@Override
	public int updateById(CoinflipUser entity) {
		return coinflipUserMapper.update(entity);
	}

	@Override
	public JSONObject insertCoinflipUser(CoinflipUser joinUser) {
		JSONObject result = new JSONObject();
		// 验证钱包
		UserWallet wallet = userWalletService.findWalletByUserId(ShiroAuthenticationManager.getUserId());
		if (wallet == null) {
			result.put("success", false);
			result.put("msg", "钱包找不到了，请联系管理员");
			return result;
		}
		if (wallet.getGold() + wallet.getSysGoldNum() < joinUser.getGoldNum()) {
			result.put("success", false);
			result.put("msg", "账户金币不足，请先充值");
			return result;
		}
		CoinflipRoom room = coinflipRoomService.findById(joinUser.getRoomId());
		if (room.getStatus() == 4) {
			result.put("success", false);
			result.put("msg", "房间已过期，请换个房间看看");
			return result;
		} else if (room.getStatus() == 3) {
			result.put("success", false);
			result.put("msg", "房间已结束，请换个房间看看");
			return result;
		} else if (room.getStatus() == 2) {
			result.put("success", false);
			result.put("msg", "正在开奖，请换个房间看看");
			return result;
		}
		Integer betedGoldNum = 0;// 加入者已下注金币数
		List<CoinflipUser> joinUsers = coinflipUserMapper.findJoinUserByRoomId(joinUser.getRoomId());
		CoinflipUser roomOwner = null;
		int joinUserNum = 0;
		for (CoinflipUser join : joinUsers) {
			if (join.getUserId().equals(ShiroAuthenticationManager.getUserId())) {
				result.put("success", false);
				result.put("msg", "您已在该房间，请换个房间参与");
				return result;
			}
			if (join.getRoomOwner() != null) {
					if (join.getRoomOwner().equals(1)) {
						roomOwner = join;
					} else {
						betedGoldNum = betedGoldNum + join.getGoldNum();
						joinUserNum = joinUserNum + 1;
					}
				
			}
		}
		if (roomOwner != null) {
			Integer goldNum = roomOwner.getGoldNum();//房主下注金额
			if (goldNum.equals(betedGoldNum)) {
				result.put("success", false);
				result.put("msg", "参与人数已满，请换个房间看看");
				return result;
			}
			if (goldNum < 100000) {//10w金币数以下  1 v 1
				if (!goldNum.equals(joinUser.getGoldNum())) {
					result.put("success", false);
					result.put("msg", "此次下注金币数可以是" + goldNum + "个");
					return result;
				} else {
					// ok
					result = insertJoinUser(joinUser, roomOwner, 2*goldNum);
				}
			} else { //10w金币数以上   1 v n (n最大等于4)

				Integer canAlsoBetGoldNum = goldNum - betedGoldNum;
				if (canAlsoBetGoldNum <= 10000) {// 还剩可下注金币数小于等于1w
					if (!joinUser.getGoldNum().equals(canAlsoBetGoldNum)) {
						result.put("success", false);
						result.put("msg", "此次下注金币数可以是" + canAlsoBetGoldNum + "个");
						return result;
					} else {
						// ok
						result = insertJoinUser(joinUser, roomOwner, 2*goldNum);
					}
				} else { // 还剩可下注金币数大于1w
					if (joinUserNum == 3) {// 已经加入了3个人，剩下的可下注金币数，只能是最后这个人加入下注的
						if (!joinUser.getGoldNum().equals(canAlsoBetGoldNum)) {
							result.put("success", false);
							result.put("msg", "此次下注金币数可以是" + canAlsoBetGoldNum + "个");
							return result;
						} else {
							// ok
							result = insertJoinUser(joinUser, roomOwner, 2*goldNum);
						}
					} else {
						if (joinUser.getGoldNum() < 10000) { // 每人最低下1w
							result.put("success", false);
							result.put("msg", "此房间最低需要下注1万个金币");
							return result;
						} else {
							if (joinUser.getGoldNum() < canAlsoBetGoldNum) {
								// ok
								result = insertJoinUser(joinUser, roomOwner, goldNum + betedGoldNum + joinUser.getGoldNum());
							} else if (joinUser.getGoldNum().equals(canAlsoBetGoldNum)) {
								// ok
								result = insertJoinUser(joinUser, roomOwner, 2*goldNum);
							} else {// joinUser.getGoldNum() > canAlsoBetGoldNum
								result.put("success", false);
								result.put("msg", "此次下注金币数可以是" + canAlsoBetGoldNum + "个");
								return result;
							}
						}
					}
				}
			
			}
		}
		return result;
	}
	/**
	 * 拼数据，insert
	 * @param joinUser
	 * @param roomOwner
	 * @return
	 */
	private JSONObject insertJoinUser(CoinflipUser joinUser, CoinflipUser roomOwner, Integer totalGoldNum) {
		JSONObject result = new JSONObject();
		joinUser.setId(IdGen.uuid());
		joinUser.setUserId(ShiroAuthenticationManager.getUserId());
		joinUser.setCreateTime(System.currentTimeMillis());
		joinUser.setRoomOwner(0);
		joinUser.setStatus(0);
		joinUser.setIsCoinFront(getIsCoinFront(roomOwner));
		int re = this.insert(joinUser);
		if (re > 0) {
			
			UserEntity user = userCacheService.getUserEntityByUserId(joinUser.getUserId());
			joinUser.setUserNick(user.getNickName());
			joinUser.setUserPhoto(user.getPhoto());
			result.put("success", true);
			result.put("msg", "success");
			result.put("joinner", joinUser);
			
			JSONObject deductWallet = userWalletService.deductWallet(joinUser.getUserId(), Long.valueOf(joinUser.getGoldNum()), GlobalConstant.DEDUCT_TYPE_JC);
			if(deductWallet!=null && deductWallet.getIntValue("result")>0){
				//新增用户交易记录
				UserTransactionLog utl = new UserTransactionLog();
				utl.setId(IdGen.uuid());
				utl.setUserId(joinUser.getUserId());
				utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_13);
				utl.setDataId(joinUser.getId());
				utl.setUtTime(System.currentTimeMillis()+"");
				String remark = "翻硬币下注";
				int gold = 0;
				if(deductWallet.getIntValue("sysGold")>0){
					gold = gold + deductWallet.getIntValue("sysGold");
					remark = remark + "，" + deductWallet.getIntValue("sysGold") + "G币";
					utl.setGoldType(GlobalConstant.GOLD_TYPE_2);
				}
				if(deductWallet.getIntValue("gold")>0){
					gold = gold + deductWallet.getIntValue("gold");
					remark = remark + "，" + deductWallet.getIntValue("gold") + "金币";
					utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
				}
				utl.setGoldNum(Long.parseLong("-" + gold));
				utl.setRemarks(remark);
				userTransactionLogService.insert(utl);
			}
			
			// 加入通知
			sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, joinUser);
			
			// 更新房间状态
			CoinflipRoom room = new CoinflipRoom();
			room.setId(joinUser.getRoomId());
			room.setTotalGoldNum(totalGoldNum);
			if (totalGoldNum/roomOwner.getGoldNum() == 2) {//完成下注金额
				room.setStatus(GlobalConstant.COINFLIP_ROOM_STATUS_UNOPEN_2);
				result.put("open", true);//准备开奖
			} else {
				result.put("open", false);
			}
			room.setUpdateTime(System.currentTimeMillis());
			room.setUpdatorId(joinUser.getUserId());
			int updateRe = coinflipRoomService.updateById(room);// 更新下注总额和房间状态
			
			if (updateRe > 0 && result.getBoolean("open")) {// 开奖倒计时通知
				sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new CoinflipRoom(roomOwner.getRoomId(), "openRoom"));
			}
		} else {
			result.put("success", false);
			result.put("msg", "加入失败，看看其他房间");
		}
		return result;
	}
	
	private Integer getIsCoinFront(CoinflipUser roomOwner) {
		if (roomOwner != null) {
			 return roomOwner.getIsCoinFront() == 0 ? 1:0;
		}
		return 0;
	}
}
