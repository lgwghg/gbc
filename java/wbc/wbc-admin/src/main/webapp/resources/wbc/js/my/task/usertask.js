$(function() {
	countTime();
	//点击签到
	$('#task_wqd').click(function(e) {
	    userSign("");
	    e.stopPropagation();
	});
});

/**
 * 领取G币完成任务
 */
function completedTask(userTaskId,taskType) {
	// 更新支付宝账号
	$.ajax({
		type : "POST",
		url :  _path + "/my/userTask/compeleted",
		data : {
			"userTaskId" : userTaskId,
			"taskType" : taskType
		},
		dataType : "json",
		async : false,
		success : function(result) {
			if (result.result > 0) {
				$("#alert-ml_"+taskType).html("+" + result.gold +" G币");
				$("#alert-ml_"+taskType).removeClass("fr").removeClass("alert-tmier").removeClass("alert-time");
				$("#alert-ml_"+taskType).parent().removeClass("col-xs-3").addClass("col-xs-2 c_sand fr");
				$("#alert-ml_"+taskType).parent().find("button").attr("disabled","true").css({"background":"#3d4148","color":"#fff"}).html("已领取");
			}
		},
		error : function(errorMsg) {
			
		}
	});
}

function countTime() {  
    //获取当前时间  
    var date = new Date();  
    var now = date.getTime();  
    //设置截止时间  
    var endDate = new Date(getNowFormatDate());  
    var end = endDate.getTime();  
    //时间差  
    var leftTime = end-now;  
    //定义变量 d,h,m,s保存倒计时的时间  
    var d,h,m,s;  
    if (leftTime>=0) {  
        d = Math.floor(leftTime/1000/60/60/24);  
        h = Math.floor(leftTime/1000/60/60%24);  
        m = Math.floor(leftTime/1000/60%60);  
        s = Math.floor(leftTime/1000%60);                     
    }  
    //将倒计时赋值到div中  
    if (h >= 0 && h <= 9) {
    	h = "0" + h;
    }
    if (m >= 0 && m <= 9) {
    	m = "0" + m;
    }
    if (s >= 0 && s <= 9) {
    	s = "0" + s;
    }
    var time = "剩余时间 " + h+":" + m+":" + s;
    $(".alert-time").html(time);
    //递归每秒调用countTime方法，显示动态时间效果  
    setTimeout(countTime,1000);  

}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    date.setDate(date.getDate() + 1);
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " 00:00:00";
    return currentdate;
}