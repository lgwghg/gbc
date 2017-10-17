<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/jc/formGb.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/jc/gbComment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/jc/gbUserJc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/jc/gbPankou.js"></script>
<div class="page-header">
	<ul class="nav nav-tabs">
		   <li class="active">
			   	<a href="#form" data-toggle="tab">比赛详情</a>
		   </li>
		   <li>
			   	<a href="#pankoulist" data-toggle="tab">盘口列表</a>
		   </li>
		   <li>
		   		<a href="#userJcList" data-toggle="tab">下注列表</a>
		   </li>
		   <li>
		   		<a href="#commentlist" data-toggle="tab">评论列表</a>
		   </li>
	</ul>
</div>
<div class="tab-content">
	<div class="tab-pane fade in active" id="form">
		<div class="row" style="margin-top:5px;">
			<div class="col-xs-12">
				<form id="gamebattle-form" name="gamebattle-form" class="form-horizontal" role="form" method="post">
					<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
					<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
					<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
					<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
					<input type="hidden" name="id" id="id" value="${entity.id}">
					
					<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="gameId">所属游戏:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					      	  <input type="hidden" name="gameId" id="gameId" value="${ entity.gameId }">
					          <input disabled="disabled"  class="form-control" name="gameName" id="gameName" type="text" 
					           		value="${ entity.game.gameName }"/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="geId">所属赛事:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					          <input type="hidden" name="geId" id="geId" value="${ entity.geId }">
					          <input disabled="disabled" class="form-control" name="eventName" id="eventName" type="text" 
					           		value="${ entity.gevent.eventName }"/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="gbName">对战名称:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input class="form-control" name="gbName" id="gbName" type="text" 
					           		value="${ entity.gbName }" placeholder="对战名称..."/>
					      </div>
				      </div>
					</div>
					
					<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="gameRule">比赛规则:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <select disabled="disabled" class="form-control" name="gameRule" id="gameRule" style="width: 100%">
					         	<c:choose>
				      				<c:when test="${!empty entity}">
				      					<c:forEach var="gr" items="${gameRuleType }">
				      						<c:choose>
				      							<c:when test="${gr.value eq entity.gameRule}">
				      								<option value="${gr.value }" selected="selected">${gr.label }</option>
												</c:when>
												<c:otherwise>
													<option value="${gr.value }">${gr.label}</option>
												</c:otherwise>
				      						</c:choose>
				      					</c:forEach>
				      				</c:when>
				      				<c:otherwise>
				      					<option value="" selected="selected">请选择比赛规则</option>
				      					<c:forEach var="gr" items="${gameRuleType }">
			      							<option value="${gr.value }">${gr.label }</option>
			      						</c:forEach>
				      				</c:otherwise>
				      			</c:choose>
					         </select>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="homeTeam">主场战队:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					          <input type="hidden" name="homeTeam" id="homeTeam" value="${ entity.homeTeam }">
					          <input disabled="disabled"  class="form-control" name="awayTeamName" id="awayTeamName" type="text" 
					           		value="${ entity.ht.teamName }"/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="awayTeam">客场战队:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					          <select class="form-control" name="awayTeam" id="awayTeam" style="width: 100%">
					         	<c:forEach var="gameT" items="${gameTeamList }">
		      						<c:choose>
		      							<c:when test="${gameT.teamId eq entity.awayTeam}">
		      								<option value="${gameT.teamId }" selected="selected">${gameT.teamName }</option>
										</c:when>
										<c:otherwise>
											<option value="${gameT.teamId }">${gameT.teamName }</option>
										</c:otherwise>
		      						</c:choose>
		      					</c:forEach>
					         </select>
					      </div>
				      </div>
					</div>
					
					<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="startTime">开始时间:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input disabled="disabled" id="startTime" name="startTime" type="text" onfocus="this.blur()" maxlength="100" placeholder="开始时间"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});" value="${ entity.startTime }" style="width: 100%"/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="endTime">结束时间:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input disabled="disabled" id="endTime" name="endTime" type="text" onfocus="this.blur()" maxlength="40" placeholder="结束时间"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});" value="${ entity.endTime }" style="width: 100%"/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="gbStatus">状态:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <select disabled="disabled" class="form-control" name="gbStatus" id="gbStatus" style="width: 100%">
								<c:forEach var="gs" items="${isDelete }">
									<option value="${gs.value }" <c:if test="${gs.value eq entity.gbStatus}">selected="selected"</c:if>>${gs.label }</option>
		      					</c:forEach>
					         </select>
					      </div>
				      </div>
				      
					</div>
					
					<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="videoUrl">直播视频地址:</label>
				      <div class="col-sm-10">
					      <div class="clearfix">
					        	1、贴入链接支持形式：通用代码。
								2、目前支持视频平台：优酷、爱奇艺、腾讯视频、土豆、凤凰、新浪、A站、哔哩哔哩弹幕网、虎牙直播、斗鱼直播、腾讯直播
								3、本站视频的尺寸比例为：高=585 宽=360
					      </div>
				      </div>
				      <div class="col-sm-8">
					      <div class="clearfix">
					         <input class="form-control" name="videoUrl" id="videoUrl" type="text" 
					           		value='${entity.videoUrl}' placeholder="直播视频地址..."/>
					      </div>
				      </div>
					</div>
					
					<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="homePrtGold">主战队下注金额:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input disabled="disabled"  class="form-control" name="homePrtGold" id="homePrtGold" type="text" 
					           		value="${ entity.homePrtGold }" placeholder="主战队下注金额..."/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="awayPrtGold">客场战队下注金额</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input disabled="disabled" class="form-control" name="awayPrtGold" id="awayPrtGold" type="text" 
					           		value="${ entity.awayPrtGold }" placeholder="客场战队下注金额"/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="thisGbProfit">当前比赛平台收益:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input disabled="disabled" class="form-control" name="thisGbProfit" id="thisGbProfit" type="text" 
					           		value="${ entity.thisGbProfit }" placeholder=""/>
					      </div>
				      </div>
					</div>
					
					<div class="form-group">
					  <label class="control-label col-sm-1 no-padding-right" for="gbTypeName">比赛现状:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input disabled="disabled"  class="form-control" name="gbTypeName" id="gbTypeName" type="text" 
					           		value="${ entity.gbTypeName }" placeholder="比赛现状..."/>
					      </div>
				      </div>
					</div>
				</form>
				
				<div class="hr hr-dotted"></div>
			</div>
		</div>
	
		<div class="center">
			<button id="btnAdd" type="button" onclick="javascript:$('#gamebattle-form').submit();" class="btn btn-success btn-sm">
			  	<i class="fa fa-user-plus"></i>&nbsp;
			  	  保存
			</button>
			
			<button id="btn" type="button" onclick="webside.common.loadPage('/gameBattle/listUI?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}')" class="btn btn-info btn-sm">
				<i class="fa fa-undo"></i>&nbsp;返回
			</button>
		</div>
	</div>

<!-- 盘口列表 -->

<div class="tab-pane fade" id="pankoulist">
	<div class="page-header">
		<shiro:hasPermission name="pankou:add">
			<button id="btnAdd_p" type="button" onclick="webside.common.addModel('/jc/pankou/addUI?gbId=${entity.id}')" class="btn btn-primary btn-sm">
			  	<i class="fa fa-user-plus"></i>&nbsp;添加
			</button>
		</shiro:hasPermission>
	</div>
	<div class="input-group">
		<label class="search">玩法类型：
	   		<select id="pankou_type_p" style="width: 169px;">
	   			<option value="">不限</option>
	   			<c:forEach items="${pankouType }" var="dict">
	   				<option value="${dict.value }">${dict.label }</option>
	   			</c:forEach>
	   		</select>
	   	</label>
		<label class="search">玩法名称：<input id="pk_name_p" type="text" placeholder="玩法名称"></label>
		<label class="search">盘口现状：
	   		<select id="pk_status_type_p" style="width: 169px;">
	   			<option value="">不限</option>
	   			<c:forEach items="${pkStatusType }" var="dict">
	   				<option value="${dict.value }">${dict.label }</option>
	   			</c:forEach>
	   		</select>
	   	</label>
		<label class="search">盘口状态：
	   		<select id="pk_status_p" style="width: 169px;">
	   			<option value="">不限</option>
	   			<c:forEach items="${isDelete }" var="dict">
	   				<option value="${dict.value }">${dict.label }</option>
	   			</c:forEach>
	   		</select>
	   	</label>
	   	<label class="search">盘口开始时间：
			<input id="beginCreateTime_p" type="text" onfocus="this.blur()" maxlength="20" placeholder="开始时间"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
			<input id="endCreateTime_p" type="text" onfocus="this.blur()" maxlength="20" placeholder="结束时间"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>	
		 </label>
	     <span class="input-group-btn">
	         <button id="btnSearch_p" class="btn btn-primary btn-sm" type="button"> <i class="fa fa-search"></i> 搜索</button>
	     </span>
	</div>
	<div class="row" style="margin-top:5px;">
		<div class="col-xs-12 widget-container-col ui-sortable"
			style="min-height: 127px;">
			<div class="widget-box transparent ui-sortable-handle"
				style="opacity: 1; z-index: 0;">
				<div class="widget-header">
					<h4 class="widget-title lighter">盘口列表</h4>
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
						<input id="pageNum_p" type="hidden" value="${page.pageNum }">
						<input id="pageSize_p" type="hidden" value="${page.pageSize }">
						<input id="orderByColumn_p" type="hidden" value="${page.orderByColumn }">
						<input id="orderByType_p" type="hidden" value="${page.orderByType }">
						<input id="materialId_p" type="hidden" value="${material.id }">
						<div id="dtGridContainer_p" class="dlshouwen-grid-container"></div>
						<div id="dtGridToolBarContainer_p" class="dlshouwen-grid-toolbar-container"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	
<!-- 下注列表 -->
<div class="tab-pane fade" id="userJcList">
	<div class="input-group">
	</div>
	<div class="row" style="margin-top:5px;">
		<div class="col-xs-12 widget-container-col ui-sortable"
			style="min-height: 127px;">
			<!-- #section:custom/widget-box.options.transparent -->
			<div class="widget-box transparent ui-sortable-handle"
				style="opacity: 1; z-index: 0;">
				<div class="widget-header">
					<h4 class="widget-title lighter">赛事下注列表</h4>
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
						<input id="pageNum_u" type="hidden" value="${page.pageNum }">
						<input id="pageSize_u" type="hidden" value="${page.pageSize }">
						<input id="orderByColumn_u" type="hidden" value="${page.orderByColumn }">
						<input id="orderByType_u" type="hidden" value="${page.orderByType }">
						<input id="materialId_u" type="hidden" value="${material.id }">
						<div id="dtGridContainer_u" class="dlshouwen-grid-container"></div>
						<div id="dtGridToolBarContainer_u" class="dlshouwen-grid-toolbar-container"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	
	
<!-- 评论列表 -->
<div class="tab-pane fade" id="commentlist">
	<div class="input-group">
		<label class="search">用户昵称：<input id="nickName_c" type="text" placeholder="用户昵称"></label>
	   	<label class="search">评论内容：<input id="content_c" type="text" placeholder="评论内容"></label>
	   	<label class="search">是否删除：
	   		<select id="isDeletedValue_c" style="width: 169px;">
	   			<option value="">不限</option>
	   			<c:forEach items="${isDeleteType }" var="dict">
	   				<option value="${dict.value }">${dict.label }</option>
	   			</c:forEach>
	   		</select>
	   	</label>
	     <span class="input-group-btn">
	         <button id="btnSearch_c" class="btn btn-primary btn-sm" type="button"> <i class="fa fa-search"></i> 搜索</button>
	     </span>
	</div>
	<div class="row" style="margin-top:5px;">
		<div class="col-xs-12 widget-container-col ui-sortable"
			style="min-height: 127px;">
			<!-- #section:custom/widget-box.options.transparent -->
			<div class="widget-box transparent ui-sortable-handle"
				style="opacity: 1; z-index: 0;">
				<div class="widget-header">
					<h4 class="widget-title lighter">评论列表</h4>
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
						<input id="pageNum_c" type="hidden" value="${page.pageNum }">
						<input id="pageSize_c" type="hidden" value="${page.pageSize }">
						<input id="orderByColumn_c" type="hidden" value="${page.orderByColumn }">
						<input id="orderByType_c" type="hidden" value="${page.orderByType }">
						<input id="materialId_c" type="hidden" value="${material.id }">
						<div id="dtGridContainer_c" class="dlshouwen-grid-container"></div>
						<div id="dtGridToolBarContainer_c" class="dlshouwen-grid-toolbar-container"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>