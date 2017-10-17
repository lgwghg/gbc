var dtGridColumns = [
	{
	    id : 'goodsName',
	    title : '所属商品',
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
	    id : 'cardNo',
	    title : '卡号',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'cardPass',
	    title : '卡密',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'goodsNo',
	    title : '编号',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'effectiveTime',
	    title : '有效期',
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
	    id : 'status',
	    title : '状态 ',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if("1" == value) {
	    		value = "未兑换";
	    	} else if("2" == value) {
	    		value = "已兑换";
	    	} else if("3" == value) {
	    		value = "已使用";
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
	    		content+= '<button type="button" onclick="webside.wodota.editModelByOperation(\'/goodsStock/editUI.html\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
	    		content+= '&nbsp;&nbsp;';
	    	}
	    	if(record.del) {
	    		content+= '<button type="button" onclick="webside.wodota.delModelByOperation(\'/goodsStock/delete.html\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button>';
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
    loadURL : sys.rootPath + '/goodsStock/list.html',
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
    if(null != $("#orderByColumn").val() && '' != $("#orderByColumn").val()) {
        grid.sortParameter.columnId = $("#orderByColumn").val();
        grid.sortParameter.sortType = $("#orderByType").val();
    }
    $("#btnSearch").click(customSearch);
    customSearch();
    
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
    grid.parameters['cardNo'] = $("#cardNo").val();
    grid.parameters['goodsNo'] = $("#goodsNo").val();
    grid.parameters['status'] = $("#status").val();
    grid.refresh(true);
}
