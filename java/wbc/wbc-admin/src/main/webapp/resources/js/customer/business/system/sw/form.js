var sw = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#sw-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	content : {
						required : true,
	 					maxlength : 256
	                },
	                contentType : {
						required : true
	                },
	                useType : {
						required : true
	                }
                },
                messages : {
                	content : {
						required : "请填写内容",
	 					maxlength : "内容长度不能大于256个字符"
	                },
	                contentType : {
						required : "请选择所属类型"
	                },
	                useType : {
						required : "请选择作用域类型"
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
                        url = '/system/sw/edit.html';
                    } else {
                        url = '/system/sw/add.html';
                    }
                    webside.common.commit('sw-form', url, '/system/sw/listUI.html');
                }
            });
		}
	}
};

		$(function() 
		{
		    sw.form.validateForm();
		    $('select').select2();
		});
