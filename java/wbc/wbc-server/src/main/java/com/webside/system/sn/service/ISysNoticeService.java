/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sn.service;

import java.util.List;
import java.util.Map;
import com.webside.system.sn.entities.SysNotice;

/**
 * 系统通知服务接口
 *
 * @author zengxn
 * @date 2016-11-25 16:27:18
 */
public interface ISysNoticeService 
{
	/**
	 * 按条件查询系统通知
	 * @throws Exception
	 * @author zengxn
	 */
	public List<SysNotice> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增系统通知
	 * @param SysNotice
	 * @throws Exception
	 * @author zengxn
	 */
	public int insert(final SysNotice entity);
	
	/**
	 * 修改系统通知
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	public int update(final SysNotice entity);
	
	/**
	 * 根据ID获取系统通知
	 * @param ID
	 * @throws Exception
	 * @author zengxn
	 */
	public SysNotice findById(String id);
	
	/**
	 * 插入竞猜结果缓存
	 * @param list	Map<String, Object> 需要三个key｛String nickName,String gbName,Integer num｝
	 * @param Map.nickName	用户昵称
	 * @param Map.gbName	对战名称
	 * @param Map.num		赢得金币数量
	 */
	public void insertGameBattleCacheList(List<Map<String, Object>> list);
	
	/**
	 * 插入翻硬币结果缓存
	 * @param list	Map<String, Object> 需要三个key｛String nickName,Integer num｝
	 * @param Map.nickName	用户昵称
	 * @param Map.num		赢得金币数量
	 */
	public void insertCoinflipWinCacheList(List<Map<String, Object>> list);
	/**
	 * 初始化缓存集合
	 * @return
	 */
	public void initCacheList();
}
