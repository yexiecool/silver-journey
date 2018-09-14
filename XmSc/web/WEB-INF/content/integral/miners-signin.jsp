<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp" %>
<!DOCTYPE html>
<html class="ui-page-login">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title style="text-align: center;">登录</title>
		<link href="${ctx}/xmMobile/css/mui.min.css" rel="stylesheet" />
		<link href="${ctx}/xmMobile/css/common.css" rel="stylesheet" />
		<style>
			.ui-page-login,
			body {
				width: 100%;
				height: 100%;
				margin: 0px;
				padding: 0px;
			}
			
			.mui-content {
				padding-top: 45px;
				height: 100%;
				background: #fff;
			}
			
			.mui-input-group {
				padding: 0 30px;
				margin-bottom: 30px;
				margin-top: 20px;
			}
			
			.mui-input-group::after,
			.mui-input-group::before {
				display: none;
			}
			
			.mui-input-group label {
				line-height:1.4 !important;
				width: 25%;
				font-size: 14px;
				color: #020202;
				padding:11px 0 !important;
			}
			
			.mui-input-row label~input,
			.mui-input-row label~select,
			.mui-input-row label~textarea {
				width: 75%;
			}
			
			.mui-content-padded {
				padding: 0 28px;
				margin:50px 0 0;
			}
			
			.mui-btn {
				padding: 0px;
				border-radius: 10px;
				height: 34px;
				line-height: 34px;
				background: #7cc3df;
				color: #fff;
				font-size: 16px;
			}
			
			.link-area {
				text-align: center;
				display: flex;
				margin-top:0;
				justify-content: space-between;
				font-size: 12px;
			}
			
			.link-area a {
				color: #A4A4A4 !important;
				width: 100%;
				margin-top: 18px;
			}
			
			.oauth-area {
				/*position: absolute;
				bottom: 20px;
				left: 0px;*/
				text-align: center;
				width: 100%;
				padding: 0px;
				margin: 0px;
				display: flex;
				justify-content: center;
			}
			
			.oauth-area .oauth-btn {
				display: inline-block;
				width: 50px;
				height: 50px;
				background-size: 30px 30px;
				background-position: center center;
				background-repeat: no-repeat;
				margin: 0px 20px;
				/*-webkit-filter: grayscale(100%); */
				border: solid 1px #ddd;
				border-radius: 50%;
				border: none;
			}
			
			/*.oauth-area .oauth-btn:active {
				border: solid 1px #aaa;
			}
			*/
			.oauth-area .oauth-btn.disabled {
				background-color: #ddd;
			}
			
			.login-logo {
				width: 100%;
				height: 150px;
				display: flex;
				justify-content: center;
				align-items: center;
			}
			
			.login-logo img {
				width: 104px;
				height: 104px;
			}
			
			.title-txt {
				position: relative;
				display: -webkit-box;
				/*margin: 14px 30px;*/
				font-size: 14px;
				-webkit-box-pack: center;
			}
			
			.title-txt::after {
				content: '';
				width: 100%;
				height: 1px;
				position: absolute;
				top: 50%;
				left: 0;
				background: #E1DFDD;
			}
			
			.title-txt .title-layout {
				background-color: #fff;
				z-index: 10;
				position: relative;
				display: block;
				padding: 0 10px;
			}
			.wx-icon{
				background: url('${ctx}/xmMobile/images/weixin.png') no-repeat; 
				/*border: none;*/
			}
			.mui-input-group .mui-input-row:after {
			    position: absolute;
			    right: 0;
			    bottom: 0;
			    left: 0px;
			    height: 1px;
			    content: '';
			    -webkit-transform: scaleY(.5);
			    transform: scaleY(.5);
			    background-color: #c8c7cc;
			}
			.mui-btn-block {
			    margin-bottom: 0px !important;
			}
			.mui-input-group .mui-input-row {
 			    margin: 25px 0 !important;
			}
			
			:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
			   color:#ccc;
			    opacity:0.8;
			    font-size:14px;
			}
			
			::-moz-placeholder { /* Mozilla Firefox 19+ */
			   color: #ccc;
			    opacity:0.8;
			    font-size:14px;
			}
			
			input:-ms-input-placeholder{
			   color: #ccc;
			    opacity:0.8;
			    font-size:14px;
			}
			
			input::-webkit-input-placeholder{
			   color: #ccc;
			    opacity:0.8;
			    font-size:14px;
			}
			
		</style>
	</head>
	<style>
		.mui-input-row label{ color:#fbfaff;}
		.mui-input-row input{ color:#fff; }		
		 #tel{ width:45%; float:left; height:40px;  }	
		.verBtn {
				background:#7cc3df;
				border:none;
				color:#fff; 
				width:25%; 
				float:left; 
				height:30px;
				margin:5px 0; padding:0px 5px; line-height:30px; font-size:13px; text-align:center; border-radius:4px; display:block;
			}
	</style>
	<body style="background: #1d2434;">
		 <!--<header class="mui-bar mui-bar-nav">
			<h1 class="mui-title">登录</h1>
		</header> -->
		<div class="mui-content" style="background: #1d2434;">
			<div class="login-logo">
				<img src="${ctx}/xmMobile/img/icon/icon-login-logo.png"/>
			</div>
			<form id='login-form' class="mui-input-group"style="background:none;" >
				
				<div class="mui-input-row">
					<label>账&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
					<input id='tel' type="text" class="" placeholder="请输入账号" style="background:none !important;" >
				</div>
				<div class="mui-input-row">
					<label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
					<input id='password' type="password" class="" placeholder="请输入密码">
				</div>

			</form>
			
			<div class="mui-content-padded">
				<button id='login' class="mui-btn mui-btn-block" onclick="login()">登录</button>
				<div class="link-area">
					<a id='reg' href="${ctx}/integral/miners!signup.action">注册</a>
					<a id='forgetPassword' href="${ctx}/integral/miners!forgetpw.action">忘记密码</a>
				</div>
			</div>
			<!--<div class="mui-content-padded">
				<div class="title-txt">
					<span class="title-layout">
						<span class="title-txt-txt">其他方式登录</span>
					</span>
				</div>
			</div>
			<div class="mui-content-padded oauth-area" style="margin-top: 50px;">
				<button class="oauth-btn wx-icon"></button>
			</div>-->
		</div>
		<script src="${ctx}/xmMobile/js/jquery-2.1.0.js" type="text/javascript" charset="utf-8"></script>
		<script src="${ctx}/xmMobile/js/mui.min.js"></script>
		<script src="${ctx}/xmMobile/js/app.js"></script>
		<script type="text/javascript" >
		
		
		

		window.onload = function(){ 
		    var oUser = document.getElementById('tel'); 
		    //页面初始化时，如果帐号密码cookie存在则填充
		    if(getCookie('user')  ){
		      oUser.value = getCookie('user');
		     
		    }
		  
		  
		  };
		  
		  
		  
		function login(){
			/* var	zh = $('#zh').val(); */
			var  tel=$('#tel').val();
			/* var  yzm=$('#yzm').val(); */
			var  password=$('#password').val();
			if(tel.length==0||password.length==0){
				alert("请输入账号或密码");
				return;
			}
			
			/* if(yzm.length==0){
				alert("请填写验证码");
				return;
			} */
	            jQuery.ajax({  
	            url:"${ctx}/user/fromuser!signin.action",
	            dataType:"json",
	            data:"tel="+tel+"&password="+password+"&type=2",
	            error:function(XMLHttpRequest, textStatus, errorThrown){
	               alert("登录失败！");
				},
	            success:function(json){
	            	if(json.state==0){
	            		setCookie('user',tel,7); //保存帐号到cookie，有效期7天
	            		//window.location.href ="${ctx}/integral/miners!list.action?lscode="+json.lscode;
	            		window.location.href ="${ctx}/integral/miners!index.action?lscode="+json.lscode;
	            	}else if(json.state == 1){
						alert('请求超时，重新登录');
					}else if(json.state == 2){
						alert('账号和手机号不匹配');
					}else if(json.state == 3){
						alert('密码错误');
					}else if(json.state == 4){
						alert('验证码超时');
					}else if(json.state == 5){
						alert('验证码错误');
					}
	            }
	        });
		} 
		
		 //设置cookie
		  function setCookie(name,value,day){
		    var date = new Date();
		    date.setDate(date.getDate() + day);
		    document.cookie = name + '=' + value + ';expires='+ date;
		  };
		  //获取cookie
		  function getCookie(name){
		    var reg = RegExp(name+'=([^;]+)');
		    var arr = document.cookie.match(reg);
		    if(arr){
		      return arr[1];
		    }else{
		      return '';
		    }
		  };
		  //删除cookie
		  function delCookie(name){
		    setCookie(name,null,-1);
		  };
		
		function send(){
			var reg =/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
			var count = 60;
			var timer;
			if($('#tel').val() == '') {
				mui.alert('请输入手机号')
			} else if(!reg.test($('#tel').val())) {
				mui.alert('手机号码不正确')
			} else {
				function countDown() {
					if(count == 0) {
						clearInterval(timer);
						$('.verbtn').css('background','#214979')
						$('.verBtn').removeAttr('disabled', true);
						$('.verBtn').html('重新发送');
					} else {
						$('.verbtn').css('background','#ddd')
						$('.verBtn').removeAttr('disabled', false);
						$('.verBtn').html(count + 's');
						count--;
					}
				}
				var timer = setInterval(countDown, 1000);
				$.ajax({
					type: "post",
					url: "${ctx}/user/fromuser!createTelCode.action",
					async: true,
					data: {
						tel: $('#tel').val()
					},
					success: function(json) {

					}
				});
			}
		}
		</script>
	</body>

</html>