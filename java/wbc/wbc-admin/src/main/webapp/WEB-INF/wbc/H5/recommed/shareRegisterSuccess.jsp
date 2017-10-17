<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.webside.common.GlobalConstant"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<title>G菠菜注册成功</title>
<link  href="${staticPrefix }/H5/css/successful.css?v=${version }"  rel="stylesheet"  type="text/css"/>

</head>
<body>

<div class="main">
           <div class="successful">
               <p><img src="${staticPrefix }/H5/images/go.png"></p>
               <h2 class="mt20">恭喜您注册成功！</h2>
               <h3 class="mt20">300 G币已放入您的帐号</h3>
               <h3 class="mt10 wzz">登录<a href="javascript:void()" class="wz">gbocai.com</a>，体验电子竞技竞猜！</h3>
           </div>
           <div class="contact">
           	<div class="txt">联系方式</div>
           </div>
           
           <div class="tel">
               <p>客服邮箱：kefu@gbocai.com</p>
               <p>商务合作：bd@gbocai.com</p>
               <p>官方QQ群：612732655</p>
           </div>
</div>
</body>
</html>