/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.jc.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.jc.model.Pankou;
import com.webside.jc.vo.PankouVo;

/**
 * 比赛盘口数据访问接口
 *
 * @author zengxn
 * @date 2017-02-18 13:41:07
 */
@Repository
public interface IPankouMapper extends BaseMapper<Pankou, String> 
{
	/**
	 * 根据比赛对战ID、盘口局数获取比赛盘口
	 * @param gbId
	 * @param inningNum
	 * @return
	 */
	public Pankou findByGbIdInningNum(Map<String, Object> parameter);

	/**
	 * @title : 修改对战参与人数 和 战队下注金额
	 * @param : String prtNumColumn  (参与人数（主战队） 和参与人数（副战队） 字段名称)
	 * @param : int prtNum  （数量）
	 * @param : String prtGoldColumn (主战队下注金额  和 副战队下注金额 字段名称)
	 * @param : int  prtGoldNum （金额数量）
	 * */
	public void updatePrtGold(Map<String, Object> parameter);

	/**
	 * @title : 修改盘口参与人数 和 战队下注金额，更换用户下注战队调用
	 * */
	public void updatePkPrtGold(Pankou pankou);
	
	/**
	 * 查询盘口VO通过盘口ID和用户ID
	 * @param id
	 * @param userId
	 * @return
	 */
	public PankouVo queryVoByidAndUser(Map<String, Object> parameter);
	
}
