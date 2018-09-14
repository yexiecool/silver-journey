<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webcom/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/webcom/meta.jsp" %>
    <%@include file="/webcom/bracket.jsp" %>
    <script src="${contextPath}/UserInterface/My97DatePicker/WdatePicker.js"
            type="text/javascript"></script>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>        
    <script type="text/javascript">
  
    </script>
    <style>
        .form-group-20 {
            margin-bottom: 20px;
        }

        .select2-container .select2-choice {
            line-height: 28px;
        !important;
        }

        .pr-10 {
            padding-right: 10px;
        }

        .pl-10 {
            padding-left: 10px;
        }

        .gx-xz {
            width: 18px;
            height: 18px;
            line-height: 18px;
            text-align: center;
            color: white;
            border: 1px solid #bbb;
            border-radius: 3px;
        }

        .gx-xz-dj {
            border: 1px solid #428BCA;
            background-color: #428BCA
        }
    </style>
</head>
<body>
<section>
    <%@include file="/webcom/header-bracket.jsp" %>
    <div class="mainpanel">
        <%@include file="/webcom/header-headerbar.jsp" %>
        <form id="custinfoForm" name="custinfoForm" method="post"
              action="${contextPath}/user/user.action">
            <div class="pageheader">
                <h2>
                    <i class="fa fa-user"></i>微管理 <span>用户管理</span>
                </h2>

               
            </div>
            <div class="panelss ">
                <div class="panel-body fu10">
                    <div class="row-pad-5"> 
                        <div class="form-group col-sm-1d">
                            <input type="text" name="wxno" value="${wxno}"
                                   placeholder="会员编号" class="form-control"/>${hbvalue}/${oidvalue}/${kjvalue}
                        </div> 
                        <a href="javascript:page_submit(-1);" class="btn btn-primary">搜&nbsp;&nbsp;索</a>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-striped table-primary mb30">
                                <thead>
                                <tr> 
                                    <th class="th5">名称</th>  
                                    <th class="th8">代理商等级</th>
                                    <th class="th8">会员编号</th>
                                    <th class="th8">推荐会员编号</th>
                                    <th class="th8">时间</th> 
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="bean">
                                    <tr> 
                                        <td>${bean.account}/${bean.nickname}</td> 
                                        <%-- </c:if> --%>
                                        <td>
                                        <c:choose>
										   <c:when test="${bean.agentLevel==1}">省级代理商</c:when>
										   <c:when test="${bean.agentLevel==2}">市级代理商</c:when>
										   <c:when test="${bean.agentLevel==3}">县级代理商</c:when>
										   <c:when test="${bean.agentLevel==4}">部门代理商</c:when>
										   <c:when test="${bean.agentLevel==5 || bean.agentLevel==6}">会员</c:when>
										   <c:otherwise>非代理商</c:otherwise>
										</c:choose>
										</td>
										
										<th class="th8">${bean.no}/${bean.number}</th>
										<td><fmt:formatDate pattern='yyyy-MM-dd HH:mm'
                                                            value='${bean.createdate}'/></td>  
                                      
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
<div id="inszc" class="modal fade bs-example-modal-static"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     data-backdrop="static" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close"
                        type="button">&times;</button>
                <h4 class="modal-title">
                    <i class="fa fa-leaf pr-10"></i>帐号管理
                </h4>
            </div>
            <div class="modal-body">
                <form id="inscxForm" action="${ctx}/user/user!save.action" class="form-horizontal" method="post">
                    <input type="hidden" id="_id" name="_id"/>
                    <input type="hidden" id="funcs" name="funcs"/>
                    <input type="hidden" id="number" name="number"/>
                    <input type="hidden" id="headimgurl" name="headimgurl"/>
                    <input type="hidden" id="fromUser" name="fromUser"/>
                    <input id="agentcountyids" type="hidden"/>
                    <input type="hidden" id="upIds" name="upIds"/>
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">登录帐号</label>
                                <input type="text" id="account" name="account"
                                       class="form-control" placeholder="请输入" onchange="isExist()"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">登录密码</label>
                                <input type="text" id="password" name="password"
                                       class="form-control" placeholder="请输入"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">单位名称:</label>
                                <input type="text" id="nickname" name="nickname"
                                       class="form-control" placeholder="请输入"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">ToUser:</label>
                                <input type="text" id="toUser" name="toUser"
                                       class="form-control" placeholder="请输入"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">角色:</label>
                                <select id="roleid" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="县域管理员" >
                                    <option value="">请选择</option> 
                                    <c:forEach items="${rolelist}" var="bean">
                                        <option value="${bean._id }">${bean.rolename }</option>
                                    </c:forEach>
                                </select>
                                <label class="error" for="roleid"></label>
                            </div>
                           
                        </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">类型:</label>
                                <select id="type" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="管理员" onchange="selrole()">
                                    <option value="">请选择</option>
                                    <option value="1">普通用户</option>
                                    <option value="2">管理员</option>
                                </select>
                                <label class="error" for="type"></label>
                            </div>
                        </div>
                         <div class="col-sm-2" id="agentLevel_show">
                            <div class="form-group-20">
                                <label class="control-label">代理类型:</label>
                                <select id="agentLevel" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="代理类型">
                                    <option value="">请选择</option>
                                    <option value="1">省</option>
                                    <option value="2">市</option>
                                    <option value="3">县</option>
                                    <option value="4">报单中心</option>
                                </select>
                                <label class="error" for="agentLevel"></label>
                            </div>
                        </div>  
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">代理省份:</label>
                               <!--  <input type="text" id="province" name="province"
                                       class="form-control" placeholder="请输入"/> -->
                                <select id="agentprovinceid" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="请选择" >
                                    <option value="">请选择</option> 
                                    <c:forEach items="${arealist}" var="bean">
                                        <option value="${bean._id}" ops="${bean.area}">${bean.area}</option>
                                    </c:forEach>
                                </select>
                       </div>
                        </div> 
                         <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">代理市:</label>
                                <!-- <input type="text" id="city" name="city"
                                       class="form-control" placeholder="请输入"/> -->
                             <select id="agentcityid" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="请选择" >
                                    
                                </select>
                       </div>
                      </div> 
                       <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">代理县:</label>
                               <!--  <input type="text" id="county" name="county"
                                       class="form-control" placeholder="请输入"/> -->
                                <select id="agentcountyid" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="请选择" >
                                    
                                </select>
                       </div>
                      </div>   
                    <div class="row"> 
                        <div class="col-md-12">
                            <label class="control-label pr-10" style="padding-bottom:10px;!important;">个人中心菜单配置:</label>
                            <div>
                                <c:forEach items="${fromfunc}" var="bean" varStatus="status">
                                    <div class="pull-left pr-10 form-group-20">
                                        <div id="func-${bean._id}" class="pull-left gx-xz ch_type" onclick="cke(this)">
                                            <input type="hidden" value="${bean._id}"/>
                                            <i class="fa fa-check"></i>
                                        </div>
                                        <div class="pull-left pl-10">${bean.title}</div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-md-2"> 
                             <div class="form-group-20">
                                <label class="control-label">个人中心模板:</label>
                                <select id="mb" name="mb" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="管理员">
                                    <option value="1">普通模板</option>
                                    <option value="2">分销模板</option>
                                </select>
                                <label class="error" for="type"></label>
                            </div>
                        </div>
                           <div class="col-md-2"> 
                             <div class="form-group-20">
                                <label class="control-label">推荐码:</label>
                                <input type="text" id="renumber" name="renumber"
                                       class="form-control" placeholder="请输入"/>
                                <label class="error" for="renumber"></label>
                            </div>
                        </div>
                    </div> 
                </form>
                <div class="panel-footer">
                    <button class="btn btn-primary col-sm-2" onclick="submint()" style="padding: 9px 15px;">提交</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 审核 -->
<div id="inszc1" class="modal fade bs-example-modal-static"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     data-backdrop="static" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close"
                        type="button">&times;</button>
                <h4 class="modal-title">
                    <i class="fa fa-leaf pr-10"></i>帐号管理
                </h4>
            </div>
            <div class="modal-body">
                <form id="inscxForm" action="${ctx}/user/user!save.action" class="form-horizontal" method="post">
                    <input type="hidden" id="_id1" name="_id1"/>
                    <input type="hidden" id="funcs1" name="funcs1"/>
                    <input type="hidden" id="number1" name="number1"/>
                    
                    <input type="hidden" id="upIds1" name="upIds1"/>
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">登录帐号</label>
                                <input type="text" id="account1" name="account1"
                                       class="form-control" placeholder="请输入" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">登录密码</label>
                                <input type="text" id="password1" name="password1"
                                       class="form-control" placeholder="请输入" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">单位名称:</label>
                                <input type="text" id="nickname1" name="nickname1"
                                       class="form-control" placeholder="请输入" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">ToUser:</label>
                                <input type="text" id="toUser1" name="toUser1"
                                       class="form-control" placeholder="请输入" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">角色:</label>
                                <select id="roleid1" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="县域管理员" disabled="disabled">
                                    <option value="">请选择</option> 
                                    <c:forEach items="${rolelist}" var="bean">
                                        <option value="${bean._id }">${bean.rolename }</option>
                                    </c:forEach>
                                </select>
                                <label class="error" for="roleid"></label>
                            </div>
                           
                        </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">类型:</label>
                                <select id="type1" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="管理员" onchange="selrole()" disabled="disabled">
                                    <option value="">请选择</option>
                                    <option value="1">普通用户</option>
                                    <option value="2">管理员</option>
                                </select>
                                <label class="error" for="type"></label>
                            </div>
                        </div>
                         <div class="col-sm-2" id="agentLevel_show1">
                            <div class="form-group-20">
                                <label class="control-label">代理类型:</label>
                                <select id="agentLevel1" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="代理类型" disabled="disabled">
                                    <option value="">请选择</option>
                                    <option value="1">省</option>
                                    <option value="2">市</option>
                                    <option value="3">县</option>
                                    <option value="4">部门</option>
                                </select>
                                <label class="error" for="agentLevel"></label>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">省份:</label>
                                <input type="text" id="province1" name="province1"
                                       class="form-control" placeholder="请输入" readonly="readonly"/>
                       </div>
                        </div> 
                         <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">区县:</label>
                                <input type="text" id="city1" name="city1"
                                       class="form-control" placeholder="请输入" readonly="readonly"/>
                       </div>
                      </div>   
                    <div class="row"> 
                        <div class="col-md-12">
                            <label class="control-label pr-10" style="padding-bottom:10px;!important;">个人中心菜单配置:</label>
                            <div>
                                <c:forEach items="${fromfunc}" var="bean" varStatus="status">
                                    <div class="pull-left pr-10 form-group-20">
                                        <div id="func-${bean._id}1" class="pull-left gx-xz ch_type" onclick="cke(this)">
                                            <input type="hidden" value="${bean._id}"/>
                                            <i class="fa fa-check"></i>
                                        </div>
                                        <div class="pull-left pl-10">${bean.title}</div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-md-2"> 
                             <div class="form-group-20">
                                <label class="control-label">个人中心模板:</label>
                                <select id="mb1" name="mb1" class="select2 form-control" style="line-height: 28px!important;"
                                        required data-placeholder="管理员" disabled="disabled">
                                    <option value="1">普通模板</option>
                                    <option value="2">分销模板</option>
                                </select>
                                <label class="error" for="type"></label>
                            </div>
                        </div>
                           <div class="col-md-2"> 
                             <div class="form-group-20">
                                <label class="control-label">推荐码:</label>
                                <input type="text" id="renumber1" name="renumber1"
                                       class="form-control" placeholder="请输入"/>
                                <label class="error" for="renumber"></label>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">真实姓名</label>
                                <input type="text" id="username" name="username"
                                       class="form-control" placeholder="请输入" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">身份证号码</label>
                                <input type="text" id="id_card" name="id_card"
                                       class="form-control" placeholder="请输入" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">公司名称</label>
                                <input type="text" id="company_name" name="company_name"
                                       class="form-control" placeholder="请输入" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">营业证号码:</label>
                                <input type="text" id="lisense_number" name="lisense_number"
                                       class="form-control" placeholder="请输入" readonly="readonly"/>
                            </div>
                        </div>
                     </div>
                     
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">身份证正面照</label>
                                <img src="" width="140px;" height="80px;" id="id_card_front"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">身份证反面照</label>
                                <img src="" width="140px;" height="80px;" id="id_card_reverse"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group-20">
                                <label class="control-label">营业证照片:</label>
                                <img src="" width="140px;" height="80px;" id="lisense_photo"/>
                            </div>
                        </div>
                     </div> 
                </form>
                <div class="panel-footer">
	                <button class="btn btn-primary col-sm-2" onclick="submint_audit(1)" style="padding: 9px 15px;float:left;margin-left:10px;">通过</button>
	                <button class="btn btn-primary col-sm-2" onclick="submint_audit(2)" style="padding: 9px 15px;float:left;margin-left:50px;">不通过</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
		$('#agentprovinceid').change(function(){ 
			var id=$("#agentprovinceid").val();
			//alert(id);
			var ops=$("#agentprovinceid").attr("ops");
			 
			/* alert(); */
			if(isNaN(id)||id==null||id==''){
				$("#agentcityid").html("<option  value=''>请选择</option>"); 
				$("#agentcountyid").html("<option  value=''>请选择</option>"); 
			}else{ 
				getchild(id); 	
			}
			
			
		});
		$('#agentcityid').change(function(){ 
			var id=$("#agentcityid").val();
			if(isNaN(id)||id==null||id==''){
				$("#agentcountyid").html("<option  value=''>请选择</option>"); 
			}else{ 
				getminchild(id); 	
			}
			
			
		});

</script>
</body>
</html>
