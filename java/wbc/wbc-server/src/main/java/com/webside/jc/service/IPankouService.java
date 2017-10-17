/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.jc.service;

import java.util.List;
import java.util.Map;

import com.webside.jc.model.Pankou;
import com.webside.jc.vo.PankouVo;

/**
 * 比赛盘口服务接口
 *
 * @author zengxn
 * @date 2017-02-18 13:41:07
 */
public interface IPankouService 
{
	/**
	 * 按条件查询比赛盘口
	 * @throws Exception
	 * @author zengxn
	 */
	public List<Pankou> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增比赛盘口
	 * @param Pankou
	 * @throws Exception
	 * @author zengxn
	 */
	public int insert(final Pankou entity);
	
	/**
	 * 修改比赛盘口
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	public int update(final Pankou entity);
	
	/**
	 * 根据ID获取比赛盘口
	 * @param ID
	 * @throws Exception
	 * @author zengxn
	 */
	public Pankou findById(String id);
	
	/**
	 * 查询盘口VO通过盘口ID和用户ID
	 * @param id
	 * @param userId
	 * @return
	 */
	public PankouVo queryVoByidAndUser(String id,String currentUser);
	
	/**
	 * 根据比赛对战ID、盘口局数获取比赛盘口
	 * @param gbId
	 * @param inningNum
	 * @return
	 */
	public Pankou findByGbIdInningNum(String gbId,String inningNum);
	
	/**
	 * @title : 修改盘口参与人数 和 战队下注金额
	 * @param id 盘口id
	 * @param type 类别：1.主场战队 2.客场战队
	 * @param num 参与人数
	 * @param gold 下注金额
	 * */
	public void updatePrtGold(String id,String type,Long num,Long gold);
	
	/**
	 * @title : 修改盘口参与人数 和 战队下注金额，更换用户下注战队调用
	 * */
	public void updatePrtGold(Pankou pankou);
}
