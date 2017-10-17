/**
 * 领取G币完成任务
 */
function right_completedTask(userTaskId,taskType) {
	// 更新支付宝账号
	$.ajax({
		type : "POST",
		url :  _path + "/my/userTask/compeleted",
		data : {
			"userTaskId" : userTaskId,
			"taskType" : taskType
		},
		dataType : "json",
		async : false,
		success : function(result) {
			if (result.result > 0) {
				$("#r-alert-ml_"+taskType).html("+" + result.gold +" G币");
				$("#r_getGold_"+taskType).addClass("hide");
				$("#r_getedGold_"+taskType).parent().find(".view1").css("width", "0%");
				$("#r_getedGold_"+taskType).removeClass("hide");
				if (taskType == 0 || taskType == 4) { // 签到和领昵称设置
					$("#completedNum_"+taskType).html("（1/1）");
				}
				if (window.location.href.indexOf("/my/userTask")) {
					if (taskType == 0) { // 签到和领昵称设置
						$("#taskName_"+taskType).html("（1/1）");
					}
					$("#alert-ml_"+taskType).html("+" + result.gold +" G币");
					$("#alert-ml_"+taskType).removeClass("fr").removeClass("alert-tmier").removeClass("alert-time");
					$("#alert-ml_"+taskType).parent().removeClass("col-xs-3").addClass("col-xs-2 c_sand fr");
					$("#alert-ml_"+taskType).parent().find("button").attr("disabled","true").css({"background":"#3d4148","color":"#fff"}).html("已领取");
				}
			}
		},
		error : function(errorMsg) {
			
		}
	});
}

function initTaskCenter() {
	$.ajax({
		type : "POST",
		url : _path + "/my/userTask/right",
		data :{
			source : "task"
		},
		dataType : "json",
		success : function(data) {
			var voList = data.taskVoList;
			if (voList != null && voList.length > 0) {
				var taskHtml = "";
				for(var i=0; i < voList.length; i++) {
					var vo = voList[i];
					var task = vo.task;
					var userTask = vo.userTask;
					var userTaskId = '';
					var buttonStr = '';
					var completeNum = 0;
					if (task.taskType == 0) { //签到
						if (userTask && (userTask.completedState == 1 || userTask.completedState == 2)) {
							completeNum = userTask.completeNum;
							buttonStr += 	'<p class="view1"></p>';
							buttonStr += 	'<p class="view2 co-af" id="right_task_yqd">已签到</p>';
						} else {
							buttonStr += 	'<p class="view1" style="width:100%;"></p>';
							buttonStr += 	'<p class="view2" id="right_task_wqd" onclick="userSign(\'right_task\')">签到</p>';
							buttonStr += 	'<p class="view2 co-af hide" id="right_task_yqd">已签到</p>';
						}
					} else { // 非签到
						if (userTask) {
							userTaskId = userTask.id;
							completeNum = userTask.completeNum;
							if (userTask.completedState == 0) {// 进行中
								buttonStr += '<p class="view1" style="width:' + completeNum*100/task.num+ '%;"></p>';
								buttonStr += '<p class="view2 co-af">进行中</p>';
							} else if (userTask.completedState == 1) {// 已完成
								buttonStr += '<p class="view1" style="width:100%;"></p>';
								buttonStr += '<p class="view2" onclick="right_completedTask(\''+userTask.id+'\', ' +task.taskType+ ')" id="r_getGold_'+task.taskType+'">领取</p>';
								buttonStr += '<p class="view2 co-af" id="r_getedGold_'+task.taskType+'">已领取</p>';
							} else if (userTask.completedState == 2) {// 已领取
								buttonStr += '<p class="view1"></p>';
								buttonStr += '<p class="view2 co-af">已领取</p>';
							}
						} else {
							if (task.taskType == 4) {// 设置个性昵称领取
								buttonStr += '<p class="view1" style="width:100%;"></p>';
								buttonStr += '<p class="view2" onclick="right_completedTask(\'\', ' +task.taskType+ ')" id="r_getGold_'+task.taskType+'">领取</p>';
								buttonStr += '<p class="view2 co-af" id="r_getedGold_'+task.taskType+'">已领取</p>';
							} else {
								buttonStr += '<p class="view1" style="width:0%;"></p>';
								buttonStr += '<p class="view2 co-af">进行中</p>';
							}
						}
					}
					// 已经领取的G币
					var getGold = "";
					if (userTask && userTask.completedState == 2 && userTask.gold > 0) {
						getGold = "+" + userTask.gold + " G币";
					}
					// 完成奖励
					var gold = "";
					if (task.taskType == 0) {
						gold = "1~30";
					} else {
						gold = task.gold;
					}
					var labelUrl = _staticPrefix + '/images/label.png';
					taskHtml += '<li>';
					taskHtml += 	'<div class="task-list-top">';
					taskHtml += 		'<div class="task-list-top-left">';
					taskHtml += 			'<img src="' + task.image + '" class="img1">';
					if (task.type == 0) {
						taskHtml += 			'<img src="' + labelUrl + '" class="img2">';
					}
					taskHtml += 			'<div class="task-list-progress-bar">';
					taskHtml += 				buttonStr;
					taskHtml += 			'</div>';
					taskHtml += 		'</div>';
					taskHtml += 		'<div class="task-list-top-right">';
					if (task.taskType == 4) {//设置昵称
						taskHtml += 			'<p class="view1">' + task.taskName + '<span id="completedNum_' +task.taskType+ '">（' + task.num+ '/' + task.num+ '）</span></p>';
					} else {
						taskHtml += 			'<p class="view1">' + task.taskName + '<span id="completedNum_' +task.taskType+ '">（' + completeNum+ '/' + task.num+ '）</span></p>';
					}
					taskHtml += 			'<p class="view2">' + task.description + '</p>';
					taskHtml += 		'</div>';
					taskHtml += 	'</div>';
					taskHtml += 	'<p class="task-list-bottom">';
					if (task.taskType == 0) {
						taskHtml += 		'<span class="view1" id="right_task_signGold">' + getGold + '</span>';
					} else {
						taskHtml += 		'<span class="view1" id="r-alert-ml_'+task.taskType+'">' + getGold + '</span>';
					}
					taskHtml += 		'<span class="view2">完成任务奖励<span class="view3">' + gold + '</span>G币</span>';
					taskHtml += 	'</p>';
					taskHtml += '</li>';
				}
				$("#right_taskList").html(taskHtml);
			}
			
		},
		complete : function() {
		}
	});
	
}