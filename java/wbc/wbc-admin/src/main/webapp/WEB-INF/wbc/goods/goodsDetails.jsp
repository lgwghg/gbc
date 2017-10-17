<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../include/common.jsp"/>
    <title>商品详情${seoConfigMap['1'].title }</title>
    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"> 

    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/goods/x-shopping-mall-details.css?v=${version }">
    <link type="text/css" rel="stylesheet" href="${staticPrefix }/css/jquery.dropdown.css?v=${version }">
    <script type="text/javascript" src="${staticPrefix }/js/jquery.dropdown.js?v=${version }"></script>
	<script type="text/javascript" src="${staticPrefix }/js/common/cityselect.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/layer-v2.3/layer.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/goods/index.js?v=${version }"></script>
    
  </head>
  
  <body>
    <!-- 头部 -->
    <jsp:include page="../include/header.jsp"/>
    
	<div class="container" id="new-container" style="padding: 0;position: relative;z-index: 1;">
	    <!-- 通知 -->
	    <jsp:include page="../include/message.jsp"/>
	    
	    <div>
	        <ul class="breadcrumb" style="background:none; font-size:14px; padding: 12px 0;margin: 0;">
	            <li class="one"><a href="${path }/match">首页</a></li>
	            <li class="two"><a href="${path }/shoppingMall">商城</a></li>
	            <li class="three"><a href="">${goods.goodsName }</a></li>
	        </ul>
	    </div>
	    <div class="shopping-details">
	        <!--left-->
	        <div class="shopping-details-left">
	            <div class="view1">
	            	<input type="hidden" id="goodsId" value="${goods.id }" />
	            	<input type="hidden" id="goodsGold" value="${goods.goodsGold }"/>
	                <div class="five"><img src="${goods.goodsImg }"></div>
	                <p class="one">${goods.goodsName }</p>
	                <p class="two">${goods.goodsGold }金币</p>
	                <p class="three">已兑换<span id="exchangeCount">${goods.exchangeCount }</span>件</p>
	                <p class="gw_num" id="gwNum">
	                    <em class="del">-</em>
	                    <input id="salesNum" type="text" value="1" class="num"/>
	                    <em class="add">+</em>
	                </p>
	                <p class="three mt-5">库存<span id="goodsNum">${goods.goodsNum }</span>件</p>
	                
	                <p class="four"><button class="btn btn-primary btn-raised" data-toggle="modal" id="exchangeId">立即兑换</button></p>
	            </div>
	            <div class="view2">
	                <p>${goods.desc }</p>
	            </div>
	        </div>
	        
	        <!--right-->
	        <div class="shopping-details-right">
	            <p class="view1">
	                <span class="one"></span>
	                <span class="two ml-10 co_92">热门兑换</span>
	            </p>
	            <ul class="view2" id="goods-ul">
	                
	            </ul>
	        </div>
	    </div>
	</div>
	
	<!-- 底部 -->
	<jsp:include page="../../wbc/include/footer.jsp"/>
	<script type="text/javascript">
		
	    $(function() {
	    	// 加载热门兑换
	    	loadHotExchange();
	    	
	    	var goodsNum = Number($("#goodsNum").text());
	    	if(goodsNum > 999) {// 上限999
	    		goodsNum = 999;
	    	}
	    	var isMax = "${goods.isMax}";
	    	if(isMax == "1") {
	    		$("#goodsNum").text(999);
	    		$("#exchangeId").text("兑换CD-KEY")
	    	} else if(goodsNum <= 0) {
	    		$("#gwNum").hide();
	    		$("#exchangeId").text("售罄").attr("disabled","disabled").css("background","#3d4148");
	    		$("#exchangeId").hover(function(){
	    			$(this).css("background","#3d4148");
	    		})
	    	}
	    	
	    	//加的效果
	        $(".add").click(function(){
	        	goodsNum = Number($("#goodsNum").text());
		    	if(goodsNum > 999) {// 上限999
		    		goodsNum = 999;
		    	}
	            var n=$(this).prev().val();
	            if(isNaN(n) || n < 0) {
	            	n = 0;
	            }
	            var num=parseInt(n)+1;
	            if(num==0||num>goodsNum){ 
	            	return;
	            } else if(num < 0) {
	            	num = 0;
	            }
	            $(this).prev().val(num);
	        });
	        //减的效果
	        $(".del").click(function(){
	        	goodsNum = Number($("#goodsNum").text());
		    	if(goodsNum > 999) {// 上限999
		    		goodsNum = 999;
		    	}
	            var n=$(this).next().val();
	            if(isNaN(n) || n < 0) {
	            	n = 0;
	            }
	            var num=parseInt(n)-1;
	            if(num==0||num>goodsNum){ 
	            	return;
	            } else if(num < 0) {
	            	num = 0;
	            }
	            $(this).next().val(num);
	        });
	        $(".gw_num .num").bind("input propertychange", function () {
	        	goodsNum = Number($("#goodsNum").text());
		    	if(goodsNum > 999) {// 上限999
		    		goodsNum = 999;
		    	}
		    	var num = $(this).val();
		    	if(isNaN(num)) {
		    		$(this).val(0);
		    	} else if (Number(num) > goodsNum) {
	                $(this).val(goodsNum);
	            }
	        });
	        
	        $("#exchangeId").click(function() {
	        	var userId = $("#userId").val();
	        	if(userId == "" || userId == undefined) {// 未登录
	        		$("#myModal").modal("show");
	        	} else {
		        	var gold = $("#goodsGold").val();
		    		var salesNum = Number($("#salesNum").val());
		    		var total = gold * salesNum;
		    		
		    		var headerMenuGold = $("#headerMenuGold").text();
		    		if(Number(headerMenuGold) < Number(total)) {
		    			layer.confirm("金币不足，是否先充值？", {icon : 3, title : "金币不足提示"}, function(index) {
			    	  		$("#headerRecharge").click();
			    			layer.close(index);
		    			});
		    		} else {
		    			// 收货地址
		    	    	defaultAddress();
			        	$("#myModalAddress").modal("show");
		    		}
	        	}
	        });
	        
	        $("#saveAddress").click(function() {
	        	exchange("${goods.type}");
	        });
	    });
	</script>
  </body>
</html>
