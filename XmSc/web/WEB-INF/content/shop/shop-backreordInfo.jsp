<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>

<%@include file="/webcom/meta.jsp"%>
<%@include file="/webcom/bracket.jsp"%>
<%@include file="/webcom/jquery.validate_js.jsp"%>
<link href="${ctx }/cmp/css/cmp_js_yangshibiao.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${contextPath}/js/upload/swfobject.js"></script>
<script type="text/javascript" src="${contextPath}/js/upload/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${contextPath}/js/upload/upload.js"></script>
<script src="${contextPath}/UserInterface/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
function page_submit(num) {
    if (num == -1) {
    	$("#id").val('${id}');
        $("#fypage").val(0);
    } else if (num == -2) {
    	$("#id").val('${id}');
        $("#fypage").val($("#fypage").val() - 1);
    } else {
    	$("#id").val('${id}');
        $("#fypage").val(num);
    }
    $("#custinfoForm").submit();
}
</script>
</head>
<body>
<section>
  <%@include file="/webcom/header-bracket.jsp"%>
  <div class="mainpanel">
	<%@include file="/webcom/header-headerbar.jsp"%>
    <form id="custinfoForm" name="custinfoForm" method="post"  action="${contextPath}/shop/shop!backreordInfo.action">
    	<div class="pageheader">
			<h2><i class="fa fa-user"></i>结算管理 <span>矿机产出明细</span><span><a href="${ctx }/shop/shop!backreordXfInfo.action?id=${id}">其他回本明细</a></span></h2>
		</div>
		<input id="id" name="id" type="hidden" value=""/>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12">
					<div class="table-responsive">
						<table class="table table-primary mb30" >
							<thead>
								<tr>
									<th>会员</th>
									<th>会员</th>
									<th>类型</th>
									<th>状态</th>
									<th>说明</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${lists}" var="bean">
								<tr>
									<td>${bean.custid}</td>
									<td>${bean.fromUser}</td>
									<td>
									<c:choose>
									<c:when test="${bean.type == 'shop_bmzt'}">利润提成</c:when>
									<c:when test="${bean.type == 'shop_exchange'}">消费兑换</c:when>
									<c:when test="${bean.type == 'recommend_give'}">业绩赠送</c:when>
									<c:when test="${bean.type == 'ps_account'}">开通账户收益</c:when>
									<c:when test="${bean.type == 'ps_recovery'}">回本赠送矿机</c:when>
									</c:choose>
									</td>
									<td>${bean.value}</td>
									<td>${bean.remark}</td>
								</tr>
							</c:forEach>
						</table>
						<%@include file="/webcom/bracket-page.jsp"%>
					</div>
				</div>
			</div>
		</div>
    </form>
  </div>
</section>
	<script>
jQuery(".select2").select2({
    width: '100%'
});
</script>
</body>
</html>
