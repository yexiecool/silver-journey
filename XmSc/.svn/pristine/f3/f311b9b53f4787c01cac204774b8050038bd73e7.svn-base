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
        $("#fypage").val(0);
    } else if (num == -2) {
        $("#fypage").val($("#fypage").val() - 1);
    } else {
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
    <form id="custinfoForm" name="custinfoForm" method="post"  action="${contextPath}/shop/shop!backreord.action">
    	<div class="pageheader">
			<h2><i class="fa fa-user"></i>结算管理 <span>回本统计</span><span>账户总额 :${sum}</span></h2>
		</div>
		<div class="panelss ">
			<div class="panel-body fu10">
				<div class="row-pad-5">
					<div class="form-group col-sm-2">
						<input type="text" id="vip_no" name="vip_no" value="${vip_no}" class="form-control" placeholder="会员编号"/>
					</div>
					<a href="javascript:page_submit(-1);" class="btn btn-primary">搜&nbsp;&nbsp;索</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12">
					<div class="table-responsive">
						<table class="table table-primary mb30" >
							<thead>
								<tr>
									<th>会员</th>
									<th>级别</th>
									<th>总额</th>
									<th>详情</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${lists}" var="bean">
								<tr>
									<td>${bean.no}</td>
									<td>
									<c:choose>
									<c:when test="${bean.agentLevel == '1'}">省代理</c:when>
									<c:when test="${bean.agentLevel == '2'}">市代理</c:when>
									<c:when test="${bean.agentLevel == '3'}">区代理</c:when>
									</c:choose>
									</td>
									<td>${bean.remark}</td>
									<td><a href="${ctx }/shop/shop!backreordInfo.action?id=${bean._id}">查看详情</a></td>
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
