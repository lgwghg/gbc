<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../include/common.jsp"/>
    <title>商城${seoConfigMap['1'].title }</title>
    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
	
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/exchange.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/goods/x-shopping-mall.css?v=${version }">
    <script type="text/javascript" src="${staticPrefix }/js/goods/index.js?v=${version }"></script>
    
  </head>
  
  <body>
    <!-- 头部 -->
    <jsp:include page="../include/header.jsp"/>
    
	<div class="container" id="new-container" style="padding: 0;position: relative;z-index: 1;">
	    <!-- 通知 -->
	    <jsp:include page="../include/message.jsp"/>
	    
	    <div>
	        <ul class="breadcrumb" style="background:none; font-size:14px; padding: 12px 0;margin: 0;">
	            <li class="one"><a href="${path }/match">首页</a></li>
	            <li class="two"><a href="">商城</a></li>
<!-- 	            <li class="fr but"><button type="button"  onclick="buyAll();">全部兑换点卡</button><button type="button" class="ml-10" onclick="salesAll();">商品全部出售</button></li> -->
	        </ul>
	    </div>
	    <div class="shopping-commodity">
	        <p class="view1">
	            <span class="ml-20">热销商品</span>
	        </p>
	        <!-- 商品列表 -->
	        <ul class="row view2" id="goods-ul">
	        
	        </ul>
	        <nav>
	            <ul class="pagination" id="pagination" style="margin-left: 0;">
	            </ul>
	        </nav>
	    </div>
	</div>
	<!-- 底部 -->
	<jsp:include page="../../wbc/include/footer.jsp"/>
	
	<script type="text/javascript">
		$(function() {
			//加载数据列表
			loadGoodsList();
			
		});	
	
	</script>
  </body>
</html>
