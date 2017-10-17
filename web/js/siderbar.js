	$(function(){
		$(".sidebar>ul>.c-vnav").click(function(){
			$(this).addClass("c-active").siblings().removeClass("c-active")
		})
	})