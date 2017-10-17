<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--注册弹框-->
   <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="top: 50%; left: 50%;position: absolute; top:50%; left:50%; transform:translate(-50%,-50%);">
        <div class="modal-content" style="background:#2e3238; color:#fff; width: 600px;height:782px;">
        	<div class="modal-header-new">
        		<img src="${staticPrefix }/images/denglu_zhuce_header.png" class="modal-header-new-bg">
        		<img src="${staticPrefix }/images/LOGO_nobeta2.png" class="modal-header-new-logo">
        		<p>加入G菠菜，竞技从这里开始</p>
        		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="${staticPrefix }/images/close_new.png"></button>
        	</div>
            <div class="modal-header" style="overflow: hidden;">
                <h2 class="modal-title" id="myModalLabel" style=" font-size: 20px;float:left;"><b>注册开始竞猜</b></h2>
                <a href="javascript:;" style="text-decoration:underline; color:#31bc9c;float:right;line-height: 28px;" onclick="$('#myModal2').find('.close').click();" data-toggle="modal" data-target="#myModal">登录</a>
    			<span style="color: #d4d4d4;float:right;line-height: 28px;">已经是会员？</span>
            </div>
            <div class="modal-body">
              <form method="post" action="" id="registerForm">
              	<input type="hidden" id="token" name="token" value="${token}">
	              <div class="form-group label-floating" style="">
	                <label for="nickName" class="control-label">昵称,注册后不能修改</label>
	                <input type="text" class="form-control" id="rnickName"  maxlength="13">
	                <span class="help-block" id="nickNameError"></span>
	              </div>
	              <div class="form-group label-floating">
	                <label for="rpassword" class="control-label">密码</label>
	                <input type="text" class="form-control" id="rpassword"  autocomplete="off" onfocus="this.type='password'">
	                <span class="help-block" id="rpasswordError"></span>
	              </div>
	              <div class="form-group label-floating">
	                <label for="rePassword" class="control-label">确认密码</label>
	                <input type="text" class="form-control"  id="rePassword" autocomplete="off" onfocus="this.type='password'">
	                <span class="help-block" id="rePasswordError"></span>
	              </div>
	              <div class="form-group label-floating">
	                <label class="control-label">邀请码 (选填)</label>
	                <input type="text" class="form-control" id="fromuid" name="fromuid">
	                <span class="help-block"></span>
	              </div>
	              <div class="form-group label-floating" style=" position: relative;">
	                <label for="rmobile" class="control-label">手机号,用做登录账号</label>
	                <input type="text" class="form-control" name="mobile" id="rmobile" onkeyup="checkMobile()">
	                <span class="help-block" id="mobileError"></span>
	              </div>
	              <div class="form-group label-floating" style="float:left;width:48%;">
	                <label for="captcha" class="control-label">手机动态码</label>
	                <button type="button" id="countdown" class="btn btn-primary btn-raised pull-right send-auth-code" style="position: absolute; top:-4px; right: 4px; width: 98px; height: 24px; line-height: 8px; padding: 0px; background-color: #607d8b;"><strong>获取动态码</strong></button>
	                <input type="text" class="form-control" name="captcha" id="captcha" style="padding-right:110px;">
	                <span class="help-block" id="captchaError"></span>
	              </div>
	              <div class="form-group label-floating hide" style="float:right;width:48%;">
	                <label class="control-label">验证码</label>
	                <input type="text" class="form-control" name="captcha" id="imgCaptcha" style="padding-right:110px;" onkeyup="checkImgCaptcha()">
	                <span class="help-block" id="imgCaptchaError"></span>
	                <img id="captchaImage" src="${ctx }/captcha" style="cursor:pointer; margin-left:10px; position: absolute; top:5px; right: 4px; width: 98px; height: 24px; line-height: 8px;padding: 0; background-color: #607d8b;" class="pull-right" title="点击更换"/> 
	              </div>
	              <div class="fix"></div>
              </form>
            </div>
            <div class="modal-footer" style="text-align: left;">
	            <div class="form-group" style="margin:0 0 0 17px;">
					<div class="checkbox" style="margin-top:0;">
						<label style="color: #fff;">
							<input type="checkbox" id="isRead" onchange="isRead()">已阅读并同意<a href="${ctx }/help/agreement" target = "_blank" style="color: #31c3a2;">《Gbocai用户注册与服务协议》</a>
						</label>
					</div>
				</div>
             <div style="padding: 0 17px;position: relative;top: -24px;">
                <button type="button" class="btn btn-primary btn-raised pull-right" style="width: 84px; height: 30px; padding-top: 4px; transform:translateY(-21px); background:#31c3a2; color: #fff;" id="mobileRegisterButton" disabled="true"><strong>提 交</strong></button>
            </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->  
  </div>  
