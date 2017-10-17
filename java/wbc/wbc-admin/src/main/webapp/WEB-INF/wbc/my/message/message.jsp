<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${header['X-PJAX']}">
		<jsp:include page="../../include/common_pjax.jsp"/>
		<title>你的通知${seoConfigMap['1'].title }</title>
    	<meta name="keywords" content="${seoConfigMap['1'].keywords }">
		<meta name="description" content="${seoConfigMap['1'].description }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
		<script type="text/javascript" src="${staticPrefix }/js/my/message/message.js?v=${version }&t=<%=Math.random() %>"></script>
		<div class="c-informright pull-left">
        	<p class="ft-16 pb-20">你的通知</p>
            <ul class="list-group" id="message-ul"></ul>
            <nav>
            	<ul id="messagePage" class="pagination"></ul>
            </nav>
        </div>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html xmlns:og="http://ogp.me/ns#" class="alt-layout">
		<head>
		    <jsp:include page="../../include/common.jsp"/>
		    <title>你的通知${seoConfigMap['1'].title }</title>
		    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
			<meta name="description" content="${seoConfigMap['1'].description }">
		    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
		    <script type="text/javascript" src="${staticPrefix }/js/my/message/message.js?v=${version }"></script>
		</head>
		<body>
		<div class="container-fluid" style="margin:0; padding:0;">
			<div class="bodyer"></div>
			<!--header S==-->
		    <jsp:include page="../../include/header.jsp"/>
		    <!--header E-->
		    
		    <!-- main S-->
		    <div class="container c-informwrap" id="new-container">
		        <!--menu ==S-->
		    	<jsp:include page="../include/menu.jsp"/>
				<!--menu ==S-->
				<div id="container">
					<div class="c-informright pull-left">
			        	<p class="ft-16 pb-20">你的通知</p>
			            <ul class="list-group" id="message-ul"></ul>
			            <nav>
			            	<ul id="messagePage" class="pagination"></ul>
			            </nav>
			        </div>
				</div>
		    </div>
		    <!-- main E-->
		    
		    <!--footer ==S-->
		    <jsp:include page="../../include/footer.jsp"/>
			<!--footer ==S-->
			
		</div>
		</body>
		</html>
	</c:otherwise>
</c:choose>