

function initMyFriends() {
	
	//定义一个新的复制对象
	var clipboardMyFriendBtn = document.getElementById('d_clip_button_myfriends');
    var clipboardMyFriend = new Clipboard(clipboardMyFriendBtn);
    clipboardMyFriend.on('success', function(e) {
 	    layer.msg("复制成功", {icon: 6,shift: 6,time: 2000});
 	});

    clipboardMyFriend.on('error', function(e) {
 		layer.msg("复制失败，请手动复制", {icon: 5,shift: 6,time: 2000});
 	});
}

var first = true;
var pageNum = 1;
//加载推荐列表
function getFriendsList(_first){
	if(_first != undefined && _first) {// 第一次点击，初始化
		first = _first;
		pageNum = 1;
	}
	
	var url = _path + "/my/friends/list";
	var dataJson = {};
	dataJson.pageSize = "10";
	dataJson.nowPage = pageNum;// 页码
	pageNum = pageNum + 1;// 下一页
	
	var parameters = {};
	//parameters.orderBy = "IF(endTime is null, jcTime, endTime) desc";
	
	dataJson.parameters = parameters;
	
	ajaxMethod(url, {"gridPager":JSON.stringify(dataJson)}, "post", false, function(backData) {
		var content = "";
		var data = backData.list;
		if(data.length > 0) {
			var friends ;
			for(var i = 0;i<data.length;i++){
				friends = data[i];
				content += '<div class="tip-main">';
				content += '<span>'+friends.friendNickName+'</span>';
				content += '<span>'+friends.createTime+'</span>';
				content += '<span>'+friends.rewardGoldNum+'</span>';
				content += '</div>';
			}
		}else{
			content += '';
		} 
		if(first) {
			$("#friends-div").html(content);
		} else {
			$("#friends-div").append(content);
		}
		
		var pageCount = backData.pageCount;
		if(data.length > 0 && pageCount >= pageNum) {
			first = false;
			$("#firendsMore").show();
			$("#firendsNoMore").hide();
		} else {
			$("#firendsMore").hide();
			$("#firendsNoMore").show();
		}
		
		$(".c-informgroup").click(function(){
            $(this).toggleClass("addword");
        });
	})
}

//分享
//type 1、QQ 2、微博
function share(type,url,title,pic){
	if(type!="" && url!=""){
		if(type=="1"){
			window.open('http://connect.qq.com/widget/shareqq/index.html?url='+url+'&desc='+title+'&pics='+pic);
		}else if(type=="2"){
			window.open('http://service.weibo.com/share/share.php?appkey=&title='+title+'&url='+url+'&pic='+pic);
		}
	}
}