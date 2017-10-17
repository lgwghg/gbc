<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/jc/pankou/addPK.js"></script>

<div class="page-header">
	<h1>
		新增盘口
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="pankou-form" name="pankou-form" class="form-horizontal" role="form" method="post">
			<input type="hidden" id="gbId" name="gbId" value="${gbId }">
			<input type="hidden" id="gbStartTime" value="${gbStartTime }">
			<input type="hidden" id="gameRule" value="${gameRule }">
			
			<div class="form-group">
			  <label class="control-label col-sm-1 no-padding-right" for="inningNum">局数:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select class="form-control" name="inningNum" id="inningNum" style="width: 100%" onChange="clickInningNum()">
			         	<option value="" selected="selected">请选择局数</option>
      					<c:forEach var="dict" begin="1" end="${gameRule }">
     						<option value="${dict}">第${dict}局</option>
     					</c:forEach>
			         </select>
			      </div>
		      </div>
				
		      <label class="control-label col-sm-1 no-padding-right" for="pankouType">玩法类型:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select class="form-control" name="pankouType" id="pankouType" style="width: 100%" onChange="clickType()">
			         	<option value="" selected="selected">请选择玩法类型</option>
      					<c:forEach var="dict" items="${pankouTypeList }">
      						<c:if test="${dict.value != '0' }">
      							<option value="${dict.value }">${dict.label }</option>
      						</c:if>
     					</c:forEach>
			         </select>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="pkName">玩法名称:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="pkName" id="pkName" type="text" 
			           		value="" placeholder="玩法名称...一血 十杀"/>
			      </div>
		      </div>
			</div>
			
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="pkStartTime">开始时间:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input id="pkStartTime" name="pkStartTime" type="text" onfocus="this.blur()" maxlength="100" placeholder="开始时间"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,minDate:'${gbStartTime}'});" style="width: 100%"/>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="pkStatus">状态:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <select class="form-control" name="pkStatus" id="pkStatus" style="width: 100%">
						<c:forEach var="dict" items="${isDelete }">
		      				<option value="${dict.value }">${dict.label }</option>
		      			</c:forEach>
			         </select>
			      </div>
		      </div>
			</div>
			
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="pkHomeRule">比赛赔率（主战队）:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="pkHomeRule" id="pkHomeRule" type="text" 
			           		value="1.95" placeholder="比赛赔率（主战队）..."/>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="pkAwayRule">比赛赔率（客场战队）:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			         <input class="form-control" name="pkAwayRule" id="pkAwayRule" type="text" 
			           		value="1.95" placeholder="比赛赔率（副战队）..."/>
			      </div>
		      </div>
			</div>
			
			<div class="form-group" id="pkRangFenTeam-group" style="display: none;">
		      <label class="control-label col-sm-1 no-padding-right" for="pkRangFenTeam">让分战队:</label>
		      <div class="col-sm-2">
			      <div class="clearfix">
			          <select class="form-control" name="pkRangFenTeam" id="pkRangFenTeam" style="width: 100%">
			         	<option value="" selected="selected">请选择让分战队</option>
      					<c:forEach var="jt" items="${jcTameType }">
     						<option value="${jt.value }">${jt.label }</option>
     					</c:forEach>
			         </select>
			      </div>
		      </div>
		      
		      <label class="control-label col-sm-1 no-padding-right" for="pkRangfenNum">让分数:</label>
		      <div class="col-sm-2">
			     <div class="clearfix">
			        <input class="form-control" name="pkRangfenNum" id="pkRangfenNum" type="text" 
			           	value="" placeholder="让分数..."/>
			     </div>
		     </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#pankou-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/gameBattle/editUI?id=${gbId}&page=1&rows=10&sidx=&sord=0')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>