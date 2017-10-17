/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.coinflip.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.coinflip.mapper.ICoinflipRoomMapper;
import com.webside.coinflip.mapper.ICoinflipUserMapper;
import com.webside.coinflip.model.CoinflipRoom;
import com.webside.coinflip.model.CoinflipUser;
import com.webside.coinflip.service.ICoinflipRoomService;
import com.webside.common.GlobalConstant;
import com.webside.common.redis.listener.SendMessage;
import com.webside.pay.alipay.sign.MD5;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.sn.service.ISysNoticeService;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.entities.UserWallet;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;
import com.webside.util.RandomUtil;

/**
 * 翻硬币游戏房间服务实现类
 *
 * @author tianguifang
 * @date 2017-07-26 14:42:49
 */
@Service("coinflipRoomService")
public class CoinflipRoomService extends AbstractService<CoinflipRoom, String> implements ICoinflipRoomService {
	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(coinflipRoomMapper);
	}

	/**
	 * 翻硬币游戏房间 DAO定义
	 */
	@Autowired
	private ICoinflipRoomMapper coinflipRoomMapper;
	@Autowired
	private ICoinflipUserMapper coinflipUserMapper;
	/**
	 * 消息队列 定义
	 */
	@Autowired
	private SendMessage sendMessage;
	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	@Autowired
	private IUserCacheService userCacheService;
	/**
	 * 用户消息管理 Service定义
	 */
	@Autowired
	private IUserMessageService userMessageService;
	/**
	 * 系统通知管理 Service定义
	 */
	@Autowired
	private ISysNoticeService sysNoticeService;
	
	@Override
	public List<CoinflipRoom> queryListByPage(Map<String, Object> parameter) {
		List<CoinflipRoom> roomList = coinflipRoomMapper.queryListByPage(parameter);
		if (!CollectionUtils.isEmpty(roomList)) {
			for (CoinflipRoom room : roomList) {
				if (StringUtils.isNotBlank(room.getPassword())) {
					room.setHasPassword(1);
				}
				setCoinflipUser(room);
			}
		}
		return roomList;
	}

	@Override
	public int updateById(CoinflipRoom entity) {
		return coinflipRoomMapper.updateById(entity);
	}

	/**
	 * 查询房间详情
	 * 
	 * @param roomId
	 * @return
	 * @author tianguifang
	 */
	@Override
	public JSONObject findRoomDetailById(String roomId) {
		JSONObject jsonObject = null;
		CoinflipRoom room = coinflipRoomMapper.findById(roomId);
		if (room != null) {
			jsonObject = new JSONObject();
			setCoinflipUser(room);
			jsonObject.put("event", "coinfliproom");
			jsonObject.put("roomOwner", room.getRoomOwner());
			jsonObject.put("roomJoinners", room.getRoomJoinners());
			jsonObject.put("id", room.getId());
			jsonObject.put("updateTime", room.getUpdateTime());
			if (room.getStatus() == 1) {
				jsonObject.put("joinTime", room.getJoinTime());
			}
			jsonObject.put("ownerGoldNum", room.getOwnerGoldNum());
			jsonObject.put("status", room.getStatus());
			jsonObject.put("totalGoldNum", room.getTotalGoldNum());
			if (StringUtils.isNotBlank(room.getPassword())) {
				jsonObject.put("hasPassword", 1);
			}
			if (room.getStatus() == 3) {// 只有开奖后的才告知用户
				if (room.getRandom() <= 0.5) {// 小于等于0.5正方赢
					jsonObject.put("winner", 1);
				} else {
					jsonObject.put("winner", 0);
				}
				jsonObject.put("openTime", room.getOpenTime());
				jsonObject.put("random", room.getRandom() * 100 + "%");
				jsonObject.put("key", room.getKey());
				jsonObject.put("hashCode", room.getHashCode());
			}
		}
		return jsonObject;
	}

	/**
	 * 创建翻硬币游戏房间
	 * 
	 * @param room
	 * @return
	 * @author tianguifang
	 */
	@Override
	public JSONObject insertCoinflipRoom(CoinflipRoom room) {
		JSONObject result = new JSONObject();
		UserWallet wallet = userWalletService.findWalletByUserId(ShiroAuthenticationManager.getUserId());
		if (wallet == null) {
			result.put("success", false);
			result.put("msg", "钱包找不到了，请联系管理员");
			return result;
		}
		if (wallet.getGold() + wallet.getSysGoldNum() < room.getOwnerGoldNum()) {
			result.put("success", false);
			result.put("msg", "账户金币不足，请先充值");
			return result;
		}
		String roomId = IdGen.uuid();
		room.setId(roomId);
		room.setCreateTime(System.currentTimeMillis());
		room.setCreatorId(ShiroAuthenticationManager.getUserId());
		room.setStatus(0);
		room.setTotalGoldNum(room.getOwnerGoldNum());
		Double random = Math.random();
		String key = RandomUtil.generateMixString(10);
		String hashCode = MD5.coinflipSign(random, key, "UTF-8");
		room.setRandom(random);
		room.setKey(key);
		room.setHashCode(hashCode);
		int re = this.insert(room);// 建房间
		if (re > 0) {
			CoinflipUser roomOwner = room.getRoomOwner();
			roomOwner.setId(IdGen.uuid());
			roomOwner.setGoldNum(room.getOwnerGoldNum());
			roomOwner.setUserId(ShiroAuthenticationManager.getUserId());
			roomOwner.setCreateTime(System.currentTimeMillis());
			roomOwner.setRoomId(roomId);
			roomOwner.setRoomOwner(1);
			roomOwner.setStatus(0);
			coinflipUserMapper.insert(roomOwner);// 房主新增
			// 扣款
			JSONObject deductWallet = userWalletService.deductWallet(roomOwner.getUserId(), Long.valueOf(roomOwner.getGoldNum()), GlobalConstant.DEDUCT_TYPE_JC);
			if (deductWallet != null && deductWallet.getIntValue("result") > 0) {
				// 新增用户交易记录
				UserTransactionLog utl = new UserTransactionLog();
				utl.setId(IdGen.uuid());
				utl.setUserId(roomOwner.getUserId());
				utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_13);
				utl.setDataId(roomOwner.getId());
				utl.setUtTime(System.currentTimeMillis() + "");
				
				String remark = "翻硬币创建房间";
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

			result.put("success", true);
			result.put("msg", "success");
			// 通知翻硬币页面有新房间创建
			JSONObject newRoom = this.findRoomDetailById(roomId);
			result.put("room", newRoom);
			sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, newRoom);
		} else {
			result.put("success", false);
			result.put("msg", "游戏房间创建失败");
		}
		return result;
	}

	/**
	 * 查询正在活动中的游戏
	 */
	@Override
	public JSONObject queryPlayingCoinflip() {
		JSONObject result = new JSONObject();
		List<CoinflipRoom> roomList = coinflipRoomMapper.queryPlayingCoinflip();
		Integer totalGoldNum = 0;
		Integer playingNum = 0;
		if (!CollectionUtils.isEmpty(roomList)) {
			playingNum = roomList.size();
			result.put("playingNum", playingNum);
			for (CoinflipRoom room : roomList) {
				if (StringUtils.isNotBlank(room.getPassword())) {
					room.setHasPassword(1);
					room.setPassword("");
				}
				totalGoldNum = totalGoldNum + room.getTotalGoldNum();
				setCoinflipUser(room);
			}
			result.put("totalGoldNum", totalGoldNum);
			result.put("roomList", roomList);
		}
		return result;
	}

	@Override
	public JSONArray queryMyJoinListByPage(Map<String, Object> parameters) {
		JSONArray jsonArray = null;
		List<CoinflipRoom> roomList = coinflipRoomMapper.queryMyJoinListByPage(parameters);
		if (!CollectionUtils.isEmpty(roomList)) {
			jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			for (CoinflipRoom room : roomList) {
				jsonObject = new JSONObject();
				setCoinflipUser(room);
				jsonObject.put("roomOwner", room.getRoomOwner());
				jsonObject.put("roomJoinners", room.getRoomJoinners());
				jsonObject.put("id", room.getId());
				if (room.getStatus() == 3) {// 只有开奖后的才告知用户
					if (room.getRandom() <= 0.5) {// 小于等于0.5正方赢
						jsonObject.put("winner", 1);
					} else {
						jsonObject.put("winner", 0);
					}
					jsonObject.put("openTime", room.getUpdateTime());
					jsonObject.put("random", room.getRandom() * 100 + "%");
				}
				jsonObject.put("status", room.getStatus());
				jsonObject.put("totalGoldNum", room.getTotalGoldNum());
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray;
	}

	@Override
	public JSONArray queryHistoryListByPage(Map<String, Object> parameters) {
		JSONArray jsonArray = null;
		List<CoinflipRoom> roomList = coinflipRoomMapper.queryListByPage(parameters);
		if (!CollectionUtils.isEmpty(roomList)) {
			jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			for (CoinflipRoom room : roomList) {
				jsonObject = new JSONObject();
				setCoinflipUser(room);
				jsonObject.put("roomOwner", room.getRoomOwner());
				jsonObject.put("roomJoinners", room.getRoomJoinners());
				jsonObject.put("id", room.getId());
				if (room.getStatus() == 3) {// 只有开奖后的才告知用户
					if (room.getRandom() <= 0.5) {// 小于等于0.5正方赢
						jsonObject.put("winner", 1);
					} else {
						jsonObject.put("winner", 0);
					}
					jsonObject.put("openTime", room.getUpdateTime());
					jsonObject.put("random", room.getRandom() * 100 + "%");
				}
				jsonObject.put("status", room.getStatus());
				jsonObject.put("totalGoldNum", room.getTotalGoldNum());
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray;
	}

	private void setCoinflipUser(CoinflipRoom room) {
		List<CoinflipUser> joinUserList = coinflipUserMapper.findJoinUserByRoomId(room.getId());
		if (!CollectionUtils.isEmpty(joinUserList)) {
			List<CoinflipUser> joinUsers = new ArrayList<>();
			for (CoinflipUser joinUser : joinUserList) {
				if (joinUser.getRoomOwner() != null && joinUser.getRoomOwner() == 1) {
					room.setRoomOwner(joinUser);
				} else {
					joinUsers.add(joinUser);
				}
			}
			room.setRoomJoinners(joinUsers);
		}
	}

	@Override
	public Integer updateRoom(String roomId, Integer status, Integer totalGoldNum, boolean isJob) {
		CoinflipRoom room = new CoinflipRoom();
		if (StringUtils.isBlank(roomId)) {
			return 0;
		}
		if (status == null && (totalGoldNum == null || totalGoldNum == 0)) {
			return 0;
		}
		room.setId(roomId);
		if (status != null) {
			room.setStatus(status);
			if (status == 3) {
				room.setOpenTime(System.currentTimeMillis());
			}
		}
		if (totalGoldNum != null && totalGoldNum != 0) {
			room.setTotalGoldNum(totalGoldNum);
		}
		room.setUpdateTime(System.currentTimeMillis());
		if(!isJob) {
			room.setUpdatorId(ShiroAuthenticationManager.getUserId());
		}
		return this.updateById(room);
	}

	/**
	 * 定时任务，10分钟内无人参与的房间或未参与满的，认为是过期的，取消房间， 并退回房主金币
	 */
	@Override
	public void updateOverdueGame() {
		Map<String, Object> param = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, (calendar.get(Calendar.MINUTE) - 10));
		param.put("time", calendar.getTimeInMillis());
		List<CoinflipRoom> roomList = coinflipRoomMapper.queryOverdueGame(param);
		if (!CollectionUtils.isEmpty(roomList)) {
			for (CoinflipRoom room : roomList) {
				int re = 0;
				try {
					re = this.updateRoom(room.getId(), GlobalConstant.COINFLIP_ROOM_STATUS_CLOSED_4, null, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (re > 0) {
					List<CoinflipUser> joinners = coinflipUserMapper.findJoinUserByRoomId(room.getId());
					if (!CollectionUtils.isEmpty(joinners)) {
						for (CoinflipUser joinner : joinners) {
							Integer rebackWallet = userWalletService.rechargeWallet(joinner.getUserId(), Long.valueOf(joinner.getGoldNum()), GlobalConstant.RECHARGE_TYPE_JC);
							if (rebackWallet != null && rebackWallet > 0) {
								// 新增用户交易记录
								UserTransactionLog utl = new UserTransactionLog();
								utl.setId(IdGen.uuid());
								utl.setUserId(joinner.getUserId());
								utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_13);
								utl.setDataId(joinner.getId());
								utl.setUtTime(System.currentTimeMillis() + "");
								utl.setRemarks("翻硬币房间10分钟内未结束，金币退回");
								utl.setGoldNum(Long.valueOf(joinner.getGoldNum()));
								utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
								userTransactionLogService.insert(utl);
								
								//用户通知
								userMessageService.addMessageForCoinflipReback(joinner);
							}
							
						}
					}
					
					// 消息通知页面删除房间
					JSONObject obRoom = new JSONObject();
					obRoom.put("id", room.getId());
					obRoom.put("event", "deleteRoom");
					obRoom.put("totalGoldNum", room.getTotalGoldNum());
					sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, obRoom);
				}
			}
			
		}
	}

	// 开奖处理
	@Override
	public CoinflipRoom updateOpenRoom(String roomId) {

		CoinflipRoom room = this.findById(roomId);
		if (room != null) {
			if (room.getStatus() != null) {
				if (room.getStatus() == 2) {
					Integer winner = frontWin(room.getRandom());
					room.setWinner(winner);
					List<CoinflipUser> joinners = coinflipUserMapper.findJoinUserByRoomId(roomId);
					if (!CollectionUtils.isEmpty(joinners) && joinners.size() > 1) {
						Integer betGoldNum = 0;
						for (CoinflipUser joinner : joinners) {// 再次验证下注是否完成
							if (joinner.getRoomOwner() == 0) {// 非房主
								betGoldNum = betGoldNum + joinner.getGoldNum();
							}
						}
						if (room.getOwnerGoldNum().equals(betGoldNum)) {
							
							int re = this.updateRoom(roomId, GlobalConstant.COINFLIP_ROOM_STATUS_OPENED_3, null, false);
							if (re > 0) {
								// 赢方 赢的总金币数
								BigDecimal winnerTotalGold = BigDecimal.valueOf((room.getOwnerGoldNum() + betGoldNum) * (1 - 0.02));
								List<CoinflipUser> winners = new ArrayList<>();
								//获胜用户系统通知    对象 定义
								List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
								Map<String,Object> map = null;
								for (CoinflipUser joinner : joinners) {
									if (winner.equals(joinner.getIsCoinFront())) {
										map = new HashMap<>();
										UserEntity user = userCacheService.getUserEntityByUserId(joinner.getUserId());
										joinner.setUserNick(user.getNickName());
										joinner.setUserPhoto(user.getPhoto());
										winners.add(joinner);
										// 个人赢的金币数，四舍五入 取整
										BigDecimal winGold = (winnerTotalGold.multiply(new BigDecimal(joinner.getGoldNum())).divide(new BigDecimal(betGoldNum))).setScale(0, BigDecimal.ROUND_HALF_UP);
										joinner.setWinGoldNum(winGold.intValue());

										Integer rebackWallet = userWalletService.rechargeWallet(joinner.getUserId(), winGold.longValue(), GlobalConstant.RECHARGE_TYPE_JC);
										if (rebackWallet != null && rebackWallet > 0) {
											// 新增用户交易记录
											UserTransactionLog utl = new UserTransactionLog();
											utl.setId(IdGen.uuid());
											utl.setUserId(joinner.getUserId());
											utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_13);
											utl.setDataId(joinner.getUserId());
											utl.setUtTime(System.currentTimeMillis() + "");
											
											utl.setRemarks("翻硬币赢得金币" + winGold.longValue());
											utl.setGoldNum(winGold.longValue());
											utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
											userTransactionLogService.insert(utl);
											
											//用户通知
											userMessageService.addMessageForCoinflipWinner(joinner);
											
											//获胜用户加入系统通知  数据处理(前台页面滚动消息)
											map.put("id", joinner.getId());
											map.put("nickName", joinner.getUserNick());
											map.put("num", winGold.intValue());
											listMap.add(map);
										}
										
									}
								}
								if(listMap!=null && listMap.size()>0){
									//获胜用户加入系统通知 接口调用
									sysNoticeService.insertCoinflipWinCacheList(listMap);
								}
								room.setWinners(winners);
								return room;
							}
						}
					}
				} else if (room.getStatus() == 3) {
					Integer winner = frontWin(room.getRandom());
					room.setWinner(winner);
					List<CoinflipUser> joinners = coinflipUserMapper.findJoinUserByRoomId(roomId);
					List<CoinflipUser> winners = new ArrayList<>();
					for (CoinflipUser joinner : joinners) {
						if (winner.equals(joinner.getIsCoinFront())) {
							UserEntity user = userCacheService.getUserEntityByUserId(joinner.getUserId());
							joinner.setUserNick(user.getNickName());
							joinner.setUserPhoto(user.getPhoto());
							// 赢方 赢的总金币数
							BigDecimal winnerTotalGold = BigDecimal.valueOf((room.getOwnerGoldNum() * 2) * (1 - 0.02));
							// 个人赢的金币数，四舍五入 取整
							BigDecimal winGold = (winnerTotalGold.multiply(new BigDecimal(joinner.getGoldNum())).divide(new BigDecimal(room.getOwnerGoldNum()))).setScale(0, BigDecimal.ROUND_HALF_UP);
							joinner.setWinGoldNum(winGold.intValue());
							
							winners.add(joinner);
						}
					}
					room.setWinners(winners);
					
					return room;
				}
				
			}
		}
		return null;
	}

	// 是否正方赢
	private Integer frontWin(Double random) {
		if (random <= 0.5) {
			return 1;
		}
		return 0;
	}

	@Override
	public JSONObject findUserHistoryStatistics(Map<String, Object> param) {
		JSONObject jsonObject = new JSONObject();
		//用户参与的游戏次数
		Integer gameCount = coinflipRoomMapper.findGameCount(param);
		if(gameCount!=null){
			jsonObject.put("gameCount", gameCount);
		}else{
			jsonObject.put("gameCount", 0);
		}
		//用户参与的金币总量
		Long goldNumSum = coinflipRoomMapper.findGoldNumSum(param);
		if(goldNumSum!=null){
			jsonObject.put("goldNumSum", goldNumSum);
		}else{
			jsonObject.put("goldNumSum", 0);
		}
		//用户参与的胜利次数
		Integer winnerCount = coinflipRoomMapper.findWinnerCount(param);
		if(winnerCount!=null && gameCount!=null && gameCount.intValue()>0){
			BigDecimal b = new BigDecimal(Double.valueOf(winnerCount)/gameCount);
			Double re = b.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
			jsonObject.put("probability", re*100+"%");
		}else{
			jsonObject.put("probability", 0);
		}
		//用户参与的胜利时候下注的总量
		Long winnerGoldNumSum = coinflipRoomMapper.findWinnerGoldNumSum(param);
		if(winnerGoldNumSum!=null){
			jsonObject.put("winnerGoldNumSum", Double.valueOf(Double.valueOf(winnerGoldNumSum)*2*0.98).intValue()-winnerGoldNumSum);		
		}else{
			jsonObject.put("winnerGoldNumSum", 0);		
		}
		return jsonObject;
	}
}
