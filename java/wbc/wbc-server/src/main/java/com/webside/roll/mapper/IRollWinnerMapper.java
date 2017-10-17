/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.roll.model.RollWinner;
import com.webside.roll.vo.RollWinnerVo;

/**
 * 获奖人员数据访问接口
 * 
 * @author zhangfei
 * @date 2017-04-18 13:51:53
 */
@Repository
public interface IRollWinnerMapper extends BaseMapper<RollWinner, String> {
	
	/**
	 * 获取获奖人名单
	 * @param paramMap
	 * @return
	 */
	public List<RollWinnerVo> getRollWinnerListByPage(Map<String, Object> paramMap);
	
	/**
	 * 获取最后一次roll次数
	 * @param roomId 房间号
	 * @return
	 */
	public int getMaxRollNum(String roomId);
}
