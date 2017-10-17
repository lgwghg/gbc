/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.wallet.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.wallet.entities.GiveGoldRate;

/**
 * 赠送G币比率数据访问接口
 *
 * @author tianguifang
 * @date 2016-12-07 18:32:04
 */
@Repository
public interface IGiveGoldRateMapper extends BaseMapper<GiveGoldRate, String> 
 {
	/**
	 * 根据金额查询可赠送G币比例
	 * 
	 * @param amount
	 * @return
	 */
	Integer queryGiveRateByAmount(Long amount);
}
