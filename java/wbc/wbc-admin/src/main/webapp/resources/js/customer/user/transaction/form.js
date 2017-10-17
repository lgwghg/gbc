var userTransactionLog = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#usertransactionlog-form').validate({
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
					utType : {
						required : true,
	 					maxlength : 4
	                },
					dataId : {
						required : true,
	 					maxlength : 32
	                },
					goldNum : {
						required : true,
	 					maxlength : 19
	                },
					utTime : {
						required : true,
	 					maxlength : 19
	                },
					remarks : {
						required : true,
	 					maxlength : 256
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
					utType : {
						required : "请填写交易类型 1:竞猜 2：充值 3:兑换 4:推荐好友奖励 5：用户签到 6：充值奖励",
	 					maxlength : "交易类型 1:竞猜 2：充值 3:兑换 4:推荐好友奖励 5：用户签到 6：充值奖励类型长度不能大于4个字符"
	                },
					dataId : {
						required : "请填写业务ID",
	 					maxlength : "业务ID类型长度不能大于32个字符"
	                },
					goldNum : {
						required : "请填写G币数量",
	 					maxlength : "G币数量类型长度不能大于19个字符"
	                },
					utTime : {
						required : "请填写交易时间",
	 					maxlength : "交易时间类型长度不能大于19个字符"
	                },
					remarks : {
						required : "请填写备注(信息)",
	 					maxlength : "备注(信息)类型长度不能大于256个字符"
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
                        url = '/userTransactionLog/edit.html';
                    } else {
                        url = '/userTransactionLog/add.html';
                    }
                    webside.common.commit('usertransactionlog-form', url, '/userTransactionLog/listUI.html');
                }
            });
		}
	}
};
