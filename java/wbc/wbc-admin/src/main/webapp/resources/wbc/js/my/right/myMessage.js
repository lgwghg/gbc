
var first = true;
var pageNum = 1;
//加载消息列表
function getMessageList(_first){
	if(_first != undefined && _first) {// 第一次点击，初始化
		first = _first;
		pageNum = 1;
	}
	
	var url = _path + "/my/message/list";
	var dataJson = {};
	dataJson.pageSize = "10";
	dataJson.nowPage = pageNum;// 页码
	pageNum = pageNum + 1;// 下一页
	
	var parameters = {};
	parameters.orderBy = "IF(endTime is null, jcTime, endTime) desc";
	
	dataJson.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(dataJson)}, "post", false, function(backData) {
		var content = "";
		var data = backData.list;
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
			content += '';
		} 
		if(first) {
			$("#messageUl").html(content);
		} else {
			$("#messageUl").append(content);
		}
		
		var pageCount = backData.pageCount;
		if(data.length > 0 && pageCount >= pageNum) {
			first = false;
			$("#messageMore").show();
			$("#messageNoMore").hide();
		} else {
			$("#messageMore").hide();
			$("#messageNoMore").show();
		}
		
		$(".c-informgroup").click(function(){
            $(this).toggleClass("addword");
        });
	})
}