package com.webside.jc.service.impl;

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
import com.webside.jc.mapper.IGameBattleMapper;
import com.webside.jc.model.GameBattle;
import com.webside.jc.model.Pankou;
import com.webside.jc.service.IGameBattleService;
import com.webside.jc.service.IPankouService;
import com.webside.jc.service.IUserJcService;
import com.webside.jc.vo.GameBattleVo;
import com.webside.jc.vo.PankouVo;
import com.webside.match.service.IGameEventService;
import com.webside.match.service.IGameService;
import com.webside.match.service.ITeamService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.system.dict.service.DictService;
import com.webside.system.sn.service.ISysNoticeService;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserIncrementService;
import com.webside.user.transaction.service.IUserTransactionLogService;
import com.webside.user.wallet.service.IUserWalletService;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * @title : 对战比赛业务接口实现类
 * @author LiGan
 * */
@Service("gameBattleService")
public class GameBattleServiceImpl extends AbstractService<GameBattle, String> implements IGameBattleService {

	/**
	 * lwh 获取比赛详情数据
	 * 
	 * @param parameter
	 * @return
	 */
	public List<GameBattleVo> queryGbDetailsListByPage(Map<String, Object> parameter) {
		List<GameBattleVo> list = null;
		try {
			list = gameBattleMapper.queryGbDetailsListByPage(parameter);
			if (list != null) {
				List<PankouVo> pankous = null;
				for (GameBattleVo gb : list) {
					// 比赛规则
					// gb.setGameRuleName(dictService.getDict(GlobalConstant.GAME_RULE,
					// gb.getGameRule()).getLabel());

					pankous = gb.getPankous();
					if (pankous != null && pankous.size() > 0) {

						for (PankouVo pk : pankous) {
							pk.getUserJc();
							// 计算参与人数百分比
							int total = 0;
							total = pk.getPkHomePrt() + pk.getPkAwayPrt();
							if (total == 0) {
								// 当前比赛对战暂无用户参与赌注
								pk.setPkHomePrtNum("50%");
								pk.setPkAwayPrtNum("50%");
							} else {
								// 乘以100 获得百分比
								double d = ((double) pk.getPkHomePrt() / total) * 100;
								// 四舍五入 取整
								int homePrtNum = (int) Math.round(d);
								// String str = new BigDecimal(d).setScale(0,
								// BigDecimal.ROUND_HALF_UP).toString(); //高精度
								// 效率底
								pk.setPkHomePrtNum(homePrtNum + "%");
								// 客场百分比 用100%减主场
								pk.setPkAwayPrtNum((100 - homePrtNum) + "%");
							}
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error("网站比赛列表页面出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}

	/**
	 * 网站比赛列表页面
	 * */
	public List<GameBattle> webQueryListByPage(Map<String, Object> parameter) {
		List<GameBattle> list = null;
		try {
			list = gameBattleMapper.webQueryListByPage(parameter);
			if (list != null && list.size() > 0) {
				for (GameBattle gb : list) {
					// 赛事
					gb.setGevent(gameEventService.findById(gb.getGeId()));
					// 游戏
					gb.setGame(gameService.findById(gb.getGameId()));
					// 主场战队
					gb.setHt(teamService.findById(gb.getHomeTeam()));
					// 客场战队
					gb.setAt(teamService.findById(gb.getAwayTeam()));

					// 比赛规则
					gb.setGameRuleName(dictService.getDict(GlobalConstant.GAME_RULE, gb.getGameRule()).getLabel());

					if (GlobalConstant.DICTTYPE_IS_DELETE_0.equals(gb.getGbStatus())) {
						// 比赛分类
						if (!StringUtils.isEmpty(gb.getEndTime())) {
							// 比赛已结束
							gb.setGbType(GlobalConstant.GB_STATUS_3);
						}
						long currentTime = System.currentTimeMillis();
						if (Long.parseLong(gb.getStartTime()) > currentTime) {
							// 比赛未开始
							gb.setGbType(GlobalConstant.GB_STATUS_1);
						}
						if (currentTime >= Long.parseLong(gb.getStartTime()) && StringUtils.isEmpty(gb.getEndTime())) {
							// 比赛进行中
							gb.setGbType(GlobalConstant.GB_STATUS_2);
						}
					} else {
						// 比赛取消
						gb.setGbType(GlobalConstant.GB_STATUS_4);
					}

					/**
					 * v米注释,有需要则修改，否则删除--start
					 */
					/*
					 * //计算参与人数百分比 total = gb.getHomePrt() + gb.getAwayPrt();
					 * if(total==0){ //当前比赛对战暂无用户参与赌注 gb.setHomePrtNum("50%");
					 * gb.setAwayPrtNum("50%"); }else{ //乘以100 获得百分比 d =
					 * ((double)gb.getHomePrt() / total)*100; //四舍五入 取整
					 * homePrtNum = (int) Math.round(d); // String str = new
					 * BigDecimal(d).setScale(0,
					 * BigDecimal.ROUND_HALF_UP).toString(); //高精度 效率底
					 * gb.setHomePrtNum(homePrtNum+"%"); //客场百分比 用100%减主场
					 * gb.setAwayPrtNum((100-homePrtNum)+"%"); }
					 */
					/**
					 * v米注释,有需要则修改，否则删除--end
					 */
				}
			}
		} catch (Exception e) {
			logger.error("网站比赛列表页面出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}

	/**
	 * @title : 根据条件查询对战比赛列表
	 * @param : String gameId 游戏ID
	 * @param : String geId 赛事ID
	 * @param : String gbStatus 状态
	 * @param : String
	 * */
	public List<GameBattle> queryListByPage(Map<String, Object> parameter) {
		List<GameBattle> list = null;
		try {
			list = gameBattleMapper.queryListByPage(parameter);

			if (list != null && list.size() > 0) {
				for (GameBattle gb : list) {
					// 赛事
					gb.setGevent(gameEventService.findById(gb.getGeId()));
					// 游戏
					gb.setGame(gameService.findById(gb.getGameId()));
					// 主场战队
					gb.setHt(teamService.findById(gb.getHomeTeam()));
					// 客场战队
					gb.setAt(teamService.findById(gb.getAwayTeam()));

					// 比赛规则
					gb.setGameRuleName(dictService.getDict(GlobalConstant.GAME_RULE, gb.getGameRule()).getLabel());

					if (GlobalConstant.DICTTYPE_IS_DELETE_0.equals(gb.getGbStatus())) {
						// 比赛现状
						if (!StringUtils.isEmpty(gb.getEndTime())) {
							// 比赛已结束
							gb.setGbType(GlobalConstant.GB_STATUS_3);
						}
						long currentTime = System.currentTimeMillis();
						if (Long.parseLong(gb.getStartTime()) > currentTime) {
							// 比赛未开始
							gb.setGbType(GlobalConstant.GB_STATUS_1);
						}
						if (currentTime >= Long.parseLong(gb.getStartTime()) && StringUtils.isEmpty(gb.getEndTime())) {
							// 比赛进行中
							gb.setGbType(GlobalConstant.GB_STATUS_2);
						}
					} else {
						// 比赛取消
						gb.setGbType(GlobalConstant.GB_STATUS_4);
					}

					// 比赛现状类型
					gb.setGbTypeName(dictService.getDict(GlobalConstant.GB_STATUS, String.valueOf(gb.getGbType())).getLabel());
					gb.setGbTypeClass(dictService.getDict(GlobalConstant.GB_STATUS, String.valueOf(gb.getGbType())).getLabelClass());

					/**
					 * v米注释,有需要则修改，否则删除--start
					 */
					/*
					 * //计算参与人数百分比 total = gb.getHomePrt() + gb.getAwayPrt();
					 * if(total==0){ //当前比赛对战暂无用户参与赌注 gb.setHomePrtNum("50%");
					 * gb.setAwayPrtNum("50%"); }else{ //乘以100 获得百分比 d =
					 * ((double)gb.getHomePrt() / total)*100; //四舍五入 取整
					 * homePrtNum = (int) Math.round(d); // String str = new
					 * BigDecimal(d).setScale(0,
					 * BigDecimal.ROUND_HALF_UP).toString(); //高精度 效率底
					 * gb.setHomePrtNum(homePrtNum+"%"); //客场百分比 用100%减主场
					 * gb.setAwayPrtNum((100-homePrtNum)+"%"); }
					 */
					/**
					 * v米注释,有需要则修改，否则删除--end
					 */
				}
			}
		} catch (Exception e) {
			logger.error("根据条件查询对战比赛列表出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}

	/**
	 * @title : 根据赛季ID查询最新一条对战比赛信息
	 * @param : String geId
	 * */
	/*
	 * public GameBattle getNewestGameBattle(String geId){ GameBattle gb = null;
	 * try{ PageHelper.startPage(1,1, "startTime"); Map<String,Object> parameter
	 * = new HashMap<String,Object>(); parameter.put("geId", geId);
	 * parameter.put("beginCreateTime", System.currentTimeMillis()+"");
	 * parameter.put("gbStatus", GlobalConstant.GB_STATUS_1);
	 * 
	 * List<GameBattle> list = queryListByPage(parameter); if(list!=null &&
	 * list.size()>0){ gb = list.get(0); } }catch(Exception e){
	 * e.printStackTrace(); logger.error("根据赛季ID查询最新一条对战比赛信息出错：", e); } return
	 * gb; }
	 */

	/**
	 * @title : 根据ID查询比赛对战信息
	 * @param : String id
	 * */
	public GameBattle findById(String id) {
		GameBattle gb = null;
		try {
			gb = gameBattleMapper.findById(id);
			if (gb != null) {
				// 赛事
				gb.setGevent(gameEventService.findById(gb.getGeId()));
				// 游戏
				gb.setGame(gameService.findById(gb.getGameId()));
				// 主场战队
				gb.setHt(teamService.findById(gb.getHomeTeam()));
				// 客场战队
				gb.setAt(teamService.findById(gb.getAwayTeam()));

				// 比赛现状
				if (GlobalConstant.DICTTYPE_IS_DELETE_0.equals(gb.getGbStatus())) {
					if (!StringUtils.isEmpty(gb.getEndTime())) {
						// 比赛已结束
						gb.setGbType(GlobalConstant.GB_STATUS_3);
					}
					long currentTime = System.currentTimeMillis();
					if (Long.parseLong(gb.getStartTime()) > currentTime) {
						// 比赛未开始
						gb.setGbType(GlobalConstant.GB_STATUS_1);
					}
					if (currentTime >= Long.parseLong(gb.getStartTime()) && StringUtils.isEmpty(gb.getEndTime())) {
						// 比赛进行中
						gb.setGbType(GlobalConstant.GB_STATUS_2);
					}
				} else {
					// 比赛取消
					gb.setGbType(GlobalConstant.GB_STATUS_4);
				}

				// 比赛现状类型
				gb.setGbTypeName(dictService.getDict(GlobalConstant.GB_STATUS, String.valueOf(gb.getGbType())).getLabel());

				// 转换开始时间
				gb.setStartTime(DateUtils.longToString(Long.parseLong(gb.getStartTime())));
				// 转换结束时间
				if (!StringUtil.isEmpty(gb.getEndTime())) {
					gb.setEndTime(DateUtils.longToString(Long.parseLong(gb.getEndTime())));
				}
			}
		} catch (Exception e) {
			logger.error("根据id查询对战比赛对象出错：", e);
			throw new ServiceException(e);
		}
		return gb;
	}

	/**
	 * @title : 根据条件查询比赛对战信息
	 * @param : String id
	 * */
	public GameBattle find(Map<String, Object> parameter) {
		GameBattle gb = null;
		try {
			gb = gameBattleMapper.find(parameter);
			if (gb != null) {
				// 赛事
				gb.setGevent(gameEventService.findById(gb.getGeId()));
				// 游戏
				gb.setGame(gameService.findById(gb.getGameId()));
				// 主场战队
				gb.setHt(teamService.findById(gb.getHomeTeam()));
				// 客场战队
				gb.setAt(teamService.findById(gb.getAwayTeam()));
			}
		} catch (Exception e) {
			logger.error("根据条件查询对战比赛列表出错：", e);
			throw new ServiceException(e);
		}
		return gb;
	}

	/**
	 * @title : 修改比赛战队信息
	 * @param : GameBattle gb
	 * */
	public int update(GameBattle gb) {
		int i = 0;
		try {
			i = gameBattleMapper.update(gb);
		} catch (Exception e) {
			logger.error(" 修改比赛战队信息出错：", e);
			throw new ServiceException(e);
		}
		return i;
	}

	/**
	 * @title 修改对战 战队下注金额
	 * @param id
	 *            比赛id
	 * @param type
	 *            类别：1.主场战队 2.客场战队
	 * @param gold
	 *            下注金额
	 */
	public void updateGold(String id, String type, Long gold) {
		try {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("id", id);
			if (gold != null) {
				parameter.put("prtGoldNum", gold);
			}
			if (GlobalConstant.JC_TEAM_TYPE_1.equals(type)) {
				parameter.put("prtGoldColumn", "HOME_PRT_GOLD");
			} else {
				parameter.put("prtGoldColumn", "AWAY_PRT_GOLD");
			}
			gameBattleMapper.updateGold(parameter);
		} catch (Exception e) {
			logger.error("修改对战 战队下注金额出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * @title 修改对战 战队下注金额，更换用户下注战队调用
	 */
	@Override
	public void updateGold(GameBattle gameBattle) {
		try {
			gameBattleMapper.updateGbNum(gameBattle);
		} catch (Exception e) {
			logger.error("修改对战 战队下注金额，更换用户下注战队调用出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * @title : 根据游戏ID查询当前游戏对战比赛场次
	 * @param : String gameId
	 * */
	public long countGameBattleNum(String gameId) {
		long num = 0;
		try {
			num = gameBattleMapper.countGameBattleNum(gameId);
		} catch (Exception e) {
			logger.error("游戏ID查询当前游戏对战比赛场次出错：", e);
			throw new ServiceException(e);
		}
		return num;
	}

	@Override
	public int insert(GameBattle t) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setId(IdGen.uuid());
			// 对战创建人
			t.setCreateUser(user.getId());
			t.setCreateDate(System.currentTimeMillis() + "");
			result = super.insert(t);
			if (result == 1) {
				// 新增成功，插入一条默认盘口
				Pankou pankou = new Pankou();
				pankou.setId(IdGen.uuid());
				pankou.setGbId(t.getId());
				pankou.setPankouType(GlobalConstant.PANKOU_TYPE_0);
				pankou.setPkName("独赢");
				pankou.setPkHomeRule("1.95");
				pankou.setPkAwayRule("1.95");
				pankou.setPkStatus(GlobalConstant.DICTTYPE_IS_DELETE_0);
				pankou.setPkStartTime(t.getStartTime());
				pankou.setInningNum("0");
				result = pankouService.insert(pankou);
			}
		} catch (Exception e) {
			logger.error("新增比赛出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IGameBattleMapper gameBattleMapper;

	/**
	 * 游戏 Service定义
	 */
	@Autowired
	private IGameService gameService;

	/**
	 * 游戏赛事 Service定义
	 */
	@Autowired
	private IGameEventService gameEventService;

	/**
	 * 战队 Service定义
	 */
	@Autowired
	private ITeamService teamService;

	/**
	 * 字典 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 用户竞猜 Service定义
	 */
	@Autowired
	private IUserJcService userJcService;

	/**
	 * 用户钱包 Service定义
	 */
	@Autowired
	private IUserWalletService userWalletService;

	/**
	 * 用户增量 Service定义
	 */
	@Autowired
	private UserIncrementService userIncrementService;

	/**
	 * 用户交易记录 Service定义
	 */
	@Autowired
	private IUserTransactionLogService userTransactionLogService;

	/**
	 * 用户消息服务接口 Service定义
	 */
	@Autowired
	private IUserMessageService userMessageService;

	/**
	 * 系统通知服务实现类 Service定义
	 */
	@Autowired
	private ISysNoticeService sysNoticeService;

	/**
	 * 盘口 Service定义
	 */
	@Autowired
	private IPankouService pankouService;

	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(gameBattleMapper);
	}
}
