<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webcom/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
.button_foot .button_group1{background: #141534; }
</style>
<div class=" button_foot  shadow-wai cmp640" style="background: #1d2434; " >

   <div class=" button_group1"> 
            <div class=" zi-hui-wx txt-c weight500  pt-5 pb-5" onclick="window.location.href='${ctx}/integral/miners!index.action?custid=${custid}&agid=${agid}&lscode=${lscode}&comid=${entity._id}'">
                <font size="4">
                    <div class="fa fa-home"></div>
                </font>
                <div class=" pt-3">
                    <font size="1">
              		          首页
                    </font>
                </div>
            </div>
         
    </div>
    <div class=" button_group1">
            <div class=" zi-hui-wx txt-c weight500  pt-5 pb-5" onclick=" $(this).css({'color':'#7cc3df'}); window.location.href='${ctx}/integral/miners!list.action?custid=${custid}&agid=${agid}&lscode=${lscode}'">
                <font size="4">
                    <div class="fa  fa-window-restore"></div>
                </font>
                <div class=" pt-3">
                    <font size="1">
                 		      矿机商场
                    </font>
                </div>
            </div> 
    </div>
    <div class=" button_group1"> 
            <div class=" zi-hui-wx txt-c weight500  pt-5 pb-5" onclick=" $(this).css({'color':'#7cc3df'}); window.location.href='${ctx}/integral/miners!ownminer.action?custid=${custid}&agid=${agid}&lscode=${lscode}'">
                <font size="4">
                    <div class="fa fa-shopping-bag"></div>
                </font>
                <div class=" pt-3">
                    <font size="1">
                       我的矿机
                    </font>
                </div>
            </div> 
    </div>

    <div class=" button_group1" > 
            <div class=" zi-hui-wx txt-c weight500  pt-5 pb-5" onclick=" $(this).css({'color':'#7cc3df'}); window.location.href='${ctx}/integral/miners!ownperson.action?custid=${custid}&agid=${agid}&lscode=${lscode}'">
                <font size="4">
                    <div class="fa fa-user"></div>
                </font>
                <div class=" pt-3">
                    <font size="1">
                       个人中心
                    </font>
                </div>
            </div>
        
    </div>
    
</div>

