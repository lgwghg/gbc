/**
 * 生成邮箱验证码
 */
var genEmailCaptcha = function(email) {
	// 发验证码的请求
	$.ajax({
        type : "POST",
        url :  _path + "/fp/genEmailCaptcha",
        data : {
            "email" : email
        },
        dataType : "json",
        async : false,
        success : function(result) {
        	if (result == -1) {
        		layer.msg("操作频繁，请稍候重试", {icon : 5,shift : 6,time : 1000});
            } else if (result == 0) {
            	layer.msg("请稍候重试", {icon : 5,shift : 6,time : 1000});
            } else {
            	layer.msg("邮箱验证码已发", {icon : 6,shift : 6,time : 1000});
            }
        },
        error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
    });
};

/**
 * 生成手机验证码
 * mobile ：手机号
 * type ： 验证码类型
 */
var genMobileCaptcha = function(mobile, type, imgCaptcha) {
	$.ajax({
        type : "POST",
        url :  _path + "/withoutAuth/genCaptcha",
        data : {
            "mobile" : mobile,
            "type" : type,
            "imgCaptcha" : imgCaptcha
        },
        dataType : "json",
        success : function(result) {
     	   if (result == -1) {
          	 layer.msg("操作频繁，请稍候重试", {icon : 5,shift : 6,time : 1000});
           } else if (result == 0) {
          	 layer.msg("请稍候重试", {icon : 5,shift : 6,time : 1000});
           } else if (result == -2) {
          	 layer.msg("验证码不能为空", {icon : 5,shift : 6,time : 1000});
           }  else if (result == -3) {
          	 layer.msg("验证码错误", {icon : 5,shift : 6,time : 1000});
           } else {
          	 layer.msg("动态码已发到尾号为" + mobile.substring(mobile.length-4) + "的手机", {icon : 6,shift : 6,time : 1000});
           }
        },
        error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
    });
};