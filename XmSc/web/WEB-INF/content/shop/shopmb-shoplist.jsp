 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webcom/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>店铺列表</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="${ctx}/xmMobile/css/mui.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${ctx}/xmMobile/css/common.css" />
		<link href="${ctx}/app/css/iosOverlay.css" rel="stylesheet"/>
		<link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css"/>
		<link href="${ctx}/app/css/font-awesome.min.css" rel="stylesheet"/> 
		<link href="${ctx }/app/css/font-awesome-ie7.min.css" rel="stylesheet">
		<script src="${ctx}/xmMobile/js/jquery-2.1.0.js" type="text/javascript" charset="utf-8"></script>
		
		<script src="${ctx}/app/js/iosOverlay.js"></script>
    	<script src="${ctx}/app/js/spin.min.js"></script>
    	<script type="text/javascript" src="${ctx }/app/js/jquery.Spinner.js"></script>
		<style type="text/css">
			.mui-table-view .mui-media-object{
				margin-top: 10px;
				max-width: 60px;
				height: 60px;
				vertical-align: middle;
				border-radius: 5px;
			}
			.mui-card .mui-card-content img {
				height: 124px;
			}
			.mui-table-view:after{
				display: none;
			}
			.similar-product-text {
				line-height: 33px;
				font-size: 12px;
				overflow: hidden;
				-o-text-overflow: ellipsis;
				text-overflow: ellipsis;
				display: -webkit-box;
				-webkit-line-clamp: 2;
				-webkit-box-orient: vertical;
				word-break: break-word;
				color: #232326;
				margin-top: 5px;
				line-height: 20px;
				margin-bottom: 3px;
				padding: 0 4px;
			}
			
			.similar-product-info {
				display: block;
				position: relative;
				overflow: hidden;
				height: 20px;
			}
			.similar-product-info span.similar-product-shopCar,.similar-product-info .similar-product-price{
				display: block;
				overflow: hidden;
			}
			.similar-product-shopCar {
				width: 18px;
				height: 18px;
				display: block;
				background: url(${ctx}/xmMobile/img/icon/icon-shopCar.png) no-repeat;
				background-size: 100% auto;
				position: absolute;
				right: 5px;
				top: 0;
			}
			.similar-product-price{
				color: #fd0707;
				position: absolute;
				top: 0;
				left: 0px;
			}
			.mui-table-view.mui-grid-view .mui-table-view-cell{
				padding-left: 5px;
			}
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
		var issend = true;
        var fypage = 0;
        var xszf = "";
        var type = "";
        var lx = "";
        var sel = "";
       
        function ajaxjz() {//加载
        	if(loadings==""){
        		loading();
        	} 
            if (!issend) {
                return;
            } 
            var submitData = {
                type: '${goodstype}'
            }; 
            issend = false;
            $.post('${ctx}/shop/shopmb!shopmblist.action?custid=${custid}&agid=${agid}&fypage=' + fypage, submitData,
                    function (json) {
            	     loadings.hide();
                        var html = $('.recomend').html();
                        if (json.state == 0) {
                            var v = json.list;
                            for (var i = 0; i < v.length; i++) {
                                	html +='<li class="mui-table-view-cell mui-media mui-col-xs-6" >'
                						+'<div class="mui-card" style="margin: 2px;">'
                						+'<div class="mui-card-content">'
                						+'<a href="${ctx}/shop/shop!index.action?custid=${custid}&agid=${agid}&lscode=${lscode}&comid=' + v[i]._id +'">'
                						+'<img src="${filehttp}' + v[i].logo + '" />'
                						+'</a>'	
                						+'</div>'
                						+'<div class="mui-card-footer" style="padding: 10px 5px;display: block;">'
                						+'<span class="similar-product-text" style="height:40px;text-align: left;">' + v[i].name + ' </span>'
                						+'<div class="similar-product-info ">';
                						/* if(v[i].price!=null){
                							html+='<span class="similar-product-price"><span>￥</span>'+ v[i].price.toFixed(2)+'</span>';
                						} 
                						html+='<span class="similar-product-shopCar" onclick="cart('+v[i]._id+','+v[i].num+','+v[i].price+')"></span>'			 */
                						+'</div></div></div></li>';		
            		    	              
            					
//                              }
                            }
                          /*   html+='</ul></div></div>'; */
                            fypage++;
                            $('.recomend').html(html);
                            issend = true;
                        } else {
                        	//$('.recomend').html('暂无数据');
                        }
                       
                    }, "json")
              }
     
		</script>
	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
		    <a class=" mui-icon mui-icon-undo mui-pull-left" href="javascript:history.go(-1)"></a>
		    <h1 class="mui-title">店铺列表</h1>
		    <!--<span class="mui-pull-right">保存到手机</span>-->
		</header>
		<div class='mui-content' style="height: 100%;background-color: #fff;">
			<div class='mui-row'>
				<ul class="mui-table-view mui-grid-view goods recomend" style="padding: 0;">
					
				</ul>
			</div>
		</div>
		
		<%@include file="/webcom/shop-foot.jsp" %>
		<script src="${ctx}/xmMobile/js/mui.min.js"></script>
		<script type="text/javascript">
			mui.init(); 
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