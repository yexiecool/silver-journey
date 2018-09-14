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
        $(document).ready(function () {
            var validator = $("#inscxForm").validate({
                rules: {
                	account: {
                        required: true
                    },
                    cid: {
                        required: true
                    },
                    mb: {
                        required: true
                    }
                },
                messages: {},
                highlight: function (element) {
                    jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                },
                success: function (element) {
                    jQuery(element).closest('.form-group').removeClass('has-error');
                }
            });
        });
        function del(id) {
            if (confirm('确实要删除吗?')) {
                location.href = "${ctx}/integral/inteaccount!delete.action?fypage=${fypage}&_id=" + id;
            }
        }
       
        function add() {
        	
        	$.post('${ctx}/integral/inteaccount!selintecurrency.action',
                    function (json) {
		        		var mess=json.list;
		        		 $("#mb").html('');
		        		 var html='';  
			        		for(var i=0;i<mess.length;i++){
			        			html+="<option  value='"+ mess[i]['_id']+"'>"+ mess[i]['name']+"</option>";
							}
			        		
		        		$("#mb").append(html); 

              }, "json")
        	
            $('#_id').val('');
            $('#account').val('');
            $('#remark').val('');
            ps_show('inszc');
        }
        function upd(id) {
        	$.post('${ctx}/integral/inteaccount!selintecurrency.action',
                    function (json) {
		        		var mess=json.list;
		        		 $("#cid").html('');
		        		 var html='';  
			        		for(var i=0;i<mess.length;i++){
			        			html+="<option  value='"+ mess[i]['_id']+"'>"+ mess[i]['name']+"</option>";
							}
			        		
		        		$("#cid").append(html); 

              }, "json")
              
            var submitData = {
                _id: id
            };
            $.post('${ctx}/integral/inteaccount!upd.action', submitData,
                    function (json) {
                        $('#_id').val(json._id);
                        $('#account').val(json.account);
                        $('#remark').val(json.remark);
                        $('#cid').val(json.cname);
                    }, "json")
            ps_show('inszc');
        }
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
       function saveState(){
    	   var submitData = {
                   _id: $('#updstate_id').val(),
                   state: $('#updstate_state').val()
           };
               $.post('${ctx}/integral/prostore!saveState.action', submitData,
                       function (json) {
            	   page_submit(-2)
                       }, "json")
       }
       function updState(id){
    	   var submitData = {
                   _id: id
           };
           $.post('${ctx}/integral/prostore!upd.action', submitData,
            	function (json) {
                	$('#updstate_id').val(json._id);
                    $('#updstate_state').val(json.state);
                    ps_show('ins_state');
           }, "json")
       }
       function updkjmoney(id){
    	    
      		$('#updmoney_id').val(id);
            ps_show('update_kj_money');
            
       }
       
       function savekjmoney(){
    	   var submitData = {
                   _id: $('#updmoney_id').val(),
                   money: $('#kjmoney').val()
           };
               $.post('${ctx}/integral/prostore!savekjmoney.action', submitData,
                       function (json) {
            	   page_submit(-2)
                       }, "json")
       }
       
      
    </script>
</head>
<body>
<section>
    <%@include file="/webcom/header-bracket.jsp" %>
    <div class="mainpanel">
        <%@include file="/webcom/header-headerbar.jsp" %>
        <form id="custinfoForm" name="custinfoForm" method="post" action="${contextPath}/integral/prostore.action">
            <div class="pageheader">
                <h2><i class="fa fa-user"></i> 账户管理 <span>账户列表</span></h2>
            </div>
             <div class="panelss ">
                <div class="panel-body fu10">
                    <div class="row-pad-5">
                         
                        <div class="form-group col-sm-1d">
                            <input type="text" name="wxno" value="${wxno}"
                                   placeholder="会员编号" class="form-control"/>
                        </div> 
                        <a href="javascript:page_submit(-1);" class="btn btn-primary">搜&nbsp;&nbsp;索</a>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-striped table-action table-primary mb30 table-bordered">
                                <thead>
                                <tr>
                                    <th class="table-action">账户</th>
                                    <th class="table-action">类型</th>
                                    <th class="table-action">预送
                                 <c:choose>
                                    <c:when test="${jfname!=null}">${jfname}</c:when>
                                   <c:otherwise>金币</c:otherwise>
                                 </c:choose>   
                                                                                         数量</th>
                                    <th class="table-action">预送状态</th>
                                    <th class="table-action">添加时间</th>
                                    <th class="table-action">截止时间</th>
                                    <th class="table-action">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="bean">
                                    <tr>
                                        <td>${bean.vip_no}</td>
                                        <td><c:if test="${bean.type=='recommend_give'}">业绩赠送</c:if>
	                                        <c:if test="${bean.type=='ps_account'}">开通账户</c:if>
	                                        <c:if test="${bean.type=='ps_recovery'}">初次回本</c:if>
	                                        <c:if test="${bean.type=='shop_bmzt'}">利润提成</c:if>
	                                        <c:if test="${bean.type=='shop_exchange'}">消费兑换</c:if>
                                        </td>
                                        <td>${bean.money}</td>
                                        <td>
                                        	<c:if test="${bean.state==0 }">返金额中</c:if>
                                        	<c:if test="${bean.state==1 }">已返完</c:if>
                                        	<c:if test="${bean.state==2 }">冻结</c:if>
				                      	
				                      	</td>
                                        <td><fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${bean.createdate}'/></td>
                                         <td><fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${bean.enddate}'/></td>
                                        <td class="table-action">
                                            <div class="btn-group1">
                                                <a data-toggle="dropdown" class="dropdown-toggle">
                                                    <i class="fa fa-cog"></i>
                                                </a>
                                                <ul role="menu" class="dropdown-menu pull-right">
                                                   <%--  <li><a href="javascript:resure('${bean._id}');">
                                                        <i class="fa fa-pencil "></i>&nbsp;&nbsp;&nbsp;&nbsp;确定回本</a>
                                                    </li> --%> 
                                                    <sec:authorize ifAnyGranted="ROLE_121">
                                                    <li><a href="${ctx}/integral/miners!pcdetail.action?id=${bean._id}">
                                                        <i class="fa fa-pencil "></i>&nbsp;&nbsp;&nbsp;&nbsp;矿机详情</a>
                                                    </li>
                                                    </sec:authorize>
                                                    <sec:authorize ifAnyGranted="ROLE_122">
                                                    <li><a href="javascript:updState('${bean._id}');">
                                                        <i class="fa fa-pencil "></i>&nbsp;&nbsp;&nbsp;&nbsp;修改状态</a>
                                                    </li> 
                                                    </sec:authorize>
                                                    <sec:authorize ifAnyGranted="ROLE_122"> 
                                                    <li><a href="javascript:updkjmoney('${bean._id}');">
                                                        <i class="fa fa-pencil "></i>&nbsp;&nbsp;&nbsp;&nbsp;修改金额</a>
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
                        <i class="weight500 size14 pl-10 line-height50">店铺管理</i>
                    </div>
                    <a href="javascript:ps_hide('inszc')">
                        <div class="hang40 pull-right zi-hui-tq">
                            <i class="weight500 size14 pl-10 pr-10 fa-1dx fa fa-remove" style="line-height: 50px;"></i>
                        </div>
                    </a>
                </div>
                <form id="inscxForm" action="${ctx }/integral/inteaccount!save.action?fypage=${fypage}"
                      class="form-horizontal" method="post">
                    <input type="hidden" id="_id" name="_id"/>
                    <%--row--%>

                    <div class="pt-15 pl-15 pr-15 overflow-auto" style="height:490px;">

                        <div class="col-sm-6">
                            <div class="mb-20">
                                <label class="control-label">账户名称：</label>
                                <input type="text" id="account" name="account"
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
                       
                        <div class="col-sm-6">
                            <div class="mb-20">
                                <label class="control-label">币种：</label>
                                <select id="cid" name="cid" class="select2 hang40"
                                        data-placeholder="请选择">
                                    
                                </select>
                                <label class="error" for="color"></label>
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

<div class="fullscreen bg-hei-8 display-none" id="ins_state" style="height: 100%;">
    <div style="padding-top:2%">
        <div class="pl-10 pr-10 maring-a cmp500"
             style="width: 100%;max-width: 500px;min-width: 320px;margin: 0px auto;right: 0px;">
            <div class=" bg-bai border-radius3 overflow-hidden">
                <div class="overflow-hidden line-height40 bg-bai line-bottom">
                    <div class="hang50 pull-left zi-hui-tq">
                        <i class="weight500 size14 pl-10 line-height50">维护矿机状态</i>
                    </div>
                    <a href="javascript:ps_hide('ins_state')">
                        <div class="hang40 pull-right zi-hui-tq">
                            <i class="weight500 size14 pl-10 pr-10 fa-1dx fa fa-remove" style="line-height: 50px;"></i>
                        </div>
                    </a>
                </div>
                <form id="insStateForm" onsubmit="return false" class="form-horizontal" method="post">
                    <input type="hidden" id="updstate_id" name="_id"/>
                    <%--row--%>

                    <div class="pt-15 pl-15 pr-15 overflow-auto" style="height:490px;">
						
                        <div class="col-sm-6">
                            <div class="mb-20">
                                <label class="control-label">状态：</label>
                                <select id="updstate_state" name="state" class="select2 hang40"  data-placeholder="请选择">
                                    <option value="0">返金额中</option>
                                    <option value="1">已返完</option>
                                    <option value="2">冻结</option>
                                   
                                </select>
                                <label class="error" for="color"></label>
                            </div>
                        </div>
                       
                    </div>
                    <div class="div-group-10 line-top" style="padding-left: 40px; padding-right: 40px;">
                        <button class="btn btn-primary width-10 maring-a clear weight500 hang40" onclick="saveState()"/>提 交
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<div class="fullscreen bg-hei-8 display-none" id="update_kj_money" style="height: 100%;">
    <div style="padding-top:2%">
        <div class="pl-10 pr-10 maring-a cmp500"
             style="width: 100%;max-width: 500px;min-width: 320px;margin: 0px auto;right: 0px;">
            <div class=" bg-bai border-radius3 overflow-hidden">
                <div class="overflow-hidden line-height40 bg-bai line-bottom">
                    <div class="hang50 pull-left zi-hui-tq">
                        <i class="weight500 size14 pl-10 line-height50">维护矿机</i>
                    </div>
                    <a href="javascript:ps_hide('update_kj_money')">
                        <div class="hang40 pull-right zi-hui-tq">
                            <i class="weight500 size14 pl-10 pr-10 fa-1dx fa fa-remove" style="line-height: 50px;"></i>
                        </div>
                    </a>
                </div>
                <form id="updatemoneyForm" onsubmit="return false" class="form-horizontal" method="post">
                    <input type="hidden" id="updmoney_id" name="_id"/>
                    <%--row--%>

                    <div class="pt-15 pl-15 pr-15 overflow-auto" style="height:490px;">
						
                        <div class="col-sm-6">
                            <div class="mb-20">
                                <label class="control-label">更改矿机金额：</label> 
                                 <input type="text" id="kjmoney" name="kjmoney"
                                       class="form-control hang40" placeholder="请输入"/>
                                <label class="error" for="color"></label>
                            </div>
                        </div>
                       
                    </div>
                    <div class="div-group-10 line-top" style="padding-left: 40px; padding-right: 40px;">
                        <button class="btn btn-primary width-10 maring-a clear weight500 hang40" onclick="savekjmoney()"/>提 交
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
jQuery(".select2").select2({
    width: '100%'
});
</script>
</body>
</html>
