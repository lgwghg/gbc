<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../include/common.jsp"/>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>翻硬币-历史记录</title>
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_cl5ct5ejudptx1or.css?v=${version }">
    <link rel="stylesheet" media="screen" href="${staticPrefix }/css/bootstrap-slider.css?v=${version }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/rollcoins.css?v=${version }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/rollcoinshistory.css?v=${version }">
    <script type="text/javascript" src="${staticPrefix }/js/bootstrap-slider.js?v=${version }"></script>
    <script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/coinflip/history.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/coinflip/viewCoinflip.js?v=${version }"></script>
</head>
<body>
<div class="bodyer"></div>
<div class="container-fluid" style="margin:0; padding:0;">
    <!--header S==-->
	<jsp:include page="../include/header.jsp" />
	<!--header E-->
    <!--main-->
    <div class="container" id="new-container" style="padding:0;">
        <!--通知 S==-->
		<jsp:include page="../include/message.jsp" /> 
		<!--通知 E==-->
        <div class="roll-coins-history">
	        <div class="message-details-outside">
	            <div class="message-details">
	                <div class="message-details-header">
	                    <span class="view1">创建者</span>
	                    <span class="view2">参与者</span>
	                    <span class="view3">金币</span>
	                    <span class="view4">随机码</span>
	                    <span class="view5">获胜者</span>
	                    <span class="view6">开奖时间</span>
	                    <span class="view7">状态</span>
	                </div>
	                <ul class="message-details-body">
	                    
	                </ul>
	                <div class="message-details-fy">
	                    <ul class="pagination" id="pagination">
	                    </ul>
	                </div>
	            </div>
	        </div>
        </div>
    </div>
</div>

<div class="modal fade addru" id="viewCoinflip" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog addhuman" style="display: block;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="/resources/wbc/images/close_new.png" class="mCS_img_loaded"></button>
                <h3 class="modal-title" id="myModalLabel">详情</h3>
            </div>
            <div class="modal-body">
            </div>
        </div>
    </div>
</div>
<jsp:include page="../include/footer.jsp" />
</body>
<script>
    $.material.init();
	loadHistoryList(_path + "/coinflip/historyData");
</script>
</html>
