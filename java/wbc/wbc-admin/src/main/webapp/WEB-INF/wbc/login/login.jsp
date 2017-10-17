<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<jsp:include page="../include/common.jsp"/>
	<title>登录${seoConfigMap['1'].title }</title>
	<meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_7d5yvokkelgzaor.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/x-login.css?v=${version }">
	
	<%-- <script src="${staticPrefix }/js/userbase.js"></script> --%>
	<script type="text/javascript" src="${staticPrefix }/js/login/login.js?v=${version }"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//错误提示信息
			if ("${error}" != "") {
		    	layer.alert('${error}', {icon : 5,shift : 6,time : 3000});
			}
			
			//页面进行跳转到login
			if (window.location.href.indexOf("/login") == -1) {
			    if($("#userId").val() == null || $("#userId").val() == "")
			    {
			        top.location.href = "login";
			    }else
			    {
			        top.location.href = "index";
			    }
			}
			
		});
	</script>
</head>
<body>
	<input id="userId" type="hidden" value="<c:if test="${not empty sessionScope.userSessionId}">${sessionScope.userSessionId }</c:if>" />

		<div class="c-bg"></div>
		<div id="inc_header">
		<!--top S==-->
	    <jsp:include page="../include/header.jsp"/>
	    <!--top E-->
		</div>
		<div class="container" style="padding: 0;position: relative;z-index: 2;">
		    <div class="login-main">
		        <p class="login-main-title mb-50">登录开始竞猜</p>
		        <form id="loginform" name="loginform" action="${path }/login" method="post">
		        	<input type="hidden" name="returnurl" value="feed">
					<input name="email" id="email" type="hidden" />
					<input name="mobile" id="mobile" type="hidden" />
				<!-- <div>
				<p class="tc"><img src="" alt="" style=" width: 70px; height: 70px; line-height:70px; text-align: center;"></p>
        		<p class="tc mt-10" style="color: #fff;">“怦然”<a style=" color: #31C3A2;  text-decoration: underline;">不是你？</a></p>
		         </div> -->
		            <div id="historyLog" style="display:none">
						<p class="tc"><img src="" class="img-circle" id="userPhoto" alt="" style=" width: 70px; height: 70px; line-height:70px; text-align: center;"></p>
        				<p class="tc mt-10" style="color: #fff;"><font id="nickName"></font><a style=" color: #31C3A2;  text-decoration: underline;" id="notMe">不是你？</a></p>
		        	</div>
		            <div class="form-group label-floating mt-70" id="loginNameDiv">
		                <label class="control-label">手机号/邮箱</label>
		                <input type="text" class="form-control text_input" name="loginName" id="loginName">
		                <span class="help-block" id="loginNameError"></span>
		            </div>
		            <div class="form-group label-floating mt-50">
		                <label class="control-label">密码</label>
		                <input type="text" class="form-control text_input" name="password" id="password" autocomplete="off" onfocus="this.type='password'">
		                <span class="help-block" id="passwordError"></span>
		            </div>
		        </form>
		        <div class="login-main-bottom">
		            <p class="mt-30"><a href="${path }/fp/forgetPassword" class="t-underline">忘记密码？</a></p>
		            <p class="mt-5">
		                <span>新成员？</span>
		                <a href="${path }/register">现在注册</a>
		            </p>
		            <button class="btn btn-primary btn-raised" onclick="login();">登录</button>
		        </div>
		    </div>
		</div>
		
		<div id="inc_footer" style="z-index: 1;bottom: 0;left: 0;width: 100%; height:291px;">
		</div>
		<!--footer ==S-->
		<jsp:include page="../include/footer.jsp" />
		<!--footer ==S-->

</body>
</html>