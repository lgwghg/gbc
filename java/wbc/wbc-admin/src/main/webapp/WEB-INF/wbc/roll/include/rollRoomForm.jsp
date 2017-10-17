<%@ page language="java" contentType="text/html;charset=utf-8"%>
<script type="text/javascript" src="${ctx}/resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
<div class="modal fade" id="rollRoomForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:500px; background:#2E3238;position: absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
		<div class="modal-content" style="background:#2E3238">
			<div class="modal-header">
				<button type="button" class="close close-x" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3 class="modal-title" id="myModalLabel" style="color: #fff;">${rollRoom.id ne null?"编辑":"创建" }ROLL</h3>
				<div class="row" style="color: #fff; margin-top: 40px;">
					<div class="col-xs-12 span4">
						<div class="media">
							<a rel="nofollow" href="#" class="pull-left"> 
								<img src="${cookie_user_photo }" class="media-object img-circle" alt='' width="60" height="60" />
							</a>
							<div class="media-body">
								<h4 class="media-heading">
									${rollRoom.userName } <span class="lv-x" style="display: inline-table;"></span>
								</h4>
								<div class="s-btn btn">
									<a href="javascript:;">竞技达人</a>
								</div>
								<h4 class="media-heading">
									${user.sign}
								</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-body">
				<form role="form">
					<div class="form-group" style="margin: 0;">
						<label class="control-label" for="remarks">活动说明* |</label> 
						<input class="form-control input-lg" id="remarks" type="text" value="${rollRoom.remarks }">
					</div>
					<div class="row" style="color:#bdbdbd; margin: 0;">
						<div class="col-xs-2 span3 pull-left" style="padding: 0; margin-top:40px;">活动时间*</div>
						<div class="col-xs-4 span3" style="padding: 0;">
							<input type='text' class="form-control" id='startTime' value="${rollRoom.startTime }" 
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" onchange="changeTime('startTime');"/>
						</div>
						<div class="col-xs-1 span3 text-center" style="padding: 0; margin-top:40px;">至</div>
						<div class="col-xs-5 span3" style="padding: 0;">
							<input type='text' class="form-control" id='endTime' value="${rollRoom.endTime }" 
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" onchange="changeTime('endTime');"/>
						</div>
					</div>
					<div class="form-group" style="margin: 0;">
						<input class="form-control input-lg" id="roomCode" onkeyup="checkRoomCode(this.value);" type="text" placeholder="自定义房间名" value="${rollRoom.roomCode }" style="color: #bdbdbd; font-size: 14px;">
					</div>
					<div class="form-group" style="margin: 0;">
						<input class="form-control input-lg" id="roomUrl" type="text" placeholder="直播间链接" value="${rollRoom.roomUrl }" style="color: #bdbdbd; font-size: 14px;">
					</div>
					<div class="form-group" style="margin: 0;">
						<input class="form-control input-lg" id="sinaUrl" type="text" placeholder="新浪微博链接" value="${rollRoom.sinaUrl }" style="color: #bdbdbd; font-size: 14px;">
					</div>
					<div class="form-group" style="margin: 0;">
						<input class="form-control input-lg" id="qqNum" type="text" placeholder="QQ粉丝群" value="${rollRoom.qqNum }" style="color: #bdbdbd; font-size: 14px;">
					</div>
				</form>
				<div style="color: #607d8b;">
					<p>温馨提醒：</p>
					<p>1)、创建活动后，请尽快添加该活动的奖品池，只有奖品池中有饰品，大家 才能看或者参加该活动。</p>
					<p>2)、如果您在该活动的截止时间内，没有将奖品池中的饰品ROLL出， Gbocai系统将自动在其10分钟后帮您ROLL出.</p>

				</div>
				<button class="view3 btn btn-primary btn-block btn-raised" style="margin-top:30px;" onclick="submitRoomForm();">提交</button>
			</div>
		</div>
	</div>
</div>
<script>
	$(function() {
		if("${rollRoom.id}" == "") {
			$("#remarks").val("");
			$("#startTime").val("");
			$("#endTime").val("");
			$("#roomCode").val("");
			$("#roomUrl").val("");
			$("#sinaUrl").val("");
			$("#qqNum").val("");
		}
	});

	function checkRoomCode(value) {
		value = changeStr(value);
		$("#roomCode").val(value);
	}
	
	function changeStr(value) {
		var endStr = value.substring(value.length-1, value.length);
		if(/[\u4E00-\u9FA5]/.test(endStr)) {
			endStr = "";
		}
		value = value.substring(0, value.length-1) + endStr;
		if(/[\u4E00-\u9FA5]/.test(value)) {
			value = changeStr(value);
		}
		return $.trim(value);
	}
	
	function changeTime(id) {
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var newDate = getSmpFormatNowDate(true);
		if(startTime == "" || startTime == undefined) {
			startTime = newDate;
		} else {
			var array = newDate.split(" ");
			var array2 = startTime.split(" ");
			startTime = array[0] + " " + array2[1];
		}
		if(endTime == "" || endTime == undefined) {
			endTime = startTime;
		}
		
		if("startTime" == id) {// 修改的开始时间
			if(startTime > endTime) {
				endTime = startTime;
			}
		} else {
			if(endTime < startTime) {
				startTime = endTime;
			}
		}
		
		$("#startTime").val(startTime);
		$("#endTime").val(endTime);
	}
	
	function submitRoomForm() {
		changeTime("");
		var id = "${rollRoom.id}";
		var remarks = $("#remarks").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var roomCode = $.trim($("#roomCode").val());
		var roomUrl = $.trim($("#roomUrl").val());
		var sinaUrl = $.trim($("#sinaUrl").val());
		var qqNum = $.trim($("#qqNum").val());
		
		var pattern = "http://e.weibo.com/[a-zA-Z]+$|http://weibo.com/[a-zA-Z]+$|http://weibo.com/u/\\d+$";
		var reg = new RegExp(pattern);
		if(sinaUrl != "" && !reg.test(sinaUrl)) {
			layer.alert("新浪微博链接有误", {icon:5 ,shift: 6,time: 3000});
			return;
		}
		reg = /^[1-9]\d{4,9}$/;
		if(qqNum != "" && !reg.test(qqNum)) {
			layer.alert("QQ粉丝群有误", {icon:5 ,shift: 6,time: 3000});
			return;
		}
		reg = /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
		if(roomUrl != "" && !reg.test(roomUrl)) {
			layer.alert("直播间链接有误", {icon:5 ,shift: 6,time: 3000});
			return;
		}
		
		var entity = {};
		entity.id = id;
		entity.remarks = remarks;
		entity.startTime = startTime;
		entity.endTime = endTime;
		entity.roomCode = roomCode;
		entity.roomUrl = roomUrl;
		entity.sinaUrl = sinaUrl;
		entity.qqNum = qqNum;
		
		var url = _path + "/rollRoom/edit.html";
		if(id == "" || id == undefined) {
			url = _path + "/rollRoom/add.html";
		}
		ajaxMethod(url, entity, "post", false, function(backData) {
			if(backData.success) {
				$("#rollRoomForm").modal("hide");
				$("#videoId").attr("src",roomUrl);
				layer.msg(backData.message);
				if (id == "" || id == null) {
// 					清空
					$("#myRollRoom").html("");
// 					初始化我创建的房间
					initMyCreateRoom();
// 					初始化所有房间
					if (currentTypeId == null || currentTypeId == "") {
						currentTypeId = 1;
					}
					initAllRoll(currentTypeId);
				}
			} else {
				var msg = backData.message;
				if(msg.indexOf("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException") != -1) {
					msg = "房间号已被使用，请修改后重新提交~";
				}
				layer.alert(msg, {icon:5 ,shift: 6,time: 3000});
			}
		})
	}
</script>