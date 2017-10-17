$(document).ready(function() {
    //注册回车键事件
//    document.onkeypress = function(e){
//    var ev = document.all ? window.event : e;
//        if(ev.keyCode==13) {
//        	mobileRegister();
//        }
//    };
	$('#registerForm').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
			mobileRegister();
	    }
	});
    $("#rnickName").blur(function() {
    	checkNickName();
    });
    $("#rmobile").blur(function() {
    	if ($("#mobileError").text() !='') {
    		$("#mobileError").parent(".form-group").removeClass("hide").addClass("has-error")
    		$("#mobileError").show();
    	}
    	//checkMobile();
    });
    $("#rpassword").blur(function() {
    	checkPassword();
    });
    $("#rePassword").blur(function() {
    	checkRePassword();
    });
    $("#captcha").blur(function() {
    	checkCaptcha(0);
    });
    
    $("#mobileRegisterButton").click(function() {
    	mobileRegister();
    });
    
    /**
     * 点击获取动态码
     */
    $(".send-auth-code").click(function(){
    	var mobile = $("#rmobile").val();
    	if (checkMobile()) {
    		var imgCaptcha = $("#imgCaptcha").val();
    		if (imgCaptcha == null || imgCaptcha == '') {
    			$('#captchaImage').hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
    			$("#imgCaptchaError").html("请先填写验证码");
    			$("#imgCaptchaError").parent(".form-group").removeClass("hide").addClass("has-error");
    			$("#imgCaptcha").focus();
    			return;
    		} else {
    			$("#imgCaptchaError").parent(".form-group").addClass("hide");
    		}
    		// 倒计时
    		var count = 60;
    	    var countdown = setInterval(CountDown, 1000);
    	    function CountDown() {
    	        $(".send-auth-code").attr("disabled", true);
    	        $(".send-auth-code").html("(" + count + ")秒后重发");
    	        //$(".send-auth-code").css("background-color","#818181");
    	        if (count == 0) {
    	            $(".send-auth-code").html("获取动态码").removeAttr("disabled");
    	            //$(".send-auth-code").css("background-color","#2582e6");
    	            clearInterval(countdown);
    	        }
    	        count--;
    	    }
    		    
    		// 发验证码的请求
    	    genMobileCaptcha(mobile,0, imgCaptcha);
    	 // 清掉验证码
            $("#imgCaptcha").val('');
            $("#captcha").focus();
    	}
       
    });
    
    $('#captchaImage').click(function() {//生成验证码
        $(this).hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
    });
    
    
});

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
//中文：2-6个；英文：4-12；中英结合：5-13
function checkNickLength(str) {  
    var realLength = 0;  
    var x = 0;//一个字节的字符数
    var y = 0;// 汉子的字符数
    for (var i = 0; i < str.length; i++) {  
        charCode = str.charCodeAt(i);  
        if (charCode >= 0 && charCode <= 128) {
        	realLength += 1;
        	x += 1;
        } else {
        	realLength += 3;
        	y += 1;
        } 
    }
    if (x > 0 && y == 0) {
    	if (x < 4 || x > 12) {
    		return false;
    	}
    } else if (x == 0 && y > 0) {
    	if (y < 2 || y > 6) {
    		return false;
    	}
    } else if (x > 0 && y > 0) {
    	if (x + y < 2 || x + y > 13) {
    		return false;
    	}
    }
    return true;  
}

function checkNickName() {
	// 验证昵称
	var nickName = $("#rnickName").val().replace(/\ +/g,"");
	$("#rnickName").val(nickName);
	if (nickName == "") {
		$("#nickNameError").html("昵称不能为空");
		$("#nickNameError").parent(".form-group").addClass("has-error");
		$("#nickNameError").show();
		//$("#nickName").focus();
		return false;
	} else if (!checkNickLength(nickName)) {
		$("#nickNameError").html("昵称2-6个字");
		$("#nickNameError").parent(".form-group").addClass("has-error");
		$("#nickNameError").show();
		//$("#nickName").focus();
		return false;
	} else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(nickName)) {
		$("#nickNameError").html("昵称只能字母、数字或汉字组成");
		$("#nickNameError").parent(".form-group").addClass("has-error");
		$("#nickNameError").show();
		//$("#nickName").focus();
		return false;
	} else {
		var flag = true;
        $.ajax({
            type : "POST",
            url : _path + "/user/withoutAuth/validateUserNick",
            data : {
                "nickName" : nickName
            },
            dataType : "json",
            success : function(resultdata) {
                if (!resultdata) {
                	$("#nickNameError").html("该昵称已注册");
                	$("#nickNameError").parent(".form-group").addClass("has-error");
                	$("#nickNameError").show();
            		//$("#nickName").focus();
                    flag = false;
                } 
            },
            error : function(errorMsg) {
                $("#nickNameError").html("服务器未响应,请稍后再试");
                $("#nickNameError").parent(".form-group").addClass("has-error");
                $("#nickNameError").show();
        		//$("#nickName").focus();
                flag = false;
            }
        });
        if(!flag)
        {
            return flag;   
        } else {
        	$("#nickNameError").html("");
        	$("#nickNameError").parent(".form-group").removeClass("has-error");
        	$("#nickNameError").hide();
        	return true; 
        }
	}
}
// 验证手机号
function checkMobile() {
	var mobile = $("#rmobile").val();
    if (mobile == "") {
    	$("#mobileError").html("手机号不能为空").css("color:red");
    	$("#mobileError").parent(".form-group").addClass("has-error");
    	$("#mobileError").show();
 		//$("#mobile").focus();
        return false;
    } else {
        if(!/^1\d{10}$/.test(mobile)) {
        	$("#mobileError").html("请输入正确的手机号");
        	$("#mobileError").parent(".form-group").addClass("has-error");
        	$("#mobileError").show();
     		//$("#mobile").focus();
            return false;
        } else {
            var flag = true;
            $.ajax({
                type : "POST",
                url : _path + "/user/withoutAuth/validateUserMobile",
                data : {
                    "mobile" : mobile
                },
                dataType : "json",
                beforeSend:function(){
                	$(".send-auth-code").attr("disabled","true");
    			},
                success : function(resultdata) {
                    if (!resultdata) {
                        $("#mobileError").html("该手机号已注册");
                        $("#mobileError").parent(".form-group").addClass("has-error");
                        $("#mobileError").show();
                 		//$("#mobile").focus();
                        flag = false;
                    }else{
                    	if ($(".send-auth-code").text().trim() == '获取动态码') {
                    		$(".send-auth-code").removeAttr("disabled");
                    	}
                    } 
                },
                error : function(errorMsg) {
                    $("#mobileError").html("服务器未响应,请稍后再试");
                    $("#mobileError").parent(".form-group").addClass("has-error");
                    $("#mobileError").show();
             		//$("#mobile").focus();
                    flag = false;
                }
            });
            if(!flag)
            {
                return flag;   
            } else {
            	$("#mobileError").html("");
            	$("#mobileError").parent(".form-group").removeClass("has-error");
            	$("#mobileError").hide();
            	return true; 
            }
        }
    }
}

function checkPassword() {
	var password = $("#rpassword").val();
    if (password == "") {
    	$("#rpasswordError").html("请输入密码");
    	$("#rpasswordError").parent(".form-group").addClass("has-error");
    	$("#rpasswordError").show();
 		//$("#password").focus();
        return false;
    } else if (password.length < 6 || password.length > 18) {
    	$("#rpasswordError").parent(".form-group").addClass("has-error");
    	$("#rpasswordError").html("密码长度6-18个字符");
    	$("#rpasswordError").show();
 		//$("#password").focus();
        return false;
    } else {
    	$("#rpasswordError").html("");
    	$("#rpasswordError").parent(".form-group").removeClass("has-error");
    	$("#rpasswordError").hide();
    	return true; 
    }
}

function checkRePassword() {
	var password = $("#rpassword").val();
	var rePassword = $("#rePassword").val()
    if (rePassword == "") {
    	$("#rePasswordError").html("请输入确认密码");
    	$("#rePasswordError").parent(".form-group").addClass("has-error");
    	$("#rePasswordError").show();
 		//$("#rePassword").focus();
        return false;
    } else if(rePassword != password) {
        $("#rePasswordError").html("两次输入密码不一致,请重输");
        $("#rePasswordError").parent(".form-group").addClass("has-error");
        $("#rePasswordError").show();
 		//$("#rePassword").focus();
        return false;
    } else {
    	$("#rePasswordError").html("");
    	$("#rePasswordError").parent(".form-group").removeClass("has-error");
    	$("#rePasswordError").hide();
    	return true; 
    }
}
var submit = true;
//注册
function mobileRegister() {
	var nickName = $("#rnickName").val();
	var mobile = $("#rmobile").val();
	var password = $("#rpassword").val();
	var rePassword = $("#rePassword").val();
	var captcha = $("#captcha").val();
	var token = $("#token").val();
	var fromuid = $("#fromuid").val();
	var thirdKey = $("#thirdKey").val();
	var thirdNick = $("#thirdNick").val();
	var thirdType = $("#thirdType").val();
	
	if (submit) {
		submit = false;
		// 验证昵称
		if (!checkNickName()) {
			submit = true;
			$("#rnickName").focus();
			return false;
		}
		// 验证密码
		if (!checkPassword()) {
			submit = true;
			$("#rpassword").focus();
			return false;
		}
		if (!checkRePassword()) {
			submit = true;
			$("#rePassword").focus();
			return false;
		}
		// 验证手机号
		if (!checkMobile()) {
			submit = true;
			$("#rmobile").focus();
			return false;
		}
		if (!checkCaptcha(1)) {
			submit = true;
			$("#captcha").focus();
			return false;
		}
		$.ajax({
			type : "POST",
			url : _path + "/register",
			data : {
				"token" : token,
				"mobile" : mobile,
				"nickName" : nickName,
				"password" : password,
				"fromuid" : fromuid,
				"thirdNick" : thirdNick,
				"thirdKey" : thirdKey,
				"thirdType" : thirdType
			},
			dataType : "json",
			beforeSend:function(){
				$("#mobileRegisterButton").html("正在注册");
				$("#mobileRegisterButton").attr("disabled", true);
			},
			complete: function () {	
				$("#mobileRegisterButton").html("注册");
				$("#mobileRegisterButton").removeAttr("disabled");
		    },
			success : function(resultdata) {
				if (resultdata=="success") {
					// 分享注册 成功页不一样。不需要登录
					var shareRegister = $("#shareRegister").val();
					if (typeof(shareRegister) != undefined && shareRegister != null && shareRegister != '' && shareRegister == '1') {
						window.location.href= _path + "/share/f/regSuccess";
					} else {
						layer.msg('注册成功', {icon : 6,shift : 6,time : 2000,end:function(){registerlogin(mobile, password);}});	//lwh:注册成功后跳转到登录页面
					}
				} else {
					layer.alert(resultdata, {icon : 5,shift : 6,time : 2000});
				}
				submit = true;
			},
			error : function(jqXHR, textStatus, errorThrown) 
			{
				/*弹出jqXHR对象的信息*/
	            if(jqXHR.status = "611")
	            {
	            	layer.alert("重复提交表单!", {icon : 5,shift : 6,time : 2000});
	            }
	            else
	            {
	            	layer.alert(errorMsg, {icon : 5,shift : 6,time : 2000});
	            }
				submit = true;
			}
		});
		
	}
}
function registerlogin(mobile, password) {
	$.ajax({
		type : "POST",
		url : _path+"/login",
		data : {
			"mobile" : mobile,
			"password" : password,
			"rememberMe" : false
		},
		success : function(data) {
			var _data = eval('(' + data + ')');
			if (_data.code == 1) {
				window.location.href = _path + "/match";
			} else {
				window.location.href = _path + "/login";
			}
		}
	});
}


function checkCaptcha(isClear) {
	var captcha = $("#captcha").val();
	var mobile = $("#rmobile").val();
	if (captcha == null || captcha == "") {
		$("#captchaError").html("动态码不能为空");
		$("#captchaError").parent(".form-group").addClass("has-error");
		$("#captchaError").show();
		return false;
	}
	if (!checkMobile()) {
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
                $("#captchaError").html("动态码错误");
                $("#captchaError").parent(".form-group").addClass("has-error");
                $("#captchaError").show();
         		flag = false;
            } else {
            	$("#captchaError").html("");
                $("#captchaError").parent(".form-group").removeClass("has-error");
                $("#captchaError").hide();
            	flag = true;
            }
        },
        error : function(errorMsg) {
        	flag = false;
        }
    });
	return flag;
}

function isRead() {
	var isRead = document.getElementById("isRead").checked;
	if (isRead) {
		$("#mobileRegisterButton").removeAttr("disabled");
	} else {
		$("#mobileRegisterButton").attr("disabled","true");
	}
}