<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${header['X-PJAX']}">
		<jsp:include page="../include/common_pjax.jsp" />
		<title>${selectedGame.gameName }${seoConfigMap['1'].title }</title>
		<meta name="keywords" content="${selectedGame.gameName }">
		<meta name="description" content="${selectedGame.gameName }">
		<script type="text/javascript" src="${staticPrefix }/js/match/common.js?v=${version }"></script>
		<script type="text/javascript" src="${staticPrefix }/js/match/index.js?v=${version }&t=<%=Math.random() %>"></script>
		<script type="text/javascript" src="${staticPrefix }/js/qrcode/jquery.qrcode.js?v=${version }&t=<%=Math.random() %>"></script>
		<script type="text/javascript" src="${staticPrefix }/js/qrcode/jquery.utf.js?v=${version }&t=<%=Math.random() %>"></script>
		<input type="hidden" id="gameId" value="${selectedGame.gameId }">
		<input type="hidden" id="englishName"
			value="${selectedGame.englishName }">
		<script>
      		//设置页面背景图
    		$("#c-bg").attr('style','background:url("${selectedGame.bgImg }") no-repeat center;');
      	</script>
		<!-- middle -->
		<div class="list-main-middle-a">
			<div class="list-main-middle">
				<h1 id="onClictGameName">${selectedGame.gameName }比赛</h1>

				<div class="middle-search">
					<a class="iconfont icon-sousuo" id="teamSearch"></a> <input
						type="text" alt="" placeholder="请输入队名" id="teamName">
				</div>
				<!--搜索框-->
				<ul class="middle-tab">
					<li class="middle-tab-border"><a href="javascript:;" gbstate="">全部</a></li>
					<li><a href="javascript:;" gbstate="0">未开始</a></li>
					<li><a href="javascript:;" gbstate="1">正在进行</a></li>
					<li><a href="javascript:;" gbstate="2">已结束</a></li>
					<span class="zhankai">展开 <img src="${staticPrefix }/images/zhankai.png"></span>
					<span class="shouqi hide"><img src="${staticPrefix }/images/shouqi.png">收起</span>
					<div></div>
				</ul>
				<ul class="match-list" id="gameBattleList">
					<!-- 比赛对战列表 -->

				</ul>
				<!-- 更多比赛 -->
				<div id="moreGb"></div>
			</div>
			<!--未投注-->
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
		</div>
		<!-- right -->
		<div class="list-main-right">
			<p class="view1">
				<span class="one"></span> 
				<span class="two ml-10 co_92">最近赛事</span>
				<span class="hide fr three"><img src="${staticPrefix }/images/right-zhankai.png"></span>
			</p>
			<ul class="view2" id="gameEvent-ul"></ul>
			<p class="load-more" id="gameEvent-p"></p>
		</div>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html xmlns:og="http://ogp.me/ns#" class="alt-layout">
<head>
<jsp:include page="../include/common.jsp" />
<title>${selectedGame.gameName }${seoConfigMap['1'].title }</title>
<meta name="keywords" content="${selectedGame.gameName }">
<meta name="description" content="${selectedGame.gameName }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/bootstrap-slider.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/fallback.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/x-trapezoid-two.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/x-list.css?v=${version }">
<script type="text/javascript" src="${staticPrefix }/js/bootstrap-slider.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/match/common.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/match/index.js?v=${version }"></script>
</head>
<body>

    <div class="fakeloader"></div>
	<div class="container-fluid" style="margin:0; padding:0;">
		<div class="c-bg" id="c-bg"
			style="background:url('${selectedGame.bgImg }') no-repeat center;"></div>
		<!--header S==-->
		<jsp:include page="../include/header.jsp" />
		<!--header E-->

		<div class="container" id="new-container"
			style="padding: 0;position: relative;z-index: 1;">

			<!--通知 S==-->
			<jsp:include page="../include/message.jsp" />
			<!--通知 E-->

			<div class="row">
				<!-- left -->
				<ul class="list-main-left" id="game-ul">
					<c:forEach items="${gameList }" var="game">
						<a href="${path }/match/${game.englishName}">
							<li <c:if test="${game.selected}">class="checked"</c:if>><img
								src="${game.littleImg }">
								<p class="ft-20 one">${game.gameName}</p>
								<p class="ft-16 two">${game.gbCount}场竞猜中</p></li>
						</a>
					</c:forEach>
				</ul>
				<div id="container">
					<input type="hidden" id="gameId" value="${selectedGame.gameId }">
					<input type="hidden" id="bgImg" value="${selectedGame.bgImg }">
					<input type="hidden" id="englishName"
						value="${selectedGame.englishName }">
					<!-- middle -->
					<div class="list-main-middle-a">
						<div class="list-main-middle">
							<h1 id="onClictGameName">${selectedGame.gameName }比赛</h1>

							<div class="middle-search">
								<a class="iconfont icon-sousuo" id="teamSearch"></a> <input
									type="text" alt="" placeholder="请输入队名" id="teamName">
							</div>
							<!--搜索框-->
							<ul class="middle-tab">
								<li class="middle-tab-border"><a href="javascript:;"
									gbstate="">全部</a></li>
								<li><a href="javascript:;" gbstate="0">未开始</a></li>
								<li><a href="javascript:;" gbstate="1">正在进行</a></li>
								<li><a href="javascript:;" gbstate="2">已结束</a></li>
								<span class="zhankai">展开 <img src="${staticPrefix }/images/zhankai.png"></span>
								<span class="shouqi hide"><img src="${staticPrefix }/images/shouqi.png">收起</span>
								<div></div>
							</ul>
							<ul class="match-list" id="gameBattleList">
								<!-- 比赛对战列表 -->

							</ul>
							<!-- 更多比赛 -->
							<div id="moreGb"></div>
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
					<!-- right -->
					<div class="list-main-right">
						<p class="view1">
							<span class="one"></span> 
							<span class="two ml-10 co_92">最近赛事</span>
							<span class="hide fr three"><img src="${staticPrefix }/images/right-zhankai.png"></span>
						</p>
						<ul class="view2" id="gameEvent-ul"></ul>
						<p class="load-more" id="gameEvent-p"></p>
					</div>
				</div>
			</div>
		</div>

		<!--footer ==S-->
		<jsp:include page="../../wbc/include/footer.jsp" />
		<!--footer ==S-->
	</div>
</body>
 	<script>
	   /*  
	   $(document).ready(function()
	    {
            $(".fakeloader").fakeLoader({
            	//var timeout="",
                timeToHide:1200,
                bgColor:"#fff",
                imagePath:_staticPrefix+"/images/loading_list.jpg"
            });
        });
	   */
	</script>
	</html>
	</c:otherwise>
</c:choose>
