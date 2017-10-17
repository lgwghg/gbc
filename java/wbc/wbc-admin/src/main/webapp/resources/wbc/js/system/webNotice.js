//这是a标签的pjax。#content 表示执行pjax后会发生变化的id，改成你主题的内容主体id或class。timeout是pjax响应时间限制，如果在设定时间内未响应就执行页面转跳，可自由设置。
$(document).pjax('#my-pjax a', '#container',{timeout: 6000});
//参考的loading动画代码 //执行pjax开始，在这里添加要重载的代码，可自行添加loading动画代码。例如你已调用了NProgress，在这里添加 NProgress.start();
$(document).on(
	'pjax:send', 
	function() { 
		NProgress.start();
	}
);
//参考的loading动画代码 //执行pjax结束，在这里添加要重载的代码，可自行添加loading动画结束或隐藏代码。例如NProgress的结束代码 NProgress.done();
$(document).on(
	'pjax:complete', 
	function() { 
		NProgress.done();
	}
);

$(function() {
    $(".list dt").click(function(){
      	$(this).toggleClass("green_active").closest("li").siblings("li").find("dt").removeClass("green_active");
      	$(this).next("dd").toggle().closest("li").siblings("li").find("dd").hide(); 
      	loadList();
    });
    
    $('#spans').click(function() {//生成验证码
        $(this).hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
    });
    
    var type = $("#type").val();
    var noticeId = $("#noticeId").val();
    if(type != "") {
    	type = type -1 ;
    	$(".list li dt:eq("+type+")").click();
    } else {
    	$(".list li dt:eq(0)").click();
    }
});

var first = true;
function loadList() {
 	var id = $(".green_active").attr("id");
 	var type = "6";
	if(id != "" && id != undefined) {
		type = "6";
		$("#showNotice").hide();
		$("#showDiv").show();
	} else {
		id = $(".green_active").next().find("ul").attr("id");
		type = id.split("_")[1];
		var options = {};
		options.url = _path + "/help/list";
		options.pageSize = "10";
		var parameters = {};
		parameters.type = type;
		options.parameters = parameters;
		
		$.pagination("pagination", options, function(data) {
			var content = "";
			if(data.length > 0) {
				var webNotice ;
				for(var i = 0; i<data.length;i++) {
					webNotice = data[i];
					var noticeId = $("#noticeId").val();
					var url = _path + "/help/" + webNotice.id;
					if(noticeId == webNotice.id) {
						var hidden = $("#showDiv").is(":hidden");// 是否隐藏
						if(hidden) {
							content += '<li style="color: #31C3A2;"><a style="color: #31C3A2;" href="'+url+'">'+webNotice.title+'</a></li>';
						} else {
							content += '<li><a href="'+url+'">'+webNotice.title+'</a></li>';
						}
					} else if(noticeId == "" || noticeId == undefined) {// 直接help
						if(i == 0 && first) {
							first = false;
							content += '<li style="color: #31C3A2;"><a style="color: #31C3A2;" href="'+url+'">'+webNotice.title+'</a></li>';
							loadWebNoticeData(webNotice.id);
						} else {
							content += '<li><a href="'+url+'">'+webNotice.title+'</a></li>';
						}
					} else {
						content += '<li><a href="'+url+'">'+webNotice.title+'</a></li>';
					}
				}
			} else {
				
			}
			$("#"+id).html(content);
			$(".ulist li").click(function() {
				$("#showNotice").show();
				$("#showDiv").hide();
		        $(this).css("color","#31C3A2").siblings().css("color","#fff");
		        $(this).find("a").css("color","#31C3A2").parent("li").siblings("li").find("a").css("color","#9D9D9D");
		    })
		});
	}
}

function loadWebNoticeData(id) {
	var url = "/help/findById";
	$.ajax({
		url: url,
		data: {"id":id},
		type: "post",
		dataType: "json",
		success: function(backData) {
			var content = "";
			var webNotice = backData.webNotice;
			if(webNotice) {
				$("#showNotice").show();
	 			$("#showDiv").hide();
				
		        content += '<h1>'+webNotice.title+'</h1>';
		        content += '<p class="author"><span>作者:&nbsp;</span><span>'+webNotice.sysUserName+'</span>&nbsp;&nbsp;&nbsp;<span>时间:&nbsp;</span>'+getSmpFormatDateByLong(webNotice.addTime, true)+'</p><br/>';
		        content += webNotice.content;
			} else {
				
			}
			$("#showNotice").html(content);
		}
	});
}

function saveNotice() {
	var title = $("#title").val();
	if(title == "" || title == undefined) {
		layer.alert("请填写标题", {icon: 5,shift: 6,time: 1000});
		$("#title").focus();
		return;
	}
	var content = $("#contentMsg").val();
	if(content == "" || content == undefined) {
		layer.alert("请填写内容", {icon: 5,shift: 6,time: 1000});
		$("#contentMsg").focus();
		return;
	}
	var captcha = $("#noticeCaptcha").val();
	if(captcha == "" || captcha == undefined) {
		layer.alert("请填写验证码", {icon: 5,shift: 6,time: 1000});
		$("#noticeCaptcha").focus();
		return;
	}
	var url = "/help/add";
	$.ajax({
		url: url,
		data: {"title":title, "content":content, "captcha":captcha},
		type: "post",
		dataType: "json",
		success: function(backData) {
			if(backData.success) {
				layer.alert("反馈成功", {icon:1,shift: 6,time: 2000});
				$("#title").val("");
				$("#contentMsg").val("");
				$("#noticeCaptcha").val("");
				$('#spans').click();
			} else {
				layer.alert(backData.message, {icon: 5,shift: 6,time: 2000});
			}
		}
	});
}
