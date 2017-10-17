/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.wallet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.user.wallet.entities.GiveGoldRate;
import com.webside.user.wallet.mapper.IGiveGoldRateMapper;
import com.webside.user.wallet.service.IGiveGoldRateService;

/**
 * 赠送G币比率服务实现类
 *
 * @author tianguifang
 * @date 2016-12-07 18:32:04
 */
@Service("giveGoldRateService")
public class GiveGoldRateService extends AbstractService<GiveGoldRate, String> implements IGiveGoldRateService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(giveGoldRateMapper);
	}

	/**
	 * 赠送G币比率 DAO定义
	 */
	@Autowired
	private IGiveGoldRateMapper giveGoldRateMapper;

	@Override
	public Integer queryGiveRateByAmount(Long amount) {
		return giveGoldRateMapper.queryGiveRateByAmount(amount);
	}
}
