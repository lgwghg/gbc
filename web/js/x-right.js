$(function () {
    //右侧导航栏
    //页面加载显示导航
    var timer1,timer2 ,timer3,timer4,timer5,timer6;
    var user = "0";
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
        var inc_padding = (document.documentElement.clientHeight - $(".inc_right_tabs_middle").height()) / 2;
        $(".inc_right_tabs_account").css("padding-top", inc_padding);
        $(".inc_right_tabs_feedback").css("padding-bottom", inc_padding);
        /*$(".inc_right_tabs_middle").css("top",((document.documentElement.clientHeight-$(".inc_right_tabs_middle").height())/2));*/
    }, 500)
    /*判断是否登录*/
    /*if(){/!*未登录*!/
     $(".inc_right_tabs_account div .iconfont").removeClass("hide");
     }
     else{/!*已登录*!/
     $(".inc_right_tabs_account div img").removeClass("hide");
     }*/
    //隐藏登陆框
    $("#inc_right_login").on("mouseenter",function(){
        $(this).removeClass("hide");
    }).on("mouseleave",function(){
        $(this).addClass("hide");
    })
    /*我的账户*/
    $(".inc_right_tabs_account .inc_right_tabs_inside").on("click",function(){
        if(user == ""){//判断是否登录
            $(this).after($("#inc_right_login"));
            $("#inc_right_login").css({"top":"","bottom":"-395px"}).removeClass("hide");
        }
        else{
            if($(this).hasClass("checked") == true){
                $(this).removeClass("checked");
                $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
                setTimeout(function () {
                    $("#inc_right_tabs_account").addClass("hide");
                },200)
            }
            else{
                if($(".inc_right_tabs li .inc_right_tabs_inside").hasClass("checked") == true){
                    $("#inc_right_tabs_account").find(".slide-content").css("margin-top","800px").animate({
                        marginTop:'0'
                    },300);
                }
                $(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
                $(this).addClass("checked");
                $(".slide").addClass("hide");
                $("#inc_right_tabs_account").removeClass("hide");
                $(".inc_right").addClass("big_load_one").removeClass("big_load_two");
            }
        }
    })
    /*投注历史*/
    $(".inc_right_tabs_history .inc_right_tabs_inside").on("click",function(){
        if(user == ""){//判断是否登录
            $(this).after($("#inc_right_login"));
            $("#inc_right_login").css({"top":"0","bottom":""}).removeClass("hide");
        }
        else {
            if ($(this).hasClass("checked") == true) {
                $(this).removeClass("checked");
                $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
                setTimeout(function () {
                    $("#inc_right_tabs_history").addClass("hide");
                }, 200)
            }
            else {
                if ($(".inc_right_tabs li .inc_right_tabs_inside").hasClass("checked") == true) {
                    $("#inc_right_tabs_history").find(".slide-content").css("margin-top", "800px").animate({
                        marginTop: '0'
                    }, 300);
                }
                $(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
                $(this).addClass("checked");
                $(".slide").addClass("hide");
                $("#inc_right_tabs_history").removeClass("hide");
                $(".inc_right").addClass("big_load_one").removeClass("big_load_two");
            }
        }
    })
    /*兑换记录*/
    $(".inc_right_tabs_record .inc_right_tabs_inside").on("click",function(){
        if(user == ""){//判断是否登录
            $(this).after($("#inc_right_login"));
            $("#inc_right_login").css({"top":"10px","bottom":""}).removeClass("hide");
        }
        else {
            if ($(this).hasClass("checked") == true) {
                $(this).removeClass("checked");
                $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
                setTimeout(function () {
                    $("#inc_right_tabs_record").addClass("hide");
                }, 200)
            }
            else {
                if ($(".inc_right_tabs li .inc_right_tabs_inside").hasClass("checked") == true) {
                    $("#inc_right_tabs_record").find(".slide-content").css("margin-top", "800px").animate({
                        marginTop: '0'
                    }, 300);
                }
                $(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
                $(this).addClass("checked");
                $(".slide").addClass("hide");
                $("#inc_right_tabs_record").removeClass("hide");
                $(".inc_right").addClass("big_load_one").removeClass("big_load_two");
            }
        }
    })
    /*我的通知*/
    $(".inc_right_tabs_inform .inc_right_tabs_inside").on("click",function(){
        if(user == ""){//判断是否登录
            $(this).after($("#inc_right_login"));
            $("#inc_right_login").css({"top":"10px","bottom":""}).removeClass("hide");
        }
        else {
            if ($(this).hasClass("checked") == true) {
                $(this).removeClass("checked");
                $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
                setTimeout(function () {
                    $("#inc_right_tabs_inform").addClass("hide");
                }, 200)
            }
            else {
                if ($(".inc_right_tabs li .inc_right_tabs_inside").hasClass("checked") == true) {
                    $("#inc_right_tabs_inform").find(".slide-content").css("margin-top", "800px").animate({
                        marginTop: '0'
                    }, 300);
                }
                $(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
                $(this).addClass("checked");
                $(".slide").addClass("hide");
                $("#inc_right_tabs_inform").removeClass("hide");
                $(".inc_right").addClass("big_load_one").removeClass("big_load_two");
            }
        }
    })
    /*推荐好友*/
    $(".inc_right_tabs_friend .inc_right_tabs_inside").on("click",function(){
        if(user == ""){//判断是否登录
            $(this).after($("#inc_right_login"));
            $("#inc_right_login").css({"top":"","bottom":"0"}).removeClass("hide");
        }
        else {
            if ($(this).hasClass("checked") == true) {
                $(this).removeClass("checked");
                $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
                setTimeout(function () {
                    $("#inc_right_tabs_friend").addClass("hide");
                }, 200)
            }
            else {
                if ($(".inc_right_tabs li .inc_right_tabs_inside").hasClass("checked") == true) {
                    $("#inc_right_tabs_friend").find(".slide-content").css("margin-top", "800px").animate({
                        marginTop: '0'
                    }, 300);
                }
                $(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
                $(this).addClass("checked");
                $(".slide").addClass("hide");
                $("#inc_right_tabs_friend").removeClass("hide");
                $(".inc_right").addClass("big_load_one").removeClass("big_load_two");
            }
        }
    }).on("mouseenter",function(){//推荐好友二维码
        var thisDome = $(this);
        timer6 = setTimeout(function(){
            thisDome.find(".invite_friends").removeClass("hide");
        },250)
    }).on("mouseleave",function(){
        var thisDome = $(this);
        clearTimeout(timer6);
        thisDome.find(".invite_friends").addClass("hide");
    })
    /*联系我们*/
    $(".inc_right_tabs_contact .inc_right_tabs_inside").on("click",function(){
        if(user == ""){//判断是否登录
            $(this).after($("#inc_right_login"));
            $("#inc_right_login").css({"top":"","bottom":"0"}).removeClass("hide");
        }
        else {
            if ($(this).hasClass("checked") == true) {
                $(this).removeClass("checked");
                $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
                setTimeout(function () {
                    $("#inc_right_tabs_contact").addClass("hide");
                }, 200)
            }
            else {
                if ($(".inc_right_tabs li .inc_right_tabs_inside").hasClass("checked") == true) {
                    $("#inc_right_tabs_contact").find(".slide-content").css("margin-top", "800px").animate({
                        marginTop: '0'
                    }, 300);
                }
                $(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
                $(this).addClass("checked");
                $(".slide").addClass("hide");
                $("#inc_right_tabs_contact").removeClass("hide");
                $(".inc_right").addClass("big_load_one").removeClass("big_load_two");
            }
        }
    })
    /*帮助中心*/
    $(".inc_right_tabs_help .inc_right_tabs_inside").on("click",function(){
        if(user == ""){//判断是否登录
            $(this).after($("#inc_right_login"));
            $("#inc_right_login").css({"top":"","bottom":"0"}).removeClass("hide");
        }
        else {
            if ($(this).hasClass("checked") == true) {
                $(this).removeClass("checked");
                $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
                setTimeout(function () {
                    $("#inc_right_tabs_help").addClass("hide");
                }, 200)
            }
            else {
                if ($(".inc_right_tabs li .inc_right_tabs_inside").hasClass("checked") == true) {
                    $("#inc_right_tabs_help").find(".slide-content").css("margin-top", "800px").animate({
                        marginTop: '0'
                    }, 300);
                }
                $(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
                $(this).addClass("checked");
                $(".slide").addClass("hide");
                $("#inc_right_tabs_help").removeClass("hide");
                $(".inc_right").addClass("big_load_one").removeClass("big_load_two");
            }
        }
    })
    /*用户反馈*/
    $(".inc_right_tabs_feedback .inc_right_tabs_inside").on("click",function(){
        if(user == ""){//判断是否登录
            $(this).after($("#inc_right_login"));
            $("#inc_right_login").css({"top":"-395px","bottom":""}).removeClass("hide");
        }
        else {
            if ($(this).hasClass("checked") == true) {
                $(this).removeClass("checked");
                $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
                setTimeout(function () {
                    $("#inc_right_tabs_feedback").addClass("hide");
                }, 200)
            }
            else {
                if ($(".inc_right_tabs li .inc_right_tabs_inside").hasClass("checked") == true) {
                    $("#inc_right_tabs_feedback").find(".slide-content").css("margin-top", "800px").animate({
                        marginTop: '0'
                    }, 300);
                }
                $(".inc_right_tabs_four .inc_right_tabs_inside").removeClass("checked");
                $(this).addClass("checked");
                $(".slide").addClass("hide");
                $("#inc_right_tabs_feedback").removeClass("hide");
                $(".inc_right").addClass("big_load_one").removeClass("big_load_two");
            }
        }
    })
    //返回顶部
    $(".inc_right_tabs_top").click(function () {
        $(this).removeClass("checked");
        $("html,body").animate({scrollTop: 0}, 500);
    })
    //窗口大小变化
    window.onresize = function () {
        if (window.innerWidth < 1277) {
            $(".inc_right_tabs_three").addClass("window_shrink").removeClass("window_unfold");
            timer1 = setTimeout(function () {
                $(".inc_right_tabs_big").css("background-color", "transparent");
                $(".inc_right_tabs_three").css("margin-left", "35px");
            }, 200);
            $(".inc_right_tabs_bg").removeClass("hide");
        }
        else if (window.innerWidth >= 1277) {
            $(".inc_right_tabs_three").addClass("window_unfold").removeClass("window_shrink");
            timer2 = setTimeout(function () {
                $(".inc_right_tabs_big").css("background-color", "transparent");
                $(".inc_right_tabs_three").css("margin-left", "0");
            }, 200);
            $(".inc_right_tabs_bg").addClass("hide");
        }
    }
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
        if(window.innerWidth<1277 && $(".inc_right_tabs li .inc_right_tabs_inside").hasClass("checked") != true){
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
        $('#myModal').modal('hide');
    })
    $("body").click(function(){
        $(".inc_right_tabs li .inc_right_tabs_inside").removeClass("checked");
        if($(".inc_right").hasClass("big_load_one") == true){
            $(".inc_right").addClass("big_load_two").removeClass("big_load_one");
        }
        setTimeout(function () {
            $(".slide").addClass("hide");
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
    })
})
