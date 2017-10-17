<%@ page language="java" contentType="text/html;charset=utf-8"%>

<div class="modal fade myModalAdd" id="rollPoolForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="margin: -295px 0px 0px -250px!important;position: fixed;top: 50%;left: 50%;width: 500px;">
		<div class="modal-content" style="background:#2e3238; color:#fff; width: 500px; height:590px;">
			<div class="modal-header" style="overflow: hidden;">
				<h2 class="modal-title" id="myModalLabel" style=" font-size: 20px;float:left;">
					<b>添加奖品</b>
				</h2>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div class="view1">
					<span class="one">奖品类型</span>
					<div class="form-group">
						<select id="provinceId" class="form-control">
							<option value="gold">金币</option>
							<option value="CDKEY">CD-KEY</option>
						</select>
					</div>
					<div class="fix"></div>
				</div>
				<p class="view1-label">请先选择要添加的商品类型</p>
				<div class="team-one">
					<div class="view1">
						<span class="one">添加金币</span>
						<div class="form-group label-floating">
							<input type="text" class="form-control text_input" id="poolGold" onkeyup="checkPoolGold(this.value);"> 
							<span class="help-block">您的钱包中有 <span class="co-31" id="poolGoldId"></span> 金币余额可使用</span>
						</div>
						<div class="fix"></div>
					</div>
					<p class="view1-label"></p>
					<div class="view1">
						<span class="one">奖品个数</span>
						<div class="form-group label-floating">
							<input type="text" class="form-control text_input" id="poolPrizeNum" value="1" onkeyup="changePrizaNum();"> 
							<span class="help-block">放入奖品池个数设置 <span class="co-31" id="poolPrizeId">1</span> 个，合计<span id="totalPoolGold"></span> 金币</span>
						</div>
						<div class="fix"></div>
					</div>
					<p class="view1-label"></p>
				</div>
				<div class="team-two hide">
					<div class="view1">
						<span class="one">CD-KEY</span>
						<ul class="two" id="cdk_ul">
						</ul>
						<div class="fix"></div>
					</div>
				</div>
				<div class="form-group view2">
					<div class="checkbox" style="margin-top:0;">
						<label style="color: #fff;"> <input type="checkbox" checked="checked" id="isRead" onchange="isRead()">同意Gbocai《<a href="javascript:;" target="_blank" style="color: #31c3a2;">ROLL活动相关规则</a>》
						</label>
					</div>
				</div>
<!-- 				<p class="view3">*当该活动参与人数大于10人时，奖品池中的饰品将无法撤回！</p> -->
				<button type="button" class="btn  btn-raised btn-primary view4" onclick="submitPoolForm();">提交</button>
			</div>
		</div>
	</div>
</div>

<script>
	$(function() {
	    $(".myModalAdd .modal-body .view1 .form-group ul li").on("click",function(){
	        if($(this).attr("value") == "gold") {
	            $(".myModalAdd .modal-body .team-one").removeClass("hide");
	            $(".myModalAdd .modal-body .team-two").addClass("hide");
	        } else {
	            $(".myModalAdd .modal-body .team-one").addClass("hide");
	            $(".myModalAdd .modal-body .team-two").removeClass("hide");
	            getCdKeyList();
	        }
	    })
	});
	
	function getCdKeyList() {
		var url = _path + "/my/exchangeLog/list";
		var options = {};
		options.url = url;
		options.pageSize = "15";
		var parameters = {};
		parameters.isCDK = "false";
		parameters.statusId = "-1";
		options.parameters = parameters;
		
		ajaxMethod(url, {"gridPager":JSON.stringify(options)}, "post", true, function(backData) {
			if(backData.isSuccess) {
				var data = backData.list;
				var content = '';
				if(data) {
					var length = data.length;
					var exchangeLog;
					for(var i=0; i<length; i++) {
						exchangeLog = data[i];
						var goodsVo = exchangeLog.goodsVo;
						var goodsImg = goodsVo.goodsImg;
						content += '<li value='+exchangeLog.id+'><img src="'+goodsImg+'"></li>';
						
					}
				}
				
				$("#cdk_ul").html(content);
				
				//CDKEY选择
			    $(".myModalAdd .modal-body .view1 .two li").on("click",function(){
			        if($(this).hasClass("checked") == true){
			            $(this).removeClass("checked");
			        } else {
			            $(this).addClass("checked");
			        }
			    })
			}
		});
		
	}

	function clickPool() {
		var headerMenuGold = $("#headerMenuGold").text();
		$("#poolGoldId").text(headerMenuGold);
		$("#poolGold").val(null);
		$("#poolGold").focus();
	}
	
	function checkPoolGold(gold) {
		if(isNaN(gold)) {
			layer.alert("请输入数字~", {icon:5 ,shift: 6,time: 1000});
			$("#poolGold").val(0);
		} else {
			gold = parseInt(gold);
			if(gold < 0) {
			 	layer.alert("提现金额不能小于0", {icon:5 ,shift: 6,time: 1000});
			 	$("#poolGold").val(0);
		 	}
			var maxGold = Number($("#poolGoldId").text());
			if(gold > maxGold) {
				gold = maxGold;
			}
			$("#poolGold").val(gold);
			
			var num = $("#poolPrizeNum").val();
			if(num != "" && num > 1) {
				var totalPoolGold = num * gold;
				var maxGold = Number($("#poolGoldId").text());
				if(totalPoolGold > maxGold) {
					layer.alert("个数太多，超出可用金币，请重新填写", {icon:5 ,shift: 6,time: 1000});
					$("#poolPrizeNum").val(1);
				}
				$("#totalPoolGold").text(totalPoolGold);
			}
		}
	}
	
	function changePrizaNum() {
		var num = $("#poolPrizeNum").val();
		if(isNaN(num)) {
			layer.alert("请输入数字~", {icon:5 ,shift: 6,time: 1000});
			$("#poolPrizeNum").val(1);
		} else if(num < 1) {
		 	layer.alert("个数不能少于1个", {icon:5 ,shift: 6,time: 1000});
		 	$("#poolPrizeNum").val(1);
	 	}
		
		$("#poolPrizeId").text(num);
		var poolGold = $("#poolGold").val();
		if(poolGold != "" && poolGold != 0) {
			var totalPoolGold = num * poolGold;
			var maxGold = Number($("#poolGoldId").text());
			if(totalPoolGold > maxGold) {
				layer.alert("个数太多，超出可用金币，请重新填写", {icon:5 ,shift: 6,time: 1000});
				$("#poolPrizeNum").val(1);
			}
		}
		$("#totalPoolGold").text(totalPoolGold);
	}

	function submitPoolForm() {
		var provinceId = $("#provinceId").val();
		var roomId = $("#roomId").val();
		var poolGold = $("#poolGold").val();
		var poolPrizeNum = $("#poolPrizeNum").val();
		var exchangeIds = getExchangeIds();
		
		if("gold" == provinceId) {// 金币
			if(poolGold == null || poolGold < 100) {
				layer.alert("金币不能少于100，请重新填写", {icon:5 ,shift: 6,time: 1000});
				return;
			}
			exchangeIds = "";
		} else {// cd-key
			if(exchangeIds == "") {
				layer.alert("请选择CD-KEY", {icon:5 ,shift: 6,time: 1000});
				return;
			}
			poolGold = null;
		}

		var entity = {};
		entity.roomId = roomId;
		entity.gold = poolGold;
		entity.num = poolPrizeNum;
		entity.exchangeIds = exchangeIds;
			   
		var url = _path + "/rollPool/addBatch.html";
		ajaxMethod(url, entity, "post", false, function(backData) {
			if (backData.success) {
				$("#rollPoolForm").modal("hide");
				getRollPool();
				layer.alert(backData.message, {icon:1,shift : 6,time : 2000});
			} else {
				layer.alert(backData.message, {icon:5 ,shift : 6,time : 3000});
			}
		})
	}
	
	function getExchangeIds() {
		var exchangeIds = "";
		$(".team-two .view1 .two .checked").each(function() {
			var value = $(this).attr("value");
			if(exchangeIds == "") {
				exchangeIds = value;
			} else {
				exchangeIds = exchangeIds + "," + value;
			}
		});
		return exchangeIds;
	}
</script>