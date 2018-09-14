<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webcom/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>会员区</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="${ctx}/xmMobile/css/mui.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${ctx}/xmMobile/css/common.css" />
		<link href="${ctx}/app/css/iosOverlay.css" rel="stylesheet"/>
		<link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css"/>
		<link href="${ctx}/app/css/font-awesome.min.css" rel="stylesheet"/> 
		<link href="${ctx }/app/css/font-awesome-ie7.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="${ctx }/xmMobile/css/panda.css" >
		<link rel="stylesheet" type="text/css" href="${ctx }/app/css/mui.min.css" >
		<link rel="stylesheet" href="${ctx }/app/iconfont/iconfont.css">
		<script src="${ctx}/xmMobile/js/jquery-2.1.0.js" type="text/javascript" charset="utf-8"></script>
		<script src="${ctx}/app/js/iosOverlay.js"></script>
    	<script src="${ctx}/app/js/spin.min.js"></script>
    	<script src="${ctx}/xmMobile/js/mui.min.js"></script>
    	<script type="text/javascript" src="${ctx }/app/js/jquery.Spinner.js"></script>
    
		<script type="text/javascript">
			var fypage = 0;
			var issend = true;
			
			ajaxjz();
			function ajaxjz() {//加载
	            var submitData = {
	                goodstype: '${goodstype}',
	                typeid: '${typeid}',
	                mintypeid: '${mintypeid}',
	                thirdtypeid: '${thirdtypeid}',
	                ptitle: '${ptitle}'
	            }; 
	            issend = false;
	            $.post('${ctx}/shop/shoppro!proAlllist.action?lscode=${lscode}&custid=${custid}&agid=${agid}&fypage'+fypage, submitData,
	                    function (json) {
	            	     if (json.state == 0) {
	            	    	 var html = $('.goodcount').html();
	                            var v = json.list;
	                            html +='<ul>'
	                            	
	                            for (var i = 0; i < v.length; i++) {
	                            	
	                            	var imageUrl="";
                                	if(v[i].logo.indexOf("http") !=-1){
                                		imageUrl = v[i].logo
                                	}else{
                                		imageUrl = "${filehttp}"+ v[i].logo
                                	}
	                            	
                                	
                                
                                	
                            		html +='<li>'
                                		+'<a href="${ctx}/shop/shop!shopproduct.action?custid=${custid}&agid=${agid}&lscode=${lscode}&pid=' + v[i]._id + '">'
                    					+'<div class="textcount fr mr" >'
                    					+'<p><span>'+v[i].comname+'</span>' + v[i].ptitle + '</p>'
                    					+'<font>￥'+ v[i].price+'</font>'
                    					+'<i class="iconfont icon-cart"></i>'
                    					+'</div>'
                    					+'<div class="imggount fl"><img src="' + imageUrl + '"/></div>'
                    					+'</a>'
                    					+'</li>'
	                            	
                    					
	                            	fypage++;
    	                            
    	                            $(".goodcount").html(html);
    	                            issend = true;
	                            }
	                            html+=+'</ul>';                      	
	                        } else {
	                        	$('.goodcount').html('暂无数据');
	                        }
	                    }, "json")
	              }
		</script>
	</head>

	<body style="background: #f3fdfe;"  onLoad="javascript:document.yourFormName.reset()">
		<header class="mui-bar mui-bar-nav">
		    <a class="mui-icon mui-icon-undo mui-pull-left" style="color: #000;" href="javascript:history.go(-1)"></a>
		    <h1 class="mui-title">会员区</h1>
		</header>
		
	
		
		<!-- <div id="showall"></div> -->
		<div class='mui-content' style="height: 100%;background-color:#f3fdfe;">
		    <!-- <div class="mui-input-row" style="position: relative;margin-top:6px;width:85%;margin-left:7.5%;">
				<span class="mui-icon mui-icon-search" style="position: absolute;top: 5px;right: 5px;" onclick="goods_search()"></span>
				<input type="search" name=""  placeholder="Search" style="padding-left: 30px;margin-bottom:6px;background:#eee;text-align: left;" id="sel">
			</div> -->
			<!--轮播图-->
			
	<div class="mui-collapse-content">
		<div id="slider" class="mui-slider">
			<div class="mui-slider-group mui-slider-loop">
				<c:forEach items="${slide}" var="bean1" varStatus="status1">
					<c:choose>
						<c:when test="${ status1.index == 0}">
							<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
							<div class="mui-slider-item mui-slider-item-duplicate">
								<a href="#">
									<img src="${filehttp}/${slide[ fn:length(slide)-1].picurl}">
								</a>
							</div>
							<!-- 第一张 -->
							<div class="mui-slider-item">
								<a href="#">
									<img src="${filehttp}/${bean1.picurl}">
								</a>
							</div>
						</c:when>
						<c:when test="${ status1.index == fn:length(slide)-1}">
							<!-- 第四张 -->
							<div class="mui-slider-item">
								<a href="#">
									<img src="${filehttp}/${bean1.picurl}">
								</a>
							</div>
							<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
							<div class="mui-slider-item mui-slider-item-duplicate">
								<a href="#">
									<img src="${filehttp}/${slide[0].picurl}">
								</a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="mui-slider-item">
								<a href="#">
									<img src="${filehttp}/${bean1.picurl}">
								</a>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="mui-slider-indicator">
			<c:forEach items="${slide}" var="bean1" varStatus="status1">
				<c:choose>
					<c:when test="${ status1.index == 0}">
						<div class="mui-indicator mui-active"></div>
					</c:when>
					<c:otherwise>
						<div class="mui-indicator"></div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			</div>
		</div>
	</div>
   
   	<script type="text/javascript">
		 $(function(){
			 mui.init({
					swipeBack:true //启用右滑关闭功能
				});
				var slider = mui("#slider");
				slider.slider({
							interval: 3000
				});
			 
		 })
		</script>
		<style>
		.goodlist {padding-top:15px; }
		.goodlist img{ display:block; border-radius:4rem; }
		
		</style>
    <div class="goodlist" style=" background: #f3fdfe; margin-top:20px;" >
    <ul>
	   		<c:forEach items="${typelist}" var="bean" varStatus="status">
				
					<c:choose>
						<c:when test="${ status.index == 0}">
							<li class="one">
								<a href="${ctx}/shop/shoppro!promain.action?custid=${custid}&agid=${agid}&lscode=${lscode}&hylx=${bean._id}&goodstype=4" >
									<div><img src="${filehttp}/${bean.picurl}"/></div>
									<span>${bean.name}</span>
								</a>
							</li>
						</c:when>
						<c:otherwise>
							<li >
								<a href="${ctx}/shop/shoppro!promain.action?custid=${custid}&agid=${agid}&lscode=${lscode}&hylx=${bean._id}&goodstype=4">
									<div><img src="${filehttp}/${bean.picurl}"/></div>
									<span>${bean.name}</span>
								</a>
							</li>
						</c:otherwise>
					</c:choose>
					
			</c:forEach>
				</ul>
    </div>
    <div class="banners">
         <img src="${ctx}/xmMobile/images/banner22.png" width="50%" height="auto" alt="" style="margin-left:25%;">  
    </div>
    <style>
   	.forecont li{position: relative; }
   	.forecont li div.textcoun2{
   	 position: absolute; 
   	 width:100px; 
   	 height:100px; 
   	 padding-top:25px; 
   	  z-index: 8;
   	   background: url("${ctx}/xmMobile/images/redbg.png") no-repeat top center; 
   	   background-size:100% 100%; 
   	   top:-20px; 
   	   left:-30px;
   	    line-height: 25px; 
   	    color: #fff; 
   	    text-align: center;  }

   </style>
    <div class="forecont"  style=" background: #f3fdfe;">
    		<ul>
	   		<li class="one">
	   			<a href="${ctx}/shop/shop!shopproduct.action?custid=${custid}&agid=${agid}&lscode=${lscode}&pid=147308">
	   				<div class="imggount"><img src="${ctx}/xmMobile/images/four1good2.png"/></div>
					<div class="textcoun2">3000元<br/>洗护大礼</div>
	   			
	   			</a>
	   		</li>
	   		<li>
	   			<a href="${ctx}/shop/shop!shopproduct.action?custid=${custid}&agid=${agid}&lscode=${lscode}&pid=147315">
					<div class="imggount"><img src="${ctx}/xmMobile/images/four2good2.png"/></div>
					<div class="textcoun2">5000元<br/>能量大礼</div>
				</a>
	   		</li>
	   		<li>
	   			<a href="${ctx}/shop/shop!shopproduct.action?custid=${custid}&agid=${agid}&lscode=${lscode}&pid=147213">
	   				<div class="imggount"><img src="${ctx}/xmMobile/images/four3good2.png"/></div>
					<div class="textcoun2">10000元<br/>尊享大礼</div>
				</a>
	   		</li>
	   		<div class="clear"></div>
	   	</ul>
    </div>
       <div class="banners" style="float:left;">
    	<img src="${ctx}/xmMobile/images/allgood2.png" width="42%" height="auto" alt="" style="margin-left:29%;">
    </div>
    <!--产品列表-->
     <div class="goodcount"  style="background: none;">
     	
     		
    </div>
		</div>
		<%@include file="/webcom/shop-foot.jsp" %>
		
	</body>

</html>