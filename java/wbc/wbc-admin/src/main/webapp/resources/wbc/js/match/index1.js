//游戏id
var gameId;
//游戏英文名称
var englishName;
//赛事id
var gameEventId;
//战队搜索
var teamName;
//赛事分页信息
var pageSize_ge = 5; //每页显示条数
var startRecord_ge =0;	//开始记录数
var nowPage_ge = 1;  //当前页
var pageCount_ge = -1;	//总页数
var recordCount_ge =-1;	//总记录数

//比赛对战现状 
var gbstate;
var toppkId = "";   //顶部列表
var topteamType = "";	//用于记录用户点击顶部列表战队

var teamType = "";
var gbId = "";
var pkId = "";
var rule = 0;
//对战分页信息
var pageSize_gb = 10; //每页显示条数
var startRecord_gb =0;	//开始记录数
var nowPage_gb = 1;  //当前页
var pageCount_gb = -1;	//总页数
var recordCount_gb =-1;	//总记录数
var game_startTime_id = [];
//最低G币
var minGold = 100;
$(function(){
	gameId = $("#gameId").val();
	englishName = $("#englishName").val();
	
	//pjax
	$(document).pjax('#game-ul a', '#container',{timeout: 6000});
	$(document).on(
		'pjax:send', 
		function() { 
			NProgress.start();
		}
	);
	$(document).on(
		'pjax:complete', 
		function() { 
			NProgress.done();
		}
	);
	//战队搜索
	$("#teamSearch").click(function(){
		teamName = $("#teamName").val();
		if(trim(teamName)!=""){
			//加载比赛对战列表
		 	loadGameBattleList(1);
		}
		
	});
	//游戏状态选项卡（结束/未开始）
	$(".middle-tab").delegate("li a", "click", function () {
		$(".middle-tab li").removeClass("middle-tab-border");
	 	$(this).parent().addClass("middle-tab-border");
	 	$("#nobet,#onUser").addClass("hide");
	 	$("#nobet,#onUser").appendTo($("#new-container"));
	 	gbstate = $(this).attr("gbstate");
		loadGameBattleList(1);
	})
	var timer1,timer2,timer3;
	
	// 下拉展开选项卡
	$(".middle-tab span").on("click", function () {
		if($(".match-list").hasClass("x-big-screen") == true){
			clearTimeout(timer2);
			clearTimeout(timer3);
			$(".match-list").removeClass("x-big-screen x-big-screen-a");
			$(".list-main-right .view1 .three").addClass("hide");
			$(".list-main-right .view1").css("cursor","");
			$(".middle-tab,.match-list,#moreGb").animate({
				width:'600px'
			},300);
			timer1 = setTimeout(function(){
				$(".list-main-right .view1").siblings(). slideToggle(300);
				$(".middle-tab .zhankai").removeClass("hide");
				$(".middle-tab .shouqi").addClass("hide");
	        },300);
		} else {
			clearTimeout(timer1);
			$(".match-list").addClass("x-big-screen");
			$(".list-main-right .view1 .three").removeClass("hide");
			$(".list-main-right .view1").css("cursor","pointer");
			$(".list-main-right .view1").siblings(). slideToggle(300);
			timer2 = setTimeout(function(){
				$(".middle-tab,.match-list,#moreGb").animate({
					width:'940px'
				},300);
				$(".middle-tab .zhankai").addClass("hide");
				$(".middle-tab .shouqi").removeClass("hide");
	        },300);
			timer3 = setTimeout(function(){
				$(".match-list").addClass("x-big-screen-a");
			},600);
		}
	})
	
	// 右侧 最近赛事
	$(".list-main-right .view1").on("click", function () {
		if($(".list-main-right .view1 .three").hasClass("hide") != true){
			clearTimeout(timer2);
			clearTimeout(timer3);
			$(".match-list").removeClass("x-big-screen x-big-screen-a");
			$(".list-main-right .view1 .three").addClass("hide");
			$(".list-main-right .view1").css("cursor","");
			$(".middle-tab,.match-list").animate({
				width:'600px'
			},300);
			timer1 = setTimeout(function(){
				$(".list-main-right .view1").siblings(). slideToggle(300);
				$(".middle-tab .zhankai").removeClass("hide");
				$(".middle-tab .shouqi").addClass("hide");
	        },300);
		}
	})
	
	//左侧 点击游戏
	$("#game-ul").delegate("a", "click", function () {
	 	//设置选中样式
		$(".list-main-left li").removeClass("checked");
	 	$(this).find("li").addClass("checked");
	 	
	 	//点击游戏初始化比赛现状
	 	$(".middle-tab li").removeClass("middle-tab-border");
	 	$(".middle-tab li:first").addClass("middle-tab-border");
	 	
	 	//重置值
	 	gbstate = "";
	 	gameEventId = "";
	 	teamName = "";
	});
	
	//加载赛事
	loadGameEventList(true);
	
	//加载比赛对战列表
	loadGameBattleList(1);
});

//加载赛事列表(右侧)
function loadGameEventList(first){
	if(first){
		nowPage_ge = 1;
		pageCount_ge = -1;
	}
	//分页
	var pagesJson = pages(pageSize_ge,startRecord_ge,nowPage_ge,recordCount_ge,pageCount_ge);
	$.ajax({
		type : "POST",
		url : _path + "/gameEvent/gbGameEventlist",
		data:{   
			"gridPager":pagesJson,
			"gameId":gameId,
		},
		dataType : "json",
		success : function(resultdata) {
			if (resultdata.isSuccess) {
				nowPage_ge = resultdata.nowPage+1;
				pageCount_ge = resultdata.pageCount;
				var data = resultdata.list;
				var gameEvent;
				var content = "";
				if(first){
					$("#gameEvent-ul").html("");
				}
				if(data.length>0){
					for(var i = 0;i<data.length;i++){
						gameEvent = data[i];
						content += '<li gameEventId="'+gameEvent.eventId+'">';
						if(gameEvent.eventImg==null || gameEvent.eventImg==''){
							if(englishName.toLowerCase()=='ow'){
								content += '<img src="'+_staticPrefix+'/images/ow320x100.jpg">';
							}else if(englishName.toLowerCase()=='lol'){
								content += '<img src="'+_staticPrefix+'/images/lol320x100.jpg">';
							}else if(englishName.toLowerCase()=='gk'){
								content += '<img src="'+_staticPrefix+'/images/gk320x100.jpg">';
							}else if(englishName.toLowerCase()=='dota2'){
								content += '<img src="'+_staticPrefix+'/images/dota2320x100.jpg">';
							}
						}else{
							content += '<img src="'+gameEvent.eventImg+'">';
						}
						content += '<p class="one hide">'+gameEvent.eventName+'</p>';
						content += '<span class="line-1"></span>';
						content += '<span class="line-2"></span>';
						content += '<span class="line-3"></span>';
						content += '<span class="line-4"></span>';
						content += '</li>';
					}
					$("#gameEvent-ul").append(content);
					if(nowPage_ge<=pageCount_ge){
						$("#gameEvent-p").html('<a href="javascript:loadGameEventList()">更多赛事</a>');
					}else{
						$("#gameEvent-p").html('<a href="javascript:void(0)">没有更多了</a>');
					}
				}else{
					$("#gameEvent-p").html('<a href="javascript:void(0)">没有更多了</a>');
				}
				//选中赛事
				$(".list-main-right").delegate(".view2 li", "click", function () {
					gameEventId = $(this).attr("gameEventId");
					if($(this).hasClass("checked") == true){
						$(".list-main-right .view2 li span").css({
					 		width:'',
					 		height:''
					 	})
					 	$(this).find(".line-1,.line-3").animate({
			            	width:'100%'
			        	},400);
				        $(this).find(".line-2,.line-4").animate({
				            height:'100%'
				        },400);
					} else {
						$(".list-main-right .view2 li").siblings(".checked").find(".line-1,.line-3").animate({
			            	width:'0%'
			        	},400);
						$(".list-main-right .view2 li").siblings(".checked").find(".line-2,.line-4").animate({
							height:'0%'
			        	},400);
						$(".list-main-right .view2 li").removeClass("checked");
					 	$(this).addClass("checked");
					 	$(this).find(".line-1,.line-3").animate({
			            	width:'100%'
			        	},400);
				        $(this).find(".line-2,.line-4").animate({
				            height:'100%'
				        },400);
					}
					$("#nobet,#onUser").addClass("hide");
				 	$("#nobet,#onUser").appendTo($("#new-container"));
				 	//加载比赛对战列表
				 	loadGameBattleList(1);
				})
				//最近赛事鼠标经过效果
				$(".list-main-right .view2 li").mouseenter(function () {
			        $(this).find(".one").removeClass("hide").animate({
			        	width: '320px',
			        	height: '100px',
			        	lineHeight: '100px',
			        	fontSize:'22px',
			        	left: '0',
			        	top: '0'
			        },100);
			        $(this).find("img").animate({
			            width: '110%',
			        	height: '110%',
			        	top: '-5%',
			        	left: '-5%'
			        },100);
			        
			    }).mouseleave(function () {
			    	$(this).find(".one").addClass("hide").animate({
			        	width: '0',
			        	height: '0',
			        	lineHeight: '0',
			        	fontSize:'0',
			        	left: '50%',
			        	top: '50%'
			        },100);
			    	$(this).find("img").animate({
			            width: '100%',
			        	height: '100%',
			        	top: '',
			        	left: '',
			        },100);
			    })
			}
		}
	});
}

// 加载比赛对战列表
function loadGameBattleList(flag){
	if(flag == 1){
		$("#gameBattleList").html('<p class="load-more-now"><img src="'+_staticPrefix+'/images/load_more_new.gif"><span>正在加载...</span></p>');
		nowPage_gb = 1;
		pageCount_gb = -1;
		
	}
	var pagesJson = pages(pageSize_gb,startRecord_gb,nowPage_gb,recordCount_gb,pageCount_gb);
	$.ajax({
		type : "POST",
		url : _path + "/gb/getPanKouList",
		data:{   
			"gridPager":pagesJson,
			"gameId":gameId,
			"geId":gameEventId,
			"gbstate":gbstate,
			"teamName":teamName
		},
		dataType : "json",
		success : function(result) {
			$("#moreGb").html("");
			if (result.isSuccess) {
				var gbList = result.gbList;
				if(gbList!=null && gbList.length>0){
					var html = '';
					var date = new Date();
					var logged = false;
					if(user!=null && user!=""){
						logged = true;
					}
					for(var i =0;i<gbList.length;i++){
						var pankous = gbList[i].pankous;
						html += '<li class="match-list-li" id="match-list-li'+gbList[i].id+'">';
						html += InfoPankouBuild(gbList[i],pankous[0],logged,date);// 刚进页面展示
						html += openTopBuild(gbList[i],pankous,logged,date);// 影藏的其他盘口
						html += '</li>';
					}
					if(flag ==1){
						$("#gameBattleList").html("");
					}
					
					//console.log(html);
					$("#gameBattleList").append(html);
					//当前页
					nowPage_gb = result.nowPage+1;
					//总页数
					pageCount_gb = result.pageCount;
					
					// 鼠标滑过动态效果
					mouseover();
					// 点击盘口战队
					pkTeamClick();
					// 再次下注样式切换
					secondJcClass();
					// 切换队伍
					changeTeam();
					// 新玩法 + 再次下注
					newSecondJcClass();
					// 新玩法 + 切换战队
					newChangeTeam();
					// 开启监听
					addSocket();
				}else{
					$(".load-more[id!='gameEvent-p']").hide();
					$("#gameBattleList").html('<p class="load-more">暂无比赛</p>')
				}
			}
			
		},error : function(errorMsg) {},
		complete:function(errorMsg) {}
	});
}

// 下注 
function userJc(top) {
	var jcGold = $("#nobet .ex8").slider('getValue');
	//当前用户G币
    if(jcGold<=(_userGold+_sysGold) && jcGold>=minGold){
    	var _pkId = "";
    	var _teamType = "";
    	if(top) {
    		_pkId = toppkId;
    		_teamType = topteamType;
    	} else {
    		_pkId = pkId;
    		_teamType = teamType;
    	}
    	var pkujcteam = $("#pk_ul_class"+_pkId).attr("pkujcteam");
    	if(judge(pkujcteam)) {// 已下注
    		_teamType = pkujcteam;
    	}
    	
    	$.ajax({
    		type : "POST",
    		url : _path + "/userJc/addUserJc",
    		data:{   
    			"pkId":_pkId,
    			"gold":jcGold,
    			"teamType":_teamType,
    			"betMode": "1" // 下注
    		},
    		dataType : "json",
    		success : function(result) {
    			if (result.isSuccess) {
    				var pankouVo = result.pankouVo;
    				var userJcInfo = pankouVo.userJc;
    				//修改页面用户G币
    				updateUserGold(2,-jcGold);
    				//修改拖拽条
    				exchangeEx8();
    				
    				if(top) {
    					refreshTopJcClass(pankouVo, userJcInfo);
    				} else {
    					refreshPkClass(_teamType, pankouVo);
    				}
    				
    				// G币赋值
    				var userJcGoldNum = userJcInfo.jcGold;
    				$("#userJcGold"+_pkId).html(userJcGoldNum);
    				if(userJcInfo.jcTeamType == '1') {
    					$("#two"+_pkId).attr("rule", pankouVo.pkHomeRule);
    					$("#userJcGoldEstimate"+_pkId).html(accSub(mul(userJcGoldNum,pankouVo.pkHomeRule),userJcGoldNum));
    				} else {
    					$("#two"+_pkId).attr("rule", pankouVo.pkAwayRule);
    					$("#userJcGoldEstimate"+_pkId).html(accSub(mul(userJcGoldNum,pankouVo.pkAwayRule),userJcGoldNum));
    				}
    				// 修改下注战队
    				$("#pk_ul_class"+_pkId).attr("pkujcteam",_teamType);
    			}else{
    				layer.alert(result.message, {icon: 5,shift: 6,time: 2000});
    			}
    		}
    	});
    }else{
    	layer.confirm('您的金币不足，请先充值', {icon: 3, title:'充值提醒'}, function(index){
    		if(index > 0) {
    			$("#headerRecharge").click();
    		}
    		layer.close(index);
    	});
    }
}

// 下注成功之后，刷新头部竞猜列表，如 下注金额
function refreshTopJcClass(pankouVo, userJcInfo) {
	//隐藏下注div
	$("#nobet").addClass("hide");
	$("#nobet").appendTo($("#new-container"));
 	
	//判断当前用户是否已经下注过
	if(judge($("#pk_ul_class"+toppkId).attr("pkujcteam"))) {
		// 显示用户下注div
		$("#match-list-bottom-pour"+toppkId).removeClass("hide");
		// 分享二维码
		showPrcode(pankouVo.gbId, pankouVo.id);
	} else {
		//更改下注百分比
		$("#pkHomePrtNum"+toppkId).html(countScale(pankouVo.pkHomePrt,pankouVo.pkHomePrt+pankouVo.pkAwayPrt));
		$("#pkAwayPrtNum"+toppkId).html(countScale(pankouVo.pkAwayPrt,pankouVo.pkHomePrt+pankouVo.pkAwayPrt));
		//用户没有下注过 所以 用户下注div 并没有在页面加载过
		var battleObj = {};
		var ht = {};
		ht.teamName = $("#pk_HomeTeam_ul"+toppkId).attr("homeTeamName");
		var at = {};
		at.teamName = $("#pk_AwayTeam_ul"+toppkId).attr("awayTeamName");
		battleObj.ht = ht;
		battleObj.at = at;
		var html = '<div class="match-list-bottom-pour" id="match-list-bottom-pour'+toppkId+'">';
		html += bettingBuild(battleObj, pankouVo, true);
		html = html + '</div>';
		$("#pk_ul_class"+toppkId).after(html);
		//点击再下注
		secondJcClass();
		// 点击切换投注队伍
		changeTeam();
	}
	$(".match-list-message[top='1']").removeAttr("pkujcteamnow");
}

// 分享页
function showPrcode(gbId, pkId) {
	// 判断是否需要新加二维码
	if ($("#match-list-bottom-pour"+pkId + " .ewmwrap").length <= 0) {
		//是否需要分享
		$.ajax({
			 type : "POST",
			 url : _path + "/share/jc/showPrcode",
			 data: {
				 "userId":userId,
				 "gbId": gbId
			 },
			 async : false,
			 dataType : "json",
			 success : function(result) {
				 if (result.isShow == 1) {
					 var jcId = result.jcId;
					 var hideHtml = '';
					 hideHtml = hideHtml + ' <div class="ewmwrap" userJcId="'+jcId+'" onClick="ewmwrapAddEvent(\'' + jcId + '\')"><img class="erweima" src="' + _staticPrefix + '/images/erweima.gif" alt=""/><img class="yuanjiao" src="' + _staticPrefix + '/images/yuanjiao.png" alt=""/></div>';
					 hideHtml = hideHtml + '<div class="qrcodewrap qrcode_' + jcId + '"><div class="qrcode" id="qrcode_' + jcId + '"></div><p style="color: #31c3a2;"><i class="iconfont icon-saoyisao"></i>打开微信扫一扫点击分享送<span class="egbg"></span>币</p></div>';
					 $("#match-list-bottom-pour"+pkId).append(hideHtml);
				 }
			 }
		});
	}
}

// 全部展开
function ewmwrapAddEvent(userJcId) {
	if(user!="" && userJcId!=""){
		$(".qrcode_"+userJcId).slideToggle();
		if (ip != null) {
			if (ip.charAt(ip.length-1) != "/") {
				ip = ip + '/';
			}
		}
		var text = ip+"share/jc?j="+userJcId; //uid="+userId+"&
		$("#qrcode_" + userJcId).html("").qrcode({
	        render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
	        text : text,    //扫描二维码后显示的内容,可以直接填一个网址，扫描二维码后自动跳向该链接
			width : "150",               //二维码的宽度
	        height : "150",              //二维码的高度
	        background : "#ffffff",       //二维码的后景色
			foreground : "#000000",    //二维码的前景色
	        src: _staticPrefix + '/images/logo_code.png'             //二维码中间的图片
		});
	}
}

function refreshPkClass(teamType, pankouVo) {// 
	$("#new_play_nobet").addClass("hide");
	
	//判断当前用户是否已经下注过
	if(judge($("#pk_ul_class"+pkId).attr("pkujcteam"))) {
		// 显示下注列表
		$("#match-list-bottom-pour-new"+pkId).removeClass("hide");
	} else {
		var battleObj = {};
		var ht = {};
		ht.teamName = $("#pk_HomeTeam_ul"+pkId).attr("homeTeamName");
		var at = {};
		at.teamName = $("#pk_AwayTeam_ul"+pkId).attr("awayTeamName");
		battleObj.ht = ht;
		battleObj.at = at;
		// 新增下注列表
		var html = '<div class="match-list-bottom-pour-new" id="match-list-bottom-pour-new'+pkId+'">';
		html += bettingBuild(battleObj, pankouVo, false);
		html = html + '</div>';
		if(teamType == "1") {// 主队
			$("#pk_HomeTeam"+pkId).append(html);
		} else {
			$("#pk_AwayTeam"+pkId).append(html);
		}
		//点击再下注
		newSecondJcClass();
		// 点击切换投注队伍
		changeTeam();
	}
	
	if(teamType == "1") {// 主队
		$("#pk_HomeTeam"+pkId).addClass("bg_31").removeClass("bg_2f");
	} else {
		$("#pk_AwayTeam"+pkId).addClass("bg_31").removeClass("bg_2f");
		$("#pk_AwayTeam_p"+pkId).addClass("bg_31").removeClass("bg_2f");
	}
}

/******************************  拼接页面   ***********************************************/

/**
 * 用于拼接比赛首个盘口未展开样式 获取外P层加ul层
 * battleObj比赛对象
 * pankouObj盘口对象
 */
function InfoPankouBuild(battleObj,pankouObj,logged,date) {
	var buildHtmlStr = topStatePankouBuild(battleObj,pankouObj,date,true);
	buildHtmlStr += infoBuild(battleObj,pankouObj,true);
	
	return buildHtmlStr;
}

/**
 * lwh 用于拼接盘口顶部状态条 获取P层
 * battleObj比赛对象
 * pankouObj盘口对象
 * date 当前时间
 */
function topStatePankouBuild(battleObj,pankouObj,date,top){
	var buildHtmlStr = '';
	if(top){
		buildHtmlStr = '<p class="match-list-top">';
		buildHtmlStr += '<a href="'+_path+'/gb/'+pankouObj.gbId+'">';
		buildHtmlStr += '<span class="fl"><img src="'+battleObj.game.littleImg+'">'+battleObj.gevent.eventName+'</span>';
		buildHtmlStr += '<span class="fl view1">'+pankouPlay(pankouObj,battleObj.ht.teamName,battleObj.at.teamName)+'</span>';
		buildHtmlStr += stateBuild(battleObj,pankouObj,date);
		buildHtmlStr += '</a></p>';
	} else {
		buildHtmlStr = '<p class="match-list-more-play-one">';
		buildHtmlStr += '<span class="view1">'+pankouPlay(pankouObj,battleObj.ht.teamName,battleObj.at.teamName)+'</span>';
		buildHtmlStr += stateBuild(battleObj,pankouObj,date);
		buildHtmlStr += '</p>';
	}
	return buildHtmlStr;
}

/**
 * lwh 用于拼接盘口当前状态
 * battleObj比赛对象
 * pankouObj盘口对象
 * date当前时间
 */
function stateBuild(battleObj,pankouObj,date){
	var buildHtmlStr = "";
	if(pankouObj.gbstate=="0"||pankouObj.gbstate=="1"){// 未开始 或进行中
		if(pankouObj.gbstate=="1"){
			buildHtmlStr += '<span class="match-list-live">LIVE</span>';
			buildHtmlStr += '<span class="fr co_31">进行中';
		}else{
			buildHtmlStr += '<span class="fr co_31">'+getTimeDifference(date.getTime(),pankouObj.pkStartTime);;
		}
	}else{// 已结束或已取消
		buildHtmlStr += '<span class="fr co_88">';
		if(pankouObj.gbstate=="2"){
			buildHtmlStr += '已结束';
		}else{
			buildHtmlStr += '已取消';
		}
	}
	buildHtmlStr += '</span>';
	return buildHtmlStr;
}

/**
 * lwh 用于拼接盘口信息
 * battleObj比赛对象
 * pankouObj盘口对象
 * top是否为比赛首个盘口
 */
function infoBuild(battleObj,pankouObj,top){
	var buildHtmlStr = "";
	// 加入状态(0未开始，1进行中，2已结束，3已取消)、 主客战队 
	var pkId = pankouObj.id;
	var pkstate = pankouObj.gbstate;
	var pkujcteam = "";
	if(judge(pankouObj.userJc)) {
		pkujcteam = pankouObj.userJc.jcTeamType;
	}
	if(top){
		buildHtmlStr += '<ul class="match-list-message" id="pk_ul_class'+pankouObj.id+'" top="1" pkstate="'+pkstate+'" pkujcteam="'+pkujcteam+'" pkId="'+pkId+'" battleId="'+battleObj.id+'">';
		buildHtmlStr += '<li class="middle">VS</li>';
	}else{
		buildHtmlStr += '<ul class="match-list-message" id="pk_ul_class'+pankouObj.id+'" top="2" pkstate="'+pkstate+'" pkujcteam="'+pkujcteam+'" pkId="'+pkId+'" battleId="'+battleObj.id+'">';
		if(judge(pankouObj.userJc)) { // 用户已下注
			if(pankouObj.gbstate=="0") {	// 比赛未开始
				buildHtmlStr += '<img src="'+_staticPrefix+'/images/more_play_shadow.png" class="top-shadow">';
				buildHtmlStr += '<li class="middle hide" style="top: 45px;" id="li_middle'+pankouObj.id+'">VS</li>';
				//切换战队
				buildHtmlStr += '<img src="'+_staticPrefix+'/images/change_team.png" class="middle-change-team" style="top: 45px;" id="change_'+pkId+'">';
			} else{	
				buildHtmlStr += '<img src="'+_staticPrefix+'/images/more_play_shadow.png" class="top-shadow">';
				buildHtmlStr += '<li class="middle" style="top: 45px;" id="li_middle'+pankouObj.id+'">VS</li>';
			}
		} else {
			buildHtmlStr += '<li class="middle" id="li_middle'+pankouObj.id+'">VS</li>';
		}
	}
	//处理盘口列表中 用户下注信息
	var panKouXiaZhuHtml = "";
	var homeTeamUl = "";
	var awayTeamUl = "";
	var teamClassLeft = "message-left";	//主战队class
	var teamClassRight = "message-right"; //客场战队class
	var teamClassRightP = "message-right-a"; //客场战队P标签 class 
	if(judge(pankouObj.userJc)) {	//用户已下注
		if(pkstate != "3"){	//比赛未开始
			panKouXiaZhuHtml += '<div class="match-list-bottom-pour-new" id="match-list-bottom-pour-new'+pankouObj.id+'">';
			panKouXiaZhuHtml += bettingBuild(battleObj, pankouObj, top);
			panKouXiaZhuHtml += '</div>';
		}
		if(!top) {
			if(pankouObj.userJc.jcTeamType == "1") {// 主队
				if(pkstate == "2") {// 已结束
					teamClassLeft += " bg_2f";
				} else if(pkstate != "3"){
					teamClassLeft += " bg_31";
				}
				if(pkstate != "3"){
					homeTeamUl = "hide";
				}
			} else {
				if(pkstate == "2") {// 已结束
					teamClassRight += " bg_2f";
					teamClassRightP += " bg_2f";
				} else if(pkstate != "3"){
					teamClassRight += " bg_31";
					teamClassRightP += " bg_31";
				}
				if(pkstate != "3"){
					awayTeamUl = "hide";
				}
			}
		}
	}
	buildHtmlStr += '<li class="'+teamClassLeft+'" id="pk_HomeTeam'+pankouObj.id+'" rule="'+pankouObj.pkHomeRule+'">';
	buildHtmlStr += '<ul class="'+homeTeamUl+' "id="pk_HomeTeam_ul'+pankouObj.id+'" homeTeamName="'+battleObj.ht.teamName+'"><li class="view1"><p class="one">';
	
	if(pankouObj.pankouType=="1"&&pankouObj.pkRangFenTeam=="1"){
		buildHtmlStr += '<span class="co_ffb ft-16 font-normal">[-'+pankouObj.pkRangfenNum+']</span>';
	}
	buildHtmlStr += battleObj.ht.teamName+'</p><p class="two"><span id="homeRule_odds'+pankouObj.id+'"></span>X<span id="homeRule_num'+pankouObj.id+'">'+pankouObj.pkHomeRule+'</span></p></li>';;
	buildHtmlStr += '<li class="view2"><img src="'+battleObj.ht.teamIcon+'"></li>';
	buildHtmlStr += '<li class="view3"><p id="pkHomePrtNum'+pankouObj.id+'">'+countScale(pankouObj.pkHomePrt,pankouObj.pkHomePrt+pankouObj.pkAwayPrt)+'</p>';
	if(judge(pankouObj.userJc) && pkstate != "3"){
		if(pankouObj.pkVictory == battleObj.homeTeam) {// 胜场  和 主队 一致
			buildHtmlStr += '<span class="co_ffb ml-5">WIN</span>';
		}
	}
	buildHtmlStr += '</li></ul>';
	if(homeTeamUl == "hide") {// 战队信息隐藏，则显示投注信息
		buildHtmlStr += panKouXiaZhuHtml;
	}
	
	buildHtmlStr += '</li>';
	buildHtmlStr += '<li class="'+teamClassRight+'" id="pk_AwayTeam'+pankouObj.id+'" rule="'+pankouObj.pkAwayRule+'"><p class="'+teamClassRightP+'" id="pk_AwayTeam_p'+pankouObj.id+'"></p>';
	buildHtmlStr += '<ul class="'+awayTeamUl+'" id="pk_AwayTeam_ul'+pankouObj.id+'" awayTeamName="'+battleObj.at.teamName+'"><li class="view1">';
	if(judge(pankouObj.userJc) && pkstate != "3"){
		if(pankouObj.pkVictory == battleObj.awayTeam) {// 胜场  和 客队 一致
			buildHtmlStr += '<span class="co_ffb ml-5">WIN</span>';
		}
	}
	buildHtmlStr += '<p id="pkAwayPrtNum'+pankouObj.id+'">'+countScale(pankouObj.pkAwayPrt,pankouObj.pkHomePrt+pankouObj.pkAwayPrt)+'</p></li>';
	buildHtmlStr += '<li class="view2"><img src="'+battleObj.at.teamIcon+'"></li>';
	buildHtmlStr += '<li class="view3"><p class="one">'+battleObj.at.teamName;
	if(pankouObj.pankouType=="1"&&pankouObj.pkRangFenTeam=="2"){
		buildHtmlStr += '<span class="co_ffb ft-16 font-normal">[-'+pankouObj.pkRangfenNum+']</span>';
	}
	buildHtmlStr += '</p><p class="two">X<span id="awayRule_num'+pankouObj.id+'">'+pankouObj.pkAwayRule+'</span><span id="awayRule_odds'+pankouObj.id+'"></span></p></li></ul>';
	if(awayTeamUl == "hide"){
		buildHtmlStr += panKouXiaZhuHtml;
	}
	buildHtmlStr += '</li></ul>';
	return buildHtmlStr;
}

/**
 * lwh 计算下注人数比例
 * @param num  计算对象
 * @param total 计算总合
 * @returns
 */
function countScale(num,total){
	num = parseFloat(num); 
	total = parseFloat(total); 
	if (isNaN(num) || isNaN(total)) { 
		return "-"; 
	} 
	return total <= 0 ? "50%" : (Math.round(num / total * 10000) / 100.00 + "%");
}

/**
 * lwh 获取外DIV层内容
 */
function openTopBuild(battleObj,pankous,logged,date){
	var buildHtmlStr = '';
	var pankouNum = pankous.length;
	var pankouObj = pankous[0];
	var nodiv = false;
	if(!logged){	//未登录
		buildHtmlStr += '<div class="match-list-bottom-pour hide" id="noUser'+pankouObj.id+'">';
		buildHtmlStr += '<p class="match-list-choose">请先<a href="javascript:showLogin();">登录</a></p>';
	}else{
		if(pankouNum>=1&&(pankouObj.gbstate=="0"&&!judge(pankouObj.userJc))){	//<!--比赛未开始未下注且非多盘口-->
			
		}else{
			buildHtmlStr += '<div class="match-list-bottom-pour hide" id="match-list-bottom-pour'+pankouObj.id+'">';
		}
		
		if(pankouObj.gbstate=="0"&&!judge(pankouObj.userJc)){	//<!--比赛未开始未下注-->
			nodiv = true;
		}else if((pankouObj.gbstate=="0"&&judge(pankouObj.userJc))||(pankouObj.gbstate=="1"&&judge(pankouObj.userJc))||(pankouObj.gbstate=="2"&&judge(pankouObj.userJc))||(pankouObj.gbstate=="2"&&judge(pankouObj.userJc))){	//<!--比赛未开始已下注-->
			buildHtmlStr += bettingBuild(battleObj, pankouObj, true)
		}else if(pankouObj.gbstate=="1"&&!judge(pankouObj.userJc)){	//<!--比赛已开始未下注-->
			buildHtmlStr += '<p class="match-list-choose">您未参与竞猜</p>';
		}else if(pankouObj.gbstate=="2"&&!judge(pankouObj.userJc)){	//<!--比赛已结束未下注-->
			buildHtmlStr += '<p class="match-list-choose">您未参与竞猜</p>';
		}else if(pankouObj.gbstate=="3"&&judge(pankouObj.userJc)){	//<!--比赛已取消-->
			buildHtmlStr += '<p class="match-list-choose">比赛已取消</p>';	
		}
	}
	if(!nodiv){
		buildHtmlStr += '</div>';	
	}
	if(pankouNum>1){
		buildHtmlStr += '<div class="match-list-more-play hide" id="match-list-more-play'+battleObj.id+'">';
		for(var pk=1;pk<pankous.length;pk++){
			buildHtmlStr += hidePankouBuild(battleObj,pankous[pk],logged,date)
		}
		buildHtmlStr += '</div>';
	}
	return buildHtmlStr;
}

function showLogin() {
	$("#myModal").modal("show");
}

/**
 * lwh 拼接下注操作和显示
 * @param battleObj
 * @param pankouObj
 */
function bettingBuild(battleObj, pankouObj, top) {
	var buildHtmlStr = '<p class="view1"><span class="co_ff">投注金额</span><span class="one ml-5"></span>';
	buildHtmlStr += '<span class="co_ff ft_18_bo" id="userJcGold'+pankouObj.id+'">'+pankouObj.userJc.jcGold+'</span>';
	if(pankouObj.gbstate == '0') {// 未开始
		var rule = 0;
		if(pankouObj.userJc.jcTeamType==1){
			rule = pankouObj.pkHomeRule;
		}else{
			rule = pankouObj.pkAwayRule;
		}
		buildHtmlStr += '<span class="two co_be ml-5" id="two'+pankouObj.id+'" rule="'+rule+'" pkId="'+pankouObj.id+'">再下注</span>';
	}
	if(top) {
		buildHtmlStr += '</p><p class="view1"><span class="co_ff">我投注的战队：</span><span class="co_31 ft_18_bo" id="userJcTeamName'+pankouObj.id+'">';
	} else {
		buildHtmlStr += '</p><p class="view1"><span class="co_ff">我投注的战队：</span><span class="co_00 ft_18_bo" id="userJcTeamName'+pankouObj.id+'">';
	}
	
	
	var userJcGoldEstimate = 0;
	var gold = pankouObj.userJc.jcGold;
	if(pankouObj.userJc.jcTeamType==1){
		buildHtmlStr += battleObj.ht.teamName;
		userJcGoldEstimate = accSub(mul(gold,pankouObj.pkHomeRule) , gold) ;
	}else{
		buildHtmlStr += battleObj.at.teamName;
		userJcGoldEstimate = accSub(mul(gold,pankouObj.pkAwayRule), gold);
	}
	buildHtmlStr += "</span>";
	if(pankouObj.pkStatus == '0' && top) {// 未开始 + 头部
		buildHtmlStr += '<span class="three co_be" id="three_'+pankouObj.id+'">切换投注队伍</span>';
	}
	buildHtmlStr += '</p><p class="view1"><span class="co_ff">可获得收益：</span><span class="co_ff ft_18_bo" id="userJcGoldEstimate'+pankouObj.id+'">'+userJcGoldEstimate+'</span></p>';
	if(top) {
		var jcId = pankouObj.userJc.id;
		buildHtmlStr += ' <div class="ewmwrap" onClick="ewmwrapAddEvent(\'' + jcId + '\')"><img class="erweima" src="' + _staticPrefix + '/images/erweima.gif" alt=""/><img class="yuanjiao" src="' + _staticPrefix + '/images/yuanjiao.png" alt=""/></div>';
		buildHtmlStr += ' <div class="qrcodewrap qrcode_' + jcId + '"><div class="qrcode" id="qrcode_' + jcId + '"></div><p style="color: #31c3a2;"><i class="iconfont icon-saoyisao"></i>打开微信扫一扫点击分享送<span class="egbg"></span>币</p></div>';
		
	}
	return buildHtmlStr;
}

/**
 * lwh 用于拼接展开的隐藏盘口
 * @param battleObj
 * @param pankouObj
 * @param logged
 * @param date
 */
function hidePankouBuild(battleObj,pankouObj,logged,date){
	var buildHtmlStr = '<div class="match-list-more-play-small">';
	buildHtmlStr += topStatePankouBuild(battleObj,pankouObj,date,false);
	buildHtmlStr += infoBuild(battleObj,pankouObj,false);
	buildHtmlStr += '</div>';
	return buildHtmlStr;
	
}

function moreGb(){
	$("#moreGb").html('<p class="load-more-now"><img src="'+_staticPrefix+'/images/load_more_new.gif"><span>正在加载...</span></p>');
	loadGameBattleList(2);
}

//乘法浮点数bug解决办法
function mul(a, b) {
    var c = 0,
        d = a.toString(),
        e = b.toString();
    try {
        c += d.split(".")[1].length;
    } catch (f) {
    }
    try {
        c += e.split(".")[1].length;
    } catch (f) {
    }
    return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
}

//滚动加载通知数据
$(document).ready(function () { 
	$(window).unbind("scroll");
	$(window).scroll(function () {
		var totalheight = parseFloat($(window).height())+parseFloat($(window).scrollTop());
		if($(document).height()<= totalheight){
			if (nowPage_gb <= pageCount_gb) {
				if(nowPage_gb>=4){
					//显示更多比赛按钮 ,点击按钮分页
					$("#moreGb").html('<p class="load-more"><a href="javascript:moreGb();">更多比赛</a></p>');
				}else{
					$("#moreGb").html('<p class="load-more-now"><img src="'+_staticPrefix+'/images/load_more_new.gif"><span>正在加载...</span></p>');
					loadGameBattleList(2);
				}
			}else{
				//暂无更多数据
				if(nowPage_gb!=1){
					$("#moreGb").html('<p class="load-more">没有下一页</p>');
				}
			}
		}
	});
});

/************************************ 页面静态JS ************************************************************/
function pkTeamClick() {
	$(".message-left, .message-right").on("click", function () {/*点击右边*/
		var pkstate = $(this).parent().attr("pkstate");
		var pkujcteam = $(this).parent().attr("pkujcteam");
		var top = $(this).parent().attr("top");
        rule = $(this).attr("rule");
		if (top == "1") {/*如果是顶部*/
			toppkId = $(this).parent().attr("pkId");
			if($(this).hasClass("message-left")) {
				topteamType = "1";
			} else {
				topteamType = "2";
			}
			if (user == "" || user == null) {/*未登录*/
				restoreTrapezoid();
				$("#noUser"+toppkId).removeClass("hide");
			} else {/*已登录*/
				if (pkstate == "0" && !judge(pkujcteam)) {/*未开始未下注*/
					restoreTrapezoid();
					if($(this).parent().next().hasClass("match-list-bottom-pour") != true){
						$("#nobet").find(".ex8").slider('setValue', minGold);
						$("#nobet").find(".ex8").change();
					}
					$(this).parent().after($("#nobet"));
					$("#nobet").removeClass("hide");
					$("#nobet").find(".earnings").html(mul($("#nobet .ex8").slider('getValue'), rule));//这里填实际比赛倍数
					if($(this).hasClass("message-left") == true) {
                        $(this).parent().attr("pkujcteamnow","1");
                    } else {
                        $(this).parent().attr("pkujcteamnow","2");
                    }
				} else if(pkstate == "0" && judge(pkujcteam)){/*未开始已下注*/
					restoreTrapezoid();
					if($(this).parent().next().next().attr("id") == 'nobet'){
						$(this).parent().after($("#nobet"));
						$("#nobet").removeClass("hide");
					} else{
						$(this).parent().next().removeClass("hide");
					}
				} else{/*其他情况*/
					restoreTrapezoid();
					$(this).parent().next().removeClass("hide");
				}
			}
		} else {/*如果是底部*/
			if($(this).hasClass("message-left")) {
				teamType = "1";
			} else {
				teamType = "2";
			}
			if (user == "" || user == null) {/*未登录*/
				restoreNewTrapezoid();
				layer.msg("请先登录", {icon: 5,shift: 6,time: 2000});
			} else {/*已登录*/
				if (pkstate == "0") {/*未开始未下注*/
					pkId = $(this).parent().attr("pkId");
					if(!judge(pkujcteam)) {
						restoreNewTrapezoid();
						if($(this).find("#new_play_nobet").hasClass("x-match-list-bottom-pour") != true){
							$("#new_play_nobet").find(".ex8").slider('setValue', minGold);
							$("#new_play_nobet").find(".ex8").change();
						}
						$(this).find("ul").addClass("hide");
						$("#new_play_nobet").appendTo($(this)).removeClass("hide");
						$(this).addClass("bg_2f");
						$(this).find(".message-right-a").addClass("bg_2f");
					}
				}
			}
		}
		
		if($(this).hasClass("message-left")) {
			$(this).css("border-bottom-left-radius", "0");
	        $(this).next().css("border-bottom-right-radius", "0");
	        if($(this).parent().parent().find(".match-list-more-play").hasClass("match-list-more-play") == true){/*new-play*/
	            $(this).parent().siblings(".match-list-more-play").removeClass("hide");
	            $(this).parent().next().css("border-radius","0");
	        }
		} else if($(this).hasClass("message-right")) {
			$(this).prev().css("border-bottom-left-radius", "0");
	        $(this).css("border-bottom-right-radius", "0");
	        if($(this).parent().parent().find(".match-list-more-play").hasClass("match-list-more-play") == true){/*new-play*/
	            $(this).parent().siblings(".match-list-more-play").removeClass("hide");
	            $(this).parent().next().css("border-radius","0");
	        }
		}
	});
}

function mouseover() {
	$(".message-left").mouseover(function () {/*左侧鼠标经过效果*/
		if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {
			$(this).css("background-color", "#2f5a56");
		}
	}).mouseout(function () {
		if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {
			$(this).css("background-color", "#2e3238");
		}
	});
	
	$(".message-right").mouseover(function () {/*右侧鼠标经过效果*/
		if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {
			$(this).css("background-color", "#2f5a56");
			$(this).find(".message-right-a").css("background-color", "#2f5a56");
		}
	}).mouseout(function () {
		if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {
			$(this).css("background-color", "#33383e");
			$(this).find(".message-right-a").css("background-color", "#33383e");
		}
	});
}

//判断属性是否存在
function judge(obj) {
	for (var i in obj) {//如果不为空，则会执行到这一步，返回true
	    return true;
	}
	return false;
}

//点击再下注
function secondJcClass() {
	$(".match-list-bottom-pour .view1 .two").click(function () {
		rule = $(this).attr("rule");
		$("#nobet").removeClass("hide");
		$(this).parent().parent().after($("#nobet"));
		$(this).parent().parent().addClass("hide");
		
		$("#nobet").find(".ex8").slider('setValue', minGold);
		$("#nobet").find(".ex8").change();
	})
}

//新的再下注
function newSecondJcClass() {
	$(".match-list-bottom-pour-new .view1 .two").on("click",function(){
		restoreNewTrapezoid();
		
		rule = $(this).attr("rule");
		pkId = $(this).attr("pkId");
		$("#new_play_nobet").removeClass("hide")
		$(this).parent().parent().after($("#new_play_nobet"));
		$(this).parent().parent().addClass("hide");
		
		$("#new_play_nobet").find(".ex8").slider('setValue', minGold);
		$("#new_play_nobet").find(".ex8").change();
		
		var obj = $(this).parent().parent().parent();
		obj.removeClass("bg_31").addClass("bg_2f");
		obj.find(".message-right-a").removeClass("bg_31").addClass("bg_2f");
	})
}

 //点击切换投注队伍
function changeTeam() {
	$(".match-list-bottom-pour .view1 .three").on("click", function () {
		var _thisPkId = $(this).attr("id").split("_")[1];
		updateUserJcTeam(_thisPkId, true);
	})
}

//新玩法切换战队
function newChangeTeam() {
	$(".middle-change-team").on("click",function(){
		var _thisPkId = $(this).attr("id").split("_")[1];
		updateUserJcTeam(_thisPkId, false);
	})
}

function updateUserJcTeam(_thisPkId, top) {
	/*这里添加弹出框，询问是否切换下注战队*/
	layer.confirm('切换投注队伍会消耗100金币，是否切换？', {icon: 3, title:'切换下注战队'}, function(index){
		if(index > 0) {
			$.ajax({
				type : "POST",
				url : _path + "/userJc/updateUserJcTeam",
				data:{"pkId":_thisPkId },
				dataType : "json",
				success : function(resultdata) {
					if (resultdata.isSuccess) {
						//修改页面用户G币
						updateUserGold(2,-100);
						//修改拖拽条
						exchangeEx8();
						var pkInfo = resultdata.pkInfo;
						var userJc = resultdata.updateUserJc;
						//更改下注百分比
						$("#pkHomePrtNum"+_thisPkId).html(pkInfo.pkHomePrtNum);
						$("#pkAwayPrtNum"+_thisPkId).html(pkInfo.pkAwayPrtNum);
						var userJcTeamName = "";
						if(userJc.jcTeamType == "1"){// 主队 
							rule = pkInfo.pkHomeRule;
							$("#two"+_thisPkId).attr("rule",rule);
							userJcTeamName = pkInfo.homeTeamName;
						} else {
							rule = pkInfo.pkAwayRule;
							$("#two"+_thisPkId).attr("rule",rule);
							userJcTeamName = pkInfo.awayTeamName;
						}
						$("#userJcTeamName"+_thisPkId).html(userJcTeamName);
						$("#userJcGold"+_thisPkId).html(userJc.jcGold);
						$("#userJcGoldEstimate"+_thisPkId).html(accSub(mul(userJc.jcGold, rule),userJc.jcGold));
						
						if(!top) {
							var a = $("#pk_ul_class"+_thisPkId);
							if(a.attr("pkujcteam") == "1"){// 主队
								$("#pk_HomeTeam"+_thisPkId).removeClass("bg_31");
								$("#pk_HomeTeam_ul"+_thisPkId).removeClass("hide");
								$("#pk_AwayTeam"+_thisPkId).addClass("bg_31");
								$("#pk_AwayTeam_p"+_thisPkId).addClass("bg_31");
								$("#pk_AwayTeam_ul"+_thisPkId).addClass("hide").after($("#match-list-bottom-pour-new"+_thisPkId));
							} else {
								$("#pk_HomeTeam"+_thisPkId).addClass("bg_31");
								$("#pk_HomeTeam_ul"+_thisPkId).addClass("hide").after($("#match-list-bottom-pour-new"+_thisPkId));
								$("#pk_AwayTeam"+_thisPkId).removeClass("bg_31");
								$("#pk_AwayTeam_p"+_thisPkId).removeClass("bg_31");
								$("#pk_AwayTeam_ul"+_thisPkId).removeClass("hide");
							}
						}
						$("#pk_ul_class"+_thisPkId).attr("pkujcteam", userJc.jcTeamType);// 修改下注队伍
					}
				}
			});
		}
		layer.close(index);
	});
}

//还原梯形选项卡
function restoreTrapezoid() {
	$(".match-list-bottom-pour,.match-list-more-play").addClass("hide");
	$(".message-left").css("border-bottom-left-radius", "8px");
	$(".message-right").css("border-bottom-right-radius", "8px");
	$(".match-list-message").removeAttr("pkujcteamnow");
}

//还原新玩法选项卡
function restoreNewTrapezoid(){
	$(".match-list-message[top='2'][pkstate='0'] .message-left ul,.match-list-message[top='2'][pkstate='0'] .message-right ul").removeClass("hide");
	$(".match-list-message[top='2'][pkstate='0'][pkujcteam='1'] .message-left ul,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .message-right ul").addClass("hide");
	$(".match-list-message[top='2'][pkstate='0'][pkujcteam='1'] .message-left .match-list-bottom-pour-new,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .message-right .match-list-bottom-pour-new").removeClass("hide");
	$(".match-list-message[top='2'][pkstate='0'][pkujcteam='1'] .middle,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .middle").addClass("hide");
	$(".match-list-message[top='2'][pkstate='0'][pkujcteam='1'] .middle-change-team,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .middle-change-team").removeClass("hide");
	$(".match-list-message[top='2'][pkstate='0'] .message-left,.match-list-message[top='2'][pkstate='0'] .message-right,.match-list-message[top='2'][pkstate='0'] .message-right-a").removeClass("bg_2f");
	$(".match-list-message[top='2'][pkstate='0'][pkujcteam='1'] .message-left,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .message-right,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .message-right-a").addClass("bg_31");
}

function exchangeEx8() {
	//当前用户G币
    var goldNum = _userGold + _sysGold;
    if(goldNum>=100000){
    	//投注上线
    	goldNum = 100000;
    }
    //金额进度条
    $(".ex8").slider({
        min: minGold,
        max: goldNum,//这里填当前用户G币总数
        step: 1,
        value: 0,
        tooltip: 'always'
    });
    
    //点击All in
    $(".match-list-bottom-pour .view4 .two").click(function () {
        $(this).parent().prev().find(".ex8").slider('setValue', parseFloat(goldNum));//这里填当前用户G币总数
        $(this).parent().prev().find(".ex8").change();
    })
    
    //新玩法点击All in
    $("#new_play_nobet .new-two").click(function () {
        $(this).siblings(".ex8").slider('setValue', parseFloat(goldNum));//这里填当前用户G币总数
        $(this).siblings(".ex8").change();
    })
    
    //拖动金额条
    $("#nobet .ex8").on("change", function () {
        $(this).parent().next().find(".one").html($(this).slider('getValue') + "G币");
        $(this).parent().prev().find("input").val($(this).slider('getValue'));
        $(this).parent().next().next().find(".earnings").html(mul($(this).slider('getValue'), parseFloat(rule)));//这里填实际比赛倍数
    });
    
    //新玩法拖动金额条
    $("#new_play_nobet .ex8").on("change", function () {
        $(this).parent().prev().find("input").val($(this).slider('getValue'));
        $(this).parent().next().find(".earnings").html(mul($(this).slider('getValue'), parseFloat(rule)));//这里填实际比赛倍数
    });
    
    //输入框输入竞猜金额
    $('.match-list-bottom-pour .view1 input').bind('input propertychange', function () {
        if ($(this).val() < minGold) {
        	$(this).parent().next().find(".ex8").slider('setValue', parseFloat(minGold));
        }
        else if($(this).val() >= minGold && $(this).val()<=goldNum){
        	$(this).parent().next().find(".ex8").slider('setValue', parseFloat($(this).val()));
            $(this).parent().next().find(".ex8").change();
        }
        else {//输入金额大于钱包金额时
            $(this).parent().next().find(".ex8").slider('setValue', parseFloat(goldNum));//这里填当前用户G币总数
            $(this).parent().next().find(".ex8").change();
        }
    });
    
    //新玩法输入框输入竞猜金额
    $('#new_play_nobet .view1 input').bind('input propertychange', function () {
        if ($(this).val() < minGold) {
        	$(this).parent().next().find(".ex8").slider('setValue', parseFloat(minGold));
        }
        else if($(this).val() >= minGold && $(this).val()<=goldNum){
        	$(this).parent().next().find(".ex8").slider('setValue', parseFloat($(this).val()));
            $(this).parent().next().find(".ex8").change();
        }
        else {//输入金额大于钱包金额时
            $(this).parent().next().find(".ex8").slider('setValue', parseFloat(goldNum));//这里填当前用户G币总数
            $(this).parent().next().find(".ex8").change();
        }
    });
    
    //阻止事件传播
    $("#new_play_nobet .view1 input").on("click",function(e){
    	e.stopPropagation();
    })
}


//减法浮点数bug解决办法
function accSub(arg1, arg2) {
	var r1, r2, m, n;
	try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
	try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
	m = Math.pow(10, Math.max(r1, r2));
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 开启监听
function addSocket() {
	socket = io.connect("ws://"+_socketAddress);
	socket.on("odds",function(data) {
		var odds = JSON.parse(data);
		var pkId = odds.id; // 盘口ID
		if(document.getElementById("pk_ul_class"+pkId)) {
			var homeRule_span = $("#homeRule_num"+pkId);
			var homeRule_num = homeRule_span.html();
			var homeRule_odds = $("#homeRule_odds"+pkId);
			
			var awayRule_span = $("#awayRule_num"+pkId);
			var awayRule_num = awayRule_span.html();
			var awayRule_odds = $("#awayRule_odds"+pkId);
			
			var odds_homeRule = odds.homeRule;
			var odds_awayRule = odds.awayRule;
			
			// 修改赔率 
			$("#pk_HomeTeam"+pkId).attr("rule",odds_homeRule);
			$("#pk_AwayTeam"+pkId).attr("rule",odds_awayRule);
			homeRule_span.html(odds_homeRule);
			awayRule_span.html(odds_awayRule);
			// 修改收益
			var userJcGoldNum = $("#userJcGold"+pkId).html();
			var pkujcteam = $("#pk_ul_class"+pkId).html();
			if("1" == pkujcteam) {
				$("#userJcGoldEstimate"+pkId).html(accSub(mul(userJcGoldNum,odds_homeRule),userJcGoldNum));
			} else {
				$("#userJcGoldEstimate"+pkId).html(accSub(mul(userJcGoldNum,odds_awayRule),userJcGoldNum));
			}
			
			// 动态展示赔率
			if(Number(odds_homeRule) != Number(homeRule_num)) {// 主队赔率有变动
				if(Number(odds_homeRule) > Number(homeRule_num)) {// 赔率加大
					homeRule_odds.removeClass("odds-decline").addClass("odds-rise");
				} else {
					homeRule_odds.removeClass("odds-rise").addClass("odds-decline");
				}
				setTimeout(function(){
					homeRule_odds.removeClass("odds-decline").removeClass("odds-rise");
				},1500);
			}
			
			
			if(Number(odds_awayRule)!=Number(awayRule_num)){
				if(Number(odds_awayRule)>Number(awayRule_num)){
					awayRule_odds.removeClass("odds-decline").addClass("odds-rise");
				}else{
					awayRule_odds.removeClass("odds-rise").addClass("odds-decline");
				}
				setTimeout(function(){
					awayRule_odds.removeClass("odds-decline").removeClass("odds-rise");
				},1500);
			}
		}
	});
}
