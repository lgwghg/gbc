var dtGridColumns = [
	{
	    id : 'title',
	    title : '标题',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value != null && value.length > 10){
	        	return '<span title="'+value+'">'+value.substring(0,10)+'...</span>';
	        }else{
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
		id : 'code',
		title : '编码',
		type : 'string',
		columnClass : 'text-center',
		headerClass : 'dlshouwen-grid-header',
		resolution : function(value, record, column, grid, dataNo, columnNo) {
			if(value != null && value.length > 10){
				return '<span title="'+value+'">'+value.substring(0,10)+'...</span>';
			}else{
				return '<span title="'+value+'">'+value+'</span>';
			}
		}
	},
	{
	    id : 'content',
	    title : '通知内容',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value != null && value.length > 20){
	        	return '<span title="'+value+'">'+value.substring(0,20)+'...</span>';
	        }else{
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'type',
	    title : '公告类型',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var typeName = "";
	    	if("1" == value) {
	    		typeName = "网站公告";
	    	}else if("2" == value) {
	    		typeName = "网站更新";
	    	} else if("3" == value) {
	    		typeName = "网站介绍";
	    	} else if("4" == value) {
	    		typeName = "使用帮助";
	    	} else if("5" == value) {
	    		typeName = "用户须知";
	    	} else if("6" == value) {
	    		typeName = "用户反馈";
	    	} else {
	    		typeName = "未知";
	    	}
	    	return '<span title="'+typeName+'">'+typeName+'</span>';
	    }
	},
	{
	    id : 'sequence',
	    title : '排序',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'status',
	    title : '状态 ',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var statusName = "";
	    	if("0" == value){
	    		statusName = "无效";
	    	} else if("1" == value){
	    		statusName = "有效";
	        } else if("2" == value){
	    		statusName = "注册";
	        } else if("3" == value){
	    		statusName = "底部";
	        } else {
	        	statusName = "未知";
	        }
	    	return '<span title="'+statusName+'">'+statusName+'</span>';
	    }
	},
	{
	    id : 'addTime',
	    title : '创建时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'sysUserName',
	    title : '创建人员',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'operation',
	    title : '操作',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var content = '';
	    	if(record.edit){
	    		content+= '<button type="button" onclick="webside.wodota.editModelByOperation(\'/system/webNotice/editUI.html\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
		    	content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.del){
	    		content+= '<button type="button" onclick="webside.wodota.delModelByOperation(\'/system/webNotice/delete.html\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button>';
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
    loadURL : sys.rootPath + '/system/webNotice/list.html',
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
    grid.parameters['title'] = $("#title").val();
    grid.parameters['code'] = $("#code").val();
    grid.parameters['content'] = $("#content").val();
    grid.parameters['type'] = $("#type").val();
    grid.parameters['status'] = $("#status").val();
    grid.refresh(true);
}
