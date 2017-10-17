<%@ page language="java" contentType="text/html;charset=utf-8" %>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal5" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog" style="width:500px; background:#2E3238;margin: -252px 0px 0px -250px;position: fixed;top: 50%;left: 50%;">
		<div class="modal-content" style="background:#2E3238;height:504px;width:500px;">
			<div class="modal-header">
			   	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
			   	<h4 class="modal-title link-white" id="myModalLabel">
					<strong>金币充值</strong>
				</h4>
                <h5 class="link-white mt-20">充值金额</h5>
			</div>
			<div class="modal-body">
				 <div class="jingbi">
						<ul id="rechargeAmount">
							<li><div class="form-group label-floating"><input type="text" class="form-control text_input" placeholder="0"></div><span id="giveGold">+ 0 金币</span></li>
                      </ul>    
                 </div> 
   				<p class="young mt-20">*充值金币后，您将可以使用金币进行竞猜。
   					<a href="${ip }help/6e13d26986eb43e4b03ec7d4c634b0dd" target="_blank">如何竞猜？</a>
   				</p>
   				<p class="link-white mt-30">请选择支付方式</p>
    			<div class="mt-5 pos-r" style="width:60%;">
                        
                        <label class="myradio-label">
                            <span><img src="${staticPrefix }/images/zf.png">支付宝支付</span>
                            <input class="myradio-radio" type="radio" name="myradio-radio" value="1" checked="checked">
                            <span class="myradio-radioInput"></span>
                        </label>
                        
                        <div class="line_s"></div>
                        
                        <label class="myradio-label">
                            <span><img src="${staticPrefix }/images/wx.png">微信支付</span>
                            <input class="myradio-radio" type="radio" name="myradio-radio" value="2">
                            <span class="myradio-radioInput"></span>
                        </label>
                     	<p>
                     	<input type="hidden" id="rechargeSource" value=""/>
                     		<button type="button" class="btn btn-primary btn-raised mt-25" style="width:105px; padding-left: 0;padding-right: 0;" onclick="recharge();">
                     			<strong>确认充值</strong>
                     		</button>
                     	</p>
				</div>
		</div>
	</div>
</div>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal-zhifu" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin: -255.5px 0px 0px -250px;position: fixed;top: 50%;left: 50%;">
        <div class="modal-content" style="background:#2E3238;width:500px;height:511px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title link-white" id="myModalLabel">
                    <strong>金币充值</strong>
                </h4>
            </div>
            <div class="modal-body" id="zhifu-modal-div"></div>
        </div>
    </div>
</div>
