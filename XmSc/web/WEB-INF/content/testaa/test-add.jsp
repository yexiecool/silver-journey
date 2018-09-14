<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>testadd</title>
<script src="${ctx}/xmMobile/js/mui.min.js"></script>
<link href="${ctx}/xmMobile/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/xmMobile/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/xmMobile/css/swiper.css" />
<link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/css/font-awesome.min.css" rel="stylesheet" />
<link href="${ctx}/app/css/font-awesome-ie7.min.css" rel="stylesheet" />
<script src="${ctx}/xmMobile/js/jquery-2.1.0.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${ctx}/mvccol/js/mtlb.js"></script>
<script type="text/javascript" src="${ctx }/app/js/bbsSwipe.js"></script>
<script type="text/javascript" src="${ctx }/app/js/swipe.js"></script>

<script type="text/javascript">
	
</script>


</head>

<body>


	<form id="testForm" action="${ctx }/testaa/testaa!save.action"
		class="form-horizontal" method="post">

		<div>
			用户名： <input type="text" name="username" id="username" />
		</div>
		<div>
			密码： <input type="text" name="passwd" id="passwd" />
		</div>
		<div>
			email: <input type="text" name="email" id="email" />
		</div>

		<input type="submit" vaule="提交" />
	</form>


</body>
</html>