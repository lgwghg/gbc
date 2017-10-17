$(function(){
	// 初始化竞猜时间
	var jcTime = $("#jcTime").val();
	$("#jcTimeDesc").html(getTimeDifferenceFront(new Date(),jcTime));
	var gbstate = $("#gbstate").val();
	if (gbstate != null && gbstate != '' && gbstate == 0) {
		var pkStartTime = $("#pkStartTime").val();
		$("#pkStartTimeStr").html('<img src="' + _staticPrefix + '/images/time_x.png?v=' + _version + '">' + getTimeDifference(new Date(),pkStartTime));
	}
	// 验证能否领取金币
	var gbId = $("#gbId").val();
	var userCode = $("#userCode").val();
	$.ajax({
		url:_path+"/share/jc/checkCanGet",
		type: 'POST',
		data:{
			gbId : gbId,
			userCode : userCode
		},
		dataType:"json",
		success: function(data) {
			if (data.isShare) {// 已分享
				if (data.get == '1') {//领过了
			        $("#userGold").html(data.userJcShare.gold);
			        $("#uMobile").html(data.userJcShare.userMobile);
					var getedRedList = data.jcShareList;
					if (getedRedList != null) {
						initLuckUserList(getedRedList)
					}
					$(".no-phone-number").addClass("hide");
			        $(".have-phone-number").removeClass("hide");
			        $("#luck-title").removeClass("hide");
				} else {
					if (data.expire) {
						// 过期了
						$("#tishiText").html("您当前操作的页面已过期!");
			    		$(".tishi").removeClass("hide");
			    		$(".tishi button").addClass("hide");
					} else {
						if (data.get == '-1') {
							$("#tishiText").html("G币已领完!");
				    		$(".tishi").removeClass("hide");
				    		$(".tishi button").removeClass("hide");
						}
					}
				}
			} else { // 未分享
				if (data.expire) {
					// 过期了
					$("#tishiText").html("您当前操作的页面已过期!");
		    		$(".tishi").removeClass("hide");
		    		$(".tishi button").addClass("hide");
		    		$(".no-phone-number").addClass("hide");
			        $(".have-phone-number").addClass("hide");
				}
			}
			
		}
	});
	
	var submit = true;
	// 点击领取G币
    $(".share-get").click(function(){
    	if (submit) {
    		submit = false;
    		// 验证手机号
        	var userMobile = $("#userMobile").val();
        	if (userMobile == null || userMobile == '') {
        		//layer.alert("手机不能为空", {icon : 5,shift : 6,time : 3000});
        		$("#tishiText").html("手机不能为空");
        		$(".tishi").removeClass("hide");
        		$(".tishi button").removeClass("hide");
        		submit = true;
        		return;
        	} else if (!/^1\d{10}$/.test(userMobile)) {
        		//layer.alert("请输入正确的手机号", {icon : 5,shift : 6,time : 3000});
        		$("#tishiText").html("请输入正确的手机号");
        		$(".tishi").removeClass("hide");
        		$(".tishi button").removeClass("hide");
        		submit = true;
        		return;
        	}
        	var gbId = $("#gbId").val();
        	var userCode = $("#userCode").val();
        	if (gbId == null || userCode == null) {
        		submit = true;
        		return;
        	}
        	$.ajax({
        		url:_path+"/share/jc/getRedPurse",
        		type: 'POST',
        		data:{
        			gbId : gbId,
        			userCode : userCode,
        			userMobile : userMobile
        		},
        		dataType:"json",
        		success: function(data) {
        			if (data.state == 1) {
        				$("#userGold").html(data.userJcShare.gold);
    			        $("#uMobile").html(data.userJcShare.userMobile);
    					$(".lottery-name").html(data.userJcShare.userNick);
        				var getedRedList = data.jcShareList;
        				if (getedRedList != null) {
        					initLuckUserList(getedRedList)
        				}
        				$(".no-phone-number").addClass("hide");
        		        $(".have-phone-number").removeClass("hide");
        		        $("#luck-title").removeClass("hide");
        				
        			} else {
        				//layer.alert(data.msg, {icon : 5,shift : 6,time : 3000});
        				$("#tishiText").html(data.msg);
        	    		$(".tishi").removeClass("hide");
        	    		$(".tishi button").removeClass("hide");
        			}
        		},
        		complete:function() {
        			submit = true;
        		}
        	});
    	}
    	
    })
    
    $("#userMobile").blur(function() {
    	var userMobile = this.value;
    	if (userMobile == null || userMobile == '') {
    		//layer.alert("手机不能为空", {icon : 5,shift : 6,time : 3000});
    		$("#tishiText").html("手机不能为空");
    		$(".tishi").removeClass("hide");
    		$(".tishi button").removeClass("hide");
    		return;
    	} else if (!/^1\d{10}$/.test(userMobile)) {
    		//layer.alert("请输入正确的手机号", {icon : 5,shift : 6,time : 3000});
    		$("#tishiText").html("请输入正确的手机号");
    		$(".tishi").removeClass("hide");
    		$(".tishi button").removeClass("hide");
    		return;
    	}
    });
    // 提示
    $(".tishi-main button,.tishi-main span").click(function(){
        $(".tishi").addClass("hide");
    })
});

function initLuckUserList(getedRedList) {
	var luckRank = "";
	var date = new Date();
	for (var i=0; i<getedRedList.length; i++) {
		var red = getedRedList[i];
		var time = getTimeDifferenceFront(date,red.createTime);
		var userNick = red.userNick;
		if (userNick == null || userNick == "") {
			userNick = "***" + red.userMobile.substr(7,4);
		}
		var bestLuck = "";
		if (red.lucky == 1 && getedRedList.length == 10) {
			bestLuck = '<span class="fr co-31"><img src="' + _staticPrefix + '/images/best_luck.png">手气最佳</span>';
		}
		luckRank += '<li>';
		luckRank +=     '<img src="' + red.userPhoto + '">'
		luckRank +=     '<div>';
		luckRank +=         '<p class="luck-message">';
		luckRank +=             '<span>' + userNick + '</span>';
		luckRank +=             '<span class="co-60"> ' + time + '领取</span>';
		luckRank +=             '<span class="fr">' + red.gold + ' G币</span>';
		luckRank +=         '</p>';
		luckRank +=         '<p class="luck-time"><span class="first">' + red.userDesc + '</span>' + bestLuck + '</p>';
		luckRank +=     '</div>';
		luckRank += '</li>';
	}
	$(".luck-rank").html(luckRank);
}