$(function(){
    var flag = true;
	$(".c-betwrap").on("click",function(){
		if(flag){
			$(this).find(".c-betindication span:eq(0)").css({"transition":"transform 0.8s","transform":"rotate(40deg)"});
			$(this).find(".c-betindication span:eq(1)").css({"transition":"transform 0.8s","transform":"translateY(-13px) rotate(-40deg)"});
            $(this).find(".c-bet").hide();
            $(this).next(".table-responsive").addClass("cuxian").removeClass("xiaoshi");
			flag = false;
		} else {
			$(this).find(".c-betindication span:eq(0)").css({"transition":"transform 0.8s","transform":"rotate(-40deg)"});
			$(this).find(".c-betindication span:eq(1)").css({"transition":"transform 0.8s","transform":"translateY(-13px) rotate(40deg)"});   
            $(this).next(".table-responsive").addClass("xiaoshi").removeClass("cuxian");
            $(this).find(".c-bet").show();
			flag = true;
		}
	});
	$(".xxr").on("click",function(){
		$(this).parents(".fatherwrap").find(".c-betindication span:eq(0)").css({"transition":"transform 2s","transform":"rotate(-40deg)"});
		$(this).parents(".fatherwrap").find(".c-betindication span:eq(1)").css({"transition":"transform 2s","transform":"translateY(-13px) rotate(40deg)"});
		$(this).parents(".table-responsive").removeClass("cuxian");
        $(this).parents(".fatherwrap").find(".c-bet").show(); 
        $(".c-betindication").removeClass("betactive");
        flag = true;
	});
	$(document).on("click",function(e){
		  var _con = $('.fatherwrap');   // 设置目标区域
		  if(!_con.is(e.target) && _con.has(e.target).length === 0 && flag == false){
		       $('.fatherwrap').find(".c-betindication span:eq(0)").css({"transition":"transform 2s","transform":"rotate(-40deg)"});
		       $('.fatherwrap').find(".c-betindication span:eq(1)").css({"transition":"transform 2s","transform":"translateY(-13px) rotate(40deg)"});
		       $(".table-responsive").removeClass("cuxian");
		       $(".fatherwrap").find(".c-bet").show();
		       $(".c-betindication").removeClass("betactive");
		       flag = true;
		  }
		});
	loadJcList();
});
 
function loadJcList() {
	var options = {};
	options.url = _path + "/userJc/list";
	options.pageSize = "6";

	$.pagination("pagination", options, function(data) {
		var content = '';
		if(data.length > 0) {
           	var userJc ;
			for(var i = 0; i<data.length; i++) {
				userJc = data[i];
				var gameResult = userJc.gameResult;

            	content += '<tr>';
            	if("1" == gameResult) {
					content += '<td style="padding-right: 0;"><span class="tip_circle tip_circletwo" style="background: #e4a713;">W</span></td>';
				} else if("0" == gameResult) {
					content += '<td style="padding-right: 0;"><span class="tip_circle tip_circletwo" style="background: #4f4c42;">L</span></td>';
				} else {
					content += '<td style="padding-right: 0;"><span class="tip_circle tip_circletwo" style="background: #0c6954;">N</span></td>';
				}
            	
            	content += '<td><span class="mr-30 nametip_c">'+userJc.homeTeam+' </span>';
            	content += '<span class="mr-30 nametip_c">'+userJc.gameRule+'</span>';
            	content += '<span class="nametip_c">'+userJc.awayTeam+'</span>';
	            content += '<br/><span class="nametip_c" style="max-width: 300px; color: #607d8b;">'+userJc.eventName+'</span></td>';
	            content += '<td><span class="nametip_c">'+userJc.jcGold+'</span><br/><span style="color: #607d8b;">本次下注</span></td>';
	            
	            if(userJc.endTime != null && userJc.endTime != undefined) {
	            	content += '<td><span class="nametip_c">'+userJc.victoryGoldNum+'</span><br/><span style="color: #607d8b;">本次收益</span></td>';
	            	content += '<td><span class="nametip_c" style="max-width: 150px;">'+getSmpFormatDateByLong(userJc.endTime, true)+'</span><br /><span style="color: #607d8b;">已结束/span></td>';
	            } else {
	            	content += '<td></td>';
	            	content += '<td><span class="nametip_c" style="max-width: 150px;">'+getSmpFormatDateByLong(userJc.startTime, true)+'</span><br /><span style="color: #607d8b;">开始时间</span></td>';
	            }
	            
	            var url = _path + "/gb/" + userJc.gbId;
	            content += '<td><button type="button" class="btn btn-primary btn-raised" onclick="window.open(\''+url+'\')">';
	            content += '<strong>查看</strong>';
	            content += '</button></td>';
				content += '</tr>';
			}
			
			if(data.length < 5) {
				var num = 6 - data.length;
				for(var i = 0; i<num; i++) {
					content += '<tr style="padding-right: 0;height:66px;background: #3d4148;"></tr>';
				}
			}
		} else {
			content = '<tr style="width:100%;height:410px"><td colspan="6"><div style="text-align: center;"><i class="iconfont icon-kule" style=" font-size: 100px;"></i></div><p style="font-size: 28px; text-align: center;">暂无数据</p></td></tr>';
		}
		$("#dataList").html(content);
	})
}
$(function(){
	$(".c-betwrap").mouseover(function(){
		$(this).find(".c-bet").addClass("betactive");
		$(this).find(".c-betindication").addClass("betactive");
	});
	$(".c-betwrap").mouseleave(function(){
		$(this).find(".c-bet").removeClass("betactive");
		$(this).find(".c-betindication").removeClass("betactive");
	});
})