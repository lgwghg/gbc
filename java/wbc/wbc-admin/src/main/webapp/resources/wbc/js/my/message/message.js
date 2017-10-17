$(function () {
	loadMessageList();
})

//加载消息列表
function loadMessageList(){
	var options = {};
	options.url = _path + "/my/message/list";
	options.pageSize = "10";
	
	$.pagination("messagePage", options, function(data) {
		var content = "";
		if(data.length > 0) {
			var message ;
			for(var i = 0;i<data.length;i++){
				message = data[i];
				if(message.status==0){
					content += '<li class="c-informgroup addword" style="color: #009688;">';
				}else{
					content += '<li class="c-informgroup addword">';
				}
				content += '<i class="iconfont icon-xiaoxitongzhi"></i>';
				content += '<span>'+message.createTime+'</span>';
				content += '<span>&nbsp;'+message.content+'</span>';
				content += '</li>';
			}
		}else{
			content += '<li class="c-informgroup">暂无通知</li>';
		} 
		$("#message-ul").html(content);
		$(".c-informgroup").click(function(){
            $(this).toggleClass("addword");
         })
	})
}