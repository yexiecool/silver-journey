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
    <script type="text/javascript" src="${ctx}/ckeditor/ckeditor.js"></script> 
    <link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/app/css/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript">

        $(document).ready(function () {
            $("#but1").val("${but1}");


            $("#but2").val("${but2}");

            var validator = $("#custinfoForm").validate({
                rules: {
                    ptitle: {
                        required: true
                    },
                    sort: {
                        number: true
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

        function changebut(value, no) {

            if (value == '货到付款') {
                $("#gm" + no).val("javascript:hd_pay()");
            } else if (value == '微信支付') {
                $("#gm" + no).val("javascript:wx_pay()");
            } else if (value == '电话购买') {
                $("#gm" + no).val("tel:");
            } else if (value == '商城购买') {
                $("#gm" + no).val("");
            } else if (value == '联系卖家') {
                $("#gm" + no).val("tel:");
            } else if (value == '立即下单') {
                $("#gm" + no).val("javascript:lj_pay()");
            } else {
                $("#gm" + no).val("");
            }
        }

        function cke() {
            var str = "";
            var tmp = document.getElementsByName("ch_type");

            for (var i = 0; i < tmp.length; i++) {
                if (tmp[i].checked) {
                    str += tmp[i].value + ","

                }
            }
            $("#hylx").val(str);

        }
        function setcke() {
            var str = "<s:property value='hylx'/>";
            var tmp = str.split(",");

            for (var i = 0; i < tmp.length; i++) {
                $("#" + tmp[i]).attr("checked", "checked");
            }
        }
        ;

        function changebq() {
            if ($('#bq').val() == 8) {
                $('#kj').show();
                $('.tgrs').show()
            } else {
                $('#kj').hide(); 
            }
            if ($('#bq').val() == 9) {
                $('.tg').show();
                $('.tgrs').show();
            } else {
                $('.tg').hide();
            }
        }
        
        var getid;
        var mintypeid;
        function getchild(id) {
        	
            var submitData = {
           		 pid: id
            };
            $.post('${ctx}/shop/shoppro!get.action', submitData,
                    function (json) {
                       if(json.state==0){ 
                       	var list=json.list;
                       	var options="<option  value=''>请选择</option>"; 
                       	for (var i = 0; i < list.length; i++) {	
                       		options+="<option  value="+list[i]._id+">"+list[i].name+"</option>";	
   						}
                          $("#mintypeid").html(options); 
                          if("${entity.typeid}"==$("#typeid").val()){ 
                       	      $("#mintypeid").val("${entity.mintypeid}").trigger("change"); 
                       	      getminchild('${entity.mintypeid}');
                          }else{ 
                       	   $("#mintypeid").val("").trigger("change"); 
                          }
                         
                          
                       }else{
                    	   $("#mintypeid").val("").trigger("change"); 
                    	   $("#mintypeid").html("<option  value=''>暂无数据</option>"); 
                       }
                    }, "json")
                    
        }
        
     function getminchild(id) {
        	
            var submitData = {
           		 pid: id
            };
            $.post('${ctx}/shop/shoppro!get.action', submitData,
                    function (json) {
                       if(json.state==0){ 
                       	var list=json.list;
                       	var options="<option  value=''>请选择</option>"; 
                       	for (var i = 0; i < list.length; i++) {	
                       		options+="<option  value="+list[i]._id+">"+list[i].name+"</option>";	
   						}
                          $("#thirdtypeid").html(options); 
                          if("${entity.mintypeid}"==$("#mintypeid").val()){ 
                       	   $("#thirdtypeid").val("${entity.thirdtypeid}").trigger("change"); 
                          }else{ 
                       	   $("#thirdtypeid").val("").trigger("change"); 
                          }
                         
                          
                       }else{
                    	   $("#thirdtypeid").val("").trigger("change"); 
                    	   $("#thirdtypeid").html("<option  value=''>暂无数据</option>"); 
                       }
                    }, "json")
                    
        }
        function clear(){
         	('#mintypeid').val('').trigger("change");
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

    <%@include file="/webcom/header-bracket.jsp" %>

    <div class="mainpanel">
        <%@include file="/webcom/header-headerbar.jsp" %>
        <div class="pageheader">
            <h2>
                <i class="fa fa-user"></i>微网店<span>产品信息</span>
            </h2>
        </div>
        <div class="row">
            <div class="col-md-12">
                    <input id="_id" name="_id" value="<s:property value='_id'/>" type="hidden"/>
                    <input type="hidden" id="logo" value="<s:property value='logo'/>" name="logo"/>
                    <input type="hidden" id="comid" value="${comid}" name="comid"/>
					<%-- <input type="hidden" id="goodstype" value="${goodstype}" name="goodstype"/> --%>
                    <div class="div-group-10 overflow-hidden">
                        <!--左边项目-->
                        <div class="overflow-hidden">
                            <div class=" div-group-10 pb-25 bg-bai border-radius5 overflow-hidden">
                                <div class="clear col-1 pl-10 overflow-hidden">
                                    <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        商品logo<i class="size12 zi-hui"></i></div>
                                    <div class="line-bottom line-right line-left1 div-group-5 pt-10 pb-10 overflow-hidden">
                                 
                                        <c:if test="${not empty entity.logo}">
                                            <div class="img-wh60 maring-a border-radius3 line-lu logos">
                                                <img src="${filehttp}/${entity.logo}" class="img-60">
                                            </div>
                                    </div>
                                    </c:if> 
                            </div>

                            <div class="clear pt-25">
                                <div class="col-2">
                                    <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        商品名称
                                    </div>
                                    <div class="line-bottom line-right line-left1 hang40 overflow-hidden">
                                        <input class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
                                               type="text"
                                               id="ptitle"
                                               value="${entity.ptitle}"
                                               name="ptitle"
                                               placeholder="商品名称" readonly="readonly">
                                    </div>
                                </div>
                                <div class="col-2 pl-10">
                                    <div class="size14 weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        店铺分类
                                    </div>
                                    <div class="overflow-hidden">
                                        <select id="hylx" name="hylx" class="select2" data-placeholder="请选择" readonly="readonly">
                                            <c:forEach items="${typelist}" var="bean" varStatus="status">
                                                <option value="${bean.type}">${bean.name}</option>
                                            </c:forEach>

                                        </select>
                                    </div>
                                </div>
                                  <div class="col-2 pl-10">
                                    <div class="size14 weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        商品一级分类
                                    </div>
                                    <div class="overflow-hidden">
                                        <select id="typeid" name="typeid" class="select2" data-placeholder="请选择">
                                            <option value="">请选择</option>
                                            <c:forEach items="${protype}" var="bean" varStatus="status">
                                                <option value="${bean._id}">${bean.name}</option>
                                            </c:forEach>

                                        </select>
                                    </div>
                                </div>
                             <div class="col-2 pl-10" id="show_mintype">
                                    <div class="size14 weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        商品二级分类
                                    </div>
                                    <div class="overflow-hidden">
                                         <select id="mintypeid" name="mintypeid" class="select2" data-placeholder="请选择" readonly="readonly">
                                        
                                        </select>
                                    </div>
                                </div>
                             <div class="col-2 pl-10" id="show_thirdtype">
                                    <div class="size14 weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        商品三级分类
                                    </div>
                                    <div class="overflow-hidden">
                                         <select id="thirdtypeid" name="thirdtypeid" class="select2" data-placeholder="请选择" readonly="readonly">
                                        
                                        </select>
                                    </div>
                                </div>
                                <div class="col-2 pl-10">
                                    <div class="size14  weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        模板
                                    </div>
                                    <div class="overflow-hidden">
                                        <select id="mb" name="mb" class="select2" data-placeholder="请选择" readonly="readonly">
                                            <option value="0">默认模板</option>
                                        </select>
                                    </div>
                                </div>
                              


                            <div class="pt-25 clear">
                              <div class="col-2 pl-10">
                                    <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        序号
                                    </div>
                                    <div class="line-bottom line-right line-left1 hang40 overflow-hidden">
                                        <input class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
                                               type="text"
                                               id="sort" value="${entity.sort}" name="sort"
                                               placeholder="请输入序号" readonly="readonly">
                                    </div>
                                </div>
                               <div class="col-2 pl-10">
                                    <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        分享说明
                                    </div>
                                    <div class="line-bottom line-right line-left1 hang40 overflow-hidden">
                                        <input class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
                                               type="text"
                                               id="summary" value="${entity.summary}" name="summary"
                                               placeholder="请输入分享说明" readonly="readonly">
                                    </div>
                                </div>
                                <div class="col-2 pl-10">
                                    <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        外部链接
                                    </div>
                                    <div class="line-bottom line-right line-left1 hang40 overflow-hidden">
                                        <input class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
                                               type="text"
                                               id="url" value="${entity.url}" name="url" placeholder="外部链接" readonly="readonly">
                                    </div>
                                </div>
                            </div>
                               <div class="col-2 pl-10">
                                    <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">
                         成本价格      
                                    </div>
                                    <div class="line-bottom line-right line-left1 hang40 overflow-hidden">
                                        <input class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
                                               type="text"
                                               id="cost"
                                               value="${entity.cost}"
                                               name="cost"
                                               placeholder="成本价格 " readonly="readonly">
                                    </div>
                                </div>
                                <div class="col-2 pl-10">
                                    <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        销售价格
                                    </div>
                                    <div class="line-bottom line-right line-left1 hang40 overflow-hidden">
                                        <input class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
                                               type="text"
                                               id="price" value="${entity.price}" name="price"
                                               placeholder="请输入价格" readonly="readonly">
                                    </div>
                                </div>
                                <div class="col-2 pl-10">
                                    <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        市场价格
                                    </div>
                                    <div class="line-bottom line-right line-left1 hang40 overflow-hidden">
                                        <input class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
                                               type="text"
                                               id="oldprice" value="${entity.oldprice}" name="oldprice"
                                               placeholder="请输入市场价格" readonly="readonly">
                                    </div>
                                </div>
    
                                <div class="col-2 pl-10">
                                    <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        库存数量
                                    </div>
                                    <div class="line-bottom line-right line-left1 hang40 overflow-hidden">
                                        <input class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
                                               type="text"
                                               id="num" value="${entity.num}" name="num"
                                               placeholder="请输入库存数量" readonly="readonly">
                                    </div>
                                </div>
                                <div class="col-2 pl-10">
                                    <div class="size14 line-bottom weight500 pt-10 pb-10" style="padding-left: 2px;">
                                        已售数量
                                    </div>
                                    <div class="line-bottom line-right line-left1 hang40 overflow-hidden">
                                        <input class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
                                               type="text"
                                               id="gmnum" value="${entity.gmnum}" name="gmnum"
                                               placeholder="请输入已售数量" readonly="readonly">
                                    </div>
                                </div>
                                <div class="col-2 pl-10">
                                    <div class="size14 weight500 pt-10 pb-10"
                                         style="padding-left: 2px;">
                                        是否显示
                                    </div>
                                    <div class="overflow-hidden">
                                        <select id="isxs" name="isxs" class="select2"
                                                data-placeholder="请选择" readonly="readonly">
                                            <option value="0">显示</option>
                                            <option value="1">隐藏</option>
                                            
                                        </select>
                                    </div>
                                </div>
                   
                    </div>

                    <!--下部编辑器-->
                    <div class="pt-10 clear">
                    推荐：图片宽为640px,大小不能超过1M
                        <div class="div-group-10 border-radius5 bg-bai">
                            <textarea  name="context" id="context" class="ckeditor" rows="10" cols="38">${entity.context}</textarea>
                            <script id="editor" type="text/plain" style="width:100%;height:300px;">${entity.context}</script>
                        </div>
             
                    </div>

            </div>

        </div>
    </div>
    <!-- mainpanel -->
</section>
<%@include file="/webcom/cut-img1.jsp" %>

<script type="text/javascript">
    $('#bq').val('${bq}');
    $('#mb').val('${mb}');
    $('#gmcs').val('${gmcs}');
    $('#isxs').val('${entity.isxs}');
    $("#hylx").val("${entity.hylx}");
    $("#pcount").val("${entity.pcount}");
    changebq();
    setcke();
    $('#typeid').change(function(){ 
    	var id=$("#typeid").val();
    	if(isNaN(id)||id==null||id==''){
    		$("#mintypeid").html("<option  value=''>请选择</option>"); 
    		console.log($("#thirdtypeid").html());
    		$("#thirdtypeid").html("<option  value=''>请选择</option>"); 
    	}else{ 
    		getchild(id); 	
    	}
    	
    	
    });
    $('#mintypeid').change(function(){ 
    	var id=$("#mintypeid").val();
    	if(isNaN(id)||id==null||id==''){
    		$("#thirdtypeid").html("<option  value=''>请选择</option>"); 
    	}else{ 
    		getminchild(id); 	
    	}
    	
    	
    });
    $("#typeid").val("${entity.typeid}").trigger("change");
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    jQuery(".select2").select2({
    width: '100%'
   });
   var editor=CKEDITOR.replace('context');
   $("#typeid").prop("disabled", true);
   $("#mintypeid").prop("disabled", true);
   $("#thirdtypeid").prop("disabled", true);
   $("#isxs").prop("disabled", true);
   $("#mb").prop("disabled", true);
   $("#hylx").prop("disabled", true);
   
</script>


</body>
</html>
