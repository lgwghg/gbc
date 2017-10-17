/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.jc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.StringUtil;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.jc.mapper.IPankouMapper;
import com.webside.jc.model.GameBattle;
import com.webside.jc.model.Pankou;
import com.webside.jc.model.UserJc;
import com.webside.jc.service.IGameBattleService;
import com.webside.jc.service.IPankouService;
import com.webside.jc.service.IUserJcService;
import com.webside.jc.vo.PankouVo;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.dict.service.DictService;
import com.webside.system.sn.service.ISysNoticeService;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserIncrement;
import com.webside.user.service.UserIncrementService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 比赛盘口服务实现类
 *
 * @author zengxn
 * @date 2017-02-18 13:41:07
 */
@Service("pankouService")
public class PankouService extends AbstractService<Pankou, String> implements IPankouService 
{
	/**
	 * 新增比赛盘口
	 * @param Pankou
	 * @throws Exception
	 * @author zengxn
	 */
	@Override
	public int insert(Pankou t) {
		int result = 0;
		try {
			t.setId(IdGen.uuid());
			result = super.insert(t);
		} catch (Exception e) {
			logger.error("新增盘口出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	/**
	 * 修改比赛盘口
	 * @param entity
	 * @throws Exception
	 * @author zengxn
	 */
	@Override
	public int update(Pankou t) {
		int result = 0;
		try {
			//比赛结束或者比赛取消，进入以下逻辑
			boolean endGb = !StringUtils.isEmpty(t.getPkEndTime()) && t.getPkStatus().equals(GlobalConstant.DICTTYPE_IS_DELETE_0);
			boolean cancel = GlobalConstant.DICTTYPE_IS_DELETE_1.equals(t.getPkStatus());
			if(endGb || cancel){
				GameBattle gb = gameBattleService.findById(t.getGbId());
				if(gb!=null){
					//用于修改部分属性的比赛对战对象
					GameBattle updateGb = new GameBattle();
					updateGb.setId(t.getGbId());
					
					double totalGold = 0;
					//当前比赛总金额
					totalGold = (t.getPkHomePrtGold()==null?0:t.getPkHomePrtGold()) + (t.getPkAwayPrtGold()==null?0:t.getPkAwayPrtGold());
					
					//根据比赛ID、盘口ID 查询当前用户竞猜信息
					Map<String,Object> parameter = new HashMap<String,Object>();
					parameter.put("gbId", t.getGbId());
					parameter.put("pankouId", t.getId());
					
					List<UserJc> ujList = userJcService.queryListByPage(parameter);
					String sid = StringUtils.toString(gb.getSid());
					if(ujList!=null && ujList.size()>0){
						//比赛结束 分配用户收益
						if(endGb){
							//获胜战队
							String victoryTeam = "";
							double rule =0;
							
							if(t.getPkVictory().equals(t.getHomeTeamId())){
								victoryTeam = GlobalConstant.JC_TEAM_TYPE_1;
								rule = Double.parseDouble(t.getPkHomeRule());
							}else if(t.getPkVictory().equals(t.getAwayTeamId())){
								victoryTeam = GlobalConstant.JC_TEAM_TYPE_2;
								rule = Double.parseDouble(t.getPkAwayRule());
							}
							
							double estimate = 0;
							
							//获胜用户系统通知    对象 定义
							List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
							Map<String,Object> map = null;
							
							for(UserJc uj:ujList){
								map = new HashMap<String,Object>();
								if(GlobalConstant.USER_JC_GAME_RESULT_2.equals(uj.getGameResult())){
									if(victoryTeam.equals(uj.getJcTeamType())){
										//赢
										estimate = uj.getJcGold() * rule;
										long gold = Math.round(estimate);
										totalGold = totalGold - gold;
										
										//修改用户竞猜信息 
										UserJc userJc = new UserJc();
										userJc.setId(uj.getId());
										userJc.setGameResult(GlobalConstant.USER_JC_GAME_RESULT_1);
										userJc.setJcGold(uj.getJcGold());
										userJc.setVictoryGoldNum(gold);
										userJcService.update(userJc);
										
										//修改用户胜利量
										UserIncrement userIncrement = new UserIncrement();
										userIncrement.setUserId(uj.getUserId());
										userIncrement.setVictoryNum(1);
										userIncrement.setTotalProfitGoldNum((int)gold);
										userIncrementService.updateUserIncrement(userIncrement);
										
										//调用钱包接口 新增收益G币
										userWalletService.rechargeWallet(uj.getUserId(), gold, GlobalConstant.RECHARGE_TYPE_JC);
										
										//调用用户交易记录
										UserTransactionLog utl = new UserTransactionLog();
										utl.setId(IdGen.uuid());
										utl.setUserId(uj.getUserId());
										utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_1);
										utl.setDataId(uj.getId());
										utl.setGoldNum(gold);
										utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
										utl.setUtTime(System.currentTimeMillis()+"");
										utl.setRemarks("竞猜奖励");
										userTransactionLogService.insert(utl);
										
										//用户通知
										userMessageService.addMessageForUserJc(uj.getUserId(), uj.getId(), (int)gold, sid, gb.getGbName());
										
										//获胜用户加入系统通知  数据处理
										map.put("nickName", uj.getUser().getNickName());
										map.put("gbName", gb.getGbName());
										map.put("num", (int)gold);
										listMap.add(map);
									}else{
										//输
										UserJc userJc = new UserJc();
										userJc.setId(uj.getId());
										userJc.setJcGold(uj.getJcGold());
										userJc.setGameResult(GlobalConstant.USER_JC_GAME_RESULT_0);
										userJcService.update(userJc);
									}
								}
							}
							
							if(listMap!=null && listMap.size()>0){
								//获胜用户加入系统通知 接口调用
								sysNoticeService.insertGameBattleCacheList(listMap);
							}
						}else{
							//比赛取消
							long gold = 0;
							for(UserJc uj:ujList){
								
								if(!uj.getGameResult().equals(GlobalConstant.USER_JC_GAME_RESULT_3)){
									gold = uj.getJcGold();
									totalGold = totalGold - gold;
									
									//修改用户竞猜信息 
									UserJc userJc = new UserJc();
									userJc.setId(uj.getId());
									userJc.setGameResult(GlobalConstant.USER_JC_GAME_RESULT_3);
									userJc.setJcGold(uj.getJcGold());
									userJcService.update(userJc);
									
									//修改用户下注量
									UserIncrement userIncrement = new UserIncrement();
									userIncrement.setUserId(uj.getUserId());
									userIncrement.setJcNum(-1);
									userIncrementService.updateUserIncrement(userIncrement);
									
									//调用钱包接口 退回下注G币
									userWalletService.rechargeWallet(uj.getUserId(), gold, GlobalConstant.RECHARGE_TYPE_JC);
									
									//用户通知
									userMessageService.addMessageForUserJcReturn(uj.getUserId(), uj.getId(), (int)gold, sid, gb.getGbName());
									
									//调用用户交易记录
									UserTransactionLog utl = new UserTransactionLog();
									utl.setId(IdGen.uuid());
									utl.setUserId(uj.getUserId());
									utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_1);
									utl.setDataId(uj.getId());
									utl.setGoldNum(gold);
									utl.setUtTime(System.currentTimeMillis()+"");
									utl.setRemarks("退回竞猜金币");
									userTransactionLogService.insert(utl);
								}
							}
						}
						//盘口及比赛对战收益
						t.setThisPkProfit(Math.round(totalGold));
						updateGb.setThisGbProfit(gb.getThisGbProfit()+Math.round(totalGold));
						gb.setStartTime(DateUtils.getStringDate(gb.getStartTime(), DateUtils._DEFAULT1).getTime() + "");
						if (org.apache.commons.lang3.StringUtils.isNotBlank(gb.getEndTime())) {
							gb.setEndTime(DateUtils.getStringDate(gb.getEndTime(), DateUtils._DEFAULT1).getTime() + "");
						}
						gameBattleService.update(gb);
					}
				}
			}
			
			
			result = super.update(t);
			if(result==1){
				//判断是否独赢盘口，比赛对战的开始时间、结束时间、状态跟随默认的独赢盘口走
				if(GlobalConstant.PANKOU_TYPE_0.equals(t.getPankouType())){
					UserEntity user = ShiroAuthenticationManager.getUserEntity();
					GameBattle gameBattle = new GameBattle();
					gameBattle.setId(t.getGbId());
					gameBattle.setStartTime(t.getPkStartTime());
					gameBattle.setEndTime(t.getPkEndTime());
					gameBattle.setGbStatus(t.getPkStatus());
					gameBattle.setUpdateUser(user.getId());
					gameBattle.setUpdateDate((System.currentTimeMillis()+""));
					result = gameBattleService.update(gameBattle);
				}
			}
		} catch (Exception e) {
			logger.error("修改盘口出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	/**
	 * 按条件查询比赛盘口
	 * @throws Exception
	 * @author zengxn
	 */
	@Override
	public List<Pankou> queryListByPage(Map<String, Object> parameter) {
		List<Pankou> list = new ArrayList<Pankou>();
		try {
			list = super.queryListByPage(parameter);
			if(list!=null && list.size()>0){
				for (Pankou pankou : list) {
					//玩法类型
					pankou.setPankouTypeName(dictService.getDict(GlobalConstant.PANKOU_TYPE, String.valueOf(pankou.getPankouType())).getLabel());
					pankou.setPankouTypeClass(dictService.getDict(GlobalConstant.PANKOU_TYPE, String.valueOf(pankou.getPankouType())).getLabelClass());
					
					//盘口现状
					if(GlobalConstant.DICTTYPE_IS_DELETE_0.equals(pankou.getPkStatus())){
						if(!StringUtils.isEmpty(pankou.getPkEndTime())){
							//比赛已结束
							pankou.setPkStatusType(GlobalConstant.GB_STATUS_3);
						}
						long currentTime = System.currentTimeMillis();
						if(Long.parseLong(pankou.getPkStartTime())>currentTime){
							//比赛未开始
							pankou.setPkStatusType(GlobalConstant.GB_STATUS_1);
						}
						if(currentTime>=Long.parseLong(pankou.getPkStartTime()) && StringUtils.isEmpty(pankou.getPkEndTime())){
							//比赛进行中
							pankou.setPkStatusType(GlobalConstant.GB_STATUS_2);
						}
					}else{
						//比赛取消
						pankou.setPkStatusType(GlobalConstant.GB_STATUS_4);
					}
					
					//盘口现状类型
					pankou.setPkStatusTypeName(dictService.getDict(GlobalConstant.GB_STATUS, String.valueOf(pankou.getPkStatusType())).getLabel());
					pankou.setPkStatusTypeClass(dictService.getDict(GlobalConstant.GB_STATUS, String.valueOf(pankou.getPkStatusType())).getLabelClass());
				}
			}
		} catch (Exception e) {
			logger.error("查询游戏赛事出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	/**
	 * 根据ID获取比赛盘口
	 * @param ID
	 * @throws Exception
	 * @author zengxn
	 */
	@Override
	public Pankou findById(String id) {
		Pankou pankou = null;
		try{
			pankou = super.findById(id);
			if(pankou!=null){
				//盘口现状
				if(GlobalConstant.DICTTYPE_IS_DELETE_0.equals(pankou.getPkStatus())){
					if(!StringUtils.isEmpty(pankou.getPkEndTime())){
						//比赛已结束
						pankou.setPkStatusType(GlobalConstant.GB_STATUS_3);
					}
					long currentTime = System.currentTimeMillis();
					if(Long.parseLong(pankou.getPkStartTime())>currentTime){
						//比赛未开始
						pankou.setPkStatusType(GlobalConstant.GB_STATUS_1);
					}
					if(currentTime>=Long.parseLong(pankou.getPkStartTime()) && StringUtils.isEmpty(pankou.getPkEndTime())){
						//比赛进行中
						pankou.setPkStatusType(GlobalConstant.GB_STATUS_2);
					}
				}else{
					//比赛取消
					pankou.setPkStatusType(GlobalConstant.GB_STATUS_4);
				}
				
				//盘口现状类型
				pankou.setPkStatusTypeName(dictService.getDict(GlobalConstant.GB_STATUS, String.valueOf(pankou.getPkStatusType())).getLabel());
				
				//转换开始时间
				pankou.setPkStartTime(DateUtils.longToString(Long.parseLong(pankou.getPkStartTime())));
				//转换结束时间
				if(!StringUtil.isEmpty(pankou.getPkEndTime())){
					pankou.setPkEndTime(DateUtils.longToString(Long.parseLong(pankou.getPkEndTime())));
				}
			}
		}catch(Exception e){
			logger.error("根据id查询盘口对象出错：", e);
			throw new ServiceException(e);
		}
		return pankou;
	}

	/**
	 * 根据比赛对战ID、盘口局数获取比赛盘口
	 * @param gbId
	 * @param inningNum
	 * @return
	 */
	@Override
	public Pankou findByGbIdInningNum(String gbId, String inningNum) {
		Pankou pankou = null;
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("gbId", gbId);
		
		int intValue = StringUtils.toInteger(inningNum).intValue();
		for (int i = intValue; i >= 1; i--) {
			parameter.put("inningNum", StringUtils.toString(i));
			pankou = pankouMapper.findByGbIdInningNum(parameter);
			if(pankou!=null){
				break;
			}
		}
		return pankou;
	}
	
	/**
	 * @title : 修改盘口参与人数 和 战队下注金额
	 * @param id 盘口id
	 * @param type 类别：1.主场战队 2.客场战队
	 * @param num 参与人数
	 * @param gold 下注金额
	 * */
	@Override
	public void updatePrtGold(String id,String type, Long num, Long gold) {
		try{
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("id", id);
			if(num!=null){
				parameter.put("prtNum", num);
			}
			if(gold!=null){
				parameter.put("prtGoldNum", gold);
			}
			if (GlobalConstant.JC_TEAM_TYPE_1.equals(type)) {
				parameter.put("prtGoldColumn", "PK_HOME_PRT_GOLD");
				parameter.put("prtNumColumn", "PK_HOME_PRT");
			}else{
				parameter.put("prtGoldColumn", "PK_AWAY_PRT_GOLD");
				parameter.put("prtNumColumn", "PK_AWAY_PRT");
			}
			pankouMapper.updatePrtGold(parameter);
		}catch(Exception e){
			logger.error("修改盘口参与人数 和 战队下注金额出错：", e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * @title : 修改盘口参与人数 和 战队下注金额，更换用户下注战队调用
	 * */
	@Override
	public void updatePrtGold(Pankou pankou) {
		try{
			pankouMapper.updatePkPrtGold(pankou);
		}catch(Exception e){
			logger.error("修改盘口参与人数 和 战队下注金额，更换用户下注战队调用出错：", e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 查询盘口VO通过盘口ID和用户ID
	 * @param id
	 * @param userId
	 * @return
	 */
	public PankouVo queryVoByidAndUser(String id,String currentUser){
		try{
			if(StringUtils.isNotBlank(id)){
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("id", id);
				if(StringUtils.isNotBlank(currentUser)){
					parameter.put("currentUser", currentUser);
				}
				PankouVo pankouVo = pankouMapper.queryVoByidAndUser(parameter);
				if(pankouVo!=null){
					pankouVo.getUserJc();
				}
				return pankouVo;
			}else{
				return null;
			}
		}catch(Exception e){
			logger.error("修改盘口参与人数 和 战队下注金额出错：", e);
			throw new ServiceException(e);
		}
	}
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(pankouMapper);
	}

	/**
	 * 比赛盘口 DAO定义
	 */
	@Autowired
	private IPankouMapper pankouMapper;
	
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;
	
	/**
	 * 比赛对战管理 Service定义
	 */
	@Autowired
	private IGameBattleService gameBattleService;
	
	/**
	 * 用户竞猜管理 Service定义
	 */
	@Autowired
	private IUserJcService userJcService;
	
	/**
	 * 用户钱包管理 Service定义
	 */
	@Autowired
	private IUserWalletService userWalletService;
	
	/**
	 * 用户增量管理 Service定义
	 */
	@Autowired
	private UserIncrementService userIncrementService;
	
	/**
	 * 用户交易记录管理 Service定义
	 */
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	
	/**
	 * 用户消息管理 Service定义
	 */
	@Autowired
	private IUserMessageService userMessageService;
	
	/**
	 * 系统通知管理 Service定义
	 */
	@Autowired
	private ISysNoticeService sysNoticeService;
}
