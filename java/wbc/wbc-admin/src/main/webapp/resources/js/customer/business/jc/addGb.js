var gameBattle = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#gamebattle-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					geId : {
						required : true,
	 					maxlength : 32
	                },
					gameId : {
						required : true,
	 					maxlength : 32
	                },
					gameRule : {
						required : true,
	 					maxlength : 4
	                },
					homeTeam : {
						required : true,
	 					maxlength : 32
	                },
					awayTeam : {
						required : true,
	 					maxlength : 32
	                },
					startTime : {
						required : true,
	 					maxlength : 19
	                },
					gbStatus : {
						required : true,
	 					maxlength : 4
	                }
                },
                messages : {
                	gameId : {
						required : "请选择游戏"
	                },
					geId : {
						required : "请选择赛事"
	                },
					gameRule : {
						required : "请选择比赛对战规则"
	                },
					homeTeam : {
						required : "请选择主战队"
	                },
					awayTeam : {
						required : "请选择客场战队"
	                },
					startTime : {
						required : "请填写开始时间"
	                },
					gbStatus : {
						required : "请选择比赛对战状态"
	                }
                },
                highlight : function(e) {
                    $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
                },
                success : function(e) {
                    $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
                    $(e).remove();
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                },
                submitHandler : function(form) {
                    var vId = $("#id").val();
                    var url = "";
                    if (vId != undefined) {
                        url = '/gameBattle/edit';
                    } else {
                        url = '/gameBattle/add';
                    }
                    webside.wodota.commit('gamebattle-form', url, '/gameBattle/editUI?page=1&rows=10&sidx=&sord=0');
                }
            });
		}
	}
};
var gameName = "";
var geName = "";
var homeTeamName = "";
var awayTeamName = "";
$(function(){
	gameBattle.form.validateForm();
	 $('select').select2();
});

function clickGame(){
	var gameId = $("#gameId").val();
	gameName = $("#gameId :selected").html();
	if(gameId !=""){
		$("#gbName").val(gameName+"-");
		//游戏赛季
		$.ajax( {
			url : sys.rootPath + "/match/gameEvent/findGameEventList.json",
			type : "POST", 
			dataType : "json",
			data:{   
				"gameId":gameId
			},
			success : function(result) {
				var ge = result.data;
				var html = "";
				if(ge!=null && ge!="" && ge.length>0){
					for(var i=0;i<ge.length;i++){
						html = html + '<option value="'+ge[i].eventId+'">'+ge[i].eventName+'</option>';
					}
				}else{
					$("#geId").html('<option value="" selected="selected">请选择赛事</option>');
				}
				$("#select2-geId-container").html("请选择赛事").attr("title","请选择赛事");
				$("#geId").append(html);
			}
		});
		//游戏战队
		$.ajax( {
			url : sys.rootPath + "/match/teamGame/findTeamList.json",
			type : "POST", 
			dataType : "json",
			data:{   
				"gameId":gameId
			},
			success : function(result) {
				if(result.data!=null && result.data!=""){
					var html = "";
					var tg = result.data;
					for(var i=0;i<tg.length;i++){
						html = html + '<option value="'+tg[i].teamId+'">'+tg[i].teamName+'</option>';
					}
					
					$("#homeTeam").html('<option value="" selected="selected">请选择主场战队</option>');
					$("#homeTeam").append(html);
					$("#awayTeam").html('<option value="" selected="selected">请选择客场战队</option>');
					$("#awayTeam").append(html);
				}
			}
		});
	}else{
		$("#gbName").val("");
	}
}

//
function clickGeName(){
	geName = $("#geId :selected").html();
	if($("#geId").val() != ""){
		$("#gbName").val(gameName+"-"+geName+"-");
	}else{
		$("#gbName").val("");
	}
	
}

function clickHomeTeam(){
	var hName = $("#homeTeam :selected").html();
	if($("#homeTeam").val() !=""){
		homeTeamName = hName;
		$("#gbName").val(gameName+"-"+geName+"-"+homeTeamName+"-");
		if(awayTeamName!=""){
			$("#gbName").val(gameName+"-"+geName+"-"+homeTeamName+"-"+awayTeamName);
		}
	}else{
		homeTeamName = "";
		$("#gbName").val(gameName+"-"+geName+"-");
	}
}
function clickAwayTeam(){
	var aName = $("#awayTeam :selected").html();
	if($("#awayTeam").val() !=""){
		awayTeamName = aName;
		$("#gbName").val(gameName+"-"+geName+"-"+homeTeamName+"-"+awayTeamName);
		if(homeTeamName==""){
			$("#gbName").val(gameName+"-"+geName+"-");
		}
	}else{
		awayTeamName = "";
		if(homeTeamName==""){
			$("#gbName").val(gameName+"-"+geName+"-");
		}else{
			$("#gbName").val(gameName+"-"+geName+"-"+homeTeamName+"-");
		}
		
	}
}