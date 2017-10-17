<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<jsp:include page="../include/common.jsp"/>
	<title>注册${seoConfigMap['1'].title }</title>
	<meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
	<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_7d5yvokkelgzaor.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/x-login.css?v=${version }">
	
	<script src="${staticPrefix }/js/login/register.js?v=${version }"></script>

</head>
<body>
<div class="c-bg"></div>
<div id="inc_header">
	<!--top S==-->
    <jsp:include page="../include/header.jsp"/>
    <!--top E-->
</div>
<div class="container" style="padding: 0;position: relative;z-index: 2;">
    <div class="login-main" style="margin-top: 40px;">
        <p class="login-main-title">注册开始竞猜</p>
        <form action="" id="registerForm">
        	<input type="hidden" id="token" name="token" value="${token}">
			<input type="hidden" name="returnurl" value="/feed">
            <div class="form-group label-floating mt-70">
                <label class="control-label">昵称,注册后不能修改</label>
                <input type="text" class="form-control text_input" name="nickName" id="rnickName" maxlength="13">
                <span class="help-block" id="nickNameError"></span>
            </div>
            <div class="form-group label-floating mt-50">
                <label class="control-label">密码</label>
                <input type="text" class="form-control text_input" id="rpassword" name="password" autocomplete="off" onfocus="this.type='password'">
                <span class="help-block" id="rpasswordError"></span>
            </div>
            <div class="form-group label-floating mt-50">
                <label class="control-label">确认密码</label>
                <input type="text" class="form-control text_input" name="rePassword" id="rePassword" autocomplete="off" onfocus="this.type='password'">
                <span class="help-block" id="rePasswordError"></span>
            </div>
            <div class="form-group label-floating mt-50">
                <label class="control-label">邀请码 (选填)</label>
                <input type="text" class="form-control text_input" value="${param.fromuid}" id="fromuid" name="fromuid">
                <span class="help-block"></span>
            </div>
            <div class="form-group label-floating mt-50">
                <label class="control-label">手机号,用做登录账号</label>
                <input type="text" class="form-control text_input" name="mobile" id="rmobile" onkeyup="checkMobile()">
                <span class="help-block" id="mobileError"></span>
            </div>
            <div class="form-group label-floating mt-50" style="float:left;width:48%;padding-top: 18px;bottom: 18px;">
                <label class="control-label">手机动态码</label>
                <input type="text" class="form-control text_input" name="captcha" id="captcha" style="padding-right:110px;">
                <span class="help-block" id="captchaError"></span>
                <button id="countdown" type="button" class="btn btn-primary btn-raised pull-right send-auth-code" style="position: absolute; top:-5px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">获取动态码</button>
            </div>
            <div class="form-group label-floating mt-50 hide" style="float:right;width:48%;padding-top: 18px;bottom: 18px;">
                <label class="control-label">验证码</label>
                <input type="text" class="form-control text_input" name="captcha" id="imgCaptcha" style="padding-right:110px;" onkeyup="checkImgCaptcha()">
                <span class="help-block" id="imgCaptchaError"></span>
                <img id="captchaImage" src="${ctx }/captcha" style="cursor:pointer; margin-left:10px; position: absolute; top:5px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;" class="pull-right" title="点击更换"/> 
            </div>
            <div class="fix"></div>
        </form>
        <div class="form-group">
			<div class="checkbox">
				<label style=" color: #fff;">
					<input type="checkbox" id="isRead" onchange="isRead()">已阅读并同意<a href="${ctx }/help/agreement" target = "_blank" style="color: #31bc9c;">《Gbocai用户注册与服务协议》</a>
				</label>
			</div>
		</div>
        <div class="login-main-bottom">
            <p style=" font-size: 16px;">
                <span style=" color: #fff;">已经是会员？</span>
                <a href="${path }/login" class="t-underline">登录</a>
            </p>
            <button type="button" class="btn btn-primary btn-raised" id="mobileRegisterButton" disabled="true">提交</button>
        </div>
    </div>
</div>
<div id="inc_footer" style="position: absolute;z-index: 1;bottom: 0;left: 0;width: 100%;">

</div>
<jsp:include page="../include/footer.jsp" />
</body>

</html>