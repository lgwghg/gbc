<%@page import="org.json.JSONObject"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.webside.common.config.service.ConfigService"%>
<%@page import="com.webside.util.SpringBeanUtil"%>
<%@page import="com.webside.common.GlobalConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("staticPrefix", request.getContextPath()+ "/resources/wbc");
	request.setAttribute("version", GlobalConstant.VERSION);
	//获取当前域名地址
	StringBuffer url = request.getRequestURL();
	String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(),url.length()).append("/").toString();

	if (StringUtils.isNotEmpty(tempContextUrl)) {
		//判断是否是真实域名
		if (tempContextUrl.indexOf("gbocai") > -1 && tempContextUrl.split(":").length == 3) {
			request.setAttribute("ip",tempContextUrl.substring(0,tempContextUrl.lastIndexOf(":"))+ "/");
		} else {
			request.setAttribute("ip", tempContextUrl);
		}
	}
	else
	{
		ConfigService configService = SpringBeanUtil.getBean(ConfigService.class);
		String domainUrl = configService.findConfigValueByKey("domain_url");
		if(StringUtils.isNotEmpty(domainUrl))
		{
			JSONObject domainUrlJson = new JSONObject(domainUrl);
			request.setAttribute("ip",  domainUrlJson.get("gbocai"));
		}
	}
%>
<script>
	var type = '${type}';
	var ip = '${ip}';
	var title = null;
	var url = null;
	var pic = null;
	if(type=='jc'){
		title = 'G菠菜竞猜分享  ${gbInfo.ht.teamName }（赔率X${pkVo.pkHomeRule }） ${gbInfo.gameRuleName } ${gbInfo.at.teamName }（赔率X${pkVo.pkAwayRule }） ${gbInfo.gevent.eventName }';
		url = ip + 'share/jc/show?g=${gbId}&u=${userCode }';
		pic = ip.substring(0, ip.length-1) + '${gbInfo.game.bigImg }';
	}else if(type=='f'){
		title = '300G币邀你体验G菠菜竞猜';
		url = ip + 'share/f/show?c=${c}';
		pic = ip.substring(0, ip.length-1) + '${user.photo_35 }';
	}
</script>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:if test="${type=='jc'}">
		<c:set var="title" value="G菠菜竞猜分享  ${gbInfo.ht.teamName }（赔率X${pkVo.pkHomeRule }） ${gbInfo.gameRuleName } ${gbInfo.at.teamName }（赔率X${pkVo.pkAwayRule }） ${gbInfo.gevent.eventName }"></c:set>
		<c:set var="name" value="G菠菜竞猜分享  ${gbInfo.ht.teamName }（赔率X${pkVo.pkHomeRule }） ${gbInfo.gameRuleName } ${gbInfo.at.teamName }（赔率X${pkVo.pkAwayRule }） ${gbInfo.gevent.eventName }"></c:set>
		<c:set var="shareImage" value="${gbInfo.game.bgImg }"></c:set>
		<c:set var="description" value="${gbInfo.ht.teamName }（赔率X${pkVo.pkHomeRule }） ${gbInfo.gameRuleName } ${gbInfo.at.teamName }（赔率X${pkVo.pkAwayRule }） ${gbInfo.gevent.eventName }"></c:set>
		<c:set var="imgUrl" value="${staticPrefix }/H5/images/banner.png"></c:set>
		<c:set var="shareUrl" value="${ip }share/jc/show?g=${gbId}&u=${userCode }"></c:set>
	</c:if>
	<c:if test="${type=='f'}">
		<c:set var="title" value="300G币邀你体验G菠菜竞猜"></c:set>
		<c:set var="name" value="300G币邀你体验G菠菜竞猜"></c:set>
		<c:set var="shareImage" value="${user.photo_35 }"></c:set>
		<c:set var="description" value="${user.nickName }送你注册大礼包！"></c:set>
		<c:set var="imgUrl" value="${staticPrefix }/H5/images/banner2.png"></c:set>
		<c:set var="shareUrl" value="${ip }share/f/show?c=${c}"></c:set>
	</c:if>
	<title>${title }</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width; initial-scale=1.0; minimum-scale=0.5; maximum-scale=2.0">
	<meta name="MobileOptimized" content="320">
	<meta name="format-detection" content="telephone=no" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta itemprop="name" content="${name }" />
	<meta itemprop="image" content="${shareImage }" />
	<meta name="description" itemprop="description" content="${description }" />
	<link rel="stylesheet" type="text/css" media="all" href="${staticPrefix }/H5/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" media="all" href="${staticPrefix }/H5/css/share_y.css">
	<link href="http://at.alicdn.com/t/font_3fwbuc7zqcl3di.css" rel="stylesheet" type="text/css" media="all">
	<script type="text/javascript" src="${staticPrefix }/js/jquery.min.js?v=${version }"></script>
	<script type="text/javascript" src="${staticPrefix }/js/bootstrap.min.js?v=${version }"></script>
	<script type="text/javascript" src="${staticPrefix }/js/clipboard.min.js?v=${version }"></script>
	<script type="text/javascript" src="${staticPrefix }/js/base64.min.js?v=${version }"></script>
	<script type="text/javascript" src="${staticPrefix }/H5/js/share/nativeShare.js?v=${version }"></script>
	<script type="text/javascript" src="${staticPrefix }/H5/js/share/index.js?v=${version }"></script>
	<style type="text/css">
		.bsFrameDiv{ background:Red}
	</style>
</head>
<body style="position: relative;">
	<div id="wx-div" class="initbanner" style="display: none">
		<img src="${staticPrefix }/images/initbanner.png" alt="">
	</div>
	<div id="not-wx-div" style="display: none">
		<div class="banner">
			<img src="${imgUrl }">
		</div>
		<div class="main">
			<div class="menu">
				<div class="nav-tabs">
					<ul style="border:none">
						<li style="display: none" id="weixin-friend-li" class="nativeShare" data-app="weixin">
							<a href="#" role="tab" data-toggle="tab">
								<span class="iconfont icon-fenxiangweixinhaoyou"></span>
								<h5>微信好友</h5>
							</a>
						</li>
						<li style="display: none" id="weixin-timeline-li" class="nativeShare" data-app="weixinFriend">
							<a href="#" role="tab" data-toggle="tab">
								<span class="iconfont icon-weixin"></span>
								<h5>微信朋友圈</h5>
							</a>
						</li>
						<li>
							<a href="#" onclick="shareToQQ()" role="tab" data-toggle="tab"> 
								<span class="iconfont icon-qq"></span>
								<h5>QQ好友</h5>
							</a>
						</li>
						<li>
							<a href="#" onclick="shareToQQKongJian()" role="tab" data-toggle="tab"> 
								<span class="iconfont icon-qqkongjian"></span>
								<h5>QQ空间</h5>
							</a>
						</li>
						<li>
							<a href="#" onclick="shareToSinaWeibo()" role="tab" data-toggle="tab">
								<span class="iconfont icon-weibo"></span>
								<h5>新浪微博</h5>
							</a>
						</li>
						<li style="display: none" id="copy-li">
							<a href="#" id="share_copy_clip" data-clipboard-action="copy" data-clipboard-text="${shareUrl }"  data-toggle="tab">
								<span class="iconfont icon-lianjie1"></span>
								<h5>复制链接</h5>
							</a>
						</li>
					</ul>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</body>
</html>
