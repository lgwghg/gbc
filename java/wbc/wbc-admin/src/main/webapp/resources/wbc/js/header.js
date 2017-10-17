var _userGold = 0;
var _sysGold = 0;

$(function(){
	
	if(user!=null && user!=''){
		//定义一个新的复制对象
		var clipboardHeaderBtn = document.getElementById('d_clip_button_head');
	    var clipboardHeader = new Clipboard(clipboardHeaderBtn);
	    clipboardHeader.on('success', function(e) {
		    layer.msg("复制成功", {icon: 6,shift: 6,time: 2000});
		});

	    clipboardHeader.on('error', function(e) {
			layer.msg("复制失败，请手动复制", {icon: 5,shift: 6,time: 2000});
		});
	}
	
	$("body").css("width", window.innerWidth);
	$(".bodyer").css("width", window.innerWidth);
	$("#c-bg").css("width", window.innerWidth);
	$(".modal-dialog").css("max-height",window.innerHeight);
    $(".modal-dialog").mCustomScrollbar();
	
	window.onresize=function(){
		$("body").css("width", window.innerWidth);
		$(".bodyer").css("width", window.innerWidth);
		$("#c-bg").css("width", window.innerWidth);
		if (window.innerWidth < 1277) {
            $(".inc_right_tabs_three").addClass("window_shrink").removeClass("window_unfold");
             timer1 = setTimeout(function () {
                $(".inc_right_tabs_big").css("background-color", "transparent");
                $(".inc_right_tabs_three").css("margin-left", "35px");
            }, 200);
            $(".inc_right_tabs_bg").removeClass("hide");
        }
        else if (window.innerWidth >= 1277) {
            $(".inc_right_tabs_three").addClass("window_unfold").removeClass("window_shrink");
             timer2 = setTimeout(function () {
                $(".inc_right_tabs_big").css("background-color", "transparent");
                $(".inc_right_tabs_three").css("margin-left", "0");
            }, 200);
            $(".inc_right_tabs_bg").addClass("hide");
        }
        $(".inc_right,.inc_right_tabs_big,.slide").css("height", document.documentElement.clientHeight);
        $(".inc_right_tabs_account").css("padding-top", "0");
        $(".inc_right_tabs_feedback").css("padding-bottom", "0");
        inc_padding = (document.documentElement.clientHeight - $(".inc_right_tabs_middle").height()) / 2;
        $(".inc_right_tabs_account").css("padding-top", inc_padding);
        $(".inc_right_tabs_feedback").css("padding-bottom", inc_padding);
        $(".modal-dialog").css("max-height", window.innerHeight).mCustomScrollbar("update"); 
	}
	
	$.material.init();
	//弹窗居中
	$("body").delegate("[data-toggle='modal']", "click", function () {
		var _target = $(this).attr('data-target')
	    t = setTimeout(function () {
	        var _modal = $(_target).find(".modal-dialog");
	        var _model_margintop = -(_modal.find(".modal-content").height()/2)+"px";
	        var _model_marginleft = -(_modal.find(".modal-content").width()/2)+"px";
	        /*_modal.animate({'margin-top': parseInt(($(window).height() - _modal.height()) / 4)}, 10)*/
	        _modal.css({
	        		'margin': _model_margintop + ' 0 0 ' + _model_marginleft,
	        		'position':'fixed',
	        		'top':'50%',
	        		'left':'50%'
	        	})
	    }, 10)
    })
    $("body").delegate("[data-target='#myModal2']", "click", function () {
    	t = setTimeout(function () {
    		$("#myModal2 .modal-dialog").css({
    			'margin': '0',
        		'position':'absolute',
    		})
	    }, 20)
    })
    
	
	$('#dm-1,#dm-2,#dm-3,#yqd').click(function(e) {
	    e.stopPropagation();
	});
	$("#loginId").click(function(e) {
		$("#myModal").modal("show");
	});
	
	//检索未读消息
	setTimeout(getUnread,0); 
	setInterval('getUnread()',1000*60*1);
	
	$("#userdropdown").click(function() {
		//isSign();
		$.ajax({
			type : "POST",
			url : _path + "/my/userTask/right",
			data :{
				source : "account"
			},
			dataType : "json",
			success : function(data) {
				var dailyTaskNum = data.dailyTaskNum;
				var completedDailyTaskNum = data.completedDailyTaskNum;
				$("#header_dailyTask").html("（" +completedDailyTaskNum+"/" +dailyTaskNum+ "）");
			},
			complete : function() {
				
			}
		});
	});
	userWallet();
	$("#headerRecharge").click(function() {
		rechargeAmountList();
	});
	
	if (window.location.href.indexOf("/my/info") > -1) {
		$("#userInfoRecharge").click(function() {
			rechargeAmountList();
		});
	}
});

var money = 0;
var gvieGold = 0;

/**
 * 待充值金额列表
 */
function rechargeAmountList(source) {
	$.ajax({
		type : "POST",
		url : _path + "/my/wallet/nl/rechargeAmount",
		dataType : "json",
		success : function(data) {
			var content = "";
			if (data.goldRateList) {
				var goldRateList = data.goldRateList;
				for (var i = 0; i < goldRateList.length; i++) {
					var goldRate = goldRateList[i];
					var sysGold = goldRate.amount * 100 * (goldRate.giveRate / 100);
					content += '<li><a href="javascript:clickMoney('+goldRate.amount+','+sysGold+');" class="btn btn-primary">' + goldRate.amount + '元</a><span>+ ' + parseInt(goldRate.amount*100+sysGold) + ' 金币</span></li>';
				}
			}
			content += '<li><div class="form-group label-floating"><input type="text" class="form-control text_input" placeholder="0" style="margin-top: -18px;"></div><span id="giveGold">+ 0 金币</span></li>';
			$("#rechargeAmount").html(content);
			$('#rechargeAmount li input').bind('input propertychange', function () {
				$("#rechargeAmount li a").removeClass("checked");
				var next = true;
				$(this).val($(this).val().replace(/\D/g,function(){
					next = false;
					return '';
				}));
				if(!next){
					return;
				}
				if($(this).val()>99999){
					$(this).val('99999');
				}
				var inputAmount = $(this).val();
				money = inputAmount;
				if (inputAmount != null && inputAmount > 10) {
					$.ajax({
						type : "POST",
						url : _path + "/my/wallet/giveGold",
						data : {amount : inputAmount},
						dataType : "json",
						success : function(data) {
							if (data!=null) {
								$("#giveGold").html("+ " + parseInt(inputAmount*100+data.giveGold) + "金币");
								gvieGold = data.giveGold;
							}
						}
					});
				} else {
					$("#giveGold").html("+ " + parseInt(inputAmount*100) + "金币");
				}
		    });
			//选择金额的样式
			$(".jingbi .btn").click(function(){
	            $(this).addClass("moneybackgd").parent().siblings().find(".btn").removeClass("moneybackgd");
	        });
			if (source=='right') {
				$("#rechargeSource").val(source);
			}
		},
		complete : function() {
			
		}
	});
}

/**
 * 点击金额
 */
function clickMoney(num,gold){
	$('#rechargeAmount li input').val("");
	$("#giveGold").html("+ " + 0 + "金币");
	$("#rechargeAmount li a").removeClass("checked");
	$(this).addClass("checked");
	money = num;
	gvieGold = gold;
}

/**
 * 点击充值
 */
function recharge(){
	if(!isPositiveNum(money)){
		layer.alert("请选择充值金额或者输入大于0的整数", {icon: 5,shift: 6,time: 2000});
		return;
	}
	var pay_type = $("input[name='myradio-radio']:checked").val();
	if(pay_type==''){
		layer.alert("请选择支付方式", {icon: 5,shift: 6,time: 2000});
		return;
	}
	$.ajax({
		type : "POST",
		url : _path + "/pay/web",
		data : {
			"money" : money,
			"type" : pay_type
		},
		async:false,
		dataType : "json",
		success : function(data) {
			if(data.isSuccess){
				$("#zhifu-modal-div").html(getWaitZhifu(pay_type));
				if(pay_type=='1'){
					window.open(_path+data.url+encodeURI("?i="+data.i+"&o="+data.o+"&u="+data.u+"&b="+data.b));
				}else{
					$.ajax({
						type : "POST",
						url : _path + data.url,
						data : {
							"i" : data.i,
							"u" : data.u,
							"b" : data.b
						},
						dataType : "json",
						success : function(result) {
							$("#header-qrcode").html("").qrcode({
							    render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
							    text : result,    //扫描二维码后显示的内容,可以直接填一个网址，扫描二维码后自动跳向该链接
								width : "160",               //二维码的宽度
							    height : "160",              //二维码的高度
							    background : "#ffffff",       //二维码的后景色
								foreground : "#000000",        //二维码的前景色
							    src: _staticPrefix + '/images/logo_code.png'           //二维码中间的图片
							});
						},
						complete : function() {
							
						}
					});
				}
				$("#myModal5").modal('hide');
				$("#myModal-zhifu").modal("show");
				//建立监听
				socket = io.connect("ws://"+_socketAddress);
				socket.on(data.i,function(result)
			   	{
					var message = JSON.parse(result);
					if(message.status=='1'){
						$("#zhifu-modal-div").html(getSuccessZhifu(message.goldNum,message.sysGoldNum));
					}else{
						$("#zhifu-modal-div").html(getErroZhifu());
					}
				});
			}else{
				layer.alert(data.message, {icon: 5,shift: 6,time: 2000});
			}
		},
		complete:function(XMLHttpRequest,textStatus) {
	 		var status = XMLHttpRequest.getResponseHeader("session_status");  
	 		if(status == "timeout") {
	 			var source = $("#rechargeSource").val();
	 			if (source == 'right') {
	 				$(".inc_right_tabs_account .inc_right_tabs_inside").after($("#inc_right_login"));
	 	            $("#inc_right_login").css({"top":"","bottom":"-395px"}).removeClass("hide");
	 			} else {
	 				$("#myModal").modal("show");
	 			}
	        }  
	    }
	});
}

/**
 * 等待支付
 */
function getWaitZhifu(payType){
	var content = '';
	//支付宝
	if(payType=='1'){
		content  = '<div class="c-zhifuphoto">';
		content += '<img src="'+_staticPrefix+'/images/zhifu.gif" alt="" />';
		content += '<p class="mt-30">等待支付</p> ';
		content += '</div>';
		content += '<div class="c-zhifumoney mt-30 mb-30">';
		content += '<p>支付:<span>'+money+'</span>元</p>';
		if(isPositiveNum(gvieGold)){
			content += '<p class="mt-5">充值金币:<span>'+money*100+'+('+gvieGold+')</span></p>';
		}else{
			content += '<p class="mt-5">充值金币:<span>'+money*100+'</span></p>';
		}
		content += '</div>';
	}
	//微信
	else{
		content += '<p class="item-w1">请使用<span>&nbsp;微信&nbsp;</span><span><img style="margin-bottom: 4px;" src="'+_staticPrefix+'/images/sysao.png" alt="" /></span><span>&nbsp;扫一扫</span></p>';
		content += '<p class="item-w1">扫描二维码支付</p>';
		content += '<div style="text-align: center; margin: 15px 0; height: 175px;">';
		content += '<div class="qrcode dimension" id="header-qrcode" style=" margin: 0px 136px; background: #31c3a2;"></div>';
		content += '</div>';
		content += '<p class="item-w2"><span><img style="margin:0 12px 4px 0;" src="'+_staticPrefix+'/images/oclock.png" alt="" /></span>二维码有效时间为两小时，请尽快支付</p>';
		if(isPositiveNum(gvieGold)){
			content += '<p class="item-w3">支付:<span>'+money+'</span>元，充值金币:<span>'+money*100+'+('+gvieGold+')</span></p>';
		}else{
			content += '<p class="item-w3">支付:<span>'+money+'</span>元，充值金币:<span>'+money*100+'</span></p>';
		}
	}
	content += '<div class="c-zhifufoot mt-10">';
	content += '<button type="button" class="btn btn-primary btn-raised mt-25" onclick="$(\'#myModal-zhifu\').modal(\'hide\');$(\'#myModal5\').modal(\'show\');">';
	content += '<strong>返回继续充值</strong>';
	content += '</button> ';
	//content += '<button type="button" class="btn-raised c-zhifuguesss" style="transform: translateY(10px);padding: 10px 20px;"><strong>已完成支付</strong></button>'
	content += '<a href="'+ip+'help/851b938834dd484cb4a76919000ff134" target="_blank" class="pull-right" style=" text-decoration: underline;">支付中遇到问题？</a>';
	content += '</div>';
	return content;
}

/**
 * 支付成功
 */
function getSuccessZhifu(goldNum,sysGoldNum){
	updateUserGold(1,goldNum,sysGoldNum);
	var content  = '<div class="c-zhifuphoto">';
		content += '<img src="'+_staticPrefix+'/images/successzhifu.png" alt="" />';
		content += '<p class="mt-30">恭喜你支付成功</p> ';
		content += '</div>';
		content += '<div class="c-zhifumoney mt-30 mb-30">';
		content += '<p>支付:<span>'+money+'</span>元</p>';
		content += '<p class="mt-5">+<span>'+parseInt(money*100+gvieGold)+'</span>金币</p>';
		content += '</div>';
		content += '<div class="c-zhifufoot mt-30">';
		content += '<button type="button" class="btn btn-primary btn-raised mt-25" onclick="$(\'#myModal-zhifu\').modal(\'hide\');$(\'#myModal5\').modal(\'show\');">';
		content += '<strong>返回继续充值</strong>';
		content += '</button> ';
		content += '<button type="button" class="btn-raised mt-25 pull-right c-zhifuguesss" onclick="window.location.href=\''+_path+'/match\'">';
		content += '<strong>去看看&nbsp;竞猜</strong>';
		content += '</button>';
		content += '</div>';
	return content;
}

/**
 * 支付失败
 */
function getErroZhifu(){
	var content  = '<div class="c-zhifuphoto">';
		content += '<img src="'+_staticPrefix+'/images/failzhifu.png" alt="" />';
		content += '<p class="mt-30">支付失败</p> ';
		content += '</div>';
		content += '<div class="c-zhifufoot mt-30">';
		content += '<button type="button" class="btn btn-primary btn-raised mt-25" onclick="$(\'#myModal-zhifu\').modal(\'hide\');$(\'#myModal5\').modal(\'show\');">';
		content += '<strong>返回继续充值</strong>';
		content += '</button> ';
		content += '<a href="#" class="pull-right">支付中遇到问题？</a>';
		content += '</div>';
	return content;
}

/**
 * 获取用户钱包金币数
 */
function userWallet() {
	if(user!=null && user!=''){
		$.ajax({
			type : "POST",
			url : _path + "/my/wallet/gold",
			async :false,
			dataType : "json",
			success : function(data) {
				if (data != null) {
					if (data.totalGold) {
						updateUserGold(1, data.gold, data.sysGold);
						if (window.location.href.indexOf("/my/info") > -1) {
							$("#userInfoGold").html(data.gold);
							$("#userInfoSysGold").html(data.sysGold);
						}
					}
					if (data.jcGold) {
						$("#jcGold").html(data.jcGold + " 金币");
						$("#userInfoJcGold").html(data.jcGold + " 金币");
						$("#right_jcGold").html(data.jcGold + " 金币");
					}
					if (data.wdGold) {
						$("#wdFirstGold").val(data.wdGold);
						$("#userInfoWdGold").html(data.wdGold);
						$("#right_wdGold").html(data.wdGold);
					}
				}
			},
			complete : function() {
				
			}
		});
	}
}

// 验证用户今天是否已经签到
function isSign() {
	$.ajax({
		type : "POST",
		url : _path + "/my/isSign",
		dataType : "json",
		success : function(data) {
			if (data >= 0) { // 已经签到
				$('#wqd').addClass("hide");
			    $('#yqd').removeClass("hide");
			    $("#signGiveGold").html("今天签到已获赠" + data + "G币");
			    
			    // 右边侧边签到情况
			    $('#right_wqd').addClass("hide");
			    $('#right_yqd').removeClass("hide");
			    $("#right_signGiveGold").html("每日签到，领取金币(1/1)");
			}else{
				$('#wqd').removeClass("hide");
			    $('#yqd').addClass("hide");
			    
			    // 右边情况
			    $('#right_wqd').removeClass("hide");
			    $('#right_yqd').addClass("hide");
			}
		},
		complete : function() {
			
		}
	});
}
// 用户签到
function userSign(source) {
	$.ajax({
		type : "POST",
		url : _path + "/my/sign",
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				updateUserGold(4, null, data);
				//var signDays = $("#userSignDays").text();
				
				$('#task_wqd').addClass("hide");
				$('#task_yqd').removeClass("hide");
				$("#signGiveGold").html("+" + data + " G币");
				$("#signGiveGold").removeClass("fr").removeClass("alert-tmier").removeClass("alert-time");
				$("#signGiveGold").parent().removeClass("col-xs-3").addClass("col-xs-2");
				$("#taskName_0").html("（1/1）");
			    /*$("#userSignDays").html(parseInt(signDays) + 1);
				$("#signGiveGold").html("今天签到已获赠" + data + "G币");
				$(".add-gold").html("+" + data);
			    $(".add-gold").removeClass("hide").addClass("start");
			    setTimeout(function(){
			    	$(".add-gold").addClass("hide").removeClass("start");
			    },2000);*/
			    
			    // 右边你的账户签到处理
				if (source == "right") {
					$('#right_wqd').addClass("hide");
					$('#right_yqd').removeClass("hide");
					//$("#right_signDays").html(parseInt(signDays) + 1);
					$("#right_signGiveGold").html("每日签到，领取金币(1/1)");
				}
				
				// 右边任务中心签到处理
				if (source == "right_task") {
					$('#right_task_wqd').addClass("hide");
					$('#right_task_wqd').parent().find(".view1").css("width","0%");
					$('#right_task_yqd').removeClass("hide");
					$("#right_task_signGold").html("+" + data + " G币");
					$("#completedNum_0").html("（1/1）");
				}
			} else if (data == -1) {
				$('#task_wqd').addClass("hide");
			    $('#task_yqd').removeClass("hide");
			    
			    // 右边签到处理
			    $('#right_wqd').addClass("hide");
			    $('#right_yqd').removeClass("hide");
				//layer.alert("今天已经签过啦，明天再来吧", {icon: 5,shift: 6,time: 2000});
			}/* else{
				//layer.alert("签到失败，请刷新后再试", {icon: 5,shift: 6,time: 2000});
			}*/
		},
		complete:function(XMLHttpRequest,textStatus) {
	 		var status = XMLHttpRequest.getResponseHeader("session_status");  
	 		if(status == "timeout") {
	 			if (source == 'right') {
	 				$(".inc_right_tabs_account .inc_right_tabs_inside").after($("#inc_right_login"));
	 	            $("#inc_right_login").css({"top":"","bottom":"-395px"}).removeClass("hide");
	 			}else if (source == 'right_task') {
	 				$(".inc_right_tabs_task .inc_right_tabs_inside").after($("#inc_right_login"));
	 	            $("#inc_right_login").css({"top":"","bottom":"-395px"}).removeClass("hide");
	 			} else {
	 				$("#myModal").modal("show");
	 			}
	        }  
	    }
	});
}
//检索未读消息
function getUnread(){
	if(user!=null && user!=''){
		$.ajax({
			type : "POST",
			url : _path + "/my/message/getUnread",
			dataType : "json",
			success : function(resultdata) {
				if (resultdata.isSuccess) {
					var count = resultdata.count;
					if(count==0){
						$("#unread-count").html(count).addClass("hide");
					}else{
						$("#unread-count").html(count).removeClass("hide");
					}
				}else{
					$("#unread-count").addClass("hide");
				}
			},
			complete : function() {
				
			}
		});
	}
}

//金币修改方法
function updateUserGold(type,gold,sysGold){
	if(type==1){
		//1、值覆盖,初始化，充值
		_userGold = gold;
		_sysGold = sysGold;
	} else if (type==2) {
		// 2. 竞猜, 分gold值，根据G币
		if (_sysGold >= -gold) {
			_sysGold = _sysGold + gold;
		} else {
			_userGold = _userGold + gold + _sysGold;
			_sysGold = 0;
		}
	} else if (type==3) {
		// 4. 兑换，直接扣充值金币
		//取原值计算新值
		_userGold = _userGold + gold;
	} else if (type==4) {
		// 5. 签到，增加体验币
		_sysGold = _sysGold + sysGold;
	}
	/*else {
		//取原值计算新值
		_userGold = _userGold + gold;
	}*/
	$("#headerGold").html((_userGold + _sysGold) + "金币");
	$("#headerMenuGold").html(_userGold);
	$("#headerMenuSysGold").html(_sysGold);
	$("#userInfoGold").html(_userGold)
	
	// 右边钱包数据
	$("#right_walletGold").html(_userGold);
	$("#right_sysGold").html(_sysGold);
	
	if($(".ex8").length > 0 && $(".coinex8").length <= 0) {
		exchangeEx8();
	}
}

function hidePayAccount(alipayAccount) {
	if(alipayAccount == "" || alipayAccount == undefined) {
		return "";
	}
	if(alipayAccount.indexOf("@") != -1) {// 邮箱
		var array = alipayAccount.split("@");
		alipayAccount = array[0].substring(0,3) + "***@" + array[1];
	} else {
		alipayAccount = alipayAccount.substring(0,3) + "***" + alipayAccount.substring(7,11);
	}
	
	return alipayAccount;
}
