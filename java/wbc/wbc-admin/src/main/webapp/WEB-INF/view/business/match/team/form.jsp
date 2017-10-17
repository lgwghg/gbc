<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/common/upload.jsp"%>
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/match/team/form.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增战队
		</c:if>
		<c:if test="${ !empty entity }">
		编辑战队
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="team-form" name="team-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="teamName">战队名称</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="teamName" id="teamName" type="text" 
			           		value="${ entity.teamName }" placeholder="战队名称..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="sortNum">排序</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="sortNum" id="sortNum" type="text" 
			           		value="${ entity.sortNum }" placeholder="排序..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="teamIcon">战队图标</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input name="teamIcon" id="teamIcon" type="hidden" class="form-control"
		         value="${entity.teamIcon }" placeholder="战队图标..."/>
		         <div id="little_pic_view_div" style="width:120px; height:120px">  
			         <img src="${ctx}/resources/images/nopic.gif" style="width:120px; height:120px" id="little_pic_view">  
			    </div>  
				<div id="little_upload"></div>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="gameIds">关联游戏</label>
		      <div class="col-sm-3">
			      <div class="clearfix">
			      	 <c:choose>
			      	 	<c:when test="${ !empty entity }">
			      	 		<c:forEach items="${gameList }" var="game" varStatus="ind">
			      	 			<input style="margin-top: 10px;" id="game-checkbox-${ind.index }" name="gameId" type="checkbox" value="${game.id }"
			      	 			<c:forEach items="${teamGameList }" var="teamGame">
			      	 				<c:if test="${teamGame.gameId==game.id}"> checked="checked" </c:if>
			      	 			</c:forEach>
				      		 	><label for="game-checkbox-${ind.index }">${game.gameName }</label>
				      	 	</c:forEach>
			      	 	</c:when>
			      	 	<c:otherwise>
				      	 	<c:forEach items="${gameList }" var="game" varStatus="ind">
				      		 	<input style="margin-top: 10px;" id="game-checkbox-${ind.index }" name="gameId" type="checkbox" value="${game.id }"><label for="game-checkbox-${ind.index }">${game.gameName }</label>
				      	 	</c:forEach>
			      	 	</c:otherwise>
			      	 </c:choose>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="teamDec">战队说明</label>
		      <div class="col-sm-3">
			      <div class="clearfix">
			      	 <textarea class="form-control" id="teamDec" name="teamDec" maxlength="512" rows="4" placeholder="战队说明...">${entity.teamDec }</textarea>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="teamStatus">战队状态</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select id="teamStatus" name="teamStatus" class="form-control">
			   			<c:forEach items="${teamStatus }" var="dict">
			   				<option value="${dict.value }" <c:if test="${dict.value==entity.teamStatus }">selected="selected"</c:if>>${dict.label }</option>
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

<div class="form-button">
	<button id="btnAdd" type="button" onclick="javascript:$('#team-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/match/team/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-primary btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>