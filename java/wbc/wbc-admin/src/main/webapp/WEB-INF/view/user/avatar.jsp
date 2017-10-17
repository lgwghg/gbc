<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath }" />
<link rel="stylesheet" href="${ctx }/resources/js/datepicker/css/bootstrap-datepicker3.standalone.min.css"/>
<script type="text/javascript" src="${ctx }/resources/js/fullavatar/js/swfobject.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/fullavatar/js/fullAvatarEditor.js"></script>

<div class="page-header">
	<h1>
		编辑头像
	</h1>
</div>

<div class="row" style="margin-top:5px;">
	<div class="col-xs-12">
		<div>
			<p id="swfContainer">
				需要安装Flash Player后才可使用，请从<a href="http://www.adobe.com/go/getflashplayer">这里</a>下载安装。
			</p>
		</div>
	</div>
</div>
<input type="hidden" name="id" id="userId" value="${userEntity.id}">
<script type="text/javascript">
	swfobject.addDomLoadEvent(function ()
	{
		var swf = new fullAvatarEditor("swfContainer", 
		{
		    id:'swf',
			upload_url:'${ctx}/servlet/avatar.java?userId=' + $("#userId").val(),
			src_upload:1 /* 0 不保留原图，1 保留原图 2 自定义*/
		},
		
		function (msg) 
		{
			switch(msg.code)
			{
				case 1 : /* alert("页面成功加载了组件！"); */ break;
				case 2 : /* alert("已成功加载默认指定的图片到编辑面板。"); */ break;
				case 3 :
					if(msg.type == 0)
					{
						/* alert("摄像头已准备就绪且用户已允许使用。"); */
					}
					else if(msg.type == 1)
					{
						/* alert("摄像头已准备就绪但用户未允许使用！"); */
					}
					else
					{
						alert("摄像头被占用！");
					}
					break;
				case 5 :
					if(msg.type == 0)
					{
						alert("头像上传成功!");
						location.reload(); 
					}
					break;
			}
		});
     });
</script>