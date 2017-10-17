<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="socket.jsp"/>
<link type="text/css" href="${staticPrefix }/css/header.css?v=${version }" rel="stylesheet">
<script type="text/javascript" src="${staticPrefix }/js/header.js?v=${version }"></script>
<c:if test="${empty loginButtonHide }">
<script type="text/javascript" src="${staticPrefix }/js/login/login.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/login/register.js?v=${version }"></script>
</c:if>  
<!--menu ==S-->
<div class="navbar navbar-default" id="c-navbar" style="padding: 0; background: #22262A;">
    <div class="container-fluid">
	    <input type="hidden" id="jcGold"/>
	    <input type="hidden" id="wdFirstGold"/>
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                        data-target=".navbar-responsive-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <h2 class="c-header-h2"><a href="${path }/index"><img src="${staticPrefix }/images/LOGO.png?v=${version }"></a></h2>
            </div>

            <div class="navbar-collapse collapse navbar-responsive-collapse">
                <ul class="c-nav nav navbar-nav navbar-right">
                	<c:choose>
                		<c:when test="${empty user }">
                		<c:if test="${empty loginButtonHide }">
                		<div class="pull-right">
			                <button type="button" class="btn btn-primary btn-raised br_8" style="margin:21px 1px;" data-toggle="modal" data-target="#myModal2">
			                    <strong>注 册</strong></button>
			                <button type="button" class="btn btn-default btn-raised br_8" style="margin:21px 1px;" data-toggle="modal" id="loginId">
			                    <strong>登 录</strong></button>
			            </div>
			            </c:if>
                		</c:when>
                		<c:otherwise>
	                		<li><a href="${path }/index"><i class="iconfont icon-index"></i>首页<div class="ripple-container"></div></a></li>
		                    <li><a href="${path }/shoppingMall"><i class="iconfont icon-shangcheng"></i>商城</a></li>
		                    <li><a href="${path }/coinflip"><i class="iconfont icon-fanyingbi"></i>翻硬币</a></li>
		                    <li><a href="${path }/roll"><i class="iconfont icon-touzi"></i>roll奖品</a></li>
		                    <li><a href="${path }/my/jc"><i class="iconfont icon-jingcai2"></i>你的竞猜</a></li>
		                    <li class="message"><a href="${path }/my/message"><i class="iconfont icon-wodetongzhi2"></i>你的通知<a href="#" class="hide" id="unread-count">0</a></a></li>
		                    <li class="dropdown">
		                    	<input type="hidden" id="userId" value="${user.id }"/>
		                        <a href="###" data-target="#" class="dropdown-toggle" data-toggle="dropdown" id="userdropdown">
		                            <img src="${user.photo_35 }" class="h-img img-circle"/>
										${user.nickName }
		                            <b class="caret"></b>
		                        </a>
		                        <ul class="dropdown-menu c-header-menu" style="padding-bottom: 0;right:-44px; z-index: 99999;">
		                            <li id="dm-1" style="margin-top: 2px;" onclick="return false;">
		                                <span><b style="position: relative;top: 1px;">￥</b></span>
		                                <span>金币</span><span id="headerMenuGold">0</span> 
		                                <span class="header-dropdown-line"></span>
		                            </li>
		                            <li>
		                                <span><b style="position: relative;top: 1px;">￥</b></span>
		                                <span>G币</span><span id="headerMenuSysGold">0</span>
		                                <span class="header-dropdown-line"></span>
		                                <a href="/my/userTask">免费领取G币</a>
		                            </li>
		                            <li id="dm-2">
		                            	<span>每日完成任务</span>
		                            	<span id="header_dailyTask">（0/5）</span>
		                            	<a href="/my/userTask">任务中心</a>
		                            </li>
		                            <li><a href="${path }/my/info" id="c_general1">编辑个人资料</a></li>
		                            <li><a href="${path }/my/exchangeLog" id="c_general2">兑换记录</a></li>
		                            <li><a href="${path }/my/transactionLog" id="c_general3">交易记录</a></li>
		                            <li>
		                            	<a href="${path }/my/friends" class="friends-one">推荐好友</a>
		                            	<a href="javascript:;" class="btn-money  btn-raised pull-right c_link5 friends-two" id="d_clip_button_head" data-clipboard-action="copy" data-clipboard-text="${ip }register?fromuid=${user.campaignKey }">复制分享</a>
		                            	<input type="hidden" value="${ip }register?fromuid=${user.campaignKey }" disabled="disabled">
		                            </li>
		                            <li><a href="${path }/logout" id="c_general4">退出</a></li>
		                            <div class="triangle_border_up">
                                		<span></span>
                            		</div>
                            		<span class="pull-right" data-toggle="modal" data-target="#myModal5" id="headerRecharge">充&nbsp;值</span>
                            		<span class="pull-right" onclick="clickWd();" id="headerWithdraw">提&nbsp;现</span>
		                            <!--未签到-->
		                            <!-- <button type="button" class="btn  btn-raised btn-primary pull-right" id="wqd">
		                                <strong>签到</strong>
		                            </button> -->
		                            <!--已签到-->
		                            <!-- <button type="button" class="btn  btn-raised btn-primary pull-right btn-yqd hide"  id="yqd" disabled>
		                                <strong>已签到</strong>
		                            </button> -->
		                        </ul>
		                    </li>
		                    <li style="height: 80px;line-height: 80px;padding-right: 10px;padding-left: 5px;" id="headerGold">0金币</li>
                		</c:otherwise>
                	</c:choose>
                </ul>
            </div>
        </div>
    </div>
</div>
<!--menu ==E-->
<!-- 充值弹框 -->
 <jsp:include page="recharge.jsp" />   
<!-- 提现弹框 -->
 <jsp:include page="withdraw.jsp" />   
<!-- 模态框（Modal） -->
<c:if test="${empty loginButtonHide }">
<!--登录弹框-->
<jsp:include page="../index/include/login_dialog.jsp" />
<!--注册弹框-->
<jsp:include page="../index/include/register_dialog.jsp" />
<!--收货地址弹框-->
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/inform.css?v=${version }">
<link type="text/css" rel="stylesheet" href="${staticPrefix }/css/goods/x-shopping-mall-details.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/jquery.dropdown.css?v=${version }">
<script type="text/javascript" src="${staticPrefix }/js/jquery.dropdown.js?v=${version }"></script>
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/exchange.css?v=${version }">
<script type="text/javascript" src="${staticPrefix }/js/common/cityselect.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/my/address/address-common.js?v=${version }"></script>
<jsp:include page="addressDialog.jsp" />
</c:if>