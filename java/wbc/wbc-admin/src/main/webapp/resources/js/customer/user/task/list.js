var dtGridColumns = [
	{
	    id : 'taskName',
	    title : '任务名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'type',
	    title : '类型',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	if (value == 0) {
	        		return "每日任务";
	        	} else if (value == 1) {
	        		return "一次性任务";
	        	}
	        }
	    }
	},
	{
	    id : 'taskType',
	    title : '任务类型',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	    		if (value == 0) {
	    			return "签到";
	    		} else if (value == 1) {
	    			return "竞猜";
	    		} else if (value == 2) {
	    			return "评论";
	    		} else if (value == 3) {
	    			return "好友推荐";
	    		} else if (value == 4) {
	    			return "首次设置个性昵称";
	    		} else if (value == 5) {
	    			return "首次设置头像";
	    		} else if (value == 6) {
	    			return "首次设置邮箱";
	    		} else if (value == 7) {
	    			return "首次设置支付密码";
	    		} else if (value == 8) {
	    			return "首次充值";
	    		} else {
	    			return "未知";
	    		}
	        }
	    }
	},
	{
	    id : 'image',
	    title : '任务图片',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<img src="' +value+ '" width="30px" height="30px"/>';
	        }
	    }
	},
	{
	    id : 'description',
	    title : '任务介绍',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'num',
	    title : '任务需要完成的次数',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }e
	    }
	},
	{
	    id : 'gold',
	    title : '完成任务的奖励金币',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'state',
	    title : '状态',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	if (value==0) {
	        		return "有效";
	        	} else if (value==1) {
	        		return "无效";
	        	}
	        }
	    }
	},
	{
	    id : 'createTime',
	    title : '创建时间',
	    type : 'string',
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
	    id : 'operation',
	    title : '操作',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var content = '';
	    	content+= '<shiro:hasPermission name="task:edit"><button type="button" onclick="webside.wodota.editModelByOperation(\'/task/editUI\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button></shiro:hasPermission>';
	    	content+= '&nbsp;&nbsp;';
	    	content+= '<shiro:hasPermission name="task:deleteBatch"><button type="button" onclick="webside.wodota.delModelByOperation(\'/task/deleteBatch\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button></shiro:hasPermission>';
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
    loadURL : sys.rootPath + '/task/list',
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
    grid.parameters['taskName'] = $("#searchKey").val();
    grid.refresh(true);
}
