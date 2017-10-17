<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/user/withdraw/form.js"></script>

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
				<input type="hidden" name="userId" id="userId" value="${entity.userId}">
				<input type="hidden" name="udGold" id="udGold" value="${entity.udGold}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="nickName">用户</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" id="nickName" type="text" value="${entity.nickName }" readonly="readonly"/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="orderName">订单标题</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" id="orderName" type="text" 
			           		value="${ entity.orderName }" readonly="readonly"/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="udGold2">提现金额</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" id="udGold2" type="text" 
			           		value="${ entity.udGold }" readonly="readonly"/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="orderTime">下单时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" id="orderTime" type="text" 
			           		value="${ entity.orderTime }" readonly="readonly"/>
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
		      <label class="control-label col-sm-1 no-padding-right" for="isPaySuccess">状态</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <select class="form-control" name="isPaySuccess" id="isPaySuccess" style="width: 100%">
		   				<option value="0" ${entity.isPaySuccess eq 0?"selected":""}>提现申请</option>
		   				<option value="1" ${entity.isPaySuccess eq 1?"selected":""}>提现到账</option>
		   				<option value="-1" ${entity.isPaySuccess eq -1?"selected":""}>提现失败</option>
					 </select>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="remarks">备注</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="remarks" id="remarks" type="text" 
			           		value="${ entity.remarks }" placeholder="备注..."/>
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
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/userDepositLog/withdrawUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>