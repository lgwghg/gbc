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

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.match.entities.Team;
import com.webside.match.mapper.ITeamMapper;
import com.webside.match.service.ITeamService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.dict.service.DictService;
import com.webside.user.model.UserEntity;
import com.webside.util.IdGen;

/**
 * 战队服务实现类
 *
 * @author zengxn
 * @date 2016-11-23 16:32:39
 */
@Service("teamService")
public class TeamService extends AbstractService<Team, String> implements ITeamService 
{
	private Logger logger = Logger.getLogger(this.getClass());
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(teamMapper);
	}

	/**
	 * 战队 DAO定义
	 */
	@Autowired
	private ITeamMapper teamMapper;
	
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;
	
	/**
	 * 战队游戏管理 Service定义
	 */
	@Autowired
	private TeamGameService teamGameService;
	
	@Override
	public int insert(Team t,List<String> ids) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setId(IdGen.uuid());
			t.setCreateTime((System.currentTimeMillis()+""));
			t.setCreateOperatorId(user.getId());
			t.setCreateOperatorName(user.getNickName());
			result = super.insert(t);
			if(result==1){
				boolean addTeamGameBatch = teamGameService.addTeamGameBatch(t.getId(), ids);
				if(!addTeamGameBatch){
					result = 0;
				}
			}
		} catch (Exception e) {
			logger.error("新增战队出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public int update(Team t,List<String> ids) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setUpdateTime(System.currentTimeMillis()+"");
			t.setUpdateOperatorId(user.getId());
			t.setUpdateOperatorName(user.getNickName());
			result = super.update(t);
			if(result==1){
				boolean addTeamGameBatch = teamGameService.addTeamGameBatch(t.getId(), ids);
				if(!addTeamGameBatch){
					result = 0;
				}
			}
		} catch (Exception e) {
			logger.error("修改战队出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public List<Team> queryListByPage(Map<String, Object> parameter) {
		List<Team> list = new ArrayList<Team>();
		try {
			list = teamMapper.queryListByPage(parameter);
			if(list!=null && list.size()>0){
				for (Team team : list) {
					//战队有效状态
					team.setTeamStatusName(dictService.getDict(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(team.getTeamStatus())).getLabel());
					team.setTeamStatusClass(dictService.getDict(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(team.getTeamStatus())).getLabelClass());
				}
			}
		} catch (Exception e) {
			logger.error("查询战队出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	@Override
	public int findByTeamName(String teamName, String id) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("teamName", teamName);
		parameter.put("id", id);
		return teamMapper.findByTeamName(parameter);
	}
}
