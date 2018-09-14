<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webcom/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/webcom/meta.jsp" %>
    <%@include file="/webcom/bracket.jsp" %>
    <%@include file="/webcom/jquery.validate_js.jsp" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <script type="text/javascript" src="${contextPath}/mvccol/color/jscolor.js"></script>
    <script type="text/javascript">
      
        function del(id) {
            if (confirm('确实要删除吗?')) {
                location.href = "${ctx}/integral/intecurrency!delete.action?fypage=${fypage}&wid=${wid}&_id=" + id;
            }
        }
        
       /*  function set(id,tag) {
            var submitData = {
                id: id,
                state:tag
            };
            $.post('${ctx}/shop/shopagent!ajaxset.action', submitData,
                    function (json) {
                        if (json.state == 0) {
                            window.location.reload();
                        }
                    }, "json");
        } */
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
       function add() {
           $('#_id').val('');
           $('#name').val('');
           $('#remark').val('');
           ps_show('inszc');
       }
       
       function upd(id) {
           var submitData = {
               _id: id
           };
           $.post('${ctx}/integral/intecurrency!upd.action', submitData,
                   function (json) {
                       $('#_id').val(json._id);
                       $('#name').val(json.name);
                       $('#remark').val(json.remark);

                   }, "json")
           ps_show('inszc');
       }
    </script>
</head>
<body>
<section>
    <%@include file="/webcom/header-bracket.jsp" %>
    <div class="mainpanel">
        <%@include file="/webcom/header-headerbar.jsp" %>
        <form id="custinfoForm" name="custinfoForm" method="post" action="${contextPath}/integral/intecurrency.action?">
              <div class="pageheader">
                <h2><i class="fa"></i>货币设置<span>货币列表</span></h2>
                <div class="breadcrumb-wrapper1">
                    <div class="input-group ">
                     
                    </div>
                </div>
            </div>
               <div class="panelss ">
                <div class="panel-body fu10">
                    <div class="row-pad-5">
                        <div class="form-group col-sm-1d">
                            <input type="text" name="title" value="${title }" placeholder="货币名称" class="form-control"/>
                        </div>
                        <a href="javascript:page_submit(-1);" class="btn btn-primary">搜&nbsp;&nbsp;索</a>
                         <div class="form-group col-sm-1d pull-right"> 
                         <sec:authorize ifAnyGranted="ROLE_123">
                         <button type="button" onclick="add()" class="btn btn-primary dropdown-toggle form-group pull-right" data-toggle="dropdown">
                                                                                 添加货币&nbsp; <i  class="fa fa-plus"></i>
                         </button>
                          </sec:authorize>
                      </div>
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
                                    <th class="table-action">货币名称</th>
                                    <th class="table-action">备注</th>
                                    <th class="table-action">申请时间</th>
                                    <th class="table-action">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="bean">
                                    <tr>
                                        <td>${bean.name}</td>
                                        <td>${bean.remark}</td>
                                        <td><fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${bean.createdate}'/></td>
                                        <td class="table-action">
                                            <div class="btn-group1">
                                                <a data-toggle="dropdown" class="dropdown-toggle">
                                                    <i class="fa fa-cog"></i>
                                                </a>
                                                <ul role="menu" class="dropdown-menu pull-right">
                                                <sec:authorize ifAnyGranted="ROLE_124">
                                                    <li><a href="javascript:upd('${bean._id}',2);">
                                                        <i class="fa fa-pencil "></i>&nbsp;&nbsp;&nbsp;&nbsp;修改</a>
                                                    </li>
                                                    </sec:authorize>
                                                    <sec:authorize ifAnyGranted="ROLE_125">
                                                    <li><a href="javascript:del('${bean._id}');">
                                                        <i class="fa fa-trash-o "></i>&nbsp;&nbsp;&nbsp;&nbsp;删除</a>
                                                    </li>
                                                    </sec:authorize>
                                                </ul>
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

                        <div class="col-sm-6">
                            <div class="mb-20">
                                <label class="control-label">货币名称：</label>
                                <input type="text" id="name" name="name"
                                       class="form-control hang40" placeholder="请输入"/>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="mb-20">
                                <label class="control-label">备注：</label>
                                <input type="text" id="remark" name="remark"
                                       class="form-control hang40" placeholder="请输入"/>
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
