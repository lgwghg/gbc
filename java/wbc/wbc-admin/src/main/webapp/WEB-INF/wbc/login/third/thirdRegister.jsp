<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <jsp:include page="../../include/common.jsp"/>
    <title>关联后注册${seoConfigMap['1'].title }</title>
	<meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/reg.css?v=${version }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/login/css/login.css?v=${version }">
    <!-- <link rel="stylesheet" type="text/css" href="node_modules/bootstrap-material-design/dist/css/bootstrap-material-design.css">-->
    <!--必要样式-->
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/component.css?v=${version }" />
	<script src="${staticPrefix }/js/userbase.js?v=${version }"></script>
    <!--[if IE]>
    <script src="js/html5.js"></script>
    <![endif]-->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script type="text/javascript">
	    var sys = sys || {};
		sys.rootPath = "${ctx}";
    </script>
    <script src="${staticPrefix }/js/login/register.js?v=${version }"></script>
</head>
<body>
<div class="mask-layer"></div>
<div class="main">
    <div id="large-header" class="large-header" style="position: absolute;">
        <canvas id="demo-canvas"></canvas>
    </div>

    <!--top S==-->
		<jsp:include page="../../include/header.jsp" />
		<!--top E-->

    <div class="data_text zi-3 data_text_x">
        <img src="${staticPrefix }/images/r.png" class="fl">
        <div><!-- <c:if test="${not empty thirdType}"><c:if test="${thirdType == 'steam' }">通过STEAM</c:if><c:if test="${thirdType == 'qq' }">通过QQ</c:if><c:if test="${thirdType == 'weibo' }">通过新浪微博</c:if><c:if test="${thirdType == 'wechat' }">通过微信</c:if></c:if>登录WODOTA -->
            <h2>HI，${thirdNick }，欢迎您<c:choose><c:when test="${thirdType eq 'steam'}">通过STEAM</c:when><c:when test="${thirdType eq 'qq'}">通过QQ</c:when><c:when test="${thirdType eq 'weibo'}">通过新浪微博</c:when><c:when test="${thirdType eq 'wechat'}">通过微信</c:when></c:choose>登录WODOTA</h2>
            <p class="light_white mt-10">首次访问WODOTA，填以下信息，以后可以用手机号登录WODOTA</p>
        </div>
    </div>

    <div class="keep-login zi-3 keep-login-two">
        <!--<div class="left fl">

            <h4 class="tl">加入我们</h4>
            <h6 class="tl">注册十分简单 - 使用以下任何方式均可</h6>

            <p class="mt-35"> <a href="#" class="steam">使用Steam登录</a></p>
            <p class="mt-15"> <a href="#" class="weibo">使用Steam登录</a></p>
            <p class="mt-15"> <a href="#" class="qq">使用Steam登录</a></p>
            <p class="mt-15"> <a href="#" class="wx">使用Steam登录</a></p>



        </div>-->
        <form role="form" id="registerForm">
        	<input type="hidden" id="token" name="token" value="${token}">
			<input type="hidden" name="returnurl" value="/feed">
        	<input type="hidden" value="${thirdNick }" id="thirdNick" name="thirdNick"/>
        	<input type="hidden" value="${thirdKey }" id="thirdKey" name="thirdKey"/>
        	<input type="hidden" value="${thirdType }" id="thirdType" name="thirdType"/>
            <!-- form-item -->
            <div class="form-item" style="margin-top: 0;">
                <div class="item-tip">昵称,注册后不能修改</div>
                <input class="form-input" name="nickName" id="nickName" tabindex="1" type="text" style="margin-top:5px; height:45px;" value="${thirdNick }">
                <span class="error" id="nickNameError"></span>
                <i style="display: none;" class="icon-loginright"><!--[if lt IE 8]>成功<![endif]--></i>

            </div>
            <!-- form-item -->
            <div class="form-item" style="margin-top: 0;">
                <div class="item-tip">密码</div>
                <input class="form-input" name="password" id="password" tabindex="1" type="password" style="margin-top:5px; height:45px;">
                <span class="error" id="passwordError"></span>
                <i style="display: none;" class="icon-loginright"><!--[if lt IE 8]>成功<![endif]--></i>

            </div>
            <!-- form-item -->
            <div class="form-item" style="margin-top: 0;">
                <div class="item-tip">确认密码</div>
                <input class="form-input" name="rePassword" id="rePassword" tabindex="1" type="password" style="margin-top:5px; height:45px;">
                <span class="error" id="rePasswordError"></span>
                <i style="display: none;" class="icon-loginright"><!--[if lt IE 8]>成功<![endif]--></i>

            </div>
            <!-- form-item -->
            <div class="form-item" style="margin-top: 0;">
                <div class="item-tip">手机号,注册后用于登录的账号</div>
                <input class="form-input" name="mobile" id="mobile" tabindex="1" type="text" style="margin-top:5px; height:45px;">
                <span class="error" id="mobileError"></span>
                <i style="display: none;" class="icon-loginright"><!--[if lt IE 8]>成功<![endif]--></i>

            </div>
            <!-- form-item -->
            <div class="form-item form-item-one" style="width: 200px;margin: 15px 0;">
                <div class="item-tip">验证码</div>
                <input class="form-input" name="captcha" id="captcha" tabindex="1" type="text" style="padding-right: 80px;margin-top:5px; height:45px;">
                <i style="display: none;" class="icon-loginright"><!--[if lt IE 8]>成功<![endif]--></i>
                <button type="button" class="btn btn-primary ml-15 send-auth-code-x send-auth-code">发送验证码
                </button>
                <span class="auth-code-error" id="captchaError" ></span>
            </div>
            <p class="mt-10"><a href="#">提交即表示你同意并遵守WODOTA服务协议</a></p>
            <span>
            <%-- ?thirdKey=${thirdNick }&thirdNick=${thirdNick}&thirdType=${thirdType} --%>
               <a href="${ctx }/bind/login?thirdKey=${thirdKey }&thirdNick=${thirdNick}&thirdType=${thirdType}" class="fr ml-5 co_00 go-binding" style="margin-top: 19px;">去绑定</a>
               <a href="${ctx }/bind/login?thirdKey=${thirdKey }&thirdNick=${thirdNick}&thirdType=${thirdType}" class="fr light_gray" style="margin-top: 19px;">有帐号？</a>
            </span>
            <button type="button" class="mt-15 btn btn-primary btn-block fn_16 btn-primary-one" style="width:180px; height:35px" id="mobileRegisterButton">提&nbsp;交</button>

        </form>
    </div>


   <jsp:include page="../../include/footer.jsp" />
    <div class="fix"></div>
    <script src="${staticPrefix }/js/TweenLite.min.js"></script>
    <script src="${staticPrefix }/js/EasePack.min.js"></script>
    <script src="${staticPrefix }/js/demo-1.js"></script>
</div>
</body>
</html>