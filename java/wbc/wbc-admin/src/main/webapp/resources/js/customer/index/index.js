var webside = {
    index : {
        initHomePage : function() {
            $(".page-content").load(sys.rootPath + "/welcome.jsp");
            $(".breadcrumb").html('<li><i class="ace-icon fa fa-home home-icon"></i><a href="javascript:webside.index.initHomePage();">首页</a></li><li class="active">控制台</li>');
        },
        menu : {
            initMenuEvent : function() {
                $("[nav-menu]").each(function() {
                    $(this).bind("click", function() {
                        var nav = $(this).attr("nav-menu");
                        var sn = nav.split(",");
                        //清除用户信息菜单样式
                        $(".user-menu").find('li').each(function() {
                            $(this).removeClass('active');
                        });
                        //处理监控-新窗口打开
                        if (sn[sn.length - 1] == '/sirona' || sn[sn.length - 1] == '/druid') {
                            window.open(sys.rootPath + sn[sn.length - 1]);
                        } else {
                            var breadcrumb = '<li><i class="ace-icon fa fa-home home-icon"></i><a href="javascript:webside.index.initHomePage();">首页</a></li>';
                            for (var i = 0; i < sn.length - 1; i++) {
                                breadcrumb += '<li class="active">' + sn[i] + '</li>';
                            }
                            //设置面包屑内容
                            $(".breadcrumb").html(breadcrumb);
                            //加载页面
                            $(".page-content").html('');
                            $(".page-content").load(sys.rootPath + sn[sn.length - 1]);
                        }
                        var level = $(this).parent("li").attr("level");
                        if (level == 'level1' || level == 'level2') {
                            //处理目录类型的点击事件-两级菜单
                            $(this).parent("li").siblings().find("ul.nav-show").removeClass('nav-show').addClass('nav-hide').attr('style', 'display:none');
                            //处理菜单类型的点击事件
                            $(this).parent().parent().parent("li").siblings().find("ul.nav-show").removeClass('nav-show').addClass('nav-hide').attr('style', 'display:none');
                            $("ul.nav-list").find("li.active").removeClass("active").removeClass('open');
                            $(this).parent().addClass("active").parent().parent("li").addClass('active open');
                        } else if (level == 'level3') {
                            //处理目录类型的点击事件-三级菜单
                            $(this).parent("li").siblings().find("ul.nav-show").removeClass('nav-show').addClass('nav-hide').attr('style', 'display:none');
                            //处理菜单类型的点击事件
                            $(this).parent().parent().parent().parent().parent("li").siblings().find("ul.nav-show").removeClass('nav-show').addClass('nav-hide').attr('style', 'display:none');
                            $("ul.nav-list").find("li.active").removeClass("active").removeClass('open');
                            $(this).parent().addClass("active").parent().parent().parent().parent("li").addClass('active open');
                        } else {
                            //处理目录类型的点击事件-四级菜单
                            $(this).parent("li").siblings().find("ul.nav-show").removeClass('nav-show').addClass('nav-hide').attr('style', 'display:none');
                            //处理菜单类型的点击事件
                            $(this).parent().parent().parent().parent().parent().parent().parent("li").siblings().find("ul.nav-show").removeClass('nav-show').addClass('nav-hide').attr('style', 'display:none');
                            $("ul.nav-list").find("li.active").removeClass("active").removeClass('open');
                            $(this).parent().addClass("active").parent().parent().parent().parent().parent().parent("li").addClass('active open');
                        }
                    });
                });
            },
            initDropdownMenuStyle : function() {
                $(".user-menu").find('li').each(function() {
                    $(this).bind('click', function() {
                        $(this).siblings().removeClass('active');
                        $(this).addClass('active');
                    });
                });
            }
        },
        initNavigation : function() {
            $("#ace-settings-navbar").click();
            $("#ace-settings-sidebar").click();
            //$("#ace-settings-breadcrumbs").click();
        },
        initScrollBar : function() {
            $("html").niceScroll({
                cursorborderradius : "5px",
                cursorwidth : 10
            });
        },
        /*
         * 监听浏览器窗口大小变化
         */
        resizeScrollBar : function() {
            $(window).resize(function() {
                $("html").getNiceScroll().resize();
            });
        }
    },
    common : {
        /**
         *加载非菜单展示页面
         * @param nav 待加载的资源URL
         */
        loadPage : function(nav) {
            //加载页面
            $(".page-content").load(sys.rootPath + nav);
        },
        /**
         * 新增
         * @param {Object} nav  提交url
         */
        addModel : function(nav) {
            //加载新增页面
            webside.common.loadPage(nav);
        },
        /**
         * 编辑
         * @param {Object} nav  提交url
         */
        editModel : function(nav) {
            //当前页码
            var nowPage = grid.pager.nowPage;
            //获取每页显示的记录数(即: select框中的10,20,30)
            var pageSize = grid.pager.pageSize;
            //获取排序字段
            var columnId = grid.sortParameter.columnId;
            //获取排序方式 [0-不排序，1-正序，2-倒序]
            var sortType = grid.sortParameter.sortType;
            //获取选择的行
            var rows = grid.getCheckedRecords();
            if (rows.length == 1) {
                webside.common.loadPage(nav + '?id=' + rows[0].id + "&page=" + nowPage + "&rows=" + pageSize + "&sidx=" + columnId + "&sord=" + sortType);
            } else {
                layer.msg("你没有选择行或选择了多行数据", {
                    icon : 0
                });
            }
        },
        /**
         * 删除
         * @param {Object} nav  提交url
         * @param callback 主函数执行完毕后调用的回调函数名称
         */
        delModel : function(nav, callback) {
            var rows = grid.getCheckedRecords();
            if (rows.length == 1) {
                if (nav == '/user/deleteBatch.html') {
                    if (rows[0].role.name == '超级管理员') {
                        layer.msg('该用户为超级管理员,不能删除!', {
                            icon : 0
                        });
                        return false;
                    }
                }
                if (nav == '/role/deleteBatch.html') {
                    if (rows[0].name == '超级管理员') {
                        layer.msg('该角色为基础角色,不能删除!', {
                            icon : 0
                        });
                        return false;
                    }
                }
                layer.confirm('确认删除吗？', {
                    icon : 3,
                    title : '删除提示'
                }, function(index, layero) {
                    var delete_ids = [];
                    /*
                     $.each(rows, function(index, value) {
                     ids.push(this.id);
                     });
                     */
                    delete_ids.push(rows[0].id);
                    $.ajax({
                        type : "POST",
                        url : sys.rootPath + nav,
                        data : {
                            "ids" : delete_ids.join(',')
                        },
                        dataType : "json",
                        success : function(resultdata) {
                            if (resultdata.success) {
                                layer.msg(resultdata.message, {
                                    icon : 1
                                });
                                if (callback) {
                                    callback();
                                }
                            } else {
                                layer.msg(resultdata.message, {
                                    icon : 5
                                });
                            }
                        },
                        error : function(errorMsg) {
                            layer.msg('服务器未响应,请稍后再试', {
                                icon : 3
                            });
                        }
                    });
                    layer.close(index);
                });
            } else {
                layer.msg("你没有选择行或选择了多行数据", {
                    icon : 0
                });
            }
        },
        /**
         * 提交表单
         * 适用场景：form表单的提交，主要用在用户、角色、资源等表单的添加、修改等
         * @param {Object} commitUrl 表单提交地址
         * @param {Object} listUrl 表单提交成功后转向的列表页地址
         */
        commit : function(formId, commitUrl, jumpUrl) {
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
                success : function(resultdata) {
                    layer.close(index);
                    if (resultdata.success) {
                        layer.msg(resultdata.message, {
                            icon : 1
                        });
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
    },
    form : {
        user : {
            validateUserForm : function() {
            	jQuery.validator.addMethod("mobile", function(value, element) {
                    return this.optional(element) || /^1[3|4|5|7|8]\d{9}$/.test(value);
                }, "请输入11位手机号码");
                $('#userForm').validate({
                    errorElement : 'div',
                    errorClass : 'help-block',
                    focusInvalid : false,
                    ignore : "",
                    rules : {
                    	nickName : {
                    		required : true,
                    		remote : {//更新时不验证
                                param : {
                                    url : sys.rootPath + '/user/withoutAuth/validateUserNick',
                                    cache : false
                                },
                                depends : function() {
                                    return typeof ($("#userId").val()) == "undefined" || $("#userId").val() == "";
                                }
                            }
                    	},
                        email : {
                            required : false,
                            email : true,
                            remote : {//更新时不验证
                                param : {
                                    url : sys.rootPath + '/user/withoutAuth/validateEmail',
                                    cache : false
                                },
                                depends : function() {
                                    return typeof ($("#userId").val()) == "undefined" || $("#userId").val() == "";
                                }
                            }
                        },
                        mobile : {
                        	required : true,
                            mobile : 'required',
                        	remote : {//更新时不验证
                                param : {
                                    url : sys.rootPath + '/user/withoutAuth/validateUserMobile',
                                    cache : false
                                },
                                depends : function() {
                                    return typeof ($("#userId").val()) == "undefined" || $("#userId").val() == "";
                                }
                            }
                        },
                        password : {
                            required : true,
                            minlength : 6
                        },
                        repassword : {
                            required : true,
                            minlength : 6,
                            equalTo : "#password"
                        },
                        userUrl : {
                            required : false,
                            remote : {// 不为空，且更新时验证
                                param : {
                                    url : sys.rootPath + '/user/nl/validateUserUrl.html',
                                    cache : false
                                },
                                depends : function() {
                                    return typeof ($("#userId").val()) != "undefined" && $("#userId").val() != "" && $("#userUrl").val() != "";
                                }
                            }
                        }/*,
                        "role.id" : {
                            required : true
                        }*/
                    },
                    messages : {
                    	nickName : {
                    		required : "请填写用户昵称",
                    		remote : "该昵称已注册,请使用其他昵称"
                    	},
                        email : {
                            //required : "请填写邮箱",
                            email : "请填写正确的邮箱",
                            remote : "该邮箱已注册,请使用其他邮箱"
                        },
                        mobile : {
                        	required : "请填写手机号",
                        	mobile : "请填写11位的手机号",
                            remote : "该手机号已注册,请使用其他手机号"
                        },
                        password : {
                            required : "请填写密码",
                            minlength : "密码长度不能少于6个字符"
                        },
                        repassword : {
                            required : "请再次填写密码",
                            minlength : "密码长度不能少于6个字符",
                            equalTo : "两次填写密码不一致，请重新填写"
                        },
                        userUrl : {
                        	remote : "该用户自定义url已经被使用，请换一个"
                        },
                        "role.id" : "请选择角色"
                    },
                    highlight : function(e) {
                        $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
                    },
                    success : function(e) {
                        $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
                        $(e).remove();
                    },
                    errorPlacement : function(error, element) {
                        if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                            var controls = element.closest('div[class*="col-"]');
                            if (controls.find(':checkbox,:radio').length > 1)
                                controls.append(error);
                            else
                                error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                        } else if (element.is('.select2')) {
                            error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                        } else if (element.is('.chosen-select')) {
                            error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                        } else
                            error.insertAfter(element.parent());
                    },
                    submitHandler : function(form) {
                        var userId = $("#userId").val();
                        var url = "";
                        if (userId != undefined && userId !="") {
                            url = '/user/edit.html';
                        } else {
                            url = '/user/add.html';
                        }
                        webside.common.commit('userForm', url, '/user/listUI.html');
                    }
                });
            },
            validateUserPasswordForm : function() {
                $('#userPassword').validate({
                    errorElement : 'div',
                    errorClass : 'help-block',
                    focusInvalid : false,
                    ignore : "",
                    rules : {
                        password : {
                            required : true,
                            minlength : 6
                        },
                        repassword : {
                            required : true,
                            minlength : 6,
                            equalTo : "#password"
                        }
                    },
                    messages : {
                        password : {
                            required : "请填写密码",
                            minlength : "密码长度不能少于6个字符"
                        },
                        repassword : {
                            required : "请再次填写密码",
                            minlength : "密码长度不能少于6个字符",
                            equalTo : "两次填写密码不一致，请重新填写"
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
                        webside.common.commit('userPassword', '/user/password.html', '/logout');
                    },
                    invalidHandler : function(form) {
                    }
                });
            }
        },
        userInfo : {
            initButton : function() {
                $("#btnEdit").click(function() {
                    //按钮切换
                    $(this).hide();
                    $("#btnAdd").show();
                    //表单切换
                    $("#lableDiv").hide();
                    $("#formDiv").show();
                });
            },
            initBirthday : function() {
                $("#birthday").datepicker({
                    format : 'yyyy-mm-dd',
                    autoclose : true,
                    language : 'zh-CN',
                    todayHighlight : true,
                    clearBtn : true,
                    immediateUpdates : true,
                    clearDate : function() {
                        $("#birthday").val('').datepicker('update');
                    }
                });
            },
            validateUserInfoForm : function() {
                /*jQuery.validator.addMethod("phone", function(value, element) {
                    return this.optional(element) || /^1[3|4|5|7|8]\d{9}$/.test(value);
                }, "请输入11位手机号码");*/

                $('#userInfoForm').validate({
                    errorElement : 'div',
                    errorClass : 'help-block',
                    focusInvalid : false,
                    ignore : "",
                    rules : {
                    	email : {
                            required : true,
                            email : true,
                            remote : {//更新时不验证
                                param : {
                                    url : sys.rootPath + '/user/withoutAuth/validateEmail',
                                    cache : false
                                },
                                depends : function() {
                                    return typeof ($("#userId").val()) == "undefined";
                                }
                            }
                        }
                    },
                    messages : {
                    	email : {
                            required : "请填写邮箱",
                            email : "请填写正确的邮箱",
                            remote : "该邮箱已注册,请使用其他邮箱"
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
                        if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                            var controls = element.closest('div[class*="col-"]');
                            if (controls.find(':checkbox,:radio').length > 1)
                                controls.append(error);
                            else
                                error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                        } else if (element.is('.select2')) {
                            error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                        } else if (element.is('.chosen-select')) {
                            error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                        } else
                            error.insertAfter(element.parent());
                    },
                    submitHandler : function(form) {
                        webside.common.commit('userInfoForm', '/user/edit.html', '/user/infoUI.html?id=' + $("#id").val());
                    }
                });
            }
        },
        role : {
            validateRoleForm : function() {
                $('#roleForm').validate({
                    errorElement : 'div',
                    errorClass : 'help-block',
                    focusInvalid : false,
                    ignore : "",
                    rules : {
                        name : {
                            required : true,
                            remote : {
                                param : {
                                    url : sys.rootPath + '/role/withoutAuth/validateRoleName.html',
                                    cache : false
                                },
                                depends : function() {
                                    return typeof ($("#roleId").val()) == "undefined";
                                }
                            }
                        },
                        key : {
                            required : true
                        }
                    },
                    messages : {
                        name : {
                            required : "请填写角色名称",
                            remote : "该角色已存在"
                        },
                        key : "请填写角色标识"
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
                        var roleId = $("#roleId").val();
                        var url = "";
                        if (roleId != undefined) {
                            url = '/role/edit.html';
                        } else {
                            url = '/role/add.html';
                        }
                        webside.common.commit('roleForm', url, '/role/listUI.html');
                    }
                });
            }
        },
        dict : {
            validateDictForm : function() {
                $('#dictForm').validate({
                    errorElement : 'div',
                    errorClass : 'help-block',
                    focusInvalid : false,
                    ignore : "",
                    rules : {
                    	type : {
                            required : true,
                            maxlength : 32
                        },
                        label : {
                            required : true,
                            maxlength : 32
                        },
                        value : {
                            required : true,
                            maxlength : 32
                        },
                        sort : {
                            required : true,
                            maxlength : 8
                        },
                        description : {
                        	maxlength : 32
                        }
                    },
                    messages : {
                    	type : {
                            required : "请填写字典类型",
                            maxlength : "字典类型长度不能大于32个字符"
                        },
                        label : {
                            required : "请填写标签名",
                            maxlength : "标签名长度不能大于32个字符"
                        },
                        value : {
                            required : "请填写数据值",
                            maxlength : "数据值长度不能大于32个字符"
                        },
                        sort : {
                            required : "请填写排序",
                            maxlength : "排序长度不能大于8个字符"
                        },
                        description : {
                            maxlength : "描述长度不能大于32个字符"
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
                        if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                            var controls = element.closest('div[class*="col-"]');
                            if (controls.find(':checkbox,:radio').length > 1)
                                controls.append(error);
                            else
                                error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                        } else if (element.is('.select2')) {
                            error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                        } else if (element.is('.chosen-select')) {
                            error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                        } else
                            error.insertAfter(element.parent());
                    },
                    submitHandler : function(form) {
                        var id = $("#id").val();
                        var url = "";
                        if (id != undefined) {
                            url = '/system/dict/edit.html';
                        } else {
                            url = '/system/dict/add.html';
                        }
                        webside.common.commit('dictForm', url, '/system/dict/listUI.html');
                    }
                });
            }
        },
        sw : {
        	validateSensitiveWordsForm : function() {
                $('#sensitiveWordsForm').validate({
                    errorElement : 'div',
                    errorClass : 'help-block',
                    focusInvalid : false,
                    ignore : "",
                    rules : {
                    	content : {
                            required : true,
                            maxlength : 32
                        },
                        contentTypeValue : {
                            required : true
                        },
                        useTypeValue : {
                            required : true
                        }
                    },
                    messages : {
                    	content : {
                            required : "请填写内容"
                        },
                        contentTypeValue : {
                            required : "请选择内容所属类型"
                        },
                        useTypeValue : {
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
                        if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                            var controls = element.closest('div[class*="col-"]');
                            if (controls.find(':checkbox,:radio').length > 1)
                                controls.append(error);
                            else
                                error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                        } else if (element.is('.select2')) {
                            error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                        } else if (element.is('.chosen-select')) {
                            error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                        } else
                            error.insertAfter(element.parent());
                    },
                    submitHandler : function(form) {
                        var id = $("#sensitiveWordsId").val();
                        var url = "";
                        if (id != undefined) {
                            url = '/sw/edit.html';
                        } else {
                            url = '/sw/add.html';
                        }
                        webside.common.commit('sensitiveWordsForm', url, '/sw/listUI.html');
                    }
                });
            }
        },
        resource : {
            initSourceType : function() {
                $('#type').select2({
                    language : "zh-CN",
                    minimumResultsForSearch : Infinity
                });
            },
            initSourceTree : function() {
                $('#parentId').select2({
                    placeholder : "请选择上级资源...",
                    language : "zh-CN",
                    minimumResultsForSearch : Infinity,
                    templateResult : webside.form.resource.formatState,
                    templateSelection : function(selection) {
                        return $.trim(selection.text);
                    }
                });
            },
            formatState : function(state) {
                if (!state.id) {
                    return state.text;
                }
                var $state = $('<span><i class="fa fa-file-text-o green"></i>&nbsp;&nbsp;' + state.text + '</span>');
                return $state;
            },
            initIcon : function() {
                $("#icon").bind('focus', function(event) {
                    var iconLayer = layer.open({
                        type : 2,
                        scrollbar : false,
                        content : sys.rootPath + '/resource/icon.html',
                        area : 'auto',
                        maxmin : true,
                        shift : 4,
                        title : '<i class="fa fa-cogs"></i>&nbsp;选择图标'
                    });
                    //弹出即全屏
                    layer.full(iconLayer);
                });
                $("#iconShow").bind('click', function(event) {
                    $("#icon").val('');
                    $(this).removeClass();
                });
                $('[data-rel=tooltip]').tooltip();
            },
            initType : function() {
                $("#parentId").change(function() {
                    var parentId = $.trim($(this).children('option:selected').val());
                    if (parentId == null || parentId == '') {
                        //$("#iconDiv").show();
                        //$("#sourceUrlDiv").hide();
                    } else {
                        //$("#iconDiv").hide();
                        //$("#sourceUrlDiv").show();
                    }
                });
            },
            validateResourceForm : function() {
                $('#resourceForm').validate({
                    errorElement : 'div',
                    errorClass : 'help-block',
                    focusInvalid : false,
                    ignore : "",
                    rules : {
                        parentId : {
                            required : true
                        },
                        name : {
                            required : true
                        },
                        sourceKey : {
                            required : true
                        },
                        type : {
                            required : true
                        },
                        sourceUrl : {
                            required : function() {
                                /*
                                 //仅目录菜单不需要url
                                 var parentId = $.trim($("#parentId").val());
                                 if (parentId == null || parentId == "") {
                                 return false;
                                 } else {
                                 return true;
                                 }
                                 */
                                return false;
                            }
                        },
                        icon : {
                            required : function() {
                                /*
                                 //仅目录菜单需要icon
                                 var parentId = $.trim($("#parentId").val());
                                 if (parentId == null || parentId == "") {
                                 return true;
                                 } else {
                                 return false;
                                 }
                                 */
                                return false;
                            }
                        }
                    },
                    messages : {
                        parentId : "请选择上级资源",
                        name : "请填写资源名称",
                        sourceKey : "请填写资源标识",
                        type : "请选择资源类型",
                        sourceUrl : "请填写资源URL",
                        icon : "请选择菜单图标"
                    },
                    highlight : function(e) {
                        $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
                    },
                    success : function(e) {
                        $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
                        $(e).remove();
                    },
                    errorPlacement : function(error, element) {
                        if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                            var controls = element.closest('div[class*="col-"]');
                            if (controls.find(':checkbox,:radio').length > 1)
                                controls.append(error);
                            else
                                error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                        } else if (element.is('.select2')) {
                            error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                        } else if (element.is('.chosen-select')) {
                            error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                        } else
                            error.insertAfter(element.parent());
                    },
                    submitHandler : function(form) {
                        var resourceId = $("#resourceId").val();
                        var url = "";
                        if (resourceId != undefined) {
                            url = '/resource/edit.html';
                        } else {
                            url = '/resource/add.html';
                        }
                        webside.common.commit('resourceForm', url, '/resource/listUI.html');
                    }
                });
            },
            authorize : {
                ids : [],
                initResourceTree : function() {
                    $('#tree').jstree({
                        "core" : {
                            'data' : {
                                "url" : sys.rootPath + "/resource/resourceTree.html?roleId=" + $("#id").val(),
                                "dataType" : "json"
                            },
                            "themes" : {
                                "responsive" : true
                            },
                            "multiple" : true,
                            "animation" : 200,
                            "dblclick_toggle" : true,
                            "expand_selected_onload" : true
                        },
                        "checkbox" : {
                            "keep_selected_style" : true,
                            "three_state" : false,
                            "cascade" : "up"
                        },
                        "plugins" : ["checkbox"]
                    });
                },
                /**
                 * 递归遍历节点，获取该节点的所有父节点
                 * @param {Object} treeObj  jstree对象
                 * @param {Object} nodeObj  jstree node节点对象
                 */
                getParents : function(treeObj, nodeObj) {
                    var parentId = treeObj.get_parent(nodeObj);
                    if (parentId != "#") {
                        webside.form.resource.authorize.ids.push(parentId);
                        webside.form.resource.authorize.getParents(treeObj, treeObj.get_node(parentId));
                    }
                },
                /**
                 * 提交表单
                 * 适用场景：form表单的提交，角色授权提交
                 * @param {Object} commitUrl 表单提交地址
                 * @param {Object} listUrl 表单提交成功后转向的列表页地址
                 */
                commitPerm : function(commitUrl, listUrl) {
                    webside.form.resource.authorize.ids = [];
                    var tree = $('#tree').jstree();
                    //获取所有选中节点id
                    var selectedIds = tree.get_checked();
                    //获取所有选中节点
                    var selected = tree.get_checked(true);
                    //遍历节点，获取选中节点的所有父节点
                    for (var i = 0; i < selected.length; i++) {
                        webside.form.resource.authorize.getParents(tree, selected[i]);
                    }
                    var index;
                    $.ajax({
                        type : "POST",
                        url : sys.rootPath + commitUrl,
                        data : {
                            "roleId" : $("#id").val(),
                            "resourceIds" : _.union(webside.form.resource.authorize.ids, selectedIds).join(',')
                        },
                        dataType : "json",
                        beforeSend : function() {
                            index = layer.load();
                        },
                        success : function(resultdata) {
                            layer.close(index);
                            if (resultdata.success) {
                                layer.msg(resultdata.message, {
                                    icon : 1
                                });
                                webside.common.loadPage(listUrl + '?page=' + $("#pageNum").val() + '&rows=' + $("#pageSize").val() + '&sidx=' + $("#orderByColumn").val() + '&sord=' + $("#orderByType").val());
                            } else {
                                layer.msg(resultdata.message, {
                                    icon : 5
                                });
                            }
                        },
                        error : function(errorMsg) {
                            layer.close(index);
                            layer.msg('服务器未响应,请稍后再试', {
                                icon : 2
                            });
                        }
                    });
                }
            }
        },
        schedule: {
            initJobGroup:function(){
                $('#jobGroupSelect').select2({
                    language : "zh-CN",
                    minimumResultsForSearch : Infinity,
                    placeholder : "请选择任务组..."
                }).on('change',function(){
                    $("#jobGroup").val($(this).find("option:selected").text());
                    $("#jobClassName").val($(this).val());
                });
            },
            initStartDate:function(){
                $("#startDate").datepicker({
                    format : 'yyyy-mm-dd',
                    autoclose : true,
                    language : 'zh-CN',
                    todayHighlight : true,
                    clearBtn : true,
                    immediateUpdates : true,
                    clearDate : function() {
                        $("#startDate").val('').datepicker('update');
                    }
                }).on('changeDate',function(e){
                    var startDate = e.date;
                    $('#endDate').datepicker('setStartDate',startDate);  
                });
            },
            initEndDate:function(){
                $("#endDate").datepicker({
                    format : 'yyyy-mm-dd',
                    autoclose : true,
                    language : 'zh-CN',
                    todayHighlight : true,
                    clearBtn : true,
                    immediateUpdates : true,
                    clearDate : function() {
                        $("#endDate").val('').datepicker('update');
                    }
                }).on('changeDate',function(e){
                    var EndDate = e.date;
                    $('#startDate').datepicker('setEndDate',EndDate);  
                });
            },
            initTrigger : function(){
                $("#triggerDiv").fadeOut(1);
                $("#isAddTrigger").bootstrapSwitch({
                    state : false,
                    onColor : 'success',
                    offColor : 'warning',
                    onText : '是',
                    offText : '否',
                    labelText : '是否添加trigger',
                    labelWidth : 100,
                    onSwitchChange : function(event, state){
                        if(state)
                        {
                            $("#triggerDiv").fadeIn();
                        }else
                        {
                            $("#triggerDiv").fadeOut();
                        }
                    }
                });
            },
            validateJobForm : function(){
                $('#jobForm').validate({
                    errorElement : 'div',
                    errorClass : 'help-block',
                    focusInvalid : false,
                    ignore : "",
                    rules : {
                        jobName :{
                           required : true 
                        },
                        jobGroupSelect : {
                           required : true 
                        },
                        triggerName : {
                            required : function() {
                                 return $("#isAddTrigger").prop("checked");
                            }
                        },
                        triggerGroup: {
                            required : function() {
                                 return false;//$("#isAddTrigger").prop("checked");
                            }
                        },
                        cronExpression : {
                            required : function() {
                                 return $("#isAddTrigger").prop("checked");
                            },
                            remote : {
                                param : {
                                    url : sys.rootPath + '/scheduleJob/withoutAuth/validateCron.html',
                                    cache : false
                                },
                                depends : function() {
                                    return $("#isAddTrigger").prop("checked");
                                }
                            }
                        },
                        startDate : {
                            required : function() {
                                 return $("#isAddTrigger").prop("checked");
                            }
                        },
                        endDate : {
                            required : function() {
                                 return $("#isAddTrigger").prop("checked");
                            }
                        }
                    },
                    messages : {
                        jobName : "请填写任务名称",
                        jobGroupSelect : "请选择任务组",
                        triggerName: "请填写trigger名称",
                        triggerGroup: "请填写trigger组",
                        cronExpression : {
                            required : "请填写任务触发表达式",
                            remote : "表达式不正确"
                        },
                        startDate : "请选择任务开始日期",
                        endDate : "请选择任务结束日期"
                    },
                    highlight : function(e) {
                        $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
                    },
                    success : function(e) {
                        $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
                        $(e).remove();
                    },
                    errorPlacement : function(error, element) {
                        if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                            var controls = element.closest('div[class*="col-"]');
                            if (controls.find(':checkbox,:radio').length > 1)
                                controls.append(error);
                            else
                                error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                        } else if (element.is('.select2')) {
                            error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                        } else if (element.is('.chosen-select')) {
                            error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                        } else
                            error.insertAfter(element.parent());
                    },
                    submitHandler : function(form) {
                        webside.common.commit('jobForm', '/scheduleJob/addJob.html', '/scheduleJob/planningJobListUI.html');
                    }
                });
            },
            validateTriggerForm:function(){
                $('#triggerForm').validate({
                    errorElement : 'div',
                    errorClass : 'help-block',
                    focusInvalid : false,
                    ignore : "",
                    rules : {
                        triggerName : {
                            required : true
                        },
                        triggerGroup:{
                            required : false
                        },
                        cronExpression : {
                            required : true,
                            remote : {
                                param : {
                                    url : sys.rootPath + '/scheduleJob/withoutAuth/validateCron.html',
                                    cache : false
                                }
                            }
                        },
                        startDate : {
                            required : true
                        },
                        endDate : {
                            required : true
                        }
                    },
                    messages : {
                        triggerName: "请填写trigger名称",
                        triggerGroup: "请填写trigger组",
                        cronExpression : {
                            required : "请填写任务触发表达式",
                            remote : "表达式不正确"
                        },
                        startDate : "请选择任务开始日期",
                        endDate : "请选择任务结束日期"
                    },
                    highlight : function(e) {
                        $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
                    },
                    success : function(e) {
                        $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
                        $(e).remove();
                    },
                    errorPlacement : function(error, element) {
                        if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                            var controls = element.closest('div[class*="col-"]');
                            if (controls.find(':checkbox,:radio').length > 1)
                                controls.append(error);
                            else
                                error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                        } else if (element.is('.select2')) {
                            error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                        } else if (element.is('.chosen-select')) {
                            error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                        } else
                            error.insertAfter(element.parent());
                    },
                    submitHandler : function(form) {
                        var jobTriggerHide = $("#jobTriggerHide").val();
                        var jobName = $("#jobName").val();
                        var jobGroup = $("#jobGroup").val();
                        var url = "";
                        if (jobTriggerHide != undefined) {
                            url = '/scheduleJob/editTrigger.html';
                        } else {
                            url = '/scheduleJob/addTrigger.html';
                        }
                        webside.common.commit('triggerForm', url, '/scheduleJob/jobTriggerListUI.html?jobName='+jobName+'&jobGroup='+jobGroup);
                    }
                });
            },
            editModel : function(nav) {
                //获取选择的行
                var rows = grid.getCheckedRecords();
                if (rows.length == 1) {
                    webside.common.loadPage(nav + '?jobName=' + rows[0].jobName+'&jobGroup='+rows[0].jobGroup+'&triggerName='+rows[0].triggerName+'&triggerGroup='+rows[0].triggerGroup);
                } else {
                    layer.msg("你没有选择行或选择了多行数据", {
                        icon : 0
                    });
                }
            },
            delModel:function(nav,callback){
                var index;
                var rows = grid.getCheckedRecords();
                var jobName = $("#jobName").val();
                var param = {};
                if(undefined ==jobName)
                {
                    //执行job
                    param.jobName = rows[0].jobName;
                    param.jobGroup = rows[0].jobGroup;
                }else
                {
                    //执行trigger
                    param.triggerName = rows[0].triggerName;
                    param.triggerGroup = rows[0].triggerGroup;
                }
                if (rows.length == 1) {
                    layer.confirm('确认删除吗？', {
                    icon : 3,
                    title : '删除提示'
                }, function(index, layero) {
                    $.ajax({
                        type : "POST",
                        url : sys.rootPath + nav,
                        data : param,
                        dataType : "json",
                        success : function(resultdata) {
                            if (resultdata.success) {
                                layer.msg(resultdata.message, {
                                    icon : 1
                                });
                                if (callback) {
                                    callback();
                                }
                            } else {
                                layer.msg(resultdata.message, {
                                    icon : 5
                                });
                            }
                        },
                        error : function(errorMsg) {
                            layer.msg('服务器未响应,请稍后再试', {
                                icon : 3
                            });
                        }
                    });
                    layer.close(index);
                });
                } else {
                    layer.msg("你没有选择行或选择了多行数据", {
                        icon : 0
                    });
                }
            },
            executeJob:function(nav, callback){
                var rows = grid.getCheckedRecords();
                var jobName = $("#jobName").val();
                var param = {};
                if(undefined ==jobName)
                {
                    //执行job
                    param.jobName = rows[0].jobName;
                    param.jobGroup = rows[0].jobGroup;
                }else
                {
                    //执行trigger
                    param.triggerName = rows[0].triggerName;
                    param.triggerGroup = rows[0].triggerGroup;
                }
                var index;
                if (rows.length == 1) {
                    $.ajax({
                        type : "POST",
                        url : sys.rootPath + nav,
                        beforeSend : function() {
                            index = layer.load();
                        },
                        data : param,
                        dataType : "json",
                        success : function(resultdata) {
                            layer.close(index);
                            if (resultdata.success) {
                                layer.msg(resultdata.message, {
                                    icon : 1
                                });
                                if (callback) {
                                    callback();
                                }
                            } else {
                                layer.msg(resultdata.message, {
                                    icon : 5
                                });
                            }
                        },
                        error : function(errorMsg) {
                            layer.msg('服务器未响应,请稍后再试', {
                                icon : 3
                            });
                        }
                    });
                }else {
                    layer.msg("你没有选择任务或选择了多个任务", {
                        icon : 0
                    });
                }
            },
            getTrigger:function(jobName, jobGroup)
            {
                webside.common.loadPage('/scheduleJob/jobTriggerListUI.html?jobName='+jobName+'&jobGroup='+jobGroup);
            }
        }
    },
    wodota : {//自定义延伸出来的
        /**
         *加载非菜单展示页面
         * @param nav 待加载的资源URL
         */
        loadPage : function(nav) {
            //加载页面
            $(".page-content").load(sys.rootPath + nav);
        },
        /**
         * 新增
         * @param {Object} nav  提交url
         */
        addModel : function(nav) {
            //加载新增页面
            webside.common.loadPage(nav);
        },
        /**
         * 编辑，需要传入当前列表的grid的id值
         * @param {Object} nav  提交url
         */
        editModel : function(nav,grid) {
            //当前页码
            var nowPage = grid.pager.nowPage;
            //获取每页显示的记录数(即: select框中的10,20,30)
            var pageSize = grid.pager.pageSize;
            //获取排序字段
            var columnId = grid.sortParameter.columnId;
            //获取排序方式 [0-不排序，1-正序，2-倒序]
            var sortType = grid.sortParameter.sortType;
            //获取选择的行
            var rows = grid.getCheckedRecords();
            if (rows.length == 1) {
                webside.common.loadPage(nav + '?id=' + rows[0].id + "&page=" + nowPage + "&rows=" + pageSize + "&sidx=" + columnId + "&sord=" + sortType);
            } else {
                layer.msg("你没有选择行或选择了多行数据", {
                    icon : 0
                });
            }
        },
        /**
         * 编辑(行内默认grid)
         * @param {Object} nav  提交url
         */
        editModelByOperation : function(nav,id,gridId) {
        	if(gridId != undefined && gridId != null){
        		grid = gridId;
        	}
            //当前页码
            var nowPage = grid.pager.nowPage;
            //获取每页显示的记录数(即: select框中的10,20,30)
            var pageSize = grid.pager.pageSize;
            //获取排序字段
            var columnId = grid.sortParameter.columnId;
            //获取排序方式 [0-不排序，1-正序，2-倒序]
            var sortType = grid.sortParameter.sortType;
            webside.common.loadPage(nav + '?id=' + id + "&page=" + nowPage + "&rows=" + pageSize + "&sidx=" + columnId + "&sord=" + sortType);
        },
        /**
         * 删除
         * @param {Object} nav  提交url
         * @param callback 主函数执行完毕后调用的回调函数名称
         */
        delModel : function(nav, callback,grid) {
            var rows = grid.getCheckedRecords();
            if (rows.length == 1) {
                if (nav == '/user/deleteBatch.html') {
                    if (rows[0].role.name == '超级管理员') {
                        layer.msg('该用户为超级管理员,不能删除!', {
                            icon : 0
                        });
                        return false;
                    }
                }
                if (nav == '/role/deleteBatch.html') {
                    if (rows[0].name == '超级管理员') {
                        layer.msg('该角色为基础角色,不能删除!', {
                            icon : 0
                        });
                        return false;
                    }
                }
                layer.confirm('确认删除吗？', {
                    icon : 3,
                    title : '删除提示'
                }, function(index, layero) {
                    var delete_ids = [];
                    /*
                     $.each(rows, function(index, value) {
                     ids.push(this.id);
                     });
                     */
                    delete_ids.push(rows[0].id);
                    $.ajax({
                        type : "POST",
                        url : sys.rootPath + nav,
                        data : {
                            "ids" : delete_ids.join(',')
                        },
                        dataType : "json",
                        success : function(resultdata) {
                            if (resultdata.success) {
                                layer.msg(resultdata.message, {
                                    icon : 1
                                });
                                if (callback) {
                                    callback();
                                }
                            } else {
                                layer.msg(resultdata.message, {
                                    icon : 5
                                });
                            }
                        },
                        error : function(errorMsg) {
                            layer.msg('服务器未响应,请稍后再试', {
                                icon : 3
                            });
                        }
                    });
                    layer.close(index);
                });
            } else {
                layer.msg("你没有选择行或选择了多行数据", {
                    icon : 0
                });
            }
        },
        /**
         * 删除(行内)
         * @param {Object} nav  提交url
         * @param callback 主函数执行完毕后调用的回调函数名称
         */
        delModelByOperation : function(nav, callback,id) {
            layer.confirm('确认删除吗？', {
                icon : 3,
                title : '删除提示'
            }, function(index, layero) {
                $.ajax({
                    type : "POST",
                    url : sys.rootPath + nav,
                    data : {
                        "id" : id
                    },
                    dataType : "json",
                    success : function(resultdata) {
                        if (resultdata.success) {
                            layer.msg(resultdata.message, {
                                icon : 1
                            });
                            if (callback) {
                                callback();
                            }
                        } else {
                            layer.msg(resultdata.message, {
                                icon : 5
                            });
                        }
                    },
                    error : function(errorMsg) {
                        layer.msg('服务器未响应,请稍后再试', {
                            icon : 3
                        });
                    }
                });
                layer.close(index);
            });
        },
        /**
         * 提交表单
         * 适用场景：form表单的提交，主要用在用户、角色、资源等表单的添加、修改等
         * @param {Object} commitUrl 表单提交地址
         * @param {Object} listUrl 表单提交成功后转向的列表页地址
         */
        commit : function(formId, commitUrl, jumpUrl) {
            //组装表单数据
            var index;
            var data = $("#" + formId).serialize();
            $.ajax({
                type : "POST",
                url : sys.rootPath + commitUrl,
                data : data,
                dataType : "json",
                beforeSend : function() {
                    index = layer.load();
                },
                success : function(resultdata) {
                    layer.close(index);
                    if (resultdata.success) {
                        layer.msg(resultdata.message, {
                            icon : 1
                        });
                        webside.common.loadPage(jumpUrl+"&id="+resultdata.data);
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
        },
        /**
         * 提交表单,不跳转loadPage，而是调用查询方法，仅限弹框编辑模式使用
         * 适用场景：form表单的提交，主要用在用户、角色、资源等表单的添加、修改等
         * @param {Object} commitUrl 表单提交地址
         * @param {Object} listUrl 表单提交成功后转向的列表页地址
         */
        commitByModel : function(formId, commitUrl, searchFunction) {
            //组装表单数据
            var index;
            var data = $("#" + formId).serialize();
            $.ajax({
                type : "POST",
                url : sys.rootPath + commitUrl,
                data : data,
                dataType : "json",
                beforeSend : function() {
                    index = layer.load();
                },
                success : function(resultdata) {
                    layer.close(index);
                    if (resultdata.success) {
                        layer.msg(resultdata.message, {
                            icon : 1
                        });
                        if(searchFunction){
                        	searchFunction();
                        }
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
    }
};
