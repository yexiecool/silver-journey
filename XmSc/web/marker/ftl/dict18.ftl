<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>

    <div class=" overflow-hidden">
        <div class="width100 height50 background_hui ">
            <a href="javascript:close_box()" class="bai">
                <div class="au"><</div>
            </a>

            <div class="bt">选择图标</div>
        </div>
		<#list jcdictList as dict> 
         
            <div class="box25 " onclick="seltb('${dict.key}')">
                <div class="box25_a_1">
                    <i class="fa ${dict.key}"></i>
                </div>
                <div class="box25_2">
                   ${dict.value}
                </div>
            </div>
      
        </#list>

        
    </div>


</body>
</html>