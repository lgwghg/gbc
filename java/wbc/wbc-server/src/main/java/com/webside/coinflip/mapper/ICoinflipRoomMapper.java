/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.coinflip.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.coinflip.model.CoinflipRoom;

/**
 * 翻硬币游戏房间数据访问接口
 *
 * @author tianguifang
 * @date 2017-07-26 14:42:49
 */
@Repository
public interface ICoinflipRoomMapper extends BaseMapper<CoinflipRoom, String> 
{

	List<CoinflipRoom> queryPlayingCoinflip();
	/**
	 * 查询我创建和我参与的所有游戏
	 * @param parameters
	 * @return
	 */
	List<CoinflipRoom> queryMyJoinListByPage(Map<String, Object> parameters);
	int updateById(CoinflipRoom entity);
	/**
	 * @param param
	 */
	List<CoinflipRoom> queryOverdueGame(Map<String, Object> param);

	/**
	 * 用户参与的游戏次数
	 * @author zengxn
	 */
	Integer findGameCount(Map<String, Object> param);
	
	/**
	 * 用户参与的金币总量
	 * @author zengxn
	 */
	Long findGoldNumSum(Map<String, Object> param);
	
	/**
	 * 用户参与的胜利次数
	 * @author zengxn
	 */
	Integer findWinnerCount(Map<String, Object> param);
	
	/**
	 * 用户参与的胜利时候下注的总量
	 * @author zengxn
	 */
	Long findWinnerGoldNumSum(Map<String, Object> param);
}
