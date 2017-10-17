/**
 * 初始化翻硬币下注金币进度条事件
 * @param value 默认值
 * @param min 最小值
 * @param max 最大值
 */
function initSlider(value,min, max) {
	if (min == null) {
		min = 1000;
	}
	if (max == null) {
		max = 500000
	}
	//金额进度条
    $(".ex8").slider({
        min: min,
        max: max,//这里填当前用户金币总数
        step: 1,
        value: value,
        tooltip: 'always'
    });
    $('.match-list-bottom-pour .view1 input').val(min);
    $('.match-list-bottom-pour .view4 .pull-left').html(min + "金币");
    $(".match-list-bottom-pour .ex8").slider('setValue', min);
   //输入框输入翻硬币游戏金额
    $('.match-list-bottom-pour .view1 input').bind('input propertychange', function () {
        if ($(this).val() < min) {
            $(this).parent().next().find(".ex8").slider('setValue', min);
        }
        else if($(this).val() >= min && $(this).val()<=max){
            $(this).parent().next().find(".ex8").slider('setValue', parseFloat($(this).val()));
            $(this).parent().next().find(".ex8").change();
        }
        else {//输入金额大于钱包金额时
            $(this).parent().next().find(".ex8").slider('setValue', max);//这里填当前用户G币总数
            $(this).parent().next().find(".ex8").change();
        }
    });
   //输入框输入翻硬币游戏金额
    $('.match-list-bottom-pour .view1 input').bind('blur', function () {
        if ($(this).val() < min) {
        	 $(this).val(min);
        }
        if ($(this).val() > max) {//输入金额大于钱包金额时
            $(this).val(max);
        }
    });
    
    //点击All in
    $(".match-list-bottom-pour .view4 .two").click(function () {
        $(this).parent().prev().find(".ex8").slider('setValue', max);//这里填当前用户G币总数
        $(this).parent().prev().find(".ex8").change();
        $('.match-list-bottom-pour .view1 input').val(max);
    })

    //拖动金额条
    $(".match-list-bottom-pour .ex8").on("change", function () {
        $(this).parent().next().find(".one").html($(this).slider('getValue') + "金币");
        $(this).parent().prev().find("input").val($(this).slider('getValue'));
    });

    
}