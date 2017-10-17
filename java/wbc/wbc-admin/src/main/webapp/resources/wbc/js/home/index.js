//cookie名称
var cookieName = "WBC_MATCH_GAMEID";
//cookie超时时间
var cookieDay = "d30";

$(function(){
	//查询最近比赛
	latelyGameBattle();
	//热门游戏
	loadGameList();
	//盈利TOP
	loadProfitTopList();
	//加载胜率TOP列表
	loadProfitRateTopList();
	$("body").css("width", window.innerWidth);
	$(".bodyer").css("width", window.innerWidth);
	$("#c-bg").css("width", window.innerWidth);
	$(".modal-dialog").css("max-height",window.innerHeight);
    $(".modal-dialog").mCustomScrollbar();
	
	window.onresize=function(){
		$("body").css("width", window.innerWidth);
		$(".bodyer").css("width", window.innerWidth);
		$("#c-bg").css("width", window.innerWidth);
		if (window.innerWidth < 1277) {
            $(".inc_right_tabs_three").addClass("window_shrink").removeClass("window_unfold");
             timer1 = setTimeout(function () {
                $(".inc_right_tabs_big").css("background-color", "transparent");
                $(".inc_right_tabs_three").css("margin-left", "35px");
            }, 200);
            $(".inc_right_tabs_bg").removeClass("hide");
        }
        else if (window.innerWidth >= 1277) {
            $(".inc_right_tabs_three").addClass("window_unfold").removeClass("window_shrink");
             timer2 = setTimeout(function () {
                $(".inc_right_tabs_big").css("background-color", "transparent");
                $(".inc_right_tabs_three").css("margin-left", "0");
            }, 200);
            $(".inc_right_tabs_bg").addClass("hide");
        }
        $(".inc_right,.inc_right_tabs_big,.slide").css("height", document.documentElement.clientHeight);
        $(".inc_right_tabs_account").css("padding-top", "0");
        $(".inc_right_tabs_feedback").css("padding-bottom", "0");
        inc_padding = (document.documentElement.clientHeight - $(".inc_right_tabs_middle").height()) / 2;
        $(".inc_right_tabs_account").css("padding-top", inc_padding);
        $(".inc_right_tabs_feedback").css("padding-bottom", inc_padding);
        $(".modal-dialog").css("max-height", window.innerHeight).mCustomScrollbar("update"); 
	}
});

function latelyGameBattle(){
	//分页
	var pagesJson = pages(5,0,1,"-1","-1");
	//调用后台   
	$.ajax( {
		url : _path + "/gb/homeGbList",
		type : "POST", 
		data:{   
			"gridPager":pagesJson
		},
		dataType : "json",
		success : function(result) {
			if(result.isSuccess){
				var gbList = result.gbList;
				if(gbList!=null && gbList.length>0){
					var html = "";
					var date = new Date();    
					for(var i=0;i<gbList.length;i++){
						html = html + '<a href="'+_path+'/gb/'+gbList[i].sid+'"><div class="list">';
						html = html + '		<img src="'+gbList[i].gevent.eventImg+'" class="list-bg" width="180" height="104">';
						html = html + '		<b class="opacity-b"></b>';
						html = html + '<div class="firstblood" style="height:32px; justify-content: center; padding: 15px 15px 10px 15px;">';
						html = html + '<p><img class="titleimg" src="'+gbList[i].game.littleImg+'" alt=""><span class="ssword" style="width: 230px;">'+gbList[i].gevent.eventName+'</span></p>';
						html = html + '<p>'+pankouPlay(gbList[i].pankous[0],gbList[i].ht.teamName,gbList[i].at.teamName)+'</p>';
						//获得时间差
						var dateD = getTimeDifference(date.getTime(),gbList[i].pankous[0].pkStartTime);
						html = html + '			<p style="color: #31c3a2;"><span style=" color: #ffffff; margin-right: 15px;">BO'+gbList[i].gameRule+'</span>'+dateD+'</p></div>';
						html = html + '<div class="dexlistwrap">';
						html = html + '		<div class="listbisaione">';		
						html = html + '				<div class="item_c" style="display: flex; flex-direction:row; justify-content: flex-end;">';
						html = html + '					<div style="display: flex; flex-direction:column; margin-right: 20px; justify-content: center; padding-right: 5px;">';
						html = html + '						<p style="position: relative;"><span class="ssword" style="text-align: right; color: #31c3a2;">'+gbList[i].ht.teamName+'</span></p>';
						html = html + '						<span class="ssword" style="text-align: right;">X '+gbList[i].pankous[0].pkHomeRule+'</span>';
						html = html + '					</div>';
						html = html + '					<img src="'+gbList[i].ht.teamIcon+'" alt="" style="margin-top: 20px;">   ';
						html = html + '				</div>';
						html = html + '		</div>';
						html = html + '		<div class="vs-c"><img src="'+_staticPrefix+'/images/r_vs.png" alt=""></div>';
						html = html + '		<div class="listbisaitwo">';
						html = html + '			<div class="item_c" style="display: flex; flex-direction:row;">';
						html = html + '				<img src="'+gbList[i].at.teamIcon+'" weight="60" height="60" alt="" style="margin-top: 20px;">';
						html = html + '				<div style="display: flex; flex-direction:column; margin-left: 20px; padding-left: 5px; justify-content: center;">';
						html = html + '					<p style="position: relative;"><span class="ssword" style="color: #31c3a2;">'+gbList[i].at.teamName+'</span></p>';
						html = html + '					<span class="ssword">X '+gbList[i].pankous[0].pkAwayRule+'</span>';
						html = html + '				</div>';
						html = html + '			</div>';
						html = html + '		</div>';
						html = html + '</div>';
						html = html + '</div></a>';
						
						
					}
					$("#latelyGameBattle").html(html);
				}else{
					$("#latelyGameBattle").html('<div class="no-record-x"><i class="iconfont icon-kule"></i>暂无比赛数据</div>');
				}
				$(".list").mouseenter(function () {  
			        $(this).find(".opacity-b").attr("class","opacity-c");
			    }).mouseleave(function () {
			        $(this).find(".opacity-c").attr("class","opacity-b");
			    })
			}
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}

//加载游戏列表
function loadGameList(){
	$.ajax({
		type : "POST",
		url : _path + "/game/indexGamelist",
		dataType : "json",
		success : function(resultdata) {
			if (resultdata.isSuccess) {
				var data = resultdata.list;
				var game;
				var content = "";
				$("#game-div").html("");
				for(var i = 0;i<data.length;i++){
					game = data[i];
					content += '<div class="col-xs-4 hot-game hot_meidia">';
					content += '<a href="'+_path+'/match/'+game.englishName+'">';
					content += '<img width="273" height="135" src="'+game.bigImg+'">';
					content += '<p class="ab_text">'+game.gameName+'</p>';
					content += '</a>';
					content += '</div>';
				}
				$("#game-div").append(content);
			}
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}

//加载盈利TOP列表
function loadProfitTopList(){
	$.ajax({
		type : "POST",
		url : _path + "/profitTopList",
		dataType : "json",
		success : function(resultdata) {
			if (resultdata.isSuccess) {
				var data = resultdata.list;
				var userEntity;
				var content = "";
				$("#profitTopList-div").html("<div class=\"title\">盈利TOP</div>");
				for(var i = 0;i<data.length;i++){
					userEntity = data[i];
					content += '<div class="row mt-10 page-header_2" style="color:white; margin: 0px 10px 0 10px;">';
					if(i==0){
						content += '<span class="fl guess-rank-list media guess_f" style="margin: 0; border: none;"></span>';
					}else{
						content += '<span class="fl guess-rank-list" style="margin: 0;">'+(i+1)+'</span>';
					}
                	content += '<span class="fl" style="margin: 0 10px;">';
                	content += '<img src="'+userEntity.userPhoto_65+'" class="img-circle c-img" alt="'+userEntity.nickName+'" width="48" height="48">';
                	content += '</span>';
                	content += '<span class="fl">';
                	content += '<p>'+userEntity.nickName+'</p>';
                	content += '<p><span class="lv-x lv-x-'+userEntity.rankLevel+'"></span></p>';
                	content += '</span>';
                	content += '<span class="fr mt-15" style="padding:0">';
                	content += userEntity.goldNum+'金币';
                	content += '</span>';
                	content += '</div>';
				}
				$("#profitTopList-div").append(content);
			}
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}

//加载胜率TOP列表
function loadProfitRateTopList(){
	$.ajax({
		type : "POST",
		url : _path + "/profitRateTopList",
		dataType : "json",
		success : function(resultdata) {
			if (resultdata.isSuccess) {
				var data = resultdata.list;
				var userEntity;
				var content = "";
				$("#profitRateTopList-div").html("<div class=\"title\">胜率TOP</div>");
				for(var i = 0;i<data.length;i++){
					userEntity = data[i];
					content += '<div class="row mt-10 page-header_2" style="color:white; margin: 0px 10px 0 10px;">';
					if(i==0){
						content += '<span class="fl guess-rank-list media guess_f" style="margin: 0; border: none;"></span>';
					}else{
						content += '<span class="fl guess-rank-list" style="margin: 0;">'+(i+1)+'</span>';
					}
                	content += '<span class="fl" style="margin: 0 10px;">';
                	content += '<img src="'+userEntity.userPhoto_65+'" class="img-circle c-img" alt="'+userEntity.nickName+'" width="48" height="48">';
                	content += '</span>';
                	content += '<span class="fl">';
                	content += '<p>'+userEntity.nickName+'</p>';
                	content += '<p><span class="lv-x lv-x-'+userEntity.rankLevel+'"></span></p>';
                	content += '</span>';
                	content += '<span class="fr mt-15" style="padding:0">';
                	content += userEntity.victoryRate+'% <font class="link-orange">win</font>';
                	content += '</span>';
                	content += '</div>';
				}
				$("#profitRateTopList-div").append(content);
			}
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}