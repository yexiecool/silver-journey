<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/webcom/meta.jsp"%>
<%@include file="/webcom/bracket.jsp"%>
<%@include file="/webcom/jquery.validate_js.jsp"%>
<script src="${contextPath}/UserInterface/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/media/js/DT_bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/mvccol/js/jquery.form.js"></script>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<script type="text/javascript">

var editor=CKEDITOR.replace('context');
	$(document).ready(
			function() {
				var validator = $("#shopwxpayForm").validate(
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
 
 
	 
	function page_submit(num) {

		if (num == -1) {
			$("#fypage").val(0);
		} else if (num == -2) {
			$("#fypage").val($("#fypage").val() - 1);
		} else {
			$("#fypage").val(num);
		}

		$("#shopwxpayForm").submit();
	}
	
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
			<form id="shopwxpayForm" name="shopwxpayForm" method="post"
				action="${contextPath}/shop/shopwxpay.action">
				<div class="pageheader">
					<h2>
						<i class="fa fa-user"></i> 微官网 <span>微信支付记录</span>
					</h2>

				</div>
				<div class="panelss ">
					<div class="panel-body fu10">
						<div class="row-pad-5">

							<div class="form-group col-sm-1d">
								<select id="sel_state" name="sel_state" class="form-control "
									data-placeholder="请选择">
									<option value="">请选择</option>
									<option value="0">待支付</option>
									<option value="1">已支付</option>
									<option value="2">支付失败</option>
								</select>
							</div>

							<a href="javascript:page_submit(-1);" class="btn btn-primary">搜&nbsp;&nbsp;索</a>

						</div>
					</div>
					<!-- panel-body -->
				</div>
				<!-- panel -->

				<div class="panel-body">
					<div class="row">
						<div class="col-md-12">
							<div class="table-responsive">
								<table
									class="table table-striped table-action table-primary mb30">
									<thead>
										<tr>
											<th class="table-action">序号</th>
											<th class="table-action">订单编号</th>
											<th class="table-action">支付金额</th>
											<!-- <th class="table-action">微信商户订单号</th>
											<th class="table-action">微信支付订单号</th> -->
											<th class="table-action">类型</th>
											<th class="table-action">支付状态</th>
											<th class="table-action">下单时间</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${paylist}" var="bean">
											<tr>
												<td>${bean._id}</td>
												<td>${bean.orderid}</td>
												<td>${bean.paymoney}</td>
												<%-- <td>${bean.outtradeno}</td>
												<td>${bean.transactionid}</td> --%>
												
												<td><c:if test="${bean.type==1}">
												 		订单支付
												  </c:if> 
												  <c:if test="${bean.type==2}">
												 		矿机商城充值
												  </c:if> 
												  </td>
												  <td><c:if test="${bean.status==0}">
												 		待支付
												  </c:if> <c:if test="${bean.status==1}">
												 		支付成功
												  </c:if> <c:if test="${bean.status==2}">
												 		支付失败
												  </c:if></td>
												<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
														value="${bean.createdate}" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<%@include file="/webcom/bracket-page.jsp"%>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</section>


<script type="text/javascript"> 
$('#sel_state').val('${sel_state}').trigger("change");
</script>
</body>
</html>
