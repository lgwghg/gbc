var team = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#team-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					teamName : {
						required : true,
	 					maxlength : 32,
	 					remote : {
	 						url: sys.rootPath+"/match/team/checkTeamName.json",
	 						type: "post",
	 						dataType: "json",
	 						data : {
	 							id : function(){
	 								return $("#id").val()
	 							}
	 						}
	 					}
	                },
					sortNum : {
						required : true,
	 					maxlength : 10,
	 					digits : true
	                },
	                teamStatus : {
						required : true,
	 					maxlength : 10
	                },
                },
                messages : {
					teamName : {
						required : "请填写战队名称",
	 					maxlength : "战队名称类型长度不能大于32个字符",
	 					remote : "战队名称已存在!"
	                },
					sortNum : {
						required : "请填写排序",
	 					maxlength : "排序类型长度不能大于10个字符",
	 					digits : "请填写不小于0的整数"
	                },
	                teamStatus : {
						required : "请选择战队状态 ",
	 					maxlength : "战队状态长度不能大于10个字符"
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
                        url = '/match/team/edit.html';
                    } else {
                        url = '/match/team/add.html';
                    }
                    webside.common.commit('team-form', url, '/match/team/listUI.html');
                }
            });
		}
	}
};

		$(function() 
		{
		    team.form.validateForm();
		    $('select').select2();
		    
			var up = $('#little_upload').Huploadify({
				auto:true,
				fileTypeExts:'*.jpg;*.gif;*.jpeg;*.png;*.bmp',//允许的格式  
				multi:false,
				formData:{jssessionid: '${cookie.SSESSIONID.value}',type: '0'},
				fileSizeLimit:99999999999,
				showUploadedPercent:false,
				showUploadedSize:false,
				removeTimeout:9999999,
				uploader:sys.rootPath+'/upload/fileUpload',
				buttonCursor   : 'hand',  
			    buttonText     : '上传战队图标',  
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
			        	$("#teamIcon").val(data.replace(new RegExp('"',"gm"),''));  
			            $("#little_pic_view").attr("src",data.replace(new RegExp('"',"gm"),''));
			        }
			        
			        $("#little_upload_file_upload_1-queue").hide();//隐藏div
			    }
			});
		});
		
		$(document).ready(function() {
			if($("#teamIcon").val()!='')
			{
				$("#little_pic_view").attr("src",$("#teamIcon").val());  
			}
		});
