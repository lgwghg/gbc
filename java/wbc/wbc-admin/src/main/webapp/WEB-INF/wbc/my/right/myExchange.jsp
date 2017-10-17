<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/my/right/myExchange.js?v=${version }"></script>

<!--兑换记录-->
<div class="slide hide" id="inc_right_tabs_record">
   	<div class="slide-head"><img class="zjtp zjtp_x" src="${staticPrefix }/images/shuangzuo.png?v=${version }" alt="">
 	   	<span class="sp checked">商品兑换记录</span>
 	   	<span class="cdk">CD-KEY兑换记录</span>
   	</div>
   	<div class="slide-one">
   		<div class="slide-content" style="padding: 0;">
	        <div class="item-sale">
	            <div>
	                <input id="conserve2" type="checkbox" name="allRight"/>
	                <label class="hold" for="conserve2">全选</label>
	            </div>
	<!--        <button type="button" class="btn" style="color: #31c3a2;" onclick="salesAllRight()">全部出售</button> -->
	        </div>
	        <div id="r_exchangeId"></div>
        </div>
	    <div id="exchangeMore" class="item-more">
	        <a href="javascript:getMyExchangeList(false);">
	            <span class="sxpic"></span>加载更多
	        </a>
	    </div>
	    <div id="exchangeNoMore" class="item-record">
	        <a href="javascript:void(0);"><span class="tzpic"></span>没有更多记录</a>
	    </div>
   	</div>
   	<div class="slide-two hide">
   		<div class="slide-content" style="padding: 0;">
<!-- 	        <div class="item-sale"> -->
<!-- 	            <div> -->
<!-- 	                <input id="conserve2" type="checkbox" name="allRight"/> -->
<!-- 	                <label class="hold" for="conserve2">全选</label> -->
<!-- 	            </div> -->
	<!--        <button type="button" class="btn" style="color: #31c3a2;" onclick="salesAllRight()">全部出售</button> -->
<!-- 	        </div> -->
	        <div id="r_CDKexchangeId">
	        	
	        </div>
        </div>
	    <div id="cdk_exchangeMore" class="item-more">
	        <a href="javascript:getMyCDKList(false);">
	            <span class="sxpic"></span>加载更多
	        </a>
	    </div>
	    <div id="cdk_exchangeNoMore" class="item-record">
	        <a href="javascript:void(0);"><span class="tzpic"></span>没有更多记录</a>
	    </div>
   	</div>
</div>
