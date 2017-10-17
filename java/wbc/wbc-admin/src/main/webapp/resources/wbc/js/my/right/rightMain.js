$(function () {
	// 右侧我的账户图标icon展示
	var userPhoto = cookie_user_photo;
	if(userPhoto==""||userPhoto==null){
		$("#right_accountIcon").html('<span class="iconfont icon-logo"></span><span class="hint_label hide">你的账户</span>');
	}else{
		var photo = $("#right_accountIcon img").attr("src");
		if (photo == null || photo == '') {
			if (userPhoto.indexOf('.') >= 0) {
				var ph = userPhoto.split('.');
				userPhoto = ph[0] + "_35." + ph[1];
			}
			userPhoto = _path + userPhoto;
			$("#right_accountIcon").html('<img src="' + userPhoto + '"><span class="hint_label hide">你的账户</span>');
		}
	}
	
    //右侧导航栏
    //页面加载显示导航
	var timer1,timer2 ,timer3,timer4,timer5,timer6;
	var inc_padding;
    $(".inc_right,.inc_right_tabs_big,.slide").css("height", document.documentElement.clientHeight);
    $(".inc_right_tabs_top").hide();
    if (window.innerWidth < 1277) {
        $(".inc_right_tabs_three").addClass("window_shrink").removeClass("window_unfold");
        timer1 = setTimeout(function () {
            $(".inc_right_tabs_big").css("background-color", "transparent");
            $(".inc_right_tabs_three").css("margin-left", "35px");
        }, 200);
    }
    setTimeout(function () {
        $(".inc_right").removeClass("hide").addClass("small_load");
        inc_padding = (document.documentElement.clientHeight - $(".inc_right_tabs_middle").height()) / 2;
        $(".inc_right_tabs_account").css("padding-top", inc_padding);
        $(".inc_right_tabs_task").css("padding-bottom", inc_padding);
        /*$(".inc_right_tabs_middle").css("top",((document.documentElement.clientHeight-$(".inc_right_tabs_middle").height())/2));*/
        
        //判断是否登陆 推荐好友
    	showRightMainQrcode();
    }, 500)
    
    //右侧你的账户任务中心按钮点击事件
    $(".task-centered,.to-task-centered").on("click",function(){
		$("#inc_right_tabs_task").find(".slide-content").css("margin-top","800px").animate({
			marginTop:'0'
		},300);
		$(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
		$(".inc_right_tabs_task .inc_right_tabs_inside").addClass("checked");
		$(".slide").addClass("hide");
		$("#inc_right_tabs_task").removeClass("hide");
		$(".inc_right").addClass("big_load_one").removeClass("big_load_two");
		//展开，隐藏小二维码
		$("#rightMain_invite_friends_div").addClass("hide");
		initTaskCenter();
    })
    $(".to-exchange").on("click",function(){
    	$("#inc_right_tabs_record .slide-one").addClass("hide");
		$("#inc_right_tabs_record .slide-two").removeClass("hide");
		$("#inc_right_tabs_record .slide-head span").removeClass("checked");
    	$("#inc_right_tabs_record .slide-head .cdk").addClass("checked");
		$("#inc_right_tabs_record").find(".slide-content").css("margin-top","800px").animate({
			marginTop:'0'
		},300);
		$(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
		$(".inc_right_tabs_record .inc_right_tabs_inside").addClass("checked");
		$(".slide").addClass("hide");
		$("#inc_right_tabs_record").removeClass("hide");
		$(".inc_right").addClass("big_load_one").removeClass("big_load_two");
		//展开，隐藏小二维码
		$("#rightMain_invite_friends_div").addClass("hide");
		getMyCDKList(true);
		getMyExchangeList(true);
    })
    
    /*判断是否登录*/
    /*if(){/!*未登录*!/
        $(".inc_right_tabs_account div .iconfont").removeClass("hide");
    }
    else{/!*已登录*!/
        $(".inc_right_tabs_account div img").removeClass("hide");
    }*/
    /*我的账户*/
    bindClick("inc_right_tabs_account");
    /*投注历史*/
    bindClick("inc_right_tabs_history");
    /*兑换记录*/
    bindClick("inc_right_tabs_record");
    /*我的通知*/
    bindClick("inc_right_tabs_inform");
    /*推荐好友*/
    bindClick("inc_right_tabs_friend");
    /*联系我们*/
    bindClick("inc_right_tabs_contact");
    /*帮助中心*/
    bindClick("inc_right_tabs_help");
    /*用户反馈*/
    bindClick("inc_right_tabs_feedback");
    /*帮助中心*/
    bindClick("inc_right_tabs_CDK");
    /*用户反馈*/
    bindClick("inc_right_tabs_task");
    //返回顶部
    $(".inc_right_tabs_top").click(function () {
        $(this).removeClass("checked");
        $("html,body").animate({scrollTop: 0}, 500);
    })
  //隐藏登陆框
    $("#inc_right_login").on("mouseenter",function(){
        $(this).removeClass("hide");
    }).on("mouseleave",function(){
        $(this).addClass("hide");
    })
    $(".inc_right_tabs_middle,.inc_right_tabs_bottom").on("mouseover",function(){
        clearTimeout(timer1);
        clearTimeout(timer3);
        if(window.innerWidth<1277){
            timer3 =  setTimeout(function(){
                $(".inc_right_tabs_three").addClass("window_unfold").removeClass("window_shrink");
                 timer2 = setTimeout(function(){
                    $(".inc_right_tabs_three").css("margin-left","0");
                },200);
            },336)
            /*$(".inc_right_tabs_bg").addClass("hide");*/
        }
    }).on("mouseout",function(){
        clearTimeout(timer2);
        clearTimeout(timer3);
        if(window.innerWidth<1277 && $(".inc_right_tabs li div").hasClass("checked") != true){
             timer3 = setTimeout(function(){
                $(".inc_right_tabs_three").addClass("window_shrink").removeClass("window_unfold");
                 timer1 = setTimeout(function(){
                    $(".inc_right_tabs_three").css("margin-left","35px");
                },200);
            },336)
            /*$(".inc_right_tabs_bg").removeClass("hide");*/
        }
    })
    $(".inc_right_tabs_middle>li:eq(1)").siblings().on("mouseover",function(e){
            e.stopPropagation();
            clearTimeout(timer1);
            clearTimeout(timer2);
            clearTimeout(timer3);
    })
    $(".inc_right_tabs_bottom>li:eq(1)").siblings().on("mouseover",function(e){
            e.stopPropagation();
            clearTimeout(timer1);
            clearTimeout(timer2);
            clearTimeout(timer3);
    })
    $(".inc_right_content").on("mouseover",function(){
        $(".inc_right_tabs_three").css("margin-left","0");
    })
    //鼠标移过标签浮现
    $(".inc_right_tabs li .inc_right_tabs_inside").on("mouseenter",function(){
        var thisDome = $(this);
        timer5 = setTimeout(function(){
            thisDome.find(".hint_label").animate({
                right:'35px',
                opacity:'1'
            },320).removeClass("hide");
        },250)
    }).on("mouseleave",function(){
        var thisDome = $(this);
        clearTimeout(timer5);
        $("#inc_right_login").addClass("hide");
        $(this).find(".hint_label").animate({
            right:'65px',
            opacity:'0'
        },320);
        timer4 = setTimeout(function(){
            thisDome.find(".hint_label").addClass("hide");
        },320)
    })
    
     //鼠标点击其他部分收缩右边栏
    $(".inc_right").click(function(e){
        if($(e.target).hasClass("right_xa_click") != true){
            e.stopPropagation();
        }
        $('.modal').modal('hide');
    })
    $("body").click(function(){
        $(".inc_right_tabs li .inc_right_tabs_inside").removeClass("checked");
        if($(".inc_right").hasClass("big_load_one") == true){
            $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
        }
        setTimeout(function () {
            $(".slide").addClass("hide");
    		//判断是否登陆 推荐好友，显示小二维码
    		showRightMainQrcode();
        },200)
    })
    //隐藏显示返回顶部标签
    $(window).scroll(function(){
        if($(document).scrollTop()>0){
            $(".inc_right_tabs_top").show();
        }
        else{
            $(".inc_right_tabs_top").hide();
        }
    })
  //点击返回滑块移回
    $(".zjtp_x").click(function(){
        $(".inc_right_tabs li .inc_right_tabs_inside").removeClass("checked");
        $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
        setTimeout(function () {
            $(".slide").addClass("hide");
        },200)
    })
    $(".close_xa_xa").click(function(){
        $("#inc_right_login").addClass("hide");
    })
    //阻止标签点击事件传播
    $(".hint_label,.invite_friends").click(function(e){
        e.stopPropagation();
    })
    //点击推荐好友二维码关闭按钮
    $(".invite_friends_top_close").click(function(){
        $(".invite_friends").addClass("hide");
        sessionStorage.setItem("inviteFriendsIsHide","true");
    })
})

function showRightMainQrcode(){
	if(user){
		var inviteFriendsIsHide = sessionStorage.getItem("inviteFriendsIsHide");
		if(inviteFriendsIsHide!='true'){
			$("#rightMain-qrcode").html("").qrcode({
			    render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
			    text : ip+"share/f?c="+$("#userCampaignKey").val(),    //扫描二维码后显示的内容,可以直接填一个网址，扫描二维码后自动跳向该链接
				width : "85",               //二维码的宽度
			    height : "85",              //二维码的高度
			    background : "#ffffff",       //二维码的后景色
				foreground : "#000000",        //二维码的前景色
			    src: $("#userPhoto35").val()           //二维码中间的图片
			});
			
	        setTimeout(function(){
	            $("#rightMain_invite_friends_div").removeClass("hide");
	        },250)
		}else{
			$("#invite_friends-div").append('<span class="hint_label hide">推荐好友</span>');
		}
	}
}


// 绑定点击事件
function bindClick(id) {
	$("."+id+" .inc_right_tabs_inside").on("click",function() {
		// 登录超时，右侧弹出登录
		$.ajaxSetup({
    	    complete:function(XMLHttpRequest,textStatus) {
    	 		var status = XMLHttpRequest.getResponseHeader("session_status");  
    	 		if(status == "timeout") {  
    	 			showRightLogin(id);
    	        }  
    	    }
    	});
		var isOpen = false;
		if (user != null && user != '') {
			isOpen = true;
		} else {
			if (id == 'inc_right_tabs_account') {
				var userPhoto = cookie_user_photo;
				if (userPhoto != null && userPhoto != '') {
					isOpen = true;
				}
			}
		}
		if(!isOpen){//判断是否登录
			showRightLogin(id);
        } else {
        	if($(this).hasClass("checked") == true){
        		$(this).removeClass("checked");
        		$(".inc_right").addClass("big_load_two").removeClass("big_load_one");
        		setTimeout(function () {
        			$("#"+id).addClass("hide");
        			//判断是否登陆 推荐好友，显示小二维码
            		showRightMainQrcode();
        		},200)
        	} else {
        		if($(".inc_right_tabs li .inc_right_tabs_inside").hasClass("checked") == true){
        			$("#"+id).find(".slide-content").css("margin-top","800px").animate({
        				marginTop:'0'
        			},300);
        		}
        		$(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
        		$(this).addClass("checked");
        		$(".slide").addClass("hide");
        		$("#"+id).removeClass("hide");
        		$(".inc_right").addClass("big_load_one").removeClass("big_load_two");
        		
        		//展开，隐藏小二维码
        		$("#rightMain_invite_friends_div").addClass("hide");
        	}
        	
        	if("inc_right_tabs_account" == id) {// 你的账户
        		initMyAccount(true);
        	} else if("inc_right_tabs_history" == id) {// 投注历史
        		getMyJcForm();
        		getMyJcList(true);
        	} else if("inc_right_tabs_record" == id) {// 兑换记录
        		getMyExchangeList(true);
        		getMyCDKList(true);
        	} else if("inc_right_tabs_inform" == id) {// 你的通知
        		getMessageList(true);
        	} else if("inc_right_tabs_friend" == id) {// 推荐好友
        		initMyFriends();
        		getFriendsList(true);
        	} else if("inc_right_tabs_contact" == id) {// 联系我们
        		
        	} else if("inc_right_tabs_help" == id) {// 帮助中心
        		getHelpList(true);
        	} else if("inc_right_tabs_feedback" == id) {// 用户反馈
        		
        	} else if ("inc_right_tabs_CDK" == id) { // CDK
        		$('#right_cdkeyCaptchaImage').click();
        	} else if ("inc_right_tabs_task" == id) { // 任务中心
        		initTaskCenter();
        	}
        }
    });
}

function showRightLogin(id) {
	var top = '';
	var bottom = '';
	if (id=='inc_right_tabs_account') {
		bottom = '-395px';
	} else if (id=='inc_right_tabs_history') {
		top='0';
	} else if (id=='inc_right_tabs_record') {
		top='10px';
	} else if (id=='inc_right_tabs_inform') {
		top='10px';
	} else if (id=='inc_right_tabs_friend') {
		bottom = '0';
	} else if (id=='inc_right_tabs_contact') {
		bottom = '0';
	} else if (id=='inc_right_tabs_help') {
		bottom = '0';
	} else if (id=='inc_right_tabs_feedback') {
		top = '-395px';
	}else if (id=='inc_right_tabs_CDK') {
		top = '-395px';
	}else if (id=='inc_right_tabs_task') {
		top = '-395px';
	}
	
	$("."+id+" .inc_right_tabs_inside").after($("#inc_right_login"));
    $("#inc_right_login").css({"top":top,"bottom":bottom}).removeClass("hide");
}

(function($){
    $(window).on("load",function(){
        $(".slide").mCustomScrollbar();
    });
})(jQuery);
$(function(){
    $(".slide").mCustomScrollbar({
        theme:"dark",
    });
})
