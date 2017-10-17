<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/common/upload.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/customer/business/goods/form/form.js"></script>

<div class="page-header">
	<h1>
		<c:if test="${empty goods }">
		新增
		</c:if>
		<c:if test="${!empty goods }">
		编辑
		</c:if>
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<form id="goods-form" name="goods-form" class="form-horizontal" role="form" method="post">
			<c:if test="${!empty goods }">
				<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}">
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
				<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn}">
				<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType}">
				<input type="hidden" name="id" id="id" value="${goods.id}">
			</c:if>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="goodsName">商品名称</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="goodsName" id="goodsName" type="text" 
			           		value="${goods.goodsName }" placeholder="商品名称..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="goodsImg">商品图片</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="goodsImg" id="goodsImg" type="hidden" 
			           		value="${goods.goodsImg }" placeholder="商品图片..."/>
			         <div id="goods_pic_view_div" style="width:120px; height:120px">  
				         <img src="${ctx}/resources/images/nopic.gif" style="width:120px; height:120px" id="goods_pic_view">  
				    </div>  
					<div id="goods_upload"></div>
			      </div>
			      <div>
		        	<span class="help-inline">图片尺寸330*220</span>
		      	  </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="goodsGold">商品金币</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="goodsGold" id="goodsGold" type="text" 
			           		value="${goods.goodsGold }" placeholder="商品金币..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="goodsNum">商品数量</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="goodsNum" id="goodsNum" type="text" readonly="readonly"
			           		value="${goods.goodsNum }" placeholder="商品数量..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="desc">商品描述</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			         <input class="form-control" name="desc" id="desc" type="text" 
			           		value="${goods.desc }" placeholder="商品描述..."/>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="type">类型 </label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <select class="form-control" name="type" id="type" style="width: 100%">
						<option value=""></option>
			   			<c:forEach items="${goodsType }" var="dict">
			   				<option value="${dict.value }" ${goods.type eq dict.value?"selected":""}>${dict.label }</option>
			   			</c:forEach>
					 </select>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="isMax">是否无限库存</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <select class="form-control" name="isMax" id="isMax" style="width: 100%">
						<option value="0" ${goods.isMax eq 0?"selected":""}>否</option>
						<option value="1" ${goods.isMax eq 1?"selected":""}>是</option>
					 </select>
			      </div>
		      </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-1 no-padding-right" for="status">状态</label>
		      <div class="col-sm-10">
			      <div class="clearfix">
			      	 <select class="form-control" name="status" id="status" style="width: 100%">
						<c:forEach items="${goodsStatus }" var="dict">
			   				<option value="${dict.value }" ${goods.status eq dict.value?"selected":""}>${dict.label }</option>
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
	<button id="btnAdd" type="button" onclick="javascript:$('#goods-form').submit();" class="btn btn-success btn-sm">
	  	<i class="fa fa-user-plus"></i>&nbsp;
	  	<c:if test="${empty goods }">
	  	添加
	  	</c:if>
	  	<c:if test="${!empty goods }">
	  	保存
	  	</c:if>
	</button>
	
	<button id="btn" type="button" onclick="webside.common.loadPage('/goods/listUI.html<c:if test="${!empty goods}">?page=${page.pageNum}&rows=${page.pageSize}&sidx=${page.orderByColumn}&sord=${page.orderByType}</c:if>')" class="btn btn-info btn-sm">
		<i class="fa fa-undo"></i>&nbsp;返回
	</button>
</div>