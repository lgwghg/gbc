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
import com.webside.match.entities.Game;
import com.webside.match.mapper.IGameMapper;
import com.webside.match.service.IGameService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.dict.service.DictService;
import com.webside.user.model.UserEntity;
import com.webside.util.IdGen;

/**
 * 游戏服务实现类
 *
 * @author zengxn
 * @date 2016-11-23 11:31:27
 */
@Service("gameService")
public class GameService extends AbstractService<Game, String> implements IGameService 
{
	private Logger logger = Logger.getLogger(this.getClass());
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(gameMapper);
	}

	/**
	 * 游戏 DAO定义
	 */
	@Autowired
	private IGameMapper gameMapper;
	
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;
	
	@Override
	public int insert(Game t) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setId(IdGen.uuid());
			t.setCreateTime((System.currentTimeMillis()+""));
			t.setCreateOperatorId(user.getId());
			t.setCreateOperatorName(user.getNickName());
			result = super.insert(t);
		} catch (Exception e) {
			logger.error("新增游戏出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public int update(Game t) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setUpdateTime(System.currentTimeMillis()+"");
			t.setUpdateOperatorId(user.getId());
			t.setUpdateOperatorName(user.getNickName());
			result = super.update(t);
		} catch (Exception e) {
			logger.error("修改游戏出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public List<Game> queryListByPage(Map<String, Object> parameter) {
		List<Game> list = new ArrayList<Game>();
		try {
			list = gameMapper.queryListByPage(parameter);
			if(list!=null && list.size()>0){
				for (Game game : list) {
					//游戏有效状态
					game.setGameStatusName(dictService.getDict(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(game.getGameStatus())).getLabel());
					game.setGameStatusClass(dictService.getDict(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(game.getGameStatus())).getLabelClass());
				}
			}
		} catch (Exception e) {
			logger.error("查询游戏出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}

	@Override
	public int findByGameName(String gameName, String id) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("gameName", gameName);
		parameter.put("id", id);
		return gameMapper.findByGameName(parameter);
	}

	@Override
	public Game findByEnglishName(String englishName, String id) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("englishName", englishName);
		parameter.put("id", id);
		return gameMapper.findByEnglishName(parameter);
	}

	@Override
	public List<Game> queryPartList(Map<String, Object> parameter) {
		//设置分页，page里面包含了分页信息
		PageHelper.startPage(1,0, "sort_num desc");
		if(parameter==null || !parameter.containsKey("gameStatus")){
			if(parameter==null){
				parameter = new HashMap<String, Object>();
			}
			parameter.put("gameStatus", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
		}
		return gameMapper.queryPartListByPage(parameter);
	}

	@Override
	public List<Game> queryPartListByPage(Map<String, Object> parameter) {
		if(parameter==null || !parameter.containsKey("gameStatus")){
			if(parameter==null){
				parameter = new HashMap<String, Object>();
			}
			parameter.put("gameStatus", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
		}
		return gameMapper.queryPartListByPage(parameter);
	}
}
