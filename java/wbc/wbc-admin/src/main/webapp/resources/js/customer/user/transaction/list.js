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
	    id : 'userNick',
	    title : '用户昵称',
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
	    id : 'utType',
	    title : '交易类型',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
//	    	 1:竞猜 2：充值 3:兑换 4:推荐好友奖励 5：用户签到 6：充值奖励
	    	if (value != null && value != '') {
	    		if (value == '1') {
	    			return "竞猜";
	    		} else if (value == '2') {
	    			return "充值";
	    		} else if (value == '3') {
	    			return "兑换";
	    		} else if (value == '4') {
	    			return "推荐好友奖励";
	    		} else if (value == '5') {
	    			return "签到奖励";
	    		} else if (value == '6') {
	    			return  "充值奖励";
	    		} else if (value == '7') {
	    			return  "注册赠送G币";
	    		} else if (value == '8') {
	    			return  "竞猜分享领取";
	    		} else if (value == '9') {
	    			return  "提现";
	    		} else if (value == '10') {
	    			return  "CD-KEY兑换";
	    		} else if (value == '11') {
	    			return  "任务奖励";
	    		} else if (value == '12') {
	    			return  "ROLL奖品";
	    		} else if (value == '13') {
	    			return  "翻硬币";
	    		} else {
	    			return  "未知";
	    		}
	    		
	    	} else {
	    		return '';
	    	}
	    	
	    }
	},
	/*{
	    id : 'dataId',
	    title : '业务ID',
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
	    id : 'goldType',
	    title : '货币类型',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
		    if(record.goldType!=null && record.goldTypeName!=null){
	        	return '<span class="'+record.goldTypeClass+'">'+record.goldTypeName+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'goldNum',
	    title : '数量',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'utTime',
	    title : '交易时间',
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
	    id : 'remarks',
	    title : '备注(信息)',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
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
	    	content+= '<shiro:hasPermission name="userTransactionLog:edit"><button type="button" onclick="webside.wodota.editModelByOperation(\'/userTransactionLog/editUI.html\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button></shiro:hasPermission>';
	    	content+= '&nbsp;&nbsp;';
	    	content+= '<shiro:hasPermission name="userTransactionLog:deleteBatch"><button type="button" onclick="webside.wodota.delModelByOperation(\'/userTransactionLog/deleteBatch.html\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button></shiro:hasPermission>';
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
    loadURL : sys.rootPath + '/userTransactionLog/list',
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
    grid.parameters['utType'] = $("#tranType").val();
    grid.parameters['beginUtTime'] = $("#beginUtTime").val();
    grid.parameters['endUtTime'] = $("#endUtTime").val();
    grid.refresh(true);
}
