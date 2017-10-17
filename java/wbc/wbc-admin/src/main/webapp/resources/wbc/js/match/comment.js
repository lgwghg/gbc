//对战分页信息
var pageSize_c = 5; //每页显示条数
var startRecord_c =0;	//开始记录数
var nowPage_c = 1;  //当前页
var pageCount_c = -1;	//总页数
var recordCount_c =-1;	//总记录数
//比赛对战ID
var gbId;
//比赛对战名称
var gbName;
//允许撤销时间，2分钟
var revokeTime = 1000*60*2;

$(function(){
	//比赛对战ID
	gbId = $("#gbId").val();
	//比赛对战名称
	gbName = $("#gbName").html();
	//加载评论列表
	loadCommentList(true);
	//enter组合发送
	$('#comment-content').on('keydown',function (e){  
		e = e||event;
	     if (e.keyCode == 13)  {  
	    	 e.preventDefault();
	    	 addComment();
	    }  
	});  
});

//评论列表
function loadCommentList(first){
	//分页
	var pagesJson = pages(pageSize_c,startRecord_c,nowPage_c,recordCount_c,pageCount_c);
	
	$.ajax({
		type : "POST",
		url : _path + "/comment/list",
		data: {
			"gridPager":pagesJson,
			"gbId":gbId
		},
		dataType : "json",
		success : function(result) {
			if (result.isSuccess) {
				nowPage_c = result.nowPage+1;
				var data = result.list;
				var comment;
				var content = "";
				if(first){
					$("#commentList-div").html("");
				}
				if(data.length>0){
					for(var i = 0;i<data.length;i++){
						var needHide = false;
						comment = data[i];
						content += '<div class="row pb-15 pt-15 dyde">';
						content += 	'<div class="col-xs-1 mr-20">';
						content += 	'<img width="48" heigth="48" src="'+comment.user.userPhoto_65+'" class="img-circle c-img dyd"/>';
						content += 	'</div>';
						content += '<div class="col-xs-9 fwei" id="cid_'+comment.id+'">';
						content += '<p class="mt-5"><span class="pull-left">'+comment.user.nickName+'</span>';
						content += '<span class=" ml-10 lv-x lv-x-'+comment.user.rankLevel+' pull-left"></span><span class=" ml-15 pull-left">'+comment.createTime+'</span>&nbsp;</p>';
						content += '<p class="pb-5 pt-5 c-word">'+comment.content+'</p>';
						content += '<button class="c_huifu" onClick="addAnswer(\''+comment.user.nickName+'\',\''+comment.id+'\')">回复</button>';
						if(comment.answerArray.length>0){
							var answerArray = comment.answerArray;
							var answer;
							for(var j = 0;j<answerArray.length;j++){
								answer = answerArray[j];
								if(j<3){
									content += '<div class="row dyde1" style="display: block;">';
								}else{
									needHide = true;
									content += '<div class="row dyde1" style="display: none;">';
								}
		                    	content += '<div class="col-xs-1 c-2-pln">';
		                    	content += '<img width="36" heigth="36" src="'+answer.user.userPhoto_35+'" class="img-circle c-ximg dyd1"/>';
		                    	content += '</div>';
		                    	content += '<div class="col-xs-9">';
		                    	content += '<p class="mt-5"><span class="c-span1">'+answer.user.nickName+'</span>@';
		                    	content += '<span class=" ml-10">'+comment.user.nickName+'</span><span class=" ml-10">'+answer.createTime+'</span>&nbsp;</p>';
		                    	content += '<p class="pb-5 pt-5 c-word">'+answer.content+'</p>';
		                    	content += '</div>';
		                    	if(answer.surplusTime != undefined && answer.surplusTime != null && answer.surplusTime < revokeTime){
									content += '<span class="chexiaoa1" name="chexiao" surplusTime="'+answer.surplusTime+'" onClick="revokeComment(this,\''+answer.id+'\')"></span>';
								}
		                    	content += '</div>';
							}
						}
						content += '</div>';
						if(comment.surplusTime != undefined && comment.surplusTime != null && comment.surplusTime < revokeTime){
							content += '<span class="chexiaoa" name="chexiao" surplusTime="'+comment.surplusTime+'" onClick="revokeComment(this,\''+comment.id+'\')"></span>';
						}
						content += '</div>';
						if(needHide){
							content += '<span style="margin-left:70px;" class="dianjimore" cid="'+comment.id+'">展开</span>';
						}
					}
					$("#commentList-div").append(content);
					$("#c-mostpln").html('<a href="javascript:loadCommentList()">更多评论</a>');
					$(".dianjimore").unbind().click(function(){
						var cid = $(this).attr("cid");
			            var plnmore = $("#cid_"+cid).find(".plnmore");
			            for(var i = 3;i<plnmore.length;i++){
			            	if($(plnmore[i]).is(':hidden')){
			            		$(plnmore[i]).show();
			            		$(this).html("收起");
					        }else{
				            	$(plnmore[i]).hide();
				            	$(this).html("展开");
				            }
			            }
			        });
					
					//定时器：一秒一次检索是否有评论时间超过2分钟的，移除撤回按钮
					var inter1 = setInterval(function(){
						$("span[name='chexiao']").each(function(){
							var surplusTime = Number($(this).attr("surplusTime"));
							if(surplusTime >= revokeTime){
								$(this).remove();
							}else{
								$(this).attr("surplusTime",surplusTime+1000);
							}
						});
					},1000);
					
					//评论列表赋予撤回按钮及悬浮动画
					updateState();
			         
				}else{
					$("#c-mostpln").html('<a href="javascript:void(0)">没有更多了</a>');
				}
			}
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}

//回复评论
function addAnswer(nickName,commentId){
	$(".discussant").html("<div class='huifuname'>@"+nickName+"<span class='schf'>x</span></div>");
	$("#comment-content").attr("commentId",commentId).attr("nickName",nickName).focus();
	$(".discussant").on("click",".schf",function(){
        $(".huifuname").remove();
        $("#comment-content").attr("commentId","").attr("nickName","");
    })
}

//发布评论
function addComment(){
	var c_content = $("#comment-content").val();
	if(trim(c_content)==''){
		layer.alert("请先填写内容在发表评论", {icon: 5,shift: 6,time: 2000});
		return false;
	}
	if(strLength(c_content)>480){
		layer.alert("评论内容不能超过240字", {icon: 5,shift: 6,time: 2000});
		return false;
	}
	var commentType = $("#commentType").val();
	var pId = $("#comment-content").attr("commentId");
	$.ajax({
		type : "POST",
		url : _path + "/comment/add",
		data: {
			"content":c_content,
			"gbId":gbId,
			"pId":pId,
			"gbName": gbName,
			"typeId": commentType
		},
		dataType : "json",
		success : function(result) {
			var message = result.message;
			if (result.success) {
				//layer.alert(message, {icon: 6,shift: 6,time: 2000,end:function(){
					var content = '';
					if(pId!=''){
						var baseNickName = $("#comment-content").attr("nickName");
						//回复
						content += '<div class="row dyde1">';
	                	content += '<div class="col-xs-1 c-2-pln mt-10">';
	                	content += '<img width="36" heigth="36" src="'+result.user.userPhoto_35+'" class="img-circle c-ximg dyd1"/>';
	                	content += '</div>';
	                	content += '<div class="col-xs-9">';
	                	content += '<p class="mt-5"><span class="c-span1">'+result.user.nickName+'</span>@';
	                	content += '<span class=" ml-10">'+baseNickName+'</span><span class=" ml-10">'+'刚刚'+'</span>&nbsp;</p>';
	                	content += '<p class="pb-5 pt-5 c-word">'+result.content+'</p>';
	                	content += '</div>';
	                	content += '<span class="chexiaoa1" name="chexiao" surplusTime="5000" onClick="revokeComment(this,\''+result.id+'\')"></span>';
	                	content += '</div>';
	                	$("#cid_"+pId).append(content);
					}else{
						//评论
						content += '<div class="row pb-15 pt-15 dyde">';
						content += 	'<div class="col-xs-1 mr-20">';
						content += 	'<img width="48" heigth="48" src="'+result.user.userPhoto_65+'" class="img-circle c-img dyd"/>';
						content += 	'</div>';
						content += '<div class="col-xs-9 fwei" id="cid_'+result.id+'">';
						content += '<p class="mt-5"><span class="pull-left">'+result.user.nickName+'</span>';
						content += '<span class=" ml-10 lv-x lv-x-'+result.user.rankLevel+' pull-left"></span><span class=" ml-15 pull-left">'+'刚刚'+'</span>&nbsp;</p>';
						content += '<p class="pb-5 pt-5 c-word">'+result.content+'</p>';
						content += '<button class="c_huifu" onClick="addAnswer(\''+result.user.nickName+'\',\''+result.id+'\')">回复</button>';
						content += '</div>';
						content += '<span class="chexiaoa" name="chexiao" surplusTime="5000" onClick="revokeComment(this,\''+result.id+'\')"></span>';
						content += '</div>';
						$("#commentList-div").prepend(content);
					}
					//清空评论框原内容
					$("#comment-content").val("").attr('placeholder','书写你的评论吧！~').attr("commentId","").attr("nickName","");
					//移除气泡
					$(".huifuname").remove();
					//评论列表赋予撤回按钮及悬浮动画
					updateState();
				//}});
			}else{
				layer.alert(message, {icon: 5,shift: 6,time: 2000});
			}
		},
		beforeSend:function(){
			$('#comment-content').unbind('keydown');
			$("#djfb").attr("href","javascript:void(0)");
		},
		complete : function() {
			$('#comment-content').on('keydown',function (){  
			     if (event.keyCode == 13)  {  
			    	 event.preventDefault();
			    	 addComment();
			    }  
			});
			$("#djfb").attr("href","javascript:addComment()");
		},
		error: function (jqXHR, textStatus, errorThrown){
			$("#comment-content").blur();
			ajaxToLogin(jqXHR);
		}
	});
}

//撤回评论
function revokeComment(obj,cid){
	$.ajax({
		type : "POST",
		url : _path + "/comment/revoke",
		data: {
			"id":cid
		},
		dataType : "json",
		success : function(result) {
			var message = result.message;
			if (result.success) {
				//layer.alert(message, {icon: 6,shift: 6,time: 2000,end:function(){
				$(obj).parent().remove();
				//}});
			}else{
				if(result.type != null && result.type =='1'){
					$(obj).remove();
				}else{
					$(obj).attr('onclick','revokeComment(this,"'+cid+'")');
				}
				layer.alert(message, {icon: 5,shift: 6,time: 2000});
			}
		},
		beforeSend:function(){
			$(obj).unbind('onclick');
		},
		complete : function() {
			
		},
		error: function (jqXHR, textStatus, errorThrown){
			ajaxToLogin(jqXHR);
		}
	});
}

//评论列表赋予撤回按钮及悬浮动画
function updateState(){
	$(".dyde").mouseover(function(e){
        e.preventDefault();
    $(this).find(".dyd").addClass("animation_updown");
     $(this).find(".chexiaoa").html("撤回");
    }).mouseleave(function(){
    $(this).find(".dyd").removeClass("animation_updown");
        $(this).find(".chexiaoa").html("");
    });
     $(".dyde1").mouseover(function(e){
        e.stopPropagation();
        $(".chexiaoa").html("");
          var _con = $(".fwei");
    _con.parent(".dyde").find(".dyd").removeClass("animation_updown");
        $(this).find(".dyd1").addClass("animation_updown");
        $(this).find(".chexiaoa1").html("撤回");
    }).mouseleave(function(e){
        $(this).find(".dyd1").removeClass("animation_updown");
        $(this).find(".chexiaoa1").html("");
    });
}