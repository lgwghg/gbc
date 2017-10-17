$(function() {
	addressList();
//	if (window.location.href.indexOf("/my/address") >=0) {
//	}
	//$("#selectCity").citySelect({"prov":"420000", "city":"420100", "dist":"420111"}); 
	
	$("#saveAddress").off("click");
	$("#saveAddress").click(function() {
		my_saveOrUpdateAddress();
	});
	if(!(navigator.userAgent.indexOf("Firefox")>0)){  
        $("#dropdown-menu select").dropdown();
        $("#dropdown-menu select").addClass("form-control-x");
        var xprov = $("#dropdown-menu #provinceId").next().find("ul li[value=420000]");
        $("#dropdown-menu #provinceId").next().find("input").val(xprov.html());
        $("#dropdown-menu #provinceId").next().find("ul li").removeClass("selected");
        xprov.addClass("selected");
   } 
	
	//弹窗居中
	$("body").delegate("[data-toggle='modal']", "click", function () {
		var _target = $(this).attr('data-target')
	    t = setTimeout(function () {
	        var _modal = $(_target).find(".modal-dialog");
	        var _model_margintop = -(_modal.find(".modal-content").height()/2)+"px";
	        var _model_marginleft = -(_modal.find(".modal-content").width()/2)+"px";
	        /*_modal.animate({'margin-top': parseInt(($(window).height() - _modal.height()) / 4)}, 10)*/
	        _modal.css({
	        		'margin': _model_margintop + ' 0 0 ' + _model_marginleft,
	        		'position':'fixed',
	        		'top':'50%',
	        		'left':'50%'
	        	})
	    }, 10)
    })
});


function addressList() {
	var options = {};
	options.url = _path + "/my/address/list";
	options.pageSize = "10";
//	
	/*$.pagination("pagination", options, function(data) {
		var content = "";
		if (data.length > 0) {
			var address ;
			for(var i = 0; i<data.length;i++) {
				address = data[i];
				var receiverName = address.receiverName == null ? '' : address.receiverName;
				var provinceName = address.provinceName == null ? '' : address.provinceName;
				var cityName = 	address.cityName == null ? '' : address.cityName;
				var areaName = address.areaName = null ? '' : address.areaName;
				var addressDetail = address.addressDetail == null ? '' : address.addressDetail;
				var mobile = address.receiverMobile == null ? '' : address.receiverMobile;
				var isDefault = address.isDefault == null ? 0 : address.isDefault;
				var defaultStr = "";
				if (isDefault == 0) {
					defaultStr = '<span class="is-not-default" onclick="setDefault(\'' + address.id + '\')">设为默认地址</span>';
				} else {
					defaultStr = '<span class="is-default">默认地址</span>';
				}
				content += '<tr>';
				content +=    '<td>' + receiverName + '</td>';
				content +=    '<td>' + provinceName + '&nbsp;' + cityName + '&nbsp;' + areaName + '</td>';
				content +=    '<td>' + addressDetail + '</td>';
				//address +=    '<td>430034</td>';
				content +=    '<td>' + mobile + '</td>';
				content +=    '<td><a href="javascript:;" onClick="editAddress(\'' + address.id + '\')" data-toggle="modal" data-target="#myModal1">修改</a>&nbsp;|&nbsp;<a href="javascript:deleteAddress(\'' + address.id + '\')">删除</a></td>';
				content +=    '<td>' + defaultStr + '</td>';
				content +='</tr>';
			}
			
		} else {
			content += '<tr><td colspan="6"><div class="no-record-x"><i class="iconfont icon-kule"></i>暂无数据</div></td></tr>';
			content +=    '<td></td>';
			content +=    '<td></td>';
			content +=    '<td>暂无数据</td>';
			//address +=    '<td>430034</td>';
			content +=    '<td></td>';
			content +=    '<td></td>';
			content +=    '<td></td>';
			content +='</tr>';
		}
		
		$("#addressList").html(content);
	});*/
	
	$.ajax({
		url: options.url,
		data: {"gridPager": JSON.stringify(options)},
		type: 'POST',
		dataType: "json",
		success: function(backData) {
			if(backData.isSuccess) {
				var content = "";
				if (backData.list.length > 0) {
					var data = backData.list;
					var address ;
					for(var i = 0; i<data.length;i++) {
						address = data[i];
						var receiverName = address.receiverName == null ? '' : address.receiverName;
						var provinceName = address.provinceName == null ? '' : address.provinceName;
						var cityName = 	address.cityName == null ? '' : address.cityName;
						var areaName = address.areaName = null ? '' : address.areaName;
						var addressDetail = address.addressDetail == null ? '' : address.addressDetail;
						var mobile = address.receiverMobile == null ? '' : address.receiverMobile;
						var isDefault = address.isDefault == null ? 0 : address.isDefault;
						var defaultStr = "";
						if (isDefault == 0) {
							defaultStr = '<span class="is-not-default" onclick="setDefault(\'' + address.id + '\')">设为默认地址</span>';
						} else {
							defaultStr = '<span class="is-default">默认地址</span>';
						}
						content += '<tr>';
						content +=    '<td>' + receiverName + '</td>';
						content +=    '<td>' + provinceName + '&nbsp;' + cityName + '&nbsp;' + areaName + '</td>';
						content +=    '<td>' + addressDetail + '</td>';
						//address +=    '<td>430034</td>';
						content +=    '<td>' + mobile + '</td>';
						content +=    '<td><a href="javascript:;" onClick="editAddress(\'' + address.id + '\')" data-toggle="modal" data-target="#myModalAddress">修改</a>&nbsp;|&nbsp;<a href="javascript:deleteAddress(\'' + address.id + '\')">删除</a></td>';
						content +=    '<td>' + defaultStr + '</td>';
						content +='</tr>';
					}
					
				} else {
					content += '<tr><td colspan="6"><div class="no-record-x"><i class="iconfont icon-kule"></i>暂无数据</div></td></tr>';
				}
				$("#addressList").html(content);
			} else {
				layer.alert(backData.message, {icon: 5,shift: 6,time: 0});
			}
		}
	});
}

function my_saveOrUpdateAddress() {
	var dataJson = checkAddressForm();
	if(dataJson) {
		var id = dataJson.id;
		var url = _path + "/my/address/add";
		if(id != "") {
			url = _path + "/my/address/edit";
		}
		var flag = false;
		//alert(JSON.stringify(dataJson) + "=====1");
		$.ajax({
			url: url,
			data: dataJson,
			type: "post",
			dataType: "json",
			async: true, // 同步
			success: function(callBack) {
				if(callBack.success) {
					//$("#id").val(callBack.data.id);
					$(".close").click();
					addressList();
					flag = true;
				} else {
					layer.alert(callBack.message, {icon: 5,shift: 6,time: 0});
				}
			}
		});
	}
	
	return flag;
}

function checkAddressForm() {
	var receiverName = $.trim($("#receiverName").val());
	if($.trim(receiverName) == "" || receiverName == undefined) {
		$("#nameError").html("请填写收货人");
    	$("#nameError").parent(".form-group").addClass("has-error");
    	$("#receiverName").focus();
		return false;
	}
	if (receiverName.length > 20) {
		$("#nameError").html("收货人不能超过20个字符");
    	$("#nameError").parent(".form-group").addClass("has-error");
    	$("#receiverName").focus();
		return false;
	}
	var mobile = $("#receiverMobile").val();
	if($.trim(mobile) == "" || mobile == undefined) {
		$("#receiverMobileError").html("请填写手机号");
    	$("#receiverMobileError").parent(".form-group").addClass("has-error");
    	$("#receiverMobile").focus();
		return false;
	}
	if(!/^1\d{10}$/.test(mobile)) {
    	$("#receiverMobileError").html("请输入正确的手机号");
    	$("#receiverMobileError").parent(".form-group").addClass("has-error");
    	$("#receiverMobile").focus();
        return false;
    }
	var addressDetail = $("#addressDetail").val();
	if($.trim(addressDetail) == "" || addressDetail == undefined) {
		$("#addressDetailError").html("请填写详细地址");
    	$("#addressDetailError").parent(".form-group").addClass("has-error");
    	$("#addressDetail").focus();
        return false;
	}
	if (addressDetail.length > 200) {
		$("#addressDetailError").html("详细地址不能超过200个字符");
    	$("#addressDetailError").parent(".form-group").addClass("has-error");
    	$("#addressDetail").focus();
        return false;
	}
	var provinceId = $("#provinceId option:selected").val();
	if($.trim(provinceId) == "" || provinceId == undefined) {
		layer.alert("请选择省份", {icon: 5,shift: 6,time: 0});
		return false;
	}
	var cityId = $("#cityId option:selected").val();
	if($.trim(cityId) == "" || cityId == undefined) {
		layer.alert("请选择城市", {icon: 5,shift: 6,time: 0});
		return false;
	}
	var areaId = $("#areaId option:selected").val();
	if($.trim(areaId) == "" || areaId == undefined) {
		layer.alert("请选择区县", {icon: 5,shift: 6,time: 0});
		return false;
	}
	
	var dataJson = {};
	dataJson.id = $("#addrId").val();
	dataJson.receiverName = receiverName;
	dataJson.receiverMobile = mobile;
	dataJson.addressDetail = $.trim(addressDetail);
	dataJson.provinceId = provinceId;
	dataJson.provinceName = $("#provinceId option:selected").text();
	dataJson.cityId = cityId;
	dataJson.cityName = $("#cityId option:selected").text();
	dataJson.areaId = areaId;
	dataJson.areaName = $("#areaId option:selected").text();
	return dataJson;
}


function deleteAddress(id) {
	layer.confirm('确认删除该地址吗？', {
        icon : 3,
        title : '确认提示'
    }, function(index, layero) {
    	$.ajax({
    		url: _path + "/my/address/delete",
    		data: {id : id},
    		type: "post",
    		dataType: "json",
    		async: false, // 异步
    		success: function(callBack) {
    			if(callBack.success) {
    				addressList();
    				layer.msg(callBack.message, {icon: 6,shift: 6,time: 1000});
    				flag = true;
    			} else {
    				layer.alert(callBack.message, {icon: 5,shift: 6,time: 1000});
    			}
    		}
    	});
        layer.close(index);
    });
	
}

function editAddress(id) {
	if ($("#provinceId").text() == '' || $("#provinceId").text() == null) {
		$("#selectCity").citySelect({"prov":"420000"}); 
	}
	if (id) {
		$.ajax({
			url: _path + "/my/address/getAddressById",
			data: {id : id},
			type: "post",
			dataType: "json",
			async: false, // 异步
			success: function(data) {
				if(data.address) {
					var address = data.address;
					var provinceId = address.provinceId;
					var cityId = address.cityId;
					var areaId = address.areaId;
					$("#addrId").val(address.id);
					$("#receiverName").val(address.receiverName).parent().removeClass("is-empty");
					$("#receiverMobile").val(address.receiverMobile).parent().removeClass("is-empty");
					$("#addressDetail").val(address.addressDetail).parent().removeClass("is-empty");
					$("#selectCity").citySelect({"prov": provinceId, "city": cityId, "dist": areaId}); 
				}
			}
		});
		
	} else {
		$("#addrId").val('');
		$("#receiverName").val('').parent().addClass("is-empty");
		$("#receiverMobile").val('').parent().addClass("is-empty");
		$("#addressDetail").val('').parent().addClass("is-empty");
		$("#selectCity").citySelect({"prov":"420000"}); 
		//$("#selectCity").citySelect({"prov":"420000", "city":"420100", "dist":"420111"}); 
	}
}

function setDefault(id) {
	layer.confirm('确认设置该地址为默认地址吗？', {
        icon : 3,
        title : '确认提示'
    }, function(index, layero) {
    	$.ajax({
    		url: _path + "/my/address/setDefault",
    		data: {id : id},
    		type: "post",
    		dataType: "json",
    		async: false, // 异步
    		success: function(callBack) {
    			if(callBack.success) {
    				addressList();
    				layer.msg(callBack.message, {icon: 6,shift: 6,time: 1000});
    				flag = true;
    			} else {
    				layer.alert(callBack.message, {icon: 5,shift: 6,time: 1000});
    			}
    		}
    	});
        layer.close(index);
    });
}