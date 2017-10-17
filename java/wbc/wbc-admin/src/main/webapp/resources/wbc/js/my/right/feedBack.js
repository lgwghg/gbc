$(function() {
	$('#spans2').click(function() {//生成验证码
        $(this).hide().attr('src', _path+'/captcha?' + new Date().getTime()).fadeIn();
    });
});

function saveNotice2() {
	var title = $("#title2").val();
	if(title == "" || title == undefined) {
		layer.alert("请填写标题", {icon: 5,shift: 6,time: 1000});
		$("#title2").focus();
		return;
	}
	var content = $("#contentMsg2").val();
	if(content == "" || content == undefined) {
		layer.alert("请填写内容", {icon: 5,shift: 6,time: 1000});
		$("#contentMsg2").focus();
		return;
	}
	var captcha = $("#noticeCaptcha2").val();
	if(captcha == "" || captcha == undefined) {
		layer.alert("请填写验证码", {icon: 5,shift: 6,time: 1000});
		$("#noticeCaptcha2").focus();
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
				$("#title2").val("");
				$("#contentMsg2").val("");
				$("#noticeCaptcha2").val("");
				$('#spans2').click();
			} else {
				layer.alert(backData.message, {icon: 5,shift: 6,time: 2000});
			}
		}
	});
}