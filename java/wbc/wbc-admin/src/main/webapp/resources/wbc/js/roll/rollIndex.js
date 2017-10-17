var currentTypeId = 2;
$(function() {
	// 初始化我创建的房间
	initMyCreateRoom();
	// 初始化所有房间
	initAllRoll(2);//1：最新  2：最热  3：我参与的活动
	// 初始化活动获奖记录
	getRollWinner();
	// 热门夺宝
	getRollRoomListForHot();
	
	$(".icon-xingxing").click(function() {
		initAllRoll(1);
		currentTypeId = 1;
	});// 最新
	$(".icon-huo1").click(function() {
		initAllRoll(2);
		currentTypeId = 2;
	});// 最热
	$(".icon-canyuqingkuang").click(function() {
		initAllRoll(3);
		currentTypeId = 3;
	});// 我参与的活动
	
	// 搜索房间
	$("#searchButton").click(function() {
		initAllRoll(currentTypeId);
	});
	$('#searchName').bind("keydown", function(event) {
		if(event.keyCode==13) {
			initAllRoll(currentTypeId);
	    }
	});
	
});
// 初始化我创建的房间
function initMyCreateRoom() {
	var url = _path + "/roll/getList";
	var options = {};
	options.url = url;
	options.pageSize = 9;
	options.nowPage = 1;
	var parameters = {};
	parameters.userId = userId;
	parameters.isOwner = "true";
	options.parameters = parameters;
	ajaxMethod(url, {"gridPager":JSON.stringify(options)}, "post", true, function(backData) {
		if (backData.isSuccess) {
			var rollRoomVoList = backData.list;
			if (rollRoomVoList && rollRoomVoList.length > 0) {
				var contentHtml = "";
				for (var i = 0; i < rollRoomVoList.length; i++) {
					var rollRoomVo = rollRoomVoList[i];
					var roomCode = rollRoomVo.roomCode;
					if (roomCode == "" || roomCode == null) {
						roomCode = rollRoomVo.id;
					}
					var roomDetailUrl = _path + "/roll/" + roomCode;
					contentHtml += '<a class="eventcard" href="' + roomDetailUrl + '" >';
					contentHtml +=     '<i class="iconfont icon-fangchanfangzi"></i>';
					contentHtml +=     '<p>' + rollRoomVo.remarks + '</p>';
					contentHtml += '</a>';
				}
				if (backData.nowPage == backData.pageCount) {
					contentHtml += '<a class="eventcard" data-toggle="modal" data-target="#rollRoomForm">';
					contentHtml +=     '<i class="iconfont icon-0100"></i>';
					contentHtml +=     '<p style="line-height: 30px; font-size: 20px; font-weight: bolder;">申请创建更多</p>';
					contentHtml += '</a>';
				} else {
					initMyCreateRoom();
				}
			} else {
				if (backData.nowPage == 1) {
					contentHtml += '<a class="eventcard" data-toggle="modal" data-target="#rollRoomForm">';
					contentHtml +=     '<i class="iconfont icon-0100"></i>';
					contentHtml +=     '<p style="line-height: 30px; font-size: 20px; font-weight: bolder;">新建房间</p>';
					contentHtml += '</a>';
				}
			}
			$("#myRollRoom").append(contentHtml);
		}
	});
}

// 初始化所有的roll活动
function initAllRoll(typeId) {
	var url = _path + "/roll/getList";
	
	var options = {};
	options.url = url;
	options.pageSize = "9";
	var searchName = $("#searchName").val();
	var parameters = {};
	parameters.typeId = typeId;
	parameters.searchName = searchName;
	parameters.isOwner = "true";
	options.parameters = parameters;

	$.pagination("pagination", options, function(rollRoomVoList) {
		if (rollRoomVoList && rollRoomVoList.length > 0) {
			var contentHtml = "";
			for (var i = 0; i < rollRoomVoList.length; i++) {
				var rollRoomVo = rollRoomVoList[i];
				var roomCode = rollRoomVo.roomCode;
				if (roomCode == null || roomCode == '') {
					roomCode = rollRoomVo.id;
				}
				var memberList = rollRoomVo.memberList;
				var memberNum = 0
				var currentTime = new Date().getTime();
				var endTime = rollRoomVo.endTime;
				contentHtml += '<div class="personcard" onclick="openRRDetail(\'' + roomCode + '\')">';
				contentHtml +=     '<div class="pcheader">';
				contentHtml +=         '<span><img width="48" height="48" src="' + rollRoomVo.userPhoto + '" alt=""></span>';
				contentHtml +=         '<p class="name">' + rollRoomVo.userName + '</p>';
				contentHtml +=         '<p class="name">房间号：' + rollRoomVo.roomCode + '</p>';
				//contentHtml +=         '<p>';
				//contentHtml +=             '<a class="btn persondj persondj31" href="#">竞技达人</a>';
				//contentHtml +=             '<a class="btn persondj persondj115" href="#">电竞解说</a>';     
				//contentHtml +=         '</p>';       
				contentHtml +=     '</div>';
				contentHtml +=     '<div class="pccontent">';
				contentHtml +=         '<div class="imgwrap" style="text-align: left;">';
				if (memberList && memberList.length > 0) {
					memberNum = memberList.length;
					var showNum = 4;
					if (memberNum < showNum) {
						showNum = memberNum;
					}
					for (var j = 0; j< showNum; j++) {
						var rollMemberVo = memberList[j];
						contentHtml +=             '<span><img width="54" height="54" src="' + rollMemberVo.photo + '" alt=""></span>';
					}
				}
				contentHtml +=         '</div>';
				contentHtml +=         '<div class="pull-left pl-15">';
				contentHtml +=             '<p class="pcitem-title mb-20" style="line-height: 15px;">简介 ： </p>';
				contentHtml +=             '<p class="pcitem-title">开始 ： </p>';
				contentHtml +=             '<p class="pcitem-title">截止 ： </p>';
				contentHtml +=             '<p class="pcitem-title">参与 ： </p>';
				contentHtml +=         '</div>';
				contentHtml +=         '<div class="pull-right pr-10">';
				contentHtml +=         '<p class="pccontent-item item-jianjie">' + rollRoomVo.remarks + '</p>';
				contentHtml +=         '<p class="pccontent-item">' + getFormatDateByLong(parseInt(rollRoomVo.startTime), "yyyy-MM-dd hh:mm") + '</p>';
				contentHtml +=         '<p class="pccontent-item">' + getFormatDateByLong(parseInt(rollRoomVo.endTime), "yyyy-MM-dd hh:mm") + '</p>';
				contentHtml +=         '<p class="pccontent-item">' + memberNum + '人</p>';
				contentHtml +=         '</div>';
				contentHtml +=     '</div>';
				contentHtml +=     '<div class="pcfooter">';
				if (currentTime > endTime) {
					contentHtml +=         '<a class="btn iconfont icon-eye mt-15 eyesituationtwo" href="javascript:;">活动已截止</a>';
				} else {
					contentHtml +=         '<a class="btn iconfont icon-eye mt-15" href="/roll/' + roomCode + '">查看活动</a>';
				}
				contentHtml +=     '</div>';
				contentHtml += '</div>';
			}
			$("#personcardList").html(contentHtml);
			$("#pagination").show();
		} else {
			$("#personcardList").html('<p style="color: #afb0b2; text-align: center; clear: both;">暂无数据</p>');
			$("#pagination").hide();
		}
	});
}
function openRRDetail(roomCode) {
	window.location.href = _path + "/roll/" + roomCode;
}

//获取参与人员名单
function getRollWinner() {
	var winner_url = _path + "/roll/getWinnerList";

	var winner_options = {};
	winner_options.url = winner_url;
	winner_options.pageSize = "5";
	winner_options.nowPage = 0;
	var winner_parameters = {};
	//parameters.roomId = roomId;
	winner_options.parameters = winner_parameters;
	ajaxMethod(winner_url, {"gridPager":JSON.stringify(winner_options)}, "post", true, function(backData) {
		if (backData.isSuccess) {
			var rollWinnerList = backData.list;
			var contentHtml = "";
			if (rollWinnerList && rollWinnerList.length > 0) {
				var currentTime = new Date();
				for (var i=0;i< rollWinnerList.length; i++) {
					var rollWinner = rollWinnerList[i];
					var poolList = rollWinner.poolList;
					var poolListNum = 0;
					if (poolList && poolList.length >0) {
						poolListNum = poolList.length;
					}
					var winTime = new Date(parseInt(rollWinner.winTime));
					contentHtml += '<li>';
					contentHtml +=     '<p>';
					contentHtml +=         '<span class="pwimgwrap"><img width="30" height="30"  src="' + rollWinner.photo + '" alt=""></span>';
					contentHtml +=         '<span class="awardname">' + rollWinner.winnerName + '</span>于';
					contentHtml +=         '<span class="awardtime">' + getTimeDifferenceFront(currentTime, winTime) + '</span>Roll获得' + poolListNum + '件奖品';
					contentHtml +=     '</p>';
					contentHtml +=     '<div class="imgwrap" style=" text-align: left;">';
					var showNum = 4;
					if (poolList) {
						if (poolList.length < showNum) {
							showNum = poolList.length;
						}
					} else {
						showNum = 0;
					}
					for (var j=0; j<showNum; j++) {
						var rollPool = poolList[j];
						var gold = rollPool.gold;
						var poolImg = _staticPrefix + "/images/cdkey-prize.jpg";
						if(gold != "" && gold != undefined) {
							poolImg = _staticPrefix + "/images/gold-prize.jpg";
						}
						contentHtml +=         '<span><img width="54" height="54" src="' + poolImg + '" alt=""></span>';
					}
					contentHtml +=     '</div>';
					contentHtml += '</li>';
				}
				$("#winnerList").html(contentHtml);
			} else {
				$("#winnerList").html('<li style="color: #afb0b2; height: 42px;">暂无数据</li>');
			}
		}
		
	});
}

function getRollRoomListForHot() {
	var url = _path + "/roll/getRollRoomListForHot";
	var dataJson = {};
	dataJson.pageSize = "5";
	dataJson.nowPage = 0;// 页码
	
	var parameters = {};
	
	dataJson.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(dataJson)}, "post", true, function(backData) {
		if (backData.isSuccess) {
			var content = '';
			var roomList = backData.list;
			var rollRoomVo;
			for (var i=0;i< roomList.length; i++) {
				rollRoomVo = roomList[i];
				var roomCode = rollRoomVo.roomCode;
				if (roomCode == null || roomCode == '') {
					roomCode = rollRoomVo.id;
				}
				content += '<li onclick="openRRDetail(' + roomCode + ')">';
				content += '<span class="c-roundpoint" style="background: #d19a17;"></span>';
				content += '<figure>';
				content += '<span><img width="48" height="48" src="'+ rollRoomVo.userPhoto +'" alt=""></span>';
				content += '<p>'+rollRoomVo.userName+'</p>';
				content += '<p>';
				content += '<a style="margin-left: 0;" class="btn persondj persondj31" href="#">竞技达人</a>';
				content += '<a class="btn persondj persondj115" href="#">电竞解说</a>';
				content += '</p>';
				content += '<p>简介 ：'+rollRoomVo.remarks+'</p>';
				content += '</figure>';
				content += '</li>';
			}
			$("#roomHot_ul").html(content);
		}
	});
}
