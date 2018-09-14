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
    <title>地址管理</title>
    <!-- Resource style -->
    <script src="${ctx }/app/js/jquery-1.8.3.js"></script>
    <link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/app/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="${ctx}/app/css/font-awesome-ie7.min.css" rel="stylesheet"/>
    <link href="${ctx}/app/css/style_0.css" rel="stylesheet"/> 

    <!--标准mui.css-->
    <link rel="stylesheet" href="${ctx}/mvccol/mui-css/mui.min.css"/>
    <!--App自定义的css-->
    <link rel="stylesheet" type="text/css" href="${ctx}/mvccol/mui-css/app.css"/>
    <link href="${ctx}/app/css/iosOverlay.css" rel="stylesheet"/>
    <script src="${ctx}/app/js/iosOverlay.js"></script>
    <script src="${ctx}/app/js/spin.min.js"></script>
    <script>
    var issend=true;
    var fypage=0;
    var xszf="";
	var type="";
	//loding
	var loading;
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
   	var submitData = {  
    }; 
   loading()
    issend=false; 
    $.post('${ctx}/shop/shop!ajaxuseraddress.action?custid=${custid}&agid=${agid}&lscode=${lscode}&pid=${pid}&fypage='+fypage, submitData,
       	function(json) { 
       		console.log(json)
       		loading.hide()
    		var xszf=$('#ajaxdiv').html(); 
	    	if(json.state=='0'){
	    		var v = json.list; 
	    		  //console.log(v);
	    		 for(var i=0;i<v.length;i++){ 
	    			 
	    		 xszf+='<ul  class="mui-table-view" onclick="setadmin('+v[i]._id+')"><li class="pl-10 pr-10 pt-5 mui-table-view-cell hang600" style="padding-bottom:20px;  line-height:30px;">'
	    		     +'<div class="mui-slider-right mui-disabled"><a href="javascript:setadmin('+v[i]._id+')" class="mui-btn mui-btn-yellow">设置默认</a>'
	    		     +'<a href="javascript:del('+v[i]._id+')" class="mui-btn mui-btn-red">删除</a></div>'
	    		     +'<div class="mui-slider-handle"><a href="#">'
	    		     +'<font size="2"><div class=" overflow-hidden zi-6 weight500 col-12">'
	    		     +'<div class=""><div class="pull-left">收货人：<i class="pl-5">'+v[i].name+'</i></div>'
	    		     +'<div class="pull-right">'+v[i].tel+'</div></div>'
	    		     +'<div class="clear"><div class="pull-left width-10" style="line-height:15px;">收货地址：<i class="pl-5">'+v[i].province+v[i].city+v[i].county+v[i].address+'</i></div>'
	    		     +'</div></div></font></a></div></li>'
	    		     +'<div class="hang5 clear bg-hui"></div></ul>';
	    		 		
				}
	    		fypage++;
				 
	    	}else{
	    		
	    	}
	    	
	    	issend=true;
			$('#ajaxdiv').html(xszf);
			 
	},"json")
	
}
    function del(id){
	var submitData = {
			id:id
	};
	$.post('${ctx}/shop/shop!ajaxdeladdress.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData,
		function(json) {
		 if(json.state==0){
		 window.location.reload();
		 }
				
	},"json");
 
	
}
   function setadmin(id){
	   var pid = '${pid}';
	var submitData = {
			id:id
	};
	$.post('${ctx}/shop/shop!ajaxsetaddress.action?custid=${custid}&agid=${agid}&lscode=${lscode}', submitData,
		function(json) {
		if(pid != "nots"){
			if(json.state==0){ //custid=efe122ce-99fd-49fd-84b9-c1bdbbdb4ff0&agid=&lscode=5a7493d5-2de3-4417-8e8b-a7d31569e31f&pid=147064
			 	window.location.href='${ctx}${backurl}&lscode=${lscode}&pid='+pid;
			 }
		}else{
			if(json.state==0){ 
				 window.location.href='${ctx}${backurl}&custid=${custid}&agid=${agid}&lscode=${lscode}&count=${count}&price=${price}&spec=${spec}';
		 	}
		}
		 
				
	},"json");
 
	
}
    </script>
    <style>
        .line-height50{
            line-height:50px;
        }
        .hang2-bj {
            height: 2px;
            background-image: url(${ctx}/img/scdz-xz.png);
        }
    </style>
</head>

<body>
<header style="background: #fefefe;width: 100%;height: 44px;position: fixed;top: 0;left: 0;padding: 0 10px;line-height: 44px;text-align: center;border-bottom: 1px solid #ddd;">
			
			<h1 class="mui-title"><a href="javascript:history.go(-1);" style="display: inline-block;float: left;width: 30px;height: 30px;background: url('${ctx}/xmMobile/img/goback.png') no-repeat;background-size: 100% 100%;margin-top: 10px;"></a>地址管理</h1>
	</header>


<main class="clear cmp640 lock" style='padding-top: 40px;'>

    <c:if test="${not empty address}"> 
        <font size="2">
            <div class="div-group-10 pt-5 pb-5 overflow-hidden zi-hui-wx weight100 col-11">
                <div class="">
                    <div class="pull-left">收货人:<i class="pl-5">${address.name}</i></div>
                    <div class="pull-right">${address.tel}</div>
                </div>
                <div class="clear pt-5">
                    <div class="pull-left sl width-10">收货地址:<i class="pl-5">${address.address}</i></div>
                </div>
            </div>
            <div class="col-1 div-group-5 txt-c zi-hui hang40 overflow-hidden">
                <i class="fa fa-chevron-right line-height50"></i>
            </div>
            <div class="hang10  clear"></div>
        </font>
        <div class="hang2-bj"></div>
        <div class="hang10 bg-hui clear">
                 </div>
           
    </c:if> 
  <div id="ajaxdiv">
  
  
  </div>
     <div class="pt-25">
     	<c:choose>
     		<c:when test="${pid == 'nots' }">
     			<a href="${ctx}/shop/shop!useraddresssave.action?lscode=${lscode}&custid=${custid}&agid=${agid}&addressis=${addressis}&backurl=${backurl}&count=${count}&price=${price}&spec=${spec}">
            </c:when>
            <c:otherwise>
                <a href="${ctx}/shop/shop!useraddresssave.action?lscode=${lscode}&custid=${custid}&agid=${agid}&addressis=${addressis}&backurl=${backurl}&pid=${pid}">
            </c:otherwise>
     	</c:choose>
     	<div class="hang40 line-height40 width-5 maring-a zi-bai size14 weight500 txt-c btn-lu border-radius3">添加新地址</div>
       </a>
    </div>


    <div class="weight500 zi-hui pt-25">
        <font size="2">
            <div class="txt-c">
                向左拖拽地址框设置默认地址或删除。
            </div>
        </font>
    </div>
    <%@include file="/webcom/shop-foot.jsp" %>
</main>
<script src="${ctx}/mvccol/mui-js/mui.min.js"></script>
<script>
ajaxjz();
mui.init();
     
</script> 
</body>
</html>