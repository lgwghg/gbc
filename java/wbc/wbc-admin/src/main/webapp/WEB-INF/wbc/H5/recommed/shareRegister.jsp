<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.webside.common.GlobalConstant"%>
<%
	request.setAttribute("path", request.getContextPath());
	request.setAttribute("ctx", request.getContextPath());
	request.setAttribute("staticPrefix",  request.getContextPath()+"/resources/wbc");
	request.setAttribute("version", GlobalConstant.VERSION);
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-Cache" />
<meta name="viewport" content="width=device-width; initial-scale=1.0; minimum-scale=0.5; maximum-scale=2.0">
<meta name="MobileOptimized" content="320">
<meta name="format-detection" content="telephone=no" />
<title>G菠菜注册</title>
<script language="JavaScript">
	var _path = '${path}';
	var _staticPrefix = '${staticPrefix}';
</script>
<link  href="${staticPrefix }/H5/css/register.css?v=${version }"  rel="stylesheet"  type="text/css"/>
<script type="text/javascript" src="${staticPrefix }/H5/js/jquery.js?v=${version }"></script>	
<script type="text/javascript" src="${staticPrefix }/H5/js/material.js?v=${version }"></script>	
<script type="text/javascript" src="${staticPrefix }/H5/js/ripples.min.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/layer-v2.3/layer.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/common/captcha.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/login/register.js?v=${version }"></script>
</head>
<body style="background:#2e3238;">

<div class="main">
  <div class="logo"><img src="${staticPrefix }/H5/images/logo_r.png" class="row_img"></div>
  
  <form action="" class="form" id="registerForm">
  			<input type="hidden" id="shareRegister" value="1" />
            <div class="form-group label-floating">
                <label class="control-label" for="nickName">昵称,注册后不能修改</label>
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
                <input type="text" class="form-control text_input" value="${inviteCode }" id="fromuid" name="fromuid">
                <span class="help-block"></span>
            </div>
            <div class="form-group label-floating mt-50">
                <label class="control-label">手机号,用做登录账号</label>
                <input type="text" class="form-control text_input" name="mobile" id="rmobile" onkeyup="checkMobile()">
                <span class="help-block" id="mobileError"></span>
            </div>
            <div class="form-group label-floating mt-50">
                <label class="control-label">动态码</label>
                <input type="text" class="form-control text_input" name="captcha" id="captcha">
                <span class="help-block" id="captchaError"></span>
                <button type="button" id="countdown" class="btn btn-primary btn-raised pull-right send-auth-code" style="position: absolute; top:-5px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">获取动态码</button>
            </div>
            <div class="form-group label-floating mt-50 hide">
                <label class="control-label">验证码</label>
                <input type="text" class="form-control text_input" name="captcha" id="imgCaptcha"  onkeyup="checkImgCaptcha()">
                <span class="help-block" id="imgCaptchaError"></span>
                <img id="captchaImage" src="${ctx }/captcha" style="cursor:pointer; margin-left:10px; position: absolute; top:5px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;" class="pull-right" title="点击更换"/> 
            </div>
             <p class="mt20">
                <span>* 提交即表示你同意并遵守</span>
                <a href="${ctx }/help/agreementToH5" class="t-underline">《Gbocai用户注册与服务协议》</a>
            </p>
            
            <!--  <p class="mt-15">
                <span>已经是会员？</span>
                <a href="javascript:;" class="t-underline">登录</a>
            </p> -->
            
            <p class="mt30 mb50">
            <button type="button" class="btn btn-primary btn-primary-br btn-raised" id="mobileRegisterButton">注册并领取大礼包</button>
            </p>
        </form>
        
</div>
</body>

<script>
    $.material.init();
</script>
</html>
