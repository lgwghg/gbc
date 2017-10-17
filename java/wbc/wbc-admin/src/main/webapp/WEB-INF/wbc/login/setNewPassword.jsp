<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="staticPrefix" value="${pageContext.request.contextPath}/resources/wodota" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<jsp:include page="../include/common.jsp"/>
	<title>设置新密码${seoConfigMap['1'].title }</title>
	<meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${staticPrefix }/login/css/login.css?v=${version }">
	<link rel="stylesheet" type="text/css" href="${staticPrefix }/login/css/component.css?v=${version }">
	<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/reg.css?v=${version }">
	
	<script src="${staticPrefix }/js/userbase.js?v=${version }"></script>
	<script type="text/javascript">
		    var sys = sys || {};
			sys.rootPath = "${ctx}";
	    </script>
	<script src="${staticPrefix }/js/login/forgetPassword.js?v=${version }"></script>
</head>
<body>
	<div class="main">

		<!--top S==-->
	    <jsp:include page="../include/header.jsp"/>
	    <!--top E-->
		<div class="demo-1">
			<div class="content">
				<div id="large-header" class="large-header">
					<canvas id="demo-canvas"></canvas>
					<div class="main-title">
						<!--set pass S==-->

						<div class="vlist">
							<h2>找回密码</h2>
						</div>
						<div class="loginbox" style="height:375px;margin: 80px auto 40px auto;">

							<div class="processor">
								<ol class="processorBox oh">
									<li>
										<div class="step_inner fl">
											<span class="icon_step">01</span>
											<h4>选择找回方式</h4>
										</div>
									</li>
									<li>
										<div class="step_inner">
											<span class="icon_step">02</span>
											<h4>进行安全验证</h4>
										</div>
									</li>
									<li class="current">
										<div class="step_inner fr">
											<span class="icon_step">03</span>
											<h4>设置新密码</h4>
										</div>
									</li>
								</ol>
								<div class="step_line"></div>
							</div>

							<p class="light_gray mt-25 sx">请输入新的登录密码</p>
							<form role="form" class="mt-15">
								<div class="form-group">

									<input type="hidden" id="userId" name="userId" value="${userId }" />
									<!-- form-item -->
									<div class="form-item form_item mt-10">
										<div class="item-tip">设置新密码</div>
										<input class="form-input" name="password" id="password" tabindex="1" type="password" style="width:100%; margin-top:5px; height:45px;" onBlur="checkPassword()">
										<span class="error" id="passwordError"></span>
									</div>


									<!-- form-item -->
									<div class="form-item form_item mt-10">
										<div class="item-tip">确认新密码</div>
										<input class="form-input" name="rePassword" id="rePassword" tabindex="1" type="password" style="width:100%; margin-top:5px; height:45px;" onBlur="checkRePassword()">
										<span class="error" id="rePasswordError"></span>
									</div>
								</div>
							</form>

							<button type="button" class="btn btn-primary btn-block  h38 fn_16" style="margin-top:20px; height:45px;" onclick="setPassword();" id="pawset" >确&nbsp;定</button>


						</div>

						<!--set pass E==-->

					</div>

				</div>

			</div>
		</div>

	</div>

	<%@ include file="../include/footer.jsp"%>

</body>

<script src="${staticPrefix }/login/js/TweenLite.min.js"></script>
<script src="${staticPrefix }/login/js/EasePack.min.js"></script>
<script src="${staticPrefix }/login/js/demo-1.js"></script>

<script type="text/javascript">
　//滚动后导航固定
			$(window).scroll(function(){
				  height = $(window).scrollTop();
				  if(height > 100){
					$('.nav_box_small').fadeIn("fast");
				  }else{
					$('.nav_box_small').fadeOut("fast");
				  };
		});
		
	
	
			
</script>
</html>