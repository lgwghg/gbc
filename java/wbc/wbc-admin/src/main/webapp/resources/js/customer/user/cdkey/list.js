var dtGridColumns = [
	{
	    id : 'type',
	    title : 'cdkey类型',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if (value != null) {
	    		if (value == 0) {
	    			return "商品";
	    		} else if (value == 1) {
	    			return "金币";
	    		} else if (value == 2) {
	    			return "G币";
	    		}
	    	}
	    }
	},
	{
	    id : 'goodsName',
	    title : '商品名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'gold',
	    title : '币值',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'cdkeyCode',
	    title : 'cdkey兑换码',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'state',
	    title : '兑换状态',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	if (value == 0) {
	        		return "未兑换";
	        	} else if (value == 1) {
	        		return "已兑换";
	        	} else if (value == 2) {
	        		return "冻结";
	        	}
	        }
	    }
	},
	{
	    id : 'startTime',
	    title : '开始时间',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+getSmpFormatDateByLong(parseInt(value),true)+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'endTime',
	    title : '结束时间',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+getSmpFormatDateByLong(parseInt(value),true)+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'createTime',
	    title : '生成时间',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+getSmpFormatDateByLong(parseInt(value),true)+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'createUserId',
	    title : '生成人id',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value.length>10){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'exchangeTime',
	    title : '兑换时间',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null && value != ''){
	        	return '<span title="'+value+'">'+getSmpFormatDateByLong(parseInt(value),true)+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'exchangeUserNick',
	    title : '兑换人昵称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '';
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
	    	//content+= '<shiro:hasPermission name="cdKey:edit"><button type="button" onclick="webside.wodota.editModelByOperation(\'/cdkey/editUI\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button></shiro:hasPermission>';
	    	//content+= '&nbsp;&nbsp;';
	    	content+= '<shiro:hasPermission name="cdKey:deleteBatch"><button type="button" onclick="webside.wodota.delModelByOperation(\'/cdkey/deleteBatch\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button></shiro:hasPermission>';
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
    loadURL : sys.rootPath + '/cdkey/list',
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
    grid.parameters['type'] = $("#type").val();
    grid.parameters['beginCreateTime'] = $("#beginCreateTime").val();
    grid.parameters['endCreateTime'] = $("#endCreateTime").val();
    grid.parameters['beginStartTime'] = $("#beginStartTime").val();
    grid.parameters['endStartTime'] = $("#endStartTime").val();
    grid.parameters['beginEndTime'] = $("#beginEndTime").val();
    grid.parameters['endEndTime'] = $("#endEndTime").val();
    grid.parameters['beginExchangeTime'] = $("#beginExchangeTime").val();
    grid.parameters['endExchangeTime'] = $("#endExchangeTime").val();
    grid.parameters['state'] = $("#state").val();
    grid.refresh(true);
}
