$(function() {
	
});

var first = true;
var pageNum = 1;
//加载消息列表
function getHelpList(_first) {
	if(_first != undefined && _first) {// 第一次点击，初始化
		first = _first;
		pageNum = 1;
	}
	
	var url = _path + "/help/list";
	var dataJson = {};
	dataJson.pageSize = "10";
	dataJson.nowPage = pageNum;// 页码
	pageNum = pageNum + 1;// 下一页
	
	var parameters = {};
	parameters.type = "4";
	
	dataJson.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(dataJson)}, "post", false, function(backData) {
		var content = "";
		var data = backData.list;
		if(data.length > 0) {
			var message ;
			for(var i = 0;i<data.length;i++){
				message = data[i];
				if(message.status==0){
					content += '<li class="c-informgroup addword" style="color: #009688;" onclick=goToHelp(\''+message.id+'\')>';
				}else{
					content += '<li class="c-informgroup addword" onclick=goToHelp(\''+message.id+'\')>';
				}
				content += '<i class="iconfont icon-youcebangzhuzhongxin helpcorner"></i>';
				content += '<span>'+message.title+'</span>';
				if(i == 0) {
					content += '<div class="redhot pull-right">HOT</div>';
				}
//				content += '<span>&nbsp;'+message.content+'</span>';
				content += '</li>';
			}
		}else{
			content += '';
		} 
		if(first) {
			$("#helpUl").html(content);
		} else {
			$("#helpUl").append(content);
		}
		
		var pageCount = backData.pageCount;
		if(data.length > 0 && pageCount >= pageNum) {
			first = false;
			$("#helpMore").show();
			$("#helpNoMore").hide();
		} else {
			$("#helpMore").hide();
			$("#helpNoMore").show();
		}
		
		$(".c-informgroup").click(function(){
            $(this).toggleClass("addword");
        });
	})
}

function goToHelp(id) {
	var url = _path + "/help/" + id;
	window.open(url);
}
