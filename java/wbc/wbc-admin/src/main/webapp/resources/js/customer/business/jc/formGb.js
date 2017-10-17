var gameBattle = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#gamebattle-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					startTime : {
						required : true,
	 					maxlength : 19
	                },
					gbStatus : {
						required : true,
	 					maxlength : 4
	                }
                },
                messages : {
					startTime : {
						required : "请填写开始时间"
	                },
					gbStatus : {
						required : "请选择比赛对战状态"
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
                        url = '/gameBattle/edit';
                        webside.common.commit('gamebattle-form', url, '/gameBattle/listUI');
                    } else {
                        
                    }
                }
            });
		}
	}
};

$(function(){
	gameBattle.form.validateForm();
	 $('select').select2();
});