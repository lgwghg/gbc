var dtGridColumns = [
	{
	    id : 'goodsName',
	    title : '商品名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value != null && value.length > 20) {
	        	return '<span title="'+value+'">'+value.substring(0, 20)+'...</span>';
	        } else {
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'goodsImg',
	    title : '商品图片',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value != "" && value != undefined) {
	    		return '<img src="'+value+'" width="60" height="60"/>';
	        }else{
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'goodsGold',
	    title : '商品金币',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'goodsNum',
	    title : '商品数量',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'desc',
	    title : '商品描述',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value != null && value.length > 20) {
	        	return '<span title="'+value+'">'+value.substring(0, 20)+'...</span>';
	        } else {
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'type',
	    title : '类型',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if("1" == value) {
	    		value = "实物";
	    	} else if("2" == value) {
	    		value = "虚拟物品";
	    	} else {
	    		value = "未知";
	    	}
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'addUserName',
	    title : '创建人',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'createTime',
	    title : '创建时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'updateUserName',
	    title : '修改人',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'updateTime',
	    title : '修改时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	        if (value == null || value == '') {
	            return '';
	        } else {
	            return getSmpFormatDateByLong(parseInt(value),true);
	        }
	    }
	},
	{
	    id : 'isMax',
	    title : '无限库存',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if("1" == value) {
	    		value = "是";
	    	} else {
	    		value = "否";
	    	}
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
		id : 'status',
		title : '状态',
		type : 'string',
		columnClass : 'text-center',
		headerClass : 'dlshouwen-grid-header',
		resolution : function(value, record, column, grid, dataNo, columnNo) {
			if("1" == value) {
				value = "有效";
			} else if("0" == value) {
				value = "无效";
			} else {
				value = "未知";
			}
			return '<span title="'+value+'">'+value+'</span>';
		}
	},
	{
	    id : 'operation',
	    title : '操作',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var content = '';
	    	if(record.edit) {
	    		content+= '<button type="button" onclick="webside.wodota.editModelByOperation(\'/goods/editUI.html\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.del) {
	    		content+= '<button type="button" onclick="soldOut(\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;下架</button>';
	    	}
	    	return content;
	    }
	}
];

//动态设置jqGrid的rowNum
var pageSize = $("#pageSize").val();
pageSize = pageSize == 0 || pageSize == "" ? sys.pageNum : pageSize;

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/goods/list.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh|print|export[excel,csv,pdf,txt]',
    exportFileName : '信息',
    pageSize : pageSize,
    pageSizeLimit : [10, 20, 30]
};

var grid = $.fn.dlshouwen.grid.init(dtGridOption);
$(function() {
    if(null != $("#orderByColumn").val() && '' != $("#orderByColumn").val())
    {
        grid.sortParameter.columnId = $("#orderByColumn").val();
        grid.sortParameter.sortType = $("#orderByType").val();
    }
    grid.load();
    $("#btnSearch").click(customSearch);
    
    //注册回车键事件
    document.onkeypress = function(e){
    var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            customSearch();
        }
    };
    
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch() {
    grid.parameters = new Object();
    grid.parameters['goodsName'] = $("#goodsName").val();
    grid.parameters['type'] = $("#goodsType").val();
    grid.parameters['status'] = $("#goodsStatus").val();
    grid.refresh(true);
}

function soldOut(id) {
	var url = sys.rootPath + "/goods/edit.html";
    layer.confirm('确认下架吗？', {
        icon : 3,
        title : '下架提示'
    }, function(index, layero) {
        $.ajax({
            type : "POST",
            url : url,
            data : {"id": id, "status":0},
            dataType : "json",
            success : function(resultdata) {
                if (resultdata.success) {
                    layer.msg(resultdata.message, {icon : 1});
                    customSearch();
                } else {
                    layer.msg(resultdata.message, {icon : 5});
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
};
