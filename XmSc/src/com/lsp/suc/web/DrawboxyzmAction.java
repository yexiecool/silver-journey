package com.lsp.suc.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lsp.pub.dao.BaseDao;
import com.lsp.pub.db.MongoSequence;
import com.lsp.pub.entity.PubConstants;
import com.lsp.pub.util.DateFormat;
import com.lsp.pub.util.SpringSecurityUtils;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.util.SysConfig;
import com.lsp.pub.util.UniObject;
import com.lsp.pub.web.GeneralAction;
import com.lsp.rechargerecord.entity.RechargeRecord;
import com.lsp.shop.entiy.ProductInfo;
import com.lsp.suc.entity.DrawboxYzm;
import com.lsp.sys.security.CustomerUser;
import com.lsp.website.service.WwzService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import net.sf.json.JSONArray;
 
/**
 * 图文资源管理
 * @author lsp
 *
 */
@Namespace("/suc")
@Results( { @Result(name = DrawboxyzmAction.RELOAD, location = "drawboxyzm.action",params={"wid", "%{wid}"}, type = "redirect") })
public class DrawboxyzmAction extends GeneralAction<DrawboxYzm> {

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
	private DrawboxYzm entity=new DrawboxYzm();
	private String _id;


	@Override
	public String execute() throws Exception {
		HashMap<String, Object> sortMap =new HashMap<String, Object>();
		HashMap<String, Object> whereMap =new HashMap<String, Object>();
		
		String wid=Struts2Utils.getParameter("wid");
		whereMap.put("wid", Long.parseLong(wid));
		
		sortMap.put("sort", 1);
		fycount=baseDao.getCount(PubConstants.WHD_DRAWBOXYZM, whereMap);
		if(StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))){
			fypage=Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		List<DBObject> list=baseDao.getList(PubConstants.WHD_DRAWBOXYZM,whereMap,fypage,10, sortMap);
		for(DBObject db:list){
			if(db.get("fromUser")!=null){
				
			
			DBObject user=baseDao.getMessage(PubConstants.DATA_WXUSER, db.get("fromUser").toString());
			if(user!=null){
				if(user.get("headimgurl")!=null){
					db.put("headimgurl", user.get("headimgurl").toString());
				}
				if(user.get("tel")!=null){
					db.put("tel", user.get("tel").toString());
				}
				if(user.get("no")!=null){
					db.put("no", user.get("no").toString());
				}
				if(user.get("nickname")!=null){
					db.put("nickname", user.get("nickname").toString());
				}
				if(user.get("name")!=null){
					db.put("name", user.get("name").toString());
				}
			}
			}
				
		}
		Struts2Utils.getRequest().setAttribute("predeList", list);
		
		Struts2Utils.getRequest().setAttribute("wid", wid);
		return SUCCESS;
	}
	
	
	@Override
	public String delete() throws Exception {
		try {
			baseDao.delete(PubConstants.WHD_DRAWBOXYZM,_id);
			addActionMessage("成功删除!");
			
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage("抱歉,删除过程中出现异常!");
		}
		return RELOAD;
	}
	public void delall() throws Exception {
		String[] id=Struts2Utils.getParameter("ids").split(",");
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		BasicDBList dbList=new BasicDBList();  //翻译数组对象
		for(int i=0;i<id.length;i++){
			if(StringUtils.isEmpty(id[i])||id[i].equals("ck")){
				
			}else{
				dbList.add(id[i]);
			}
		}
		whereMap.put("_id", new BasicDBObject("$in",dbList));
		baseDao.delete(PubConstants.WHD_DRAWBOXYZM, whereMap);
		
	}
 
	@Override
	public String input() throws Exception {
		
		return "add";
	}

		public String imp() throws Exception {
			
			return "imp";
		}
	
	@Override
	public String update() throws Exception {	
		
		return "add";
	}
	@Override
	protected void prepareModel() throws Exception {
		if (_id != null) {
			//有custId查出来 用户信息
			entity = (DrawboxYzm)UniObject.DBObjectToObject(baseDao.getMessage(PubConstants.WHD_DRAWBOXYZM,_id),DrawboxYzm.class);
		} else {
			entity = new DrawboxYzm();
		}
	}
	


	@Override
	public String save() throws Exception {
		//注册业务逻辑
		try {
			
			
			entity.set_id(_id);
		
			baseDao.insert(PubConstants.WHD_DRAWBOXYZM,entity);
			addActionMessage("成功添加!");

		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage("抱歉,添加过程中出现异常!");
		}
		
		return RELOAD;
	}
	
	@Override
	public DrawboxYzm getModel() {
		return entity;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public void  cz(){
		RechargeRecord recharge = new RechargeRecord();
		if(SpringSecurityUtils.getCurrentUser().getId().equals(SysConfig.getProperty("custid"))){
			String id=Struts2Utils.getParameter("id");
			String value=Struts2Utils.getParameter("value");
			wwzService.addjfoid(value, wwzService.getfromUseridVipNo(id), "xtcz",SysConfig.getProperty("custid"), 0, 1, 0, null,"盼盼币充值");
		} 
	}
	
	//盼盼币充值
	public void recharge(){
		RechargeRecord recharge = new RechargeRecord();
		Map<String, Object> sub_map = new HashMap<>();
//		if(!"".equals(SpringSecurityUtils.getCurrentUser().getId()) && SpringSecurityUtils.getCurrentUser().getId() != null ){
//			String id=Struts2Utils.getParameter("vipno");
//			String value=Struts2Utils.getParameter("quantity");
//			boolean bool = wwzService.addjfoid(value, wwzService.getfromUseridVipNo(id), "xtcz",SysConfig.getProperty("custid"), 0, 1, 0, null,"盼盼币充值");
//			if(bool){
//				recharge.set_id(mongoSequence.currval(PubConstants.RECHARGE_RECORD));
//				recharge.setQuantity(Double.valueOf(value));
//				CustomerUser cust =(CustomerUser)SpringSecurityUtils.getCurrentUser();
//				recharge.setRechargeId(cust.getId());
//				recharge.setRechargeName(cust.getLoginname());
//				wwzService.getWXuserVipNo(id);
//				recharge.setVipName(wwzService.getWXuserVipNo(id).get("userName").toString());
//				recharge.setVipno(id);
//				recharge.setRechargeDate(new Date());
//				baseDao.insert(PubConstants.RECHARGE_RECORD,recharge);
//				sub_map.put("state", 0);// 充值成功
//			}else{
//				sub_map.put("state", 1);// 充值失败
//			}
//			
//		}else{
//			sub_map.put("state", 2);
//		}
//		String json = JSONArray.fromObject(sub_map).toString();
//		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}
	
	/**
	 * 充值记录查询
	 * @return
	 */
	public String getrecharge(){
		HashMap<String, Object>whereMap=new HashMap<String, Object>();
		HashMap<String, Object>sortMap=new HashMap<String, Object>();
//		if(StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))){
//			fypage=Integer.parseInt(Struts2Utils.getParameter("fypage"));
//		} 
//		sortMap.put("rechargeDate", -1);
//		BasicDBObject dateCondition = new BasicDBObject();
//		String sel_insdate = Struts2Utils.getParameter("sel_insdate");
//		String sel_enddate = Struts2Utils.getParameter("sel_enddate");
//		String vipno = Struts2Utils.getParameter("vipno");
//		if (StringUtils.isNotEmpty(sel_insdate)) {
//			dateCondition.append("$gte", DateFormat.getFormat(sel_insdate));
//			whereMap.put("rechargeDate", dateCondition);
//			Struts2Utils.getRequest().setAttribute("sel_insdate", sel_insdate);
//		}
//		if (StringUtils.isNotEmpty(sel_enddate)) {
//			dateCondition.append("$lte", DateFormat.getFormat(sel_enddate));
//			whereMap.put("rechargeDate", dateCondition);
//			Struts2Utils.getRequest().setAttribute("sel_enddate", sel_enddate);
//		}
//		
//		if (StringUtils.isNotEmpty(vipno)) {
//			
//			whereMap.put("vipno", vipno);
//			Struts2Utils.getRequest().setAttribute("vipno", vipno);
//		}
//		List<DBObject> list = baseDao.getList(PubConstants.RECHARGE_RECORD, whereMap,fypage,10,sortMap);
//		Struts2Utils.getRequest().setAttribute("list", list);
//		fycount=baseDao.getCount(PubConstants.RECHARGE_RECORD, whereMap);
//		Struts2Utils.getRequest().setAttribute("fycount",fycount);
		return "recharge";
	}
	
	public String addrecharge(){
		return "add";
	}
	public void  changePro(){
		if(SpringSecurityUtils.getCurrentUser().getId().equals(SysConfig.getProperty("custid"))){
			String type=Struts2Utils.getParameter("type");
			String cmid=Struts2Utils.getParameter("cmid");
			String totype=Struts2Utils.getParameter("totype");
			HashMap<String, Object>whereMap=new HashMap<>();
			whereMap.put("goodstype", Integer.parseInt(type));
			whereMap.put("comid",Long.parseLong(cmid));
			List<DBObject>list=baseDao.getList(PubConstants.DATA_PRODUCT, whereMap, null);
			for (DBObject dbObject : list) {
				ProductInfo productInfo=(ProductInfo) UniObject.DBObjectToObject(dbObject, ProductInfo.class);
				productInfo.set_id(Long.parseLong(dbObject.get("_id").toString()));
				productInfo.setGoodstype(Integer.parseInt(totype));
				baseDao.insert(PubConstants.DATA_PRODUCT,productInfo);
			}
		}
	}
	public void dels(){
		if(SpringSecurityUtils.getCurrentUser().getId().equals(SysConfig.getProperty("custid"))){
			String cmid=Struts2Utils.getParameter("cmid");
			String type=Struts2Utils.getParameter("type");
			HashMap<String, Object>whereMap=new HashMap<>();
			whereMap.put("goodstype", Integer.parseInt(type));
			whereMap.put("comid",Long.parseLong(cmid));
			baseDao.delete(PubConstants.DATA_PRODUCT, whereMap);
		}
	
	}	
}
