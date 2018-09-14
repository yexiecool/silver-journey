package com.lsp.integral.web;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON; 
import com.lsp.integral.entity.InteSetting;
import com.lsp.pub.dao.BaseDao;
import com.lsp.pub.db.MongoSequence; 
import com.lsp.pub.entity.PubConstants;
import com.lsp.pub.util.BaseDecimal;
import com.lsp.pub.util.SpringSecurityUtils;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.util.SysConfig;
import com.lsp.pub.web.GeneralAction;
import com.lsp.shop.entiy.Useraddress;
import com.lsp.website.service.WwzService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 设置
 * 
 * @author lsp
 * 
 */
@Namespace("/integral")
@Results({ @Result(name = IntesettingAction.RELOAD, location = "intesetting.action", params = {"fypage", "%{fypage}" }, type = "redirect") })
public class IntesettingAction extends GeneralAction<InteSetting> {
	private static final long serialVersionUID = -6784469775589971579L;
	@Autowired
	private BaseDao baseDao;
	private InteSetting entity = new InteSetting();
	private String _id;
	
	public void set_id(String _id) {
		this._id = _id;
	}
	
	@Override
	public InteSetting getModel() {
		return entity;
	}

	@Override
	public String execute() throws Exception {
		DBObject db = baseDao.getMessage(PubConstants.INTEGRAL_INTESETTING, SysConfig.getProperty("custid"));
		System.out.println("db--->"+db);
		Struts2Utils.getRequest().setAttribute("entity", db);
		return SUCCESS;
	}
	
	@Override
	public String save() throws Exception {
		
		try {
			if (_id == null || _id.equals("")) {
				_id = SysConfig.getProperty("custid");
			}
			entity.set_id(_id); 
			entity.setCreatedate(new Date());
			if(entity.getNum() == null){
				entity.setNum("0");
			}
			if(entity.getNums() == null){
				entity.setNums("0");
			}
			if(entity.getNownum() == null){
				entity.setNownum("0");
			}
			if(entity.getNownums() == null){
				entity.setNownums("0");
			}
			baseDao.insert(PubConstants.INTEGRAL_INTESETTING, entity); 
			addActionMessage("成功添加!");
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage("抱歉,添加过程中出现异常!");
		}
		return RELOAD;
	}
	
	@Override
	public String delete() throws Exception {
		
		return RELOAD;
	}

	@Override
	public String input() throws Exception { 
		return "add";
	}

	@Override
	public String update() throws Exception { 

		return "add";
	}

	@Override
	protected void prepareModel() throws Exception {
		if (_id != null) { 
			DBObject db = baseDao.getMessage(PubConstants.INTEGRAL_INTESETTING, _id);

			entity = JSON.parseObject(db.toString(), InteSetting.class);
			entity.set_id((Long) db.get("_id"));
		} else {
			entity = new InteSetting();
		}
	}

	
	/**
	 * 账户预览
	 * @return
	 */
	public String  lists(){ 
		HashMap<String,Object>whereMap=new HashMap<>();
		HashMap<String,Object>sortMap=new HashMap<>();
		BasicDBList dblist = new BasicDBList();
		//下单支出
		dblist.add(new BasicDBObject("type", "shop_jfdh"));
		//商城转出交易所
		dblist.add(new BasicDBObject("type", "shop_tx"));
		//交易转入商城
		dblist.add(new BasicDBObject("type", "jyscz"));
		//提币量
		dblist.add(new BasicDBObject("type", "kj_tx"));
		// or判断
		whereMap.put("$or", dblist);
		sortMap.put("createdate", -1);
		BigDecimal shopJfdh = new BigDecimal(0);//下单支出
		BigDecimal shopTx = new BigDecimal(0);//商城转出交易所
		BigDecimal JYSCZ = new BigDecimal(0);//交易转入商城
		BigDecimal kjTx = new BigDecimal(0);//提币量
		
		BigDecimal CBSUM = new BigDecimal(0);//总产币
		
		BigDecimal LLBSUM = new BigDecimal(0);//LLB
		BigDecimal YJSUM = new BigDecimal(0);//佣金
		BigDecimal XFZHSUM = new BigDecimal(0);//消费账户
		BigDecimal scsy = new BigDecimal(0);//商城剩余（ppb）
		List<DBObject> list = baseDao.getList(PubConstants.INTEGRAL_INFO, whereMap, sortMap);
		for (DBObject dbObject : list) {
			switch (dbObject.get("type").toString()) {
			case "shop_jfdh"://下单支出
				if(null != dbObject.get("value") && 1 == Integer.parseInt(dbObject.get("state").toString())) {
					shopJfdh = shopJfdh.add(new BigDecimal(dbObject.get("value").toString()));
				}
				break;
			case "shop_tx"://商城转出交易所
				if(null != dbObject.get("value") && 1 == Integer.parseInt(dbObject.get("state").toString())) {
					shopTx = shopTx.add(new BigDecimal(dbObject.get("value").toString()));
				}
				break;
			case "jyscz"://交易转入商城(币)
				if(null !=dbObject.get("value") && 0 == Integer.parseInt(dbObject.get("state").toString())) {
					JYSCZ = JYSCZ.add(new BigDecimal(dbObject.get("value").toString()));
				}
				break;
			case "kj_tx"://提币量
				if(null !=dbObject.get("value") && 1 == Integer.parseInt(dbObject.get("state").toString())) {
					kjTx = kjTx.add(new BigDecimal(dbObject.get("value").toString()));
				}
				break;
			}
		}
		whereMap.clear();
		
		BasicDBList dblist1 = new BasicDBList();

		dblist1.add(new BasicDBObject("type", "ps_account"));//开通矿机
		dblist1.add(new BasicDBObject("type", "ps_recovery"));//回本后矿机
		dblist1.add(new BasicDBObject("type", "shop_bmzt"));//
		dblist1.add(new BasicDBObject("type", "shop_exchange"));//消费兑换
		dblist1.add(new BasicDBObject("type", "recommend_give"));//业绩赠送
		// or判断
		whereMap.put("$or", dblist1);
		List<DBObject> list1 = baseDao.getList(PubConstants.INTEGRAL_INFO, whereMap, sortMap);
		for (DBObject dbObject : list1) {
			if(null !=dbObject.get("value") && 0 == Integer.parseInt(dbObject.get("state").toString())) {
				CBSUM = CBSUM.add(new BigDecimal(dbObject.get("value").toString()));
			}
		}
		whereMap.clear();
		List<DBObject> db = baseDao.getList(PubConstants.SUC_INTEGRALRECORD, whereMap,sortMap);//佣金、LLB、PPB、XFQB
		for (DBObject dbObject : db) {
			if(null !=dbObject.get("llkyvalue")) {
				LLBSUM = LLBSUM.add(new BigDecimal(dbObject.get("llkyvalue").toString()));
			}
			if(null !=dbObject.get("yjvalue")) {
				YJSUM = YJSUM.add(new BigDecimal(dbObject.get("yjvalue").toString()));
			}
			if(null !=dbObject.get("prostore")) {
				XFZHSUM = XFZHSUM.add(new BigDecimal(dbObject.get("prostore").toString()));
			}
			if(null != dbObject.get("uservalue")) {
				scsy = scsy.add(new BigDecimal(dbObject.get("uservalue").toString()));
			}
		}
		Struts2Utils.getRequest().setAttribute("shopJfdh",shopJfdh);
		Struts2Utils.getRequest().setAttribute("shopTx",shopTx);
		Struts2Utils.getRequest().setAttribute("JYSCZ",JYSCZ);
		Struts2Utils.getRequest().setAttribute("kjTx",kjTx);
		
		Struts2Utils.getRequest().setAttribute("LLBSUM",LLBSUM);
		Struts2Utils.getRequest().setAttribute("YJSUM",YJSUM);
		Struts2Utils.getRequest().setAttribute("XFZHSUM",XFZHSUM);
		Struts2Utils.getRequest().setAttribute("scsy",scsy);
		Struts2Utils.getRequest().setAttribute("CBSUM",CBSUM);
		return "lists"; 
	} 
}
