var dtGridColumns = [
	{
	    id : 'id',
	    title : '唯一标识',
	    hide : true,
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
	    id : 'ge.eventName',
	    title : '赛事',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	        resolution : function(value, record, column, grid, dataNo, columnNo) {
	            if(record.gevent!=null && record.gevent.eventName!=null){
	            	return record.gevent.eventName;
	            }else{
	            	return '';
	            }
	    }
	},
	{
	    id : 'game.gameName',
	    title : '游戏ID',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
        resolution : function(value, record, column, grid, dataNo, columnNo) {
            if(record.game!=null && record.game.gameName!=null){
            	return record.game.gameName;
            }else{
            	return '';
            }
    }
	},
	{
	    id : 'gbName',
	    title : '对战名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'gameRule',
	    title : '比赛规则',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	    		if(value=="1"){
	    			return '<span title="BO1">BO1</span>';
	    		}else if(value=="2"){
	    			return '<span title="BO2">BO2</span>';
	    		}else if(value=="3"){
	    			return '<span title="BO3">BO3</span>';
	    		}else if(value=="5"){
	    			return '<span title="BO5">BO5</span>';
	    		}else{
	    			return '<span title="BO7">BO7</span>';
	    		}
	        }
	    }
	},
	{
	    id : 'homeTeam',
	    title : '主场战队',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
        resolution : function(value, record, column, grid, dataNo, columnNo) {
            if(record.ht!=null && record.ht.teamName!=null){
            	return record.ht.teamName;
            }else{
            	return '';
            }
        }
	},
	{
	    id : 'awayTeam',
	    title : '客场战队',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
        resolution : function(value, record, column, grid, dataNo, columnNo) {
            if(record.at!=null && record.at.teamName!=null){
            	return record.at.teamName;
            }else{
            	return '';
            }
        }
	},
	{
	    id : 'startTime',
	    title : '开始时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'gbStatus',
	    title : '状态',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null ){
	    		if(value=='0'){
	    			return '<span title="正常">正常</span>';
	    		}else{
	    			return '<span title="取消">取消</span>';
	    		}
	        }
	    }
	},
	{
	    id : 'gbType',
	    title : '比赛现状',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.gbType!=null && record.gbTypeName!=null){
	        	return '<span class="'+record.gbTypeClass+'">'+record.gbTypeName+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'thisGbProfit',
	    title : '当前比赛平台收益',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'homePrtGold',
	    title : '下注金额(主)',
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
	    id : 'awayPrtGold',
	    title : '下注金额(客)',
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
	    id : 'endTime',
	    title : '结束时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	        if (value == null || value == '') {
	            return '';
	        } else {
	            return getSmpFormatDateByLong(parseInt(value),true);
	        }
	    }
	},
	{
	    id : 'createUserName',
	    title : '新增人员',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg'
	},
	{
	    id : 'createDate',
	    title : '创建时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg'
	},
	{
	    id : 'updateUserName',
	    title : '修改人员',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg'
	},
	{
	    id : 'updateDate',
	    title : '修改时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    hideType : 'sm|xs|md|lg',
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
	    	if(record.edit){
	    		content+= '<button type="button" onclick="webside.wodota.editModelByOperation(\'/gameBattle/editUI\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
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
    loadURL : sys.rootPath + '/gameBattle/list',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh|print|export[excel]',
    exportFileName : '比赛对战信息',
    pageSize : pageSize,
    pageSizeLimit : [10, 20, 30]
};

var grid = $.fn.dlshouwen.grid.init(dtGridOption);
$(function() {
	$('select').select2();
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
    grid.parameters['gameId'] = $("#gameId").val();
    grid.parameters['geId'] = $("#geId").val();
    grid.parameters['teamId'] = $("#teamId").val();
    grid.parameters['gameRule'] = $("#gameRule").val();
    grid.parameters['gbType'] = $("#gbType").val();
    grid.parameters['gbStatus'] = $("#gbStatus").val();
    grid.parameters['beginCreateTime'] = $("#beginCreateTime").val();
    grid.parameters['endCreateTime'] = $("#endCreateTime").val();
    grid.parameters['id'] = $("#id").val();
    grid.refresh(true);
}
