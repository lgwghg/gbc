<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--登录弹框-->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin: -240px 0px 0px -300px;position: fixed;top: 50%;left: 50%;">
        <div class="modal-content" style="background:#2e3238; color:#fff; width: 600px; height:480px;">
        	<div class="modal-header-new">
        		<img src="${staticPrefix }/images/denglu_zhuce_header.png" class="modal-header-new-bg">
        		<img src="${staticPrefix }/images/LOGO_nobeta2.png" class="modal-header-new-logo">
        		<p>登录G菠菜，竞技从这里开始</p>
        		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="${staticPrefix }/images/close_new.png"></button>
        	</div>
            <div class="modal-header" style="overflow: hidden;">
                <h2 class="modal-title" id="myModalLabel" style=" font-size: 20px;float:left;"><b>账号登录</b></h2>
                <a href="javascript:;" style="text-decoration:underline; color:#31bc9c;float:right;line-height: 28px;" onclick="$('#myModal').find('.close').click();" data-toggle="modal" data-target="#myModal2">现在注册</a>
    			<span style="color: #d4d4d4;float:right;line-height: 28px;">新成员？</span>
            </div>
            <div class="modal-body">
           	<!-- <p style="text-align: center;"><img src="" alt="" style=" width: 70px; height: 70px; line-height:70px; text-align: center;"></p>
        		<p class=" mt-10" style="text-align: center;">“怦然”<a style=" color: #31C3A2;  text-decoration: underline;">不是你？</a></p> 
               --><form id="loginform" name="loginform" action="${ctx }/login" method="post">
              		  <input name="email" id="email" type="hidden" />
					  <input name="mobile" id="mobile" type="hidden" />
                      
                      <div id="historyLog" style="display:none">
						<p style="text-align: center;"><img src="" class="img-circle" id="userPhoto" alt="" style=" width: 70px; height: 70px; line-height:70px; text-align: center;"></p>
		        		<p class=" mt-10" style="text-align: center;"><font id="nickName"></font><a style=" color: #31C3A2;  text-decoration: underline;" id="notMe">不是你？</a></p>
		        	  </div>
		              <div class="form-group label-floating" id="loginNameDiv">
                        <label for="loginName" class="control-label">手机号/邮箱</label>
                        <input type="text" class="form-control" name="loginName" id="loginName">
                        <span class="help-block" id="loginNameError"></span>
                      </div>
                      <div class="form-group label-floating">
                        <label for="password" class="control-label">密码</label>
                        <input type="text" class="form-control" name="password" id="password" autocomplete="off" onfocus="this.type='password'">
                        <span class="help-block" id="passwordError"></span>
                      </div>
              </form>
            </div>
            <div class="modal-footer" style="text-align: left;">
            <div style="padding: 0 17px;margin-top:10px;">
            <p style="margin-bottom: -5px;">
              <a href="${ctx }/fp/forgetPassword" style="text-decoration:underline; color:#31bc9c;">忘记密码？</a><br/></p>
              <button type="button" class="btn btn-primary btn-raised pull-right"  style="width: 84px; height: 30px; padding-top: 4px; transform:translateY(-21px);" onclick="login();"><strong>登 录</strong></button>
            </div>  
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    </div>