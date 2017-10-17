<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${header['X-PJAX']}">
		<jsp:include page="../../include/common_pjax.jsp"/>
		<title>任务中心${seoConfigMap['1'].title }</title>
		<meta name="keywords" content="${seoConfigMap['1'].keywords }">
		<meta name="description" content="${seoConfigMap['1'].description }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/magic-check.css?v=${version }&t=<%=Math.random() %>">
<style type="text/css">
.alert-ml {
	color: #31c3a2;
}

.alert-tmier {
	color: #8d8e8f;
}

.alert-or {
	color: #a2a3a5;
}

font {
	color: #32c3a2;
}

.tag-day {
	position: relative;
}

.tag-day .label {
	position: absolute;
	left: 38px;
	top: 9px;
	background: url(${staticPrefix }/images/label.png) no-repeat;
	width: 74px;
	height: 62px;
	z-index: 1;
	display: inline-table;
}

.tag-day img {
	width: 115px;
	height: 115px;
}

.load-ing {
	position: relative;
}

.load-ing .in-a {
	border-radius: 8px;
	display: block;
	background: #3d4148;
	width: 120px;
	height: 35px;
	text-align: center;
	color: white;
	font-weight: bold;
	position: relative;
	line-height: 35px;
}

.load-ing .in-a .in-txt {
	position: absolute;
	z-index: 1;
	left: 45px;
}

.load-ing .in-a .in-b {
	position: absolute;
	background: #31c3a2;
	width: 50%;
	left: 0;
	top: 0;
	height: 35px;
	border-radius: 8px 0px 0px 8px;
}

.have-in {
	background: red;
}
</style>
<script type="text/javascript" src="${staticPrefix }/js/my/task/usertask.js?v=${version }&t=<%=Math.random() %>"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!--[if lt IE 9]>
    <script>
        document.createElement('video');
    </script>
    <![endif]-->
	<%-- <div class="bodyer"></div>
	<div class="container-fluid" style="margin:0; padding:0;">
		main
		<div class="container c-informwrap" style="" id="new-container">
			<div class="c-informright pull-left" id="container">

				<

			</div>
		</div>
	</div> --%>
	<div class="c-informright pull-left" >
	<div class="item-exmall exhide exshow">

					<ul class="c-changelist ft-14">
						<c:forEach items="${taskVoList }" var="taskVo">
							<li class="row"
								style="border-bottom: 1px solid #40484c; border-radius: 0; margin-bottom: 0; height:auto; overflow:hidden">
								<div class="col-xs-2 tag-day">
									<c:if test="${taskVo.task.type == 0 }">
										<div class="label"></div>
									</c:if>
									<a href=""><img src="${taskVo.task.image }"
										width="100" height="100" /></a>
								</div>
								<div class="col-xs-7">
									<p>
										<span>${taskVo.task.taskName } <span id="taskName_${taskVo.task.taskType }">（<c:choose><c:when test="${taskVo.task.taskType != 4 and not empty taskVo.userTask && taskVo.userTask.completeNum gt 0}">${taskVo.userTask.completeNum }</c:when><c:when test="${taskVo.task.taskType eq 4 }">${taskVo.task.num }</c:when><c:otherwise>0</c:otherwise></c:choose>/${taskVo.task.num }）</span></span>
									</p>
									<div class="mt-5 mb-20 alert-or">${taskVo.task.description }</div>
									<c:if test="${taskVo.task.taskType == 0 }">
									<span>完成任务奖励 <font>1~30</font> G币</span>
									</c:if>
									<c:if test="${taskVo.task.taskType != 0 }">
									<span>完成任务奖励 <font>${taskVo.task.gold }</font> G币</span>
									</c:if>
								</div> 
								<c:if test="${taskVo.task.taskType == 0 }">
									<%-- 签到 --%>
									<c:if test="${taskVo.userTask.completedState == 0 or taskVo.userTask.completedState == null}">
									<div class="col-xs-3 c_sand fr">
										<button type="button"
											class="btn btn-primary btn-raised pull-right" id="task_wqd">
											<strong>签到</strong>
										</button>
										<button type="button"
											class="btn btn-primary btn-raised pull-right hide"
											style="background:#3d4148;color:#fff;" disabled="true" id="task_yqd">
											<strong>已签到</strong>
										</button>
										<text class="alert-ml fr alert-tmier <c:if test="${taskVo.task.type==0 }">alert-time</c:if>" id="signGiveGold"> <c:if test="${taskVo.userTask.gold > 0 }">+${taskVo.userTask.gold } G币</c:if></text>
									</div>
									</c:if>
									<c:if test="${taskVo.userTask.completedState == 1 or taskVo.userTask.completedState == 2}">
									<div class="col-xs-2 c_sand fr">
										<button type="button"
											class="btn btn-primary btn-raised pull-right"
											style="background:#3d4148;color:#fff;" disabled="true">
											<strong>已签到</strong>
										</button>
										<text class="alert-ml" id="signGiveGold"> <c:if test="${taskVo.userTask.gold > 0 }">+${taskVo.userTask.gold } G币</c:if></text>
									</div>
									</c:if>
									
								</c:if>
								<c:if test="${taskVo.task.taskType != 0 }">
									<%--非签到 --%>
									<c:choose>
										<%--  or taskVo.userTask.completedState == null or taskVo.userTask.completedState == 0 --%>
										<c:when test="${taskVo.userTask.completedState == 0 } ">
											<div class="col-xs-3 pull-right ">
												<div class="load-ing pull-right">
													<div class="progress"
														style="height: 35px; border-radius: 8px; background: #3d4148;">
														<div class="progress-bar progress-bar-success"
															role="progressbar" aria-valuenow="60" aria-valuemin="0"
															aria-valuemax="100"
															style="width: ${taskVo.userTask.completeNum*100/taskVo.task.num}%; background: #009789">
															<span class="sr-only"
																style="position: relative; color:#fff;left: 50%; line-height:35px; font-size: 14px;">进行中</span>
														</div>
													</div>
													<%--每日任务才有倒计时 --%>
													<text class="alert-ml fr alert-tmier <c:if test="${taskVo.task.type==0 }">alert-time</c:if>" style="margin-top:-10px;" id="alert-ml_${taskVo.userTask.id }"></text>
												</div>
											</div>

										</c:when>
										<%--昵称领取和完成状态的任务可以领取 --%>
										<c:when test="${(taskVo.task.taskType == 4 and empty taskVo.userTask) or taskVo.userTask.completedState == 1}">
											<div class="col-xs-3">
												<button type="button"
													class="btn btn-primary btn-raised pull-right"
													onclick="completedTask('${taskVo.userTask.id}', ${taskVo.task.taskType})">
													<strong>领取</strong>
												</button>
												<%--每日任务才有倒计时 --%>
												<text class="alert-ml fr alert-tmier <c:if test="${taskVo.task.type==0 }">alert-time</c:if>" id="alert-ml_${taskVo.task.taskType }"></text>
											</div>
										</c:when>

										<c:when test="${taskVo.userTask.completedState == 2 }">
											<div class="col-xs-2 c_sand fr">
												<button type="button"
													class="btn btn-primary btn-raised pull-right"
													style="background:#3d4148;color:#fff;" disabled="true">
													<strong>已领取</strong>
												</button>
												<text class="alert-ml" id="alert-ml_${taskVo.userTask.id }"> <c:if test="${taskVo.userTask.gold > 0 }">+${taskVo.userTask.gold } G币</c:if></text>
											</div>
										</c:when>

										<c:otherwise>
											<div class="col-xs-3 pull-right mt-15">
												<div class="load-ing pull-right">
													<div class="in-a">
														<span class="in-txt">进行中</span>
														<div class="in-b"
															style="width:${taskVo.userTask.completeNum*100/taskVo.task.num}%"></div>
													</div>
													<%--每日任务才有倒计时 --%>
													<text class="alert-ml fr alert-tmier <c:if test="${taskVo.task.type==0 }">alert-time</c:if>" style="margin-top:10px;" id="alert-ml_${taskVo.userTask.id }"></text>
												</div>
											</div>
										</c:otherwise>
									</c:choose>
								</c:if>

							</li>
						</c:forEach>
					</ul>
				</div>
				</div>
	</c:when>
	<c:otherwise>
		<!DOCTYPE html>
		<html lang="en">
<head>
<jsp:include page="../../include/common.jsp" />
<title>任务中心${seoConfigMap['1'].title }</title>
<meta name="keywords" content="${seoConfigMap['1'].keywords }">
<meta name="description" content="${seoConfigMap['1'].description }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/magic-check.css?v=${version }">
<style type="text/css">
.alert-ml {
	color: #31c3a2;
}

.alert-tmier {
	color: #8d8e8f;
}

.alert-or {
	color: #a2a3a5;
}

font {
	color: #32c3a2;
}

.tag-day {
	position: relative;
}

.tag-day .label {
	position: absolute;
	left: 38px;
	top: 9px;
	background: url(${staticPrefix }/images/label.png) no-repeat;
	width: 74px;
	height: 62px;
	z-index: 1;
	display: inline-table;
}

.tag-day img {
	width: 115px;
	height: 115px;
}

.load-ing {
	position: relative;
}

.load-ing .in-a {
	border-radius: 8px;
	display: block;
	background: #3d4148;
	width: 120px;
	height: 35px;
	text-align: center;
	color: white;
	font-weight: bold;
	position: relative;
	line-height: 35px;
}

.load-ing .in-a .in-txt {
	position: absolute;
	z-index: 1;
	left: 45px;
}

.load-ing .in-a .in-b {
	position: absolute;
	background: #31c3a2;
	width: 50%;
	left: 0;
	top: 0;
	height: 35px;
	border-radius: 8px 0px 0px 8px;
}

.have-in {
	background: red;
}
</style>
<script type="text/javascript" src="${staticPrefix }/js/my/task/usertask.js?v=${version }"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!--[if lt IE 9]>
    <script>
        document.createElement('video');
    </script>
    <![endif]-->
    
</head>

<body>
	<div class="bodyer"></div>
	<div class="container-fluid" style="margin:0; padding:0;">
		<!--header S==-->
		<jsp:include page="../../include/header.jsp" />
		<!--header E-->
		<!-- main -->
		<div class="container c-informwrap" style="" id="new-container">
			<!--menu ==S-->
			<jsp:include page="../include/menu.jsp" />
			<!--menu ==S-->
			<div id="container">
			<div class="c-informright pull-left" >

				<div class="item-exmall exhide exshow">

					<ul class="c-changelist ft-14">
						<c:forEach items="${taskVoList }" var="taskVo">
							<li class="row"
								style="border-bottom: 1px solid #40484c; border-radius: 0; margin-bottom: 0; height:auto; overflow:hidden">
								<div class="col-xs-2 tag-day">
									<c:if test="${taskVo.task.type == 0 }">
										<div class="label"></div>
									</c:if>
									<a href=""><img src="${taskVo.task.image }"
										width="100" height="100" /></a>
								</div>
								<div class="col-xs-7">
									<p>
										<span>${taskVo.task.taskName } <span id="taskName_${taskVo.task.taskType }">（<c:choose><c:when test="${taskVo.task.taskType != 4 and not empty taskVo.userTask && taskVo.userTask.completeNum gt 0}">${taskVo.userTask.completeNum }</c:when><c:when test="${taskVo.task.taskType eq 4 }">${taskVo.task.num }</c:when><c:otherwise>0</c:otherwise></c:choose>/${taskVo.task.num }）</span></span>
									</p>
									<div class="mt-5 mb-20 alert-or">${taskVo.task.description }</div>
									<c:if test="${taskVo.task.taskType == 0 }">
									<span>完成任务奖励 <font>1~30</font> G币</span>
									</c:if>
									<c:if test="${taskVo.task.taskType != 0 }">
									<span>完成任务奖励 <font>${taskVo.task.gold }</font> G币</span>
									</c:if>
								</div> 
								<c:if test="${taskVo.task.taskType == 0 }">
									<%-- 签到 --%>
									<c:if test="${taskVo.userTask.completedState == 0 or taskVo.userTask.completedState == null}">
									<div class="col-xs-3 c_sand fr">
										<button type="button"
											class="btn btn-primary btn-raised pull-right" id="task_wqd">
											<strong>签到</strong>
										</button>
										<button type="button"
											class="btn btn-primary btn-raised pull-right hide"
											style="background:#3d4148;color:#fff;" disabled="true" id="task_yqd">
											<strong>已签到</strong>
										</button>
										<text class="alert-ml fr alert-tmier <c:if test="${taskVo.task.type==0 }">alert-time</c:if>" id="signGiveGold"> <c:if test="${taskVo.userTask.gold > 0 }">+${taskVo.userTask.gold } G币</c:if></text>
									</div>
									</c:if>
									<c:if test="${taskVo.userTask.completedState == 1 or taskVo.userTask.completedState == 2}">
									<div class="col-xs-2 c_sand fr">
										<button type="button"
											class="btn btn-primary btn-raised pull-right"
											style="background:#3d4148;color:#fff;" disabled="true">
											<strong>已签到</strong>
										</button>
										<text class="alert-ml" id="signGiveGold"> <c:if test="${taskVo.userTask.gold > 0 }">+${taskVo.userTask.gold } G币</c:if></text>
									</div>
									</c:if>
									
								</c:if>
								<c:if test="${taskVo.task.taskType != 0 }">
									<%--非签到 --%>
									<c:choose>
										<%--  or taskVo.userTask.completedState == null or taskVo.userTask.completedState == 0 --%>
										<c:when test="${taskVo.userTask.completedState == 0 } ">
											<div class="col-xs-3 pull-right ">
												<div class="load-ing pull-right">
													<div class="progress"
														style="height: 35px; border-radius: 8px; background: #3d4148;">
														<div class="progress-bar progress-bar-success"
															role="progressbar" aria-valuenow="60" aria-valuemin="0"
															aria-valuemax="100"
															style="width: ${taskVo.userTask.completeNum*100/taskVo.task.num}%; background: #009789">
															<span class="sr-only"
																style="position: relative; color:#fff;left: 50%; line-height:35px; font-size: 14px;">进行中</span>
														</div>
													</div>
													<%--每日任务才有倒计时 --%>
													<text class="alert-ml fr alert-tmier <c:if test="${taskVo.task.type==0 }">alert-time</c:if>" style="margin-top:-10px;" id="alert-ml_${taskVo.userTask.id }"></text>
												</div>
											</div>

										</c:when>
										<%--昵称领取和完成状态的任务可以领取 --%>
										<c:when test="${(taskVo.task.taskType == 4 and empty taskVo.userTask) or taskVo.userTask.completedState == 1}">
											<div class="col-xs-3">
												<button type="button"
													class="btn btn-primary btn-raised pull-right"
													onclick="completedTask('${taskVo.userTask.id}', ${taskVo.task.taskType})">
													<strong>领取</strong>
												</button>
												<%--每日任务才有倒计时 --%>
												<text class="alert-ml fr alert-tmier <c:if test="${taskVo.task.type==0 }">alert-time</c:if>" id="alert-ml_${taskVo.task.taskType }"></text>
											</div>
										</c:when>

										<c:when test="${taskVo.userTask.completedState == 2 }">
											<div class="col-xs-2 c_sand fr">
												<button type="button"
													class="btn btn-primary btn-raised pull-right"
													style="background:#3d4148;color:#fff;" disabled="true">
													<strong>已领取</strong>
												</button>
												<text class="alert-ml" id="alert-ml_${taskVo.userTask.id }"> <c:if test="${taskVo.userTask.gold > 0 }">+${taskVo.userTask.gold } G币</c:if></text>
											</div>
										</c:when>

										<c:otherwise>
											<div class="col-xs-3 pull-right mt-15">
												<div class="load-ing pull-right">
													<div class="in-a">
														<span class="in-txt">进行中</span>
														<div class="in-b"
															style="width:${taskVo.userTask.completeNum*100/taskVo.task.num}%"></div>
													</div>
													<%--每日任务才有倒计时 --%>
													<text class="alert-ml fr alert-tmier <c:if test="${taskVo.task.type==0 }">alert-time</c:if>" style="margin-top:10px;" id="alert-ml_${taskVo.userTask.id }"></text>
												</div>
											</div>
										</c:otherwise>
									</c:choose>
								</c:if>

							</li>
						</c:forEach>
					</ul>
				</div>

			</div>
			</div>
		</div>
	</div>
	<!--footer ==S-->
	<jsp:include page="../../include/footer.jsp" />
	<!--footer ==S-->
	</div>

</body>
		</html>
	</c:otherwise>
</c:choose>