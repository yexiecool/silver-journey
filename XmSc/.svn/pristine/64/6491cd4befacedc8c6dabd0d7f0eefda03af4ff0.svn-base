<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<head>
		<meta charset="utf-8" />
		<title>充值记录</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" href="${ctx}/xmMobile/css/index.css" />
		<link href="${ctx}/xmMobile/css/mui.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${ctx}/xmMobile/css/common.css" />
		<script src="${ctx}/xmMobile/js/jquery-2.1.0.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body style="background:url('${ctx}/xmMobile/images/kjbg2.jpg') repeat-y top center; background-size:100% 100%;">
		<header class="mui-bar mui-bar-nav" style="background: #141534;" >
		    <a class="mui-action-back mui-icon mui-icon-undo mui-pull-left" style="	color:#fbfaff !important;" href="javascript:history.go(-1)"></a>
		    <h1 class="mui-title" style="color:#fbfaff !important;" >充值记录</h1>
		</header>
		<style>
		.zichan>p{height:40px !important; line-height:40px !important; }
		.zichan p span{color:#7cc3df;  float:left; text-align: center !important; height:40px !important; line-height:40px !important;}
		.zichan span.fir{ width:25%;}
		.zichan span.two{ width:25%; text-align: left;}
		.zichan span.thr{ width:50%;}
		</style>
		 
		 
	
		<!--修改后的-->
		<div class="zichan" style="padding-top:50px;">
			<p style="border-top:none;"><span class="fir">金额</span><span class="two">支付状态</span><span class="thr">时间</span></p>
			<div  style="clear: both;"></div>
			<c:forEach items="${paylist}" var="obj">
			
			
				<p>
				<span class="fir">${obj.paymoney}</span>
				<span  class="two">			
					<c:if test="${obj.status==0}">
				 		待支付
				  </c:if> 
				  <c:if test="${obj.status==1}">
				 		支付成功
				  </c:if> 
				  <c:if test="${obj.status==2}">
				 		支付失败
				  </c:if>
				</span>
				<span class="thr">
				 <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${obj.createdate}" />
				 </span>
				</p>
			 
			</c:forEach>
		</div>
		
		 
	</body>
</html>
