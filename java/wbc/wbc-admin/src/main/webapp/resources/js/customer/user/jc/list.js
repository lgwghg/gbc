var dtGridColumns = [
	{
	    id : 'id',
	    title : '竞猜编号',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'user',
	    title : '用户昵称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var userNick = "";
	    	if (value != null) {
	    		userNick = value.nickName;
	    	}
	    	return '<span title="'+userNick+'">'+userNick+'</span>';
	    }
	},
	{
	    id : 'eventName',
	    title : '赛事',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'gameRule',
	    title : '比赛规则',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'startTime',
	    title : '比赛开始时间',
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
	    id : 'endTime',
	    title : '比赛结束时间',
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
	    id : 'gbId',
	    title : '比赛编号',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'pankouId',
	    title : '盘口编号',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'pkName',
	    title : '盘口名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'inningNum',
	    title : '盘口局数',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'jcGold',
	    title : '竞猜G币',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'jcTeamType',
	    title : '竞猜战队',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	if (value == '1') {
	        		return "主战队：" + record.homeTeam;
	        	} else if (value == '2') {
	        		return "客场战队：" + record.awayTeam;
	        	}
	        }
	    }
	},{
	    id : 'gameResult',
	    title : '比赛结果',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	    		if (value=='0') {
	    			return "输";
	    		} else if (value=='1') {
	    			return "赢";
	    		} else if (value=='2') {
	    			return "进行中";
	    		} else if (value=='3') {
	    			return "已取消";
	    		}
	        }
	    }
	},
	{
	    id : 'victoryGoldNum',
	    title : '用户赢得金币数量',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'jcTime',
	    title : '竞猜时间',
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
	}/*,
	{
	    id : 'operation',
	    title : '操作',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var content = '';
	    	content+= '<shiro:hasPermission name="userJc:edit"><button type="button" onclick="webside.wodota.editModelByOperation(\'/userJc/editUI.html\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button></shiro:hasPermission>';
	    	content+= '&nbsp;&nbsp;';
	    	content+= '<shiro:hasPermission name="userJc:deleteBatch"><button type="button" onclick="webside.wodota.delModelByOperation(\'/userJc/deleteBatch.html\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button></shiro:hasPermission>';
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
    loadURL : sys.rootPath + '/userJc/listJc',
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
    grid.parameters['jcId'] = $("#jcId").val();
    grid.parameters['gbId'] = $("#gbId").val();
    grid.parameters['jcTeamType'] = $("#jcTeamType").val();
    grid.parameters['pankouId'] = $("#pankouId").val();
    grid.refresh(true);
}
