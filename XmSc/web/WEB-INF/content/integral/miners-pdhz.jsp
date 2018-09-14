﻿<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webcom/taglibs.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title></title>
		<link rel="stylesheet" type="text/css" href="${ctx}/xmMobile/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/xmMobile/css/common.css" />
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<style type="text/css">
			input:focus {
				outline: none;
			}
			
			.mui-input-row {
				position: relative;
			}
			
			.mui-input-row label {
				width: 25%;
				font-size: 14px;
		       padding:11px 0px 11px 20px;
				padding-bottom:0px;
				<!--text-align: justify;-->
			}
			.mui-input-row>a{
			    position: absolute;
			    right: 0;
			    top: 8px;
			    height: 25px;
			    width: 50px;
			    text-align: center;
			    line-height: 25px;
			    font-size: 12px;
			    color: #fff !important;
			    border-radius: 3px;
			    background: #007AFF;
			}
			
			.mui-input-row label::after{
				display: inline-block ; 
				content: ''; 
				padding-left: 100%; 
			}
			.mui-input-row label~input,
			.mui-input-row label~select,
			.mui-input-row label~textarea {
				width: 75%;
			}
			
			.mui-input-row::after {
				width: 100%;
				content: '';
				height: 1px;
				background: #ccc;
				position: absolute;
				bottom: 0;
				left: 0;
			}
			
			.liftCash-tit {
				line-height: 20px;
				font-size: 12px;
				color: #999;
				margin-top: 5px;
			}
			
			.rank {
				width: 100%;
				height: auto;
				margin-top: 10px;
			}
			
			.liftBtn {
				margin: 0 auto;
				background: #E4393C;
				border: none;
				color: #fff;
			}
			
			.modal {
				width: 100%;
				height: 100%;
				position: fixed;
				top: 0;
				bottom: 0;
				left: 0;
				right: 0;
				background: rgba(0, 0, 0, .6);
				z-index: 1999;
				display: none;
			}
			
			.modal-cont {
				width: 80%;
				height: 200px;
				background: #fff;
				border-radius: 10px;
				margin: 0 auto;
				margin-top: 50px;
			}
			
			.modal-cont-tit {
				width: 100%;
				height: 34px;
				line-height: 34px;
				text-align: center;
				position: relative;
			}
			
			.modal-cont-tit::after {
				content: '';
				width: 100%;
				height: 1px;
				background: #e3e3e3;
				position: absolute;
				bottom: 0;
				left: 0;
			}
			
			.modal-cont-cont {
				width: 100%;
				height: 132px;
				line-height: 132px;
				position: relative;
			}
			
			.sixpwd {
				width: 80%;
				margin: 0 auto;
				border: 1px solid #000;
				position: absolute;
				top: 50%;
				left: 10%;
				height: 34px;
				line-height: 34px;
				margin-top: -17px;
				border-radius: 5px;
				display: flex;
				box-sizing: border-box;
				z-index: 10;
			}
			
			.sixpwd input {
				flex: 1;
				width: 1%;
				margin: 0;
				height: 32px;
				line-height: 32px;
				position: relative;
				border: none;
				border-right: 1px solid #000;
				border-radius: 0;
			}
			
			.sixpwd input:last-child {
				border: none;
			}
			.madol-foot{
				width: 100%;
				display: flex;
				height: 34px;
				line-height: 34px;
			}
			.madol-foot button{
				width: 1%;
				flex: 1;
				border: none;
				color: #fff;				
				border-radius:0 ;
			}
			.madol-foot button:first-child{
				border-bottom-left-radius: 5px;
				background: #E4393C;
			}
			.madol-foot button:last-child{
				
				background: #007AFF;
				border-bottom-right-radius: 5px;
			}
		</style>
		<script type="text/javascript">
		      function withdrawal(){
		    	  if($('#eth').val() == ''){
		    		  alert('请输入账号');
		    		  $('.modal').hide();
		    		  return ;
		    	  }
		    	  if($('#tel').val() == ''){
		    		  alert('请输入手机');
		    		  $('.modal').hide();
		    		  return ;
		    	  }
		    	  if($('#yzm').val() == ''){
		    		  alert('请输入验证码');
		    		  $('.modal').hide();
		    		  return ;
		    	  }
		    	  if($('#price').val() == ''){
		    		  alert('请输入交易所金额');
		    		  $('.modal').hide();
		    		  return ;
		    	  }
		    	  if($('#payPassword_rsainput').val() == ''){
		    		  alert('请输入支付密码');
		    		  return ;
		    	  }
		    	  var submitData = { 
		    			  password:$('#payPassword_rsainput').val()
		    	    }; 
		    	  
		    	  var eth = $('#eth').val();
		    	  console.log('rth'+eth+'rth');
		    	  console.log('rth'+$.trim(eth)+'rth');
		    	  var submitData3 = { 
		    			  toid:$.trim(eth)
		    	    }; 
		    	  
		    	  //密码验证
		    		 $.post('${ctx}/integral/miners!wdpassword.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData,
					        	function (json) {
					            	if(json.state==0){ 
					            		$.post('${ctx}/integral/miners!ajaxfromuserid.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData3,
									        	function (json) {
					            			if(json.state==1){
					            				var fromuserid = json.toid;
					            				console.log(fromuserid);
					            				var submitData1 = { 
					            						tel:$('#tel').val(),
					            						yzm:$('#yzm').val(),
							            				toid:$('#eth').val(),
							            				price:$('#price').val(),
							            				remark:$('#remark').val()
									    	    };
					            				$.post('${ctx}/integral/miners!transfer.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData1,
											        	function (json) {
											            	if(json.state==0){
											            		alert('交易成功');
											            	}else if(json.state==1){
											            		alert('交易失败，请重新提交');
											            	}else if(json.state==2){
											            		alert('余额不足');
											            	}else if(json.state==3){
											            		alert('转账失败');
											            	}else if(json.state==4){
											            		alert('账号与手机号不匹配');
											            	}else if(json.state==5){
											            		alert('验证码有误');
											            	}else if(json.state==6){
											            		alert('验证码超时');
											            	}
											            	window.location.reload();
											            	
												},"json");
					            				
					            			}else{
					            				alert('用户不存在');
					            			}
									            	
									            	
										},"json");
					            		
					            	}else if(json.state==1){
					            		alert('操作失败');
					            	}else if(json.state==2){
					            		alert('账号不存在');
					            	}else  if(json.state==3){
					            		alert('未设置密码，请先设置支付密码');
					            		window.location.href="${ctx}/user/fromuser!safePwd.action?custid=${custid}&agid=${agid}&lscode=${lscode}";
					            	}else  if(json.state==4){
					            		alert('密码错误');
					            	}
					},"json")
		      }
		</script>
	</head>
<!--mjy开始-->
    <style>
.clearfix:after {
    clear: both;
}
.clearfix:before, .clearfix:after {
    content: " ";
    display: table;
}
 .alieditContainer{
        position: relative; width:244px;
    } 
    
.sixDigitPassword {
    position: absolute;
    left: -122px;
    top: 0;   
    width: 240px;
    height: 34px;  
    color: #fff;
    font-size: 12px;
    -webkit-box-sizing: content-box;
    box-sizing: content-box;
    -webkit-user-select: initial;
    outline: 'none';
    z-index: 999;
    opacity:0;
    filter:alpha(opacity=0);
  }

  .sixDigitPassword-box {
      
        cursor:text;
        background: #fff;
        outline: none;
        position: relative;
        padding: 8px 0;
        height: 30px;
        border: none;
        border-radius: 2px;
  }
  .sixDigitPassword-box i {
  	    width:39px;
        float: left;
        display: block;
        padding: 4px 0;
        height: 15px;
        border-left: 1px solid #cccccc;
    }
   .sixDigitPassword-box i.active { padding:4px 0; height:15px; margin-top:5px;
        background: url('${ctx }/xmMobile/images/password-blink.gif') no-repeat center center;        
    }
   .sixDigitPassword-box b {
        display: block;
        margin: 0 auto;
        width: 7px;
        height: 7px;
        overflow: hidden;
        visibility:hidden;
        background: url('${ctx }/xmMobile/images/passeord-dot.png') no-repeat;
    }
  .sixDigitPassword-box span {
  	    width: 40px;
        position: absolute;
        display: block;
        left: 0px;
        top: 0px;
        height: 30px;
        border: 1px solid rgba(82, 168, 236, .8);
        border: 1px solid #00ffff\9;
        border-radius: 2px;
        visibility: hidden;
        -webkit-box-shadow: inset 0px 2px 2px rgba(0, 0, 0, 0.75), 0 0 8px rgba(82, 168, 236, 0.6);
        box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
    }
    .ui-securitycore  .ui-form-item .ui-form-explain{
		margin-top: 8px; 
	}
  .i-block{
	display:inline-block;
  }
 .six-password{
    position: relative;
    height:33px;
    width:244px;
    overflow: hidden;
    vertical-align: middle;
    
}

.modal-cont-cont p {
	float: left;
	margin-top: 20%;
	width: 94%;
	padding: 3%;
	font-size: 14px; height：auto;
	word-break: normal;
	white-space: pre-warp;
	word-wrapL: break-word;
	line-height: 20px;
}



.sixpwd1 {
	width: 242px;
	margin: 0 auto;
	border: 1px solid #000;
	position: absolute;
	top: 20%;
	left: 10%;
	height: 34px;
	line-height: 34px;
	margin-top: -17px;
	border-radius: 5px;
	display: flex;
	box-sizing: border-box;
	z-index: 10;
}

.sixpwd input {
	flex: 1;
	width: 1%;
	margin: 0;
	height: 32px;
	line-height: 32px;
	position: relative;
	border: none;
	border-right: 1px solid #000;
	border-radius: 0;
}

.sixpwd input:last-child {
	border: none;
}

</style>
    <!--mjy结束-->
	<body style="background:#fff;">
		<header class="mui-bar mui-bar-nav" style="background:#fff;">
			<a class=" mui-icon mui-icon-undo mui-pull-left" href="javascript:history.go(-1)"></a>
			<h1 class="mui-title">PADA互转</h1>
		</header>
		<style>		
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
		<div class="mui-content" style="background: #fff; margin-top:5px; padding-left:10px ;padding-right: 10px;">
			<c:if test="${status==1 }">
			<div class="mui-row">			    				
				
				<div class="mui-input-row">
					<label for="">手&nbsp;&nbsp;&nbsp;机：</label>
					<input type="text" id="tel" class="mui-input-clear" value="" />
					<button id="fsyzm" class="verBtn" onclick="send()">发送验证码</button>		
				</div>
				<div class="mui-input-row">
					<label for="">验证码：</label>
					<input type="text" id="yzm" class="mui-input-clear" value="" />
										
				</div>
				<div class="mui-input-row">
					<label for="">会员号：</label>
					<input type="text" id="eth" class="mui-input-clear" value="" />					
				</div>
				<div class="mui-input-row">
					<label for="">金&nbsp;&nbsp;&nbsp;额：</label>
					<input type="text" id="price" class="mui-input-clear" value="" />
				</div>
				<div class="mui-input-row">
					<label for="">备&nbsp;&nbsp;&nbsp;注：</label>
					<input type="text" id="remark" class="mui-input-clear" value="" />
				</div>
			</div>
			<div class="mui-row" style="width: 100%;display: flex;justify-content: center;margin-top: 20px;">
				<button class="mui-btn mui-btn-block liftBtn">确认转账</button>
			</div>
			<style>
			  .neat{ width:100%; margin:0px; }
			  .neat span{width:100%; display:block;  font-size:14px; color:#E4393C; line-height:40px; text-align: center; font-weight:600; }
			  .neat p{text-indent:2rem; color:#000; }
			</style>
			<div class="neat">
				<img src="${ctx}/xmMobile/images/kraize.jpg" alt="" width="100%" />
				<span>温馨提醒</span>
				<p>会员编号为要转入方的账号，请确认无误后再转账。转账成功后则无法退回。</p>
			</div>		
			<div class="modal">
				<div class="modal-cont">
					<div class="modal-cont-tit">
						请输入支付密码
					</div>
					<div class="modal-cont-cont">
						<div class="sixpwd1">
							<div id="payPassword_container" class="alieditContainer clearfix" data-busy="0">	
									<div class="i-block six-password">
										<input class="i-text sixDigitPassword" id="payPassword_rsainput" type="password" autocomplete="off" required="required" value="" name="payPassword_rsainput" data-role="sixDigitPassword" tabindex="" maxlength="6" minlength="6" aria-required="true">
										<div tabindex="0" class="sixDigitPassword-box" style="width: 240px;">
											<i style="border-color: transparent;" class=""><b style="visibility: hidden;"></b></i>
											<i ><b style="visibility: hidden;"></b></i>
											<i ><b style="visibility: hidden;"></b></i>
											<i ><b style="visibility: hidden;"></b></i>
											<i ><b style="visibility: hidden;"></b></i>
											<i><b style="visibility: hidden;"></b></i>
											<span style="left: 0px; visibility: hidden;" id="cardwrap" data-role="cardwrap"></span>
										</div>
									</div>			
								</div>	
						</div>
						<!-- <div class="sixpwd">
							<input type="password" name="" class="pwd" id="password" value="" maxlength="16" />
							<input type="password" name="" class="pwd" id="password" value="" maxlength="1" />
							<input type="password" name="" class="pwd" id="password" value="" maxlength="1" />
							<input type="password" name="" class="pwd" id="password" value="" maxlength="1" />
							<input type="password" name="" class="pwd" id="password" value="" maxlength="1" />
							<input type="password" name="" class="pwd" id="password" value="" maxlength="1" />
							<input type="password" name="" class="pwd" id="password" value="" maxlength="1" />
						</div> -->
						<p>备注：该购物密码是您在个人资料中设置的，未设置点击确认前往个人资料中设置并完善。</p>
					</div>				
					<div class="madol-foot">
						<button class="cancel"  style="background: #73d1c4;">取消</button><button onclick="withdrawal()" style="background: #f20101;">确认</button>
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${status==0 }">
				<div class="mui-row">您的账号已被冻结，请联系管理员</div>
			</c:if>
		</div>
		<script src="${ctx}/xmMobile/js/jquery-2.1.0.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$('.liftBtn').click(function() {
				var  yzm=$('#yzm').val();
				if(yzm==""){
					alert('验证码不能为空')
					return;
				}
				$('.modal').show()
			})
			$('.cancel').click(function(){
				$('.modal').hide()
			})
			 wx.config({
				    debug: false,
				    appId: '${token.appid}', 
				    timestamp: '${token.timestamp}', 
				    nonceStr: '${token.noncestr}', 
				    signature: '${token.signature}',
				    jsApiList: [ 'checkJsApi',
				                 'onMenuShareTimeline',
				                 'onMenuShareAppMessage',
				                 'onMenuShareQQ',
				                 'onMenuShareWeibo',
				                 'hideMenuItems',
				                 'showMenuItems'
				                 ] 
				});
				wx.ready(function(){ 
					var share={
						    title: '${share.fxtitle}', // 分享标题
						    desc: '${share.fxsummary}', // 分享描述
						    link: '${share.fxurl}', // 分享链接
						    imgUrl: '${filehttp}${share.fximg}', // 分享图标
						    success: function () { 
						      
						    },
						    cancel: function () { 
						    	
						    }
						};
					wx.onMenuShareAppMessage(share);
					wx.onMenuShareTimeline(share);
					wx.onMenuShareAppMessage(share);
					wx.onMenuShareQQ(share);
					wx.onMenuShareWeibo(share);
				});
				
				function send(){
					var reg =/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
					var count = 60;
					var timer;
					if($('#tel').val() == '') {
						alert('请输入手机号')
					} else if(!reg.test($('#tel').val())) {
						alert('手机号码不正确')
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
		
		<!--mjy开始-->
<script src="${ctx }/xmMobile/js/jquery-1.8.3.min.js" type="text/javascript"></script>

<script type="text/javascript">				
var payPassword = $("#payPassword_container"),
    _this = payPassword.find('i'),	
	k=0,j=0,
	password = '' ,
	_cardwrap = $('#cardwrap');
	//点击隐藏的input密码框,在6个显示的密码框的第一个框显示光标
	payPassword.on('focus',"input[name='payPassword_rsainput']",function(){
	
		var _this = payPassword.find('i');
		if(payPassword.attr('data-busy') === '0'){ 
		//在第一个密码框中添加光标样式
		   _this.eq(k).addClass("active");
		   _cardwrap.css('visibility','visible');
		   payPassword.attr('data-busy','1');
		}
		
	});	
	//change时去除输入框的高亮，用户再次输入密码时需再次点击
	payPassword.on('change',"input[name='payPassword_rsainput']",function(){
		_cardwrap.css('visibility','hidden');
		_this.eq(k).removeClass("active");
		payPassword.attr('data-busy','0');
	}).on('blur',"input[name='payPassword_rsainput']",function(){
		
		_cardwrap.css('visibility','hidden');
		_this.eq(k).removeClass("active");					
		payPassword.attr('data-busy','0');
		
	});
	
	//使用keyup事件，绑定键盘上的数字按键和backspace按键
	payPassword.on('keyup',"input[name='payPassword_rsainput']",function(e){
	
	var  e = (e) ? e : window.event;
	
	//键盘上的数字键按下才可以输入
	if(e.keyCode == 8 || (e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105)){
			k = this.value.length;//输入框里面的密码长度
			l = _this.size();//6
			
			for(;l--;){
			
			//输入到第几个密码框，第几个密码框就显示高亮和光标（在输入框内有2个数字密码，第三个密码框要显示高亮和光标，之前的显示黑点后面的显示空白，输入和删除都一样）
				if(l === k){
					_this.eq(l).addClass("active");
					_this.eq(l).find('b').css('visibility','hidden');
					
				}else{
					_this.eq(l).removeClass("active");
					_this.eq(l).find('b').css('visibility', l < k ? 'visible' : 'hidden');
					
				}				
			
			if(k === 6){
				j = 5;
			}else{
				j = k;
			}
			$('.sixDigitPassword-box').css('top','2px');
			$('#cardwrap').css({'left':j*40+'px','top':'2px'});
		
			}
		}else{
		//输入其他字符，直接清空
			var _val = this.value;
			this.value = _val.replace(/\D/g,'');
		}
	});	
	
</script>	

<!--mjy结束-->
	</body>
</html>