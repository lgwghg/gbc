<%@ page language="java" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
	<jsp:include page="../../wbc/include/common.jsp"/>
	<title>error${seoConfigMap['1'].title }</title>
    <meta name="keywords" content="${seoConfigMap['1'].keywords }">
	<meta name="description" content="${seoConfigMap['1'].description }">
	<link type="text/css" href="${staticPrefix }/css/error.css" rel="stylesheet">
</head>
<body>

    <!--top S==-->
    <jsp:include page="../../wbc/include/header.jsp"/>
    <!--top E-->
    <div id="new-container">
    </div>
    <div class="serviceError">
    	<img src="${staticPrefix }/images/error.png">
    	<div class="return"><a href="${ctx }/index"><img src="${staticPrefix }/images/return.png"</a></div>
    </div>

	<!--footer ==S-->
	<jsp:include page="../../wbc/include/footer.jsp"/>
	<!--footer ==S-->
	
</body>

<script type="text/javascript">
    //footer显示在底部
    var containerHeight = document.getElementById("container").scrollHeight;
	var footer = document.getElementById("footer")
	var allHeight = document.documentElement.clientHeight;
	if(containerHeight < allHeight){
	footer.style.position = "absolute";
	footer.style.bottom = "0";
	}
	
	else{
	footer.style.positong = "";
	footer.style.bottom = "";
	}

</script>
</html>
