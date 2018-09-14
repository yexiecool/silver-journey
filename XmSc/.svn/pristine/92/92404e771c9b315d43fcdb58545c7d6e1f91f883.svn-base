package com.lsp.shop.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lsp.pub.dao.BaseDao;
import com.lsp.pub.db.MongoSequence;
import com.lsp.pub.entity.PubConstants;
import com.lsp.pub.util.DictionaryUtil;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.web.GeneralAction;
import com.lsp.shop.entiy.ShopMb;
import com.lsp.website.service.WwzService;
import com.mongodb.DBObject;

import net.sf.json.JSONArray;

@Namespace("/shop")
@Results({
		@org.apache.struts2.convention.annotation.Result(name = "reload", location = "shopxm.action", type = "redirect") })
public class ShopxmAction extends GeneralAction {
	private static final long serialVersionUID = -7868703949557549292L;

	@Autowired
	private BaseDao baseDao;
	@Autowired
	private WwzService wwzService;
	@Autowired
	private DictionaryUtil dictionaryUtil;
	private MongoSequence mongoSequence;

	@Autowired
	public void setMongoSequence(MongoSequence mongoSequence) {
		this.mongoSequence = mongoSequence;
	}

	/**
	 * 主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception {
		getLscode();
		Struts2Utils.getRequest().setAttribute("lscode", lscode);
		Struts2Utils.getRequest().setAttribute("custid", custid);
		String comid = Struts2Utils.getParameter("comid"); 
		HashMap<String, Object> sortMap =new HashMap<String, Object>();
		HashMap<String, Object> whereMap =new HashMap<String, Object>();
		HashMap<String, Object> backMap =new HashMap<String, Object>();
		sortMap.put("sort", -1); 
	    //查询熊猫店铺
		whereMap.put("name", "熊猫超市");
		DBObject db = baseDao.getMessage(PubConstants.SHOP_SHOPMB, whereMap,backMap,sortMap);
		ShopMb shopmb = JSON.parseObject(db.toString(), ShopMb.class);
		long id =(Long) db.get("_id");
		shopmb.set_id(id);
		System.out.println("-------------"+shopmb.get_id());
		String type= "shopmb-"+id;
		System.out.println("-------------type="+type);
		//查询滚动图片
		whereMap.clear();
		whereMap.put("type", type);	
		List<DBObject> list=baseDao.getList(PubConstants.SUC_SLIDE,whereMap,fypage,5,sortMap,backMap);
		fycount=baseDao.getCount(PubConstants.SUC_SLIDE,whereMap);
		Struts2Utils.getRequest().setAttribute("llist", list);
		
		//查询分类
		whereMap.clear();

		whereMap.put("parentid", id);
		Struts2Utils.getRequest().setAttribute("parentid",  id);
	 
	    
		List<DBObject> typelist = baseDao.getList(PubConstants.SHOP_SHOPTYPE,whereMap,fypage,10,sortMap);
		fycount=baseDao.getCount(PubConstants.SHOP_SHOPTYPE,whereMap);
		Struts2Utils.getRequest().setAttribute("typelist", typelist);
		System.out.println("------------------*******"+typelist.toString());
		//查询栏目
		
		List<DBObject> columnlist = baseDao.getList(PubConstants.SHOP_COLUMN,whereMap,fypage,10,sortMap);
	 
		List<DBObject> cplist =  new ArrayList();
		
		for(int i=0;i<columnlist.size();i++) {
			DBObject  object=columnlist.get(i);
			whereMap.clear();
			whereMap.put("columnid",Long.parseLong( object.get("_id").toString()));
			List<DBObject> plist = baseDao.getList(PubConstants.DATA_PRODUCT,whereMap,fypage,10,sortMap);
			object.put("products", plist);
			
			cplist.add(object);
		}
		
		Struts2Utils.getRequest().setAttribute("cplist", cplist);
		System.out.println("=========:"+cplist.toString());
		System.out.println("----------------:"+typelist.size());
		return "index";
				
	 
 
 
	 
	}

	/**
	 * 获取商品分类图片
	 */
	public void geTypePicture(){
		Map<String, Object> sub_map = new HashMap<String, Object>();
		HashMap<String, Object> sortMap =new HashMap<String, Object>();
		HashMap<String, Object> whereMap =new HashMap<String, Object>();
		HashMap<String, Object> backMap =new HashMap<String, Object>();
		sortMap.put("sort", -1); 
		whereMap.put("name", "会员区");
		DBObject db = baseDao.getMessage(PubConstants.SHOP_SHOPMB, whereMap,backMap,sortMap);
		ShopMb shopmb = JSON.parseObject(db.toString(), ShopMb.class);
		long id =(Long) db.get("_id");
		shopmb.set_id(id);
		whereMap.put("parentid", id);
		Struts2Utils.getRequest().setAttribute("parentid",  id);
		List<DBObject> typelist = baseDao.getList(PubConstants.SHOP_SHOPTYPE,whereMap,fypage,10,sortMap);
		if(typelist.size()>0){
			sub_map.put("typelist",typelist);
			sub_map.put("state", 0);
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}
	
	
	/**
	 * 获取会员区商品轮循图片
	 */
	public void geRobinPicture(){
		Map<String, Object> sub_map = new HashMap<String, Object>();
		HashMap<String, Object> sortMap =new HashMap<String, Object>();
		HashMap<String, Object> whereMap =new HashMap<String, Object>();
		HashMap<String, Object> backMap =new HashMap<String, Object>();
		String type = "shopmb-"+14;
		sortMap.put("sort", -1); 
		whereMap.clear();
		whereMap.put("type", type);	
		List<DBObject> list=baseDao.getList(PubConstants.SUC_SLIDE,whereMap,fypage,5,sortMap,backMap);
		if(list.size()>0){
			sub_map.put("list", list);
			sub_map.put("state", 0);
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update() throws Exception {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	 
}