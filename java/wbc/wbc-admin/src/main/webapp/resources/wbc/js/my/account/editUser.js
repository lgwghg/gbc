$(function(){
			 $(".c-informgroup").click(function(){
	             $(this).children('.c-xiala').toggle().closest(".c-informgroup").siblings(".c-informgroup").children(".c-xiala").hide();
	                })
		})
$(function(){
	var t;	
	$(".img").mouseover(function(){
		$(".img_editor").show();
		clearTimeout(t);
	})
	$(".img_editor").mouseover(function(){
		$(this).show();
		clearTimeout(t);
	})
	$(".img").mouseout(function(){
	t = setTimeout(function(){
		$(".img_editor").hide();
	},300)	
	})		
})

$(function(){
	if ($("#emailInfo").val() == '' || $("#emailInfo").val() == null) {
		$("#emailInfo").parent().addClass("is-empty");
		$("#a_email").parent().addClass("is-empty");
		$("#countdown_email").parent().addClass("is-empty");
	}
	if ($("#alipay").val() == '' || $("#alipay").val() == null) {
		$("#alipay").parent().addClass("is-empty");
		$("#alipayAccount").parent().addClass("is-empty");
		$("#countdown_alipay").parent().addClass("is-empty");
	}
	if ($("#sign").val() == '' || $("#sign").val() == null) {
		$("#sign").parent().addClass("is-empty");
	}
	// 用户钱包金额
	$("#userInfoGold").html($("#headerMenuGold").text());
	$("#userInfoSysGold").html($("#headerMenuSysGold").text());
	// 用户参与竞猜的金额
	$("#userInfoJcGold").html($("#jcGold").text());
	// 用户提现的金额
	$("#userInfoWdGold").html($("#wdFirstGold").val());
	// 充值弹框填充充值金额数据
	$("#userInfoRecharge").click(function() {
		rechargeAmountList();
	});
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
    
    // 定义一个新的复制对象
    var clipboardAccountBtn = document.getElementById('d_clip_button_account');
    var clipboardAccount = new Clipboard(clipboardAccountBtn);
    clipboardAccount.on('success', function(e) {
	    layer.msg("复制成功", {icon: 6,shift: 6,time: 2000});
	});

    clipboardAccount.on('error', function(e) {
		layer.msg("复制失败，请手动复制", {icon: 5,shift: 6,time: 2000});
	});
	
	/**
	 * 手机编辑框打开
	 */
	$(".iconnum").click(function(){
		$(this).closest(".c_num").hide();
		$(this).closest(".c_num").siblings(".numwrap1,.c_email,.password,.c_pay,.c_pay_password").show();
		$(this).closest(".c_num").siblings(".emailwrap1,.passwordwrap,.apaywrap1,.emailwrap2,.numwrap2,.paypasswordwrap").hide();
	});
	/**
	 * 手机编辑框收起
	 */
	$(".c_tip2").click(function(){
		$(this).closest(".numwrap1").hide();
		$(this).closest(".numwrap1").siblings(".c_num").show();
	});
	
	// 确定原手机验证码
	$(".numbtn").click(function(){
		if (checkOriginalMobileCaptcha(1)) {
			$(".numwrap1").hide();
			$(".numwrap2").show();
		}
	});
	/**
	 * 新手机输入框失去焦点，验证手机号输入正确性
	 */
	$("#newMobile").blur(function() {
		checkNewMobile();
	});
	/**
	 * 确定新手机的验证码正确性，并保存手机号
	 */
	var mobileConfirmFlag = true;
	$(".numbtn1").click(function(){
		var checked = checkMobileCaptcha(1);
		if (checked && mobileConfirmFlag) {
			var editValue = $("#newMobile").val();
			mobileConfirmFlag = false;
			$.ajax({
				type : "POST",
				url : _path + "/my/editMobileOrEmail",
				data : {
					"editType" : 0,//编辑手机号
					"editValue" : editValue
				},
				dataType : "json",
				async : false,
				success : function(resultdata) {
					if (resultdata == 1) {
						layer.msg('修改成功！', {icon : 1,shift : 6,time : 3000});
						$("#a_mobile").val(editValue);
						$("#mobileInfo").val(editValue);
						// 隐藏下拉
						$(".numwrap2").hide();
						$(".c_num").show();
					} else if(resultdata == 0) {
						$("#newMobile").html("手机号不能为空");
						$("#newMobile").parent('.form-group').addClass('has-error');
					} else if (resultdata == 2) {
						$("#newMobile").html("更新失败，请刷新页面重试");
						$("#newMobile").parent('.form-group').addClass('has-error');
					} else {
						$("#newMobile").html("该手机号用户已存在");
						$("#newMobile").parent('.form-group').addClass('has-error');
					}
					mobileConfirmFlag = true;
				},
				error : function(errorMsg) {
					layer.alert('服务器未响应,请稍后再试', {icon : 5,shift : 6,time : 3000});
					mobileConfirmFlag = true;
				}
			});
		}
	});
	
	/*
	 * email编辑框打开
	 */
	$(".iconemail").click(function(){
		$(this).closest(".c_email").hide();
		$(this).closest(".c_email").siblings(".emailwrap1,.password,.c_num,.c_pay,.c_pay_password").show();
		$(this).closest(".c_email").siblings(".passwordwrap,.numwrap1,.apaywrap1,.emailwrap2,.numwrap2,.paypasswordwrap").hide();
	});
	
	/**
	 * email编辑框收起
	 */
	$(".c_tip1").click(function(){
		$(this).closest(".emailwrap1").hide();
		$(this).closest(".emailwrap1").siblings(".c_email").show();
	});
	/**
	 * 确定原邮箱验证码
	 */
	$(".embtn").click(function(){
		if (checkOriginalEmailCaptcha(1)) {
			$(".emailwrap1").hide();
			$(".emailwrap2").show();
		}
		
	});
	
	/**
	 * 保存新邮箱
	 */
	var emailConfirmFlag = true;
	$(".embtn1").click(function(){
		var checked = checkEmailCaptcha(1);
		if (checked && emailConfirmFlag) {
			var editValue = $("#newEmail").val();
			emailConfirmFlag = false;
			$.ajax({
				type : "POST",
				url : _path + "/my/editMobileOrEmail",
				data : {
					"editType" : 1,//编辑邮箱
					"editValue" : editValue
				},
				dataType : "json",
				async : false,
				success : function(resultdata) {
					if (resultdata == 1) {
						layer.msg('修改成功！', {icon : 1,shift : 6,time : 3000});
						$("#emailInfo").val(editValue);
						$("#a_email").val(editValue);
						$(".emailwrap2").hide();
						$(".c_email").show();
					} else if(resultdata == 0) {
						$("#newEmailError").html("邮箱不能为空");
						$("#newEmailError").parent(".form-group").addClass('has-error');
					} else if (resultdata == 2) {
						$("#newEmailError").html("更新失败，请刷新页面重试");
						$("#newEmailError").parent(".form-group").addClass('has-error');
					} else {
						$("#newEmailError").html("该邮箱用户已存在");
						$("#newEmailError").parent(".form-group").addClass('has-error');
					}
					emailConfirmFlag = true;
				},
				error : function(errorMsg) {
					layer.alert('服务器未响应,请稍后再试', {icon : 5,shift : 6,time : 3000});
					emailConfirmFlag = true;
				}
			});
		}
	});
	
	
	// 密码编辑框打开
	$(".iconpwd").click(function(){
		$(this).closest(".password").hide();
		$(this).closest(".password").siblings(".passwordwrap,.c_email,.c_num,.c_pay,.c_pay_password").show();
		$(this).closest(".password").siblings(".emailwrap1,.numwrap1,.apaywrap1,.emailwrap2,.numwrap2,.paypasswordwrap").hide();
	});
	// 密码编辑框收起
	$(".pasd_jiansha").click(function(){
		$(this).closest(".passwordwrap").hide();
		$(this).closest(".passwordwrap").siblings(".password").show();
	});
	
	
	/**
	 * 支付宝编辑框打开
	 */
	$(".iconpay").click(function(){
		$(this).closest(".c_pay").hide();
		$(this).closest(".c_pay").siblings(".apaywrap1,.c_email,.password,.c_num,.c_pay_password").show();
		$(this).closest(".c_pay").siblings(".emailwrap1,.numwrap1,.passwordwrap,.emailwrap2,.numwrap2,.paypasswordwrap").hide();
	});
	/**
	 * 支付宝编辑框收起
	 */
	$(".c_tip3").click(function(){
		$(this).closest(".apaywrap1").hide();
		$(this).closest(".apaywrap1").siblings(".c_pay").show();
	});
	/**
	 * 支付宝编辑确定保存按钮
	 */
	/*$(".paybtn").click(function(){
		$(".apaywrap1").hide();
		$(".c_pay").show();
	});*/
	$('#captchaImage_mobile, #captchaImage_newMobile, #captchaImage_alipay').click(function() {//生成验证码
        $(this).hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
    });
	/**
	 * 发送原手机号验证码
	 */
	$("#countdown_mobile").click(function(){
		 var imgCaptcha_mobile = $("#imgCaptcha_mobile").val();
		 if (imgCaptcha_mobile == null || imgCaptcha_mobile == '') {
 			$("#imgCaptchaError_mobile").html("请先填写验证码");
 			$("#imgCaptchaError_mobile").parent(".form-group").removeClass("hide").addClass("has-error");
 			$("#imgCaptcha_mobile").focus();
 			$('#captchaImage_mobile').hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
 			return;
 		} else {
 			$("#imgCaptchaError_mobile").parent(".form-group").addClass("hide");
 		}
         var  timer = 60;
         $(this).attr("disabled","disabled")
         $("#countdown_mobile").html("("+timer+")"+"秒后重发")
         var DSQ = setInterval(function(){
               timer--; 
               if(timer>0){
                $("#countdown_mobile").html("("+timer+")"+"秒后重发")
               }else{
                clearInterval(DSQ)
                $("#countdown_mobile").html("获取验证码")
                $("#countdown_mobile").removeAttr("disabled")
               }
          },1000);
         var mobile = $("#a_mobile").val();
         var imgCaptcha_mobile = $("#imgCaptcha_mobile").val();
         // 发验证码的请求
         genMobileCaptcha(mobile,1,imgCaptcha_mobile);
         // 清掉验证码
         $("#imgCaptcha_mobile").val('');
         $("#mobileCaptcha").focus();
    });
	
	/**
	 * 发送新手机号验证码
	 */
	$("#countdown_newMobile").click(function(){
		var imgCaptcha_newMobile = $("#imgCaptcha_newMobile").val();
		 if (imgCaptcha_newMobile == null || imgCaptcha_newMobile == '') {
			$("#imgCaptchaError_newMobile").html("请先填写验证码");
			$("#imgCaptchaError_newMobile").parent(".form-group").removeClass("hide").addClass("has-error");
			$("#imgCaptcha_newMobile").focus();
			$('#captchaImage_newMobile').hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
			return;
		} else {
			$("#imgCaptchaError_newMobile").parent(".form-group").addClass("hide");
		}
         var  timer = 60;
         $(this).attr("disabled","disabled")
         $("#countdown_newMobile").html("("+timer+")"+"秒后重发")
         var DSQ = setInterval(function(){
               timer--; 
               if(timer>0){
                $("#countdown_newMobile").html("("+timer+")"+"秒后重发")
               }else{
                clearInterval(DSQ)
                $("#countdown_newMobile").html("获取动态码")
                $("#countdown_newMobile").removeAttr("disabled")
               }
          },1000);
         var mobile = $("#newMobile").val();
         var imgCaptcha_newMobile = $("#imgCaptcha_newMobile").val();
         // 发验证码的请求
         genMobileCaptcha(mobile,1,imgCaptcha_newMobile);
      // 清掉验证码
         $("#imgCaptcha_newMobile").val('');
         $("#newMobileCaptcha").focus();
    });
	
	/**
	 * 发送原邮箱验证码
	 */
	$("#countdown_email").click(function(){
         var  timer = 60;
         $(this).attr("disabled","disabled")
         $("#countdown_email").html("("+timer+")"+"秒后重发")
         var DSQ = setInterval(function(){
               timer--; 
               if(timer>0){
                $("#countdown_email").html("("+timer+")"+"秒后重发")
               }else{
                clearInterval(DSQ)
                $("#countdown_email").html("获取动态码")
                $("#countdown_email").removeAttr("disabled")
               }
          },1000);
         var email = $("#a_email").val();
         // 发验证码的请求
         genEmailCaptcha(email);
         $("#emailCaptcha").focus();
    });
	
	/**
	 * 发送原邮箱验证码
	 */
	$("#countdown_newEmail").click(function(){
         var  timer = 60;
         $(this).attr("disabled","disabled")
         $("#countdown_newEmail").html("("+timer+")"+"秒后重发")
         var DSQ = setInterval(function(){
               timer--; 
               if(timer>0){
                $("#countdown_newEmail").html("("+timer+")"+"秒后重发")
               }else{
                clearInterval(DSQ)
                $("#countdown_newEmail").html("获取动态码")
                $("#countdown_newEmail").removeAttr("disabled")
               }
          },1000);
         var email = $("#newEmail").val();
         // 发验证码的请求
         genEmailCaptcha(email);
         $("#newEmailCaptcha").focus();
    });
});


//验证手机号
function checkNewMobile() {
	var mobile = $("#newMobile").val();
    if (mobile == "") {
    	$("#newMobileError").html("请输入手机号");
    	$("#newMobileError").parent('.form-group').addClass('has-error');
    	$("#newMobileError").show();
        return false;
    }
    if(!/^1\d{10}$/.test(mobile)) {
    	$("#newMobileError").html("请输入正确的手机号");
    	$("#newMobileError").parent('.form-group').addClass('has-error');
    	$("#newMobileError").show();
        return false;
    } else {
    	if (mobile == $("#a_mobile").val()) {
    		$("#newMobileError").html("新手机不能与原手机一样");
        	$("#newMobileError").parent('.form-group').addClass('has-error');
        	$("#newMobileError").show();
    		return false;
    	}
        var flag = true;
        $.ajax({
            type : "POST",
            url : _path + "/user/withoutAuth/validateUserMobile",
            data : {
                "mobile" : mobile
            },
            dataType : "json",
            async : false,
            success : function(resultdata) {
                if (resultdata) {
                	flag = true;
                } else {
                	$("#newMobileError").html("该手机号的用户已存在");
                	$("#newMobileError").parent('.form-group').addClass('has-error');
                	$("#newMobileError").show();
                	flag = false;
                }
            },
            error : function(errorMsg) {
            	layer.alert("服务器未响应,请稍后再试", {icon : 5,shift : 6,time : 3000});
                flag = false;
            }
        });
        if(!flag)
        {	
        	$("#countdown_newMobile").attr("disabled","true");
            return flag;   
        } else {
        	$("#newMobileError").html("");
        	$("#newMobileError").parent('.form-group').removeClass('has-error');
        	$("#countdown_newMobile").removeAttr("disabled");
        	return true; 
        }
    }
}
//验证原始手机的验证码
function checkOriginalMobileCaptcha(isClear) {
	var captcha = $("#mobileCaptcha").val();
	var mobile = $("#a_mobile").val();
	if (captcha == null || captcha == "") {
		$("#mobileCaptchaError").html("不能为空");
		$("#mobileCaptchaError").parent('.form-group').addClass('has-error');
		$("#mobileCaptchaError").show();
		//$(".main-title-one .auth-code-right").attr("disabled","true");
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
        		$("#mobileCaptchaError").html("动态码错误");
        		$("#mobileCaptchaError").parent('.form-group').addClass('has-error');
        		$("#mobileCaptchaError").show();
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
		$("#mobileCaptchaError").html("");
		$("#mobileCaptchaError").parent('.form-group').removeClass('has-error');
		$("#countdown_mobile").removeAttr("disabled");
	} else {
		$("#countdown_mobile").attr("disabled","true");
	}
	return flag;
}

//验证新设手机的验证码
function checkMobileCaptcha(isClear) {
	var captcha = $("#newMobileCaptcha").val();
	var mobile = $("#newMobile").val();
	if (!checkNewMobile()) {
		return false;
	}
	if (captcha == null || captcha == "") {
		$("#newMobileCaptchaError").html("不能为空");
		$("#newMobileCaptchaError").parent('.form-group').addClass('has-error');
		$("#newMobileCaptchaError").show();
		$("#countdown_newMobile").attr("disabled","true");
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
        		$("#newMobileCaptchaError").html("动态码错误");
        		$("#newMobileCaptchaError").parent('.form-group').addClass('has-error');
        		$("#newMobileCaptchaError").show();
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
		$("#newMobileCaptchaError").html("");
		$("#newMobileCaptchaError").parent('.form-group').removeClass('has-error');
		$("#countdown_newMobile").removeAttr("disabled");
	} else {
		$("#countdown_newMobile").attr("disabled","true");
	}
	return flag;
}

/**
 * 非第一次编辑邮箱
 */ 
// 原邮箱验证码验证
function checkOriginalEmailCaptcha(isClear) {
	return checkNewEmailCaptcha(isClear);
}
//验证新邮箱
function checkEmail() {
	var email = $("#newEmail").val();
    if (email == "") {
    	$("#newEmailError").html("邮箱不能为空");
    	$("#newEmailError").parent('.form-group').addClass('has-error');
    	$("#newEmailError").show();
        return false;
    } else {
    	var emailReg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        if(!emailReg.test(email)) {
        	$("#newEmailError").html("请输入正确的邮箱地址");
        	$("#newEmailError").parent('.form-group').addClass('has-error');
        	$("#newEmailError").show();
            return false;
        } else {
        	if (email == $("#a_email").val()) {
        		$("#newEmailError").html("新邮箱不能与原邮箱一样");
            	$("#newEmailError").parent('.form-group').addClass('has-error');
            	$("#newEmailError").show();
        		return false;
        	}
            var flag = true;
            $.ajax({
                type : "POST",
                url : _path + "/user/withoutAuth/validateUserEmail",
                data : {
                    "email" : email
                },
                dataType : "json",
                async : false,
                success : function(resultdata) {
                    if (resultdata) {
                        flag = true;
                    } else {
                    	$("#newEmailError").html("该邮箱用户已存在");
                    	$("#newEmailError").parent('.form-group').addClass('has-error');
                    	$("#newEmailError").show();
                    	flag = false;
                    } 
                },
                error : function(errorMsg) {
                	layer.alert("服务器未响应,请稍后再试", {icon : 5,shift : 6,time : 3000});
                    flag = false;
                }
            });
            if(!flag)
            {
            	$("#countdown_newEmail").attr("disabled","true");
                return flag;   
            } else {
            	$("#newEmailError").html("");
            	$("#newEmailError").parent('.form-group').removeClass('has-error');
            	$("#countdown_newEmail").removeAttr("disabled");
            	return true; 
            }
        }
    }
}

function checkEmailCaptcha(isClear) {
	var captcha =  $("#newEmailCaptcha").val();
	var email = $("#newEmail").val();
	if (!checkEmail()) {
		return false;
	}
	if (captcha == null || captcha == "") {
		$("#newEmailCaptchaError").html("不能为空");
		$("#newEmailCaptchaError").parent('.form-group').addClass('has-error');
		$("#newEmailCaptchaError").show();
		//$("#countdown_newEmail").attr("disabled","true");
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
        		$("#newEmailCaptchaError").html("动态码错误");
        		$("#newEmailCaptchaError").parent('.form-group').addClass('has-error');
        		$("#newEmailCaptchaError").show();
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
		$("#newEmailCaptchaError").html("");
		$("#newEmailCaptchaError").parent('.form-group').removeClass('has-error');
		$("#countdown_newEmail").removeAttr("disabled");
	} else {
		$("#countdown_newEmail").attr("disabled","true");
	}
	return flag;
}








//////其他信息修改////////////

$(function() {
	$("#a_email").blur(function() {
		if (checkNewEmail()) {
			$("#countdown_email").removeAttr("disabled");
		} else {
			$("#countdown_email").attr("disabled","true");
		}
	});
	$("#emailCaptcha").keyup(function() {
		checkNewEmailCaptcha(0);
	});
	/**
	 * 新邮箱点击获取验证码
	 */
	$(".countdown_email").click(function(){
        var  timer = 60;
        $(this).attr("disabled","disabled")
        $("#countdown_email").html("("+timer+")"+"秒后重发")
        var DSQ = setInterval(function(){
              timer--; 
              if(timer>0){
               $("#countdown_email").html("("+timer+")"+"秒后重发")
              }else{
               clearInterval(DSQ)
               $("#countdown_email").html("获取动态码")
               $("#countdown_email").removeAttr("disabled")
              }
         },1000);
        var email = $("#a_email").val();
    	// 发验证码的请求
	    genEmailCaptcha(email);
   });
	
	// 验证密码
	$("#oldPassword").blur(function() {
		checkOldPassword();
	});
	$("#newPassword").blur(function() {
		// 验证新设密码是否合法
		var newPassword = $("#newPassword").val();
		if (newPassword == null || newPassword == "") {
			$("#newPasswordError").html("新密码不能为空");
			$("#newPasswordError").parent('.form-group').addClass('has-error');
			$("#newPasswordError").show();
		} else {
			if (newPassword.length <6 || newPassword.length > 18) {
				$("#newPasswordError").html("密码6-18个字符之间");
				$("#newPasswordError").parent('.form-group').addClass('has-error');
				$("#newPasswordError").show();
			} else {
				$("#newPasswordError").html("");
				$("#newPasswordError").parent('.form-group').removeClass('has-error');
				$("#newPasswordError").show();
				
			}
			
		}
	});
	$("#a_rePassword").blur(function() {
		// 验证新设密码两次输入是否一致
		var newPassword = $("#newPassword").val();
		var rePassword = $("#a_rePassword").val();
		if (rePassword == null || rePassword == "") {
			$("#a_rePasswordError").html("确认密码不能为空");
			$("#a_rePasswordError").parent('.form-group').addClass('has-error');
			$("#a_rePasswordError").show();
		} else {
			if (rePassword != newPassword) {
				$("#a_rePasswordError").html("两次输入密码不一致");
				$("#a_rePasswordError").parent('.form-group').addClass('has-error');
				$("#a_rePasswordError").show();
			} else {
				$("#a_rePasswordError").html("");
				$("#a_rePasswordError").parent('.form-group').removeClass('has-error');
			}
			
		}
	});
	// 提交编辑用户信息
	$("#editBtn").click(function() {
		var emailInfo = $("#emailInfo").val();
		var email = $("#a_email").val();
		var password = $("#newPassword").val();
		var sign = $("#sign").val();
		var oldSign = $("#oldSign").val();
		var oldNickName = $("#oldNickName").val();
		var nickName = $("#a_nickName").val().replace(/\ +/g,"");
		if ((email == null || email == "" || email == emailInfo) && (password == null || password == "") && (sign == oldSign) && (oldNickName == nickName)) {
			layer.alert('请修改后再保存', {icon : 0,shift : 6,time : 2000});
			return;
		}
		
		if (nickName == null || nickName == '') {
			$("#nickError").html("用户昵称不能为空");
			$("#nickError").parent('.form-group').addClass('has-error');
			$("#nickError").show();
			return;
		} else {
			if (oldNickName != nickName) {
				if (!checkNick()) {
					return;
				}
			}
		}
		var oldPassword = $("#oldPassword").val();
		var rePassword = $("#a_rePassword").val();
		if (password != null && password != "") {
			if (password.length <6 || password.length > 18) {
				$("#newPasswordError").html("密码6-18个字符之间");
				$("#newPasswordError").parent('.form-group').addClass('has-error');
				$("#newPasswordError").show();
				return;
			} else {
				$("#newPasswordError").html("");
				$("#newPasswordError").parent('.form-group').removeClass('has-error');
				$("#newPasswordError").show();
			}
			if (checkOldPassword()) {
				return;
			}
			if (rePassword == null || rePassword == "") {
				$("#a_rePasswordError").html("确认密码不能为空");
				$("#a_rePasswordError").parent('.form-group').addClass('has-error');
				$("#a_rePasswordError").show();
				return;
			} else {
				if (rePassword != password) {
					$("#a_rePasswordError").html("两次输入密码不一致");
					$("#a_rePasswordError").parent('.form-group').addClass('has-error');
					$("#a_rePasswordError").show();
					return;
				} else {
					$("#a_rePasswordError").html("");
					$("#a_rePasswordError").parent('.form-group').removeClass('has-error');
				}
				
			}
		}
		if (sign != null && sign != "") {
			if (sign.length >120) {
				$("#signError").html("签名只能120个字符");
				$("#signError").parent('.form-group').addClass('has-error');
				$("#signError").show();
				return;
			} else {
				$("#signError").html("");
				$("#signError").parent('.form-group').removeClass('has-error');
			}
		}
		if (email != null && email != "" && emailInfo != email) {
			if (!checkNewEmailCaptcha(1)) {
				return;
			}
		} else {
			email = ''; // 邮箱绑定之后不能通过此处编辑
		}
		var flag = true;// 防重复提交
		if (flag) {
			flag = false;
			$.ajax({
                type : "POST",
                url : _path + "/my/edit",
                data : {
                    "email" : email,
                    "password" : password,
                    "sign" : sign,
                    "nickName" : nickName
                },
                dataType : "json",
                async : false,
                success : function(resultdata) {
                    if (!resultdata) {
                    	layer.alert("编辑失败", {icon : 5,shift : 6,time : 2000});
                    	//$("#editBtn").parent().find("span").html("编辑失败！");
                    	//$("#editBtn").parent().find("span").removeClass("none");
                    } else {
                    	layer.alert('编辑成功', {icon : 6,shift : 6,time : 2000,end:function(){
                    		window.location.href = window.location.href;
                    	}});
                    	//$("#editBtn").parent().find("span").html("编辑成功！");
                    	//$("#editBtn").parent().find("span").removeClass("none");
                    }
                    flag = true;
                },
                error : function(errorMsg) {
                	layer.alert("服务器未响应,请稍后再试", {icon : 5,shift : 6,time : 3000});
                    flag = true;
                }
            });
		}
	});
});

function checkOldPassword() {
	// 验证输入的原密码是否正确
	var oldPassword = $("#oldPassword").val();
	$.ajax({
		type : "POST",
		url : _path + "/my/checkPwd",
		data : {
			"pwd" : oldPassword
		},
		dataType : "json",
		async : false,
		success : function(resultdata) {
			if (resultdata.state == 0) {
				$("#oldPasswordError").html("");
				$("#oldPasswordError").parent(".form-group").removeClass('has-error');
				return true;
			} else {
				$("#oldPasswordError").html(resultdata.msg);
				$("#oldPasswordError").parent(".form-group").addClass('has-error');
				$("#oldPasswordError").show();
				return false;
			}
		},
		error : function(errorMsg) {
			layer.alert("服务器未响应,请稍后再试", {icon : 5,shift : 6,time : 3000});
			return false;
		}
	});
}
// 检查新邮箱是否输入正确且未被使用,第一次绑定邮箱
function checkNewEmail() {
	var email = $("#a_email").val();
    if (email == "") {
    	$("#emailError").html("邮箱不能为空");
    	$("#emailError").parent(".form-group").addClass('has-error');
    	$("#emailError").show();
        return false;
    } else {
    	var emailReg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        if(!emailReg.test(email)) {
        	$("#emailError").html("请输入正确的邮箱地址");
        	$("#emailError").parent(".form-group").addClass('has-error');
        	$("#emailError").show();
            return false;
        } else {
        	if (email.length > 30) {
        		$("#emailError").html("邮箱长度不能大于30个字符");
            	$("#emailError").parent(".form-group").addClass('has-error');
            	$("#emailError").show();
                return false;
        	}
            var flag = true;
            $.ajax({
                type : "POST",
                url : _path + "/user/withoutAuth/validateUserEmail",
                data : {
                    "email" : email
                },
                dataType : "json",
                async : false,
                success : function(resultdata) {
                    if (resultdata) {
                        flag = true;
                        $("#emailError").html("");
                    	$("#emailError").parent(".form-group").removeClass('has-error');
                    } else {
                    	$("#emailError").html("该邮箱用户已存在");
                    	$("#emailError").parent(".form-group").addClass('has-error');
                    	$("#emailError").show();
                    	flag = false;
                    } 
                },
                error : function(errorMsg) {
                	layer.alert("服务器未响应,请稍后再试", {icon : 5,shift : 6,time : 3000});
                    flag = false;
                }
            });
            return flag;
        }
    }
}
// 检查新邮箱是否是可用邮箱,第一次绑定邮箱
function checkNewEmailCaptcha(isClear) {
	var captcha =  $("#emailCaptcha").val();
	var email = $("#a_email").val();
	/*if (!checkNewEmail()) {
		return false;
	}*/
	if (captcha == null || captcha == "") {
		$("#emailCaptchaError").html("动态码不能为空");
    	$("#emailCaptchaError").parent(".form-group").addClass('has-error');
    	$("#emailCaptchaError").show();
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
        		$("#emailCaptchaError").html("动态码错误");
            	$("#emailCaptchaError").parent(".form-group").addClass('has-error');
            	$("#emailCaptchaError").show();
         		flag = false;
            } else {
            	$("#emailCaptchaError").html("");
            	$("#emailCaptchaError").parent(".form-group").removeClass('has-error');
            	flag = true;
            }
        },
        error : function(errorMsg) {
        	flag = false;
        }
    });
	return flag;
}

//上传头像
function uploadImg(){
	var avatar_data = $("#avatar_data").val();
	var avatarInput = $("#avatarInput").val();
	if(trim(avatar_data)=="" || trim(avatarInput)==""){
		layer.alert("请选择要上传的图片", {icon : 5,shift : 6,time : 3000});
	}else{
		$("#uploadImgForm").ajaxSubmit({
	        type : "POST",
	        url : _path + "/avatar/upload",
	        data : $("#uploadImgForm").serialize(),
	        success : function(result) {
	        	var data = eval("("+result+")");
	        	if(parseInt(data.code) == 1)//上传成功
	            {
	        		layer.msg("上传成功!", {icon : 6,shift : 6,time : 2000 ,end:function(){window.location.reload();}});
	        		//$("#user_pic").attr("src",data.src);
	            }
	            else
	            {
	            	var info = "未知错误,请稍后再试!";
	            	
	            	if(data.msg != "")
	            	{
	            		info = data.msg;
	            	}
	            	layer.alert(info, {icon : 5,shift : 6,time : 3000});
	            }
	        }
	    });
	}
}

function user_checkImgCaptcha(imgCaptchaId, getCaptchaBtnId) {
	var imgCaptcha = $("#" + imgCaptchaId).val();
	$.ajax({
        type : "POST",
        url : _path + "/captcha/check",
        data : {
            "imgCaptcha" : imgCaptcha
        },
        dataType : "json",
        success : function(resultdata) {
            if (resultdata == 1) {
            	$("#" + getCaptchaBtnId).click();
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
// 验证用户昵称
function checkNick() {
	// 验证昵称
	var nickName = $("#a_nickName").val().replace(/\ +/g,"");
	if (nickName == "") {
		$("#nickError").html("昵称不能为空");
		$("#nickError").parent(".form-group").addClass("has-error");
		$("#nickError").show();
		//$("#nickName").focus();
		return false;
	} else if (!checkNickLength(nickName)) {
		$("#nickError").html("昵称2-6个字");
		$("#nickError").parent(".form-group").addClass("has-error");
		$("#nickError").show();
		//$("#nickName").focus();
		return false;
	} else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(nickName)) {
		$("#nickError").html("昵称只能字母、数字或汉字组成");
		$("#nickError").parent(".form-group").addClass("has-error");
		$("#nickError").show();
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
            async: false,
            success : function(resultdata) {
                if (!resultdata) {
                	$("#nickError").html("该昵称已注册");
                	$("#nickError").parent(".form-group").addClass("has-error");
                	$("#nickError").show();
            		//$("#nickName").focus();
                    flag = false;
                } 
            },
            error : function(errorMsg) {
                $("#nickError").html("服务器未响应,请稍后再试");
                $("#nickError").parent(".form-group").addClass("has-error");
                $("#nickError").show();
        		//$("#nickName").focus();
                flag = false;
            }
        });
        if(!flag)
        {
            return flag;   
        } else {
        	$("#nickError").html("");
        	$("#nickError").parent(".form-group").removeClass("has-error");
        	$("#nickError").hide();
        	return true; 
        }
	}
}