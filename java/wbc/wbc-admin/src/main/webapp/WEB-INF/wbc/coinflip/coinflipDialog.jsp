<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%-- 创建弹框 --%>
 <div class="modal fade chuang" id="createCoinflipRoom" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="${staticPrefix }/images/close_new.png" class="mCS_img_loaded"></button>
               <h3 class="modal-title" id="myModalLabel">创建</h3>         
            </div>
            <div class="modal-body">
            	<div class="typeselect">
					<div class="imgwrap zheng xzactive">
						<img src="${staticPrefix }/images/zheng.png" alt="" width=100% height=100%>
					</div>
					<div class="imgwrap">
						<img src="${staticPrefix }/images/fan.png" alt="" width=100% height=100%>
					</div>
            	</div>
            	<div class="cjpass">
						<!-- <div class="form-group checkboxwrap">
						  <div class="checkbox">
						    <label>
						      <input type="checkbox" id="isSetPassword" checked="checked">
						    </label>
						  </div>
						</div> -->
					<div class="form-group label-floating">
					   <label for="roomPassword" class="control-label">设置密码</label>
					   <input type="password" class="form-control" id="roomPassword">
					   <span class="help-block" id="roomPasswordError"><code style="background: transparent;">密码可以是最多6位的数字和字母组合</code></span>
					</div>
            	</div>
            	<!-- <div class="suoha">
					<p><span class="ex6CurrentSliderValLabel">我要下注<span class="ex6SliderVal iconfont icon-jinbi">1000</span></span></p>
					<div class="lslider">
						<span class="iconfont icon-jianshao"></span>
						<input id="betGoldNum" type="text" data-slider-min="1000" data-slider-max="500000" data-slider-step="10" data-slider-value="1000"/>
						<span class="iconfont icon-zengjia"></span>
					</div>
					<p>
						<span class="ex6SliderVal">1000</span>金币
						<span class="zhongjian"></span>
						<span class="allin" style="cursor: pointer;">All in</span>
					</p>
            	</div> -->
            	
            	<div class="suoha match-list-bottom-pour" id="nobet">
			 		<p class="view1">
			            <span class="co_be">我要下注</span>
			            <span class="one iconfont icon-jinbi"></span>
			            <input type="text" class="ft_18_bo" placeholder="0" value="1000">
			        </p>
					<p class="mt-20">
			            <input class="ex8 coinex8" id="betGoldNum" data-slider-id='ex1Slider' type="text" data-slider-min="1000" data-slider-max="500000" data-slider-step="1" data-slider-value="1000"/>
			        </p>
					<p class="view4">
			            <span class="co_be pull-left">1000金币</span>
			            <a class="co_be pull-right two" href="javascript:;">All in</a>
			        </p>
            	</div>
            	<p class="jieshao">下注金币数最少1000金币，最多500000金币数，超过100000金币数可以多人参与</p>
        		<button class="btn btn-primary btn-block btn-raised chuangjian" id="createRoomBtn" ondblclick="return false;">创建</button>  
             </div> 
        </div>
           
        </div>
    </div>
<%-- 加入游戏弹框 --%>
<div class="modal fade addru" id="joinCoinflip" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 <input type="hidden" id="joinRoomId" />
 <div class="modal-dialog addmm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="${staticPrefix }/images/close_new.png" class="mCS_img_loaded"></button>
               <h3 class="modal-title" id="myModalLabel">房间密码</h3>         
            </div>
            <div class="modal-body">
            	<div class="cjpass">
					<div class="form-group label-floating" style="margin-left: 0px">
					   <label for="joinPassword" class="control-label">输入房间密码</label>
					   <input type="password" class="form-control" id="joinPassword">
					   <span class="help-block" id="joinPasswordError"><code style="background: transparent;">请输入正确的房间密码</code></span>
					</div>
            	</div>
            	<p class="jieshao">可通过联系房主获取密码</p>
        		<button class="btn btn-primary btn-block btn-raised chuangjian" id="joinPasswordBtn">加入</button>  
             </div> 
        </div>

        </div>
                <div class="modal-dialog addhuman">
          <div class="modal-content">
			<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="${staticPrefix }/images/close_new.png" class="mCS_img_loaded"></button>
               <h3 class="modal-title" id="myModalLabel">加入</h3>         
            </div>
            <div class="modal-body">
            </div>
          </div> 
          </div>
    </div>
    
    <%-- 查看弹框 --%>
<div class="modal fade addru" id="viewCoinflip" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<input type="hidden" id="viewRoomId" />
<div class="modal-dialog addhuman" style="display: block;">
          <div class="modal-content">
			<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="/resources/wbc/images/close_new.png" class="mCS_img_loaded"></button>
               <h3 class="modal-title" id="myModalLabel">
                    详情
                </h3>         
            </div>
            <div class="modal-body">
            	
            </div>
          </div> 
          </div>
    </div>