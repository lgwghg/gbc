<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${header['X-PJAX']}">
	<jsp:include page="../../include/common_pjax.jsp"/>
		<title>CD-KEY兑换${seoConfigMap['1'].title }</title>
		<meta name="keywords" content="${seoConfigMap['1'].keywords }">
		<meta name="description" content="${seoConfigMap['1'].description }">
<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_wp3a5t78p12d42t9.css?v=${version }&t=<%=Math.random() %>">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/myaccount.css?v=${version }&t=<%=Math.random() %>">
<script src="${staticPrefix }/js/common/cropper.min.js?v=${version }&t=<%=Math.random() %>"></script>
<script src="${staticPrefix }/js/common/sitelogo.js?v=${version }&t=<%=Math.random() %>"></script>
<script src="${staticPrefix }/js/my/cdkey/cdkey.js?v=${version }&t=<%=Math.random() %>"></script>


<style type="text/css">
.text_sm,.text_sm a {
	color: #31c3a2;
	margin-left: 10px;
}
</style>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!--[if lt IE 9]>
    <script>
        document.createElement('video');
    </script>
    <![endif]-->
		<div class="c-informright pull-left">
		<p class="ft-16 pt-5 pb-20">CD-KEY兑换</p>
				<div class="c-centerwrap"
					style="width: 100%; padding: 3px 10px 90px;">
					<form action=""
						style="padding: 0 20px; width: 50%; margin: 20px auto;">
						<div class="form-group label-floating mt-50">
							<label class="control-label">输入CD-KEY</label> <input type="text"
								class="form-control text_input" style="text-align:left"
								id="cdkeyCode" /> <span class="help-block" style="color: red;"
								id="cdkeyError"></span>
						</div>
						<div class="form-group label-floating mt-50" style="width:60%;">
							<label class="control-label">验证码</label> <input type="text"
								class="form-control text_input" style="text-align:left"
								id="cdkeyCaptcha" onkeyup="checkCdkeyCaptcha()" /> <span
								class="help-block" id="cdkeyCaptchaError"></span> <span
								id="countdown"
								style="position: absolute; bottom:15px; right: 0px; ;padding: 0;padding: 0;"><img
								src="/captcha" id="cdkeyCaptchaImage" /></span>
						</div>
						<div class="mt-20">
							<button class="btn btn-primary btn-raised btn-sm"
								id="cdkeyExchangeBtn" type="button" disabled="true" style="color:white; border:1px solid #31c3a2">兑换</button>
							<span><a href="/my/exchangeLog" class="text_sm">兑换记录</a></span>
						</div>
					</form>

					<div class="login-main-bottom" style="padding: 0 20px;">
						<p class="mt-30">
							<span>CD-KEY兑换说明：</span>
						</p>
						<p class="mt-5" style="color: #4e636e;">1、无论兑换何种CD-KEY，在CD-KEY有效期内只能兑换成功一次。</p>
						<p class="mt-5" style="color: #4e636e;">2、各种非官方渠道获得的CD-KEY都有可能无法兑换、兑换失败、兑换不到您想要的商品。请尽量避免在第三方渠道购买CD-KEY。</p>
						<p class="mt-5" style="color: #4e636e;">3、如您使用的是“商品兑换CD-KEY”，请在商品兑换记录查询商品情况。</p>
					</div>
				</div>
				</div>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html lang="en">
<head>
<jsp:include page="../../include/common.jsp" />
<title>CD-KEY兑换${seoConfigMap['1'].title }</title>
<meta name="keywords" content="${seoConfigMap['1'].keywords }">
<meta name="description" content="${seoConfigMap['1'].description }">

<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_wp3a5t78p12d42t9.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/myaccount.css?v=${version } %>">
<script src="${staticPrefix }/js/common/cropper.min.js?v=${version }"></script>
<script src="${staticPrefix }/js/common/sitelogo.js?v=${version }"></script>
<script src="${staticPrefix }/js/my/cdkey/cdkey.js?v=${version }"></script>


<style type="text/css">
.text_sm,.text_sm a {
	color: #31c3a2;
	margin-left: 10px;
}
</style>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!--[if lt IE 9]>
    <script>
        document.createElement('video');
    </script>
    <![endif]-->
</head>
<body>
	<div class="container-fluid" style="margin:0; padding:0;">
		<!--header S==-->
		<jsp:include page="../../include/header.jsp" />
		<!--header E-->
		<!-- main -->
		<div class="container c-informwrap" style="" id="new-container">
			<!--menu ==S-->
			<jsp:include page="../include/menu.jsp" />
			<!--menu ==S-->
			<!-- <div class="pull-left c-informleft">
					<iframe src="sideBar.html"
						style="width:100%;height:500px; border: none;"></iframe>
				</div> -->
			<div id="container">
			
			<div class="c-informright pull-left">

				<p class="ft-16 pt-5 pb-20">CD-KEY兑换</p>
				<div class="c-centerwrap"
					style="width: 100%; padding: 3px 10px 90px;">
					<form action=""
						style="padding: 0 20px; width: 50%; margin: 20px auto;">
						<div class="form-group label-floating mt-50">
							<label class="control-label">输入CD-KEY</label> <input type="text"
								class="form-control text_input" style="text-align:left"
								id="cdkeyCode" /> <span class="help-block" style="color: red;"
								id="cdkeyError"></span>
						</div>
						<div class="form-group label-floating mt-50" style="width:60%;">
							<label class="control-label">验证码</label> <input type="text"
								class="form-control text_input" style="text-align:left"
								id="cdkeyCaptcha" onkeyup="checkCdkeyCaptcha()" /> <span
								class="help-block" id="cdkeyCaptchaError"></span> <span
								id="countdown"
								style="position: absolute; bottom:15px; right: 0px; ;padding: 0;padding: 0;"><img
								src="/captcha" id="cdkeyCaptchaImage" /></span>
						</div>
						<div class="mt-20">
							<button class="btn btn-primary btn-raised btn-sm"
								id="cdkeyExchangeBtn" type="button" disabled="true" style="color:white; border:1px solid #31c3a2">兑换</button>
							<span><a href="/my/exchangeLog" class="text_sm">兑换记录</a></span>
						</div>
					</form>

					<div class="login-main-bottom" style="padding: 0 20px;">
						<p class="mt-30">
							<span>CD-KEY兑换说明：</span>
						</p>
						<p class="mt-5" style="color: #4e636e;">1、无论兑换何种CD-KEY，在CD-KEY有效期内只能兑换成功一次。</p>
						<p class="mt-5" style="color: #4e636e;">2、各种非官方渠道获得的CD-KEY都有可能无法兑换、兑换失败、兑换不到您想要的商品。请尽量避免在第三方渠道购买CD-KEY。</p>
						<p class="mt-5" style="color: #4e636e;">3、如您使用的是“商品兑换CD-KEY”，请在商品兑换记录查询商品情况。</p>
					</div>
				</div>
			</div>
			</div>
		</div>

		<!--footer ==S-->
		<jsp:include page="../../include/footer.jsp" />
		<!--footer ==S-->
	</div>
</body>
		</html>
	</c:otherwise>
</c:choose>

