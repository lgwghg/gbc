<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${staticPrefix }/js/match/comment.js?v=${version }"></script>
                <div class="c-pln ft-14" style="padding:0; overflow:hidden; margin-top: 16px;">
                    <div class="c-plnheader">
                        <i class="c-point"></i>
                        <span>达人评论</span>
                    </div>
                    <div class="c-morepln">
                        <div class="row">
                            <div class="col-xs-1">
                            	<div class="discussant"></div>
                            	<c:choose>
                            		<c:when test="${not empty userPhoto_65}">
                            			<a href="${path }/my/info"><img src="${userPhoto_65}" class="img-circle c-img"/></a>
                            		</c:when>
                            		<c:otherwise>
                            			<a ><img src="${staticPrefix }/images/denglu_n_65.png" class="img-circle c-img"/></a>
                            		</c:otherwise>
                            	</c:choose>
                            </div>
                            <div class="col-xs-11">
                                <div class="c-plnwrap ml-20">
                                	<div class="form-group label-floating" style="margin: 0 auto; padding: 4px 0; width: 95%;"> 
                                        <textarea nickName="" commentId="" class="form-control" placeholder="书写你的评论!~" id="comment-content" style="margin: 0; padding: 0;color:#bdbdbd; height: 38px;"></textarea>
                                    </div>
                                    <c:choose>
                                    	<c:when test="${empty user || user==''}">
                                    		<a class="pull-right" data-toggle="modal" onclick="$('#myModal').modal('show')">请先登录</a>
                                    	</c:when>
                                    	<c:otherwise>
                                    		<a onclick="addComment()" class="pull-right" id="djfb">点击发表</a>
                                    		<span style="margin: 5px 0px;" class="pull-right">Enter快捷回复</span>
                                    	</c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div id="commentList-div" class="critical"></div>
                    </div>
                    <div class="c-mostpln" id="c-mostpln">
                    </div>
                </div>
