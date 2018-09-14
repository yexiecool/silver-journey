<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/webcom/meta.jsp"%>
<%@include file="/webcom/bracket.jsp"%>
<%@include file="/webcom/jquery.validate_js.jsp"%>
<script src="${contextPath}/bracket/js/jquery.tagsinput.min.js"></script>
<link rel="stylesheet"
	href="${contextPath}/bracket/css/jquery.tagsinput.css" />
<script src="${contextPath}/UserInterface/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/ckeditor/ckeditor.js"></script>
<link href="${ctx}/app/css/YLui.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/app/css/font-awesome.min.css" rel="stylesheet">
<script type="text/javascript">

        $(document).ready(function () {
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
			<div class="pageheader">
				<h2>
					<i class="fa fa-user"></i> 微官网 <span>滚动管理</span>
				</h2>
			</div>
			<div class="row">
				<div class="col-md-12">
					<form id="addroleform"
						action="${ctx}/suc/roll!save.action?fypage=${fypage}"
						class="form-horizontal" method="post">
						<input type="hidden" id="_id" name="_id" /> <input type="hidden"
							id="type" name="type" value="${type}" />

						<%-- <input type="hidden" id="goodstype" value="${goodstype}" name="goodstype"/> --%>
						<div class="div-group-10 overflow-hidden">
							<!--左边项目-->
							<div class="overflow-hidden">
								<div class="col-2 pl-10">
									<div class="size14 line-bottom weight500 pt-10 pb-10"
										style="padding-left: 2px;">名称</div>
									<div
										class="line-bottom line-right line-left1 hang40 overflow-hidden">
										<input
											class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
											type="text" id="title" name="title" placeholder="请输入名称">
									</div>
								</div>
							</div>
							<div class="overflow-hidden">

								<div class="col-2 pl-10">
									<div class="size14 line-bottom weight500 pt-10 pb-10"
										style="padding-left: 2px;">序号</div>
									<div
										class="line-bottom line-right line-left1 hang40 overflow-hidden">
										<input
											class="width-10 size14 zi-hui hang40 pl-10 pr-10 weight100"
											type="text"  id="sort" name="sort" placeholder="请输入序号">
									</div>
								</div>
							</div>

							<!--下部编辑器-->
							<div class="pt-10 clear">
								内容
								<div class="div-group-10 border-radius5 bg-bai">
									<textarea name="context" id="context" class="ckeditor"
										rows="10" cols="38">${context}</textarea>
									<script id="editor" type="text/plain"
										style="width: 100%; height: 300px;">${context}</script>
								</div>
								<a href="javascript:checksubmit()">
									<div class="pt-20 pb-20 clear">
										<div class="col-sm-12 btn btn-primary dropdown-toggle">
											提交</div>
									</div>
								</a>
							</div>
					</form>

				</div>

			</div>
		</div>
		<!-- mainpanel -->
	</section>
	<%@include file="/webcom/cut-img1.jsp"%>

	<script type="text/javascript">
 
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    jQuery(".select2").select2({
    width: '100%'
   });
   var editor=CKEDITOR.replace('context');

    function checksubmit() {
     
        $("#context").val(editor.getData()); 
        $('#addroleform').submit();
    }
</script>


</body>
</html>
