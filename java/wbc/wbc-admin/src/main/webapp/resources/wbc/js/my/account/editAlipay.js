$(function() {
	var alipayType = 0;// 0:手机号支付宝  1:邮箱支付宝
	// 验证支付宝账号
	$("#alipayAccount").blur(function () {
		var alipayAccount = $("#alipayAccount").val();
		if(alipayAccount == "") {
			$("#alipayAccountError").html("请输入支付宝账号");
			$("#alipayAccountError").parent('.form-group').addClass('has-error');
			$("#countdown_alipay").attr("disabled", "true");
			//$(".paybtn").attr("disabled", "true");
			return false;
	    } else {
	        var emailReg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
	        var mobileReg = /^1\d{10}$/;
	        if(!emailReg.test(alipayAccount) && !mobileReg.test(alipayAccount)) {
	        	$("#alipayAccountError").html("支持邮箱或手机号的支付宝账号");
	        	$("#alipayAccountError").parent('.form-group').addClass('has-error');
	        	$("#countdown_alipay").attr("disabled", "true");
				//$(".paybtn").attr("disabled", "true");
	            return false;
	        } else if (emailReg.test(alipayAccount) && !mobileReg.test(alipayAccount)) {
	        	// 用邮箱支付宝，发邮箱验证码
	        	//alipayType = 1;
	        	alipayType = 0;//2017-4-7 支付宝修改的手机号验证试用账号的手机号
	        	$("#alipayAccountError").html("");
	        	$("#alipayAccountError").parent('.form-group').removeClass('has-error');
	        	if ($("#countdown_alipay").text().trim() == "获取动态码") {
	        		$("#countdown_alipay").removeAttr("disabled")
	        	}
	        } else if (!emailReg.test(alipayAccount) && mobileReg.test(alipayAccount)) {
	        	// 用手机支付宝，发手机验证码
	        	alipayType = 0;
	        	$("#alipayAccountError").html("");
	        	$("#alipayAccountError").parent('.form-group').removeClass('has-error');
	        	if ($("#countdown_alipay").text().trim() == "获取动态码") {
	        		$("#countdown_alipay").removeAttr("disabled")
	        	}
	        }
	        return true;
	    }
	});
	// 发送验证码
	$("#countdown_alipay").click(function(){
		if (alipayType == 0) {
			var imgCaptcha_alipay = $("#imgCaptcha_alipay").val();
			 if (imgCaptcha_alipay == null || imgCaptcha_alipay == '') {
				$("#imgCaptchaError_alipay").html("请先填写验证码");
				$("#imgCaptchaError_alipay").parent(".form-group").removeClass("hide").addClass("has-error");
				$("#imgCaptcha_alipay").focus();
				$('#captchaImage_alipay').hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
				return;
			} else {
				$("#imgCaptchaError_alipay").parent(".form-group").addClass("hide");
			}
	    	
	    }
        var  timer = 60;
        $(this).attr("disabled","disabled")
        $("#countdown_alipay").html("("+timer+")"+"秒后重发")
        var DSQ = setInterval(function(){
              timer--; 
              if(timer>0){
               $("#countdown_alipay").html("("+timer+")"+"秒后重发")
              }else{
               clearInterval(DSQ)
               $("#countdown_alipay").html("获取动态码")
               $("#countdown_alipay").removeAttr("disabled")
              }
         },1000);
        if (alipayType == 0) {
	    	//var mobile = $("#alipayAccount").val();
        	var mobile = $("#mobileInfo").val();//2017-4-7 支付宝修改的手机号验证试用账号的手机号
	    	var imgCaptcha_alipay = $("#imgCaptcha_alipay").val();
	    	// 发验证码的请求
	    	genMobileCaptcha(mobile,1,imgCaptcha_alipay);
	    	// 清掉验证码
	         $("#imgCaptcha_alipay").val('');
	    	
	    } else {
	    	var email = $("#alipayAccount").val();
	    	// 发验证码的请求
	    	genEmailCaptcha(email);
	    }
        $("#alipayAccountCaptcha").focus();
   });
	
	// 验证支付宝账号的验证码
	$("#alipayAccountCaptcha").blur(function() {
		if (alipayType == 0) {
			checkAlipayMobileCaptcha(0);
		} else {
			checkAlipayEmailCaptcha(0);
		}
	});
	
	// 保存编辑的支付宝账号
	$(".paybtn").click(function() {
		var alipayAccount = $("#alipayAccount").val();
		if (alipayType == 0) {
			if (!checkAlipayMobileCaptcha(1)) {
				return;
			}
		} else {
			if (!checkAlipayEmailCaptcha(1)) {
				return;
			}
		}
		// 更新支付宝账号
		$.ajax({
    		type : "POST",
    		url :  _path + "/my/editAlipayAccount",
    		data : {
    			"alipayAccount" : alipayAccount,
    		},
    		dataType : "json",
    		async : false,
    		success : function(result) {
    			if (result) {
    				$("#alipay").val(alipayAccount);
    				$("#alipay").parent().removeClass("is-empty");
    				$("#alipayAccount").val('');
    				$("#alipayAccount").parent().addClass("is-empty");
    				$("#alipayAccountCaptcha").val('');
    				$("#alipayAccountCaptcha").parent().addClass("is-empty");
    				$(".apaywrap1").hide();
    				$(".c_pay").show();
    			}
    		},
    		error : function(errorMsg) {
    			
    		}
    	});
	});
});


//验证支付宝账号手机的验证码
function checkAlipayMobileCaptcha(isClear) {
	var captcha = $("#alipayAccountCaptcha").val();
	//var mobile = $("#alipayAccount").val();
	var mobile = $("#mobileInfo").val();//2017-4-7 支付宝修改的手机号验证试用账号的手机号
	if (!checkAlipayAccount()) {
		return false;
	}
	if (captcha == null || captcha == "") {
		$("#alipayAccountCaptchaError").html("动态码不能为空");
		$("#alipayAccountCaptchaError").parent(".form-group").addClass('has-error');
		//$("#countdown_alipay").attr("disabled","true");
		return false;
	}
	var flag = true;
	$.ajax({
        type : "POST",
        url : _path + "/withoutAuth/checkCaptcha",
        data : {
            "captcha" : captcha,
            "mobile" : mobile,
            "isClear" : isClear
        },
        async : false,
        success : function(resultdata) {
        	if (resultdata == 0) {
        		$("#alipayAccountCaptchaError").html("动态码错误");
        		$("#alipayAccountCaptchaError").parent(".form-group").addClass('has-error');
        		//$("#countdown_alipay").attr("disabled","true");
         		flag = false;
            } else {
            	flag = true;
            }
        },
        error : function(errorMsg) {
        	flag = false;
        }
    });
	if (flag) {
		$("#alipayAccountCaptchaError").html("");
		$("#alipayAccountCaptchaError").parent(".form-group").removeClass('has-error');
		//$("#countdown_alipay").removeAttr("disabled");
		//$(".l-save").removeAttr("disabled");
	} /*else {
		//(".l-save").attr("disabled","true");
	}*/
	return flag;
}
function checkAlipayEmailCaptcha(isClear) {
	var captcha =  $("#alipayAccountCaptcha").val();
	var email = $("#alipayAccount").val();
	if (!checkAlipayAccount()) {
		return false;
	}
	if (captcha == null || captcha == "") {
		$("#alipayAccountCaptchaError").html("动态码不能为空");
		$("#alipayAccountCaptchaError").parent(".form-group").addClass('has-error');
		//$("#countdown_alipay").attr("disabled","true");
		return false;
	}
	var flag = true;
	$.ajax({
        type : "POST",
        url : _path + "/fp/checkEmailCaptcha",
        data : {
            "captcha" : captcha,
            "email" : email,
            "isClear" : isClear
        },
        async : false,
        success : function(resultdata) {
        	if (resultdata == 0) {
        		$("#alipayAccountCaptchaError").html("动态码错误");
        		$("#alipayAccountCaptchaError").parent(".form-group").addClass('has-error');
        		//$("#countdown_alipay").attr("disabled","true");
         		flag = false;
            } else {
            	flag = true;
            }
        },
        error : function(errorMsg) {
        	flag = false;
        }
    });
	if (flag) {
		$("#alipayAccountCaptchaError").html("");
		$("#alipayAccountCaptchaError").parent(".form-group").removeClass('has-error');
		//$(".l-save").removeAttr("disabled");
		//$("#countdown_alipay").removeAttr("disabled");
	} /*else {
		//$(".l-save").attr("disabled","true");
	}*/
	return flag;
}

function checkAlipayAccount() {
	var alipayAccount = $("#alipayAccount").val();
	if(alipayAccount == "") {
		$("#alipayAccountError").html("请输入支付宝账号");
		$("#alipayAccountError").parent(".form-group").addClass('has-error');
		$("#countdown_alipay").attr("disabled", "true");
		//$(".l-save").attr("disabled", "true");
		return false;
    } else {
        var emailReg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        var mobileReg = /^1\d{10}$/;
        if(!emailReg.test(alipayAccount) && !mobileReg.test(alipayAccount)) {
        	$("#alipayAccountError").html("支持邮箱或手机号的支付宝账号");
        	$("#alipayAccountError").parent(".form-group").addClass('has-error');
        	$("#countdown_alipay").attr("disabled", "true");
            //$(".l-save").attr("disabled", "true");
            return false;
        } else if (emailReg.test(alipayAccount) && !mobileReg.test(alipayAccount)) {
        	// 用邮箱支付宝，发邮箱验证码
        	alipayType = 1;
        	$("#alipayAccountError").html("");
        	$("#alipayAccountError").parent(".form-group").removeClass('has-error');
        	if ($("#countdown_alipay").text().trim() == "获取动态码") {
        		$("#countdown_alipay").removeAttr("disabled")
        	}
        } else if (!emailReg.test(alipayAccount) && mobileReg.test(alipayAccount)) {
        	// 用手机支付宝，发手机验证码
        	alipayType = 0;
        	$("#alipayAccountError").html("");
        	$("#alipayAccountError").parent(".form-group").removeClass('has-error');
        	if ($("#countdown_alipay").text().trim() == "获取动态码") {
        		$("#countdown_alipay").removeAttr("disabled")
        	}
        }
        return true;
    }
}