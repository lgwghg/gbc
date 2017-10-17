var userIncrementDtGridColumns = [{
    id : 'id',
    title : '唯一标识',
    type : 'string',
    hide : true,
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'mobile',
    title : '手机号',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'nickName',
    title : '昵称',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'email',
    title : '邮箱',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'jcNum',
    title : '竞猜量',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'victoryNum',
    title : '胜利量',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'victoryRate',
    title : '胜利率',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'totalProfitGoldNum',
    title : '累计盈利金币数',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'unreadNum',
    title : '消息未读量',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'dateUpdateTime',
    title : '更新时间',
    type : 'date',
    format : 'yyyy-MM-dd hh:mm:ss',
    otype : 'string',
    oformat : 'yyyy-MM-dd hh:mm:ss',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    hideType : 'sm|xs|md',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
        if (value == null) {
            return '';
        } else {
            return value;
        }
    }
}];

var dtGridColumns = [{
    id : 'id',
    title : '唯一标识',
    type : 'string',
    hide : true,
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'nickName',
    title : '昵称',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'mobile',
    title : '手机号',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'email',
    title : '邮箱',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'rankLevel',
    title : '头衔级别',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, /*{
    id : 'photo',
    title : '头像',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'backgroundImg',
    title : '背景图',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},*/ {
    id : 'sign',
    title : '签名',
    type : 'string',
    width : 'auto',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'roleName',
    title : '所属角色',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    hideType : 'xs',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
        if (/*typeof(value) == "undefined" ||*/ "" == value || null == value) {
            return '';
        } else {
            return value;
        }
    }
}, {
    id : 'isDeleted',
    title : '是否删除',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    hideType : 'sm|xs',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
        if (value == 0) {
            return '<span class="label label-sm label-success arrowed arrowed-righ">正常</span>';
        } else {
            return '<span class="label label-sm label-warning arrowed arrowed-righ">已删除</span>';
        }
    }
}, {
    id : 'locked',
    title : '是否锁定',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    hideType : 'sm|xs|md|lg',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
        if (value == 0) {
            return '<span class="label label-sm label-success arrowed arrowed-righ">正常</span>';
        } else {
            return '<span class="label label-sm label-warning arrowed arrowed-righ">已删除</span>';
        }
    }
}, {
    id : 'creatorName',
    title : '创建者',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    hideType : 'sm|xs|md|lg'
}, {
    id : 'updatorName',
    title : '更新者',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    hideType : 'sm|xs|md|lg'
}, {
    id : 'dateCreateTime',
    title : '创建时间',
    type : 'date',
    format : 'yyyy-MM-dd hh:mm:ss',
    otype : 'string',
    oformat : 'yyyy-MM-dd hh:mm:ss',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    hideType : 'sm|xs|md|lg'
}, {
    id : 'dateUpdateTime',
    title : '更新时间',
    type : 'date',
    format : 'yyyy-MM-dd hh:mm:ss',
    otype : 'string',
    oformat : 'yyyy-MM-dd hh:mm:ss',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    hideType : 'sm|xs|md|lg',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
        if (value == null) {
            return '';
        } else {
            return value;
        }
    }
}];

//动态设置jqGrid的rowNum
var pageSize = $("#pageSize").val();
pageSize = pageSize == 0 || pageSize == "" ? sys.pageNum : pageSize;

var userIncrementDtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/user/incrementList.html',
    columns : userIncrementDtGridColumns,
    gridContainer : 'dtGridContainer_increment',
    toolbarContainer : 'dtGridToolBarContainer_increment',
    /*tools : 'refresh|print|export[excel,csv,pdf,txt]',*/
    tools : 'refresh|print|export[excel]',
    exportFileName : '用户增量信息',
    pageSize : pageSize,
    pageSizeLimit : [10, 20, 30]
};

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/user/list.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh|print|export[excel]',
    exportFileName : '用户信息',
    pageSize : pageSize,
    pageSizeLimit : [10, 20, 30]
};
var userIncrementGrid = $.fn.dlshouwen.grid.init(userIncrementDtGridOption);
var grid = $.fn.dlshouwen.grid.init(dtGridOption);
$(function() {
	// 用户信息列表
    if(null != $("#orderByColumn").val() && '' != $("#orderByColumn").val())
    {
        grid.sortParameter.columnId = $("#orderByColumn").val();
        grid.sortParameter.sortType = $("#orderByType").val();
    }
    grid.load();
    $("#btnSearch").click(customSearch);
    
    // 用户增量信息列表
    if(null != $("#orderByColumn_increment").val() && '' != $("#orderByColumn_increment").val())
    {
    	userIncrementGrid.sortParameter.columnId = $("#orderByColumn_increment").val();
    	userIncrementGrid.sortParameter.sortType = $("#orderByType_increment").val();
    }
    userIncrementGrid.load();
    $("#userIncrementBtnSearch").click(userIncrementSearch);
    /*$("#userInfo").click(function() {
    	$("#dtGridContainer").html("");
    	$("#dtGridToolBarContainer").html("");
    	grid = $.fn.dlshouwen.grid.init(dtGridOption);
    	grid.load();
    });
    $("#userIncrement").click(function() {
    	$("#dtGridContainer").html("");
    	$("#dtGridToolBarContainer").html("");
    	userIncrementGrid = $.fn.dlshouwen.grid.init(userIncrementDtGridOption);
    	userIncrementGrid.load();
    });*/
    //注册回车键事件
   /* document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            customSearch();
        }
    };*/
    
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch() {
    grid.parameters = new Object();
    grid.parameters['nickName'] = $("#userNick").val();
    grid.parameters['beginCreateTime'] = $("#beginCreateTime").val();
    grid.parameters['endCreateTime'] = $("#endCreateTime").val();
    grid.parameters['beginUpdateTime'] = $("#beginUpdateTime").val();
    grid.parameters['endCreateTime'] = $("#endCreateTime").val();
    grid.refresh(true);
}
/**
 * 用户增量信息查询
 */
function userIncrementSearch() {
	userIncrementGrid.parameters = new Object();
	userIncrementGrid.parameters['nickName'] = $("#searchKeyUserIncrement").val();
	userIncrementGrid.parameters['beginUpdateTime'] = $("#beginUpdateTimeInc").val();
	userIncrementGrid.parameters['endCreateTime'] = $("#endCreateTimeInc").val();
	userIncrementGrid.refresh(true);
	
}
/**
 *重置密码
 */
function resetPWDModel() {
    var rows = grid.getCheckedRecords();
    if (rows.length == 1) {
        var index;
        $.ajax({
            type : "POST",
            url : sys.rootPath + '/user/resetPassword/',
            data : {
                "id" : rows[0].id,
                "accountName" : rows[0].accountName,
                "userName" : rows[0].userName
            },
            dataType : "json",
            beforeSend : function()
            {
                index = layer.load();
            },
            success : function(resultdata) {
                layer.close(index);
                if (resultdata.success) {
                    layer.msg(resultdata.message, {
                        icon : 1
                    });
                } else {
                    layer.msg(resultdata.message, {
                        icon : 5
                    });
                }
            },
            error : function(data, errorMsg) {
                layer.close(index);
                layer.msg(data.responseText, {icon : 2});
            }
        });
    } else {
        layer.msg("你没有选择行或选择了多行数据", {
            icon : 0
        });
    }
}

