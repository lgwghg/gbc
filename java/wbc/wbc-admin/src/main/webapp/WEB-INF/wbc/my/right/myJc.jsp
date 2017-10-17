<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="${staticPrefix }/js/my/right/myJc.js?v=${version }"></script>

<!--投注历史-->
<div class="slide hide" id="inc_right_tabs_history">
    <div class="slide-head">
        <img class="zjtp zjtp_x" src="${staticPrefix }/images/shuangzuo.png?v=${version }" alt="">
        <h2>投注历史</h2>
    </div>
    <div id="jcForm" class="slide-content"></div>
    <div class="jcrecord mb-10">
        <div class="jcrecord-title" style="display: flex; flex-direction: row; justify-content: space-between; padding: 0 18px; color: #fff;">
            <div><sapn class="tip-yuan" style="background: #e4a713;">W</sapn>赢</div>
            <div><sapn class="tip-yuan" style="background: #525252;">L</sapn>输</div>
            <div><sapn class="tip-yuan" style="background: #0c6954;">N</sapn>待结束</div>
            <div><sapn class="tip-yuan" style="background: #4470B1;">!</sapn>已取消</div>
        </div>
	    <div id="jcList"></div>
    </div>
    <div id="jcMore" class="item-more">
        <a href="javascript:getMyJcList();"><span class="sxpic"></span>加载更多</a>
    </div>
    <div id="jcNoMore" class="item-record">
    	<a href="javascript:void(0);"><span class="tzpic"></span>没有更多记录</a>
    </div>
</div>
