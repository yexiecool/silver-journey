<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>

<%@include file="/webcom/meta.jsp"%>
<%@include file="/webcom/bracket.jsp"%>
<%@include file="/webcom/jquery.validate_js.jsp"%>
<link href="${ctx}/cmp/css/cmp_js_yangshibiao.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${contextPath}/js/upload/swfobject.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/upload/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${contextPath}/js/upload/upload.js"></script>

<script src="${contextPath}/UserInterface/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function del(id) {
		if (confirm('确实要删除吗?')) {
			location.href = "${ctx}/suc/integral!delete.action?_id=" + id;
		}
	}
	function add() {
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
	function upd(id) {
		var submitData = {
			id : id
		};
		$.post('${ctx}/suc/integral!upd.action', submitData, function(json) {
			$('#_id').val(json._id);
			$('#title').val(json.title);
			$('#summary').val(json.summary);
			$('#url').val(json.url);
			$('#sort').val(json.sort);
			$('#type').val(json.type);
			$('#uploadresultFour').val(json.picurl);

		}, "json")

		$('#insadd').modal({
			show : true
		});
	}

	function updfx(type) {
		var submitData = {
			fxtype : type
		};
		$.post('${ctx}/weixin/sharefx!upd.action', submitData, function(json) {
			$('#fxtype').val(type);
			$('#fxtitle').val(json.fxtitle);
			$('#fxsummary').val(json.fxsummary);
			$('#oldurl').val(json.oldurl);
			$('#fxurl').val(json.fxurl);
			$('#type').val(json.type);
			$('#uploadresultFour').val(json.fximg);

		}, "json")
		$('#insfx').modal({
			show : true
		});

	}
	function savefx() {
		var submitData = {
			fxtype : $('#fxtype').val(),
			fxtitle : $('#fxtitle').val(),
			fxsummary : $('#fxsummary').val(),
			oldurl : $('#oldurl').val(),
			fxurl : $('#fxurl').val(),
			fximg : $('#uploadresultFour').val()
		};
		$.post('${ctx}/weixin/sharefx!ajaxsave.action', submitData, function(
				json) {
			window.location.href = '${ctx}/whd/wxmatrix.action';

		}, "json")
		$('#inszc').modal({
			show : true
		});

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
	function share(url) {
		window.open("${contextPath}/weixin/share.action?method="
				+ encodeURIComponent(url));
	}

	function exp() {
		var sel_state = $("#sel_state").val();
		var sel_type = $("#sel_type").val();
		var sel_insdate = $("#sel_insdate").val();
		var sel_enddate = $("#sel_enddate").val();
		location.href = "${ctx }/suc/integral!integerallfromexp.action?comid=${comid}&sel_state="
				+ sel_state
				+ "&sel_insdate="
				+ sel_insdate
				+ "&sel_enddate="
				+ sel_enddate + "&sel_type=" + sel_type;

	}
</script>
</head>

<body>

	<section>

		<%@include file="/webcom/header-bracket.jsp"%>

		<div class="mainpanel">
			<%@include file="/webcom/header-headerbar.jsp"%>

			<form id="custinfoForm" name="custinfoForm" method="post"
				action="${contextPath}/suc/integrall!profit.action">

				<div class="pageheader">
					<h2>
						<i class="fa fa-user"></i>系统管理 <span>乐乐币管理</span>
					</h2>

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
				            	<select  id="sel_type"  name="type" class="form-control "  data-placeholder="请选择盼盼币类型 ">
	            	                <option value="">请选择类型</option>
	                    			
				                 </select>
				            </div>
							<div class="form-group col-sm-2">
								<input type="text" id="vipno" name="vipno" value="${vipno}"
									placeholder="会员编号" class="form-control" />
							</div>

							<div class="form-group col-sm-2">
								<input type="text" id="sel_insdate" name="sel_insdate"
									value="${sel_insdate}" placeholder="开始日期"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									class="form-control" />
							</div>
							<div class="form-group col-sm-2">
								<input type="text" id="sel_enddate" name="sel_enddate"
									value="${sel_enddate}" placeholder="结束日期"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									class="form-control" />
							</div>


							<a href="javascript:page_submit(-1);" class="btn btn-primary">搜&nbsp;&nbsp;索</a>

						</div>

					</div>
				</div>

<div class="panel-body">
	<div class="row">
		<div class="col-md-12">
			<div class="table-responsive">
				<%@include file="/webcom/bracket-page.jsp"%>
</div>
<!--mjy添加开始-->

<script type="text/javascript" src="${ctx}/bracket/js/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/bracket/js/jquery.ui.js"></script>
<link href="${ctx}/bracket/css/jquery.treeTable.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${ctx}/bracket/js/jquery.treeTable.js"></script>
<script type="text/javascript">
	$(document).ready(
					function() {
						$("#dnd-example")
								.treeTable();

						// Drag & Drop Example Code
						$(
								"#dnd-example .file, #dnd-example .folder")
								.draggable(
										{
											helper : "clone",
											opacity : .75,
											refreshPositions : true, // Performance?
											revert : "invalid",
											revertDuration : 300,
											scroll : true
										});

						$("#dnd-example .folder")
								.each(
										function() {
											$(
													$(
															this)
															.parents(
																	"tr")[0])
													.droppable(
															{
																accept : ".file, .folder",
																drop : function(
																		e,
																		ui) {
																	$(
																			$(
																					ui.draggable)
																					.parents(
																							"tr")[0])
																			.appendBranchTo(
																					this);
																},
																hoverClass : "accept",
																over : function(
																		e,
																		ui) {
																	if (this.id != ui.draggable
																			.parents("tr.parent")[0].id
																			&& !$(
																					this)
																					.is(
																							".expanded")) {
																		$(
																				this)
																				.expand();
																	}
																}
															});
										});

						// Make visible that a row is clicked
						$(
								"table#dnd-example tbody tr")
								.mousedown(
										function() {
											$(
													"tr.selected")
													.removeClass(
															"selected"); // Deselect currently selected rows
											$(this)
													.addClass(
															"selected");
										});

						// Make sure row is selected when span is clicked
						$(
								"table#dnd-example tbody tr span")
								.mousedown(
										function() {
											$(
													$(
															this)
															.parents(
																	"tr")[0])
													.trigger(
															"mousedown");
										});
					});
</script>
<style>
#trtr tr td {
	padding: 10px 25px;
}
</style>
							<!--mjy添加结束-->
							<table class="table table-primary mb30" id="dnd-example">
								<thead>
									<tr>
										<th>会员编号</th>
										<th>总业绩</th>
										<th>详情</th>
									</tr>
								</thead>
								<tbody id="trtr">
									<tr id='node--2' class='pasrent '>
										<td><span>001</span></td>
										<td>50000</td>
										<td>呵呵呵</td>
									</tr>
									<tr id='node--2-1' class='parent child-of-node--2'>
										<td><span>001-1</span></td>
										<td>200000</td>
										<td>呜呜呜</td>
									</tr>
									<tr id='node--2-1-1' class='child-of-node--2-1'>
										<td><span>001-1-1</span></td>
										<td>30000</td>
										<td>丽丽</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>

				</div>
				<!-- contentpanel -->
			</form>
		</div>
		<!-- mainpanel -->
	</section>
	<script>
		jQuery(".select2").select2({
			width : '100%'
		});
		$('#sel_state').val('${state}').trigger("change");
		$('#sel_type').val('${type}').trigger("change");
	</script>
</body>
</html>
