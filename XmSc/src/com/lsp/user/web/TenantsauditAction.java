package com.lsp.user.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lsp.pub.dao.BaseDao;
import com.lsp.pub.db.MongoSequence;
import com.lsp.pub.entity.PubConstants;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.util.SysConfig;
import com.lsp.pub.util.UniObject;
import com.lsp.pub.web.GeneralAction;
import com.lsp.user.entity.UserInfo;
import com.lsp.user.service.MailService;
import com.lsp.website.service.WwzService;
import com.mongodb.DBObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 审核管理
 * @author lsp
 *
 */
@Namespace("/user")
@Results({@org.apache.struts2.convention.annotation.Result(name="reload",location="tenantsaudit.action", type="redirect")})
public class TenantsauditAction extends GeneralAction<UserInfo>{
	 private static final long serialVersionUID = -6784469775589971579L;

	  @Autowired
	  private BaseDao basedao;
	  private String _id;
	  private MongoSequence mongoSequence;
	  @Autowired
	  private WwzService  wwzservice; 
	  
	  private UserInfo entity = new UserInfo();
	  
	  @Autowired
	  public void setMongoSequence(MongoSequence mongoSequence)
	  {
	    this.mongoSequence = mongoSequence;
	  }
	  public void set_id(String _id) {
	    this._id = _id;
	  }
	  @Override
	  public String execute() throws Exception {
		gsCustid();
	  	HashMap<String, Object> sortMap = new HashMap<String, Object>();
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
	 
		sortMap.put("createdate", Integer.valueOf(-1));
		
		//audit_status  0-未审核     1审核通过    2审核不通过
		whereMap.put("audit_status", 0);
		
		if(StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))){
			fypage=Integer.parseInt(Struts2Utils.getParameter("fypage"));
		} 
		if(custid.equals(SysConfig.getProperty("gsid"))||custid.equals(SysConfig.getProperty("custid"))) {
			whereMap.put("custid", SysConfig.getProperty("custid"));
		}else{
			whereMap.put("custid", custid);
		}
		whereMap.put("roleid", Long.parseLong(String.valueOf(SysConfig.getProperty("sjroleid"))));//判断商家角色信息
		List<DBObject> list = basedao.getList(PubConstants.USER_INFO, whereMap,fypage,10,sortMap);
		
		fycount=basedao.getCount(PubConstants.USER_INFO,whereMap);
		Struts2Utils.getRequest().setAttribute("list", list); 
		Struts2Utils.getRequest().setAttribute("fycount", fycount); 
	  	 return SUCCESS;
	  }
	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return "add";
	}

	@Override
	public String update() throws Exception {
		// TODO Auto-generated method stub
		return "add";
	}

	


	
	public UserInfo getModel()
	  {
	    return this.entity;
	  }
	public void upd() throws Exception { 
		DBObject db = basedao.getMessage(PubConstants.USER_INFO, _id); 
		String json = JSONObject.fromObject(db).toString();
		Struts2Utils.renderJson(json, new String[0]);
	}
	
	/**
	 * ajax我要开店审核
	 */
	public void  ajaxauditsave(){
		
		Map<String, Object>sub_Map=new HashMap<String, Object>();
		try {
			String id=Struts2Utils.getParameter("id");
			String comments=Struts2Utils.getParameter("comments");
			String audit_status =  Struts2Utils.getParameter("audit_status");//审核状态
			DBObject db = basedao.getMessage(PubConstants.USER_INFO, id);
			UserInfo user = (UserInfo) UniObject.DBObjectToObject(db, UserInfo.class);
			user.setAudit_status(Integer.parseInt(audit_status));
			user.setComments(comments); //审核意见
			//给用户发邮件通知审核结果
			try {
				MailService.sendHtmlMail( user.getEmail(), "熊猫商城入住审核通知", comments );
			} catch (UnsupportedEncodingException | MessagingException e) {
				// TODO Auto-generated catch block
				 System.out.println("邮箱地址有误");
			}
			basedao.insert(PubConstants.USER_INFO, user);
			sub_Map.put("state", 0);//修改成功
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			sub_Map.put("state", 1);
			e.printStackTrace();
		}
	  	String json = JSONArray.fromObject(sub_Map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}
	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}
	 
}
