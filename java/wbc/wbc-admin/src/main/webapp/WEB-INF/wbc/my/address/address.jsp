<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
 <c:when test="${header['X-PJAX']}">
 	<jsp:include page="../../include/common_pjax.jsp"/>
 	<title>收货地址${seoConfigMap['1'].title }</title>
    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/exchange.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/goods/x-shopping-mall-details.css?v=${version }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/address.css?v=${version }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/jquery.dropdown.css?v=${version }&t=<%=Math.random() %>">
    <script type="text/javascript" src="${staticPrefix }/js/jquery.dropdown.js?v=${version }&t=<%=Math.random() %>"></script>
    <script type="text/javascript" src="${staticPrefix }/js/common/cityselect.js?v=${version }&t=<%=Math.random() %>"></script>
    <script type="text/javascript" src="${staticPrefix }/js/my/address/address.js?v=${version }&t=<%=Math.random() %>"></script>
    
    
    <div class="c-informright pull-left">
           <!-- <p class="ft-16 pb-10">兑换(数据从2016年开始)</p> -->
           <button type="button" class="btn btn-primary btn-raised" data-toggle="modal" data-target="#myModalAddress" onclick="editAddress()">
               <strong>新建收货地址</strong></button>
           <div class="table-responsive" style="border: none;margin-top: 12px;">
               <table class="table">
                   <thead class="ft-14">
                   <tr>
                       <th>收货人</th>
                       <th>所在地区</th>
                       <th>详细地址</th>
                       <!-- <th>邮编</th> -->
                       <th>电话/手机</th>
                       <th>操作</th>
                       <th></th>
                   </tr>
                   </thead>
                   <tbody id = "addressList">
                   
                   </tbody>
               </table>
           </div>
           <nav>
           <ul class="pagination" id="pagination">
               
           </ul>
           </nav>
       </div>
       
 </c:when>
 <c:otherwise>
	<!DOCTYPE html>
	<html lang="en">
	<head>
		<jsp:include page="../../include/common.jsp"/>
	    <title>收货地址${seoConfigMap['1'].title }</title>
	    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
		<meta name="description" content="${seoConfigMap['1'].description }">
	    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
	    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/exchange.css?v=${version }">
	    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/goods/x-shopping-mall-details.css?v=${version }">
	    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/address.css?v=${version }">
	    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/jquery.dropdown.css?v=${version }">
	    <script type="text/javascript" src="${staticPrefix }/js/jquery.dropdown.js?v=${version }"></script>
	    <script type="text/javascript" src="${staticPrefix }/js/common/cityselect.js?v=${version }"></script>
	    <script type="text/javascript" src="${staticPrefix }/js/my/address/address.js?v=${version }"></script>
	</head>
	<body>
	<div class="bodyer"></div>
	<div class="container-fluid" style="margin:0; padding:0;">
	    <!--header S==-->
	    <jsp:include page="../../include/header.jsp"/>
	    <!--header E-->
	    
	    <!-- main -->
	    <div class="container c-informwrap" id="new-container">
	        <!--menu ==S-->
			<jsp:include page="../include/menu.jsp"/>
			<!--menu ==S-->
			<div id="container">
		        <div class="c-informright pull-left">
		            <!-- <p class="ft-16 pb-10">兑换(数据从2016年开始)</p> -->
		            <button type="button" class="btn btn-primary btn-raised" data-toggle="modal" data-target="#myModalAddress" onclick="editAddress()">
		                <strong>新建收货地址</strong></button>
		            <div class="table-responsive" style="border: none;margin-top: 12px;">
		                <table class="table">
		                    <thead class="ft-14">
		                    <tr>
		                        <th>收货人</th>
		                        <th>所在地区</th>
		                        <th>详细地址</th>
		                        <!-- <th>邮编</th> -->
		                        <th>电话/手机</th>
		                        <th>操作</th>
		                        <th></th>
		                    </tr>
		                    </thead>
		                    <tbody id = "addressList">
		                    
		                    </tbody>
		                </table>
		            </div>
		            <nav>
		            <ul class="pagination" id="pagination">
		                
		            </ul>
		            </nav>
		        </div>
		        
			</div>
	    </div>
	    <!--footer ==S-->
		<jsp:include page="../../include/footer.jsp"/>
		<!--footer ==S-->
	</div>
	
	</body>
	</html>
 </c:otherwise>
</c:choose>