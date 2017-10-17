/**
 * 加载翻硬币历史数据
 * @param url 
 */
function loadHistoryList(url) {
	var options = {};
	options.url = url;
	options.pageSize = "10";

	$.pagination("pagination", options, function(data) {
		var content = "";
		if(data && data.length > 0) {
			var content = '';
			for (var i=0;i<data.length;i++) {
				var room = data[i];
				var roomOwner = room.roomOwner;
				var joinners = room.roomJoinners;
				content += '<li>';
				content +=     '<div class="view1">';//房主
				content +=         '<img src="' + roomOwner.userPhoto + '" class="one">';
				content +=         '<span class="two" title="'+roomOwner.userNick+'">' + roomOwner.userNick + '</span>';
				content +=     '</div>';
				content +=     '<div class="view2">';//参与者
				if (joinners && joinners.length > 0) {
					for (var j=0;j<joinners.length;j++) {
						var joinner = joinners[j];
						content +=         '<div class="content">';
						content +=             '<img src="' + joinner.userPhoto + '" class="one">';
						content +=             '<span class="two" title="'+joinner.userNick+'">' + joinner.userNick + '</span>';
						content +=         '</div>';
					}
				}
				content +=    '</div>';
				content +=    '<div class="view3">' + room.totalGoldNum+ '</div>';
				if(room.random != undefined && room.random != null){
					content +=     '<div class="view4">' + room.random + '</div>';
				}else{
					content +=     '<div class="view4"></div>';
				}
				content +=     '<div class="view5">'; //赢方
				if (room.status == 3) {
					var coinImg = '';
					if (room.winner == 1) {
						coinImg = _staticPrefix + "/images/zheng.png";
					} else {
						coinImg = _staticPrefix + "/images/fan.png";
					}
					content +=         '<div class="left">';
					content +=             '<img src="' + coinImg + '">';
					content +=         '</div>';
					content +=         '<div class="right">';
					if (roomOwner.isCoinFront == room.winner) {
						content +=             '<img src="' + roomOwner.userPhoto + '">';
					} else {
						if (joinners && joinners.length > 0) {
							for (var j=0;j<joinners.length;j++) {
								var joinner = joinners[j];
								content +=             '<img src="' + joinner.userPhoto + '">';
							}
						}
					}
					content +=         '</div>';
				}
				content +=     '</div>';
				if (room.openTime != undefined && room.openTime != null) {
					content +=     '<div class="view6">' + getSmpFormatDateByLong(room.openTime, true)+ '</div>';// 开奖时间
				} else {
					content +=     '<div class="view6"></div>';// 开奖时间
				}
				
				if (room.status == 0) {
					content +=     '<div class="view7">等待加入</div>';
				} else if (room.status == 1) {
					content +=     '<div class="view7">加入中</div>';
				} else if (room.status == 2) {
					content +=     '<div class="view7">等待开奖</div>';
				} else if (room.status == 3) {
					content +=     '<div class="view7">已结束</div>';
				} else if (room.status == 4) {
					content +=     '<div class="view7">已取消</div>';
				}
				content +=     '<div class="view8"><button class="btn viewBtn" roomId="' + room.id + '">查看</button></div>';
				content += '</li>';
			}
			
		} else {
			content = '<p style="height: 50px; line-height: 27px; padding: 12px;">暂无数据</p>';
		}
		$(".message-details-body").html(content);
		$(".viewBtn").click(function() {
			var roomId = $(this).attr("roomId");
			$("#viewCoinflip").modal("show");
			initViewRoom(roomId, 0);
		});
	})
}

/**
 * 个人翻硬币总量相关数据
 */
function myData(){
	$.ajax({
		url : _path + "/coinflip/u/historyStatistics",
		type: "POST",
		dataType : "json",
		success : function(data) {
			if (data) {
				if (data.success) {
					if(data.all){
						$("#gameCount").html(data.all.gameCount);
						$("#goldNumSum").html(data.all.goldNumSum);
						$("#probability").html(data.all.probability);
						$("#winnerGoldNumSum").html(data.all.winnerGoldNumSum);
					}
					if(data.today){
						$("#todayGameCount").html(data.today.gameCount);
						$("#todayGoldNumSum").html(data.today.goldNumSum);
						$("#todayProbability").html(data.today.probability);
						$("#todayWinnerGoldNumSum").html(data.today.winnerGoldNumSum);
					}
				}
			}
		}
	});
}
