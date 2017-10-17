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
	    id : 'stockId',
	    title : '库存ID',
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
	    id : 'exchangeTime',
	    title : '兑换时间',
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
	    id : 'exchangeGold',
	    title : '兑换金额',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	        return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'exchangeStatus',
	    title : '兑换状态',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	if(value!=null){
	        	if (value == 0) {
	        		return '<span title="'+value+'">无效</span>';
	        	} else if (value == 1) {
	        		return '<span title="'+value+'">未领取</span>';
	        	} else if (value == 2) {
	        		return '<span title="'+value+'">已领取</span>';
	        	} else if (value == 3) {
	        		return '<span title="'+value+'">出售中</span>';
	        	} else if (value == 4) {
	        		return '<span title="'+value+'">已出售</span>';
	        	}
	        }else{
	        	return '';
	        }
	    }
	},
	{
	    id : 'ueOrderNo',
	    title : '订单编号',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'">'+value+'</span>';
	    }
	},
	{
	    id : 'trackerNo',
	    title : '快递单号',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	return '<span title="'+value+'"><input type="text" value="'+value+'" onblur="editTrackerNo(\'' + record.id + '\',\'' + record.trackerNo +'\',this.value)"></span>';
	    }
	},
	{
	    id : 'goodsVo',
	    title : '商品名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var obj = record.goodsVo;
	    	if (obj != null) {
	    		return '<span title="'+obj.goodsName+'">'+obj.goodsName+'</span>';
	    	} else {
	    		return '';
	    	}
	    }
	},
	{
	    id : 'goodsVo',
	    title : '商品金币',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var obj = record.goodsVo;
	    	if (obj != null) {
	    		return '<span title="'+obj.goodsGold+'">'+obj.goodsGold+'</span>';
	    	} else {
	    		return '';
	    	}
	    }
	},
	{
	    id : 'goodsVo',
	    title : '商品类型',
	    type : 'string',
	    columnClass : 'text-center',
	    headerClass : 'dlshouwen-grid-header',
	    resolution : function(value, record, column, grid, dataNo, columnNo) {
	    	var obj = record.goodsVo;
	    	if (obj != null) {
	    		if (obj.goodsType == 1) {
	    			return '<span title="'+obj.goodsType+'">实物</span>';
	    		} else if (obj.goodsType == 2) {
	    			return '<span title="'+obj.goodsType+'">虚拟物品</span>';
	    		}
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
	    	if (record.exchangeStatus && record.exchangeStatus == 3) {
	    		content+= '<shiro:hasPermission name="userExchangeLog:saledCard"><button type="button" onclick="saledCard(\''+record.id+'\')" class="btn btn-success btn-sm"><i class="fa fa-pencil-square-o"></i>&nbsp;完成出售</button></shiro:hasPermission>';
	    	}
	    	/*content+= '&nbsp;&nbsp;';
	    	content+= '<shiro:hasPermission name="userExchangeLog:deleteBatch"><button type="button" onclick="webside.wodota.delModelByOperation(\'/userExchangeLog/deleteBatch.html\',customSearch,\''+record.id+'\')" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i>&nbsp;删除</button></shiro:hasPermission>';
	    	*/
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
    loadURL : sys.rootPath + '/userExchangeLog/list',
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
    grid.parameters['exchangeStatus'] = $("#exchangeStatus").val();
    grid.parameters['ueOrderNo'] = $("#ueOrderNo").val();
    grid.parameters['trackerNo'] = $("#trackerNo").val();
    grid.parameters['exchangeTimeStart'] = $("#exchangeTimeStart").val();
    grid.parameters['exchangeTimeEnd'] = $("#exchangeTimeEnd").val();
    
    grid.refresh(true);
}


function saledCard(exchangeId) {
	layer.confirm('确认已售出？', {
        icon : 3,
        title : '已售出提示'
    }, function(index, layero) {
    	$.ajax({
    		type : 'POST',
    		url : '/userExchangeLog/saledCard',
    		data : {exchangeId : exchangeId},
    		success : function (data) {
    			if (data == 1) {
    				layer.msg("操作成功", {icon : 6});
    				grid.refresh(true);
    			} else {
    				layer.msg("操作失败", {icon : 5});
    			}
    		}
    	});
    	layer.close(index);
    });
	
}

function editTrackerNo(exchangeId,oldTrackerNo, trackerNo) {
	if (oldTrackerNo == trackerNo) {
		return;
	}
	if (!/^[0-9a-zA-Z]{8,30}$/.test(trackerNo)) {
		layer.alert("快递单号只能由8--30位的数字和字母组合", {icon : 5});
		return;
	}
	layer.confirm('确认修改快递单号？', {
        icon : 3,
        title : '修改快递单号提示'
    }, function(index, layero) {
    	if (index > 0) {
    		$.ajax({
    			type : 'POST',
    			url : '/userExchangeLog/editTrackerNo',
    			data : {exchangeId : exchangeId,trackerNo : trackerNo},
    			success : function (data) {
    				if (data == 1) {
    					layer.msg("操作成功", {icon : 6});
    				} else {
    					layer.msg("操作失败", {icon : 5});
    				}
    				grid.refresh(true);
    			}
    		});
    	}
    	layer.close(index);
    });
	
	
}