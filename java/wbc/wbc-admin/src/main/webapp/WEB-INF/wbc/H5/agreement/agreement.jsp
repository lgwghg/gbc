<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.webside.common.GlobalConstant"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("staticPrefix",  request.getContextPath()+"/resources/wbc");
request.setAttribute("path", path);
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
<meta itemprop="name" content="300G币邀你体验G菠菜竞猜"/>
<meta name="description" itemprop="description" content="G菠菜是电子竞技菠菜/竞猜娱乐平台.这里有:王者荣耀KPL联赛,DOTA2CDEC联赛等直播赛事竞猜项目,注册即赠G币.">
<!-- <meta itemprop="image" content="" /> -->
<title>G菠菜注册&服务协议</title>
<link  href="${staticPrefix }/H5/css/global.css?v=${version }"  rel="stylesheet"  type="text/css"/>
<style>
.main{
	
	padding: 10px 2px;
}
</style>
</head>
<body>

<div class="main" style="width: 90%;">
         <div style="text-align: center; font-size: 24px; font-weight: 500;">${webNotice.title }</div>
         	${webNotice.content }
         <div class="panel">
         </div>
         
</div>
</body>

</html>

