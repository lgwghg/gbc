package com.webside.jc.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.jc.service.IGameBattleService;
import com.webside.match.entities.Game;
import com.webside.match.service.IGameService;
import com.webside.util.CookieUtil;
import com.webside.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/match")
public class MatchCtrl extends BaseController{
	
	/**
	 * 游戏 Service定义
	 */
	@Autowired
	private IGameService gameService;
	
	/**
	 * 比赛对战 Service定义
	 */
	@Autowired
	private IGameBattleService gameBattleService;
	
	@RequestMapping("")
	public ModelAndView match(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String gameEnglishName = CookieUtil.findCookieByName(request, GlobalConstant.COOKIE_GAME_ENGLISH_NAME);
		if(StringUtils.isNotBlank(gameEnglishName)){
			try {
				gameEnglishName = URLDecoder.decode(gameEnglishName,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			List<Game> list = gameService.queryPartList(null);
			if(list!=null&&list.size()>0){
				gameEnglishName = list.get(0).getEnglishName();
			}
		}
        ModelAndView mv = new ModelAndView("redirect:/match/"+gameEnglishName);//默认为forward模式  
        return mv; 
    }  
	
	/**
	 * @title : 对战列表
	 * */
	@RequestMapping("/{gameEnglishName}")
	public String matchGame(@PathVariable String gameEnglishName,Model model,HttpServletRequest request,HttpServletResponse response) {
		if(StringUtils.isBlank(gameEnglishName)){
			String englishName = CookieUtil.findCookieByName(request, GlobalConstant.COOKIE_GAME_ENGLISH_NAME);
			if(StringUtils.isNotBlank(englishName)){
				try {
					gameEnglishName = URLDecoder.decode(englishName,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		Game game = new Game();
		if(StringUtils.isNotBlank(gameEnglishName)){
			game = gameService.findByEnglishName(gameEnglishName, null);
			if(game==null){
				return Common.BACKGROUND_PATH + "/error/error-404";
			}
		}
		
		//判断请求方式
		String pjax = request.getHeader("X-PJAX");
		JSONObject jsonObject = null;
		if(StringUtils.isBlank(pjax) || !pjax.equals("true")){
			//非pjax请求
			List<Game> list = gameService.queryPartList(null);
			JSONArray jsonArray = new JSONArray();
			String TempGameEnglishName = "";
			boolean haveSelected = false;	//是否有选中
			for (int i = 0; i < list.size(); i++) {
				game = list.get(i);
				jsonObject = new JSONObject();
				jsonObject.put("gameId", game.getId());
				jsonObject.put("gameName", game.getGameName());
				jsonObject.put("bgImg", game.getBgImg());
				jsonObject.put("littleImg", game.getLittleImg());
				jsonObject.put("englishName", game.getEnglishName());
				jsonObject.put("gbCount", gameBattleService.countGameBattleNum(game.getId()));
				
				if(StringUtils.isNotBlank(gameEnglishName) && game.getEnglishName().toLowerCase().equals(gameEnglishName.toLowerCase())){
					haveSelected = true;
					jsonObject.put("selected", true);
					model.addAttribute("selectedGame", jsonObject);
				}
				if(i==0){
					TempGameEnglishName = game.getEnglishName();
				}
				jsonArray.add(jsonObject);
			}
			if(!haveSelected){
				model.addAttribute("selectedGame", jsonArray.get(0));
				gameEnglishName = TempGameEnglishName;
			}
			model.addAttribute("gameList", jsonArray);
		}else{
			jsonObject = new JSONObject();
			jsonObject.put("gameId", game.getId());
			jsonObject.put("bgImg", game.getBgImg());
			jsonObject.put("gameName", game.getGameName());
			jsonObject.put("englishName", game.getEnglishName());
			model.addAttribute("selectedGame", jsonObject);
		}
		
		try {
			// 存储游戏英文名称
			CookieUtil.addCookie(response, GlobalConstant.COOKIE_GAME_ENGLISH_NAME, URLEncoder.encode(gameEnglishName,"UTF-8"), 60 * 60 * 24 * 7);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return Common.BACKGROUND_PATH_WEB + "/match/index";
	}
}
