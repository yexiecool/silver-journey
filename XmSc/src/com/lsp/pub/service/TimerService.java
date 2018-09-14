package com.lsp.pub.service;

 
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.lsp.integral.entity.InteSetting;
import com.lsp.pub.dao.BaseDao;
import com.lsp.pub.db.MongoDbUtil;
import com.lsp.pub.db.MongoSequence;
import com.lsp.pub.entity.BtcChangerate;
import com.lsp.pub.entity.EthChangerate;
import com.lsp.pub.entity.Exchangerate;
import com.lsp.pub.entity.GetAllFunc;
import com.lsp.pub.entity.HttpClient;
import com.lsp.pub.entity.PadaChangerate;
import com.lsp.pub.entity.PubConstants;
import com.lsp.pub.util.BaseDecimal;
import com.lsp.pub.util.DateFormat;
import com.lsp.pub.util.DateUtil;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.util.SysConfig;
import com.lsp.pub.util.UniObject;
import com.lsp.shop.entiy.OrderForm;
import com.lsp.shop.entiy.OrderFormpro;
import com.lsp.suc.entity.IntegralRecord;
import com.lsp.website.service.WwzService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
 
/**
 * 定时任务
 * @author lsp
 *
 */
public class TimerService {
	/** LOG4J对象 */
	private static Logger logger = LoggerFactory.getLogger(TimerService.class);

	private static TimerService timerService;
	@Autowired
	private BaseDao baseDao; 
	private MongoSequence mongoSequence;	
	@Autowired
	private WwzService  wwzservice; 
	 
	@Autowired
	public void setMongoSequence(MongoSequence mongoSequence) {
		this.mongoSequence = mongoSequence;
	}
    /**
	 * 服务类实例化
	 * 
	 * @return CzrzService
	 */
	public synchronized static TimerService getInstance() {
		if (timerService == null) {
			timerService = new TimerService();
		}
		return timerService;
	} 
	/**
	 * 每日返还
	 * @throws Exception 
	 */
	public synchronized void updProstore() throws Exception{
		System.out.println("------------------ 开始调用产币的方法 -------------------------------------------");
		if(wwzservice==null){
			wwzservice=new WwzService();
		}
		MongoDbUtil mongoDbUtil=new MongoDbUtil();
		HashMap<String, Object>sortMap=new HashMap<String, Object>();
		HashMap<String, Object>whereMap=new HashMap<String, Object>();
		BasicDBObject dateCondition1 = new BasicDBObject();
		dateCondition1.append("$lte", DateUtil.addHour(new Date(), -22));
		whereMap.put("createdate", dateCondition1);
		sortMap.put("createdate", -1);
		BasicDBObject dateCondition = new BasicDBObject();
		dateCondition.append("$gte",new Date());
		whereMap.put("enddate", dateCondition);
		whereMap.put("state", 0);
//		
//		String  pbno=SysConfig.getProperty("pbno");
//		if(pbno!=null){
//			 BasicDBList   dblist=new BasicDBList();  
//			 String[]lsno=pbno.split(",");
//			 for (String string : lsno) {
//				 System.out.println(string);
//	            if(StringUtils.isNotEmpty(string)){
//	            	BasicDBObject dat=new BasicDBObject(); 
//	            	String noid=wwzservice.getfromUseridVipNo(string);
//	            	if(StringUtils.isNotEmpty(noid)){
//	            		dat.put("fromUserid",new BasicDBObject("$ne",noid));
//		            	dblist.add(dat);
//	            	}
//	            	
//	            }			 
//				
//			}
//			 System.out.println(dblist);
//			whereMap.put("$and", dblist); 
//			 
//		} 
		List<DBObject>list=mongoDbUtil.queryAll(PubConstants.INTEGRAL_PROSTORE,whereMap, sortMap).toArray(); 
		String ppbprice=wwzservice.getPPBSprice()+""; 
		for (DBObject dbObject : list) {
			System.out.println(dbObject.toString());
//			wwzservice.updHbKj(dbObject.get("fromUserid").toString());
			String ccid=dbObject.get("_id").toString();
			if(ccid.equals("99999999")){
				System.err.println("sssssss");
			}else{
			if(dbObject.get("money")!=null){
				String price = "0"; 
				
				if(dbObject.get("money")!=null&&dbObject.get("time")!=null&&Integer.parseInt(dbObject.get("time").toString())>0) { 
            		price = BaseDecimal.division(dbObject.get("money").toString(),dbObject.get("time").toString(),6);
            	}else {
            		price = BaseDecimal.division("0","1",6);
            	}
				double kjlx=0;
				HashMap<String, Object>where1Map=new HashMap<>();
	    		whereMap.put("custid",SysConfig.getProperty("custid"));
	    		whereMap.put("fromUserid", dbObject.get("fromUserid").toString());
	    		IntegralRecord ir = null;
				DBObject db = mongoDbUtil.findOne(PubConstants.SUC_INTEGRALRECORD, where1Map);
				if(db!=null){
					ir=(IntegralRecord) UniObject.DBObjectToObject(db, IntegralRecord.class);
					kjlx=ir.getKjlx(); 
					
				} 
				String jzprice=price;
				String bprice="0";
				if(kjlx>=0){
					if(kjlx==1){
						price=BaseDecimal.division(price,ppbprice,10);
						bprice=ppbprice;
					}else if(kjlx==2){
						price=BaseDecimal.division(price,wwzservice.getBTCSprice()+"",20);
						bprice=wwzservice.getBTCSprice()+"";
					}else if(kjlx==3){
						price=BaseDecimal.division(price,wwzservice.getETHSprice()+"",20); 
						bprice=wwzservice.getETHSprice()+"";
					}else{
						price=BaseDecimal.division(price,ppbprice,10);
						bprice=ppbprice;
					}  
					if(dbObject.get("fromUserid")!=null&&dbObject.get("type")!=null){
						
						//验证每日返
						HashMap<String, Object>wherewMap=new HashMap<>();
						wherewMap.put("fromUserid", dbObject.get("fromUserid").toString());
						wherewMap.put("type", dbObject.get("type").toString());
						wherewMap.put("fid", dbObject.get("_id").toString());
						wherewMap.put("insdate",DateFormat.getDate());
						Long count=mongoDbUtil.getCount(PubConstants.INTEGRAL_INFO, wherewMap); 
						if(count==0){
							
							System.out.println("-----"+dbObject.get("fromUserid").toString() + "  -----  产币 -------------------------------------------" +DateFormat.getDate(new Date()));
							
							if(dbObject.get("type").toString().equals("ps_account")||dbObject.get("type").toString().equals("ps_recovery")){
								//挖矿到矿机账号 
								wwzservice.addyfjf(price, dbObject.get("fromUserid").toString(), dbObject.get("type").toString(), SysConfig.getProperty("custid"),1,dbObject.get("_id").toString(), jzprice,bprice);
								
							}else{
								wwzservice.addyfjf(price, dbObject.get("fromUserid").toString(), dbObject.get("type").toString(), SysConfig.getProperty("custid"),1,dbObject.get("_id").toString(), jzprice,bprice);
							}  
//							wwzservice.updHbKj(dbObject.get("fromUserid").toString());
							//矿机推荐收益
//							DBObject user=wwzservice.getCustUser(dbObject.get("fromUserid").toString());
//						    if(user!=null&&user.get("renumber")!=null&&dbObject.get("type").toString().equals("shop_bmzt")&&StringUtils.isNotEmpty(wwzservice.getfromUseridVipNo(dbObject.get("renumber").toString()))) {
//						    	wwzservice.addyfjf(BaseDecimal.multiplication(price, "0.1"),wwzservice.getfromUseridVipNo(dbObject.get("renumber").toString()),"kj_tjsy", SysConfig.getProperty("custid"),1,dbObject.get("_id").toString(), BaseDecimal.multiplication(jzprice, "0.1"),bprice);
//						    }
						    
//						    if(user!=null&&user.get("renumber")!=null&&dbObject.get("type").toString().equals("shop_bmzt")&&StringUtils.isNotEmpty(wwzservice.getfromUseridVipNo(user.get("renumber").toString()))) {
//						    	wwzservice.addyfjf(BaseDecimal.multiplication(price, "0.1"),wwzservice.getfromUseridVipNo(user.get("renumber").toString()),"kj_tjsy", SysConfig.getProperty("custid"),1,dbObject.get("_id").toString(), BaseDecimal.multiplication(jzprice, "0.1"),bprice);
//						    }
							
						} 
						
					}
				}
			
			}
		}
		}
	}
	/**
	 * 更新汇率（每半小时一次）
	 */
	public synchronized void updaterate(){
		System.out.println("更新汇率");
		//更新btc
		String str=HttpClient.sendGet("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=CNY");
    	if(StringUtils.isNotEmpty(str)){
    		JSONObject json=JSONObject.parseObject(str);
        	if(json.get("CNY")!=null) {
        		//存储
        		BtcChangerate btcChangerate=new BtcChangerate();
        		btcChangerate.set_id(mongoSequence.currval(PubConstants.PUB_BTCCHANGERATE));
        		btcChangerate.setType("CNY_BTC");
        		btcChangerate.setUpddate(new Date());
        		btcChangerate.setValue(json.get("CNY").toString()); 
        		baseDao.insert(PubConstants.PUB_BTCCHANGERATE,btcChangerate);
        		GetAllFunc.btcarate.put(SysConfig.getProperty("custid"),btcChangerate);
        	};
    	}
    	
    	
    	//更新rmb
		str=HttpClient.sendGet(SysConfig.getProperty("cny_price_api"));
		if(StringUtils.isNotEmpty(str)){
			JSONObject json=JSONObject.parseObject(str);
	    	if(json.get("result")!=null){
				json=JSONObject.parseObject(json.get("result").toString());
			} 
	    	if(json.get("USD")!=null) { 
	    		JSONObject usd=JSONObject.parseObject(json.get("USD").toString());
	    		if(usd.get("BOC")!=null){ 
	    			JSONObject boc=JSONObject.parseObject(usd.get("BOC").toString());
	    			if(boc.get("se_buy")!=null){ 
	    				 
	    				Exchangerate  exchangerate=new Exchangerate();
        				exchangerate.set_id(mongoSequence.currval(PubConstants.PUB_EXCHANGERATE)); 
        				exchangerate.setType("USD_CNY");
        	    		exchangerate.setValue(BaseDecimal.division(boc.get("se_buy").toString(), "100", 6));
        	    		exchangerate.setUpddate(new Date());
        	    		baseDao.insert(PubConstants.PUB_EXCHANGERATE, exchangerate);
        	    		GetAllFunc.rmbrate.put(SysConfig.getProperty("custid"),exchangerate); 
	    			}
	    		}
	    	};
		}
		
		//更新pada
    	str=HttpClient.sendGet(SysConfig.getProperty("uskd_price_api"));
    	if(StringUtils.isNotEmpty(str)){
    		JSONObject json=JSONObject.parseObject(str);
        	if(json.get("new_price")!=null) {
        		//存储
        		PadaChangerate padaChangerate=new PadaChangerate();
        		padaChangerate.set_id(mongoSequence.currval(PubConstants.PUB_PADACHANGERATE));
        		padaChangerate.setType("CNY_PADA");
        		padaChangerate.setUpddate(new Date());
        		padaChangerate.setValue(BaseDecimal.multiplication(GetAllFunc.rmbrate.get(SysConfig.getProperty("custid")).getValue(), json.get("new_price").toString())); 
        		baseDao.insert(PubConstants.PUB_PADACHANGERATE,padaChangerate);
        		GetAllFunc.padarate.put(SysConfig.getProperty("custid"),padaChangerate);  
        	};
    	}
    	//更新eth
    	str=HttpClient.sendGet("https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=CNY");
    	if(StringUtils.isNotEmpty(str)){
    		JSONObject json=JSONObject.parseObject(str);
        	if(json.get("CNY")!=null) {
        		//存储
        		EthChangerate ethChangerate=new EthChangerate();
        		ethChangerate.set_id(mongoSequence.currval(PubConstants.PUB_ETHCHANGERATE));
        		ethChangerate.setType("CNY_ETH");
        		ethChangerate.setUpddate(new Date());
        		ethChangerate.setValue(json.get("CNY").toString()); 
        		baseDao.insert(PubConstants.PUB_ETHCHANGERATE,ethChangerate);
        		GetAllFunc.ethrate.put(SysConfig.getProperty("custid"),ethChangerate); 
        	};
    	}
	}
	
	public void updateConfirmPro() {
		System.out.println("自动收货");
		String custid = SysConfig.getProperty("custid");
		HashMap<String, Object>sortMap=new HashMap<String, Object>();
		HashMap<String, Object>whereMap=new HashMap<String, Object>();
		whereMap.put("goodstate", 3);// 1订单 2 确认 3 发货 4收货 5退货 6 取消7已支付等待平台确认 99 支付失败
		whereMap.put("state",0);//0-正常 1-退货申请 2-换货申请 3-退货 4-换货
		BasicDBObject dateCondition = new BasicDBObject();
		Date d = DateUtil.addDay(new Date(), -15);
		dateCondition.append("$lte", DateFormat.getFormat(d.toString()));
		whereMap.put("deliveryDate",dateCondition);
		sortMap.put("deliveryDate", -1);
		List<DBObject> list = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap,sortMap);
		for (DBObject dbObject : list) {
			String oid = dbObject.get("orderid").toString();
			DBObject dbObjects = baseDao.getMessage(PubConstants.WX_ORDERFORM, oid);
			if (dbObjects != null && dbObjects.get("fromUserid") != null) {
				System.out.println("进入这个方法");
				System.out.println("---->"+dbObject);
				String fromUserid = dbObjects.get("fromUserid").toString();
				OrderFormpro order = (OrderFormpro) UniObject.DBObjectToObject(dbObject, OrderFormpro.class);
				order.set_id(Long.parseLong(dbObject.get("_id").toString()));
				order.setGoodstate(4);
				
				baseDao.insert(PubConstants.SHOP_ODERFORMPRO, order);
				// 开始返利结算
				Double bl = 0.0;
				Double bl_ty = 0.0;
				Double bl_hy = 0.0;
				// 1.会员区
				if(order.getContri_money() != null){
					bl_hy =  order.getContri_money();
				}
				System.out.println("1--->"+order.getContri_money());
				// 2.大众区
				if(order.getPublic_money() != null){
				    bl_ty =  order.getPublic_money();
				}
				System.out.println("2--->"+order.getPublic_money());
				// 3.特约区
				if(order.getMembers_money() != null){
					bl = order.getMembers_money();
				}
				System.out.println("3--->"+order.getMembers_money());
				whereMap.clear();
				whereMap.put("_id",SysConfig.getProperty("custid"));
				DBObject db = baseDao.getMessage(PubConstants.INTEGRAL_INTESETTING, whereMap);
				if (db != null) {
					InteSetting sett = (InteSetting) UniObject.DBObjectToObject(db, InteSetting.class);
					
					// 记录当前购物用户的提成
					wwzservice.addjfoid(wwzservice.getGivingPro(bl_ty), fromUserid, "shop_bmzt", custid, 1, 2, 0,oid,"大众区下单");
					bl_ty = Double.parseDouble(BaseDecimal.division(
							BaseDecimal.multiplication(wwzservice.getGivingPro(bl_ty), sett.getDzqtc() + ""), "100"));
					bl_hy = Double.parseDouble(BaseDecimal.division(
							BaseDecimal.multiplication(wwzservice.getGivingPro(bl_hy), sett.getHyqtc() + ""), "100"));
					// 获取直推人员
					DBObject wxuser = wwzservice.getWxUser(fromUserid);
                    if(wxuser.get("tjlx") != null){
                    	if (Integer.parseInt(wxuser.get("tjlx").toString()) == 0) {
    						System.out.println("----->"+wxuser.get("renumber"));
                    		// 会员推荐
    						if(wxuser.get("renumber")!=null){
    							DBObject tjuser = wwzservice.getWXuserVipNo(wxuser.get("renumber").toString()); 
        						if (tjuser != null) {
        							// 记录提成
        							System.out.println(bl);
        							System.out.println(bl_ty);
        							System.out.println(bl_hy);
        							System.out.println(BaseDecimal.division(BaseDecimal.multiplication(bl + "", sett.getDirect() + ""), "100"));
        							System.out.println(BaseDecimal.division(BaseDecimal.multiplication(bl_ty + "", sett.getDirect() + ""), "100"));
        							System.out.println(BaseDecimal.division(BaseDecimal.multiplication(bl_hy + "", sett.getDirect() + ""), "100"));
        							System.out.println(sett.getDirect());
        							wwzservice.addjfoid(
        									BaseDecimal.division(BaseDecimal.multiplication(bl + "", sett.getDirect() + ""), "100"),
        									tjuser.get("_id").toString(), "shop_bmzt", SysConfig.getProperty("custid"), 1, 1, 0,oid,"特约区下单");
        							wwzservice.addjfoid(BaseDecimal
        									.division(BaseDecimal.multiplication(bl_ty + "", sett.getDirect() + ""), "100"),
        									tjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 2, 0,oid,"大众区下单");
        							wwzservice.addjfoid(BaseDecimal
        									.division(BaseDecimal.multiplication(bl_hy + "", sett.getDirect() + ""), "100"),
        									tjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 2, 0,oid,"会员区下单");


            						// 获取间接推荐人员
            						if(tjuser!=null&&tjuser.get("renumber")!=null){
            							tjuser = wwzservice.getWXuserVipNo(tjuser.get("renumber").toString());
            						    if(tjuser!=null){
            						    	// 记录提成
                							wwzservice.addjfoid(BaseDecimal
                									.division(BaseDecimal.multiplication(bl + "", sett.getBetween() + ""), "100"),
                									tjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 1, 0,oid,"间推人返利");
            						    } 	 

            						} 
        						}
    						}
    					} else if (Integer.parseInt(wxuser.get("tjlx").toString()) == 1) {
    						// 管理员推荐
    						whereMap.clear();
    						whereMap.put("number", Long.parseLong(wxuser.get("renumber").toString()));
    						DBObject tjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
    						if (tjuser != null) {
    							// 记录提成
    							wwzservice.addjfoid(
    									BaseDecimal.division(BaseDecimal.multiplication(bl + "", sett.getDirect() + ""), "100"),
    									tjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 1, 0,oid,"直推荐返利");

    						}
    						// 获取间接推荐人员 （当推荐人为管理员时候间接推荐的奖励也发放到管理员账户）
    						if (tjuser != null) {
    							// 记录提成
    							wwzservice.addjfoid(BaseDecimal
    									.division(BaseDecimal.multiplication(bl + "", sett.getBetween() + ""), "100"),
    									tjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 1, 0,oid,"直推荐人返利");

    						}
    					}
                    }
					

					// 获取部门人员;
					whereMap.clear();
					System.out.println("--------------------------"+order.getDeptCode());
					whereMap.put("number",order.getDeptCode());
					DBObject bmuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap); 
					if (bmuser != null) { 
						// 记录提成
						System.out.println(BaseDecimal
								.division(BaseDecimal.multiplication(bl + "", sett.getSameDepartment() + ""), "100"));
						System.out.println("开始则鞥见+++"+bmuser.get("_id").toString());
						wwzservice.addjfoid(BaseDecimal
								.division(BaseDecimal.multiplication(bl + "", sett.getSameDepartment() + ""), "100"),
								bmuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 1, 0,oid,"报单中心产品返利10%");

						// 获取县级

						whereMap.clear(); 
						if(bmuser.get("renumber") != null){
							whereMap.put("number", bmuser.get("renumber").toString());
						}
						DBObject xjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
						if (xjuser != null) {
							// 记录提成
							wwzservice.addjfoid(BaseDecimal
									.division(BaseDecimal.multiplication(bl + "", sett.getSameCounty() + ""), "100"),
									xjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 1, 0,oid,"区县返利返利5%");

							// 县级存在，获取市级

							whereMap.clear();
							if(xjuser.get("renumber") != null){
								whereMap.put("number", xjuser.get("renumber").toString());
							}
							DBObject sjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
							if (sjuser != null) {
								// 记录提成
								wwzservice.addjfoid(BaseDecimal
										.division(BaseDecimal.multiplication(bl + "", sett.getSameCity() + ""), "100"),
										sjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 1, 0,oid,"市代理返利3%");

								// 县级存在，市级存在，获取省级
								whereMap.clear();
								if(sjuser.get("renumber") != null){
									whereMap.put("number", sjuser.get("renumber").toString());
								}
							    DBObject shjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
								if (shjuser != null) {
									// 记录提成
									wwzservice.addjfoid(BaseDecimal.division(
											BaseDecimal.multiplication(bl + "", sett.getSameProvince() + ""), "100"),
											shjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 1, 0,oid,"省级代理返利2%");
								}
							} else {

								// 县级存在，市级不存在， 获取省级

								whereMap.clear();
								whereMap.put("agentLevel", 1);
								if(bmuser.get("province") != null){
									whereMap.put("province", bmuser.get("province").toString());
								}
								DBObject shjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
								if (shjuser != null) {
									// 记录提成
									wwzservice.addjfoid(BaseDecimal.division(
											BaseDecimal.multiplication(bl + "", sett.getSameProvince() + ""), "100"),
											shjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"省级代理返利2%");

								}

							}

						} else {
							// 县级不存在， 获取市级

							whereMap.clear();
							whereMap.put("agentLevel", 2);
							if(bmuser.get("city")!=null){
								whereMap.put("city", bmuser.get("city").toString());
							}
							DBObject sjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
							if (sjuser != null) {
								// 记录提成
								wwzservice.addjfoid(BaseDecimal
										.division(BaseDecimal.multiplication(bl + "", sett.getSameCity() + ""), "100"),
										sjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"市级代理返利3%");

								// 县级不存在， 市级存在，获取省级
								whereMap.clear();
								if(sjuser.get("renumber") != null){
									whereMap.put("number", sjuser.get("renumber").toString());
								}
								DBObject shjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
								if (shjuser != null) {
									// 记录提成
									wwzservice.addjfoid(BaseDecimal.division(
											BaseDecimal.multiplication(bl + "", sett.getSameProvince() + ""), "100"),
											shjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"市级返利2%");
								}
							} else {
								// 县级不存在，市级不存在， 获取省级

								whereMap.clear();
								whereMap.put("agentLevel", 1);
								if(bmuser.get("province") != null){
									whereMap.put("province", bmuser.get("province").toString());
								}
								DBObject shjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
								if (shjuser != null) {
									// 记录提成
									wwzservice.addjfoid(BaseDecimal.division(
											BaseDecimal.multiplication(bl + "", sett.getSameProvince() + ""), "100"),
											shjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"市级返利2%");

								}

							}

						}
					}

					// 获取异地县域
					whereMap.clear();
					if(bmuser != null &&bmuser.get("county")!=null){
						whereMap.put("county", bmuser.get("county").toString());
					}
					DBObject xjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
					if (xjuser != null) {

						// 县域存在 记录提成
						wwzservice.addjfoid(BaseDecimal
								.division(BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
								xjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"外地区县返利1%");

						// 县级存在，获取市级

						whereMap.clear();
						if(xjuser.get("renumber") != null){
							whereMap.put("number", xjuser.get("renumber").toString());
						}
						DBObject sjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
						if (sjuser != null) {
							// 记录提成
							wwzservice.addjfoid(BaseDecimal
									.division(BaseDecimal.multiplication(bl + "", sett.getDiffCity() + ""),"100"),
									sjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"外地市级代理返利0.5%");

							// 县级存在，市级存在，获取省级
							whereMap.clear();
							if(sjuser.get("renumber") != null){
								whereMap.put("number", sjuser.get("renumber").toString());
							}
							DBObject shjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
							if (shjuser != null) {
								// 记录提成
								wwzservice.addjfoid(
										BaseDecimal.division(
												BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
										shjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"外地省级代理返利0.5%");
							}
						} else {

							// 县级存在，市级不存在， 获取省级

							whereMap.clear();
							whereMap.put("agentLevel", 1);
							if(xjuser.get("province")!=null){
								whereMap.put("province", xjuser.get("province").toString());
							}
							DBObject shjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
							if (shjuser != null) {
								// 记录提成
								wwzservice.addjfoid(
										BaseDecimal.division(
												BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
										shjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"外地省级代理返利0.5%");

							}

						}

					} else {
						// 县域不存在获取市级
						whereMap.clear();
						whereMap.put("agentLevel", 2);
						if(bmuser.get("city")!=null){
							whereMap.put("city", bmuser.get("city").toString());
						}
						DBObject sjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
						if (sjuser != null) {
							// 记录提成
							wwzservice.addjfoid(BaseDecimal
									.division(BaseDecimal.multiplication(bl + "", sett.getDiffCity() + ""), "100"),
									sjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"外地市级代理返利0.5%");

							// 县级不存在， 市级存在，获取省级
							whereMap.clear();
							if(sjuser.get("renumber") != null){
								whereMap.put("number", sjuser.get("renumber").toString());
							}
							
							DBObject shjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
							if (shjuser != null) {
								// 记录提成
								wwzservice.addjfoid(
										BaseDecimal.division(
												BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
										shjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"省级产品返利2%");
							}
						} else {
							// 县级不存在，市级不存在， 获取省级

							whereMap.clear();
							whereMap.put("agentLevel", 1);
							if(bmuser.get("province") != null){
								whereMap.put("province", bmuser.get("province").toString());
							}
							DBObject shjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
							if (shjuser != null) {
								// 记录提成
								wwzservice.addjfoid(
										BaseDecimal.division(
												BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
										shjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"外地省级代理返利0.5%");

							}

						}

					}

				}
//				sub_map.put("state", 0);//操作成功
			}
			
			//检测收货情况
			if(StringUtils.isNotEmpty(oid)){
				whereMap.clear();
				whereMap.put("orderid", oid);
				whereMap.put("goodstate",4); 
				Long count=baseDao.getCount(PubConstants.SHOP_ODERFORMPRO,whereMap);
				whereMap.clear();
				whereMap.put("orderid", oid);
				Long count1=baseDao.getCount(PubConstants.SHOP_ODERFORMPRO,whereMap);
				if(count==count1){
					//全部发货
					DBObject db=baseDao.getMessage(PubConstants.WX_ORDERFORM, oid);
					if(db!=null){
						OrderForm form=(OrderForm) UniObject.DBObjectToObject(db, OrderForm.class);
						form.setState(4);
						form.setDeliveryDate(new Date());
						baseDao.insert(PubConstants.WX_ORDERFORM, form);
					}
				
				}
			}
		}
		
//		System.out.println(orderList.size());
	}
	
	 public static void main(String[] args) throws Exception {
		 TimerService  timerService=new TimerService();
		 timerService.updProstore();
	    }
}