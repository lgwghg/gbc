<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
	<jsp:include page="../include/common.jsp"/>
    <title>成功找回密码${seoConfigMap['1'].title }</title>
	<meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_7d5yvokkelgzaor.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/x-login.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/x-forget-password.css?v=${version }">
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
        <ul class="login-main-tab" style="margin: 50px 0 10px 0">
            <li class="fl">找回密码</li>
            <li class="fr checked">修改成功</li>
        </ul>
        <img src="${staticPrefix }/images/forget_password_x.png" style="margin-left: 130px;">
        <p class="login-main-time">恭喜你，密码修改成功，系统将会在<span>5</span>秒内，自动跳转登录页面</p>
        <button class="btn btn-primary btn-raised btn-xa">立即登录</button>
    </div>
</div>
<div id="inc_footer" style="position: absolute;z-index: 1;bottom: 0;left: 0;width: 100%;">
	<!--footer ==S-->
	<jsp:include page="../include/footer.jsp" />
	<!--footer ==S-->
</div>
<script type="text/javascript">
    $(function(){
        //倒计时
        var timer = 5;
        var DSQ = setInterval(function () {
            timer--;
            if (timer > 0) {
                $(".login-main-time span").html(timer);
            } else {
                location.href = _path + "/login";
            }
        }, 1000)
        //立即登录
        $(".btn-xa").click(function(){
            location.href = _path + "/login";
        })
    });
</script>
</body>
</html>
