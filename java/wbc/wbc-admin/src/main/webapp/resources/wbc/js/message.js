//通知滚动轮播
var myInterval;
function autoScroll(obj){
	$(obj).animate({
		marginTop : "-40px"
	},500,function(){
		$(this).css({marginTop : "0px"}).find("li:first").appendTo(this);
	})
}
function setMyInterval(){
	myInterval = setInterval('autoScroll(".list-inform-two")',3000);
}

$(function(){
	
	var hideMessage = sessionStorage.getItem("hideMessage");
	if(hideMessage!='true'){
		
		setTimeout(function(){
			$("#message-close").parent().removeClass("hide");
        },250)
		
		loadMsg();
		
		setMyInterval();
		
		$(".list-inform").mouseover(function () {
			clearInterval(myInterval);
		}).mouseout(function () {
			setMyInterval();
		})
		
		$("#message-close").click(function(){
			sessionStorage.setItem("hideMessage","true");
			$(this).parent().remove();
		})
	}
})

function loadMsg(){
	socket = io.connect("ws://"+_socketAddress);
	socket.on("notice",function(data)
   	{
    	if(data instanceof Array)
    	{
	    	data.forEach(function(e)
	    	{  
	    		var message = JSON.parse(e);
	    	    $("#sn-list-ul").append('<li msg_id="'+message.id+'">'+message.value+'</li>');
	    	});
    	}
    	else
    	{
    		var message = JSON.parse(data);
    		if (message == null) {
    			return;
    		}
    		if(message=='-9'){
    			$("#sn-list-ul").html("");
    			return;
    		}
    		if(message.id=='-1'){
    			$("#sn-list-ul li").each(function(){
    				if($(this).attr("msg_id")==''){
    					$(this).remove();
    				}
    			})
    		}else{
    			if(message.status=='1'){
    				//新增
    				$("#sn-list-ul").append('<li msg_id="'+message.id+'">'+message.value+'</li>');
    			}else{
    				//删除/修改
    				$("#sn-list-ul li").each(function(){
        				if($(this).attr("msg_id")==message.id){
        					$(this).remove();
        				}
        			})
        			if(message.status=='3'){
        				//修改(新增数据)
        				$("#sn-list-ul").append('<li msg_id="'+message.id+'">'+message.value+'</li>');
        			}
    			}
    		}
    	}
	});
}