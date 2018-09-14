package com.lsp.user.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.record.formula.functions.If;
import org.apache.poi.hssf.record.formula.functions.Int;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.directwebremoting.servlet.BaseDtoAllHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.User;

import com.lsp.integral.entity.InteSetting;
import com.lsp.pub.dao.BaseDao;
import com.lsp.pub.db.MongoDbUtil;
import com.lsp.pub.db.MongoSequence;
import com.lsp.pub.entity.PubConstants;
import com.lsp.pub.util.BaseDecimal;
import com.lsp.pub.util.SpringSecurityUtils;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.util.SysConfig;
import com.lsp.pub.util.UniObject;
import com.lsp.pub.web.GeneralAction;
import com.lsp.suc.entity.ActivityYd;
import com.lsp.sys.security.CustomerUser;
import com.lsp.user.entity.Relations;
import com.lsp.user.entity.UserInfo;
import com.lsp.user.entity.UserRelation;
import com.lsp.website.service.WwzService;
import com.mongodb.DBObject;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import net.sf.json.JSONArray;

/***
 * 用户关系
 * 
 * @author 朱攀
 *
 */
@Namespace("/user")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "reload", location = "relation.action", params = {
		"fypage", "%{fypage}" }, type = "redirect") })
public class RelationAction extends GeneralAction<UserRelation> {
	private static final long serialVersionUID = -6784469775589971579L;

	private String results = "0";
	@Autowired
	private BaseDao basedao;
 
	private UserRelation entity = new UserRelation();
	private MongoSequence mongoSequence;
	@Autowired
	private WwzService wwzservice;
	//用户关系
	private UserRelation currrelarion =null  ;
 
	
	@Autowired
	public void setMongoSequence(MongoSequence mongoSequence) {
		this.mongoSequence = mongoSequence;
	}

 

	@Override
	public UserRelation getModel() {
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
		// TODO Auto-generated method stub

	}
	
	/**
	 * 更新用户推荐人
	 */
	public void updatereferee () {
		
	}
	
	
	/**
	 * 根据会员编号返回子节点的为空的节点 ,返回插入的节点
	 * @param parentnumber //推荐人的会员编号
	 * @param userrelation //返回插入节点的对象
	 * @return
	 */
	
	public UserRelation getbyparentnumber(String parentnumber) {
		 
		//根据输入的父类的会员编号查询
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("parentnumber", parentnumber);
		DBObject db = basedao.getMessage(PubConstants.USER_RELATION, whereMap);
		
		if(  db!=null) {
			UserRelation curr = (UserRelation) UniObject.DBObjectToObject(db, UserRelation.class);
			String lnumber =curr.getLeftnumber();
			String rnumber =curr.getRightnumber();
			if(lnumber!=null && !lnumber.equals("")  && rnumber!=null && !rnumber.equals("")  ) {
				//如果左右去的人都有，判断当前左右去的业绩，返回业绩小的节点
				String left =getAchievementbyNumber(lnumber);
				String right =getAchievementbyNumber(rnumber);
				System.out.println("----左区业绩："+left);
				System.out.println("----右区业绩："+right);
				//判断大小区
				if(Double.valueOf(left) <= Double.valueOf(right)) { //左区为小区 如果都没有业绩获取业绩相等，默认左区
					getbyparentnumber( lnumber);
				}else { //右区为小区
					getbyparentnumber( rnumber);
				}
				
			}else {  //左右区有一个空位
				currrelarion = curr; 
				 
			}
			
		}else {  //为null 的话说明没有推荐关系
		 
			currrelarion =new UserRelation();
			currrelarion.setParentnumber(parentnumber);
			 
		}
		return currrelarion;
		
	}
	
	
	
	/**
	 *  ajax 请求根据会员编号返回子节点的为空的节点 ,返回插入的节点
	 * @param parentnumber //推荐人的会员编号
	 * @param userrelation //返回插入节点的对象
	 * @return
	 */
	public void ajaxgetbyparentnumber() {
		getLscode();
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		Map<String, Object> sub_map = new HashMap<>();
		boolean res = false;
		String parentnumber =  Struts2Utils.getParameter("parentnumber") ;  //获取会员编号
		//根据输入的父类的会员编号查询
		UserRelation currrelarion= getbyparentnumber(parentnumber);
		String leftN = currrelarion.getLeftnumber();
		String rightN = currrelarion.getRightnumber();
		String fromno = wwzservice.getVipNo(fromUserid);
		MongoDbUtil mongoDbUtil=new MongoDbUtil();
		whereMap.put("parentnumber", currrelarion.getParentnumber());
		DBObject db = mongoDbUtil.findOne(PubConstants.USER_RELATION, whereMap);
		if((null == leftN || "".equals(leftN)) && (null == rightN || "".equals(rightN))) {
			//新增记录
			if(null == db) {
				UserRelation relarion   =new UserRelation();
				relarion.setCreatetime(new Date());
				relarion.setLeftnumber(fromno);
				relarion.setParentnumber(currrelarion.getParentnumber());
				int n = basedao.insert(PubConstants.USER_RELATION, relarion);
				if(n>0) {
					res = true;
				}
			}else {
				UserRelation relarion = (UserRelation) UniObject.DBObjectToObject(db, UserRelation.class);
				relarion.setLeftnumber(fromno);
				relarion.set_id(db.get("_id"));
				int n = basedao.insert (PubConstants.USER_RELATION, relarion);
				if(n>0) {
					res = true;
				}
			}
		}else{
			if(null == leftN || "".equals(leftN)) {//修改记录左区
				UserRelation relarion = (UserRelation) UniObject.DBObjectToObject(db, UserRelation.class);
				relarion.setLeftnumber(fromno);
				relarion.set_id(db.get("_id"));
				int n = basedao.insert (PubConstants.USER_RELATION, relarion);
				if(n>0) {
					res = true;
				}
			}else if (null == rightN || "".equals(rightN)) {//修改记录右区
				UserRelation relarion = (UserRelation) UniObject.DBObjectToObject(db, UserRelation.class);
				relarion.setRightnumber(fromno);
				relarion.set_id(db.get("_id"));
				//int n = mongoDbUtil.insertUpdate(PubConstants.USER_RELATION, relarion);
				int n = basedao.insert (PubConstants.USER_RELATION, relarion);
				if(n>0) {
					res = true;
				}
			}
		}
		if(res) {
			whereMap.clear();
			whereMap.put("_id", fromUserid);
			DBObject db1 = mongoDbUtil.findOne(PubConstants.USER_INFO, whereMap);
			UserInfo user = (UserInfo) UniObject.DBObjectToObject(db1, UserInfo.class);
			user.setRecommend(1);
			user.setRenumber(Long.valueOf(parentnumber));
			mongoDbUtil.insertUpdate(PubConstants.USER_INFO, user);
		}
		sub_map.put("status", res);
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}
	 
	/**
	 * 根据父级的会员编号获取对应节点的用户列表
	 * @param parentnumber 父节点的会员编号
	 * @param userlist  //返回的list集合.传一个空的list过来即可 --List 是有序的数组
	 * @return
	 */
	public  List<DBObject> getUserList(List<DBObject> userlist,String   parentnumber){
		//根据输入的父类的会员编号查询
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("parentnumber", parentnumber);
		DBObject db = basedao.getMessage(PubConstants.USER_RELATION, whereMap);
		if(db!=null) {
			UserRelation curr = (UserRelation) UniObject.DBObjectToObject(db, UserRelation.class);
			HashMap<String,Object> userwhereMap=new HashMap<>();
			if(curr.getLeftnumber()!=null && curr.getLeftnumber()!="" ) {
				userwhereMap.clear();
				userwhereMap.put("no", curr.getLeftnumber());
				DBObject dbObject= basedao.getMessage(PubConstants.USER_INFO, userwhereMap);
				if(dbObject!=null) {
					userlist.add(dbObject);
				}
			} 
			if(curr.getRightnumber()!=null && curr.getRightnumber()!="") { 
				userwhereMap.put("no", curr.getRightnumber());
				DBObject dbObject= basedao.getMessage(PubConstants.USER_INFO, userwhereMap);
				if(dbObject!=null) {
					userlist.add(dbObject);
				}
			} 
			getUserList( userlist,curr.getLeftnumber()); //递归左区
			getUserList(userlist,curr.getRightnumber()); //递归右区
		} 
		return userlist;  
	}
	
	
	/**
	 * 共享奖 3000 10层 ，5000 15层 ，10000 20层
	 * @param parentnumber 父节点的会员编号
	 * @param userlist  //返回的list集合
	 * @param count 循环到几层
	 * @return
	 */
	public  List<DBObject> getUserListbycount(String   parentnumber,int count){
		//根据输入的父类的会员编号查询
		List<DBObject> list =new  ArrayList<>();
		List<DBObject> getlist =getUserList(list, parentnumber);
		int listsize = 0; //存储获取用户的长度
		if(getlist!=null) {
			listsize=list.size();
		}
		//根据输入的层级获取要取得的用户列表 Math.pow(2,n) 标识2的n次方
		int  aa  =(int) Math.pow(2,count);
		if(listsize<= aa) { //根据条件查询的用户列表小于当前的查询数量直接返回
			return getlist;
		}else {
			List<DBObject> currlist =new ArrayList<>(); 
			currlist.addAll(getlist.subList(0, aa));//按下标截取，再添加 aa标识截取的长度
			return currlist;
		}
	 
	}
	
	
	/**
	 * 根据输入的会员编号，获取该区业绩(计算本人业绩 =本人的业绩 + 本人节点下所有人的业绩 )
	 * 个人业绩包含：是否是代理或者报单中心,如果是 他的角色对应的值 + 会员区已完成的订单累计 
	 * @param number 当前节点的会员编号
	 * @return 业绩
	 * 
	 */
	public  String getAchievementbyNumber(String number){
		String  results="0";  //返回的业绩
		//获取当前会员节点下所有的会员列表
		List<DBObject> list =new ArrayList<>(); //存取返回的list
		List<DBObject> userlist =getUserList(list, number);
		//获取该会员的用户信息
		HashMap<String,Object>whereMap=new HashMap<>();
		whereMap.put("no", number);
		DBObject userdbObject= basedao.getMessage(PubConstants.USER_INFO, whereMap);
		
		//判断如果该用户没有推会员，计算自己的业绩
		if(userlist ==null) {
			userlist =new ArrayList<>();
		}
		//判断如果该会员信息无误把自己的信息加入list里面循环计算业绩
		if(userdbObject!=null ) {
			userlist.add(userdbObject);
		}
		 
		//判断userlist的大小，开始循环计算业绩
		System.out.println("-----计算业绩方法需要计算循环的次数："+userlist.size()); 
		System.out.println("*************");
		//获取系统设置
		whereMap.clear();
		whereMap.put("_id", SysConfig.getProperty("custid"));
		DBObject db = basedao.getMessage(PubConstants.INTEGRAL_INTESETTING, whereMap);
		InteSetting sett=null;
		if(db!=null){
			sett = (InteSetting) UniObject.DBObjectToObject(db, InteSetting.class);
		}
		if(userlist.size() > 0) {
			for (DBObject dbObject : userlist) { 
				if(dbObject.get("agentLevel")!=null){
					System.out.println("////当前用户的角色"+dbObject.get("agentLevel"));
					int level=Integer.parseInt(dbObject.get("agentLevel").toString());
					String  currmoney ="0.0";    //统计代理奖
					//统计本身业绩
					System.out.println(sett);
					if(sett!=null){
						
						if(level==1){
							//省
							currmoney= sett.getReturnProvince()+"" ;
						}else if(level==2){
							//市
							currmoney= sett.getReturnCity()+"" ;
						}else if(level==3){
							//县
							currmoney= sett.getReturnCounty()+"" ;
						}else if(level==4){
							//部门
							currmoney=  sett.getReturnDept()+"" ;
						}
					}
					//计算网体业绩的10%
					if(wwzservice.stringToInt(currmoney) > 0) {
						currmoney =BaseDecimal.division(currmoney,"10") ;
						System.out.println("----------代理的10%："+currmoney);
					}
					//统计业绩
					results = BaseDecimal.add(results, currmoney);
					//统计订单业绩
					whereMap.clear();
					whereMap.put("fromUserid",dbObject.get("_id").toString());
					whereMap.put("state", 4);
					List<DBObject>orderlist=basedao.getList(PubConstants.WX_ORDERFORM, whereMap,null);
					for (DBObject dbObject2 : orderlist) {
						if(dbObject2!=null &&   !dbObject2.get("contri_money").toString().equals("")) {
							results=BaseDecimal.add(results,dbObject2.get("contri_money").toString());
						}
					}
				} 
				 
			}
		}
		return results; 
	}
	
	
	
	
	
	
	 
	
	
	
	
	/**
	 * 根据父级的会员编号获取 二叉树集合
	 * @count 存储用户层级关系
	 * @param parentnumber 查询的会员编号
	 * @param relationlist  //返回的list集合.传一个空的list过来即可 --List 是有序的数组
	 * @param 指定查询的层级
	 * @return
	 */
	 
	public  List<Relations> getrelationList(int count ,List<Relations> relationlist,String   parentnumber ,int layer ){
		
		//根据输入的父类的会员编号查询
		if(count < layer) { //默认获取三级节点 count默认传的是0 
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("parentnumber", parentnumber);
		DBObject db = basedao.getMessage(PubConstants.USER_RELATION, whereMap);
		count ++;
		if(db!=null) {
			
			UserRelation curr = (UserRelation) UniObject.DBObjectToObject(db, UserRelation.class);
			String lnumber =curr.getLeftnumber();
			String rnumber =curr.getRightnumber();
			if(lnumber!=null && lnumber!="" ) { //统计左区信息
				 List<DBObject> aacurrlist =new ArrayList<>();
				 List<DBObject> currlist =getUserList(aacurrlist, lnumber);
				 int number =0;
				 if(currlist!=null) {
					 number =currlist.size();
				 }
				 
				 Relations relations =new Relations();
				 relations.setPnumber(curr.getParentnumber());
				 relations.setNumber(lnumber);
				 relations.setHierarchy(count);
				 relations.setPosition (1);
				 relations.setMoney(Double.valueOf(getAchievementbyNumber(lnumber)));
				 relations.setAgentLevel(wwzservice.getAgentLevelByVipNo(lnumber));
				 relations.setCount( number);
				 relationlist.add(relations);
			} else {
				 Relations rr =new Relations();
				 rr.setPnumber("");
				 rr.setNumber("");
				 rr.setHierarchy(count);
				 rr.setPosition (1);
				 rr.setMoney(0.00);
				 rr.setCount(0);
				 relationlist.add(rr ); //站位
			}
			if(rnumber!=null && rnumber!="") {  //统计右区信息
				List<DBObject> aacurrlist =new ArrayList<>();
				 List<DBObject> currlist =getUserList(aacurrlist, rnumber);
				 int number =0;
				 if(currlist!=null) {
					 number =currlist.size();
				 }
				 Relations relations =new Relations();
				 relations.setPnumber(curr.getParentnumber());
				 relations.setNumber(rnumber);
				 relations.setHierarchy(count);
				 relations.setPosition (2);
				 relations.setAgentLevel(wwzservice.getAgentLevelByVipNo(rnumber));
				 relations.setMoney(Double.valueOf(getAchievementbyNumber(rnumber)));
				 relations.setCount( number);
				 relationlist.add(relations);
			} else {
				 Relations rr =new Relations();
				 rr.setPnumber("");
				 rr.setNumber("");
				 rr.setHierarchy(count);
				 rr.setPosition (2);
				 rr.setMoney(0.00);
				 rr.setCount(0);
				 relationlist.add(rr ); //站位
				 
			} 
			getrelationList(count ,relationlist, lnumber,layer); //递归左区
			getrelationList(count ,relationlist, rnumber,layer); //递归右区
			System.out.println("------------------count:"+count);
		} else {
			//站位 左右各自穿一个空对象
			 Relations ll =new Relations();
			 ll.setPnumber("");
			 ll.setNumber("");
			 ll.setHierarchy(count);
			 ll.setPosition (1);
			 ll.setMoney(0.00);
			 ll.setCount(0);
			 relationlist.add(ll ); //站位
			 
			 Relations rr =new Relations();
			 rr.setPnumber("");
			 rr.setNumber("");
			 rr.setHierarchy(count);
			 rr.setPosition (2);
			 rr.setMoney(0.00);
			 rr.setCount(0);
			 relationlist.add(rr ); //站位
			
		}
		
		}
		
		return relationlist;  
	}
	
	
	
	/**
	 * 查询二叉树等级编号的信息
	 * @param number 
	 * @return
	 */
	public   Relations  getrelation(String  number){
			 Relations relation =new Relations();
			 List<DBObject> aacurrlist =new ArrayList<>();
			 List<DBObject> currlist =getUserList(aacurrlist, number);
			 int count =0;
			 if(currlist!=null) {
				 count =currlist.size();
			 }
			 relation.setPnumber("");
			 relation.setNumber(number);
			 relation.setHierarchy(0);
			 relation.setPosition (0); //0 主节点部分左右去
			 relation.setMoney(Double.valueOf(getAchievementbyNumber(number))); //查询业绩
			 relation.setCount( count);
			return relation;  
	 }
	
	
	/*
	 * 根据会员编号查询网体结构   
	 * 管理后台使用
	 */
	public  String   findrelation() {
		String currnumber =""; //存储查询的会员编号
	 
		String getnumber  = Struts2Utils.getParameter("vipno") ;  //获取会员编号
		Struts2Utils.getRequest().setAttribute("vipno", getnumber);
		//获取当前登录用户的信息
		CustomerUser cust =(CustomerUser)SpringSecurityUtils.getCurrentUser();
		String id =cust.getId();
		DBObject dbObject = basedao.getMessage(PubConstants.USER_INFO,id );
		String loginnumber =""; //登录的会员编号
		if (dbObject != null) {
			UserInfo user = (UserInfo) UniObject.DBObjectToObject(dbObject, UserInfo.class);
				loginnumber =user.getNo();
	    }
		//判断是否传入会员编号，如果没有传入查自己的
		if( getnumber!=null && !getnumber.equals("") && getnumber.length()>0) {
			currnumber =getnumber;
		}else {
			currnumber =loginnumber;
		}
		//测试
		//currnumber ="65797";
		//如果会员有值查询二叉树信息
		if(currnumber!="") {  //
			Relations relations =null; //存储二叉树第一级
			int count =0;
			List<Relations> list = new  ArrayList<>(); 
			List<Relations> listrelations =null;
			relations=getrelation(currnumber);
			relations.setAgentLevel(wwzservice.getAgentLevelByVipNo(currnumber));
	    	//获取子节点的信息
	    	listrelations = getrelationList(count,list,currnumber,3);
	    	
	    	Struts2Utils.getRequest().setAttribute("relations", relations);
	    	  
	    	//排序
	    	
	    	if(listrelations!=null && listrelations.size()>0) {
	    		ListUtils<Relations> listUtils =new ListUtils<Relations>();
	    		listUtils.sortByMethod(list, "getHierarchy", false);
	    	}
	    	System.out.println("网体的节点：");
	    	System.out.println(listrelations.toString());
	    	
	    	Struts2Utils.getRequest().setAttribute("listrelations", listrelations);
	    	 
		}else {
			Struts2Utils.getRequest().setAttribute("errormessage", "会员编号有误");
		}
		
		 
		return "find";
	}
	
	
	
	/*
	 * 根据会员编号查询网体结构   
	 * 客户端使用
	 */
	public  String   mobilefindrelation() {
		String currnumber =""; //存储查询的会员编号
		//获取会员的ID ;
		getLscode();
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("lscode", lscode);
		 
		return "mfind";
	}
	
	
	/*
	 * ajax根据会员编号查询网体结构   
	 * 客户端使用
	 */
	public  void   ajaxmobilefindrelation() {
		
		Map<String, Object> sub_map = new HashMap<>();
		String currnumber =""; //存储查询的会员编号
		//获取会员的ID ;
		getLscode();
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("lscode", lscode);
	 
		String getnumber  = Struts2Utils.getParameter("vipno") ;  //获取会员编号
		//获取当前登录用户的信息 ---客户端获取用户编号查询用户的会员编号。
		String loginnumber =""; //登录的会员编号
		DBObject dbObject = basedao.getMessage(PubConstants.USER_INFO,fromUserid );
		if (dbObject != null) {
			UserInfo user = (UserInfo) UniObject.DBObjectToObject(dbObject, UserInfo.class);
				loginnumber =user.getNo();
	    }
		//判断是否传入会员编号，如果没有传入查自己的
		if( getnumber!=null && !getnumber.equals("") && getnumber.length()>0) {
			currnumber =getnumber;
		}else {
			currnumber =loginnumber;
		}
		System.out.println("--查询到 会员编号："+currnumber);
		//测试
		//currnumber ="65797";
		//如果会员有值查询二叉树信息
		if(currnumber!="") {  //
			Relations relations =null; //存储二叉树第一级
			int count =0;
			
			relations=getrelation(currnumber);
			relations.setAgentLevel(wwzservice.getAgentLevelByVipNo(currnumber));
	    	
	    	//Struts2Utils.getRequest().setAttribute("relations", relations);
	    	
	    	sub_map.put("relations", relations);  
	    	 
	    	List<Relations> list = new  ArrayList<>(); 
			List<Relations> listrelations =null;
	    	//获取子节点的信息
	    	listrelations = getrelationList(count,list,currnumber,2);
	    	//排序
	    	
	    	if(listrelations!=null && listrelations.size()>0) {
	    		ListUtils<Relations> listUtils =new ListUtils<Relations>();
	    		listUtils.sortByMethod(list, "getHierarchy", false);
	    	}
	    	
	    	//Struts2Utils.getRequest().setAttribute("listrelations", listrelations);
	    	sub_map.put("listrelations", listrelations);  
	    	 
		} 
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
		 
	}
	
	
	
	
	
	



	/*
	 * ajax加载 网体结构 
	 * 
	 */
	public  void   ajaxfindrelation() {
		Map<String, Object> sub_map = new HashMap<>();
		String currnumber =""; //存储查询的会员编号
		String getnumber  = Struts2Utils.getParameter("number") ;  //获取会员编号
		//获取当前登录用户的信息
		CustomerUser cust =(CustomerUser)SpringSecurityUtils.getCurrentUser();
		String id =cust.getId();
		DBObject dbObject = basedao.getMessage(PubConstants.USER_INFO,id );
		String loginnumber =""; //登录的会员编号
		if (dbObject != null) {
			UserInfo user = (UserInfo) UniObject.DBObjectToObject(dbObject, UserInfo.class);
				loginnumber =user.getNo();
	    }
		//判断是否传入会员编号，如果没有传入查自己的
		if( getnumber!=null && !getnumber.equals("") && getnumber.length()>0) {
			currnumber =getnumber;
		}else {
			currnumber =loginnumber;
		}
		//如果会员有值查询二叉树信息
		if(currnumber!="") {  //
			Relations relations =null; //存储二叉树第一级
			int count =0;
			List<Relations> list = new  ArrayList<>(); 
			List<Relations> listrelations =null;
			relations=getrelation(currnumber); //获取主节点的信息
	    	//获取子节点的信息
	    	listrelations = getrelationList(count,list,currnumber,3);
	    	sub_map.put("relations", relations);  
	    	//排序
	    	ListUtils<Relations> listUtils =new ListUtils<Relations>();
	    	listUtils.sortByMethod(list, "getHierarchy", false);
	    	sub_map.put("listrelations", listrelations); 
	    	sub_map.put("statue", 0);//99表示会员信息有误  0 表示成功
		}else {
			sub_map.put("statue", 99);//99表示会员信息有误  0 表示成功
		}
		 
	    String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
		 
	}
	
	
	 
	
	/**
	 * 根据父级的会员编号获取 二叉树集合 判断输入的节点是否在自己的网体里面，如果存在返回这个网体，如果不存在返回空的网体
	 * @param parentnumber 查询的会员编号
	 * @param 指定查询的层级
	 * @return
	 */
	
	public  UserRelation getsitebypnumber( boolean  isfor, String setnumber ,String   parentnumber   ){
		
		//根据输入的父类的会员编号查询
		
	    if(isfor) { //找到了就返回
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("parentnumber", parentnumber);
		DBObject db = basedao.getMessage(PubConstants.USER_RELATION, whereMap);
			if(db!=null) {
				
				UserRelation curr = (UserRelation) UniObject.DBObjectToObject(db, UserRelation.class);
				String lnumber =curr.getLeftnumber();
				String rnumber =curr.getRightnumber();
				String pnumber =curr.getParentnumber();
				curr.set_id(db.get("_id"));
				
				if(lnumber!=null && lnumber.equals(setnumber)  ) {
					currrelarion =curr;
					isfor =false;
				}else if(rnumber!=null && rnumber.equals(setnumber) ) {
					currrelarion =curr;
					isfor =false;
				}else if( pnumber !=null && pnumber.equals(setnumber)) {
					currrelarion =curr;
					isfor =false;
				}
				  
				getsitebypnumber(isfor,setnumber, lnumber); //递归左区
				getsitebypnumber(isfor,setnumber, rnumber); //递归右区 
			} 
	    }
		return currrelarion;  
	}
	
	/**
	 * 根据会员编号查询网体信息
	 * 
	 */
	public UserRelation getbypnumber(String  pnumber) {
		UserRelation uRelation=null;
		
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("parentnumber", pnumber);
		DBObject db = basedao.getMessage(PubConstants.USER_RELATION, whereMap);
			if(db!=null) {
				uRelation = (UserRelation) UniObject.DBObjectToObject(db, UserRelation.class);
				uRelation.set_id(db.get("_id"));
			} 
		return uRelation;
	}
	
	
	/**
	 *  ajax 
	 *  推荐人设置推荐会员的摆放节点
	 * @return
	 */
	public void ajaxsetnumber() {
		getLscode();
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		Map<String, Object> sub_map = new HashMap<>();
		String resultmsg   = ""; //标识成功，其余的输入错误信息
		int status =99 ;   // 99标识失败   0 标识成功
		//存放的节点
		int LorR =2;  //0左 1右 2左右区都有人
		UserRelation curr =null;  // 存放节点的relation
		boolean ispull =false;  //true 节点可用 false节点不可用
		
		
		String parentnumber =  Struts2Utils.getParameter("parentnumber") ;  //获取推荐人编号
		String setnumber =  Struts2Utils.getParameter("setnumber") ;  //获取摆放节点编号
		String vipno =Struts2Utils.getParameter("vipno") ;  //被设置的会员编号
		System.out.println("获取推荐人编号" +parentnumber);
		System.out.println("获取摆放节点编号" +setnumber);
		System.out.println("被设置的会员编号" +vipno);
		if(parentnumber.equals(setnumber)) {  //如果节点等于推荐人  ---
			UserRelation sur =getbypnumber(parentnumber); //查看自己是否有推荐关系
			if(sur==null) { //节点没有人
				LorR =0;
				ispull=true;
			}else if(sur.getLeftnumber()==null || sur.getLeftnumber().equals("")) { //可以放左区
				LorR =0;
				curr =sur;
				ispull=true;
			}else if(sur.getRightnumber()==null || sur.getRightnumber().equals("")) { //可以放右区
				LorR =1;
				curr =sur;
				ispull=true;
			}else {
				resultmsg="当前节点下人数已满";
				ispull=false;
			}
			
		}else {
			//查询摆放节点是否在推荐人的节点下：
			UserRelation  srelation=getsitebypnumber(true,setnumber,parentnumber);
		
			//如果srelation 为null 输入的节点有误
			if( srelation==null  ||  srelation.getParentnumber()==null) {
				ispull=false;
				resultmsg="输入的节点有误，不在您的体系内";
			}else { //如果不为空判断是否还有位置
				if(setnumber.equals(srelation.getParentnumber())){ //如果节点是父节点
					if(srelation.getLeftnumber()==null || srelation.getLeftnumber().equals("")) { //可以放左区
						LorR =0;
						curr =srelation;
						curr.set_id(srelation.get_id());
						ispull=true;
					}else if(srelation.getRightnumber()==null || srelation.getRightnumber().equals("")) { //可以放右区
						LorR =1;
						curr =srelation;
						curr.set_id(srelation.get_id());
						ispull=true;
					}else {
						resultmsg="当前节点下人数已满";
						ispull=false;
					}
				}else  { //如果是左区或者是右区，查询该节点下有没有人
					UserRelation sur =getbypnumber(setnumber);
					if(sur==null) { //节点没有人
						LorR =0;
						ispull=true;
					}else if(sur.getLeftnumber()==null || sur.getLeftnumber().equals("")) { //可以放左区
						LorR =0;
						curr =sur;
						curr.set_id(sur.get_id());
						ispull=true;
					}else if(sur.getRightnumber()==null || sur.getRightnumber().equals("")) { //可以放右区
						LorR =1;
						curr =sur;
						curr.set_id(sur.get_id());
						ispull=true;
					}else {
						resultmsg="当前节点下人数已满";
						ispull=false;
					}
				} 
				
			}
			
		}
		
		
		if(ispull) { //节点正确   增加存储逻辑
			if(curr == null) {
				curr =new UserRelation();
				curr.setCreatetime(new Date());
				curr.setLeftnumber(vipno);
				curr.setParentnumber(setnumber);
			}else if(LorR ==0){
				curr.setLeftnumber(vipno);
			}else {
				curr.setRightnumber(vipno);
			}
			
			//增加用户关系表
		    basedao.insert(PubConstants.USER_RELATION, curr);
			
			//修改用户信息
			whereMap.clear();
			//根据会员编号查询用户端的ID
			 
			whereMap.put("no", vipno);
			DBObject db = basedao.getMessage(PubConstants.USER_INFO, whereMap);
			if (db != null) {
				UserInfo user = (UserInfo) UniObject.DBObjectToObject(db, UserInfo.class);
				user.setRecommend(1); 
				basedao.insert(PubConstants.USER_INFO, user);
			}
			status =0;
		}else {
			status=99; //节点信息有误 ，对应的提示消息存储在resultmsg 里面
		}
		
		sub_map.put("resultmsg", resultmsg);
		sub_map.put("status", status);
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}
	
	/**
	 * 增加用户
	 */
	public void adduser () {
		String  nn = "";
		for(int i=0;i<7;i++) {
			String no =wwzservice.getVipNo();
			
			UserInfo user =new UserInfo();
			user.set_id(UUID.randomUUID().toString() );
			user.setNo(no);
			user.setNumber(Long.parseLong(no));
			user.setCreatedate(new Date());
			user.setPassword("000000");
			user.setRecommend(0);
			user.setNickname(no);
			user.setRenumber(Long.parseLong("80837"));
			System.out.println("userid:"+user.get_id()+"----no:"+no +"------renumber:80837" );
			 
			 
			
			basedao.insert(PubConstants.USER_INFO, user);
			
		}
		
	}
	 
	
	//测试类
	public void test() {
		
		
		//String number =Struts2Utils.getParameter("number");
		//adduser ();
		
//		UserRelation  userRelation  =getsitebypnumber(true,number,"63979");
//		System.out.println("----------:"+userRelation.getLeftnumber());
//		System.out.println("----------:"+userRelation.getRightnumber());
//		System.out.println("----------:"+userRelation.getParentnumber());
		
//业绩计算		
//		String aa=getAchievementbyNumber(number);
//		System.out.println("number:"+number  +"--的业绩："+aa);
		
//获取会员编号下的用户列表
//		List<DBObject> list =new  ArrayList<>();
//		List<DBObject> getlist =getUserList(list, number);
//		System.out.println("-----------getlist:"+getlist.size());
//		System.out.println(getlist.toString());
//		 
		
		
//获取节点测试方法		
//		UserRelation uu =new UserRelation();
//		UserRelation curr  =getbyparentnumber(number);
//		System.out.println("---------获取插入的节点父级编号"+curr.getParentnumber());
	 
		

//		//System.out.println("----------------userlist:"+list.toString());
//		for(int i=0;i<list.size();i++) {
//			Relations db = list.get(i);
//			System.out.println("--pnumber:"+db.getPnumber()+"--number:"+db.getNumber()
//			+"--hierarchy:"+db.getHierarchy()+"--count:"+db.getCount()+"--position:"+db.getPosition()+"--money:"+db.getMoney() );
//		}
//		
//		
//获取二叉树战士方法		
//		List<Relations> list1 = new  ArrayList<>(); 
//		int count=0;
//		List<Relations> list  =getrelationList(count,list1,number,3);
//		System.out.println("-----------排序后----------------------");
//		ListUtils<Relations> listUtils =new ListUtils<Relations>();
//		
//		 listUtils.sortByMethod(list, "getHierarchy", false);
//		//System.out.println("----------------userlist:"+list.toString());
//		for(int i=0;i<list.size();i++) {
//			Relations db = list.get(i);
//			System.out.println("--pnumber:"+db.getPnumber()+"--number:"+db.getNumber()
//			+"--hierarchy:"+db.getHierarchy()+"--count:"+db.getCount()+"--position:"+db.getPosition()+"--money:"+db.getMoney() );
//		}
		
	}
	
	
	
	public static void main(String[] args) {
		 int  aa  =(int) Math.pow(2,200);
		 System.out.println(aa);
		
	}
	
}