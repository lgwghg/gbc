$(function() {
	loadCoinflipRoom();
	$("#createCoinflipRoomBtn").click(function() {
		initCreatRoom();
	});
	/*$("#createRoomBtn").click(function() {
		createRoom();
	});*/
	// 创建房间监听
	roomCreateSocket();
	// 房间参与者加入监听
	joinnerSocket();
	// 房间过期监听
	deleteSocket();
	// 开奖监听
	openSocket();
	// 加入按钮隐藏和显示监听
	joinBtnHideOrShow();
	
	var hideCoinMessage = sessionStorage.getItem("hideCoinMessage");
	if(hideCoinMessage!='true'){
		
		setTimeout(function(){
			$("#coinmessage-close").parent().removeClass("hide");
        },250)
		
		$("#coinmessage-close").click(function(){
			sessionStorage.setItem("hideCoinMessage","true");
			$(this).parent().remove();
		})
	}
	
	// 创建房间弹框中，选择正反方事件
	$(".typeselect .imgwrap").click(function(){
		$(this).addClass("xzactive").siblings().removeClass("xzactive");
	});
});

/**
 * 初始化创建房间弹框中的创建按钮事件
 */
function initcreateEvent() {
	$("#createRoomBtn").click(function() {
		createRoom();
	});
}

/**
 * 游戏列表中，加入按钮和查看按钮事件初始化
 */
function initEvent() {
	// 列表中加入按钮点击
	$(".joinBtn").off("click").on("click",function() {
		if (user ==null || user=='') {
			$("#myModal").modal("show");//登录弹框
			return;
		}
		var minNeedGold = 1000;
		var ownerGoldNum = $(this).attr("ownerGoldNum");
		var joinnersNum = $(this).attr("joinnersNum");
		var _this = $(this);
		if (ownerGoldNum < 100000) {
			minNeedGold = ownerGoldNum;
		} else {
			minNeedGold = 10000;
			if (joinnersNum) {
				if (joinnersNum == 3) {
					minNeedGold = room.ownerGoldNum - (room.totalGoldNum - room.ownerGoldNum);
				}
			}
		}
		
		$.ajax({
			type : "POST",
			url : _path + "/my/wallet/gold",
			async :false,
			dataType : "json",
			success : function(data) {
				if (data != null) {
					if (data.totalGold != null) {
						if (data.totalGold < minNeedGold) {
							layer.confirm('您的金币不足，请先充值', {icon: 3, title:'充值提醒'}, function(index){
	    			    		if(index > 0) {
	    			    			$("#headerRecharge").click();
	    			    		}
	    			    		layer.close(index);
	    			    	});
						} else {
							var roomId = _this.attr("roomId");
							$("#joinRoomId").val(roomId);
							if (_this.hasClass("icon-mima")) {
								$("#joinCoinflip #joinPassword").val('');
								$("#joinCoinflip .addmm").show();
								$("#joinCoinflip .addhuman").hide();
								// 输入房间密码，点击加入按钮
								$("#joinPasswordBtn").click(function() {
									var joinPassword = $("#joinPassword").val();
									$.ajax({
										url : _path + "/coinflip/u/checkRoomPwd",
										type: "POST",
										data : {
											"roomId" : roomId,
											"password" : joinPassword
										},
										dataType : "json",
										success : function(data) {
											if (data) {
												if (data.success) {
													$("#joinCoinflip .addmm").hide();
													$("#joinCoinflip .addhuman").show();
													initViewRoom(roomId, 1);
												} else {
													$("#joinPasswordError").find("code").html(data.msg);
													$("#joinPasswordError").show();
													return;
												}
											}
										},
										complete : function() {
											
										},
										error: function (jqXHR, textStatus, errorThrown){
											ajaxToLogin(jqXHR);
										}
									});
								});
							} else {
								$("#joinCoinflip .addmm").hide();
								$("#joinCoinflip .addhuman").show();
								initViewRoom(roomId, 1);
							}
							$("#joinCoinflip").modal({backdrop:"static"});
							$("#joinCoinflip").modal("show");
							
							$("#joinCoinflip .close").off('click');
							$("#joinCoinflip .close").click(function() {
								var roomId= $("#joinCoinflip #joinRoomId").val();
								closeTimer('tanjishi_j_' + roomId);// 关闭弹框计时器
								noticeJoinBtn(roomId, "show");
							});
							
							
							
							
						}
					} else {
						layer.confirm('您的金币不足，请先充值', {icon: 3, title:'充值提醒'}, function(index){
    			    		if(index > 0) {
    			    			$("#headerRecharge").click();
    			    		}
    			    		layer.close(index);
    			    	});
					}
				}
			},
			complete : function() {
				
			},
			error: function (jqXHR, textStatus, errorThrown){
				ajaxToLogin(jqXHR);
			}
		});
		
		
	});
	
	// 列表中查看按钮点击
	$(".viewBtn").off("click").on("click",function() {
		var roomId = $(this).attr("roomId");
		$("#viewRoomId").val(roomId);
		initViewRoom(roomId, 0);
		$("#viewCoinflip").modal({backdrop:"static"});
		$("#viewCoinflip").modal("show");
		
	});
	
}


var joinFlag = true;
/**
 * 加入房间
 * @param roomId
 */
function joinRoom(roomId) {
	if (user ==null || user=='') {
		$("#myModal").modal("show");//登录弹框
	}
	if (joinFlag) {
		joinFlag = false;
		var betGoldNum = $("#joinCoinflip #ex7").val();
		$.ajax({
			url : _path + "/coinflip/u/joinRoom",
			type: "POST",
			data : {
				"roomId" : roomId,
				"goldNum" : betGoldNum
			},
			dataType : "json",
			success : function(data) {
				if (data) {
					if (data.success) {
						/*var joinner = data.joinner;
					var thisGoldNum = parseInt(joinner.goldNum);
					var totalGoldNum = parseInt($("#totalGoldNum").text()) + thisGoldNum;
					$("#totalGoldNum").html(totalGoldNum);
					
					var content = '';
					content +=			'<div class="kesitem">';
					content +=				'<div class="imgwrap">';
					content +=					'<img src="' + joinner.userPhoto + '" alt="" width=100% height=100%>';
					content +=				'</div>';
					content +=				'<div class="text">';
					content +=					'<span>' + joinner.userNick + '</span>';
					content +=				'</div>';
					content +=			'</div>';
					$("#room_" + joinner.roomId + " .kes").append(content);
					$("#room_" + joinner.roomId + " .vs").html('<span>vs</span>');
					var roomBeted = parseInt($("#room_" + joinner.roomId + " .rc-goldwarp span").text()) + thisGoldNum ;
					$("#room_" + joinner.roomId + " .rc-goldwarp span").html(roomBeted);
					
					$("#room_" + joinner.roomId + " .joinBtn").hide();
					
					var joinTime = joinner.createTime;*/
						$("#joinCoinflip").modal("hide");
						$("#viewBtn_" + roomId).click();
						$("#room_" + roomId + " .joinBtn").remove();
						updateUserGold(2, -betGoldNum);
					} else {
						layer.alert(data.msg, {icon: 5,shift: 6,time: 0});
					}
				}
			},
			complete : function() {
				joinFlag = true;
			},
			error: function (jqXHR, textStatus, errorThrown){
				ajaxToLogin(jqXHR);
			}
		});
		
	}
}


/**
 * 页面初始化，正在进行中的游戏
 */
function loadCoinflipRoom() {
	$.ajax({
		url : _path + "/coinflip/playing",
		type: "POST",
		dataType : "json",
		success : function(data) {
			if (data) {
				var content = "";
				if (data.playingNum) {
					$("#playingNum").html(data.playingNum);
					$("#totalGoldNum").html(data.totalGoldNum);
					var roomList = data.roomList;
					var playingNum = data.playingNum;
					for (var i=0; i < playingNum; i++) {
						var room = roomList[i];
						content = content + initRoomDiv(room);
					}
					$("#roomList").html(content);
					initEvent();
					
					for (var i=0; i < playingNum; i++) {
						var room = roomList[i];
						var now = data.now;
						if (room.status == 1) {
							var seconds = disSeconds(now, room.joinTime);
							if (seconds > 0) {
								if (seconds < 60) {
									// 倒计时
									/*$.fn.circleJt({
										domId:'jishi_' + room.id,//必须
										value: seconds,//必须
										totalValue: 60,//10是绿，99是红
										percentage:true,//非必需
									});*/
									startTimer('jishi_' + room.id, seconds, 60);
								}
							}
							
						}
					}
				}else{
					content+='<div class="rc-listitem nothing"><div class="text"><span>暂无游戏</span></div></div>';
					$("#roomList").html(content);
				}
			}
		}
	});
}

/**
 * 创建游戏房间弹框初始化
 */
function initCreatRoom() {
	if(user!=null && user!=''){
		$.ajax({
			type : "POST",
			url : _path + "/my/wallet/gold",
			async :false,
			dataType : "json",
			success : function(data) {
				if (data != null) {
					if (data.totalGold) {
						if (data.totalGold <1000) {
							layer.confirm('您的金币不足，请先充值', {icon: 3, title:'充值提醒'}, function(index){
	    			    		if(index > 0) {
	    			    			$("#headerRecharge").click();
	    			    		}
	    			    		layer.close(index);
	    			    	});
						} else {
							$("#createCoinflipRoom").modal("show");
							$("#createRoomBtn").removeAttr("disabled");
							initcreateEvent();
							$(".typeselect .zheng").click();
							//$("#isSetPassword").removeAttr("checked");
							$("#roomPassword").val('');
							//$("#betGoldNum").attr("data-slider-value", 1000);
							//$("#betGoldNum").val(1000);
							var max = 500000;
							if (data.totalGold <= 500000) {
								//$("#betGoldNum").attr("data-slider-max", data.gold);
								max = data.totalGold;
							} else {
								//$("#betGoldNum").attr("data-slider-max", 500000);
							}
							initSlider(1000,1000,max);
							/*$("#createCoinflipRoom #betGoldNum").slider();
							$("#createCoinflipRoom #betGoldNum").on("slide click", function(slideEvt) {
								$(".ex6SliderVal").text(slideEvt.value);
							});*/
						}
					} else {
						layer.confirm('您的金币不足，请先充值', {icon: 3, title:'充值提醒'}, function(index){
				    		if(index > 0) {
				    			$("#headerRecharge").click();
				    		}
				    		layer.close(index);
				    	});
					}
				} 
			},
			complete : function() {
				
			},
			error: function (jqXHR, textStatus, errorThrown){
				ajaxToLogin(jqXHR);
			}
		});
	} else {
		$("#myModal").modal("show");//登录弹框
	}
}

var createFlag = true;
/**
 * 创建游戏房间提交
 */
function createRoom() {
	$("#createRoomBtn").attr("disabled","disabled");
	$("#createRoomBtn").off("click");
	if (!createFlag) {
		return;
	}
	createFlag = false;
	var isCoinFront = '';
	if ($(".typeselect .xzactive").hasClass("zheng")) {
		isCoinFront = 1;
	} else {
		isCoinFront = 0;
	}
	var roomPassword = $.trim($("#roomPassword").val());
	if (roomPassword != '') {
		if (!/^[a-zA-Z0-9]+$/.test(roomPassword)) {
			$("#roomPasswordError").find("code").show();
			createFlag = true;
			$("#createRoomBtn").removeAttr("disabled");
			initcreateEvent();
			return;
		}
		if (roomPassword.length > 6) {
			$("#roomPasswordError").find("code").show();
			$("#createRoomBtn").removeAttr("disabled");
			createFlag = true;
			initcreateEvent();
			return;
		}
	}
	var betGoldNum = $("#betGoldNum").val();
	if (betGoldNum < 1000 || betGoldNum > 500000) {
		layer.alert("单词下注最少1000个金币，最多500000个金币", {icon: 5,shift: 6,time: 0});
		createFlag = true;
		$("#createRoomBtn").removeAttr("disabled");
		initcreateEvent();
		return;
	}
	
	$.ajax({
		url : _path + "/coinflip/u/createRoom",
		type: "POST",
		data: {
			"roomOwner.isCoinFront" : isCoinFront,
			"password" : roomPassword,
			"ownerGoldNum" : betGoldNum
		},
		dataType: "json",
		async :false,
		success : function(data) {
			if (data.success) {
				$("#createCoinflipRoom").modal("hide");
				updateUserGold(2, -betGoldNum);
				/*var room = data.room;
				var content = initRoomDiv(room);
				var thisOwnerGoldNum = parseInt(room.ownerGoldNum);
				var totalGoldNum = parseInt($("#totalGoldNum").text()) + thisOwnerGoldNum;
				$("#totalGoldNum").html(totalGoldNum);
				var playingNum = parseInt($("#playingNum").text()) + 1;
				$("#playingNum").html(playingNum);
				
				$(".rc-listitem").each(function(i) {
					var goldNum = parseInt($(this).attr("ownerGoldNum"));
					if (thisOwnerGoldNum >= goldNum) {
						$(this).before(content);
						return false;
					}
				});
				initEvent();*/
			}
		},
		complete : function() {
			createFlag = true;
			$("#createRoomBtn").removeAttr("disabled");
		},
		error: function (jqXHR, textStatus, errorThrown){
			ajaxToLogin(jqXHR);
		}
		
	});
}
/**
 * 列表中每间房间的html组装
 * @param room
 * @param create  用于创建的给特效所用
 * @returns {String}
 */
function initRoomDiv(room, create) {
	var content = "";
	var roomOwner = room.roomOwner;
	var roomJoinners = room.roomJoinners;
	if (create) {
		content +='<div class="rc-listitem" ownerGoldNum="' + room.ownerGoldNum + '" id="room_' + room.id + '" roomId="' + room.id + '">';
	} else {
		content +='<div class="rc-listitem rc-listitembefore" ownerGoldNum="' + room.ownerGoldNum + '" id="room_' + room.id + '" roomId="' + room.id + '">';
	}
	// 玩家start
	content +=	'<div class="gamerwrap">';
	content +=	'<div class="gamer">';
	// 房主信息
	content +=		'<div class="zhu">';
	content +=			'<div class="imgwrap">';
	content +=				'<img src="' + roomOwner.userPhoto + '" alt="" width=100% height=100%>';
	content +=			'</div>';
	content +=			'<div class="text">';
	content +=				'<span title="'+ roomOwner.userNick +'">' + roomOwner.userNick + '</span>';
	content +=			'</div>';
	content +=		'</div>';
	// 中间是vs还是硬币正反面，状态决定
	content +=		'<div class="vs">';
	if (room.status == 0) {//初始状态
		content += '<div class="imgwrap">';
		if (roomOwner.isCoinFront == 1) {
			content += '<img src="' + _staticPrefix + '/images/zheng.png" alt="" width=100% height=100%>';
		} else {
			content += '<img src="' + _staticPrefix + '/images/fan.png" alt="" width=100% height=100%>';
		}
		content += '</div>';
	} else {
		content +=			'<span>vs</span>';
	}
	content +=		'</div>';
	
	// 加入者信息
	content +=		'<div class="kes">';
	var isJoin = false;
	for (var j = 0; j < roomJoinners.length; j++) {
		var joinner = roomJoinners[j];
		if (userId != null && userId != '' && joinner.userId == userId) {
			isJoin = true;
		}
		content +=			'<div class="kesitem" userId="' + joinner.userId + '">';
		content +=				'<div class="imgwrap">';
		content +=					'<img src="' + joinner.userPhoto + '" alt="" width=100% height=100%>';
		content +=				'</div>';
		content +=				'<div class="text">';
		content +=					'<span title="'+ joinner.userNick +'">' + joinner.userNick + '</span>';
		content +=				'</div>';
		content +=			'</div>';
	}
	content +=		'</div>';
	content +=	'</div>';// gamer end
	content +=	'</div>';// gamerwrap end 玩家end
	
	// 金币信息 start
	content += '<div class="rc-gotwrap">';
	content += '<div class="rc-got">';
	content +=	'<div class="rc-goldwarp">';
	content +=		'<div class="imgwrap">';
	content +=			'<img src="' + _staticPrefix + '/images/gold-prize.jpg" alt="" width=100% height=100%>';
	content +=		'</div>';
	content +=		'<div class="text">';
	content +=			'<span>' + room.totalGoldNum + '</span>';
	content +=		'</div>';
	content +=	'</div>';
	
	// 游戏结果 start
	content +=	'<div class="other">';
	content +=		'<div class="timer"><div id="jishi_' + room.id + '">';
	//content +=			'<canvas width="48" height="48">111</canvas>';
	content +=		'</div><div id="openjishi_' + room.id + '"></div></div>';
	content +=		'<div class="winer">';
	/*content +=			'<div class="imgwrap">';
	content +=				'<img src="images/nvnv.png" alt="" width=100% height=100%>';
	content +=			'</div>';
	content +=			'<div class="imgwrap">';
	content +=				'<img src="images/nvnv.png" alt="" width=100% height=100%>';
	content +=			'</div>';
	content +=			'<div class="imgwrap">';
	content +=				'<img src="images/nvnv.png" alt="" width=100% height=100%>';
	content +=			'</div>';
	content +=			'<div class="imgwrap">';
	content +=				'<img src="images/nvnv.png" alt="" width=100% height=100%>';
	content +=			'</div>';*/
	content +=		'</div>';
	content +=		'<div class="typer">';
	content +=			'<div class="imgwrap">';
	//content +=				'<img src="images/nvnv.png" alt="" width=100% height=100%>';
	content +=			'</div>';
	content +=		'</div>';
	content +=	'</div>';// 游戏结果 end
	// 按钮
	content +=	'<div class="btn-group">';
	var hasPassword = '';
	if (room.hasPassword != null && room.hasPassword == 1) {
		hasPassword = 'iconfont icon-mima';
	}
	if (room.status == 0) {
		var joinnersNum = 0;
		if (roomJoinners) {
			joinnersNum = roomJoinners.length;
		}
			
		// 登录状态下，1. 自己是房主的房间不展示加入按钮；2.自己已经加入的房间不展示加入按钮
		if (userId != null && userId != '') {
			if (roomOwner.userId != userId && !isJoin) {
				content +=		'<button type="button" class="btn joinBtn ' + hasPassword + '" roomId="' + room.id + '" ownerGoldNum="' + room.ownerGoldNum + '" joinnersNum = "' + joinnersNum + '">加入</button>';//data-toggle="modal" data-target="#joinCoinflip"
			}
		} else {
			content +=		'<button type="button" class="btn joinBtn ' + hasPassword + '" roomId="' + room.id + '" ownerGoldNum="' + room.ownerGoldNum + '" joinnersNum = "' + joinnersNum + '">加入</button>';//data-toggle="modal" data-target="#joinCoinflip"
		}
		
	}
	content +=		'<button type="button" class="btn viewBtn" roomId="' + room.id + '" id="viewBtn_' + room.id + '">查看</button>';//data-toggle="modal" data-target="#viewCoinflip"
	content +=	'</div>';
	
	content +='</div>'
	content +='</div>'
		
	content +='</div>';
	
	return content;
}
