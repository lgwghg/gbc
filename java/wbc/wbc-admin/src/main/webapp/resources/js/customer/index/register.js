$(document).ready(function() {
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
        	mobileRegister();
        }
    };
    
});
function checkNickName() {
	// 验证昵称
	var nickName = $("#nickName").val();
	if (nickName == "") {
		$("#nickNameError").parent("span").removeClass("yes");
    	$("#nickNameError").parent("span").addClass("error");
		$("#nickNameError").html("昵称不能为空");
		$("#nickNameError").parent("span").css("display","inline-block");
		//$("#nickName").focus();
		return false;
	} else if (nickName.length > 8) {
		$("#nickNameError").parent("span").removeClass("yes");
    	$("#nickNameError").parent("span").addClass("error");
		$("#nickNameError").html("昵称不能超过8个字符");
		$("#nickNameError").parent("span").css("display","inline-block");
		//$("#nickName").focus();
		return false;
	} else {
		var flag = true;
        $.ajax({
            type : "POST",
            url : sys.rootPath + "/user/withoutAuth/validateUserNick",
            data : {
                "nickName" : nickName
            },
            dataType : "json",
            async : false,
            success : function(resultdata) {
                if (!resultdata) {
                	$("#nickNameError").parent("span").removeClass("yes");
                	$("#nickNameError").parent("span").addClass("error");
                	$("#nickNameError").html("该昵称已注册,请使用其他昵称");
            		$("#nickNameError").parent("span").css("display","inline-block");
            		//$("#nickName").focus();
                    flag = false;
                } 
            },
            error : function(errorMsg) {
            	$("#nickNameError").parent("span").removeClass("yes");
            	$("#nickNameError").parent("span").addClass("error");
                $("#nickNameError").html("服务器未响应,请稍后再试");
        		$("#nickNameError").parent("span").css("display","inline-block");
        		//$("#nickName").focus();
                flag = false;
            }
        });
        if(!flag)
        {
            return flag;   
        } else {
        	$("#nickNameError").parent("span").removeClass("error");
        	$("#nickNameError").parent("span").addClass("yes");
        	$("#nickNameError").parent("span").css("display","inline-block");
        	return true; 
        }
	}
}
// 验证手机号
function checkMobile() {
	var mobile = $("#mobile").val();
    if (mobile == "") {
    	$("#mobileError").parent("span").removeClass("yes");
    	$("#mobileError").parent("span").addClass("error");
    	$("#mobileError").html("手机号不能为空");
 		$("#mobileError").parent("span").css("display","inline-block");
 		//$("#mobile").focus();
        return false;
    } else {
        if(!/^1[3|4|5|7|8]\d{9}$/.test(mobile)) {
        	$("#mobileError").parent("span").removeClass("yes");
        	$("#mobileError").parent("span").addClass("error");
        	$("#mobileError").html("请输入正确的手机号");
     		$("#mobileError").parent("span").css("display","inline-block");
     		//$("#mobile").focus();
            return false;
        } else {
            var flag = true;
            $.ajax({
                type : "POST",
                url : sys.rootPath + "/user/withoutAuth/validateUserMobile",
                data : {
                    "mobile" : mobile
                },
                dataType : "json",
                async : false,
                success : function(resultdata) {
                    if (!resultdata) {
                    	$("#mobileError").parent("span").removeClass("yes");
                    	$("#mobileError").parent("span").addClass("error");
                        $("#mobileError").html("该手机号已注册,请使用其他手机号");
                 		$("#mobileError").parent("span").css("display","inline-block");
                 		//$("#mobile").focus();
                        flag = false;
                    } 
                },
                error : function(errorMsg) {
                	$("#mobileError").parent("span").removeClass("yes");
                	$("#mobileError").parent("span").addClass("error");
                    $("#mobileError").html("服务器未响应,请稍后再试");
             		$("#mobileError").parent("span").css("display","inline-block");
             		//$("#mobile").focus();
                    flag = false;
                }
            });
            if(!flag)
            {
                return flag;   
            } else {
            	$("#mobileError").parent("span").removeClass("error");
            	$("#mobileError").parent("span").addClass("yes");
            	$("#mobileError").parent("span").css("display","inline-block");
            	return true; 
            }
        }
    }
}

function checkPassword() {
	var password = $("#password").val();
    if (password == "") {
    	$("#passwordError").parent("span").removeClass("yes");
    	$("#passwordError").parent("span").addClass("error");
    	$("#passwordError").html("请输入密码");
 		$("#passwordError").parent("span").css("display","inline-block");
 		//$("#password").focus();
        return false;
    } else if (password.length < 6 || password > 18) {
    	$("#passwordError").parent("span").removeClass("yes");
    	$("#passwordError").parent("span").addClass("error");
    	$("#passwordError").html("密码长度在6-18个字符之间");
 		$("#passwordError").parent("span").css("display","inline-block");
 		//$("#password").focus();
        return false;
    } else {
    	$("#passwordError").parent("span").removeClass("error");
    	$("#passwordError").parent("span").addClass("yes");
    	$("#passwordError").parent("span").css("display","inline-block");
    	return true; 
    }
}

function checkRePassword() {
	var password = $("#password").val();
	var rePassword = $("#rePassword").val()
    if (rePassword == "") {
    	$("#rePasswordError").parent("span").removeClass("yes");
    	$("#rePasswordError").parent("span").addClass("error");
    	$("#rePasswordError").html("请输入确认密码");
 		$("#rePasswordError").parent("span").css("display","inline-block");
 		//$("#rePassword").focus();
        return false;
    } else if(rePassword != password) {
    	$("#rePasswordError").parent("span").removeClass("yes");
    	$("#rePasswordError").parent("span").addClass("error");
        $("#rePasswordError").html("两次输入密码不一致,请重新输入");
 		$("#rePasswordError").parent("span").css("display","inline-block");
 		//$("#rePassword").focus();
        return false;
    } else {
    	$("#rePasswordError").parent("span").removeClass("error");
    	$("#rePasswordError").parent("span").addClass("yes");
    	$("#rePasswordError").parent("span").css("display","inline-block");
    	return true; 
    }
}
var submit = true;
//注册
function mobileRegister() {
	var nickName = $("#nickName").val();
	var mobile = $("#mobile").val();
	var password = $("#password").val();
	var rePassword = $("#rePassword").val();
	var kaptcha = $("#kaptcha").val();
	if (submit) {
		submit = false;
		// 验证昵称
		if (!checkNickName()) {
			submit = true;
			return false;
		}
		// 验证手机号
		if (!checkMobile()) {
			submit = true;
			return false;
		}
		// 验证密码
		if (!checkPassword()) {
			submit = true;
			return false;
		}
		if (!checkRePassword()) {
			submit = true;
			return false;
		}
		if (!checkCaptcha()) {
			submit = true;
			return false;
		}
		$.ajax({
			type : "POST",
			url : sys.rootPath + "/register",
			data : {
				"mobile" : mobile,
				"nickName" : nickName,
				"password" : password
			},
			dataType : "json",
			async : false,
			success : function(resultdata) {
				if (resultdata=="success") {
					$("#my-modal").modal("toggle");                    
			        $(".modal-backdrop").remove();//删除除阴影
				} else {
					layer.alert('注册失败', {icon : 5,shift : 6,time : 0});
				}
				submit = true;
			},
			error : function(errorMsg) {
				submit = true;
			}
		});
		
	}
}
function login() {
	window.location.href= sys.rootPath + "login.html";
}

/**
 * 点击获取验证码
 */
/*$(".send-auth-code").click(function(){
	var mobile = $("#mobile").val();
	if (checkMobile()) {
		// 倒计时
		 var count = 60;
	    var countdown = setInterval(CountDown, 1000);
	    function CountDown() {
	        $(".send-auth-code").attr("disabled", true);
	        $(".send-auth-code").html("重新发送(" + count + ")秒");
	        $(".send-auth-code").css("background-color","#818181");
	        if (count == 0) {
	            $(".send-auth-code").html("发送验证码").removeAttr("disabled");
	            $(".send-auth-code").css("background-color","#2582e6");
	            clearInterval(countdown);
	        }
	        count--;
	    }
		    
		// 发验证码的请求
    	$.ajax({
            type : "POST",
            url : sys.rootPath + "/captcha.html",
            data : {
                "mobile" : mobile
            },
            dataType : "json",
            async : false,
            success : function(resultdata) {
                 
            },
            error : function(errorMsg) {
                
            }
        });
	}
   
});
*//**
 * 获取短信验证码
 *//*
function getCaptcha() {
	var mobile = $("#mobile").val();
	if (checkMobile()) {
		// 发验证码的请求
    	$.ajax({
            type : "POST",
            url : sys.rootPath + "/captcha.html",
            data : {
                "mobile" : mobile
            },
            dataType : "json",
            async : false,
            success : function(resultdata) {
                 
            },
            error : function(errorMsg) {
                
            }
        });
	}
    
}*/

function checkCaptcha() {
	var captcha = $("#captcha").val();
	var mobile = $("#mobile").val();
	if (captcha == null || captcha == "") {
		$("#kaptchaError").html("验证码错误");
  		$("#kaptchaError").css("display","inline-block");
		return false;
	}
	if (!checkMobile()) {
		return false;
	}
	var flag = true;
	$.ajax({
        type : "POST",
        url : sys.rootPath + "/withoutAuth/checkCaptcha",
        data : {
            "captcha" : captcha,
            "mobile" : mobile,
            "isClear" : 1
        },
        async : false,
        success : function(resultdata) {
        	if (resultdata == 0) {
                $("#kaptchaError").html("验证码错误");
         		$("#kaptchaError").css("display","inline-block");
         		flag = false;
            } else {
            	flag = true;
            }
        },
        error : function(errorMsg) {
        	flag = false;
        }
    });
	return flag;
}