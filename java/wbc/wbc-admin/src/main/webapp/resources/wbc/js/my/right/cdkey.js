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
	$('#right_cdkeyCaptchaImage').click(function() {//生成验证码
        $(this).hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
    });
	
	$("#right_cdkeyCode").blur(function() {
		var cdkey = $("#right_cdkeyCode").val();
		if (cdkey == null || cdkey == "") {
			$("#right_cdkeyError").html("请输入兑换码");
			$("#right_cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#right_cdkeyError").show();
			return;
		}
		if (cdkey.length != 12) {
			$("#right_cdkeyError").html("兑换码错误，请重新输入");
			$("#right_cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#right_cdkeyError").show();
			return;
		}
		if (cdkey.substring(0,2) != "SP" && cdkey.substring(0,2) != "JB" && cdkey.substring(0,2) != "GB") {
			$("#right_cdkeyError").html("兑换码错误，请重新输入");
			$("#right_cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#right_cdkeyError").show();
			return;
		}
		
		$("#right_cdkeyError").html("");
		$("#right_cdkeyError").parent(".form-group").removeClass("has-error")
		$("#right_cdkeyError").hide();
	});
	$("#right_cdkeyExchangeBtn").click(function() {
		var cdkey = $("#right_cdkeyCode").val();
		if (cdkey == null || cdkey == "") {
			$("#right_cdkeyError").html("请输入兑换码");
			$("#right_cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#right_cdkeyError").show();
			return;
		}
		if (cdkey.length != 12) {
			$("#right_cdkeyError").html("兑换码错误，请重新输入");
			$("#right_cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#right_cdkeyError").show();
			return;
		}
		if (cdkey.substring(0,2) != "SP" && cdkey.substring(0,2) != "JB" && cdkey.substring(0,2) != "GB") {
			$("#right_cdkeyError").html("兑换码错误，请重新输入");
			$("#right_cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#right_cdkeyError").show();
			return;
		}
		$("#right_cdkeyError").html("");
		$("#right_cdkeyError").parent(".form-group").removeClass("has-error")
		$("#right_cdkeyError").hide();
		$.ajax({
	        type : "POST",
	        url : _path + "/my/cdkey/exchange",
	        data : {
	            "code" : cdkey
	        },
	        dataType : "json",
	        success : function(resultdata) {
	            if (resultdata.state == 'success') {
	            	if (resultdata.msg == 'SP') {
	            		// 收货地址
	            		defaultAddress();
	            		$("#myModalAddress").modal("show");
	            		$("#saveAddress").off("click");
	            		$("#saveAddress").click(function() {
	            			r_saveAddressAndExchangeCdk();
	            		});
	            	} else {
	            		layer.msg(resultdata.msg, {icon : 1});
	            		$("#right_cdkeyCode").val('');
	            		$("#right_cdkeyCaptcha").val('');
	            		$("#right_cdkeyExchangeBtn").attr("disabled", "true");
	            		$("#right_cdkeyCaptchaImage").click();
	            	}
	            } else {
	            	layer.alert(resultdata.msg, {icon : 5,shift : 6,time : 0});
	            }
	        },
	        error : function(errorMsg) {
	        }
	    });
	});
	// 验证验证码
	/*$("#cdkeyCaptcha").blur(function() {
		
	});*/
	
});
function r_saveAddressAndExchangeCdk() {
	var flag = true;
	// 保存/修改地址
	flag = saveOrUpdateAddress();
	if(flag) {
		var addressId = $("#addrId").val();
		var cdkey = $("#right_cdkeyCode").val();
		var cdkeyCaptcha = $("#right_cdkeyCaptcha").val();
		$.ajax({
	        type : "POST",
	        url : _path + "/my/cdkey/exchange",
	        data : {
	            "code" : cdkey,
	            "addressId" : addressId
	        },
	        dataType : "json",
	        success : function(resultdata) {
	            if (resultdata.state == 'success') {
	            	$('.close').click();
	            	$("#right_cdkeyCode").val('');
            		$("#right_cdkeyCaptcha").val('');
            		$("#right_cdkeyExchangeBtn").attr("disabled", "true");
	            	$("#right_cdkeyCaptchaImage").click();
	            	layer.msg(resultdata.msg, {icon : 1});
	            } else {
	            	layer.alert(resultdata.msg, {icon : 5,shift : 6,time : 0});
	            }
	        },
	        error : function(errorMsg) {
	        }
	    });
	}
}
function right_checkCdkeyCaptcha() {
	var cdkeyCaptcha = $("#right_cdkeyCaptcha").val();
	$.ajax({
        type : "POST",
        url : _path + "/captcha/check",
        data : {
            "imgCaptcha" : cdkeyCaptcha
        },
        dataType : "json",
        success : function(resultdata) {
            if (resultdata != 1) {
            	$("#right_cdkeyCaptchaError").html("验证码错误");
            	$("#right_cdkeyCaptchaError").parent(".form-group").removeClass("hide").addClass("has-error")
        		$("#right_cdkeyCaptchaError").show();
            	$("#right_cdkeyExchangeBtn").attr("disabled","true");
            } else {
            	$("#right_cdkeyCaptchaError").html("");
            	$("#right_cdkeyCaptchaError").parent(".form-group").removeClass("has-error")
        		$("#right_cdkeyCaptchaError").hide();
            	$("#right_cdkeyExchangeBtn").removeAttr("disabled");
            }
        },
        error : function(errorMsg) {
        }
    });
}
