<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customer/business/system/sn/webNoticeForm.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<!-- <script type="text/javascript" charset="utf-8" src="lang/zh-cn/zh-cn.js"></script> -->
<script type="text/javascript">
	$(function()  {
	    sysWebNotice.form.validateForm();
	    var ue = UE.getEditor("content");
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
		<form id="syswebnotice-form" name="syswebnotice-form" class="form-horizontal" role="form" method="post">
			<c:if test="${ !empty entity }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${entity.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="title">标题</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="title" id="title" type="text" 
			           		value="${ entity.title }" placeholder="标题..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="code">编码</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="code" id="code" type="text" 
			           		value="${ entity.code }" placeholder="编码..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="content">通知内容</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <script id="content" name="content" type="text/plain" style="width:100%;height:256px;">${ entity.content }</script>
<!-- 			      	 <textarea maxlength="2000" rows="5" cols="100%" placeholder="通知内容..." name="content" id="content">${ entity.content }</textarea> -->
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="type">公告类型</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <select class="form-control" name="type" id="type" style="width: 100%">
						<c:forEach items="${webNoticeType }" var="dict">
			   				<option value="${dict.value }" ${entity.type eq dict.value?"selected":""}>${dict.label }</option>
			   			</c:forEach>
					 </select>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="sequence">序列</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="sequence" id="sequence" type="text" 
			           		value="${ entity.sequence }" placeholder="序列..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="status">状态 </label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <select class="form-control" name="status" id="status" style="width: 100%">
						<c:forEach items="${webNoticeStatus }" var="dict">
			   				<option value="${dict.value }" ${entity.status eq dict.value?"selected":""}>${dict.label }</option>
			   			</c:forEach>
					 </select>
			      </div>
		      </div>
			</div>
		</form>
		
		<div class="hr hr-dotted"></div>
	</div>
</div>

<div class="center">
	<button id="btnAdd" type="button" onclick="javascript:$('#syswebnotice-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${ empty entity }">
	  	添加
	  	</c:if>
	  	<c:if test="${ !empty entity }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/system/webNotice/listUI.html<c:if test="${!empty entity}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>