var userDepositLog = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#userdepositlog-form').validate({
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
					orderName : {
						required : true,
	 					maxlength : 128
	                },
					type : {
						required : true,
	 					maxlength : 4
	                },
					orderNo : {
						required : true,
	 					maxlength : 128
	                },
					udGold : {
						required : true,
	 					maxlength : 19
	                },
					orderTime : {
						required : true,
	 					maxlength : 19
	                },
					payTime : {
						required : true,
	 					maxlength : 19
	                },
					isPaySuccess : {
						required : true,
	 					maxlength : 4
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
					orderName : {
						required : "请填写订单标题",
	 					maxlength : "订单标题类型长度不能大于128个字符"
	                },
					type : {
						required : "请填写充值平台 1：支付宝，2：微信",
	 					maxlength : "充值平台 1：支付宝，2：微信类型长度不能大于4个字符"
	                },
					orderNo : {
						required : "请填写交易订单编号",
	 					maxlength : "交易订单编号类型长度不能大于128个字符"
	                },
					udGold : {
						required : "请填写充值金额",
	 					maxlength : "充值金额类型长度不能大于19个字符"
	                },
					orderTime : {
						required : "请填写下单时间",
	 					maxlength : "下单时间类型长度不能大于19个字符"
	                },
					payTime : {
						required : "请填写支付时间",
	 					maxlength : "支付时间类型长度不能大于19个字符"
	                },
					isPaySuccess : {
						required : "请填写是否支付成功 1:是 0：否　，默认初始化未0",
	 					maxlength : "是否支付成功 1:是 0：否　，默认初始化未0类型长度不能大于4个字符"
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
                        url = '/userDepositLog/edit.html';
                    } else {
                        url = '/userDepositLog/add.html';
                    }
                    webside.common.commit('userdepositlog-form', url, '/userDepositLog/listUI.html');
                }
            });
		}
	}
};
