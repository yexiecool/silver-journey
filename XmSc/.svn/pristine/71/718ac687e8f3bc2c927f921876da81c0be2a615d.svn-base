<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/webcom/meta.jsp"%>
<%@include file="/webcom/bracket.jsp"%>
<%@include file="/webcom/jquery.validate_js.jsp"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script src="${contextPath}/bracket/js/jquery.tagsinput.min.js"></script>
<link rel="stylesheet"
	href="${contextPath}/bracket/css/jquery.tagsinput.css" />
<link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/app/css/font-awesome.min.css"
	rel="stylesheet">

<script type="text/javascript">
</script>
<style type="text/css">
.fa-spin1 {
	-webkit-animation: fa-spin 1s infinite linear;
	animation: fa-spin 1s infinite linear
}

.img-60 {
	width: 60px;
	height: 60px;
}
</style>

</head>
<body>
	<section>
		<%@include file="/webcom/header-bracket.jsp"%>
		<div class="mainpanel">
			<%@include file="/webcom/header-headerbar.jsp"%>
			 
				<div class="pageheader">
					<h2>
						<i class="fa fa-user"></i> 系统管理 <span>PADA充值</span>
					</h2>

				</div>

				<div class="panel-body">
					<div class="row">
					 
						<div class="col-md-12">
							<div class="table-responsive">
								<div style="margin-top:20px;">
									<label class="control-label"  >会员编号：</label> <input style="width: 500px;"
										type="text" id="vipno" name="vipno" class="form-control"
										placeholder="请输入会员编号" />
								</div>
								<div style="margin-top:20px;">
									<label class="control-label"  >请输入盼盼币数量：</label> <input type="text" style="width: 500px;"
										id="quantity" name="quantity" class="form-control" placeholder="请输入盼盼币数量" />
								</div>
								
								<div  >
									<button style="margin-top:20px;width:500px;"  id="savebtn" class="btn btn-primary width-10 maring-a clear weight500 hang40">提 &nbsp;&nbsp;交</button>
                                </div>							
							</div>
						</div>
	
					</div>
				</div>
			 
		</div>
	</section>

<script src="${ctx}/pc_gw/pc-wj/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function() {
				$('#savebtn').click(function(){
					var vipno =$('#vipno').val();
					var quantity =$('#quantity').val();
					if(vipno == '') {
						confirm('请输入会员编号')
						return;
					}else if(!check_validate(vipno)) {
						confirm('会员号输入错误')
						return;
					} else if(quantity == '') {
						confirm('请输入盼盼币数量')
						return;
					} else if(!isNumber(quantity)){
						confirm('盼盼币数量输入错误')
						return;
					}else{
						$.ajax({
							type:"post",
							url:"${ctx}/suc/drawboxyzm!recharge.action",
							async:true,
							data:{
								vipno:$('#vipno').val(),
								quantity:$('#quantity').val()
							},
							success:function(json){
								if(json.state == 1){
									confirm('网络超时，获取数据失败请重新操作')
								}else if(json.state == 0){
									location.href='${ctx}/suc/drawboxyzm!getrecharge.action'
								}
							}
						});
					}
				})
			})

		function check_validate(value){ 
		
		    //定义正则表达式部分 
		
		    var reg = "^[0-9]*[1-9][0-9]*$"; 
		    var re = new RegExp( reg ); 
		    if( re.test(value) ){ 
		        return true; 
		    }else{
			    return false; 		    	
		    }
		
		} 
			
			function isNumber( s )
			{
			    var regu = "^[0-9]+\.?[0-9]*$";
			    var re = new RegExp(regu);
			    if (re.test(s)) 
			    {
			        return true;
			    } 
			    else 
			    {
			        return false;
			    }
			}

		</script>
</body>
</html>
