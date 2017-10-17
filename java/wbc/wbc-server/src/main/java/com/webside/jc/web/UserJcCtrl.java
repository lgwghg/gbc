package com.webside.jc.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.GlobalConstant;
import com.webside.common.redis.listener.SendMessage;
import com.webside.dtgrid.model.Pager;
import com.webside.jc.model.GameBattle;
import com.webside.jc.model.Pankou;
import com.webside.jc.model.UserJc;
import com.webside.jc.service.IGameBattleService;
import com.webside.jc.service.IPankouService;
import com.webside.jc.service.IUserJcService;
import com.webside.jc.service.impl.OddsUtils;
import com.webside.jc.vo.OddsVo;
import com.webside.jc.vo.PankouVo;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.friends.service.IUserFriendsService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.entities.UserWallet;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * @title : 用户竞猜业务处理类
 * @author LiGan
 * */
@Controller
@RequestMapping("/userJc/")
public class UserJcCtrl extends BaseController {

	/**
	 * 用户竞猜 Service定义
	 */
	@Autowired
	private IUserJcService userJcService;
	
	/**
	 * 比赛对战 Service定义
	 */
	@Autowired
	private IGameBattleService gameBattleService;
	
	/**
	 * 盘口 Service定义
	 */
	@Autowired
	private IPankouService pankouService;
	
	/**
	 * 用户缓存 Service定义
	 */
	@Autowired
	private IUserCacheService userCacheService;
	
	/**
	 * 用户交易记录(钱包明细) Service定义
	 */
	@Autowired
	private IUserTransactionLogService userTransactionLogService;
	
	/**
	 * 用户钱包 Service定义
	 */
	@Autowired
	private IUserWalletService userWalletService;
	
	/**
	 * 推荐好友 Service定义
	 */
	@Autowired
	private IUserFriendsService userFriendsService;
	
	/**
	 * 消息队列 Service定义
	 */
	@Autowired
	private SendMessage sendMessage;
	
	/**
	 * @title : 获得用户下注信息
	 * */
	@RequestMapping("getThisUserJc")
	@ResponseBody
	public Map<String,Object> getThisUserJc(String gbId){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		try{
			UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("gbId", gbId);
			List<UserJc> list = userJcService.queryListByPage(map);
			if(list!=null && list.size()>0){
				if(userEntity!=null){
					//当前用户竞猜信息
					UserJc userJc = new UserJc();
					long userJcGold = 0;
					long victoryGoldNum = 0;
					for(UserJc uj:list){
						if(userEntity.getId().equals(uj.getUserId())){
							userJc.setId(uj.getId());
							userJc.setUserId(userEntity.getId());
							userJcGold = userJcGold + uj.getJcGold();
							victoryGoldNum = victoryGoldNum + uj.getVictoryGoldNum();
							userJc.setJcTeamType(uj.getJcTeamType());
						}
					}
					userJc.setGbId(gbId);
					userJc.setJcGold(userJcGold);
					userJc.setVictoryGoldNum(victoryGoldNum-userJcGold);
					jsonMap.put("userJcInfo", userJc);
				}
			}
			jsonMap.put("isSuccess", Boolean.TRUE);
		}catch (Exception e) {
			logger.error("比赛对战用户竞猜列表出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
		}
		return jsonMap;
	}
	
	/**
	 * @title : 比赛对战用户竞猜列表
	 * @param : String gbId
	 * */
	@RequestMapping("gbUserJcList")
	@ResponseBody
	public Map<String,Object> gbUserJcList(String gridPager, String gbId, boolean isHome, boolean isAway) {
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("pageCount", 0);
		try{
			// 1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "uj.jc_time desc");
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("gbId", gbId);
			if(isHome) {
				map.put("jcTeamType", "1");
				List<UserJc> homeList = userJcService.queryJcListByPage(map);
				jsonMap.put("nowPage", pager.getNowPage());
				jsonMap.put("pageSize", pager.getPageSize());
				jsonMap.put("pageCount", page.getPages());
				jsonMap.put("recordCount", page.getTotal());
				jsonMap.put("startRecord", page.getStartRow());
				if(pager.getNowPage() == page.getPages()) {// 当前页 和 最后一页 相等
					isHome = false;
				}
				jsonMap.put("homeList", homeList);
			}
			
			if(isAway) {
				map.put("jcTeamType", "2");
				page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "uj.jc_time desc");
				List<UserJc> awayList = userJcService.queryJcListByPage(map);
				if(page.getPages() > MapUtils.getInteger(jsonMap, "pageCount")) {
					jsonMap.put("nowPage", pager.getNowPage());
					jsonMap.put("pageSize", pager.getPageSize());
					jsonMap.put("pageCount", page.getPages());
					jsonMap.put("recordCount", page.getTotal());
					jsonMap.put("startRecord", page.getStartRow());
				}
				if(pager.getNowPage() == page.getPages()) {// 当前页 和 最后一页 相等
					isAway = false;
				}
				jsonMap.put("awayList", awayList);
			}
			
			jsonMap.put("isHome", isHome);
			jsonMap.put("isAway", isAway);
			jsonMap.put("isSuccess", Boolean.TRUE);
		} catch (Exception e) {
			logger.error("比赛对战用户竞猜列表出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
		}
		return jsonMap;
	}
	
	/**
	 * @title : 用户下注
	 * @param : String pkId
	 * @param : String gold
	 * @param : String team
	 * @param : String betMode 下注模式 1下注 2切换队伍
	 * */
	@RequestMapping("addUserJc")
	@ResponseBody
	public Map<String, Object> addUserJc(String pkId,String gold,String teamType,String betMode){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String message = "";
		boolean success = false;
		try{
			UserEntity userEntity = userCacheService.getUserEntityByUserId(ShiroAuthenticationManager.getUserId());
			if(userEntity!=null){
				//获得当前用户钱包信息
				UserWallet uw = userWalletService.findWalletByUserId(userEntity.getId());
				//判断用户下注金币 是否小于等于当前用户金币
				long total = uw.getGold() + uw.getSysGoldNum();
				long jcGold = Long.parseLong(gold);
				if(jcGold <= total && jcGold>=100 && jcGold<=100000){
					PankouVo pankouVo = pankouService.queryVoByidAndUser(pkId, userEntity.getId());
					if(pankouVo!=null){
						if(pankouVo.getPkStatus().equals(GlobalConstant.DICTTYPE_IS_DELETE_0)){
							if(pankouVo.getGbstate().equals(GlobalConstant.PK_STATUS_0)){
								UserJc uj = new UserJc();
								if(betMode.equals("1")){	//用户下注
									uj.setId(IdGen.uuid());
									uj.setUserId(userEntity.getId());
									uj.setGbId(pankouVo.getGbId());
									uj.setPankouId(pankouVo.getId());
									uj.setJcGold(jcGold);
									uj.setJcTeamType(teamType);
									uj.setJcTime(System.currentTimeMillis()+"");
									uj.setUser(userEntity);
									//用户竞猜结果 默认2 进行中
									uj.setGameResult(GlobalConstant.USER_JC_GAME_RESULT_2);
									int i = userJcService.insert(uj);	//插入或更新？
									if(i==1){
										UserJc userjc = null;
										if(pankouVo.getUserJc()!=null){		//用户已经下注过
											userjc = pankouVo.getUserJc();
											userjc.setJcGold(jcGold+userjc.getJcGold());
											userjc.setJcTeamType(uj.getJcTeamType());
											userjc.setJcTime(uj.getJcTime());
										}else{
											userjc = uj;
										}
										pankouVo.setUserJc(userjc);	//赋予最新的userJc信息给盘口信息
										
										
										//获得当前竞猜盘口
										Pankou p = pankouService.findById(pkId);
										//计算赔率
										Map<String, Object> mapOdds = OddsUtils.getJcOdds(Double.parseDouble(p.getPkHomeRule()), Double.parseDouble(p.getPkAwayRule()), p.getPkHomePrt(), p.getPkAwayPrt(), p.getPkHomePrtGold(), p.getPkAwayPrtGold());
										//更新比赛赔率
										Pankou pankou = new Pankou();
										pankou.setId(pkId);
										pankou.setPkHomeRule(mapOdds.get("betOddsa").toString());
										pankou.setPkAwayRule(mapOdds.get("betOddsb").toString());
										pankouService.update(pankou);
										
										pankouVo.setPkHomeRule(mapOdds.get("betOddsa").toString());
										pankouVo.setPkAwayRule(mapOdds.get("betOddsb").toString());
										pankouVo.setPkHomePrt(p.getPkHomePrt());
										pankouVo.setPkAwayPrt(p.getPkAwayPrt());
										
										
										//用户第一次竞猜记录
										userFriendsService.addJc();

										//发送消息，当前赔率
										sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new OddsVo(pankouVo.getId(), pankouVo.getPkHomeRule(), pankouVo.getPkAwayRule()));
										success = true;
										jsonMap.put("pankouVo", pankouVo);
									}else{
										message = "操作失败errorCode[002]，请联系客服";
									}
								}else{	//切换队伍
									
									
									
									
									
									
								}
								
							}else if(pankouVo.getGbstate().equals(GlobalConstant.PK_STATUS_1)){
								message = "当前盘口已开始，下注已关闭。";
							}else if(pankouVo.getGbstate().equals(GlobalConstant.PK_STATUS_2)){
								message = "当前盘口已结束，下注已关闭。";
							}else{
								message = "当前盘口已取消，下注已关闭。";
							}
						}else{
							message = "当前盘口已取消，下注已关闭。";
						}
					}else{
						message = "操作失败errorCode[001]，请联系客服";
					}
				}else{
					if(total<100||(jcGold > total && jcGold>=100 && jcGold<=100000)){
						message = "金币不足请充值";
					}else{
						message = "金币输入有误";
					}
				}
			}
		}catch (Exception e) {
			logger.error("用户下注出错：", e);
			message = "操作失败errorCode[003]，请联系客服";
		}
		jsonMap.put("isSuccess", success);
		jsonMap.put("message", message);
		return jsonMap;
	}
	
	/**
	 * @title : 更换用户下注战队
	 * @param : String gbId
	 * */
	@RequestMapping("updateUserJcTeam")
	@ResponseBody
	public Map<String, Object> updateUserJcTeam(String pkId){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			if(userEntity!=null){
				//获得当前竞猜盘口
				Pankou pk = pankouService.findById(pkId);
				if(pk==null){
					jsonMap.put("isSuccess", Boolean.FALSE);
					jsonMap.put("message", "操作失败，请联系客服");
					return jsonMap;
				}
				
				if(pk.getPkStatusType().equals(GlobalConstant.GB_STATUS_1)){
					//根据比赛ID 和用户ID 查询当前用户下注信息
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("gbId", pk.getGbId());
					param.put("pankouId", pkId);
					param.put("userId", userEntity.getId());
					List<UserJc> userJcList = userJcService.findByMap(param);
					UserJc userJc = null;
					if (CollectionUtils.isNotEmpty(userJcList)) {
						userJc = userJcList.get(0);
					}
					if(userJc!=null){
						//更换战队
						Pankou p = new Pankou();
						p.setId(pkId);
						
						GameBattle g = new GameBattle();
						g.setId(pk.getGbId());
						if(userJc.getJcTeamType().equals(GlobalConstant.JC_TEAM_TYPE_1)){
							//用户下注主场战队，现更换到客场战队
							userJc.setJcTeamType(GlobalConstant.JC_TEAM_TYPE_2);
							
							//更改比赛对象属性值
							pk.setPkHomePrt(pk.getPkHomePrt()-1);
							pk.setPkHomePrtGold(pk.getPkHomePrtGold()-userJc.getJcGold());
							pk.setPkAwayPrt(pk.getPkAwayPrt()+1);
							pk.setPkAwayPrtGold(pk.getPkAwayPrtGold()+userJc.getJcGold());
							
							//更新盘口数据库对象
							p.setPkHomePrt(-1);
							p.setPkHomePrtGold(-userJc.getJcGold());
							p.setPkAwayPrt(1);
							p.setPkAwayPrtGold(userJc.getJcGold());
							
							//更新比赛数据库对象
							g.setHomePrtGold(-userJc.getJcGold());
							g.setAwayPrtGold(userJc.getJcGold());
						}else{
							userJc.setJcTeamType(GlobalConstant.JC_TEAM_TYPE_1);
							
							//更改比赛对象属性值
							pk.setPkHomePrt(pk.getPkHomePrt()+1);
							pk.setPkHomePrtGold(pk.getPkHomePrtGold()+userJc.getJcGold());
							pk.setPkAwayPrt(pk.getPkAwayPrt()-1);
							pk.setPkAwayPrtGold(pk.getPkAwayPrtGold()-userJc.getJcGold());
							
							//更新盘口数据库对象
							p.setPkHomePrt(1);
							p.setPkHomePrtGold(userJc.getJcGold());
							p.setPkAwayPrt(-1);
							p.setPkAwayPrtGold(-userJc.getJcGold());
							
							//更新比赛数据库对象
							g.setHomePrtGold(userJc.getJcGold());
							g.setAwayPrtGold(-userJc.getJcGold());
						}
						userJcService.updateUserJcTeam(userJc);
						//修改比赛下注金额
						gameBattleService.updateGold(g);
						//修改盘口参与人数、下注金额
						pankouService.updatePrtGold(p);
						
						//计算赔率
						Map<String, Object> mapOdds = OddsUtils.getJcOdds(Double.parseDouble(pk.getPkHomeRule()), Double.parseDouble(pk.getPkAwayRule()), pk.getPkHomePrt(), pk.getPkAwayPrt(), pk.getPkHomePrtGold(), pk.getPkAwayPrtGold());
						//更新比赛赔率
						Pankou pankou = new Pankou();
						pankou.setId(pkId);
						pankou.setPkHomeRule(mapOdds.get("betOddsa").toString());
						pankou.setPkAwayRule(mapOdds.get("betOddsb").toString());
						pankouService.update(pankou);
						
						//返回当前比赛信息
						pk.setPkHomeRule(mapOdds.get("betOddsa").toString());
						pk.setPkAwayRule(mapOdds.get("betOddsb").toString());
						pk.setPkHomePrtNum(mapOdds.get("betRatioa").toString());
						pk.setPkAwayPrtNum(mapOdds.get("betRatiob").toString());
						jsonMap.put("pkInfo", pk);
						jsonMap.put("updateUserJc", userJc);
						
						jsonMap.put("isSuccess", Boolean.TRUE);
						//发送消息，当前赔率
						sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, new OddsVo(pk.getId(), pk.getPkHomeRule(), pk.getPkAwayRule()));
					}else{
						jsonMap.put("isSuccess", Boolean.FALSE);
						jsonMap.put("message", "用户未参与当前盘口竞猜");
						return jsonMap;
					}
				}else{
					jsonMap.put("isSuccess", Boolean.FALSE);
					jsonMap.put("message", "盘口已开始不可修改");
					return jsonMap;
				}
			}
		}catch (Exception e) {
			logger.error("用户下注出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
			jsonMap.put("message", "服务器异常");
		}
		return jsonMap;
	}
	
	/**
	 * 获取你的竞猜详情
	 * 
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "getMyJc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyJc() {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String userId = ShiroAuthenticationManager.getUserId();
			UserEntity userEntity = userCacheService.getUserEntityByUserId(userId);
			UserWallet userWallet = userWalletService.findWalletByUserId(userId);

			if (userWallet != null) {
				Long gold = userWallet.getGold();
				gold = gold == null ? 0 : gold;
				Long sysGoldNum = userWallet.getSysGoldNum();
				sysGoldNum = sysGoldNum == null ? 0 : sysGoldNum;
				jsonMap.put("userGold", gold + sysGoldNum);
			}
			// 获取本周收益, 按日期排序
			Map<String, Long> weekProfit = userTransactionLogService.getMapForWeekJc(userId);

			jsonMap.put("weekProfit", weekProfit);
			jsonMap.put("userEntity", userEntity);
			jsonMap.put("isSuccess", Boolean.TRUE);
		} catch (Exception e) {
			logger.error("你的竞猜获取出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
			jsonMap.put("message", "你的竞猜获取出错:" + e.getMessage());
		}
		return jsonMap;
	}

	/**
	 * 获取你的竞猜列表
	 * @param gridPager
	 * @return
	 * @author zhangfei
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			// 1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			Map<String, Object> map = pager.getParameters();
			String orderBy = MapUtils.getString(map, "orderBy");
			if(StringUtils.isBlank(orderBy)) {
				orderBy = "startTime";
			}

			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), orderBy);
			String userId = ShiroAuthenticationManager.getUserId();
			List<UserJc> list = userJcService.findListByUserId(userId);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			parameters.put("list", list);
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			parameters.put("message", e.getMessage());
			logger.error("ajax获取兑换记录列表数据出错：", e);
		}
		return parameters;
	}
}
