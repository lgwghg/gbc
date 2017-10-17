<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.webside.common.GlobalConstant"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("staticPrefix",  request.getContextPath()+"/resources/wbc");
request.setAttribute("path", path);
request.setAttribute("version", GlobalConstant.VERSION);
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-Cache" />
<meta name="viewport" content="width=device-width; initial-scale=1.0; minimum-scale=0.5; maximum-scale=2.0">
<meta name="MobileOptimized" content="320">
<meta name="format-detection" content="telephone=no" />
<meta itemprop="name" content="300G币邀你体验G菠菜竞猜"/>
<meta name="description" itemprop="description" content="G菠菜是电子竞技菠菜/竞猜娱乐平台.这里有:王者荣耀KPL联赛,DOTA2CDEC联赛等直播赛事竞猜项目,注册即赠G币.">
<meta itemprop="image" content="${staticPrefix }/images/LOGO.png?v=1.3.1" />
<title>300G币邀你体验G菠菜竞猜  | G菠菜是电子竞技菠菜/竞猜娱乐平台.这里有:王者荣耀KPL联赛,DOTA2CDEC联赛等直播赛事竞猜项目,注册即赠G币.</title>
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_3fwbuc7zqcl3di.css">
<link  href="${staticPrefix }/H5/css/global.css?v=${version }"  rel="stylesheet"  type="text/css"/>
</head>
<body>
<%-- <c:if test="${not empty param.f && param.f=='fx'}">
	<div class="initbanner">
        <img src="${staticPrefix }/images/initbanner.png" alt="">
         <div class="xxx">
          <i class="iconfont icon-guanbi"></i></div>  
    </div>
</c:if> --%>
  
<div class="main">
  <div class="header">
    <div class="logo"><a href="###"><img src="${staticPrefix }/H5/images/logo.png"></a></div>
    <h2 class="big_title"><a href="###"><img src="${staticPrefix }/H5/images/tb.png"></a></h2>
    <div class="wx_rg"><div class="r"><img src="${userPhoto }" class="img icor"></div><c:if test="${not empty userNick }">${userNick }，送你注册大礼包！</c:if><c:if test="${empty userNick }">注册就有大礼包！</c:if></div>
    <div class="small_title"><a href="###"><img src="${staticPrefix }/H5/images/ts.png"></a></div>
    <div class="tc"> <img src="${staticPrefix }/H5/images/money.png" class="m_img"> </div>
  </div>  
     
    <div class="wxtext_box"> 
        <button type="button" class="btn">领取并注册</button>
        <c:if test="${not empty inviteCode }">
        <h4 class="mt20">请在注册过程中邀请码一栏输入</h4>
        <h2 class="mt5">${inviteCode }</h2>
        </c:if>
    </div>
   
         <div class="flip">查看活动规则</div>
         <div class="panel">
         	<p>1. 活动期间，被邀请人通过邀请人分享的邀请链接并正确填写邀请人的邀请码开通G菠菜账户，可获赠300G币；</p>
			<p>2. 被邀请人完成注册，邀请人可获赠100G币；被邀请人完成首次竞猜，邀请人可再获赠200G币；</p>
			<p>3. 被邀请人奖励一次性发放，同一被邀请人仅可获赠一次奖励；邀请人邀请奖励可叠加；奖励也将即时发放至您的账户；</p>
			<p>4. 请确保您接受邀请所输入的手机号未在G菠菜网注册账户；</p>
			<p>5. G币使用时间及规则详见G菠菜G币使用规则；</p>
			<p>6. 对于恶意刷奖的用户，一经发现，G菠菜有权取消其领取活动奖励的资格，并保留相关法律权利；</p>
			<p>7. G菠菜保留此活动最终解释权。</p>
         </div>
         
</div>
</body>
<script type="text/javascript" src="${staticPrefix }/H5/js/jquery.js"></script>	
<script type="text/javascript">
var _path = '${path}';
var _staticPrefix = '${staticPrefix}';
$(function ($){
	$(".flip").toggle(function(){
		$(".panel").slideDown("slow");
		$(this).addClass('down')
	},
	function(){
   		$(".panel").slideUp("slow");
   		$(this).removeClass('down');
	});
	var inviteCode = '${inviteCode}';
	$(".wxtext_box button").on("click", function() {
		var param = "";
		if (inviteCode != null && inviteCode != '') {
			param = "?c=" + inviteCode;
		}
		window.location.href = _path + "/share/f/reg"+param;
	});
});
$(function(){
    $(".icon-guanbi").click(function(){
        $(this).closest(".initbanner").hide();
    });
});
</script>
</html>

