var dtGridColumns_p = [{
    id : 'id',
    title : '唯一标识',
    type : 'string',
    columnClass : 'text-center',
    hide : true,
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'inningNum',
    title : '局数',
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
    id : 'pankouType',
    title : '玩法类型',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(record.pankouType!=null && record.pankouTypeName!=null){
        	return '<span class="'+record.pankouTypeClass+'">'+record.pankouTypeName+'</span>';
        }else{
        	return '';
        }
    }
},{
    id : 'pkName',
    title : '玩法名称',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'pkStartTime',
    title : '开始时间',
    type : 'date',
    format : 'yyyy-MM-dd hh:mm:ss',
    otype : 'time_stamp_ms',
    oformat : 'yyyy-MM-dd hh:mm:ss',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header'
},{
    id : 'pkStatus',
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
},{
    id : 'pkStatusType',
    title : '盘口现状',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(record.pkStatusType!=null && record.pkStatusTypeName!=null){
        	return '<span class="'+record.pkStatusTypeClass+'">'+record.pkStatusTypeName+'</span>';
        }else{
        	return '';
        }
    }
},{
    id : 'pkVictory',
    title : '胜方战队',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(value!=null && value.length>10){
    		if(value == record.homeTeamId){
    			return record.homeTeamName;
    		}else{
    			return record.awayTeamName;
    		}
        }else{
        	return '';
        }
    }
},{
    id : 'pkHomeRule',
    title : '比赛赔率（主）',
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
    id : 'pkAwayRule',
    title : '比赛赔率（客）',
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
    id : 'pkHomePrt',
    title : '参与人数（主）',
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
    id : 'pkAwayPrt',
    title : '参与人数（客）',
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
    id : 'thisPkProfit',
    title : '当前比赛平台收益',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
},{
    id : 'pkHomePrtGold',
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
},{
    id : 'pkAwayPrtGold',
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
},{
    id : 'pkRangFenTeam',
    title : '让分战队',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    hideType : 'sm|xs|md|lg',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	if(value=='1'){
        	return '主场战队';
        }else if(value=='2'){
        	return '客场战队';
        }else{
        	return '';
        }
    }
},{
    id : 'pkRangfenNum',
    title : '让分数',
    type : 'string',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    hideType : 'sm|xs|md|lg'
},{
    id : 'pkEndTime',
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
},{
    id : 'operation',
    title : '操作',
    type : 'date',
    format : 'yyyy-MM-dd hh:mm:ss',
    otype : 'time_stamp_ms',
    oformat : 'yyyy-MM-dd hh:mm:ss',
    columnClass : 'text-center',
    headerClass : 'dlshouwen-grid-header',
    resolution : function(value, record, column, grid, dataNo, columnNo) {
    	var content = '';
    	if(record.edit){
    		content+= '<button type="button" onclick="webside.wodota.editModelByOperation(\'/jc/pankou/editUI\',\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</button>';
    	}
    	return content;
    }
}];

//动态设置jqGrid的rowNum
var pageSize_p = $("#pageSize_p").val();
pageSize_p = pageSize_p == 0 || pageSize_p == "" ? sys.pageNum : pageSize_p;

var dtGridOption_p = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    extraWidth : '37px',
    loadURL : sys.rootPath + '/jc/pankou/list',
    columns : dtGridColumns_p,
    gridContainer : 'dtGridContainer_p',
    toolbarContainer : 'dtGridToolBarContainer_p',
    tools : 'refresh|print',
    exportFileName : '盘口列表',
    pageSize : pageSize_p,
    pageSizeLimit : [10, 20, 30]
};

var grid_p = $.fn.dlshouwen.grid.init(dtGridOption_p);
$(function() {
    if(null != $("#orderByColumn_p").val() && '' != $("#orderByColumn_p").val())
    {
    	grid_p.sortParameter.columnId = $("#orderByColumn_p").val();
    	grid_p.sortParameter.sortType = $("#orderByType_p").val();
    }
    grid_p.parameters = new Object();
    grid_p.parameters['gbId'] = $("#id").val();
    grid_p.load();
    $("#btnSearch_p").click(customSearch_p);
    
});

/**
 * 自定义查询
 * 这里不传入分页信息，防止删除记录后重新计算的页码比当前页码小而导致计算异常
 */
function customSearch_p() {
	grid_p.parameters = new Object();
	grid_p.parameters['gbId'] = $("#id").val();
	grid_p.parameters['pankouType'] = $("#pankou_type_p").val();
	grid_p.parameters['pkName'] = $("#pk_name_p").val();
	grid_p.parameters['pkStatusType'] = $("#pk_status_type_p").val();
	grid_p.parameters['pkStatus'] = $("#pk_status_p").val();
	grid_p.parameters['beginCreateTime'] = $("#beginCreateTime_p").val();
	grid_p.parameters['endCreateTime'] = $("#endCreateTime_p").val();
	grid_p.refresh(true);
}