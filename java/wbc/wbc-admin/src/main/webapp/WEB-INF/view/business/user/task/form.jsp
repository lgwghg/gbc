<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/common/upload.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/user/task/form.js"></script>

<script type="text/javascript">
	$(function() 
	{
	    task.form.validateForm();
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
		<form id="task-form" name="task-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="taskName">任务名称：</label>
		      <div class="col-sm-3">
			      <div class="clearfix">
			         <input class="form-control" name="taskName" id="taskName" type="text" 
			           		value="${ entity.taskName }" placeholder="任务名称..."/>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="type">类型：</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select name="type" id="type" >
			         	<option value="0" <c:if test="${not empty entity.type && entity.type == 0}">selected="true"</c:if>>每日任务</option>
			         	<option value="1" <c:if test="${not empty entity.type && entity.type == 1}">selected="true"</c:if>>一次性任务</option>
			         </select>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="taskType">任务类型：</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select name="taskType" id="taskType">
			         	<c:forEach items="${dictList }" var="dict">
			         		<option value="${dict.value }" <c:if test="${not empty entity.taskType && entity.taskType == dict.value}">selected="true"</c:if>>${dict.label }</option>
			         	</c:forEach>
			         </select>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="num">任务要完成的次数：</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="num" id="num" type="text" 
			           		value="${ entity.num }" placeholder="任务需要完成的次数..."/>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="gold">完成任务奖励G币：</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="gold" id="gold" type="text" 
			           		value="${ entity.gold }" placeholder="完成任务奖励的G币..."/>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="state">状态：</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select name="state" id="state">
			         	<option value="0" <c:if test="${not empty entity.state && 0 == entity.state}">selected="true"</c:if>>有效</option>
			         	<option value="1" <c:if test="${not empty entity.state && 1 == entity.state}">selected="true"</c:if>>无效</option>
			         </select>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="image">任务图片：</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input name="image" id="image" type="hidden" class="form-control" value="${entity.image }" />
			         <div id="image_view_div" style="width:100px; height:100px">  
				         <img src="${ctx}/resources/images/nopic.gif" style="width:100px; height:100px" id="image_view">  
				    </div>  
					<div id="image_upload"></div>
			      </div>
			      <div>
		        	<span class="help-inline">图片尺寸100*100</span>
		      	</div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="description">任务介绍：</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="description" id="description" type="text" 
			           		value="${ entity.description }" placeholder="任务介绍..."/>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#task-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/task/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>