/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.coinflip.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.coinflip.model.CoinflipRoom;

/**
 * 翻硬币游戏房间服务接口
 *
 * @author tianguifang
 * @date 2017-07-26 14:42:49
 */
public interface ICoinflipRoomService {
	/*
	 * 按条件查询翻硬币游戏房间
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<CoinflipRoom> queryListByPage(Map<String, Object> parameter);

	/*
	 * 新增翻硬币游戏房间
	 * @param CoinflipRoom
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final CoinflipRoom entity);

	/*
	 * 修改翻硬币游戏房间
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final CoinflipRoom entity);

	/*
	 * 根据ID获取翻硬币游戏房间
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public CoinflipRoom findById(String id);

	/*
	 * 根据对象删除翻硬币游戏房间
	 * @param CoinflipRoom
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);

	/**
	 * 查询房间详情
	 * 
	 * @param roomId
	 * @return
	 * @author tianguifang
	 */
	public JSONObject findRoomDetailById(String roomId);

	/**
	 * 创建翻硬币游戏房间
	 * 
	 * @param room
	 * @return
	 * @author tianguifang
	 */
	public JSONObject insertCoinflipRoom(CoinflipRoom room);

	/**
	 * 查询正在比赛中的游戏
	 * 
	 * @return
	 */
	public JSONObject queryPlayingCoinflip();

	/**
	 * 查询我参与的游戏list
	 * 
	 * @param parameters
	 * @return
	 */
	public JSONArray queryMyJoinListByPage(Map<String, Object> parameters);

	/**
	 * 查询历史所有游戏
	 * 
	 * @param parameters
	 * @return
	 */
	public JSONArray queryHistoryListByPage(Map<String, Object> parameters);

	/**
	 * 更新房间状态
	 * 
	 * @param roomId
	 * @param status
	 * @return
	 */
	public Integer updateRoom(String roomId, Integer status, Integer totalGoldNum, boolean isJob);

	/**
	 * 定时任务，每隔10分钟清理过期未参与的翻硬币游戏
	 */
	public void updateOverdueGame();

	public CoinflipRoom updateOpenRoom(String roomId);
	
	/**
	 * 获取用户翻硬币数据统计
	 * 
	 * @author zengxn
	 */
	JSONObject findUserHistoryStatistics(Map<String, Object> param);
}
