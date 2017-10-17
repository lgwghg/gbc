<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript" src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/select2/zh-CN.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/customer/user/cdkey/form.js"></script>

<script type="text/javascript">
	$(function() 
	{
	    cdKey.form.validateForm();
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
		<form id="cdkey-form" name="cdkey-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			<div class="form-group" >
		      <label class="control-label col-sm-2 no-padding-right" for="type">cdkey类型</label>
		      <div class="col-sm-4">
			      <div class="clearfix">
			         <select class="form-control" name="type" id="type" value="${ entity.type }" <c:if test="${not empty entity.type}">disabled=true</c:if> onchange="selectType(this.value)">
			         	<option value="2" <c:if test="${not empty entity.type and entity.type==2}">selected="true"</c:if>>G币</option>
			         	<option value="1" <c:if test="${not empty entity.type and entity.type==1}">selected="true"</c:if>>金币</option>
			         	<option value="0" <c:if test="${not empty entity.type and entity.type==0}">selected="true"</c:if>>商品</option>
			         </select>
			      </div>
		      </div>
			</div>
			<div class="form-group" id="goodsIdDiv" <c:if test="${empty entity.id }">style="display:none;"</c:if><c:if test="${not empty entity.type and entity.type != 0}">style="display:none;"</c:if>>
		      <label class="control-label col-sm-2 no-padding-right" for="goodsId">商品</label>
		      <div class="col-sm-4">
			      <div class="clearfix">
			         <select class="form-control" name="goodsId" id="goodsId" value="${ entity.goodsId }" <c:if test="${not empty entity}">disabled=true</c:if>>
			         	<option value=""></option>
			         	<c:forEach items="${goodsList }" var="goods">
			         	<option value="${goods.id }" ${entity.goodsId eq goods.id?"selected":""}>${goods.goodsName }</option>
			         	</c:forEach>
			         </select>
			      </div>
		      </div>
			</div>
			<div class="form-group" id="goldDiv" <c:if test="${not empty entity.type and entity.type == 0}">style="display:none;"</c:if>>
		      <label class="control-label col-sm-2 no-padding-right" for="gold">币值</label>
		      <div class="col-sm-4">
			      <div class="clearfix">
			         <input class="form-control" name="gold" id="gold" type="text" 
			           		value="${ entity.gold }" placeholder="币值..." <c:if test="${not empty entity.id}">disabled=true</c:if>/>
			      </div>
		      </div>
		      
			</div>
			<div class="form-group" >
			  <label class="control-label col-sm-2 no-padding-right" for="codeNum">兑换码数量</label>
		      <div class="col-sm-4">
			      <div class="clearfix">
			         <input class="form-control" name="codeNum" id="codeNum" type="text" 
			           		value="" placeholder="cdkey兑换码数量" <c:if test="${not empty entity.id}">disabled=true</c:if>/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-2 no-padding-right" for="startTime">开始时间</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="startTime" id="startTime" type="text" 
			           		value="${ entity.startTime }" placeholder="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" readonly/>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-2 no-padding-right" for="endTime">结束时间</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="endTime" id="endTime" type="text" 
			           		value="${ entity.endTime }" placeholder="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" readonly/>
			      </div>
		      </div>
			</div>
			<div class="form-group" id="userMobileDiv">
		      <label class="control-label col-sm-2 no-padding-right" for="userId">发放到用户</label>
		      <div class="col-sm-4">
			      <div class="clearfix">
			      	 <input class="form-control" name="userMobile" id="userMobile" type="text" onkeyup="checkUserExist()"  value="" placeholder="请输入用户手机号" <c:if test="${not empty entity.userId}">disabled=true</c:if>/>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#cdkey-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/cdkey/listUI<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>