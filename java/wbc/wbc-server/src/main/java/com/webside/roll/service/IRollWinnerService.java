/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zhangfei
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.service;

import java.util.List;
import java.util.Map;

import com.webside.roll.model.RollWinner;
import com.webside.roll.vo.RollWinnerVo;

/**
 * 获奖人员服务接口
 * 
 * @author zhangfei
 * @date 2017-04-18 13:51:53
 */
public interface IRollWinnerService {
	/**
	 * 按条件查询获奖人员
	 * 
	 * @throws Exception
	 * @author zhangfei
	 */
	public List<RollWinner> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增获奖人员
	 * 
	 * @param RollWinner
	 * @throws Exception
	 * @author zhangfei
	 */
	public int insert(final RollWinner entity);

	/**
	 * 修改获奖人员
	 * 
	 * @param entity
	 * @throws Exception
	 * @author zhangfei
	 */
	public int update(final RollWinner entity);

	/**
	 * 根据ID获取获奖人员
	 * 
	 * @param ID
	 * @throws Exception
	 * @author zhangfei
	 */
	public RollWinner findById(String id);

	/**
	 * 根据对象删除获奖人员
	 * 
	 * @param RollWinner
	 * @throws Exception
	 * @author zhangfei
	 */
	public int deleteBatchById(List<String> ids);
	
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
	
	/**
	 * 添加roll点获奖名单
	 * @param roomId 房间号
	 * @param poolIds 奖品
	 * @param num 本次roll的人数
	 * @param unique 是否一人一件
	 * @param clear roll之后清空参与人数
	 */
	public void addRollWinner(String roomId, String poolIds, int num, String unique, String clear);
	
	/**
	 * 活动结束之后，系统roll奖品
	 */
	public void addForRollEnd();
}
