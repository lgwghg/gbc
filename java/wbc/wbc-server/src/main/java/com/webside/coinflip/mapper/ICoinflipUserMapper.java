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

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.coinflip.model.CoinflipUser;

/**
 * 翻硬币游戏参与者数据访问接口
 *
 * @author tianguifang
 * @date 2017-07-26 14:42:04
 */
@Repository
public interface ICoinflipUserMapper extends BaseMapper<CoinflipUser, String> 
{
	/**
	 * 根据房间id，查询房间游戏参与人
	 * @param roomId
	 * @return
	 */
	List<CoinflipUser> findJoinUserByRoomId(String roomId);
	/**
	 * 根据房间id，查询房主下注信息
	 * @param roomId
	 * @return
	 */
	CoinflipUser findRoomOwnerByRoomId(String roomId);
	
}
