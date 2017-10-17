$(function(){
    var flag = true;
    $(".inc_right_content").on("click",function(e){
        var _con = $('.item-getbag');
        if(!_con.is(e.target) && _con.has(e.target).length === 0 && $(".bounced1").is(":visible")){
            $(".bounced1").hide();
            $(".bounced1").attr("show","false");
            flag = true;
        }
    });
    
    $(".slide-head span").on("click",function(e){
    	if($(this).hasClass("sp") == true && $(this).hasClass("checked") == false){
    		$(".slide-two").addClass("hide");
    		$(".slide-one").removeClass("hide").css("margin-top","800px").animate({
				marginTop:'0'
			},300);
    	}
    	else if($(this).hasClass("cdk") == true && $(this).hasClass("checked") == false){
    		$(".slide-one").addClass("hide");
    		$(".slide-two").removeClass("hide").css("margin-top","800px").animate({
				marginTop:'0'
			},300);
    	}
    	$(".slide-head span").removeClass("checked");
    	$(this).addClass("checked");
    });
    
    $("input[name='allRight']").click(function(){
        if($(this).is(":checked")){
            $("input[name='sonRight']").prop("checked",true);
        } else{
            $("input[name='sonRight']").prop("checked",false);
        }
    });
    
}); 

var first = true;
var pageNum = 1;
// 竞猜列表
function getMyExchangeList(_first) {
	if(_first != undefined && _first) {// 第一次点击，初始化
		first = _first;
		pageNum = 1;
	}
	var url = _path + "/my/exchangeLog/list";
	var dataJson = {};
	dataJson.pageSize = "3";
	dataJson.nowPage = pageNum;// 页码
	pageNum = pageNum + 1;// 下一页
	
	var parameters = {};
	parameters.orderBy = "";
	
	dataJson.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(dataJson)}, "post", false, function(backData) {
		var content = '';
		
		var data = backData.list;
		if(data.length > 0) {
			var exchangeLog ;
			for(var i = 0; i<data.length; i++) {
				exchangeLog = data[i];
				var cardNo = exchangeLog.goodsVo.cardNo;
				var cardPass = exchangeLog.goodsVo.cardPass;
				
				var url = _path + "/shopping/"+exchangeLog.goodsVo.goodsId;
				content += '<div class="item-getbag mb-10" style="display: flex; flex-direction: row; justify-content: space-between; padding: 0 16px; background: #2e3238; overflow: hidden;">';
				content += '<div style="display: flex; flex-direction: column; flex: 1;">';
				if(cardNo == cardPass && ckdCode == "") {
					content += '<p class="mb-10">订单号&nbsp;:&nbsp;'+exchangeLog.ueOrderNo+'（CD-KEY）</p>';
				} else {
					content += '<p class="mb-10">订单号&nbsp;:&nbsp;'+exchangeLog.ueOrderNo+'</p>';
				}
				content += '<div style="display: flex; flex-direction: row; justify-content: flex-start;">';
				content += '<div>';
				if("2" == exchangeLog.goodsVo.goodsType && "1" == exchangeLog.exchangeStatus) {// 虚拟 且  未领取
					content += '<input id="check'+exchangeLog.id+'" type="checkbox" name="sonRight">';
					content += '<label class="hold" for="check'+exchangeLog.id+'"></label>';
				} else {
					content += '<input type="checkbox" disabled="disabled">';
				}
				content += '</div>';
				content += '<a href="'+url+'" class="ml-10" style="display: inline-block; width:44px; height: 30px;">';
				if("2" == exchangeLog.goodsVo.goodsType) {// 虚拟
					if("1" == exchangeLog.exchangeStatus) {// 未领取
						content += '<img src="'+exchangeLog.goodsVo.goodsImg+'" width="100%" height="100%"/></a>';
					} else {
						content += '<img title="已领取或出售" src="'+exchangeLog.goodsVo.goodsImg+'" width="100%" height="100%"/></a>';
					}
				} else {// 实物
					content += '<img title="实物不出售" src="'+exchangeLog.goodsVo.goodsImg+'" width="100%" height="100%"/></a>';
				}
				content += '<div>';
				content += '<p class="ml-10" style="white-space: nowrap;"><span>'+exchangeLog.goodsVo.goodsName+'</span></p>';
				content += '<p class="ml-10" style="color: #d29b19; white-space: nowrap;">'+exchangeLog.exchangeGold+'金币</p>';
				content += '</div>';
				content += '</div>';
				content += '<p style=" margin: 5px 0 10px 0;"><span>'+getSmpFormatDateByLong(exchangeLog.exchangeTime, true)+'</span></p>';
				content += '</div>';
				
				content += '<div class="liuliu" style="display: flex; flex-direction: column; text-align: right; position: relative;">';
					content += '<div class="bounced1" id="show'+exchangeLog.id+'">';
					if((cardNo == cardPass || "-1" == exchangeLog.exchangeStatus) && cardPass != null) {
						if("1" == exchangeLog.exchangeStatus) {
							cardPass = "";
						}
						content += getCdkDivRight(cardPass, exchangeLog.id);
					} else {
					content += '<div class="webside_"><b style="white-space: nowrap; color: #31c3a2;">网址&nbsp;:&nbsp;</b><div><a href="https://www.baidu.com/" target="_blank" class="cardpa" style="width: 166px; text-decoration: underline;text-align: left;padding-left: 7px;">https://www.baidu.com/</a></div></div>';
					content += '<div class="webside_"><b style="white-space: nowrap; color: #31c3a2;">卡号&nbsp;:&nbsp;';
					content += '<span class="cardpa" id="no_"'+exchangeLog.id+' style="color: #fff;width: 126px; text-align: left;padding-left: 7px;vertical-align: middle; padding-bottom: 3px;">'+cardNo+'</span></b>';
					content += '<a name="clip_button_ex" data-clipboard-action="copy" data-clipboard-text="'+cardNo+'" class="cardpa clip_button" style="text-align: left;width: 66px; text-decoration: underline;">复制</a></div>';
					content += '<div class="webside_"><b style="white-space: nowrap; color: #31c3a2;">卡密&nbsp;:&nbsp;';
					content += '<span class="cardpa" id="pw_"'+exchangeLog.id+' style="color: #fff;width: 126px; text-align: left;padding-left: 7px;vertical-align: middle; padding-bottom: 3px;">'+cardPass+'</span></b>';
					content += '<a name="clip_button_ex" data-clipboard-action="copy" data-clipboard-text="'+cardPass+'" class="cardpa clip_button" style="text-align: left;width: 66px; text-decoration: underline;">复制</a></div>';
				}
				content += '</div>';
				if("1" == exchangeLog.exchangeStatus || "-1" == exchangeLog.exchangeStatus) {// 未领取
					var sign = "领取";
					if("-1" == exchangeLog.exchangeStatus) {
						sign = "CD-KEY";
					}
					content += '<a href="#" style="text-align: center; color: #31c3a2;">未领取</a>';
					content += '<button type="button" id=logRight'+exchangeLog.id+' class="btn btn-primary btn-raised" style="width: 58px; height: 24px; padding: 0;" onclick="updateRightStatus(\''+exchangeLog.id+'\',\''+exchangeLog.goodsVo.goodsType+'\',2)"><strong>'+sign+'</strong></button>';
					if("2" == exchangeLog.goodsVo.goodsType && "-1" != exchangeLog.exchangeStatus) {// 虚拟
//						content += '<button id=salesRight_'+exchangeLog.id+' type="button" class="btn" style=" border: 1px solid #31c3a2; color: #31c3a2; width: 48px; height: 24px; padding: 0;" onclick="updateRightStatus(\''+exchangeLog.id+'\',\''+exchangeLog.goodsVo.goodsType+'\',3)">出售</button>';
					}
				} else if("1" == exchangeLog.goodsVo.goodsType) {// 实物
					if(exchangeLog.trackerNo != null) {
						content += '<a href="#" style="text-align: center; color: #767678;">已发货</a>';
						content += '<button type="button" class="btn btn-primary btn-raised" style="width: 48px; height: 24px; padding: 0;background:#3d4148;outline:none;border:none"><strong>已发货</strong></button>';
					} else {
						content += '<a href="#" style="text-align: center; color: #767678;">发货中</a>';
						content += '<button type="button" class="btn btn-primary btn-raised" style="width: 48px; height: 24px; padding: 0;background:#3d4148;outline:none;border:none; box-shadow: none;"><strong>发货中</strong></button>';
					}
				} else {// 虚拟
					if("3" == exchangeLog.exchangeStatus) {// 出售中
						content += '<a href="#" style="text-align: center; color: #767678;">出售中</a>';
						content += '<button type="button" class="btn btn-primary btn-raised" style="width: 48px; height: 24px; padding: 0;background:#3d4148;outline:none;border:none;box-shadow: none;"><strong>出售中</strong></button>';
					} else if("2" == exchangeLog.exchangeStatus){// 已领取
						content += '<a href="#" style="text-align: center; color: #767678;">已领取</a>';
						content += '<button type="button" class="btn btn-primary btn-raised getlook" style="width: 48px; height: 24px; padding: 0;background:#3d4148;outline:none;border:none;" onclick="showRightDiv(\''+exchangeLog.id+'\')"><strong>查 看</strong></button>';
					} else {
						content += '<a href="#" style="text-align: center; color: #767678;">已出售</a>';
						content += '<button type="button" class="btn btn-primary btn-raised" style="width: 48px; height: 24px; padding: 0;background:#3d4148;outline:none;border:none;color: #787878; box-shadow: none;"><strong>已出售</strong></button>';
					}
				}
				
				content += '</div>';
				content += '</div>';
			}
		} else if(first) {
			content += "";
		}
		
		if(first) {
			$("#r_exchangeId").html(content);
		} else {
			$("#r_exchangeId").append(content);
		}
		
		var clipboardMyFriendBtn = document.getElementsByName('clip_button_ex');
	    var clipboard = new Clipboard(clipboardMyFriendBtn);
	    
		clipboard.on('success', function(e) {
		    layer.msg("复制成功", {icon: 6,shift: 6,time: 2000});
		});

		clipboard.on('error', function(e) {
			layer.msg("复制失败，请手动复制", {icon: 5,shift: 6,time: 2000});
		});
		
		var pageCount = backData.pageCount;
		if(data.length > 0 && pageCount >= pageNum) {
			first = false;
			$("#exchangeMore").show();
			$("#exchangeNoMore").hide();
		} else {
			$("#exchangeMore").hide();
			$("#exchangeNoMore").show();
		}

	})
}

function getCdkDivRight(cardPass, id) {
	var content = '<div class="webside_"><b style="white-space: nowrap; color: #31c3a2;">&nbsp;&nbsp;<span></span></b></div>';
	content += '<div class="webside_"><b style="white-space: nowrap; color: #31c3a2;">CD-KEY:';
	content += '<span class="cardpa" id="cdk_'+id+'" style="color: #fff;width: 126px; text-align: left;padding-left: 7px;vertical-align: middle; padding-bottom: 3px;">'+cardPass+'</span></b>';
	content += '<a name="clip_button_ex" id="cdkC_'+id+'" data-clipboard-action="copy" data-clipboard-text="'+cardPass+'" class="cardpa clip_button" style="text-align: left;width: 66px; text-decoration: underline;">复制</a>';
	content += '</div>';
	content += '<div class="webside_"><b style="white-space: nowrap; color: #31c3a2;"><span></span></b></div>';
	return content;
}

function updateRightStatus(logId, goodsType, status) {
	var url = _path + "/my/exchangeLog/updateStatus";
	var dataJson = {"id":logId, "exchangeStatus":status};
	$.ajax({
		url: url,
		data: dataJson,
		type: "post",
		dataType: "json",
		async: false, // 同步
		success: function(callBack) {
			if(callBack.success) {
				if("1" == goodsType) {// 实物
					$("#"+logId).html("发货中").css({"background": "#3d4148", "outline": "none", "border": "none"});
					$("#"+logId).prev("a").css("color", "#767678").html("发货中");
					
					$("#logRight"+logId).html("发货中").css({"background": "#3d4148", "outline": "none", "border": "none"});
					$("#logRight"+logId).prev("a").css("color", "#767678").html("发货中");
				} else {
					if("2" == status) {// 已领取
						var data = callBack.data;
						if(data) {
							var cardPass = data.goodsVo.cardPass;
							$("#cdk2_"+logId).text(cardPass);
							$("#cdkC2_"+logId).attr("data-clipboard-text",cardPass);
							
							$("#cdk_"+logId).text(cardPass);
							$("#cdkC_"+logId).attr("data-clipboard-text",cardPass);
							
							$("#"+logId).html("查 看").css({"background": "#3d4148", "outline": "none", "border": "none"});
							$("#"+logId).prev("a").css("color", "#767678").html("已领取");
							$("#sales_"+logId).hide();
							
							$("#logRight"+logId).html("查 看").css({"background": "#3d4148", "outline": "none", "border": "none"});
							$("#logRight"+logId).prev("a").css("color", "#767678").html("已领取");
							$("#salesRight_"+logId).hide();
							
							if(document.getElementById(logId)) {
								document.getElementById(logId).onclick = function() {
									$(this).next().toggle();
								};
							}
							document.getElementById("logRight"+logId).onclick = function() {
								showRightDiv(logId);
							};
						} else {
							layer.alert("已放入奖池，请刷新页面", {icon: 5,shift: 6,time: 0});
						}
					} else if("3" == status) {// 出售中
						$("#"+logId).html("出售中").css({"background": "#3d4148", "outline": "none", "border": "none"});
						$("#"+logId).prev("a").css("color", "#767678").html("已领取");
						$("#sales_"+logId).hide();
						
						$("#logRight"+logId).html("出售中").css({"background": "#3d4148", "outline": "none", "border": "none"});
						$("#logRight"+logId).attr("disabled","disabled");
						$("#logRight"+logId).prev("a").css("color", "#767678").html("已领取");
						$("#salesRight_"+logId).hide();
						if(document.getElementById(logId)) {
							document.getElementById(logId).onclick = function() {
								
							};
						}
						document.getElementById("logRight"+logId).onclick = function() {// 清空方法
							
						};
					}
				}
			} else {
				layer.alert(callBack.message, {icon: 5,shift: 6,time: 0});
			}
		}
	});
}

var first2 = true;
function getMyCDKList(_first) {
	if(_first != undefined && _first) {// 第一次点击，初始化
		first2 = _first;
		pageNum = 1;
	}
	var url = _path + "/my/cdkey/list";
	var dataJson = {};
	dataJson.pageSize = "10";
	dataJson.nowPage = pageNum;// 页码
	pageNum = pageNum + 1;// 下一页
	
	var parameters = {};
	parameters.exchangeUserId = userId;
	
	dataJson.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(dataJson)}, "post", false, function(backData) {
		var content = "";
		var data = backData.list;
		if(data.length > 0) {
			var cdKey ;
			for(var i = 0; i<data.length; i++) {
				cdKey = data[i];
				
				content += '<div class="item-getbag-CKD mb-10">';
				content += '<i class="iconfont icon-xiaoxitongzhi"></i>';
				content += '<div>';
				content += '<p>'+ getSmpFormatDateByLong(cdKey.exchangeTime, true) +'</p>';
				content += '<p>'+ cdKey.cdkeyCode +'</p>';
				content += '<p>'+ getCDKeyRemarks(cdKey) +'</p>';
				content += '</div>';
				content += '</div>';
        	
			}
		} else if(first2) {
			content += "";
		}
		
		if(first2) {
			$("#r_CDKexchangeId").html(content);
		} else {
			$("#r_CDKexchangeId").append(content);
		}
		
		var pageCount = backData.pageCount;
		if(data.length > 0 && pageCount >= pageNum) {
			first2 = false;
			$("#cdk_exchangeMore").show();
			$("#cdk_exchangeNoMore").hide();
		} else {
			$("#cdk_exchangeMore").hide();
			$("#cdk_exchangeNoMore").show();
		}
	})
}

function getCDKeyRemarks(cdKey) {
	var remarks = "";
	var type = cdKey.type;
	var gold = cdKey.gold;
	var goodsName = cdKey.goodsName;
	if("1" == type) {// 金币
		remarks = gold + "金币兑换成功";
	} else if("2" == type) {// G币
		remarks = gold + "G币兑换成功";
	} else {
		remarks = goodsName + " 兑换成功";
	}
	
	return remarks;
}

function salesAllRight() {
	layer.confirm("是否全部出售？", {icon : 3, title : "出售提示"}, function(index) {
		var ids = new Array();
		$("input[name='sonRight']").each(function() {
			var checked = $(this).is(":checked");
			if(checked) {
				var id = $(this).val();
				if(id != "on") {
					ids.push(id);
				}
			}
		});
		
		ids = ids.join(",");
		$.ajax({
			url: _path + "/my/exchangeLog/updateStatusForSales",
			data: {"ids": ids},
			type: "post",
			dataType: "json",
			success: function(callBack) {
				if(callBack.success) {
					layer.alert("出售成功，请等待。。。", {icon: 1,shift: 6,time: 0});
					setTimeout("getMyExchangeList(true)",2000);
				} else {
					layer.alert(callBack.message, {icon: 5,shift: 6,time: 0});
				}
			}
		});
	});
}

function showRightDiv(logId) {
	var show = $("#show"+logId).attr("show");
	if(show == "true") {
		$("#show"+logId).hide();
		$("#show"+logId).attr("show","false");
	} else {
		$("#show"+logId).show();
		$("#show"+logId).attr("show","true");
	}
}
