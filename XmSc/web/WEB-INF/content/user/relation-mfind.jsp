<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webcom/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>	
   	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title>我的团队</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/xmMobile/css/mui.min.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/xmMobile/css/common.css" />
	<link href="${ctx}/app/css/iosOverlay.css" rel="stylesheet"/>
	<script src="${ctx}/xmMobile/js/jquery-2.1.0.js" type="text/javascript" charset="utf-8"></script>
     <script src="${ctx}/app/js/iosOverlay.js"></script>
    <script src="${ctx}/app/js/spin.min.js"></script>
    <style type="text/css">
			.mui-table-view-cell {
				padding-right: 0;
				
			}
			.mui-table-view-cell.mui-active{
				background-color: #fff;
			}
			.mui-table-view-cell>.mui-navigate-right.link:after {
				content: '\e581';
			}
			
			.mui-table-view-cell.mui-active>.mui-navigate-right:after{
				content: '\e580';
			}
			.mui-table-view.mui-collapse{
				display: none;
			}
			#accordion a{ color:#7cc3df !important; }
			#accordion li{ background: #141534;}
		</style>
		<script type="text/javascript">
		var loading;
        function  jiazai(){
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
        
        
        
     
	    var xszf=""; 
		 
	  function ajaxjz(number){//加载 
	    
		  jiazai();
	   	var submitData = { 
	   			vipno:number
	    }; 
	   
	  
	    $.post('${ctx}/user/relation!ajaxmobilefindrelation.action?custid=${custid}&lscode=${lscode}', submitData,
	       	function(json) { 
	    	loading.hide();
	    	   xszf="";
	    		 
	    		$('#relationtable').html("");
	       		var relations =json.relations;
	       		var listrelations =json.listrelations;
	       		//第一层
	       	    xszf+='<tr>';
				     if(relations!=null){
				    	 xszf+=' <td colspan="8">'
				    	 +'	 <div class="sjcont" style="width:40%; float:left; margin-left:30%;">'
				    	 +'	 	 <p class="bianhao"> <span>'+relations.number 
				    	 if(relations.agentLevel ==1){
				    		 xszf+='【省代】';
				    	 }else if(relations.agentLevel ==2){
				    		 xszf+='【市代】';
				    	 }else if(relations.agentLevel ==3){
				    		 xszf+='【区代】';
				    	 }else if(relations.agentLevel ==4){
				    		 xszf+='【报单】';
				    	 }else if(relations.agentLevel ==100){
				    		 xszf+='【微】';
				    	 }else if(relations.agentLevel ==101){
				    		 xszf+='【小】';
				    	 }else if(relations.agentLevel ==102){
				    		 xszf+='【中】';
				    	 }else if(relations.agentLevel ==103){
				    		 xszf+='【大】';
				    	 }else {
				    		 xszf+='【游客】';
				    	 }
				    	 xszf+='</span></p>	'	
				    	 +'	 	 <div class="jine">￥<span>'+relations.money+'</span></div>'
				    	 +'		 <div class="renshu"><span>'+relations.count+'</span>人</div>'
				    	 +'		 <div class="clear"></div>	'			
				    	 +'	 </div>'
				    	 +'</td>';
				     }
	       	    xszf+='</tr>';
	       	   //第1层
	       	   
	       	    if(listrelations!=null){
				 
					 xszf+='<tr>';
					 xszf+='<td colspan="4" width="50%">';
						    if (listrelations[0] !=null &&  listrelations[0].number!=null && listrelations[0].number !="" ){
						    	xszf+='<div class="sjcont" style="width:70%; float:left; margin-left:15%;" onclick="selectcurr('+listrelations[0].number +')">'
						    		+' <p class="bianhao"><span>'+listrelations[0].number
						    		if(listrelations[0].agentLevel ==1){
						    		 xszf+='【省代】';
						    	 }else if(listrelations[0].agentLevel ==2){
						    		 xszf+='【市代】';
						    	 }else if(listrelations[0].agentLevel ==3){
						    		 xszf+='【区代】';
						    	 }else if(listrelations[0].agentLevel ==4){
						    		 xszf+='【报单】';
						    	 }else if(listrelations[0].agentLevel ==100){
						    		 xszf+='【微】';
						    	 }else if(listrelations[0].agentLevel ==101){
						    		 xszf+='【小】';
						    	 }else if(listrelations[0].agentLevel ==102){
						    		 xszf+='【中】';
						    	 }else if(listrelations[0].agentLevel ==103){
						    		 xszf+='【大】';
						    	 }else {
						    		 xszf+='【游客】';
						    	 }
						    	 xszf+='</span></p>	'
						    		+' <div class="jine">￥<span>'+listrelations[0].money+'</span></div>'
						    		+' <div class="renshu"><span>'+listrelations[0].count+'</span>人</div>'
						    		+' <div class="clear"></div>'
						    		+'</div>';
	       	   				 }
	       			 xszf+='</td>';
	       			 xszf+='<td colspan="4" width="50%">';
					    if (listrelations[1] !=null  &&  listrelations[1].number!=null && listrelations[1].number !=""){
					    	xszf+='<div class="sjcont" style="width:70%; float:left; margin-left:15%;" onclick="selectcurr('+listrelations[1].number +')">'
					    		+' <p class="bianhao"><span>'+listrelations[1].number
					    		if(listrelations[1].agentLevel ==1){
					    		 xszf+='【省代】';
					    	 }else if(listrelations[1].agentLevel ==2){
					    		 xszf+='【市代】';
					    	 }else if(listrelations[1].agentLevel ==3){
					    		 xszf+='【区代】';
					    	 }else if(listrelations[1].agentLevel ==4){
					    		 xszf+='【报单】';
					    	 }else if(listrelations[1].agentLevel ==100){
					    		 xszf+='【微】';
					    	 }else if(listrelations[1].agentLevel ==101){
					    		 xszf+='【小】';
					    	 }else if(listrelations[1].agentLevel ==102){
					    		 xszf+='【中】';
					    	 }else if(listrelations[1].agentLevel ==103){
					    		 xszf+='【大】';
					    	 }else {
					    		 xszf+='【游客】';
					    	 }
					    	 xszf+='</span></p>	'
					    		+' <div class="jine">￥<span>'+listrelations[1].money+'</span></div>'
					    		+' <div class="renshu"><span>'+listrelations[1].count+'</span>人</div>'
					    		+' <div class="clear"></div>'
					    		+'</div>';
    	   				 }
    				 xszf+='</td>';
    				 xszf+='</tr>';
	       	     }
	       	   //第二层
	       	    if (listrelations!=null  &&  listrelations.length   >1 ){
		       	     xszf+='<tr>';
		       	    	 xszf+='<td colspan="2" width="25%">';
							 if ( listrelations[2] !=null &&  listrelations[2].number!=null && listrelations[2].number !=""){
						    		
						    	xszf+=' <div class="sjcont" style="width:90%; float:left; margin-left:5%;" onclick="selectcurr('+listrelations[2].number +')">'
						    		+' <p class="bianhao"><span>'+listrelations[2].number
						    	if(listrelations[2].agentLevel ==1){
						    		 xszf+='【省代】';
						    	 }else if(listrelations[2].agentLevel ==2){
						    		 xszf+='【市代】';
						    	 }else if(listrelations[2].agentLevel ==3){
						    		 xszf+='【区代】';
						    	 }else if(listrelations[2].agentLevel ==4){
						    		 xszf+='【报单】';
						    	 }else if(listrelations[2].agentLevel ==100){
						    		 xszf+='【微】';
						    	 }else if(listrelations[2].agentLevel ==101){
						    		 xszf+='【小】';
						    	 }else if(listrelations[2].agentLevel ==102){
						    		 xszf+='【中】';
						    	 }else if(listrelations[2].agentLevel ==103){
						    		 xszf+='【大】';
						    	 }else {
						    		 xszf+='【游客】';
						    	 }
						    	 xszf+='</span></p>	'
						    		+'<div class="jine" style="width:100%; border-radius:0px;">￥<span>'+listrelations[2].money+'</span></div>'
						    		+' <div class="renshu" style="width:100%; border-radius:0 0 10px 10px; "><span>'+listrelations[2].count+'</span>人</div>'
						    		+' <div class="clear"></div>'
						    		+'</div>';
							 }
						 xszf+='</td>';
						 xszf+='<td colspan="2" width="25%">';
							 if ( listrelations[3] !=null &&  listrelations[3].number!=null && listrelations[3].number !=""){
								 
								 xszf+=' <div class="sjcont" style="width:90%; float:left; margin-left:5%;" onclick="selectcurr('+listrelations[3].number +')">'
						    		+' <p class="bianhao"><span>'+listrelations[3].number

						    		if(listrelations[3].agentLevel ==1){
							    		 xszf+='【省代】';
							    	 }else if(listrelations[3].agentLevel ==2){
							    		 xszf+='【市代】';
							    	 }else if(listrelations[3].agentLevel ==3){
							    		 xszf+='【区代】';
							    	 }else if(listrelations[3].agentLevel ==4){
							    		 xszf+='【报单】';
							    	 }else if(listrelations[3].agentLevel ==100){
							    		 xszf+='【微】';
							    	 }else if(listrelations[3].agentLevel ==101){
							    		 xszf+='【小】';
							    	 }else if(listrelations[3].agentLevel ==102){
							    		 xszf+='【中】';
							    	 }else if(listrelations[3].agentLevel ==103){
							    		 xszf+='【大】';
							    	 }else {
							    		 xszf+='【游客】';
							    	 }
						    		 xszf+='</span></p>	'
						    		+'<div class="jine" style="width:100%; border-radius:0px;">￥<span>'+listrelations[3].money+'</span></div>'
						    		+' <div class="renshu" style="width:100%; border-radius:0 0 10px 10px; "><span>'+listrelations[3].count+'</span>人</div>'
						    		+' <div class="clear"></div>'
						    		+'</div>';
							 }
						 xszf+='</td>';
						 
						 xszf+='<td colspan="2" width="25%">';
						 if ( listrelations[4] !=null &&  listrelations[4].number!=null && listrelations[4].number !=""){
							 
							 xszf+=' <div class="sjcont" style="width:90%; float:left; margin-left:5%;" onclick="selectcurr('+listrelations[4].number +')">'
					    		+' <p class="bianhao"><span>'+listrelations[4].number
					    		if(listrelations[4].agentLevel ==1){
						    		 xszf+='【省代】';
						    	 }else if(listrelations[4].agentLevel ==2){
						    		 xszf+='【市代】';
						    	 }else if(listrelations[4].agentLevel ==3){
						    		 xszf+='【区代】';
						    	 }else if(listrelations[4].agentLevel ==4){
						    		 xszf+='【报单】';
						    	 }else if(listrelations[4].agentLevel ==100){
						    		 xszf+='【微】';
						    	 }else if(listrelations[4].agentLevel ==101){
						    		 xszf+='【小】';
						    	 }else if(listrelations[4].agentLevel ==102){
						    		 xszf+='【中】';
						    	 }else if(listrelations[4].agentLevel ==103){
						    		 xszf+='【大】';
						    	 }else {
						    		 xszf+='【游客】';
						    	 }
					    		 xszf+='</span></p>	'
					    		+'<div class="jine" style="width:100%; border-radius:0px;">￥<span>'+listrelations[4].money+'</span></div>'
					    		+' <div class="renshu" style="width:100%; border-radius:0 0 10px 10px; "><span>'+listrelations[4].count+'</span>人</div>'
					    		+' <div class="clear"></div>'
					    		+'</div>';
						 }
						 xszf+='</td>';
					 
						 xszf+='<td colspan="2" width="25%">';
						 if ( listrelations[5] !=null &&  listrelations[5].number!=null && listrelations[5].number !="" ){
							 
							 xszf+=' <div class="sjcont" style="width:90%; float:left; margin-left:5%;" onclick="selectcurr('+listrelations[5].number +')">'
					    		+' <p class="bianhao"><span>'+listrelations[5].number
					    	if(listrelations[5].agentLevel ==1){
					    		 xszf+='【省代】';
					    	 }else if(listrelations[5].agentLevel ==2){
					    		 xszf+='【市代】';
					    	 }else if(listrelations[5].agentLevel ==3){
					    		 xszf+='【区代】';
					    	 }else if(listrelations[5].agentLevel ==4){
					    		 xszf+='【报单】';
					    	 }else if(listrelations[5].agentLevel ==100){
					    		 xszf+='【微】';
					    	 }else if(listrelations[5].agentLevel ==101){
					    		 xszf+='【小】';
					    	 }else if(listrelations[5].agentLevel ==102){
					    		 xszf+='【中】';
					    	 }else if(listrelations[5].agentLevel ==103){
					    		 xszf+='【大】';
					    	 }else {
					    		 xszf+='【游客】';
					    	 }
				    		 xszf+='</span></p>	'
					    		+'<div class="jine" style="width:100%; border-radius:0px;">￥<span>'+listrelations[5].money+'</span></div>'
					    		+' <div class="renshu" style="width:100%; border-radius:0 0 10px 10px; "><span>'+listrelations[5].count+'</span>人</div>'
					    		+' <div class="clear"></div>'
					    		+'</div>';
						 }
						 xszf+='</td>';
					xszf+='</tr>';
					 
	       	    }
			 
			$('#relationtable').html(xszf);
		},"json")
		
	}
	  
</script>
<script type="text/javascript">
function selectcurr(number) {
	ajaxjz(number);
	//location.href = "${ctx}/user/relation!mobilefindrelation.action?vipno="+ number;
 } 
</script>
</head>

<body style=" background:url('${ctx}/xmMobile/images/kjbg2.jpg') no-repeat top center; background-size:100% 100%;">

   <header class="mui-bar mui-bar-nav" style="background: #141534;">
        <a class="mui-icon mui-icon-undo mui-pull-left" style="color:#fbfaff  !important;" href="javascript:history.go(-1)"></a>
        <h1 class="mui-title" style="color:#fbfaff;">我的团队</h1>
    </header>
    <style>
		.wangti{ width: 100%; text-align: center; border:1px solid #7cc3df; border-width: 1px 1px 0px  0px; margin-top:20px;  }	 
		.wangti tr td{ border:1px solid #7cc3df; border-width: 0px 0px 1px  1px; }	
		.sjcont{ background:#006891; margin: 10px auto; position: static; float: none; color: #fff; padding: 10px 0 0; border-radius:10px;  font-size: 12px;   }
		.sjcont p{ width: 100%; margin:0px;  border-bottom: 1px solid #7cc3df; color:#fff;  }
		.sjcont div.jine{  width: 50%; float:left; border-right: 1px solid #7cc3df;  background: #7cc3df; padding: 5px 0; border-radius:0 0 0 10px;  }
		.sjcont div.renshu{ width: 50%;  float:left;  background:#679eb3; padding: 5px 0; border-radius:0 0  10px 0;     }	
		.clear{ clear: both; }
		.anniu{   font-size:14px;  color: #7cc3df; line-height:60px; float:left; width:100%; padding:0 3%; }
		.anniu button{background:#006891;  border:none;  text-align: center;  border-radius: 10px; float:right; margin:10px 0;   }
		.anniu button a{ color: #fff !important; }
	</style>
		<div class="mui-content" style="background: #141534;">
		     <div class="anniu">未分配会员列表 <button><a href="${ctx}/integral/miners!wfp.action?custid=${custid}&agid=${agid}&lscode=${lscode}">查看</a></button></div>
			<table class="wangti" id ="relationtable"  border="0" cellpadding="0" cellspacing="0" >
					 
			 </table>
		
 
		</div>
		<script type="text/javascript">
		ajaxjz("");
		</script>
	 
</body>
</html>
