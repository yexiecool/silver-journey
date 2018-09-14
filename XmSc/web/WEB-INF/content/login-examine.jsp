
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>熊猫审核</title>
		<style>
			*{margin: 0; padding: 0; box-sizing: border-box;}
			.zxxcontent{ font-size:0.9rem; height:400px; width:100%; overflow:auto;  background:#fff; padding:2% 8% 0 8%; top:15%; display: block;left:10%; z-index:100001; word-wrap:break-word;}	
			.zxxcontent span{display:block;width: 100%;height: 2.5rem; line-height:2.5rem;text-align:center; margin-top: 2rem; }4
		</style>

	</head>

	<body style="background:#fff;">					
		<div class="zxxcontent" id="zxxcontent3">
			<div>
				<c:if test="${curruser.audit_status == 0}">
	    		    <span style="margin:30px 0;" >您的店铺正在审核中，请您耐心等待</span>
		 		</c:if>
		 		<c:if test="${curruser.audit_status == 1}">
		    		<span style="margin:30px 0;" >您的店铺资料审核已通过</span>
		 		</c:if>
		 		<c:if test="${curruser.audit_status == 2}">
		    		<span style="margin:30px 0;" >您申请店铺的资料审核不通过：</span>
		    		<span style="margin:10px 0;" >审核意见：${curruser.comments}</span>
		 		</c:if>
				
				<img src="${ctx}/xmMobile/images/wait.jpg" alt="" width="100%"  />
			</div>		
		</div>
	</body>
	  
		
</html>

