 $(function(){
    	var nickName = cookie_nick_name;
    	//layer.alert(nickName, {icon : 5,shift : 6,time : 0});
    	if(nickName!=""&&nickName!=null){
    		var loginName = cookie_login_name;
    		var userPhoto = cookie_user_photo;
    		if(userPhoto==""||userPhoto==null){
    			userPhoto = _path + "/resources/wodota/images/denglu_n.png";
    		}else{
    			userPhoto = _path + userPhoto;
    		}
    		$("#right_nickName").html("‘"+nickName+"’");
    		$("#right_mobile").val(loginName);
    		$("#right_userPhoto").attr('src',userPhoto);
    		$("#right_historyLog").show();
    		$("#right_loginNameDiv").hide();
    	}
    	
    	$("#right_notMe").click(function() {
    		$("#right_historyLog").hide();
   		 	$("#right_loginNameDiv").show();
   		 	$("#right_mobile").val('');
    	});
    });
    
$(document).ready(function() {
	$('#right_loginform').find("input").bind("keydown", function(event) {
		if(event.keyCode==13) {
	        login();
	    }
	});
    $('#right_loginform').bind('submit', function()
   	{
        ajaxSubmit(this, function(data)
        {
            if(parseInt(data.code) == 1)//帐号验证成功
            {
            	if(data.url.trim() != "" && data.url.indexOf(".") < 0)
            	{
            		getTopWinow().location.href = _path + data.url.trim();
            	}
            	else
            	{
                	getTopWinow().location.href = window.location.href;
            	}
            }
            else
            {
            	var info = "未知错误,请稍后再试!";
            	
            	if(data.msg != "")
            	{
            		info = data.msg;
            		$("#right_email").val('');
            		$("#right_mobile").val('');
            	}
            	
            	layer.alert(info, {icon : 5,shift : 6,time : 3000});
            }
        });
        
        return false;
    });
    
});


//登录
function right_login() {
    if($("#right_loginName").val() == "") {
    	if ($("#right_email").val() == "" && $("#right_mobile").val() == "") {
    		$("#right_loginNameError").html("请输入账户邮箱或手机号");
    		$("#right_loginNameError").parent(".form-group").addClass("has-error");
    		$("#right_loginName").focus();
    		return false;
    	}
    } else {
    	if($("#right_email").val()=='' && $("#right_mobile").val()==''){
    		var emailReg = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
            var mobileReg = /^1[3|4|5|7|8]\d{9}$/;
            if(!emailReg.test($("#right_loginName").val()) && !mobileReg.test($("#right_loginName").val()))
            {
                $("#right_loginNameError").html("请输入正确的邮箱或手机号");
                $("#right_loginNameError").parent(".form-group").addClass("has-error");
                $("#right_loginName").focus();
                return false;
            } else if (emailReg.test($("#right_loginName").val()) && !mobileReg.test($("#right_loginName").val())) {
            	// 用邮箱登录
            	$("#right_email").val($("#right_loginName").val());
            } else if (!emailReg.test($("#right_loginName").val()) && mobileReg.test($("#right_loginName").val())) {
            	// 用手机号登录
            	$("#right_mobile").val($("#right_loginName").val());
            }
    	}
    }
    $("#right_loginNameError").html("");
    $("#right_loginNameError").parent(".form-group").removeClass("has-error");
    if($("#right_password").val() == "") {
    	$("#right_passwordError").html("请输入密码");
    	$("#right_passwordError").parent(".form-group").addClass("has-error");
        $("#right_password").focus();
         return false;
    }
    $("#right_passwordError").html("");
    $("#right_passwordError").parent(".form-group").removeClass("has-error");
    /*if($("#captcha").val() == "")
    {
        $("#captcha").focus();
        layer.alert('请输入验证码', {icon : 5,shift : 6,time : 0});
         return false;
    }*/
    if($("#right_rememberMeCheckBox").is(':checked')) {
        $("#right_rememberMe").val(true);
    }
    $("#right_loginform").submit();
}

