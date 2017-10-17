var dtGridColumns_c = [{
    id : 'id',
    title : '编号',
    type : 'string',
    columnClass : 'text-center',
    hide : true,
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'nickName',
    title : '用户',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
        if(record.user!=null && record.user.nickName!=null){
        	return record.user.nickName;
        }else{
        	return '';
        }
    }
},{
    id : 'content',
    title : '评论内容',
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
},{
    id : 'isDeletedName',
    title : '是否删除',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
        if(record.isDeleted!=null && record.isDeleted!=""){
        	 if (record.isDeleted == 0) {
                 return '<span class="label label-sm label-success arrowed arrowed-righ">'+record.isDeletedName+'</span>';
             } else {
                 return '<span class="label label-sm label-warning arrowed arrowed-righ">'+record.isDeletedName+'</span>';
             }
        }else{
        	return '';
        }
    }
}, {
    id : 'createTime',
    title : '发表时间',
    type : 'date',
    format : 'yyyy-MM-dd hh:mm:ss',
    otype : 'time_stamp_ms',
    oformat : 'yyyy-MM-dd hh:mm:ss',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'updateOperatorNickName',
    title : '修改人',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'updateTime',
    title : '更新时间',
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
}, {
    id : 'operation',
    title : '操作',
    type : 'date',
    format : 'yyyy-MM-dd hh:mm:ss',
    otype : 'time_stamp_ms',
    oformat : 'yyyy-MM-dd hh:mm:ss',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(record.isDeleted=="1"){
    		return '';
    	}else{
    		//return '<a href="javascript:deleteComment(\''+record.id+'\');">删除</a>';
    		return '<button type="button" onclick="deleteComment(\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button>';
    	}
    	
    }
}];

//动态设置jqGrid的rowNum
var pageSize_c = $("#pageSize_c").val();
pageSize_c = pageSize_c == 0 || pageSize_c == "" ? sys.pageNum : pageSize_c;

var dtGridOption_c = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/jc/comment/list',
    columns : dtGridColumns_c,
    gridContainer : 'dtGridContainer_c',
    toolbarContainer : 'dtGridToolBarContainer_c',
    tools : 'refresh|print',
    exportFileName : '评论信息',
    pageSize : pageSize_c,
    pageSizeLimit : [10, 20, 30]
};

var grid_c = $.fn.dlshouwen.grid.init(dtGridOption_c);
$(function() {
    if(null != $("#orderByColumn_c").val() && '' != $("#orderByColumn_c").val())
    {
    	grid_c.sortParameter.columnId = $("#orderByColumn_c").val();
    	grid_c.sortParameter.sortType = $("#orderByType_c").val();
    }
    grid_c.parameters = new Object();
    grid_c.parameters['gbId'] = $("#id").val();
    grid_c.load();
    $("#btnSearch_c").click(customSearch_c);
    
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch_c() {
	grid_c.parameters = new Object();
	grid_c.parameters['gbId'] = $("#id").val();
	grid_c.parameters['content'] = $("#content_c").val();
	grid_c.parameters['isDeleted'] = $("#isDeletedValue_c").val();
	grid_c.parameters['nickName'] = $("#nickName_c").val();
	grid_c.refresh(true);
}