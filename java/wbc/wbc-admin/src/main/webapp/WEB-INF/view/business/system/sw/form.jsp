<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/system/sw/form.js"></script>


<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增敏感词
		</c:if>
		<c:if test="${ !empty entity }">
		编辑敏感词
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="sw-form" name="sw-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="content">内容</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="content" id="content" type="text" 
			           		value="${ entity.content }" placeholder="内容..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="contentType">所属类型</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select id="contentType" name="contentType" class="form-control">
			   			<c:forEach items="${contentType }" var="dict">
			   				<option value="${dict.value }" <c:if test="${dict.value==entity.contentType }">selected="selected"</c:if>>${dict.label }</option>
			   			</c:forEach>
			   		</select>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="useType">作用域类型</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select id="useType" name="useType" class="form-control">
			   			<c:forEach items="${useType }" var="dict">
			   				<option value="${dict.value }" <c:if test="${dict.value==entity.useType }">selected="selected"</c:if>>${dict.label }</option>
			   			</c:forEach>
			   		</select>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#sw-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/system/sw/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>