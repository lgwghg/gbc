<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${staticPrefix }/js/my/right/myFriends.js?v=${version }"></script>
<!--推荐好友-->
<div class="slide hide" id="inc_right_tabs_friend">
    <div class="slide-head">
        <img class="zjtp zjtp_x" src="${staticPrefix }/images/shuangzuo.png?v=${version }" alt="">
        <h2>推荐好友</h2>
    </div>
    <div class="slide-content">
        <p style="color: #607d8b;width: 300px;">推荐您的好友在平台注册后，您将获得100个G币奖励。</p>

        <div class="x-recommend-link" style="background: none; padding-left: 0; margin-top: 10px;">
            <p class="view1"><span class="one">推广链接</span><span class="copy-link two" id="d_clip_button_myfriends" data-clipboard-action="copy" data-clipboard-text="${ip }register?fromuid=${user.campaignKey }">复制</span><span style="margin-left:42px">邀请码&nbsp;:&nbsp;<span>${user.campaignKey }</span></span></p>

            <p class="view2"><input type="text" value="${ip }register?fromuid=${user.campaignKey }" disabled="true" style="background: #3d4148;"></p>

            <p class="view3"><a href="${path }/shoppingMall">兑换</a><a class="ml-20" href="${path }/my/transactionLog">交易记录</a></p>
        </div>
        <div class="item-erweima">
            <div class="erweima-title">微信邀请</div>
            <div style="text-align: center;">
				<div class="qrcode" id="myfriedns-qrcode" style=" margin: 0 50px; background: #2e3238; width: 187px;height: 187px;">
				</div>
            </div>
            <div class="qxw">
                <p style="text-align: center;">更多分享&nbsp;:&nbsp;</p>
                <div class="qxwfxwrap">
                    <a href="#" onclick="share('1','${ip }share/f/show?c=${user.campaignKey }','','')"><img src="${staticPrefix }/images/qqicon.png?v=${version }" alt=""></a>
                    <a href="#" onclick="share('2','${ip }share/f/show?c=${user.campaignKey }','','')"><img src="${staticPrefix }/images/sinlang.png?v=${version }" alt=""></a>
                </div>
            </div>
        </div>
        <div class="item-thistory">
            <div class="history-title">推荐历史</div>
            <p style="color: #607d8b; width: 300px;">您的好友完成首次竞猜，您将再获得200个G币的奖励。</p>
        </div>
    </div>
    <div class="item-list">
        <div class="tip-title">
            <span>会员昵称</span>
            <span>时间</span>
            <span>奖励</span>
        </div>
        <div id="friends-div">
        </div>
        
    </div>
    <div class="item-more" id="firendsMore">
        <a href="javascript:getFriendsList();"><span class="sxpic"></span>加载更多</a>
    </div>
    <div class="item-record" id="firendsNoMore">
        <a href="javascript:void(0);"><span class="tzpic"></span>没有更多记录</a>
    </div>
</div>
<script>
	 window.onload = function(){
			$("#myfriedns-qrcode").html("").qrcode({
			    render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
			    text : "${ip }share/f?c=${user.campaignKey }",    //扫描二维码后显示的内容,可以直接填一个网址，扫描二维码后自动跳向该链接
				width : "150",               //二维码的宽度
			    height : "150",              //二维码的高度
			    background : "#ffffff",       //二维码的后景色
				foreground : "#000000",        //二维码的前景色
			    src: "${user.photo_35 }"           //二维码中间的图片
			}); 
	 }
</script>