<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <jsp:include page="../../include/common.jsp"/>
    <title>关联后登录${seoConfigMap['1'].title }</title>
    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/reg.css?v=${version }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/login/css/login.css?v=${version }">
    <!-- <link rel="stylesheet" type="text/css" href="node_modules/bootstrap-material-design/dist/css/bootstrap-material-design.css">-->
    <!--必要样式-->
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/component.css?v=${version }" />

    <!--[if IE]>
    <script src="js/html5.js"></script>
    <![endif]-->
    <script src="${staticPrefix }/js/userbase.js?v=${version }"></script>
	<script type="text/javascript">
		    var sys = sys || {};
			sys.rootPath = "${ctx}";
	    </script>
	<script type="text/javascript" src="${staticPrefix }/js/login/login.js?v=${version }"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="mask-layer"></div>

<div id="large-header" class="large-header" style="position: absolute;">
    <canvas id="demo-canvas"></canvas>
</div>

<!--top S==-->
		<jsp:include page="../../include/header.jsp" />
		<!--top E-->

<div class="data_text zi-3">
    <img src="${staticPrefix }/images/r.png" class="fl">
    <div>
        <h2>HI，${thirdNick }，欢迎您<c:if test="${not empty thirdType}"><c:if test="${thirdType eq 'steam' }">通过STEAM</c:if><c:if test="${thirdType eq 'qq' }">通过QQ</c:if><c:if test="${thirdType eq 'weibo' }">通过新浪微博</c:if><c:if test="${thirdType eq 'wechat' }">通过微信</c:if></c:if>登录WODOTA</h2>
        <p class="light_white mt-10">首次访问WODOTA，填以下信息，以后可以用手机号登录WODOTA</p>
    </div>
</div>

<div class="keep-login zi-3 keep-login-one">
    <form role="form" id="loginform" name="loginform" action="${ctx }/login/" method="post">
        <input name="email" id="email" type="hidden" />
		<input name="mobile" id="mobile" type="hidden" />
		<input name="thirdNick" id="thirdNick" type="hidden" value="${thirdNick }"/>
		<input name="thirdType" id="thirdType" type="hidden" value="${thirdType }"/>
		<input name="thirdKey" id="thirdKey" type="hidden" value="${thirdKey }"/>
		<input name="rememberMe" id="rememberMe" type="hidden" value="false"/>
        <!-- form-item -->
        <div class="form-item">
            <div class="item-tip">手机/邮箱</div>
            <input class="form-input" name="loginName" id="loginName" tabindex="1" type="text" style="margin-top:5px; height:45px;">
            <span style="display: inline-block;" class="error" id="loginNameError"></span>
            <i style="display: none;" class="icon-loginright"><!--[if lt IE 8]>成功<![endif]--></i>
        </div>
        <!-- form-item -->
        <div class="form-item" style="margin-top: 0;">
            <div class="item-tip">密码</div>
            <input class="form-input" name="password" id="password" tabindex="1" type="password" style="margin-top:5px; height:45px;">
            <span style="display: inline-block;" class="error" id="passwordError"></span>
            <i style="display: none;" class="icon-loginright"><!--[if lt IE 8]>成功<![endif]--></i>
        </div>
        <a href="${ctx }/bind/register?thirdKey=${thirdKey}&thirdNick=${thirdNick}&thirdType=${thirdType}" class="fr mt-5 co_00">马上去注册</a>
        <a href="${ctx }/bind/register?thirdKey=${thirdKey}&thirdNick=${thirdNick}&thirdType=${thirdType}" class="fr mt-5">没有账号？</a>
        <button type="button" class="btn btn-primary btn-block fn_16 btn-info-one" style="width:180px; height:35px;" onclick="login();">绑&nbsp;定</button>
    </form>


</div>

 <jsp:include page="../../include/footer.jsp" />
<div class="fix"></div>
<!-- <script src="js/TweenLite.min.js"></script>
<script src="js/EasePack.min.js"></script>
<script src="js/demo-1.js"></script> -->
</body>
</html>