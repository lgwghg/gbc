var task = 
{
	form : 
	{
		validateForm : function() 
		{	
			jQuery.validator.addMethod("num", function(value, element) {
				if (!/^([1-9][0-9]*)$/.test(value)) {
					return false;
				}
                return true;
            }, "请输入不大于100的正整数");
			$('#task-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					taskName : {
						required : true,
	 					maxlength : 255
	                },
					image : {
						required : true,
	 					maxlength : 200
	                },
					description : {
						required : true,
	 					maxlength : 255
	                },
					num : {
						required : true,
	 					maxlength : 10,
	 					num : "required"
	                },
					gold : {
						required : true,
	 					maxlength : 10,
	 					num : "required"
	                }
                },
                messages : {
					taskName : {
						required : "请填写",
	 					maxlength : "类型长度不能大于255个字符"
	                },
					image : {
						required : "请上传图片",
	 					maxlength : "类型长度不能大于200个字符"
	                },
					description : {
						required : "请填写介绍",
	 					maxlength : "类型长度不能大于255个字符"
	                },
					num : {
						required : "请填写任务需要完成的次数",
	 					maxlength : "任务需要完成的次数类型长度不能大于10个字符",
	 					num : "请填写正整数"
	                },
					gold : {
						required : "请填写完成任务奖励的G币",
	 					maxlength : "完成任务的奖励金币类型长度不能大于10个字符",
	 					num : "请填写正整数"
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
                        url = '/task/edit';
                    } else {
                        url = '/task/add';
                    }
                    webside.common.commit('task-form', url, '/task/listUI');
                }
            });
		}
	}
};


$(function() {
	var up2 = $('#image_upload').Huploadify({
		auto:true,
		fileTypeExts:'*.jpg;*.png;',//允许的格式  
		multi:false,
		formData:{jssessionid: '${cookie.SSESSIONID.value}',type: '0'},
		fileSizeLimit:99999999999,
		showUploadedPercent:false,
		showUploadedSize:false,
		removeTimeout:9999999,
		uploader:sys.rootPath+'/upload/fileUpload',
		buttonCursor   : 'hand',  
	    buttonText     : '上传任务图片',  
	    height         : '25',  
		onUploadSuccess:function(file,data,response) 
		{  
	        //设置图片预览  
	        if(data)
	        {
	        	if(data.indexOf("error:") > -1)
	        	{
	        		alert(data.substring(data.indexOf("error:")));
	        		return;
	        	}
	        	$("#image").val(data.replace(new RegExp('"',"gm"),''));  
	            $("#image_view").attr("src",data.replace(new RegExp('"',"gm"),''));
	        }
	        
	        $("#image_upload_file_upload_1-queue").hide();//隐藏div
	    }
	});
	
	if($("#image").val()!='') {
		$("#image_view").attr("src",$("#image").val());  
	}
	
});