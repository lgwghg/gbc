var dtGridColumns_u = [{
    id : 'id',
    title : '编号',
    type : 'string',
    columnClass : 'text-center',
    hide : true,
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'eventName',
    title : '赛事',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'inningNum',
    title : '盘口局数',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(value == '0'){
    		return '整场比赛';
    	}else{
    		return '<span>第'+value+'局</span>';
    	}
    }
},{
    id : 'pkName',
    title : '盘口玩法名称',
    type : 'string',
    columnClass : 'text-center',
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
    id : 'jcTeamType',
    title : '下注队伍',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(value==1){
        	return '主战队';
        }else if(value==2){
        	return '客场战队';
        }
    }
},{
    id : 'jcTime',
    title : '下注时间',
    type : 'date',
    format : 'yyyy-MM-dd hh:mm:ss',
    otype : 'time_stamp_ms',
    oformat : 'yyyy-MM-dd hh:mm:ss',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'jcGold',
    title : '下注G币',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}, {
    id : 'gameResult',
    title : '比赛结果',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(value==0){
        	return '输';
        }else if(value==1){
        	return '赢';
        }else if(value==2){
        	return '进行中';
        }
    }
},{
    id : 'victoryGoldNum',
    title : '用户赢得金币数量',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
}];

//动态设置jqGrid的rowNum
var pageSize_u = $("#pageSize_u").val();
pageSize_u = pageSize_u == 0 || pageSize_u == "" ? sys.pageNum : pageSize_u;

var dtGridOption_u = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/userJc/listJc',
    columns : dtGridColumns_u,
    gridContainer : 'dtGridContainer_u',
    toolbarContainer : 'dtGridToolBarContainer_u',
    tools : 'refresh|print',
    exportFileName : '评论信息',
    pageSize : pageSize_u,
    pageSizeLimit : [10, 20, 30]
};

var grid_u = $.fn.dlshouwen.grid.init(dtGridOption_u);
$(function() {
    if(null != $("#orderByColumn_u").val() && '' != $("#orderByColumn_u").val())
    {
    	grid_u.sortParameter.columnId = $("#orderByColumn_u").val();
    	grid_u.sortParameter.sortType = $("#orderByType_u").val();
    }
    grid_u.parameters = new Object();
    grid_u.parameters['gbId'] = $("#id").val();
    grid_u.load();
  //  $("#btnSearch_u").click(customSearch_u);
    
});