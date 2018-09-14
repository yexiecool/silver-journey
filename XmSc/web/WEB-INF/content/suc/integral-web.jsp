<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>数字资产</title>
<script src="${ctx}/app/js/jquery-1.8.3.js"></script>
<link href="${ctx}/xmMobile/css/mui.min.css" rel="stylesheet" />
<link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/css/font-awesome.min.css" rel="stylesheet" />
<script src="${contextPath}/mvccol/js/fomatdate.js"></script>
<script>
    var issend=true;
    var fypage=0;
    var xszf="";
	var type="";
	 
  function ajaxjz(){//加载 
    if(!issend){
    	return;
    }
   
   	var submitData = { 
     
    }; 
   
    issend=false; 
    $.post('${ctx}/suc/integral!ajaxweb.action?custid=${custid}&lscode=${lscode}&fypage='+fypage, submitData,
       	function(json) { 
    		var xszf=$('#ajaxdiv').html();
	    	if(json.state=='0'){
	    		var v = json.list; 
	    		 for(var i=0;i<v.length;i++){  
	    		    xszf+='<a href="javascript:void(0)"><div class="line-bottom zi-6 overflow-hidden txt-c">'
	    		    +'<div class="col-3 pt-15 pb-10 sl">';
	    		  
	    		     
	    		    if(v[i].type=='shop_jfdh'){
	    		     xszf+='<div class="width-9 maring-a clear sl"><font size="2">下单使用</font></div>';
	    		    } 
	    		 
	    		    if(v[i].type=='pada_add'){
		    		     xszf+='<div class="width-9 maring-a clear sl"><font size="2">转账收益</font></div>';
		    		    } 
		    		    if(v[i].type=='pada_jian'){
		    		     xszf+='<div class="width-9 maring-a clear sl"><font size="2">转账支出</font></div>';
		    		    } 
		    		    if(v[i].type=='jyscz'){
		    		     xszf+='<div class="width-9 maring-a clear sl"><font size="2">交易所转入</font></div>';
		    		    }
		    		    if(v[i].type=='shop_tx'){
			    		     xszf+='<div class="width-9 maring-a clear sl"><font size="2">商城转出</font></div>';
			    		 }
		    		    if(v[i].type=='returngood_add'){
			    		     xszf+='<div class="width-9 maring-a clear sl"><font size="2">退货入账</font></div>';
			    		    }
			    		    if(v[i].type=='returngood_jian'){
				    		     xszf+='<div class="width-9 maring-a clear sl"><font size="2">退货扣款</font></div>';
				    		 }
		    		   
	    		    
	    		    xszf+='</div><div class="col-7 pt-15 pb-10"><font size="2">'+Date.prototype.format(v[i].createdate)+'</font></div>';
	    		   if(v[i].state==1){
	    		    xszf+='<div class="col-2 pt-15 pb-10"><font size="2">-'+v[i].value+'</font></div></div></a>'; 
	    		   }else{ 
	    		    xszf+='<div class="col-2 pt-15 pb-10"><font size="2">+'+v[i].value+'</font></div></div></a>'; 
	    		   }
	    		   
				 }
	    		fypage++;
				 
	    		
	    	}else{
	    		
	    	}
	    	
	    	issend=true;
			$('#ajaxdiv').html(xszf);
			 
	},"json")
	
}
    </script>
</head>
<body style=" padding-top:40px; ">

  <!--   <header style="width: 100%;height: 40px;line-height: 40px;text-align: center;padding: 0 10px;background: #fff;">
	   <a href="javascript:history.go(-1);"
		style="font-size: 18px; float: left; color: #000; width: 30px; display: inline-block; height: 40px; line-height: 40px;"
		class="mui-icon mui-icon-undo"></a> 数字资产 
	</header> -->
	<header class="mui-bar mui-bar-nav" style="background: #fff;" >
		    <a class=" mui-icon mui-icon-undo mui-pull-left" style="color: #000;" href="javascript:history.back(-1)"></a>
		    <h1 class="mui-title">数字资产</h1>
	</header>
	
	<main class="lock cmp640" id="section1">
	<div class=" bg-hui-92 zi-bailine-height40 cmp640 line-bottom">
		<a href="#"> <!--  -->
			<div class="zi-hei weight500 pl-15" style="overflow: hidden;">
				<div
					style="margin: 10px 0;  line-height: 20px;">
					<font size="2">PADA：<i class="zi-cheng"
						style="float: right; margin-right: 15px;"><fmt:formatNumber
								value='${jf.uservalue}' pattern="0.0000000000" /></i></font>
				</div>

				<%-- <div style="margin: 10px 0;border-bottom: 1px solid #ddd;line-height:20px;"><font size="2">可兑换${setting.name}：<i class="zi-cheng"  style="float: right;margin-right: 15px;"><fmt:formatNumber value='${jf.prostore}'  pattern="0.00"/></i></font></div> --%>
			</div>
		</a>
		<%-- <a href="${ctx}/suc/integral!blacklist.action?custid=${custid}&lscode=${lscode}">
            <div class="pull-right zi-green weight500 pr-15"><font size="2">充值</font></div>
        </a> --%>
	</div>

	<div class="txt-c zi-cheng  div-group-5">

		<div class="line-left1 line-top line-bottom line-right border-radius5" style="padding-bottom:10px; ">
			<!--<a href="#">-->
			<!--<div class="line-bottom  zi-6 overflow-hidden txt-c"-->
			<!--style="border-radius:5px 5px 0px 0px ;">-->
			<!--<div class="col-12 pt-15 pb-10 weight500"><font size="2">积分获得/消费明细</font></div>-->
			<!--</div>-->
			<!--</a>-->

			<div class="line-bottom  zi-6 overflow-hidden txt-c weight500"
				style="border-radius: 5px 5px 0px 0px;">
				<div class="col-3 pt-15 pb-10">
					<font size="2">名称</font>
				</div>
				<div class="col-7 pt-15 pb-10">
					<font size="2">时间</font>
				</div>
				<div class="col-2 pt-15 pb-10">
					<font size="2">PADA</font>
				</div>
			</div>

			<!--循环列表开始-->

			<div id="ajaxdiv"></div>

			<!--下面代码不要删除或者循环-->
			<a href="javascript:void(0)">
				<div class="zi-hui zi-green overflow-hidden txt-c"
					style="border-radius: 5px 5px 0px 0px;">
					<div class="col-15 pt-15 pb-10">
						<font size="2">以上所有明细</font>
					</div>
				</div>
			</a> <a
				href="${ctx}/suc/integral!webll.action?custid=${custid}&agid=${agid}&lscode=${lscode}"  style="color:#fff; padding:5px; font-size:12px; background-color:#ff5500; border-radius:5px;">返回LLB明细</a>
		</div>
	</div>




	</main>

	<script type="text/javascript">
ajaxjz();
$(window).scroll(function () {

    var offsetY = $(window).scrollTop();

    var section1 = $("#section1").height();
	if(section1-offsetY<600){
		ajaxjz(); 
	}
   
});

</script>
</body>
</html>