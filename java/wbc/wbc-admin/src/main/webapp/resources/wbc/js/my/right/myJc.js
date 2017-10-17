$(function() {
	
});

// 竞猜表头
function getMyJcForm() {
	var url = _path + "/userJc/getMyJc";
	var dataJson = {};
	
	ajaxMethod(url, dataJson, "post", false, function(resultdata) {
		if (resultdata.isSuccess) {
			var userEntity = resultdata.userEntity;
			var userIncrement = userEntity.userIncrement;
			var total = 0;
			if(resultdata.weekProfit != null) {
				total = resultdata.weekProfit["total"];
			}
			var content = '';
			content += '<div class="picwrap">';
			content += '<dl><dt style="text-align:center;"><img src="'+_staticPrefix+'/images/jccai.png" alt=""/></dt><dd>共有效竞猜 <span style="color:#3ec3a2">'+userIncrement.jcNum+'</span> 次</dd></dl>';
			content += '<dl><dt style="text-align:center;"><img src="'+_staticPrefix+'/images/win.png" alt=""/></dt><dd>获胜 <span style="color:#3ec3a2">'+userIncrement.victoryNum+'</span> 次</dd></dl>';
			content += '<dl><dt style="text-align:center;"><img src="'+_staticPrefix+'/images/gougouzi.png" alt=""/></dt><dd>胜率 <span style="color:#3ec3a2">'+Number(userIncrement.victoryRate*100).toFixed(2)+'%</span></dd></dl>';
			content += '</div>';
			content += '<div class="picwrap">';
			var color = "#3ec3a2";
			if(total < 0) {
				color = "#df3c3e";
			} else if(total > 0) {
				color = "#e4a713";
			}
			content += '<dl><dt style="text-align:center;"><img src="'+_staticPrefix+'/images/shouyia.png" alt=""></dt><dd>本周收益 <span style="color:'+color+'">'+total+'</span> 金币</dd></dl>';
			content += '<dl><dt></dt><dd><span></span></dd></dl>';
			content += '<dl><dt></dt><dd><span></span></dd></dl>';
			content += '</div>';
			
			$("#jcForm").html(content);
		} else {
			layer.alert(resultdata.message, {icon: 5,shift: 6,time: 2000});
		}
	});
}

var first = true;
var pageNum = 1;
// 竞猜列表
function getMyJcList(_first) {
	if(_first != undefined && _first) {// 第一次点击，初始化
		first = _first;
		pageNum = 1;
	}
	var url = _path + "/userJc/list";
	var dataJson = {};
	dataJson.pageSize = "3";
	dataJson.nowPage = pageNum;// 页码
	pageNum = pageNum + 1;// 下一页
	
	var parameters = {};
//	parameters.orderBy = "IF(endTime is null, jcTime, endTime) desc";
	
	dataJson.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(dataJson)}, "post", false, function(backData) {
		var content = "";
		var data = backData.list;
		if(data.length > 0) {
			var userJc ;
			for(var i = 0; i<data.length; i++) {
				userJc = data[i];
				content += '<div class="jcrecord-content">';
				content += '<div class="shadow-item"></div>';
				var url = _path + "/gb/" + userJc.gbId;
				content += '<a href="'+url+'" class="btn btn-primary btn-raised chakan" style="width: 48px; height: 24px; padding: 0; line-height: 24px;">查 看</a>';
				content += '<div style="width: 50px; height: 42px; line-height: 42px; text-align: center;">';
				
				var gameResult = userJc.gameResult;
				if("1" == gameResult) {
					content += '<sapn class="tip-yuan" style="background: #e4a713; margin: 0;">W</sapn>';
				} else if("0" == gameResult) {
					content += '<sapn class="tip-yuan" style="background: #525252; margin: 0;">L</sapn>';
				} else if("3" == gameResult) {// 取消
					content += '<sapn class="tip-yuan" style="background: #4470B1; margin: 0;">!</sapn>';
				}  else {
					content += '<sapn class="tip-yuan" style="background: #0c6954; margin: 0;">N</sapn>';
				}
				var jcTeamType = userJc.jcTeamType;
				content += '</div>';
				content += '<div>';
				content += '<div class="myjc-item" style="margin-bottom: 3px;">';
				content += '<img src="'+ userJc.gameImg +'" alt="" class="a-bit" />';
				if(1 == jcTeamType) {// 主队
					content += '<span class="myvote">'+userJc.homeTeam+'</span>';
				} else {
					content += '<span>'+userJc.homeTeam+'</span>';
				}
				content += '<span>'+pankouPlay(userJc, userJc.homeTeam, userJc.awayTeam)+'</span>';
				if(2 == jcTeamType) {// 客队
					content += '<span class="myvote">'+userJc.awayTeam+'</span>';
				} else {
					content += '<span>'+userJc.awayTeam+'</span>';
				}
				content += '</div>';
				content += '<div class="myjc-item" style="margin-top: 6px;">';
				content += '<span class="goldspan">下注&nbsp;:&nbsp;'+userJc.jcGold+'</span>';// 下注金额
				if("1" == gameResult) {// 获胜
					content += '<span class="goldspan" style="padding-left: 21px;">收益&nbsp;:&nbsp;<b style="color: #e4a713;">'+(userJc.victoryGoldNum - userJc.jcGold)+'</b></span>';
				} else if("2" == gameResult) {// 进行中	
					content += '<span class="goldspan">预收益&nbsp;:&nbsp;<b style="color: #3ec3a2;">'+userJc.expectGold+'</b></span>';
				} else if("3" == gameResult) {// 取消
					content += '<span class="goldspan" style="padding-left: 21px;">返还&nbsp;:&nbsp;<b>'+userJc.jcGold+'</b></span>';
				} else {// 失败
					content += '<span class="goldspan" style="padding-left: 21px;">收益&nbsp;:&nbsp;<b style="color: #df3c3e;">'+(userJc.victoryGoldNum - userJc.jcGold)+'</b></span>';
				}
				if("3" == gameResult) {// 取消
					content += '<span class="jctime">已取消</span>'; // 距离现在多久
				} else if("2" == gameResult) {// 等待
					content += '<span class="jctime">'+getDifToNow(userJc.startTime)+'</span>'; // 距离现在多久
				} else {
					if(("1" == jcTeamType && "1" == gameResult) || ("2" == jcTeamType && "0" == gameResult)) {// 主场胜、客场输
	            		content += '<span class="jctime">'+userJc.homeTeam+' 获胜</span>';
	            	} else {
	            		content += '<span class="jctime">'+userJc.awayTeam+' 获胜</span>';
	            	}
				}
				content += '</div>';
				content += '</div>';
				content += '</div>';
			}
		} else if(first) {
			content = '';
		}
		
		if(first) {
			$("#jcList").html(content);
		} else {
			$("#jcList").append(content);
		}
		
		var pageCount = backData.pageCount;
		if(data.length > 0 && pageCount >= pageNum) {
			first = false;
			$("#jcMore").show();
			$("#jcNoMore").hide();
		} else {
			$("#jcMore").hide();
			$("#jcNoMore").show();
		}

		$('.jcrecord-content').mouseover(function(){
	        $(this).find('.shadow-item,.chakan').show();
	    });
	    $('.jcrecord-content').mouseout(function(){
	        $(this).find('.shadow-item,.chakan').hide();
	    });
	})
}

function getDifToNow(long) {
	var now = new Date();// 当前日期
	
	var dif = long - now.valueOf();
	dif = dif/1000/60/60; // 小时
	
	if(dif >= 24) {
		return parseInt(dif/24) + "天后";
	} else if(dif >= 1) {
		return parseInt(dif) + "小时后";
	} else {
		var minutes = parseInt(dif*60);
		if(minutes <= 1) {
			return "进行中";
		} else {
			return parseInt(dif*60) + "分钟后";
		}
	}
}
