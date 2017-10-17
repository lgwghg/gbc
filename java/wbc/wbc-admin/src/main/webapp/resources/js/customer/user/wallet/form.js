var userWallet = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#userwallet-form').validate({
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
					gold : {
						required : true,
	 					maxlength : 19
	                },
					sysGoldNum : {
						required : true,
	 					maxlength : 19
	                },
					updateTime : {
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
					gold : {
						required : "请填写金币",
	 					maxlength : "金币类型长度不能大于19个字符"
	                },
					sysGoldNum : {
						required : "请填写G币",
	 					maxlength : "G币类型长度不能大于19个字符"
	                },
					updateTime : {
						required : "请填写修改时间",
	 					maxlength : "修改时间类型长度不能大于19个字符"
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
                        url = '/userWallet/edit.html';
                    } else {
                        url = '/userWallet/add.html';
                    }
                    webside.common.commit('userwallet-form', url, '/userWallet/listUI.html');
                }
            });
		}
	}
};
