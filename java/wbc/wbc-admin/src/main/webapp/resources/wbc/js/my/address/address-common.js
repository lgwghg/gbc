$(function() {
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
    });
});
function saveAddressOrOther() {
	saveOrUpdateAddress();
}
// 收货地址
function defaultAddress() {
	$.ajax({
		url: _path + "/my/address/default",
		data: {},
		type: "post",
		dataType: "json",
		success: function(callBack) {
			var prov = "420000";
			if(callBack.address) {
				var address = callBack.address;
				$("#addrId").val(address.id);
				$("#receiverName").val(address.receiverName);
				$("#receiverMobile").val(address.receiverMobile);
				$("#addressDetail").val(address.addressDetail);
				
				$("#receiverName").parents("div").removeClass("is-empty");
				$("#receiverMobile").parents("div").removeClass("is-empty");
				$("#addressDetail").parents("div").removeClass("is-empty");
				prov = address.provinceId;
				$("#dropdown-menu").citySelect({"prov":address.provinceId, "city":address.cityId, "dist":address.areaId}); 
			} else {
				$("#dropdown-menu").citySelect({"prov": prov, "city":"420100", "dist":"420111"}); 
			}
			if(!(navigator.userAgent.indexOf("Firefox")>0)){
				$("#dropdown-menu select").dropdown();
				$("#dropdown-menu select").addClass("form-control-x");
				var xprov = $("#dropdown-menu #provinceId").next().find("ul li[value="+prov+"]");
				$("#dropdown-menu #provinceId").next().find("input").val(xprov.html());
				$("#dropdown-menu #provinceId").next().find("ul li").removeClass("selected");
				xprov.addClass("selected");
			}
		}
	});
}

function saveOrUpdateAddress() {
	var flag = false;
	var dataJson = checkAddressForm();
	if(dataJson) {
		var id = dataJson.id;
		var url = _path + "/my/address/add";
		if(id != "") {
			url = _path + "/my/address/edit";
		}
		$.ajax({
			url: url,
			data: dataJson,
			type: "post",
			dataType: "json",
			async: false, // 同步
			success: function(callBack) {
				if(callBack.success) {
					$("#id").val(callBack.data.id);
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
	$("#nameError").html("");
	$("#receiverMobileError").html("");
	$("#addressError").html("");
	$("#nameError").parent(".form-group").removeClass("has-error-x");
	$("#receiverMobileError").parent(".form-group").removeClass("has-error-x");
	$("#addressError").parent(".form-group").removeClass("has-error-x");
	
	var receiverName = $("#receiverName").val();
	if($.trim(receiverName) == "" || receiverName == undefined) {
		$("#nameError").html("请填写收货人");
    	$("#nameError").parent(".form-group").addClass("has-error-x");
		return false;
	}
	var receiverMobile = $("#receiverMobile").val();
	if($.trim(receiverMobile) == "" || receiverMobile == undefined) {
		$("#receiverMobileError").html("请填写手机号");
    	$("#receiverMobileError").parent(".form-group").addClass("has-error-x");
		return false;
	}
	if(!/^1\d{10}$/.test(receiverMobile)) {
    	$("#receiverMobileError").html("请输入正确的手机号");
    	$("#receiverMobileError").parent(".form-group").addClass("has-error-x");
        return false;
    }
	var addressDetail = $("#addressDetail").val();
	if($.trim(addressDetail) == "" || addressDetail == undefined) {
		$("#addressError").html("请输入正确的手机号");
    	$("#addressError").parent(".form-group").addClass("has-error-x");
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
	dataJson.receiverMobile = receiverMobile;
	dataJson.addressDetail = addressDetail;
	dataJson.provinceId = provinceId;
	dataJson.provinceName = $("#provinceId option:selected").text();
	dataJson.cityId = cityId;
	dataJson.cityName = $("#cityId option:selected").text();
	dataJson.areaId = areaId;
	dataJson.areaName = $("#areaId option:selected").text();

	return dataJson;
}