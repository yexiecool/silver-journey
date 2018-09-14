<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webcom/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/webcom/meta.jsp" %>
    <%@include file="/webcom/bracket.jsp" %>
    <%@include file="/webcom/jquery.validate_js.jsp" %>
    <script type="text/javascript" src="${contextPath}/mvccol/color/jscolor.js"></script>
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
       
       function huifu(oid){
    	   ps_show('inszc');
       }
       
       
    
    </script>
</head>
<body>
<section>
    <%@include file="/webcom/header-bracket.jsp" %>
    <div class="mainpanel">
        <%@include file="/webcom/header-headerbar.jsp" %>
        <form id="custinfoForm" name="custinfoForm" method="post" action="${contextPath}/shop/orderform!orderDetailsById.action?orderId=${orderId}">
              <div class="pageheader">
                <h2><i class="fa"></i>订单详情<span>商品列表</span></h2>
                <div class="breadcrumb-wrapper1">
                    <div class="input-group ">
                     
                    </div>
                </div>
            </div>
               <div class="panelss ">
                <div class="panel-body fu10">
                    <div class="row-pad-5">
                        <div class="form-group col-sm-1d">
                            <input type="text" name="title" value="${title }" placeholder="商品名称" class="form-control"/>
                        </div>
                        <a href="javascript:page_submit(-1);" class="btn btn-primary">搜&nbsp;&nbsp;索</a>
                         
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-striped table-action table-primary mb30">
                                <thead>
                                <tr>
                                    <th class="table-action">商品名称</th>
                                    <th class="table-action">单价</th>
                                    <th class="table-action">数量</th>
                                    <th class="table-action">规格</th>
                                    <th class="table-action">是否异常</th>
                                    <th class="table-action">订单详情状态</th>
                                    <th class="table-action">快递名称</th>
                                    <th class="table-action">快递单号</th>
                                    <th class="table-action">所属店铺</th>
                                    <th class="table-action">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="bean">
                                    <tr>
                                        <td>${bean.pro.ptitle}</td>
                                        <td>${bean.pro.price}</td>
                                        <td>${bean.count}</td>
                                        <td>${bean.spec}</td>
                                        <td>
                                        <c:if test="${bean.state==0}">否</c:if>
                                        <c:if test="${bean.state!=0}"><span style="color:#F00">是</span></c:if>
                                        </td>
                                        <td>
                                        <c:if test="${bean.goodstate==1}">已下单</c:if>
                                        <c:if test="${bean.goodstate==2}">已付款</c:if>
                                        <c:if test="${bean.goodstate==3}">已发货</c:if>
                                        <c:if test="${bean.goodstate==4}">确认收货</c:if>
                                       <%--  <c:if test="${bean.state==5}"></c:if> --%>
                                        </td>
                                        <td>${bean.kdname}</td>
                                        <td>${bean.kdno}</td>
                                        <td>${bean.shop.name}</td>
                                        <td class="table-action">
                                            <div class="btn-group1">
                                            <c:if test="${bean.state==0}">
                                                <a data-toggle="dropdown" class="dropdown-toggle">
                                                    <i class="fa fa-cog"></i>
                                                </a>
                                                
                                                <ul role="menu" class="dropdown-menu pull-right">
                                                   
                                                    <li><a href="${ctx}/shop/orderform!ordercom.action?gid=${bean.pro._id}">
                                                        <i class="fa fa-pencil "></i>&nbsp;&nbsp;&nbsp;&nbsp;评论列表</a>
                                                    </li>  
                                                    <%-- <li><a href="javascript:huifu('${bean._id}',2);">
                                                        <i class="fa fa-pencil "></i>&nbsp;&nbsp;&nbsp;&nbsp;回复</a>
                                                    </li>  --%>
                                                </ul>
                                                </c:if> 
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                             <%@include file="/webcom/bracket-page.jsp" %>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
<%--弹出层新--%>
<div class="fullscreen bg-hei-8 display-none" id="inszc" style="height: 100%;">
    <div style="padding-top:2%">
        <div class="pl-10 pr-10 maring-a cmp500"
             style="width: 100%;max-width: 500px;min-width: 320px;margin: 0px auto;right: 0px;">
            <div class=" bg-bai border-radius3 overflow-hidden">
                <div class="overflow-hidden line-height40 bg-bai line-bottom">
                    <div class="hang50 pull-left zi-hui-tq">
                        <i class="weight500 size14 pl-10 line-height50">货币新增</i>
                    </div>
                    <a href="javascript:ps_hide('inszc')">
                        <div class="hang40 pull-right zi-hui-tq">
                            <i class="weight500 size14 pl-10 pr-10 fa-1dx fa fa-remove" style="line-height: 50px;"></i>
                        </div>
                    </a>
                </div>
                <form id="inscxForm" action="${ctx }/integral/intecurrency!save.action?fypage=${fypage}"
                      class="form-horizontal" method="post">
                    <input type="hidden" id="_id" name="_id"/>
                    <%--row--%>

                    <div class="pt-15 pl-15 pr-15 overflow-auto" style="height:490px;">

                        <div class="col-sm-12">
                            <div class="mb-20">
                                <label class="control-label">回复内容：</label>
                                <textarea rows="10" cols="" class="form-control hang40" placeholder="请输入" id="sj_huifu"></textarea>
                            </div>
                        </div>
                      
                        
                    </div>
                    <div class="div-group-10 line-top" style="padding-left: 40px; padding-right: 40px;">
                        <button class="btn btn-primary width-10 maring-a clear weight500 hang40">提 交
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<%@include file="/webcom/cut-img.jsp" %>
<%@ include file="/webcom/preview.jsp" %>  
<script type="text/javascript"> 
if('${state}'!=0){
$('#state').val('${state}'); 
} 
  
</script>
</body>
</html>
