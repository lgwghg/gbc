<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet" href="${ctx }/resources/js/select2/select2.min.css" />
<script type="text/javascript" src="${ctx }/resources/js/select2/select2.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/select2/zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
	    webside.form.user.validateUserForm();
	});
</script>
<div class="page-header">
	<h1>
		<c:if test="${empty userEntity}">
		新增用户
		</c:if>
		<c:if test="${!empty userEntity}">
		用户详细信息
		</c:if>
	</h1>
	<c:if test="${!empty userEntity}">
		<hr />
		<ul class="nav nav-tabs">
			<li class="active"><a href="#form" data-toggle="tab">编辑用户</a></li>
			<li><a href="#userIncrement" data-toggle="tab">用户增量信息</a></li>
			<!-- <li><a href="#laudLoglist" data-toggle="tab">用户的钱包</a></li> -->
		</ul>

	</c:if>
</div>
<div class="tab-content">
	<%--编辑用户 --%>
	<div class="tab-pane fade in active" id="form">
		<div class="row" style="margin-top:5px;">
			<div class="col-xs-12">
				<form id="userForm" name="userForm" class="form-horizontal" role="form" method="post">
					<c:if test="${!empty userEntity}">
						<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum }">
						<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }">
						<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn }">
						<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType }">
						<input type="hidden" name="id" id="userId" value="${userEntity.id }">
					</c:if>
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="nickName">昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input <c:if test="${not empty userEntity}">readonly</c:if> class="form-control" name="nickName" id="nickName" type="text" value="${userEntity.nickName }" placeholder="昵称..." />
							</div>
						</div>

						<label class="control-label col-sm-1 no-padding-right" for="mobile">手机号</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input <c:if test="${not empty userEntity}">readonly</c:if> class="form-control" name="mobile" id="mobile" type="mobile" value="${userEntity.mobile}" />
							</div>
						</div>
						<c:if test="${not empty userEntity}">
						<label class="control-label col-sm-1 no-padding-right" for="email">邮箱</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="email" id="email" type="text" value="${userEntity.email }" />
							</div>
						</div>
						</c:if>
					</div>
					<c:if test="${empty userEntity}">
					<div class="form-group">
					      <label class="control-label col-sm-1 no-padding-right" for="password">密码</label>
					      <div class="col-sm-2">
					      <div class="clearfix">
					         <input class="form-control" name="password" id="password" type="password"
					          placeholder="密码..."/>
				      	</div>
				      	</div>
				      	
				      	<label class="control-label col-sm-1 no-padding-right" for="repassword">确认密码</label>
					    <div class="col-sm-2">
					      <div class="clearfix">
					         <input class="form-control" name="repassword" id="repassword" type="password"
					          placeholder="确认密码..."/>
				      		</div>
				      	</div>
				    </div>   
				   </c:if>
				   
				   <c:if test="${not empty userEntity}">
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="steamKey">steam key</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="steamKey" id="steamKey" type="text" value="${userEntity.steamKey }"  />
							</div>
						</div>

						<label class="control-label col-sm-1 no-padding-right" for="qqKey">QQ key</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="qqKey" id="qqKey" type="text" value="${userEntity.qqKey}" />
							</div>
						</div>

						<label class="control-label col-sm-1 no-padding-right" for="wechatKey">wechat key</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="wechatKey" id="wechatKey" type="text" value="${userEntity.wechatKey }" />
							</div>
						</div>

						<label class="control-label col-sm-1 no-padding-right" for="weiboKey">weibo key</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="weiboKey" id="weiboKey" type="text" value="${userEntity.weiboKey }" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="steamNick">steam 昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="steamNick" id="steamNick" type="text" value="${userEntity.steamNick }"  />
							</div>
						</div>

						<label class="control-label col-sm-1 no-padding-right" for="qqNick">QQ 昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="qqNick" id="qqNick" type="text" value="${userEntity.qqNick}" />
							</div>
						</div>

						<label class="control-label col-sm-1 no-padding-right" for="wechatNick">wechat 昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="wechatNick" id="wechatNick" type="text" value="${userEntity.wechatNick }" />
							</div>
						</div>

						<label class="control-label col-sm-1 no-padding-right" for="weiboNick">weibo 昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="weiboNick" id="weiboNick" type="text" value="${userEntity.weiboNick }" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="photo">头像</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<img src="${userEntity.photo }" style="width:100px;height:100px;"/>
							</div>
						</div>
						
						<c:if test="${not empty userEntity and not empty userEntity.rankLevel}">
						<label class="control-label col-sm-1 no-padding-right" for="alipayAccount">头衔等级</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="rankLevel" id="rankLevel" type="text" value="${userEntity.rankLevel }" />
							</div>
						</div>
						
						</c:if>
						
						<label class="control-label col-sm-1 no-padding-right" for="alipayAccount">支付宝</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input readonly class="form-control" name="alipayAccount" id="alipayAccount" type="text" value="${userEntity.alipayAccount }" />
							</div>
						</div>
						
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="userName">所属角色</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<select class="form-control" name="role.id" id="roleId" style="width: 100%">
									<option value=""></option>
									<c:choose>
										<c:when test="${!empty userEntity}">
											<c:forEach var="role" items="${roleList }">
												<c:choose>
													<c:when test="${userSession.role.name eq '超级管理员' && userEntity.role.name eq '超级管理员'}">
														<c:if test="${role.name eq '超级管理员'}">
															<option value="${role.id }" <c:if test="${userEntity.role.id eq role.id}">selected="selected"</c:if>>${role.name }</option>
														</c:if>
													</c:when>
													<c:when test="${userSession.role.name eq '超级管理员'}">
														<c:if test="${role.name ne '超级管理员'}">
															<option value="${role.id }" <c:if test="${userEntity.role.id eq role.id}">selected="selected"</c:if>>${role.name }</option>
														</c:if>
													</c:when>
													<c:when test="${userSession.role.name eq '管理员'}">
														<c:if test="${role.name ne '超级管理员' && role.name ne '管理员'}">
															<option value="${role.id }" <c:if test="${userEntity.role.id eq role.id}">selected="selected"</c:if>>${role.name }</option>
														</c:if>
													</c:when>
													<c:otherwise>
														<c:if test="${userEntity.role.id eq role.id}">
															<option value="${role.id }" selected="selected">${role.name }</option>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="role" items="${roleList }">
												<c:choose>
													<c:when test="${userSession.role.name eq '超级管理员'}">
														<c:if test="${role.name ne '超级管理员'}">
															<option value="${role.id }">${role.name }</option>
														</c:if>
													</c:when>
													<c:when test="${userSession.role.name eq '管理员'}">
														<c:if test="${role.name ne '超级管理员' && role.name ne '管理员'}">
															<option value="${role.id }">${role.name }</option>
														</c:if>
													</c:when>
													<c:otherwise>
														<option value="${role.id }">${role.name }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</select>
							</div>
						</div>
						
						<label class="control-label col-sm-1 no-padding-right" for="sign">用户签名</label>
						<div class="col-sm-5">
							<div class="clearfix">
								<input class="form-control" name="sign" id="sign" type="text" value="${userEntity.sign }" placeholder="个性签名..." />
							</div>
						</div>
					</div>
						</c:if>
				</form>
				<div class="hr hr-dotted"></div>
			</div>
		</div>
		<div class="center">
			<button id="btnAdd" type="button" onclick="javascript:$('#userForm').submit();" class="btn btn-success btn-sm">
				<i class="fa fa-user-plus"></i>&nbsp;
				<c:if test="${empty userEntity}">
		添加
		</c:if>
				<c:if test="${!empty userEntity}">
		保存
		</c:if>
			</button>
			<button id="btn" type="button" onclick="webside.common.loadPage('/user/listUI.html<c:if test="${!empty userEntity}">?page=${page.pageNum }&rows=${page.pageSize }&sidx=${page.orderByColumn }&sord=${page.orderByType }</c:if>')" class="btn btn-primary btn-sm">
				<i class="fa fa-undo"></i>&nbsp;返回
			</button>
		</div>
	</div>

	<%-- 用户增量信息 --%>
	<div class="tab-pane fade" id="userIncrement">
		<div class="row" style="margin-top:5px;">
			<div class="col-xs-12">
				<form id="userIncrementForm" name="userIncrementForm" class="form-horizontal" role="form" method="post">
					<c:if test="${!empty userEntity}">
						<input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum }">
						<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }">
						<input type="hidden" id="orderByColumn" name="orderByColumn" value="${page.orderByColumn }">
						<input type="hidden" id="orderByType" name="orderByType" value="${page.orderByType }">
						<input type="hidden" name="id" id="userId" value="${userEntity.id }">
					</c:if>
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="nickName">用户昵称</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input class="form-control" readonly="true" type="text" value="${userIncrement.nickName }" name="nickName" />
							</div>
						</div>
						
						<label class="control-label col-sm-1 no-padding-right" for="totalProfitGoldNum">累计盈利金币数</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input class="form-control" readonly="true" type="text" value="${userIncrement.totalProfitGoldNum }" name="totalProfitGoldNum" />

							</div>
						</div>
						
						<label class="control-label col-sm-1 no-padding-right" for="unreadNum">消息未读量</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input class="form-control" readonly="true" type="text" value="${userIncrement.unreadNum}" name="unreadNum" />

							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-1 no-padding-right" for="victoryNum">胜利量</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input class="form-control" readonly="true" type="text" value="${userIncrement.victoryNum }" name="victoryNum" />

							</div>
						</div>
						
						<label class="control-label col-sm-1 no-padding-right" for="jcNum">竞猜量</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input class="form-control" readonly="true" type="text" value="${userIncrement.jcNum }" name="jcNum" />
							</div>
						</div>
						
						<label class="control-label col-sm-1 no-padding-right" for="victoryRate">胜利率</label>
						<div class="col-sm-2">
							<div class="clearfix">
								<input class="form-control" readonly="true" type="text" value="${userIncrement.victoryRate }" name="victoryRate" />

							</div>
						</div>
						
					</div>
				</form>
				<div class="hr hr-dotted"></div>
			</div>
		</div>
	</div>
	<%-- 用户钱包 --%>

</div>
<%--class="tab-content" end --%>
