var gbId;
//赛事分页信息
var pageSize_ge = 5; //每页显示条数
var startRecord_ge = 0;	//开始记录数
var nowPage_ge = 1;  //当前页
var pageCount_ge = -1;	//总页数
var recordCount_ge =-1;	//总记录数
// 是否查询主客战队
var isHome = true;
var isAway = true;
$(function(){
	//比赛对战ID
	gbId = $("#gbId").val();
	// 加载比赛盘口列表
	loadGameBattleList(1, gbId);
	
	userJcList(true);
});

//用户竞猜列表
function userJcList(first){
	if(first){
		nowPage_ge = 1;
		pageCount_ge = -1;
	}
	//分页
	var pagesJson = pages(pageSize_ge,startRecord_ge,nowPage_ge,recordCount_ge,pageCount_ge);
	$.ajax({
		type : "POST",
		url : _path + "/userJc/gbUserJcList",
		data: {
			"gridPager": pagesJson,
			"gbId": gbId,
			"isHome": isHome,
			"isAway": isAway
		},
		dataType : "json",
		success : function(result) {
			if (result.isSuccess) {
				 //比赛用户竞猜列表信息
				getContent(result.homeList);
				getContent(result.awayList);
				 
				nowPage_ge = result.nowPage+1;
				pageCount_ge = result.pageCount;
				
				if(nowPage_ge <= pageCount_ge) {
					$("#jc_list").html('<div class="c-gdyh" onclick="userJcList(false)"><a>加载更多</a></div>');
				}else{
					$("#jc_list").html('<div class="c-gdyh"><a>没有更多了</a></div>');
				}
				
				if(!result.isHome) {
					isHome = result.isHome;
				}
				if(!result.isAway) {
					isAway = result.isAway;
				}
			}
		}
	});
}

function getContent(list) {
	if(list !=null && list.length > 0) {
		 var date = new Date();   
		 var userJc;
		 var content;
		 var jcGold;
		 for(var i=0;i<list.length;i++) {
			 userJc = list[i];
			 jcGold = userJc.jcGold.toString();
			 content = "";
			 content = content+ '<div class="row pt-10">';
			 content = content+ '	<div class="col-xs-2">';
			 content = content+ '		<a href="javascript:;"><img src="'+userJc.user.photo_65+'" width="48" height="48" class="img-circle c-img"/></a>';
			 content = content+ '	</div>';
			 content = content+ '	<div class="col-xs-10">';
			 content = content+ '	<div style="display: flex; justify-content: space-between; margin-bottom: 5px;">';
			 content = content+ '	<span class="c-gamer span60" title="'+userJc.user.nickName+'">'+userJc.user.nickName+'</span>';
			 content = content+ '	<span class="lv-x ml-5 span60"></span>';
			 content = content+ '	<span class="ml-5 span60" style="text-align: right; color: #bfc0c2; ">'+getTimeDifferenceFront(date.getTime(),userJc.jcTime)+'</span>';
			 content = content+ '	</div>';
			 
			 content = content+ '		<p class="c-whattime" style="display: flex; justify-content: space-between;">';
			 content = content+ '		<span class="span60" style="max-width: 110px; position: relative; line-height: 20px;">'+pankouPlay(userJc,userJc.homeTeam,userJc.awayTeam)+'</span>';
			 if(jcGold.length <= 6) {
				 content = content+ '	<span style="text-align: right;">'+jcGold+'金币</span></p>';
			 } else {
				 content = content + '	<span style="text-align: right;" title="'+jcGold+'">';
				 jcGold = jcGold.substring(0, 5) + "...";
				 content = content + jcGold+'金币</span></p>';
			 }
			 content = content+ '	</div>';
			 content = content+ '</div>';
			 
			 if("1" == userJc.jcTeamType) {// 主队
				 $("#homeUserJc").append(content);
			 } else {
				 $("#awayUserJc").append(content);
			 }
		 }
	 }
}
