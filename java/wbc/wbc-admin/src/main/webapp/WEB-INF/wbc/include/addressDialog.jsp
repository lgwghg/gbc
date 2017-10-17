<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModalAddress" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="margin: -234px 0px 0px -250px;position: fixed;top: 50%;left: 50%;background:#2E3238;width: 500px;">
		<div class="modal-content"
			style="background:#2E3238;width:500px;height:468px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h5 class="modal-title link-white" id="myModalLabel"
					style="color: #8b8f92;">请输入你的姓名、地址、手机号以便我们为您寄送</h5>
			</div>
			<!--实物-->
			<div class="modal-body">
				<form action="">
					<input type="hidden" id="addrId" />
					<div class="form-group label-floating">
						<label class="control-label">收货人</label> <input type="text"
							class="form-control text_input" id="receiverName"
							name="receiverName"> <span class="help-block"
							id="nameError"></span>
					</div>
					<div class="form-group label-floating mt-50">
						<label class="control-label">手机号码</label> <input type="text"
							class="form-control text_input" id="receiverMobile"
							name="receiverMobile"> <span class="help-block"
							id="receiverMobileError"></span>
					</div>
					<div id="dropdown-menu">
						<div id="selectCity" class="pull-left">
							<div class="form-group">
								<select id="provinceId" name="provinceId"
									class="prov form-control"></select>
							</div>
							<div class="form-group">
								<select id="cityId" name="cityId" class="city form-control">
								</select>
							</div>
							<div class="form-group">
								<select id="areaId" name="areaId" class="dist form-control">
								</select>
							</div>
						</div>
					</div>

					<div class="fix"></div>
					<div class="form-group label-floating">
						<label for="addressDetail" class="control-label">详细地址</label> <input
							type="text" class="form-control" id="addressDetail"
							name="addressDetail"> <span class="help-block"
							id="addressDetailError"></span>
					</div>

					<button type="button" class="view3 btn btn-primary btn-raised"
						style="margin-left: 0;" id="saveAddress"
						type="button" >确认保存</button>
					<button type="button"
						class="view3 view4 btn btn-primary btn-raised"
						onclick="$('.close').click();" type="button">取消</button>
				</form>
			</div>
		</div>
	</div>
</div>