var userExchangeLog = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#userexchangelog-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					id : {
						required : true,
	 					maxlength : 32
	                },
					stockId : {
						required : true,
	 					maxlength : 32
	                },
					userId : {
						required : true,
	 					maxlength : 32
	                },
					exchangeTime : {
						required : true,
	 					maxlength : 19
	                },
					exchangeGold : {
						required : true,
	 					maxlength : 19
	                },
					exchangeStatus : {
						required : true,
	 					maxlength : 4
	                },
					ueOrderNo : {
						required : true,
	 					maxlength : 64
	                },
                },
                messages : {
					id : {
						required : "请填写唯一标识",
	 					maxlength : "唯一标识类型长度不能大于32个字符"
	                },
					stockId : {
						required : "请填写库存ID",
	 					maxlength : "库存ID类型长度不能大于32个字符"
	                },
					userId : {
						required : "请填写用户ID",
	 					maxlength : "用户ID类型长度不能大于32个字符"
	                },
					exchangeTime : {
						required : "请填写兑换时间",
	 					maxlength : "兑换时间类型长度不能大于19个字符"
	                },
					exchangeGold : {
						required : "请填写兑换金额",
	 					maxlength : "兑换金额类型长度不能大于19个字符"
	                },
					exchangeStatus : {
						required : "请填写兑换状态 默认 1  1：有效 0：无效",
	 					maxlength : "兑换状态 默认 1  1：有效 0：无效类型长度不能大于4个字符"
	                },
					ueOrderNo : {
						required : "请填写数字订单编号",
	 					maxlength : "数字订单编号类型长度不能大于64个字符"
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
                        url = '/userExchangeLog/edit.html';
                    } else {
                        url = '/userExchangeLog/add.html';
                    }
                    webside.common.commit('userexchangelog-form', url, '/userExchangeLog/listUI.html');
                }
            });
		}
	}
};
