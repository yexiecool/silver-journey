<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ include file="/webcom/limit.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>${title}</title>
    <!-- Resource style -->
    <script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
    <link href="${ctx}/app/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="${ctx}/app/css/font-awesome-ie7.min.css" rel="stylesheet"/>
    <link href="${ctx}/app/css/style_0.css" rel="stylesheet"/> 
    <link href="${ctx}/app/css/iosOverlay.css" rel="stylesheet"/>
	<link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css"/>
    <!-- Resource style -->
    <script src="${ctx}/app/js/jquery-1.8.3.js"></script>
    <script src="${ctx}/app/js/iosOverlay.js"></script>
    <script src="${ctx}/app/js/spin.min.js"></script>
    <script type="text/javascript" src="${ctx }/app/js/jquery.Spinner.js"></script>
    <script type="text/javascript" src="${ctx }/app/js/swipe.js"></script>
    <script type="text/javascript" src="${ctx}/app/js/jquery.qrcode.js"></script>
    <script type="text/javascript" src="${ctx}/app/js/qrcode.js"></script> 
    <script type="text/javascript">
     
    </script>
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
    
    
    
    <style> 

        .web-site {
            margin-left: 125px;
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
          .bk {
            height: 70px;
            width: 70px;
        }
        .border-bottom-d9d9d9{
            border-bottom: solid 1px #d9d9d9;
            
        }
        .zi-353535{
            color: #353535;
        }
        .zi-bbbbbb{
            color: #bbbbbb;
        }
        .zi-9a9a9a{
            color: #9a9a9a;
        }
        .zi-d8d8d8{
            color: #d8d8d8;
        }
        .hang-sl-2{
            overflow: hidden;text-overflow:ellipsis;display: -webkit-box;-webkit-line-clamp:4;-webkit-box-orient:vertical;
        }
        .masks,
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
				text-align: center;
				background: #fefefe;
				border-radius: 5px;
				padding: 10px;
				overflow-y: auto;
				position: absolute;
			    top: 40px;
			    left: 10%;
			    right: 10%;
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
				padding: 5px 0px 5px 10px;
				text-align: left;
				height: 80px;
				overflow-y: auto;
			}
			.modal-cont-tit p{
				margin: 0;
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
				input:focus {
				outline: none;
			}
			
			.mui-input-row {
				position: relative;
			}
			
			.mui-input-row label {
				width: 20%;
				font-size: 14px;
			}
			
			.mui-input-row label~input,
			.mui-input-row label~select,
			.mui-input-row label~textarea {
				width: 80%;
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
				width: 244px;
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
			.bmadol-foot{
				width: 100%;
				/*display: flex;*/
				height: 34px;
				line-height: 34px;
				overflow: hidden;
			}
			.bmadol-foot button{
				width: 50%;
				height: 100%;
				/*flex: 1;*/
				float: left;
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
    <script>
    
    var issend=true;
   
    var fypage=0;
    var xszf="";
	var type="";
	 //loding
	var loading;
	 ajaxjz();
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
	 
	function ajaxjz(){//加载  
	    if(!issend){
	    	return;
	    }
	   //loading()
	   	/* var submitData = {
	   		state:${state}
	    };  */
	    var data = {
		   		state:"${state}"
		    }; 
	   
	    issend=false; 
	    $.post('${ctx}/shop/shop!ajaxorders1.action?custid=${custid}&agid=${agid}&lscode=${lscode}&fypage='+fypage, data,
	       	function(json) { 
	       		//loading.hide()
	    		var xszf=$('#ajaxdiv').html();  
	    		
		    	if(json.state=='0'){
		    		var v = json.list; 
		    		console.log(v);
		    		 for(var i=0;i<v.length;i++){ 
		    			 var price=parseFloat(v[i].kdprice)+parseFloat(v[i].ppb_money);
		    		  xszf+='<div class="pl-15 pr-15 pt-15 overflow-hidden" >'
		    		      +'<div class="bg-bai border-radius5" style="padding:10px">'
		    		      +'<div class="hang30 line-bottom-98 zi-hui-tq weight500 overflow-hidden line-height30 pl-5 pr-5 zi-353535">'
		    		      +'<font size="1"><div class="col-8 sl">订单编号:<i class="pl-5">'+v[i]._id+'</i></div>'
		    		      +'<span style="display:block;color:#e4393c; " class="col-3">PADA:'+price+'</span>'
		    		      +'<div class="col-1 txt-r sl zi-cheng" onclick="del('+v[i]._id+')"><i class="fa fa-trash-o zi-hong line-height30"></i></div>'
							/* if(v[i].state==1){
		    		          	  xszf+='<div class="col-4 txt-r zi-bbbbbb">已下单</div>'
		    		        }else if(v[i].state==2){
		    		          xszf+='<div class="col-4 txt-r zi-bbbbbb">待发货</div>'
		    		        }else if(v[i].state==3){
		    		          xszf+='<div class="col-4 txt-r zi-bbbbbb" onclick="resure('+v[i]._id+')">确认收货</div>';
		    		        }else if(v[i].state==4){
		    		        	xszf+='<div class="col-4 txt-r zi-bbbbbb">订单完成</div>'
		    		        	   xszf+='<div class="col-4 txt-r zi-bbbbbb">订单完成</div>'
		    		          		 +'<div class="col-4 txt-r zi-bbbbbb" onclick="shopcom('+list[j]._id+','+list[j].pro._id+')">评价</div>'; 
		    		         }else if(v[i].state==5){
		    		          xszf+='<div class="col-4 txt-r zi-bbbbbb">已退款</div>';
		    		         } */
		    		      xszf+=''
		    		      +'</font></div>';
		    		      if(v[i].comlist!=null){
		    		    	   var comlist=v[i].comlist; 
		    		    	   for(var k=0;k<comlist.length;k++){
		    		    		   xszf+='<div style="line-height:30px"><span>'+comlist[k].shop.name+'</span>';
		    		    		    if(comlist[k].list!=null){
		    			    		       var list=comlist[k].list;
		    			    		       var mprice=parseFloat(v[i].zfmoney)+parseFloat(v[i].kdprice);
		    			    		       if(list[0].goodstate == 1 || list[0].goodstate == 0){
		    			    		    	   xszf+='<div class="col-5 txt-r zi-bbbbbb" style="float: right;margin-right: 5px;" >待付款<span href="" style="color:#e4393c;margin-left: 5px;" onclick="choosePay('+v[i]._id+','+mprice+','+list[0].pro.goodstype+')">去支付</span></div>'
		    			    		    		   
		    			    		       }
		    			    		       
		    			    		       if(list[0].goodstate == 2){
		    			    		    	   xszf+='<div class="col-4 txt-r zi-bbbbbb" style="float: right;margin-right: 5px;" >待发货</div>'
		    			    		       }
		    			    		       if(list[0].goodstate == 7){
		    			    		    	   xszf+='<div class="col-4 txt-r zi-bbbbbb" style="float: right;margin-right: 5px;" >已付款待确认</div>'
		    			    		       }
		    			    		       if(list[0].goodstate == 3){
		    			    		    	   xszf+='<div class="col-4 txt-r zi-bbbbbb" onclick="resure('+v[i]._id+','+comlist[k].shop._id+')" style="color:#e4393c;float: right;margin-right: 5px;" id="resures">确认收货</div>'
		    			    		       }
		    			    		       
		    			    		       if(list[0].goodstate == 4){
		    			    		    	   xszf+='<div class="col-4 txt-r zi-bbbbbb" style="float: right;margin-right: 5px;">订单完成</div>'
		    			    		       }
		    			    		       
		    			    		       for(var j=0;j<list.length;j++){
		    			    		            xszf+='<div class="clear div-group-10 position-r  border-radius5" style="overflow:hidden;">'
		    			    		         +'<div class=" position-a"><div class="img-bj bk border-radius3" style="background-image:url(${filehttp}/'+list[j].pro.logo+');" onclick="prodetail('+list[j].pro._id+')"></div>'
		    			    		         +'</div>'
		    			    		         +'<div style="padding-left:80px;">'
		    			    		         +'<font size="2">'
		    			    		         +'<div class="zi-6 weight500 sl"> '+list[j].pro.ptitle +'</div>'
		    			    		         +'</font>'
		    			    		         +'<div class=" pull-left weight500 width-10">'
		    			    		         +'<font size="1">';
		    			    		         if(list[j].kdcom!=null){
		    			    		           xszf+='<div class="clear sl hang30 weight100" style="line-height:35px;" onclick="getkd('+v[i].kdno+')">'
		    			    		               +'<span class="zi-hui">'+list[j].kdname+'<i class="zi-lan pl-5">'+list[j].kdno+'</i></span>'/* <span class="zi-lan-tq pl-10">点击查看</span> */
		    			    		               +'</div>';
		    			    		         }else{
		    			    		           xszf+='<div class="clear sl hang30 weight100" style="line-height:35px;" onclick="getkd('+v[i].kdno+')">'
		    			    		               +'<span class="zi-hui"><span class="zi-lan-tq">暂无物流信息</span></span>'
		    			    		               +'</div>';
		    			    		         }
		    			    		         xszf+='<div class=" width-10 line-height20 zi-6">'  
		    			    		         +'<div class="col-9"><span>共'+list[j].count+'件商品<i class="pl-10 zi-hong">单价:￥'+(list[j].pro.price*1).toFixed(2)+'元</i></span>'
		    			    		         +'<span style="display:block;color:#e4393c;">快递费：'+list[j].pro.kdprice.toFixed(2)+'</span>'
		    			    		         +'</div>';
		    			    		         if(list[j].goodstate<4&&list[j].goodstate>=2){
		    				    		          if(list[j].state==1 || list[j].state==3){
		    				    		        	  xszf+='<div class="col-4 txt-r zi-bbbbbb" onclick="find('+v[i]._id+','+list[j].sid+')" style="color:#e4393c">退货查看</div>';
		    				    		          }else if(list[j].state==2 || list[j].state==4){
		    				    		        	  xszf+='<div class="col-4 txt-r zi-bbbbbb" onclick="find('+v[i]._id+','+list[j].sid+')" style="color:#e4393c">退货查看</div>';
		    				    		          }else {
		    				    		        	  xszf+='<div class="col-4 txt-r zi-bbbbbb" onclick="service('+list[j]._id+')" style="color:#e4393c">申请售后</div>';
		    				    		          }
		    			    		          
		    			    		         }else if(list[j].goodstate==2){
		    			    		          xszf+='<div class="col-4 txt-r zi-bbbbbb" onclick="service('+list[j]._id+')" style="color:#e4393c">申请售后</div>';
		    			    		         }else if(list[j].goodstate==3){
		    			    		          xszf+='<div class="col-4 txt-r zi-bbbbbb" onclick="service('+list[j]._id+')" style="color:#e4393c">申请售后</div>';
		    			    		          
		    			    		         }else if(list[j].goodstate==4){
		    			    		        	  if(list[j].states==0){
		    			    		        		 xszf+='<div class="col-4 txt-r zi-bbbbbb" onclick="shopcom('+list[j]._id+','+list[j].pro._id+')" style="color:#e4393c">评价</div>'; 
		    			    		        	 }else if(list[j].states==1){
		    			    		        		 xszf+='<div class="col-4 txt-r zi-bbbbbb" style="color:#e4393c">已评价</div>'; 
		    			    		        	 } 
		    			    		        	/*   xszf+='<div class="col-4 txt-r zi-bbbbbb">订单完成</div>'
		    			    		          		 +'<div class="col-4 txt-r zi-bbbbbb" onclick="shopcom('+list[j]._id+','+list[j].pro._id+')">评价</div>';  */
		    			    		         }
		    			    		         xszf+=''
		    			    		             +'</div></font></div></div></div>';
		    			    		       }
		    			    		     
		    			    		      }
		    		    		   
		    		    		   
		    		    		    xszf+='</div>';  
		    		    	   }
		    		    	  
		    		      }
		    		  
		    		     xszf+='</div></div>';
		    	 
					}
					
				 
		    		fypage++;
	            }else{
	               // xszf='<font size="2"><div class="div-group-10 width-10 txt-c zi-hui-tq weight500 pt-40">您暂时还没有任何订单，<a href="${ctx}/shop/shop!index.action?lx=1&custid=${custid}&agid=${agid}&fromUserid=${fromUserid}"><i class="zi-green">去转转</a></div></font>';
	            }
	            issend=true;
				$('#ajaxdiv').html(xszf);
				 
		},"json")
		
	}
	 
function find(oid,sid){
	window.location.href="${ctx}/shop/service!find.action?custid=${custid}&agid=${agid}&lscode=${lscode}&orderid="+oid+"&sid="+sid;
}

function service(orderproId){
	window.location.href="${ctx}/shop/service!serviceadd.action?custid=${custid}&agid=${agid}&lscode=${lscode}&orderproId="+orderproId;
}

function shopcom(oid,gid){
	window.location.href="${ctx}/shop/shopcom!shopcomadd.action?custid=${custid}&agid=${agid}&lscode=${lscode}&oid="+oid+"&gid="+gid;
}
function del(id) {
	  
    var submitData = {
    	 id:id
    };

    $.post('${ctx}/shop/shop!ajaxdelorder.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData,
    	function (json) {
    	
        	if(json.state==0){ 	
        	 alert("删除成功！");
        	 window.location.reload();
        	}
        },"json");
  
} 
  function  getkd(id){
   window.location.href='http://m.kuaidi100.cn/result.html#com=auto&no='+id;
  }
  function resure(oid,comid){
	  var submitData = {
			  oid:oid,
			  comid:comid
		    };

		    $.post('${ctx}/shop/shop!delivery2.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData,
		    	function (json) {
		    	
		        	if(json.state==0){ 	
		        	 alert("收货成功！");
		        	 $.post('${ctx}/shop/shop!getJf.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData,
		     		    	function (json) {
		     		    	
		     		        	if(json.state==0){ 	 
		     		        	   if(json.data.prostore>=3000){
		     		        		   $("#ppbjf").html("您有"+json.data.prostore+"PADA，可兑换一个矿机！");
		     		        		  friedtx_show();
		     		        	   }else{
		     		        		   window.location.reload();
		     		        	   }
		     		        	  
		     		        	}else{
		     		        		alert('操作失败');
		     		        		window.location.reload();
		     		        	}
		     		        },"json");
		        	 //window.location.reload();
		        	}else{
		        		alert('操作失败');
		        	}
		        },"json");
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
   	 if(val == 0){ 
   		 if(shoptype==3){
   			  txt="";
      		  txt="3AYHYGbC9uE5vBWfbQutyrEmyUd3cAwEdY";
      		 $('#btbutton').show();
    		 $('#ytfbutton').show();
      	  }else if(shoptype==5){
      		 txt="";
      		  txt="3MKiB7ZX16zgwAwbXxM4LugGXm3Co1fFJe";
      		 $('#btbutton').show();
    		 $('#ytfbutton').show();
      	  }else if(shoptype==4){
      		  txt="";
      		  txt="3CS2WvYFnrwL1G3e9jKg6o4guGh4boP4Pr";
      		  $('#btbutton').hide();
      		  $('#ytfbutton').hide();
      	  }
   		 $('#bt').css('display','block');
				pay_bt();
				//二维码生成
				$('#qrcode').qrcode({ 
				  width : 200,
			      height : 200,
			      text	: '1GTapaVtP9JgS4GHtnxZbcoFTxdKXECuKu'
			    });
			    $("#bturl").val('1GTapaVtP9JgS4GHtnxZbcoFTxdKXECuKu'); 
   	 }
   	 if(val == 1){
   		 if(shoptype==3){
   			  txt="";
      		  txt="0xefa03a9B480A9890C5541065A398F43f82F32832";
      		  $('#btbutton').show();
   		      $('#ytfbutton').show();
      	  }else if(shoptype==5){
      		  txt="";
      		  txt="0xc3d8d8fBDb34dA5930d95869b692D80B668d4b98";
      		 $('#btbutton').show();
   		     $('#ytfbutton').show();
      	  }else if(shoptype==4){
      		 txt="";
      		  txt="0x842B0afCaA759ea325A915D2a5e5963B618DcEf1";
      		 $('#btbutton').hide();
     		 $('#ytfbutton').hide();
      	  }
   		 $('#ytf').css('display','block');
				pay_ytf();
				//二维码生成
				$('#qrcodes').qrcode({ 
				  width : 200,
			      height : 200,
			      text	: '0x842B0afCaA759ea325A915D2a5e5963B618DcEf1'
			    });
				$("#ytfurl").val('0x842B0afCaA759ea325A915D2a5e5963B618DcEf1'); 
   	 }
   	 if(val == 2){
   		 $('.bmodal').show();
   		//ppbpay();
   	 }
   	 
   //微信支付
 	if (val == 3) {
 		
  		if(isWeiXin()){
  			var submitData = {
  					orid : $('#oid') .val(),
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
  //唤起微信支付
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
  
  var shoptype;
  function choosePay(v,p,f){
	  if(confirm("是否支付?")){
		  shoptype=f; 
		  if(shoptype ==3){
			  $("#padabutton").hide();
		  }
		  $("#totalPrice").val(p); 
		  $("#oid").val(v);
		  $(this).click(function(){
				$('.masks').show();
				 
			})
	  }
  }
   
	
	 function pay_ytf(){
	    	var totalPrice = $('#totalPrice').val(); 
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
	 
    function pay_bt(){
    	var totalPrice = $('#totalPrice').val(); 
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
    function prodetail(id){
    	window.location.href='${ctx}/shop/shop!shopproduct.action?custid=${custid}&agid=${agid}&lscode=${lscode}&pid='+id;
    }
    
    
    function  ppbpay(){
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
					  				 orid:$('#oid').val(),
					  				 zflx:3,
					       	};
					  		 $.post('${ctx}/shop/shop!OrderPayJf.action?lscode=${lscode}', submitData1,
					           		function(json) {
					  				//alert(json.state);
					   					if(json.state == 0){
					   						alert("支付成功！");
					   						window.location.href="${ctx}/shop/shop!orderform.action?agid=${agid}&lscode=${lscode}";
					   					}else if(json.state == 1){
					 						alert("操作失败");
					 					}else if(json.state == 2){
					 						alert("PADA不足");
					 					}else if(json.state == 3){
					 						alert("库存不足");
					 					}else if(json.state == 4){
					 						alert("商品已下架");
					 					}else if(json.state == 5){
					 						alert("订单不存在");
					 					}else if(json.state == 6){
					 						alert("订单不存在");
					 					}
					           		},
					           		"json");
		            	}else if(json.state==1){
		            		alert('操作失败,请重新输入');
		            	}else if(json.state==2){
		            		alert('账号不存在');
		            	}else  if(json.state==3){
		            		alert('未设置密码，请先设置支付密码');
		            		//window.location.href="${ctx}/user/fromuser!safePwd.action?custid=${custid}&agid=${agid}&lscode=${lscode}";
		            		
		            		window.location.href="${ctx}/user/fromuser!safePwd.action?custid=${custid}&agid=${agid}&lscode=${lscode}";
		            	}else  if(json.state==4){
		            		//alert('密码错误,请重新输入');
		            		alert('密码错误,请重新输入');
		            	}
		},"json")
       }
  
</script>
</head>
<body>
    <input type="hidden" id="totalPrice" />
    <input type="hidden" id="oid" />
	<header style="background: #fefefe;width: 100%;height: 44px;position: fixed;top: 0;left: 0;padding: 0 10px;line-height: 44px;text-align: center;">
			<a  href="javascript:history.go(-1);" style="display: inline-block;float: left;width: 30px;height: 30px;background: url('${ctx}/xmMobile/img/goback.png') no-repeat;background-size: 100% 100%;margin-top: 10px;"></a>
			<h1 class="mui-title">订单列表</h1>
	</header> 
 
<main class="clear cmp640 lock bg-hui-92" style='padding-top: 44px;'>

    <div id="ajaxdiv"></div>
    <c:if test="${ordercount==0}">
    <font size="2"><div class="div-group-10 width-10 txt-c zi-hui-tq weight500 pt-40">您暂时还没有任何订单，<a href="${ctx}/shop/shop!index.action?lx=1&custid=${custid}&agid=${agid}&lscode=${lscode}"><i class="zi-green">去转转</a></div></font>
    </c:if>
    <div class="hang50"></div>

</main>

<!--底部三个按钮-->
<%-- <%@include file="/webcom/shop-foot.jsp" %>  --%>
<div class=" button_foot bg-bai shadow-wai cmp640">

    <div class=" button_group1"> 
            <div class="bottom-bai zi-hui-wx txt-c weight500 line-right_bai pt-5 pb-5" onclick="window.location.href='${ctx}/shop/shop!index.action?lscode=${lscode}&comid=${comid}'">
                <font size="4">
                    <div class="fa fa-home"></div>
                </font>

                <div class=" pt-3">
                    <font size="1">
                        首页
                    </font>
                </div>
            </div>
         
    </div>
 <div class=" button_group1"> 
            <div class="bottom-bai zi-hui-wx txt-c weight500 line-right_bai pt-5 pb-5" onclick="window.location.href='${ctx}/shop/protype!classme.action?custid=${custid}&agid=${agid}&lscode=${lscode}'">
                <font size="4">
                    <div class="fa fa-file"></div>
                </font>
                <div class=" pt-3">
                    <font size="1">
                      分类
                    </font>
                </div>
            </div> 
    </div>
    <div class=" button_group1"> 
            <div class="bottom-bai zi-hui-wx txt-c weight500 line-right_bai pt-5 pb-5" onclick="window.location.href='${ctx}/shop/shop!shoppingcar.action?custid=${custid}&agid=${agid}&lscode=${lscode}'">
                <font size="4" id="shopcartlogo">
                <!-- <div class="fa fa-file"></div> -->
                     
                </font>
                <div class=" pt-3">
                    <font size="1" id="shopcar">
                       购物车
                    </font>
                </div>
            </div> 
    </div>

   
    
    <div class=" button_group1" onclick="window.location.href='${ctx}/user/fromuser!UserDetail.action?custid=${custid}&agid=${agid}&lscode=${lscode}'"> 
            <div class="bottom-bai zi-hui-wx txt-c weight500 line-right_bai pt-5 pb-5">
                <font size="4">
                    <div class="fa fa-user"></div>
                </font>
                <div class=" pt-3">
                    <font size="1">
                       个人中心
                    </font>
                </div>
            </div>
        
    </div>

    
</div>
 <div class="fullscreen cmp640 bg-hei-5 lock" id="friedtx">
    <div class="overflow-hidden width-10">
        <a href="javascript:friedtx_hide()">
            <div class="width-10 overflow-hidden" style="height:1000px;"></div>
        </a> 
        <div class=" cmp640 position-f position-r" style="top:15%;left:0px;z-index: 99;">

            <div class="position-a hang60 width-10" style="top:-35px;z-index: 100;">
                <div class="img-wh70 border-radius50 maring-a">
                    <img src="${filehttp}/${user.headimgurl}" width="100%" class=" border-radius50">
                </div>
            </div>

            <div class="maring-a position-r border-radius5 div-group-15 pt-45 bg-bai" style="width:280px;">
                <a href="javascript:friedtx_hide()">
                    <div class="position-a" style="right:-7px;top:-7px;z-index: 101;">
                        <div class="img-wh20 border-radius50 bg-hui-tx txt-c">
                            <font size="2">
                                <i class="fa fa-remove zi-green" style="line-height:22px"></i>
                            </font>
                        </div>
                    </div>
                </a>

                <div class=" width-10 border-radius5 zi-hui-tq overflow-hidden">
                    <font size="2">
                        <div class="weight500">您好:</div>
                        <div class="weight500 line-height25">&nbsp&nbsp&nbsp尊敬的：<i class="zi-green">${user.nickname}</i><i class="zi-green" id="ppbjf">您已消费${jf.prostore}PADA可兑换一个矿机 </i></div>
                    </font>
                </div>

                <div class="width-10 pt-15">
                    <div class="button_group1" onclick="window.location.href='${ctx}/integral/miners!list.action?lscode=${lscode}'"><%-- ${ctx}/user/fromuser!basemsg.action?custid=${custid}&agid=${agid}&lscode=${lscode} --%>
                        <div class="width-8 maring-a border-radius3 btn-green zi-bai txt-c hang30 line-height30">
                          立即兑换
                        </div>
                    </div>
                    <div class="button_group1" onclick="friedtx_hide()">
                        <div class="width-8 maring-a border-radius3 btn-green zi-bai txt-c hang30 line-height30">
                           稍后查看
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<div class="masks" id="masks">
	<div class="mask-cont">
		<div class="mask-cont-tit">
			付款方式
			<i class="fa fa-close pull-right" style="font-size: 16px;padding-right: 5px;padding-top: 5px;" id="closes"></i>
		</div>
		<div class="mask-cont-cont">
			<button onclick="popcode(0)" class="currency" id="btbutton">比特币</button>
			<button onclick="popcode(1)" class="currency" id="ytfbutton">以太坊</button>
			<button onclick="popcode(2)" class="currency" id="padabutton">PADA</button>
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
		<div class="bturlBox" style="height: 34px;line-height: 34px;font-size: 10px;">		
			<input type="text" id="bturl" style=" line-height: 30px; height:30px; padding:5px; background: #fff !important;width:80%;float:left;  font-size:.6rem;"/>			
			<input type="button" onClick="copyUrl2()" value="复制"  style="width:18%; height:30px; background:#E4393C; color:#fff; border-radius:5px;   font-size:.6rem;   float:right; "  />			
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
		<div class="bturlBox"  style="height: 34px;line-height: 34px;font-size: 10px;text-align:center;width:100%;">
			 <input type="text" id="ytfurl" style=" line-height: 30px; height:30px; padding:5px; background: #fff !important;width:80%;float:left;  font-size:.6rem;"/>
			 <input type="button" onClick="copyUrl3()" value="复制"  style="width:18%; height:30px;  background:#E4393C;  color:#fff; border-radius:5px;  font-size:.6rem;  float:right; "  />
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
						<button class="cancel" style="background:#73d1c4;" >取消</button><button onclick="ppbpay()" style="background:#f20101;">确认</button>
					</div>
				</div>
			</div>


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
function  friedtx_hide(){
 $("#friedtx").hide();
 window.location.reload();
}
$("#friedtx").hide();
function  friedtx_show(){
 $("#friedtx").show();
}
$('.cancel').click(function(){
	$('.bmodal').hide();
})

//支付弹出
//$('.bmodal').show();

 $("#closes").on('click',function(){
		 console.log($('.masks').hide("slow"))
			$('.masks').hide("slow")
		}) 
//$('#shopcartlogo').addClass('fa fa-shopping-bag');
 $('#shopcartlogo').html('<div class="fa fa-shopping-bag"></div>');
$('#shopcar').html('购物车');		
 function htmlEncodeJQ ( str ) {
    return $('<span/>').text( str ).html();
}
 
function htmlDecodeJQ ( str ) {
    return $('<span/>').html( str ).text();
}
</script>
</body>
<script>

$(window).scroll(function () {

    var offsetY = $(window).scrollTop();

    var section1 = $("#section1").height();
	if(section1-offsetY<600){
		ajaxjz();
	}
   
});

$('.cancel').click(function(){
	$('.modal').hide();
})
 
</script>


</html>