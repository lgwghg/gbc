<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/common/upload.jsp"%>
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/match/gameEvent/form.js"></script>


<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增游戏赛事
		</c:if>
		<c:if test="${ !empty entity }">
		编辑游戏赛事
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="gameEvent-form" name="gameEvent-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="eventName">赛事名称</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="eventName" id="eventName" type="text" 
			           		value="${ entity.eventName }" placeholder="赛事名称..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="gameId">游戏名称</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select id="gameId" name="gameId" class="form-control">
			   			<c:forEach items="${gameList }" var="game">
			   				<option value="${game.id }" <c:if test="${game.id==entity.gameId }">selected="selected"</c:if>>${game.gameName }</option>
			   			</c:forEach>
			   		</select>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="eventImg">赛事图片</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input name="eventImg" id="eventImg" type="hidden" class="form-control"
		         value="${entity.eventImg }" placeholder="赛事图片..."/>
		         <div id="big_pic_view_div" style="width:120px; height:120px">  
			         <img src="${ctx}/resources/images/nopic.gif" style="width:120px; height:120px" id="big_pic_view">  
			    </div>  
				<div id="big_upload"></div>
			      </div>
			      <div>
		        	<span class="help-inline">图片尺寸320*100</span>
		      	</div>
		      	</div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="startTime">开始时间</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			      	 <input class="form-control" name="startTime" id="startTime" type="text" value="${ entity.startTime }" onfocus="this.blur()" maxlength="20" placeholder="开始时间..."
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="endTime">结束时间</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			      	 <input class="form-control" name="endTime" id="endTime" type="text" value="${ entity.endTime }" onfocus="this.blur()" maxlength="20" placeholder="结束时间..."
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="geStatus">赛事状态</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select id="geStatus" name="geStatus" class="form-control">
			   			<c:forEach items="${geStatus }" var="dict">
			   				<option value="${dict.value }" <c:if test="${dict.value==entity.geStatus }">selected="selected"</c:if>>${dict.label }</option>
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
	<button id="btnAdd" type="button" onclick="javascript:$('#gameEvent-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/match/gameEvent/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>