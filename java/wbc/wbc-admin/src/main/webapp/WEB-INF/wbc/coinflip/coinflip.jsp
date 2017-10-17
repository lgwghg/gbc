<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../include/common.jsp"/>
	<meta charset="UTF-8">
	<meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<title>翻硬币</title>
	<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_cl5ct5ejudptx1or.css">
	<link rel="stylesheet" media="screen" href="${staticPrefix }/css/bootstrap-slider.css">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/rollcoins.css?v=${version }">
    <script type="text/javascript" src="${staticPrefix }/js/jquery.timers-1.2.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/circle_JT_min.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/bootstrap-slider.js"></script>
    <script type="text/javascript" src="${staticPrefix }/js/coinflip/coinflipSocket.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/coinflip/initSlider.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/coinflip/coinflip.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/coinflip/viewCoinflip.js?v=${version }"></script>
</head>
<body>
	<div class="bodyer"></div>
<div class="container-fluid" style="margin:0; padding:0;">
    <div id="inc_header"></div>
    <!--main-->
    <div class="container" style="padding:0;">
        <!--header S==-->
		<jsp:include page="../include/header.jsp" />
		<!--header E-->
        <!--通知 S==-->
		<jsp:include page="../include/message.jsp" /> 
        <div class="playdescription mt-20 hide">
            <span class="list-inform-three" style="transform: translateY(-10px);" id="coinmessage-close">×</span>
            <h2>提示 :</h2>
            <p>翻硬币是娱乐性游戏，网站在开奖结算时抽取2%金币，请玩家理性游戏，切勿沉迷！</p>
        </div>
         <div class="createvent mt-20">
            <div class="header">
	           	<div class="wrap1">
	            	<i class="c-point"></i><span>游戏总览</span>
	            </div>
	            <div class="wrap2">
	            	<!-- data-toggle="modal" data-target="#createCoinflipRoom" -->
	            	<button type="button" class="btn creatcoin"   id="createCoinflipRoomBtn">创建</button>
	            </div>
            </div>
         	<article>
				<div class="aigold">
					<p id="totalGoldNum">0</p>
					<p>金币数</p>
				</div>
				<div class="aicount">
					<p id="playingNum">0</p>
					<p>游戏个数</p>
				</div>
         	</article> 
        </div>
        <div class="rollcoins mt-20">
        	<div class="header">
				<div class="wrap1">
	            	<i class="c-point"></i><span>翻硬币</span>
	            </div>
	            <div class="wrap2">
					<ul>
						<li><a href="${path }/coinflip/u/history">我的历史</a></li>
						<li><a href="${path }/coinflip/history">历史记录</a></li>
					</ul>
	            </div>
	        </div>
	        <div class="rc-title">
				<div class="wrap1"><span>玩家</span></div>
				<div class="wrap2"><span>金币</span></div>
	        </div>
	        <div class="rc-list" id="roomList">
				
	        </div>
        </div>
        </div>
        </div>
        <jsp:include page="../include/footer.jsp" />
        
        <jsp:include page="coinflipDialog.jsp" />
</body>
<script>
	$.material.init();
</script>
</html>