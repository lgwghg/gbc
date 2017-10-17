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
import com.webside.user.wallet.entities.UserWallet;

/**
 * 用户钱包数据访问接口
 *
 * @author tianguifang
 * @date 2016-11-24 15:27:02
 */
@Repository
public interface IUserWalletMapper extends BaseMapper<UserWallet, String> 
{
	
	/**
	 * 根据用户id，查询用户的钱包数据
	 * 
	 * @param userId
	 * @return
	 */
	UserWallet findWalletByUserId(String userId);

}
