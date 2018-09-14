<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp" %>
<!DOCTYPE html>
<html class="ui-page-login">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title></title>
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
				height: 100%;
				background: #fff;
			}
			
			.mui-input-group {
				padding: 0 30px;
			}
			
			.mui-input-group::after,
			.mui-input-group::before {
				display: none;
			}
			
			.mui-input-group label {
				width: 25%;
				font-size: 14px;
				color: #020202;
			}
			
			.mui-input-row label~input,
			.mui-input-row label~select,
			.mui-input-row label~textarea {
				width: 75%;
			}
			
			.mui-content-padded {
				margin-top: 30px;
				padding: 0 30px;
				margin-right: 0;
			}
			
			.mui-btn {
				padding: 0px;
				border-radius: 10px;
				height: 34px;
				line-height: 34px;
				background: #e4393c;
				color: #fff;
				font-size: 16px;
			}
			
			.link-area {
				display: flex;
				margin-top: 22px;
				justify-content: space-between;
				font-size: 12px;
			}
			
			.link-area a {
				color: #000;
			}
			
			.oauth-area {
				/*position: fixed;
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
				height: 200px;
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
		</style>
		
 

	</head>

	<body style="background: #fff;">
		<!-- <header class="mui-bar mui-bar-nav">
			<h1 class="mui-title">登录</h1>
		</header> -->
		<div class="mui-content" style="padding-top:0;">
			<div class="login-logo">
				<img src="${ctx}/xmMobile/img/icon/icon-login-logo.png"/>
			</div>
			<form id='login-form' class="mui-input-group">
				<div class="mui-input-row">
					<label>账号</label>
					<input id='tel' type="text" class="mui-input-clear mui-input" placeholder="请输入账号">
				</div>
				<div class="mui-input-row">
					<label>密码</label>
					<input id='password' type="password" class="mui-input-clear mui-input" placeholder="请输入密码">
				</div>
				<div class="mui-input-row1" style="margin-top:10px;" >
					<input type="checkbox" id="remember" style="float:left; width:15px; height:15px; margin:12px 10px 0 20px;" />
					<label style="width:40%;line-height:38px;">记住密码</label>
					<a id='forgetPassword' href="${ctx}/login!forgetpw.action" style="font-size:14px; line-height:38px; color:#e4393c !important; margin-right:2%; float:right" >忘记密码</a>
				</div>
				
			</form>
			
			<div class="mui-content-padded" style="margin-top:20px;">
				<button id='login' class="mui-btn mui-btn-block" onclick="login()">登录</button>
				<div class="link-area">
					<a id='reg' href="${ctx}/login!signup.action"><!-- 注册 --></a>
					
				</div>
			</div>
			<div class="mui-content-padded">
				<div class="title-txt">
					<span class="title-layout">
						<span class="title-txt-txt">其他方式登录</span>
					</span>
				</div>
			</div>
			<div class="mui-content-padded oauth-area" style="margin-top: 50px;">
				<button class="oauth-btn wx-icon" onclick="javascrtpt:window.location.href='${ctx}/shop/shop!index.action'"></button>
				
			</div>
		</div>
		
		
		<script src="${ctx}/xmMobile/js/jquery-2.1.0.js" type="text/javascript" charset="utf-8"></script>
		<script src="${ctx}/xmMobile/js/mui.min.js"></script>
		<script src="${ctx}/xmMobile/js/app.js"></script>
		<script type="text/javascript" >
		
		
		window.onload = function(){
		    var oForm = document.getElementById('login-form');
		    var oUser = document.getElementById('tel');
		    var oPswd = document.getElementById('password');
		    var oRemember = document.getElementById('remember');
		    //页面初始化时，如果帐号密码cookie存在则填充
		    if(getCookie('user') && getCookie('pswd')){
		      oUser.value = getCookie('user');
		      oPswd.value = getCookie('pswd');
		      oRemember.checked = true;
		    }
		    //复选框勾选状态发生改变时，如果未勾选则清除cookie
		    oRemember.onchange = function(){
		      if(!this.checked){ 
		        delCookie('user');
		        delCookie('pswd');
		      }
		    };
		  
		  };
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
		  
		  
		function login(){
			var  tel=$('#tel').val();
			var  password=$('#password').val();
			var  oRemember = document.getElementById('remember');
				if(tel.length==0||password.length==0){
					alert("请输入账号或密码");
					return;
				}
	            jQuery.ajax({  
	            url:"${ctx}/user/fromuser!signin.action",
	            dataType:"json",
	            data:"tel="+tel+"&password="+password,
	            error:function(XMLHttpRequest, textStatus, errorThrown){
	               alert("登录失败！");
				},
	            success:function(data){
	            	if(data.state==0){
	            		  alert('登陆成功');
	            		  //$.alerts.alert("点击下方确认按钮刷新页面", "温馨提示","确定",function(){
								//window.location.reload();
						 // });
	            		  
	            		 
	            		//document.getElementById("mask").style.display = "block";
	     		        //document.getElementById("zxxcontent").style.display = "block";
	     		        //$("body").css("position","fixed");
	            		 
	            		 if(remember.checked){ 
	            			 setCookie('user',tel,7); //保存帐号到cookie，有效期7天
	    		       		 setCookie('pswd',password,7); //保存密码到cookie，有效期7天
	            		 }
	            		window.location.href ="${ctx}/shop/shop!index.action?lscode="+data.lscode;
	            	}else{
	            		 alert("登录失败！");	
	            	}
	            }
	        });
		} 
		</script>
		
		
		<!--mjy开始-->
		<style>
	        .mask{height:100%; width:100%; position:fixed; top:0; z-index:100000; display: none;}
			.opacity{ opacity:0.5; filter: alpha(opacity=30); background-color:#000; }
			.zxxcontent{ font-size:0.9rem; text-align:center; border-radius:20px;  height:200px; width:80%; overflow:auto;  background:#fff;position:fixed; padding:0px; top:28%; display: none;left:10%; z-index:100001; word-wrap:break-word;}			
			
			.zxxcontent h1{ width：100%; height:40x; color:#000000;  line-height:40px; font-size: 1.4rem; margin-top: 0px; padding: 0;  }
	    	.zxxcontent p{ font-size: 1rem; color:#000000;}
			.zxxcontent input{display:block;width: 80%; margin:0 auto;  height: 2.5rem; line-height:2.5rem;text-align:center; margin-top: 1rem; }
	    	.turecont{width:100%; margin-top:2rem; float:left;  border-top: 1px solid #ccc;}
	    	.turecont button{ width:50%; height:2.5rem; float:left; color:#007aff; border: 0px; }
	    	.turecont button.left{border-right:1px solid #ccc; }
	    </style>
		<div class="mask opacity" id="mask" > </div>
		<div class="zxxcontent" id="zxxcontent">	
	        <form action="" method="post" >
				 <h1>提醒</h1>
				 <p>请输入您的编号：</p>
				 <input type="text" name="bh" value="84664"/>
				<div class="turecont">
					<button class="left">取消</button>
					<button class="right" id="close">确定</button>
				</div>  	     
	        </form>				
		</div>
		<script>
		 /*  function zfxy() {	  
		      
		    } */
		
		   $('#close').click(function () {
	            $('.mask').hide();
	            $('#zxxcontent').hide();	
	            $("body").css("position","static");            
	        })		       
		</script>
		
		<!--mjy结束-->
	</body>

</html>