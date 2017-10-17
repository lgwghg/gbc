var roomUserId;
var roomId;
var flag = false;
$(function () {
	roomUserId = $("#roomUserId").val();
	roomId = $("#roomId").val();
	if(roomUserId == userId) {
		flag = true;
	}
	$(".form-group select").dropdown();
	// 隐藏获奖人名单
	$("#rollWinnerDiv").hide();

    // 获取roll点次数
    getRoolNum();
    // 获取奖品池
    getRollPool();
    // 获取参与人员名单
    getRollMember();
    
});

//获取roll点次数
function getRoolNum() {
	var url = _path + "/rollRoom/getRollNum.html";
	var options = {};
	options.url = url;
	options.pageSize = "25";
	
	var parameters = {};
	parameters.roomId = roomId;
	
	options.parameters = parameters;

	ajaxMethod(url, {"gridPager":JSON.stringify(options)}, "post", true, function(backData) {
		if(backData.isSuccess) {
			var data = backData.list;
			var content = '';
			var content2 = '';
			if(data) {
				var length = data.length;
				var num = data.length + 1;
				content += '<li class="view1 view4 checked">第'+num+'次ROLL进行中...</li>';
				if(length > 3) {
					length = 3;// 只展示4个
				}
				for(var i=0; i<length; i++) {
					num = num - 1;
					if(i == 0) {
						content += '<li id="maxRollNum" class="view2 view4" value="'+num+'">第'+num+'次ROLL</li>';
					} else {
						content += '<li class="view2 view4" value="'+num+'">第'+num+'次ROLL</li>';
					}
				}
				
				if(data.length > 3) {
					content += '<li class="view3">更多+</li>';
					content += '<li class="fix"></li>';
				}
				
				num = data.length - 2;
				for(var i=0; i<data.length - 3; i++) {
					num = num - 1;
					content2 += '<li value="'+num+'">第'+num+'次</li>';
				}
			} else {
				content += '<li class="view1 view4 checked">第1次ROLL进行中...</li>';
			}
			$("#rollNum_ul").html(content);
			$("#moreRollNum").html(content2);
			
			rollNumCss();
		}
	})
}

// 获取奖品池
var pageNum = 1;
function getRollPool(second) {
	var url = _path + "/rollPool/list.html";
	var dataJson = {};
	dataJson.pageSize = "24";
	if(!second) {
		pageNum = 1;
		if(flag) {
			dataJson.pageSize = "23";
		}
	}
	dataJson.nowPage = pageNum;// 页码
	pageNum = pageNum + 1;// 下一页
	
	var parameters = {};
	parameters.isCurrent = "true";
	parameters.roomId = roomId;
	
	dataJson.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(dataJson)}, "post", true, function(backData) {
		if(backData.isSuccess) {
			var data = backData.exhibitDatas;
			var length = data.length;
			var content = '';
			if(flag && !second) {
				content += '<li class="add-prize" data-target="#rollPoolForm" data-toggle="modal" onclick="clickPool();"><img src="'+_staticPrefix+'/images/add-prize.png"></li>';
			}
			var delImgSrc = _staticPrefix + "/images/delete-prize.png";
			var rollPool;
			for(var i=0; i<length; i++) {
				rollPool = data[i];
				var gold = rollPool.gold;
				var imgSrc = _staticPrefix + "/images/cdkey-prize.jpg";
				if(gold != "" && gold != undefined) {
					imgSrc = _staticPrefix + "/images/gold-prize.jpg";
				}
				
				content += '<li id="'+rollPool.id+'" value="'+rollPool.id+'"><img class="bg" src="'+imgSrc+'">';
		    	if(flag) {
		    		content += '<img src="'+delImgSrc+'" class="delete-prize hide">';
		    	}
				
				content += '</li>';
				
			}
			
			if(second) {
				$("#rollPool_ul").append(content);
			} else {
				$("#rollPool_ul").html(content);
			}
			
			// 触发css
			poolCss();
			var pageCount = backData.pageCount;
			if(data.length > 0 && pageCount >= pageNum) {
				first = false;
				$("#poolMore").removeClass("hide");
				$("#poolNoMore").addClass("hide");
			} else {
				$("#poolMore").addClass("hide");
			}
		}
	});
}

// 获取参与人员名单
function getRollMember() {
	var url = _path + "/roll/getMemberList";
	var options = {};
	options.url = url;
	options.pageSize = "20";
	
	var parameters = {};
	parameters.roomId = roomId;
	
	options.parameters = parameters;
	
	$.pagination("pagination_member", options, function(data, recordCount) {
		var length = data.length;
		var content = '';
		if(length > 0) {
			content += '<p class="view1">';
			content += '<span class="one"></span> <span class="two">参与名单</span> <span class="three">( '+recordCount+'人 )</span>';
			if(flag) {
				content += '<button type="button" class="btn btn-raised btn-primary four" onclick="clearMember();">清除</button>';
			}
			content += '</p>';
			content += '<div class="view3">';
			var rollMember;
			for(var i=0; i<length; i++) {
				rollMember = data[i];
				content += '<div>';
				content += '<img src="'+rollMember.photo+'">';
				content += '<p>'+rollMember.memberName+'</p>';
				content += '</div>';
			}
			content += '</div>';
		} else {
			content += '<p class="view1">';
			content += '<span class="one"></span> <span class="two">参与名单</span> <span class="three">( 0人 )</span>';
			content += '</p>';
			content += '<div class="view2">暂时还没有用户参加</div>';
		}
		
		$("#rollMemberDiv").html(content);
	});
}

// 获取获奖人员名单
function getRollWinner(num) {
	var url = _path + "/roll/getWinnerList";
	
	var options = {};
	options.url = url;
	options.pageSize = "5";
	
	var parameters = {};
	parameters.roomId = roomId;
	parameters.num = num;
	
	options.parameters = parameters;

	$.pagination("pagination_win", options, function(data, recordCount) {
		var length = data.length;
		var content = '';
		if(length > 0) {
			$("#winnerNums").text("( "+recordCount+"人 )");
			content += '<li class="title">';
			content += '<div class="one">参与时间</div>';
			content += '<div class="two">玩家</div>';
			content += '<div class="three">奖品</div>';
			content += '</li>';
			var rollWinner;
			for(var i=0; i<length; i++) {
				rollWinner = data[i];
				content += '<li class="content">';
				content += '<div class="one">'+getSmpFormatDateByLong(parseInt(rollWinner.winTime), true)+'</div>';
				content += '<div class="two">';
				content += '<img src="'+rollWinner.photo+'"><span>'+rollWinner.winnerName+'</span>';
				content += '</div>';
				content += '<ul class="three">';
				var poolList = rollWinner.poolList;
				var rollPool;
				var jiantou = _staticPrefix + "/images/prize-message-icon.png";
				for(var j=0; j<poolList.length; j++) {
					rollPool = poolList[j];
					var gold = rollPool.gold;
					var poolImg = _staticPrefix + "/images/cdkey-prize.jpg";
					if(gold != "" && gold != undefined) {
						poolImg = _staticPrefix + "/images/gold-prize.jpg";
					}
					content += '<li>';
					content += '<img src="'+poolImg+'" class="prize-img">';
					content += '<div class="prize-message hide">';
					content += '<img src="'+jiantou+'" class="prize-message-img2"> <img src="'+poolImg+'" class="prize-message-img">';
					if(gold != "" && gold != undefined) {
						content += '<p class="prize-message-ms-three">';
						content += '<span class="icon">￥</span> <span class="co-ff">'+rollPool.gold+'</span> <span>金币</span>';
						content += '</p>';
					} else {
						content += '<p class="prize-message-ms-one">';
						content += '<span>商品：</span> <span class="co-ff" title="'+rollPool.goodsName+'">'+rollPool.goodsName+'</span>';
						content += '</p>';
					}
					content += '</div>';
					content += '</li>';
				}
				content += '</ul>';
				content += '<div class="fix"></div>';
				content += '</li>';
			}
			content += '</div>';
			
			$("#rollWinnerDiv").show();
		}
		$("#rollWinner_ul").html(content);
		
		rollWinnerCss();
	
	});
}

function targetUrl(url) {
	window.open(url);
}

// 参加活动
function joinRoll() {
	var url = _path + "/rollMember/add.html";
	ajaxMethod(url, {"roomId":roomId}, "post", true, function(backData) {
		if(backData.success) {
			getRollMember();
			$("#joinRoll").text("已参与");
			$("#joinRoll").attr("disabled","disabled");
			layer.alert(backData.message, {icon:1 ,shift: 6,time: 1000});
		} else {
			layer.alert(backData.message, {icon:5 ,shift: 6,time: 2000});
		}
	})
}

// 删除奖品
function delRollPool(poolId) {
	layer.confirm('确认删除奖品吗？', {
        icon : 3,
        title : '删除提示'
    }, function(index, layero) {
    	var url = _path + "/rollPool/deleteBatch.html";
    	ajaxMethod(url, {"ids":poolId}, "post", false, function(backData) {
    		if(backData.success) {
    			// 更新金币
    			userWallet();
    			$("#"+poolId).remove();
    			layer.alert(backData.message, {icon:6 ,shift: 6,time: 1000});
    		} else {
    			layer.alert(backData.message, {icon:5 ,shift: 6,time: 2000});
    		}
    	})
        layer.close(index);
    });
	
}

// 开始roll
function addRollWinner() {
	var num = $("#winnerNum").val();
	
	var dataJson = {};
	dataJson.roomId = roomId;
	dataJson.poolIds = getPoolIds();
	dataJson.num = num;
	dataJson.unique = $("#unique").is(':checked');
	dataJson.clear = $("#clear").is(':checked');
	
	var url = _path + "/roll/addRollWinner";
	ajaxMethod(url, dataJson, "post", true, function(backData) {
		if(backData.success) {
			getRoolNum();
			getRollPool();
			getRollMember();
			$("#rollMemberDiv").show();
			$("#rollMemberView").show();
			$("#rollWinnerDiv").hide();
			layer.alert(backData.message, {icon:6 ,shift: 6,time: 1000});
		} else {
			layer.alert(backData.message, {icon:5 ,shift: 6,time: 2000});
		}
	})
	
}

function getPoolIds() {
	var poolIds = "";
	$("#rollPool_ul .checked").each(function() {
		var value = $(this).attr("value");
		if(poolIds == "") {
			poolIds = value;
		} else {
			poolIds = poolIds + "," + value;
		}
	});
	return poolIds;
}

function changeWinnerNum() {
	var num = $("#winnerNum").val();
	if(isNaN(num)) {
		layer.alert("请输入数字~", {icon:5 ,shift: 6,time: 1000});
		$("#winnerNum").val(1);
	} else if(num < 1) {
	 	layer.alert("个数不能少于1个", {icon:5 ,shift: 6,time: 1000});
	 	$("#winnerNum").val(1);
 	}
}

// 清除参与人员
function clearMember() {
	layer.confirm('确认清除人员吗？', {
        icon : 3,
        title : '清除提示'
    }, function(index, layero) {
    	var url = _path + "/rollMember/clearMember.html";
    	ajaxMethod(url, {"roomId":roomId}, "post", true, function(backData) {
    		if(backData.success) {
    			getRollMember();
    			layer.alert(backData.message, {icon:6 ,shift: 6,time: 1000});
    		} else {
    			layer.alert(backData.message, {icon:5 ,shift: 6,time: 2000});
    		}
    	})
    	layer.close(index);
    });
}

// 跳转到第几次roll
function gotoRollNum() {
	var gotoNum = Number($("#gotoNum").val());
	if(isNaN(gotoNum) || gotoNum < 1) {
		gotoNum = 1;
	}
	var maxRollNum = Number($("#maxRollNum").attr("value"));
	if(gotoNum > maxRollNum) {
		gotoNum = maxRollNum;
	}
	var content = '<li value="'+gotoNum+'">第'+gotoNum+'次</li>';
	$("#moreRollNum").html(content);
	$("#gotoNum").val(gotoNum);
	
	rollNumCss();
}

/**************************************************  CSS控制  *******************************************************************************/
function poolCss() {
	// 显示删除按钮
    $(".ornament-left-prize .view2 li").on("mouseenter",function(){
        $(this).find(".delete-prize").removeClass("hide");
    }).on("mouseleave",function(){
    	$(this).find(".delete-prize").addClass("hide");
    })
    // 选中奖品
    $(".ornament-left-prize .view2 li").on("click",function() {
    	if(flag) {
    		var checked = $(this).hasClass("checked");
    		if(checked) {
    			$(this).removeClass("checked");
    		} else {
    			$(this).addClass("checked");
    		}
    	}
    })
    //删除奖品池
    $(".ornament-left-prize .view2 li .delete-prize").on("click",function(){
    	var poolId = $(this).parent().attr("value");
    	delRollPool(poolId);
    });
}

function rollNumCss() {
	//活动切换
    $(".ornament-left-screen .view4").on("click",function(){
        $(".ornament-left-screen .view4").removeClass("checked");
        $(this).addClass("checked");
		if($(this).hasClass("view1")) {// 进行中
			$("#rollMemberDiv").show();
			$("#rollMemberView").show();
			$("#rollWinnerDiv").hide();
		} else {
			// 获取获奖人员名单
			$("#rollMemberDiv").hide();
			$("#rollMemberView").hide();
			var num = $(this).attr("value");
		    getRollWinner(num);
		}
    })

    //展开更多ROLL
    $(".ornament-left-screen .view3").on("click",function(){
        $(".ornament-left-screen-a .view5").removeClass("hide");
    })
    $(".ornament-left-screen-a .view5").on("mouseenter",function(){
        $(this).removeClass("hide");
    }).on("mouseleave",function(){
        $(this).addClass("hide");
    })
    $(".ornament-left-screen-a .view5 .two li").on("click",function(){
        $(".ornament-left-screen-a .view5 .two li").removeClass("checked");
        $(this).addClass("checked");
        // 获取获奖人员名单
        $("#rollMemberDiv").hide();
        $("#rollMemberView").hide();
		var num = $(this).attr("value");
	    getRollWinner(num);
    })
    $(".ornament-left-screen-a .view5 .one").on("click",function(){
        $(".ornament-left-screen-a .view5").addClass("hide");
    })
}

function rollWinnerCss() {
	//获奖结果奖品鼠标经过显示详细信息
    $(".ornament-left-participation-before .view2 .content .three li .prize-img").on("mouseenter",function(){
        $(this).parent().find(".prize-message").removeClass("hide");
    }).on("mouseleave",function(){
        $(this).parent().find(".prize-message").addClass("hide");
    })
}











