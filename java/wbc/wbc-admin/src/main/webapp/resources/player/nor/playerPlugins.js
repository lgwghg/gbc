
//HD按钮插件
function HDButton(hdoptions){
	videojs.HDButton = videojs.Button.extend({
		/* @constructor */
		init: function(player, options){
			videojs.Button.call(this, player, options);
		}
	});
	
	//创建HD按钮样式
	function createHDButtonButton(voptions){
		var vid = voptions['vid'];
		var url1080 = voptions['1080'];
		var url720 = voptions['720'];
		var url480 = voptions['480'];
		
		var innerHTMLStr = '<div class="vjs-off-button"><span class="vjs-control-content">HD</span></div>'
						+'<form class="video-select">';
		if(url1080!=null&&url1080!=""){
			innerHTMLStr += '<input type="radio" class="magic-radio" name="video" id="ria_1080_'+vid+'" value="1080"  checked datasrc="'+url1080+'"> <label for="ria_1080_'+vid+'" class="c_1">1080</label>';
		}
		if(url720!=null&&url720!=""){
			innerHTMLStr += '<input type="radio" class="magic-radio" name="video" id="ria_720_'+vid+'" value="720" ';
			if(url1080==null||url1080.trim()==""){
				innerHTMLStr += 'checked';
			}
			innerHTMLStr += ' datasrc="'+url720+'"><label for="ria_720_'+vid+'" class="c_1"> 720</label>';
		}
		if(url480!=null&&url480!=""){
			innerHTMLStr += '<input type="radio" class="magic-radio" name="video" id="ria_480_'+vid+'" value="480" ';
			if((url1080==null||url1080.trim()=="")&&(url720==null||url720.trim()=="")){
				innerHTMLStr += 'checked';
			}
			innerHTMLStr += ' datasrc="'+url480+'"><label for="ria_480_'+vid+'" class="c_1"> 480</label>';
		}
		innerHTMLStr += '</form>';
		
		var props = {
				className: 'selected-button',
				innerHTML: innerHTMLStr,
				role: 'button',
				'aria-live': 'polite',
				tabIndex: 0
		};
		return videojs.Component.prototype.createEl(null, props);                	
	}
	var X;
	var newoptions = { 'el' : createHDButtonButton(hdoptions) };
	X = new videojs.HDButton(this, newoptions);
	this.controlBar.el().appendChild(X.el());

}

videojs.plugin('HDButton', HDButton);


//组件播放器及事件
function buildPlayer(vid,options){
	var myPlayer = videojs(vid, {
		"autoplay":false,
		plugins : { HDButton : options },//使用插件
		"preload": "none",				//预加载  auto  none
		//poster": "myPoster.jpg       视频缩略图
		children : {
			controlBar : {
				captionsButton: false,
				chaptersButton : false,
				liveDisplay:false,
				playbackRateMenuButton: false,
				subtitlesButton:false
			}
		},
		controlBar: {
			captionsButton: false,
			chaptersButton : false,
			liveDisplay:false,
			playbackRateMenuButton: false,
			subtitlesButton:false,
			"  ProgressControl":{
				"VolumeControl":false
			}
		}
	}, function(){
		
		//鼠标移到音量图标时,显示音量条,并且给音量图标添加个自定义属性表示鼠标在上面
		$("#"+vid).on("mouseover",".vjs-mute-control",function(){
			$("#"+vid).find(".vjs-volume-control").fadeIn();
			$("#"+vid).find(".vjs-mute-control").attr("ison","yes");
		});
		
		//当鼠标移在音量条上时，给音量条加个自定义属性表示鼠标在上面
		$("#"+vid).on("mouseover",".vjs-volume-control",function(){
			$("#"+vid).find(".vjs-volume-control").attr("ison","yes");
		});
		
		//鼠标移开音量图标时,将音量图标添加的自定义属性删除，并进行判断是否鼠标在音量条上，否则隐藏音量条
		$("#"+vid).on("mouseleave",".vjs-mute-control",function(){
			$("#"+vid).find(".vjs-mute-control").removeAttr("ison");
			setTimeout(function(){
				if($("#"+vid).find(".vjs-mute-control").attr("ison")==null&&$("#"+vid).find(".vjs-volume-control").attr("ison")==null){
					$("#"+vid).find(".vjs-volume-control").fadeOut();
				}
			},1000);
		});
		
		//鼠标移开音量条时，将音量条添加的自定义属性删除，并进行判断是否鼠标在音量图标上，否则隐藏音量条
		$("#"+vid).on("mouseleave",".vjs-volume-control",function(){
			$("#"+vid).find(".vjs-volume-control").removeAttr("ison");
			setTimeout(function(){
				if($("#"+vid).find(".vjs-mute-control").attr("ison")==null&&$("#"+vid).find(".vjs-volume-control").attr("ison")==null){
					$("#"+vid).find(".vjs-volume-control").fadeOut();
				}
			},1000);
		});
		
		//下面实现同理
		$("#"+vid).on("mouseover",".vjs-off-button",function(){
			$("#"+vid).find(".video-select").fadeIn();
			$("#"+vid).find(".vjs-off-button").attr("ison","yes");
		});
		
		$("#"+vid).on("mouseover",".video-select",function(){
			$("#"+vid).find(".video-select").attr("ison","yes");
		});
		
		$("#"+vid).on("mouseleave",".vjs-off-button",function(){
			$("#"+vid).find(".vjs-off-button").removeAttr("ison");
			setTimeout(function(){
				if($("#"+vid).find(".video-select").attr("ison")==null&&$("#"+vid).find(".vjs-off-button").attr("ison")==null){
					$("#"+vid).find(".video-select").fadeOut();
				}
			},1000);
		});
		
		$("#"+vid).on("mouseleave",".video-select",function(){
			$("#"+vid).find(".video-select").removeAttr("ison");
			setTimeout(function(){
				if($("#"+vid).find(".video-select").attr("ison")==null&&$("#"+vid).find(".vjs-off-button").attr("ison")==null){
					$("#"+vid).find(".video-select").fadeOut();
				}
			},1000);
		});
		
		$("#"+vid).on("click",".video-select input",function(){
			var u = $(this).attr("dataSrc");
			var whereYouAt = myPlayer.currentTime();
			myPlayer.src(u);  //重置video的src
			myPlayer.currentTime(whereYouAt); 
			myPlayer.load(u);  //使video重新加载
			myPlayer.play();   //重新播放
			myPlayer.volume();
		})
		
	});
	
	//第一次播放
	myPlayer.on("firstplay", function(){
		//删除封面图
		$("#"+vid).find(".vjs-poster").remove();
		//调正音量条位置
		$("#"+vid).find(".vjs-volume-control").appendTo("#mute_"+vid);
		//增加历史记录以及计算播放量
		videoPlayLog(vid);
		
		//loadstart suspend abort error emptied stalled loadedmetadata loadeddata canplay canplaythrough playing waiting seeking seeked ended durationchange timeupdate progress play pause ratechange volumechange
		//读取播放器所有属性以及值，用于了解播放器
//		for(str in myPlayer)
//		{
//		    alert(str+":::"+myPlayer[str]);
//		}
	});
	//播放
	myPlayer.on("play", function(){
		$("#"+vid).find(".vjs-big-play-button").hide();
	});
	
	//等待
	myPlayer.on("waiting", function(){
		$("#"+vid).find(".vjs-loading-spinner").show();
	});
	
	//可以播放
	myPlayer.on("canplay", function(){
		$("#"+vid).find(".vjs-loading-spinner").hide();
		if($("#"+vid).find(".vjs-play-control").hasClass("vjs-paused")){
			$("#"+vid).find(".vjs-big-play-button").show();
		}
	});
	
	//暂停
	myPlayer.on("pause", function(){
		if(!$("#"+vid).find(".vjs-loading-spinner").is(':visible')){
			$("#"+vid).find(".vjs-big-play-button").show();
		}
	});
	
}

//循环组件每一个视频
function modifyMyPlayer(videoGroup){
	for(var i = 0;i < videoGroup.length; i++) {
		var vid = "video_"+videoGroup[i][0];
		var vhd = videoGroup[i][1];
		buildPlayer(vid,vhd);
	}
}
