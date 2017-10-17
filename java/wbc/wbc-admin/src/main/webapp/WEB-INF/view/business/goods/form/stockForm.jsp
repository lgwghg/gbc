<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript" src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/customer/business/goods/form/stockForm.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${empty goodsStock}">
		新增
		</c:if>
		<c:if test="${!empty goodsStock}">
		编辑
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="goodsstock-form" name="goodsstock-form" class="form-horizontal" role="form" method="post">
			<c:if test="${!empty goodsStock}">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${goodsStock.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="goodsId">所属商品</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <select class="form-control" name="goodsId" id="goodsId" style="width: 100%">
						<option value=""></option>
						<c:forEach items="${goodsList }" var="goods">
			   				<option value="${goods.id }" ${goodsStock.goodsId eq goods.id?"selected":""}>${goods.goodsName }</option>
			   			</c:forEach>
					 </select>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="cardNo">卡号</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="cardNo" id="cardNo" type="text" 
			           		value="${goodsStock.cardNo }" placeholder="卡号..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="cardPass">卡密</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="cardPass" id="cardPass" type="text" 
			           		value="${goodsStock.cardPass }" placeholder="卡密..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="goodsNo">编号</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="goodsNo" id="goodsNo" type="text" 
			           		value="${goodsStock.goodsNo }" placeholder="编号..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="effectiveTime">有效期</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <input class="form-control" name="effectiveTime" id="effectiveTime" type="text" 
			      	 	value="${goodsStock.effectiveTime }" onfocus="this.blur()" maxlength="20" placeholder="有效期..."
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="status">状态</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <select class="form-control" name="status" id="status" style="width: 100%">
						<c:forEach items="${stockStatus }" var="dict">
			   				<option value="${dict.value }" ${goodsStock.status eq dict.value?"selected":""}>${dict.label }</option>
			   			</c:forEach>
					 </select>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#goodsstock-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty goodsStock}">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty goodsStock}">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/goodsStock/listUI.html<c:if test="${!empty goodsStock}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>