//加载数据列表
function loadGoodsList() {
	var options = {};
	options.url = _path + "/shopping/list";
	options.pageSize = "10";

	$.pagination("pagination", options, function(data) {
		$("#goods-ul").html("");
		var content = "";
		if(data.length > 0) {
			var goods ;
			for(var i = 0; i<data.length;i++) {
				goods = data[i];
				var url = _path + "/shopping/"+goods.id;
				
				content += '<li class="col-lg-4 col-md-4 col-sm-6 col-xs-12">';
				content += '<a href="'+url+'" class="one" target="_blank"><img src="'+goods.goodsImg+'" height="100%" width="100%"></a>';
				content += '<p class="two"><a href="javascript:;">'+goods.goodsName+'</a></p>';
				content += '<p class="three">';
				content += '<span class="ft-18 co_eb">'+goods.goodsGold+'金币</span>';
				content += '<span class="fr ft-16 co_78">已兑换'+goods.exchangeCount+'件</span>';
				content += '</p></li>';
			}
			
			if(data.length < 8) {
            	content += '<li class="col-lg-4 col-md-4 col-sm-6 col-xs-12">';
            	content += '<a href="javascript:;" class="one"><span>敬请期待</span></a>';
            	content += '<p class="two"></p>';
            	content += '<p class="three"></p>';
            	content += '</li>';
			}
		} else {
			content += '<li class="col-lg-4 col-md-4 col-sm-6 col-xs-12">';
        	content += '<a href="javascript:;" class="one"><span>敬请期待</span></a>';
        	content += '<p class="two"></p>';
        	content += '<p class="three"></p>';
        	content += '</li>';
		}
		$("#goods-ul").append(content);
	})
}

// 热门兑换
function loadHotExchange() {
	//分页
	var pagesJson = {};
	pagesJson.nowPage_ge = 1;
	pagesJson.pageCount_ge = -1;
	pagesJson.pageSize_ge = 5; // 只显示5列
	
	$.ajax({
		url: _path + "/shopping/list",
		data: {"gridPager": JSON.stringify(pagesJson)},
		dataType: "json",
		type: "POST",
		success : function(resultdata) {
			if (resultdata.isSuccess) {
				nowPage_ge = resultdata.nowPage + 1;
				pageCount_ge = resultdata.pageCount;
				var data = resultdata.list;
				var content = "";
				if(data.length > 0) {
					var goods ;
					for(var i = 0; i<data.length;i++) {
						goods = data[i];
						var url = _path + "/shopping/"+goods.id;
						content += '<li>';
						content += '<a class="one" href="'+url+'"><img src="'+goods.goodsImg+'"></a>';
						content += '<p class="two">'+goods.goodsName+'</p>';
						content += '<p class="three">'+goods.goodsGold+'金币</p>';
						content += '</li>';
					}
				} 
				$("#goods-ul").append(content);
			} else {
				layer.alert(callBack.message, {icon: 5,shift: 6,time: 0});
			}
		}
	});
}

//兑换
function exchange(type) {
	var flag = true;
	if(1 == type) {// 保存/修改地址
		flag = saveOrUpdateAddress();
	}
	if(flag) {
		var goodsId = $("#goodsId").val();
		var gold = $("#goodsGold").val();
		var addressId = $("#addrId").val();
		var salesNum = Number($("#salesNum").val());
		var total = Number(gold * salesNum);
		
		var wallet = $("#wallet").val();
		if(Number(wallet) < total) {
			layer.alert("金币不足，请先充值", {icon: 5,shift: 6,time: 0});
		} else {
			$.ajax({
				url: _path + "/my/exchangeLog/add",
				data: {"goodsId": goodsId, "gold": gold, "salesNum":salesNum, "addressId":addressId},
				type: "post",
				dataType: "json",
				success: function(callBack) {
					if(callBack.success) {
						layer.alert("已兑换成功", {icon: 1,shift: 6,time: 2000});
						$("#exchangeCount").text(Number($("#exchangeCount").text()) + salesNum);
						$("#goodsNum").text(Number($("#goodsNum").text()) - salesNum);
						$("#wallet").text(Number($("#wallet").val()) - total);
						updateUserGold(3, -1*total, null);
						$("#myModalAddress").modal("hide");
					} else {
						layer.alert(callBack.message, {icon: 5,shift: 6,time: 0});
					}
				}
			});
		}
	}
}

function salesAll() {
	var userId = $("#userId").val();
	if(userId == "" || userId == undefined) {
		$("#myModal").modal("show");
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

function buyAll() {
	var userId = $("#userId").val();
	if(userId == "" || userId == undefined) {
		$("#myModal").modal("show");
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
					setTimeout("window.location.reload()",2000);
				} else {
					layer.alert(callBack.message, {icon: 5,shift: 6,time: 0});
				}
			}
		});
		layer.close(index);
	});
}
