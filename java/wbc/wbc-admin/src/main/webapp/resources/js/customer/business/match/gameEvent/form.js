var gameEvent = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#gameEvent-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					eventName : {
						required : true,
	 					maxlength : 32,
	 					remote : {
	 						url: sys.rootPath+"/match/gameEvent/checkEventName.json",
	 						type: "post",
	 						dataType: "json",
	 						data : {
	 							id : function(){
	 								return $("#id").val()
	 							}
	 						}
	 					}
	                },
	                gameId : {
						required : true
	                },
	                startTime : {
	                	required : true
	                },
	                endTime : {
	                	required : true
	                },
	                geStatus : {
						required : true
	                },
                },
                messages : {
                	eventName : {
						required : "请填写赛事名称",
	 					maxlength : "赛事名称类型长度不能大于32个字符",
	 					remote : "赛事名称已存在!"
	                },
	                gameId : {
						required : "请选择游戏"
	                },
	                startTime : {
						required : "请选择开始时间"
	                },
	                endTime : {
						required : "请选择结束时间"
	                },
	                geStatus : {
						required : "请选择赛事状态 "
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
                        url = '/match/gameEvent/edit.html';
                    } else {
                        url = '/match/gameEvent/add.html';
                    }
                    webside.common.commit('gameEvent-form', url, '/match/gameEvent/listUI.html');
                }
            });
		}
	}
};

		$(function() 
		{
		    gameEvent.form.validateForm();
		    $('select').select2();
		    
		    var up = $('#big_upload').Huploadify({
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
			    buttonText     : '上传赛事图片',  
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
			        	$("#eventImg").val(data.replace(new RegExp('"',"gm"),''));  
			            $("#big_pic_view").attr("src",data.replace(new RegExp('"',"gm"),''));
			        }
			        
			        $("#big_upload_file_upload_1-queue").hide();//隐藏div
			    }
			});
			
		});
		
		$(document).ready(function() {
			if($("#eventImg").val()!='')
			{
				$("#big_pic_view").attr("src",$("#eventImg").val());  
			}
		});
