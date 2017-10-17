jQuery.validator.addMethod("timeVictoryCheck", function() {
	var pkEndTime = $("#pkEndTime").val();
	var pkVictory = $("#pkVictory").val();
	if(pkEndTime!="" || pkVictory!=""){
		if(pkEndTime!="" && pkVictory!=""){
			return true;
		}else{
			return false;
		}
	}else{
		return true;
	}
}, "盘口结束时间与胜方战队必须同步存在或消失！");

jQuery.validator.addMethod("endThanStartCheck", function() {
	var pkEndTime = $("#pkEndTime").val();
	var pkStartTime = $("#pkStartTime").val();
	if(pkEndTime!="" && pkStartTime!=""){
		var pkEndTimeL = getLongDateFromStr(pkEndTime);
		var pkStartTimeL = getLongDateFromStr(pkStartTime);
		if(pkEndTimeL > pkStartTimeL){
			return true;
		}else{
			return false;
		}
	}else{
		return true;
	}
}, "结束时间必须晚于开始时间！");

var pankou = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#pankou-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	inningNum : {
						required : true
	                },
	                pankouType : {
						required : true
	                },
	                pkName : {
						required : true,
	 					maxlength : 64
	                },
	                pkStartTime : {
						required : true
	                },
	                pkEndTime : {
	                	timeVictoryCheck : true,
	                	endThanStartCheck : true
	                },
	                pkVictory : {
	                	timeVictoryCheck : true
	                },
	                pkStatus : {
						required : true
	                },
	                pkHomeRule : {
	                	required : true,
	 					maxlength : 16,
	 					number:true
	                },
	                pkAwayRule : {
	                	required : true,
	 					maxlength : 16,
	 					number:true
	                }
                },
                messages : {
                	inningNum : {
						required : "请选择局数"
	                },
	                pankouType : {
						required : "请选择玩法类型"
	                },
	                pkName : {
						required : "请输入玩法名称",
						maxlength : "玩法名称最大支持64字符"
	                },
	                pkStartTime : {
						required : "请填写开始时间"
	                },
	                pkEndTime : {
	                	timeVictoryCheck : "盘口结束时间与胜方战队必须同步存在或消失！"
	                },
	                pkVictory : {
	                	timeVictoryCheck : "盘口结束时间与胜方战队必须同步存在或消失！"
	                },
	                pkStatus : {
						required : "请选择盘口状态"
	                },
	                pkHomeRule : {
	                	required : "请填写比赛赔率（主战队）",
	 					maxlength : "比赛赔率（主战队）类型长度不能大于16个字符",
	 					number : "必须是数字"
	                },
	                pkAwayRule : {
	                	required : "请填写比赛赔率（客场战队）",
	 					maxlength : "比赛赔率（客场战队）类型长度不能大于16个字符",
	 					number : "必须是数字"
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
                        url = '/jc/pankou/edit';
                    } else {
                        url = '/jc/pankou/add';
                    }
                    webside.common.commit('pankou-form', url, '/gameBattle/editUI?id='+$("#gbId").val()+"&page=" + $("#pageNum").val() + "&rows=" + $("#pageSize").val() + "&sidx=" + $("#orderByColumn").val() + "&sord=" + $("#orderByType").val());
                }
            });
		}
	}
};

var gbId = '';
var rangFenTeam = '';

$(function(){
	pankou.form.validateForm();
	$('select').select2();
	gbId = $("#gbId").val();
	rangFenTeam = $("#rangFenTeam").val();
	
	if(rangFenTeam!=null && rangFenTeam!=''){
		$("#pkRangFenTeam-group").show();
		$("#pkRangFenTeam").rules("add",{required:true,messages:{required:"请选择让分战队"}});
		$("#pkRangfenNum").rules("add",{required:true,messages:{required:"请输入让分数"}});
	}
	
});

//玩法类型改变事件
function clickType(){
	var pankouType = $("#pankouType").val();
	var gameRule = $("#gameRule").val();
	
	//非BO1
	if(gameRule != '1'){
		//pankouType==1 让分
		if(pankouType == "1"){
			$("#pkRangFenTeam").rules("add",{required:true,messages:{required:"请选择让分战队"}});
			$("#pkRangfenNum").rules("add",{required:true,messages:{required:"请输入让分数"}});
			$("#pkRangFenTeam-group").show();
		}else{
			$("#pkRangFenTeam").rules("remove");
			$("#pkRangFenTeam-group").removeClass("has-error");
			$("#pkRangFenTeam-error").remove();
			
			$("#pkRangfenNum").rules("remove");
			$("#pkRangfenNum-group").removeClass("has-error");
			$("#pkRangfenNum-error").remove();
			
			$("#pkRangFenTeam-group").hide();
		}
	}
}

//局数改变事件
function clickInningNum(){
	var gbStartTime = $("#gbStartTime").val();
	var inningNum = $("#inningNum").val();
	//第一局时间与比赛对战时间一致
	if(inningNum == '1'){
		$("#pkStartTime").val(gbStartTime);
		$("#pkStartTime").attr("onclick","");
	}else{
		$.ajax({
			type : "POST",
			url : sys.rootPath + "/jc/pankou/getSatrtTimeByInningNum",
			async : false,
			data:{   
				"inningNum":inningNum,
				"gbId" : gbId
			},
			dataType : "json",
			success : function(resultdata) {
				if(resultdata.success){
					$("#pkStartTime").val(resultdata.data);
					$("#pkStartTime").attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,minDate:'"+resultdata.data+"'})");
				}else{
					$("#pkStartTime").val(gbStartTime);
					$("#pkStartTime").attr("onclick","WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,minDate:'"+gbStartTime+"'})");
				}
			}
		});
	}
}