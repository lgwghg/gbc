var goodsStock = 
{
	form : 
	{
		validateForm : function() {
			$('#goodsstock-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					goodsId : {
						required : true,
	 					maxlength : 32
	                },
					cardNo : {
	 					maxlength : 128,
	 					remote : {
	 						url: sys.rootPath+"/goodsStock/checkCardNo.json",
	 						type: "post",
	 						dataType: "json",
	 						data : {
	 							id : function() {
	 								return $("#id").val();
	 							}
	 						}
	 					}
	                },
					cardPass : {
	 					maxlength : 128
	                },
					goodsNo : {
	 					maxlength : 128,
	 					remote : {
	 						url: sys.rootPath+"/goodsStock/checkGoodsNo.json",
	 						type: "post",
	 						dataType: "json",
	 						data : {
	 							id : function() {
	 								return $("#id").val();
	 							},
	 							goodsId : function() {
	 								return $("#goodsId").val();
	 							}
	 						}
	 					}
	                },
					effectiveTime : {
	 					maxlength : 19
	                },
					status : {
						required : true,
	 					maxlength : 4
	                },
                },
                messages : {
					goodsId : {
						required : "请填写所属商品",
	 					maxlength : "所属商品类型长度不能大于32个字符"
	                },
					cardNo : {
						required : "请填写卡号",
	 					maxlength : "卡号类型长度不能大于128个字符",
	 					remote: "卡号已存在！"
	                },
					cardPass : {
						required : "请填写卡密",
	 					maxlength : "卡密类型长度不能大于128个字符"
	                },
					goodsNo : {
						required : "请填写编号",
	 					maxlength : "编号类型长度不能大于128个字符",
	 					remote: "编号已存在！"
	                },
					effectiveTime : {
						required : "请填写有效期",
	 					maxlength : "有效期类型长度不能大于19个字符"
	                },
					status : {
						required : "请选择状态",
	 					maxlength : "状态 类型长度不能大于4个字符"
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
                        url = '/goodsStock/edit.html';
                    } else {
                        url = '/goodsStock/add.html';
                    }
                    webside.common.commit('goodsstock-form', url, '/goodsStock/listUI.html');
                }
            });
		}
	}
};

$(function () {
	 goodsStock.form.validateForm();
	 $('select').select2();
	 
});
