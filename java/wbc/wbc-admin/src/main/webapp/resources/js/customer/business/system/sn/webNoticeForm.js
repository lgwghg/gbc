var sysWebNotice = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#syswebnotice-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					title : {
						required : true,
	 					maxlength : 128
	                },
					content : {
						required : true,
	                },
					type : {
						required : true,
	 					maxlength : 10
	                },
	                sequence: {
	                	number:true
	                },
					status : {
						required : true,
	 					maxlength : 10
	                },
                },
                messages : {
					title : {
						required : "请填写标题",
	 					maxlength : "标题类型长度不能大于128个字符"
	                },
					content : {
						required : "请填写通知内容"
	                },
					type : {
						required : "请填写1.公告 2.更新 3.介绍 4.使用帮助 5.用户须知 6.反馈",
	 					maxlength : "1.公告 2.更新 3.介绍 4.使用帮助 5.用户须知 6.反馈类型长度不能大于10个字符"
	                },
	                sequence: {
	                	number: "请输入合法的数字"
	                },
					status : {
						required : "请填写状态 1：有效 0 无效 默认1",
	 					maxlength : "状态 1：有效 0 无效 默认1类型长度不能大于10个字符"
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
                        url = '/system/webNotice/edit.html';
                    } else {
                        url = '/system/webNotice/add.html';
                    }
                    webside.common.commit('syswebnotice-form', url, '/system/webNotice/listUI.html');
                }
            });
		}
	}
};
