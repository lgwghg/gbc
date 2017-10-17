<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${staticPrefix }/js/my/right/helpCenter.js?v=${version }"></script>

<!--帮助中心-->
<div class="slide hide" id="inc_right_tabs_help">
    <div class="slide-head"><img class="zjtp zjtp_x" src="${staticPrefix }/images/shuangzuo.png?v=${version }" alt="">
        <h2>帮助中心</h2>
    </div>
    <div class="slide-content" style="padding:16px 0;">
        <ul class="list-group" id="helpUl">
        
        </ul>
        <div class="item-more" id="helpMore">
            <a href="javascript:getHelpList();"><span class="sxpic"></span>加载更多</a>
        </div>
        <div class="item-record" id="helpNoMore">
            <a href="javascript:void(0);"><span class="tzpic"></span>没有更多记录</a>
        </div>
    </div>
</div>
