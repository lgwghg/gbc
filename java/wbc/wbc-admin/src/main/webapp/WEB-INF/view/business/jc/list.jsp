<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/jc/list.js"></script>

<div class="page-header">
	<shiro:hasPermission name="gameBattle:add">
		<button id="btnAdd" type="button" onclick="webside.common.addModel('/gameBattle/addUI')" class="btn btn-primary btn-sm">
		  	<i class="fa fa-user-plus"></i>&nbsp;添加
		</button>
	</shiro:hasPermission>
</div>

<div class="input-group" >
     <label class="search">游戏：
   		<select class="form-control" id="gameId" style="width: 170px;">
   			<option value="">不限</option>
   			<c:forEach items="${gameList }" var="game">
   				<option value="${game.id }">${game.gameName }</option>
   			</c:forEach>
   		</select>
   	</label>
   	
   	<label class="search">赛事：
     	<select id="geId" style="width: 170px;">
   			<option value="">不限</option>
   			<c:forEach items="${gameEventList }" var="g">
   				<option value="${g.id }">${g.eventName }</option>
   			</c:forEach>
   		</select>
     </label>
     
     <label class="search">战队：
   		<select id="teamId" style="width: 169px;">
   			<option value="">不限</option>
   			<c:forEach items="${teamList }" var="team">
   				<option value="${team.id }">${team.teamName }</option>
   			</c:forEach>
   		</select>
   	</label>
   	
   		<label class="search">对战规则：
   		<select id="gameRule" style="width: 169px;">
   			<option value="">不限</option>
   			<c:forEach items="${gameRuleType }" var="dict">
   				<option value="${dict.value }">${dict.label }</option>
   			</c:forEach>
   		</select>
   	</label>
   	
   	<label class="search">比赛现状：
   		<select id="gbType" style="width: 169px;">
   			<option value="">不限</option>
   			<c:forEach items="${gbStatusType }" var="dict">
   				<option value="${dict.value }">${dict.label }</option>
   			</c:forEach>
   		</select>
   	</label>
   	<label class="search">比赛状态：
   		<select id="gbStatus" style="width: 169px;">
   			<option value="">不限</option>
   			<c:forEach items="${isDelete }" var="dict">
   				<option value="${dict.value }">${dict.label }</option>
   			</c:forEach>
   		</select>
   	</label>
     <label class="search">创建时间：
		<input id="beginCreateTime" type="text" onfocus="this.blur()" maxlength="20" placeholder="开始时间"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
		<input id="endCreateTime" type="text" onfocus="this.blur()" maxlength="20" placeholder="结束时间"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>	
	 </label>
     <label class="search">比赛编号：
     	<input id="id" type="text"  placeholder="比赛编号...">
     </label>
     <span class="input-group-btn">
         <button id="btnSearch" class="btn btn-primary btn-sm" type="button"> <i class="fa fa-search"></i> 搜索</button>
     </span>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12 widget-container-col ui-sortable" style="min-height: 127px;">
		<!-- #section:custom/widget-box.options.transparent -->
		<div class="widget-box transparent ui-sortable-handle" style="opacity: 1; z-index: 0;">
			<div class="widget-header">
				<h4 class="widget-title lighter">列表</h4>
				
				<div class="widget-toolbar no-border">
					<a href="#" data-action="fullscreen" class="orange2"> 
						<i class="ace-icon fa fa-arrows-alt"></i>
					</a> 
					<a href="#" data-action="collapse" class="green"> 
						<i class="ace-icon fa fa-chevron-up"></i>
					</a>
				</div>
			</div>

			<div class="widget-body" style="display: block;">
				<div class="widget-main padding-6 no-padding-left no-padding-right">
					<input id="pageNum" type="hidden" value="${page.pageNum}">
					<input id="pageSize" type="hidden" value="${page.pageSize}">
					<input id="orderByColumn" type="hidden" value="${page.orderByColumn}">
					<input id="orderByType" type="hidden" value="${page.orderByType}">
					<div id="dtGridContainer" class="dlshouwen-grid-container"></div>
					<div id="dtGridToolBarContainer" class="dlshouwen-grid-toolbar-container"></div>
				</div>
			</div>
		</div>
	</div>
</div>