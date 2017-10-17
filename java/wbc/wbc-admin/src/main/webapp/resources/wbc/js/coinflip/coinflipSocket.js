/**
 * 房间创建监听
 */
function roomCreateSocket() {
	socket = io.connect(_socketAddress);
	socket.on("coinfliproom",function(data) {
		var room = JSON.parse(data);
		var thisOwnerGoldNum = parseInt(room.ownerGoldNum);
		var totalGoldNum = parseInt($("#totalGoldNum").text()) + thisOwnerGoldNum;
		$("#totalGoldNum").html(totalGoldNum);
		var playingNum = parseInt($("#playingNum").text()) + 1;
		$("#playingNum").html(playingNum);
		var content = initRoomDiv(room, true);
		if ($(".rc-listitem.nothing").length != 0) {
			$("#roomList").html(content);
		} else {
			$(".rc-listitem").each(function(i) {
				var goldNum = parseInt($(this).attr("ownerGoldNum"));
				if (thisOwnerGoldNum >= goldNum) {
					$(this).before(content);
					return false;
				}
			});
		}
		$("#room_" + room.id).animate({
			width: "100%"
		},1);
		$("#room_" + room.id).addClass('rc-listitembefore');
		initEvent();
	});
}

/**
 * 房间有人参与游戏提交监听
 */
function joinnerSocket() {
	socket = io.connect(_socketAddress);
	socket.on("coinflipuser",function(data) {
		var joinner = JSON.parse(data);
		var thisGoldNum = parseInt(joinner.goldNum);
		var totalGoldNum = parseInt($("#totalGoldNum").text()) + thisGoldNum;
		$("#totalGoldNum").html(totalGoldNum);
		
		// 列表处理
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
		
		var ownerGoldNum = parseInt($("#room_" + joinner.roomId + " .joinBtn").attr("ownerGoldNum"));
		if (roomBeted == ownerGoldNum *2) {
			//$("#room_" + joinner.roomId + " .joinBtn").hide();
			$("#room_" + joinner.roomId + " .joinBtn").remove();
		}
		
		// 查看弹框处理
		if ($("#viewCoinflip #viewRoomId").val() == joinner.roomId) {
			var addJoin = true;
			if ($("#viewCoinflip .humanright .humanitem").length > 0) {
				$("#viewCoinflip .humanright .humanitem").each(function() {
					var uid = $(this).attr("uid");
					if (uid == joinner.userId) {
						addJoin = false;
					}
				});
			}
			if (addJoin) {
				var joinners = [];
				joinners[0] = joinner;
				var content = dialogJoinnersDiv(joinners);
				$("#viewCoinflip .humanright .human").prepend(content);
			}
		}
		
	});
}


/**
 * 房间过期删除监听
 */
function deleteSocket() {
	socket = io.connect(_socketAddress);
	socket.on("deleteRoom",function(data) {
		var room = JSON.parse(data);
		var roomId = room.id;
		// 移除列表中的房间数据
		$("#room_" + roomId).remove();
		
		// 重新计算总下注金币数和总游戏数
		var totalGoldNum = parseInt(room.totalGoldNum);
		var totalGoldNum = parseInt($("#totalGoldNum").text()) - totalGoldNum;
		if (!totalGoldNum) {
			totalGoldNum = 0;
		}
		$("#totalGoldNum").html(totalGoldNum);
		
		var playingNum = parseInt($("#playingNum").text()) - 1;
		$("#playingNum").html(playingNum);
		
		closeTimer("jishi_" + roomId);
		
		// 正在加入的，关闭加入弹框
		if ($("#joinCoinflip #joinRoomId").val() == roomId) {
			$("#joinCoinflip").modal("hide");
			closeTimer("tanjishi_j_" + roomId);
		}
		// 有人在查看，关闭查看弹框
		if ($("#viewCoinflip #viewRoomId").val() == roomId) {
			$("#viewCoinflip").modal("hide");
			closeTimer("tanjishi_" + roomId);
		}
	});
}

/**
 * 开奖倒计时监听
 */
function openSocket() {
	socket = io.connect(_socketAddress);
	socket.on("openRoom",function(data) {
		var room = JSON.parse(data);
		var roomId = room.id;
		$("#room_" + room.id + " .joinBtn").remove();
		$("#jishi_" + room.id).hide();
		//startTimer('jishi_'+roomId, 60, 60);//结束列表里的考虑倒计时
		/*var jishi =document.createElement("div");
		jishi.setAttribute("id", "#jishi_" + room.id);
		$("#room_" + room.id + " .timer").append(jishi);*/
		// 倒计时
		closeTimer("tanjishi_j_" + roomId);
		closeTimer("jishi_" + roomId);
		closeTimer("tanjishi_" + roomId);
		closeTimer('opentanjishi_' + roomId);
		startTimer('openjishi_'+roomId, 0, 10);
		// 倒计时   查看弹框中已有
		if ($("#viewCoinflip #viewRoomId").val() == room.id) {
			$("#tanjishi_" + roomId).hide();
			if ($('#opentanjishi_' + roomId + " cancvs").length <= 0) {
				startTimer('opentanjishi_' + roomId, 0, 10);
			}
		}
		// 对应房间倒计时
		setTimeout(function() {
			closeTimer("openjishi_" + roomId);
			closeTimer("opentanjishi_" + roomId);
			openRoom(roomId);
		},10100);
		
		
	});
}

/**
 * 开奖
 * @param roomId
 */
function openRoom(roomId) {
	$.ajax({
		url : _path + "/coinflip/openRoom",
		type: "POST",
		data: {
			"roomId" : roomId
		},
		dataType: "json",
		async :false,
		success : function(data) {
			if (data && data.success) {
				var room = data.room;
				// 列表处理
				var coinImg = '';
				var rollClass = '';
				if (room.winner == 1) {
					coinImg = _staticPrefix + "/images/zheng.png";
					rollClass = 'rollzheng';
				} else {
					coinImg = _staticPrefix + "/images/fan.png";
					rollClass = 'rollfan';
				}
				var content = '';
				var winners = room.winners;
				var currentUserWin = false;
				var winGoldNum = 0;
				for (var i=0; i < winners.length; i++) {
					var win = winners[i];
					content +=			'<div class="imgwrap">';
					content +=				'<img src="' + win.userPhoto + '" alt="" width=100% height=100%>';
					content +=			'</div>';
					if (userId == win.userId) {
						currentUserWin = true;
						winGoldNum = win.winGoldNum;
					}
				}
				
				setTimeout(function(){
					// 列表
					$("#room_" + roomId+ " .typer .imgwrap").html('<img src="' + coinImg + '" alt="" width=100% height=100%>');
					$("#room_" + roomId+ " .winer").html(content);
					// 更新页面钱包
					if (currentUserWin) {
						updateUserGold(3, winGoldNum);
					}
				},4000);
				
				if ($("#viewCoinflip #viewRoomId").val() == room.id) {
					// 弹框处理
					$("#viewCoinflip #opentanjishi_" + room.id).html('');
					$("#viewCoinflip #opentanjishi_" + room.id).closest(".timerwatch").find('.anim-wrap').show();
					$("#viewCoinflip #opentanjishi_" + room.id).closest(".timerwatch").find('.anim-wrap').addClass(rollClass);
					$("#viewCoinflip .coin").html('<img src="' + coinImg + '" alt="" width=100% height=100%>');
					
					var hashContent = '';
					if (room.hashCode != '' && room.hashCode != null) {
						hashContent +=	'<p><span>HASH:<span class="vue">' + room.hashCode +'</span></span></p>';
					}
					if (room.random != '' && room.random != null) {
						hashContent +=	'<p><span>RANDOM:<span class="vue">' + room.random*100 + '%</span></span></p>';
					}
					if (room.key != '' && room.key != null) {
						hashContent +=	'<p><span>KEY:<span class="vue">' + room.key + '</span></span></p>';
					}
					setTimeout(function(){
						// 弹框
						$("#viewCoinflip .coin").show();
						$("#viewCoinflip .hashwrap").html(hashContent);
					},4000);
				}
				
				
				// 重新计算总下注金币数和总游戏数
				var totalGoldNum = parseInt(room.totalGoldNum);
				var totalGoldNum = parseInt($("#totalGoldNum").text()) - totalGoldNum;
				if (!totalGoldNum) {
					totalGoldNum = 0;
				}
				$("#totalGoldNum").html(totalGoldNum);
				
				var playingNum = parseInt($("#playingNum").text()) - 1;
				$("#playingNum").html(playingNum);
				
			}
			
		},
		complete : function() {
			
		}
	});
}

/**
 * 加入按钮隐藏还是显示监听
 */
function joinBtnHideOrShow() {
	socket = io.connect(_socketAddress);
	socket.on("joinBtn",function(data) {
		var btn = JSON.parse(data);
		var roomId = btn.roomId;
		var show = btn.joinBtn;
		if (show == 'show') {
			var userBeted = false;//用户是否已经下注
			$("#room_" + roomId + " .kesitem").each(function() {
				var uid = $(this).attr("userId");
				if (userId = uid) {
					userBeted = true;
				}
			});
			if (!userBeted) {
				$("#room_" + roomId + " .joinBtn").show();
			}
			closeTimer('jishi_' + roomId);// 关闭列表计时器
			// 有人在查看， 查看弹框关闭考虑倒计时
			if ($("#viewCoinflip #viewRoomId").val() == roomId) {
				closeTimer('tanjishi_'+roomId);
			}
		} else if (show == 'hide') {
			$("#room_" + roomId + " .joinBtn").hide();
			// 列表倒计时
			startTimer('jishi_'+roomId, 0, 60);
			// 有人在查看，查看弹框开启加入考虑倒计时
			if ($("#viewCoinflip #viewRoomId").val() == roomId) {
				startTimer('tanjishi_'+roomId, 0, 60);
			}
		}
	});
}

