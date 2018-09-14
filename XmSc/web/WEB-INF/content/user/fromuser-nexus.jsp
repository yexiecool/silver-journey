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
		var loadings="";
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
	   loadings=iosOverlay({
		text: "Loading", 
		spinner: spinner
	   });
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
</style>
		<div class="mui-content" style="background: #141534;">
			<table class="wangti"  border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td colspan="8">
							 <div class="sjcont" style="width:40%; float:left; margin-left:30%;">
							 	 <p class="bianhao">id:<span>84664</span></p>				 	
						 	 	 <div class="jine">￥<span>10800</span></div>
								 <div class="renshu"><span>5000</span>人</div>
								 <div class="clear"></div>				
							 </div>
			
						</td>
					</tr>
					<tr>
						<td colspan="4">
							 <div class="sjcont" style="width:70%; float:left; margin-left:15%;">
							 	 <p class="bianhao">id:<span>84664</span></p>
								 <div class="jine">￥<span>10800</span></div>
								 <div class="renshu"><span>5000</span>人</div>
								 <div class="clear"></div>
							 </div>
			
						</td>
						<td colspan="4">
							 <div class="sjcont" style="width:70%; float:left; margin-left:15%;" >
							 	 <p class="bianhao">id:<span>84664</span></p>
								 <div class="jine">￥<span>10800</span></div>
								 <div class="renshu"><span>5000</span>人</div>
								 <div class="clear"></div>
							 </div>
			
						</td>
					</tr>
					<tr>
						<td colspan="2">
							 <div class="sjcont" style="width:90%; float:left; margin-left:5%;">
							 	 <p class="bianhao">id:<span>84664</span></p>
								 <div class="jine" style="width:100%; border-radius:0px;">￥<span>10800</span></div>
								 <div class="renshu" style="width:100%; border-radius:0 0 10px 10px; "><span>5000</span>人</div>
								 <div class="clear"></div>
							 </div>
			
						</td>
						<td colspan="2">
							 <div class="sjcont"  style="width:90%; float:left; margin-left:5%;">
							 	 <p class="bianhao">id:<span>84664</span></p>
								<div class="jine" style="width:100%; border-radius:0px;">￥<span>10800</span></div>
								 <div class="renshu" style="width:100%; border-radius:0 0 10px 10px; "><span>5000</span>人</div>
								 <div class="clear"></div>
							 </div>
			
						</td>
						<td colspan="2">
							 <div class="sjcont" style="width:90%; float:left; margin-left:5%;">
							 	 <p class="bianhao">id:<span>84664</span></p>
								<div class="jine" style="width:100%; border-radius:0px;">￥<span>10800</span></div>
								 <div class="renshu" style="width:100%; border-radius:0 0 10px 10px; "><span>5000</span>人</div>
								 <div class="clear"></div>
							 </div>
			
						</td>
						<td colspan="2">
							 <div class="sjcont"  style="width:90%; float:left; margin-left:5%;">
							 	 <p class="bianhao">id:<span>84664</span></p>
								 <div class="jine" style="width:100%; border-radius:0px;">￥<span>10800</span></div>
								 <div class="renshu" style="width:100%; border-radius:0 0 10px 10px; "><span>5000</span>人</div>
								 <div class="clear"></div>
							 </div>
			
						</td>
					</tr>
			
				</table>
		
 
		</div>
		
	 
</body>
</html>
