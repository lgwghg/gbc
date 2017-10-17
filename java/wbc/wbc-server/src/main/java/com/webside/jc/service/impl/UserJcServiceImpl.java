package com.webside.jc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.jc.mapper.IUserJcMapper;
import com.webside.jc.model.UserJc;
import com.webside.jc.service.IGameBattleService;
import com.webside.jc.service.IPankouService;
import com.webside.jc.service.IUserJcService;
import com.webside.user.model.UserIncrement;
import com.webside.user.service.IUserCacheService;
import com.webside.user.service.UserIncrementService;
import com.webside.user.share.util.RedPurseUtil;
import com.webside.user.task.service.IUserTaskService;
import com.webside.user.transaction.entities.UserTransactionLog;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;


/**
 * @title : 用户竞猜业务实现类
 * @author LiGan
 * */

@Service("userJcService")
public class UserJcServiceImpl extends AbstractService<UserJc, String> implements IUserJcService {
	
	@Autowired
	private IUserTaskService userTaskService;
	/**
	 * @title ： 根据条件查询用户竞猜记录
	 * @param : String userId  用户ID
	 * @param : String gbId	   对战比赛ID
	 * @param : List gbIds 对战比赛ID（多条 ）
	 * @param : String gameResult  0:输 1：赢  2 ：进行中 3：已取消
	 * */
	@Override
	public List<UserJc> queryListByPage(Map<String, Object> parameter){
		List<UserJc> list = null;
		try{
			list = userJcMapper.queryListByPage(parameter);
			if(list!=null && list.size()>0){
				for(UserJc ujc:list){
					ujc.setUser(userCacheService.getUserEntityByUserId(ujc.getUserId()));
				}
			}
		}catch(Exception e){
			logger.error("根据条件查询用户竞猜记录出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	/**
	 * @title : 用户竞猜记录
	 * @param UserJc uj
	 * */
	@Override
	public int insert(UserJc uj){
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("gbId", uj.getGbId());
			param.put("pankouId", uj.getPankouId());
			param.put("userId", uj.getUserId());
			List<UserJc> userJcList = findByMap(param);
			UserJc userJc = null;
			if (CollectionUtils.isNotEmpty(userJcList)) {
				userJc = userJcList.get(0);
			}
			//判断用户是否已下注 如果已下注更新用户下注金额
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(userJc == null){
				//新增用户竞猜记录
				userJcMapper.insert(uj);
				//修改用户竞猜数量
				UserIncrement userIncrement = new UserIncrement();
				userIncrement.setUserId(uj.getUserId());
				userIncrement.setJcNum(1);
				userIncrementService.updateUserIncrement(userIncrement);
				
				//修改盘口参与人数和金额
				pankouService.updatePrtGold(uj.getPankouId(), uj.getJcTeamType(), 1L, uj.getJcGold());
				// 红包生成
				try {
					RedPurseUtil.generateJcRedPurse(uj.getGbId(), uj.getUser().getId(), uj.getUser().getCampaignKey());
				} catch (Exception e) {
					logger.error("竞猜生成红包出错：", e);
				}
			}else{
				//修改用户下注信息 
				userJc.setJcGold(userJc.getJcGold()+uj.getJcGold());
				userJc.setJcTime(uj.getJcTime());
				uj.setId(userJc.getId());
				userJcMapper.update(userJc);
				//修改盘口下注金额
				pankouService.updatePrtGold(uj.getPankouId(), uj.getJcTeamType(), null, uj.getJcGold());
			}
			
			//用户钱包扣款
			JSONObject deductWallet = userWalletService.deductWallet(uj.getUserId(), uj.getJcGold(), GlobalConstant.DEDUCT_TYPE_JC);
			if(deductWallet!=null && deductWallet.getIntValue("result")>0){
				//新增用户交易记录
				UserTransactionLog utl = null;
				
				if(deductWallet.getIntValue("sysGold")>0){
					utl = new UserTransactionLog();
					utl.setId(IdGen.uuid());
					utl.setUserId(uj.getUserId());
					utl.setUtTime(uj.getJcTime());
					utl.setRemarks("参与竞猜");
					utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_1);
					utl.setDataId(uj.getId());
					String str = "-"+deductWallet.getIntValue("sysGold");
					utl.setGoldNum(Long.parseLong(str));
					utl.setGoldType(GlobalConstant.GOLD_TYPE_2);
					userTransactionLogService.insert(utl);
				}
				if(deductWallet.getIntValue("gold")>0){
					utl = new UserTransactionLog();
					utl.setId(IdGen.uuid());
					utl.setUserId(uj.getUserId());
					utl.setUtTime(uj.getJcTime());
					utl.setRemarks("参与竞猜");
					utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_1);
					utl.setDataId(uj.getId());
					String str = "-"+deductWallet.getIntValue("gold");
					utl.setGoldNum(Long.parseLong(str));
					utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
					userTransactionLogService.insert(utl);
				}
				
				//修改比赛下注金额
				gameBattleService.updateGold(uj.getGbId(), uj.getJcTeamType(), uj.getJcGold());
			}
			
			// 竞猜任务
			userTaskService.updateUserTask(uj.getUserId(), GlobalConstant.USER_TASK_TYPE_JC_1, null);
		}catch(Exception e){
			logger.error("根据条件查询用户竞猜记录出错：", e);
			throw new ServiceException(e);
		}
		return 1;
	}
	
	/**
	 * @title : 更换下注战队
	 * */
	@Override
	public void updateUserJcTeam(UserJc uj){
		try{
			userJcMapper.update(uj);
			//用户钱包扣款
			JSONObject deductWallet = userWalletService.deductWallet(uj.getUserId(), GlobalConstant.UPDATE_UESR_JCTEAM_GOLD, GlobalConstant.DEDUCT_TYPE_JC);
			if(deductWallet!=null && deductWallet.getIntValue("result")>0){
				//新增用户交易记录
				UserTransactionLog utl = new UserTransactionLog();
				utl.setId(IdGen.uuid());
				utl.setUserId(uj.getUserId());
				utl.setUtType(GlobalConstant.USER_TRANSACTION_TYPE_1);
				utl.setDataId(uj.getId());
				utl.setUtTime(System.currentTimeMillis()+"");
				utl.setRemarks("更换下注战队");
				if(deductWallet.getIntValue("sysGold")>0){
					String str = "-"+deductWallet.getIntValue("sysGold");
					utl.setGoldNum(Long.parseLong(str));
					utl.setGoldType(GlobalConstant.GOLD_TYPE_2);
				}else if(deductWallet.getIntValue("gold")>0){
					String str = "-"+deductWallet.getIntValue("gold");
					utl.setGoldNum(Long.parseLong(str));
					utl.setGoldType(GlobalConstant.GOLD_TYPE_1);
				}
				userTransactionLogService.insert(utl);
			}
			//通知
			// 竞猜任务
			userTaskService.updateUserTask(uj.getUserId(), GlobalConstant.USER_TASK_TYPE_JC_1, null);
		}catch(Exception e){
			logger.error("更换下注战队：", e);
			throw new ServiceException(e);
		}
	}
	/**
	 * @title : 统计用户参与竞猜G币量
	 * @param : String userId
	 * */
	@Override
	public String getUserJcGold(String userId){
		String userGold = "0";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("gameResult", GlobalConstant.USER_JC_GAME_RESULT_2);
			userGold = userJcMapper.getUserJcGold(map);
		}catch(Exception e){
			logger.error("统计用户参与竞猜G币量出错：", e);
			throw new ServiceException(e);
		}
		return userGold;
	}
	
	@Override
	public List<UserJc> findListByUserId(String userId) {
		return userJcMapper.findListByUserId(userId);
	}

	/**
	 * @title : 根据条件查询用户竞猜记录
	 * @param String ID
	 * */
	@Override
	public List<UserJc> findByMap(Map<String, Object> param) {
		return userJcMapper.findByMap(param);
	}
	
	/**
	 * 后台用户竞猜管理用
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public List<UserJc> queryJcListByPage(Map<String, Object> param) {
		List<UserJc> list = null;
		try {
			list = userJcMapper.queryJcListByPage(param);
			if (list != null && list.size() > 0) {
				for (UserJc ujc : list) {
					ujc.setUser(userCacheService.getUserEntityByUserId(ujc.getUserId()));
				}
			}
		} catch (Exception e) {
			logger.error("根据条件查询用户竞猜记录出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IUserJcMapper userJcMapper;
	
	/**
	 * 用户缓存接口 Service定义
	 * */
	@Autowired
	private IUserCacheService userCacheService;
	
	/**
	 * 用户钱包 Service定义
	 */
	@Autowired
	private IUserWalletService userWalletService;
	
	/**
	 * 用户交易记录 Service定义
	 */
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	
	/**
	 * 比赛对战 Service定义
	 */
	@Autowired
	private IGameBattleService gameBattleService;
	
	/**
	 * 用户增量 Service定义
	 */
	@Autowired
	private UserIncrementService userIncrementService;
	
	/**
	 * 盘口 Service定义
	 */
	@Autowired
	private IPankouService pankouService;
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userJcMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userJcMapper);
	}
}
