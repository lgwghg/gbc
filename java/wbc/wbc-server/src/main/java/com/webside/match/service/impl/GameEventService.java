/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.match.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.match.entities.GameEvent;
import com.webside.match.mapper.IGameEventMapper;
import com.webside.match.service.IGameEventService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.dict.service.DictService;
import com.webside.user.model.UserEntity;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 游戏赛事服务实现类
 *
 * @author zengxn
 * @date 2016-11-23 17:36:43
 */
@Service("gameEventService")
public class GameEventService extends AbstractService<GameEvent, String> implements IGameEventService 
{
	private Logger logger = Logger.getLogger(this.getClass());
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(gameEventMapper);
	}

	/**
	 * 游戏赛事 DAO定义
	 */
	@Autowired
	private IGameEventMapper gameEventMapper;
	
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;
	
	@Override
	public int insert(GameEvent t) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setId(IdGen.uuid());
			t.setCreateTime((System.currentTimeMillis()+""));
			t.setCreateOperatorId(user.getId());
			t.setCreateOperatorName(user.getNickName());
			if(StringUtils.isNotEmpty(t.getStartTime())){
				t.setStartTime(StringUtils.toString(DateUtils.parseDate(t.getStartTime()).getTime()));
			}else{
				t.setStartTime(null);
			}
			if(StringUtils.isNotEmpty(t.getEndTime())){
				t.setEndTime(StringUtils.toString(DateUtils.parseDate(t.getEndTime()).getTime()));
			}else{
				t.setEndTime(null);
			}
			result = super.insert(t);
		} catch (Exception e) {
			logger.error("新增游戏赛事出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public int update(GameEvent t) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setUpdateTime(System.currentTimeMillis()+"");
			t.setUpdateOperatorId(user.getId());
			t.setUpdateOperatorName(user.getNickName());
			if(StringUtils.isNotEmpty(t.getStartTime())){
				t.setStartTime(StringUtils.toString(DateUtils.parseDate(t.getStartTime()).getTime()));
			}else{
				t.setStartTime(null);
			}
			if(StringUtils.isNotEmpty(t.getEndTime())){
				t.setEndTime(StringUtils.toString(DateUtils.parseDate(t.getEndTime()).getTime()));
			}else{
				t.setEndTime(null);
			}
			result = super.update(t);
		} catch (Exception e) {
			logger.error("修改游戏赛事出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public List<GameEvent> queryListByPage(Map<String, Object> parameter) {
		List<GameEvent> list = new ArrayList<GameEvent>();
		try {
			list = gameEventMapper.queryListByPage(parameter);
			if(list!=null && list.size()>0){
				for (GameEvent gameEvent : list) {
					//赛事状态
					gameEvent.setGeStatusName(dictService.getDict(GlobalConstant.GAME_EVENT_STATUS, String.valueOf(gameEvent.getGeStatus())).getLabel());
					gameEvent.setGeStatusClass(dictService.getDict(GlobalConstant.GAME_EVENT_STATUS, String.valueOf(gameEvent.getGeStatus())).getLabelClass());
				}
			}
		} catch (Exception e) {
			logger.error("查询游戏赛事出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	@Override
	public int findByEventName(String eventName, String id) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("eventName", eventName);
		parameter.put("id", id);
		return gameEventMapper.findByEventName(parameter);
	}

	@Override
	public List<GameEvent> queryPartListByPage(Map<String, Object> parameter) {
		//设置分页，page里面包含了分页信息
		PageHelper.startPage(1,0, "start_time");
		return gameEventMapper.queryPartListByPage(parameter);
	}

	@Override
	public List<GameEvent> findListByGameId(String gameId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("gameId", gameId);
		parameter.put("nowTime", System.currentTimeMillis());
		return queryPartListByPage(parameter);
	}

	@Override
	public List<GameEvent> gbGameEventList(Map<String, Object> parameter) {
		if(parameter==null || !parameter.containsKey("geStatus")){
			if(parameter==null){
				parameter = new HashMap<String, Object>();
			}
			parameter.put("geStatus", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
		}
		return gameEventMapper.gbGameEventList(parameter);
	}
}
