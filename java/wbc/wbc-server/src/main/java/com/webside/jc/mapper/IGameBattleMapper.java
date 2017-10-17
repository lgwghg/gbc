package com.webside.jc.mapper;

import java.util.List;
import java.util.Map;

import com.webside.base.basemapper.BaseMapper;
import com.webside.jc.model.GameBattle;
import com.webside.jc.vo.GameBattleVo;

/**
 * @title : 对战比赛数据库接口
 * */
public interface IGameBattleMapper extends BaseMapper<GameBattle, String>{
	
	/** lwh
	 * 新网站比赛列表页面
	 * @param parameter
	 * @return
	 */
	public List<GameBattleVo> queryGbDetailsListByPage(Map<String, Object> parameter);

	/**
	 *  网站比赛列表页面
	 * */
	public List<GameBattle> webQueryListByPage(Map<String, Object> parameter);
	
	/**
	 * @title 修改对战 战队下注金额
	 * @param String prtGoldColumn (主战队下注金额  和 副战队下注金额 字段名称)
	 * @param int  prtGoldNum （金额数量）
	 */
	public void updateGold(Map<String, Object> parameter);
	
	/**
	 * @title 修改对战 战队下注金额，更换用户下注战队调用
	 */
	public void updateGbNum(GameBattle gameBattle);
	
	/**
	 * @title : 根据游戏ID查询当前游戏对战比赛场次
	 * @param : String gameId
	 * */
	public long countGameBattleNum(String gameId);
}
