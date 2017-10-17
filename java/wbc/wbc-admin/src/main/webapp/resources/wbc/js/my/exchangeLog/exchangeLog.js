$(function() {
	loadList();
	$(".pull-cdownlist select").dropdown();
	$(".exrecord").click(function(){
        var index = $(this).index();
        if("0" == index) {
        	loadList();
        } else {
        	loadCDKList();
        }
        $(this).addClass("activecolor").siblings().removeClass("activecolor");
        $(".item-exmall").eq(index).addClass("exshow").siblings().removeClass("exshow");
    });

});

function loadList() {
	var options = {};
	options.url = _path + "/my/exchangeLog/list";
	options.pageSize = "10";
	var parameters = {};
	parameters.isCDK = "false";
	parameters.statusId = $("#statusId").val();
	options.parameters = parameters;
	
	$.pagination("pagination", options, function(data) {
		var content = "";
		if(data.length > 0) {
			var exchangeLog ;
			for(var i = 0; i<data.length; i++) {
				exchangeLog = data[i];
				var ckdCode = exchangeLog.cdkCode;
				var exchangeStatus = exchangeLog.exchangeStatus;
				if(exchangeLog.goodsVo) {// 有库存
					var cardNo = exchangeLog.goodsVo.cardNo;
					var cardPass = exchangeLog.goodsVo.cardPass;
					if("-1" == exchangeStatus) {
						exchangeStatus = 1;
					}
					
					content += '<li class="row">';
					content += '<div class="col-xs-2">';
					var url = _path + "/shopping/"+exchangeLog.goodsVo.goodsId;
					if("2" == exchangeLog.goodsVo.goodsType && "1" == exchangeStatus) {// 虚拟 且  未领取
						content += '<div class="c-checkbox">';
						content += '<input id="for_'+exchangeLog.id+'" type="checkbox" class="magic-checkbox" name="son" value="'+exchangeLog.id+'">';
						content += '<label class="hold" for="for_'+exchangeLog.id+'"></label></div>';
						content += '<a href="'+url+'"><img src="'+exchangeLog.goodsVo.goodsImg+'" width="100%" height="100%"/></a>';
					} else {
						content += '<div class="c-checkbox">';
						var title = "实物不支持出售";
						if("2" == exchangeLog.goodsVo.goodsType) {// 虚拟
							if("2" == exchangeStatus) {
								var title = "已领取";
							} else if("3" == exchangeStatus) {
								var title = "出售中";
							} else if("4" == exchangeStatus) {
								var title = "已出售";
							}
						}
						content += '<div class="tishi-tip" id="f_'+exchangeLog.id+'">'+title+'</div>';
						content += '<input type="checkbox" class="magic-checkbox" name="" value="" disabled="disabled">';
						content += '<label class="hold"></label></div>';
						content += '<a href="'+url+'"><img title="'+title+'" src="'+exchangeLog.goodsVo.goodsImg+'" width="100%" height="100%"/></a>';
					}
					
					content += '</div><div class="col-xs-8">';
					content += '<p><span>'+exchangeLog.ueOrderNo + ' - ' + exchangeLog.goodsVo.goodsName;
					if("-1" == exchangeLog.exchangeStatus || (cardNo == cardPass && ckdCode == "")) {
						content += '（CD-KEY）';
					}
					content += '</span></p>';
					if(ckdCode != "" && ckdCode != undefined) {
						content += '<p class="mt-5 mb-20">CD-KEY&nbsp&nbsp<span>'+ckdCode+'</span></p>';
					} else {
						content += '<p class="mt-5 mb-20">金币  <span>&nbsp&nbsp'+exchangeLog.exchangeGold+'</span></p>';
					}
					content += ' <span>'+getSmpFormatDateByLong(exchangeLog.exchangeTime, true)+'</span>';
					if("2" == exchangeLog.goodsVo.goodsType && "1" == exchangeStatus && "-1" != exchangeLog.exchangeStatus) {// 虚拟 且  未领取
//					content += '<button id=sales_'+exchangeLog.id+' type="button" class="btn c-saleon pull-right" onclick="updateStatus(\''+exchangeLog.id+'\',\''+exchangeLog.goodsVo.goodsType+'\',3)">出售</button>';
					}
					
					content += '</div><div class="col-xs-2">';
					
					if("1" == exchangeStatus) {// 未领取
						var sign = "领取";
						if("-1" == exchangeLog.exchangeStatus) {
							sign = "领取CD-KEY";
						}
						content += '<p><a class="pull-right" href="#">未领取</a></p>';
						content += '<button type="button" id='+exchangeLog.id+' class="btn btn-primary btn-raised pull-right" onclick="updateStatus(\''+exchangeLog.id+'\',\''+exchangeLog.goodsVo.goodsType+'\',2)"><strong>'+sign+'</strong></button>';
					} else if("1" == exchangeLog.goodsVo.goodsType) {// 实物
						if(exchangeLog.trackerNo != null) {
							content += '<p><a class="pull-right" href="#" style="color: #767678;">已发货</a></p>';
							content += '<button type="button" class="btn btn-primary btn-raised pull-right" style="background:#3d4148;outline:none;onfocus:none"><strong>已发货</strong></button>';
						} else {
							content += '<p><a class="pull-right" href="#" style="color: #767678;">发货中</a></p>';
							content += '<button type="button" class="btn btn-primary btn-raised pull-right" style="background:#3d4148;outline:none;onfocus:none; box-shadow: none;"><strong>发货中</strong></button>';
						}
					} else {// 虚拟
						if("3" == exchangeStatus) {// 出售中
							content += '<p><a class="pull-right" href="#" style="color: #767678;">出售中</a></p>';
							content += '<button type="button" class="btn btn-primary btn-raised pull-right" style="background:#3d4148;outline:none;onfocus:none;box-shadow: none;"><strong>出售中</strong></button>';
						} else if("2" == exchangeStatus){// 已领取
							content += '<p><a class="pull-right" href="#" style="color: #767678;">已领取</a></p>';
							content += '<button type="button" class="btn btn-primary btn-raised pull-right" style="background:#3d4148;outline:none;onfocus:none;" onclick="$(this).next().toggle().closest(\'li\').siblings().find(\'.bounced\').hide()"><strong>查 看</strong></button>';
						} else {
							content += '<p><a class="pull-right" href="#" style="color: #767678;">已出售</a></p>';
							content += '<button type="button" class="btn btn-primary btn-raised pull-right" style="background:#3d4148;outline:none;onfocus:none;color: #787878; box-shadow: none;"><strong>已出售</strong></button>';
						}
					}
					
					content += '<div class="bounced" style="display: none;" id="showCard_'+exchangeLog.id+'">';
					if((cardNo == cardPass || "-1" == exchangeLog.exchangeStatus) && cardPass != null) {
						if("1" == exchangeStatus) {
							cardPass = "";
						}
						content += getCdkDiv(cardPass, exchangeLog.id);
					} else {
						content += '<p><b>网址：</b><a href="https://www.baidu.com/">https://www.baidu.com/</a></p>';
						content += '<p><b>卡号：</b><span id="no2_"'+exchangeLog.id+' style="display: inline-block;width: 128px;text-decoration: underline;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;vertical-align: middle;">'+cardNo+'</span>';
						content += '<a name="clip_button_ex2" data-clipboard-action="copy" data-clipboard-text="'+cardNo+'" style="text-align: left;width: 38px; text-decoration: underline;display: inline-block;transform: translateY(7px);">复制</span></a></p>';
						content += '<p><b>密码：</b><span id="pw2_"'+exchangeLog.id+' style="display: inline-block;width: 128px;text-decoration: underline;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;vertical-align: middle;">'+cardPass+'</span>';
						content += '<a name="clip_button_ex2" data-clipboard-action="copy" data-clipboard-text="'+cardPass+'" style="text-align: left;width: 38px; text-decoration: underline;display: inline-block;transform: translateY(7px);">复制</span></a></p>';
					}
					content += "</div></div></li>";
				} else { // 没库存
					content += getGoldDiv(exchangeLog);
				}
			}
		} else {
			content += "<div class='no-record-x'><i class='iconfont icon-kule'></i>暂无数据</div>";
		}
		$("#listUl").html(content);
		
		var clipboardMyFriendBtn = document.getElementsByName('clip_button_ex2');
		var clipboard = new Clipboard(clipboardMyFriendBtn);
		
		clipboard.on('success', function(e) {
			layer.msg("复制成功", {icon: 6,shift: 6,time: 2000});
		});
		clipboard.on('error', function(e) {
			layer.msg("复制失败，请手动复制", {icon: 5,shift: 6,time: 2000});
		});
	})
}

function getCdkDiv(cardPass, id) {
	var content = '<p></p>';
	content += '<p><b>CD-KEY：</b><span id="cdk2_'+id+'" display: inline-block;width: 128px;text-decoration: underline;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;vertical-align: middle;>'+cardPass+'</span>';
	content += '<a name="clip_button_ex2" id="cdkC2_'+id+'" data-clipboard-action="copy" data-clipboard-text="'+cardPass+'" style="text-align: left;width: 38px; text-decoration: underline;display: inline-block;transform: translateY(7px);">复制</span></a></p>';
	content += '<p></p>';
	
	return content;
}

function getGoldDiv(exchangeLog) {
	var content = '<li class="row">';
	content += '<div class="col-xs-2">';
	content += '<div class="c-checkbox">';
	content += '<input id="for_'+exchangeLog.id+'" type="checkbox" class="magic-checkbox" name="son" value="'+exchangeLog.id+'">';
	content += '<label class="hold" for="for_'+exchangeLog.id+'"></label></div>';
	content += '<a href="javascript:;"><img src="'+_staticPrefix+'/images/goldGoods.jpg" width="100%" height="100%"/></a>';
	content += '</div><div class="col-xs-8">';
	content += '<p><span>'+exchangeLog.ueOrderNo + ' - ';
	content += '金币（CD-KEY）</span></p>';
	content += '<p class="mt-5 mb-20">金币  <span>&nbsp&nbsp'+exchangeLog.exchangeGold+'</span></p>';
	content += ' <span>'+getSmpFormatDateByLong(exchangeLog.exchangeTime, true)+'</span>';
	content += '</div><div class="col-xs-2">';
	content += '<p><a class="pull-right" href="#">未领取</a></p>';
	content += '<button type="button" id='+exchangeLog.id+' class="btn btn-primary btn-raised pull-right" onclick="updateStatus(\''+exchangeLog.id+'\',\''+2+'\',2)"><strong>领取CD-KEY</strong></button>';
	content += '<div class="bounced" style="display: none;" id="showCard_'+exchangeLog.id+'">';
	content += getCdkDiv("", exchangeLog.id);
	content += "</div></div></li>";
	
	return content;
}

function loadCDKList() {
	var options = {};
	options.url = _path + "/my/cdkey/list";
	options.pageSize = "10";
	var parameters = {};
	parameters.exchangeUserId = userId;
	options.parameters = parameters;

	$.pagination("cdk_pagination", options, function(data) {
		var content = "";
		if(data.length > 0) {
			var cdKey ;
			for(var i = 0; i<data.length; i++) {
				cdKey = data[i];
				
				content += '<tr>';
				content += '<td style="width:25%;text-align: center;"><i class="iconfont icon-xiaoxitongzhi"></i><span>'+getSmpFormatDateByLong(cdKey.exchangeTime, true)+'</span></td>';
				content += '<td style="width:25%;text-align: center;">'+ cdKey.cdkeyCode +'</td>';
				content += '<td style="text-align: center;"><span>'+ getCDKeyRemarks(cdKey) +'</span></td>';
				content += '</tr>';
			}
		} else {
			content += "<div class='no-record-x'><i class='iconfont icon-kule'></i>暂无数据</div>";
		}
		$("#cdkBody").html(content);
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


function updateStatus(logId, goodsType, status) {
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
					$("#"+logId).html("发货中").css({"background": "#3d4148", "outline": "none", "onfocus": "none"});
					$("#"+logId).prev("p").find("a").css("color", "#767678").html("发货中");
				} else {
					if("2" == status) {// 已领取
						var data = callBack.data;
						if(data) {
							var cardPass = data.cdkCode;
							if(cardPass == "" || cardPass == null) {
								cardPass = data.goodsVo.cardPass;
							}
							$("#cdk2_"+logId).text(cardPass);
							$("#cdkC2_"+logId).attr("data-clipboard-text",cardPass);
							
							$("#"+logId).html("查 看").css({"background": "#3d4148", "outline": "none", "onfocus": "none"});
							$("#"+logId).prev("p").find("a").css("color", "#767678").html("已领取");
							$("#sales_"+logId).hide();
							document.getElementById(logId).onclick = function() {
								$(this).next().toggle();
							};
						} else {
							layer.alert("已放入奖池，请刷新页面", {icon: 5,shift: 6,time: 2000});
						}
					} else if("3" == status) {// 出售中
						$("#"+logId).html("出售中").css({"background": "#3d4148", "outline": "none", "onfocus": "none"});
						$("#"+logId).prev("p").find("a").css("color", "#767678").html("已领取");
						$("#sales_"+logId).hide();
						document.getElementById(logId).onclick = function() {
							
						};
					}
				}
			} else {
				layer.alert(callBack.message, {icon: 5,shift: 6,time: 0});
			}
		}
	});
}
$(function(){
    $("input[name='all']").click(function(){
        if($(this).is(":checked")){
            $("input[name='son']").prop("checked",true);
        }else{
            $("input[name='son']").prop("checked",false);
        }
    });
    
})

function salesAll() {
	layer.confirm("是否全部出售？", {icon : 3, title : "出售提示"}, function(index) {
		var ids = new Array();
		$("input[name='son']").each(function() {
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
					setTimeout("window.location.reload()",2000);
				} else {
					layer.alert(callBack.message, {icon: 5,shift: 6,time: 0});
				}
			}
		});
	});
}

