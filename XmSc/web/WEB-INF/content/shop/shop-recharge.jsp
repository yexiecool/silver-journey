﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>消费充值</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<script src="${ctx }/app/js/jquery-1.8.3.js"></script>
<link href="${ctx}/xmMobile/css/mui.min.css" rel="stylesheet" />
<link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/app/css/font-awesome.min.css" rel="stylesheet" />
<link href="${ctx }/app/css/font-awesome-ie7.min.css" rel="stylesheet" />
<link href="${ctx}/app/css/iosOverlay.css" rel="stylesheet" />
</head>
<style>
.czcount {
   
    background:#141534;
	width: 100%; padding-top:50px;
	
	margin: 0px auto  70px;
}

.czcount div {
	line-height: 40px;
	font-size: 16px;
	color: #7cc3df;
}

.leftc {
	float: left;
	width: 30%;
	text-align: right;
}

.rightc {
	float: right;
	width: 65%;
	padding-left: 5%;
}

.rightc p {
	color: #7cc3df;
}

.clear {
	clear: both;
}

.czcount input[type=submit] {
	margin: 50px 0 20px 20%;
	background: #54d86b;
	border: none;
	width: 60%
}
.czcount p.tipp{ padding:10px 15%; font-size:12px; line-height:24px; color:#fbfaff; }
.anniu{   font-size:14px;  color: #7cc3df; line-height:60px; float:left; width:100%; padding:0 3%; }
		.anniu button{background:#006891;  border:none;  text-align: center;  border-radius: 10px; float:right; margin:10px 0;   }
		.anniu button a{ color: #fff !important; }
</style>
<body style="background:url('${ctx}/xmMobile/images/xfcz.jpg') no-repeat top center; background-size:100% 100%; height:40rem;">
	<header class="mui-bar mui-bar-nav" style="background: #141534;">
		<a class=" mui-icon mui-icon-undo mui-pull-left"
			style="font-size: 24px; color: #fbfaff !important;"
			href="javascript:history.go(-1)"></a>
		<h1 class="mui-title" style="color: #fbfaff;">消费充值</h1>
	</header>
	
	<div class="czcount" style="color: #fff; ">
	 <div class="anniu">充值记录列表 <button><a href="${ctx}/shop/shop!recdetail.action?custid=${custid}&lscode=${lscode}">查看</a></button></div>
	
		<input id="lscode" name="lscode" type="hidden" value="${lscode}" />
		<div class="leftc">金额:</div>
		<div class="rightc">
			<p>
				<input id="paymoney" name="paymoney" type="radio" value="3000" />
				3000 
			</p>
			<p>
				<input id="paymoney" name="paymoney" type="radio" value="5000" />
				5000
			</p>
			<p>
				<input id="paymoney" name="paymoney" type="radio" value="10000" />
				10000
			</p>
		</div>
		<div class="clear"></div>
		
		<input type="submit" value="确认支付"  id="wxpay"    />
		<p class="tipp">提示：此处消费可直接用于兑换矿机，支付成功后的矿机商场的右上角会显示您的消费累计额，直接兑换即可！</p>
		
		 
		
		
	</div>
	<%@include file="/webcom/shop-foot3.jsp"%>

	<script>
		function weixinpay(val) {

			if (isWeiXin()) {
				var submitData = { 
				    paymoney : val,
				    lscode:  $('#lscode').val(),
					returnip : returnCitySN["cip"]
				};
				jQuery.post(
						'${ctx}/shop/shop!KJWXPay.action?lscode=${lscode}&custid=${custid}',
						submitData,
						function(json) {
							if (json.state == 5) {
								alert("数据加载出错！");
							} else if (json.state == 0) {
								var appid = json.appId;
								var timestamp = json.timeStamp;
								var noncestr = json.nonceStr;
								var packagevalue = json.packages;
								var paysign = json.paySign;

								//alert("appid:"+appid +"--packagevalue:"+packagevalue+"--noncestr:"+noncestr+"--paysign:"+paysign+"--timestamp:"+timestamp);  

								//如果有参数 ，唤起支付界面
								if (appid != null && timestamp != null&& appid != "") {

									//alert("00000000000000");  

									if (typeof WeixinJSBridge == "undefined") {
										if (document.addEventListener) {
											document.addEventListener('WeixinJSBridgeReady',onBridgeReady,false);
										} else if (document.attachEvent) {
											document.attachEvent('WeixinJSBridgeReady',onBridgeReady);
											document.attachEvent('onWeixinJSBridgeReady',onBridgeReady);
										}
									} else {
										//alert("0000000onBridgeReady0000000");  
										onBridgeReady(appid, timestamp,noncestr, packagevalue,paysign);

									}
								}

								return;
							} else {
								alert("添加失败！");
							}
						}, "json");
			} else {
				alert("请在微信里面操作")
			} 

		}

		//判断是否微信登陆
		function isWeiXin() {
			var ua = window.navigator.userAgent.toLowerCase();
			console.log(ua);//mozilla/5.0 (iphone; cpu iphone os 9_1 like mac os x) applewebkit/601.1.46 (khtml, like gecko)version/9.0 mobile/13b143 safari/601.1
			if (ua.match(/MicroMessenger/i) == 'micromessenger') {
				return true;
			} else {
				return false;
			}
		}

		function onBridgeReady(appid, timestamp, noncestr, packagevalue,paysign) {
			WeixinJSBridge.invoke(
				'getBrandWCPayRequest',
				{
					"appId" : appid, //公众号名称，由商户传入     
					"timeStamp" : timestamp, //时间戳，自1970年以来的秒数     
					"nonceStr" : noncestr, //随机串     
					"package" : packagevalue,
					"signType" : "MD5", //微信签名方式:     
					"paySign" : paysign
				//微信签名 
				},
				function(res) {
					if (res.err_msg == "get_brand_wcpay_request:ok") {

						alert("支付成功");
						window.location.href = "${ctx}/integral/miners!list.action?lscode=${lscode}";
						/*  window.location.href="http://www.cmcc123.com/paysucc.do?id="+rechargeid+"&mobile="+mobile;  */
					} // 使用以上方式判断前端返回,微信团队郑重提示:res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
				});
		}
		
		
		 
		 $(function(){
	         $("#wxpay").click(function(){
	            var val=$('input:radio[name="paymoney"]:checked').val();
	            if(val==null){
	                alert("请选择支付金额!");
	                return false;
	            }
	            else{
	                
	                weixinpay(val);
	            }
	                    
	         });
	     });
	</script>

</body>

</html>