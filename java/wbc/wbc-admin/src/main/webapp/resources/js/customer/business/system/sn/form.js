var sn = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#sn-form').validate({
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
						maxlength : 1000
	                },
	                status : {
						required : true
	                }
                },
                messages : {
                	title : {
						required : "请填写标题",
	 					maxlength : "标题长度不能大于128个字符"
	                },
	                content : {
						required : "请填写内容",
						maxlength : "内容长度不能大于1000个字符"
	                },
	                status : {
						required : "请选择状态"
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
                        url = '/system/sn/edit.html';
                    } else {
                        url = '/system/sn/add.html';
                    }
                    webside.common.commit('sn-form', url, '/system/sn/listUI.html');
                }
            });
		}
	}
};

		$(function() 
		{
		    sn.form.validateForm();
		    $('select').select2();
		});
