$(function() {
	loadList();
	$(".pull-cdownlist select").dropdown();
});

function loadList() {
	var options = {};
	options.url = _path + "/my/transactionLog/list";
	options.pageSize = "10";
	var parameters = {};
	parameters.utType = $("#utType").val();
	options.parameters = parameters;

	$.pagination("pagination", options, function(data) {
		var content = "";
		if(data.length > 0) {
			var transactionLog ;
			for(var i = 0; i<data.length; i++) {
				transactionLog = data[i];
				var remarks = transactionLog.remarks == null ? "" : transactionLog.remarks;
				content += '<tr>';
				content += '<td>'+getSmpFormatDateByLong(Number(transactionLog.utTime), true)+'</td>';
				content += '<td>'+getTypeName(transactionLog.utType)+'</td>';
				if(transactionLog.goldNum < 0) {
					content += '<td><span>'+transactionLog.goldNum+'</span>'+transactionLog.goldTypeName+'</td>';
				} else {
					content += '<td><span>+'+transactionLog.goldNum+'</span>'+transactionLog.goldTypeName+'</td>';
				}
				content += '<td>'+remarks+'</td>';
				content += '</tr>';
			}
		} else {
			content = "<tr><td colspan='4'><div class='no-record-x'><i class='iconfont icon-kule'></i>暂无数据</div></td></tr>";
		}
		$("#listUl").html(content);
	})
}

