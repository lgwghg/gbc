$(function () {
    //点击效果
    //右边鼠标经过效果
    $(".message-right").mouseover(function () {/*new-play*/
        if($(this).parent().hasClass("match-list-message-one") == true || $(this).parent().hasClass("match-list-message-two") == true || $(this).parent().hasClass("match-list-message-nine") == true){
            $(this).css("background-color", "#2f5a56");
            $(this).find(".message-right-a").css("background-color", "#2f5a56");
        }
    }).mouseout(function () {
        if($(this).parent().hasClass("match-list-message-one") == true || $(this).parent().hasClass("match-list-message-two") == true || $(this).parent().hasClass("match-list-message-nine") == true){
            $(this).css("background-color", "#33383e");
            $(this).find(".message-right-a").css("background-color", "#33383e");
        }
    })
    //未登录
    $(".match-list-message-one .message-left").click(function () {
        restoreTrapezoid();
        $(this).parent().next().removeClass("hide");
    })
    $(".match-list-message-one .message-right").click(function () {
        restoreTrapezoid();
        $(this).parent().next().removeClass("hide");
    })
    //比赛未开始未下注
    $(".match-list-message-two .message-left").click(function () {
        restoreTrapezoid();
        if($(this).parent().next().hasClass("match-list-bottom-pour") != true){
            $("#nobet").find(".ex8").slider('setValue', 0);
            $("#nobet").find(".ex8").change();
        }
        $(this).parent().after($("#nobet"));
        $("#nobet").removeClass("hide");
        $(this).addClass("message-right-o message-checked");
        $(this).next().addClass("message-right-x").removeClass("message-right-o message-checked");
        $(this).next().find(".message-right-a").addClass("message-right-x").removeClass("message-right-o message-checked");
        if($(this).parent().next().next().hasClass("match-list-more-play") == true){/*new-play*/
            $(this).parent().siblings(".match-list-more-play").removeClass("hide");
            $(this).parent().next().css("border-radius","0");
        }
    })
    $(".match-list-message-two .message-right").click(function () {
        if ($(this).hasClass("message-right-x") == true) {
            restoreTrapezoid();
            if($(this).parent().next().hasClass("match-list-bottom-pour") != true){
                $("#nobet").find(".ex8").slider('setValue', 0);
                $("#nobet").find(".ex8").change();
            }
            $(this).parent().after($("#nobet"));
            $("#nobet").removeClass("hide");
            $(this).removeClass("message-right-x").addClass("message-right-o message-checked");
            $(this).find(".message-right-a").removeClass("message-right-x").addClass("message-right-o");
            $(this).prev().removeClass("message-right-o message-checked");
            if($(this).parent().next().next().hasClass("match-list-more-play") == true){/*new-play*/
                $(this).parent().siblings(".match-list-more-play").removeClass("hide");
                $(this).parent().next().css("border-radius","0");
            }
        }
    })
    //比赛未开始已下注
    $(".match-list-message-three .message-left").click(function () {
        restoreTrapezoid();
        if($(this).hasClass("message-checked") == true){
            $(this).addClass("message-right-o");
        }
        else{
            $(this).next().addClass("message-right-o").removeClass("message-right-x");
            $(this).next().find(".message-right-a").addClass("message-right-o").removeClass("message-right-x");
        }
        if($(this).parent().next().next().attr("id") == 'nobet'){
            $(this).parent().after($("#nobet"));
            $("#nobet").removeClass("hide");
        }
        else{
            $(this).parent().next().removeClass("hide");
        }
    })
    $(".match-list-message-three .message-right").click(function () {
            restoreTrapezoid();
            if($(this).hasClass("message-checked") == true){
                $(this).addClass("message-right-o").removeClass("message-right-x");
                $(this).find(".message-right-a").addClass("message-right-o").removeClass("message-right-x");
            }
            else{
                $(this).prev().addClass("message-right-o");
            }
            if($(this).parent().next().next().attr("id") == 'nobet'){
                $(this).parent().after($("#nobet"));
                $("#nobet").removeClass("hide");
            }
            else{
                $(this).parent().next().removeClass("hide");
            }
    })
    //点击切换投注队伍
    $(".match-list-bottom-pour .view1 .three").click(function(){
        /*这里添加弹出框，询问是否切换下注战队*/
        /*下面确认切换战队后执行*/
        if($(this).parent().parent().prev().find(".message-left").hasClass("message-checked") == true){
            $(this).parent().parent().prev().find(".message-left").removeClass("message-right-o message-checked");
            $(this).parent().parent().prev().find(".message-right").addClass("message-right-o message-checked").removeClass("message-right-x");
            $(this).parent().parent().prev().find(".message-right-a").addClass("message-right-o").removeClass("message-right-x");
        }
        else{
            $(this).parent().parent().prev().find(".message-left").addClass("message-right-o message-checked");
            $(this).parent().parent().prev().find(".message-right").removeClass("message-right-o message-checked").addClass("message-right-x");
            $(this).parent().parent().prev().find(".message-right-a").removeClass("message-right-o").addClass("message-right-x");
        }
    })
    //比赛已开始未下注,比赛已结束未下注
    $(".match-list-message-four .message-left,.match-list-message-six .message-left").click(function () {
        restoreTrapezoid();
        $(this).parent().next().removeClass("hide");
    })
    $(".match-list-message-four .message-right,.match-list-message-six .message-right").click(function () {
        restoreTrapezoid();
        $(this).parent().next().removeClass("hide");
    })
    //比赛已开始已下注,比赛已结束已下注
    $(".match-list-message-five .message-left,.match-list-message-seven .message-left").click(function () {
        restoreTrapezoid();
        $(this).parent().next().removeClass("hide");
    })
    $(".match-list-message-five .message-right,.match-list-message-seven .message-right").click(function () {
        restoreTrapezoid();
        $(this).parent().next().removeClass("hide");
    })
    //比赛已取消
    $(".match-list-message-eight .message-left").click(function () {
        restoreTrapezoid();
        $(this).parent().next().removeClass("hide");
    })
    $(".match-list-message-eight .message-right").click(function () {
        restoreTrapezoid();
        $(this).parent().next().removeClass("hide");
    })

    //新玩法
    $(".message-left").on("click",function(){/*new-play*/
        if($(this).parent().hasClass("match-list-message-nine") == true){
            restoreNewTrapezoid();
            $(this).find("ul").addClass("hide");
            $('#new_play_nobet').appendTo($(this)).removeClass("hide");
            $(this).addClass("bg_2f");
        }
    })
    $(".message-right").on("click",function(){
        if($(this).parent().hasClass("match-list-message-nine") == true){
            restoreNewTrapezoid();
            $(this).find("ul").addClass("hide");
            $("#new_play_nobet").appendTo($(this)).removeClass("hide");
            $(this).addClass("bg_2f");
            $(this).find(".message-right-a").addClass("bg_2f");
        }
    })

    //点击再下注
    $(".match-list-bottom-pour .view1 .two").click(function(){
        $(this).parent().parent().addClass("hide");
        $("#nobet").find(".ex8").slider('setValue', 0);
        $("#nobet").find(".ex8").change();
        $(this).parent().parent().after($("#nobet"));
        $("#nobet").removeClass("hide");
    })


    //点击message-left,message-right
    $(".message-left").click(function () {
        $(this).css("border-bottom-left-radius", "0");
        $(this).next().css("border-bottom-right-radius", "0");
    })
    $(".message-right").click(function () {
        $(this).prev().css("border-bottom-left-radius", "0");
        $(this).css("border-bottom-right-radius", "0");
    })

    //还原梯形选项卡
    function restoreTrapezoid(){
        $(".match-list-bottom-pour,.match-list-more-play").addClass("hide");
        $(".match-list-message-two .message-left").removeClass("message-right-o");
        $(".match-list-message-two .message-right,.match-list-message-two .message-right-a").addClass("message-right-x").removeClass("message-right-o");
        $(".message-left").css("border-bottom-left-radius", "8px");
        $(".message-right").css("border-bottom-right-radius", "8px");
    }

    //金额进度条
    $(".ex8").slider({
        min: 0,
        max: 1000,//这里填当前用户金币总数
        step: 1,
        value: 0,
        tooltip: 'always'
    });
    //点击All in
    $(".match-list-bottom-pour .view4 .two").click(function () {
        $(this).parent().prev().find(".ex8").slider('setValue', 1000);//这里填当前用户金币总数
        $(this).parent().prev().find(".ex8").change();
    })
    //拖动金额条
    $(".ex8").on("change", function () {
        $(this).parent().next().find(".one").html($(this).slider('getValue') + "金币");
        $(this).parent().prev().find("input").val($(this).slider('getValue'));
        $(this).parent().next().next().find(".earnings").html(mul($(this).slider('getValue'), 1.2));//这里填实际比赛倍数
    });
    //输入框输入竞猜金额
    $('.match-list-bottom-pour .view1 input').bind('input propertychange', function () {
        if ($(this).val() <= 1000) {
            $(this).parent().next().find(".ex8").slider('setValue', parseFloat($(this).val()));
            $(this).parent().next().find(".ex8").change();
        }
        else {//输入金额大于钱包金额时
            $(this).parent().next().find(".ex8").slider('setValue', 1000);//这里填当前用户金币总数
            $(this).parent().next().find(".ex8").change();
        }
    });
    //乘法浮点数bug解决办法
    function mul(a, b) {
        var c = 0,
            d = a.toString(),
            e = b.toString();
        try {
            c += d.split(".")[1].length;
        } catch (f) {
        }
        try {
            c += e.split(".")[1].length;
        } catch (f) {
        }
        return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
    }

    //新的再下注
    $(".match-list-bottom-pour-new .view1 .two").on("click",function(){/*new-play*/
        restoreNewTrapezoid();
        var a = $(this).parent().parent();
        a.addClass("hide");
        a.parent().siblings(".middle").removeClass("hide");
        a.parent().siblings(".middle-change-team").addClass("hide");
        a.after($("#new_play_nobet").removeClass("hide"));
        a.parent().parent().find(".message-left,.message-right,.message-right-a").removeClass("bg_31");
    })


    //新玩法
    //点击确认投注
    $("#new_play_nobet .view1 button").on("click",function(e){/*new-play*/
        var a = $("#new_play_nobet").parent().parent();
        var b = $("#new_play_nobet").parent();
        a.attr("class","match-list-message match-list-message-ten");
        $("#new_play_nobet").addClass("hide");
        $("#new_play_nobet").after(a.parent().find(".match-list-bottom-pour-new").removeClass("hide"));
        a.find(".message-left,.message-right,.message-right-a").removeClass("bg_2f");
        if(b.hasClass("message-right") == true){
            b.find(".message-right-a").addClass("bg_31");
        }
        $("#new_play_nobet").parent().addClass("bg_31");
        a.find(".middle").addClass("hide");
        a.find(".middle-change-team").removeClass("hide");
        a.find(".message-right,.message-right-a").css("background-color","");
        e.stopPropagation();
    })

    //新玩法切换战队
    $(".middle-change-team").on("click",function(){/*new-play*/
        var a = $(this).parent();
        if($(this).parent().find(".message-left").hasClass("bg_31") == true){
            a.find(".message-left").removeClass("bg_31");
            a.find(".message-left ul").removeClass("hide");
            a.find(".message-right,.message-right-a").addClass("bg_31");
            a.find(".message-right ul").addClass("hide").after(a.find(".match-list-bottom-pour-new"));
        }
        else{
            a.find(".message-left").addClass("bg_31");
            a.find(".message-left ul").addClass("hide").after(a.find(".match-list-bottom-pour-new"));
            a.find(".message-right,.message-right-a").removeClass("bg_31");
            a.find(".message-right ul").removeClass("hide");
        }
    })

    //还原新玩法选项卡
    function restoreNewTrapezoid(){/*new-play*/
        $(".match-list-message-nine .message-left ul,.match-list-message-nine .message-right ul").removeClass("hide");
        $(".match-list-message-nine .message-left,.match-list-message-nine .message-right,.match-list-message-nine .message-right-a").removeClass("bg_2f");
        var a = $("#new_play_nobet").parent();
        if(a.parent().hasClass("match-list-message-ten") == true){
            if(a.hasClass("message-left") == true){
                a.addClass("bg_31");
            }
            else{
                a.addClass("bg_31");
                a.find(".message-right-a").addClass("bg_31");
            }
            a.find(".match-list-bottom-pour-new").removeClass("hide");
            a.parent().find(".middle").addClass("hide");
            a.parent().find(".middle-change-team").removeClass("hide");
        }
    }
})