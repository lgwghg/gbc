var dtGridColumns = [
	{
	    id : 'configKey',
	    title : '配置键',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '<span title="'+value+'"></span>';
	        }
	    }
	},
	{
	    id : 'configValue',
	    title : '配置值',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '<span title="'+value+'"></span>';
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
	    id : 'createOperatorId',
	    title : '创建人',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null ){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '<span title="'+value+'"></span>';
	        }
	    }
	},
	{
	    id : 'updateTime',
	    title : '修改时间',
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
	    id : 'updateOperatorId',
	    title : '修改人',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '<span title="'+value+'"></span>';
	        }
	    }
	}/*,
	{
	    id : 'operation',
	    title : '操作',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var content = '';
	    	content+= '<shiro:hasPermission name="config:edit"><button type="button" onclick="webside.wodota.editModelByOperation(\'/config/editUI\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button></shiro:hasPermission>';
	    	content+= '&nbsp;&nbsp;';
	    	content+= '<shiro:hasPermission name="config:deleteBatch"><button type="button" onclick="webside.wodota.delModelByOperation(\'/config/deleteBatch\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button></shiro:hasPermission>';
	    	return content;
	    }
	}*/
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
    loadURL : sys.rootPath + '/config/list',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh|export[excel]',
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
    grid.parameters['configKey'] = $("#searchKey").val();
    grid.refresh(true);
}
