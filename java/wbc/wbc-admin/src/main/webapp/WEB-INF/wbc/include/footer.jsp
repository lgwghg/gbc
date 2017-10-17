<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${staticPrefix }/js/footer.js?v=${version }"></script>
<c:if test="${empty loginButtonHide }"><%--登录和注册页不展示侧边栏 --%>
<jsp:include page="../my/right/rightMain.jsp"></jsp:include>
</c:if>
	<style type="text/css">
		.blackish-green{ color:#31c3a2;}
		.footer{ height: 130px; background: #1B1E21;position:relative;z-index:1;}
		.footer .link a{ margin-right: 20px; color: #31c3a2;}
		.footer .link a:hover{color: #31c3a2;}
		.footer:before{ content:""; display: block; margin-bottom: 249px;}
		.server_btn{ background:#16191C; border:1px solid #666666; border-radius:5px;}
	</style>

 	<div class="footer mt-50">
        <div class="container">         
            <div class="row">
                <div class="center-block mt-30" style="width:80%;background-color:#ccc;">
                	<div class="pull-left">
		     			<p class="link" id="showLink">
		     				
		     			</p>
	     				<p class="link-white mt-10">&copy;<span id="yearText">2017</span> Gbocai.com <a target="_blank" rel="nofollow" href="http://www.miitbeian.gov.cn/">鄂ICP备17001172号</a> </p>
		     		</div>          
	                <div class="pull-right">
		     			<button alt="点击这里给我发消息" title="点击这里给我发消息" type="button" class="btn server_btn" onclick="javascript:window.open('http://wpa.qq.com/msgrd?v=3&uin=3251829496&site=qq&menu=yes','_blank')">
		     				<img src="${staticPrefix }/images/mail.png">　
		     				<font class="blackish-green">联系客服</font>
		     			</button>
		     		</div>
                </div>
			</div>
     	</div>
     </div>
     