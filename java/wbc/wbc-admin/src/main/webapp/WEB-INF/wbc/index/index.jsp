<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns:og="http://ogp.me/ns#" class="alt-layout">
<head>
    <jsp:include page="../include/common.jsp"/>
    <title>${seoConfigMap['2'].title }</title>
    <meta name="keywords" content="${seoConfigMap['2'].keywords }">
	<meta name="description" content="${seoConfigMap['2'].description }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/index_loading.css?v=${version }">
    <link rel="stylesheet" type="text/css" href="${staticPrefix }/css/index.css?v=${version }">
    <script type="text/javascript" src="${staticPrefix }/js/home/index.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/login/login.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/login/register.js?v=${version }"></script>
    <script type="text/javascript" src="${staticPrefix }/js/header.js?v=${version }"></script>
    <style>
        .dexlistwrap,.firstblood{
            display: flex;
            flex-direction:row;
            height: 104px;
            width: 862px; 
            color: #fff;
            position: relative;
    		z-index: 1;
        }
        .listbisaione,.listbisaitwo{
            display: flex;
            flex-direction:row;
            width: 355px;
        }
        .listbisaione>div,.listbisaitwo>div{
            flex: 1;
        }
        .vs-c{
            width: 162px;
            text-align: center;
            line-height: 106px;
        }
        .vs-c img{
            border-radius: 50%;
            height: 36px;
            width: 36px;
            line-height: 36px;
        }
        .item_c{
            vertical-align: middle;
        }
        .item_c img{ 
         	border-radius: 50%;
            height: 60px;
            width: 60px;
            line-height: 60px;
         }
         .titleimg{
            height: 32px;
            width: 32px;
            line-height: 32px;
            margin-right: 6px;
            transform: translateY(-5px);
         }
        .ssword{
            display: inline-block;
            width: 50px;
            height: 20px;
            line-height: 20px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .firstblood p{
            flex: 1;
            text-align: center;
        }
        .firstblood p:first-child{
            text-align: left;
        }
        .firstblood p:last-child{
            text-align: right;
        }
    </style>
</head>
<body>
    
    <div  class="bodyer"  style="z-index:-1;"></div>
	<div class="container-fluid"  style="margin: 0; padding: 0;">
        <div class="container" style="padding:0;">
           <div class="header" style="margin:0 15px;overflow:auto;">
                <div class="pull-left logo" style="margin:22px 0;z-index:1;position:relative;">
                	<a href="${path }/index" style="cursor: pointer;"><img src="${staticPrefix }/images/LOGO.png"></a>
                </div>
                <div class="pull-right">
				   <button type="button" class="btn btn-primary btn-raised br_8" style="margin:21px 1px;"  data-toggle="modal" data-target="#myModal2" ><strong>注  册</strong></button>
				   <button type="button" class="btn btn-default btn-raised br_8" style="margin:21px 1px;" data-toggle="modal" onclick="$('#myModal').modal('show')"><strong>登  录</strong></button>
                </div>
            </div>
            
            <div class="fix"></div>
      		<h5 class="text-center welc mt-80 zi-1" style="font-size:15px;letter-spacing: 2px;">WELCOME TO GBOCAI</h5>
      		<h1 class="text-center link-white mt-30 ft-60 zi-1 bold yh">专注电子竞技比赛竞猜</h1>
      		<h4 class="text-center welc mt-30 ft-16 zi-1 yh">通过你的知识、技艺参与比赛竞猜，并在比赛结束赢得相应的参与激励</h4>
   			<p class="text-center">     
  				<button type="button" class="btn btn-primary btn-raised mt-35 ft-18 br_8 yh" style="height:48px; width:265px;" data-toggle="modal" onclick="$('#myModal').modal('show')"><strong>开始竞猜</strong></button>
   			</p>   
   			<h2 class="text-center link-white mt-100 zi-1 yh"><strong>最近比赛</strong></h2>
        </div>
	</div>
             

	<div class="animated-background" id="index-load-div">
	   <div class="fix"></div>
	   <div class="left bj_color fl">
	      <div class="header_left fl gradient2"></div>
	      <div class="h_w10 header_left gradient2 fr"></div>    
	      <div class="c_w800">
	         <div class="c1 gradient"></div>
	         <div class="c2 gradient"></div>
	         <div class="c3 gradient"></div>
	         <div class="c3 gradient"></div>
	      </div>
	      <div class="flex_box">
              <div class="cs"> 
                  <ul>
                    <li class="">
                     <div class="lis gradient"></div>
                     <div class="lis fr gradient" style="width:50px;"></div>
                    </li>
                    <li class="ml10">
                      <div class="aric_big gradient"></div>
                    </li>
                     <li class="ml50">
                      <div class="aric_small gradient"></div>
                    </li>
                    <li class="ml50">
                      <div class="aric_big gradient"></div>
                    </li>
                    <li class="ml10">
                     <div class="lis gradient"></div>
                     <div class="lis fl gradient" style="width:50px;"></div>
                    </li>
                  </ul>
              </div>
	      </div>
	      
	      <div class="c_w800">
	         <div class="c1 gradient"></div>
	         <div class="c2 gradient"></div>
	         <div class="c3 gradient"></div>
	         <div class="c3 gradient"></div>
	      </div>
	      <div class="flex_box">
              <div class="cs"> 
                  <ul>
                    <li class="">
                     <div class="lis gradient"></div>
                     <div class="lis fr gradient" style="width:50px;"></div>
                    </li>
                    <li class="ml10">
                      <div class="aric_big gradient"></div>
                    </li>
                     <li class="ml50">
                      <div class="aric_small gradient"></div>
                    </li>
                    <li class="ml50">
                      <div class="aric_big gradient"></div>
                    </li>
                    <li class="ml10">
                     <div class="lis gradient"></div>
                     <div class="lis fl gradient" style="width:50px;"></div>
                    </li>
                  </ul>
              </div>
	      </div>
	      
	      <div class="c_w800">
	         <div class="c1 gradient"></div>
	         <div class="c2 gradient"></div>
	         <div class="c3 gradient"></div>
	         <div class="c3 gradient"></div>
	      </div>
	      <div class="flex_box">
              <div class="cs"> 
                  <ul>
                    <li class="">
                     <div class="lis gradient"></div>
                     <div class="lis fr gradient" style="width:50px;"></div>
                    </li>
                    <li class="ml10">
                      <div class="aric_big gradient"></div>
                    </li>
                     <li class="ml50">
                      <div class="aric_small gradient"></div>
                    </li>
                    <li class="ml50">
                      <div class="aric_big gradient"></div>
                    </li>
                    <li class="ml10">
                     <div class="lis gradient"></div>
                     <div class="lis fl gradient" style="width:50px;"></div>
                    </li>
                  </ul>
              </div>
	      </div>
	   </div>
	   <div class="right bj_color fr">
	   	   <div class="abox">
	   	   	   <div class="abox_l pulick_bj gradient2"></div>
	   	   	   <div class="abox_2 pulick_bj gradient2"></div>
	   	   	   <div class="abox_show">
	   	   	   	   <div class="abox_3 gradient2" style="width: 30%;"></div>
	   	   	   	   <div class="abox_3 gradient2"></div> 	   	   	 
	   	   	   </div>
	   	   </div>
	   	
	   	 <div class="abox" style="margin-top:15px;">
	   	   	   <div class="abox_l pulick_bj gradient2"></div>
	   	   	   <div class="abox_2 pulick_bj gradient2"></div>
	   	   	   <div class="abox_show">
	   	   	   	   <div class="abox_3 gradient2" style="width: 30%;"></div>
	   	   	   	   <div class="abox_3 gradient2"></div> 	   	   	 
	   	   	   </div>
	   	   </div>
	   	   
	   	    <div class="abox" style="margin-top:15px;">
	   	   	   <div class="abox_l pulick_bj gradient2"></div>
	   	   	   <div class="abox_2 pulick_bj gradient2"></div>
	   	   	   <div class="abox_show">
	   	   	   	   <div class="abox_3 gradient2" style="width: 30%;"></div>
	   	   	   	   <div class="abox_3 gradient2"></div> 	   	   	 
	   	   	   </div>
	   	   </div>
	   	   
	   	    <div class="abox" style="margin-top:15px;">
	   	   	   <div class="abox_l pulick_bj gradient2"></div>
	   	   	   <div class="abox_2 pulick_bj gradient2"></div>
	   	   	   <div class="abox_show">
	   	   	   	   <div class="abox_3 gradient2" style="width: 30%;"></div>
	   	   	   	   <div class="abox_3 gradient2"></div> 	   	   	 
	   	   	   </div>
	   	   </div>
	   </div>
	   
	   <div class="right bj_color fr" style="margin-top:20px;">
	   	   <div class="abox">
	   	   	   <div class="abox_l pulick_bj gradient2"></div>
	   	   	   <div class="abox_2 pulick_bj gradient2"></div>
	   	   	   <div class="abox_show">
	   	   	   	   <div class="abox_3 gradient2" style="width: 30%;"></div>
	   	   	   	   <div class="abox_3 gradient2"></div> 	   	   	 
	   	   	   </div>
	   	   </div>
	   	
	   	 <div class="abox" style="margin-top:15px;">
	   	   	   <div class="abox_l pulick_bj gradient2"></div>
	   	   	   <div class="abox_2 pulick_bj gradient2"></div>
	   	   	   <div class="abox_show">
	   	   	   	   <div class="abox_3 gradient2" style="width: 30%;"></div>
	   	   	   	   <div class="abox_3 gradient2"></div> 	   	   	 
	   	   	   </div>
	   	   </div>
	   	   
	   	    <div class="abox" style="margin-top:15px;">
	   	   	   <div class="abox_l pulick_bj gradient2"></div>
	   	   	   <div class="abox_2 pulick_bj gradient2"></div>
	   	   	   <div class="abox_show">
	   	   	   	   <div class="abox_3 gradient2" style="width: 30%;"></div>
	   	   	   	   <div class="abox_3 gradient2"></div> 	   	   	 
	   	   	   </div>
	   	   </div>
	   	   
	   	    <div class="abox" style="margin-top:15px;">
	   	   	   <div class="abox_l pulick_bj gradient2"></div>
	   	   	   <div class="abox_2 pulick_bj gradient2"></div>
	   	   	   <div class="abox_show">
	   	   	   	   <div class="abox_3 gradient2" style="width: 30%;"></div>
	   	   	   	   <div class="abox_3 gradient2"></div> 	   	   	 
	   	   	   </div>
	   	   </div>
	   </div>
	   
	   <div class="fix"></div>
	   <div class="slibder">
	   	   <div class="s_frist">
	   	       <div class="s_shide">  	       	
		   	   	   <div class="cad1 pulick_bj gradient2"></div>
		   	   	   <div class="abox_show" style="margin-top:8px;">
		   	   	   	   <div class="abox_3 gradient2"></div>
		   	   	   	   <div class="abox_3 gradient2" style="width:70%;"></div> 	   	   	 
   	   	           </div>
	   	       </div> 
	   	       <div class="s_shide">
	   	        	<div class="cad1 pulick_bj gradient2"></div>
   	        	    <div class="abox_show" style="margin-top:8px;">
		   	   	   	   <div class="abox_3 gradient2"></div>
		   	   	   	   <div class="abox_3 gradient2"  style="width:70%;"></div> 	   	   	 
	   	   	        </div>
	   	        </div> 
	   	        <div class="s_shide">
	   	        	<div class="cad1 pulick_bj gradient2"></div>
   	        	    <div class="abox_show" style="margin-top:8px;">
		   	   	   	   <div class="abox_3 gradient2"></div>
		   	   	   	   <div class="abox_3 gradient2"  style="width:70%;"></div> 	   	   	 
	   	   	        </div>
	   	        </div> 
	   	   </div>
	   	
	   		<div class="fix"></div>
	   		<div class="s_frist" style="margin:40px -20px 0 55px;">
				  <div class="fL">
				  	<ul>
				  		  <li>
	                          <span class="fr gradient2"></span>
	                          <div class="f1_1 gradient2"></div>
	                      </li>
	                      <li style="margin-top:8px;">
	                             <span class="fr gradient2"></span>
	                             <div class="f1_1 gradient2" style="width:15%;"></div>
	                      </li>
				  	</ul>
				  	<div class="fix"></div>
				  	<ul style="margin-top:50px;">
				  		  <li>
						      <span class="fr gradient2"></span>
	                      	  <div class="f1_1 gradient2"></div>
	                      </li>
				  		  <li style="margin-top:8px;">
	                          <span class="fr gradient2"></span>
	                          <div class="f1_1 gradient2" style="width:15%;"></div>
	                      </li>
				  	</ul>
				  	<div class="fix"></div>
				  	<ul style="margin-top:50px;">
				  		  <li>
				  		  	<span class="fr gradient2"></span>
				  		  	<div class="f1_1 gradient2"></div>
				  		  </li>
				  		  <li style="margin-top:8px;">
	                      	<span class="fr gradient2"></span>
	                      	<div class="f1_1 gradient2" style="width:15%;"></div>
	                      </li>
				  	</ul>
				  </div>
				  <div class="fL">
		              <div class="h_50"></div>
		              <p class="h_50" style="margin-top:20px;"></p>
		              <p class="h_50" style="margin-top:20px;"></p>
				  </div>
				  <div class="fL">
				  	<div class="h_30"></div>
				    <div class="rig_bid">
				    	<div class="bid_1 gradient2"></div>
				    	<div class="bid_2 gradient2"></div>
				    	<div class="bid_3 gradient2"></div>
				    </div>
				    
				     <div class="rig_bid">
				    	<div class="bid_1 gradient2"></div>
				    	<div class="bid_2 gradient2"></div>
				    	<div class="bid_3 gradient2"></div>
				    </div>
				    
				     <div class="rig_bid">
				    	<div class="bid_1 gradient2"></div>
				    	<div class="bid_2 gradient2"></div>
				    	<div class="bid_3 gradient2"></div>
				    </div>
				  </div>
	   		</div>
	  </div>
	  
	  <div class="fix"></div>
	  <div class="last">
	  	<div class="m-1 gradient"></div>
	  	<div class="m-1 gradient"></div>
	  	<div class="m-1 gradient" style=" margin-right: 0;"></div>
	  	</div>
	</div>
 
	<div id="index-main-div" class="hide">
	
	     <div class="container-fluid"  style="margin: 0; padding: 0;">
	        <div class="container" style="padding:0;">
				<div class="row mt-60" style="padding:0; margin:0">
	      			<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9" style="padding:0 10px 0 0;">
						<div class="match_thing">
	             			<div class="title">
	                  			<ul>
	                      			<li class="bold yh">赛事名称</li>
	                      			<li class="text-center bold yh">对阵</li>
	                      			<li class="text-center bold yh" style="float: right;width: 20%;text-align:left;padding:0;">开始倒计时</li>
	                      			<a href="${path }/match" class="more yh">更多>></a>
	                  			</ul>
	             			</div>
	             			<!-- 最近比赛 -->
	             			<div id="latelyGameBattle"></div>
	        			</div>
	      			</div>
	      			
	      			<div class="col-xs-2 hidden-xs hidden-sm col-md-3 col-lg-3" style="padding:0 0px 0 0;">
	      				<div class="profit_top" id="profitTopList-div">
	      					<div class="title">盈利TOP</div>
	      				</div>
	      	
	      				<div class="profit_top mt-15" id="profitRateTopList-div">
	      					<div class="title">胜率TOP</div>
	      			</div>
	      		</div>
	   		</div>
	
	  		<h2 class="text-center link-white mt-100 mb-50 zi-1 yh"><strong>如何竞猜</strong></h2>
	  		<div class="row zi-1 yh" style="padding:35px 70px 15px 70px;background-color:#2e3238;border-radius:8px;">
	  	   		<div class="col-xs-4 pr-40">    
					<div class="media">
						 <span class="pull-left">
						 	<a class="get">1</a>
						 </span>
						 	
						<div class="media-body">
							<h4 class="media-heading link-white ft-16 bold">
								注册即送G币奖励
							</h4> 
							<p class="link-blue">赠送100G币作为注册奖励</p>
						</div>
					</div>
				
				    <div class="page-header link-white mt-45">
				    	<p><span class="fr">100G币</span>
				    	王者荣耀</p>
				    	<p class="small link-blue">
				    		<span class="fr"><a href="javascript:;">奖金</a> </span>
				    		<a href="javascript:;">比赛</a>
				    	</p>
				    </div>	
			    
				    <div class="page-header link-white">
				    	<p><span class="fr">100G币</span>
				    	英雄联盟</p>
				    	<p class="small link-blue">
				    		<span class="fr"><a href="javascript:;">奖金</a> </span>
				    		<a href="javascript:;">比赛</a>
				    	</p>
				    </div>	
			    
			    	<div class="page-header link-white">
				    	<p><span class="fr">100G币</span>
				    	守望先锋</p>
				    	<p class="small link-blue">
				    		<span class="fr"><a href="javascript:;">奖金</a> </span>
				    		<a href="javascript:;">比赛</a>
				    	</p>
				    </div>	
			
	  	   		</div>
	  	     	<div class="col-xs-4 pr-40" >
		  	     	<div class="media">
						 <span class="pull-left">
						 	<a class="get">2</a>
						 </span>
						 	
						<div class="media-body">
							<h4 class="media-heading link-white ft-16 bold">
								选择你认为会获胜的队伍
							</h4> 
							<p class="link-blue">开始下注并确认</p>
						</div>
					</div>
	  	     		<div class="page-header-groub link-white mt-20">
			  			<table class="table">
							<tbody>
								<tr>
									<td width="12%" align="right"><p>IG</p><p>47%</p></td>
									<td width="13%"><img src="${staticPrefix }/images/r_ic.png" width="50" height="50"></td>
									<td width="12%"><img src="${staticPrefix }/images/r_vs_small.png"></td>
									<td width="7%"><img src="${staticPrefix }/images/r_logo.png" width="50" height="50"></td>
									<td width="16%" align="left"><p>LGD</p><p>53%</p></td>
								</tr>
								<tr>
									<td width="12%" align="right"><p>IG</p><p>47%</p></td>
									<td width="13%"><img src="${staticPrefix }/images/r_ic.png" width="50" height="50"></td>
									<td width="12%"><img src="${staticPrefix }/images/r_vs_small.png"></td>
									<td width="7%"><img src="${staticPrefix }/images/r_logo.png" width="50" height="50"></td>
									<td width="16%" align="left"><p>LGD</p><p>53%</p></td>
								</tr>
								<tr>
									<td width="12%" align="right"><p>IG</p><p>47%</p></td>
									<td width="13%"><img src="${staticPrefix }/images/r_ic.png" width="50" height="50"></td>
									<td width="12%"><img src="${staticPrefix }/images/r_vs_small.png"></td>
									<td width="7%"><img src="${staticPrefix }/images/r_logo.png" width="50" height="50"></td>
									<td width="16%" align="left"><p>LGD</p><p>53%</p></td>
								</tr>
							</tbody>
					    </table>
			    	</div>	
	  	     	</div>
	  	       	<div class="col-xs-4">
		  	       	<div class="media">
						 <span class="pull-left">
						 	<a class="get">3</a>
						 </span>
						 	
						<div class="media-body">
							<h4 class="media-heading link-white ft-16 bold">
								商城兑换你喜欢的礼品
							</h4> 
							<p class="link-blue">赢得相应的参与激励</p>
						</div>
					</div>
			  	    <div class="tip mt-20">
			  	     	2000
			  	     	<div class="gren"></div>
			  	     	<div class="pos"></div>
			  	     </div>
			    
				    <div class="page-header-3 link-white">
				    	<span class="text-right fr">720000金币</span>
				    	<p class="text-left text-st">1<font class="st"></font>
				    		<span><img src="${staticPrefix }/images/jj_head.png" class="img-circle" width="32" height="32"></span>
				    		<font class="link-white">Iphone 7 Plus</font>
				    	</p>
				    </div>	
			    
				     <div class="page-header-3 link-white">
				    	<span class="text-right fr">1000金币</span>
				    	<p class="text-left text-st">2<font class="ed"></font>
				    		<span><img src="${staticPrefix }/images/jj_head_1.png" class="img-circle" width="32" height="32"></span>
				    		<font class="link-white">骏网一卡通10元</font>
				    	</p>
				    </div>	
			    
				     <div class="page-header-3 link-white">
				    	<span class="text-right fr">100金币</span>
				    	<p class="text-left text-st">3<font class="rd"></font>
				    		<span><img src="${staticPrefix }/images/jj_head_2.png" class="img-circle" width="32" height="32"></span>
				    		<font class="link-white">1Q币</font>
				    	</p>
				    </div>	
	  	       </div>
	  		</div>
	  
	  		<h2 class="text-center link-white mt-70 mb-50 zi-1 yh"><strong>热门游戏</strong></h2>
	  		<div class="row pic_pot" style="margin: 0 20px;" id="game-div"></div>
		</div>
	     
		<div class="fix fix1"></div>
	
		
	       
		</div> 
		<!-- 充值弹框 -->
	 	<jsp:include page="../include/recharge.jsp" /> 
		<!--登录弹框-->
		<jsp:include page="include/login_dialog.jsp" />
		<!--注册弹框-->
		<jsp:include page="include/register_dialog.jsp" />
	</div>  
	 
	<!--footer ==S-->
	<jsp:include page="../../wbc/include/footer.jsp"/>
	<!--footer ==S-->
</body>
<script>
  $.material.init();
</script>

<script type="text/javascript">
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
    
    var resourcesLoad = false;
    var ajaxLoad = false;
    
  	//预加载-静态资源判断
    window.onload = function(){
    	resourcesLoad = true;
	}
    
    $(function(){
    	//预加载-ajax判断
    	$(document).ajaxStop(function(){
			ajaxLoad = true;
		});
    	
    	 var showMain = setInterval(function(){
			if(resourcesLoad && ajaxLoad){
	   	  	  	$(document).unbind('ajaxStop');
	       	  	$("#index-load-div").addClass("hide");
	       	  	$("#index-main-div").removeClass("hide");
	       	 	NProgress.done();
	       	 	clearInterval(showMain);
			}
		},100); 
    })
</script>

</html>
