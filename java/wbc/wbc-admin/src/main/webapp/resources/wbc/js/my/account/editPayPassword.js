$(function() {
	$("#payPassword").blur(function() {
		var payPassword = $(this).val();
		if (payPassword == null || payPassword == '') {
			$("#payPasswordError").html("请输入支付密码");
			$("#payPasswordError").parent('.form-group').addClass('has-error');
			$("#payPasswordError").show();
			$("#countdown_payPassword").attr("disabled", "true");
		} else {
			if (payPassword.length <6 || payPassword.length > 18) {
				$("#payPasswordError").html("密码6-18个字符之间");
				$("#payPasswordError").parent('.form-group').addClass('has-error');
				$("#payPasswordError").show();
				$("#countdown_payPassword").attr("disabled", "true");
			} else {
				$("#payPasswordError").html("");
				$("#payPasswordError").parent('.form-group').removeClass('has-error');
				$("#payPasswordError").hide();
				$("#countdown_payPassword").removeAttr("disabled");
			}
		}
	});
	
	// 发送验证码
	$("#countdown_payPassword").click(function(){
		var imgCaptcha_payPassword = $("#imgCaptcha_payPassword").val();
		if (imgCaptcha_payPassword == null || imgCaptcha_payPassword == '') {
			$("#imgCaptchaError_payPassword").html("请先填写验证码");
			$("#imgCaptchaError_payPassword").parent(".form-group").removeClass("hide").addClass("has-error");
			$("#imgCaptcha_payPassword").focus();
			$('#captchaImage_payPassword').hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
			return;
		} else {
			$("#imgCaptchaError_payPassword").parent(".form-group").addClass("hide");
		}
		
        var  timer = 60;
        $(this).attr("disabled","disabled")
        $("#countdown_payPassword").html("("+timer+")"+"秒后重发")
        var DSQ = setInterval(function(){
              timer--; 
              if(timer>0){
               $("#countdown_payPassword").html("("+timer+")"+"秒后重发")
              }else{
               clearInterval(DSQ)
               $("#countdown_payPassword").html("获取动态码")
               $("#countdown_payPassword").removeAttr("disabled")
              }
         },1000);
        var mobile = $("#mobileInfo").val();//2017-4-7 支付宝修改的手机号验证试用账号的手机号
        var imgCaptcha_payPassword = $("#imgCaptcha_payPassword").val();
        // 发验证码的请求
        genMobileCaptcha(mobile,1,imgCaptcha_payPassword);
        // 清掉验证码
        $("#imgCaptcha_alipay").val('');
        $("#payPasswordCaptcha").focus();
   });
	
	// 验证支付宝账号的验证码
	$("#payPasswordCaptcha").blur(function() {
		checkPayPasswordCaptcha(0);
	});
	
	/**
	 * 支付宝编辑框打开
	 */
	$(".iconpaypassword").click(function(){
		$(this).closest(".c_pay_password").hide();
		$(this).closest(".c_pay_password").siblings(".c_pay,.c_email,.password,.c_num,.paypasswordwrap").show();
		$(this).closest(".c_pay_password").siblings(".emailwrap1,.emailwrap2,.numwrap2,.numwrap1,.passwordwrap,.apaywrap1").hide();
	});
	/**
	 * 支付宝编辑框收起
	 */
	$(".c_tip4").click(function(){
		$(this).closest(".paypasswordwrap").hide();
		$(this).closest(".paypasswordwrap").siblings(".c_pay_password").show();
	});
	// 保存编辑的支付宝账号
	$(".payPasswordBtn").click(function() {
		var payPassword = $("#payPassword").val();
		if (!checkPayPasswordCaptcha(1)) {
			return;
		}
		// 更新支付宝账号
		$.ajax({
    		type : "POST",
    		url :  _path + "/my/editPayPassword",
    		data : {
    			"payPassword" : payPassword,
    		},
    		dataType : "json",
    		async : false,
    		success : function(result) {
    			if (result) {
    				$("#payPasswordInfo").val('********');
    				$("#payPasswordInfo").parent().removeClass("is-empty");
    				$("#payPassword").val('');
    				$("#payPassword").parent().addClass("is-empty");
    				$("#payPasswordCaptcha").val('');
    				$("#payPasswordCaptcha").parent().addClass("is-empty");
    				$(".paypasswordwrap").hide();
    				$(".c_pay_password").show();
    			}
    		},
    		error : function(errorMsg) {
    			
    		}
    	});
	});
});

function checkPayPassword() {
	var payPassword = $("#payPassword").val();
	if (payPassword == null || payPassword == '') {
		$("#payPasswordError").html("请输入支付密码");
		$("#payPasswordError").parent('.form-group').addClass('has-error');
		$("#payPasswordError").show();
		$("#countdown_payPassword").attr("disabled", "true");
		return false;
	} else {
		if (payPassword.length <6 || payPassword.length > 18) {
			$("#payPasswordError").html("密码6-18个字符之间");
			$("#payPasswordError").parent('.form-group').addClass('has-error');
			$("#payPasswordError").show();
			$("#countdown_payPassword").attr("disabled", "true");
			return false;
		} else {
			$("#payPasswordError").html("");
			$("#payPasswordError").parent('.form-group').removeClass('has-error');
			$("#countdown_payPassword").removeAttr("disabled");
			return true;
		}
	}
}
//验证账号手机的验证码
function checkPayPasswordCaptcha(isClear) {
	var captcha = $("#payPasswordCaptcha").val();
	//var mobile = $("#alipayAccount").val();
	var mobile = $("#mobileInfo").val();//2017-4-7 支付宝修改的手机号验证试用账号的手机号
	if (!checkPayPassword()) {
		return false;
	}
	if (captcha == null || captcha == "") {
		$("#payPasswordCaptchaError").html("动态码不能为空");
		$("#payPasswordCaptchaError").parent(".form-group").addClass('has-error');
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
        		$("#payPasswordCaptchaError").html("动态码错误");
        		$("#payPasswordCaptchaError").parent(".form-group").addClass('has-error');
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
		$("#payPasswordCaptchaError").html("");
		$("#payPasswordCaptchaError").parent(".form-group").removeClass('has-error');
	}
	return flag;
}