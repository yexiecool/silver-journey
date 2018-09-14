<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%> 
<%@ include file="/webcom/limit.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>${title}</title>
    <!-- Resource style -->
    <script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
    <script src="${ctx }/app/js/jquery-1.8.3.js"></script>
    <script src="${ctx}/app/js/iosOverlay.js"></script>
    <script src="${ctx}/app/js/spin.min.js"></script>
    <script type="text/javascript" src="${ctx}/app/js/jquery.qrcode.js"></script>
    <script type="text/javascript" src="${ctx}/app/js/qrcode.js"></script> 
    <link href="${ctx}/app/css/iosOverlay.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx }/app/js/jquery.Spinner.js"></script>
    <link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx }/app/css/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx }/app/css/font-awesome-ie7.min.css" rel="stylesheet">
    <link href="${ctx }/app/css/style_0.css" rel="stylesheet"> 
    <%@ include file="/webcom/toast.jsp" %>
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
   .sixDigitPassword-box .active {
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
</style>
    <!--mjy结束-->
    <script type="text/javascript">
         var total=0;
         var remoney=0;
         var jfdh=0;
         var loading;
         var w= $(window).width()/2; 
         var zflx=0;
         var isxd=true;
          function  loading(){
        var opts = {
		lines: 13, // The number of lines to draw
		length: 8, // The length of each line
		width: 4, // The line thickness
		radius: 13, // The radius of the inner circle
		corners: 1, // Corner roundness (0..1)
		rotate: 0, // The rotation offset
		color: '#FFF', // #rgb or #rrggbb
		speed: 1, // Rounds per second
		trail: 60, // Afterglow percentage
		shadow: false, // Whether to render a shadow
		hwaccel: false, // Whether to use hardware acceleration
		className: 'spinner', // The CSS class to assign to the spinner
		zIndex: 2e9, // The z-index (defaults to 2000000000)
		top: 'auto', // Top position relative to parent in px
		left: 'auto' // Left position relative to parent in px
	}; 
	   var target = document.createElement("div");
	   document.body.appendChild(target);
	   var spinner = new Spinner(opts).spin(target);
	  loading=iosOverlay({
		text: "Loading", 
		spinner: spinner
	   });
     }
          
          $(function(){ 
        		$('#close').click(function(){
        			$('.mask').css('display','none')
        		}) 
//      		$('.modal').click(function(){
//      			$('.modal').css('display','none')
//      		})
        		 
        	if('${entity.jfdh}'==''||'${entity.jfdh}'==0){ 
        	   var totalPrice='${entity.price}'*'${count}'; 
        	    if('${price}'>0){
        	     totalPrice=parseFloat('${price}')*'${count}';
        	    }
        	   remoney=totalPrice.toFixed(2); 
        	    if('${byprice}'==''||'${byprice}'<=0||totalPrice<'${byprice}'){ 
        	     var kd='${entity.kdprice}'; 
        	      if(kd!=''&&kd>0){ 
        	       totalPrice=parseFloat(totalPrice)+parseFloat(kd);  
        	       $("#totalPrice").html(totalPrice.toFixed(2)+'元  <i>快递:￥'+parseFloat(kd).toFixed(2)+'</i>');
        	       
        	      }else{
        	      $("#totalPrice").html(totalPrice.toFixed(2));
        	     
        	     } 
        	    }else{ 
        	   $("#totalPrice").html(totalPrice.toFixed(2));
        	  }
        	   total=totalPrice.toFixed(2);
        	  }else{
        	   var totalPrice='${entity.jfdh}'*'${count}';
        	   jfdh=totalPrice.toFixed(2);
        	   total='${entity.kdprice}'; 
        	 $("#totalPrice").parent().html('<i id="totalPrice" class="fa fa-cny pl-5 weight500">'+totalPrice.toFixed(2)+'</i>平台币 快递'+parseFloat('${entity.kdprice}').toFixed(2)+'元');
        	}
        	
        	});
  
         function moneypay(){
         if('${address.tel}'==""){
            alert("请先设置收货地址");
            return ;
           }
           if('${entity.jfdh}'>0&&'${entity.kdprice}'==0){
             jfpay();
             return;
           }
           if('${count}'==0){
            alert("数量不能为空请重新选择购买");
            return ;
           }
          /*  if($('#deptcode').val() == ''){
        	   alert('请填写部门编号');
        	   return;
           } */
           if(!isxd){ 
        	   return;
           }
           isxd=false;
            var address='${address.province}'+"-"+'${address.city}'+"-"+'${address.county}'+" "+'${address.address}';
            
            var submitData = { 
        			lx:0,
        			no:'0',
        			name:'${address.name}',
        			tel:'${address.tel}',
        			address:address, 
        			total:total, 
                	remoney:'${entity.price}',
                	recordid:'${entity._id}', 
        			price:total,
        			remark:'${entity.ptitle}',
        			comid:'${entity.comid}',
        			kjid:$("#kdpric").val(),
        			num:'${count}',
        			logo:'${entity.logo}',
        			title:'${entity.title}',
        			spec:'${spec}',
        			jffh:'${entity.jffh}',
        			jfdh:jfdh,
        			deptCode:$('#deptcode').val(),
        			zflx:zflx
        	}; 
        	//loading();
        	/* $.post('${ctx}/shop/shop!wxpay.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData,
        		function(json) { 
        		     loading.hide();
        		 	if (json.state == 0) { 
        				WeixinJSBridge.invoke('getBrandWCPayRequest',{
        			  		 "appId" : json.appId,"timeStamp" : json.timeStamp, "nonceStr" : json.nonceStr, "package" : json.packageValue,"signType" : json.signType, "paySign" : json.paySign 
        			   			},function(res){  
        			   				if(res.err_msg == "get_brand_wcpay_request:ok"){  
        			   					 var text='购买成功！';
        			   					 if(!jQuery.isEmptyObject(json.jffh)){
        			   					   text="购买成功！获得平台币"+json.jffh
        			   					 }
        			   					 noty({text: text,type:'alert', layout: "top", timeout: 1000,callback: { // 回调函数
                                              afterClose: function() {
                                        window.location.href="${ctx}/shop/shop!orderform.action?custid=${custid}&agid=${agid}&lscode=${lscode}";
                                              } // 关闭之后
                                            },});
        			   				}else{
        			   				
        			   					//alert(res.err_code+res.err_desc+res.err_msg);
        			   					 
        			   				}
        						}); 
        				return;
        			}else if(json.state == 1) {
        				alert("该账号没有开通支付"); 
        			}else if(json.state == 3){
        			  alert("没有登录");
        			}else if(json.state==10){
        			  alert("购买次数已完");
        			}
        		},
        		"json") */
        		$.post('${ctx}/shop/shop!COrderFromCar.action?custid=${custid}&agid=${agid}&lscode=${lscode}&isgwc=1', submitData,
                		function(json) {
        			      isxd=true;
                		     //loading.hide();
                		 	if (json.state == 0) { 
                				var orderno=json.orderno; 
                				$('#orderno').val(json.orderno);
                    			$('.mask').css('display','block');  
                			}else if(json.state == 1) {
                				alert("该账号没有开通支付"); 
                			}else if(json.state == 3){
                			  alert("没有登录");
                			}else if(json.state==10){
                			  alert("购买次数已完");
                			}
                		},
                		"json")
        	 
        } 
         
     	function copyUrl2()
    	{
    	
    		$("#bturl").select(); // 选择对象
    		document.execCommand("Copy"); // 执行浏览器复制命令
    	}
     	function copyUrl3()
    	{
    	
    		$("#ytfurl").select(); // 选择对象
    		document.execCommand("Copy"); // 执行浏览器复制命令
    	}
     	
     	function copyUrl4()
    	{
    	
    		$("#btnum").select(); // 选择对象
    		document.execCommand("Copy"); // 执行浏览器复制命令
    	}
     	function copyUrl5()
    	{
    	
    		$("#ytfnum").select(); // 选择对象
    		document.execCommand("Copy"); // 执行浏览器复制命令
    	}
     	
            
         function popcode(val){ 
        	  var txt="1GTapaVtP9JgS4GHtnxZbcoFTxdKXECuKu";
        	  zflx=val;
        	 if(val == 0){
        		 if('${entity.goodstype}'==3){
           		  txt="3AYHYGbC9uE5vBWfbQutyrEmyUd3cAwEdY";
           	  }else if('${entity.goodstype}'==5){
           		  txt="3MKiB7ZX16zgwAwbXxM4LugGXm3Co1fFJe";
           	  }else if('${entity.goodstype}'==4){
           		  txt="3CS2WvYFnrwL1G3e9jKg6o4guGh4boP4Pr";
           	  }
        		 $('#bt').css('display','block');
    				pay_bt();
    				//二维码生成
    				$('#qrcode').qrcode({ 
    				  width : w,
    			      height : w,
    			      text	: txt
    			    });
    			    $("#bturl").val(txt); 
        	 }
        	 
        	 if(val == 1){
        		 if('${entity.goodstype}'==3){
           		  txt="0xefa03a9B480A9890C5541065A398F43f82F32832";
           	  }else if('${entity.goodstype}'==5){
           		  txt="0xc3d8d8fBDb34dA5930d95869b692D80B668d4b98";
           	  }else if('${entity.goodstype}'==4){
           		  txt="0x842B0afCaA759ea325A915D2a5e5963B618DcEf1";
           	  }
        		 $('#ytf').css('display','block');
    				pay_ytf();
    				//二维码生成
    				$('#qrcodes').qrcode({ 
    				  width : w,
    			      height : w,
    			      text	: txt
    			    });
    				$("#ytfurl").val(txt); 
        	 }
        	 if(val == 2){ 
        		 zflx=3;
        		 $(".bmodal").show();
        		 $('.mask').css('display','none');
        		 
            } //微信支付
        		if (val == 3) {
        			
        	 		if(isWeiXin()){
        	 			var submitData = {
        	 					orid : $('#orderno').val(),
        	 					 returnip  :  returnCitySN["cip"]
        	 				};
        	 				jQuery.post(
        	 				   '${ctx}/shop/shop!WXPay.action?lscode=${lscode}&custid=${custid}',
        	 					submitData, function(json) {
        	 						if (json.state == 5) {
        	 							alert("数据加载出错！");
        	 						}else if(json.state == 3){
        	 							alert("库存不足");
        	 						}else if(json.state == 4){
        	 							alert("商品已下架");
        	 						}else if (json.state == 0) {
        	 							  var appid = json.appId ;
        	 							  var timestamp =json.timeStamp ;
        	 							  var noncestr = json.nonceStr ;
        	 							  var packagevalue = json.packages ;
        	 							  var paysign = json.paySign ;
        	 							  
        	 							  //alert("appid:"+appid +"--packagevalue:"+packagevalue+"--noncestr:"+noncestr+"--paysign:"+paysign+"--timestamp:"+timestamp);  
        	 								 
        	 							
        	 							//如果有参数 ，唤起支付界面
        	 						 	if(appid!=null && timestamp!=null && appid!="" ){
        	 						 		
        	 						 		 //alert("00000000000000");  
        	 						 		 
        	 							    if (typeof WeixinJSBridge == "undefined"){
        	 								   if( document.addEventListener ){
        	 								       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        	 								   }else if (document.attachEvent){
        	 								       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
        	 								       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        	 								   }
        	 								}else{
        	 									 //alert("0000000onBridgeReady0000000");  
        	 								   onBridgeReady(appid,timestamp,noncestr,packagevalue,paysign);
        	 								  
        	 								}
        	 							  }
        	 							 
        	 							return;
        	 						}  else {
        	 							alert("添加失败！");
        	 						}
        	 					}, "json");
        	 		}else{
        					alert("请在微信里面操作")
        				}  
        			
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
         	
          


         function onBridgeReady(appid,timestamp,noncestr,packagevalue,paysign){
                WeixinJSBridge.invoke(
         	       'getBrandWCPayRequest', {
         	           "appId" : appid,     //公众号名称，由商户传入     
         	           "timeStamp":timestamp,         //时间戳，自1970年以来的秒数     
         	           "nonceStr" : noncestr, //随机串     
         	           "package" : packagevalue,     
         	           "signType" : "MD5",         //微信签名方式:     
         	           "paySign" : paysign //微信签名 
         	       },
         	       function(res){     
         	           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
         	        	    
         	        	   alert("支付成功");
         	        	   window.location.href = "${ctx}/shop/shop!orderform.action?agid=${agid}&lscode=${lscode}";
         	        	  /*  window.location.href="http://www.cmcc123.com/paysucc.do?id="+rechargeid+"&mobile="+mobile;  */
         	           }     // 使用以上方式判断前端返回,微信团队郑重提示:res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
         	       }
         	   ); 
         	}
          
         
         
         function zhifu(){
        	 if('${isfull}' != '1'){
					alert('您还未补全信息，请先补全信息，即可支付订单');
					window.location.href = "${ctx}/user/fromuser!safePwd.action?custid=${custid}&agid=${agid}&lscode=${lscode}";
			        return;
        	 }
        	 var submitData = { 
	    			  password:$('#payPassword_rsainput').val()
	    	    }; 
	    		 $.post('${ctx}/integral/miners!wdpassword.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData,
				        	function (json) {
				            	if(json.state==0){
						        		 var submitData1 = { 
						        				 orid:$('#orderno').val(),
						        				 zflx:3
						             	}; 
						        		 $.post('${ctx}/shop/shop!OrderPayJf.action?lscode=${lscode}', submitData1,
						                 		function(json) {
						    					//alert(json.state);
						         					if(json.state == 0){
						         						alert("支付成功！");
						         						window.location.href="${ctx}/shop/shop!orderform.action?agid=${agid}&lscode=${lscode}";
						         					}else if(json.state == 1){
						         						alert("操作失败");
						         						$(".bmodal").hide();
						         					}else if(json.state == 2){
						         						alert("PADA不足，请重新选择付款方式");
						         						$(".bmodal").hide();
						         					}else if(json.state == 3){
						         						alert("库存不足");
						         						$(".bmodal").hide();
						         					}else if(json.state == 4){
						         						alert("商品已下架");
						         						$(".bmodal").hide();
						         					}else if(json.state == 5){
						         						alert("订单不存在");
						         						$(".bmodal").hide();
						         					}else if(json.state == 6){
						         						alert("订单不存在");
						         						$(".bmodal").hide();
						         					}
						                 		},"json");
				            	}else if(json.state==1){
				            		alert('操作失败,请重新输入');
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

        function  jfpay(){
         var address='${address.province}'+"-"+'${address.city}'+"-"+'${address.county}'+" "+'${address.address}';
        	var submitData = { 
        			lx:0,
        			no:'0',
        			name:'${address.name}',
        			tel:'${address.tel}',
        			address:address, 
        			total:total, 
                	remoney:'${entity.price}',
                	recordid:'${entity._id}', 
        			price:total,
        			remark:'${entity.ptitle}',
        			comid:'${entity.comid}', 
        			num:'${count}',
        			logo:'${entity.logo}',
        			title:'${entity.title}',
        			spec:'${spec}',
        			jffh:'${entity.jffh}',
        			jfdh:jfdh,
        			
        	}; 
        	$.post('${ctx}/shop/shop!jfpay.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData,
        		function(json) { 
        			if (json.state == 0) {
        				  noty({text: "兑换成功！",type:'alert', layout: "top", timeout: 1000,callback: { // 回调函数
                                              afterClose: function() {
                                        window.location.href="${ctx}/shop/shop!orderform.action?custid=${custid}&agid=${agid}&lscode=${lscode}";
                                              } // 关闭之后
                                            },});
        			}else{
        			  noty({text: "兑换异常！",type:'alert', layout: "top", timeout: 1000,callback: { // 回调函数
                                              afterClose: function() {
                                       // window.location.href="${ctx}/shop/shop!orderform.action?custid=${custid}&agid=${agid}&lscode=${lscode}";
                                              } // 关闭之后
                                            },});
        			}
        		},
        		"json")
        
        
        }
   
    </script>
    <style>
    
    
    
    
    
        .bk {
            height: 70px;
            width: 90px;
        }

        .button-kong {
            width: 30px;
            height: 30px;
            line-height: 26px;
            border-top: solid 2px #eee;
            border-bottom: solid 2px #eee;
        }

        .line-height33 {
            line-height: 33px;
        }
         .hang2-bj {
            height: 2px;
            background-image: url(${ctx}/img/scdz-xz.png);
        }
         .mask,
			.modal {
				width: 100%;
				height: 100%;
				background: rgba(0, 0, 0, .1);
				position: fixed;
				top: 0;
				left: 0;
				right: 0;
				bottom: 0;
				z-index: 100;
				display: none;
			}
			
			.mask-cont {
				width: 100%;
				height: auto;
				background: #fff;
				position: absolute;
				bottom: 50px;
				left: 0;
				padding: 0 10px;
				z-index: 101;
			}
			
			.mask-cont-tit {
				width: 100%;
				height: 30px;
				line-height: 30px;
				text-align: center;
				position: relative;
			}
			
			.mask-cont-tit::after {
				content: '';
				width: 100%;
				height: 0.5px;
				position: absolute;
				bottom: 0;
				left: 0;
				background: #ddd;
			}
			
			.mask-cont-cont {
				width: 100%;
				height: auto;
				padding-bottom: 10px;
			}
			
			.mask-cont-cont button {
				width: 100%;
				height: 34px;
				line-height: 34px;
				background: none;
				border: 1px solid #000;
				border-radius: 5px;
				display: block;
				margin-top: 10px;
			}
			
			.modal {
				z-index: 1001;
				background: rgb(127, 127, 127);
			}
			
			.modal-cont {
				width: 80%;
				height: auto;
				z-index: 1002;
				margin: 0 auto;
				margin-top: 50px;
				text-align: center;
				background: #fefefe;
				border-radius: 5px;
				padding: 10px;
			}
			.modal-tit{
				line-height: 16px;
				position: relative;
				font-size: 12px;
				margin-top: 10px;
			}
			.modal-cont-tit{
				line-height: 16px;
				font-size: 11px;
				padding: 5px 0 5px 10px;
				text-align: left;
				height:80px;
				overflow-y: auto;
			}
			.modal-cont-tit p{
				margin: 0; font-size:12px;
			}
			canvas{
				background: #fefefe;
				border-radius: 10px;
				padding: 10px;
			}
			.gopayBtn{
				width: 100px;
				height: 34px;
				line-height: 34px;
				color: #fff;
				background: #E4393C;
				display: inline-block;
				border-radius: 5px;
				margin-top: 15px;
				text-decoration: none;
			}
			.bturlBox{
				width: 100%;
				height: 24px;
			}
			input:focus{
				outline: none;
			}
			#bturl{
				width: 80%;
				margin: 0 auto;
				border: none;
				border-bottom: 1px solid #e3e3e3;
			}
			.virtualcoin{
				display: block;
    			line-height: 24px;
    			color: #e4393c;
			}
			.bmodal {
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
			
			.bmodal-cont {
				width: 80%;
				height: 200px;
				background: #fff;
				border-radius: 10px;
				margin: 0 auto;
				margin-top: 50px;
			}
			
			.bmodal-cont-tit {
				width: 100%;
				height: 34px;
				line-height: 34px;
				text-align: center;
				position: relative;
			}
			
			.bmodal-cont-tit::after {
				content: '';
				width: 100%;
				height: 1px;
				background: #e3e3e3;
				position: absolute;
				bottom: 0;
				left: 0;
			}
			
			.bmodal-cont-cont {
				width: 100%;
				height: 132px;
				line-height: 132px;
				position: relative;
			}
			.bmodal-cont-cont p {
			       float:left; margin-top:20%;
				   width:94%;padding:3%; font-size:14px; 
				   height：auto; 			   
				    word-break:normal;
					white-space:pre-warp;
					word-wrapL:break-word; 
					line-height:20px;
				   
			}
			
			.sixpwd1 {
				width: 240px
				margin: 0 auto;
				border: 1px solid #000;
				position: absolute;
				top: 20%;
				left: 10%;
				height: 34px;
				line-height: 20px;
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
			.bmadol-foot{
				width: 100%;
				display: flex;
				height: 34px;
				line-height: 34px;
			}
			.bmadol-foot button{
				width: 1%;
				flex: 1;
				border: none;
				color: #fff;				
				border-radius:0 ;
			}
			.bmadol-foot button:first-child{
				border-bottom-left-radius: 5px;
				background: #E4393C;
			}
			.bmadol-foot button:last-child{
				
				background: #007AFF;
				border-bottom-right-radius: 5px;
			}
    </style>
</head>
<body>
 <header style="width: 100%;height: 44px;line-height: 44px;text-align: center;padding: 0 10px;background: #fff;border-bottom: 1px solid #ddd;">
		<a  href="javascript:history.back(-1);" style="display: inline-block;float: left;width: 30px;height: 30px;background: url('${ctx}/xmMobile/img/goback.png') no-repeat;background-size: 100% 100%;margin-top: 10px;"></a>
			商品购买
	</header>
<main class="clear cmp640 lock"style='padding-top: 44px;'>
    <c:if test="${not empty address}">
     <a href="${ctx}/shop/shop!useraddress.action?lscode=${lscode}&custid=${custid}&agid=${agid}&addressis=ok&backurl=/shop/shop!orderconfirmation.action?pid=${entity._id}&count=${count}&price=${price}&spec=${spec}">
        
        <font size="2">
            <div class="div-group-10 overflow-hidden zi-6 weight100 col-11">
                <div class="">
                    <div class="pull-left">收货人:<i>${address.name}</i></div>
                    <div class="pull-right">${address.tel}</div>
                </div>
                <div class="clear pt-5">
                    <div class="pull-left txt-l sl width-10">收货地址:<i>${address.address}</i></div>
                </div>
            </div>
            <div class="col-1 div-group-5 txt-c zi-hui hang40 overflow-hidden">
                <i class="fa fa-chevron-right fa-1dx" style="line-height:50px;"></i>
            </div>
            <div class="hang10  clear"></div>
        </font>
         <div class="hang2-bj"></div>
        <div class="hang10 bg-hui clear"></div>
     </a>
    
    </c:if>
    <c:if test="${empty address}">
    <a href="${ctx}/shop/shop!useraddresssave.action?lscode=${lscode}&custid=${custid}&agid=${agid}&addressis=err&backurl=/shop/shop!orderconfirmation.action?pid=${entity._id}&count=${count}&price=${price}&spec=${spec}">
     <div class="hang60 div-group-10">
        <div class="hang40 line-height40">
            <div class="img-wh40 pull-left txt-c line-height40 bj-lan3 zi-bai border-radius5"><i
                    class="fa fa-plus fa-1dx line-height40"></i></div>
            <div class="zi-hui pl-15 pull-left">新增收货地址</div>
            <div class="zi-hui pull-right"><i class="fa fa-1dx fa-angle-right line-height40"></i></div>
     </div>
    </div>
    <div class="hang2-bj"></div>
    <div class="hang10 bg-hui clear"></div>
    </a>
    </c:if>
    <div class="clear"></div>
	<div style="width: 100%;height: 40px;padding: 0 10px;">
		<input type="text" name="" id="deptcode" value="" style="width: 100%;height: 100%;border-bottom: 1px solid #ddd;" placeholder="请填写你的部门编号"/>
	</div>
    <div class="line-bottom div-group-10 overflow-hidden position-r" >
        <div class=" position-a">
            <div class="img-bj bk border-radius3" style="background-image: url(${filehttp}/${entity.logo});"></div>
        </div>
        <div style="padding-left:100px;">
            <div class="zi-6 weight500 pt-2">
                <font size="2">
                    <div class="col-10 sl zi-hei">${entity.ptitle}</div>
                </font>
            </div>
            <div class="pull-left weight500">
                <font size="2">
                    <div class="clear sl hang30" style="line-height:35px;">
                        <span class="zi-hui">规格：${spec}</span>
                    </div>
                    <div class="sl " style="line-height:30px;">
                      <c:if test="${empty entity.jfdh||entity.jfdh==0}">
                       <c:if test="${empty price}">
                       <span class="zi-cheng">￥<fmt:formatNumber value='${entity.price}'  pattern="0.0#"/>元
                       </c:if>
                       <c:if test="${not empty price}">
                       <span class="zi-cheng">￥<fmt:formatNumber value='${price}'  pattern="0.0#"/>元
                       </c:if>
                       
                      </c:if>
                      <c:if test="${entity.jfdh>0}">
                      <span class="zi-cheng">平台币<fmt:formatNumber value='${entity.jfdh}'  pattern="0.0#"/>
                      </c:if>
                       <i class="pl-10 zi-6">数量:${count}件</i></span>
                       <span class="virtualcoin" id="PPBprice"></span>
                    </div>
                </font>
            </div>
        </div>
    </div>
     
    <div class="hang40 clear"></div>

    <c:if test="${empty entity.jfdh}">
      <input type="hidden" value="<fmt:formatNumber value='${entity.price}'  pattern="0.0#"/>" id="price"/><!--单价-->
    </c:if>
    <c:if test="${entity.jfdh>0}">
      <input type="hidden" value="<fmt:formatNumber value='${entity.jfdh}'  pattern="0.0#"/>" id="price"/><!--单价-->
    </c:if>
   
    <input type="hidden" value="<fmt:formatNumber value='${entity.kdprice}'  pattern="0.0#"/>" id="kdprice"/><!--单价-->
   
</main>
<input id="orderno" type="hidden"></input>
<div class=" button_foot shadow-wai overflow-hidden bg-bai cmp640">
 
    <div class="div-group-5 hang50 overflow-hidden line-top">
        <div class="col-8 zi-hong weight500 pl-15" style="line-height:42px;">
            <font size="2">
            <c:if test="${empty price}">
              总计:<i id="totalPrice" class="fa fa-cny pl-5 weight500"><fmt:formatNumber value='${entity.price}'  pattern="0.0#"/></i>元
            </c:if>
            <c:if test="${not empty price }">
             总计:<i id="totalPrice" class="fa fa-cny pl-5 weight500"><fmt:formatNumber value='${price}'  pattern="0.0#"/></i>元       
            </c:if>
              
            </font>
        </div>
        <div class="col-4 zi-bai size14 weight500 txt-c pull-right">
            <!--<a href="javascript:moneypay()">-->
            
                <div class=" hang40 ">
                    <div class="hang40 line-height40 btn-lu border-radius3"  onclick="moneypay()">确认付款</div>
                </div>
            <!--</a>-->
        </div>
    </div>
</div>
<div class="mask">
	<div class="mask-cont">
		<div class="mask-cont-tit">
			付款方式
			<i class="fa fa-close pull-right" style="font-size: 16px;padding-right: 5px;padding-top: 5px;" id="close"></i>
		</div>
		<div class="mask-cont-cont">
		<c:if test="${entity.goodstype == 3 || entity.goodstype == 5}">
			<button onclick="popcode(0)" class="currency">比特币</button>
			<button onclick="popcode(1)" class="currency">以太坊</button>
	    </c:if>
	     <c:if test="${entity.goodstype != 3  }">
			<button onclick="popcode(2)" class="currency">盼盼币</button>
			</c:if> 
			<button onclick="popcode(3)" class="currency">微信支付</button>
		
		</div>
		
	</div>
</div>
<div class="modal" id="bt">
	<div class="modal-cont">
		<div style="height: 34px;font-size: 18px;text-align: center;">
			BitCoin
		</div>
		<div id="qrcode"></div>
		<div class="bturlBox"  style="height: 34px;line-height: 34px;font-size: 10px;">
			<input type="text" id="bturl" style=" line-height: 30px; height:30px; padding:5px; background: #fff !important;width:80%;float:left;  font-size:.6rem;"/>
			<input type="button" onClick="copyUrl2()" value="复制"  style="width:18%; height:30px; background:#E4393C; color:#fff; border-radius:5px;   font-size:.6rem;  float:right; "  />
		</div>
		
		<div class="modal-tit">
			提示
		</div>
		<div class="modal-cont-tit">
			<p>1.您需要支付的比特币:</p>
			<div class="bturlBox"  style="height: 34px;line-height: 34px;font-size: 10px;">
				<input type="text" id="btnum" style=" line-height: 30px; height:30px; padding:5px; background: #fff !important;width:80%;float:left;  font-size:12px;"/>
				<input type="button" onClick="copyUrl4()" value="复制"  style="width:18%; background:#E4393C; color:#fff; border-radius:5px;   height:30px; font-size:.6rem;  float:right; "  />
			</div>
			<p>2.请备注你的手机号码;</p>
			<p>3.请备注你的会员号码;</p>
			<p>4.请交易手续费最大化;</p>
			<p>5.平台确认收币后发货，若由此造成的延误由个人承担</p>
			<p>6.请复制上方的付款码</p>
		</div>
		<a href="${ctx}/shop/shop!orderform.action?agid=${agid}&lscode=${lscode}" class="gopayBtn">去付款</a>
	</div>
</div>
<div class="modal" id="ytf">
	<div class="modal-cont" >
		<div style="height: 34px;font-size: 18px;text-align: center;">
			ETH
		</div>
		<div id="qrcodes"></div>
		<div class="bturlBox"  style="height: 34px;line-height: 34px;font-size: 10px;">
					<input type="text" id="ytfurl" style=" line-height: 30px; height:30px; padding:5px; background: #fff !important;width:80%;float:left;  font-size:.6rem;"/>
			       <input type="button" onClick="copyUrl3()" value="复制"  style="width:18%; height:30px; font-size:.6rem;  float:right; background:#E4393C;  color:#fff; border-radius:5px; "  />		
		</div>
		<div class="modal-tit">
			提示
		</div>
		<div class="modal-cont-tit">
			<p>1.您需要支付的以太坊币:</p>
			<div class="bturlBox"  style="height: 34px;line-height: 34px;font-size: 10px;">
				<input type="text" id="ytfnum" style=" line-height: 30px; height:30px; padding:5px; background: #fff !important;width:80%;float:left;  font-size:12px;"/>
		        <input type="button" onClick="copyUrl5()" value="复制"  style="width:18%; height:30px; font-size:.6rem;  float:right; background:#E4393C;  color:#fff; border-radius:5px; "  />		
		    </div>
			<p>2.请备注你的手机号码;</p>
			<p>3.请备注你的会员号码;</p>
			<p>4.请交易手续费最大化;</p>
			<p>5.平台确认收币后发货，若由此造成的延误由个人承担</p>
			<p>6.请复制上方的付款码</p>
		</div>
		<a href="${ctx}/shop/shop!orderform.action?agid=${agid}&lscode=${lscode}" class="gopayBtn">去付款</a>
	</div>
</div>
<div class="bmodal">
				<div class="bmodal-cont">
					<div class="bmodal-cont-tit">
						请输入支付密码
					</div>
					<div class="bmodal-cont-cont">
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
						<p>备注：该购物密码是您在个人资料中设置的，未设置点击确认前往个人资料中设置并完善。</p>					 				  					  
					</div>
					<div class="bmadol-foot">
							<button class="cancel" style="background:#73d1c4;" >取消</button><button onclick="zhifu()" style="background:#f20101;">确认</button>
					</div>
				</div>
			</div>
</body>

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
			$('#cardwrap').css('left',j*40+'px');
		
			}
		}else{
		//输入其他字符，直接清空
			var _val = this.value;
			this.value = _val.replace(/\D/g,'');
		}
	});	
	
</script>	

<!--mjy结束-->
<script>
	
//支付弹出
//$('.bmodal').show();

$('.cancel').click(function(){
	$('.bmodal').hide();
})


function pay_bt(){
	var totalPrice = $('#totalPrice').html();
	var submitData = {
	};
	$.post('${ctx}/integral/miners!getBTCSrice.action?lscode=${lscode}', submitData,
		function(json) {
	    if(json.state==0){
	       if(totalPrice != '0.00'){
	    	   $('#btnum').val(parseFloat(totalPrice)/json.data); 
	       }else{
	    	   $('#btnum').val(0.00);
	       }
	    	
	   }
				
	},"json")
}
function pay_ytf(){
	var totalPrice = $('#totalPrice').html();
	var submitData = {
	};
	$.post('${ctx}/integral/miners!getETHSrice.action?lscode=${lscode}', submitData,
		function(json) {
	    if(json.state==0){
	       if(totalPrice != '0.00'){
	    	   $('#ytfnum').val(parseFloat(totalPrice)/json.data); 
	       }else{
	    	   $('#ytfnum').val(0.00);
	       }
	   }			
	},"json")
}
function pay_ppb(){
	var totalPrice = $('#totalPrice').html();
	var submitData = {
	};
	$.post('${ctx}/integral/miners!getPPBSrice.action?lscode=${lscode}', submitData,
		function(json) {
	    if(json.state==0){
	       if(totalPrice != '0.00'){
	    	   $('#PPBprice').html("PADA:"+parseFloat(totalPrice)/json.data); 
	       }else{
	    	   $('#PPBprice').html("PADA:"+0.00);
	       }
	   }			
	},"json")
}
pay_ppb();
</script>
</html>