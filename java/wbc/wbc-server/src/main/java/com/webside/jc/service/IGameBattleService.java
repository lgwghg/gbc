package com.webside.jc.service;

import java.util.List;
import java.util.Map;

import com.webside.jc.model.GameBattle;
import com.webside.jc.vo.GameBattleVo;


/**
 * @title : 比赛对战业务接口
 * @author LiGan
 * */

public interface IGameBattleService {
	
	/**
	 * lwh
	 * 获取比赛详情数据
	 * @param parameter
	 * @return
	 */
	public List<GameBattleVo> queryGbDetailsListByPage(Map<String, Object> parameter);
	
	/**
	 *  网站比赛列表页面
	 * */
	public List<GameBattle> webQueryListByPage(Map<String, Object> parameter);
	
	/**
	 * @title : 根据条件查询对战比赛列表
	 * @param : String gameId  游戏ID
	 * @param : String geId 赛事ID
	 * @param : String gbStatus 状态
	 * @param：    String teamName 战队名称
	 * @param : String 
	 * */
	public List<GameBattle> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * @title : 新增比赛对战记录
	 * @param : GameBattle gb
	 * */
	public int insert(GameBattle gb);
	
	/**
	 * @title : 修改比赛战队信息
	 * @param : GameBattle gb
	 * */
	public int update(GameBattle gb);
	
	/**
	 * @title :  根据ID查询比赛对战信息
	 * @param :  String id
	 * */
	public GameBattle findById(String id);
	
	/**
	 * @title :  根据条件查询比赛对战信息
	 * @param :  String id
	 * */
	public GameBattle find(Map<String, Object> parameter);
	
	/**
	 * @title 修改对战 战队下注金额
	 * @param id 比赛id
	 * @param type 类别：1.主场战队 2.客场战队
	 * @param gold 下注金额
	 */
	public void updateGold(String id,String type,Long gold);
	
	/**
	 * @title 修改对战 战队下注金额，更换用户下注战队调用
	 */
	public void updateGold(GameBattle gameBattle);
	
	/**
	 * @title : 根据游戏ID查询当前游戏对战比赛场次
	 * @param : String gameId
	 * */
	public long countGameBattleNum(String gameId);
	
	/**
	 * @title : 根据赛季ID查询最新一条对战比赛信息
	 * @param : String geId
	 * */
	/*public GameBattle getNewestGameBattle(String geId);*/
}
