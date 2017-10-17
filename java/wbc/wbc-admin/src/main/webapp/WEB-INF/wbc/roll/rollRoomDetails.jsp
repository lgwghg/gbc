<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns:og="http://ogp.me/ns#" class="alt-layout">
<head>
<jsp:include page="../include/common.jsp" />
<title>房间${rollRoom.roomCode }${seoConfigMap['1'].title }</title>
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/bootstrap-slider.css">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/jquery.mCustomScrollbar.css">
<link type="text/css" rel="stylesheet" href="${staticPrefix }/css/x-right.css">
<link type="text/css" rel="stylesheet" href="${staticPrefix }/css/x-list.css">
<link type="text/css" rel="stylesheet" href="${staticPrefix }/css/x-ornament-details.css">
<link type="text/css" rel="stylesheet" href="${staticPrefix }/css/details.css">

<script type="text/javascript" src="${staticPrefix }/js/bootstrap-slider.js"></script>
<script type="text/javascript" src="${staticPrefix }/js/roll/rollRoom.js"></script>
<script type="text/javascript" src="${staticPrefix }/js/jquery.mCustomScrollbar.concat.min.js"></script>

</head>
<body>
	<input id="roomId" type="hidden" value="${rollRoom.id }"/>
	<input id="roomUserId" type="hidden" value="${rollRoom.userId }"/>
	<jsp:include page="include/rollRoomForm.jsp" />
	<jsp:include page="include/rollPoolForm.jsp" />
	<div class="container-fluid" style="margin:0; padding:0;">
		<div class="c-bg"></div>
		<!--header S==-->
		<jsp:include page="../include/header.jsp" />
		<!--header E-->
		<div class="container" style="padding: 0;position: relative;z-index: 1;">
			<!--通知 S==-->
			<jsp:include page="../include/message.jsp" />
			<!--通知 E-->
			<div class="row">
				<!-- left -->
				<div class="col-md-6 ornament-left">
					<div class="ornament-left-explain ornament-left-title">
						<p class="view1">
							<span class="one"></span> <span class="two">房间说明</span>
							<!--活动已截止或活动正在进行按钮-->
							<c:choose>
								<c:when test="${rollRoom.userId eq userId }">
									<button type="button" class="btn btn-raised btn-primary four" data-target="#rollRoomForm" data-toggle="modal">
										<img src="${staticPrefix }/images/bianji.png">编辑活动
									</button>
								</c:when>
								<c:when test="${isEnd }">
									<button type="button" class="btn btn-raised btn-primary three">活动已截止</button>
								</c:when>
								<c:when test="${isJoin }">
									<button type="button" class="btn btn-raised btn-primary three">已参与</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-raised btn-primary four" onclick="joinRoll();" id="joinRoll">参加活动</button>
								</c:otherwise>
							</c:choose>
						</p>
						<div class="view2">
							<p class="one">
								<span class="co-af">活动时间：</span> 
								<span class="co-31">${rollRoom.startTime } 至  ${rollRoom.endTime }</span>
							</p>
							<p class="two">
								<span>活动简介：</span> 
								<span>${rollRoom.remarks }</span>
							</p>
						</div>
					</div>
					<div class="ornament-left-screen-a">
						<ul class="ornament-left-screen" id="rollNum_ul">
						</ul>
						<div class="view5 hide">
							<div class="one">×</div>
							<div class="middle-search">
								<a href="javascript:gotoRollNum();">GO</a> 
								<input type="text" id="gotoNum" alt="" placeholder="直达ROLL次">
							</div>
							<ul class="two" id="moreRollNum">
							</ul>
<!-- 							<div class="three"> -->
<!-- 								<ul class="pagination" id="pagination_num" style="margin-left: 0;"> -->
<!-- 								</ul> -->
<!-- 							</div> -->
						</div>
					</div>
					
					<!-- 奖品池 -->
					<div class="ornament-left-prize ornament-left-title">
						<p class="view1">
							<span class="one"></span> <span class="two">奖品池</span>
						</p>
						<ul class="view2" id="rollPool_ul">
						</ul>
						<div id="poolMore" class="item-more item-more-new hide">
					        <a href="javascript:getRollPool(true);">
					            <span class="sxpic"></span>加载更多
					        </a>
					    </div>
					    <div id="poolNoMore" class="item-record item-record-new hide">
					        <a href="javascript:void(0);"><span class="tzpic"></span>没有更多记录</a>
					    </div>
						<c:if test="${rollRoom.userId eq userId }">
							<div class="view3">
								<div class="form-group five">
									<div class="checkbox" style="margin-top:0;">
										<label> 
											<input type="checkbox" id="unique">该活动已经获奖的人员将不再获奖
										</label>
									</div>
								</div>
								<div class="form-group five">
									<div class="checkbox" style="margin-top:0;">
										<label> 
											<input type="checkbox" id="clear">ROLL完过后清除所有人员
										</label>
									</div>
								</div>

								<span class="one">中奖人数设置</span> 
								<input type="text" placeholder="1" value="1" class="two" id="winnerNum" onkeyup="changeWinnerNum();">
								<c:choose>
									<c:when test="${isEnd }">
										<button type="button" disabled="disabled">ROLL</button>
										<span class="four">*活动已结束，请重新设置活动时间</span>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn  btn-raised btn-primary three" onclick="addRollWinner();">ROLL</button>
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
					</div>
					
					<!--参与人员-->
					<div class="ornament-left-participation ornament-left-title" id="rollMemberDiv">
					
					</div>
					<div class="view1" style="text-align:center;" id="rollMemberView">
						<ul class="pagination" id="pagination_member" style="margin: 8.5px 0;">
						</ul>
					</div>
					
					<!--往期参与名单和结算结果-->
					<div class="ornament-left-participation-before ornament-left-title" id="rollWinnerDiv">
						<p class="view1">
							<span class="one"></span> <span class="two">本期结算结果</span> <span class="three" id="winnerNums"></span>
						</p>
						<ul class="view2" id="rollWinner_ul">
							
						</ul>
						<div class="view3">
							<ul class="pagination" id="pagination_win" style="margin-left: 0;">
							</ul>
						</div>
					</div>
				</div>
				<!-- right -->
				<div class="col-md-6 ornament-right">
					<div class="ornament-personal-information">
						<img src="${staticPrefix }/images/grxx_bg.png" class="bg">
						<div class="view1">
							<div class="left">
								<img src="${rollRoom.userPhoto}" class=""> <span class="lv-x lv-x-1"></span>
							</div>
							<div class="right">
								<div class="one">
									<span class="first">${rollRoom.userName }</span>
									<div class="second">
										<span>竞技达人</span> <span>竞技达人</span>
									</div>
								</div>
								<p class="two">${rollRoom.sign }</p>
							</div>
						</div>
						<ul class="view2">
							<c:if test="${rollRoom.sinaUrl ne ''}">
								<li onclick="targetUrl('${rollRoom.sinaUrl}');"><img src="${staticPrefix }/images/ornament_wb.png"> <span>新浪微博</span></li>
							</c:if>
							<c:if test="${rollRoom.roomUrl ne ''}">
								<li onclick="targetUrl('${rollRoom.roomUrl}');"><img src="${staticPrefix }/images/ornament_dy.png"> <span>直播间</span></li>
							</c:if>
						</ul>
					</div>
					<c:if test="${rollRoom.roomUrl ne ''}">
						<div class="ornament-left-title ornament--right-room">
							<p class="view1">
								<span class="one"></span> <span class="two">直播间</span>
							</p>
							<div class="view2">
								<img src="${staticPrefix }/images/rotate.gif" class="rotate">
								<div class="c-video" style="background: transparent;height: 333px;">
									<div class="video-wrap" style="padding:0px;">
										<embed id="videoId" width="565" height="313" allownetworking="all" allowscriptaccess="always" src="${rollRoom.roomUrl }" quality="high" bgcolor="#000" wmode="window"
											allowfullscreen="true" allowFullScreenInteractive="true" type="application/x-shockwave-flash">
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<!-- 评论 -->
					<input id="gbId" type="hidden" value="${rollRoom.id }"/>
					<input id="commentType" type="hidden" value="2"/>
					<span style="display: none;" id="gbName">${rollRoom.roomCode }</span>
					<jsp:include page="../match/include/comment.jsp"/>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../../wbc/include/footer.jsp" />
</body>
</html>

