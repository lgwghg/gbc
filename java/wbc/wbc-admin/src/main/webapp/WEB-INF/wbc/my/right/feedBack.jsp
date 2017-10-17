<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${staticPrefix }/js/my/right/feedBack.js?v=${version }"></script>

<!--用户反馈-->
<div class="slide hide" id="inc_right_tabs_feedback">
    <div class="slide-head"><img class="zjtp zjtp_x" src="${staticPrefix }/images/shuangzuo.png?v=${version }" alt="">
        <h2>用户反馈</h2>
    </div>
    <div class="slide-content">
        <div class="form-group label-floating pull-left" style="width: 288px;">
            <label for="title2" class="control-label">请输入标题</label>
            <input type="text" class="form-control" id="title2">
        </div>
        <textarea name="" id="contentMsg2" placeholder="请输入你的意见"></textarea>

        <div class="form-group label-floating pull-left" style="width: 288px;">
            <label for="noticeCaptcha2" class="control-label" style="font-size: 15px;">请输入验证码</label>
            <img id="spans2" src="${ctx }/captcha" style="background:#fff; cursor: pointer; height: 20px; width: 50px; text-align: center; position: absolute; top:7px; right: 0px;" title="点击更换"/>
            <input type="text" class="form-control" id="noticeCaptcha2">
        </div>
        <button type="button" onclick="saveNotice2();" class="btn btn-primary btn-raised" style="width: 288px;"><strong>提交</strong></button>
    </div>
</div>
