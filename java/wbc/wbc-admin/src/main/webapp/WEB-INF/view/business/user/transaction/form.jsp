<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/user/transaction/form.js"></script>

<script type="text/javascript">
	$(function() 
	{
	    userTransactionLog.form.validateForm();
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
		<form id="usertransactionlog-form" name="usertransactionlog-form" class="form-horizontal" role="form" method="post">
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
		      <label class="control-label col-sm-1 no-padding-right" for="utType">交易类型 1:竞猜 2：充值 3:兑换 4:推荐好友奖励 5：用户签到 6：充值奖励</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="utType" id="utType" type="text" 
			           		value="${ entity.utType }" placeholder="交易类型 1:竞猜 2：充值 3:兑换 4:推荐好友奖励 5：用户签到 6：充值奖励..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="dataId">业务ID</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="dataId" id="dataId" type="text" 
			           		value="${ entity.dataId }" placeholder="业务ID..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="goldNum">G币数量</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="goldNum" id="goldNum" type="text" 
			           		value="${ entity.goldNum }" placeholder="G币数量..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="utTime">交易时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="utTime" id="utTime" type="text" 
			           		value="${ entity.utTime }" placeholder="交易时间..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="remarks">备注(信息)</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="remarks" id="remarks" type="text" 
			           		value="${ entity.remarks }" placeholder="备注(信息)..."/>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#usertransactionlog-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/userTransactionLog/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>