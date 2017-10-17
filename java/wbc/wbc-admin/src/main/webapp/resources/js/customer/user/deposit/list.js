var dtGridColumns = [
	/*{
	    id : 'id',
	    title : '唯一标识',
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
	},*/
	{
	    id : 'nickName',
	    title : '用户昵称',
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
	    id : 'orderName',
	    title : '订单标题',
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
	},
	{
	    id : 'type',
	    title : '充值平台',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	if (value == 1) {
	        		return "支付宝支付";
	        	} else if (value == 2) {
	        		return "微信支付";
	        	}
	        }
	    }
	},
	{
	    id : 'orderNo',
	    title : '交易订单编号',
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
	    id : 'udGold',
	    title : '充值金额',
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
	    id : 'orderTime',
	    title : '下单时间',
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
	    id : 'payTime',
	    title : '支付时间',
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
	    id : 'isPaySuccess',
	    title : '是否支付成功',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	if (value == 0) {
	        		return "未支付";
	        	} else if (value == 1) {
	        		return "已支付";
	        	}
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
	    	content+= '<shiro:hasPermission name="userDepositLog:edit"><button type="button" onclick="webside.wodota.editModelByOperation(\'/userDepositLog/editUI.html\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button></shiro:hasPermission>';
	    	content+= '&nbsp;&nbsp;';
	    	content+= '<shiro:hasPermission name="userDepositLog:deleteBatch"><button type="button" onclick="webside.wodota.delModelByOperation(\'/userDepositLog/deleteBatch.html\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button></shiro:hasPermission>';
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
    loadURL : sys.rootPath + '/userDepositLog/list.html',
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
    grid.parameters['userNick'] = $("#userNick").val();
    grid.parameters['beginOrderTime'] = $("#beginOrderTime").val();
    grid.parameters['endOrderTime'] = $("#endOrderTime").val();
    grid.refresh(true);
}
