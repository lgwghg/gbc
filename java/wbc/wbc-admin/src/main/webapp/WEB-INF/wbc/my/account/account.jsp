<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${header['X-PJAX']}">
		<jsp:include page="../../include/common_pjax.jsp" />
		<title>你的账户${seoConfigMap['1'].title }</title>
		<meta name="keywords" content="${seoConfigMap['1'].keywords }">
		<meta name="description" content="${seoConfigMap['1'].description }">
		<link rel="stylesheet" type="text/css"
			href="${staticPrefix }/css/inform.css?v=${version }">
		<link rel="stylesheet" type="text/css"
			href="${staticPrefix }/css/myaccount.css?v=${version }&t=<%=Math.random() %>">
		<link rel="stylesheet" type="text/css"
			href="${staticPrefix }/css/cropper.min.css?v=${version }">
		<link rel="stylesheet" type="text/css"
			href="${staticPrefix }/css/sitelogo.css?v=${version }">
		<script type="text/javascript"
			src="${staticPrefix }/js/jquery.form.js?v=${version }&t=<%=Math.random() %>"></script>
		<script type="text/javascript"
			src="${staticPrefix }/js/ZeroClipboard.min.js?v=${version }"></script>
		<script type="text/javascript"
			src="${staticPrefix }/js/my/account/editUser.js?v=${version }&t=<%=Math.random() %>"></script>
		<script type="text/javascript"
			src="${staticPrefix }/js/my/account/editAlipay.js?v=${version }&t=<%=Math.random() %>"></script>
		<script type="text/javascript"
			src="${staticPrefix }/js/my/account/editPayPassword.js?v=${version }&t=<%=Math.random() %>"></script>
		<script type="text/javascript"
			src="${staticPrefix }/js/goods/index.js?v=${version }&t=<%=Math.random() %>"></script>
		<script type="text/javascript"
			src="${staticPrefix }/js/common/cropper.min.js?v=${version }"></script>
		<script type="text/javascript"
			src="${staticPrefix }/js/common/sitelogo.js?v=${version }"></script>
			
			<style type="text/css">
					
			</style>
		<!-- 上传头像 -->
		<div class="modal fade" id="avatar-modal" aria-hidden="true"
			aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
			<div class="modal-dialog modal-lg">
				<div class="modal-content"
					style="background:#2E3238;width:900px;height:646px;">
					<form class="avatar-form" action="${path }/avatar/upload"
						enctype="multipart/form-data" method="post" id="uploadImgForm">
						<div class="modal-header">
							<button class="close" data-dismiss="modal" type="button">&times;</button>
							<h4 class="modal-title" id="avatar-modal-label"
								style="color: #009688; font-size: 20px;">图片上传</h4>
						</div>
						<div class="modal-body" style="padding-top: 0;">
							<div class="avatar-body">
								<div class="avatar-upload">
									<input class="avatar-data" name="avatar_data" id="avatar_data"
										type="hidden">
									<div class="form-group">
										<label class="control-label" for="avatarInput"></label> <input
											type="file" id="avatarInput" multiple="" class="avatar-input"
											name="file"> <input type="text" readonly=""
											class="form-control" placeholder="请选择图片">
									</div>
								</div>
								<div class="row">
									<div class="col-md-9">
										<div class="avatar-wrapper" style="background:#2E3238"></div>
									</div>
									<div class="col-md-3">
										<div class="avatar-preview preview-lg"
											style="background:#2E3238"></div>
										<div class="avatar-preview preview-md"
											style="background:#2E3238"></div>
										<div class="avatar-preview preview-sm"
											style="background:#2E3238"></div>
									</div>
								</div>
								<div class="row avatar-btns">
									<div class="col-md-9">
										<div class="btn-group">
											<button class="btn btn-primary btn-raised"
												data-method="rotate" data-option="-90" type="button"
												title="Rotate -90 degrees">向左旋转</button>
										</div>
										<div class="btn-group">
											<button class="btn btn-primary btn-raised"
												data-method="rotate" data-option="90" type="button"
												title="Rotate 90 degrees">向右旋转</button>
										</div>
									</div>
									<div class="col-md-3">
										<button class="btn btn-success btn-block avatar-save"
											type="button" onclick="uploadImg()">保存修改</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- 上传头像end -->

		<div class="c-informright pull-left">
					<p style="position: relative;">
						<img src="${userEntity.photo_65 }" class="img-circle img"
							width="54px" height="54px" /><span class="ml-10">${userEntity.nickName }</span><a
							class="img_editor" data-toggle="modal"
							data-target="#avatar-modal">编辑</a>
					</p>
					<p class="ft-16 pt-5 pb-20">编辑个人资料</p>
					<div class="c-centerwrap">
						<div class="form-group label-floating" style="">
							<input type="hidden" id="oldNickName" name="oldNickName"
								value="${userEntity.nickName }"> 
								<label for="a_nickName" class="control-label">昵称</label> 
								<input type="text" class="form-control" id="a_nickName" name="nickName" value="${userEntity.nickName }"  /> 
								<span class="help-block" id="nickError"></span>
						</div>
						<!-- 展示的手机号框 -->
						<div class="form-group label-floating c_num" style="">
							<i class="iconfont icon-bianji iconnum c_greyicon"></i> <label
								for="mobileInfo" class="control-label">手机号码</label> <input
								type="text" class="form-control" id="mobileInfo" disabled="true"
								value="${userEntity.mobile }"> <span class="help-block"></span>
						</div>
						<div class="numwrap1">
							<div class="form-group label-floating " style="">
								<i class="iconfont icon-jianshao c_tip2 c_greyicon"></i> <label
									for="mobile" class="control-label">手机号码</label> <input
									type="text" class="form-control" disabled="true"
									value="${userEntity.mobile }" id="a_mobile" name="mobile">
							</div>
							<div class="form-group label-floating"
								style=" position: relative;float:left;width:48%;">
								<label for="mobileCaptcha" class="control-label">手机动态码</label>
								<button type="button" id="countdown_mobile"
									class="btn btn-primary btn-raised pull-right countdown_mobile"
									style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
									<strong>获取动态码</strong>
								</button>
								<input type="text" class="form-control" id="mobileCaptcha"
									style="padding-right:110px;"> <span class="help-block"
									id="mobileCaptchaError"></span>
								<button class="btn btn-primary btn-raised pull-right numbtn">确定</button>
							</div>
							<div class="form-group label-floating hide"
								style="position:relative;float:right;width:48%;">
								<label class="control-label">验证码</label> <input type="text"
									class="form-control text_input" name="captcha"
									id="imgCaptcha_mobile"
									style="padding-right:110px;text-align:left;margin-top:0;"
									onkeyup="user_checkImgCaptcha('imgCaptcha_mobile', 'countdown_mobile')">
								<span class="help-block" id="imgCaptchaError_mobile"></span> <img
									id="captchaImage_mobile" src="${ctx }/captcha"
									style="cursor:pointer; margin-left:10px; position: absolute; top:9px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;"
									class="pull-right" title="点击更换" />
							</div>
							<div class="fix"></div>
						</div>
						<div class="numwrap2">
							<div class="form-group label-floating" style="">
								<label for="newMobile" class="control-label">请输入新手机号</label> <input
									type="text" class="form-control" id="newMobile"
									name="newMobile"> <span class="help-block"
									id="newMobileError"></span>
							</div>
							<div class="form-group label-floating"
								style=" position: relative;float:left;width:48%;">
								<label for="newMobileCaptcha" class="control-label">手机动态码</label>
								<button type="button" id="countdown_newMobile" disabled="true"
									class="btn btn-primary btn-raised pull-right"
									style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
									<strong>获取动态码</strong>
								</button>
								<input type="text" class="form-control" id="newMobileCaptcha"
									style="padding-right:110px;"> <span class="help-block"
									id="newMobileCaptchaError"></span>
								<button class="btn btn-primary btn-raised pull-right numbtn1">保存</button>
							</div>
							<div class="form-group label-floating hide"
								style="position:relative;float:right;width:48%;">
								<label class="control-label">验证码</label> <input type="text"
									class="form-control text_input" name="captcha"
									id="imgCaptcha_newMobile"
									style="padding-right:110px;text-align:left;margin-top:0;"
									onkeyup="user_checkImgCaptcha('imgCaptcha_newMobile', 'countdown_newMobile')">
								<span class="help-block" id="imgCaptchaError_newMobile"></span>
								<img id="captchaImage_newMobile" src="${ctx }/captcha"
									style="cursor:pointer; margin-left:10px; position: absolute; top:9px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;"
									class="pull-right" title="点击更换" />
							</div>
							<div class="fix"></div>
						</div>
						<!-- 编辑邮箱 -->
						<div class="form-group label-floating c_email" style="">
							<i class="iconfont icon-bianji iconemail c_greyicon"></i> <label
								for="emailInfo" class="control-label">邮箱地址</label> <input
								type="email" class="form-control" id="emailInfo"
								disabled="disabled" value="${userEntity.email }"> <span
								class="help-block"></span>
						</div>
						<div class="emailwrap1">
							<div class="form-group label-floating " style="">
								<i class="iconfont icon-jianshao c_tip1 c_greyicon"></i> <label
									for="email" class="control-label"><c:if
										test="${empty userEntity.email }">请输入邮箱地址</c:if>
									<c:if test="${not empty userEntity.email }">邮箱地址</c:if></label> <input
									type="email" class="form-control" id="a_email"
									value="${userEntity.email }"
									<c:if test="${not empty userEntity.email }">disabled="true"</c:if>>
								<span class="help-block" id="emailError"></span>
							</div>
							<div class="form-group label-floating"
								style=" position: relative;">
								<label for="emailCaptcha" class="control-label">邮箱验证码</label>
								<button type="button" id="countdown_email"
									<c:if test="${empty userEntity.email }">disabled="true"</c:if>
									class="btn btn-primary btn-raised pull-right"
									style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
									<strong>获取验证码</strong>
								</button>
								<input type="text" class="form-control" id="emailCaptcha">
								<span class="help-block" id="emailCaptchaError"></span>
								<c:if test="${not empty userEntity.email }">
									<button class="btn btn-primary btn-raised pull-right embtn">确定</button>
								</c:if>
							</div>
						</div>
						<c:if test="${not empty userEntity.email }">
							<div class="emailwrap2">
								<div class="form-group label-floating" style="">
									<label for="newEmail" class="control-label">请输入新邮箱地址</label> <input
										type="text" class="form-control" id="newEmail"> <span
										class="help-block" id="newEmailError"></span>
								</div>
								<div class="form-group label-floating"
									style=" position: relative;">
									<label for="newEmailCaptcha" class="control-label">邮箱验证码</label>
									<button type="button" id="countdown_newEmail"
										class="btn btn-primary btn-raised pull-right"
										style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
										<strong>获取验证码</strong>
									</button>
									<input type="text" class="form-control" id="newEmailCaptcha">
									<span class="help-block" id="newEmailCaptchaError"></span>
									<button class="btn btn-primary btn-raised pull-right embtn1">保存新邮箱</button>
								</div>
							</div>
						</c:if>
						<div class="form-group label-floating password" style="">
							<i class="iconfont icon-bianji iconpwd c_greyicon"></i> <label
								for="password" class="control-label">密码</label> <input
								type="password" class="form-control" id="password"
								disabled="disabled" value="<c:if test='${not empty userEntity.password }'>********</c:if>"> <span class="help-block"></span>
						</div>
						<div class="passwordwrap">
							<div class="form-group label-floating" style="">
								<i class="iconfont icon-jianshao pasd_jiansha c_greyicon"></i> <label
									for="password" class="control-label">原密码</label> <input
									type="password" class="form-control" id="oldPassword">
								<span class="help-block" id="oldPasswordError"></span>
							</div>
							<div class="form-group label-floating" style="">
								<label for="newPassword" class="control-label">新密码</label> <input
									type="password" class="form-control" id="newPassword">
								<span class="help-block" id="newPasswordError"></span>
							</div>
							<div class="form-group label-floating" style="">
								<label for="a_rePassword" class="control-label">重新输入密码</label> <input
									type="password" class="form-control" id="a_rePassword">
								<span class="help-block" id="a_rePasswordError"></span>
							</div>
						</div>
						<%-- 支付密码start--%>
						<div class="form-group label-floating c_pay_password" style="">
							<i class="iconfont icon-bianji iconpaypassword c_greyicon"></i> <label
								for="payPasswordInfo" class="control-label">支付密码</label> <input
								type="password" class="form-control" id="payPasswordInfo"
								disabled="disabled" value="<c:if test='${not empty userEntity.payPassword }'>********</c:if>" /> <span
								class="help-block"></span>
						</div>
						<div class="paypasswordwrap">
							<div class="form-group label-floating " style="">
								<i class="iconfont icon-jianshao c_tip4 c_greyicon"></i> <label
									for="payPassword" class="control-label">请输入支付密码</label> <input
									type="password" class="form-control" id="payPassword">
								<span class="help-block" id="payPasswordError"></span>
							</div>
							<div class="form-group label-floating"
								style=" position: relative;float:left;width:48%;">
								<label for="payPasswordCaptcha" class="control-label">手机动态码</label>
								<button type="button" id="countdown_payPassword" disabled="true"
									class="btn btn-primary btn-raised pull-right countdown_mobile"
									style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
									<strong>获取动态码</strong>
								</button>
								<input type="text" class="form-control" id="payPasswordCaptcha"
									style="padding-right:110px;"> <span class="help-block"
									id="payPasswordCaptchaError"></span>
								<button
									class="btn btn-primary btn-raised pull-right payPasswordBtn">保存</button>
							</div>
							<div class="form-group label-floating hide"
								style="position:relative;float:right;width:48%;">
								<label class="control-label">验证码</label> <input type="text"
									class="form-control text_input" name="captcha"
									id="imgCaptcha_payPassword"
									style="padding-right:110px;text-align:left;margin-top:0;"
									onkeyup="user_checkImgCaptcha('imgCaptcha_payPassword', 'countdown_payPassword')">
								<span class="help-block" id="imgCaptchaError_payPassword"></span>
								<img id="captchaImage_payPassword" src="${ctx }/captcha"
									style="cursor:pointer; margin-left:10px; position: absolute; top:9px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;"
									class="pull-right" title="点击更换" />
							</div>
							<div class="fix"></div>
						</div>
						<%-- 支付密码end--%>
						<div class="form-group label-floating c_pay" style="">
							<i class="iconfont icon-bianji iconpay c_greyicon"></i> <label
								for="alipay" class="control-label">支付宝账号</label> <input
								type="text" class="form-control" id="alipay" disabled="disabled"
								value="${userEntity.alipayAccount }" /> <span class="help-block"></span>
						</div>
						<div class="apaywrap1">
							<div class="form-group label-floating " style="">
								<i class="iconfont icon-jianshao c_tip3 c_greyicon"></i> <label
									for="alipayAccount" class="control-label">请输入支付宝账户</label> <input
									type="text" class="form-control" id="alipayAccount"> <span
									class="help-block" id="alipayAccountError"></span>
							</div>
							<div class="form-group label-floating"
								style=" position: relative;float:left;width:48%;">
								<label for="alipayAccountCaptcha" class="control-label">手机动态码</label>
								<button type="button" id="countdown_alipay" disabled="true"
									class="btn btn-primary btn-raised pull-right countdown_mobile"
									style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
									<strong>获取动态码</strong>
								</button>
								<input type="text" class="form-control"
									id="alipayAccountCaptcha" style="padding-right:110px;">
								<span class="help-block" id="alipayAccountCaptchaError"></span>
								<button class="btn btn-primary btn-raised pull-right paybtn">保存</button>
							</div>
							<div class="form-group label-floating hide"
								style="position:relative;float:right;width:48%;">
								<label class="control-label">验证码</label> <input type="text"
									class="form-control text_input" name="captcha"
									id="imgCaptcha_alipay"
									style="padding-right:110px;text-align:left;margin-top:0;"
									onkeyup="user_checkImgCaptcha('imgCaptcha_alipay', 'countdown_alipay')">
								<span class="help-block" id="imgCaptchaError_alipay"></span> <img
									id="captchaImage_alipay" src="${ctx }/captcha"
									style="cursor:pointer; margin-left:10px; position: absolute; top:9px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;"
									class="pull-right" title="点击更换" />
							</div>
							<div class="fix"></div>
						</div>
						<div class="form-group label-floating">
							<input type="hidden" id="oldSign" value="${userEntity.sign }">
							<label for="sign" class="control-label">个人简介</label>
							<textarea class="form-control" id="sign" name="sign">${userEntity.sign }</textarea>
							<span class="help-block" id="signError"></span>
						</div>
					</div>

					<div class="c-accountwrapright pull-left">
						<p class="ft-16 pt-5 pb-20">你的钱包</p>
						<ul>
							<li style="text-align:center; padding-top:15px;">
							<span><b style="width:35px;">￥</b></span>	
							<div style="height:15px; line-height:15px"><span id="userInfoGold">0</span> 金币</div>
							
								    <button type="button" class="btn" data-toggle="modal"
										data-target="#myModal5" id="userInfoRecharge">
										<strong>充值</strong>
									</button>
									
									 <button type="button" class="btn" onclick="clickWd();"
									 	id="userInfoWithDraw" style=" border: 2px solid #3b9985; color:#3b9985">
										<strong>提现</strong>
									</button>
							
							</li>					
							
								<li>
									<span><b>￥</b></span>
									<span id="userInfoSysGold">100</span>
									<span>G币</span>
									<code class="demoSpan1"></code> <a href="${ctx }/help/findByCode?code=htug">金币有什么用？</a>
								</li>
								
								
							<li style="line-height:50px; height:50px">
								<span class="fr" style="color:#5f7c8c">参与竞猜未结算的金币</span>
								<span id="userInfoJcGold">0 金币 </span>
							</li>
								
							<li style="line-height:50px; height:50px">
								<span class="fr" style="color:#5f7c8c">发起提现未到账的金币</span>
								<span id="userInfoWdGold">0 </span> 金币
							</li>
							
							
						
						</ul>
						<div class="fix"></div>
						<p class="ft-16 pt-5 pb-20">推荐好友</p>
						<p class="ft-14 choosefd">推荐您的好友在平台注册成功后，您将获得100个G币奖励，您的好友完成首次竞猜您将再获得200个G币奖励。</p>
						<div class="x-recommend-link">
							<p class="view1">
								<span class="one">推广链接</span><span class="copy-link two"
									id="d_clip_button_account" data-clipboard-action="copy"
									data-clipboard-text="${ip }register?fromuid=${user.campaignKey }">复制</span>
							</p>
							<p class="view2">
								<input type="text"
									value="${ip }register?fromuid=${user.campaignKey }"
									disabled="true">
							</p>
							<p class="view3">
								<a href="/my/exchangeLog">兑换</a><a href="/my/transactionLog"
									class="ml-20">交易记录</a>
							</p>
						</div>
					</div>
					<button class="btn btn-primary btn-raised pull-left savebtn"
						id="editBtn">保存修改</button>
				</div>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html lang="en">
<head>
<jsp:include page="../../include/common.jsp" />
<title>你的账户${seoConfigMap['1'].title }</title>
<meta name="keywords" content="${seoConfigMap['1'].keywords }">
<meta name="description" content="${seoConfigMap['1'].description }">
<link rel="stylesheet" type="text/css"
	href="${staticPrefix }/css/inform.css?v=${version }">
<link rel="stylesheet" type="text/css"
	href="${staticPrefix }/css/myaccount.css?v=${version }">
<link rel="stylesheet" type="text/css"
	href="${staticPrefix }/css/cropper.min.css?v=${version }">
<link rel="stylesheet" type="text/css"
	href="${staticPrefix }/css/sitelogo.css?v=${version }">
<script type="text/javascript"
	src="${staticPrefix }/js/jquery.form.js?v=${version }"></script>
<script type="text/javascript"
	src="${staticPrefix }/js/ZeroClipboard.min.js?v=${version }"></script>
<script type="text/javascript"
	src="${staticPrefix }/js/my/account/editUser.js?v=${version }"></script>
<script type="text/javascript"
	src="${staticPrefix }/js/my/account/editAlipay.js?v=${version }"></script>
<script type="text/javascript"
	src="${staticPrefix }/js/my/account/editPayPassword.js?v=${version }"></script>
<script type="text/javascript"
	src="${staticPrefix }/js/common/cropper.min.js?v=${version }"></script>
<script type="text/javascript"
	src="${staticPrefix }/js/common/sitelogo.js?v=${version }"></script>
<script type="text/javascript"
	src="${staticPrefix }/js/goods/index.js?v=${version }"></script>


</head>
<body>
	<div class="modal fade" id="avatar-modal" aria-hidden="true"
		aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content"
				style="background:#2E3238;width:900px;height:646px;">
				<form class="avatar-form" action="${path }/avatar/upload"
					enctype="multipart/form-data" method="post" id="uploadImgForm">
					<div class="modal-header">
						<button class="close" data-dismiss="modal" type="button">&times;</button>
						<h4 class="modal-title" id="avatar-modal-label"
							style="color: #009688; font-size: 20px;">图片上传</h4>
					</div>
					<div class="modal-body" style="padding-top: 0;">
						<div class="avatar-body">
							<div class="avatar-upload">
								<input class="avatar-data" name="avatar_data" id="avatar_data"
									type="hidden">
								<div class="form-group">
									<label class="control-label" for="avatarInput"></label> <input
										type="file" id="avatarInput" multiple="" class="avatar-input"
										name="file"> <input type="text" readonly=""
										class="form-control" placeholder="请选择图片">
								</div>
							</div>
							<div class="row">
								<div class="col-md-9">
									<div class="avatar-wrapper" style="background:#2E3238"></div>
								</div>
								<div class="col-md-3">
									<div class="avatar-preview preview-lg"
										style="background:#2E3238"></div>
									<div class="avatar-preview preview-md"
										style="background:#2E3238"></div>
									<div class="avatar-preview preview-sm"
										style="background:#2E3238"></div>
								</div>
							</div>
							<div class="row avatar-btns">
								<div class="col-md-9">
									<div class="btn-group">
										<button class="btn btn-primary btn-raised"
											data-method="rotate" data-option="-90" type="button"
											title="Rotate -90 degrees">向左旋转</button>
									</div>
									<div class="btn-group">
										<button class="btn btn-primary btn-raised"
											data-method="rotate" data-option="90" type="button"
											title="Rotate 90 degrees">向右旋转</button>
									</div>
								</div>
								<div class="col-md-3">
									<button class="btn btn-success btn-block avatar-save"
										type="button" onclick="uploadImg()">保存修改</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="margin:0; padding:0;">
		<div class="bodyer"></div>
		<!--header S==-->
		<jsp:include page="../../include/header.jsp" />
		<!--header E-->
		<!-- main -->
		<div class="container c-informwrap" id="new-container">
			<!--menu ==S-->
			<jsp:include page="../include/menu.jsp" />
			<!--menu ==S-->
			<div id="container">
				<div class="c-informright pull-left">
					<p style="position: relative;">
						<img src="${userEntity.photo_65 }" class="img-circle img"
							width="54px" height="54px" /><span class="ml-10">${userEntity.nickName }</span><a
							class="img_editor" data-toggle="modal"
							data-target="#avatar-modal">编辑</a>
					</p>
					<p class="ft-16 pt-5 pb-20">编辑个人资料</p>
					<div class="c-centerwrap">
						<div class="form-group label-floating" style="">
							<input type="hidden" id="oldNickName" name="oldNickName" value="${userEntity.nickName }" >
							<label for="a_nickName" class="control-label">昵称</label>
							<input type="text" class="form-control" id="a_nickName" value="${userEntity.nickName }" onfocus="value='${userEntity.nickName } '"/>
							<span class="help-block" id="nickError"></span>
						</div>
						<!-- 展示的手机号框 -->
						<div class="form-group label-floating c_num" style="">
							<i class="iconfont icon-bianji iconnum c_greyicon"></i> <label
								for="mobileInfo" class="control-label">手机号码</label> <input
								type="text" class="form-control" id="mobileInfo" disabled="true"
								value="${userEntity.mobile }"> <span class="help-block"></span>
						</div>
						<div class="numwrap1">
							<div class="form-group label-floating " style="">
								<i class="iconfont icon-jianshao c_tip2 c_greyicon"></i> <label
									for="mobile" class="control-label">手机号码</label> <input
									type="text" class="form-control" disabled="true"
									value="${userEntity.mobile }" id="a_mobile" name="mobile">
							</div>
							<div class="form-group label-floating"
								style=" position: relative;float:left;width:48%;">
								<label for="mobileCaptcha" class="control-label">手机动态码</label>
								<button type="button" id="countdown_mobile"
									class="btn btn-primary btn-raised pull-right countdown_mobile"
									style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
									<strong>获取动态码</strong>
								</button>
								<input type="text" class="form-control" id="mobileCaptcha"
									style="padding-right:110px;"> <span class="help-block"
									id="mobileCaptchaError"></span>
								<button class="btn btn-primary btn-raised pull-right numbtn">确定</button>
							</div>
							<div class="form-group label-floating hide"
								style="position:relative;float:right;width:48%;">
								<label class="control-label">验证码</label> <input type="text"
									class="form-control text_input" name="captcha"
									id="imgCaptcha_mobile"
									style="padding-right:110px;text-align:left;margin-top:0;"
									onkeyup="user_checkImgCaptcha('imgCaptcha_mobile', 'countdown_mobile')">
								<span class="help-block" id="imgCaptchaError_mobile"></span> <img
									id="captchaImage_mobile" src="${ctx }/captcha"
									style="cursor:pointer; margin-left:10px; position: absolute; top:9px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;"
									class="pull-right" title="点击更换" />
							</div>
							<div class="fix"></div>
						</div>
						<div class="numwrap2">
							<div class="form-group label-floating" style="">
								<label for="newMobile" class="control-label">请输入新手机号</label> <input
									type="text" class="form-control" id="newMobile"
									name="newMobile"> <span class="help-block"
									id="newMobileError"></span>
							</div>
							<div class="form-group label-floating"
								style=" position: relative;float:left;width:48%;">
								<label for="newMobileCaptcha" class="control-label">手机动态码</label>
								<button type="button" id="countdown_newMobile" disabled="true"
									class="btn btn-primary btn-raised pull-right"
									style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
									<strong>获取动态码</strong>
								</button>
								<input type="text" class="form-control" id="newMobileCaptcha"
									style="padding-right:110px;"> <span class="help-block"
									id="newMobileCaptchaError"></span>
								<button class="btn btn-primary btn-raised pull-right numbtn1">保存</button>
							</div>
							<div class="form-group label-floating hide"
								style="position:relative;float:right;width:48%;">
								<label class="control-label">验证码</label> <input type="text"
									class="form-control text_input" name="captcha"
									id="imgCaptcha_newMobile"
									style="padding-right:110px;text-align:left;margin-top:0;"
									onkeyup="user_checkImgCaptcha('imgCaptcha_newMobile', 'countdown_newMobile')">
								<span class="help-block" id="imgCaptchaError_newMobile"></span>
								<img id="captchaImage_newMobile" src="${ctx }/captcha"
									style="cursor:pointer; margin-left:10px; position: absolute; top:9px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;"
									class="pull-right" title="点击更换" />
							</div>
							<div class="fix"></div>
						</div>
						<!-- 编辑邮箱 -->
						<div class="form-group label-floating c_email" style="">
							<i class="iconfont icon-bianji iconemail c_greyicon"></i> <label
								for="emailInfo" class="control-label">邮箱地址</label> <input
								type="email" class="form-control" id="emailInfo"
								disabled="disabled" value="${userEntity.email }"> <span
								class="help-block"></span>
						</div>
						<div class="emailwrap1">
							<div class="form-group label-floating " style="">
								<i class="iconfont icon-jianshao c_tip1 c_greyicon"></i> <label
									for="email" class="control-label"><c:if
										test="${empty userEntity.email }">请输入邮箱地址</c:if>
									<c:if test="${not empty userEntity.email }">邮箱地址</c:if></label> <input
									type="email" class="form-control" id="a_email"
									value="${userEntity.email }"
									<c:if test="${not empty userEntity.email }">disabled="true"</c:if>>
								<span class="help-block" id="emailError"></span>
							</div>
							<div class="form-group label-floating"
								style=" position: relative;">
								<label for="emailCaptcha" class="control-label">邮箱验证码</label>
								<button type="button" id="countdown_email"
									<c:if test="${empty userEntity.email }">disabled="true"</c:if>
									class="btn btn-primary btn-raised pull-right"
									style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
									<strong>获取验证码</strong>
								</button>
								<input type="text" class="form-control" id="emailCaptcha">
								<span class="help-block" id="emailCaptchaError"></span>
								<c:if test="${not empty userEntity.email }">
									<button class="btn btn-primary btn-raised pull-right embtn">确定</button>
								</c:if>
							</div>
						</div>
						<c:if test="${not empty userEntity.email }">
							<div class="emailwrap2">
								<div class="form-group label-floating" style="">
									<label for="newEmail" class="control-label">请输入新邮箱地址</label> <input
										type="text" class="form-control" id="newEmail"> <span
										class="help-block" id="newEmailError"></span>
								</div>
								<div class="form-group label-floating"
									style=" position: relative;">
									<label for="newEmailCaptcha" class="control-label">邮箱验证码</label>
									<button type="button" id="countdown_newEmail"
										class="btn btn-primary btn-raised pull-right"
										style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
										<strong>获取验证码</strong>
									</button>
									<input type="text" class="form-control" id="newEmailCaptcha">
									<span class="help-block" id="newEmailCaptchaError"></span>
									<button class="btn btn-primary btn-raised pull-right embtn1">保存新邮箱</button>
								</div>
							</div>
						</c:if>
						<div class="form-group label-floating password" style="">
							<i class="iconfont icon-bianji iconpwd c_greyicon"></i> <label
								for="password" class="control-label">密码</label> <input
								type="password" class="form-control" id="password"
								disabled="disabled" value="<c:if test='${not empty userEntity.password }'>********</c:if>"> <span class="help-block"></span>
						</div>
						<div class="passwordwrap">
							<div class="form-group label-floating" style="">
								<i class="iconfont icon-jianshao pasd_jiansha c_greyicon"></i> <label
									for="password" class="control-label">原密码</label> <input
									type="password" class="form-control" id="oldPassword">
								<span class="help-block" id="oldPasswordError"></span>
							</div>
							<div class="form-group label-floating" style="">
								<label for="newPassword" class="control-label">新密码</label> <input
									type="password" class="form-control" id="newPassword">
								<span class="help-block" id="newPasswordError"></span>
							</div>
							<div class="form-group label-floating" style="">
								<label for="a_rePassword" class="control-label">重新输入密码</label> <input
									type="password" class="form-control" id="a_rePassword">
								<span class="help-block" id="a_rePasswordError"></span>
							</div>
						</div>
						<%-- 支付密码start--%>
						<div class="form-group label-floating c_pay_password" style="">
							<i class="iconfont icon-bianji iconpaypassword c_greyicon"></i> <label
								for="payPasswordInfo" class="control-label">支付密码</label> <input
								type="password" class="form-control" id="payPasswordInfo"
								disabled="disabled" value="<c:if test='${not empty userEntity.payPassword }'>********</c:if>" /> <span
								class="help-block"></span>
						</div>
						<div class="paypasswordwrap">
							<div class="form-group label-floating " style="">
								<i class="iconfont icon-jianshao c_tip4 c_greyicon"></i> <label
									for="payPassword" class="control-label">请输入支付密码</label> <input
									type="password" class="form-control" id="payPassword">
								<span class="help-block" id="payPasswordError"></span>
							</div>
							<div class="form-group label-floating"
								style=" position: relative;float:left;width:48%;">
								<label for="payPasswordCaptcha" class="control-label">手机动态码</label>
								<button type="button" id="countdown_payPassword" disabled="true"
									class="btn btn-primary btn-raised pull-right countdown_mobile"
									style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
									<strong>获取动态码</strong>
								</button>
								<input type="text" class="form-control" id="payPasswordCaptcha"
									style="padding-right:110px;"> <span class="help-block"
									id="payPasswordCaptchaError"></span>
								<button
									class="btn btn-primary btn-raised pull-right payPasswordBtn">保存</button>
							</div>
							<div class="form-group label-floating hide"
								style="position:relative;float:right;width:48%;">
								<label class="control-label">验证码</label> <input type="text"
									class="form-control text_input" name="captcha"
									id="imgCaptcha_payPassword"
									style="padding-right:110px;text-align:left;margin-top:0;"
									onkeyup="user_checkImgCaptcha('imgCaptcha_payPassword', 'countdown_payPassword')">
								<span class="help-block" id="imgCaptchaError_payPassword"></span>
								<img id="captchaImage_payPassword" src="${ctx }/captcha"
									style="cursor:pointer; margin-left:10px; position: absolute; top:9px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;"
									class="pull-right" title="点击更换" />
							</div>
							<div class="fix"></div>
						</div>
						<%-- 支付密码end--%>
						<div class="form-group label-floating c_pay" style="">
							<i class="iconfont icon-bianji iconpay c_greyicon"></i> <label
								for="alipay" class="control-label">支付宝账号</label> <input
								type="text" class="form-control" id="alipay" disabled="disabled"
								value="${userEntity.alipayAccount }" /> <span class="help-block"></span>
						</div>
						<div class="apaywrap1">
							<div class="form-group label-floating " style="">
								<i class="iconfont icon-jianshao c_tip3 c_greyicon"></i> <label
									for="alipayAccount" class="control-label">请输入支付宝账户</label> <input
									type="text" class="form-control" id="alipayAccount"> <span
									class="help-block" id="alipayAccountError"></span>
							</div>
							<div class="form-group label-floating"
								style=" position: relative;float:left;width:48%;">
								<label for="alipayAccountCaptcha" class="control-label">手机动态码</label>
								<button type="button" id="countdown_alipay" disabled="true"
									class="btn btn-primary btn-raised pull-right countdown_mobile"
									style="position: absolute; top:0px; right: 0px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;">
									<strong>获取动态码</strong>
								</button>
								<input type="text" class="form-control"
									id="alipayAccountCaptcha" style="padding-right:110px;">
								<span class="help-block" id="alipayAccountCaptchaError"></span>
								<button class="btn btn-primary btn-raised pull-right paybtn">保存</button>
							</div>
							<div class="form-group label-floating hide"
								style="position:relative;float:right;width:48%;">
								<label class="control-label">验证码</label> <input type="text"
									class="form-control text_input" name="captcha"
									id="imgCaptcha_alipay"
									style="padding-right:110px;text-align:left;margin-top:0;"
									onkeyup="user_checkImgCaptcha('imgCaptcha_alipay', 'countdown_alipay')">
								<span class="help-block" id="imgCaptchaError_alipay"></span> <img
									id="captchaImage_alipay" src="${ctx }/captcha"
									style="cursor:pointer; margin-left:10px; position: absolute; top:9px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;"
									class="pull-right" title="点击更换" />
							</div>
							<div class="fix"></div>
						</div>
						<div class="form-group label-floating">
							<input type="hidden" id="oldSign" value="${userEntity.sign }">
							<label for="sign" class="control-label">个人简介</label>
							<textarea class="form-control" id="sign" name="sign">${userEntity.sign }</textarea>
							<span class="help-block" id="signError"></span>
						</div>
					</div>

					<div class="c-accountwrapright pull-left">
						<p class="ft-16 pt-5 pb-20">你的钱包</p>
						<ul>
							<li style="text-align:center; padding-top:15px;">
							<span><b style="width:35px;">￥</b></span>	
							<div style="height:15px; line-height:15px"><span id="userInfoGold">0</span> 金币</div>
							
								    <button type="button" class="btn" data-toggle="modal"
										data-target="#myModal5" id="userInfoRecharge">
										<strong>充值</strong>
									</button>
									
									 <button type="button" class="btn" onclick="clickWd();"
										 id="userInfoWithDraw" style=" border: 2px solid #3b9985; color:#3b9985">
										<strong>提现</strong>
									</button>
							
							</li>					
							
								<li>
									<span><b>￥</b></span>
									<span id="userInfoSysGold"></span>
									<span>G币</span>
								 	<code class="demoSpan1"></code> <a href="${ctx }/help/findByCode?code=htug">金币有什么用？</a>
								</li>
								
								
							<li style="line-height:50px; height:50px">
								<span class="fr" style="color:#5f7c8c">参与竞猜未结算的金币</span>
								<span id="userInfoJcGold">0 金币 </span>
							</li>
								
							<li style="line-height:50px; height:50px">
								<span class="fr" style="color:#5f7c8c">发起提现未到账的金币</span>
								<span id="userInfoWdGold">0 </span> 金币
							</li>
							
							
						
						</ul>
						<div class="fix"></div>
						<p class="ft-16 pt-5 pb-20">推荐好友</p>
						<p class="ft-14 choosefd">推荐您的好友在平台注册成功后，您将获得100个G币奖励，您的好友完成首次竞猜您将再获得200个G币奖励。</p>
						<div class="x-recommend-link">
							<p class="view1">
								<span class="one">推广链接</span><span class="copy-link two"
									id="d_clip_button_account" data-clipboard-action="copy"
									data-clipboard-text="${ip }register?fromuid=${user.campaignKey }">复制</span>
							</p>
							<p class="view2">
								<input type="text"
									value="${ip }register?fromuid=${user.campaignKey }"
									disabled="true">
							</p>
							<p class="view3">
								<a href="/my/exchangeLog">兑换</a><a href="/my/transactionLog"
									class="ml-20">交易记录</a>
							</p>
						</div>
					</div>
					<button class="btn btn-primary btn-raised pull-left savebtn"
						id="editBtn">保存修改</button>
				</div>
			</div>
			<!-- id="container" end -->
		</div>
		<!--footer ==S-->
		<jsp:include page="../../include/footer.jsp" />
		<!--footer ==S-->
	</div>
</body>
		</html>

	</c:otherwise>
</c:choose>
