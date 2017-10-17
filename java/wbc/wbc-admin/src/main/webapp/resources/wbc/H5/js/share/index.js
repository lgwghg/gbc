var config = null;
var share_obj = null;

$(function(){
	//定义一个复制对象
	var clipboardHeaderBtn = document.getElementById('share_copy_clip');
	var clipboardHeader = new Clipboard(clipboardHeaderBtn);
	clipboardHeader.on('success', function(e) {
	    alert("复制成功");
	});
	clipboardHeader.on('error', function(e) {
		alert("复制失败，该浏览器不支持复制");
	});
	
	//判断浏览器，QQ与UC浏览器显示微信分享按钮
	var bLevel = {
        qq: {forbid: 0, lower: 1, higher: 2},
        uc: {forbid: 0, allow: 1}
    };
	var UA = navigator.appVersion;
	var isqqBrowser = (UA.split("MQQBrowser/").length > 1) ? bLevel.qq.higher : bLevel.qq.forbid;
    var isucBrowser = (UA.split("UCBrowser/").length > 1) ? bLevel.uc.allow : bLevel.uc.forbid;
    var isWeixin = false;
    var ua = navigator.userAgent.toLowerCase();  
    if(ua.match(/MicroMessenger/i)=="micromessenger") {  
    	isWeixin = true;
    }
    if(isWeixin){
    	$("#wx-div").show();
    	url += "&f=fx";
        window.history.pushState({},0,url);
    }else{
    	if(isqqBrowser || isucBrowser){
        	$("#weixin-friend-li").show();
        	$("#weixin-timeline-li").show();
        }else{
        	$("#copy-li").show();
        }
    	$("#not-wx-div").show();
    }
    
    config = {
        url:url,
        title:title,
        desc:title,
        img:pic,
        img_title:title,
        from:'G菠菜'
    };
	share_obj = new nativeShare('nativeShare',config);
})

//分享到QQ
function shareToQQ() {
	var shareUrl = 'mqqapi://share/to_fri?';
	shareUrl += 'src_type=web';
	shareUrl += '&version=1';
	shareUrl += '&file_type=news';
	shareUrl += '&share_id=1106017098';
	shareUrl += '&title='+Base64.encode(title);
	shareUrl += '&thirdAppDisplayName='+Base64.encode("G菠菜");
	shareUrl += '&url='+Base64.encode(url);
	window.open(shareUrl);
}

//分享到QQ空间
function shareToQQKongJian() {
	var shareUrl = 'http://h5.qzone.qq.com/q/qzs/open/connect/widget/mobile/qzshare/index.html?';
	shareUrl += 'desc='+title;
	shareUrl += '&imageUrl='+pic;
	shareUrl += '&loginpage=loginindex.html';
	shareUrl += '&logintype=qzone';
	shareUrl += '&otype=share';
	shareUrl += '&page=qzshare.html';
	shareUrl += '&referer='+url;
	shareUrl += '&sid=';
	shareUrl += '&summary='+title;
	shareUrl += '&title='+title;
	shareUrl += '&url='+url;
	shareUrl += '&t='+new Date().getTime();
	window.open(shareUrl);
}

//分享到新浪微博
function shareToSinaWeibo(){
	var shareUrl = 'http://service.weibo.com/share/share.php?';
	shareUrl += 'title='+title;
	shareUrl += '&pic='+pic;
	shareUrl += '&url='+url;
	shareUrl += '&searchPic=false';
	window.open(shareUrl);
}