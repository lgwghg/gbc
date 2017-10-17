/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.CacheConstant;
import com.webside.common.GlobalConstant;
import com.webside.common.redis.listener.SendMessage;
import com.webside.exception.ServiceException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.shiro.cache.ICache;
import com.webside.system.dict.service.DictService;
import com.webside.system.sn.entities.SysNotice;
import com.webside.system.sn.mapper.ISysNoticeMapper;
import com.webside.system.sn.service.ISysNoticeService;
import com.webside.system.sn.vo.NoticeVo;
import com.webside.user.model.UserEntity;
import com.webside.util.IdGen;

/**
 * 系统通知服务实现类
 *
 * @author zengxn
 * @date 2016-11-25 16:27:18
 */
@Service("sysNoticeService")
public class SysNoticeService extends AbstractService<SysNotice, String> implements ISysNoticeService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(sysNoticeMapper);
	}

	/**
	 * 系统通知 DAO定义
	 */
	@Autowired
	private ISysNoticeMapper sysNoticeMapper;
	
	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;
	
	/**
	 * reids 定义
	 */
	@Autowired
	private ICache redisCache;
	
	/**
	 * 消息队列 定义
	 */
	@Autowired
	private SendMessage sendMessage;
	
	/**
	 * 该类用到的常量
	 */
	//消息状态
	private String STATUS_ADD = "1";
	private String STATUS_DEL = "2";
	private String STATUS_UPDATE = "3";
	//系统通知、获胜通知、删除通知
	private String MESSAGE_TYPE_SN = "1";
	private String MESSAGE_TYPE_GB = "2";
	private String MESSAGE_TYPE_DL = "3";
	private String MESSAGE_TYPE_COINFLIP = "5";//翻硬币获胜通知
	
	@Override
	public int insert(SysNotice t) {
		int result = 0;
		try {
			UserEntity user = ShiroAuthenticationManager.getUserEntity();
			t.setId(IdGen.uuid());
			t.setCreateTime((System.currentTimeMillis()+""));
			t.setCreateOperatorId(user.getId());
			t.setCreateOperatorName(user.getNickName());
			result = super.insert(t);
			//新增有效状态的系统通知成功，刷新缓存，推送通知
			if(result==1 && t.getStatus().equals(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1)){
				sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, gottenObject(MESSAGE_TYPE_SN, STATUS_ADD, t.getId(), t.getTitle(),t.getContent()));
				reloadCache();
			}
		} catch (Exception e) {
			logger.error("新增系统通知出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public int update(SysNotice t) {
		int result = 0;
		try {
			result = super.update(t);
			//修改系统通知成功，刷新缓存，推送通知
			if(result==1){
				if(t.getStatus().equals(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1)){
					sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, gottenObject(MESSAGE_TYPE_SN, STATUS_UPDATE, t.getId(), t.getTitle(),t.getContent()));
				}else{
					sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, gottenObject(MESSAGE_TYPE_SN, STATUS_DEL, t.getId(), t.getTitle(),t.getContent()));
				}
				reloadCache();
			}
		} catch (Exception e) {
			logger.error("修改系统通知出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public void insertGameBattleCacheList(List<Map<String, Object>> list) {
		redisCache.del(CacheConstant.SNConstant.SYSTEM_NOTICE_GAME_BATTLE_SET);
		sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, gottenObject(MESSAGE_TYPE_DL, STATUS_ADD, "-1"));
		for (Map<String, Object> map : list) {
			NoticeVo noticeVo = gottenObject(MESSAGE_TYPE_GB, STATUS_ADD, "", map.get("nickName"),map.get("gbName"),map.get("num"));
			redisCache.sAdd(CacheConstant.SNConstant.SYSTEM_NOTICE_GAME_BATTLE_SET, JSONObject.toJSONString(noticeVo));
			sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, noticeVo);
		}
	}
	
	@Override
	public void insertCoinflipWinCacheList(List<Map<String, Object>> list) {
		
		for (Map<String, Object> map : list) {
			NoticeVo noticeVo = gottenObject(MESSAGE_TYPE_COINFLIP, STATUS_ADD, (String)map.get("id"), map.get("nickName"), map.get("num"));
			sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, noticeVo);
			redisCache.lPush(CacheConstant.SNConstant.SYSTEM_NOTICE_COINFLIP_LIST, JSONObject.toJSONString(noticeVo));
		}
		
		List<String> winners = redisCache.lRange(CacheConstant.SNConstant.SYSTEM_NOTICE_COINFLIP_LIST);
		if (winners.size() > 10) {
			redisCache.lTrim(CacheConstant.SNConstant.SYSTEM_NOTICE_COINFLIP_LIST, 10);
			int length = winners.size() -1;
			for (int i= length; i >= 10 ; i--) {
				JSONObject noticeJson = JSONObject.parseObject(winners.get(i));
				sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, gottenObject(MESSAGE_TYPE_DL, STATUS_DEL, (String)noticeJson.get("id")));
			}
		}
		
	}
	
	/**
	 * 刷新系统通知缓存
	 */
	private void reloadCache(){
		redisCache.del(CacheConstant.SNConstant.SYSTEM_NOTICE_SET);
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("status", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
		List<SysNotice> list = sysNoticeMapper.queryListByPage(parameter);
		for (SysNotice t : list) {
			redisCache.sAdd(CacheConstant.SNConstant.SYSTEM_NOTICE_SET, JSONObject.toJSONString(gottenObject(MESSAGE_TYPE_SN, STATUS_ADD, t.getId(), t.getTitle(),t.getContent())));
		}
	}

	/**
	 * 初始化缓存
	 */
	@Override
	public void initCacheList() {
		if(!redisCache.exists(CacheConstant.SNConstant.SYSTEM_NOTICE_SET)){
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("status", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
			List<SysNotice> list = sysNoticeMapper.queryListByPage(parameter);
			for (SysNotice t : list) {
				redisCache.sAdd(CacheConstant.SNConstant.SYSTEM_NOTICE_SET, JSONObject.toJSONString(gottenObject(MESSAGE_TYPE_SN, STATUS_ADD, t.getId(), t.getTitle(),t.getContent())));
			}
		}
	}
	
	/**
	 * 拼装对象
	 * @return
	 */
	private NoticeVo gottenObject(String type,String status,String id,Object ...value){
		NoticeVo noticeVo = new NoticeVo(id==null?"":id, status);
		if(type.equals(MESSAGE_TYPE_SN)){
			noticeVo.setValue("系统通知：《"+value[0]+"》  "+value[1]);
		}else if(type.equals(MESSAGE_TYPE_GB)){
			noticeVo.setValue("恭喜玩家："+value[0]+"在 "+value[1]+" 竞猜成功赢得 金币"+value[2]);
		}else if (type.equals(MESSAGE_TYPE_COINFLIP)) {
			noticeVo.setValue("恭喜玩家："+value[0]+" 在翻硬币游戏中，成功赢得 金币"+value[1]);
		}
		return noticeVo;
	}
	
	@Override
	public List<SysNotice> queryListByPage(Map<String, Object> parameter) {
		List<SysNotice> list = new ArrayList<SysNotice>();
		try {
			list = sysNoticeMapper.queryListByPage(parameter);
			if(list!=null && list.size()>0){
				for (SysNotice sysNotice : list) {
					//有效状态
					sysNotice.setStatusName(dictService.getDict(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(sysNotice.getStatus())).getLabel());
					sysNotice.setStatusClass(dictService.getDict(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(sysNotice.getStatus())).getLabelClass());
				}
			}
		} catch (Exception e) {
			logger.error("查询系统通知出错：", e);
			throw new ServiceException(e);
		}
		return list;
	}
}
