$(function () {
    var noUser = '<div class="match-list-bottom-pour" id="noUser">' +
        '<p class="match-list-choose">' +
        '请先<a href="../16.0登录.html">登录</a>' +
        '</p>' +
        '</div>';
    var user = "1";
    //new
    $(".message-left").on("click", function () {/*点击左边*/
        if ($(this).parent().attr("top") == "1") {/*如果是顶部*/
            if (user == "") {/*未登录*/
                restoreTrapezoid();
                $("#noUser").remove();
                $(this).parent().after(noUser);
            }
            else {/*已登录*/
                if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {/*未开始未下注*/
                    restoreTrapezoid();
                    if($(this).parent().next().hasClass("match-list-bottom-pour") != true){
                        $("#nobet").find(".ex8").slider('setValue', 1000);
                        $("#nobet").find(".ex8").change();
                    }
                    $(this).parent().after($("#nobet"));
                    $("#nobet").removeClass("hide");
                    $("#nobet").find(".earnings").html(mul($(".ex8").slider('getValue'), 1.2));//这里填实际比赛倍数
                    if($(this).hasClass("message-left") == true){
                        $(this).parent().attr("pkujcteamnow","1");
                    }
                    else{
                        $(this).parent().attr("pkujcteamnow","2");
                    }
                }
                else if($(this).parent().attr("pkstate") == "0" && judge($(this).parent().attr("pkujcteam"))){/*未开始已下注*/
                    restoreTrapezoid();
                    if($(this).parent().next().next().attr("id") == 'nobet'){
                        $(this).parent().after($("#nobet"));
                        $("#nobet").removeClass("hide");
                    }
                    else{
                        $(this).parent().next().removeClass("hide");
                    }
                }
                else{/*其他情况*/
                    restoreTrapezoid();
                    $(this).parent().next().removeClass("hide");
                }
            }
        }
        else {/*如果是底部*/
            if (user == "") {/*未登录*/
                restoreNewTrapezoid();
                alert("您还未登录");/*这里改成后台的提示方法*/
            }
            else {/*已登录*/
                if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {/*未开始未下注*/
                    restoreNewTrapezoid();
                    if($(this).find("#new_play_nobet").hasClass("x-match-list-bottom-pour") != true){
                        $("#new_play_nobet").find(".ex8").slider('setValue', 1000);
                        $("#new_play_nobet").find(".ex8").change();
                    }
                    $(this).find("ul").addClass("hide");
                    $('#new_play_nobet').appendTo($(this)).removeClass("hide");
                    $(this).addClass("bg_2f");
                }
            }
        }
    }).mouseover(function () {/*左侧鼠标经过效果*/
        if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {
            $(this).css("background-color", "#2f5a56");
        }
    }).mouseout(function () {
        if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {
            $(this).css("background-color", "#2e3238");
        }
    });
    $(".message-right").on("click", function () {/*点击右边*/
        if ($(this).parent().attr("top") == "1") {/*如果是顶部*/
            if (user == "") {/*未登录*/
                restoreTrapezoid();
                $("#noUser").remove();
                $(this).parent().after(noUser);
            }
            else {/*已登录*/
                if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {/*未开始未下注*/
                    restoreTrapezoid();
                    if($(this).parent().next().hasClass("match-list-bottom-pour") != true){
                        $("#nobet").find(".ex8").slider('setValue', 0);
                        $("#nobet").find(".ex8").change();
                    }
                    $(this).parent().after($("#nobet"));
                    $("#nobet").removeClass("hide");
                    $("#nobet").find(".earnings").html(mul($("#nobet .ex8").slider('getValue'), 1.2));//这里填实际比赛倍数
                    if($(this).hasClass("message-left") == true){
                        $(this).parent().attr("pkujcteamnow","1");
                    }
                    else{
                        $(this).parent().attr("pkujcteamnow","2");
                    }
                }
                else if($(this).parent().attr("pkstate") == "0" && judge($(this).parent().attr("pkujcteam"))){/*未开始已下注*/
                    restoreTrapezoid();
                    if($(this).parent().next().next().attr("id") == 'nobet'){
                        $(this).parent().after($("#nobet"));
                        $("#nobet").removeClass("hide");
                    }
                    else{
                        $(this).parent().next().removeClass("hide");
                    }
                }
                else{/*其他情况*/
                    restoreTrapezoid();
                    $(this).parent().next().removeClass("hide");
                }
            }
        }
        else {/*如果是底部*/
            if (user == "") {/*未登录*/
                restoreNewTrapezoid();
                alert("您还未登录");/*这里改成后台的提示方法*/
            }
            else {/*已登录*/
                if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {/*未开始未下注*/
                    restoreNewTrapezoid();
                    if($(this).find("#new_play_nobet").hasClass("x-match-list-bottom-pour") != true){
                        $("#new_play_nobet").find(".ex8").slider('setValue', 1000);
                        $("#new_play_nobet").find(".ex8").change();
                    }
                    $(this).find("ul").addClass("hide");
                    $("#new_play_nobet").appendTo($(this)).removeClass("hide");
                    $(this).addClass("bg_2f");
                    $(this).find(".message-right-a").addClass("bg_2f");
                }
            }
        }
    }).mouseover(function () {/*右侧鼠标经过效果*/
        if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {
            $(this).css("background-color", "#2f5a56");
            $(this).find(".message-right-a").css("background-color", "#2f5a56");
        }
    }).mouseout(function () {
        if ($(this).parent().attr("pkstate") == "0" && !judge($(this).parent().attr("pkujcteam"))) {
            $(this).css("background-color", "#33383e");
            $(this).find(".message-right-a").css("background-color", "#33383e");
        }
    })

    //判断属性是否存在
    function judge(obj) {
        for (var i in obj) {//如果不为空，则会执行到这一步，返回true
            return true;
        }
        return false;
    }

    //点击再下注
    $(".match-list-bottom-pour .view1 .two").click(function () {
        $(this).parent().parent().addClass("hide");
        $("#nobet").find(".ex8").slider('setValue', 1000);
        $("#nobet").find(".ex8").change();
        $(this).parent().parent().after($("#nobet"));
        $("#nobet").removeClass("hide");
    })

    //新的再下注
    $(".match-list-bottom-pour-new .view1 .two").on("click",function(){
        restoreNewTrapezoid();
        var a = $(this).parent().parent();
        a.addClass("hide");
        a.parent().siblings(".middle").removeClass("hide");
        a.parent().siblings(".middle-change-team").addClass("hide");
        a.after($("#new_play_nobet").removeClass("hide"));
        a.parent().parent().find(".message-left,.message-right,.message-right-a").removeClass("bg_31");
        a.parent().addClass("bg_2f");
        a.parent().find(".message-right-a").addClass("bg_2f");
        $("#new_play_nobet").find(".ex8").slider('setValue', 1000);
        $("#new_play_nobet").find(".ex8").change();
    })

    //新玩法切换战队
    $(".middle-change-team").on("click",function(){
        var a = $(this).parent();
        if($(this).parent().find(".message-left").hasClass("bg_31") == true){
            a.find(".message-left").removeClass("bg_31");
            a.find(".message-left ul").removeClass("hide");
            a.find(".message-right,.message-right-a").addClass("bg_31");
            a.find(".message-right ul").addClass("hide").after(a.find(".match-list-bottom-pour-new"));
            a.attr("pkujcteam","2");
        }
        else{
            a.find(".message-left").addClass("bg_31");
            a.find(".message-left ul").addClass("hide").after(a.find(".match-list-bottom-pour-new"));
            a.find(".message-right,.message-right-a").removeClass("bg_31");
            a.find(".message-right ul").removeClass("hide");
            a.attr("pkujcteam","1");
        }
    })

    //点击message-left,message-right
    $(".message-left").click(function () {
        $(this).css("border-bottom-left-radius", "0");
        $(this).next().css("border-bottom-right-radius", "0");
        if($(this).parent().parent().find(".match-list-more-play").hasClass("match-list-more-play") == true){/*new-play*/
            $(this).parent().siblings(".match-list-more-play").removeClass("hide");
            $(this).parent().next().css("border-radius","0");
        }
    })
    $(".message-right").click(function () {
        $(this).prev().css("border-bottom-left-radius", "0");
        $(this).css("border-bottom-right-radius", "0");
        if($(this).parent().parent().find(".match-list-more-play").hasClass("match-list-more-play") == true){/*new-play*/
            $(this).parent().siblings(".match-list-more-play").removeClass("hide");
            $(this).parent().next().css("border-radius","0");
        }
    })

    //还原梯形选项卡
    function restoreTrapezoid() {
        $(".match-list-bottom-pour,.match-list-more-play").addClass("hide");
        $(".message-left").css("border-bottom-left-radius", "8px");
        $(".message-right").css("border-bottom-right-radius", "8px");
    }

    //还原新玩法选项卡
    function restoreNewTrapezoid(){
        $(".match-list-message[top='2'][pkstate='0'] .message-left ul,.match-list-message[top='2'][pkstate='0'] .message-right ul").removeClass("hide");
        $(".match-list-message[top='2'][pkstate='0'][pkujcteam='1'] .message-left ul,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .message-right ul").addClass("hide");
        $(".match-list-message[top='2'][pkstate='0'][pkujcteam='1'] .message-left .match-list-bottom-pour-new,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .message-right .match-list-bottom-pour-new").removeClass("hide");
        $(".match-list-message[top='2'][pkstate='0'][pkujcteam='1'] .middle,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .middle").addClass("hide");
        $(".match-list-message[top='2'][pkstate='0'][pkujcteam='1'] .middle-change-team,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .middle-change-team").removeClass("hide");
        $(".match-list-message[top='2'][pkstate='0'] .message-left,.match-list-message[top='2'][pkstate='0'] .message-right,.match-list-message[top='2'][pkstate='0'] .message-right-a").removeClass("bg_2f");
        $(".match-list-message[top='2'][pkstate='0'][pkujcteam='1'] .message-left,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .message-right,.match-list-message[top='2'][pkstate='0'][pkujcteam='2'] .message-right-a").addClass("bg_31");
    }

    //金额进度条
    $(".ex8").slider({
        min: 1000,
        max: 100000,//这里填当前用户金币总数
        step: 1,
        value: 0,
        tooltip: 'always'
    });

    //输入框输入竞猜金额
    $('.match-list-bottom-pour .view1 input').bind('input propertychange', function () {
        if ($(this).val() <= 100000) {
            $(this).parent().next().find(".ex8").slider('setValue', parseFloat($(this).val()));
            $(this).parent().next().find(".ex8").change();
        }
        else {//输入金额大于钱包金额时
            $(this).parent().next().find(".ex8").slider('setValue', 100000);//这里填当前用户金币总数
            $(this).parent().next().find(".ex8").change();
        }
    });
    //点击All in
    $(".match-list-bottom-pour .view4 .two").click(function () {
        $(this).parent().prev().find(".ex8").slider('setValue', 100000);//这里填当前用户G币总数
        $(this).parent().prev().find(".ex8").change();
    })

    //新玩法点击All in
    $("#new_play_nobet .new-two").click(function () {
        $(this).siblings(".ex8").slider('setValue', 100000);//这里填当前用户G币总数
        $(this).siblings(".ex8").change();
    })

    //拖动金额条
    $("#nobet .ex8").on("change", function () {
        $(this).parent().next().find(".one").html($(this).slider('getValue') + "G币");
        $(this).parent().prev().find("input").val($(this).slider('getValue'));
        $(this).parent().next().next().find(".earnings").html(mul($(this).slider('getValue'), 1.2));//这里填实际比赛倍数
    });

    //新玩法拖动金额条
    $("#new_play_nobet .ex8").on("change", function () {
        $(this).parent().prev().find("input").val($(this).slider('getValue'));
        $(this).parent().next().find(".earnings").html(mul($(this).slider('getValue'), 1.2));//这里填实际比赛倍数
    });

    //输入框输入竞猜金额
    $('.match-list-bottom-pour .view1 input').bind('input propertychange', function () {
        if ($(this).val() < 1000) {
            $(this).parent().next().find(".ex8").slider('setValue', 1000);
        }
        else if($(this).val() >= 1000 && $(this).val()<=100000){
            $(this).parent().next().find(".ex8").slider('setValue', parseFloat($(this).val()));
            $(this).parent().next().find(".ex8").change();
        }
        else {//输入金额大于钱包金额时
            $(this).parent().next().find(".ex8").slider('setValue', 100000);//这里填当前用户G币总数
            $(this).parent().next().find(".ex8").change();
        }
    });

    //新玩法输入框输入竞猜金额
    $('#new_play_nobet .view1 input').bind('input propertychange', function () {
        if ($(this).val() < 1000) {
            $(this).parent().next().find(".ex8").slider('setValue', 1000);
        }
        else if($(this).val() >= 1000 && $(this).val()<=100000){
            $(this).parent().next().find(".ex8").slider('setValue', parseFloat($(this).val()));
            $(this).parent().next().find(".ex8").change();
        }
        else {//输入金额大于钱包金额时
            $(this).parent().next().find(".ex8").slider('setValue', 100000);//这里填当前用户G币总数
            $(this).parent().next().find(".ex8").change();
        }
    });

    //阻止事件传播
    $("#new_play_nobet .view1 input").on("click",function(e){
        e.stopPropagation();
    })
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

    //减法浮点数bug解决办法
    function accSub(arg1, arg2) {
        var r1, r2, m, n;
        try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
        try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
        m = Math.pow(10, Math.max(r1, r2));
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    }
})