<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.webside.common.GlobalConstant"%>
<%
	request.setAttribute("path", request.getContextPath());
	request.setAttribute("ctx", request.getContextPath());
	request.setAttribute("staticPrefix",  request.getContextPath()+"/resources/wbc");
	request.setAttribute("version", GlobalConstant.VERSION);
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <%-- <jsp:include page="../include/common.jsp"/> --%>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta itemprop="name" content="G菠菜竞猜分享"/>
    <meta itemprop="image" content="${gbInfo.game.bgImg }" />
    <meta name="description" itemprop="description" content="${gbInfo.ht.teamName }（赔率X${pkVo.pkHomeRule }） ${gbInfo.gameRuleName } ${gbInfo.at.teamName }（赔率X${pkVo.pkAwayRule }） ${gbInfo.gevent.eventName }" />
    <title>G菠菜竞猜分享</title>
     <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_3fwbuc7zqcl3di.css">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/bootstrap.min.css?v=${version }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/bootstrap-material-design.css?v=${version }">
    <%-- <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/fallback.css">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/ripples.min.css"> --%>
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/base.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/x-share.css?v=${version }">
<script language="JavaScript">
	var _path = '${path}';
	var _staticPrefix = '${staticPrefix}';
	var user = '${user}';		//user值 在 header.js 中赋值
	var ip = '${ip}';
	var seoConfigMap = '${seoConfigMap}';
	var _version = '${version }';
</script>
    <script type="text/javascript" src="${staticPrefix }/js/jquery.min.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/bootstrap.min.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/layer-v2.3/layer.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/common/base.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/share/share.js?v=${version }"></script>
</head>
<body>
<%-- <c:if test="${not empty param.f && param.f=='fx'}">
	<div class="initbanner">
        <img src="${staticPrefix }/images/initbanner.png" alt="">
         <div class="xxx">
          <i class="iconfont icon-guanbi"></i></div>  
    </div>
</c:if> --%>
   
<div class="share">
<input type="hidden" id="gbId" value="${gbId }" />
<input type="hidden" id="userCode" value="${userCode }" />
    <div class="share-match">
        <img src="${staticPrefix }/images/LOGO_share.png?v=${version }" class="share-match-header">
        <p class="share-match-title">${gbInfo.gbName} ${pkVo.pkName}<%-- ${gbInfo.gevent.eventName } ${gbInfo.gameRuleName } --%></p>
        <ul class="share-match-game">
        	<img src="${staticPrefix }/images/${gbInfo.game.englishName}.png" class="one">
            <li class="two">
             	<div class="fl">
                <img src="${gbInfo.ht.teamIcon }" class="fl">
                </div>
                <ul class="message">
                    <li class="li1">
                        <p><c:if test="${pkVo.pankouType != null and pkVo.pankouType==1 }"><c:if test="${pkVo.pkRangFenTeam != null and pkVo.pkRangFenTeam == 1}"><span style="color:#fdbb05;">[-${pkVo.pkRangfenNum }]</span></c:if></c:if><span class="co-31">${gbInfo.ht.teamName }</span></p>
                        <p><span>赔率</span>X${pkVo.pkHomeRule }</p>
                    </li>
                    <li class="li2">
                        <p><span style="color:#fff;">${gbInfo.at.teamName }</span><c:if test="${pkVo.pankouType != null and pkVo.pankouType==1 }"><c:if test="${pkVo.pkRangFenTeam != null and pkVo.pkRangFenTeam == 2}"><span style="color:#fdbb05;">[-${pkVo.pkRangfenNum }]</span></c:if></c:if></p>
                        <p>X${pkVo.pkAwayRule }<span>赔率</span></p>
                    </li>
                </ul>
                <div class="fr">
                <img src="${gbInfo.at.teamIcon }" class="fr">
                </div>
            </li>
            <li class="three">
                <span class="span1"><span>${pkVo.pkHomePrtNum}</span><c:if test="${pkVo.gbstate != null and pkVo.gbstate == 2 }"><c:if test="${pkVo.pkVictory != null and pkVo.pkVictory == gbInfo.ht.id}"><span class="span1-win">WIN</span></c:if></c:if></span>
                <input type="hidden" id="gbstate" value="${pkVo.gbstate}"/>
                <input type="hidden" id="pkStartTime" value="${pkVo.pkStartTime}"/>
                <c:choose>
                
                <c:when test="${pkVo.gbstate == '2'}"><span class="span2"><span style="color:#888888;width:100%;">已结束</span></span></c:when>
                <c:when test="${pkVo.gbstate == '3'}"><span class="span2"><span style="color:#888888;width:100%;">已取消</span></span></c:when>
                <c:when test="${pkVo.gbstate == '1'}"><span class="span2">进行中</span></c:when>
                <c:otherwise><span class="span2" id="pkStartTimeStr"><img src="${staticPrefix }/images/time_x.png?v=${version }"></span></c:otherwise>
                
                </c:choose>
                <span class="span3"><span>${pkVo.pkAwayPrtNum}</span><c:if test="${pkVo.gbstate != null and pkVo.gbstate == 2 }"><c:if test="${pkVo.pkVictory != null and pkVo.pkVictory == gbInfo.at.id}"><span class="span1-win">WIN</span></c:if></c:if></span>
            </li>
        </ul>
    </div>
    <ul class="match-message">
        <li class="one">
            <img src="${userJc.user.getPhoto_65() }">
        </li>
        <li class="two">
            <p>${userJc.user.nickName }</p>
            <span class="lv-x lv-x-${userJc.user.rankLevel }"></span>
        </li>
        <li class="three">
            <p class="text-center"><img src="${staticPrefix }/images/touzhu_x.png?v=${version }"></p>
            <input type="hidden" id="jcTime" value="${userJc.jcTime}"/>
            <p><span id="jcTimeDesc"></span>，投注了<span><c:if test="${userJc.jcTeamType == 1}">${gbInfo.ht.teamName }</c:if><c:if test="${userJc.jcTeamType == 2}">${gbInfo.at.teamName }</c:if></span>战队</p>
        </li>
        <li class="four">
        	<c:if test="${userJc.jcTeamType == 1}">
        	<img src="${gbInfo.ht.teamIcon }" class="img1">
        	</c:if>
        	<c:if test="${userJc.jcTeamType == 2}">
        	<img src="${gbInfo.at.teamIcon }" class="img1">
        	</c:if>
            <img src="${staticPrefix }/images/img_top_x.png?v=${version }"  class="img2">
        </li>
    </ul>
    <div class="no-phone-number">
        <input type="text" placeholder="请输入手机号领取红包" id="userMobile">
        <button class="btn btn-primary btn-raised share-get"><img src="${staticPrefix }/images/icon_get_x.png?v=${version }"><span>领取红包</span></button>
    </div>
    <div class="have-phone-number hide">
        <div>
            <img src="${staticPrefix }/images/icon_get2_x.png?v=${version }">
            <span id="userGold"></span>
        </div>
        <p>G币已放至账户<span id="uMobile"></span><br/>登录<span>gbocai.com</span>，即可使用</p>
    </div>
    <ul class="luck-title hide" id="luck-title">
        <li class="line"></li>
        <li class="title">看看手气</li>
        <li class="line"></li>
    </ul>
    <ul class="luck-rank">
        
    </ul>
    <ul class="luck-title mt-20">
        <li class="line"></li>
        <li class="title">活动规则</li>
        <li class="line"></li>
    </ul>
    <div class="rule">
        <p class="bold">1.G币红包明细</p>
        <p>每个红包总额由下注金额的比例决定，包括分享本人每次随机金额可领取10份，每人每日最多4个红包。</p>
        <p class="bold">2.领取方式</p>
        <p>注册用户：输入手机号即可快速领取。</p>
        <p>新用户：输入手机号即可快速领取，需在7天之内完成注册，注册成功后领取的G币自动关联到手机账号中。</p>
        <p class="bold">3.G红包使用</p>
        <p>领取G币红包仅供参与竞猜下注使用，未参与竞猜的G币不纳入进行商品兑换金币。</p>
        <p class="bold">4.声明</p>
        <p>活动最终解释权归G菠菜所有。客服邮箱：kefu@gbocai.com</p>
    </div>
</div>

<!--提示-->
<div class="tishi hide">
    <div class="tishi-shade"></div>
    <div class="tishi-main">
        <span></span>
        <img src="${staticPrefix }/images/tishi_x.png?v=${version }">
        <p id="tishiText"></p>
        <button class="btn btn-primary btn-raised hide">确定</button>
    </div>
</div>
</body>
<script src="${staticPrefix }/js/material.js?v=${version }"></script>
<script src="${staticPrefix }/js/ripples.min.js?v=${version }"></script>
<script>
    $.material.init();
    $(function(){
        $(".icon-guanbi").click(function(){
            $(this).closest(".initbanner").hide();
        });
    });
</script>


</html>
