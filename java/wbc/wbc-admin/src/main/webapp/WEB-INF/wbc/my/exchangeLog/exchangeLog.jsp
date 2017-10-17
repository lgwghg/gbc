<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${header['X-PJAX']}">
		<jsp:include page="../../include/common_pjax.jsp"/>
		<title>兑换记录${seoConfigMap['1'].title }</title>
		<meta name="keywords" content="${seoConfigMap['1'].keywords }">
		<meta name="description" content="${seoConfigMap['1'].description }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/magic-check.css?v=${version }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/jquery.dropdown.css?v=${version }">
   		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/exchange.css?v=${version }">
	    
	    <script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
	    <script type="text/javascript" src="${staticPrefix }/js/jquery.dropdown.js?v=${version }"></script>
	    <script type="text/javascript" src="${staticPrefix }/js/my/exchangeLog/exchangeLog.js?v=${version }&t=<%=Math.random() %>"></script>
	    
		<div class="c-informright pull-left">
			<ul style="overflow: hidden; padding-left: 10px;">
	        	<li class="exrecord activecolor" style="cursor:pointer">商品兑换记录</li>
	           	<li class="exrecord" style="cursor:pointer">CD-KEY兑换记录</li>
	        </ul>
			<div class="item-exmall exhide exshow" style="margin-top: 16px;">
	            <div style="color:#fff; background: #363e44; margin-bottom: 2px; padding:0 10px; text-align: right;">
	            	<input id="conserve" type="checkbox" class="magic-checkbox" name="all" value=""/>
             				<label class="hold" for="conserve" style=" float: left; margin-top: 15px;">全选</label> 
<!-- 				             	<button type="button" class="btn c-salemall" onclick="salesAll()">全部出售 </button>    -->
	             	<div class="form-group pull-cdownlist">
	                    <select class="form-control" id="statusId" onchange="loadList();">
	                      	<option value="">全部查询</option>
	                      	<option value="1">未领取</option>
	                      	<option value="2">已领取</option>
	                      	<option value="3">出售中</option>
	                      	<option value="4">已出售</option>
	                      	<option value="5">发货中</option>
                   			<option value="6">已发货</option>
	                    </select>
	              	</div> 
	            </div> 
	            <ul class="c-changelist ft-14" id="listUl">
	                
	            </ul>
	            <nav>
		            <ul class="pagination" id="pagination" style="margin-left: 0;"> </ul>
		        </nav>
			</div>
			
			<div class="table-responsive cdkey exhide item-exmall">
				<table class="table" style="margin-bottom: 2px;">
	                <thead>
	                    <tr>
		                    <th style="width:25%;text-align: center;"><span>兑换时间</span></th>
		                    <th style="width:25%;text-align: center;"><span>CD-KEY</span></th>
		                    <th style="text-align: center;"><span>备注</span></th>
	                    </tr>
	                </thead>
	            </table>
				<table class="table" style=" background: #2d3137;">
					<tbody id="cdkBody">
					</tbody>
				</table>
				<nav>
		            <ul class="pagination" id="cdk_pagination" style="margin-left: 0;"> </ul>
		        </nav>
			</div>
        </div>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html xmlns:og="http://ogp.me/ns#" class="alt-layout">
			<head>
			    <jsp:include page="../../include/common.jsp"/>
			    <title>兑换记录${seoConfigMap['1'].title }</title>
			    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
				<meta name="description" content="${seoConfigMap['1'].description }">
			    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
			    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/magic-check.css?v=${version }">
			    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/jquery.dropdown.css?v=${version }">
	    		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/exchange.css?v=${version }">
			    
			    <script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
			    <script type="text/javascript" src="${staticPrefix }/js/my/exchangeLog/exchangeLog.js?v=${version }"></script>
			    <script type="text/javascript" src="${staticPrefix }/js/jquery.dropdown.js?v=${version }"></script>
			</head>
			<body>
				<div class="bodyer"></div>
				<jsp:include page="../../include/header.jsp"/>
				
				<div class="container c-informwrap" id="new-container">
					<jsp:include page="../include/menu.jsp"/>
					<div id="container">
						<div class="c-informright pull-left">
							<ul style="overflow: hidden; padding-left: 10px;">
					        	<li class="exrecord activecolor" style="cursor:pointer">商品兑换记录</li>
					           	<li class="exrecord" style="cursor:pointer">CD-KEY兑换记录</li>
					        </ul>
							<div class="item-exmall exhide exshow" style="margin-top: 16px;">
					            <div style="color:#fff; background: #363e44; margin-bottom: 2px; padding:0 10px; text-align: right;">
					            	<input id="conserve" type="checkbox" class="magic-checkbox" name="all" value=""/>
	                				<label class="hold" for="conserve" style=" float: left; margin-top: 15px;">全选</label> 
	<!-- 				             	<button type="button" class="btn c-salemall" onclick="salesAll()">全部出售 </button>    -->
					             	<div class="form-group pull-cdownlist">
					                    <select class="form-control" id="statusId" onchange="loadList();">
					                      	<option value="">全部查询</option>
					                      	<option value="1">未领取</option>
					                      	<option value="2">已领取</option>
					                      	<option value="3">出售中</option>
					                      	<option value="4">已出售</option>
					                      	<option value="5">发货中</option>
	                      					<option value="6">已发货</option>
					                    </select>
					              	</div> 
					            </div> 
					            <ul class="c-changelist ft-14" id="listUl">
					                
					            </ul>
					            <nav>
						            <ul class="pagination" id="pagination" style="margin-left: 0;"> </ul>
						        </nav>
							</div>
							
							<div class="table-responsive cdkey exhide item-exmall">
								<table class="table" style="margin-bottom: 2px;">
					                <thead>
					                    <tr>
						                    <th style="width:25%;text-align: center;"><span>兑换时间</span></th>
						                    <th style="width:25%;text-align: center;"><span>CD-KEY</span></th>
						                    <th style="text-align: center;"><span>备注</span></th>
					                    </tr>
					                </thead>
					            </table>
								<table class="table" style=" background: #2d3137;">
									<tbody id="cdkBody">
									</tbody>
								</table>
								<nav>
						            <ul class="pagination" id="cdk_pagination" style="margin-left: 0;"> </ul>
						        </nav>
							</div>
				        </div>
					</div>
				</div>
				
				<jsp:include page="../../include/footer.jsp"/>
			</body>
		</html>
	</c:otherwise>
</c:choose>