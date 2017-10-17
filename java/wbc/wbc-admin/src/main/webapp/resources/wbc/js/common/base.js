// JavaScript Document
$.fn.extend({
    //checkbox 美化
    hcheckbox:function(options){
	$(':checkbox+label',this).each(function(){
	$(this).addClass('checkbox');
	if($(this).prev().is(':disabled')==false){
	if($(this).prev().is(':checked'))
	$(this).addClass("checked");
	}else{
	$(this).addClass('disabled');
	}
	}).click(function(event){
	if(!$(this).prev().is(':checked')){
	$(this).addClass("checked");
	$(this).prev()[0].checked = true;
	}
	else{
	$(this).removeClass('checked');
	$(this).prev()[0].checked = false;
	}
	event.stopPropagation();
	}
	).prev().hide();
	},
	
	
	
	
	//select美化.start
	divSeleFn:function(){
		$(this).each(function(){
			var _method= this;
			var _cur = $(_method).find('.sele_cur');
			var _drop = $(_method).find('.sele_drop');
            var _dropAll = $('.sele_drop').not(_drop);
			$(_method).click(function(){
                _cur.toggleClass('open');
				_drop.toggle().css('z-index','9999999');
                //$(this).parents().siblings().find('.sele_drop').hide();
                 _dropAll.hide();
				return false;
			})
			$(_method).find('.sele_drop ul li').click(function(){
				_cur.removeClass('open').children('p').text($(this).text());
				_drop.hide();
				return false;
			}).hover(
				function(){$(this).addClass('hover');},
				function(){$(this).removeClass('hover');}
			);
		})
		$(document).click(function(){
			$('.sele_drop').hide();
			$('.sele_cur').removeClass('open');  
			
		})
	},	  
	});
	


//选项卡
function oTab(obj,oTab,oList){
	var oBj=$(obj);
	var aTab=oBj.find(oTab);
	var aList=oBj.find(oList);
	for(var i = 0; i < aTab.length; i++){
		aTab.eq(0).css("display","block");
		}
	aTab.find("li").click(function(){
		aTab.find("li").removeClass("cur");
		$(this).addClass("cur");
		aList.css("display","none");
		aList.eq($(this).index()).fadeIn(0);
		aList.eq($(this).index()).css("display","block");
	})
}


//点击左右滚动
function Mantle(obj){	
	var oMantle=$(obj);
	var oPrev=oMantle.find(".prev");
	var oNext=oMantle.find(".next");
	var oUl=oMantle.find("ul");
	var oFistLih=oUl.find("li:first").outerWidth(true);
	var oUlh=oFistLih*oUl.find("li").length;
	var onOff = true;
	var timer=null;
	
	oUl.css("width",oUlh);
	
	//自动播放
	/*oMantle.timer=setInterval(function(){
		fnScroll(1)
	},3000)*/
	
	//下一个
	oNext.click(function(){
		if(!onOff)return;
		fnScroll(1)
	})
	
	//上一个
	oPrev.click(function(){	
		if(!onOff)return;
		fnScroll(2)	
	})
	
	//鼠标移上停止播放
	oMantle.mouseover(function(){
		clearInterval(oMantle.timer);
	})
	//鼠标移出继续播放
	/*oMantle.mouseout(function(){
		clearInterval(oMantle.timer);
		oMantle.timer=setInterval(function(){
			fnScroll(1)
		},3000)
	})*/
	
	function fnScroll(dir){		
		onOff = false;
		if(oUl.find("li").length<=5){
			clearInterval(oMantle.timer);
		}
		if(dir==1){
			oUl.css("left",0).stop().animate({left:-oFistLih+"px"},"slow",function(){
				oUl.find("li:first").appendTo(oUl);
				$(this).css({'left':'0'});
				onOff = true;
			});
		}
		if(dir==2){
			oUl.find("li:last").prependTo(oUl);
			oUl.css("left",-oFistLih).stop().animate({left:0},"slow",function(){onOff = true;});
		}
	}
}

  //input获取焦点
    $(function(){
    	$(":text").focus(function(){
            $(this).addClass('focus');
            if($(this).val()==this.defaultValue){
                $(this).val('');
            }
        }).blur(function(){
            $(this).removeClass('focus');
            if($(this).val()==''){
                $(this).val(this.defaultValue);
            }

        })

    });
	
	
	




//返回顶部
var ScrollToTop=ScrollToTop||{
	setup:function(){						
	var a=$(window).height()/3;
	$(window).scroll(function(){
	(window.innerWidth?window.pageYOffset:document.documentElement.scrollTop)>=a?$("#ScrollToTop").removeClass("Offscreen"):$("#ScrollToTop").addClass("Offscreen")
	});
	$("#ScrollToTop").click(function(){
	$("html, body").animate({scrollTop:"0px"},400);
							return false
						})
					}
				};
				
				
$.fn.smartFloat = function() {
 var position = function(element) {
  var top = element.position().top, pos = element.css("position");
  $(window).scroll(function() {
   var scrolls = $(this).scrollTop();
   if (scrolls > top) {
    if (window.XMLHttpRequest) {
     element.css({
      position: "fixed",
      top: 0
     }); 
    } else {
     element.css({
      top: scrolls
     }); 
    }
   }else {
    element.css({
     position: pos,
     top: top
    }); 
   }
  });
 };
 return $(this).each(function() {
  position($(this));      
 });
};

/**
 * @title : 分页
 * pageSize 每页显示条数
 * startRecord 开始记录数 初始化 0
 * nowPage 当前页 初始化 1 
 * recordCount 记录总数 初始化 -1
 * pageCount 总页数 初始化 -1
 * */
function pages(pageSize,startRecord,nowPage,recordCount,pageCount){
	return '{"pageSize":"'+pageSize+'","startRecord":"'+startRecord+'","nowPage":"'+nowPage+'","recordCount":"'+recordCount+'","pageCount":"'+pageCount+'"}';
}

//文字间歇自动向上滚动
;(function($){
 $.fn.textSlider = function(options){
   var defaults = { //初始化参数
      scrollHeight:25,
      line:1,
      speed:'normal',
      timer:5000
   };
   var opts = $.extend(defaults,options);
   this.each(function(){
     var timerID;
     var obj = $(this);
     var $ul = obj.children("ul");
     var $height = $ul.find("li").height();
     var $Upheight = 0-opts.line*$height;
     obj.hover(function(){
       clearInterval(timerID);
     },function(){
       timerID = setInterval(moveUp,opts.timer);
       });
     function moveUp(){
       $ul.animate({"margin-top":$Upheight},opts.speed,function(){
          for(i=0;i<opts.line;i++){ //只有for循环了才可以设置一次滚动的行数
           $ul.find("li:first").appendTo($ul);
          }
         $ul.css("margin-top",0);
       });
     };
     timerID = setInterval(moveUp,opts.timer);
     });
   };
})(jQuery)

//去左右空格;
function trim(s){
    return s.replace(/(^\s*)|(\s*$)/g, "");
}

//返回字符串长度，英文占1个字符，中文汉字占2个字符
function strLength(str){
	  var len = 0;  
	  for (var i=0; i<str.length; i++) {  
	    if (str.charCodeAt(i)>127 || str.charCodeAt(i)==94) {  
	       len += 2;  
	    }else {  
	       len ++;  
	    }  
	  }  
	  return len;  
}

//将form转为AJAX提交
function ajaxSubmit(frm, fn) 
{
	var dataPara = getFormJson(frm);
  
	$.ajax({
		url: frm.action,
		type: frm.method,
		dataType : "json",
		data: dataPara,
		success: fn
	});
}

//将form中的值转换为键值对。
function getFormJson(frm) 
{
	var o = {};
	var a = $(frm).serializeArray();
  
	$.each(a, function ()
    {
		if (o[this.name] !== undefined) 
		{
			if (!o[this.name].push) 
          	{
				o[this.name] = [o[this.name]];
          	}
          
			o[this.name].push(this.value || '');
		} 
		else 
      	{
			o[this.name] = this.value || '';
      	}
    });

	return o;
}

function getTimeDifference(thisTime,endtime){
//	  var result = "";
//	  //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////		  Date d1 = df.parse("2016-05-23 15:00:00");
////		  
////		  Date d2 = df.parse("2016-06-22 11:00:00");
//	  var diff = startTime - endtime;//这样得到的差值是微秒级别
//	  var  days = Math.floor(diff / (1000 * 60 * 60 * 24));
//	 
//	  var hours = Math.floor((diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60));
//	  var minutes = Math.floor((diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60));
//	  if(days!=0){
//		  result = result.concat(days+"天前") ;
//		  return result;
//	  }
//	  if(hours!=0){
//		  result = result.concat(hours+"小时前");
//		  return result;
//	  }
//	  if(minutes!=0){
//		  result = result.concat(minutes+"分钟前");
//		  return result;
//	  }else if(minutes ==0 ){
//		  result = result.concat("刚刚");
//		  return result;
//	  }
//	return result;
	
	var date3 = endtime - thisTime;  //时间差的毫秒数  
	  
  //计算出相差天数  
  var days = Math.floor(date3 / (24 * 3600 * 1000));  

  //计算出小时数  
  var leave1 = date3 % (24 * 3600 * 1000);    //计算天数后剩余的毫秒数  
  var hours = Math.floor(leave1 / (3600 * 1000));  

  //计算相差分钟数  
  var leave2 = leave1 % (3600 * 1000);        //计算小时数后剩余的毫秒数  
  var minutes = Math.floor(leave2 / (60 * 1000));  

  //计算相差秒数  
  var leave3 = leave2 % (60 * 1000);      //计算分钟数后剩余的毫秒数  
  var seconds = Math.round(leave3 / 1000);  

  var strTime = "";  
  if (days >= 1) {  
      strTime = days + "天后";  
      return strTime;
  } else if (hours >= 1) {  
      strTime = hours + "小时后";  
      return strTime;
  } else if(minutes>=1){  
      strTime = minutes + "分钟后";  
      return strTime;
//      if(minutes>=3){
//    	  return strTime;
//      }
  } else{
  	 strTime = "即将开始";  
      return strTime;
  }
  return strTime;  
}

function getTimeDifferenceFront(thistTime,endtime){	
var date3 = thistTime - endtime;  //时间差的毫秒数  
	  
//计算出相差天数  
var days = Math.floor(date3 / (24 * 3600 * 1000));  

//计算出小时数  
var leave1 = date3 % (24 * 3600 * 1000);    //计算天数后剩余的毫秒数  
var hours = Math.floor(leave1 / (3600 * 1000));  

//计算相差分钟数  
var leave2 = leave1 % (3600 * 1000);        //计算小时数后剩余的毫秒数  
var minutes = Math.floor(leave2 / (60 * 1000));  

//计算相差秒数  
var leave3 = leave2 % (60 * 1000);      //计算分钟数后剩余的毫秒数  
var seconds = Math.round(leave3 / 1000);  

var strTime = "";  
if (days >= 1) {  
    strTime = days + "天前";  
    return strTime;
} else if (hours >= 1) {  
    strTime = hours + "小时前";  
    return strTime;
} else if(minutes>=1){  
    strTime = minutes + "分钟前";  
    return strTime;
} else{
	strTime = "刚刚";  
    return strTime;
}
return strTime;  
}

//生成随机数
var randomChars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

function generateMixed(n) {
     var res = "";
     for(var i = 0; i < n ; i ++) {
         var id = Math.ceil(Math.random()*35);
         res += randomChars[id];
     }
     return res;
}

function isEmail(mail) {  
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;  
    if (filter.test(mail)) {  
        return true;   
    } else {  
        return false;  
    }   
} 

function isPositiveNum(s){//是否为正整数  
    var re = /^[0-9]*[1-9][0-9]*$/ ;  
    return re.test(s)  
}

//lwh:判断对象是否为空
function judge(obj){
　　for(var i in obj){//如果不为空，则会执行到这一步，返回true
 　　　　return true;
 　　}
　　 return false;
}

//lwh:通过玩盘口判断玩法名称
function pankouPlay(obj,tameNameA,tameNameB){	//参数为盘口对象、主战队名称、客战队名称
	if(judge(obj)){
		if(obj.pankouType=='0'){	//独赢
			return "独赢";
		}else if(obj.pankouType=='1'){	//让分
			var teamName = "";
			if(obj.pkRangFenTeam=='1'){
				teamName = tameNameA
			}else{
				teamName = tameNameB;
			}
			if(teamName.length <= 3) {
				return "<span>让分局 [</span><span class='xa-rf' title='"+teamName+"'>"+teamName+"</span><span>-"+obj.pkRangfenNum+"]</span>";
			} else {
				var _teamName = teamName.substring(0, 2) + "...";
				return "让分局 [<span class='xa-rf' title='"+teamName+"'>"+_teamName+"</span>-"+obj.pkRangfenNum+"]";
			}
		}else{
			return "第"+obj.inningNum+"局"+obj.pkName;
		}
	}
	return "玩法读取出错(盘口为空)";
}