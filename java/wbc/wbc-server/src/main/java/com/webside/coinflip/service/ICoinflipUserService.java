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

import com.alibaba.fastjson.JSONObject;
import com.webside.coinflip.model.CoinflipUser;

/**
 * 翻硬币游戏参与者服务接口
 *
 * @author tianguifang
 * @date 2017-07-26 14:42:04
 */
public interface ICoinflipUserService 
{
	/*
	 * 按条件查询翻硬币游戏参与者
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<CoinflipUser> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增翻硬币游戏参与者
	 * @param CoinflipUser
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final CoinflipUser entity);
	
	/*
	 * 修改翻硬币游戏参与者
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final CoinflipUser entity);
	
	/*
	 * 根据ID获取翻硬币游戏参与者
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public CoinflipUser findById(String id);
	
	/*
	 * 根据对象删除翻硬币游戏参与者
	 * @param CoinflipUser
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/**
	 * 用户加入房间开始游戏
	 * @param joinUser
	 * @return
	 */
	public JSONObject insertCoinflipUser(CoinflipUser joinUser);
}
