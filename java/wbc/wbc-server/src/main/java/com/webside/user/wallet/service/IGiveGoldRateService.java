/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.wallet.service;

import java.util.List;
import java.util.Map;

import com.webside.user.wallet.entities.GiveGoldRate;

/**
 * 赠送G币比率服务接口
 *
 * @author tianguifang
 * @date 2016-12-07 18:32:04
 */
public interface IGiveGoldRateService 
{
	/*
	 * 按条件查询赠送G币比率
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<GiveGoldRate> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增赠送G币比率
	 * @param GiveGoldRate
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final GiveGoldRate entity);
	
	/*
	 * 修改赠送G币比率
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int update(final GiveGoldRate entity);
	
	/*
	 * 根据ID获取赠送G币比率
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public GiveGoldRate findById(String id);
	
	/*
	 * 根据对象删除赠送G币比率
	 * @param GiveGoldRate
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);

	/**
	 * 根据金额查询可赠送G币比例
	 * 
	 * @param amount
	 * @return
	 */
	Integer queryGiveRateByAmount(Long amount);
}
