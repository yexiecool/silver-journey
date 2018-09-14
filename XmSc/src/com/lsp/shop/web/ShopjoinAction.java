package com.lsp.shop.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lsp.pub.dao.BaseDao;
import com.lsp.pub.db.MongoSequence;
import com.lsp.pub.entity.PubConstants;
import com.lsp.pub.util.SpringSecurityUtils;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.util.SysConfig;
import com.lsp.pub.util.UniObject;
import com.lsp.pub.web.GeneralAction;
import com.lsp.shop.entiy.ShopJoin;
import com.lsp.suc.entity.Bbstypeinfo;
import com.lsp.suc.entity.HouseType;
import com.lsp.suc.entity.RollInfo;
import com.lsp.website.service.WebsiteService;
import com.mongodb.DBObject;
/**
 * 商家入住
 * @author zp
 *
 */
@Namespace("/shop")
@Results( { @Result(name ="reload", location = "shopjoin.action", type = "redirect") })
public class ShopjoinAction extends GeneralAction<ShopJoin>{


	private static final long serialVersionUID = -6784469775589971579L;
	@Autowired
	private BaseDao baseDao;
	private MongoSequence mongoSequence;	
	@Autowired
	public void setMongoSequence(MongoSequence mongoSequence) {
		this.mongoSequence = mongoSequence;
	}  
	private ShopJoin entity=new ShopJoin();
	private Long _id;


	@Override
	public String execute() throws Exception {
		gsCustid();
		HashMap<String, Object> sortMap =new HashMap<String, Object>();
		HashMap<String, Object> whereMap =new HashMap<String, Object>();
		HashMap<String, Object> backMap =new HashMap<String, Object>();

		sortMap.put("sort", -1); 

		String title=Struts2Utils.getParameter("titles");
		String type=Struts2Utils.getParameter("type");
		if(StringUtils.isNotEmpty(type)){
			whereMap.put("type", type);
			Struts2Utils.getRequest().setAttribute("type",type);
		}
		if(StringUtils.isNotEmpty(title)){
			Pattern pattern = Pattern.compile("^.*" + title + ".*$",
					Pattern.CASE_INSENSITIVE);
			whereMap.put("title", pattern);
			Struts2Utils.getRequest().setAttribute("titles",  title);
		}
	 
		if(StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))){
			fypage=Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		
		List<DBObject> list=baseDao.getList(PubConstants.SHOP_JOIN,whereMap,fypage,10,sortMap,backMap);
		fycount=baseDao.getCount(PubConstants.SHOP_JOIN,whereMap);
		Struts2Utils.getRequest().setAttribute("joinlist", list);
		Struts2Utils.getRequest().setAttribute("custid", SpringSecurityUtils.getCurrentUser().getId());
		Struts2Utils.getRequest().setAttribute("fycount", fycount);
		return SUCCESS;
	}
	/***
	  * 线下招商会列表
	 * @return
	 */
	public String shopjoinlist(){
		getLscode();
		Map<String, Object> map = new HashMap<String, Object>(); 
		HashMap<String, Object> whereMap=new HashMap<String, Object>();
		HashMap<String, Object> sortMap=new HashMap<String, Object>();
		HashMap<String, Object> backMap =new HashMap<String, Object>();
		 
		
		if(StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))){
			fypage=Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		List<DBObject> list=baseDao.getList(PubConstants.SHOP_JOIN,whereMap,fypage,10,sortMap,backMap);
		fycount=baseDao.getCount(PubConstants.SHOP_JOIN,whereMap);
		Struts2Utils.getRequest().setAttribute("joinlist", list);
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("lscode", lscode);
		return "lists";
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
	public void upd() throws Exception { 
		DBObject db = baseDao.getMessage(PubConstants.SHOP_JOIN, _id); 
		String json = JSONObject.fromObject(db).toString();
		System.out.println("-----:"+json);
		Struts2Utils.renderJson(json, new String[0]);
	}
	
	
	public String  selectdetail() throws Exception { 
		DBObject db = baseDao.getMessage(PubConstants.SHOP_JOIN, _id); 
		Struts2Utils.getRequest().setAttribute("shopjoin", db);
		
		return "web";
		 
	}


	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		try {
			if(_id==null){
				_id=mongoSequence.currval(PubConstants.SHOP_JOIN); 
			} 
		 
			String content=Struts2Utils.getParameter("context"); 
			System.out.println("------------"+content);
			entity.set_id(_id);
		    entity.setContent(content);
			entity.setCreatedate(new Date()); 
			baseDao.insert(PubConstants.SHOP_JOIN, entity);
			addActionMessage("添加成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionMessage("添加失败");
		}
		return RELOAD;
	}
	
	 
	 

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub 
		try {
			baseDao.delete(PubConstants.SHOP_JOIN, _id);
			addActionMessage("删除成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addActionMessage("删除失败！");
			e.printStackTrace();
		}
		return RELOAD;
	}


	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		try {
			if(_id!=null){
				DBObject db=baseDao.getMessage(PubConstants.SHOP_JOIN, _id);
				entity= (ShopJoin) UniObject.DBObjectToObject(db, RollInfo.class);
			}else{
				entity=new ShopJoin();
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
	public ShopJoin getModel() {
		// TODO Auto-generated method stub
		return entity;
	}
	 
	 
     

}