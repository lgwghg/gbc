/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.share.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.share.entities.UserJcShare;

/**
 * 用户竞猜分享数据访问接口
 *
 * @author tianguifang
 * @date 2017-01-11 14:34:07
 */
@Repository
public interface IUserJcShareMapper extends BaseMapper<UserJcShare, String> 
{
	
	/**
	 * 查询用户今天已经抢红包的次数
	 * 
	 * @param param
	 * @return
	 */
	Integer todayGetRedPurseNum(Map<String, Object> param);

	/**
	 * 查询某手机号，7天内领取的G币
	 * 
	 * @param param
	 * @return
	 */
	List<UserJcShare> queryJcShareByMobile(Map<String, Object> param);

}
