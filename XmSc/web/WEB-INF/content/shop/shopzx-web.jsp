<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webcom/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport"  content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>${title}</title>
    <script src="${ctx}/app/js/jquery-1.8.3.js"></script>
 		<link rel="stylesheet" type="text/css" href="${ctx}/xmMobile/css/mui.min.css" />
	
    <style>
        .img-100 img {
            width: 100%;
        }
        .textcount{margin-top:50px; }
         div.textcount p{ font-size: 16px; float:left; color:#000;  }
	     div.textcount font{ font-size: 14px; margin-top:15px; float:right; color:#000;   }
	 	 div.textcount font.two{ color:red;  margin-right:15px;  }   	
	   	.imggount img{ display:block;margin:15px 0;  }
	   	.imggount p{ width:100%;  font-size:14px; text-indent:2rem; line-height:24px; color:#000; }
	   	.clear{clear:both; }
	   	
    </style>
</head>
<body class="bg-bai" style="background:#fff;">
		<header class="mui-bar mui-bar-nav" style="background: #1d2434;">
			<a class=" mui-icon mui-icon-undo mui-pull-left" href="javascript:history.go(-1)" style="color:#fbfaff;"></a>
			<h1 class="mui-title" style="color:#fbfaff;">文章详情页</h1>
		</header>
	<main class="cmp640 img-100" style=" padding: 15px;  " >
		
	  <div class="textcount fl" >
	  		<p>${shopzx.title}</p>
	    	<font> <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${shopzx.createdate}" /></font>
	    	<!-- <font class="two">熊猫商场</font> -->
	    	<div class="clear"></div>
	  </div>
	  <div class="imggount fl">
	  	  <img src="${filehttp}${shopzx.logo}"/>
	  	  ${shopzx.content} 
	  </div>
	  
	  
	</main>
</body>
</html>
