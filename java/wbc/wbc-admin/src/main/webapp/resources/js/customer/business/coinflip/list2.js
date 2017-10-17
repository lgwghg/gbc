var dtGridColumns = [
	{
	    id : 'hashCode',
	    title : 'hash值',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	},
	{
	    id : 'roomOwner',
	    title : '房主',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var roomOwner = record.roomOwner;
	    	if(roomOwner!=null){
	        	return '<img src="' + roomOwner.userPhoto + '" width="50px" height="50px"/><span title="'+roomOwner.userNick+'">'+roomOwner.userNick+'</span>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'ownerGoldNum',
	    title : '房主下注金币数',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	return '<span title="'+value+'">'+value+'</span>';
	        }else{
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'roomOwner',
	    title : '房主下注方',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var roomOwner = record.roomOwner;
	    	if(roomOwner!=null){
	    		var isCoinFront = roomOwner.isCoinFront;
	    		var coinfrontImg = "";
	    		if (isCoinFront == 1) {
	    			coinfrontImg = "/resources/images/zheng.png";
	    		} else {
	    			coinfrontImg = "/resources/images/fan.png";
	    		}
	        	return '<img src="' + coinfrontImg + '" width="50px" height="50px"/>';
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'random',
	    title : 'VS赢方',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.status==3){
    			if (value < 0.5) {
	    			return '<img src="/resources/images/zheng.png" width="50px" height="50px"/>';
	    		} else {
	    			return '<img src="/resources/images/fan.png" width="50px" height="50px"/>';
	    		}
    		}else{
    			return '?';
    		}
	    }
	},
	{
	    id : 'roomJoinners',
	    title : '参与者下注方',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var content = '';
	    	var roomOwner = record.roomOwner;
	    	if(roomOwner!=null){
	    		var isCoinFront = roomOwner.isCoinFront;
	    		var coinfrontImg = "";
	    		if (isCoinFront == 0) {
	    			coinfrontImg = "/resources/images/zheng.png";
	    		} else {
	    			coinfrontImg = "/resources/images/fan.png";
	    		}
	    		content = content + '<img src="' + coinfrontImg + '" width="50px" height="50px"/>';
	        	return content;
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'roomJoinners',
	    title : '参与者',
	    type : 'string',
	    width : '500',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var roomJoinners = record.roomJoinners;
	    	var content = '';
	    	if(roomJoinners!=null){
	    		for (var i=0; i< roomJoinners.length; i++) {
	    			var joiner = roomJoinners[i];
	    			content = content + '<img src="' + joiner.userPhoto + '" width="50px" height="50px"/><span title="'+joiner.userNick+'">'+joiner.userNick+'</span>';
	    			content = content + '<span color="red">||下注：' + joiner.goldNum + '</span>';
	    			if ((i+1)%2 == 0) {
	    				content = content + '<br/><br/>';
	    			}
	    		}
	        	return content;
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'totalGoldNum',
	    title : '房间下注总金币数',
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
	    id : 'status',
	    title : '房间状态',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	    		if (value == 0) {
	    			return "等待加入";
	    		} else if (value == 1) {
	    			return "加入中";
	    		} else if (value == 2) {
	    			return "等待开奖";
	    		} else if (value == 3) {
	    			return "已结束";
	    		} else if (value == 4) {
	    			return "已取消";
	    		}
	        }else{
	        	return '<span title="'+value+'">'+value+'</span>';
	        }
	    }
	},
	{
	    id : 'createTime',
	    title : '房间创建时间',
	    type : 'date',
	    format : 'yyyy-MM-dd hh:mm:ss',
	    otype : 'time_stamp_ms',
	    oformat : 'yyyy-MM-dd hh:mm:ss',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header'
	},
	{
	    id : 'updateTime',
	    title : '开奖时间',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(record.status == 3){
	    		return getSmpFormatDateByLong(parseInt(value),true);
	    	}else{
	    		return '';
	    	}
	    }
	}
];

//动态设置jqGrid的rowNum
var pageSize = $("#pageSize").val();
pageSize = pageSize == 0 || pageSize == "" ? sys.pageNum : pageSize;

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : false,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/coinflipRoom/list2.html',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh',
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
    grid.parameters['hashCode'] = $("#hashCode").val();
    grid.parameters['status'] = $("#roomStatus").val();
    grid.parameters['beginCreateTime'] = $("#beginCreateTime").val();
    grid.parameters['endCreateTime'] = $("#endCreateTime").val();
    grid.parameters['beginOpenTime'] = $("#beginOpenTime").val();
    grid.parameters['endOpenTime'] = $("#endOpenTime").val();
    grid.refresh(true);
}
