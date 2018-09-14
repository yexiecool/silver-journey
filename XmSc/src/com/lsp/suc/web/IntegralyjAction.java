package com.lsp.suc.web;

import java.math.BigDecimal;
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
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.util.UniObject;
import com.lsp.pub.web.GeneralAction;
import com.lsp.suc.entity.IntegralYjInfo;
import com.lsp.website.service.WebsiteService;
import com.lsp.website.service.WwzService;
import com.mongodb.DBObject;

@Namespace("/suc")
@Results( { @Result(name ="reload", location = "integralyj.action", type = "redirect") })
public class IntegralyjAction extends GeneralAction<IntegralYjInfo>{

	private static final long serialVersionUID = -6784469775589971579L;
	@Autowired
	private BaseDao baseDao;
	private MongoSequence mongoSequence;	
	@Autowired
	public void setMongoSequence(MongoSequence mongoSequence) {
		this.mongoSequence = mongoSequence;
	}
	@Autowired
	private WwzService wwzService;
	
	private IntegralYjInfo entity = new IntegralYjInfo();
	private Long _id;
	
	@Override
	public String execute() throws Exception {
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		HashMap<String, Object> sortMap = new HashMap<String, Object>();
		HashMap<String, Object> backMap = new HashMap<String, Object>();
		sortMap.put("createdate", -1);
		String vipno = Struts2Utils.getParameter("vipno");
		if(StringUtils.isNotEmpty(vipno)){
			DBObject dbObject = wwzService.getWXuserVipNo(vipno);
			if(dbObject != null){
				whereMap.put("fromUserid", dbObject.get("_id").toString());
			}else{
				whereMap.put("vipno", vipno);
			}
			Struts2Utils.getRequest().setAttribute("vipno", vipno);
		}
		BigDecimal sum = new BigDecimal(0.00);
		List<DBObject> list = baseDao.getList(PubConstants.INTEGRALYJ_INFO, whereMap, fypage, 10, sortMap, backMap);
		for (DBObject dbObject : list) {
			dbObject.put("vip_no",wwzService.getVipNo(dbObject.get("fromUserid").toString()));
			dbObject.put("cust_no",wwzService.getVipNo(dbObject.get("custid").toString()));
			if(null!=dbObject) {
				sum = sum.add(new BigDecimal(dbObject.get("value").toString()));
			}
		}
		fycount = baseDao.getCount(PubConstants.INTEGRALYJ_INFO,whereMap);
		Struts2Utils.getRequest().setAttribute("fycount", fycount);
		Struts2Utils.getRequest().setAttribute("integralyjList", list);
		Struts2Utils.getRequest().setAttribute("sums", sum);
		return SUCCESS;
	}
	
	
	@Override
	public IntegralYjInfo getModel() {
		// TODO Auto-generated method stub
		return null;
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
		try {
			if(_id!=null){
				DBObject db=baseDao.getMessage(PubConstants.INTEGRALYJ_INFO, _id);
				entity= (IntegralYjInfo) UniObject.DBObjectToObject(db, IntegralYjInfo.class);
			}else{
				entity=new IntegralYjInfo();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void set_id(Long _id) {
		this._id = _id;
	}
}