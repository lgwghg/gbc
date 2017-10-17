$(function() {
	$('#forgetPasswordForm').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
			setPassword();
	    }
	});
	
	$("#password").blur(function() {
		checkPassword();
	});
	$("#rePassword").blur(function() {
		checkRePassword();
	});
	$("#mobile").blur(function() {
		checkMobile();
	});
	$("#captcha").blur(function() {
		checkCaptcha(0);
	});
	
	
	//验证码
    $(".send-auth-code").click(function () {
    	var imgCaptcha = $("#imgCaptcha").val();
    	if (imgCaptcha == null || imgCaptcha == '') {
			$("#imgCaptchaError").html("请先填写验证码");
			$("#imgCaptchaError").parent(".form-group").removeClass("hide").addClass("has-error");
			$("#imgCaptcha").focus();
			return;
		} else {
			$("#imgCaptchaError").parent(".form-group").addClass("hide");
		}
        $(this).attr("disabled", "disabled")
        var mobile = $("#mobile").val();
        var timer = 60;
        var DSQ = setInterval(function () {
            timer--;
            if (timer > 0) {
                $(".send-auth-code").html("(" + timer + ")" + "秒后重发")
            } else {
                clearInterval(DSQ)
                $(".send-auth-code").html("获取动态码")
                $(".send-auth-code").removeAttr("disabled")
            }
        }, 1000);
        var imgCaptcha = $("#imgCaptcha").val();
       // 发验证码的请求,身份验证
        genMobileCaptcha(mobile, 1, imgCaptcha);
     // 清掉验证码
        $("#imgCaptcha").val('');
        $("#captcha").focus();
    })
});

// 验证手机号
function checkMobile() {
	var mobile = $("#mobile").val();
    if (mobile == "") {
    	$("#mobileError").html("手机号不能为空");
    	$("#mobileError").parent('.form-group').addClass("has-error");
    	$(".send-auth-code").attr("disabled","true");
    	$("#mobileError").show();
 		//$("#mobile").focus();
        return false;
    } else {
        if(!/^1\d{10}$/.test(mobile)) {
        	$("#mobileError").html("请输入正确的手机号");
        	$("#mobileError").parent('.form-group').addClass("has-error");
        	$(".send-auth-code").attr("disabled","true");
        	$("#mobileError").show();
     		//$("#mobile").focus();
            return false;
        } else {
            var flag = true;
            $.ajax({
                type : "POST",
                url : _path + "/user/withoutAuth/validateUserMobile",
                async : false,
                data : {
                    "mobile" : mobile
                },
                dataType : "json",
                beforeSend:function(){
                	$(".send-auth-code").attr("disabled","true");
    			},
                success : function(resultdata) {
                    if (resultdata) {
                    	$("#mobileError").html("不存在该手机号的用户");
                    	$("#mobileError").parent('.form-group').addClass("has-error");
                    	$(".send-auth-code").attr("disabled","true");
                    	$("#mobileError").show();
                 		//$("#mobile").focus();
                        flag = false;
                    }else{
                    	flag = true;
                    	if ($(".send-auth-code").text().trim() == '获取动态码') {
                    		$(".send-auth-code").removeAttr("disabled");
                    	}
                    }
                },
                error : function(errorMsg) {
                	layer.alert('服务器未响应,请稍后再试', {icon : 5,shift : 6,time : 3000});
             		//$("#mobile").focus();
                    flag = false;
                }
            });
            if(!flag)
            {	
            	//$("#submitCheckMobile").attr("disabled","true");
                return flag;   
            } else {
            	$("#mobileError").html("");
            	$("#mobileError").parent('.form-group').removeClass("has-error");
            	$("#mobileError").hide();
            	return true; 
            }
        }
    }
}

function checkCaptcha(isClear) {
	var captcha = $("#captcha").val();
	var mobile = $("#mobile").val();
	if (!checkMobile()) {
		return "mobileError";
	}
	if (captcha == null || captcha == "") {
		$("#captchaError").html("动态码不能为空");
		$("#captchaError").parent('.form-group').addClass("has-error");
		$("#captchaError").show();
		//$("#submitCheckMobile").attr("disabled","true");
		return false;
	}
	var flag = true;
	$.ajax({
        type : "POST",
        url : _path + "/withoutAuth/checkCaptcha",
        async : false,
        data : {
            "captcha" : captcha,
            "mobile" : mobile,
            "isClear" : isClear
        },
        beforeSend:function(){
        	//$("#submitCheckMobile").attr("disabled","true");
		},
        success : function(resultdata) {
        	if (resultdata == 0) {
        		$("#captchaError").html("动态码错误");
        		$("#captchaError").parent('.form-group').addClass("has-error");
        		$("#captchaError").show();
        		flag = false;
            } else {
            	$("#captchaError").html("");
            	$("#captchaError").parent('.form-group').removeClass("has-error");
            	$("#captchaError").hide();
            	return true;
            }
        },
        error : function(errorMsg) {
        	return false;
        }
    });
	if(!flag)
    {	
        return flag;   
    } else {
    	//$("#submitCheckMobile").removeAttr("disabled");
    	return true; 
    }
}

function submitCheckMobile() {
	if (checkCaptcha(0)) {
		$("#findPasswordForm").submit();
	}
}


//验证邮箱
function checkEmail() {
	var email = $("#email").val();
    if (email == "") {
    	$("#emailError").html("邮箱不能为空");
 		//$("#mobile").focus();
        return false;
    } else {
    	var emailReg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        if(!emailReg.test(email)) {
        	$("#emailError").html("请输入正确的邮箱地址");
     		//$("#mobile").focus();
            return false;
        } else {
            var flag = true;
            $.ajax({
                type : "POST",
                url : _path + "/user/withoutAuth/validateUserEmail",
                data : {
                    "email" : email
                },
                dataType : "json",
                async : false,
                beforeSend:function(){
                	$(".send-auth-code").attr("disabled","true");
    			},
                success : function(resultdata) {
                    if (resultdata) {
                    	$("#emailError").html("不存在该邮箱的用户");
                 		//$("#mobile").focus();
                        flag = false;
                    }else{
                    	$(".send-auth-code").removeAttr("disabled");
                    } 
                },
                error : function(errorMsg) {
                	layer.alert('服务器未响应,请稍后再试', {icon : 5,shift : 6,time : 3000});
             		//$("#mobile").focus();
                    flag = false;
                }
            });
            if(!flag)
            {
                return flag;   
            } else {
            	$("#emailError").html("");
            	return true; 
            }
        }
    }
}
function checkEmailCaptcha(isClear) {
	var captcha = $("#captcha").val();
	var email = $("#email").val();
	if (!checkEmail()) {
		return false;
	}
	if (captcha == null || captcha == "") {
		$("#captchaError").html("不能为空");
		$("#submitCheckEmail").attr("disabled","true");
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
        beforeSend:function(){
        	$("#submitCheckEmail").attr("disabled","true");
		},
        success : function(resultdata) {
        	if (resultdata == 0) {
        		$("#captchaError").html("验证码错误");
         		flag = false;
            } else {
            	$("#submitCheckEmail").removeAttr("disabled");
            	flag = true;
            }
        },
        error : function(errorMsg) {
        	flag = false;
        }
    });
	if (flag) {
		$("#captchaError").html("");
	} 
	return flag;
}
function submitCheckEmail() {
	if (checkEmailCaptcha(0)) {
		$("#findPasswordForm").submit();
	}
}

function checkPassword() {
	var password = $("#password").val();
    if (password == "") {
    	$("#passwordError").html("请输入密码");
    	$("#passwordError").parent('.form-group').addClass("has-error");
 		//$("#password").focus();
        return false;
    } else if (password.length < 6 || password.length > 18) {
    	$("#passwordError").html("密码长度在6-18个字符之间");
    	$("#passwordError").parent('.form-group').addClass("has-error");
 		//$("#password").focus();
        return false;
    } else {
    	$("#passwordError").html("");
    	$("#passwordError").parent('.form-group').removeClass("has-error");
    	return true; 
    }
}

function checkRePassword() {
	var password = $("#password").val();
	var rePassword = $("#rePassword").val()
    if (rePassword == "") {
    	$("#rePasswordError").html("请输入确认密码");
    	$("#rePasswordError").parent('.form-group').addClass("has-error");
 		//$("#rePassword").focus();
        return false;
    } else if(rePassword != password) {
    	$("#rePasswordError").html("两次输入密码不一致,请重新输入");
    	$("#rePasswordError").parent('.form-group').addClass("has-error");
 		//$("#rePassword").focus();
        return false;
    } else {
    	$("#rePasswordError").html("");
    	$("#rePasswordError").parent('.form-group').removeClass("has-error");
    	return true; 
    }
}

function setPassword() {
	var mobile = $("#mobile").val();
	if (!checkCaptcha(1)) {
		$("#captcha").focus();
		return;
	} else if (checkCaptcha(1) == "mobileError") {
		$("#mobile").focus();
		return;
	}
	
	var password = $("#password").val();
	if (!checkPassword()) {
		$("#password").focus();
		return;
	}
	if (!checkRePassword()) {
		$("#rePassword").focus();
		return;
	}
	
	$.ajax({
        type : "POST",
        url :  _path + "/fp/password/",
        data : {
        	password : password,
        	mobile : mobile
        },
        dataType : "json",
        beforeSend:function(){
        	$("#pawset").attr("disabled","true");
        	$("#pawset").html("提交中...");
		},
		complete: function () {	
			$("#pawset").removeAttr("disabled");
			$("#pawset").html("确&nbsp;定");
	    },
        success : function(result) {
             if (result.state) {
            	 window.location.href= _path + "/fp/success";
            	 //layer.alert('新密码设置成功', {icon : 6,shift : 6,time : 3000,end:function(){login();}});	//lwh:设置成功后跳转到登录页面
             } else {
            	 $("#passwordError").html(result.message);
            	 $("#passwordError").parent('.form-group').addClass("has-error");
             }
        },
        error : function(errorMsg) {
        	layer.alert('新密码设置失败', {icon : 5,shift : 6,time : 3000});
        }
    });
}

function login() {
	window.location.href= _path + "/fp/success";
}

function checkImgCaptcha() {
	var imgCaptcha = $("#imgCaptcha").val();
	$.ajax({
        type : "POST",
        url : _path + "/captcha/check",
        data : {
            "imgCaptcha" : imgCaptcha
        },
        dataType : "json",
        success : function(resultdata) {
            if (resultdata == 1) {
            	$(".send-auth-code").click();
            } 
        },
        error : function(errorMsg) {
        }
    });
}