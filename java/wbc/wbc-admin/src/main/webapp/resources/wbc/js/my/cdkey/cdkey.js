

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
	
	
	$(".c-informgroup") .click(function() {
		$(this).children('.c-xiala').toggle().closest(".c-informgroup").siblings(".c-informgroup").children(".c-xiala").hide();
	});
	
	$('#cdkeyCaptchaImage').click(function() {//生成验证码
        $(this).hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
    });
	
	$("#cdkeyCode").blur(function() {
		var cdkey = $("#cdkeyCode").val();
		if (cdkey == null || cdkey == "") {
			$("#cdkeyError").html("请输入兑换码");
			$("#cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#cdkeyError").show();
			return;
		}
		if (cdkey.length != 12) {
			$("#cdkeyError").html("兑换码错误，请重新输入");
			$("#cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#cdkeyError").show();
			return;
		}
		if (cdkey.substring(0,2) != "SP" && cdkey.substring(0,2) != "JB" && cdkey.substring(0,2) != "GB") {
			$("#cdkeyError").html("兑换码错误，请重新输入");
			$("#cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#cdkeyError").show();
			return;
		}
		
		$("#cdkeyError").html("");
		$("#cdkeyError").parent(".form-group").removeClass("has-error")
		$("#cdkeyError").hide();
	});
	$("#cdkeyExchangeBtn").click(function() {
		var cdkey = $("#cdkeyCode").val();
		if (cdkey == null || cdkey == "") {
			$("#cdkeyError").html("请输入兑换码");
			$("#cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#cdkeyError").show();
			return;
		}
		if (cdkey.length != 12) {
			$("#cdkeyError").html("兑换码错误，请重新输入");
			$("#cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#cdkeyError").show();
			return;
		}
		if (cdkey.substring(0,2) != "SP" && cdkey.substring(0,2) != "JB" && cdkey.substring(0,2) != "GB") {
			$("#cdkeyError").html("兑换码错误，请重新输入");
			$("#cdkeyError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#cdkeyError").show();
			return;
		}
		$("#cdkeyError").html("");
		$("#cdkeyError").parent(".form-group").removeClass("has-error")
		$("#cdkeyError").hide();
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
	            			saveAddressAndExchangeCdk();
	            		});
	            	} else {
	            		layer.msg(resultdata.msg, {icon : 1});
	            		$("#cdkeyCode").val("");
		            	$("#cdkeyCaptcha").val("");
		            	$("#cdkeyCaptchaImage").click();
	            	}
	            } else {
	            	layer.alert(resultdata.msg, {icon : 5,shift : 6,time : 0});
	            }
	        },
	        error : function(errorMsg) {
	        }
	    });
	});
	
	
});
//保存收货地址，并cd-key兑换
function saveAddressAndExchangeCdk() {
	var flag = true;
	// 保存/修改地址
	flag = saveOrUpdateAddress();
	if(flag) {
		var addressId = $("#addrId").val();
		var cdkey = $("#cdkeyCode").val();
		var cdkeyCaptcha = $("#cdkeyCaptcha").val();
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
	            	$("#cdkeyCode").val("");
	            	$("#cdkeyCaptcha").val("");
	            	$("#cdkeyCaptchaImage").click();
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
function checkCdkeyCaptcha() {
	var cdkeyCaptcha = $("#cdkeyCaptcha").val();
	$.ajax({
        type : "POST",
        url : _path + "/captcha/check",
        data : {
            "imgCaptcha" : cdkeyCaptcha
        },
        dataType : "json",
        success : function(resultdata) {
            if (resultdata != 1) {
            	$("#cdkeyCaptchaError").html("验证码错误");
            	$("#cdkeyExchangeBtn").attr("disabled","true");
            } else {
            	$("#cdkeyCaptchaError").html("");
            	$("#cdkeyExchangeBtn").removeAttr("disabled");
            }
        },
        error : function(errorMsg) {
        }
    });
}