var cdKey = 
{
	form : 
	{
		validateForm : function() 
		{	
			jQuery.validator.addMethod("goldNum", function(value, element) {
                return this.optional(element) || /^([1-9][0-9]*00)$/.test(value);
            }, "请输入100倍数的正整数");
			jQuery.validator.addMethod("codeNum", function(value, element) {
				if (!/^([1-9][0-9]*)$/.test(value)) {
					return false;
				}
				if (value > 100) {
					return false;
				}
                return true;
            }, "请输入不大于100的正整数");
			jQuery.validator.addMethod("startTimeCheck", function(value, element) {
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
				if (startTime == "" || endTime == "") {
					return true;
				}
				if (startTime >= endTime) {
					return false;
				}
                return true;
            }, "开始时间必须小于结束时间");
			jQuery.validator.addMethod("checkUserMobile", function(value, element) {
				var userMobile = $("#userMobile").val();
				var result = true;
				$.ajax({
			        type : "POST",
			        url : sys.rootPath + "/user/withoutAuth/validateUserMobile",
			        data : {mobile : userMobile},
			        dataType : "json",
			        async: false,
			        success : function(resultdata) {
			            if (resultdata) {
			            	result = false;
			            } else {
			            	result = true;
			            }
			        },
			        error : function(data, errorMsg) {
			        	result = false;
			        }
			    });
				return result;
				//return checkUserExist();
            }, "请输入正确并存在的手机号用户");
			
			$('#cdkey-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					type : {
						required : true
	                },
					goodsId : {
						required : function() {
							var type = $("#type").val();
							if (type==0) {
								return true;
							} else {
								return false;
							}
						},
	 					maxlength : 32
	                },
					gold : {
						required : function() {
							var type = $("#type").val();
							if (type==1 || type==2) {
								return true;
							} else {
								return false;
							}
						},
	 					maxlength : 10,
	 					goldNum : 'required'
	                },
					codeNum : {
						required : true,
	 					codeNum : 'required'
	                },
					startTime : {
						required : false
	                },
					endTime : {
						required : true,
						startTimeCheck : "required"
	                },
	                userMobile : {
	                	required : false,
	                	checkUserMobile : "required"
	                }
                },
                messages : {
					type : {
						required : "请选择cdkey类型"
	                },
					goodsId : {
						required : "请填写商品id",
	 					maxlength : "商品id类型长度不能大于32个字符"
	                },
					gold : {
						required : "请填写币值",
	 					maxlength : "币值不能超过10位数",
	 					goldNum : "请输入100倍数的正整数"
	                },
					codeNum : {
						required : "请填写需要生成的兑换码数量",
	 					codeNum : "请输入不大于100的正整数"
	                },
					startTime : {
						
	                },
					endTime : {
						required : "请填写结束时间",
						startTimeCheck : "开始时间不能大于结束时间"
	                },
	                userMobile : {
	                	checkUserMobile : "请填入正确并存在的手机号用户"
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
                        url = '/cdkey/edit';
                    } else {
                        url = '/cdkey/add';
                    }
                    // webside.common.commit('cdkey-form', url, '/cdkey/listUI');
                    saveCdkey('cdkey-form', url, '/cdkey/listUI');
                }
            });
		}
	}
};


function selectType(type) {
	if (type==0) {
		$("#goodsIdDiv").css("display","block");
		$("#goldDiv").css("display","none");
		//$("#userMobileDiv").css("display","block");
	} else {
		$("#goodsIdDiv").css("display","none");
		$("#goldDiv").css("display","block");
		//$("#userMobileDiv").css("display","none");
	}
}

$(function() {
	$('select').select2();
});


function saveCdkey(formId, commitUrl, jumpUrl) {
	//组装表单数据
    var index;
    var data = $("#" + formId).serialize();
    if (undefined != $("#pageNum").val()) {
        jumpUrl = jumpUrl + '?page=' + $("#pageNum").val() + '&rows=' + $("#pageSize").val() + '&sidx=' + $("#orderByColumn").val() + '&sord=' + $("#orderByType").val();
    }
    $.ajax({
        type : "POST",
        url : sys.rootPath + commitUrl,
        data : data,
        dataType : "json",
        beforeSend : function() {
            index = layer.load();
        },
        async: false,
        success : function(resultdata) {
            layer.close(index);
            if (resultdata.success) {
            	layer.confirm(resultdata.message + ',是否导出生成的兑换码？', {
                    icon : 3,
                    title : '导出兑换码'
                }, function(index, layero) {
                	var idList = resultdata.idList;
                	var idListStr = "";
                	for (var i = 0; i < idList.length; i++) {
                		if (i==0) {
                			idListStr = idListStr + idList[i];
                		} else {
                			idListStr = idListStr + "," + idList[i];
                		}
                	}
                	window.location.href = sys.rootPath + "/cdkey/export" +"?idListStr=" + idListStr;
                    layer.close(index);
                });
            	/*layer.msg(resultdata.message, {
                    icon : 1
                });*/
                webside.common.loadPage(jumpUrl);
            } else {
                layer.msg(resultdata.message, {
                    icon : 5
                });
            }
        },
        error : function(data, errorMsg) {
            layer.close(index);
            layer.msg(data.responseText, {
                icon : 2
            });
        }
    });
}

function checkUserExist() {
	var userMobile = $("#userMobile").val();
	$.ajax({
        type : "POST",
        url : sys.rootPath + "/user/withoutAuth/validateUserMobile",
        data : {mobile : userMobile},
        dataType : "json",
        async: false,
        success : function(resultdata) {
            if (resultdata) {
            	return false;
            } else {
            	return true;
            }
        },
        error : function(data, errorMsg) {
        	return false;
        }
    });
}