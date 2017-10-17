<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns:og="http://ogp.me/ns#" class="alt-layout">
<head>
<jsp:include page="../include/common.jsp" />
<title>${gbInfo.ht.teamName } VS ${gbInfo.at.teamName }_${gbInfo.game.gameName }${seoConfigMap['1'].title }</title>
<meta name="keywords" content="${gbInfo.ht.teamName } VS ${gbInfo.at.teamName }，${gbInfo.game.gameName }">
<meta name="description" content="">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/bootstrap-slider.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/fallback.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/x-trapezoid-two.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/details.css?v=${version }">
<script type="text/javascript" src="${staticPrefix }/js/bootstrap-slider.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/x-trapezoid.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/match/common.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/match/details.js?v=${version }"></script>



</head>
<body>
	<input type="hidden" id="gbId" value="${gbInfo.id }"/>
    <div class="fakeloader"></div>
	<div class="container-fluid" style="margin:0; padding:0;">
		<!--header S==-->
		<jsp:include page="../include/header.jsp" />
		<!--header E-->

		<div class="container" id="new-container" style="padding: 0;position: relative;z-index: 1;">
			<!--通知 S==-->
			<jsp:include page="../include/message.jsp" />
			<!--通知 E-->
			<div>
	            <ul class="breadcrumb mt-5 mb-5" style="background:none; font-size:14px; padding: 10px 0;">
	                <li><a href="${path }/match">首页</a></li>
	                <li><a href="${path }/match/${gbInfo.game.englishName }">${gbInfo.game.gameName }</a></li>
	                <li><a href="" id="gbName">${gbInfo.ht.teamName } VS ${gbInfo.at.teamName }</a></li>
	                <input type="hidden" id="gbId" value="${gbInfo.id }">
	                <input type="hidden" id="gbType" value="${gbInfo.gbType }">
	                <input type="hidden" id="startTime" value="${gbInfo.startTime }">
	            </ul>
	        </div>
			<div class="row">
				<div class="col-md-6" id="c-dbwrap">
	                <div class="c-guess">
	                	<!-- middle -->
						<div class="list-main-middle-a">
							<div class="list-main-middle">
								<ul class="match-list" id="gameBattleList">
									<!-- 比赛对战列表 -->
	
								</ul>
							</div>
						</div>
						<div id="nobet" class="match-list-bottom-pour hide">
							<p class="view1">
								<span class="co_be">投注金额</span> <span class="one ml-5"></span> <input
									type="text" class="ml-5 ft_18_bo" placeholder="0">
							</p>
							<p class="mt-20">
								<input class="ex8" data-slider-id='ex1Slider' type="text"
									data-slider-min="0" data-slider-max="100" data-slider-step="1"
									data-slider-value="1" />
							</p>
							<p class="view4">
								<span class="one">0金币</span> <a class="two" href="javascript:;">All
									in</a>
							</p>
							<p class="view1">
								<span class="co_be">可获得收益：</span> <span
									class="co_ff ft_18_bo earnings">0</span>
							</p>
							<button class="view3 ft_18_bo btn btn-primary btn-raised"
								onclick="userJc(true)">确认投注</button>
						</div>
						<!--新玩法下注-->
					    <div id="new_play_nobet" class="x-match-list-bottom-pour hide">
					        <p class="view1">
					            <span class="co_be">投注金额</span>
					            <span class="one ml-5"></span>
					            <input type="text" class="ml-5 ft_18_bo" placeholder="100">
					        </p>
					        <p class="mt-20">
					            <input class="ex8" data-slider-id="ex1Slider" type="text" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="1"/>
					            <a class="new-two" href="javascript:;">All in</a>
					        </p>
					        <p class="view1 mt-5" id="newdochange">
					            <span class="co_be">收益≈</span>
					            <span class="co_ff ft_18_bo earnings" style="min-width: 95px;">0</span>
					            <button class="view3 ft_18_bo btn btn-primary btn-raised" onclick="userJc(false)">确认投注</button>
					        </p>
					        <p class="view1 mt-5 hide" id="newnochange">
					            <span class='co_be'>请先选择投注队伍</span>
					        </p>
					    </div>
						<!--未登录，未下注，未选择队伍-->
						<div id="onUser" class="match-list-bottom-pour hide">
							<p class="match-list-choose"></p>
						</div>
						
						<!-- 投注人数 -->
						<div class="vote mt-15">
	                        <div class="c-titlewrap mb-20">
	                            <div class="pull-left ml-20">
	                             		   投<span class="mr-20 ml-5">${gbInfo.ht.teamName }</span>
	                                <span id="vote_homePrtNum"></span>
	                            </div>
	                            <div class="pull-right mr-20">
	                                <span class="mr-20" id="vote_awayPrtNum"></span>
	                           			     投<span class="ml-5">${gbInfo.at.teamName }</span>
	                            </div>
	                        </div>
	                        <div class="c-list1" id="homeUserJc">
	                            <!-- 主战队用户竞猜列表 -->
	                            
	                        </div>
	                        <div class="c-list2" id="awayUserJc">
	                            
	                        </div>
	                        <div class="gdyh-wrap">
	                        	<div class="c-gdyh" id="jc_list">加载更多</div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            <div class=" col-md-6">
					<!-- 视频 -->
		            <c:if test= "${ !empty gbInfo.videoUrl }">
	                 	<div class="c-video" style="margin-top: 15px;">
	                     	<div class="video-wrap" style=" padding: 5px 0;" >
		                    	${gbInfo.videoUrl }
	                 		</div>
	                 	</div>
	                </c:if>
					<!-- 评论-->
					<jsp:include page="include/comment.jsp"/>
                </div>
				
			</div>
		</div>

		<!--footer ==S-->
		<jsp:include page="../../wbc/include/footer.jsp" />
		<!--footer ==S-->
	</div>
</body>
</html>
