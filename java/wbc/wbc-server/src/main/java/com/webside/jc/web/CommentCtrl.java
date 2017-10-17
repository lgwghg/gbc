package com.webside.jc.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.CacheConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.jc.model.Comment;
import com.webside.jc.service.ICommentService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.shiro.cache.redis.RedisCache;
import com.webside.shiro.cache.redis.VCache;
import com.webside.user.message.service.IUserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.IUserCacheService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/comment/")
public class CommentCtrl extends BaseController{

	/**
	 * 评论 Service定义
	 */
	@Autowired
	private ICommentService commentService;
	
	/**
	 * 用户缓存 Service定义
	 */
	@Autowired
	private IUserCacheService userCacheService;
	
	/**
	 * 用户消息 Service定义
	 */
	@Autowired
	private IUserMessageService userMessageService;
	
	/**
	 * reids 定义
	 */
	@Autowired
	private RedisCache redisCache;
	
	/**
	 * 比赛详情页评论列表
	 * @author zengxn
	 */
	@RequestMapping(value="list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager,String gbId){
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			//1、映射Pager对象
			Pager pager = JSON.parseObject(gridPager, Pager.class);
			
			if(StringUtils.isNotBlank(gbId)){
				parameters.put("gbId", gbId);
				parameters.put("start", (pager.getNowPage()-1)*pager.getPageSize());
				parameters.put("pageSize", pager.getPageSize());
				
				JSONArray jsonArray = commentService.queryPartListByPage(parameters);
				parameters.clear();
				parameters.put("isSuccess", Boolean.TRUE);
				parameters.put("nowPage", pager.getNowPage());
				parameters.put("list", jsonArray);
			}else{
				parameters.put("isSuccess", Boolean.FALSE);
			}
		} catch (Exception e) {
			parameters.put("isSuccess", Boolean.FALSE);
			logger.error("ajax获取比赛详情页评论列表数据出错：", e);
		}
		return parameters;
	}
	
	/**
	 * 发表评论
	 * @author zengxn
	 */
	@RequestMapping(value="add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(String gbId,String content,String pId,String gbName, String typeId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String type = "评论";
			if(StringUtils.isNotBlank(gbId) && StringUtils.isNotBlank(content)){
				UserEntity user = ShiroAuthenticationManager.getUserEntity();
				//判断上次发表评论是否大于3秒
				if(redisCache.exists(CacheConstant.COMMENT_CODE_INFO+user.getId())){
					map.put("success", Boolean.FALSE);
					map.put("message", "亲~太勤了哟，休息一下吧。");
				}else{
					Comment comment = new Comment();
					comment.setGbId(gbId);
					comment.setContent(replaceSensitiveWord(content, "*"));
					comment.setId(IdGen.uuid());
					comment.setUserId(user.getId());
					if(StringUtils.isNotBlank(pId)){
						comment.setPcommentId(pId);
						type = "回复";
					}
					int result = commentService.insert(comment);
					if(result == 1){
						if(StringUtils.isNotBlank(pId)){
							//插入用户消息
							Comment findComment = commentService.findById(pId);
							if(findComment!=null && StringUtils.isNotBlank(findComment.getUserId())){
								userMessageService.addMessageForComment(findComment.getUserId(), comment.getId(), content, user.getNickName(), gbId, gbName, typeId);
							}
						}
						
						map.put("success", Boolean.TRUE);
						map.put("message", type+"成功");
						map.put("content", comment.getContent());
						map.put("id", comment.getId());
						UserEntity userEntity = userCacheService.getUserEntityByUserId(ShiroAuthenticationManager.getUserId());
						JSONObject userObject = new JSONObject();
						if(StringUtils.isNotBlank(pId)){
							userObject.put("userPhoto_35", userEntity.getPhoto_35());
						}else{
							userObject.put("userPhoto_65", userEntity.getPhoto_65());
						}
						userObject.put("nickName", userEntity.getNickName());
						userObject.put("rankLevel", userEntity.getRankLevel());
						map.put("user", userObject);
						//缓存插入当前用户评论记录
						redisCache.setEx(CacheConstant.COMMENT_CODE_INFO+user.getId(), System.currentTimeMillis()+"", 3);
					}else{
						map.put("success", Boolean.FALSE);
						map.put("message", type+"失败");
					}
				}
			}else{
				map.put("success", Boolean.FALSE);
				map.put("message", type+"失败，参数有误");
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "操作失败");
			logger.error("ajax发表评论出错：", e);
		}
		return map;
	}
	
	/**
	 * 撤回评论
	 * @author zengxn
	 */
	@RequestMapping(value="revoke", method = RequestMethod.POST)
	@ResponseBody
	public Object revoke(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			long nowTimeMillis = System.currentTimeMillis();
			if(StringUtils.isNotBlank(id)){
				String userId = ShiroAuthenticationManager.getUserId();
				Comment comment = commentService.findById(id);
				if(StringUtils.isNotBlank(userId) && comment!=null && StringUtils.isNotBlank(comment.getId()) && id.equals(comment.getId())){
					if(nowTimeMillis - StringUtils.toLong(comment.getCreateTime()).longValue() <= 2*60*1000){
						//判断是否父级评论
						if (StringUtils.isBlank(comment.getPcommentId())) {
							//是父级 先删除所有子集 再删自身
							commentService.deleteByPid(id);
							commentService.deleteById(id);
						} else {
							//子级
							commentService.deleteById(id);
						}
						map.put("success", Boolean.TRUE);
						map.put("message", "撤回成功");
					}else{
						map.put("success", Boolean.FALSE);
						map.put("type", "1");
						map.put("message", "撤回失败，已超过2分钟不可撤回");
					}
				}else{
					map.put("success", Boolean.FALSE);
					map.put("message", "撤回操作失败，请联系客服");
				}
			}else{
				map.put("success", Boolean.FALSE);
				map.put("message", "撤回失败，参数有误");
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "操作失败");
			logger.error("ajax撤回评论出错：", e);
		}
		return map;
	}
	
    /**
     * 获取文字中的敏感词 
     * @param txt
     * @param swMap
     * @return
     */
    public Set<String> getSensitiveWord(String txt,Map swMap) {  
        Set<String> sensitiveWordList = new HashSet<String>();  
  
        for (int i = 0; i < txt.length(); i++) {  
  
            // 判断是否包含敏感字符  
            int length = CheckSensitiveWord(txt, i,swMap);  
  
            // 存在,加入list中  
            if (length > 0) {  
                sensitiveWordList.add(txt.substring(i, i + length));  
  
                // 减1的原因，是因为for会自增  
                i = i + length - 1;  
            }  
        }  
  
        return sensitiveWordList;  
    }  
  
    /**
     * 替换敏感字字符   
     * @param txt
     * @param replaceChar
     * @return
     */
    public String replaceSensitiveWord(String txt, String replaceChar) {  
    	Map nowMap = VCache.get(CacheConstant.SYSTEM_SENSITIVEWORDS_SET, Map.class);
		
        String resultTxt = txt;  
  
        // 获取所有的敏感词  
        Set<String> set = getSensitiveWord(txt,nowMap);  
        Iterator<String> iterator = set.iterator();  
        String word = null;  
        String replaceString = null;  
        while (iterator.hasNext()) {  
            word = iterator.next();  
            replaceString = getReplaceChars(replaceChar, word.length());  
            resultTxt = resultTxt.replaceAll(word, replaceString);  
        }  
  
        return resultTxt;  
    }  
  
    /**
     * 获取替换字符串 
     * @param replaceChar
     * @param length
     * @return
     */
    private String getReplaceChars(String replaceChar, int length) {  
        String resultReplace = replaceChar;  
        for (int i = 1; i < length; i++) {  
            resultReplace += replaceChar;  
        }  
  
        return resultReplace;  
    }
	
	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：
     * 如果存在，则返回敏感词字符的长度，不存在返回0
	 * @param txt
	 * @param beginIndex
	 * @param swMap
	 * @return
	 */
    public int CheckSensitiveWord(String txt, int beginIndex,Map swMap) {  
  
        // 敏感词结束标识位：用于敏感词只有1位的情况  
        boolean flag = false;  
  
        // 匹配标识数默认为0  
        int matchFlag = 0;  
        
        for (int i = beginIndex; i < txt.length(); i++) {  
            char word = txt.charAt(i);  
  
            // 获取指定key  
            swMap = (Map) swMap.get(word);  
  
            // 存在，则判断是否为最后一个  
            if (swMap != null) {  
  
                // 找到相应key，匹配标识+1  
                matchFlag++;  
  
                // 如果为最后一个匹配规则,结束循环，返回匹配标识数  
                if ("1".equals(swMap.get("e"))) {  
                    // 结束标志位为true  
                    flag = true;  
                }  
            }  
  
            // 不存在，直接返回  
            else {  
                break;  
            }  
        }  
  
        // 长度必须大于等于1，为词  
        if (matchFlag < 2 || !flag) {  
            matchFlag = 0;  
        }  
        return matchFlag;  
    }  
}
