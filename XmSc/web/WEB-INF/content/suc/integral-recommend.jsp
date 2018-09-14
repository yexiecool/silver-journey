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
 
function del(id) {
	if(confirm('确实要删除吗?')) {
		location.href = "${ctx}/suc/integral!delete.action?_id="+ id;
	}		
}
function add(){
	$('#_id').val('');
	$('#title').val('');
	$('#summary').val('');
    $('#uploadresultFour').val('');
	$('#sort').val(0);
	$('#url').val('');
	 

	$('#insadd').modal({
			show : true
		});
}
function qrsk(id){
   var submitData = {
		oid : id
	};
	$.post('${ctx}/shop/orderform!qrsk.action', submitData, function(json) {
		 if(json.state==0){
			 alert("操作成功！");
		 }else{
			 alert("操作失败！")  
		 }

	}, "json")
	  
}
 
function page_submit(num){
	
	if(num==-1){
		$("#fypage").val(0);	
	}else if(num==-2){
		$("#fypage").val($("#fypage").val()-1);	
	}else{
		$("#fypage").val(num);	
	}

	$("#custinfoForm").submit();
}
function share(url) {
	window.open("${contextPath}/weixin/share.action?method="+ encodeURIComponent(url));
}

function exp() {
	var sel_state=$("#sel_state").val();
	var sel_type="tj_account";
	var sel_insdate=$("#sel_insdate").val();
	var sel_enddate=$("#sel_enddate").val(); 
	location.href = "${ctx }/suc/integral!integeralfromexp.action?comid=${comid}&sel_state="+sel_state+"&sel_insdate="+sel_insdate+"&sel_enddate="+sel_enddate+"&sel_type="+sel_type;
	
}

</script>
</head>

<body>

<section>

  <%@include file="/webcom/header-bracket.jsp"%>

  <div class="mainpanel">
	<%@include file="/webcom/header-headerbar.jsp"%>
    
	<form  id="custinfoForm" name="custinfoForm" method="post"  action="${contextPath}/suc/integral!recommend.action" >
    
    <div class="pageheader">
      <h2><i class="fa fa-user"></i>系统管理 <span>推荐收益</span><span>总数：${sum }</span></h2>
      
      <div class="breadcrumb-wrapper1">
          <div class="input-group ">
              <div style="border-radius:3px; height:40px;padding-left:10px;" class="btn-primary">
                  <a href="javascript:exp();"style="color: #ffffff;line-height:25px;">
                      导出&nbsp;<i class="fa fa-mail-reply-all"style="line-height:30px;font-size: 14px;"></i>
                  </a>
              </div>
          </div>
      </div>
      
    </div>
    
   <div class="panelss ">
                <div class="panel-body fu10">
                    <div class="row-pad-5">
                      
			            <div class="form-group col-sm-2">
			            	 <input type="text" id="vip_no" name="vip_no" value="${vip_no}" class="form-control" placeholder="会员编号"/>
			            </div>
			            <div class="form-group col-sm-2">
			                 <input type="text" id="sel_insdate" name="sel_insdate" value="${sel_insdate}" placeholder="开始日期"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="form-control" />
			            </div>
			             <div class="form-group col-sm-2">
			                 <input type="text" id="sel_enddate" name="sel_enddate" value="${sel_enddate}" placeholder="结束日期"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="form-control" />
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
                      	<th>会员级别</th>
                        <th>会员号</th>
                      	<th>状态</th>
                      	<th>类型</th>
                      	<th>时间</th>
                      	<th>备注</th> 
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${integralList}" var="bean">
                      <tr>
                      	<c:if test="${bean.level==0}">
                      	<td>普通会员</td>
                      	</c:if>
                      	<c:if test="${bean.level==1}">
                      	<td>省级代理</td>
                      	</c:if>
                      	<c:if test="${bean.level==2}">
                      	<td>市级代理</td>
                      	</c:if>
                      	<c:if test="${bean.level==3}">
                      	<td>区县代理</td>
                      	</c:if>
                      	<c:if test="${bean.level==4}">
                      	<td>报单中心</td>
                      	</c:if>
                      	<td>${bean.vip_no}</td>
                      	<c:if test="${bean.state==0}"><td><span style="color: red;">+${bean.value}</span></td>
                      	</c:if>
                      	<c:if test="${bean.state==1}"><td><span style="color:green;">-${bean.value}</span></td>
                      	</c:if>
                      	<td>
                      	<c:choose>
                      	  <c:when test="${bean.type == 'ps_account'}">开通账户收益</c:when>
                      	  <c:when test="${bean.type == 'tj_account'}">推荐收益</c:when>
                      	  <c:when test="${bean.type == 'ps_recovery'}">回本后待返收益</c:when>
                      	  <c:when test="${bean.type == 'shop_bmzt'}">利润提成</c:when>
                      	  <c:when test="${bean.type == 'shop_jfdh'}">下单支出</c:when>
                      	  <c:when test="${bean.type == 'shop_jfsr'}">订单收益</c:when>
                      	  <c:when test="${bean.type == 'jfcz'}">盼盼币充值</c:when>
                      	  <c:when test="${bean.type == 'kj_tx'}">盼盼币转出</c:when>
                      	  <c:when test="${bean.type == 'pada_add'}">转账收益</c:when>
                      	  <c:when test="${bean.type == 'pada_jian'}">转账支出</c:when>
                      	  <c:when test="${bean.type == 'returngood_add'}">退货入账</c:when>
                      	  <c:when test="${bean.type == 'returngood_jian'}">退货扣款</c:when>
                      	  <c:when test="${bean.type == 'kj_txfh'}">提现失败返还</c:when>
                      	  <c:when test="${bean.type == 'jyscz'}">交易所转入</c:when>
                      	  <c:when test="${bean.type == 'shop_tx'}">商城转出</c:when>
                      	  <c:when test="${bean.type == 'recommend_give'}">小区业绩赠送</c:when>
                      	 <%--  <c:when test="${bean.type == 'shop_tx'}"><c:if test="${bean.state==1}">商城转出</c:if><c:if test="${bean.state==0}">提现失败返还</c:if></c:when> --%>
                      	</c:choose> 
                      	</td>
                      	<td><fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${bean.createdate}'/></td>
                      	<td>${bean.remark}</td>
                      </tr>
                      </c:forEach>

                </table>
                <%@include file="/webcom/bracket-page.jsp"%>
                
            </div>
        </div>
      </div>
		
    </div><!-- contentpanel -->
	</form>
  </div><!-- mainpanel -->
</section>
	<script>
jQuery(".select2").select2({
    width: '100%'
});
$('#sel_state').val('${state}').trigger("change"); 
$('#sel_type').val('${type}').trigger("change"); 
</script>
</body>
</html>