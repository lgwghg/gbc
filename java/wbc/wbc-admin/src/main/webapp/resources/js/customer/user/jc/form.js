var userJc = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#userjc-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					id : {
						required : true,
	 					maxlength : 32
	                },
					userId : {
						required : true,
	 					maxlength : 32
	                },
					gbId : {
						required : true,
	 					maxlength : 32
	                },
					jcGold : {
						required : true,
	 					maxlength : 19
	                },
					jcTeamType : {
						required : true,
	 					maxlength : 4
	                },
					gameResult : {
						required : true,
	 					maxlength : 4
	                },
					victoryGoldNum : {
						required : true,
	 					maxlength : 19
	                },
					jcTime : {
						required : true,
	 					maxlength : 19
	                },
                },
                messages : {
					id : {
						required : "请填写唯一标识",
	 					maxlength : "唯一标识类型长度不能大于32个字符"
	                },
					userId : {
						required : "请填写用户ID",
	 					maxlength : "用户ID类型长度不能大于32个字符"
	                },
					gbId : {
						required : "请填写比赛对战ID",
	 					maxlength : "比赛对战ID类型长度不能大于32个字符"
	                },
					jcGold : {
						required : "请填写竞猜G币",
	 					maxlength : "竞猜G币类型长度不能大于19个字符"
	                },
					jcTeamType : {
						required : "请填写竞猜战队分类 1:主战队，2：客场战队",
	 					maxlength : "竞猜战队分类 1:主战队，2：客场战队类型长度不能大于4个字符"
	                },
					gameResult : {
						required : "请填写比赛结果 默认2     0:输 1：赢  2 ：进行中 3：已取消",
	 					maxlength : "比赛结果 默认2     0:输 1：赢  2 ：进行中 3：已取消类型长度不能大于4个字符"
	                },
					victoryGoldNum : {
						required : "请填写用户赢得金币数量",
	 					maxlength : "用户赢得金币数量类型长度不能大于19个字符"
	                },
					jcTime : {
						required : "请填写竞猜时间",
	 					maxlength : "竞猜时间类型长度不能大于19个字符"
	                },
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
                        url = '/userJc/edit.html';
                    } else {
                        url = '/userJc/add.html';
                    }
                    webside.common.commit('userjc-form', url, '/userJc/listUI.html');
                }
            });
		}
	}
};
