var dtGridColumns = [{
    id : 'title',
    title : '标题',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(value!=null && value.length>10){
        	return '<span title="'+value+'">'+value.substring(0,10)+'...</span>';
        }else{
        	return '<span title="'+value+'">'+value+'</span>';
        }
    }
}, {
    id : 'content',
    title : '通知内容',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(value!=null && value.length>10){
        	return '<span title="'+value+'">'+value.substring(0,10)+'...</span>';
        }else{
        	return '<span title="'+value+'">'+value+'</span>';
        }
    }
}, {
    id : 'status',
    title : '状态',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
        if(record.status!=null && record.statusName!=null){
        	return '<span class="'+record.statusClass+'">'+record.statusName+'</span>';
        }else{
        	return '';
        }
    }
}, {
    id : 'createOperatorName',
    title : '创建人',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'createTime',
    title : '创建时间',
    type : 'date',
    format : 'yyyy-MM-dd hh:mm:ss',
    otype : 'time_stamp_ms',
    oformat : 'yyyy-MM-dd hh:mm:ss',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'operation',
    title : '操作',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	var content = '';
    	if(record.edit){
    		content+= '<button type="button" onclick="webside.wodota.editModelByOperation(\'/system/sn/editUI.html\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
    	}
    	return content;
    }
}];

//动态设置jqGrid的rowNum
var pageSize = $("#pageSize").val();
pageSize = pageSize == 0 || pageSize == "" ? sys.pageNum : pageSize;

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/system/sn/list.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh|print|export[excel]',
    exportFileName : '系统通知列表',
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
    grid.parameters['status'] = $("#status").val();
    grid.parameters['beginCreateTime'] = $("#beginCreateTime").val();
    grid.parameters['endCreateTime'] = $("#endCreateTime").val();
    grid.refresh(true);
}

$(function() {
	$('select').select2();
});