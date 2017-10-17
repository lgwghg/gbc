var goods = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#goods-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					goodsName : {
						required : true,
	 					maxlength : 128,
	 					remote : {
	 						url: sys.rootPath+"/goods/checkGoodsName.json",
	 						type: "post",
	 						dataType: "json",
	 						data : {
	 							id : function() {
	 								return $("#id").val();
	 							}
	 						}
	 					}
	                },
					goodsImg : {
	 					maxlength : 256
	                },
					goodsGold : {
						required : true,
	 					maxlength : 19
	                },
					goodsNum : {
	 					maxlength : 19
	                },
					desc : {
	 					maxlength : 1000
	                },
					type : {
						required : true,
	 					maxlength : 4
	                },
					status : {
						required : true,
	 					maxlength : 4
	                },
                },
                messages : {
					goodsName : {
						required : "请填写商品名称",
	 					maxlength : "商品名称类型长度不能大于128个字符",
	 					remote : "商品名称已存在!"
	                },
					goodsImg : {
						required : "请填写商品图片",
	 					maxlength : "商品图片类型长度不能大于256个字符"
	                },
					goodsGold : {
						required : "请填写商品金币",
	 					maxlength : "商品金币类型长度不能大于19个字符"
	                },
					goodsNum : {
						required : "请填写商品数量",
	 					maxlength : "商品数量类型长度不能大于19个字符"
	                },
					desc : {
						required : "请填写商品描述",
	 					maxlength : "商品描述类型长度不能大于1000个字符"
	                },
					type : {
						required : "请选择类型",
	 					maxlength : "类型 长度不能大于4个字符"
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
                        url = '/goods/edit.html';
                    } else {
                        url = '/goods/add.html';
                    }
                    webside.common.commit('goods-form', url, '/goods/listUI.html');
                }
            });
		}
	}
};

$(function() {
	goods.form.validateForm();
	
	var goodsImg = $("#goodsImg").val();
	if(goodsImg != '' && goodsImg != undefined) {
		$("#goods_pic_view").attr("src", goodsImg);  
	}
	
	var up = $('#goods_upload').Huploadify({
		auto:true,
		fileTypeExts:'*.jpg;*.gif;*.jpeg;*.png;*.bmp',// 允许的格式
		multi:false,
		formData:{jssessionid: '${cookie.SSESSIONID.value}',type: '0'},
		fileSizeLimit:99999999999,
		showUploadedPercent:false,
		showUploadedSize:false,
		removeTimeout:9999999,
		uploader:sys.rootPath+'/upload/fileUpload',
		buttonCursor   : 'hand',  
	    buttonText     : '上传大图片',  
	    height         : '25',  
		onUploadSuccess:function(file,data,response) {  
	        // 设置图片预览
	        if(data) {
	        	if(data.indexOf("error:") > -1) {
	        		alert(data.substring(data.indexOf("error:")));
	        		return;
	        	}
	        	$("#goodsImg").val(data.replace(new RegExp('"',"gm"),''));  
	            $("#goods_pic_view").attr("src",data.replace(new RegExp('"',"gm"),''));
	        }
	        
	        $("#goods_upload_file_upload_1-queue").hide();// 隐藏div
	    }
	})
});
