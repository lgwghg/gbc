$(function () {
    /*//未开始且未下注
    $(".match-list-message-one .message-left").mouseover(function () {
        $(this).find(".co_31").css("color", "#fff");
    }).mouseout(function () {
        $(this).find(".co_31").css("color", "#31c3a2");
    }).click(function () {
    	alert(1);
        restoreTrapezoid();
        if($(this).parent().next().hasClass("match-list-bottom-pour") != true){
            $("#nobet").find(".ex8").slider('setValue', 0);
            $("#nobet").find(".ex8").change();
        }
        $("#nobet").appendTo($(this).parent().parent());
        $("#nobet").removeClass("hide");
        $(this).parent().parent().find(".match-list-choose").addClass("hide");
        $(this).addClass("message-right-o");
        $(this).next().addClass("message-right-x").removeClass("message-right-o");
        $(this).next().find(".message-right-a").addClass("message-right-x").removeClass("message-right-o");
        $(this).find(".co_31").addClass("co_00");
        $(this).next().find(".co_31").removeClass("co_00");
    })
    $(".match-list-message-one .message-right").mouseover(function () {
        $(this).css("background-color", "#2f5a56");
        $(this).find(".message-right-x").css("background-color", "#2f5a56");
        $(this).find(".co_31").css("color", "#fff");
    }).mouseout(function () {
        $(this).css("background-color", "#33383e");
        $(this).find(".message-right-x").css("background-color", "#33383e");
        $(this).find(".co_31").css("color", "#31c3a2");
    }).click(function () {
    	alert(1);
        if ($(this).hasClass("message-right-x") == true) {
            restoreTrapezoid();
            if($(this).parent().next().hasClass("match-list-bottom-pour") != true){
                $("#nobet").find(".ex8").slider('setValue', 0);
                $("#nobet").find(".ex8").change();
            }
            $("#nobet").appendTo($(this).parent().parent());
            $("#nobet").removeClass("hide");
            $(this).parent().parent().find(".match-list-choose").addClass("hide");
            $(this).css("background-color", "#33383e").find(".message-right-x").css("background-color", "#33383e");
            $(this).removeClass("message-right-x").addClass("message-right-o");
            $(this).find(".message-right-a").removeClass("message-right-x").addClass("message-right-o");
            $(this).prev().removeClass("message-right-o");
            $(this).prev().find(".co_31").removeClass("co_00");
            $(this).find(".co_31").addClass("co_00");
        }
    })
    //未开始已下注
    $(".match-list-message-two .message-left").click(function () {
        restoreTrapezoid();
        if($(this).parent().next().next().attr("id") == 'nobet'){
            $("#nobet").appendTo($(this).parent().parent());
            $("#nobet").removeClass("hide");
        }
        else{
            $(this).parent().next().removeClass("hide");
        }
    })
    $(".match-list-message-two .message-right").click(function () {
        restoreTrapezoid();
        if($(this).parent().next().next().attr("id") == 'nobet'){
            $("#nobet").appendTo($(this).parent().parent());
            $("#nobet").removeClass("hide");
        }
        else{
            $(this).parent().next().removeClass("hide");
        }
    })

    //点击再下注
    $(".match-list-bottom-pour .view1 .two").click(function(){
        $(this).parent().parent().addClass("hide");
        $("#nobet").find(".ex8").slider('setValue', 0);
        $("#nobet").find(".ex8").change();
        $("#nobet").appendTo($(this).parent().parent().parent());
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
        $(".match-list-bottom-pour").addClass("hide");
        $(".message-left").removeClass("message-right-o");
        $(".message-right,.message-right-a").addClass("message-right-x").removeClass("message-right-o");
        $(".co_31").removeClass("co_00");
        $(".message-left").css("border-bottom-left-radius", "8px");
        $(".message-right").css("border-bottom-right-radius", "8px");
    }

    //金额进度条
    $(".ex8").slider({
        min: 0,
        max: 1000,//这里填当前用户G币总数
        step: 1,
        value: 0,
        tooltip: 'always'
    });
    //点击All in
    $(".match-list-bottom-pour .view4 .two").click(function () {
        $(this).parent().prev().find(".ex8").slider('setValue', 1000);//这里填当前用户G币总数
        $(this).parent().prev().find(".ex8").change();
    })
    //拖动金额条
    $(".ex8").on("change", function () {
        $(this).parent().next().find(".one").html($(this).slider('getValue') + "G币");
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
            $(this).parent().next().find(".ex8").slider('setValue', 1000);//这里填当前用户G币总数
            $(this).parent().next().find(".ex8").change();
        }
    });*/
    
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

//减法 
function accSub(arg1, arg2) { 
    var r1, r2, m, n; 
    try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 } 
    try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 } 
    m = Math.pow(10, Math.max(r1, r2)); 
    n = (r1 >= r2) ? r1 : r2; 
    return ((arg1 * m - arg2 * m) / m).toFixed(n); 
} 
function exchangeEx8(){
	
	//当前用户G币
    var goldNum = _userGold + _sysGold;
    if(goldNum>=100000){
    	//投注上线
    	goldNum = 100000;
    }
    //金额进度条
    $(".ex8").slider({
        min: minGold,
        max: goldNum,//这里填当前用户G币总数
        step: 1,
        value: 0,
        tooltip: 'always'
    });
    
    //点击All in
    $(".match-list-bottom-pour .view4 .two").click(function () {
        $(this).parent().prev().find(".ex8").slider('setValue', parseFloat(goldNum));//这里填当前用户G币总数
        $(this).parent().prev().find(".ex8").change();
    })
    
    //新玩法点击All in
    $("#new_play_nobet .new-two").click(function () {
        $(this).siblings(".ex8").slider('setValue', parseFloat(goldNum));//这里填当前用户G币总数
        $(this).siblings(".ex8").change();
    })
    
    //拖动金额条
    $("#nobet .ex8").on("change", function () {
        $(this).parent().next().find(".one").html($(this).slider('getValue') + "金币");
        $(this).parent().prev().find("input").val($(this).slider('getValue'));
        $(this).parent().next().next().find(".earnings").html(mul($(this).slider('getValue'), parseFloat(rule)));//这里填实际比赛倍数
    });
    
    //新玩法拖动金额条
    $("#new_play_nobet .ex8").on("change", function () {
        $(this).parent().prev().find("input").val($(this).slider('getValue'));
        $(this).parent().next().find(".earnings").html(mul($(this).slider('getValue'), parseFloat(rule)));//这里填实际比赛倍数
    });
    
    //输入框输入竞猜金额
    $('.match-list-bottom-pour .view1 input').bind('input propertychange', function () {
        if ($(this).val() < minGold) {
        	$(this).parent().next().find(".ex8").slider('setValue', parseFloat(minGold));
        }
        else if($(this).val() >= minGold && $(this).val()<=goldNum){
        	$(this).parent().next().find(".ex8").slider('setValue', parseFloat($(this).val()));
            $(this).parent().next().find(".ex8").change();
        }
        else {//输入金额大于钱包金额时
            $(this).parent().next().find(".ex8").slider('setValue', parseFloat(goldNum));//这里填当前用户G币总数
            $(this).parent().next().find(".ex8").change();
        }
    });
    
    //新玩法输入框输入竞猜金额
    $('#new_play_nobet .view1 input').bind('input propertychange', function () {
        if ($(this).val() < minGold) {
        	$(this).parent().next().find(".ex8").slider('setValue', parseFloat(minGold));
        }
        else if($(this).val() >= minGold && $(this).val()<=goldNum){
        	$(this).parent().next().find(".ex8").slider('setValue', parseFloat($(this).val()));
            $(this).parent().next().find(".ex8").change();
        }
        else {//输入金额大于钱包金额时
            $(this).parent().next().find(".ex8").slider('setValue', parseFloat(goldNum));//这里填当前用户G币总数
            $(this).parent().next().find(".ex8").change();
        }
    });
    
    //阻止事件传播
    $("#new_play_nobet .view1 input").on("click",function(e){
    	e.stopPropagation();
    })
}