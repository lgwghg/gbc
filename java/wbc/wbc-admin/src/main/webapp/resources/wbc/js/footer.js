$(function(){
	showLink();
});

function showLink() {
	var url = "/help/findForFoot";
	ajaxMethod(url, {}, "post", false, function(backData) {
		var content = "";
		var list = backData.list;
		if(list && list.length > 0) {
			var webNotice;
			for(var i=0; i<list.length; i++) {
				webNotice = list[i];
				if(webNotice.sequence == -1) {
					$("#yearText").text(webNotice.content.replace("<p>","").replace("</p>",""));
				} else {
					var url = _path + "/help/" + webNotice.id;
					content += '<a href="'+url+'">'+webNotice.title+' </a>'
				}
			}
		}
		$("#showLink").html(content);
	})
}

function ajaxMethod(url, dataJson, type, sync, callBack) {
	if(url == "" || url == undefined) {// 链接
		layer.alert("url 不能为空", {icon: 5,shift: 6,time: 2000});
		return;
	}
	
	if(dataJson == "" || dataJson == undefined) {// 数据
		dataJson = {};
	}
	if(type == "" || type == undefined) {// post/get 请求
		type = "post";
	}
	var _sync = false;
	if(sync == "true" || sync) {// 同步
		_sync = true;
	}
	
	$.ajax({
		url: url,
		data: dataJson,
		type: type,
		async: _sync,
		dataType : "json",
		success : function(data) {
			if(callBack) {
				callBack(data);
			}
		}
	});
}