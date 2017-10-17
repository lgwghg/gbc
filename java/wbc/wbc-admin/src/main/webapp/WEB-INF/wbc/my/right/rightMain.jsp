<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/base.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/x-right.css?v=${version }">
<script src="${staticPrefix }/js/login/rightLogin.js?v=${version }"></script>
<script src="${staticPrefix }/js/my/right/rightMain.js?v=${version }"></script>


<input type="hidden" id="userPhoto35" value="${user.photo_35 }">
<input type="hidden" id="userCampaignKey" value="${user.campaignKey }">

<!--right-->
<div class="inc_right hide">
    <!--left-->
    <div class="inc_right_tabs_big">
        <ul class="inc_right_tabs inc_right_tabs_middle">
            <li class="inc_right_tabs_one inc_right_tabs_three inc_right_tabs_four inc_right_tabs_account">
                <div id="right_accountIcon" class="inc_right_tabs_inside">
                	<c:if test="${empty user }">
                    <span class="iconfont icon-logo"></span>
                    </c:if>
                    <c:if test="${not empty user }">
                    <img src="${user.photo_35 }">
                    </c:if>
                    <span class="hint_label hide">你的账户</span>
                </div>
            </li>
            <li class="inc_right_tabs_two inc_right_tabs_four inc_right_tabs_history" >
                <div class="inc_right_tabs_history_small inc_right_tabs_inside">
                    <p>
                        <span class="iconfont icon-youcetouzhulishi"></span>
                        <span class="mt-5">投注历史</span>
                    </p>
                </div>
            </li>
            <li class="inc_right_tabs_one inc_right_tabs_three inc_right_tabs_four inc_right_tabs_record" >
                <div class="inc_right_tabs_inside">
                    <span class="iconfont icon-youceduihuanjilu"></span>
                    <span class="hint_label hide">兑换记录</span>
                </div>
            </li>
            <li class="inc_right_tabs_one inc_right_tabs_three inc_right_tabs_four inc_right_tabs_inform" >
                <div class="inc_right_tabs_inside">
                    <span class="iconfont icon-wodetongzhi2"></span>
                    <span class="hint_label hide">你的通知</span>
                </div>
            </li>
            <li class="inc_right_tabs_one inc_right_tabs_three inc_right_tabs_four inc_right_tabs_friend" >
                <div class="inc_right_tabs_inside" id="invite_friends-div">
                    <span class="iconfont icon-youcetuijianhaoyou"></span>
                    <c:if test="${empty user }">
                    	<span class="hint_label hide">推荐好友</span>
                    </c:if>
					<c:if test="${not empty user }">
            			<div class="invite_friends hide" id="rightMain_invite_friends_div">
	        				<p class="invite_friends_top">
	        					<img src="${staticPrefix }/images/LOGO_nobeta.png?v=${version }" class="invite_friends_top_logo">
	        					<img src="${staticPrefix }/images/right_close.png?v=${version }" class="invite_friends_top_close">
	        				</p>
	        				<div class="invite_friends_bottom">
	        					<div style="text-align: center;background-color: transparent;">
									<div class="qrcode" id="rightMain-qrcode" style="padding:0px; background: #2e3238;">
									</div>
	        					</div>
	        					<p class="invite_friends_bottom_text">推荐好友注册成功可获得G币奖励</p>
	        				</div>
	        			</div>
            		</c:if>
                </div>
            </li>
            <li class="inc_right_tabs_one inc_right_tabs_three inc_right_tabs_four inc_right_tabs_contact">
                <div class="inc_right_tabs_inside">
                    <span class="iconfont icon-youcelianxiwomen"></span>
                    <span class="hint_label hide">联系我们</span>
                </div>
            </li>
            <li class="inc_right_tabs_one inc_right_tabs_three inc_right_tabs_four inc_right_tabs_help" >
                <div class="inc_right_tabs_inside">
                    <span class="iconfont icon-youcebangzhuzhongxin"></span>
                    <span class="hint_label hide">帮助中心</span>
                </div>
            </li>
            <li class="inc_right_tabs_one inc_right_tabs_three inc_right_tabs_four inc_right_tabs_feedback" >
                <div class="inc_right_tabs_inside">
                    <span class="iconfont icon-bianji"></span>
                    <span class="hint_label hide">用户反馈</span>
                </div>
            </li>
            <li class="inc_right_tabs_one inc_right_tabs_three inc_right_tabs_four inc_right_tabs_CDK" >
                <div class="inc_right_tabs_inside">
                    <span class="iconfont icon-key"></span>
                    <span class="hint_label hide" style="min-width:100px;">CDKEY兑换</span>
                </div>
            </li>
            <li class="inc_right_tabs_one inc_right_tabs_three inc_right_tabs_four inc_right_tabs_task" >
                <div class="inc_right_tabs_inside">
                    <span class="iconfont icon-renwuzhongxin"></span>
                    <span class="hint_label hide">任务中心</span>
                </div>
            </li>
        </ul>
        <ul class="inc_right_tabs inc_right_tabs_bottom">
            <%-- <li class="inc_right_tabs_one inc_right_tabs_three inc_right_tabs_weixin" >
                <div class="inc_right_tabs_inside">
                    <span class="iconfont icon-youcexiaochengxuerweima"></span>
                    <span class="hint_label hide">微信公众号</span>
                </div>
            </li> --%>
            <li class="inc_right_tabs_one inc_right_tabs_top">
                <div class="inc_right_tabs_inside">
                    <span class="iconfont icon-youcefanhuitoubu"></span>
                    <span class="hint_label hide">返回顶部</span>
                </div>
            </li>
        </ul>
        <%--登录 --%>
        <div id="inc_right_login" class="modal-content hide" style="background:#2e3238; color:#fff; width: 350px; height:440px;position: absolute;right: 35px;">
            <div class="modal-header" style="padding-top: 55px;">
                <button type="button" class="close close_xa_xa" data-dismiss="modal" aria-hidden="true" style="position: absolute;right: 20px;top: 20px;"><img src="${staticPrefix }/images/close_new.png"></button>
                <h2 class="modal-title" id="myModalLabel" style=" font-size: 30px;font-weight: bold;"><b>登录开始竞猜</b></h2>
            </div>
            <div class="modal-body">
                <!-- <p style="text-align: center;"><img src="" alt="" style=" width: 70px; height: 70px; line-height:70px; text-align: center;"></p>
                 <p class=" mt-10" style="text-align: center;">“怦然”<a style=" color: #31C3A2;  text-decoration: underline;">不是你？</a></p>
                --><form id="right_loginform" name="right_loginform" action="/login" method="post">
                <input name="email" id="right_email" type="hidden">
                <input name="mobile" id="right_mobile" type="hidden" value="">

                <div id="right_historyLog" style="display: none;">
                    <p style="text-align: center;"><img src="${staticPrefix }/images/denglu_n_65.png" class="img-circle" id="right_userPhoto" alt="" style=" width: 70px; height: 70px; line-height:70px; text-align: center;"></p>
                    <p class=" mt-10" style="text-align: center;"><font id="right_nickName">‘superadmin’</font><a style=" color: #31C3A2;  text-decoration: underline;" id="right_notMe">不是你？</a></p>
                </div>
                <div class="form-group label-floating is-empty" id="right_loginNameDiv" style="display: block;padding:0">
                    <label for="right_loginName" class="control-label">手机号/邮箱</label>
                    <input type="text" class="form-control" name="loginName" id="right_loginName">
                    <span class="help-block" id="right_loginNameError"></span>
                </div>
                <div class="form-group label-floating is-empty" style="padding:0">
                    <label for="right_password" class="control-label">密码</label>
                    <input type="text" class="form-control" name="password" id="right_password" autocomplete="off" onfocus="this.type='password'">
                    <span class="help-block" id="right_passwordError"></span>
                </div>
            </form>
            </div>
            <div class="modal-footer" style="text-align: left;">
                <div style="padding: 0 17px;">
                    <p style="margin-bottom: 8px;">
                        <a href="/fp/forgetPassword" style="text-decoration:underline; color:#31bc9c;">忘记密码？</a><br></p>
                    <span style="color: #d4d4d4;">新成员？</span><a href="/register" style="text-decoration:underline; color:#31bc9c;" >现在注册</a>
                    <button type="button" class="btn btn-primary btn-raised pull-right right_x_button" style="width: 84px; height: 30px; padding-top: 4px; transform:translateY(-21px);" onclick="right_login();"><strong>登 录</strong></button>
                </div>
            </div>
        </div>
    </div>
    <!--right-->
    <div class="inc_right_content">
       
       	<!-- 你的账户 -->
		<jsp:include page="myAccount.jsp"/> 
       	<!-- 投注历史 -->
		<jsp:include page="myJc.jsp"/> 
		<!-- 兑换记录 -->
		<jsp:include page="myExchange.jsp"/> 
		<!-- 你的通知 -->
		<jsp:include page="myMessage.jsp"/> 
		<!-- 推荐好友 -->
		<jsp:include page="myFriends.jsp"/> 
		<!-- 联系我们 -->
		<jsp:include page="contactUs.jsp"/> 
		<!-- 帮助中心 -->
		<jsp:include page="helpCenter.jsp"/> 
		<!-- 用户反馈 -->
		<jsp:include page="feedBack.jsp"/> 
       	<!-- CDKEY兑换 -->
		<jsp:include page="CDKexchange.jsp"/> 
		<!-- 任务中心 -->
		<jsp:include page="taskCenter.jsp"/> 
    </div>
</div>