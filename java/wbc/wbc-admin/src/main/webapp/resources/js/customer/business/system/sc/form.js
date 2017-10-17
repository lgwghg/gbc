var sc = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#sc-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	keywords : {
	 					maxlength : 256
	                },
	                description : {
	 					maxlength : 256
	                },
	                title : {
						required : true,
	 					maxlength : 256
	                },
					type : {
						required : true
	                }
                },
                messages : {
	                keywords : {
	 					maxlength : "keywords类型长度不能大于32个字符"
	                },
	                description : {
	 					maxlength : "description类型长度不能大于256个字符"
	                },
	                title : {
	                	required : "请填写title",
	 					maxlength : "title类型长度不能大于256个字符"
	                },
					type : {
						required : "请选择类别"
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
                        url = '/system/sc/edit.html';
                    } else {
                        url = '/system/sc/add.html';
                    }
                    webside.common.commit('sc-form', url, '/system/sc/listUI.html');
                }
            });
		}
	}
};

$(function() {
	sc.form.validateForm();
	$('select').select2();
});
