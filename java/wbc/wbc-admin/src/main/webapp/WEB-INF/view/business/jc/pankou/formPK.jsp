<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/jc/pankou/formPK.js"></script>
<div class="page-header">
	<h1>
		盘口详情
	</h1>
</div>
<div class="tab-content">
	<div class="tab-pane fade in active" id="form">
		<div class="row" style="margin-top:5px;">
			<div class="col-xs-12">
				<form id="pankou-form" name="pankou-form" class="form-horizontal" role="form" method="post">
					<c:if test="${ !empty entity }">
						<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
						<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
						<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
						<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
						<input type="hidden" name="id" id="id" value="${entity.id}">
					</c:if>
					
					<input type="hidden" id="gbId" name="gbId" value="${entity.gbId }">
					<input type="hidden" id="homeTeamId" name="homeTeamId" value="${entity.homeTeamId }">
					<input type="hidden" id="awayTeamId" name="awayTeamId" value="${entity.awayTeamId }">
					<input type="hidden" id="gbStartTime" value="${gbStartTime }">
					<input type="hidden" id="gameRule" value="${gameRule }">
					<input type="hidden" id="rangFenTeam" value="${entity.pkRangFenTeam}">
			
					<div class="form-group">
					  <label class="control-label col-sm-1 no-padding-right" for="inningNum">局数:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					      <c:choose>
					      	 	<c:when test="${entity.inningNum == '0' }">
					      	 		<select disabled="disabled" class="form-control" name="inningNum" id="inningNum" style="width: 100%">
					         			<option value="${entity.inningNum}" selected="selected">整场比赛</option>
					         		</select>
					      	 	</c:when>
					      	 	<c:otherwise>
					      	 		<select class="form-control" name="inningNum" id="inningNum" style="width: 100%" onChange="clickInningNum()">
						         		<c:forEach var="dict" begin="1" end="${gameRule }">
							         		<option value="${dict}" <c:if test="${dict eq entity.inningNum}">selected="selected"</c:if>>第${dict}局</option>
				     					</c:forEach>
					         		</select>
					      	 	</c:otherwise>
				      	  </c:choose>
					      </div>
				      </div>
						
				      <label class="control-label col-sm-1 no-padding-right" for="pankouType">玩法类型:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					      	 <c:choose>
					      	 	<c:when test="${entity.pankouType == '0' }">
					      	 		<select disabled="disabled" class="form-control" name="pankouType" id="pankouType" style="width: 100%">
						      	 		<c:forEach var="dict" items="${pankouTypeList }">
				     						<c:if test="${dict.value == '0'}">
				     							<option value="${dict.value }"selected="selected">${dict.label }</option>
				     						</c:if>
				     					</c:forEach>
					      	 		</select>
					      	 	</c:when>
					      	 	<c:otherwise>
					      	 		<select class="form-control" name="pankouType" id="pankouType" style="width: 100%" onChange="clickType()">
				      					<c:forEach var="dict" items="${pankouTypeList }">
				      						<c:if test="${dict.value != '0'}">
				     							<option value="${dict.value }" <c:if test="${dict.value eq entity.pankouType}">selected="selected"</c:if>>${dict.label }</option>
				     						</c:if>
				     					</c:forEach>
							        </select>
					      	 	</c:otherwise>
				      	    </c:choose>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="pkName">玩法名称:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input class="form-control" name="pkName" id="pkName" type="text" 
					           		value="${entity.pkName }" placeholder="玩法名称...一血 十杀"/>
					      </div>
				      </div>
					</div>
			
					<div class="form-group">
				      <label class="control-label col-sm-1 no-padding-right" for="pkStartTime">开始时间:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input id="pkStartTime" name="pkStartTime" type="text" onfocus="this.blur()" maxlength="100" placeholder="开始时间"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,minDate:'${gbStartTime}'});" value="${entity.pkStartTime }" style="width: 100%"/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="pkEndTime">结束时间:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input id="pkEndTime" name="pkEndTime" type="text" onfocus="this.blur()" maxlength="40" placeholder="结束时间"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,minDate:'${entity.pkStartTime}'});" value="${entity.pkEndTime }" style="width: 100%"/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="pkStatus">状态:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <select class="form-control" name="pkStatus" id="pkStatus" style="width: 100%">
								<c:forEach var="dict" items="${isDelete }">
				      				<option value="${dict.value }" <c:if test="${dict.value eq entity.pkStatus}">selected="selected"</c:if>>${dict.label }</option>
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
					           		value="${entity.pkHomeRule }" placeholder="比赛赔率（主战队）..."/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="pkAwayRule">比赛赔率（客场战队）:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					         <input class="form-control" name="pkAwayRule" id="pkAwayRule" type="text" 
					           		value="${entity.pkAwayRule }" placeholder="比赛赔率（副战队）..."/>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="pkVictory">胜方战队</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
				        	<select class="form-control" name="pkVictory" id="pkVictory" style="width: 100%">
				        		<c:if test="${!empty entity.pkVictory}">
				        			 <c:if test="${entity.pkVictory == homeTeam}">
				        			 	<option value="${ homeTeam }" selected="selected">${ homeTeamName }</option>
				        			 	<option value="${ awayTeam }" >${ awayTeamName }</option>
				        			 </c:if>
				        			 <c:if test="${entity.pkVictory == awayTeam}">
				        			 	<option value="${ awayTeam }" selected="selected">${ awayTeamName }</option>
						         		<option value="${ homeTeam }" >${ homeTeamName }</option>
				        			 </c:if>
				        	    </c:if>
				        	    <c:if test="${empty entity.pkVictory}">
				        	    	<option value="">请选择胜方战队</option>
					         		<option value="${ homeTeam }" >${ homeTeamName }</option>
					         		<option value="${ awayTeam }" >${ awayTeamName }</option>
				        	    </c:if>
					        </select>
					      </div>
				      </div>
					</div>
					
					<div class="form-group" id="pkRangFenTeam-group" style="display: none;">
				      <label class="control-label col-sm-1 no-padding-right" for="pkRangFenTeam">让分战队:</label>
				      <div class="col-sm-2">
					      <div class="clearfix">
					          <select class="form-control" name="pkRangFenTeam" id="pkRangFenTeam" style="width: 100%">
					         	<option value="">请选择让分战队</option>
		      					<c:forEach var="jt" items="${jcTameType }">
		     						<option value="${jt.value }" <c:if test="${jt.value eq entity.pkRangFenTeam}">selected="selected"</c:if>>${jt.label }</option>
		     					</c:forEach>
					         </select>
					      </div>
				      </div>
				      
				      <label class="control-label col-sm-1 no-padding-right" for="pkRangfenNum">让分数:</label>
				      <div class="col-sm-2">
					     <div class="clearfix">
					        <input class="form-control" name="pkRangfenNum" id="pkRangfenNum" type="text" 
					           	value="${entity.pkRangfenNum }" placeholder="让分数..."/>
					     </div>
				     </div>
					</div>
					
					<c:if test="${! empty entity }">
						<div class="form-group">
						  <label class="control-label col-sm-1 no-padding-right" for="thisPkProfit">当前比赛平台收益:</label>
					      <div class="col-sm-2">
						      <div class="clearfix">
						         <input disabled="disabled" class="form-control" name="thisPkProfit" id="thisPkProfit" type="text" 
						           		value="${ entity.thisPkProfit }" placeholder=""/>
						      </div>
					      </div>
					      
					      <label class="control-label col-sm-1 no-padding-right" for="pkHomePrtGold">主战队下注金额:</label>
					      <div class="col-sm-2">
						      <div class="clearfix">
						         <input disabled="disabled"  class="form-control" name="pkHomePrtGold" id="pkHomePrtGold" type="text" 
						           		value="${ entity.pkHomePrtGold }" placeholder="主战队下注金额..."/>
						      </div>
					      </div>
					      
					      <label class="control-label col-sm-1 no-padding-right" for="pkAwayPrtGold">客场战队下注金额</label>
					      <div class="col-sm-2">
						      <div class="clearfix">
						         <input disabled="disabled" class="form-control" name="pkAwayPrtGold" id="pkAwayPrtGold" type="text" 
						           		value="${ entity.pkAwayPrtGold }" placeholder="客场战队下注金额"/>
						      </div>
					      </div>
						</div>
						
						<div class="form-group">
						  <label class="control-label col-sm-1 no-padding-right" for="pkStatusTypeName">盘口现状:</label>
					      <div class="col-sm-2">
						      <div class="clearfix">
						         <input disabled="disabled"  class="form-control" name="pkStatusTypeName" id="pkStatusTypeName" type="text" 
						           		value="${ entity.pkStatusTypeName }" placeholder="盘口现状..."/>
						      </div>
					      </div>
					      
					      <label class="control-label col-sm-1 no-padding-right" for="pkHomePrt">参与人数（主）:</label>
					      <div class="col-sm-2">
						      <div class="clearfix">
						         <input disabled="disabled"  class="form-control" name="pkHomePrt" id="pkHomePrt" type="text" 
						           		value="${ entity.pkHomePrt }" placeholder="竞猜比例（主战队）..."/>
						      </div>
					      </div>
					      
					      <label class="control-label col-sm-1 no-padding-right" for="pkAwayPrt">参与人数（客场）:</label>
					      <div class="col-sm-2">
						      <div class="clearfix">
						         <input disabled="disabled"  class="form-control" name="pkAwayPrt" id="pkAwayPrt" type="text" 
						           		value="${ entity.pkAwayPrt }" placeholder="竞猜比例（副战队）..."/>
						      </div>
					      </div>
						</div>
					</c:if>
				</form>
				
				<div class="hr hr-dotted"></div>
			</div>
		</div>
	
		<div class="center">
			<button id="btnAdd" type="button" onclick="javascript:$('#pankou-form').submit();" class="btn btn-success btn-sm">
			  	<i class="fa fa-user-plus"></i>&nbsp;
			  	<c:if test="${ !empty entity }">
			  	  保存
			  	</c:if>
			</button>
			
			<button id="btn" type="button" onclick="webside.common.loadPage('/gameBattle/editUI?id=${entity.gbId}&page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}')" class="btn btn-info btn-sm">
				<i class="fa fa-undo"></i>&nbsp;返回
			</button>
		</div>
	</div>
</div>