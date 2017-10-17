<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/user/exchange/form.js"></script>

<script type="text/javascript">
	$(function() 
	{
	    userExchangeLog.form.validateForm();
	});
</script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增
		</c:if>
		<c:if test="${ !empty entity }">
		编辑
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="userexchangelog-form" name="userexchangelog-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="id">唯一标识</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="id" id="id" type="text" 
			           		value="${ entity.id }" placeholder="唯一标识..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="stockId">库存ID</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="stockId" id="stockId" type="text" 
			           		value="${ entity.stockId }" placeholder="库存ID..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="userId">用户ID</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="userId" id="userId" type="text" 
			           		value="${ entity.userId }" placeholder="用户ID..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="exchangeTime">兑换时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="exchangeTime" id="exchangeTime" type="text" 
			           		value="${ entity.exchangeTime }" placeholder="兑换时间..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="exchangeGold">兑换金额</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="exchangeGold" id="exchangeGold" type="text" 
			           		value="${ entity.exchangeGold }" placeholder="兑换金额..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="exchangeStatus">兑换状态 默认 1  1：有效 0：无效</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="exchangeStatus" id="exchangeStatus" type="text" 
			           		value="${ entity.exchangeStatus }" placeholder="兑换状态 默认 1  1：有效 0：无效..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="ueOrderNo">数字订单编号</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="ueOrderNo" id="ueOrderNo" type="text" 
			           		value="${ entity.ueOrderNo }" placeholder="数字订单编号..."/>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#userexchangelog-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/userExchangeLog/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>