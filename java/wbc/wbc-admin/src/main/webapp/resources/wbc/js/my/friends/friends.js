$(function () {
	//推荐历史列表
	loadFriendsList();
	
	//定义一个新的复制对象
	var clipboardFriendsBtn = document.getElementById('d_clip_button_friends');
    var clipboardFriends = new Clipboard(clipboardFriendsBtn);
    clipboardFriends.on('success', function(e) {
	    layer.msg("复制成功", {icon: 6,shift: 6,time: 2000});
	});

    clipboardFriends.on('error', function(e) {
		layer.msg("复制失败，请手动复制", {icon: 5,shift: 6,time: 2000});
	});
})

//加载推荐历史列表
function loadFriendsList(){
	var options = {};
	options.url = _path + "/my/friends/list";
	options.pageSize = "10";
	
	$.pagination("friendsPage", options, function(data) {
		var friends;
		var content = "";
		if(data.length>0){
			for(var i = 0;i<data.length;i++){
				friends = data[i];
				content += '<tr>';
				content += '<td>'+friends.friendNickName+'</td>';
				content += '<td>'+friends.createTime+'</td>';
				content += '<td>+'+friends.rewardGoldNum+'G币</td>';
				content += '</tr>';
			}
		}else{
			content += '<tr>';
			content += '<td colspan="3">暂无数据</td>';
			content += '</tr>';
		}
		$("#friends-tbody").html(content);
	})
}