<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${staticPrefix }/js/my/right/myMessage.js?v=${version }"></script>

<!--你的通知-->
<div class="slide hide" id="inc_right_tabs_inform">
    <div class="slide-head"><img class="zjtp zjtp_x" src="${staticPrefix }/images/shuangzuo.png?v=${version }" alt="">
        <h2>你的通知</h2>
    </div>
    <div class="slide-content" style="padding:16px 0;">
        <ul class="list-group" id="messageUl">
            
        </ul>
        <div class="item-more" id="messageMore">
            <a href="javascript:getMessageList();"><span class="sxpic"></span>加载更多</a>
        </div>
        <div class="item-record" id="messageNoMore">
            <a href="javascript:void(0);"><span class="tzpic"></span>没有更多记录</a>
        </div>
    </div>
</div>
