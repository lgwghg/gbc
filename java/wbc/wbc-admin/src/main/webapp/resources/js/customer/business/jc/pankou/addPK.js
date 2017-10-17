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
                    webside.common.commit('pankou-form', url, '/gameBattle/editUI?id='+$("#gbId").val()+"&page=" + "1" + "&rows=" + "10" + "&sidx=" + "" + "&sord=" + "0");
                }
            });
		}
	}
};

var gbId = '';

$(function(){
	pankou.form.validateForm();
	$('select').select2();
	gbId = $("#gbId").val();
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
			url : sys.rootPath + "/jc/pankou/getSatrtTimeByinningNum",
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