<%@ page language="java" contentType="text/html;charset=utf-8" %>
<div class="modal fade" id="myModaltxian" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width:500px; background:#2E3238; position: absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        <div class="modal-content" style="background:#2E3238">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title link-white" id="myModalLabel">
                    <strong style="font-size: 30px;">金币提现</strong>
                </h4>
            </div>
            <div class="modal-body" style="height:515px;">
                <div class="tixianzj xyb-a mt-70">
                    <p class="tixianzh" id="paWd"></p>
                    <p class="grecofont mt-15">提现到支付宝账户，最低提现1000金币，手续费率0.1%，最低手续费10金币</p>
                    <div class="form-group label-floating tixianje">
                        <input id="wdGold" type="text" class="form-control" onchange="checkWdGold(this.value);">
                    </div>
                    <p class="grecofont">
                    	当前可提现 <span id="wdMaxGold"></span> 金币，
                    	<a href="javascript:allInWd();" style="color: #31c3a2; text-decoration: underline;">全部提现</a>，
                    	<a href="javascript:showWdReg();" style="color: #31c3a2; text-decoration: underline;">收费规则</a>
                    </p>
                </div>
                <p class="grecofont mb-10">*预计<span id="wdTime"></span> 23&nbsp;:&nbsp;59前到账</p>
                <div class="qrtx-a hide">
                	<div class="view1">
                		<div class="view2">
                			<p class="first">收款方账号</p>
                			<p class="second" style="color:#fff;" id="paWd-new"></p>
                		</div>
                		<p class="one">提现到支付宝账户，手续费率0.1%，最低手续费10金币</p>
                	</div>
                	<div class="view2 mt-40">
                		<p class="first">提现金币</span>
                		<p class="second" style="color:#fff;" id="wdGold-new"></span>
                	</div>
                	<div class="view2 mt-40">
                		<p class="first">提现金额</p>
                		<p class="second">
                			<span class="co-31" id="wdMoney"></span>
                			<span class="co-31">元</span>
                			<span id="factorage" style="color:#fff;">(+0.00 元 服务费)</span>
                		</p>
                	</div>
                	<div class="view2 mt-40">
                		<p class="first">到账时间</p>
                		<p class="second">
                			<img src="${staticPrefix }/images/daozhangsj.png">
                			<span class="co-31"><span id="wdTime-new"></span> 23&nbsp;:&nbsp;59前到账</span>
                		</p>
                	</div>
                	<div class="view2">
                		<p class="first" style="margin-top: 37px;">支付密码</p>
                		<div class="form-group label-floating second">
                        	<input id="passWord" type="password" class="form-control">
                    	</div>
                	</div>
                	<a href="${path }/my/info" class=view3>忘记支付密码？</a>
                	<div class="go-to-change mt-40">
                		<p class="one">安全设置检测成功！无需短信校验。</p>
                		<span class="two">返回修改</span>
                	</div>
                </div>
                <button type="button" class="btn tixianbtn xyb-x" style="color: #ffffff;">下一步</button>
                <button type="button" class="btn tixianbtn qrtx-x hide" style="color: #ffffff;" onclick="submitForWd();" id="submitForWdBtn">确认提现</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
<script>
 $(function() {
	 var newDate = getSmpFormatNowDate(false);
	 var array = newDate.split("-");
	 $("#wdTime").text(array[0] + "年" + array[1] + "月" + array[2] + "日");
	 $("#wdTime-new").text(array[0] + "年" + array[1] + "月" + array[2] + "日");
	 
	//点击下一步
	 $(".xyb-x").on("click",function(){
		 var wdGold = $("#wdGold").val();
		 if(wdGold == "" || isNaN(wdGold) || wdGold < 1000) {
			 layer.alert("请确认提现金额，且不能少于1000金币", {icon:5 ,shift: 5,time: 1000});
			 $("#wdGold").focus();
			 return ;
		 }
		 setGold();
		 $(".xyb-x,.xyb-a").addClass("hide");
		 $(".qrtx-x,.qrtx-a").removeClass("hide");
	 });
	 //点击返回修改
	 $(".qrtx-a .go-to-change .two").on("click",function(){
		 $(".qrtx-x,.qrtx-a").addClass("hide");
		 $(".xyb-x,.xyb-a").removeClass("hide");
	 });
 });
 
 function setGold() {
	 var wdGold = Number($("#wdGold").val());
	 var num = wdGold % 100;
	 if(num > 0) {
		 wdGold = wdGold - num;
	 }
	 // 手续费
	 factorage = parseInt(wdGold/1000);
	 if(factorage < 10) {
		 factorage = 10;
	 }
	 var totalGold = wdGold + factorage;
	 var headerMenuGold = Number($("#headerMenuGold").text());
	 if(totalGold > headerMenuGold) {// 金币不足，全部由提现金额出
		 wdGold = wdGold - factorage;
	 }
	 
	 $("#wdGold").val(wdGold);
	 $("#wdGold-new").text(wdGold);
	 $("#wdMoney").text(wdGold/100);
	 $("#factorage").text("(+"+factorage/100+" 元 服务费)");
 }
 
 function allInWd() {
	 var gold = Number($("#wdMaxGold").text());
	 gold = gold - gold%10;
	 $("#wdGold").val(gold);
 }
 
 function showWdReg() {
	 var msg = "自2017年4月10日起，每笔手续费按提现金额的0.1%（千分之一），单笔手续费小于0.1元的按照0.1元收取。";
	 layer.alert(msg, {icon: 0,shift: 6,time: 5000});
 }
 
 function clickWd() {
	 var headerMenuGold = $("#headerMenuGold").text();
	 $("#wdMaxGold").text(headerMenuGold);
	 $("#wdGold").val(null);
	 $("#wdGold").focus();
	 
	 $(".qrtx-x,.qrtx-a").addClass("hide");
	 $(".xyb-x,.xyb-a").removeClass("hide");
	 
	 var url = _path + "/user/info.html";
	 $.ajax({
		url: url,
		data: {"id": userId},
		type: "POST",
		async: true,
		dataType: "json",
		success: function(data) {
			if(data.success) {
				var userEntity = data.data; 
				if(userEntity.alipayAccount == "" || userEntity.alipayAccount == null) {
					layer.msg("请到你的账户录入支付宝账号~");
					$("#myModaltxian").modal("hide");
				} else if(userEntity.payPassword == "" || userEntity.payPassword == null) {
					layer.msg("请到你的账户录入支付密码~");
					$("#myModaltxian").modal("hide");
				} else {
					var alipayAccount = hidePayAccount(userEntity.alipayAccount);
					$("#paWd").html(alipayAccount);
					$("#paWd-new").html(alipayAccount);
					$("#myModaltxian").modal("show");
				}
			} else {
				layer.alert(data.message, {icon:5 ,shift: 6,time: 5000});
			}
		}
	 });
 }
 
 function checkWdGold(gold) {
	 if(isNaN(gold) || gold == 0) {
		 layer.alert("请输入数字~", {icon:5 ,shift: 6,time: 1000});
		 $("#wdGold").val(null);
		 return;
	 }
	 gold = parseInt(gold);
	 var wdMaxGold = Number($("#wdMaxGold").text());
	 if(gold > wdMaxGold) {
		 layer.alert("金币不足", {icon:5 ,shift: 6,time: 1000});
		 return;
	 }
	 if(gold < 1000) {
		 layer.alert("最低提现1000金币", {icon:5 ,shift: 6,time: 1000});
		 if(wdMaxGold >= 1000) {
			 $("#wdGold").val(1000);
		 } else {
			 $("#wdGold").val(null);
		 }
		 return;
	 }
	 gold = gold - gold%100;
	 $("#wdGold").val(gold);
 }
 
 var factorage = 10;
 function submitForWd() {
	 var flag = checkPassWord();
	 if(flag) {
		 $("#submitForWdBtn").attr("disabled",true).html("提现中,请稍等...");
		 var wdGold = Number($("#wdGold").val()); 
		 $.ajax({
				type : "POST",
				url : _path + "/depositLog/addForWithdraw",
				data: {
					"gold": wdGold
				},
				async : false,
				dataType : "json",
				success : function(data) {
					if(data.success) {
						$("#submitForWdBtn").html("提现成功");
						updateUserGold(3, -1*wdGold, null);
						
						var wdFirstGold = Number($("#wdFirstGold").val());
						wdGold = Number(wdGold) + wdFirstGold;
						$("#wdFirstGold").val(wdGold);
						$("#userInfoWdGold").html(wdGold);
						$("#right_wdGold").html(wdGold);
						
						$("#myModaltxian").modal("hide");
						layer.alert("提现成功", {icon:6 ,shift: 6,time: 1000});
					} else {
						$("#submitForWdBtn").html("提现失败");
						layer.alert(data.message, {icon:5 ,shift: 6,time: 5000});
					}
				},error : function(errorMsg) {
		            
		        },
		        complete:function(errorMsg) {
		        	$("#submitForWdBtn").attr("disabled",false);
		        }
			});
	 }
 }
 
 function checkPassWord() {
	 var passWord = $("#passWord").val();
	 if(passWord == "" || passWord == undefined) {
		 layer.alert("请输入支付密码", {icon:5 ,shift: 6,time: 1000});
		 return false;
	 }
	 var flag = false;
	 var url = _path + "/my/checkPayPwd";
	 ajaxMethod(url, {"pwd": passWord}, "post", false, function(data) {
		 if(data.state == "0") {
			 flag = true;
		 } else {
			 layer.alert(data.msg, {icon:5 ,shift: 6,time: 1000});
		 }
	 });
	 
	 return flag;
	 
 }
 
</script>