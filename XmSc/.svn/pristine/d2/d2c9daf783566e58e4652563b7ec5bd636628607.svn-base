package com.lsp.shop.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lsp.pub.dao.BaseDao;
import com.lsp.pub.db.MongoSequence;
import com.lsp.pub.entity.Code;
import com.lsp.pub.entity.GetAllFunc;
import com.lsp.pub.entity.PubConstants;
import com.lsp.pub.util.DateUtil;
import com.lsp.pub.util.SpringSecurityUtils;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.util.UniObject;
import com.lsp.pub.web.GeneralAction;
import com.lsp.shop.entiy.ShopJoin;
import com.lsp.suc.entity.RollInfo;
import com.lsp.sys.security.CustomerUser;
import com.lsp.user.entity.UserInfo;
import com.mongodb.DBObject;

import net.sf.json.JSONArray;
/**
 * 修改密码
 * @author zp
 *
 */
@Namespace("/shop")
@Results( { @Result(name ="reload", location = "updatezxg.action", type = "redirect") })
public class UpdatezxgAction extends GeneralAction<UserInfo>{


	private static final long serialVersionUID = -6784469775589971579L;
	@Autowired
	private BaseDao baseDao;
	private MongoSequence mongoSequence;	
	@Autowired
	public void setMongoSequence(MongoSequence mongoSequence) {
		this.mongoSequence = mongoSequence;
	}  
	private UserInfo entity=new UserInfo();
	private Long _id;


	@Override
	public String execute() throws Exception {
		 //获取当前登录用户的信息
		 CustomerUser cust =(CustomerUser)SpringSecurityUtils.getCurrentUser();
		 String loginname =cust.getLoginname();
		 String id =cust.getId();
		 String passwd  =cust.getPassword();
		 
		 
		 Struts2Utils.getRequest().setAttribute("loginname", loginname);
		 Struts2Utils.getRequest().setAttribute("id", id);
		 Struts2Utils.getRequest().setAttribute("passwd", passwd);
		 
		return SUCCESS;
	}
	
	
	/**
	 * 后台管理修改密码
	 * @throws Exception
	 */
	 
	public void changepw() throws Exception {
		Map<String, Object> sub_map = new HashMap<>();
		String newpwd = Struts2Utils.getParameter("password");
		String id = Struts2Utils.getParameter("id");   
		 
		
		DBObject dbObject = baseDao.getMessage(PubConstants.USER_INFO,id );
		if (dbObject != null) {
			UserInfo user = (UserInfo) UniObject.DBObjectToObject(dbObject, UserInfo.class);
			 	user.setPassword(newpwd);
				baseDao.insert(PubConstants.USER_INFO, user);
				sub_map.put("state", 0);// 修改成功
			} else {
				sub_map.put("state", 1);// 获取用户信息有误
			}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}
	 

 


	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return "add";
	}

	
 

	@Override
	public String update() throws Exception {
	 
		return "add";
	}
	 

	 
	
	 
	 

 


	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		try {
			if(_id!=null){
				DBObject db=baseDao.getMessage(PubConstants.SHOP_JOIN, _id);
				entity= (UserInfo) UniObject.DBObjectToObject(db, UserInfo.class);
			}else{
				entity=new UserInfo();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	@Override
	public UserInfo getModel() {
		// TODO Auto-generated method stub
		return entity;
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
	 
	 
     

}
