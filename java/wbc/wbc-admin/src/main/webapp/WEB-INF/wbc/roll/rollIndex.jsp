<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="../include/common.jsp" />
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>roll点房间</title>
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_t55llhjiwpv0wwmi.css">
    <link rel="stylesheet" media="screen" href="${staticPrefix }/css/bootstrap-slider.css">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/details.css">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/fallback.css">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/ripples.min.css">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/rollornament.css">
    <script type="text/javascript" src="${staticPrefix }/js/bootstrap-slider.js"></script>
    <script type="text/javascript" src="${path }/resources/js/common/dateformat.js"></script>
    <script type="text/javascript" src="${staticPrefix }/js/roll/rollIndex.js"></script>
      <style type="text/css">
        .s-btn{ background: red; display: block; border-radius: 8px; width:70px; text-align: center; padding: 0px 5px; background: none; border: 1px solid #31c3a2;}
        .s-btn a{ color:#31c3a2 ;}
        .s-btn:hover{ background:rgba(0,0,0,0.5);}
       </style>
       


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!--[if lt IE 9]>
    <script>
        document.createElement('video');
    </script>
    <![endif]-->

</head>
<body>
<div class="bodyer"></div>
<div class="container-fluid" style="margin:0; padding:0;">
    <div id="inc_header"></div>
    <!--main-->
    <div class="container" style="padding:0;">
        <!--header S==-->
		<jsp:include page="../include/header.jsp" />
		<!--header E-->
        <!--通知 S==-->
		<jsp:include page="../include/message.jsp" />
		<!--通知 E-->
        <%-- <p style="text-align: center; margin: 55px 0;">
        <a class="btn btn-primary btn-raised ft-18 rolladd" style="background: #31c3a2;"><i class="iconfont icon-0100" style="vertical-align: top; margin-right: 6px;"></i>创建roll活动</a>
        </p> --%>
        <div class="playdescription mt-20">
            <span class="list-inform-three" style="transform: translateY(-10px);">×</span>
            <h2>ROll玩法说明</h2>
            <p>1、创建一个Roll房间，将自己的饰品作为礼物回馈给你忠实的粉丝们，与粉丝分享你的快乐，获得更多关注。</p>
            <p>2、参与自己偶像的Roll活动，攒人品夺饰品，与偶像一起享受快乐生活！</p>
        </div>
        <div class="createvent mt-20">
            <header><i class="c-point" style="margin:0 0 0 6px;"></i>我创建的活动</header>
            <section style="padding-left: 5px;" id="myRollRoom">
			
            </section>
        </div>
        <div class="rollroom mt-20">
            <article class="col-md-9" style="padding: 0; margin: 0;">
                <nav style="position: relative; height: 40px;">
                    <a class="iconfont icon-huo1 effecta">最热</a>
                    <a class="iconfont icon-xingxing">最新</a>
                    <a class="iconfont icon-canyuqingkuang">正在参与的活动</a>
                     <div class="middle-search">
                        <i class="iconfont icon-sousuo" id="searchButton"></i>
                        <input type="text" alt="" placeholder="请输入房间名或房主名" id="searchName" />
                    </div>
                </nav>    
                <section style="padding:10px; background: #2e3238; overflow: hidden;" id="personcardList">
                    
                </section>
            <div style="text-align: center; background: #2e3238; border-radius: 0 0 8px 8px;"> 
	            <ul class="pagination" id="pagination">
	            </ul>
            </div>  
            </article>
            <aside class="col-md-3 ml-10" style="padding: 0; margin: 0;">
               <div class="tuijiandb">
                    <div class="dbheader"></div>
                    <div class="dbcontent">
                        <ul id="roomHot_ul">
                        </ul>
                    </div>
               </div>
               <div class="partyaward mt-20">
                    <div class="pwheader">
                        <i class="c-point" style="margin:0 0 0 6px;"></i> 活动获奖记录
                    </div>
                    <div class="pwcontent">
                        <ul id="winnerList">
                        </ul>
                    </div>
               </div>
            </aside>
        </div>
</div>
    <jsp:include page="../include/footer.jsp" />

    <jsp:include page="include/rollRoomForm.jsp" />
</body>
<script>
    //通知滚动轮播
    /* var myInterval;
    function autoScroll(obj){
        $(obj).animate({
            marginTop : "-40px"
        },500,function(){
            $(this).css({marginTop : "0px"}).find("li:first").appendTo(this);
        })
    }
    function setMyInterval(){
        myInterval = setInterval('autoScroll(".list-inform-two")',3000);
    }
    $(function(){
        setMyInterval();
        $(".list-inform").mouseover(function () {
            clearInterval(myInterval);
        }).mouseout(function () {
            setMyInterval();
        })
    }) */
     $(function(){
        $("#myModal1").modal("hide");
                                        //弹窗居中
	    /* $("[data-toggle='modal']").click(function () {
	        var _target = $(this).attr('data-target')
	        t = setTimeout(function () {
	            var _modal = $(_target).find(".modal-dialog")
	            _modal.animate({'margin-top': parseInt(($(window).height() - _modal.height()) / 10)}, 10)
	        }, 10)
	    }); */
    });
	$(function(){
		$("nav a").click(function(){
			$(this).addClass("effecta").siblings().removeClass("effecta");
		});
	});

</script>
</html>
