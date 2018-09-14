<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webcom/taglibs.jsp" %>
<%@ include file="/webcom/limit.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>个人中心</title>
    <!-- Resource style -->	
    <script src="${ctx }/app/js/jquery-1.8.3.js"></script>
    <link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/app/css/font-awesome.min.css" rel="stylesheet"/> 
    <link rel="stylesheet" href="${ctx}/xmMobile/iconfont/iconfont.css"> 
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
	<link rel="stylesheet" href="${ctx}/app/css/mui.min.css">   
	
    <style>
		.line-bottom-dddddd {
            border-bottom: 1px solid #dddddd;
        }

        .line-top-dddddd {
            border-top: 1px solid #dddddd;
        }

        .line-left-dddddd {
            border-left: 1px solid #dddddd;
        }

        .line-right-dddddd {
            border-right: 1px solid #dddddd;
        }

        .bg-f5f5f9 {
            background-color: #f5f5f9;
        }

        .zi-6b6b6b {
            color: #6b6b6b
        }

        .line-height22 {
            line-height: 22px;
        }
        .mine-headbox{
        	min-height:190px;
        	background:#141534;
        	background-size:100% 100%;
        }
        .line-height30 {
    		line-height: 24px !important;
		}
		.tab-nav{
			width:100%;
			height:80px;
			/*display:-webkit-box;
			display:-ms-flexbox;
			display:flex;
			padding-top:20px;*/
			overflow: hidden;
		}
		.tab-nav li{
			/*-webkit-box-flex:1;
			    -ms-flex:1;
			        flex:1;
			width:1%;*/
			width: 25%;
			float: left;
			text-align: center;
			font-size: 12px;
		}
		.tab-nav li a{
			color: #000;
		}
		.tab-nav li i{
			font-size:20px;
			/*margin-bottom:10px;*/
			color: #f3e392;
		}
		.my_cate{
			/*position: relative;*/
    		padding-top: 13px;
    		padding-bottom: 10px;
    		overflow: hidden;
		}
		.my_cate_item {
		    -webkit-box-sizing: border-box;
		            box-sizing: border-box;
		    float: left;
		    width: 25%;
		    margin-bottom: 10px;
		}
		.my_cate_item a{
			text-decoration: none;
			color: #000;
		}
		.my_cate_item_logo {
		    display: block;
		    margin: 0 auto 7px;
		    width: 50px;
		    height: 50px;
		    padding: 0px;
		    border-radius: 50px;
		    text-align: center;
		    line-height: 40px;
		    color: #fff;
		}
		.my_cate_item_logo div{ color:#7cc3df;
		   margin-top:15px;
		}
		.my_cate_item_name {
		    font-size: 12px;
		   color:#7cc3df;
		    text-align: center;
		}
		.modal{
        	width: 100%;
        	height: 100%;
        	background: rgba(0,0,0,.7);
        	position: fixed;
        	top: 0;
        	left: 0;
        	right: 0;
        	bottom: 0;
        	z-index: 1001;
        	display: -webkit-box;
        	display: -ms-flexbox;
        	display: flex;
        	-webkit-box-pack: center;
        	    -ms-flex-pack: center;
        	        justify-content: center;
        	-webkit-box-align: center;
        	    -ms-flex-align: center;
        	        align-items: center;
        	display: none;
       }
        .modal-cont{
        	width: 80%;
        	height: 150px;
        	background: #fff;
        	border-radius: 10px;
        	z-index: 1002;
        	position: relative;
        	margin-top: -150px;
        }
        .modal-cont-tit{
        	width: 100%;
        	height: 30px;
        	line-height: 30px;
        	text-align: center;
        }
        .modal-cont-cont{
        	width: 100%;
        	height: 120px;
        	line-height: 20px;
        	font-size: 12px;
        	overflow-y: auto;
        	padding: 0 10px;
        }
        .modal-cont-foot{
        	width: 100%;
        	height: 30px;
        	line-height: 30px;
        	text-align: center;
        	position: absolute;
        	bottom: -50px;
        	left: 0;
        	color: #fff;
        }
        .modal-cont-foot span{
        	display: block;
        	margin: 0 auto;
        	border-radius: 50%;
        	width: 24px;
        	height: 24px;
        	line-height: 24px;
        	border: 1px solid #fff;
        }
        .collector{
        	width: 100%;
        	height: 80px;
        	display: -webkit-box;
        	display: -ms-flexbox;
        	display: flex;
        	padding: 20px 0;
        	background: #fff;
        }
        .collector li{
        	-webkit-box-flex: 1;
        	    -ms-flex: 1;
        	        flex: 1;
        	width: 1%;
        	text-align: center;
        }
        .collector li a{
        	color: #000;
        	font-size: 12px;
        	color: #999;
        }
        .collector li a div{
        	margin-bottom: 5px;
        	color: #6D6D6D;
        }
        .vipNumName{
        	color: #F02B2B;
        }
    </style>
     
</head>
<body class="cmp650" style="background:url('${ctx}/xmMobile/images/kjbg2.jpg') no-repeat top center; background-size:100% 100%;">
 <!--弹出层开始 -->
	<div class="mask opacity" id="mask" > </div>
	<div class="zxxcontent" id="zxxcontent">
		<a href="javascript:;" id="close" class="close">
			<i class="iconfont icon-close"></i>
		</a> <br />
		
		<p>您已经<span>${tbjlday}</span>天没有提现了</p>
		
	</div>
	<script type="text/javascript" >
    $(function () {
        document.getElementsByTagName('body').height="100%";
        $("body").css("overflow","hidden");
        var day = ${tbjlday };
        if(day == "" || day == null || day == undefined){
        	document.getElementById("zxxcontent").style.display = "none";
        }else{
        	document.getElementById("zxxcontent").style.display = "none";//block
        }
        
        $('#close').click(function () {
            
            $('.zxxcontent').hide();
            document.getElementsByTagName('body').height="auto";
            $("body").css("overflow","visible");
        })
    });
</script>
<style>

#close{ margin-top:8px; }
.opacity{ opacity:0.5; filter: alpha(opacity=30); background-color:#005EAC; }
.zxxcontent{ font-size:0.9rem; border-radius:10px; height:8rem; width:80%; overflow:auto;  opacity:0.8; filter: alpha(opacity=30); background-color:#005EAC;  position:fixed; padding:2% 8% 0 8%; top:30%; display: none;left:10%; z-index:100001; word-wrap:break-word;}
.zxxcontent a{opacity:0.5;display: block; background: black; border-radius: 50%; width: 1.5rem; height: 1.5rem; text-align: center;  line-height: 1rem; position:absolute;z-index: 1003;right: 2%;top:2%;}
.zxxcontent a i{ font-size: 1rem; color: #fff;  }
.zxxcontent p{ width:100%; text-align: center;  line-height:3rem; padding-top:10px; font-size: 1.1rem; color:#fff; }

</style>
 <!--弹出层结束 -->
		<header class="mui-bar mui-bar-nav" style="background: #141534; box-shadow: none;">
		    <a class="mui-action-back mui-icon mui-icon-undo mui-pull-left" href="javascript:history.go(-1)"  style="color:#fbfaff  !important;"></a>
		    <h1 class="mui-title"  style="color:#fbfaff;">个人中心</h1>
		    <!--<span class="mui-pull-right">保存到手机</span>-->
		</header>
	<main style='padding:40px 0px; height z-index: -10;'>
   	<div class="overflow-hidden width-10 position-r line-bottom-dddddd mine-headbox pd-20" style="padding: 0px 0px 40px 0">
  <!--设置-->
   	<!--客户头像-->
   	<div class="left" style="width:40%; float:left;">
   		<div class="border-radius50 width-10 txt-c" style="margin-top:17px;" >     
           <c:if test="${empty entity.headimgurl}">
            <img src="${ctx}/mvccol/img/user/weizhuce.jpg" class="img-wh70 border-radius50" style="margin:0 auto;"/>
          </c:if>
          <c:if test="${not empty entity.headimgurl}">
            <%-- <img src="${filehttp}/${entity.headimgurl}" class="width-10 border-radius50"/> --%>
             <img src="${ctx}/mvccol/img/user/weizhuce.jpg" class="img-wh70 border-radius50"/>
          </c:if>	
              
        </div>
        <!--会员级别-->
        <font size="2">
        	<div class="clear txt-c  zi-bai" style='display:flex;justify-content: center; margin:16px 0;  ' >
                 <div class="pull-left hang20 line-height22 zi-bai border-radius3 pl-5 pr-5" style="background:#7cc3df !important">
                 		<i class="fa fa-server  line-height20"></i><i class="pl-2" id="sfxs">
                         <c:if test="${user.agentLevel==1}">省级代理</c:if>
                         <c:if test="${user.agentLevel==2}">市级代理</c:if>
                         <c:if test="${user.agentLevel==3}">县级代理</c:if>
                         <c:if test="${user.agentLevel==4}">报单中心</c:if>
                         
                         <c:if test="${user.agentLevel==100}">微型矿机</c:if>
                         <c:if test="${user.agentLevel==101}">小型矿机</c:if>
                         <c:if test="${user.agentLevel==102}">中型矿机</c:if>
                         <c:if test="${user.agentLevel==103}">大型矿机</c:if>
                        
                         <c:if test="${user.agentLevel== '' || user.agentLevel == null}">
                             <c:choose>
                                 <c:when test="${user._id != 'notlogin'}">
                                                                                                                              游客
                                 </c:when>
                                 <c:otherwise>
                                 未登录                                                                                                
                                    </c:otherwise>
                             </c:choose>
                         </c:if>
                         </i></div>
                
             </div>
             
              <!-- 会员编号 -->
                   <div class="txt-c" >
                   		<span style="color:#7cc3df !important;">ID: ${user.no}</span>
                   </div>
             
        </font>
   	</div>
   	<div class="right" style="width:60%; float:left;">
   	     <div class="width-10" style='position:absolute;top:45px;'>
           
                <font size="2">
                    <div class="line-height25 " style="color:#fbfaff; ">
                    	${entity.nickname}
	                   <%--  <i class="zi-lan-tq fa fa-gear" style='font-size:16px; padding-left:10px; color:#F02B2B;'  onclick="window.location.href='${ctx}/user/fromuser!detail.action?custid=${custid}&lscode=${lscode}&backurl=/integral/miners!ownperson.action'" ></i>	   					 --%>
   					</div>
   			                 
                    <%-- <div class="hang25 line-height20 pt-5" style="color:#888888">
                        <div class="txt-c"><i class="pr-10 zi-cheng">积分<i class="pl-2 zi-cheng">${entity.jf}</i></i><c:if test="${not empty  entity.email}">${entity.email}</c:if><c:if test="${empty entity.email}">这家伙很懒，没有邮箱！</c:if></div>
                        <<div class="pull-right" onclick="window.location.href='${ctx}/user/fromuser!detail.action?custid=${custid}&lscode=${lscode}'"><i class="zi-lan-tq">修改</i></div> 
                    </div> --%>
                </font>
            
            <style>
             .bdcount span{color: #7cc3df; text-alight:left; font-size:12px; line-height:30px;  display:block; width:100%; float:left;   }
              .bdcount span i{ padding-left:10px; }
            </style> 
            <div class="bdcount" style="padding:0; margin-top: 7px;height: auto;overflow: hidden;">
             
               <c:if test="${not empty yongjin}">
                  <span style="float:left">佣金:<i>${yongjin}</i></span>
                </c:if>
                <c:if test="${empty llb}">
                <span style="float:left">佣金:<i>0.00</i></span> 
                </c:if>
               
            	<c:if test="${not empty jf}">
            	<span >PADA:<i >${jf}</i></span> 
            	</c:if> 
            	<c:if test="${empty jf}">
            	<span >PADA:<i >0.00</i></span> 
            	</c:if> 
            <br/>  
             <c:if test="${empty xfjl }">
                <span>消费钱包：<i >0.00</i></span>
                </c:if>
                <c:if test="${not empty xfjl }"> 
                <span>消费钱包：<i >${xfjl}</i></span>  
                </c:if> 
                <br />
            	 <c:if test="${empty ktppb }">
                <span>可提PADA：<i >0.00</i></span>
                </c:if>
                <c:if test="${not empty ktppb }"> 
                <span>可提PADA：<i >${ktppb}</i></span>  
                </c:if> 
                <br/>  
	           <%--   <c:if test="${empty freezeppb }">
	                <span>冻结PADA：<i >0.00</i></span>
	            </c:if>
	            <c:if test="${not empty freezeppb }"> 
	                <span>冻结PADA：<i >${freezeppb}</i></span>  
	            </c:if> --%>
            </div>
            
             
           
        </div>
   	</div>
        
      
    </div>
    <div class="clear hang10"></div> 
      <!-- 个人中心导航 -->
    <ul class="my_cate" style="padding-bottom:100px;" >
    	
    	<li class="my_cate_item">  
    		<a href="${ctx}/shop/shop!recharge.action?custid=${custid}&agid=${agid}&lscode=${lscode}"> 
    			<span class="my_cate_item_logo ">
    			  <font size="6">
    			  		<div class="fa fa-credit-card "></div>
    			  </fon> 			
    			</span>
    			<p class="my_cate_item_name ">消费充值</p>   	
    		 </a> 
    	</li>
    	
    	<style>
    	 .my_cate_item{ position: relative;  }
    	 .tdnum{background:red;  position: absolute; top:5px;left:15px; width:20px;height:20px; color: #fff; text-align: center;  border-radius: 10px; font-size:14px;  }
    	</style>
    	<li class="my_cate_item">
    		<c:if test="${ not empty numbercount &&  numbercount > 0 }" >
    		<div class="tdnum">${numbercount}</div>   	
    		</c:if>
    		<a href="${ctx}/user/relation!mobilefindrelation.action?custid=${custid}&lscode=${lscode}">
    			<span class="my_cate_item_logo ">
    			  <font size="6">
    			  		<div class="fa fa-group"></div>
    			  </font>   			
    			</span>
    			<p class="my_cate_item_name ">我的团队</p>
    		</a>
    	</li>
    	<li class="my_cate_item">
    		<a href="${ctx}/user/fromuser!share.action?custid=${custid}&lscode=${lscode}">
    			<span class="my_cate_item_logo ">
    			  <font size="6">
    			  		<div class="fa fa-share-alt"></div>
    			  </font>
    			
    			</span>
    			<p class="my_cate_item_name ">我的分享</p>
    		</a>
    	</li>
    	<li class="my_cate_item">
    		<a href="${ctx}/integral/miners!szjy.action?custid=${custid}&agid=${agid}&lscode=${lscode}">
    		<span class="my_cate_item_logo ">
    			  <font size="6">
    			  		<div class="fa fa-eercast"></div>
    			  </font>
    			
    			</span>
    			<p class="my_cate_item_name">交易所</p>
    		</a>
    	</li>
    	
    		<div style="clear:both;" ></div>	
		<li class="my_cate_item">
    		<a href="${ctx}/integral/miners!ppbtj.action?custid=${custid}&agid=${agid}&lscode=${lscode}">
    			<span class="my_cate_item_logo ">
    			  <font size="6">
    			  		<div class="fa fa-bars"></div>
    			  </font>			
    			</span>
    			<p class="my_cate_item_name">PADA明细</p>
    		</a>
    	</li>
    
     <li class="my_cate_item" id="alertBtn3">
    		<%-- <a href="${ctx}/integral/miners!ownminer.action?custid=${custid}&agid=${agid}&lscode=${lscode}"> --%>
    			<span class="my_cate_item_logo ">
    			  <font size="6">
    			  		<div class="fa fa-list-alt"></div>
    			  </font>
    			
    			</span>
    			<p class="my_cate_item_name ">佣金明细</p>
    		<!-- </a> -->
    	</li>
    	<%-- <li class="my_cate_item">
    		<a href="${ctx}/integral/miners!list.action?custid=${custid}&agid=${agid}&lscode=${lscode}">
    			<span class="my_cate_item_logo ">
    			  <font size="6">
    			  		<div class="fa fa-arrows-h"></div>
    			  </font>
    			
    			</span>
    			<p class="my_cate_item_name ">佣金互转</p>
    		 </a>
    	</li> --%>
    	
    	 <li class="my_cate_item"  id="alertBtn2">
    	<!--${ctx}/integral/commission!apply.action?custid=${custid}&agid=${agid}&lscode=${lscode}-->
    		<a href="javascript::">
    			<span class="my_cate_item_logo ">
    			  <font size="6">
    			  		<div class="fa fa-briefcase"></div>
    			  </font>
    			
    			</span>
    			<p class="my_cate_item_name ">佣金提现</p>
    		</a>
    	</li>
    	
    		
    	
    	<li class="my_cate_item" id="alertBtn">   		
    			<span class="my_cate_item_logo ">
    			  <font size="6">
    			  		<div class="fa fa-address-book-o"></div>
    			  </font>
    			
    			</span>
    			<p class="my_cate_item_name ">联系我们</p>
    					
    	</li>   
    	
    	 
    	
    	<script src="${ctx}/app/js/mui.min.js"></script>
		<script type="text/javascript" charset="utf-8">
	    	  //mui初始化
	    	    document.getElementById("alertBtn3").addEventListener('tap', function() {
	                mui.alert('数据完善中，请耐心等待', '提示', function() {
	                   
	                });
	            });
	            document.getElementById("alertBtn2").addEventListener('tap', function() {
	                mui.alert('数据完善中，请耐心等待', '提示', function() {
	                   
	                });
	            });
	            document.getElementById("alertBtn").addEventListener('tap', function() {
	                mui.alert('请拨打客服电话：4001008706', '提示', function() {
	                   
	                });
	            });
	           
	    	
	      </script>	
<%-- 
    	<li class="my_cate_item">
    		<a href="${ctx}/integral/miners!ownminer.action?custid=${custid}&agid=${agid}&lscode=${lscode}">
    			<span class="my_cate_item_logo ">
    			  <font size="6">
    			  		<div class="fa fa-star"></div>
    			  </font>
    			
    			</span>
    			<p class="my_cate_item_name ">新手指南</p>
    		<!-- </a> -->
    	</li> --%>
    	
		<div style="clear:both;" ></div>
    	<!-- 
    	li class="my_cate_item">
    		<a href="${ctx}/suc/integral!webll.action?custid=${custid}&lscode=${lscode}">
    			<span class="my_cate_item_logo bg-cheng"><i class="fa fa-diamond" style="font-size: large;"></i></span>
    			<p class="my_cate_item_name ">我的乐乐收益</p>
    		</a>
    	</li>
    	-->
     
    </ul>
    <!--<div class="clear hang10 bg-f5f5f9 line-bottom-dddddd"></div>
    <div class="clear hang10"></div> -->
     <%@include file="/webcom/kjfoot.jsp" %>
     <div class="modal">
	<div class="modal-cont">
		<div class="modal-cont-tit">
			提示
		</div>
		<div class="modal-cont-cont">
			提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示
		</div>
		<div class="modal-cont-foot">
			<span class="fa fa-close"></span>
		</div>
	</div>
</div>

	  
	<div class="modal">
		<div class="modal-cont">
			<div class="modal-cont-tit">
				提示
			</div>
			<div class="modal-cont-cont">
				提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示提示
			</div>
			<div class="modal-cont-foot">
				<span class="fa fa-close"></span>
			</div>
		</div>
	</div>
<script>
		function cllx(){
			$(".bmask").hide(); 
		}
		function  popcode(v){
			  var submitData = {
					  type:v
					};
					$.post('${ctx}/integral/miners!stkj.action?custid=${custid}&lscode=${lscode}', submitData, function(json) {
						  if(json.state==0){
							  alert("设置成功！");
							  window.location.href=window.location.href;
						  }
					}, "json");
					  
		}
		
		function getcbLx(){
			  var submitData = { 
					};
					$.post('${ctx}/integral/miners!getkjlx.action?custid=${custid}&lscode=${lscode}', submitData, function(json) {
						if(json.state==0){
							if(json.data==0){
								$(".bmask").show();
							}
						}else if(json.state==2){
							$(".bmask").show(); 
						}else {
							alert("获取产币类型失败！");
						} 
						
						
					}, "json");
					  
		}
		getcbLx();
		$('#close').click(function(){
				$('.mask').css('display','none')
		})
		$('.modal-cont-foot span').click(function(){
		 	$('.modal').css('display','none')
		})
		
		function checksf(){
			 if($.trim($("#sfxs").html())=="游客"){
				  $.post('${ctx}/user/user!getScxf.action?lscode=${lscode}', submitData,
		                  function (json) {
					      if(json.state==0){
					    	  $("#sfxs").html("会员"); 
					      } 
		                  }, "json")
			 }
			 
			
		}
		checksf();

</script>

<%@ include file="/webcom/toast.jsp" %>
<c:if test="${com.zsjf>0}">
  <c:if test="${sczs==1}">
  <%@ include file="/webcom/jfts-page.jsp" %>
  </c:if> 
</c:if> 
<%@include file="/webcom/shop-foot3.jsp" %>
</body>
</html>