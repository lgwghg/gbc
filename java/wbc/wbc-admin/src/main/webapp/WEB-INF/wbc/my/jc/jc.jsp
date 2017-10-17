<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${header['X-PJAX']}">
		<jsp:include page="../../include/common_pjax.jsp"/>
		<title>你的竞猜${seoConfigMap['1'].title }</title>
		<meta name="keywords" content="${seoConfigMap['1'].keywords }">
		<meta name="description" content="${seoConfigMap['1'].description }">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }&t=<%=Math.random() %>">
		<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/guessing.css?v=${version }&t=<%=Math.random() %>">
	    
	    <script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
	    <script type="text/javascript" src="${staticPrefix }/js/common/Chart-1.0.1-beta.4.js?v=${version }"></script>
	    <script type="text/javascript" src="${staticPrefix }/js/my/jc/jc.js?v=${version }&t=<%=Math.random() %>"></script>
	    
		<div class="md-center">
            <p class="link-white ft-16">你的竞猜</p>
            <div class="year mt-10">
                <div style="padding:0 10px;">
                    <h5>
                        <img id="photo" src="${staticPrefix }/images/denglu_n_35.png" class="img-circle h-img" style="margin-right:5px">
                        <strong id="nickName"></strong>
                        　					<span class="blackish-green"><strong id="userGold"></strong></span> 金币
                    </h5>
                </div>
                <div class="guess_frequency">
                    <ul>
                        <li>共参加有效竞猜 <font class="blackish-green" id="jcNum"></font> 次</li>
                        <li>获胜 <font class="blackish-green" id="victoryNum"></font> 次</li>
                        <li>胜率 <font class="blackish-green" id="victoryRate"></font></li>
                    </ul>
                </div>

                <div class="pd10">
                    <p>本周收益： <font class="blackish-green" id="weekProfit"></font> 金币</p>

                    <div class="echar mt-30">
                        <canvas id="myChart" width="457" height="164"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <div class="md-right" style="margin-top:34px;">
            <div class="profit_top" id="profitTopList-div">
            </div>
        </div>
        
        <div class="row pull-right nrm" style="margin: 15px 5px 5px 5px;">
            <div class="pull-left link-white">已参与的比赛</div>
            <div class="pull-right link-gray6">
                <span class="badge2 badge_orange">W</span>赢
                <span class="badge2 badge_gray">L</span>输
                <span class="badge2 badge_green">N</span>待结束
                <span class="badge2" style="background: #4470B1;">!</span>已取消
            </div>

            <div class="fix"></div>
            <table width="100%" border="0" class="fl mt-15 match" id="dataList" style="transform: translateX(-4px);">
            </table>

			<nav style="text-align: center;">
	          <ul class="pagination" id="pagination" style="margin-left: 0;"> </ul>
	      	</nav>
        </div>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html xmlns:og="http://ogp.me/ns#" class="alt-layout">
			<head>
			    <jsp:include page="../../include/common.jsp"/>
			    <title>你的竞猜${seoConfigMap['1'].title }</title>
			    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
				<meta name="description" content="${seoConfigMap['1'].description }">
			    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
			    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/guessing.css?v=${version }">
			    
			    <script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
			    <script type="text/javascript" src="${staticPrefix }/js/common/Chart-1.0.1-beta.4.js?v=${version }"></script>
			    <script type="text/javascript" src="${staticPrefix }/js/my/jc/jc.js?v=${version }"></script>
			</head>
			<body>
				<div class="bodyer"></div>
				<jsp:include page="../../include/header.jsp"/>
				
				<div id="new-container" class="container c-informwrap">
		        	<div class="row" style="margin:0px;">
		        		<div style="padding:0;">
					        <jsp:include page="../include/menu.jsp"/>
		        		</div>
		        		<div id="container">
				            <div class="md-center">
				                <p class="link-white ft-16">你的竞猜</p>
				                <div class="year mt-10">
				                    <div style="padding:0 10px;">
				                        <h5>
				                            <img id="photo" src="${staticPrefix }/images/denglu_n_35.png" class="img-circle h-img" style="margin-right:5px">
				                            <strong id="nickName"></strong>
				                            　					<span class="blackish-green"><strong id="userGold"></strong></span金币金币
				                        </h5>
				                    </div>
				                    <div class="guess_frequency">
				                        <ul>
				                            <li>共参加有效竞猜 <font class="blackish-green" id="jcNum"></font> 次</li>
				                            <li>获胜 <font class="blackish-green" id="victoryNum"></font> 次</li>
				                            <li>胜率 <font class="blackish-green" id="victoryRate"></font></li>
				                        </ul>
				                    </div>
				
				                    <div class="pd10">
				                        <p>本周收益： <font class="blackish-green" id="weekProfit"></font> 金币</p>
				
				                        <div class="echar mt-30">
				                            <canvas id="myChart" width="457" height="164"></canvas>
				                        </div>
				                    </div>
				                </div>
				            </div>
				
				            <div class="md-right" style="margin-top:34px;">
				                <div class="profit_top" id="profitTopList-div">
				                </div>
				            </div>
				            
				            <div class="row pull-right nrm" style="margin: 15px 5px 5px 5px;">
				                <div class="pull-left link-white">已参与的比赛</div>
				                <div class="pull-right link-gray6">
				                    <span class="badge2 badge_orange">W</span>赢
				                    <span class="badge2 badge_gray">L</span>输
				                    <span class="badge2 badge_green">N</span>待结束
				                    <span class="badge2" style="background: #4470B1;">!</span>已取消
				                </div>
				
				                <div class="fix"></div>
				                <table width="100%" border="0" class="fl mt-15 match" id="dataList">
				                </table>
								
								<nav style="text-align: center;">
						            <ul class="pagination" id="pagination" style="margin-left: 0;"> </ul>
						        </nav>
				            </div>
		        		</div>
			        </div>
			
			    </div>
				<jsp:include page="../../include/footer.jsp"/>
			</body>
		</html>
	</c:otherwise>
</c:choose>