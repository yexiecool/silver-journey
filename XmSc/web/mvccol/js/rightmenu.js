
var rightmenu=function(elementID,menuID){   
	    var menu=document.getElementById(menuID);      //获取菜单对象
	    var element=document.getElementById(elementID);//获取点击拥有自定义右键的 元素
	    element.onmousedown=function(aevent){         //设置该元素的 按下鼠标右键右键的 处理函数
	     if(window.event)aevent=window.event;      //解决兼容性
	    	if(aevent.button==2){                   //当事件属性button的值为2时，表用户按下了右键
	    	document.oncontextmenu=function(aevent){
	    	  if(window.event){
	    	      aevent=window.event;
	    	      aevent.returnValue=false;         //对IE 中断 默认点击右键事件处理函数
	    	}else{
	    	     aevent.preventDefault();          //对标准DOM 中断 默认点击右键事件处理函数
	    	       };
	    	      };
	    	   menu.style.cssText='display:block;top:'+aevent.clientY+'px;'+'left:'+aevent.clientX+'px;';
	              //将菜单相对 鼠标定位
	    	    }
	    	 }
	    	menu.onmouseout=function(){                  //设置 鼠标移出菜单时 隐藏菜单
	    	setTimeout(function(){menu.style.display="none";},400);
	    	} 
		 
	    }

 