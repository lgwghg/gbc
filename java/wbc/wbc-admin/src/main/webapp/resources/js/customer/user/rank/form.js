var jcRank = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#jcrank-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					id : {
						required : true,
	 					maxlength : 32
	                },
					rankName : {
						required : true,
	 					maxlength : 50
	                },
					rankClass : {
						required : true,
	 					maxlength : 30
	                },
					rankLevel : {
						required : true,
	 					maxlength : 10
	                },
                },
                messages : {
					id : {
						required : "请填写唯一标识",
	 					maxlength : "唯一标识类型长度不能大于32个字符"
	                },
					rankName : {
						required : "请填写",
	 					maxlength : "类型长度不能大于50个字符"
	                },
					rankClass : {
						required : "请填写",
	 					maxlength : "类型长度不能大于30个字符"
	                },
					rankLevel : {
						required : "请填写",
	 					maxlength : "类型长度不能大于10个字符"
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
                        url = '/jcRank/edit.html';
                    } else {
                        url = '/jcRank/add.html';
                    }
                    webside.common.commit('jcrank-form', url, '/jcRank/listUI.html');
                }
            });
		}
	}
};
