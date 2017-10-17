/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.cdkey.service;

import java.util.List;
import java.util.Map;

import com.webside.user.cdkey.entities.CdKey;

/**
 * cd-key兑换服务接口
 *
 * @author tianguifang
 * @date 2017-04-07 11:47:48
 */
public interface ICdKeyService 
{
	/*
	 * 按条件查询cd-key兑换
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<CdKey> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增cd-key兑换
	 * @param CdKey
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final CdKey entity);
	/**
	 * 生成cd-key，返回生成的cd-key id
	 * @param cdkey
	 * @return
	 */
	public List<String> insertCdkey(CdKey cdkey);
	
	/*
	 * 修改cd-key兑换
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int update(final CdKey entity);
	
	/*
	 * 根据ID获取cd-key兑换
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public CdKey findById(String id);
	
	public CdKey findByCode(String cdkeyCode);
	
	/*
	 * 根据对象删除cd-key兑换
	 * @param CdKey
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/**
	 * cdkey兑换
	 * @param code
	 * @return
	 * @author tianguifang
	 */
	public Map<String, Object> updateExchange(String code, String addressId);
	/**
	 * 根据cd-key id查询cdkey 导出list
	 * @param idList
	 * @return
	 */
	public List<Map<String, Object>> queryCdkeyListByIds(List<String> idList);
}
