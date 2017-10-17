<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_9t2c4b7sl1c680k9.css">
		<div class="pull-left c-informleft">
            <div class="sidebar">
                <ul id="my-pjax">
                    <li class="btn btn-block c-vnav" id="info"><a href="${path }/my/info"><i class="iconfont icon-aboutme"></i>你的账户</a></li>
                    <li class="btn btn-block c-vnav" id="jc"><a href="${path }/my/jc"><i class="iconfont icon-jingcai"></i>你的竞猜</a></li>
                    <li class="btn btn-block c-vnav" id="address"><a href="${path }/my/address"><i class="iconfont icon-dizhi"></i>收货地址</a></li>
                    <li class="btn btn-block c-vnav" id="exchangeLog"><a href="${path }/my/exchangeLog"><i class="iconfont icon-duihuan"></i>兑换记录</a></li>
                    <li class="btn btn-block c-vnav" id="transactionLog"><a href="${path }/my/transactionLog"><i class="iconfont icon-jiaoyijilu"></i>交易记录</a></li>
                    <li class="btn btn-block c-vnav" id="message"><a href="${path }/my/message"><i class="iconfont icon-wodetongzhi"></i>你的通知</a></li>
                    <li class="btn btn-block c-vnav" id="friends"><a href="${path }/my/friends"><i class="iconfont icon-haoyou"></i>推荐好友</a></li>
                    <li class="btn btn-block c-vnav" id="cdkey"><a href="${path }/my/cdkey"><i class="iconfont icon-key1"></i>CD-KEY兑换</a></li>
                    <li class="btn btn-block c-vnav" id="userTask" style="border-top:1px solid #41494d;border-bottom:none"><a href="${path }/my/userTask"><i class="iconfont icon-renwuzhongxin1"></i>任务中心</a></li>
                </ul>
            </div>
        </div>
	<script type="text/javascript">  
		//这是a标签的pjax。#content 表示执行pjax后会发生变化的id，改成你主题的内容主体id或class。timeout是pjax响应时间限制，如果在设定时间内未响应就执行页面转跳，可自由设置。
		$(document).pjax('#my-pjax a', '#container',{timeout: 6000});
		//参考的loading动画代码 //执行pjax开始，在这里添加要重载的代码，可自行添加loading动画代码。例如你已调用了NProgress，在这里添加 NProgress.start();
		$(document).on(
			'pjax:send', 
			function() { 
				NProgress.start();
			}
		);
		//参考的loading动画代码 //执行pjax结束，在这里添加要重载的代码，可自行添加loading动画结束或隐藏代码。例如NProgress的结束代码 NProgress.done();
		$(document).on(
			'pjax:complete', 
			function() { 
				NProgress.done();
			}
		);
		
		$(function() {
			var url = window.location.href;
			var id = url.substring(url.lastIndexOf('/') + 1);
			$("#"+id).addClass("c-active").siblings().removeClass("c-active");
		});
	</script> 
