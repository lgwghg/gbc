$(function() {
	loadProfitTopList();// 获利前五的高手
	getMyJc();// 你的竞猜统计
	loadList();// 你的竞猜列表
});

function getMyJc() {
	$.ajax({
		type : "POST",
		url : _path + "/userJc/getMyJc",
		dataType : "json",
		success : function(resultdata) {
			if (resultdata.isSuccess) {
				var userEntity = resultdata.userEntity;
				$("#nickName").text(userEntity.nickName);
				$("#photo").attr("src", userEntity.photo_35);
				
				var userIncrement = userEntity.userIncrement;
				
				$("#jcNum").text(userIncrement.jcNum);
				$("#victoryNum").text(userIncrement.victoryNum);
				$("#victoryRate").text(Number(userIncrement.victoryRate*100).toFixed(2) + "%");
				
				$("#userGold").text(resultdata.userGold);
				var total = 0;
				if(resultdata.weekProfit != null) {
					total = resultdata.weekProfit["total"];
				}
				$("#weekProfit").text(total);
				
				// 图表
				var labelList = [getDate(-7),getDate(-6),getDate(-5),getDate(-4),getDate(-3),getDate(-2),getDate(-1)];
				var dataList = [];
				if(resultdata.weekProfit != null) {
					dataList = getChartData(resultdata.weekProfit, labelList);
				} else {
					dataList = [0,0,0,0,0,0,0];
				}
				
				var data = {
					labels : labelList,
					datasets : [
						{
							fillColor : "rgba(000,000,000,000)",
							strokeColor : "rgba(49,195,162,1)",
							pointColor : "rgba(49,195,162,1)",
							pointStrokeColor : "#30C3A3",
							data : dataList
						}
					]
				};
				
				var ctx = document.getElementById("myChart").getContext("2d");
				new Chart(ctx).Line(data);
			}
		}
	});
}

function getChartData(weekProfitMap, labelList) {
	var dataList = [];
	for(var i=0; i<labelList.length; i++) {
		var date = labelList[i];
		var gold = weekProfitMap[date];
		if(gold == undefined) {
			if(i == 0) {
				gold = 0;
				weekProfitMap[date] = 0;
			} else {
				var date2 = labelList[i-1];
				gold = weekProfitMap[date2];
				weekProfitMap[date] = gold;
			}
		}
		dataList.push(gold);
	}
	
	return dataList;
}

function loadList() {
	var options = {};
	options.url = _path + "/userJc/list";
	options.pageSize = "5";

	$.pagination("pagination", options, function(data) {
		var content = "";
		if(data.length > 0) {
			var userJc ;
			for(var i = 0; i<data.length; i++) {
				userJc = data[i];
				var gameResult = userJc.gameResult;

            	content += '<tr>';
            	if("1" == gameResult) {
					content += '<td width="4%"><span class="badge2 badge_orange">W</span></td>';
				} else if("0" == gameResult) {
					content += '<td width="4%"><span class="badge2 badge_gray">L</span></td>';
				} else if("3" == gameResult) {
					content += '<td width="4%"><span class="badge2" style="background: #4470B1;">!</span></td>';
				} else {
					content += '<td width="4%"><span class="badge2 badge_green">N</span></td>';
				}
            	
            	content += '<td width="35%"><span style="display: inline-block; width:94px; height:20px; white-space: nowrap;text-overflow: ellipsis;overflow: hidden;" title="'+userJc.homeTeam+'">'+userJc.homeTeam+' </span>';
            	content += '<span style="display: inline-block; width:30px; height:20px; white-space: nowrap; margin: 0 20px;text-overflow: ellipsis;overflow: hidden;">'+userJc.gameRule+'</span>';
            	content += '<span style="display: inline-block; width:94px; height:20px; white-space: nowrap;text-overflow: ellipsis;overflow: hidden;" title="'+userJc.awayTeam+'">'+userJc.awayTeam+'</span>';
            	content += '<p style="color:#607c8b">'+userJc.eventName+'</p></td>';
	            content += '<td width="14%"><p>'+pankouPlay(userJc, userJc.homeTeam, userJc.awayTeam)+'</p></td>';
	            content += '<td width="13%"><p>'+userJc.jcGold+'</p><p style="color:#607c8b">本次下注</p></td>';
	            var jcTeamType = userJc.jcTeamType;// 下注队伍
	            if("1" == gameResult) {// 获胜
	            	content += '<td width="13%"><p style="color:#e4a713">'+(userJc.victoryGoldNum - userJc.jcGold)+'</p><p style="color:#607c8b">本次收益</p></td>';
	            	if("1" == jcTeamType) {// 主场
	            		content += '<td width="13%"><p class="blackish-green">'+userJc.homeTeam+' 获胜</p><p></p></td>';
	            	} else {
	            		content += '<td width="13%"><p class="blackish-green">'+userJc.awayTeam+' 获胜</p><p></p></td>';
	            	}
	            } else if("2" == gameResult) {// 进行中
	            	content += '<td width="13%"><p class="blackish-green">'+userJc.expectGold+'</p><p style="color:#607c8b">预计收益</p></td>';
	            	content += '<td width="13%"><p></p><p class="blackish-green">'+getDifToNow2(userJc.startTime)+'</p></td>';
	            } else if("3" == gameResult) {// 取消
	            	content += '<td width="13%"><p>'+userJc.jcGold+'</p><p style="color:#607c8b">已返还</p></td>';
	            	content += '<td width="13%"><p></p><p style="color:#98999b">已取消</p></td>';
	            } else {
	            	content += '<td width="13%"><p style="color:#df3c3e">'+(userJc.victoryGoldNum - userJc.jcGold)+'</p><p style="color:#607c8b">本次收益</p></td>';
	            	if("2" == jcTeamType) {// 客场
	            		content += '<td width="13%"><p class="blackish-green">'+userJc.homeTeam+' 获胜</p><p></p></td>';
	            	} else {
	            		content += '<td width="13%"><p class="blackish-green">'+userJc.awayTeam+' 获胜</p><p></p></td>';
	            	}
	            }
	            content += '<td width="8%">';
	            content += '<button type="button" class="btn btn-primary btn-raised" style="width:62px;padding:8px 10px" onclick="showDetail(\''+userJc.gbId+'\')">';
	            content += '<strong style="text-aglin:center;">查看</strong>';
	            content += '</button></td>';
				content += '</tr>';
			}
		} else {
			content = '<p style="height: 50px; line-height: 27px; padding: 12px;">暂无数据</p>';
		}
		$("#dataList").html(content);
	})
}

function getObjList() {
	var array = new Array();
	var obj;
	obj = getStaticObj("2","1", "1血", 2, "", "");
	array.push(obj);
	obj = getStaticObj("1","2", "1血", 2, "", "");
	array.push(obj);
	obj = getStaticObj("0","3", "输赢", 2, "", "");
	array.push(obj);
	obj = getStaticObj("3","1", "10杀", 2, "", "");
	array.push(obj);
	
	return array;
}

function getStaticObj(gameResult,inningNum, pkName, pankouType, pkRangFenTeam, pkRangfenNum) {
	var obj = {};
	obj.homeTeam = "EDG";
	obj.awayTeam = "LGD";
	obj.eventName = "2017春季赛";
	obj.gameRule = "BO3";
	obj.startTime = new Date();
	obj.endTime = new Date();
	obj.jcTime = new Date();
	obj.gameImg = "";
	obj.jcGold = "500";
	obj.gbId = "";
	obj.pankouId = "";
	obj.victoryGoldNum = "700";
	if("0" == gameResult) {
		obj.victoryGoldNum = "0";
	}
	obj.jcTeamType = "1";
	obj.expectGold = "190";
	obj.gameResult = gameResult;
	obj.inningNum = inningNum;
	obj.pkName = pkName;
	obj.pankouType = pankouType;
	obj.pkRangFenTeam = pkRangFenTeam;
	obj.pkRangfenNum = pkRangfenNum;

	return obj;
}

function getDifToNow2(long) {
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

function showDetail(gbId) {
	var url = _path + "/gb/" + gbId;
	window.open(url);
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
				var content = "<div class=\"title\">盈利TOP</div>";
				for(var i = 0;i<data.length;i++){
					userEntity = data[i];
					content += '<div class="row mt-10 page-header_2" style="color:white; margin: 0px 10px 0 10px;">';
					if(i==0) {
						content += '<span class="guess fl" style="margin: 0;"></span>';
					} else if(i==1) {
						content += '<span class="fl guess-rank-list media" style="margin: 0;">2</span>';
					} else {
						content += '<span class="fl guess-rank-list" style="margin: 0;">'+(i+1)+'</span>';
					}
                	content += '<span class="fl" style="margin: 0 10px;">';
                	content += '<img src="'+userEntity.userPhoto_65+'" class="img-circle c-img" alt="'+userEntity.nickName+'">';
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
				$("#profitTopList-div").html(content);
			}
		}
	});
}

function getDate(num) {
	if(num == undefined || Number(num) == undefined) {
		num = 0;
	}
	var date = new Date();
	date = date.valueOf() + num * 24 * 60 * 60 * 1000;
	date = new Date(date);
	return getFormatDate(date, "MM-dd");
};



