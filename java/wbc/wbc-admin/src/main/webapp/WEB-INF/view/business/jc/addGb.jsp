<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/jc/addGb.js"></script>

<div class="page-header">
	<h1>
		新增比赛对战
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="gamebattle-form" name="gamebattle-form" class="form-horizontal" role="form" method="post">
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="gameId">所属游戏:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select class="form-control" name="gameId" id="gameId" style="width: 100%" onChange="clickGame()">
			         	<option value="" selected="selected">请选择游戏</option>
      					<c:forEach var="gl" items="${gameList }">
     						<option value="${gl.id }">${gl.gameName }</option>
     					</c:forEach>
			         </select>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="geId">所属赛事:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			          <select class="form-control" name="geId" id="geId" style="width: 100%" onChange="clickGeName()">
			          		<option value="" selected="selected">请选择赛事</option>
			          </select>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="gbName">对战名称:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="gbName" id="gbName" type="text" 
			           		value="" placeholder="对战名称...LGD VS EDG"/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="gameRule">比赛规则:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select class="form-control" name="gameRule" id="gameRule" style="width: 100%">
						<option value="" selected="selected">请选择比赛规则</option>
      					<c:forEach var="gr" items="${gameRuleType }">
     						<option value="${gr.value }">${gr.label }</option>
     					</c:forEach>
			         </select>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="homeTeam">主场战队:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			          <select class="form-control" name="homeTeam" id="homeTeam" style="width: 100%" onChange="clickHomeTeam()">
			          		<option value="" selected="selected">请选择主场战队</option>
			          </select>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="awayTeam">客场战队:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			        	<select class="form-control" name="awayTeam" id="awayTeam" style="width: 100%" onChange="clickAwayTeam()" >
			          		<option value="" selected="selected">请选择客场战队</option>
			          </select>
			      </div>
		      </div>
			</div>
			
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="startTime">开始时间:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input id="startTime" name="startTime" type="text" onfocus="this.blur()" maxlength="100" placeholder="开始时间"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});" style="width: 100%"/>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="gbStatus">状态:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select class="form-control" name="gbStatus" id="gbStatus" style="width: 100%">
						<c:forEach var="gs" items="${isDelete }">
		      				<option value="${gs.value }">${gs.label }</option>
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
					3、本站视频的尺寸比例为：高=585宽=360
			      </div>
		      </div>
		      <div class="col-sm-8">
			      <div class="clearfix">
			         <input class="form-control" name="videoUrl" id="videoUrl" type="text" placeholder="直播视频地址..."/>
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
	  	添加
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/gameBattle/listUI')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>