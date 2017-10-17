<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:choose>
	<c:when test="${header['X-PJAX']}">
		<jsp:include page="../include/common_pjax.jsp"/>
	    <title>${webNotice.title }${seoConfigMap['1'].title }</title>
		    <meta name="keywords" content="${webNotice.title }">
			<meta name="description" content="">
		<div id="showNotice">
			<input id="type" type="hidden" value="${webNotice.type }" />
			<input id="noticeId" type="hidden" value="${webNotice.id }" />
			<h1>${webNotice.title }</h1>
			<p class="author">
				<span>作者:&nbsp;</span>
				<span>${webNotice.sysUserName }</span>
				&nbsp;&nbsp;&nbsp;<span>时间:&nbsp;</span>
				${webNotice.addTimeStr }
		    </p><br/>
		    ${webNotice.content }
		</div>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html xmlns:og="http://ogp.me/ns#" class="alt-layout">
		  <head>
		    <jsp:include page="../include/common.jsp"/>
		    <title>${webNotice.title }${seoConfigMap['1'].title }</title>
		    <meta name="keywords" content="${webNotice.title }">
			<meta name="description" content="">
			<meta http-equiv="pragma" content="no-cache">
			<meta http-equiv="cache-control" content="no-cache">
			<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
		
		    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_7d5yvokkelgzaor.css?v=${version }">
		    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/details.css?v=${version }">
		    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/listarticle.css?v=${version }">
		    <script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
		    <script type="text/javascript" src="${staticPrefix }/js/system/webNotice.js?v=${version }"></script>
		
		    <style>
		        textarea{
		                height: 500px;
		                width: 90%;
		                margin: 10px 5%;
		                padding: 15px;
		                resize: none;
		                background: #282a2e;
		                outline: none;
		                border: none;
		                border-bottom: 1px solid #282a2e;
		                font-size: 14px;
		            }
		        textarea:focus{
		            background: #36383d;
		            border-bottom: 1px solid #36383d;
		        }
		        .aactive{
		        	color: #31C3A2;
		        }
		    </style>
		  </head>
		  
		  <body>
		    <!-- 头部 -->
		    <jsp:include page="../include/header.jsp"/>
		    
			<div class="container" id="new-container" style="padding: 0;position: relative;z-index: 1;">
			    <!-- 通知 -->
			    <jsp:include page="../include/message.jsp"/>
			    
			    <div>
		            <ul class="breadcrumb mt-5 mb-5" style="background:none; font-size:14px; padding: 10px 0;">
		                <li><a href="${ctx }/match">首页</a></li>
		                <li><a href="${ctx }/help">网站公告</a></li>
		            </ul>
		        </div>
		        <div class="left_ulistwrap pull-left">
		            <ul class="list" id="my-pjax">
		                <li>
			                <dl>     
				                <dt class="withripple">网站公告</dt>
				                <dd>
				                    <ul class="ulist" id="notice_1">
				                    </ul>    
				                </dd>
				            </dl>
		                </li>
		                <li>
			                <dl>     
				                <dt class="withripple">网站更新</dt>
				                <dd>
				                    <ul class="ulist" id="notice_2">
				                    </ul>    
				                </dd>
				            </dl>
		                </li>
		                <li>
			                <dl>     
				                <dt class="withripple">网站介绍</dt>
				                <dd>
				                    <ul class="ulist" id="notice_3">
				                    </ul>    
				                </dd>
				            </dl>
		                </li>
		                <li>
			                <dl>     
				                <dt class="withripple">使用帮助</dt>
				                <dd>
				                    <ul class="ulist" id="notice_4">
				                    </ul>    
				                </dd>
				            </dl>
		                </li>
		                <li>
			                <dl>     
				                <dt class="withripple">用户须知</dt>
				                <dd>
				                    <ul class="ulist" id="notice_5">
				                    </ul>    
				                </dd>
				            </dl>
		                </li>
		                <li>
			                <dl>     
				                <dt class="withripple" id="notice_6">
				                	用户反馈
				                </dt>
				                <dd>   
				                </dd>
				            </dl>
		                </li>
		            </ul>
		        </div>
			    
			    <div class="right_ulistwrap pull-left wordintroduce">
			    	<div id="container">
				    	<div id="showNotice">
				    		<input id="type" type="hidden" value="${webNotice.type }" />
				    		<input id="noticeId" type="hidden" value="${webNotice.id }" />
				    		<h1>${webNotice.title }</h1>
							<p class="author">
								<span>作者:&nbsp;</span>
								<span>${webNotice.sysUserName }</span>
								&nbsp;&nbsp;&nbsp;<span>时间:&nbsp;</span>
								${webNotice.addTimeStr }
						    </p><br/>
						    ${webNotice.content }
				    	</div>
			    	</div>
			    	<div id="showDiv" style="display: none;">
			           	<h1>用户反馈</h1>
			           	<div class="form-group label-floating pull-left" style=" width: 90%; margin: 10px 5%;">
				            <label for="title" class="control-label">请输入标题</label>
				            <input type="text" class="form-control" id="title">
				       	</div> 
			           	<textarea name="" id="contentMsg" placeholder="请输入你的意见"></textarea>
			           	<div class="form-group label-floating pull-left" style="width: 200px; margin-left: 55%;">
				            <label for="noticeCaptcha" class="control-label" style="font-size: 15px;">请输入验证码</label>
				            <img id="spans" src="${ctx }/captcha" style="background:#fff; cursor: pointer; height: 20px; width: 50px; text-align: center; position: absolute; top:7px; right: 0px;" title="点击更换"/>  
				            <input type="text" class="form-control" id="noticeCaptcha">
			          	</div> 
			            <button type="submit" class="btn btn-primary btn-raised pull-right" style="margin: 30px 5% 0 0;" onclick="saveNotice();"><strong>提交</strong></button>
			    	</div>
		        </div>
			</div>    
			
			<!-- 底部 -->
			<jsp:include page="../include/footer.jsp"/>
		  </body>
		</html>
	</c:otherwise>
</c:choose>

