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
					orderNo : {
	 					maxlength : 128
	                },
					isPaySuccess : {
						required : true
	                }
                },
                messages : {
					orderNo : {
	 					maxlength : "交易订单编号类型长度不能大于128个字符"
	                },
					isPaySuccess : {
						required : "请选择状态",
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
                        url = '/userDepositLog/editForWithdraw.html';
                    } else {
                        url = '/userDepositLog/addForWithdraw.html';
                    }
                    webside.common.commit('userdepositlog-form', url, '/userDepositLog/withdrawUI.html');
                }
            });
		}
	}
};
