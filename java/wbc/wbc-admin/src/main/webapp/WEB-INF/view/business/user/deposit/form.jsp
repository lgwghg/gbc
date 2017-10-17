<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/user/deposit/form.js"></script>

<script type="text/javascript">
	$(function() 
	{
	    userDepositLog.form.validateForm();
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
		<form id="userdepositlog-form" name="userdepositlog-form" class="form-horizontal" role="form" method="post">
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
		      <label class="control-label col-sm-1 no-padding-right" for="userId">用户ID</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="userId" id="userId" type="text" 
			           		value="${ entity.userId }" placeholder="用户ID..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="orderName">订单标题</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="orderName" id="orderName" type="text" 
			           		value="${ entity.orderName }" placeholder="订单标题..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="type">充值平台 1：支付宝，2：微信</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="type" id="type" type="text" 
			           		value="${ entity.type }" placeholder="充值平台 1：支付宝，2：微信..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="orderNo">交易订单编号</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="orderNo" id="orderNo" type="text" 
			           		value="${ entity.orderNo }" placeholder="交易订单编号..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="udGold">充值金额</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="udGold" id="udGold" type="text" 
			           		value="${ entity.udGold }" placeholder="充值金额..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="orderTime">下单时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="orderTime" id="orderTime" type="text" 
			           		value="${ entity.orderTime }" placeholder="下单时间..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="payTime">支付时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="payTime" id="payTime" type="text" 
			           		value="${ entity.payTime }" placeholder="支付时间..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="isPaySuccess">是否支付成功 1:是 0：否　，默认初始化未0</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="isPaySuccess" id="isPaySuccess" type="text" 
			           		value="${ entity.isPaySuccess }" placeholder="是否支付成功 1:是 0：否　，默认初始化未0..."/>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#userdepositlog-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/userDepositLog/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>