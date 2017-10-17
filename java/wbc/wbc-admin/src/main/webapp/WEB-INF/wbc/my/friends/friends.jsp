<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${header['X-PJAX']}">
		<jsp:include page="../../include/common_pjax.jsp"/>
		<title>推荐好友${seoConfigMap['1'].title }</title>
    	<meta name="keywords" content="${seoConfigMap['1'].keywords }">
		<meta name="description" content="${seoConfigMap['1'].description }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/x-recommend-friend.css?v=${version }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/deal.css?v=${version }">
		<script type="text/javascript" src="${staticPrefix }/js/ZeroClipboard.min.js?v=${version }"></script>
		<script type="text/javascript" src="${staticPrefix }/js/my/friends/friends.js?v=${version }&t=<%=Math.random() %>"></script>
		<div class="c-informright pull-left">
        	<p class="ft-16 pb-20">推荐好友</p>
            <p class="ft-14 x-recommend-title">推荐您的好友在平台注册成功后，您将获得100个G币奖励。</p>

            <div class="x-recommend-link" style="width: 100%">
                <p class="view1"><span class="one">推广链接</span><span class="copy-link two" id="d_clip_button_friends" data-clipboard-action="copy" data-clipboard-text="${ip }register?fromuid=${user.campaignKey }">复制</span></p>

                <p class="view2"><input style="width: 400px;" type="text" value="${ip }register?fromuid=${user.campaignKey }" disabled="disabled"></p>

                <p class="view3"><a href="${path }/shoppingMall">兑换</a><a class="ml-20" href="${path }/my/transactionLog">交易记录</a></p>
            </div>
            <p class="ft-16 pt-5 pb-20">推荐历史</p>

            <p class="ft-14 x-recommend-title">您的好友完成首次竞猜，您将再获得200个G币的奖励。</p>

            <div class="table-responsive" style="border: none;">
                <table class="table">
                    <thead class="ft-14">
	                    <tr>
	                        <th width="30%;">会员昵称</th>
	                        <th width="30%;">时间</th>
	                        <th class="text-center">奖励</th>
	                    </tr>
                    </thead>
                    <tbody id="friends-tbody"></tbody>
                </table>
            </div>
            <nav>
				<ul id="friendsPage" class="pagination"></ul>
			</nav>
        </div>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html xmlns:og="http://ogp.me/ns#" class="alt-layout">
		<head>
		    <jsp:include page="../../include/common.jsp"/>
		    <title>推荐好友${seoConfigMap['1'].title }</title>
		    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
			<meta name="description" content="${seoConfigMap['1'].description }">
		    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/x-recommend-friend.css?v=${version }">
		    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
		    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/deal.css?v=${version }">
		    <script type="text/javascript" src="${staticPrefix }/js/ZeroClipboard.min.js?v=${version }"></script>
		    <script type="text/javascript" src="${staticPrefix }/js/my/friends/friends.js?v=${version }"></script>
		</head>
		<body>
		<div class="container-fluid" style="margin:0; padding:0;">
			<div class="bodyer"></div>
			<!--header S==-->
		    <jsp:include page="../../include/header.jsp"/>
		    <!--header E-->
		    
		    <!-- main S-->
		    <div class="container c-informwrap" id="new-container">
		        <!--menu ==S-->
		    	<jsp:include page="../include/menu.jsp"/>
				<!--menu ==S-->
				<div id="container">
					<div class="c-informright pull-left">
			        	<p class="ft-16 pb-20">推荐好友</p>
			            <p class="ft-14 x-recommend-title">推荐您的好友在平台注册成功后，您将获得100个G币奖励。</p>
			
			            <div class="x-recommend-link" style="width: 100%">
			                <p class="view1"><span class="one">推广链接</span><span class="copy-link two" id="d_clip_button_friends" data-clipboard-action="copy" data-clipboard-text="${ip }register?fromuid=${user.campaignKey }">复制</span></p>
			
			                <p class="view2"><input style="width: 400px;" type="text" value="${ip }register?fromuid=${user.campaignKey }" disabled="disabled"></p>
			
			                <p class="view3"><a href="${path }/shoppingMall">兑换</a><a class="ml-20" href="${path }/my/transactionLog">交易记录</a></p>
			            </div>
			            <p class="ft-16 pt-5 pb-20">推荐历史</p>
			
			            <p class="ft-14 x-recommend-title">您的好友完成首次竞猜，您将再获得200个G币的奖励。</p>
			
			            <div class="table-responsive" style="border: none;">
			                <table class="table">
			                    <thead class="ft-14">
				                    <tr>
				                        <th width="30%;">会员昵称</th>
				                        <th width="30%;">时间</th>
				                        <th class="text-center">奖励</th>
				                    </tr>
			                    </thead>
			                    <tbody id="friends-tbody"></tbody>
			                </table>
			            </div>
			            <nav>
							<ul id="friendsPage" class="pagination"></ul>
						</nav>
			        </div>
				</div>
		    </div>
		    <!-- main E-->
		    
		    <!--footer ==S-->
		    <jsp:include page="../../include/footer.jsp"/>
			<!--footer ==S-->
			
		</div>
		</body>
		</html>
	</c:otherwise>
</c:choose>