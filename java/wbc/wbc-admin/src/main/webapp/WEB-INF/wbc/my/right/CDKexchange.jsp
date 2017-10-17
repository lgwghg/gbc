<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${staticPrefix }/js/my/right/cdkey.js?v=${version }"></script>

<!--你的账户-->
<div class="slide hide" id="inc_right_tabs_CDK">
    <div class="slide-head"><img class="zjtp zjtp_x" src="${staticPrefix }/images/shuangzuo.png?v=${version }" alt="">
        <h2>CD-KEY兑换</h2>
    </div>
    <div class="slide-content">
        <div class="form-group label-floating is-empty">
	    	<label class="control-label">输入CD-KEY</label>
	        <input type="text" class="form-control" id="right_cdkeyCode" />
	        <span class="help-block" id="right_cdkeyError"></span>
	    </div>
	    <div class="form-group label-floating is-empty">
	    	<label class="control-label">验证码</label>
	    	<input type="text" class="form-control" name="captcha" id="right_cdkeyCaptcha" style="padding-right:110px;" onkeyup="right_checkCdkeyCaptcha()">
	    	<span class="help-block" id="right_cdkeyCaptchaError"></span>
	    	<img id="right_cdkeyCaptchaImage" src="/captcha" style="cursor: pointer; margin-left: 10px; position: absolute; top: 5px; right: 4px; width: 98px; height: 24px; line-height: 8px; padding: 0px; background-color: rgb(96, 125, 139); display: block;" class="pull-right" title="点击更换"> 
	    </div>
	    <button type="button" class="btn btn-primary btn-raised pull-right" style="width: 100%; height: 50px; padding-top: 4px; transform:translateY(-21px);top: 30px;" id="right_cdkeyExchangeBtn" type="button" disabled><strong>兑换</strong></button>
    	<span class="to-exchange">兑换记录</span>
    	<p class="explain-title">CD-KEY兑换说明：</p>
    	<p class="explain-content">1、无论兑换何种CD-KEY，在CD-KEY有效期内能兑换成功一次。</p>
    	<p class="explain-content">2、各种非官方渠道获得的CD-KEY都有可能无法兑换、兑换失败、兑换不到您想要的商品。请尽量避免在第三方渠道购买CD-KEY。</p>
		<p class="explain-content">3、如您使用的是“商品兑换CD-KEY”，请在商品兑换记录查询商品情况。</p>
    </div>
</div>

