<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/userjc/form.js"></script>

<script type="text/javascript">
	$(function() 
	{
	    userJc.form.validateForm();
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
		<form id="userjc-form" name="userjc-form" class="form-horizontal" role="form" method="post">
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
		      <label class="control-label col-sm-1 no-padding-right" for="gbId">比赛对战ID</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="gbId" id="gbId" type="text" 
			           		value="${ entity.gbId }" placeholder="比赛对战ID..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="jcGold">竞猜G币</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="jcGold" id="jcGold" type="text" 
			           		value="${ entity.jcGold }" placeholder="竞猜G币..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="jcTeamType">竞猜战队分类 1:主战队，2：客场战队</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="jcTeamType" id="jcTeamType" type="text" 
			           		value="${ entity.jcTeamType }" placeholder="竞猜战队分类 1:主战队，2：客场战队..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="gameResult">比赛结果 默认2     0:输 1：赢  2 ：进行中 3：已取消</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="gameResult" id="gameResult" type="text" 
			           		value="${ entity.gameResult }" placeholder="比赛结果 默认2     0:输 1：赢  2 ：进行中 3：已取消..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="victoryGoldNum">用户赢得金币数量</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="victoryGoldNum" id="victoryGoldNum" type="text" 
			           		value="${ entity.victoryGoldNum }" placeholder="用户赢得金币数量..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="jcTime">竞猜时间</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="jcTime" id="jcTime" type="text" 
			           		value="${ entity.jcTime }" placeholder="竞猜时间..."/>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#userjc-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/userJc/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>