<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${header['X-PJAX']}">
		<jsp:include page="../../include/common_pjax.jsp"/>
		<title>交易记录${seoConfigMap['1'].title }</title>
		<meta name="keywords" content="${seoConfigMap['1'].keywords }">
		<meta name="description" content="${seoConfigMap['1'].description }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/magic-check.css?v=${version }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/jquery.dropdown.css?v=${version }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/deal.css?v=${version }">
   		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/exchange.css?v=${version }">
	    
	    <script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
	    <script type="text/javascript" src="${staticPrefix }/js/jquery.dropdown.js?v=${version }"></script>
	    <script type="text/javascript" src="${staticPrefix }/js/my/transactionLog/transactionLog.js?v=${version }&t=<%=Math.random() %>"></script>
	    
		<div class="c-informright pull-left">
            <p class="ft-16 pb-20">交易记录</p>
            <div style="color:#fff; background: #363e44; margin-bottom: 2px; padding:0 10px; text-align: right;">
             	<div class="form-group pull-cdownlist">
                    <select class="form-control" id="utType" onchange="loadList();">
                    	<option value="">全部查询</option>
                    	<c:forEach items="${typeList }" var="type">
                    		<option value="${type.value }">${type.label }</option>
                    	</c:forEach>
                    </select>
              	</div> 
            </div>
            <div class="table-responsive" style="border: none;">
                <table class="table">
                    <thead class="ft-14">
	                    <tr>
	                        <th>交易时间</th>
	                        <th>类型</th>
	                        <th>金额</th>
	                        <th>信息</th>
	                    </tr>
                    </thead>
                    <tbody id="listUl">
                    </tbody>
                </table>
            </div>

            <nav>
	            <ul class="pagination" id="pagination" style="margin-left: 0;"> </ul>
	        </nav>
        </div>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html xmlns:og="http://ogp.me/ns#" class="alt-layout">
			<head>
			    <jsp:include page="../../include/common.jsp"/>
			    <title>交易记录${seoConfigMap['1'].title }</title>
			    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
				<meta name="description" content="${seoConfigMap['1'].description }">
			    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
			    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/magic-check.css?v=${version }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/jquery.dropdown.css?v=${version }">
			    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/deal.css?v=${version }">
	    		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/exchange.css?v=${version }">
			    
			    <script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
			    <script type="text/javascript" src="${staticPrefix }/js/jquery.dropdown.js?v=${version }"></script>
			    <script type="text/javascript" src="${staticPrefix }/js/my/transactionLog/transactionLog.js?v=${version }"></script>
			</head>
			<body>
				<div class="bodyer"></div>
				<jsp:include page="../../include/header.jsp"/>
				
				<div class="container c-informwrap" id="new-container">
					<jsp:include page="../include/menu.jsp"/>
					<div id="container">
						<div class="c-informright pull-left">
				            <p class="ft-16 pb-20">交易记录</p>
				            <div style="color:#fff; background: #363e44; margin-bottom: 2px; padding:0 10px; text-align: right;">
				             	<div class="form-group pull-cdownlist">
				                    <select class="form-control" id="utType" onchange="loadList();">
				                    	<option value="">全部查询</option>
				                    	<c:forEach items="${typeList }" var="type">
				                    		<option value="${type.value }">${type.label }</option>
				                    	</c:forEach>
				                    </select>
				              	</div> 
				            </div>
				            <div class="table-responsive" style="border: none;">
				                <table class="table">
				                    <thead class="ft-14">
					                    <tr>
					                        <th>交易时间</th>
					                        <th>类型</th>
					                        <th>金额</th>
					                        <th>信息</th>
					                    </tr>
				                    </thead>
				                    <tbody id="listUl">
				                    </tbody>
				                </table>
				            </div>
		
				            <nav>
					            <ul class="pagination" id="pagination" style="margin-left: 0;"> </ul>
					        </nav>
				        </div>
					</div>
				</div>
				
				<jsp:include page="../../include/footer.jsp"/>
			</body>
		</html>
	</c:otherwise>
</c:choose>