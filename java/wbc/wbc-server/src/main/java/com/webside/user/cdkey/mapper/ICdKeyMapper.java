/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.cdkey.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.cdkey.entities.CdKey;

/**
 * cd-key兑换数据访问接口
 *
 * @author tianguifang
 * @date 2017-04-07 11:47:48
 */
@Repository
public interface ICdKeyMapper extends BaseMapper<CdKey, String> 
{
	
	/**
	 * 根据id list 查询cd-key
	 * @param idList
	 * @return
	 */
	List<CdKey> queryCdkeyListByIds(Map<String, Object> paramMap);
	
}
