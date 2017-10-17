function initMyAccount(_first) {
	var nickName = cookie_nick_name;
	var userPhoto = cookie_user_photo;
	if(userPhoto==""||userPhoto==null){
		userPhoto = _path + "/resources/wodota/images/denglu_n.png";
	}else{
		if (userPhoto.indexOf('.') >= 0) {
			var ph = userPhoto.split('.');
			userPhoto = ph[0] + "_170." + ph[1];
		}
		userPhoto = _path + userPhoto;
	}
	if ($("#right_userPhoto_r").attr("src") == "" || $("#right_userPhoto_r").attr("src") == null) {
		$("#right_userPhoto_r").attr("src", userPhoto);
		$("#right_nickName_r").html("hi," + nickName);
	}
	
	// 验证今天签到
	//right_isSign();
	var dailyTaskNum = 0;
	var completedDailyTaskNum =0;
	// 用户签到
	$('#right_wqd').off("click");
	$('#right_wqd').click(function(e) {
	    userSign('right');
	    right_dailyTask();
	    e.stopPropagation();
	});
	
	// 右边钱包数据
	if ($("#headerMenuGold").text() != '') {
		$("#right_walletGold").html($("#headerMenuGold").text());
	} else {
		$("#right_walletGold").html(0);
	}
	// 右边体验币
	if ($("#headerMenuSysGold").text() != '') {
		$("#right_sysGold").html($("#headerMenuSysGold").text());
	} else {
		$("#right_sysGold").html(0);
	}
	
	// 右边显示正在竞猜的G币
	if ($("#jcGold").text() != '') {
		$("#right_jcGold").html($("#jcGold").text());
	} else {
		$("#right_jcGold").html("0 金币");
	}
	// 右边签到天数
	if ($("#userSignDays").text() != '') {
		$("#right_signDays").html($("#userSignDays").text());
	} else {
		$("#right_signDays").html(0);
	}
	
	$("#right_recharge").off("click");
	$("#right_recharge").click(function(e) {
		rechargeAmountList('right');
	});
	// 每日任务
	right_dailyTask();
	right_transaction(_first);
}

function freeGetGold() {
	$('.inc_right_tabs_task').click();
}
// 你的账号，右侧每日任务显示
function right_dailyTask() {
	$.ajax({
		type : "POST",
		url : _path + "/my/userTask/right",
		data :{
			source : "account"
		},
		dataType : "json",
		success : function(data) {
			dailyTask = data.dailyTask;
			dailyTaskNum = data.dailyTaskNum;
			completedDailyTaskNum = data.completedDailyTaskNum;
			$("#completeTaskNum").html("每日完成任务(<span id='completedDaily'>" + completedDailyTaskNum + "</span>/" + dailyTaskNum + ")");
			if (dailyTask) {
				$("#dailyTask").removeClass("hide");
				$("#congratulationDailyTask").addClass("hide");
				var dailyTaskVo = data.dailyTaskVo;
				if (dailyTaskVo != null) {
					var completeNum = 0;
					if (dailyTaskVo.userTask && dailyTaskVo.userTask.completeNum > 0) {
						completeNum = dailyTaskVo.userTask.completeNum;
					}
					if (dailyTaskVo.task.taskType == 0) {//签到
						if (dailyTaskVo.userTask && (dailyTaskVo.userTask.completedState == 1 || dailyTaskVo.userTask.completedState == 2)) {
							$("#right_yqd").removeClass("hide");
							$("#right_wqd").addClass("hide");
						} else {
							$("#right_yqd").addClass("hide");
							$("#right_wqd").removeClass("hide");
						}
						$("#right_dailyTask").addClass("hide");
					} else {// 非签到
						$("#right_yqd").addClass("hide");
						$("#right_wqd").addClass("hide");
						$("#right_dailyTask").removeClass("hide");
						$("#right_dailyTask").find(".view1").css("width",completeNum*100/dailyTaskVo.task.num+'%');
					}
					$("#right_signGiveGold").html(dailyTaskVo.task.taskName + "(" +completeNum+ "/" + dailyTaskVo.task.num +")");
					var giveGold = '';
					if (dailyTaskVo.task.taskType == 0) {
						giveGold = '1~30';
					} else {
						giveGold = dailyTaskVo.task.gold;
					}
					$("#right_completedGive").html(giveGold);
				}
			} else {
				$("#congratulationDailyTask").removeClass("hide");
				$("#dailyTask").addClass("hide");
			}
			
		},
		complete : function() {
			
		}
	});
}
//验证用户今天是否已经签到
function right_isSign() {
	$.ajax({
		type : "POST",
		url : _path + "/my/isSign",
		dataType : "json",
		success : function(data) {
			if (data >= 0) { // 已经签到
				$('#wqd').addClass("hide");
			    $('#yqd').removeClass("hide");
			    $("#signGiveGold").html("今天签到已获赠" + data + "G币");
			    
			    // 右边侧边签到情况
			    $('#right_wqd').addClass("hide");
			    $('#right_yqd').removeClass("hide");
			    $("#right_signGiveGold").html("每日签到，领取金币(1/1)");/*今天签到已获赠<span>" + data + "</span>G币*/
			}else{
				$('#wqd').removeClass("hide");
			    $('#yqd').addClass("hide");
			    
			    // 右边情况
			    $('#right_wqd').removeClass("hide");
			    $('#right_yqd').addClass("hide");
			}
		},
		complete : function() {
			
		}
	});
}

function right_salesAll() {
	var userId = $("#userId").val();
	if(userId == "" || userId == undefined) {
		$(".inc_right_tabs_account .inc_right_tabs_inside").after($("#inc_right_login"));
        $("#inc_right_login").css({"top":"","bottom":"-395px"}).removeClass("hide");
		return;
	}
	layer.confirm("是否全部出售？", {icon : 3, title : "出售提示"}, function(index) {
		$.ajax({
			url: _path + "/my/exchangeLog/updateStatusForSales",
			data: {},
			type: "post",
			dataType: "json",
			success: function(callBack) {
				if(callBack.success) {
					layer.alert("出售成功，请等待。。。", {icon: 1,shift: 6,time: 1000});
				} else {
					layer.alert(callBack.message, {icon: 5,shift: 6,time: 0});
				}
			},
			error: function() {
				
			}
		});
		layer.close(index);
	});
}

function right_buyAll() {
	var userId = $("#userId").val();
	if(userId == "" || userId == undefined) {
		$(".inc_right_tabs_account .inc_right_tabs_inside").after($("#inc_right_login"));
        $("#inc_right_login").css({"top":"","bottom":"-395px"}).removeClass("hide");
		return;
	}
	layer.confirm("是否全部兑换？", {icon : 3, title : "兑换提示"}, function(index) {
		$.ajax({
			url: _path + "/my/exchangeLog/buyAll",
			data: {},
			type: "post",
			dataType: "json",
			success: function(callBack) {
				if(callBack.success) {
					var deductGold = callBack.deductGold;
					updateUserGold(3, -1*deductGold, null);
					layer.alert("购买成功，请等待。。。", {icon: 1,shift: 6,time: 1000});
				} else {
					layer.alert(callBack.message, {icon: 5,shift: 6,time: 0});
				}
			}
		});
		layer.close(index);
	});
}

var first = true;
var pageNum = 1;
function right_transaction(_first) {
	if(_first != undefined && _first) {// 第一次点击，初始化
		first = _first;
		pageNum = 1;
	}
	var url = _path + "/my/transactionLog/list";
	var dataJson = {};
	dataJson.pageSize = "5";
	dataJson.nowPage = pageNum;// 页码
	pageNum = pageNum + 1;// 下一页
	
	var parameters = {};
	parameters.orderBy = "";
	
	dataJson.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(dataJson)}, "post", false, function(backData) {
		var content = '';
		
		var data = backData.list;
		if(data.length > 0) {
			var transactionLog ;
			for(var i = 0; i<data.length; i++) {
				transactionLog = data[i];
				var date = getSmpFormatDateByLong(Number(transactionLog.utTime), true);
				
				content += '<li>';
				content += '<span class="span1">'+date.substring(0,date.length-3)+'</span>';
				content += '<span class="span2">'+getTypeName(transactionLog.utType)+'</span>';
				if(transactionLog.goldNum < 0) {
					content += '<span class="span3">'+transactionLog.goldNum+transactionLog.goldTypeName+'</span>';
				} else {
					content += '<span class="span3">+'+transactionLog.goldNum+transactionLog.goldTypeName+'</span>';
				}
				content += '</li>';
			}
		}  else if(first) {
			content += "";
		}
		if(first) {
			$("#r_transaction").html(content);
		} else {
			$("#r_transaction").append(content);
		}
		
		var pageCount = backData.pageCount;
		if(data.length > 0 && pageCount >= pageNum) {
			first = false;
			$("#transactionMore").show();
			$("#transactionNoMore").hide();
		} else {
			$("#transactionMore").hide();
			$("#transactionNoMore").show();
		}
	});
	
}

function getTypeName(typeId) {
	var typeName = "";
	if("1" == typeId) {
		typeName = "竞猜";
	} else if("2" == typeId) {
		typeName = "充值";
	} else if("3" == typeId) {
		typeName = "兑换";
	} else if("4" == typeId) {
		typeName = "推荐好友奖励";
	} else if("5" == typeId) {
		typeName = "用户签到";
	} else if("6" == typeId) {
		typeName = "充值奖励";
	} else if ("7" == typeId) {
		typeName = "注册赠送";
	} else if ("8" == typeId) {
		typeName = "分享领取";
	} else if ("9" == typeId) {
		typeName = "提现";
	} else if ("10" == typeId) {
		typeName = "CD-KEY兑换";
	} else if ("11" == typeId) {
		typeName = "任务奖励";
	} else if ("12" == typeId) {
		typeName = "roll奖品";
	} else if ("13" == typeId) {
		typeName = "翻硬币";
	} else {
		typeName = "未知";
	}
	
	return typeName;
}