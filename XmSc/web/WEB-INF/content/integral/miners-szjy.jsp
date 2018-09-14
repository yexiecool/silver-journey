<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<head>
		<meta charset="utf-8" />
		<title>行情</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" href="${ctx}/xmMobile/css/index.css" />
		<link href="${ctx}/xmMobile/css/mui.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${ctx}/xmMobile/css/common.css" />
	</head>
	<body style="background:url('${ctx}/xmMobile/images/kjbg2.jpg') no-repeat top center; background-size:100% 100%;">
		<header class="mui-bar mui-bar-nav" style="background: #141534;" >
		    <a class="mui-action-back mui-icon mui-icon-undo mui-pull-left" style="	color:#fbfaff !important;" href="javascript:history.go(-1)"></a>
		    <h1 class="mui-title" style="color:#fbfaff !important;" >交易所</h1>
		</header>
		<style>
		  .zichan>p>span{color:#7cc3df;  text-align:left; }
		  .zichan>p>span.fir{width:25%;}
		  .zichan>p>span.two{width:55%;}
		  .zichan>p>span.thr{width:20%;}
		</style>
		<%-- <div class="top">
			<a href="javascript:void(0)" style="float: left;"><img src="${ctx}/xmMobile/img/gohome.png" alt="" /></a>
			行情
			<img class="serch_img" style="float: right;" src="${ctx}/xmMobile/img/serch.png" alt="" />
		</div>
		
		<div class="serch">
			<input type="text" placeholder="币种查询"/>
			<button></button>
		</div> --%>
		<!-- <script type="text/javascript">
			var serch_img=document.getElementsByClassName("serch_img")[0];
			var serch=document.getElementsByClassName("serch")[0]
			serch_img.onclick=function(){
				serch.style.display="block"
			}
		</script> -->
	
		<!--修改后的-->
		<div class="zichan" style="padding-top:50px;">
			<p style="border-top:none;">
				<span class="fir">资产名称</span>
				<span class="two">今日交易额</span>
				<span class="thr">方式</span>
			</p>
			<div  style="clear: both;"></div>
			<p>
			  <span class="fir"><img src="${ctx}/xmMobile/img/left2.png" alt="" />PADA</span>
			  <span class="two" style="text-align:left;">			
				<c:if test="${jf == null }">0.00</c:if>
				<c:if test="${jf != null }">${jf.kjvalue}</c:if>
			  </span>
			  	<c:if test="${status==1}">
			  		<button type="button" class="mui-btn miner-btnbuy" onclick="tips()" style="background:#54d86b; color:#fbfaff; float:right; padding:3px 12px; margin-top:15px;   boder:none  !important;">交易</button>
			  	</c:if>
			  	<c:if test="${status==0}">
					<button type="button" class="mui-btn miner-btnbuy" onclick="javascript:window.location.href='${ctx}/integral/miners!kjtx.action?custid=${custid}&agid=${agid}&lscode=${lscode}'" style="background:#54d86b; color:#fbfaff; float:right; padding:3px 12px; margin-top:15px;   boder:none  !important;">交易</button>
			  	</c:if>
			</p>
			<!--  p>
				<span><img src="${ctx}/xmMobile/img/left2.png" alt="" />LLB</span><span>
			
				<c:if test="${jf == null }">0.00</c:if>
				<c:if test="${jf != null }">${jf.llkyvalue}</c:if>
				</span><span><img src="${ctx}/xmMobile/img/jiaoyi.png" alt="" onclick="javascript:window.location.href='http://www.uskdpro.com'"/></span>
			</p-->
			<script type="text/javascript">
			function tips(){
				alert("您的账号已被冻结，请联系管理员！");
			}
		</script>
		</div>
	</body>
</html>
