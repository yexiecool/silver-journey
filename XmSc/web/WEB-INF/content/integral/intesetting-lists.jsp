<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webcom/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/webcom/meta.jsp" %>
    <%@include file="/webcom/bracket.jsp" %>
    <%@include file="/webcom/jquery.validate_js.jsp" %>
    <script src="${contextPath}/bracket/js/jquery.tagsinput.min.js"></script>
    <link rel="stylesheet" href="${contextPath}/bracket/css/jquery.tagsinput.css"/>
    <script src="${contextPath}/UserInterface/My97DatePicker/WdatePicker.js" type="text/javascript"></script> 
    <link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/app/css/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript">
        $(document).ready(function () {
            /* var validator = $("#custinfoForm").validate({
                rules: { 
                },
                messages: {},
                highlight: function (element) {
                    jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                },
                success: function (element) {
                    jQuery(element).closest('.form-group').removeClass('has-error');
                }
            }); */
        });
       
    </script>
</head>
<body>
<section>
    <%@include file="/webcom/header-bracket.jsp" %>
    <div class="mainpanel">
        <%@include file="/webcom/header-headerbar.jsp" %> 
        <div class="pageheader">
            <h2><i class="fa fa-user"></i>系统设置 <span>账户预览</span></h2>
        </div>
       	<div class="div-group-10 overflow-hidden">
            <div class="overflow-hidden">
                <div class=" div-group-10 pb-25 bg-bai border-radius5 overflow-hidden">
                    <div class="clear pt-25 pr-10">
                    	<h3>商城:</h3>
                        <div class="col-2 pl-10">
                            <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">交易所转入商城:</div>
                            <div class="line-bottom line-right line-left1 hang40">
                                <input class="width-10 size14 zi-hui hang30 pt-10 pl-10 pr-10 weight100" type="text"
                                       value="${JYSCZ}" name="name" id="name" placeholder="请输入">
                            </div>
                        </div>
                         <div class="col-2 pl-10">
                            <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">下单支出:</div>
                            <div class="line-bottom line-right line-left1 hang40">
                                <input class="width-10 size14 zi-hui hang30 pt-10 pl-10 pr-10 weight100" type="text"
                                       value="${shopJfdh}" name="num" id="num" placeholder="请输入">
                            </div>
                        </div>
                        <div class="col-2 pl-10">
                            <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">商城转出交易所:</div>
                            <div class="line-bottom line-right line-left1 hang40">
                                <input class="width-10 size14 zi-hui hang30 pt-10 pl-10 pr-10 weight100" type="text"
                                       value="${shopTx}"  name="remark" id="remark" placeholder="请输入">
                            </div>
                        </div>
                        <div class="col-2 pl-10">
                            <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">商城剩余:</div>
                            <div class="line-bottom line-right line-left1 hang40">
                                <input class="width-10 size14 zi-hui hang30 pt-10 pl-10 pr-10 weight100" type="text"
                                       value="${scsy}" name="names" id="names" placeholder="请输入">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
      	</div>
      
        <div class="div-group-10 overflow-hidden">
            <div class="overflow-hidden">
                <div class=" div-group-10 pb-25 bg-bai border-radius5 overflow-hidden">
                    <div class="clear pt-25 pr-10">
                        <h3>矿机:</h3>
                        <div class="col-2 pl-10">
                            <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">总产币量:</div>
                            <div class="line-bottom line-right line-left1 hang40">
                                <input class="width-10 size14 zi-hui hang30 pt-10 pl-10 pr-10 weight100" type="text"
                                       value="${CBSUM}"
                                       name="any" id="any" placeholder="请输入">
                            </div>
                        </div>
                        <div class="col-2 pl-10">
                            <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">总提比量:</div>
                            <div class="line-bottom line-right line-left1 hang40">
                                <input class="width-10 size14 zi-hui hang30 pt-10 pl-10 pr-10 weight100" type="text"
                                       value="${kjTx}"
                                       name="direct" id="direct" placeholder="请输入">
                            </div>
                        </div>
                        <div class="col-2 pl-10">
                            <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">剩余量:</div>
                            <div class="line-bottom line-right line-left1 hang40">
                                <input class="width-10 size14 zi-hui hang30 pt-10 pl-10 pr-10 weight100" type="text"
                                       value="${CBSUM-kjTx}"
                                       name="between" id="between" placeholder="请输入">
                            </div>
                        </div>
                        
                        <div class="pt-25 clear">
                       
                        
                        </div>
                    </div>
                </div>
            </div>
      </div>
      
       <div class="div-group-10 overflow-hidden">
            <div class="overflow-hidden">
                <div class=" div-group-10 pb-25 bg-bai border-radius5 overflow-hidden">
                    <div class="clear pt-25 pr-10">
                        <div class="col-2 pl-10">
                            <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">佣金账户:</div>
                            <div class="line-bottom line-right line-left1 hang40">
                                <input class="width-10 size14 zi-hui hang30 pt-10 pl-10 pr-10 weight100" type="text"
                                       value="${YJSUM}"
                                       name="returnProvince" id="returnProvince" placeholder="请输入">
                            </div>
                        </div>
                         
                         <div class="pt-25 clear">
                        <div class="col-2 pl-10">
                            <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">消费账户:</div>
                            <div class="line-bottom line-right line-left1 hang40">
                                <input class="width-10 size14 zi-hui hang30 pt-10 pl-10 pr-10 weight100" type="text"
                                       value="${XFZHSUM}"
                                       name="returnCounty" id="returnCounty" placeholder="请输入">
                            </div>
                        </div>
                        <div class="pt-25 clear">
                        <div class="col-2 pl-10">
                            <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">LLB总量:</div>
                            <div class="line-bottom line-right line-left1 hang40">
                                <input class="width-10 size14 zi-hui hang30 pt-10 pl-10 pr-10 weight100" type="text"
                                       value="${LLBSUM}"
                                       name="returnCounty" id="returnCounty" placeholder="请输入">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
      </div>
  </div> 
</section> 
<script type="text/javascript"> 
  
 function checksubmit() {  
  $('#custinfoForm').submit();
 }  
</script>
</body>
</html>
