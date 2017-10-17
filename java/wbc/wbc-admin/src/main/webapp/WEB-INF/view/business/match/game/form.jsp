<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/common/upload.jsp"%>
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/match/game/form.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${ empty entity }">
		新增游戏
		</c:if>
		<c:if test="${ !empty entity }">
		编辑游戏
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="game-form" name="game-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="gameName">游戏名称</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="gameName" id="gameName" type="text" 
			           		value="${ entity.gameName }" placeholder="游戏名称..."/>
			      </div>
		      </div>
		      <div>
		         <span class="help-inline">*</span>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="englishName">英文简称</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="englishName" id="englishName" type="text" 
			           		value="${ entity.englishName }" placeholder="英文简称..."/>
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
		      <label class="control-label col-sm-1 no-padding-right" for="bigImg">大图片</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input name="bigImg" id="bigImg" type="hidden" class="form-control"
		         		value="${entity.bigImg }" placeholder="大图片..."/>
			         <div id="big_pic_view_div" style="width:120px; height:120px">  
				         <img src="${ctx}/resources/images/nopic.gif" style="width:120px; height:120px" id="big_pic_view">  
				    </div>  
					<div id="big_upload"></div>
		      	</div>
			    <div>
		        	<span class="help-inline">图片尺寸273*135</span>
		      	</div>
		    </div>
		      
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="littleImg">小图片</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input name="littleImg" id="littleImg" type="hidden" class="form-control"
		         value="${entity.littleImg }" placeholder="小图片..."/>
		         <div id="little_pic_view_div" style="width:120px; height:120px">  
			         <img src="${ctx}/resources/images/nopic.gif" style="width:120px; height:120px" id="little_pic_view">  
			    </div>  
				<div id="little_upload"></div>
			      </div>
			      <div>
		        	<span class="help-inline">图片尺寸100*100</span>
		      	</div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="bgImg">背景图片</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input name="bgImg" id="bgImg" type="hidden" class="form-control"
		         value="${entity.bgImg }" placeholder="背景图片..."/>
		         <div id="bg_pic_view_div" style="width:120px; height:120px">  
			         <img src="${ctx}/resources/images/nopic.gif" style="width:120px; height:120px" id="bg_pic_view">  
			    </div>  
				<div id="bg_upload"></div>
			      </div>
			      <div>
		        	<span class="help-inline">图片尺寸1920*1080</span>
		      	</div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="gameStatus">游戏状态</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select id="gameStatus" name="gameStatus" class="form-control">
			   			<c:forEach items="${gameStatus }" var="dict">
			   				<option value="${dict.value }" <c:if test="${dict.value==entity.gameStatus }">selected="selected"</c:if>>${dict.label }</option>
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
	<button id="btnAdd" type="button" onclick="javascript:$('#game-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/match/game/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-primary btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>
