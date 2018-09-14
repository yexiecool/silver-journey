package com.lsp.pub.web;

import com.lsp.pub.dao.BaseDao;
import com.lsp.pub.db.MongoSequence;
import com.lsp.pub.entity.FuncInfo;
import com.lsp.pub.entity.PubConstants;
import com.lsp.pub.entity.RoleInfo;
import com.lsp.pub.service.TimerService;
import com.lsp.pub.util.ListUtil;
import com.lsp.pub.util.SpringSecurityUtils;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.util.UniObject;
import com.lsp.user.entity.UserInfo;
import com.lsp.website.service.WwzService;
import com.mongodb.DBObject;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern; 
import net.sf.json.JSONArray;
import net.sf.json.JSONObject; 

import org.apache.activemq.filter.function.splitFunction;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 手动产币的方法
 * @author  zp
 *   
 */ 
@Namespace("/pub")
@Results({@org.apache.struts2.convention.annotation.Result(name="reload", location="pada.action", type="redirect")})
public class PadaAction extends GeneralAction<RoleInfo>
{
  private static final long serialVersionUID = -6784469775589971579L;

  @Autowired
  private BaseDao basedao;
  private Long _id;
  private RoleInfo entity = new RoleInfo();
  private MongoSequence mongoSequence;
  @Autowired
  private WwzService  wwzservice; 
  @Autowired
  public void setMongoSequence(MongoSequence mongoSequence)
  {
    this.mongoSequence = mongoSequence;
  }

  public void set_id(Long _id) {
    this._id = _id;
  }

  public String execute() throws Exception {
	  
	  return "reload";
  }
  
  
public void addpada() throws Exception {
	Struts2Utils.getResponse().setContentType("text/html;charset=UTF-8");
	 Struts2Utils.getResponse().getWriter().write("------------------ 开始手动调用产币的方法 ------------------------------------------");
	  //调用定时器里面产比的方法：
	  System.out.println("------------------ 手动调用产币的方法 -------------------------------------------");
	  TimerService timerService =new TimerService();
	  timerService.updProstore();
	  
	  Struts2Utils.getResponse().getWriter().write("------------------ 手动调用产币的方法已执行完成 ------------------------------------------");
       
	  
  }

  public String input()
    throws Exception
  {
    return "add";
  }

  public String update()
    throws Exception
  {
    return "add";
  }

  public String save() throws Exception
  {
    
    return "reload";
  }

  public String delete() throws Exception
  {
     
    return "reload";
  }

  protected void prepareModel()
    throws Exception
  {
    if (this._id != null)
    {
      DBObject emDbObject = this.basedao.getMessage(PubConstants.ROLE_INFO, 
        this._id);
      this.entity = ((RoleInfo)UniObject.DBObjectToObject(emDbObject, 
        RoleInfo.class));
    } else {
      this.entity = new RoleInfo();
    }
  }

  public RoleInfo getModel()
  {
    return this.entity;
  }

  public void getfunclist() {
  
  }
 
}