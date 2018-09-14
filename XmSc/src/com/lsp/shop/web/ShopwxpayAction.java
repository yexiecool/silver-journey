package com.lsp.shop.web;

import java.util.HashMap;
import java.util.List;

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
import com.lsp.pub.util.UniObject;
import com.lsp.pub.web.GeneralAction;
import com.lsp.shop.entiy.WxPayDetail;
import com.mongodb.DBObject;
/**
 * 微信支付的订单记录
 * @author zp
 *
 */
@Namespace("/shop")
@Results( { @Result(name ="reload", location = "shopwxpay.action", type = "redirect") })
public class ShopwxpayAction extends GeneralAction<WxPayDetail>{


	private static final long serialVersionUID = -6784469775589971579L;
	@Autowired
	private BaseDao baseDao;
	private MongoSequence mongoSequence;	
	@Autowired
	public void setMongoSequence(MongoSequence mongoSequence) {
		this.mongoSequence = mongoSequence;
	}  
	private WxPayDetail entity=new WxPayDetail();
	private Long _id;


	@Override
	public String execute() throws Exception {
 
		HashMap<String, Object> sortMap =new HashMap<String, Object>();
		HashMap<String, Object> whereMap =new HashMap<String, Object>();
		HashMap<String, Object> backMap =new HashMap<String, Object>();

		sortMap.put("createdate", -1); 
		
		
		String status=Struts2Utils.getParameter("sel_state");
		
	 
		if(StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))){
			fypage=Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		if(StringUtils.isNotEmpty(status)) {
			whereMap.put("status", Integer.parseInt(status));
			Struts2Utils.getRequest().setAttribute("sel_state", status);
		} 
		List<DBObject> list=baseDao.getList(PubConstants.WXPAYDETAIL,whereMap,fypage,10,sortMap,backMap);
		fycount=baseDao.getCount(PubConstants.WXPAYDETAIL,whereMap);
		Struts2Utils.getRequest().setAttribute("paylist", list);
		Struts2Utils.getRequest().setAttribute("custid", SpringSecurityUtils.getCurrentUser().getId());
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
	 
		return "add";
	}
	 
	
	
 

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
	 
		return RELOAD;
	}
	
	 
	 

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub 
	 
		return RELOAD;
	}


	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		try {
			if(_id!=null){
				DBObject db=baseDao.getMessage(PubConstants.WXPAYDETAIL, _id);
				entity= (WxPayDetail) UniObject.DBObjectToObject(db, WxPayDetail.class);
			}else{
				entity=new WxPayDetail();
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
	public WxPayDetail getModel() {
		// TODO Auto-generated method stub
		return entity;
	}
	 
	 
     

}
