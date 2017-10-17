<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${staticPrefix }/js/my/right/myAccount.js?v=${version }"></script>

<!--你的账户-->
<div class="slide hide" id="inc_right_tabs_account">
    <div class="slide-head"><img class="zjtp zjtp_x" src="${staticPrefix }/images/shuangzuo.png?v=${version }" alt="">
        <h2>你的账户</h2>
    </div>
    <div class="slide-content">
        <div style="overflow: hidden; max-height: 80px;">
           <a href="#"><img class="p-img img-circle" src="${user.getPhoto_170() }" id="right_userPhoto_r"/></a>

           <p class="user-name" id="right_nickName_r">hi,${user.nickName }</p>
           <span class="lv-x lv-x-${user.rankLevel }" style="float: left;"></span>
        </div>
        <div class="item-1" id="dailyTask">
            <dl>
                <dt id="right_signGiveGold" style="margin-bottom:0;font-weight:100;"></dt>
                <dd>完成任务奖励<span style="color:#31c3a2;margin:0 3px;" id="right_completedGive"></span>G币</dd><!-- 金币数量改成动态随机数 -->
            </dl>
            <button type="button" class="btn  btn-raised btn-primary hide" id="right_wqd" style="top:-5px;">
                <strong>签到</strong>
            </button>
            <button type="button" class="btn  btn-raised btn-primary hide" disabled id="right_yqd" style="padding: 0; background-color: #666666!important;top:-5px;">
                <strong>已签到</strong>
            </button>
            <div class="task-list-progress-bar new hide" id="right_dailyTask">
        		<p class="view1" style="width:33.33%;"></p>
        		<p class="view2">进行中</p>
        	</div>
        </div>
        <div class="item-1 task-done hide" id="congratulationDailyTask">
        	<img src="${staticPrefix }/images/task-done.png">
        	<span class="co-31 ml-5">恭喜您，今日每日任务已完成!</span>
        </div>
        <div class="item-1">
            <p class="task-number" id="completeTaskNum">每日完成任务(<span>0</span>/5)</p>
            <span class="task-centered">任务中心</span>
        </div>
        <div class="item-2 item-6" style="margin-top: 25px;border-bottom:0;">
            <div class="yourswallet">你的钱包</div>
            <!-- <p>
            	<button type="button" class="btn pull-right right_xa_click" data-toggle="modal" data-target="#myModal5" id="right_recharge">
	                <strong class="right_xa_click">充值</strong>
	            </button>
            	<span><b>￥</b></span><span id="right_walletGold">0</span> 金币
            </p> -->
            <div class="dispose-gold">
            	<div class="gold-number">
            		<span class="gold-number-icon"><b>￥</b></span>
            		<p><span id="right_walletGold"></span>金币</p>
            		<span class="gold-number-button">
            			<button type="button" class="btn right_xa_click" data-toggle="modal" data-target="#myModal5" id="right_recharge" style="float:left;">
	                		<strong class="right_xa_click">充值</strong>
	            		</button>
	            		<button type="button" class="btn pull-right right_xa_click" onclick="clickWd();" id="right_withdraw" style="margin-left:15px;color:#31c3a2;border:2px solid #31c3a2;">
	                		<strong class="right_xa_click">提现</strong>
	            		</button>
            		</span>
            	</div>
            </div>
        </div>
        <div class="item-2 item-2-p">
        	<p>
            	<span class="to-task-centered">免费领取G币</span>
            	<span class="item-2-icon-x"><b>￥</b></span><span id="right_sysGold">0</span> G币
            	<a href="javascript:;" title="G币有什么用" class="item-2-a"><img src="${staticPrefix }/images/question.png"></a>
            </p>
            <!-- 下面需转变成js动态加载，有下注过未结算的比赛就有下面内容 -->
            <p>
            	<a href="${ctx }/help/findByCode?code=htug" class="item-2-a2">参与竞猜未结算的金币</a>
            	<span id="right_jcGold"></span>
            </p>
            <p>
            	<a href="${ctx }/help/findByCode?code=htug" class="item-2-a2">发起提现未到账的金币</a>
            	<span id="right_wdGold"></span> 金币
            </p>
            <!-- 上面需转变成js动态加载，有下注过未结算的比赛就有上面内容 -->
        </div>
        <!-- 下面部分改成js动态加载 -->
        <div class="item-2 item-7">
        	<div class="yourswallet">交易记录</div>
			<ul class="item-7-record" id="r_transaction">
			</ul>
        </div>
        <!-- 上面部分改成js动态加载 -->
        <div id="transactionMore" class="item-more item-more-new">
	        <a href="javascript:right_transaction(false);">
	            <span class="sxpic"></span>加载更多
	        </a>
	    </div>
	    <div id="transactionNoMore" class="item-record  item-record-new">
	        <a href="javascript:void(0);"><span class="tzpic"></span>没有更多记录</a>
	    </div>
    </div>
</div>
