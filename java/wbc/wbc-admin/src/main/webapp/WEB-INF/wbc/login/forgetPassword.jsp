<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<jsp:include page="../include/common.jsp"/>
	<title>忘记密码${seoConfigMap['1'].title }</title>
	<meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_7d5yvokkelgzaor.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/x-login.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/x-forget-password.css?v=${version }">
    
    <script src="${staticPrefix }/js/login/forgetPassword.js?v=${version }"></script>
</head>
<body>

<div class="c-bg"></div>
<div id="inc_header">
	<!--top S==-->
   <jsp:include page="../include/header.jsp"/>
   <!--top E-->
</div>
<div class="container" style="padding: 0;position: relative;z-index: 2;">
    <div class="login-main" style="margin-top: 80px;">
        <p class="login-main-title">忘记密码</p>
        <p class="login-main-tab-border"></p>
        <ul class="login-main-tab">
            <li class="fl checked">找回密码</li>
            <li class="fr">修改成功</li>
        </ul>
        <form action="" style="padding: 0 20px;" id="forgetPasswordForm">
        	<div class="form-group label-floating mt-50">
                <label class="control-label">手机号码</label>
                <input type="text" class="form-control text_input" id="mobile" onkeyup="checkMobile()">
                <span class="help-block" id="mobileError"></span>
            </div>
            <div class="form-group label-floating mt-50" style="float:left;width:48%;padding-top: 18px;bottom: 18px;">
                <label class="control-label">手机动态码</label>
                <input type="text" class="form-control text_input" id="captcha" style="padding-right:110px;">
                <span class="help-block" id="captchaError"></span>
                <button id="countdown" type="button" disabled="true" class="btn btn-primary btn-raised pull-right send-auth-code" style="position: absolute; top:-5px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">获取动态码</button>
            </div>
            <div class="form-group label-floating mt-50 hide" style="float:right;width:48%;padding-top: 18px;bottom: 18px;">
                <label class="control-label">验证码</label>
                <input type="text" class="form-control text_input" name="captcha" id="imgCaptcha" style="padding-right:110px;" onkeyup="checkImgCaptcha()">
                <span class="help-block" id="imgCaptchaError"></span>
                <img id="captchaImage" src="${ctx }/captcha" style="cursor:pointer; margin-left:10px; position: absolute; top:5px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;" class="pull-right" title="点击更换"/> 
            </div>
            <div class="fix"></div>
            
            <div class="form-group label-floating mt-50">
                <label class="control-label">新密码</label>
                <input type="password" class="form-control text_input" id="password">
                <span class="help-block" id="passwordError"></span>
            </div>
            <div class="form-group label-floating mt-50">
                <label class="control-label">确认密码</label>
                <input type="password" class="form-control text_input" id="rePassword">
                <span class="help-block" id="rePasswordError"></span>
            </div>
            
        </form>
        <div class="login-main-bottom" style="padding: 0 20px;">
            <p class="mt-30">
                <!-- <span>* 提交即表示你同意并遵守</span>
                <a href="javascript:;" class="t-underline">xxx服务协议</a> -->
            </p>
            <p class="mt-5">
                <span>已经想起密码？</span>
                <a href="${ctx }/login" class="t-underline">登录</a>
            </p>
            <button class="btn btn-primary btn-raised" style="right: 20px;" onclick="setPassword()" id="pawset">提交</button>
        </div>
    </div>
</div>
<div id="inc_footer" style="position: absolute;z-index: 1;bottom: 0;left: 0;width: 100%;">
	
</div>
<jsp:include page="../include/footer.jsp" />
</body>
</html>