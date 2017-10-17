<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_jkd75ppj6vlvj9k9.css?v=${version }">
<link rel="stylesheet" type="text/css" href="${staticPrefix }/css/jcDetails.css?v=${version }">
<script type="text/javascript" src="${path }/resources/js/common/dateformat.js?v=${version }"></script>
<script type="text/javascript" src="${staticPrefix }/js/jcDetails.js?v=${version }"></script>

<div class="fatherwrap" style="position: fixed; top: 25%; right: 0; z-index: 9;">
	<div class="c-betwrap" style=" cursor: pointer;float: left; margin-top: 162px;">
		<div class="c-betindication"><span></span><span></span></div>
		<div class="c-bet"><i class="iconfont icon-liebiao"></i>投注历史</div>
	</div>
	<div class="table-responsive chushi">
		<div style="background: #3d4148; height: 54px; overflow: hidden;">
	       <h2 class="pull-left col-md-7" style="max-width: 150px; padding: 18px 22px; max-height: 53px;overflow: hidden; font-size: 16px; color: #fff; font-weight: 600; overflow: hidden; white-space: normal; text-overflow: ellipsis; max-height: 53px; min-width: 145px;">已参与的比赛</h2>
	        <ul class="pull-right list-inline col-md-5" style="color: #999; margin-top: 15px; max-height: 23px; overflow: hidden;">
	           <li class="pull-right xxr" style=" cursor: pointer;"><i class="iconfont icon-x"></i></li>
	           <li class="mrr2"><span class="tip_circle" style="background: #e4a713;">W</span>赢</li>
	           <li class="mrr2"><span class="tip_circle" style="background: #4f4c42;">L</span>输</li>
	           <li><span class="tip_circle" style="background: #0c6954;">N</span>待结束</li>
	       </ul>   
	  	</div>
	   	<table class="table" style="margin-bottom: 0;background: #2e3238; ">
	       	<thead class="ft-14">
	  
	       	</thead>
	       	<tbody style=" color: #fff;" id="dataList">
	       	
	       	</tbody>
	   	</table>
	   	<div style="height: 50px; background: #3d4148;">
	   		<nav style="text-align: center;">
	            <ul class="pagination" id="pagination" style="margin-left: 0;  margin: 12px 0; overflow: hidden; max-height: 25px; min-width: 185px;"> </ul>
	        </nav>
	   	</div>
	</div>
</div>
