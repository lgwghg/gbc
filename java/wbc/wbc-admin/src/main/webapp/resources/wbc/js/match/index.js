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
	loadGameBattleList(1, "");
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

function moreGb(){
	$("#moreGb").html('<p class="load-more-now"><img src="'+_staticPrefix+'/images/load_more_new.gif"><span>正在加载...</span></p>');
	loadGameBattleList(2);
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
