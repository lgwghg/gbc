/**
 * 计算两个时间相差的s数
 * @param now
 * @param oldTime
 * @returns
 */
function disSeconds(now, oldTime) {
	var date3 = now-oldTime;//时间差的毫秒数  
	  //计算出小时数  
	  var leave1 = date3 % (24 * 3600 * 1000);    //计算天数后剩余的毫秒数  
	  var hours = Math.floor(leave1 / (3600 * 1000));  
	  var leave2 = leave1 % (3600 * 1000);        //计算小时数后剩余的毫秒数  
	  var minutes = Math.floor(leave2 / (60 * 1000));  
	  //计算相差秒数  
	  var leave3 = leave2 % (60 * 1000);      //计算分钟数后剩余的毫秒数  
	  var seconds = Math.round(leave3 / 1000); 
	  return seconds;
}

/**
 * 查看房间
 * @param roomId
 * @param isJoin 
 * @returns
 */
function initViewRoom(roomId, isJoin) {
	$.ajax({
		url : _path + "/coinflip/viewRoom",
		type: "POST",
		data : {
			"roomId" : roomId
		},
		dataType : "json",
		success : function(data) {
			if (data) {
				if (data.success) {
					var room = data.room;
					var content = '';
					var timerid = 'tanjishi_' + room.id;
					var opentimerid = 'opentanjishi_' + room.id;
					if (isJoin) {
						timerid = 'tanjishi_j_' + room.id;
						opentimerid = 'opentanjishi_j_' + room.id;
					}
					
					content +='<div class="timerwatch">'+
					'<div class="cavtimer"><div id="' + timerid + '"></div><div id="' + opentimerid + '"></div></div>'+
					'<div class="anim-wrap"></div><div class="coin coinzoom"></div></div>';
					
					content +='<div class="hashwrap">';
					if (room.hashCode != '' && room.hashCode != null) {
						content +=	'<p><span>HASH:<span class="vue">' + room.hashCode +'</span></span></p>';
					}
					if (room.random != '' && room.random != null) {
						content +=	'<p><span>RANDOM:<span class="vue">' + room.random + '</span></span></p>';
					}
					if (room.key != '' && room.key != null) {
						content +=	'<p><span>KEY:<span class="vue">' + room.key + '</span></span></p>';
					}
					content +='</div>';
					
					// 房主信息
					var roomOwner = room.roomOwner;
					var isCoinFront = roomOwner.isCoinFront;
					var roomOwnerCoin = '';
					var joinerCoin = '';
					if (isCoinFront == 1) {
						roomOwnerCoin = _staticPrefix + '/images/zheng.png';
						joinerCoin = _staticPrefix + '/images/fan.png';
					} else {
						roomOwnerCoin = _staticPrefix + '/images/fan.png';
						joinerCoin = _staticPrefix + '/images/zheng.png';
					}
					content +='<div class="humanwrap">';
					content +=	'<div class="humanleft">';
					content +=		'<div class="fire">';
					content +=			'<div class="imgwrap">';
					content +=				'<img src="' + roomOwnerCoin + '" alt="" width=100% height=100%>';
					content +=			'</div>';
					content +=		'</div>';
					content +=		'<div class="human">';
					content +=			'<div class="humanitem">';
					content +=				'<div class="pull-left">';
					content +=					'<div class="imgwrap">';
					content +=						'<img src="' + roomOwner.userPhoto + '" alt="" width=100% height=100%>';
					content +=					'</div>';
					content +=					'<div class="text">';
					content +=						'<span>' + roomOwner.userNick + '</span>';
					content +=					'</div>';
					content +=				'</div>';
					content +=				'<div class="rc-goldwarp">';
					content +=					'<div class="imgwrap">';
					content +=						'<img src="' + _staticPrefix + '/images/gold-prize.jpg" alt="" width=100% height=100%>';
					content +=					'</div>';
					content +=					'<div class="text">';
					content +=						'<span>' + roomOwner.goldNum + '</span>';
					content +=					'</div>';
					content +=				'</div>';
					content +=			'</div>';
					content +=		'</div>';

					content +=	'</div>';
					
					// 参与者信息
					content +=	'<div class="humanright">';
					content +=		'<div class="ice">';
					content +=			'<div class="imgwrap">';
					content +=				'<img src="' + joinerCoin + '" alt="" width=100% height=100%>';
					content +=			'</div>';
					content +=		'</div>';
					content +=		'<div class="human">';
					
					var joinners = room.roomJoinners;
					content += dialogJoinnersDiv(joinners);
					
					if (isJoin) {// 加入操作
						content += '<div class="suoha match-list-bottom-pour" id="new_play_nobet">';
						content += 	'<p class="view1">';
						content +=         '<span class="co_be">我要下注</span>';
						content +=         '<span class="one iconfont icon-jinbi"></span>';
						content +=         '<input type="text" class="ft_18_bo" placeholder="0" value="1000">';
						content +=     '</p>';
						content += 	'<p class="mt-20">';
						content +=         '<input class="ex8 coinex8" id="ex7" data-slider-id="ex1Slider" type="text" data-slider-min="1000" data-slider-max="500000" data-slider-step="1" data-slider-value="1000"/>';
						content +=  '</p>';
						content += 	'<p class="view4">';
						content +=         '<span class="co_be pull-left">1000金币</span>';
						content +=         '<a class="co_be pull-right two" href="javascript:;">All in</a>';
						content +=   '</p>';
						content += '</div>';
						
					}
					
					content +=		'</div>';// end class="human"

					content +=	'</div>';
					content +='</div>';
					
					if (isJoin) {
						content +='<p style="text-align: center;"><button class="btn btn-primary btn-raised chuangjian join">提交</button></p>';
						$("#joinCoinflip .addhuman  .modal-body").html(content);
						
						var min = 1000;
						var max = 500000;
						var value = 0;
						var ownerGoldNum = roomOwner.goldNum;
						if (ownerGoldNum <= 100000) {
							value = ownerGoldNum;
							max = ownerGoldNum;
							min = ownerGoldNum;
						} else {
							var ownerGoldNum = room.ownerGoldNum;
							var totalGoldNum = room.totalGoldNum;
							max = ownerGoldNum - (totalGoldNum - ownerGoldNum);
							
							var walletGoldNum = 0;
							if ($("#headerMenuGold").text() != '') {
								walletGoldNum = parseInt($("#headerMenuGold").text()) + parseInt($("#headerMenuSysGold").text());
							}
							
							if (max > 10000) {
								min = 10000;
								if (joinners) {
									if (joinners.length < 3) {
										if (walletGoldNum >=10000 && walletGoldNum < max) {
											max = walletGoldNum;
										}
									} else {
										min = max;
									}
								}
							} else {
								min = max;
							}
							
						}
						initSlider(min,min,max);
						$("#joinCoinflip .join").click(function() {
							joinRoom(room.id);
						});
						var joinRoomGold = parseInt($("#room_"+room.id).attr("ownerGoldNum"));
						var roomBeted = parseInt($("#room_" + room.id + " .rc-goldwarp span").text());
						if (joinRoomGold <= 100000) {// 小于10w通知其他该按钮消失
							// 通知其他人隐藏加入按钮
							noticeJoinBtn(room.id, "hide")
						} else {
							$("#room_" + room.id + " .joinBtn").hide();
						}
						// 60秒倒计时
						startTimer(timerid, 0, 60);
					} else {
						content +='<p style="text-align: center;"><button class="btn btn-primary btn-raised chuangjian closeBtn" data-dismiss="modal" aria-hidden="true">关闭</button></p>';
						$("#viewCoinflip .modal-body").html(content);
						
						$("#viewCoinflip .close, #viewCoinflip .closeBtn").off('click');
						$("#viewCoinflip .close, #viewCoinflip .closeBtn").click(function() {
							var roomId= $("#viewCoinflip #viewRoomId").val();
							closeTimer('tanjishi_' + roomId);// 关闭弹框计时器
						});
						
						if (room.status == 1) {
							// 有人加入倒计时
							var now = data.now;
							var seconds = disSeconds(now, room.joinTime);
							// 倒计时
							closeTimer(timerid);
							if (seconds > 0) {
								if (seconds < 60) {
									startTimer(timerid, seconds, 60);
								}
							}
						} else if (room.status == 2) {
							// 开奖倒计时
							var now = data.now;
							var seconds = disSeconds(now, room.updateTime);
							// 倒计时
							$("#"+timerid).hide();
							//startTimer(timerid, 60, 60);
							closeTimer(opentimerid);
							startTimer(opentimerid, seconds, 10);
						} else if (room.status == 3){
							// 已开奖
							//$("#" + opentimerid).closest(".timerwatch").find('.anim-wrap').show();
							if (room.winner == 1) {
								coinImg = _staticPrefix + "/images/zheng.png";
								//$("#" + opentimerid).closest(".timerwatch").find('.anim-wrap').addClass("rollzheng");
							} else {
								coinImg = _staticPrefix + "/images/fan.png";
								//$("#" + opentimerid).closest(".timerwatch").find('.anim-wrap').addClass("rollfan");
							}
							$("#" + opentimerid).closest(".timerwatch").find('.coin').html('<img src="' + coinImg + '" alt="" width=100% height=100%>');
							$("#" + opentimerid).closest(".timerwatch").find('.coin').show();
							/*var sh =  setTimeout(function(){
							},4000);*/
						} else if (room.status == 0) {
							//$("#" + timerid).html('<img src="' + coinImg + '" alt="" width=100% height=100%>');
						}
					}
					
				} else {
					layer.alert(data.msg, {icon: 5,shift: 6,time: 0});
					return;
				}
			}
		}
	});
}
/**
 * 弹框里的参与者div
 * @param joinners
 * @returns {String}
 */
function dialogJoinnersDiv(joinners) {
	var content = '';
	if (joinners && joinners.length > 0) {
		for (var i = 0; i < joinners.length; i++) {
			var joinner = joinners[i];
			content +=			'<div class="humanitem" uid="' + joinner.userId+ '">';
			content +=				'<div class="pull-left">';
			content +=					'<div class="imgwrap">';
			content +=						'<img src="' + joinner.userPhoto + '" alt="" width=100% height=100%>';
			content +=					'</div>';
			content +=					'<div class="text">';
			content +=						'<span>' + joinner.userNick + '</span>';
			content +=					'</div>';
			content +=				'</div>';
			content +=				'<div class="rc-goldwarp">';
			content +=					'<div class="imgwrap">';
			content +=						'<img src="' + _staticPrefix + '/images/gold-prize.jpg" alt="" width=100% height=100%>';
			content +=					'</div>';
			content +=					'<div class="text">';
			content +=						'<span>' + joinner.goldNum + '</span>';
			content +=					'</div>';
			content +=				'</div>';
			content +=			'</div>';
		}
	}
	return content;
}
/**
 * show  or hide 加入按钮通知
 * @param roomId
 * @param state
 */
function noticeJoinBtn(roomId, state) {
	$.ajax({
		url : _path + "/coinflip/noticeJoin",
		type: "POST",
		data : {
			"roomId" : roomId,
			"joinBtn" : state
		}
	});
}

/**
 * 加入弹框倒计时结束，处理弹框和加入按钮
 * @param roomId
 */
function dealJoinBtn(roomId) {
	// 关闭弹框
	$("#joinCoinflip").modal("hide");
	if ($("#room_"+roomId)) {
		// 处理加入按钮
		var joinRoomGold = parseInt($("#room_"+roomId).attr("ownerGoldNum"));
		var roomBeted = parseInt($("#room_" + roomId + " .rc-goldwarp span").text());
		if (roomBeted == joinRoomGold *2) {
			$("#room_" + roomId + " .joinBtn").hide();
		} else {
			if (joinRoomGold <= 100000) {// 小于10w通知其他该按钮消失
				noticeJoinBtn(roomId, "show");
			} else {
				$("#room_" + roomId + " .joinBtn").show();
			}
		}
		
	}
}
/**
 * 开始计时器 circleJt
 * @param domId
 * @param value
 * @param totalValue
 */
function startTimer(domId, value, totalValue) {
	$.fn.circleJt({
		domId:domId,//必须
		value: value,//必须
		totalValue: totalValue,//10是绿，99是红
		percentage:true,//非必需
	});
}
/**
 * 关掉计时器
 * @param domId
 */
function closeTimer(domId) {
	$("#Circle_JT_innerDom"  + domId).stopTime("innerDom" + domId);
	$("#Circle_JT_cancvsDom"  + domId).stopTime(domId);
	var cancvsDom = document.getElementById("Circle_JT_cancvsDom" + domId);
	if (cancvsDom) {
		var _this = cancvsDom.getContext('2d');
		_this.clearRect(0, 0, 100, 100);
	}
	$("#" + domId).html('');
}