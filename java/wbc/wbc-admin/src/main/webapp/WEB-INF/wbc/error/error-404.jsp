<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	
<!DOCTYPE html>
<html>
<head lang="en">
<jsp:include page="../include/common.jsp"/>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>出错啦~${seoConfigMap['1'].title }</title>
    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/base.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/wx404.css?v=${version }">
</head>
<body>
<div class="c-bg">
    <img src="${staticPrefix }/images/weixin_404.png?v=${version }">
    <p class="error-one">抱歉，你访问的页面找不到了</p>
    <!-- <button class="btn btn-primary btn-raised error-two">返回</button> -->
</div>
</body>
</html>

