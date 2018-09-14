<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp" %>
<!DOCTYPE html>
<html class="ui-page-login">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>注册</title>
		<link href="${ctx}/xmMobile/css/mui.min.css" rel="stylesheet" />
		<link href="${ctx}/xmMobile/css/style.css" rel="stylesheet" />
		<link href="${ctx}/mvccol/mui-css/mui.picker.css" rel="stylesheet"/>
	    <link href="${ctx}/mvccol/mui-css/mui.poppicker.css" rel="stylesheet"/>
	    <link href="${ctx}/app/css/font-awesome.min.css" rel="stylesheet">
	    <link href="${ctx}/app/css/font-awesome-ie7.min.css" rel="stylesheet">	
	    <link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css"/>
		 <!--标准mui.css-->
    	<link rel="stylesheet" href="${ctx}/mvccol/mui-css/mui.min.css">
    	<script src="${ctx}/xmMobile/js/jquery-2.1.0.js" type="text/javascript" charset="utf-8"></script>
		<style>
						.area {
				margin: 20px auto 0px auto;
			}
			
			.mui-input-group:first-child {
				/*margin-top: 20px;*/
			}
			
			.mui-input-group label {
				width: 25%;
				font-size: 15px;
			}
			
			.mui-input-row label~input,
			.mui-input-row label~select,
			.mui-input-row label~textarea {
				width: 75%;
			}
			
			.mui-input-row label~input::-webkit-input-placeholder {
				font-size: 12px;
			}
			
			.mui-checkbox input[type=checkbox],
			.mui-radio input[type=radio] {
				top: 6px;
			}
			
			.mui-content-padded {
				margin-top: 45px;
			}
			
			#reg.mui-btn {
				padding: 0px;
				border-radius: 10px;
				height: 34px;
				line-height: 34px;
				background: #E4393C;
				color: #fff;
				font-size: 16px;
			}
			
			.Idimg{
				display: inline-block;
				width: 100%;
				height:100%;
				position:absolute;
				left:0;
				top:0;
				background: #eee;
				text-align: center;
				line-height: 100px;
				font-size: 20px;
				z-index: 0;
			}
			
			.mui-btn.verBtn {
				width: 100px;
				position: absolute;
				right: 0;
				top: 8px;
				padding: 5px 10px;
				background:#214979;
			}
			
			.mui-popup-title {
				color: #000;
				padding: 32px 0 36px 0;
			}
			
			.mui-row::after,
			.mui-row::before,
			.mui-input-group::before,
			.mui-input-group::after {
				display: none;
			}
			.mask{display: none;width:100%;height: 100%;background: rgba(0,0,0,.6);position: fixed;top: 0;left: 0;right: 0;bottom: 0;z-index: 10;}
			.mask-cont{width: 300px;height: 400px;margin: 0 auto;position: absolute;top: 50%;left: 68%;margin-left: -220px;margin-top: -200px;background: #fff;z-index: 10;border-radius: 6px;padding: 10px;}
			.mask-tit{width: 100%;height: 40px;line-height: 40px;text-align: center;font-size: 16px;}
			.mask-body{width: 100%;height: 300px;overflow-y: auto;padding: 10px;position: relative;}
			.mask-body::after,.mask-body::before{content: '';width: 100%;height: 1px;background: #ddd;position: absolute;left: 0;}
			.mask-body::after{top: 0;}
			.mask-body::before{bottom: 0;}
			.mask-foot{width: 100%;height: 40px;text-align: right;}
			
		</style>
		<script>
		var reg = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
		function sendsms(){
			if($('#phone').val() == '') {
				mui.alert('请输入手机号')
				return ;
			} else if(!reg.test($('#phone').val())) {
				mui.alert('手机号码不正确')
				return ;
			} 
			
			var count = 60;
			var timer;
			function countDown() {
				if(count == 0) {
					clearInterval(timer);
					$('.verBtn').prop("disabled",false);
					$('.verBtn').html('重新发送');
					$('.verBtn').attr('onclick','sendsms1()');
				} else {
					$('.verBtn').removeAttr('onclick'); 									
					$('.verBtn').html(count + 's');
					count--;
				}
			}
			var timer = setInterval(countDown, 1000);
			
			if($('.verBtn').html()=='重新发送'||$('.verBtn').html()=='获取验证码'){
				sendsms1();
			}
		}
		
		function sendsms1(){
			
					$.ajax({
						type:"post",
						url:"${ctx}/user/fromuser!createTelCode.action",
						async:true,
						data:{
							tel:$('#phone').val()
						},
						success:function(json){
							if(json.state == 0){
								
							}else{
								clearInterval(timer);
								$('.verBtn').removeAttr('disabled', true);
								$('.verBtn').html('重新发送');
							}
						}
					});
			

			} 
		</script>
	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
		    <a class="mui-action-back mui-icon mui-icon mui-icon-undo mui-pull-left" style="color: #000;" href="javascript:history.back(-1);"></a>
		    <h1 class="mui-title">注册</h1>
		</header>
		<div class="mui-content" style="background: #fff;padding: 0 16px; padding-top: 44px;">
			<!--<div class="mui-row">
				<p class="mui-popup-title">注册</p>
			</div>-->
			<form class="mui-input-group">
				<div class="mui-input-row">
					<label>手机号</label>
					<input id="phone" type="text" class="mui-input-clear mui-input" minlength='11' maxlength="11" placeholder="请输入手机号码" />
				</div>
				<div class="mui-input-row" style="position: relative;">
					<label>验证码</label>
					<input id="verCode" type="text" class="mui-input-clear mui-input" placeholder="请输入验证码" />
					<span class="mui-btn mui-btn-grey verBtn" onclick="sendsms()">获取验证码</span>
				</div>
				<div class="mui-input-row">
					<label>密码</label>
					<input id='password' type="password" class="mui-input mui-input-password" placeholder="请输入密码">
				</div>
				<div class="mui-input-row">
					<label>确认</label>
					<input id='password_confirm' type="password" class=" mui-input mui-input-password" placeholder="请确认密码">
				</div>
				<div class="mui-input-row">
					<label>推荐人</label>
					<input id="renumber" type="text" class="mui-input-clear mui-input"   placeholder="请输入推荐人" />
				</div>
			 
			</form>
			<div class="mui-content-padded">
				<button id='reg' class="mui-btn mui-btn-block " data-loading-icon= = "mui-spinner mui-spinner-custom">注册</button>
			</div>
			<div class="mui-content-padded">

			</div>
		</div>
		
 
		
		<script src="${ctx}/xmMobile/js/mui.min.js"></script>
		<script src="${ctx}/mvccol/mui-js/mui.picker.js"></script>
<script src="${ctx}/mvccol/mui-js/mui.poppicker.js"></script>

 
		<script>
			$(function() {
				 
				var reg = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
			 
					
				 
				$('#reg').click(function(){
					
					var phone =$('#phone').val();
					var pwd =$('#password').val();
					var pwd1= $('#password_confirm').val();
					var renumber =$('#renumber').val();
					var  verCode =$('#verCode').val();
					if(phone == '') {
						mui.alert('请输入手机号')
						return ;
					} else if(!reg.test(phone)) {
						mui.alert('手机号码不正确')
						return ;
					}else if(verCode=='') {
						mui.alert('验证码不能为空')
						return ;
					} else if(pwd=='') {
						mui.alert('密码不能为空')
						return ;
					}  else if(pwd1=='') {
						mui.alert('确认密码不能为空')
						return ;
					}  else if(pwd!=pwd1) {
						mui.alert('两次输入的密码不一致')
						return ;
					}  else if(renumber=='') {
						mui.alert('推荐人不能为空')
						return ;
					}  else{
						mui($(this)).button('loading');
						$.ajax({
							type:"post",
							url:"${ctx}/user/fromuser!usersignup.action",
							async:true,
							data:{
								tel:phone,
								password:pwd,
								yzcode:verCode,
								renuber:renumber
							},
							success:function(json){
								if(json){
									if(json.state == 0){
										 
										 alert("您的会员编号："+json.number +"    请牢记")
										 location.href='${ctx}/integral/miners!signin.action';
									}else if(json.state == 1){
										 alert('请求超时，重新注册');
									}else if(json.state == 2){
										 alert('用户不存在');
									}else if(json.state == 3){
										 alert('验证码输入错误');
									}else if(json.state == 4){
										 alert('验证码超时');
									}
									
								}else{
									mui($(this)).button('reset');
								}
							}
						});
					}  
				})
				

			})
			
			
		</script>
		
 
		 
	</body>

</html>