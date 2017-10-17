<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet"
	href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		webside.form.dict.validateDictForm();
	});
</script>
<div class="page-header">
	<h1>
		<c:if test="${empty dictEntity}">
		新增字典
		</c:if>
		<c:if test="${!empty dictEntity}">
		编辑字典
		</c:if>
	</h1>
</div>
<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="dictForm" name="dictForm" class="form-horizontal" role="form" method="post">
		<c:if test="${!empty dictEntity}">
			<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum }">
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }">
			<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn }">
			<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType }">
			<input type="hidden" name="id" id="id" value="${dictEntity.id }">
		</c:if>
		   <div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="type">字典类型</label>
		      <div class="col-sm-10">
		      <div class="clearfix">
		         <input <c:if test="${!empty dictEntity}">readonly</c:if> class="form-control" name="type" id="type" type="text" 
		           value="${dictEntity.type }" placeholder="字典类型..."/>
		      </div>
		      </div>
		   </div>
		   <div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="label">标签名</label>
		      <div class="col-sm-10">
		      <div class="clearfix">
		         <input class="form-control" name="label" id="label" type="text"
		         value="${dictEntity.label }" placeholder="标签名..."/>
		      </div>
		      </div>
		   </div>   
		   <div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="value">数据值</label>
		      <div class="col-sm-10">
		      <div class="clearfix">
		         <input class="form-control" name="value" id="value" type="text"
		         value="${dictEntity.value }" placeholder="数据值..."/>
		      </div>
		      </div>
		   </div>
		   <div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="sort">排序</label>
		      <div class="col-sm-10">
		      <div class="clearfix">
		         <input class="form-control" name="sort" id="sort" type="text"
		         value="${dictEntity.sort }" placeholder="排序..."/>
		      </div>
		      </div>
		   </div>
		   <div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="description">描述</label>
		      <div class="col-sm-10">
		      <div class="clearfix">
		         <input class="form-control" name="description" id="description" type="text"
		         value="${dictEntity.description }" placeholder="描述..."/>
		      </div>
		      </div>
		   </div> 
		   <div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="labelClass">文字列表样式</label>
		      <div class="col-sm-10">
		      <div class="clearfix">
		         <input class="form-control" name="labelClass" id="labelClass" type="text"
		         value="<c:if test="${!empty dictEntity }">${dictEntity.labelClass }</c:if><c:if test="${empty dictEntity }">label label-sm label-success arrowed arrowed-righ</c:if>" placeholder="文字列表样式..."/>
		      </div>
		      </div>
		   </div> 
		   <div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="description">是否删除</label>
		      <div class="col-sm-10">
		      <div class="clearfix">
		      		<select class="form-control" name="delFlag" id="delFlag" style="width: 100%">
		      			<c:choose>
		      				<c:when test="${!empty dictEntity}">
	      						<c:choose>
	      							<c:when test="${dictEntity.delFlag eq '0'}">
	      								<option value="0" selected="selected">正常</option>
	      								<option value="1">删除</option>
									</c:when>
									<c:otherwise>
										<option value="1" selected="selected">删除</option>
										<option value="0" >正常</option>
									</c:otherwise>
	      						</c:choose>
		      				</c:when>
		      				<c:otherwise>
	      						<option value="0">正常</option>
	      						<option value="1">删除</option>
		      				</c:otherwise>
		      			</c:choose>
		      		</select>
		      </div>
		      </div>
		   </div> 
		</form>
		<div class="hr hr-dotted"></div>
	</div>
</div>
<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#dictForm').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${empty dictEntity}">
		添加
		</c:if>
	  	<c:if test="${!empty dictEntity}">
		保存
		</c:if>
	</button>
		<button id="btn" type="button" onclick="webside.common.loadPage('/system/dict/listUI.html<c:if test="${!empty dictEntity}">?page=${page.pageNum }&rows=${page.pageSize }&sidx=${page.orderByColumn }&sord=${page.orderByType }</c:if>')" class="btn btn-primary btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>