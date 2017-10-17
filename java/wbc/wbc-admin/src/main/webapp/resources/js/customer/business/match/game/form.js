var game = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#game-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					gameName : {
						required : true,
	 					maxlength : 32,
	 					remote : {
	 						url: sys.rootPath+"/match/game/checkGameName.json",
	 						type: "post",
	 						dataType: "json",
	 						data : {
	 							id : function(){
	 								return $("#id").val()
	 							}
	 						}
	 					}
	                },
	                englishName : {
						required : true,
	 					maxlength : 32,
	 					remote : {
	 						url: sys.rootPath+"/match/game/checkEnglishName.json",
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
	                gameStatus : {
						required : true,
	 					maxlength : 10
	                },
                },
                messages : {
					gameName : {
						required : "请填写游戏名称",
	 					maxlength : "游戏名称类型长度不能大于32个字符",
	 					remote : "游戏名称已存在!"
	                },
	                englishName : {
						required : "请填写英文简称",
	 					maxlength : "英文简称类型长度不能大于32个字符",
	 					remote : "游戏英文名称已存在!"
	                },
					sortNum : {
						required : "请填写排序",
	 					maxlength : "排序类型长度不能大于10个字符",
	 					digits : "请填写不小于0的整数"
	                },
	                gameStatus : {
						required : "请选择游戏状态 ",
	 					maxlength : "游戏状态长度不能大于10个字符"
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
                        url = '/match/game/edit.html';
                    } else {
                        url = '/match/game/add.html';
                    }
                    webside.common.commit('game-form', url, '/match/game/listUI.html');
                }
            });
		}
	}
};

		$(function() 
		{
		    game.form.validateForm();
		    $('select').select2();
		    
		    var up = $('#big_upload').Huploadify({
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
			    buttonText     : '上传大图片',  
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
			        	$("#bigImg").val(data.replace(new RegExp('"',"gm"),''));  
			            $("#big_pic_view").attr("src",data.replace(new RegExp('"',"gm"),''));
			        }
			        
			        $("#big_upload_file_upload_1-queue").hide();//隐藏div
			    }
			});
			
			var up2 = $('#little_upload').Huploadify({
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
			    buttonText     : '上传小图片',  
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
			        	$("#littleImg").val(data.replace(new RegExp('"',"gm"),''));  
			            $("#little_pic_view").attr("src",data.replace(new RegExp('"',"gm"),''));
			        }
			        
			        $("#little_upload_file_upload_1-queue").hide();//隐藏div
			    }
			});
			
			var up3 = $('#bg_upload').Huploadify({
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
			    buttonText     : '上传背景图片',  
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
			        	$("#bgImg").val(data.replace(new RegExp('"',"gm"),''));  
			            $("#bg_pic_view").attr("src",data.replace(new RegExp('"',"gm"),''));
			        }
			        
			        $("#bg_upload_file_upload_1-queue").hide();//隐藏div
			    }
			});
		});
		
		$(document).ready(function() {
			if($("#bigImg").val()!='')
			{
				$("#big_pic_view").attr("src",$("#bigImg").val());  
			}
			if($("#littleImg").val()!='')
			{
				$("#little_pic_view").attr("src",$("#littleImg").val());  
			}
			if($("#bgImg").val()!='')
			{
				$("#bg_pic_view").attr("src",$("#bgImg").val());  
			}
		});
