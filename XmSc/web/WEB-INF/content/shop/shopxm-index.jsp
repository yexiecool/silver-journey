<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
			<meta name="viewport"
				content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
			<title>${title}</title> <script src="${ctx}/app/js/jquery-1.8.3.js"></script>
			<meta charset="UTF-8">
				<meta name="viewport"
					content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
				<title>熊猫超市</title>
				<link rel="stylesheet" href="${ctx}/app/css/sm_shop.css" />
				<link rel="stylesheet" type="text/css"
					href="${ctx}/app/css/mui.min.css" />
				<link rel="stylesheet" href="${ctx}/app/iconfont/iconfont.css">
					<script type="text/javascript" src="${ctx}/app/js/mui.min.js"></script>
</head>
<body>
	<div class="mui-slider">
		<div class="mui-slider-group mui-slider-loop">
			<!--
	  	<div class="mui-slider-item mui-slider-item-duplicate"><a href="#"><img src="http://pic6.photophoto.cn/20080324/0034034837747013_b.jpg" /></a></div>
	    <div class="mui-slider-item"><a href="#"><img src="images/banner1.jpg" /></a></div>
	    <div class="mui-slider-item"><a href="#"><img src="http://pic28.photophoto.cn/20130915/0011024062356712_b.jpg" /></a></div>
	    <div class="mui-slider-item"><a href="#"><img src="https://goss.veer.com/creative/vcg/veer/800water/veer-149449636.jpg" /></a></div>
	    <div class="mui-slider-item"><a href="#"><img src="http://pic.qiantucdn.com/58pic/25/56/25/5839607538c8c_1024.jpg" /></a></div>
	    <div class="mui-slider-item"><a href="#"><img src="http://pic29.photophoto.cn/20131127/0035035928175889_b.jpg" /></a></div>
	    <div class="mui-slider-item"><a href="#"><img src="http://pic6.photophoto.cn/20080324/0034034837747013_b.jpg" /></a></div>
	    <div class="mui-slider-item mui-slider-item-duplicate"><a href="#"><img src="images/banner1.jpg" /></a></div>
	   
	    
	    <c:forEach items="${llist}" var="bean"   >
	   		 <div class="mui-slider-item ">**************${bean.title}<a href="#"><img src="${filehttp}${bean.picurl}" /></a></div>
	    </c:forEach>
	     
	     -->
		</div>


		<c:forEach items="${llist}" var="bean">
			<div class="mui-slider-item ">
				<a href="${bean.url}"><img src="${filehttp}${bean.picurl}" /></a>
			</div>
		</c:forEach>

	</div>
	<script type="text/javascript">
		//获得slider插件对象
		var gallery = mui('.mui-slider');
		gallery.slider({
		  interval:5000//自动轮播周期，若为0则不自动播放，默认为0；
		});
	</script>

	<div class="list">
		<ul>
			<c:forEach items="${typelist}" var="typebean">

				<li><a
					href="${ctx}/shop/protype!classme.action?custid=${custid}&agid=${agid}&lscode=${lscode}&goodstype=">
						<img src="${filehttp}${typebean.picurl}" alt="" />
						<p>${typebean.name}</p>
				</a></li>

			</c:forEach>

		</ul>
	</div>
	<!--  
	<div class="Supermarket">
		<h6 style="font-size:14px;color:#302d34;">超市必买</h6>
		<div class="Supermarket_left">
			<a href="#"><img src="${ctx}/app/images/fengqiang.jpg" alt="" /></a>
		</div>
		<div class="Supermarket_right">
			<div class="Supermarket_right_img">
				<a href="#">
					<div>
						<h6 style="font-size:14px;color:#302d34;">有好货</h6>
						<p style="color:#74797f">草原今朝</p>
						<p style="color:#7dc5db">不添加防腐剂 </p>
						<span>内蒙古特产</span>
					</div>
					<div><img src="images/shipin2.png" alt="" /></div>
				</a>
			</div>
			<div class="Supermarket_right_img">
				<a href="#">
					<div>
						<h6 style="font-size:14px;color:#302d34;">量贩装</h6>
						<p>威猛先生</p>
						<p style="color:#a4a8ce">除菌99.9% </p>
						<span style="background:#a9acd7">热卖19000件</span>
					</div>
					<div><img src="images/shipin3.png" alt="" /></div>
				</a>
			</div>
		</div>
	</div>
	-->

	<c:forEach items="${cplist}" var="cpbean">
		<div class="home">
			<h6 style="font-size: 14px; color: #302d34; float: left;">${cpbean.name}</h6>
			<h6
				style="font-size: 14px; color: #07B849; letter-spacing: 3px; margin-left: 5px; float: left;">PANDAMALL</h6>
			<p style="clear: both;"></p>
			<ul>
				<c:forEach items="${cpbean.products}" var="pbean">

					<li><a
						href="${ctx}/shop/shop!shopproduct.action?custid=${pbean.custid}&agid=${agid}&lscode=${lscode}&pid=${pbean._id}">
							<img src="${filehttp}${pbean.picurl}" alt="" />
							<p style="background: linear-gradient(90deg, #f3aa10, #f4cc74);">${pbean.ptitle}</p>
					</a></li>
				</c:forEach>
				<!-- 
			<li>
				<a href="#">
					<img src="images/home1.png" alt="" />
					<p style="background: linear-gradient(90deg,#f88157,#f7b39c);">水果大咖</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/home2.png" alt="" />
					<p style="background: linear-gradient(90deg,#f873a2,#f8acc6);">香氛控</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/home3.png" alt="" />
					<p style="background: linear-gradient(90deg,#8695da,#b8c0e7);">夏日清凉季</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/home4.png" alt="" />
					<p style="background: linear-gradient(90deg,#59c3af,#9fdbcf);">洁癖星人</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/home5.png" alt="" />
					<p style="background: linear-gradient(90deg,#dbb497,#e7d2c1);">家有儿女</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/home6.png" alt="" />
					<p style="background: linear-gradient(90deg,#f3aa10,#f4cc74);">睡前好福利</p>
				</a>
			</li>
			
			 -->


			</ul>
		</div>
	</c:forEach>
	<!-- 
	<div class="Brand">
		<h6 style="font-size:14px;color:#302d34;">品牌精选</h6>
		<a href="#"><img src="" alt="" /><img src="images/shopimg.jpg" alt="" /></a>
		<ul>
			<li>
				<a href="#">
					<h6 style="font-size:14px;color:#302d34;">好货</h6>
					<p>洗护冰霜狂欢</p>
					<img src="images/brand1.png" alt="" />
				</a>
			</li>
			<li>
				<a href="#">
					<h6 style="font-size:14px;color:#302d34;">美素佳儿</h6>
					<p>母婴狂欢购</p>
					<img src="images/brand2.png" alt="" />
				</a>
			</li>
			<li>
				<a href="#">
					<h6 style="font-size:14px;color:#302d34;">满满零食箱</h6>
					<p>百变多样 任性挑</p>
					<img src="images/brand3.png" alt="" />
				</a>
			</li>
		</ul>
	</div>
	<div class="Nomination">
		<h6 style="font-size:14px;color:#302d34;">金榜题名</h6>
		<h6 style="font-size:14px;color:#302d34;font-size: 12px;">热门休闲零食榜</h6>
		<ul style="background: #E3EBF6;">
			<li>
				<a href="#">
					<img src="images/names1.png" alt="" />
					<p>格力离百醇注心饼干多种口味组合吃货零食</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/names2.png" alt="" />
					<p>百草味蔓越莓干</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/names3.png" alt="" />
					<p>彩虹糖125G混合水果口味糖果创意</p>
				</a>
			</li>
		</ul>
	</div>
	<!--私藏美容护肤榜 
	<div class="Nomination">
		<h6 style="font-size:14px;color:#302d34;font-size: 12px;">私藏美容护肤榜</h6>
		<ul style="background: #e6f6ec;">
			<li>
				<a href="#">
					<img src="images/chaoshi1.png" alt="" />
					<p>高倍防晒，透明不化妆娜扎同款150ml</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/chaoshi2.png" alt="" />
					<p>法国Dior迪奥香水Miss花漾甜心小姐女士淡香氛</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/chaoshi3.png" alt="" />
					<p>【新装上市】薇姿温泉矿物保湿水活霜50ml(清爽型)面霜 补水</p>
				</a>
			</li>
		</ul>
	</div>
	<!--我的品质生活榜-->
	<!--
	<div class="Nomination">
		<h6 style="font-size:14px;color:#302d34;font-size: 12px;">我的品质生活榜</h6>
		<ul style="background: #e8eaf7;">
			<li>
				<a href="#">
					<img src="images/chaoshi4.png" alt="" />
					<p>行李箱铝框万向轮拉杆箱旅行箱24寸</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/chaoshi5.png" alt="" />
					<p>马桶家用坐便器，小户型卫生间防臭虹吸式陶瓷坐便器</p>
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/chaoshi6.png" alt="" />
					<p>Apple/苹果 iphone X苹果X 5.8英寸手机iPhoneX港版国行美版白版</p>
				</a>
			</li>
		</ul>
	</div>
	<!--熊猫直营店-->
	<!--
     <div class="xmcs"  style="background:#fff;">
     	<h6 >熊猫直营店</h6>
     	<ul>
     		<li>		
 				<a href="#">
 					<img src="images/chaoshi7.png"/>
 					<p><span>熊猫直营店</span>名创优品咱们裸熊腰果180g香脆单包3包</p>
					<font>￥85.00</font>
					<i class="iconfont icon-cart"></i>
 				</a>
     		</li>
     		<li>		
 				<a href="#">
 					<img src="images/chaoshi8.png"/>
 					<p><span>熊猫直营店</span>益达木糖醇无糖口香糖（40粒）56g香橙薄荷味</p>
					<font>￥65.00</font>
					<i class="iconfont icon-cart"></i>
 				</a>
     		</li>
     		<li>		
 				<a href="#">
 					<img src="images/chaoshi9.png"/>
 					<p><span>熊猫直营店</span>食人谷靖江猪肉铺200g蜜汁味10袋</p>
					<font>￥195.00</font>
					<i class="iconfont icon-cart"></i>
 				</a>
     		</li>
     		<div class="clear"></div>
     		<li>		
 				<a href="#">
 					<img src="images/chaoshi10.png"/>
 					<p><span>熊猫直营店</span>格力高组合装（百醇草莓香草48g+百醇柠檬）</p>
					<font>￥38.60</font>
					<i class="iconfont icon-cart"></i>
 				</a>
     		</li>
     		<li>		
 				<a href="#">
 					<img src="images/chaoshi11.png"/>
 					<p><span>熊猫直营店</span>百草味黄金玉米豆70g两袋</p>
					<font>￥3.50</font>
					<i class="iconfont icon-cart"></i>
 				</a>
     		</li>
     		<li>		
 				<a href="#">
 					<img src="images/chaoshi12.png"/>
 					<p><span>熊猫直营店</span>自在乡村玉米碎400g一包</p>
					<font>￥12.00</font>
					<i class="iconfont icon-cart"></i>
 				</a>
     		</li>
     		<div class="clear"></div>
     		<li>		
 				<a href="#">
 					<img src="images/chaoshi13.png"/>
 					<p><span>熊猫直营店</span>安琪百钻小苏打粉180g十五袋</p>
					<font>￥37.00</font>
					<i class="iconfont icon-cart"></i>
 				</a>
     		</li>
     		<li>		
 				<a href="#">
 					<img src="images/chaoshi14.png"/>
 					<p><span>熊猫直营店</span>贝因美菁爱婴儿配方奶粉（0-6月龄）400g1段</p>
					<font>￥480.00</font>
					<i class="iconfont icon-cart"></i>
 				</a>
     		</li>
     		<li>		
 				<a href="#">
 					<img src="images/chaoshi15.png"/>
 					<p><span>西安儒愿生物科技有限公司</span>儒愿SOD露酒46°500ml
 					/瓶</p>
					<font>￥255.00</font>
					<i class="iconfont icon-cart"></i>
 				</a>
     		</li>
			<div class="clear"></div>
     	</ul>
     </div>
 -->




</body>
</html>

