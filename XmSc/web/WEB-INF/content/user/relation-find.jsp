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
<link rel="stylesheet" type="text/css" href="${ctx }/xmMobile/css/bootstrap.min.css" >
<script src="${ctx }/xmMobile/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script type="text/javascript">

 
 
</script>
</head>
<style>
	.wangti{ width: 100%; text-align: center; border:1px solid #005EAC; border-width: 0 0 1px  1px;  }	 
	.wangti tr td{ border:1px solid #005EAC; border-width: 1px 1px 0px  0px; }	
	.sjcont{ background: #0f436f; margin: 10px auto; border:2px solid #f7f7f7; position: static; float: none; color: #fff; padding: 10px 0 0; border-radius:10px;  font-size: 12px;   }
	.sjcont p{ width: 100%; margin:0px;  border-bottom: 2px solid #f7f7f7;  }
	.sjcont div.jine{  width: 50%; float:left; border-right: 2px solid #f7f7f7;  background: #0089fa; padding: 5px 0;  border-radius:0 0 0 10px;    }
	.sjcont div.renshu{ width: 50%;  float:left;  background:  #005EAC; padding: 5px 0;  border-radius: 0 0 10px 0;    } 
	
	
	/* .sjcont{ background: #005EAC; margin: 10px auto; border:2px solid #f7f7f7; position: static; float: none; color: #fff; padding: 10px 0 0; border-radius:10px;  font-size: 12px;   }
	.sjcont p{ width: 100%; margin:0px;  border-bottom: 2px solid #f7f7f7;  }
	.sjcont div.jine{  width: 50%; float:left; border-right: 2px solid #f7f7f7;  background: #54d86b; padding: 5px 0;  border-radius:0 0 0 10px;    }
	.sjcont div.renshu{ width: 50%;  float:left;  background: #007aff; padding: 5px 0;  border-radius: 0 0 10px 0;    } */
	
	.clear{ clear: both; }
</style>

<script type="text/javascript">

function page_submit(){
	 
	var vipno =$("#vipno").val();
	if(vipno==""){
		alert("请输入会员编号");
	}else{
		$("#relationForm").submit();
	}
	
}

function selectcurr(number) {
	 
	location.href = "${ctx}/user/relation!findrelation.action?vipno="+ number;
 } 
</script>


<script>
 
</script>


<body>

<section>

  <%@include file="/webcom/header-bracket.jsp"%>

  <div class="mainpanel">
	<%@include file="/webcom/header-headerbar.jsp"%>
    
	<form  id="relationForm" name="relationForm" method="post"  action="${contextPath}/user/relation!findrelation.action" >
    
    <div class="pageheader">
      <h2><i class="fa fa-user"></i>系统管理 <span>网体管理</span></h2>
    </div>
    
   <div class="panelss ">
                <div class="panel-body fu10">
                    <div class="row-pad-5">
                     	<div class="form-group col-sm-2">
			                 <input type="number" id="vipno" name="vipno" value="${vipno}" placeholder="会员编号"   class="form-control" />
			            </div>
                        <a href="javascript:page_submit();" class="btn btn-primary">搜&nbsp;&nbsp;索</a>
                        
                    </div>
                    
                </div>
  		<!--网体-->
        <table class="wangti"  id="ajaxtable" border="0" cellpadding="0" cellspacing="0" >
        <!-- 主节点 -->
		<tr>
		  
		   
		   <c:if test="${not empty errmessage}">
			   <td colspan="8">
					${errmessage}
				</td>
		   </c:if>
		   
		   <c:if test="${not empty relations}">
			  <td colspan="8">
				 <div class="sjcont col-md-2">
				 	 <p class="bianhao">编号:
				 	 <span>${relations.number}
				 	  <c:choose>
				 	  	  <c:when test="${relations.agentLevel == 1}">【省代】</c:when>
                      	  <c:when test="${relations.agentLevel == 2}">【市代】</c:when>
                      	  <c:when test="${relations.agentLevel == 3}">【区代】</c:when> 
                      	  <c:when test="${relations.agentLevel == 4}">【报单】</c:when>
                      	  <c:when test="${relations.agentLevel == 100}">【微】</c:when>
                      	  <c:when test="${relations.agentLevel == 101}">【小】</c:when>
                      	  <c:when test="${relations.agentLevel == 102}">【中】</c:when> 
                      	  <c:when test="${relations.agentLevel == 103}">【大】</c:when>
                      	  <c:otherwise>【游客】 </c:otherwise>
                      </c:choose>
                      
				 	 </span></p>				 	
			 	 	 <div class="jine">￥<span>${relations.money}</span></div>
					 <div class="renshu"><span>${relations.count}</span>人</div>
					 <div class="clear"></div>				
				 </div>
			</td>
		   </c:if>
		</tr>
		<c:if test="${not empty listrelations  }">
		
			<!-- 第一层   -->
			 <tr>
				<td colspan="4" width="50%" >
					 <c:if test="${not empty listrelations[0] && not empty listrelations[0].number  }" >
						 <div class="sjcont col-md-3 " onclick="selectcurr(${listrelations[0].number})">
						 	 <p class="bianhao">编号:<span>${listrelations[0].number}
						 	  <c:choose>
						 	   	  <c:when test="${listrelations[0].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[0].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[0].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[0].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[0].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[0].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[0].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[0].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose>
						 	 </span></p>
							 <div class="jine">￥<span>${listrelations[0].money}</span></div>
							 <div class="renshu"><span>${listrelations[0].count}</span>人</div>
							 <div class="clear"></div>
						 </div>
					 </c:if>
	
				</td>
				<td colspan="4"  width="50%">
				<c:if test="${ not empty listrelations[1] &&  not empty listrelations[1].number }" >
					 <div class="sjcont col-md-3"   onclick="selectcurr(${listrelations[1].number})">
					 	 <p class="bianhao">编号:<span>${listrelations[1].number} <c:choose>
					 	 		  <c:when test="${listrelations[1].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[1].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[1].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[1].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[1].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[1].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[1].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[1].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
						 <div class="jine">￥<span>${listrelations[1].money}</span></div>
						 <div class="renshu"><span>${listrelations[1].count}</span>人</div>
						 <div class="clear"></div>
					 </div>
				</c:if>
				</td>
			 </tr>
			 <!-- 第二层网体 -->
			 <c:if test="${ not empty listrelations  && fn:length(listrelations)   >1 }" >
				 <tr>
				    
					<td colspan="2" width="25%">
						<c:if test="${ not empty listrelations[2] &&  not empty listrelations[2].number }" >
						 <div class="sjcont col-md-6" onclick="selectcurr(${listrelations[2].number})">
						 	 <p class="bianhao">编号:<span>${listrelations[2].number} <c:choose>
						 	      <c:when test="${listrelations[2].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[2].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[2].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[2].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[2].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[2].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[2].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[2].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
							 <div class="jine">￥<span>${listrelations[2].money}</span></div>
							 <div class="renshu"><span>${listrelations[2].count}</span>人</div>
							 <div class="clear"></div>
						 </div>
						</c:if>
					</td>
					<td colspan="2" width="25%">
						<c:if test="${ not empty listrelations[3] &&  not empty listrelations[3].number }" >
						 <div class="sjcont col-md-6" onclick="selectcurr(${listrelations[3].number})">
						 	 <p class="bianhao">编号:<span>${listrelations[3].number} <c:choose>
		                      	  <c:when test="${listrelations[3].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[3].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[3].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[3].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[3].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[3].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[3].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[3].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
							 <div class="jine">￥<span>${listrelations[3].money}</span></div>
							 <div class="renshu"><span>${listrelations[3].count}</span>人</div>
							 <div class="clear"></div>
						 </div>
						</c:if>
					</td>
					 
					 
					<td colspan="2" width="25%">
					  <c:if test="${ not empty listrelations[4] &&  not empty listrelations[4].number }" >
						 <div class="sjcont col-md-6" onclick="selectcurr(${listrelations[4].number})">
						 	<p class="bianhao">编号:<span>${listrelations[4].number} <c:choose>
		                      	  <c:when test="${listrelations[4].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[4].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[4].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[4].agentLevel == 4}">【报单】</c:when><c:when test="${listrelations[4].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[4].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[4].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[4].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
							 <div class="jine">￥<span>${listrelations[4].money}</span></div>
							 <div class="renshu"><span>${listrelations[4].count}</span>人</div>
							 <div class="clear"></div>
						 </div>
					   </c:if>
					</td>
				 
					 
					<td colspan="2" width="25%">
					<c:if test="${ not empty listrelations[5] &&  not empty listrelations[5].number }" >
						 <div class="sjcont col-md-6" onclick="selectcurr(${listrelations[5].number})">
						 	 <p class="bianhao">编号:<span>${listrelations[5].number} <c:choose>
		                      	  <c:when test="${listrelations[5].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[5].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[5].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[5].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[5].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[5].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[5].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[5].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
							 <div class="jine">￥<span>${listrelations[5].money}</span></div>
							 <div class="renshu"><span>${listrelations[5].count}</span>人</div>
							 <div class="clear"></div>
						 </div>
				    </c:if>
					</td>
					 
				</tr>
			 </c:if>
			 <!-- 第三层网体 -->
			  <c:if test="${ not empty listrelations  && fn:length(listrelations)   >5 }" >
			  	
				  <tr>
					
						<td width="12.5%" >
							<c:if test="${ not empty listrelations[6] &&  not empty listrelations[6].number }" >
							 <div class="sjcont col-md-12" onclick="selectcurr(${listrelations[6].number})">
							 	 <p class="bianhao">编号:<span>${listrelations[6].number}<c:choose>
		                      	  <c:when test="${listrelations[6].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[6].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[6].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[6].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[6].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[6].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[6].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[6].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
								 <div class="jine">￥<span>${listrelations[6].money}</span></div>
								 <div class="renshu"><span>${listrelations[6].count}</span>人</div>
								 <div class="clear"></div>
							 </div>
						    </c:if>
						</td>
						<td width="12.5%" >
							<c:if test="${ not empty listrelations[7] &&  not empty listrelations[7].number }" >
							 <div class="sjcont col-md-12" onclick="selectcurr(${listrelations[7].number})">
							 	 <p class="bianhao">编号:<span>${listrelations[7].number}<c:choose>
		                      	  <c:when test="${listrelations[7].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[7].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[7].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[7].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[7].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[7].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[7].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[7].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
								 <div class="jine">￥<span>${listrelations[7].money}</span></div>
								 <div class="renshu"><span>${listrelations[7].count}</span>人</div>
								 <div class="clear"></div>
							 </div>
						    </c:if>
						</td>
						<td width="12.5%" >
							<c:if test="${ not empty listrelations[8] &&  not empty listrelations[8].number }" >
							 <div class="sjcont col-md-12" onclick="selectcurr(${listrelations[8].number})">
							 	 <p class="bianhao">编号:<span>${listrelations[8].number}<c:choose>
		                      	  <c:when test="${listrelations[8].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[8].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[8].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[8].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[8].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[8].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[8].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[8].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
								 <div class="jine">￥<span>${listrelations[8].money}</span></div>
								 <div class="renshu"><span>${listrelations[8].count}</span>人</div>
								 <div class="clear"></div>
							 </div>
						    </c:if>
						</td>
						<td width="12.5%" >
							<c:if test="${ not empty listrelations[9] &&  not empty listrelations[9].number }" >
							 <div class="sjcont col-md-12" onclick="selectcurr(${listrelations[9].number})">
							 	 <p class="bianhao">编号:<span>${listrelations[9].number}<c:choose>
		                      	  <c:when test="${listrelations[9].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[9].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[9].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[9].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[9].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[9].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[9].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[9].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
								 <div class="jine">￥<span>${listrelations[9].money}</span></div>
								 <div class="renshu"><span>${listrelations[9].count}</span>人</div>
								 <div class="clear"></div>
							 </div>
						    </c:if>
						</td>
						<td width="12.5%" >
							<c:if test="${ not empty listrelations[10] &&  not empty listrelations[10].number }" >
							 <div class="sjcont col-md-12" onclick="selectcurr(${listrelations[10].number})">
							 	 <p class="bianhao">编号:<span>${listrelations[10].number}<c:choose>
		                      	  <c:when test="${listrelations[10].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[10].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[10].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[10].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[10].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[10].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[10].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[10].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
								 <div class="jine">￥<span>${listrelations[10].money}</span></div>
								 <div class="renshu"><span>${listrelations[10].count}</span>人</div>
								 <div class="clear"></div>
							 </div>
						    </c:if>
						</td>
						<td width="12.5%" >
							<c:if test="${ not empty listrelations[11] &&  not empty listrelations[11].number }" >
							 <div class="sjcont col-md-12" onclick="selectcurr(${listrelations[11].number})">
							 	 <p class="bianhao">编号:<span>${listrelations[11].number}<c:choose>
		                      	  <c:when test="${listrelations[11].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[11].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[11].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[11].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[11].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[11].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[11].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[11].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
								 <div class="jine">￥<span>${listrelations[11].money}</span></div>
								 <div class="renshu"><span>${listrelations[11].count}</span>人</div>
								 <div class="clear"></div>
							 </div>
						    </c:if>
						</td>
						<td width="12.5%" >
							<c:if test="${ not empty listrelations[12] &&  not empty listrelations[12].number }" >
							 <div class="sjcont col-md-12" onclick="selectcurr(${listrelations[12].number})">
							 	 <p class="bianhao">编号:<span>${listrelations[12].number}<c:choose>
		                      	  <c:when test="${listrelations[12].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[12].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[12].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[12].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[12].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[12].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[12].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[12].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
								 <div class="jine">￥<span>${listrelations[12].money}</span></div>
								 <div class="renshu"><span>${listrelations[12].count}</span>人</div>
								 <div class="clear"></div>
							 </div>
						    </c:if>
						</td>
						<td width="12.5%" >
							<c:if test="${ not empty listrelations[13] &&  not empty listrelations[13].number }" >
							 <div class="sjcont col-md-12" onclick="selectcurr(${listrelations[13].number})">
							 	 <p class="bianhao">编号:<span>${listrelations[13].number}<c:choose>
		                      	  <c:when test="${listrelations[13].agentLevel == 1}">【省代】</c:when>
		                      	  <c:when test="${listrelations[13].agentLevel == 2}">【市代】</c:when>
		                      	  <c:when test="${listrelations[13].agentLevel == 3}">【区代】</c:when> 
		                      	  <c:when test="${listrelations[13].agentLevel == 4}">【报单】</c:when>
		                      	  <c:when test="${listrelations[13].agentLevel == 100}">【微】</c:when>
		                      	  <c:when test="${listrelations[13].agentLevel == 101}">【小】</c:when>
		                      	  <c:when test="${listrelations[13].agentLevel == 102}">【中】</c:when> 
		                      	  <c:when test="${listrelations[13].agentLevel == 103}">【大】</c:when>
		                      	  <c:otherwise>【游客】 </c:otherwise>
		                      </c:choose></span></p>
								 <div class="jine">￥<span>${listrelations[13].money}</span></div>
								 <div class="renshu"><span>${listrelations[13].count}</span>人</div>
								 <div class="clear"></div>
							 </div>
						    </c:if>
						</td>
					 
				  </tr>
			  </c:if>
		
		</c:if>
		 
	</table>
  
   </div>
				
    <div class="panel-body">
      <div class="row">
 
      </div>
		
    </div><!-- contentpanel -->
	</form>
  </div><!-- mainpanel -->
</section>
	<script>
jQuery(".select2").select2({
    width: '100%'
});
 
</script>
</body>
</html>
