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
	var editor = CKEDITOR.replace('context');
	$(document).ready(
			function() {
				var validator = $("#addshopjoinForm").validate(
						{
							rules : {
								title : {
									required : true
								},
								sort : {
									digits : true,
									required : true
								}

							},
							messages : {},
							highlight : function(element) {
								jQuery(element).closest('.form-group')
										.removeClass('has-success').addClass(
												'has-error');
							},
							success : function(element) {
								jQuery(element).closest('.form-group')
										.removeClass('has-error');
							}
						});
			});

 
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
						<i class="fa fa-user"></i> 系统管理 <span>修改密码</span>
					</h2>

				</div>

				<div class="panel-body">
					<div class="row">
					 
						<div class="col-md-12">
							<div class="table-responsive">
							    <input type="hidden" id="fid" value="${id}" />
							    <input type="hidden" id ="fpwd" value="${passwd}" />
								<div style="margin-top:20px;">
									<label class="control-label">当前登录账户：</label> <label class="control-label"> ${loginname}</label>
								</div>
								<div style="margin-top:20px;">
									<label class="control-label"  >请输入原始密码：</label> <input style="width: 500px;"
										type="text" id="oldpwd" name="oldpwd" class="form-control"
										placeholder="请输入" />
								</div>
								<div style="margin-top:20px;">
									<label class="control-label"  >请输入新密码：</label> <input type="password" style="width: 500px;"
										id="newpwd" name="newpwd" class="form-control" placeholder="请输入" />
								</div>
								<div style="margin-top:20px;">
									<label class="control-label"  >确认新密码：</label> <input type="password" style="width: 500px;"
										id="newpwds" name="newpwds" class="form-control" placeholder="请输入" />
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
					
					var oldpwd =$('#oldpwd').val();
					var fpwd =$('#fpwd').val();
						
					var newpwd=	$('#newpwd').val();
					var newpwds=$('#newpwds').val()
					 
					
					if(oldpwd == '') {
						confirm('请输入原始密码')
					} else if(newpwd == '') {
						confirm('请输入新密码')
					} else if(newpwds == '') {
						confirm('确认密码不能为空')
					} else if(newpwds != newpwd ) {
						confirm('两次输入密码不一致')
					} else if(oldpwd != fpwd) {
						confirm('原始密码不正确')
					}else{
						
						$.ajax({
							type:"post",
							url:"${ctx}/shop/updatepwd!changepw.action",
							async:true,
							data:{
								id:$('#fid').val(),
								password:$('#newpwd').val()
							},
							success:function(json){
								if(json.state == 1){
									confirm('网络超时，获取数据失败请重新操作')
								}else if(json.state == 0){
									location.href='${ctx}/login.action'
								}
							}
						});
						
					}
					
					
				})
				

			})
		</script>


</body>
</html>
