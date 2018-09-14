package com.lsp.shop.web;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Results;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;

import com.lsp.android.entity.MessageInfo;
import com.lsp.integral.entity.InteSetting;
import com.lsp.pub.dao.BaseDao;
import com.lsp.pub.db.MongoDbUtil;
import com.lsp.pub.db.MongoSequence;
import com.lsp.pub.entity.GetAllFunc;
import com.lsp.pub.entity.HttpClient;
import com.lsp.pub.entity.PubConstants;
import com.lsp.pub.entity.WxToken;
import com.lsp.pub.util.BaseDecimal;
import com.lsp.pub.util.CommonUtil;
import com.lsp.pub.util.DateFormat;
import com.lsp.pub.util.DateUtil;
import com.lsp.pub.util.DictionaryUtil;
import com.lsp.pub.util.JmsService;
import com.lsp.pub.util.PayCommonUtil;
import com.lsp.pub.util.RelativeDate;
import com.lsp.pub.util.SpringSecurityUtils;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.pub.util.SysConfig;
import com.lsp.pub.util.TenpayUtil;
import com.lsp.pub.util.UniObject;
import com.lsp.pub.util.WeiXinUtil;
import com.lsp.pub.util.WxhbUtil;
import com.lsp.pub.util.XMLUtil;
import com.lsp.pub.web.GeneralAction;
import com.lsp.shop.entiy.Bargainingstati;
import com.lsp.shop.entiy.Bargainingyd;
import com.lsp.shop.entiy.BulkYd;
import com.lsp.shop.entiy.ComMain;
import com.lsp.shop.entiy.OrderForm;
import com.lsp.shop.entiy.OrderFormpro;
import com.lsp.shop.entiy.Paymentorder;
import com.lsp.shop.entiy.ProductInfo;
import com.lsp.shop.entiy.ShopAgent;
import com.lsp.shop.entiy.ShopMb;
import com.lsp.shop.entiy.ShopRemind;
import com.lsp.shop.entiy.Shoppingcart;
import com.lsp.shop.entiy.Useraddress;
import com.lsp.shop.entiy.WxPayDetail;
import com.lsp.shop.util.IpUtils;
import com.lsp.shop.util.RequestHandler;
import com.lsp.shop.util.Sha1Util;
import com.lsp.shop.util.WXCommonUtil;
import com.lsp.suc.entity.Comunit;
import com.lsp.suc.entity.IntegralBackreord;
import com.lsp.suc.entity.IntegralRecord;
import com.lsp.user.entity.UserInfo;
import com.lsp.website.service.WwzService;
import com.lsp.weixin.entity.WxPayConfig;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.awt.windows.WWindowPeer;

@Namespace("/shop")
@Results({
		@org.apache.struts2.convention.annotation.Result(name = "reload", location = "shop.action", type = "redirect") })
public class ShopAction extends GeneralAction {
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
		String comid = Struts2Utils.getParameter("comid"); 
		getLscode();
		Struts2Utils.getRequest().setAttribute("custid", custid); 
		WxToken token = null;
		if (StringUtils.isNotEmpty(custid)) {
			token = GetAllFunc.wxtoken.get(custid);
		} else {
			token = GetAllFunc.wxtoken.get(SysConfig.getProperty("custid"));
		}
		if (token.getSqlx() > 0) {
			token = GetAllFunc.wxtoken.get(wwzService.getparentcustid(custid));
		}

		Struts2Utils.getRequest().setAttribute("token", WeiXinUtil.getSignature(token, Struts2Utils.getRequest()));
		token = WeiXinUtil.getSignature(token, Struts2Utils.getRequest());
		String url = "";

		if (StringUtils.isNotEmpty(comid)) {
			url = SysConfig.getProperty("ip") + "/shop/shop!index.action?comid=" + comid + "&custid=" + custid
					+ "&agid=" + agid;
		} else {
			url = SysConfig.getProperty("ip") + "/shop/shop!index.action?custid=" + custid + "&agid=" + agid;
		}
		if (StringUtils.isEmpty(fromUserid)) {
			String inspection = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + token.getAppid()
					+ "&redirect_uri=" + URLEncoder.encode(url)
					+ "&response_type=code&scope=snsapi_base&state=c1c2j3h4#wechat_redirect";
			Struts2Utils.getRequest().setAttribute("inspection", inspection);
			return "refresh";
		} else if (fromUserid.equals("register")) {
			String inspection = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + token.getAppid()
					+ "&redirect_uri=" + URLEncoder.encode(url)
					+ "&response_type=code&scope=snsapi_userinfo&state=register#wechat_redirect";
			Struts2Utils.getRequest().setAttribute("inspection", inspection);
			return "refresh";
		}
		Struts2Utils.getRequest().setAttribute("cid", comid);

		List<DBObject> list = wwzService.advertisement(custid, "shopmb-" + comid);
		Struts2Utils.getRequest().setAttribute("advertising", list);
		List<DBObject> list1 = wwzService.slide(custid, "shopmb-" + comid);
		Struts2Utils.getRequest().setAttribute("slide", list1);
		DBObject shopmb =null;
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(comid)) {
			whereMap.put("_id", Long.parseLong(comid));
			shopmb=baseDao.getMessage(PubConstants.SHOP_SHOPMB, whereMap);
		} else {
			// whereMap.put("lx",1);
		} 
		if (StringUtils.isEmpty(agid) || agid.equals("")) { 

			if (StringUtils.isNotEmpty(agid)) {
				if (StringUtils.isNotEmpty(comid)) {
					url = SysConfig.getProperty("ip") + "/shop/shop!index.action?comid=" + comid + "&custid=" + custid
							+ "&agid=" + agid;
				} else {
					url = SysConfig.getProperty("ip") + "/shop/shop!index.action?custid=" + custid + "&agid=" + agid;
				}
				Struts2Utils.getRequest().setAttribute("agid", agid);
			}

		}
		if (shopmb != null) {
			wwzService.flow(custid, "shop-" + shopmb.get("_id"));
		}

		wwzService.flow(custid, "shop");
		Struts2Utils.getRequest().setAttribute("entity", shopmb);
		// 加载分类
		whereMap.clear();
		HashMap<String, Object> sortMap = new HashMap<String, Object>();
		System.out.println(shopmb);
		if (shopmb != null) {
			System.out.println("加载店铺分类");
			whereMap.put("parentid", Long.parseLong(shopmb.get("_id").toString()));
			// 加载滚动
			
			//Struts2Utils.getRequest().setAttribute("roll", wwzService.getRoll(custid, "shopmb-" + shopmb.get("_id").toString()));
			Struts2Utils.getRequest().setAttribute("roll", wwzService.getRoll(custid, "shopmb-"));
			// 加载广告
			Struts2Utils.getRequest().setAttribute("slide",
					wwzService.getslide(custid, "shopmb-" + shopmb.get("_id").toString()));
			sortMap.put("sort", -1);
			// 获取店铺分类
			List<DBObject> typelist = baseDao.getList(PubConstants.SHOP_SHOPTYPE, whereMap, sortMap);
			Struts2Utils.getRequest().setAttribute("typelist", typelist);
		} else {
			System.out.println("加载商城分类");
			// 加载滚动
			Struts2Utils.getRequest().setAttribute("roll", wwzService.getRoll(custid, "shopmb-"));
			// 加载广告
			Struts2Utils.getRequest().setAttribute("slide", wwzService.getslide(custid, "shopmb-"));
			whereMap.clear();
			whereMap.put("parentid", 0L);
			sortMap.put("sort", 1);
			// 获取店铺分类
			List<DBObject> typelists = baseDao.getList(PubConstants.SHOP_PROTYPE, whereMap, sortMap);
			Struts2Utils.getRequest().setAttribute("typelists", typelists);
		 
			// 获取店铺分类
			List<DBObject> typelist = baseDao.getList(PubConstants.SHOP_SHOPTYPE, whereMap, sortMap);
			Struts2Utils.getRequest().setAttribute("typelist", typelist);
			
			

		}
		DBObject share = new BasicDBObject();
		if (shopmb != null) {
			share.put("fxtitle", shopmb.get("name"));
			share.put("fxsummary", shopmb.get("summary"));
			share.put("fximg", shopmb.get("logo"));
		}

		if (StringUtils.isNotEmpty(comid)) {
			share.put("fxurl", url);
		} else {
			share.put("fxurl", url);
		}
		// 加载代理分享
		DBObject agent = wwzService.getAgent(agid);
		if (agent != null) {
			share.put("fxtitle", wwzService.getWxUsertype(agent.get("fromUserid").toString(), "nickname") + "的小店");
			share.put("fxsummary", "我的店铺新到很多产品，大家快来看看！");
			share.put("fximg", wwzService.getWxUsertype(agent.get("fromUserid").toString(), "headimgurl"));
			agent.put("nickname", wwzService.getWxUsertype(agent.get("fromUserid").toString(), "nickname"));
			agent.put("headimgurl", wwzService.getWxUsertype(agent.get("fromUserid").toString(), "headimgurl"));
			Struts2Utils.getRequest().setAttribute("agent", agent);
		}
		Struts2Utils.getRequest().setAttribute("share", share);

		// 检测代理
		if (wwzService.checkAgent(agid, custid, fromUserid)) {
			Struts2Utils.getRequest().setAttribute("isAgent", "ok");
		}
		// 检测全局代理
		if (wwzService.checkAgent(custid, fromUserid)) {
			Struts2Utils.getRequest().setAttribute("isAgents", "ok");
		}
		
		if (shopmb != null) {
			// 检测当前店铺
			if (StringUtils
					.isNotEmpty(wwzService.getAgid(shopmb.get("_id").toString(), wwzService.getVipNo(fromUserid)))) {
				Struts2Utils.getRequest().setAttribute("isAgentcom", "ok");
			}
		}
		 
		if(shopmb!=null){
			System.out.println(shopmb.get("mb"));	
		}
		whereMap.clear();
		whereMap.put("type", "home-ad");
		List<DBObject> slideAdlist = baseDao.getList(PubConstants.SUC_SLIDE, whereMap, sortMap);
		if(slideAdlist.size()>0){
			for (int i = 0; i < slideAdlist.size(); i++) {
				String k = "slideAd"+i;
				String v = slideAdlist.get(i).get("picurl").toString();
				Struts2Utils.getRequest().setAttribute(k, v);
			}
			
		}
		
		if (shopmb != null) {
			if (shopmb.get("type") != null && Integer.parseInt(shopmb.get("type").toString()) == 1) {
				return "jfdh" + shopmb.get("mb");
			} else {
				return "index" + shopmb.get("mb");
			}
		} else {
			System.out.println("9999");
			return "index0";
		}

	}

	/**
	 * 主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public void ajaxindex() throws Exception {

		HashMap<String, Object> sortMap = new HashMap<String, Object>();
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		HashMap<String, Object> backMap = new HashMap<String, Object>();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		if (Struts2Utils.getParameter("fypage") != null) {
			fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		Long comid = Long.parseLong(Struts2Utils.getParameter("comid"));
		whereMap.put("comid", comid);

		String sel = Struts2Utils.getParameter("sel");
		if (StringUtils.isNotEmpty(sel)) {
			Pattern pattern = Pattern.compile("^.*" + sel + ".*$", Pattern.CASE_INSENSITIVE);
			whereMap.put("ptitle", pattern);

		}
		sortMap.put("sort", -1);

		backMap.put("context", 0);
		List<DBObject> proList = baseDao.getList(PubConstants.DATA_PRODUCT, whereMap, fypage, 10, sortMap, backMap);
		if (proList.size() == 0) {
			sub_map.put("state", 1);
		} else {
			sub_map.put("state", 0);
		}

		sub_map.put("list", proList);
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 出货单
	 * 
	 * @return
	 * @throws IOException
	 */
	public String orderform() throws IOException {
		getLscode();
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("lscode", lscode);
		String state=Struts2Utils.getParameter("state");
		Struts2Utils.getRequest().setAttribute("state", state);
		// 加载订单量
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("fromUserid", fromUserid); 
		if(StringUtils.isNotEmpty(state)) {
			whereMap.put("state", Integer.parseInt(state));
		} 
		Struts2Utils.getRequest().setAttribute("ordercount", baseDao.getCount(PubConstants.WX_ORDERFORM, whereMap));
		whereMap.clear(); 
		whereMap.put("fromUserid", fromUserid);
		DBObject db = baseDao.getMessage(PubConstants.SUC_INTEGRALRECORD, whereMap);
		System.out.println(db);
		Struts2Utils.getRequest().setAttribute("jf",db);
		DBObject user = baseDao.getMessage(PubConstants.USER_INFO, fromUserid);
		if(user!=null){
			if(user.get("isfull") != null){
				Struts2Utils.getRequest().setAttribute("isfull", user.get("isfull").toString());
			}
		}
		return "orderform";
	}

	/**
	 * 确认货单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String fahuo() throws Exception {
		custid = getCustid();
		lscode = getLscode();
		List<DBObject> relist = new ArrayList<DBObject>();
		Long _id = Long.parseLong(Struts2Utils.getParameter("_id"));

		DBObject order = baseDao.getMessage(PubConstants.WX_ORDERFORM, _id);
		OrderForm entity = (OrderForm) UniObject.DBObjectToObject(order, OrderForm.class);
		entity.set_id(_id);
		entity.setState(3);
		baseDao.insert(PubConstants.WX_ORDERFORM, entity);
		orderform();
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("lscode", lscode);
		return "orderform";
	}

	/**
	 * 确认货单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loginout() throws Exception {
		Struts2Utils.getSession().setAttribute("fromLogin", null);
		Struts2Utils.getRequest().setAttribute("method", "/wwz/wwz!fromuser.action");
		return "refresh";
	}

	/**
	 * 微信支付
	 * 
	 * @throws Exception
	 */
	public void wxpay() throws Exception {
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		getLscode();
		DBObject wx = wwzService.getWxUser(fromUserid);
		if (wx.get("_id").equals("notlogin")) {
			params.put("state", 3);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
		WxToken wxtoken = GetAllFunc.wxtoken.get(custid);
		WxPayConfig wxconfig = new WxPayConfig();
		if (wxtoken.getQx() == 0) {
			params.put("state", 1);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		} else if (wxtoken.getQx() == 1) {
			wxconfig = GetAllFunc.wxPay.get(custid);
		} else if (wxtoken.getQx() == 2) {// 父类结算
			wxconfig = GetAllFunc.wxPay.get(wwzService.getparentcustid(custid));
		}
		// 支付的价格
		String price = Struts2Utils.getParameter("price");
		// 获取提交的商品名称
		String remark = Struts2Utils.getParameter("remark");
		// 商品类型
		int lx = Integer.parseInt(Struts2Utils.getParameter("lx"));
		// 总金额
		double total = Double.parseDouble(Struts2Utils.getParameter("total"));

		Long recordid = 0L;
		// 商品编号
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("recordid"))) {
			recordid = Long.parseLong(Struts2Utils.getParameter("recordid"));// 14
		}
		double remoney = 0f;
		// 商品价格
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("remoney"))) {
			remoney = Double.parseDouble(Struts2Utils.getParameter("remoney"));// 14
		}
		// 地址信息
		String name = Struts2Utils.getParameter("name");
		String tel = Struts2Utils.getParameter("tel");
		String address = Struts2Utils.getParameter("address");
		String no = Struts2Utils.getParameter("no");
		// 店铺编号
		Long comid = 0L;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("comid"))) {
			comid = Long.parseLong(Struts2Utils.getParameter("comid"));// 14
		}
		String nums = Struts2Utils.getParameter("num");
		// 数量
		int num = Integer.parseInt(nums);
		Long proid = 0L;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("proid"))) {
			proid = Long.parseLong(Struts2Utils.getParameter("proid"));// 14
		}
		// 规格
		String spec = Struts2Utils.getParameter("spec");
		String kjid = Struts2Utils.getParameter("kjid");
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 返还
		String jffh = Struts2Utils.getParameter("jffh");
		// 兑换
		String jfdh = Struts2Utils.getParameter("jfdh");
		// 10位序列号,可以自行调整。
		// 限购
		HashMap<String, Object> backMap = new HashMap<String, Object>();
		backMap.put("context", 0);
		backMap.put("summary", 0);
		DBObject pro = baseDao.getMessage(PubConstants.DATA_PRODUCT, recordid, backMap);
		if (pro.get("gmcs") != null && Integer.parseInt(pro.get("gmcs").toString()) > 0) {
			HashMap<String, Object> whereMap = new HashMap<>();
			whereMap.put("pid", Integer.parseInt(pro.get("_id").toString()));
			whereMap.put("fromUserid", fromUserid);
			int ll = 0;
			List<DBObject> listdb = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap, null);
			for (DBObject dbObject : listdb) {
				DBObject order = baseDao.getMessage(PubConstants.WX_ORDERFORM, dbObject.get("orderid").toString());
				if (order != null && Integer.parseInt(order.get("state").toString()) != 1) {
					ll += Integer.parseInt(order.get("count").toString());
				}
			}
			if (ll >= Integer.parseInt(pro.get("gmcs").toString())) {
				// 购买次数已完
				params.put("state", 10);
				String json = JSONArray.fromObject(params).toString();
				Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
				return;
			}
		}

		String orderno = DateFormat.getDate() + strRandom + mongoSequence.currval("orderno");
		OrderForm entity = new OrderForm();
		entity.set_id(orderno);
		entity.setState(1);
		entity.setNo(no);
		entity.setLx(lx);

		entity.setFromUserid(fromUserid);
		entity.setCustid(custid);
		entity.setName(name);
		entity.setTel(tel);
		entity.setAddress(address);
		entity.setInsDate(new Date());

		entity.setComid(comid);// 14
		entity.setCount(num);// 15
		entity.setTotal(total);// 6
		entity.setKjid(kjid);
		if (StringUtils.isNotEmpty(jfdh)) {
			entity.setJfdh(Double.parseDouble(jfdh));
		}
		entity.setZfmoney(Double.parseDouble(price));// 7
		entity.setRecordid(recordid);
		entity.setRemoney(remoney);

		entity.setRemark(remark + "-" + spec);
		if (StringUtils.isNotEmpty(jffh)) {
			entity.setJffh(Double.parseDouble(jffh));
		}
		String zfmoneys = "";
		String cost = "";
		String profit = "";
		if (pro.get("price") != null) {
			// 支付的价格
			zfmoneys = BaseDecimal.multiplication(pro.get("price").toString(), nums);
			entity.setZfmoney(Double.parseDouble(zfmoneys));
			// 总价
			entity.setTotal(entity.getZfmoney());
			if (pro.get("cost") != null) {
				// 成本
				cost = BaseDecimal.multiplication(pro.get("cost").toString(), nums);
				entity.setCost(Double.parseDouble(cost));
				// 收益
				profit = BaseDecimal.subtract(zfmoneys, cost);
				entity.setProfit(Double.parseDouble(profit));
			}
		}

		baseDao.insert(PubConstants.WX_ORDERFORM, entity);

		if (pro.get("goodstype") != null) {
			if (pro.get("goodstype").toString().equals("3")) {// 大众区商品返还币种二

			}
			if (pro.get("goodstype").toString().equals("4")) {// 特约区商品返还币种一
				// 店铺地址
				DBObject db = wwzService.getCustUser(custid);

				// 下单人信息
				DBObject user = wwzService.getCustUser(fromUserid);
				if (db != null) {
					if (db.get("").toString().equals(address)) {
						// 省代金额计算
						BaseDecimal.multiplication(profit, "0.02");
						// 市代金额计算
						BaseDecimal.multiplication(profit, "0.03");
						// 县代金额计算
						BaseDecimal.multiplication(profit, "0.05");

						// 运营部金额计算

					} else {// 跨区域

					}
				}

			}
		}

		DBObject com = baseDao.getMessage(PubConstants.SHOP_SHOPMB, comid, backMap);
		// JmsService.permessageMessage(custid, fromUserid, "订单信息",
		// "用户:"+wwzService.getWxUsertype(fromUserid,
		// "nickname")+"有一条新订单",null,pro.get("picurl").toString(),"shop-nopay","3",
		// com.get("title").toString(),orderno,pro.get("ptitle").toString(), num+"",
		// "0");
		if (pro != null) {
			OrderFormpro o = new OrderFormpro();
			o.set_id(mongoSequence.currval(PubConstants.SHOP_ODERFORMPRO));
			o.setCount(num);
			o.setOrderid(orderno);
			o.setPro(pro);
			o.setSpec(spec);
			o.setFromUserid(fromUserid);
			o.setPid(Long.parseLong(pro.get("_id").toString()));
			baseDao.insert(PubConstants.SHOP_ODERFORMPRO, o);
		}

		StringBuffer attach = new StringBuffer(orderno);// 0
		attach.append("&").append(custid);//
		attach.append("&").append(jffh);
		attach.append("&").append(fromUserid);
		attach.append("&").append(agid);

		String nonce_str = PayCommonUtil.CreateNoncestr();
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", wxconfig.getAppid());
		parameters.put("mch_id", wxconfig.getPartner());
		parameters.put("nonce_str", nonce_str);
		parameters.put("attach", attach.toString());
		parameters.put("body", remark);
		parameters.put("is_subscribe", "Y");
		parameters.put("out_trade_no", orderno);
		parameters.put("total_fee", BaseDecimal.round(BaseDecimal.multiplication(price, "100"), 0));
		parameters.put("spbill_create_ip", Struts2Utils.getRequest().getRemoteAddr());
		parameters.put("notify_url", this.getCtxurl() + "/shop/shop!payok.action");
		parameters.put("trade_type", "JSAPI");
		parameters.put("openid", wwzService.getWxUser(fromUserid).get("fromUser").toString());

		String sign = PayCommonUtil.createSign("UTF-8", parameters, wxconfig.getPartner_key());
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);

		String result = CommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);

		Map<String, String> map = XMLUtil.doXMLParse(result);

		params.put("appId", wxconfig.getAppid());
		params.put("timeStamp", Long.toString(new Date().getTime()));
		params.put("nonceStr", nonce_str);
		params.put("package", "prepay_id=" + map.get("prepay_id"));
		params.put("signType", "MD5");
		String paySign = PayCommonUtil.createSign("UTF-8", params, wxconfig.getPartner_key());
		params.put("packageValue", "prepay_id=" + map.get("prepay_id")); // 这里用packageValue是预防package是关键字在js获取值出错
		params.put("paySign", paySign);
		if (StringUtils.isNotEmpty(jffh) && Double.parseDouble(jffh) > 0) {
			params.put("jffh", jffh);
		} // paySign的生成规则和Sign的生成规则一致
		params.put("state", 0);

		params.put("orderno", orderno);
		String json = JSONArray.fromObject(params).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 通知
	 * 
	 * @throws Exception
	 */
	public void payok() throws Exception {
		InputStream inStream = Struts2Utils.getRequest().getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}

		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");

		Map<Object, Object> map = XMLUtil.doXMLParse(result);
		String return_code = map.get("return_code").toString();

		if (return_code.equals("SUCCESS")) {
			fromUser = map.get("openid").toString(); // 推送信息时用到
			String[] attach = map.get("attach").toString().split("&");
			String orderno = attach[0];
			String custid = attach[1];
			String jffh = attach[2];
			String fromUserid = attach[3];
			String agid = attach[4];
			// 更新订单
			DBObject orderdb = baseDao.getMessage(PubConstants.WX_ORDERFORM, orderno);
			OrderForm entity = (OrderForm) UniObject.DBObjectToObject(orderdb, OrderForm.class);
			entity.set_id(orderno);
			entity.setState(2);// 2为已经支付成功
			baseDao.insert(PubConstants.WX_ORDERFORM, entity);
			// 更新库存
			DBObject pro = baseDao.getMessage(PubConstants.DATA_PRODUCT, entity.getRecordid());
			if (pro != null) {
				ProductInfo obj = (ProductInfo) UniObject.DBObjectToObject(pro, ProductInfo.class);
				obj.setGmnum(obj.getGmnum() + entity.getCount());
				obj.setNum(obj.getNum() - entity.getCount());
				baseDao.insert(PubConstants.DATA_PRODUCT, obj);

				// 记录提成
				double price = (obj.getPrice() - obj.getDlprice()) * entity.getCount();
				if (price > 0) {

					wwzService.addAgent(agid, price, orderno,
							wwzService.getfromUseridVipNo(agid.substring(agid.indexOf("-") + 1)), custid);

				}

			}
			DBObject mb = baseDao.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(pro.get("comid").toString()));
			if (mb != null) {
				ShopMb shopmb = (ShopMb) UniObject.DBObjectToObject(mb, ShopMb.class);
				shopmb.setSales(shopmb.getSales() + Double.parseDouble(entity.getZfmoney() + ""));
				baseDao.insert(PubConstants.SHOP_SHOPMB, shopmb);
			}

			if (StringUtils.isNotEmpty(jffh) && Double.parseDouble(jffh) > 0) {
				wwzService.addjf(jffh, fromUserid, "shop-fh", custid, null);
			}
			if (entity.getJfdh() > 0) {
				wwzService.deljf(entity.getJfdh() + "", fromUserid, "shop-dh", custid, null);
			}
			Paymentorder paymentorder = new Paymentorder();
			paymentorder.set_id(orderno);
			paymentorder.setComid(entity.getComid());
			paymentorder.setCustid(custid);
			paymentorder.setFromUserid(fromUserid);
			paymentorder.setPrice(Double.parseDouble(entity.getZfmoney() + ""));
			paymentorder.setCreatedate(new Date());
			paymentorder.setType(3);
			baseDao.insert(PubConstants.SHOP_PAYMENTORDER, paymentorder);
			MessageInfo me = new MessageInfo();
			me.setCustid(custid);
			me.setFromUserid(fromUserid);
			me.setTitle("订单提醒");
			me.setSummary("您有一条来自" + wwzService.getWxUsertype(fromUserid, "nickname") + "的订单");
			me.setPicurl("");
			me.setUrl("");
			me.setLx("shop-pay");
			me.setType(3);
			me.setComname(baseDao.getMessage(PubConstants.SHOP_SHOPMB, entity.getComid()).get("name").toString());
			me.setProcount(entity.getCount() + "");
			me.setProtitle(pro.get("ptitle").toString() + " " + entity.getCount() + "件");
			me.setProstate("2");
			me.setOrderid(orderno);
			JmsService.permessageMessage(me);

			Struts2Utils.getResponse().getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));

		} else {
			Struts2Utils.getResponse().getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));
		}

	}

	/**
	 * 微信支付
	 * 
	 * @throws Exception
	 */
	public void wxcarpay() throws Exception {
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		getLscode();
		DBObject wx = wwzService.getWxUser(fromUserid);
		if (wx.get("_id").equals("notlogin")) {
			params.put("state", 3);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
		String comtoUser = toUser;
		WxToken wxtoken = GetAllFunc.wxtoken.get(custid);
		WxPayConfig wxconfig = new WxPayConfig();
		if (wxtoken.getQx() == 0) {
			params.put("state", 1);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		} else if (wxtoken.getQx() == 1) {
			wxconfig = GetAllFunc.wxPay.get(custid);
		} else if (wxtoken.getQx() == 2) {// 父类结算
			wxconfig = GetAllFunc.wxPay.get(wwzService.getparentcustid(custid));
		}

		if (wxconfig == null || wxconfig.getAppid() == null) {
			params.put("state", 1);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
		// 支付的价格
		String price = Struts2Utils.getParameter("price");
		// 获取提交的商品名称
		String remark = Struts2Utils.getParameter("remark");
		// 商品类型
		int lx = Integer.parseInt(Struts2Utils.getParameter("lx"));// 0 商品 1选号 2扫码付3优惠劵4砍价
		// 总金额
		float total = 0f;

		String recordid = null;
		// 商品编号
		if (StringUtils.isNotEmpty(Struts2Utils.getParameter("recordid"))) {
			recordid = Struts2Utils.getParameter("recordid");// 14
		}
		String remoney = null;
		// 商品价格
		if (StringUtils.isNotEmpty(Struts2Utils.getParameter("remoney"))) {
			remoney = Struts2Utils.getParameter("remoney");// 14
		}
		String spec = null;
		if (StringUtils.isNotEmpty(Struts2Utils.getParameter("spec"))) {
			spec = Struts2Utils.getParameter("spec");
		}

		// 地址信息
		String name = Struts2Utils.getParameter("name");
		String tel = Struts2Utils.getParameter("tel");
		String address = Struts2Utils.getParameter("address");
		String no = Struts2Utils.getParameter("no");
		// 店铺编号
		Long comid = 0L;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("comid"))) {
			comid = Long.parseLong(Struts2Utils.getParameter("comid"));// 14
		}
		// 数量
		String num = Struts2Utils.getParameter("num");
		Long proid = 0L;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("proid"))) {
			proid = Long.parseLong(Struts2Utils.getParameter("proid"));// 14
		}

		double jffh = 0;

		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String orderno = DateFormat.getDate() + strRandom + mongoSequence.currval("orderno");

		OrderForm entity = new OrderForm();
		entity.set_id(orderno);
		entity.setState(1);
		entity.setNo(no);
		entity.setLx(lx);

		entity.setFromUserid(fromUserid);
		entity.setCustid(custid);
		entity.setName(name);
		entity.setTel(tel);
		entity.setAddress(address);
		entity.setInsDate(new Date());

		entity.setComid(comid);// 14
		entity.setCounts(num);// 15
		entity.setTotal(total);// 6

		entity.setZfmoney(Double.parseDouble(price));// 7
		entity.setIds(recordid);
		entity.setRemark(remark);

		String cost = "";
		String zfmoney = "";

		String[] ids = recordid.split(",");
		String[] nums = num.split(",");
		String[] specs = spec.split(",");
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isNotEmpty(ids[i])) {
				DBObject shop = baseDao.getMessage(PubConstants.SUC_SHOPPINGCART, Long.parseLong(ids[i]));
				HashMap<String, Object> backMap = new HashMap<String, Object>();
				backMap.put("context", 0);
				backMap.put("summary", 0);
				DBObject pro = baseDao.getMessage(PubConstants.DATA_PRODUCT, Long.parseLong(shop.get("pid").toString()),
						backMap);

				if (pro.get("gmcs") != null && Integer.parseInt(pro.get("gmcs").toString()) > 0) {
					HashMap<String, Object> whereMap = new HashMap<>();
					whereMap.put("pid", Integer.parseInt(pro.get("_id").toString()));
					whereMap.put("fromUserid", fromUserid);
					int ll = 0;
					List<DBObject> listdb = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap, null);
					for (DBObject dbObject : listdb) {
						DBObject order = baseDao.getMessage(PubConstants.WX_ORDERFORM,
								dbObject.get("orderid").toString());
						if (order != null && Integer.parseInt(order.get("state").toString()) != 1) {
							ll += Integer.parseInt(order.get("count").toString());
						}
					}
					if (ll >= Integer.parseInt(pro.get("gmcs").toString())) {
						// 购买次数已完
						params.put("state", 10);
						String json = JSONArray.fromObject(params).toString();
						Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
						return;
					}
				}

				if (pro.get("price") != null) {
					String zfmoneys = zfmoney;
					System.out.println("赋值的钱数---》" + zfmoneys);
					// 商品价格
					zfmoney = BaseDecimal.multiplication(pro.get("price").toString(), nums[i]);
					System.out.println("当前商品的价格---》" + zfmoney);
					zfmoney = BaseDecimal.add(zfmoney, zfmoneys);
					System.out.println("当前商品的价格1---》" + zfmoney);
					if (pro.get("cost") != null) {
						String costs = cost;
						System.out.println("赋值的成本---》" + costs);
						// 成本
						cost = BaseDecimal.multiplication(pro.get("cost").toString(), nums[i]);
						System.out.println("当前商品的成本---》" + cost);
						cost = BaseDecimal.add(cost, costs);
						System.out.println("当前商品的成本1---》" + cost);
					}
				}

				// 生成信息
				if (pro != null) {
					if (pro.get("jffh") != null) {
						jffh = jffh + Double.parseDouble(pro.get("jffh").toString());
					}
					OrderFormpro ord = new OrderFormpro();
					ord.set_id(mongoSequence.currval(PubConstants.SHOP_ODERFORMPRO));

					ord.setOrderid(orderno);
					ord.setCount(Integer.parseInt(nums[i]));
					ord.setPro(pro);
					ord.setPid(Long.parseLong(pro.get("_id").toString()));
					ord.setSpec(specs[i]);
					baseDao.insert(PubConstants.SHOP_ODERFORMPRO, ord);
				}

			}
		}
		entity.setZfmoney(Double.parseDouble(zfmoney));
		System.out.println("支付最后结果---" + entity.getZfmoney());
		entity.setCost(Double.parseDouble(cost));
		System.out.println("成本最后结果---" + entity.getCost());
		entity.setProfit(Double.parseDouble(BaseDecimal.subtract(zfmoney, cost)));
		System.out.println("收益最后结果---" + entity.getProfit());
		entity.setJffh(jffh);
		baseDao.insert(PubConstants.WX_ORDERFORM, entity);

		StringBuffer attach = new StringBuffer(orderno);// 0
		attach.append("&").append(custid);//
		attach.append("&").append(jffh);//
		attach.append("&").append(fromUserid);//
		attach.append("&").append(agid);//

		String nonce_str = PayCommonUtil.CreateNoncestr();
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", wxconfig.getAppid());
		parameters.put("mch_id", wxconfig.getPartner());
		parameters.put("nonce_str", nonce_str);
		parameters.put("attach", attach.toString());
		parameters.put("body", remark);
		parameters.put("out_trade_no", orderno);
		parameters.put("total_fee", BaseDecimal.round(BaseDecimal.multiplication(price, "100"), 0));
		parameters.put("spbill_create_ip", Struts2Utils.getRequest().getRemoteAddr());
		parameters.put("notify_url", this.getCtxurl() + "/shop/shop!paycarok.action");
		parameters.put("trade_type", "JSAPI");
		parameters.put("openid", wwzService.getWxUser(fromUserid).get("fromUser").toString());

		String sign = PayCommonUtil.createSign("UTF-8", parameters, wxconfig.getPartner_key());
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);

		String result = CommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);

		Map<String, String> map = XMLUtil.doXMLParse(result);

		params.put("appId", wxconfig.getAppid());
		params.put("timeStamp", Long.toString(new Date().getTime()));
		params.put("nonceStr", nonce_str);
		params.put("package", "prepay_id=" + map.get("prepay_id"));
		params.put("signType", "MD5");
		String paySign = PayCommonUtil.createSign("UTF-8", params, wxconfig.getPartner_key());
		params.put("packageValue", "prepay_id=" + map.get("prepay_id")); // 这里用packageValue是预防package是关键字在js获取值出错
		params.put("paySign", paySign);
		if (jffh > 0) {
			params.put("jffh", jffh);
		} // paySign的生成规则和Sign的生成规则一致
		params.put("state", 0);

		params.put("orderno", orderno);
		String json = JSONArray.fromObject(params).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 通知
	 * 
	 * @throws Exception
	 */
	public void paycarok() throws Exception {
		InputStream inStream = Struts2Utils.getRequest().getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}

		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");

		Map<Object, Object> map = XMLUtil.doXMLParse(result);
		String return_code = map.get("return_code").toString();

		if (return_code.equals("SUCCESS")) {
			fromUser = map.get("openid").toString(); // 推送信息时用到
			String[] attach = map.get("attach").toString().split("&");
			String orderno = attach[0];
			String custid = attach[1];
			String jffh = attach[2];
			String fromUserid = attach[3];
			String agid = attach[4];
			DBObject orderdb = baseDao.getMessage(PubConstants.WX_ORDERFORM, orderno);
			OrderForm entity = (OrderForm) UniObject.DBObjectToObject(orderdb, OrderForm.class);
			// 支付成功
			entity.set_id(orderno);
			entity.setState(2);// 2为已经支付成功
			baseDao.insert(PubConstants.WX_ORDERFORM, entity);
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("orderid", orderno);
			List<DBObject> list = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap, null);
			String ptitle = "";
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					OrderFormpro db = (OrderFormpro) UniObject.DBObjectToObject(dbObject, OrderFormpro.class);
					DBObject pro = db.getPro();
					if (pro != null) {
						ProductInfo obj = (ProductInfo) UniObject.DBObjectToObject(pro, ProductInfo.class);
						obj.setGmnum(obj.getGmnum() + db.getCount());
						obj.setNum(obj.getNum() - db.getCount());
						baseDao.insert(PubConstants.DATA_PRODUCT, obj);
						ptitle += obj.getPtitle() + " " + db.getCount() + "件,";
						DBObject mb = baseDao.getMessage(PubConstants.SHOP_SHOPMB, obj.getComid());
						if (mb != null) {
							ShopMb shopmb = (ShopMb) UniObject.DBObjectToObject(mb, ShopMb.class);
							shopmb.setSales(shopmb.getSales() + obj.getPrice());
							baseDao.insert(PubConstants.SHOP_SHOPMB, shopmb);
						}
						// 记录提成
						double price = (obj.getPrice() - obj.getDlprice()) * db.getCount();
						if (price > 0) {
							wwzService.addAgent(agid, price, orderno,
									wwzService.getfromUseridVipNo(agid.substring(agid.indexOf("-") + 1)), custid);
						}
					}
				}
			}

			if (StringUtils.isNotEmpty(jffh) && Double.parseDouble(jffh) > 0) {
				wwzService.addjf(jffh, fromUserid, "shop-fh", custid, null);
			}
			Paymentorder paymentorder = new Paymentorder();
			paymentorder.set_id(orderno);
			paymentorder.setComid(entity.getComid());
			paymentorder.setCustid(custid);
			paymentorder.setFromUserid(fromUserid);
			paymentorder.setPrice(Double.parseDouble(entity.getZfmoney() + ""));
			paymentorder.setType(2);
			paymentorder.setCreatedate(new Date());
			baseDao.insert(PubConstants.SHOP_PAYMENTORDER, paymentorder);
			MessageInfo me = new MessageInfo();
			me.setCustid(custid);
			me.setFromUserid(fromUserid);
			me.setTitle("订单提醒");
			me.setSummary("您有一条来自" + wwzService.getWxUsertype(fromUserid, "nickname") + "的订单");
			me.setPicurl("");
			me.setUrl("");
			me.setLx("shop-pay");
			me.setType(3);
			me.setComname(baseDao.getMessage(PubConstants.SHOP_SHOPMB, entity.getComid()).get("name").toString());
			me.setProcount("");
			me.setProtitle(ptitle);
			me.setProstate("2");
			me.setOrderid(orderno);
			JmsService.permessageMessage(me);

			Struts2Utils.getResponse().getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));

		} else {
			Struts2Utils.getResponse().getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));
		}

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

	/**
	 * ajax获取列表
	 */
	public void ajaxweb() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			String cid = Struts2Utils.getParameter("cid");
			custid = Struts2Utils.getParameter("custid");
			String lx = Struts2Utils.getParameter("lx");
			String sel = Struts2Utils.getParameter("sel");
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			HashMap<String, Object> backMap = new HashMap<String, Object>();
			BasicDBObject dateCondition = new BasicDBObject();
			// 去除不显示的数据
			dateCondition.append("$ne", 1);
			whereMap.put("isxs", dateCondition);
			if (Struts2Utils.getParameter("fypage") != null) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			if (StringUtils.isNotEmpty(lx)) {
				Pattern pattern = Pattern.compile("^.*" + lx + ".*$", Pattern.CASE_INSENSITIVE);
				whereMap.put("hylx", pattern);
			}
			if (StringUtils.isNotEmpty(sel)) {
				Pattern pattern = Pattern.compile("^.*" + sel + ".*$", Pattern.CASE_INSENSITIVE);
				whereMap.put("ptitle", pattern);
			}
			if (StringUtils.isNotEmpty(cid)) {
				DBObject obj = baseDao.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(cid));
				whereMap.put("comid", Long.parseLong(cid));
			}

			sortMap.put("sort", -1);
			backMap.put("context", 0);
			List<DBObject> list = baseDao.getList(PubConstants.DATA_PRODUCT, whereMap, fypage, 10, sortMap, backMap);
			if (list.size() > 0) {
				sub_map.put("state", 0);
				sub_map.put("list", list);
			} else {
				sub_map.put("state", 1);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}
	
	/**
	 * ajax获取列表
	 */
	public void discountAjaxweb() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			String cid = Struts2Utils.getParameter("cid");
			custid = Struts2Utils.getParameter("custid");
			int discountType = Integer.parseInt(Struts2Utils.getParameter("discountType"));
			String lx = Struts2Utils.getParameter("lx");
			String sel = Struts2Utils.getParameter("sel");
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			HashMap<String, Object> backMap = new HashMap<String, Object>();
			BasicDBObject dateCondition = new BasicDBObject();
			// 去除不显示的数据
			dateCondition.append("$ne", 1);
			whereMap.put("isxs", dateCondition);
			whereMap.put("discountType",discountType);
			if (Struts2Utils.getParameter("fypage") != null) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			if (StringUtils.isNotEmpty(lx)) {
				Pattern pattern = Pattern.compile("^.*" + lx + ".*$", Pattern.CASE_INSENSITIVE);
				whereMap.put("hylx", pattern);
			}
			if (StringUtils.isNotEmpty(sel)) {
				Pattern pattern = Pattern.compile("^.*" + sel + ".*$", Pattern.CASE_INSENSITIVE);
				whereMap.put("ptitle", pattern);
			}
			if (StringUtils.isNotEmpty(cid)) {
				DBObject obj = baseDao.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(cid));
				whereMap.put("comid", Long.parseLong(cid));
			}

			sortMap.put("sort", -1);
			backMap.put("context", 0);
			List<DBObject> list = baseDao.getList(PubConstants.DATA_PRODUCT, whereMap, fypage, 10, sortMap, backMap);
			if (list.size() > 0) {
				sub_map.put("state", 0);
				sub_map.put("list", list);
			} else {
				sub_map.put("state", 1);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * ajax添加购物车
	 */
	public void ajaxshopcarsave() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		HashMap<String, Object>whereMap = new HashMap<>();
		try {
			getLscode();
			String id = Struts2Utils.getParameter("id");
			String pid = Struts2Utils.getParameter("pid");
			String spec = Struts2Utils.getParameter("spec");
			String counts = Struts2Utils.getParameter("count");
			String price = Struts2Utils.getParameter("price");
			String type = Struts2Utils.getParameter("type");//add-增加   desc-减少
			Shoppingcart shop = new Shoppingcart();
			whereMap.put("fromUserid", fromUserid);
			if(StringUtils.isNotEmpty(id)){
				whereMap.put("_id", Long.parseLong(id));
			}
			if(StringUtils.isNotEmpty(pid)){
				whereMap.put("pid", Long.parseLong(pid));
			}
			if(StringUtils.isNotEmpty(spec)){
				whereMap.put("spec", spec);
			}
			
			DBObject dbObject = baseDao.getMessage(PubConstants.SUC_SHOPPINGCART, whereMap);
			if(dbObject != null){
				shop = (Shoppingcart) UniObject.DBObjectToObject(dbObject, Shoppingcart.class);
				if(StringUtils.isNotEmpty(type)){
					if(type.equals("add")){
						shop.setCount(shop.getCount()+1);
					}else{
						shop.setCount(shop.getCount()-1);
					}
				}
				System.out.println("cart--111-->"+shop.getCount());
			}else{
				System.out.println("cart--111-->进入这个方法");
				shop.set_id(mongoSequence.currval(PubConstants.SUC_SHOPPINGCART));
				shop.setCount(1);
				shop.setFromUserid(fromUserid);
				shop.setCreatedate(new Date());
				shop.setCustid(custid);
				shop.setPid(Long.parseLong(pid));
				shop.setSpec(spec);
				shop.setCount(Integer.parseInt(counts));
				shop.setState(0);
				if (StringUtils.isNotEmpty(price)) {
					shop.setPrice(Double.parseDouble(price));
				}
			}
			baseDao.insert(PubConstants.SUC_SHOPPINGCART, shop);
			//HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.clear();
			whereMap.put("fromUserid", fromUserid);
			whereMap.put("custid", custid);
			Long count = baseDao.getCount(PubConstants.SUC_SHOPPINGCART, whereMap);
			sub_map.put("state", 0);
			sub_map.put("value", count);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 移除购物车
	 */
	public void ajaxdelshopcar() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("fromUserid", fromUserid);
			whereMap.put("_id", Long.parseLong(id));
			baseDao.delete(PubConstants.SUC_SHOPPINGCART, whereMap);
			sub_map.put("state", 0);
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * ajax获取购物车列表
	 */
	public void ajaxshopcarweb() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			getLscode();
			double  bl=wwzService.getPPBSprice();
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			whereMap.put("fromUserid", fromUserid);
			whereMap.put("custid", custid);
			sortMap.put("ceatedate", -1);
			if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			List<Long> comList = new ArrayList<Long>();
			List<DBObject> shopmblist = new ArrayList<DBObject>();
			List<DBObject> productlist = new ArrayList<DBObject>();
			List<DBObject> list = baseDao.getList(PubConstants.SUC_SHOPPINGCART, whereMap, fypage, 10, sortMap);
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					DBObject db = baseDao.getMessage(PubConstants.DATA_PRODUCT,Long.parseLong(dbObject.get("pid").toString()));
					if (db != null) {
						long cid = Long.valueOf(String.valueOf(db.get("comid")));
						productlist.add(db);
//						dbObject.put("product", db);
//						dbObject.put("ppb_price",BaseDecimal.division(String.valueOf(db.get("price")), String.valueOf(bl),2));
						if(!comList.contains(cid)) {
							comList.add(cid);
							DBObject comDB = baseDao.getMessage(PubConstants.SHOP_SHOPMB, cid);
							shopmblist.add(comDB);
						}
//						comDB.put("carInfo", dbObject);
//						zlist.add(comDB);
					} else {
						// 移除已经失效的订单
						baseDao.delete(PubConstants.SUC_SHOPPINGCART, Long.parseLong(dbObject.get("_id").toString()));

					}
				}
				for (DBObject shopmb : shopmblist) {
					List<DBObject> cartList = new ArrayList<DBObject>();
					for (DBObject product : productlist) {
						if(product.get("comid").toString().equals(shopmb.get("_id").toString())) {
							for (DBObject cart : list) {
								if(product.get("_id").equals(cart.get("pid"))) {
									cart.put("product", product);
									cart.put("ppb_price",BaseDecimal.division(String.valueOf(product.get("price")), String.valueOf(bl),2));
									cartList.add(cart);
								}
							}
							shopmb.put("cartInfo", cartList);
						}
					}
				}
				sub_map.put("state", 0);
				sub_map.put("list", shopmblist);
			} else {
				sub_map.put("state", 1);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}
	public List<Long> pastLeep1(List<Long> list){
        System.out.println("list = [" + list.toString() + "]");
        List<Long> listNew=new ArrayList<Long>();
        HashSet set=new HashSet();
        for (Long str:list) {
            if(set.add(str)){
                listNew.add(str);
            }
        }
        return listNew;
    }

	/**
	 * 商品详情
	 * 
	 * @return
	 */
	public String shopproduct() {
		getLscode();
		String pid = Struts2Utils.getParameter("pid");
		if(StringUtils.isEmpty(custid)) {
			custid=SysConfig.getProperty("custid");
		} 
		WxToken token = GetAllFunc.wxtoken.get(custid);
		if (token.getSqlx() > 0) {
			token = GetAllFunc.wxtoken.get(wwzService.getparentcustid(custid));
		}
		Struts2Utils.getRequest().setAttribute("token", WeiXinUtil.getwxSignature(token, Struts2Utils.getRequest()));
		System.out.println("-----------------token");
		token = WeiXinUtil.getSignature(token, Struts2Utils.getRequest());
		String url = SysConfig.getProperty("ip") + "/shop/shop!shopproduct.action?custid=" + custid + "&pid=" + pid
				+ "&agid=" + agid;
		if (StringUtils.isEmpty(fromUserid)) {
			String inspection = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + token.getAppid()
					+ "&redirect_uri=" + URLEncoder.encode(url)
					+ "&response_type=code&scope=snsapi_base&state=c1c2j3h4#wechat_redirect";
			Struts2Utils.getRequest().setAttribute("inspection", inspection);
			return "refresh";
		} else if (fromUserid.equals("register")) {
			String inspection = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + token.getAppid()
					+ "&redirect_uri=" + URLEncoder.encode(url)
					+ "&response_type=code&scope=snsapi_userinfo&state=register#wechat_redirect";
			Struts2Utils.getRequest().setAttribute("inspection", inspection);
			return "refresh";
		}
		wwzService.flow(custid, "shop");
		wwzService.flow(custid, "shop-pro-" + pid);
		DBObject db = baseDao.getMessage(PubConstants.DATA_PRODUCT, Long.parseLong(pid));
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("fromUserid", fromUserid);
		whereMap.put("custid", custid);
		Long count = baseDao.getCount(PubConstants.SUC_SHOPPINGCART, whereMap);
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("entity", db);
	//	Struts2Utils.getRequest().setAttribute("ppb_price",  BaseDecimal.division(db.get("price").toString(),wwzService.getPPBSprice()+"",2));
		if (Integer.parseInt(db.get("bq").toString()) == 8) {
			double bl = Double.parseDouble(db.get("price").toString())
					/ Double.parseDouble(db.get("oldprice").toString());
			Struts2Utils.getRequest().setAttribute("bl", new java.text.DecimalFormat("#").format(bl * 100));
		} else if (Integer.parseInt(db.get("bq").toString()) == 9) {
			whereMap.clear();
			whereMap.put("proid", Long.parseLong(pid));
			Long bcount = baseDao.getCount(PubConstants.SHOP_BULKYD, whereMap);
			double bl = Double.parseDouble(bcount + "") / Double.parseDouble(db.get("pcount").toString());
			Struts2Utils.getRequest().setAttribute("bl", new java.text.DecimalFormat("#").format(bl * 100));
			Struts2Utils.getRequest().setAttribute("needcount", Long.parseLong(db.get("pcount").toString()) - bcount);
			whereMap.put("fromUserid", fromUserid);
			bcount = baseDao.getCount(PubConstants.SHOP_BULKYD, whereMap);
			if (bcount == 1) {
				Struts2Utils.getRequest().setAttribute("ispay", true);
			}
		}
		if (StringUtils.isEmpty(agid)) {
			agid = wwzService.getAgid(db.get("comid").toString(), wwzService.getVipNo(fromUserid));
			if (StringUtils.isNotEmpty(agid)) {
				Struts2Utils.getRequest().setAttribute("agid", agid);
			}
		}
		Struts2Utils.getRequest().setAttribute("shopcount", count);
		Struts2Utils.getRequest().setAttribute("slide", wwzService.getslide(custid, "shoppro-" + pid));
		// 加载地址信息
		whereMap.clear();
		whereMap.put("fromUserid", fromUserid);
		whereMap.put("lx", 1);
		DBObject address = baseDao.getMessage(PubConstants.SHOP_USERADDRESS, whereMap);
		Struts2Utils.getRequest().setAttribute("address", address);

		// 加载商品规格
		whereMap.clear();
		whereMap.put("parentid", Long.parseLong(pid));
		HashMap<String, Object> sortMap = new HashMap<String, Object>();
		sortMap.put("sort", -1);
		List<DBObject> spelist = baseDao.getList(PubConstants.SHOP_SPECIFICATION, whereMap, sortMap);
		if (spelist.size() > 0) {
			Struts2Utils.getRequest().setAttribute("spelist", spelist);
		}

		//Struts2Utils.getRequest().setAttribute("jf", wwzService.getJf(SysConfig.getProperty("custid"), fromUserid));
		DBObject share = new BasicDBObject();
		share.put("fxtitle", db.get("ptitle"));
		share.put("fximg", db.get("logo"));
		share.put("fxsummary", db.get("summary"));
		share.put("fxurl", url);
		Struts2Utils.getRequest().setAttribute("share", share);

		// 加载限购信息
		if (db.get("gmcs") != null && Integer.parseInt(db.get("gmcs").toString()) > 0) {
			whereMap.clear();
			whereMap.put("pid", Long.parseLong(pid));
			whereMap.put("fromUserid", fromUserid);
			int ll = 0;
			// 预订库检测
			List<DBObject> list = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap, null);
			for (DBObject dbObject : list) {
				DBObject order = baseDao.getMessage(PubConstants.WX_ORDERFORM, dbObject.get("orderid").toString());
				if (order != null && Integer.parseInt(order.get("state").toString()) != 1) {
					ll += Integer.parseInt(order.get("count").toString());
				}
			}
			// 购物车检测
			whereMap.clear();
			whereMap.put("pid", Long.parseLong(pid));
			whereMap.put("fromUserid", fromUserid);

			List<DBObject> listgwc = baseDao.getList(PubConstants.SUC_SHOPPINGCART, whereMap, null);
			for (DBObject dbObject : listgwc) {
				ll += Integer.parseInt(dbObject.get("count").toString());
			}
			if (ll >= Integer.parseInt(db.get("gmcs").toString())) {
				Struts2Utils.getRequest().setAttribute("gmcs", 0);
			} else {
				Struts2Utils.getRequest().setAttribute("gmcs", Integer.parseInt(db.get("gmcs").toString()) - ll);
			}
		}

		// 检测代理
		if (wwzService.checkAgent(agid, custid, fromUserid)) {
			Struts2Utils.getRequest().setAttribute("isAgent", "ok");
		}
		// 检测全局代理
		if (wwzService.checkAgent(custid, fromUserid)) {
			Struts2Utils.getRequest().setAttribute("isAgents", "ok");
		}
		// 检测当前店铺
		if (StringUtils.isNotEmpty(wwzService.getAgid(db.get("comid").toString(), wwzService.getVipNo(fromUserid)))) {
			Struts2Utils.getRequest().setAttribute("isAgentcom", "ok");
		}

		System.out.println("------productdetail"+db.get("bq"));
		return "productdetail" + db.get("bq");

	}

	/**
	 * 购物车详情
	 * 
	 * @return
	 */
	public String shoppingcar() {
		getLscode();
		Struts2Utils.getRequest().setAttribute("user", wwzService.getWxUser(fromUserid));
		String pid = Struts2Utils.getParameter("pid");
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("pid", pid);
		// 加载地址信息
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("fromUserid", fromUserid);
		whereMap.put("lx", 1);
		DBObject address = baseDao.getMessage(PubConstants.SHOP_USERADDRESS, whereMap);
		Struts2Utils.getRequest().setAttribute("address", address);
		whereMap.clear();
		whereMap.put("custid", custid);
		whereMap.put("lx", 1);
		DBObject entity = baseDao.getMessage(PubConstants.SHOP_SHOPMB, whereMap);
		Struts2Utils.getRequest().setAttribute("entity", entity);
		DBObject user = baseDao.getMessage(PubConstants.USER_INFO, fromUserid);
		if(user!=null){
			if(user.get("isfull") != null){
				Struts2Utils.getRequest().setAttribute("isfull", user.get("isfull").toString());
			}
		}
		return "shoppingcar";

	}

	/**
	 * 订单确认
	 * 
	 * @return
	 */
	public String orderconfirmation() {
		getLscode();
		String pid = Struts2Utils.getParameter("pid");
		DBObject db = baseDao.getMessage(PubConstants.DATA_PRODUCT, Long.parseLong(pid));
		// 加载地址信息
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("fromUserid", fromUserid);
		whereMap.put("lx", 1);
		DBObject address = baseDao.getMessage(PubConstants.SHOP_USERADDRESS, whereMap);
		Struts2Utils.getRequest().setAttribute("address", address);
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("entity", db);
		Struts2Utils.getRequest().setAttribute("user", wwzService.getWxUser(fromUserid));
		Struts2Utils.getRequest().setAttribute("spec", Struts2Utils.getParameter("spec"));

		Struts2Utils.getRequest().setAttribute("count", Struts2Utils.getParameter("count"));
		Struts2Utils.getRequest().setAttribute("byprice", baseDao
				.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(db.get("comid").toString())).get("byprice"));
		Struts2Utils.getRequest().setAttribute("price", Struts2Utils.getParameter("price"));
		DBObject user = baseDao.getMessage(PubConstants.USER_INFO, fromUserid);
		if(user!=null){
			if(user.get("isfull") != null){
				Struts2Utils.getRequest().setAttribute("isfull", user.get("isfull").toString());
			}
		}
		return "orderconfirmation";

	}

	/**
	 * ajax删除地址
	 */
	public void ajaxdeladdress() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			String id = Struts2Utils.getParameter("id");
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("fromUserid", fromUserid);
			whereMap.put("_id", Long.parseLong(id));
			baseDao.delete(PubConstants.SHOP_USERADDRESS, whereMap);
			sub_map.put("state", 0);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 生成订单
	 */
	public void shoppay() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			getLscode();
			String pid = Struts2Utils.getParameter("pid");
			String jfdh = Struts2Utils.getParameter("jfdh");
			String count = Struts2Utils.getParameter("count");
			String money = Struts2Utils.getParameter("money");
			String address = Struts2Utils.getParameter("address");
			String tel = Struts2Utils.getParameter("tel");
			if (pid.indexOf(",") < 0) {

				OrderForm obj = new OrderForm();
				obj.set_id(mongoSequence.currval(PubConstants.WX_ORDERFORM));
				obj.setAddress(address);
				obj.setComid(Long.parseLong(pid));
				obj.setFromUserid(fromUserid);
				obj.setCreatedate(new Date());
				obj.setCustid(custid);
				obj.setJfdh(Double.parseDouble(jfdh));
				obj.setMoney(Double.parseDouble(money));
				obj.setState(0);
				obj.setTel(tel);
				obj.setCount(Integer.parseInt(count));
				baseDao.insert(PubConstants.WX_ORDERFORM, obj);
			} else {

				String[] pids = pid.split(",");
				String[] counts = count.split(",");
				for (int i = 0; i < pids.length; i++) {
					if (StringUtils.isNotEmpty(pids[i])) {
						OrderForm obj = new OrderForm();
						obj.set_id(mongoSequence.currval(PubConstants.WX_ORDERFORM));
						obj.setAddress(address);
						obj.setComid(Long.parseLong(pids[i]));
						obj.setFromUserid(fromUserid);
						obj.setCreatedate(new Date());
						obj.setCustid(custid);
						obj.setState(0);
						obj.setTel(tel);
						obj.setCount(Integer.parseInt(counts[i]));
						baseDao.insert(PubConstants.WX_ORDERFORM, obj);
					}

				}

			}

			sub_map.put("state", 0);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 订单列表
	 */
	public String orderfrom() {
		Struts2Utils.getRequest().setAttribute("custid", Struts2Utils.getParameter("custid"));
		Struts2Utils.getRequest().setAttribute("lscode", Struts2Utils.getParameter("lscode"));
		return "orderfrom";

	}

	public void ajaxdelorder() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("fromUserid", fromUserid);
			whereMap.put("_id", id);
			DBObject db = baseDao.getMessage(PubConstants.WX_ORDERFORM, whereMap);
			if (db != null) {
				OrderForm orderForm = (OrderForm) UniObject.DBObjectToObject(db, OrderForm.class);
				orderForm.setIsxs(1);
				baseDao.insert(PubConstants.WX_ORDERFORM, orderForm);
				sub_map.put("state", 0);
			}

		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * ajax获取订单列表
	 */
	public void ajaxorder() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			getLscode();
			Struts2Utils.getRequest().setAttribute("custid", Struts2Utils.getParameter("custid"));
			if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			sortMap.put("insDate", -1);
			whereMap.put("custid", custid);
			whereMap.put("fromUserid", fromUserid);

			List<DBObject> list = baseDao.getList(PubConstants.WX_ORDERFORM, whereMap, fypage, 10, sortMap);
			List<DBObject> lsodb = new ArrayList<DBObject>();
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					if (dbObject.get("recordid") != null) {
						DBObject db = baseDao.getMessage(PubConstants.DATA_PRODUCT,
								Long.parseLong(dbObject.get("recordid").toString()));
						if (db != null) {
							dbObject.put("pro", db);
							lsodb.add(dbObject);
						} else {
							// 清空

							baseDao.delete(PubConstants.WX_ORDERFORM, dbObject.get("_id").toString());

						}

					} else if (dbObject.get("ids") != null) {
						String[] sd = dbObject.get("ids").toString().split(",");
						List<DBObject> lists = new ArrayList<DBObject>();
						for (int i = 0; i < sd.length; i++) {
							if (StringUtils.isNotEmpty(sd[i])) {
								DBObject db = baseDao.getMessage(PubConstants.DATA_PRODUCT, Long.parseLong(sd[i]));
								if (db != null) {
									lists.add(db);
								}

							}

						}
						if (lists.size() > 0) {
							dbObject.put("list", lists);
							lsodb.add(dbObject);
						} else {
							// 清空
							baseDao.delete(PubConstants.WX_ORDERFORM, dbObject.get("_id").toString());

						}

					} else {
						// 清空
						baseDao.delete(PubConstants.WX_ORDERFORM, dbObject.get("_id").toString());
					}

				}
				sub_map.put("state", 0);
				sub_map.put("list", lsodb);
			} else {
				sub_map.put("state", 1);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * ajax获取订单列表
	 */
	public void ajaxorders() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			getLscode();
			Struts2Utils.getRequest().setAttribute("custid", Struts2Utils.getParameter("custid"));
			if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			String state=Struts2Utils.getParameter("state").toString();
			if(state!=null&&state!="") {
				whereMap.put("state", Integer.parseInt(state));
			}
			sortMap.put("insDate", -1);
			//whereMap.put("custid", custid);
			whereMap.put("isxs", new BasicDBObject("$ne", 1));
			whereMap.put("fromUserid", fromUserid);
			
			List<DBObject> list = baseDao.getList(PubConstants.WX_ORDERFORM, whereMap, fypage, 10, sortMap);
			List<DBObject> lsodb = new ArrayList<DBObject>();
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					if (dbObject.get("kdcom") != null) {
						dbObject.put("kdcom", wwzService.getKdName(dbObject.get("kdcom").toString()));
					}
					HashMap<String, Object> wheresMap = new HashMap<String, Object>();
					HashMap<String, Object> sortsMap = new HashMap<String, Object>();
					wheresMap.put("orderid", dbObject.get("_id").toString());
					List<DBObject> lists = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, wheresMap, sortsMap);

					if (lists.size() > 0) {
						for (DBObject obj : lists) {
							System.out.println(obj.get("_id").toString());
							whereMap.clear();
							whereMap.put("oid", obj.get("_id").toString());
							DBObject com = baseDao.getMessage(PubConstants.SHOP_SHOPCOMMENTS, whereMap);
							System.out.println(com);
							if (com != null) {
								obj.put("states", 1);// 代表已评价
							} else {
								obj.put("states", 0);// 代表未评价
							}
						}
						dbObject.put("list", lists);
						lsodb.add(dbObject);
					} else {
						// 清空
						baseDao.delete(PubConstants.WX_ORDERFORM, dbObject.get("_id").toString());
					}

				}
				sub_map.put("state", 0);
				sub_map.put("list", lsodb);
			} else {
				sub_map.put("state", 1);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString();
		System.out.println(json);
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}
	
	/**
	 * ajax获取订单列表
	 */
	public void ajaxorders1() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			getLscode();
			Struts2Utils.getRequest().setAttribute("custid", Struts2Utils.getParameter("custid"));
			if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			String state=Struts2Utils.getParameter("state");
			if(StringUtils.isNotEmpty(state)) {
				if(state.equals("10")) {
					whereMap.put("state", 4);
				}else {
					whereMap.put("state", Integer.parseInt(state));
				}
				
			}
			sortMap.put("insDate", -1);
			//whereMap.put("custid", custid);
			whereMap.put("isxs", new BasicDBObject("$ne", 1));
			whereMap.put("fromUserid", fromUserid);
			
			List<DBObject> list = baseDao.getList(PubConstants.WX_ORDERFORM, whereMap, fypage, 10, sortMap);
			List<DBObject> lsodb = new ArrayList<DBObject>();
			
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					List<DBObject>lscomord=new ArrayList<DBObject>();
					if (dbObject.get("kdcom") != null) {
						dbObject.put("kdcom", wwzService.getKdName(dbObject.get("kdcom").toString()));
					}
					if (dbObject.get("kdprice") != null) {
						dbObject.put("kdprice",BaseDecimal.division(dbObject.get("kdprice").toString(),wwzService.getPPBSprice()+"",10));
					}
					OrderForm orderForm=(OrderForm) UniObject.DBObjectToObject(dbObject, OrderForm.class);
					List<Long> lscomis=orderForm.getComids(); 
					for (int i = 0; i < lscomis.size(); i++) { 
						HashMap<String, Object> wheresMap = new HashMap<String, Object>();
						HashMap<String, Object> sortsMap = new HashMap<String, Object>();
						DBObject  shopcom=baseDao.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(lscomis.get(i)+""));
						DBObject  shopdata=new BasicDBObject();
						//wheresMap.put("orderid",long1);
						wheresMap.put("orderid", dbObject.get("_id").toString());
						wheresMap.put("pro.comid",Long.parseLong(lscomis.get(i)+""));
						if(state!=null&&state!=""){
							whereMap.put("goodstate", Integer.parseInt(state));
							
							if(state.equals("10")) {
								whereMap.put("states", 0);
							}
						}
						
						List<DBObject> lists = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, wheresMap, sortsMap);
						if (lists.size() > 0) {
							for (DBObject obj : lists) { 
								if(null == obj.get("kdcom") || "".equals(obj.get("kdcom"))) {
									obj.put("kdname", "");
								}else {
									obj.put("kdname", wwzService.getKdName(obj.get("kdcom").toString()));									
								}
								whereMap.clear();
								whereMap.put("oid", obj.get("_id").toString());
								DBObject com = baseDao.getMessage(PubConstants.SHOP_SHOPCOMMENTS, whereMap);
								//System.out.println(com);
								if (com != null) {
									obj.put("states", 1);// 代表已评价
								} else {
									obj.put("states", 0);// 代表未评价
								}
							} 
							shopdata.put("list",lists);
							shopdata.put("order",lists.get(0));
							shopdata.put("shop",shopcom);
							//shopcom.put("list", list);
							
						} else {
							// 清空
							//baseDao.delete(PubConstants.WX_ORDERFORM, dbObject.get("_id").toString());
						} 
						System.out.println(lscomis.get(i));
						lscomord.add(shopdata);	 
					}
					
					dbObject.put("comlist", lscomord);
					lsodb.add(dbObject);

				}
				sub_map.put("state", 0);
				sub_map.put("list", lsodb);
			} else {
				sub_map.put("state", 1);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString(); 
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}
	

	/**
	 * 用户地址管理
	 */
	public String useraddress() {
		getLscode();
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("addressis", Struts2Utils.getParameter("addressis"));
		Struts2Utils.getRequest().setAttribute("backurl", Struts2Utils.getParameter("backurl"));
		Struts2Utils.getRequest().setAttribute("count", Struts2Utils.getParameter("count"));
		Struts2Utils.getRequest().setAttribute("price", Struts2Utils.getParameter("price"));
		Struts2Utils.getRequest().setAttribute("spec", Struts2Utils.getParameter("spec"));
		if(null != Struts2Utils.getParameter("pid") && !"".equals(Struts2Utils.getParameter("pid"))) {
			Struts2Utils.getRequest().setAttribute("pid", Struts2Utils.getParameter("pid"));
		}else{
			Struts2Utils.getRequest().setAttribute("pid", "nots");
		}
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("fromUserid", fromUserid);
		whereMap.put("lx", 1);
		DBObject address = baseDao.getMessage(PubConstants.SHOP_USERADDRESS, whereMap);
		Struts2Utils.getRequest().setAttribute("address", address);
		return "address";

	}

	/**
	 * 用户新增管理
	 */
	public String useraddresssave() {
		getLscode();
		Struts2Utils.getRequest().setAttribute("addressis", Struts2Utils.getParameter("addressis"));

		Struts2Utils.getRequest().setAttribute("backurl", Struts2Utils.getParameter("backurl"));
		Struts2Utils.getRequest().setAttribute("count", Struts2Utils.getParameter("count"));
		Struts2Utils.getRequest().setAttribute("price", Struts2Utils.getParameter("price"));
		Struts2Utils.getRequest().setAttribute("spec", Struts2Utils.getParameter("spec"));
		if(null != Struts2Utils.getParameter("pid")) {
			Struts2Utils.getRequest().setAttribute("pid", Struts2Utils.getParameter("pid"));
		}else {
			Struts2Utils.getRequest().setAttribute("pid", "nots");
		}
		Struts2Utils.getRequest().setAttribute("custid", custid);
		return "addresssave";

	}

	/**
	 * ajax获取用户地址
	 */
	public void ajaxuseraddress() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			getLscode();
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			sortMap.put("createdate", -1);
			whereMap.put("fromUserid", fromUserid);
			System.out.println(Struts2Utils.getParameter("pid"));
			if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			List<DBObject> list = baseDao.getList(PubConstants.SHOP_USERADDRESS, whereMap, fypage, 10, sortMap);
			if (list.size() > 0) {
				sub_map.put("state", 0);
				sub_map.put("list", list);
			} else {
				sub_map.put("state", 1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 设置默认地址
	 */
	public void ajaxsetaddress() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			getLscode();

			String id = Struts2Utils.getParameter("id");
			// 先验证
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("lx", 1);
			whereMap.put("fromUserid", fromUserid);
			List<DBObject> list = baseDao.getList(PubConstants.SHOP_USERADDRESS, whereMap, null);
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					Useraddress address = (Useraddress) UniObject.DBObjectToObject(dbObject, Useraddress.class);
					address.setLx(0);
					baseDao.insert(PubConstants.SHOP_USERADDRESS, address);
				}
			}
			DBObject db = baseDao.getMessage(PubConstants.SHOP_USERADDRESS, Long.parseLong(id));
			if (db != null) {
				Useraddress address = (Useraddress) UniObject.DBObjectToObject(db, Useraddress.class);
				address.setLx(1);
				baseDao.insert(PubConstants.SHOP_USERADDRESS, address);
				sub_map.put("state", 0);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * ajax新增用户地址
	 */
	public void ajaxuseraddresssave() {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		try {
			getLscode();
			String address = Struts2Utils.getParameter("address");
			String province = Struts2Utils.getParameter("province");
			String city = Struts2Utils.getParameter("city");
			String county = Struts2Utils.getParameter("county");
			String name = Struts2Utils.getParameter("name");
			String tel = Struts2Utils.getParameter("tel");

			Useraddress obj = new Useraddress();
			Long id = mongoSequence.currval(PubConstants.SHOP_USERADDRESS);
			obj.set_id(id);
			obj.setAddress(address);
			obj.setCity(city);
			obj.setCounty(county);
			obj.setCreatedate(new Date());
			obj.setFromUserid(fromUserid);
			obj.setName(name);
			obj.setTel(tel);
			obj.setProvince(province);
			baseDao.insert(PubConstants.SHOP_USERADDRESS, obj);
			sub_map.put("state", 0);
			sub_map.put("value", id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sub_map.put("state", 1);
			e.printStackTrace();
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * ajax删除地址
	 */
	public String useraddressdel() {
		getLscode();
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		whereMap.put("fromUserid", fromUserid);
		whereMap.put("_id", Long.parseLong(id));
		baseDao.delete(PubConstants.SHOP_USERADDRESS, whereMap);
		return "address";

	}

	/**
	 * ajax获取推广
	 */
	public void ajaxgettg() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String comid = Struts2Utils.getParameter("comid");
		if (StringUtils.isNotEmpty(comid)) {
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			whereMap.put("comid", Long.parseLong(comid));
			whereMap.put("custid", custid);
			sortMap.put("createdate", -1);
			if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			List<DBObject> list = baseDao.getList(PubConstants.SHOP_SHOPTG, whereMap, fypage, 9, sortMap);
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					dbObject.put("pro", baseDao.getMessage(PubConstants.DATA_PRODUCT,
							Long.parseLong(dbObject.get("pid").toString())));
				}

				sub_map.put("state", 0);
				sub_map.put("list", list);
			}
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 砍价详情
	 */
	public String bargaindetail() {
		getLscode();
		String id = Struts2Utils.getParameter("id");
		WxToken token = GetAllFunc.wxtoken.get(custid);
		if (token.getSqlx() > 0) {
			token = GetAllFunc.wxtoken.get(wwzService.getparentcustid(custid));
		}
		Struts2Utils.getRequest().setAttribute("token", WeiXinUtil.getSignature(token, Struts2Utils.getRequest()));
		token = WeiXinUtil.getSignature(token, Struts2Utils.getRequest());
		String url = SysConfig.getProperty("ip") + "/shop/shop!bargaindetail.action?custid=" + custid + "&id=" + id;
		if (StringUtils.isEmpty(fromUserid)) {
			String inspection = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + token.getAppid()
					+ "&redirect_uri=" + URLEncoder.encode(url)
					+ "&response_type=code&scope=snsapi_base&state=c1c2j3h4#wechat_redirect";
			Struts2Utils.getRequest().setAttribute("inspection", inspection);
			return "refresh";
		} else if (fromUserid.equals("register")) {
			String inspection = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + token.getAppid()
					+ "&redirect_uri=" + URLEncoder.encode(url)
					+ "&response_type=code&scope=snsapi_userinfo&state=register#wechat_redirect";
			Struts2Utils.getRequest().setAttribute("inspection", inspection);
			return "refresh";
		}
		DBObject db = baseDao.getMessage(PubConstants.SHOP_BARGAININGYD, id);
		Bargainingyd yd = (Bargainingyd) UniObject.DBObjectToObject(db, Bargainingyd.class);
		ProductInfo prd = yd.getPro();
		db.put("nickname", wwzService.getWxUsertype(db.get("fromUserid").toString(), "nickname"));
		db.put("headimgurl", wwzService.getWxUsertype(db.get("fromUserid").toString(), "headimgurl"));
		Struts2Utils.getRequest().setAttribute("entity", db);
		// 加载库存
		Struts2Utils.getRequest().setAttribute("kcount",
				baseDao.getMessage(PubConstants.DATA_PRODUCT, yd.getPid()).get("num"));
		double bl = prd.getPrice() / prd.getOldprice();
		Struts2Utils.getRequest().setAttribute("bl", new java.text.DecimalFormat("#").format(bl * 100));
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("slide", wwzService.getslide(custid, "shoppro-" + prd.get_id()));
		if (fromUserid.equals(yd.getFromUserid())) {
			Struts2Utils.getRequest().setAttribute("isadmin", true);
			// 加载地址信息
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("fromUserid", fromUserid);
			whereMap.put("lx", 1);
			DBObject address = baseDao.getMessage(PubConstants.SHOP_USERADDRESS, whereMap);
			Struts2Utils.getRequest().setAttribute("address", address);
			// 加载购买状态
			whereMap.clear();
			whereMap.put("formUserid", fromUserid);
			whereMap.put("kjid", id);
			whereMap.put("state", 2);
			whereMap.put("custid", custid);
			Long count = baseDao.getCount(PubConstants.WX_ORDERFORM, whereMap);
			if (count == 0) {
				Struts2Utils.getRequest().setAttribute("ispay", true);
			}
		}

		DBObject share = new BasicDBObject();
		share.put("fxtitle", "砍价(" + prd.getPtitle() + ")");
		share.put("fximg", wwzService.getWxUsertype(db.get("fromUserid").toString(), "headimgurl"));
		share.put("fxsummary", prd.getSummary());
		share.put("fxurl", url);
		Struts2Utils.getRequest().setAttribute("share", share);
		return "bargaindetail";
	}

	/**
	 * 砍价
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void bargain() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String ydid = Struts2Utils.getParameter("ydid");
		if (StringUtils.isNotEmpty(ydid)) {
			DBObject db = baseDao.getMessage(PubConstants.SHOP_BARGAININGYD, ydid);
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("fromUserid", fromUserid);
			whereMap.put("ydid", ydid);
			Long count = baseDao.getCount(PubConstants.SHOP_BARGAININGSTATI, whereMap);
			if (db != null & count == 0) {
				Bargainingyd yd = (Bargainingyd) UniObject.DBObjectToObject(db, Bargainingyd.class);
				ProductInfo pro = yd.getPro();

				DBObject bar = baseDao.getMessage(PubConstants.DATA_PRODUCT, Long.parseLong(pro.get_id().toString()));
				if (Integer.parseInt(bar.get("num").toString()) > 0) {
					float iprice = 0;
					if (pro.getLowprice() == 0) {
						float price = TenpayUtil.buildRandom(1);
						if (pro.getOldprice() / pro.getPcount() < 10 && pro.getOldprice() / pro.getPcount() > 1) {
							price = TenpayUtil.buildRandom(1) * 0.1f;
						} else if (pro.getOldprice() / pro.getPcount() < 1
								&& pro.getOldprice() / pro.getPcount() > 0.1) {
							price = TenpayUtil.buildRandom(1) * 0.01f;
						}
						int fprice = TenpayUtil.buildRandom(1);
						if (fprice > 5) {
							iprice = (float) (pro.getOldprice() / pro.getPcount() - price);
						} else {
							iprice = (float) (pro.getOldprice() / pro.getPcount() + price);
						}

					} else {
						float price = TenpayUtil.buildRandom(1);
						if (pro.getOldprice() / pro.getPcount() < 10 && pro.getOldprice() / pro.getPcount() > 1) {
							price = TenpayUtil.buildRandom(1) * 0.1f;
						} else if (pro.getOldprice() / pro.getPcount() < 1
								&& pro.getOldprice() / pro.getPcount() > 0.1) {
							price = TenpayUtil.buildRandom(1) * 0.01f;
						}
						int fprice = TenpayUtil.buildRandom(1);
						if (fprice > 5) {
							iprice = (float) ((pro.getOldprice() - pro.getLowprice()) / pro.getPcount() - price);
						} else {
							iprice = (float) ((pro.getOldprice() - pro.getLowprice()) / pro.getPcount() + price);
						}
					}
					if (iprice > 0 && iprice <= pro.getPrice() - pro.getLowprice()
							&& pro.getPrice() - pro.getLowprice() > 0) {
						pro.setPrice(pro.getPrice() - iprice);
						yd.setPro(pro);
						baseDao.insert(PubConstants.SHOP_BARGAININGYD, yd);
						// 记录用户
						Bargainingstati obj = new Bargainingstati();
						obj.set_id(mongoSequence.currval(PubConstants.SHOP_BARGAININGSTATI));
						obj.setFromUserid(fromUserid);
						obj.setYdid(ydid);
						obj.setKjprice(iprice);
						obj.setCreatedate(new Date());
						baseDao.insert(PubConstants.SHOP_BARGAININGSTATI, obj);
						if (pro.getPrice() == pro.getLowprice()) {
							ProductInfo p = (ProductInfo) UniObject.DBObjectToObject(bar, ProductInfo.class);
							if (p.getNum() - 1 >= 0) {
								p.setNum(p.getNum() - 1);
							}
							baseDao.insert(PubConstants.DATA_PRODUCT, p);
						}

						sub_map.put("value", obj.getKjprice());
						sub_map.put("state", 0);
					} else if (iprice > pro.getPrice() - pro.getLowprice() && pro.getPrice() - pro.getLowprice() > 0) {
						pro.setPrice(Double.parseDouble(pro.getLowprice() + ""));
						yd.setPro(pro);
						baseDao.insert(PubConstants.SHOP_BARGAININGYD, yd);
						// 记录用户
						Bargainingstati obj = new Bargainingstati();
						obj.set_id(mongoSequence.currval(PubConstants.SHOP_BARGAININGSTATI));
						obj.setFromUserid(fromUserid);
						obj.setYdid(ydid);
						obj.setKjprice(iprice);
						obj.setCreatedate(new Date());
						baseDao.insert(PubConstants.SHOP_BARGAININGSTATI, obj);
						// 更新库存
						if (pro.getPrice() == pro.getLowprice()) {
							ProductInfo p = (ProductInfo) UniObject.DBObjectToObject(bar, ProductInfo.class);
							if (p.getNum() - 1 >= 0) {
								p.setNum(p.getNum() - 1);
								p.setGmnum(p.getGmnum() + 1);
							}
							baseDao.insert(PubConstants.DATA_PRODUCT, p);
						}
						sub_map.put("value", pro.getPrice());
						sub_map.put("state", 0);
					} else {
						// 已经到底
						sub_map.put("state", 1);
					}

				} else {
					// 库存已完
					sub_map.put("state", 2);
				}

			} else {
				// 已经砍过
				sub_map.put("state", 3);
			}
		}

		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 砍价预定
	 */
	public void bargainyd() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			// 检测是否已经预定（单款产品只可预定一次）
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			sortMap.put("createdate", -1);
			whereMap.put("pid", Long.parseLong(id));
			whereMap.put("custid", custid);
			whereMap.put("fromUserid", fromUserid);
			List<DBObject> list = baseDao.getList(PubConstants.SHOP_BARGAININGYD, whereMap, sortMap);
			if (list.size() > 0) {
				sub_map.put("state", 2);
				sub_map.put("value", list.get(0).get("_id"));
				String json = JSONArray.fromObject(sub_map).toString();
				Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
				return;
			}
			DBObject bar = baseDao.getMessage(PubConstants.DATA_PRODUCT, Long.parseLong(id));
			if (bar != null && Integer.parseInt(bar.get("num").toString()) > 0) {
				ProductInfo pro = (ProductInfo) UniObject.DBObjectToObject(bar, ProductInfo.class);
				// pro.set_id(Long.parseLong(id));
				// pro.setNum(pro.getNum()-1);
				// baseDao.insert(PubConstants.DATA_PRODUCT, pro);
				Bargainingyd db = new Bargainingyd();
				String code = DateFormat.getDate() + TenpayUtil.buildRandom(6)
						+ mongoSequence.currval(PubConstants.SHOP_BARGAININGYD);
				db.set_id(code);
				db.setPid(Long.parseLong(id));
				db.setCreatedate(new Date());
				db.setCustid(custid);
				db.setFromUserid(fromUserid);
				db.setPro(pro);
				baseDao.insert(PubConstants.SHOP_BARGAININGYD, db);

				sub_map.put("state", 0);
				sub_map.put("value", code);
			} else {
				// 库存已完
				sub_map.put("state", 1);
			}

		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 砍价记录
	 */
	public void bargaintj() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String ydid = Struts2Utils.getParameter("ydid");
		if (StringUtils.isNotEmpty(ydid)) {
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			whereMap.put("ydid", ydid);
			sortMap.put("createdate", -1);
			if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			List<DBObject> list = baseDao.getList(PubConstants.SHOP_BARGAININGSTATI, whereMap, fypage, 10, sortMap);
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					dbObject.put("nickname",
							wwzService.getWxUsertype(dbObject.get("fromUserid").toString(), "nickname"));
					dbObject.put("headimgurl",
							wwzService.getWxUsertype(dbObject.get("fromUserid").toString(), "headimgurl"));

				}
				sub_map.put("state", 0);
				sub_map.put("list", list);
			}

		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 砍价购买记录
	 */
	public void bargaingm() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		String kjid = Struts2Utils.getParameter("kjid");
		if (StringUtils.isNotEmpty(id)) {
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			whereMap.put("custid", custid);
			if (StringUtils.isNotEmpty(kjid)) {
				whereMap.put("kjid", kjid);
			}
			whereMap.put("recordid", Long.parseLong(id));
			whereMap.put("state", 2);
			sortMap.put("insDate", -1);
			if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			List<DBObject> list = baseDao.getList(PubConstants.WX_ORDERFORM, whereMap, fypage, 10, sortMap);
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					dbObject.put("nickname",
							wwzService.getWxUsertype(dbObject.get("fromUserid").toString(), "nickname"));
					dbObject.put("headimgurl",
							wwzService.getWxUsertype(dbObject.get("fromUserid").toString(), "headimgurl"));

				}
				sub_map.put("state", 0);
				sub_map.put("list", list);
			}
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 团购预定
	 */
	public void ajaxbulkyd() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			DBObject db = baseDao.getMessage(PubConstants.DATA_PRODUCT, Long.parseLong(id));
			if (db != null) {
				HashMap<String, Object> whereMap = new HashMap<String, Object>();
				whereMap.put("proid", Long.parseLong(id));
				whereMap.put("fromUserid", fromUserid);
				Long count = baseDao.getCount(PubConstants.SHOP_BULKYD, whereMap);
				if (count >= Long.parseLong(db.get("pcount").toString())) {
					sub_map.put("state", 2);
					String json = JSONArray.fromObject(sub_map).toString();
					Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
					return;
				}
				if (count == 0) {
					BulkYd yd = new BulkYd();
					yd.set_id(mongoSequence.currval(PubConstants.SHOP_BULKYD));
					yd.setCreatedate(new Date());
					yd.setCustid(custid);
					yd.setFromUserid(fromUserid);
					yd.setProid(Long.parseLong(id));
					baseDao.insert(PubConstants.SHOP_BULKYD, yd);
					Long needcount = Long.parseLong(db.get("pcount").toString()) - count;
					double bl = Double.parseDouble(count + "") / Double.parseDouble(db.get("pcount").toString());
					sub_map.put("needcount", needcount);
					sub_map.put("bl", new java.text.DecimalFormat("#").format(bl * 100));
					sub_map.put("state", 0);
				} else {
					sub_map.put("state", 1);
				}

			}
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 团购预定列表
	 */
	public void ajaxbulkydlist() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			sortMap.put("createdate", -1);
			whereMap.put("proid", Long.parseLong(id));
			if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			List<DBObject> list = baseDao.getList(PubConstants.SHOP_BULKYD, whereMap, fypage, 10, sortMap);
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					if (dbObject.get("fromUserid") != null) {
						dbObject.put("nickname",
								wwzService.getWxUsertype(dbObject.get("fromUserid").toString(), "nickname"));
						dbObject.put("headimgurl",
								wwzService.getWxUsertype(dbObject.get("fromUserid").toString(), "headimgurl"));
					}
					if (dbObject.get("insDate") != null) {
						dbObject.put("insDate", RelativeDate
								.format(DateFormat.getFormat(dbObject.get("insDate").toString()), new Date()));
					}
				}
				sub_map.put("state", 0);
				sub_map.put("list", list);
			}
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 团购购买列表
	 */
	public void ajaxbulkgmlist() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			sortMap.put("createdate", -1);
			whereMap.put("recordid", Long.parseLong(id));
			whereMap.put("state", 2);
			if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
				fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
			}
			List<DBObject> list = baseDao.getList(PubConstants.WX_ORDERFORM, whereMap, fypage, 10, sortMap);
			if (list.size() > 0) {
				for (DBObject dbObject : list) {
					if (dbObject.get("fromUserid") != null) {
						dbObject.put("nickname",
								wwzService.getWxUsertype(dbObject.get("fromUserid").toString(), "nickname"));
						dbObject.put("headimgurl",
								wwzService.getWxUsertype(dbObject.get("fromUserid").toString(), "headimgurl"));
					}
					if (dbObject.get("insDate") != null) {
						dbObject.put("insDate", RelativeDate
								.format(DateFormat.getFormat(dbObject.get("insDate").toString()), new Date()));
					}
				}
				sub_map.put("state", 0);
				sub_map.put("list", list);
			}
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 微信退款
	 * 
	 * @throws Exception
	 */
	public void refund() throws Exception {
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String orderno = Struts2Utils.getParameter("orderno");
		OrderForm entity = (OrderForm) UniObject
				.DBObjectToObject(baseDao.getMessage(PubConstants.WX_ORDERFORM, orderno), OrderForm.class);
		entity.set_id(orderno);
		String total = BaseDecimal.round(BaseDecimal.multiplication(String.valueOf(entity.getTotal()), "100"), 0);
		String zfmoney = BaseDecimal.round(BaseDecimal.multiplication(String.valueOf(entity.getZfmoney()), "100"), 0);

		String tkno = "tk" + orderno;

		Comunit wxToUser = GetAllFunc.wxTouser.get(toUser);
		WxPayConfig wxconfig = new WxPayConfig();
		if (wxToUser.getQx() == 0) {
			sub_map.put("state", 1);
			String json = JSONArray.fromObject(sub_map).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		} else if (wxToUser.getQx() == 1) {
			wxconfig = GetAllFunc.wxPay.get(toUser);
		} else if (wxToUser.getQx() == 2) {// 父类结算
			ComMain commain = GetAllFunc.comToUser.get(toUser);
			if (commain == null) {
				sub_map.put("state", 1);
				String json = JSONArray.fromObject(sub_map).toString();
				Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
				return;
			}
			toUser = commain.getToUser();
			wxToUser = GetAllFunc.wxTouser.get(toUser);
			wxconfig = GetAllFunc.wxPay.get(toUser);
		}

		if (wxconfig == null || wxconfig.getAppid() == null) {
			sub_map.put("state", 1);
			String json = JSONArray.fromObject(sub_map).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}

		String nonce_str = PayCommonUtil.CreateNoncestr();
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", wxconfig.getAppid());
		parameters.put("mch_id", wxconfig.getPartner());
		parameters.put("nonce_str", nonce_str);
		parameters.put("out_trade_no", orderno);
		parameters.put("out_refund_no", tkno);

		parameters.put("total_fee", zfmoney);
		parameters.put("refund_fee", zfmoney);

		parameters.put("op_user_id", wxconfig.getAppid());

		String sign = PayCommonUtil.createSign("UTF-8", parameters, wxconfig.getPartner_key());

		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);

		Map<String, String> map = WxhbUtil.sendpost(requestXML, wxconfig.getCertLocalPath(), wxconfig.getPartner(),
				"https://api.mch.weixin.qq.com/secapi/pay/refund");
		if (map == null) {
			sub_map.put("state", 1);
		} else if (map.get("result_code").equals("SUCCESS")) {
			sub_map.put("state", 0);
			entity.setState(9);
			baseDao.insert(PubConstants.WX_ORDERFORM, entity);

			entity.set_id(tkno);
			entity.setTotal(-entity.getTotal());
			entity.setZfmoney(-entity.getZfmoney());
			entity.setJfdh(-entity.getJfdh());
			entity.setInsDate(new Date());
			baseDao.insert(PubConstants.WX_ORDERFORM, entity);
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("orderid", orderno);
			List<DBObject> buydb = baseDao.getList(PubConstants.WX_ORDERBUY, whereMap, null);

		}

		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	public void jfpay() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String remark = Struts2Utils.getParameter("remark");
		// 商品类型
		int lx = Integer.parseInt(Struts2Utils.getParameter("lx"));

		Long recordid = 0L;
		// 商品编号
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("recordid"))) {
			recordid = Long.parseLong(Struts2Utils.getParameter("recordid"));// 14
		}

		// 地址信息
		String name = Struts2Utils.getParameter("name");
		String tel = Struts2Utils.getParameter("tel");
		String address = Struts2Utils.getParameter("address");
		String no = Struts2Utils.getParameter("no");
		// 店铺编号
		Long comid = 0L;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("comid"))) {
			comid = Long.parseLong(Struts2Utils.getParameter("comid"));// 14
		}
		// 数量
		int num = Integer.parseInt(Struts2Utils.getParameter("num"));
		// 规格
		String spec = Struts2Utils.getParameter("spec");
		String kjid = Struts2Utils.getParameter("kjid");
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 返还
		String jffh = Struts2Utils.getParameter("jffh");
		// 兑换
		String jfdh = Struts2Utils.getParameter("jfdh");
		// 10位序列号,可以自行调整。
		String orderno = DateFormat.getDate() + strRandom + mongoSequence.currval("orderno");
		OrderForm entity = new OrderForm();
		entity.set_id(orderno);
		entity.setNo(no);
		entity.setLx(lx);
		entity.setFromUserid(fromUserid);
		entity.setCustid(custid);
		entity.setName(name);
		entity.setTel(tel);
		entity.setAddress(address);
		entity.setInsDate(new Date());

		entity.setComid(comid);// 14
		entity.setCount(num);// 15
		entity.setKjid(kjid);
		if (StringUtils.isNotEmpty(jfdh)) {
			entity.setJfdh(Double.parseDouble(jfdh));
		}
		entity.setRecordid(recordid);

		entity.setRemark(remark + "-" + spec);
		if (StringUtils.isNotEmpty(jffh)) {
			entity.setJffh(Double.parseDouble(jffh));
		}

		HashMap<String, Object> backMap = new HashMap<String, Object>();
		backMap.put("context", 0);
		backMap.put("summary", 0);
		DBObject pro = baseDao.getMessage(PubConstants.DATA_PRODUCT, recordid, backMap);
		if (pro != null) {
			OrderFormpro o = new OrderFormpro();
			o.set_id(mongoSequence.currval(PubConstants.SHOP_ODERFORMPRO));
			o.setCount(num);
			o.setOrderid(orderno);
			o.setPro(pro);
			o.setSpec(spec);
			o.setFromUserid(fromUserid);
			o.setPid(Long.parseLong(pro.get("_id").toString()));

			baseDao.insert(PubConstants.SHOP_ODERFORMPRO, o);
		}
		if (entity.getJfdh() > 0) {
			wwzService.deljf(entity.getJfdh() + "", fromUserid, "shop-dh", custid, null);
		}
		if (entity.getJffh() > 0) {
			wwzService.addjf(entity.getJffh() + "", fromUserid, "shop-fh", custid, null);
		}
		entity.setState(2);// 14
		baseDao.insert(PubConstants.WX_ORDERFORM, entity);
		// 更新库存
		ProductInfo pr = (ProductInfo) UniObject.DBObjectToObject(pro, ProductInfo.class);
		if (pr.getNum() - num > 0) {
			pr.setNum(pr.getNum() - num);
			pr.setGmnum(pr.getGmnum() + num);
		}
		baseDao.insert(PubConstants.DATA_PRODUCT, pr);
		sub_map.put("state", 0);
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 商城验证
	 */
	public void checkJfsc() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("custid", custid);
		whereMap.put("type", 1);
		List<DBObject> list = baseDao.getList(PubConstants.SHOP_SHOPMB, whereMap, null);
		if (list.size() > 0) {
			sub_map.put("state", 0);
			sub_map.put("value", list.get(0).get("_id"));
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 更新状态
	 */
	public void gxzt() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		DBObject db = baseDao.getMessage(PubConstants.SHOP_BARGAININGYD, Long.parseLong(id));
		if (db != null) {
			Bargainingyd bargainingyd = (Bargainingyd) UniObject.DBObjectToObject(db, Bargainingyd.class);
			bargainingyd.setState(1);
			baseDao.insert(PubConstants.SHOP_BARGAININGYD, bargainingyd);
			sub_map.put("state", 0);
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * ajax获取砍价预定列表
	 */
	public void ajaxbargainyd() {
		getLscode();
		String id = Struts2Utils.getParameter("id");
		Map<String, Object> sub_map = new HashMap<String, Object>();
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		HashMap<String, Object> sortMap = new HashMap<String, Object>();
		HashMap<String, Object> backMap = new HashMap<String, Object>();
		whereMap.put("pid", Long.parseLong(id));
		sortMap.put("createdate", -1);
		backMap.put("pro", 0);
		List<DBObject> list = baseDao.getList(PubConstants.SHOP_BARGAININGYD, whereMap, sortMap, backMap);
		if (list.size() > 0) {
			for (DBObject dbObject : list) {
				if (dbObject.get("fromUserid") != null) {
					DBObject db = wwzService.getWxUser(dbObject.get("fromUserid").toString());
					dbObject.put("headimgurl", db.get("headimgurl"));
					dbObject.put("nickname", db.get("nickname"));
				}
				if (dbObject.get("createdate") != null) {
					dbObject.put("createdate", RelativeDate
							.format(DateFormat.getFormat(dbObject.get("createdate").toString()), new Date()));
				}
			}
			sub_map.put("state", 0);
			sub_map.put("list", list);
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 店铺支付列表
	 * 
	 * @return
	 */
	public String storepayweb() {
		getLscode();
		Struts2Utils.getRequest().setAttribute("custid", custid);
		WxToken token = GetAllFunc.wxtoken.get(custid);
		if (token.getSqlx() > 0) {
			token = GetAllFunc.wxtoken.get(wwzService.getparentcustid(custid));
		}
		Struts2Utils.getRequest().setAttribute("token", WeiXinUtil.getSignature(token, Struts2Utils.getRequest()));
		token = WeiXinUtil.getSignature(token, Struts2Utils.getRequest());
		String url = SysConfig.getProperty("ip") + "/shop/shop!storepayweb.action?custid=" + custid;
		if (StringUtils.isEmpty(fromUserid)) {
			String inspection = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + token.getAppid()
					+ "&redirect_uri=" + URLEncoder.encode(url)
					+ "&response_type=code&scope=snsapi_base&state=c1c2j3h4#wechat_redirect";
			Struts2Utils.getRequest().setAttribute("inspection", inspection);
			return "refresh";
		} else if (fromUserid.equals("register")) {
			String inspection = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + token.getAppid()
					+ "&redirect_uri=" + URLEncoder.encode(url)
					+ "&response_type=code&scope=snsapi_userinfo&state=register#wechat_redirect";
			Struts2Utils.getRequest().setAttribute("inspection", inspection);
			return "refresh";
		}
		return "storepayweb";
	}

	/**
	 * ajax获取店铺列表
	 */
	public void ajaxshopmb() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		HashMap<String, Object> whereMap = new HashMap<String, Object>();
		HashMap<String, Object> sortMap = new HashMap<String, Object>();
		HashMap<String, Object> backMap = new HashMap<String, Object>();
		whereMap.put("custid", custid);
		String name = Struts2Utils.getParameter("sel");
		if (StringUtils.isNotEmpty(name)) {

			Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
			whereMap.put("name", pattern);
			Struts2Utils.getRequest().setAttribute("name", name);
		}
		// 只加载商家店铺
		whereMap.put("type", 2);
		sortMap.put("createdate", -1);
		backMap.put("summary", 0);
		backMap.put("content", 0);
		backMap.put("picurl", 0);
		List<DBObject> list = baseDao.getList(PubConstants.SHOP_SHOPMB, whereMap, sortMap, backMap);
		if (list.size() > 0) {
			sub_map.put("state", 0);
			for (DBObject dbObject : list) {
				// 加载日销售额
				double yprice = 0;
				whereMap.clear();
				whereMap.put("comid", Long.parseLong(dbObject.get("_id").toString()));
				BasicDBObject dateCondition = new BasicDBObject();
				dateCondition.append("$gte", DateUtil.getTimesmorning());
				dateCondition.append("$lt", DateUtil.getTimesnight());
				whereMap.put("createdate", dateCondition);
				List<DBObject> paylist = baseDao.getList(PubConstants.SHOP_PAYMENTORDER, whereMap, null);
				if (paylist.size() > 0) {
					for (DBObject pay : paylist) {
						yprice += Double.parseDouble(pay.get("price").toString());
					}
				}
				dbObject.put("yprice", yprice);
			}
			sub_map.put("list", list);
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 店铺支付
	 * 
	 * @return
	 */
	public String storepay() {
		getLscode();
		String id = Struts2Utils.getParameter("id");
		WxToken token = GetAllFunc.wxtoken.get(custid);
		if (token.getSqlx() > 0) {
			token = GetAllFunc.wxtoken.get(wwzService.getparentcustid(custid));
		}
		Struts2Utils.getRequest().setAttribute("token", WeiXinUtil.getSignature(token, Struts2Utils.getRequest()));
		token = WeiXinUtil.getSignature(token, Struts2Utils.getRequest());
		String url = SysConfig.getProperty("ip") + "/shop/shop!storepay.action?custid=" + custid + "&id=" + id;
		if (StringUtils.isEmpty(fromUserid)) {
			String inspection = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + token.getAppid()
					+ "&redirect_uri=" + URLEncoder.encode(url)
					+ "&response_type=code&scope=snsapi_base&state=c1c2j3h4#wechat_redirect";
			Struts2Utils.getRequest().setAttribute("inspection", inspection);
			return "refresh";
		} else if (fromUserid.equals("register")) {
			String inspection = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + token.getAppid()
					+ "&redirect_uri=" + URLEncoder.encode(url)
					+ "&response_type=code&scope=snsapi_userinfo&state=register#wechat_redirect";
			Struts2Utils.getRequest().setAttribute("inspection", inspection);
			return "refresh";
		}
		Struts2Utils.getRequest().setAttribute("entity",
				baseDao.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(id)));
		return "storepay";
	}

	/**
	 * 微信支付
	 */
	public void storewxpay() throws Exception {
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		getLscode();
		DBObject wx = wwzService.getWxUser(fromUserid);
		if (wx.get("_id").equals("notlogin")) {
			params.put("state", 3);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
		WxToken wxtoken = GetAllFunc.wxtoken.get(custid);
		WxPayConfig wxconfig = new WxPayConfig();
		if (wxtoken.getQx() == 0) {
			params.put("state", 1);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		} else if (wxtoken.getQx() == 1) {
			wxconfig = GetAllFunc.wxPay.get(custid);
		} else if (wxtoken.getQx() == 2) {// 父类结算
			wxconfig = GetAllFunc.wxPay.get(wwzService.getparentcustid(custid));
		}
		// 支付的价格
		String price = Struts2Utils.getParameter("price");
		// 店铺编号
		String comid = Struts2Utils.getParameter("comid");
		String remark = Struts2Utils.getParameter("remark");
		double jffh = 0;
		DBObject mb = baseDao.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(comid));

		if (mb.get("jfbl") != null && Integer.parseInt(mb.get("jfbl").toString()) > 0) {
			jffh = Double.parseDouble(price) * Integer.parseInt(mb.get("jfbl").toString());
		}
		// 10位序列号,可以自行调整。
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		String orderno = DateFormat.getDate() + strRandom + mongoSequence.currval(PubConstants.SHOP_PAYMENTORDER);
		/*
		 * MessageInfo me=new MessageInfo(); me.setCustid(custid);
		 * me.setFromUserid(fromUserid); me.setTitle("订单提醒");
		 * me.setSummary("您有一条来自"+wwzService.getWxUsertype(fromUserid,
		 * "nickname")+"的订单"); me.setPicurl(""); me.setUrl(""); me.setLx("shop-pay");
		 * me.setType(3); me.setComname(mb.get("name").toString()); me.setProcount("");
		 * me.setProtitle(""); me.setProstate("2"); me.setOrderid(orderno);
		 * JmsService.permessageMessage(me);
		 */
		StringBuffer attach = new StringBuffer(orderno);// 0
		attach.append("&").append(custid);//
		attach.append("&").append(fromUserid);
		attach.append("&").append(comid);
		attach.append("&").append(price);
		attach.append("&").append(jffh);

		String nonce_str = PayCommonUtil.CreateNoncestr();
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", wxconfig.getAppid());
		parameters.put("mch_id", wxconfig.getPartner());
		parameters.put("nonce_str", nonce_str);
		parameters.put("attach", attach.toString());
		parameters.put("body", remark);
		parameters.put("is_subscribe", "Y");
		parameters.put("out_trade_no", orderno);
		parameters.put("total_fee", BaseDecimal.round(BaseDecimal.multiplication(price, "100"), 0));
		parameters.put("spbill_create_ip", Struts2Utils.getRequest().getRemoteAddr());
		parameters.put("notify_url", this.getCtxurl() + "/shop/shop!storewxpayok.action");
		parameters.put("trade_type", "JSAPI");
		parameters.put("openid", wwzService.getWxUser(fromUserid).get("fromUser").toString());

		String sign = PayCommonUtil.createSign("UTF-8", parameters, wxconfig.getPartner_key());
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);

		String result = CommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);

		Map<String, String> map = XMLUtil.doXMLParse(result);

		params.put("appId", wxconfig.getAppid());
		params.put("timeStamp", Long.toString(new Date().getTime()));
		params.put("nonceStr", nonce_str);
		params.put("package", "prepay_id=" + map.get("prepay_id"));
		params.put("signType", "MD5");
		String paySign = PayCommonUtil.createSign("UTF-8", params, wxconfig.getPartner_key());
		params.put("packageValue", "prepay_id=" + map.get("prepay_id")); // 这里用packageValue是预防package是关键字在js获取值出错
		params.put("paySign", paySign);
		if (jffh > 0) {
			params.put("jffh", jffh);
		} // paySign的生成规则和Sign的生成规则一致
			// paySign的生成规则和Sign的生成规则一致
		params.put("state", 0);

		params.put("orderno", orderno);
		String json = JSONArray.fromObject(params).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 微信支付回调
	 */
	public void storewxpayok() throws Exception {
		InputStream inStream = Struts2Utils.getRequest().getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}

		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");

		Map<Object, Object> map = XMLUtil.doXMLParse(result);
		String return_code = map.get("return_code").toString();

		if (return_code.equals("SUCCESS")) {
			fromUser = map.get("openid").toString(); // 推送信息时用到
			String[] attach = map.get("attach").toString().split("&");
			String orderno = attach[0];
			String custid = attach[1];
			String fromUserid = attach[2];
			String comid = attach[3];
			String price = attach[4];
			String jffh = attach[5];
			Paymentorder paymentorder = new Paymentorder();
			paymentorder.set_id(orderno);
			paymentorder.setComid(Long.parseLong(comid));
			paymentorder.setCustid(custid);
			paymentorder.setFromUserid(fromUserid);
			paymentorder.setPrice(Double.parseDouble(price));
			paymentorder.setCreatedate(new Date());
			paymentorder.setType(1);
			baseDao.insert(PubConstants.SHOP_PAYMENTORDER, paymentorder);
			DBObject mb = baseDao.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(comid));
			if (mb != null) {
				ShopMb shopmb = (ShopMb) UniObject.DBObjectToObject(mb, ShopMb.class);
				shopmb.setSales(shopmb.getSales() + Double.parseDouble(price));
				baseDao.insert(PubConstants.SHOP_SHOPMB, shopmb);
			}
			// 返还
			if (StringUtils.isNotEmpty(jffh) && Double.parseDouble(jffh) > 0) {
				wwzService.addjf(jffh, fromUserid, "shop-fh", custid, null);
			}
			MessageInfo me = new MessageInfo();
			me.setCustid(custid);
			me.setFromUserid(fromUserid);
			me.setTitle("订单提醒");
			me.setSummary("您有一条来自" + wwzService.getWxUsertype(fromUserid, "nickname") + "的订单");
			me.setPicurl("");
			me.setUrl("");
			me.setLx("shop-pay");
			me.setType(3);
			me.setComname(mb.get("name").toString());
			me.setProcount("");
			me.setProtitle("");
			me.setProstate("2");
			me.setOrderid(orderno);
			JmsService.permessageMessage(me);
			Struts2Utils.getResponse().getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));

		} else {
			Struts2Utils.getResponse().getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));
		}

	}

	/**
	 * ajax获取支付流水
	 */
	public void ajaxpayweb() {
		getLscode();
		String id = Struts2Utils.getParameter("id");
		Map<String, Object> sub_map = new HashMap<String, Object>();
		HashMap<String, Object> whereMap = new HashMap<>();
		HashMap<String, Object> sortMap = new HashMap<>();
		whereMap.put("custid", custid);
		if (StringUtils.isNotEmpty(id)) {
			whereMap.put("comid", Long.parseLong(id));
		}
		sortMap.put("createdate", -1);
		if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
			fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		List<DBObject> list = baseDao.getList(PubConstants.SHOP_PAYMENTORDER, whereMap, fypage, 10, sortMap);

		if (list.size() > 0) {
			sub_map.put("state", 0);
			for (DBObject dbObject : list) {
				if (dbObject.get("comid") != null) {
					dbObject.put("comname", baseDao
							.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(dbObject.get("comid").toString()))
							.get("name"));
				}
				if (dbObject.get("fromUserid") != null) {
					dbObject.put("headimgurl",
							wwzService.getWxUsertype(dbObject.get("fromUserid").toString(), "headimgurl"));
					dbObject.put("nickname",
							wwzService.getWxUsertype(dbObject.get("fromUserid").toString(), "nickname"));
				}
				dbObject.put("comname",
						baseDao.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(dbObject.get("comid").toString()))
								.get("name"));
			}
			sub_map.put("list", list);
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * ajax获取支付详情
	 */
	public void ajaxpaydetail() {

		getLscode();
		String id = Struts2Utils.getParameter("id");
		Map<String, Object> sub_map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(id)) {
			DBObject db = baseDao.getMessage(PubConstants.SHOP_PAYMENTORDER, id);
			if (db != null) {
				db.put("headimgurl", wwzService.getWxUsertype(db.get("fromUserid").toString(), "headimgurl"));
				db.put("nickname", wwzService.getWxUsertype(db.get("fromUserid").toString(), "nickname"));
				db.put("comname", baseDao
						.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(db.get("comid").toString())).get("name"));
			}
			if (db != null && Integer.parseInt(db.get("type").toString()) != 1) {
				db.put("obj", baseDao.getMessage(PubConstants.WX_ORDERFORM, db.get("_id").toString()));
			}
			if (db != null) {
				sub_map.put("state", 0);
				sub_map.put("value", db);
			}

		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 申请代理
	 */
	public void applyagent() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		String name = Struts2Utils.getParameter("name");
		String tel = Struts2Utils.getParameter("tel");
		if (StringUtils.isNotEmpty(id)) {
			ShopAgent agent = new ShopAgent();
			agent.set_id(id + "-" + wwzService.getVipNo(fromUserid));
			agent.setCreatedate(new Date());
			agent.setCustid(custid);
			agent.setWid(Long.parseLong(id));
			agent.setFromUserid(fromUserid);
			agent.setName(name);
			agent.setTel(tel);
			agent.setState(1);
			agent.setNickname(wwzService.getWxUsertype(fromUserid, "nickname"));
			agent.setHeadimgurl(wwzService.getWxUsertype(fromUserid, "headimgurl"));
			agent.setNo(wwzService.getWxUsertype(fromUserid, "no"));
			baseDao.insert(PubConstants.SHOP_SHOPAGENT, agent);
			sub_map.put("state", 0);
			sub_map.put("value", id + "-" + wwzService.getVipNo(fromUserid));
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 申请代理
	 */
	public String agent() {
		getLscode();
		Struts2Utils.getRequest().setAttribute("custid", custid);
		String id = Struts2Utils.getParameter("id");
		String pid = Struts2Utils.getParameter("pid");
		Struts2Utils.getRequest().setAttribute("id", id);
		Struts2Utils.getRequest().setAttribute("pid", pid);
		return "agent";

	}

	/**
	 * 佣金统计列表
	 * 
	 * @return
	 */
	public String agentweb() {
		getLscode();
		DBObject db = wwzService.getAgentPrice(custid, fromUserid);
		Struts2Utils.getRequest().setAttribute("entity", db);
		Struts2Utils.getRequest().setAttribute("state", 1);
		return "agentweb";
	}

	/**
	 * ajax获取佣金统计详情
	 */
	public void ajaxagent() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		HashMap<String, Object> whereMap = new HashMap<>();
		HashMap<String, Object> sortMap = new HashMap<>();
		sortMap.put("createdate", -1);
		String state = Struts2Utils.getParameter("state");
		if (StringUtils.isNotEmpty(state)) {
			whereMap.put("type", Integer.parseInt(state));
		}
		whereMap.put("fromUserid", fromUserid);
		if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
			fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		List<DBObject> list = baseDao.getList(PubConstants.SHOP_AGENTDETAIL, whereMap, fypage, 20, sortMap);

		if (list.size() > 0) {
			sub_map.put("state", 0);
			// 加载订单信息
			if (state.equals("1")) {
				for (DBObject dbObject : list) {
					DBObject order = baseDao.getMessage(PubConstants.WX_ORDERFORM, dbObject.get("oid").toString());
					if (order != null) {
						if (order.get("kdcom") != null) {
							order.put("kdcom", wwzService.getKdName(dbObject.get("kdcom").toString()));
						}
						order.put("nickname", wwzService.getWxUsertype(order.get("fromUserid").toString(), "nickname"));
						order.put("headimgurl",
								wwzService.getWxUsertype(order.get("fromUserid").toString(), "headimgurl"));
					}
					dbObject.put("obj", order);

				}
			}
			sub_map.put("list", list);
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 代理提现
	 * 
	 * @return
	 */
	public String agenttx() {
		getLscode();
		DBObject db = wwzService.getAgentPrice(custid, fromUserid);
		db.put("headimgurl", wwzService.getWxUsertype(fromUserid, "headimgurl"));
		db.put("nickname", wwzService.getWxUsertype(fromUserid, "nickname"));
		Struts2Utils.getRequest().setAttribute("entity", db);
		return "agenttx";
	}

	/**
	 * 代理提现记录
	 * 
	 * @return
	 */
	public String agenttxweb() {
		getLscode();
		Struts2Utils.getRequest().setAttribute("state", 2);
		return "agenttxweb";
	}

	/**
	 * 微信提现
	 */
	public void wxtx() throws Exception {
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		getLscode();
		DBObject wx = wwzService.getWxUser(fromUserid);
		if (wx.get("_id").equals("notlogin")) {
			params.put("state", 3);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
		WxToken wxtoken = GetAllFunc.wxtoken.get(custid);
		WxPayConfig wxconfig = new WxPayConfig();
		if (wxtoken.getQx() == 0) {
			params.put("state", 1);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		} else if (wxtoken.getQx() == 1) {
			wxconfig = GetAllFunc.wxPay.get(custid);
		} else if (wxtoken.getQx() == 2) {// 父类结算
			wxconfig = GetAllFunc.wxPay.get(wwzService.getparentcustid(custid));
		}
		// 支付的价格
		String price = Struts2Utils.getParameter("price");
		if (wwzService.getAgent(custid, fromUserid) < Double.parseDouble(price)) {
			// 账号余额不足
			params.put("state", 2);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
		if (Double.parseDouble(price) > 200 || Double.parseDouble(price) < 1) {
			// 输入金额有误（支持1-200）
			params.put("state", 4);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
		// 备注
		String remark = Struts2Utils.getParameter("remark");
		// 活动名称
		String act_name = Struts2Utils.getParameter("act_name");
		// 红包祝福语
		String wishing = Struts2Utils.getParameter("wishing");

		// 10位序列号,可以自行调整。
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		String orderno = DateFormat.getDate() + strRandom + mongoSequence.currval(PubConstants.SHOP_AGENTDETAIL);

		String nonce_str = PayCommonUtil.CreateNoncestr();
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("nonce_str", nonce_str);
		parameters.put("mch_billno", orderno);
		parameters.put("mch_id", wxconfig.getPartner());
		parameters.put("wxappid", wxconfig.getAppid());
		parameters.put("remark", remark);
		parameters.put("send_name", wxconfig.getName());// 商户名称
		parameters.put("re_openid", wwzService.getWxUsertype(fromUserid, "fromUser"));
		parameters.put("total_amount", BaseDecimal.round(BaseDecimal.multiplication(price, "100"), 0));
		parameters.put("total_num", "1");
		parameters.put("wishing", wishing);
		parameters.put("client_ip", Struts2Utils.getRequest().getRemoteAddr());
		parameters.put("act_name", act_name);

		String sign = PayCommonUtil.createSign("UTF-8", parameters, wxconfig.getPartner_key());
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);

		String result = CommonUtil.httpsRequestSSL("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack",
				"POST", requestXML, wxconfig.getPartner(),
				"D:/certs/" + wxconfig.getPartner() + "_" + wxconfig.getPartner() + "/apiclient_cert.p12");

		Map<String, String> map = XMLUtil.doXMLParse(result);
		if (map.get("return_msg").equals("发放成功") && map.get("err_code_des").equals("发放成功")) {
			// 更新账号
			wwzService.delAgent(agid, Double.parseDouble(price), orderno, fromUserid, custid);

		}
		params.put("state", 0);
		params.put("value", price);
		String json = JSONArray.fromObject(params).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 加载限购信息
	 */
	public void ajaxrestriction() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			DBObject db = baseDao.getMessage(PubConstants.DATA_PRODUCT, Long.parseLong(id));
			// 加载限购信息
			HashMap<String, Object> whereMap = new HashMap<>();
			if (db.get("gmcs") != null && Integer.parseInt(db.get("gmcs").toString()) > 0) {
				whereMap.clear();
				whereMap.put("pid", Long.parseLong(id));
				whereMap.put("fromUserid", fromUserid);
				int ll = 0;
				// 预订库检测
				List<DBObject> list = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap, null);
				for (DBObject dbObject : list) {
					DBObject order = baseDao.getMessage(PubConstants.WX_ORDERFORM, dbObject.get("orderid").toString());
					if (order != null && Integer.parseInt(order.get("state").toString()) != 1) {
						ll += Integer.parseInt(order.get("count").toString());
					}
				}
				// 购物车检测
				whereMap.clear();
				whereMap.put("pid", Long.parseLong(id));
				whereMap.put("fromUserid", fromUserid);

				List<DBObject> listgwc = baseDao.getList(PubConstants.SUC_SHOPPINGCART, whereMap, null);
				for (DBObject dbObject : listgwc) {
					ll += Integer.parseInt(dbObject.get("count").toString());
				}
				if (ll >= Integer.parseInt(db.get("gmcs").toString())) {
					sub_map.put("value", 0);
				} else {
					sub_map.put("value", Integer.parseInt(db.get("gmcs").toString()) - ll);
				}
			}
			sub_map.put("state", 0);
			// 加载规格
			whereMap.clear();
			whereMap.put("parentid", Long.parseLong(id));
			HashMap<String, Object> sortMap = new HashMap<String, Object>();
			sortMap.put("sort", -1);
			List<DBObject> spelist = baseDao.getList(PubConstants.SHOP_SPECIFICATION, whereMap, sortMap);
			sub_map.put("list", spelist);

		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}

	/**
	 * 代理的店铺列表
	 * 
	 * @return
	 */
	public String agentshop() {
		getLscode();
		return "agentshop";
	}

	/**
	 * ajax获取代理店铺
	 */
	public void ajaxagentshop() {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		HashMap<String, Object> whereMap = new HashMap<>();
		HashMap<String, Object> sortMap = new HashMap<>();
		whereMap.put("fromUserid", fromUserid);
		sortMap.put("createdate", -1);
		if (StringUtils.isNotEmpty(Struts2Utils.getParameter("fypage"))) {
			fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		List<DBObject> list = baseDao.getList(PubConstants.SHOP_SHOPAGENT, whereMap, fypage, 10, sortMap);
		if (list.size() > 0) {
			sub_map.put("state", 0);
			for (DBObject dbObject : list) {
				DBObject db = baseDao.getMessage(PubConstants.SHOP_SHOPMB,
						Long.parseLong(dbObject.get("wid").toString()));
				dbObject.put("picurl", db.get("logo"));
				dbObject.put("name", db.get("name"));
				dbObject.put("summary", db.get("summary"));
			}
			sub_map.put("list", list);
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	* 
	*/
	public void check() {
		HashMap<String, Object> whereMap = new HashMap<>();
		List<DBObject> list = baseDao.getList("user", whereMap, null);
		for (DBObject dbObject : list) {
			UserInfo user = (UserInfo) UniObject.DBObjectToObject(dbObject, UserInfo.class);
			baseDao.insert("user_info", user);
		}
	}

	/**
	 * 确认收货
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public void delivery() throws Exception {
		getLscode();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		sub_map.put("state", 1);
		String oid = Struts2Utils.getParameter("oid");
		DBObject dbObject = baseDao.getMessage(PubConstants.WX_ORDERFORM, oid); 
		if (dbObject != null && dbObject.get("fromUserid") != null
				&& dbObject.get("fromUserid").toString().equals(fromUserid)) {
			OrderForm order = (OrderForm) UniObject.DBObjectToObject(dbObject, OrderForm.class);
			order.setState(4);// 确认收货
			order.setDeliveryDate(new Date());
			baseDao.insert(PubConstants.WX_ORDERFORM, order);
			// 开始返利结算
			// 1.特约区
			Double bl = (double) order.getContri_money();
			// 2.大众区
			Double bl_ty = (double) order.getPublic_money();
			// 3.会员区
			Double bl_hy = (double) order.getMembers_money();
			HashMap<String, Object> whereMap = new HashMap<>();
			whereMap.clear();
			whereMap.put("custid", SysConfig.getProperty("custid"));
			DBObject db = baseDao.getMessage(PubConstants.INTEGRAL_INTESETTING, whereMap);
			if (db != null) {
				InteSetting sett = (InteSetting) UniObject.DBObjectToObject(db, InteSetting.class);

				// 记录当前购物用户的提成
				wwzService.addjf(bl + "", fromUserid, "shop_djzj", custid, 1, 1, 1);
				wwzService.addjf(wwzService.getGivingPro(bl_ty), fromUserid, "shop_bmzt", custid, 1, 2, 0);
				wwzService.addjf(wwzService.getGivingPro(bl_hy), fromUserid, "shop_bmzt", custid, 1, 2, 0);
				bl_ty = Double.parseDouble(BaseDecimal.division(
						BaseDecimal.multiplication(wwzService.getGivingPro(bl_ty), sett.getDzqtc() + ""), "100"));
				bl_hy = Double.parseDouble(BaseDecimal.division(
						BaseDecimal.multiplication(wwzService.getGivingPro(bl_hy), sett.getHyqtc() + ""), "100"));
				// 获取直推人员
				DBObject wxuser = wwzService.getWxUser(fromUserid);

				if (Integer.parseInt(wxuser.get("tjlx").toString()) == 0) {
					// 会员推荐
					DBObject tjuser = wwzService.getWXuserVipNo(wxuser.get("renumber").toString());
					if (tjuser != null) {
						// 记录提成
						wwzService.addjf(
								BaseDecimal.division(BaseDecimal.multiplication(bl + "", sett.getDirect() + ""), "100"),
								tjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);
						wwzService.addjf(BaseDecimal
								.division(BaseDecimal.multiplication(bl_ty + "", sett.getDirect() + ""), "100"),
								tjuser.get("_id").toString(), "shop_bmzt", custid, 1, 2, 0);
						wwzService.addjf(BaseDecimal
								.division(BaseDecimal.multiplication(bl_hy + "", sett.getDirect() + ""), "100"),
								tjuser.get("_id").toString(), "shop_bmzt", custid, 1, 2, 0);

					}
					System.out.println("**************************"+tjuser.get("renumber"));

					// 获取间接推荐人员
					if(tjuser!=null&&tjuser.get("renumber")!=null){
						tjuser = wwzService.getWXuserVipNo(tjuser.get("renumber").toString());
						// 记录提成
						System.out.println("----+----"+tjuser.get("renumber"));
						System.out.println("----+----"+BaseDecimal
								.division(BaseDecimal.multiplication(bl + "", sett.getBetween() + ""), "100"));
						wwzService.addjf(BaseDecimal
								.division(BaseDecimal.multiplication(bl + "", sett.getBetween() + ""), "100"),
								tjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);
					}  
				} else if (Integer.parseInt(wxuser.get("tjlx").toString()) == 1) {
					// 管理员推荐
					whereMap.clear();
					whereMap.put("number", Long.parseLong(wxuser.get("renumber").toString()));
					DBObject tjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
					if (tjuser != null) {
						// 记录提成
						wwzService.addjf(
								BaseDecimal.division(BaseDecimal.multiplication(bl + "", sett.getDirect() + ""), "100"),
								tjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

					}
					// 获取间接推荐人员 （当推荐人为管理员时候间接推荐的奖励也发放到管理员账户）
					if (tjuser != null) {
						// 记录提成
						wwzService.addjf(BaseDecimal
								.division(BaseDecimal.multiplication(bl + "", sett.getBetween() + ""), "100"),
								tjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

					}
				}

				// 获取部门人员;
				whereMap.clear();
				whereMap.put("number", order.getDeptCode());
				DBObject user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
				if (user != null) {
					// 记录提成
					wwzService.addjf(BaseDecimal
							.division(BaseDecimal.multiplication(bl + "", sett.getSameDepartment() + ""), "100"),
							user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

					// 获取县级

					whereMap.clear();
					if(user.get("parentId") != null){
						whereMap.put("_id", user.get("parentId").toString());
					}
					user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
					if (user != null) {
						// 记录提成
						wwzService.addjf(BaseDecimal
								.division(BaseDecimal.multiplication(bl + "", sett.getSameCounty() + ""), "100"),
								user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

						// 县级存在，获取市级

						whereMap.clear();
						if(user.get("parentId") != null){
							whereMap.put("_id", user.get("parentId").toString());
						}
						user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
						if (user != null) {
							// 记录提成
							wwzService.addjf(BaseDecimal
									.division(BaseDecimal.multiplication(bl + "", sett.getSameCity() + ""), "100"),
									user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

							// 县级存在，市级存在，获取省级
							whereMap.clear();
							if(user.get("parentId") != null){
								whereMap.put("_id", user.get("parentId").toString());
							}
							user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
							if (user != null) {
								// 记录提成
								wwzService.addjf(BaseDecimal.division(
										BaseDecimal.multiplication(bl + "", sett.getSameProvince() + ""), "100"),
										user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);
							}
						} else {

							// 县级存在，市级不存在， 获取省级

							whereMap.clear();
							whereMap.put("agentLevel", 1);
							if(user.get("province") != null){
								whereMap.put("province", user.get("province").toString());
							}
							user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
							if (user != null) {
								// 记录提成
								wwzService.addjf(BaseDecimal.division(
										BaseDecimal.multiplication(bl + "", sett.getSameProvince() + ""), "100"),
										user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

							}

						}

					} else {
						// 县级不存在， 获取市级

						whereMap.clear();
						whereMap.put("agentLevel", 2);
						if(user.get("county")!=null){
							whereMap.put("county", user.get("county").toString());
						}
						user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
						if (user != null) {
							// 记录提成
							wwzService.addjf(BaseDecimal
									.division(BaseDecimal.multiplication(bl + "", sett.getSameCity() + ""), "100"),
									user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

							// 县级不存在， 市级存在，获取省级
							whereMap.clear();
							if(user.get("parentId") != null){
								whereMap.put("_id", user.get("parentId").toString());
							}
							user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
							if (user != null) {
								// 记录提成
								wwzService.addjf(BaseDecimal.division(
										BaseDecimal.multiplication(bl + "", sett.getSameProvince() + ""), "100"),
										user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);
							}
						} else {
							// 县级不存在，市级不存在， 获取省级

							whereMap.clear();
							whereMap.put("agentLevel", 1);
							if(user.get("province") != null){
								whereMap.put("province", user.get("province").toString());
							}
							user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
							if (user != null) {
								// 记录提成
								wwzService.addjf(BaseDecimal.division(
										BaseDecimal.multiplication(bl + "", sett.getSameProvince() + ""), "100"),
										user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

							}

						}

					}
				}

				// 获取异地县域
				whereMap.clear();
				if(user != null &&user.get("county")!=null){
					whereMap.put("county", user.get("county").toString());
				}
				user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
				if (user != null) {

					// 县域存在 记录提成
					wwzService.addjf(BaseDecimal
							.division(BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
							user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

					// 县级存在，获取市级

					whereMap.clear();
					if(user.get("parentId") != null){
						whereMap.put("_id", user.get("parentId").toString());
					}
					user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
					if (user != null) {
						// 记录提成
						wwzService.addjf(BaseDecimal
								.division(BaseDecimal.multiplication(bl + "", sett.getDiffCity() + ""), "100"),
								user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

						// 县级存在，市级存在，获取省级
						whereMap.clear();
						if(user.get("parentId") != null){
							whereMap.put("_id", user.get("parentId").toString());
						}
						user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
						if (user != null) {
							// 记录提成
							wwzService.addjf(
									BaseDecimal.division(
											BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
									user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);
						}
					} else {

						// 县级存在，市级不存在， 获取省级

						whereMap.clear();
						whereMap.put("agentLevel", 1);
						if(user.get("province")!=null){
							whereMap.put("province", user.get("province").toString());
						}
						user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
						if (user != null) {
							// 记录提成
							wwzService.addjf(
									BaseDecimal.division(
											BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
									user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

						}

					}

				} else {
					// 县域不存在获取市级
					whereMap.clear();
					whereMap.put("agentLevel", 2);
					if(user.get("county")!=null){
						whereMap.put("county", user.get("county").toString());
					}
					user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
					if (user != null) {
						// 记录提成
						wwzService.addjf(BaseDecimal
								.division(BaseDecimal.multiplication(bl + "", sett.getDiffCity() + ""), "100"),
								user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

						// 县级不存在， 市级存在，获取省级
						whereMap.clear();
						if(user.get("parentId") != null){
							whereMap.put("_id", user.get("parentId").toString());
						}
						
						user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
						if (user != null) {
							// 记录提成
							wwzService.addjf(
									BaseDecimal.division(
											BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
									user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);
						}
					} else {
						// 县级不存在，市级不存在， 获取省级

						whereMap.clear();
						whereMap.put("agentLevel", 1);
						if(user.get("province") != null){
							whereMap.put("province", user.get("province").toString());
						}
						user = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
						if (user != null) {
							// 记录提成
							wwzService.addjf(
									BaseDecimal.division(
											BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
									user.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0);

						}

					}

				}

			}
			sub_map.put("state", 0);//操作成功
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}
	
	/**
	 * 确认收货
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public void delivery1() throws Exception {
		getLscode();
		HashMap<String, Object>whereMap = new HashMap<>();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		sub_map.put("state", 1);
		//String oid = Struts2Utils.getParameter("oid");
		//DBObject dbObject = baseDao.getMessage(PubConstants.SHOP_ODERFORMPRO, Long.parseLong(oid));
		//System.out.println(dbObject);
		//System.err.println(fromUserid);
		String oid =Struts2Utils.getParameter("oid");
		String comid =Struts2Utils.getParameter("comid");
		if(StringUtils.isNotEmpty(comid)){
		    whereMap.put("orderid", oid);
		}
		if(StringUtils.isNotEmpty(comid)){
			whereMap.put("pro.comid", Long.parseLong(comid));
		}
		List<DBObject> list = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap,null);
		DBObject dbObjects = baseDao.getMessage(PubConstants.WX_ORDERFORM, oid);
		for (DBObject dbObject : list) {
			if (dbObjects != null && dbObjects.get("fromUserid") != null
					&& dbObjects.get("fromUserid").toString().equals(fromUserid)) {
				System.out.println("进入这个方法");
				System.out.println("---->"+dbObject);
				OrderFormpro order = (OrderFormpro) UniObject.DBObjectToObject(dbObject, OrderFormpro.class);
				order.set_id(Long.parseLong(dbObject.get("_id").toString()));
				order.setGoodstate(4);
				
				baseDao.insert(PubConstants.SHOP_ODERFORMPRO, order);
				// 开始返利结算
				Double bl = 0.0;
				Double bl_ty = 0.0;
				Double bl_hy = 0.0;
				// 1.会员区支付金额
				if(order.getContri_money() != null){
					bl_hy =  order.getContri_money();
				}
				System.out.println("1--->"+order.getContri_money());
				// 2.大众区支付金额
				if(order.getPublic_money() != null){
				    bl_ty =  order.getPublic_money();
				}
				System.out.println("2--->"+order.getPublic_money());
				// 3.特约区支付金额
				if(order.getMembers_money() != null){
					bl = order.getMembers_money();
				}
				System.out.println("3--->"+order.getMembers_money());
				whereMap.clear();
				whereMap.put("_id",SysConfig.getProperty("custid"));
				//获取设置信息
				DBObject db = baseDao.getMessage(PubConstants.INTEGRAL_INTESETTING, whereMap);
				if (db != null) {
					InteSetting sett = (InteSetting) UniObject.DBObjectToObject(db, InteSetting.class);

					// 记录当前购物用户的提成
					//wwzService.addjfoid(bl + "", fromUserid, "shop_djzj", custid, 1, 1, 1,oid,"特约区下单"); 
					//大众区购买商品赠送乐乐币
					//金额、fromUserid推荐人Id、类型、平台ID、交易类型0正常交易、1为系统赠送、0普通积分，1为PP，2为LL、0-未冻结   1-已冻结
					wwzService.addjfoid(wwzService.getGivingPro(bl_ty), fromUserid, "shop_bmzt", custid, 1, 2, 0,oid,"大众区下单");
					//会员区下单消费累计
					wwzService.addjfoid(wwzService.getGivingPro(bl_hy), fromUserid, "shop_bmzt", custid, 1, 2, 1,oid,"会员区下单");
					//获取大众区LL币赠送数量和赠送比例
					bl_ty = Double.parseDouble(BaseDecimal.division(
							BaseDecimal.multiplication(wwzService.getGivingPro(bl_ty), sett.getDzqtc() + ""), "100"));
					//获取会员区LL币赠送数量和赠送比例
					bl_hy = Double.parseDouble(BaseDecimal.division(
							BaseDecimal.multiplication(wwzService.getGivingPro(bl_hy), sett.getHyqtc() + ""), "100"));
					// 获取直推人员
					DBObject wxuser = wwzService.getWxUser(fromUserid);
                    if(wxuser.get("tjlx") != null){
                    	//有推荐人
                    	if (Integer.parseInt(wxuser.get("tjlx").toString()) == 0) {
    						System.out.println("----->"+wxuser.get("renumber"));
                    		// 获取推荐人会员编号
    						if(wxuser.get("renumber")!=null){
    							//根据会员编号获取推荐人信息
    							DBObject tjuser = wwzService.getWXuserVipNo(wxuser.get("renumber").toString()); 
        						//推荐人不为空
    							if (tjuser != null) {
        							// 记录提成
        							wwzService.addjfoid(
        									BaseDecimal.division(BaseDecimal.multiplication(bl + "", sett.getDirect() + ""), "100"),
        									tjuser.get("_id").toString(), "shop_bmzt", SysConfig.getProperty("custid"), 1, 1, 0,oid,"特约区下单");
        							wwzService.addjfoid(BaseDecimal
        									.division(BaseDecimal.multiplication(bl_ty + "", sett.getDirect() + ""), "100"),
        									tjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 2, 0,oid,"大众区下单");
        							wwzService.addjfoid(BaseDecimal
        									.division(BaseDecimal.multiplication(bl_hy + "", sett.getDirect() + ""), "100"),
        									tjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 2, 0,oid,"会员区下单");


            						// 获取间接推荐人员
            						if(tjuser!=null&&tjuser.get("renumber")!=null){
            							tjuser = wwzService.getWXuserVipNo(tjuser.get("renumber").toString());
            						    if(tjuser!=null){
            						    	// 记录二级推荐人提成
                							wwzService.addjfoid(BaseDecimal
                									.division(BaseDecimal.multiplication(bl + "", sett.getBetween() + ""), "100"),
                									tjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 1, 0,oid,"间推人返利");
            						    } 	 

            						}
        						}
    						}
    						//
    					} else if (Integer.parseInt(wxuser.get("tjlx").toString()) == 1) {
    						// 管理员推荐
    						whereMap.clear();
    						whereMap.put("number", Long.parseLong(wxuser.get("renumber").toString()));
    						DBObject tjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
    						if (tjuser != null) {
    							// 记录提成
    							wwzService.addjfoid(
    									BaseDecimal.division(BaseDecimal.multiplication(bl + "", sett.getDirect() + ""), "100"),
    									tjuser.get("_id").toString(), "shop_bmzt",  SysConfig.getProperty("custid"), 1, 1, 0,oid,"直推荐返利");

    						}
    						// 获取间接推荐人员 （当推荐人为管理员时候间接推荐的奖励也发放到管理员账户）
    						if (tjuser != null) {
    							// 记录提成
    							wwzService.addjfoid(BaseDecimal
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
						wwzService.addjfoid(BaseDecimal
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
							wwzService.addjfoid(BaseDecimal
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
								wwzService.addjfoid(BaseDecimal
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
									wwzService.addjfoid(BaseDecimal.division(
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
									wwzService.addjfoid(BaseDecimal.division(
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
								wwzService.addjfoid(BaseDecimal
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
									wwzService.addjfoid(BaseDecimal.division(
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
									wwzService.addjfoid(BaseDecimal.division(
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
						wwzService.addjfoid(BaseDecimal
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
							wwzService.addjfoid(BaseDecimal
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
								wwzService.addjfoid(
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
								wwzService.addjfoid(
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
							wwzService.addjfoid(BaseDecimal
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
								wwzService.addjfoid(
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
								wwzService.addjfoid(
										BaseDecimal.division(
												BaseDecimal.multiplication(bl + "", sett.getDiffProvince() + ""), "100"),
										shjuser.get("_id").toString(), "shop_bmzt", custid, 1, 1, 0,oid,"外地省级代理返利0.5%");

							}

						}

					}

				}
				sub_map.put("state", 0);//操作成功
			}
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
		 
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}
	
	/**
	 * 确认收货
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public void delivery2() throws Exception {
		getLscode();
		HashMap<String, Object>whereMap = new HashMap<>();
		Map<String, Object> sub_map = new HashMap<String, Object>();
		sub_map.put("state", 1);
		String oid =Struts2Utils.getParameter("oid");
		String comid =Struts2Utils.getParameter("comid");
		if(StringUtils.isNotEmpty(comid)){
		    whereMap.put("orderid", oid);
		}
		if(StringUtils.isNotEmpty(comid)){
			whereMap.put("pro.comid", Long.parseLong(comid));
		}
		//只查询这个订单为待确认的收货的商品
		whereMap.put("goodstate", 3);
		
		DBObject userdb = baseDao.getMessage(PubConstants.USER_INFO, fromUserid);
		List<DBObject> list = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap,null);
		DBObject dbObjects = baseDao.getMessage(PubConstants.WX_ORDERFORM, oid);
		for (DBObject dbObject : list) {
			if (dbObjects != null && dbObjects.get("fromUserid") != null && dbObjects.get("fromUserid").toString().equals(fromUserid)) {
				OrderFormpro order = (OrderFormpro) UniObject.DBObjectToObject(dbObject, OrderFormpro.class);
				order.set_id(Long.parseLong(dbObject.get("_id").toString()));
				order.setGoodstate(4);
				baseDao.insert(PubConstants.SHOP_ODERFORMPRO, order);
				// 开始返利结算
//				Double bl = 0.0;
				Double bl_ty = 0.0;
				Double bl_hy = 0.0;
				// 1.会员区支付金额
				if(order.getContri_money() != null){
					bl_hy =  order.getContri_money();
				}
				// 2.大众区支付金额
				if(order.getPublic_money() != null){
				    bl_ty =  order.getPublic_money();
				}
				if(bl_ty>0){
					wwzService.addjfoid(wwzService.getGivingPro(bl_ty), fromUserid, "shop_bmzt", custid, 1, 2, 0,oid,"大众区下单");
					sub_map.put("state", 0);//操作成功
				}
				if(bl_hy>0) {
					String custids = SysConfig.getProperty("custid");
					whereMap.clear();
					whereMap.put("_id",custids);
					//获取设置信息
					DBObject db = baseDao.getMessage(PubConstants.INTEGRAL_INTESETTING, whereMap);
					if (db != null) {
						InteSetting sett = (InteSetting) UniObject.DBObjectToObject(db, InteSetting.class);
						 // 获取部门人员;
						whereMap.clear();
						if(dbObjects.get("deptCode")!=null) {
							whereMap.put("no",dbObjects.get("deptCode").toString());
							whereMap.put("agentLevel", 4);
							DBObject bmuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap); 
							if (bmuser != null) {
								// 记录提成
								String price = BaseDecimal.division(BaseDecimal.multiplication(bl_hy + "", sett.getSameDepartment() + ""), "100");
								System.out.println(price);
								wwzService.addIntegralYjInfo(oid, userdb.get("_id").toString(), "shop_bmzt", bl_hy.toString(), "报单中心产品返利10%");
								whereMap.clear();
								MongoDbUtil mongoDbUtil=new MongoDbUtil();
						    	whereMap.put("fromUserid", bmuser.get("_id").toString());
						    	DBObject db1 = mongoDbUtil.findOne(PubConstants.SUC_INTEGRALRECORD, whereMap);
								IntegralRecord recordInfo = (IntegralRecord) UniObject.DBObjectToObject(db1, IntegralRecord.class);
								if(null==db1) {
									recordInfo.set_id(mongoSequence.currval(PubConstants.SUC_INTEGRALRECORD));
									recordInfo.setCustid(SysConfig.getProperty("custid"));
									recordInfo.setFromUserid(bmuser.get("_id").toString());
									recordInfo.setYjhbvalue(String.valueOf(price));
									
								}else {
									BigDecimal oldyj = new BigDecimal(recordInfo.getYjvalue());
									BigDecimal newyj = new BigDecimal(price);
									String setYj = oldyj.add(newyj).toString();
									recordInfo.set_id(db1.get("_id"));
									recordInfo.setYjvalue(setYj);
								}
								baseDao.insert (PubConstants.SUC_INTEGRALRECORD, recordInfo);
								
							}
						}
						
						
						
						String[] address = dbObjects.get("address").toString().split("-");
						int agentprovinceid =0;
						int agentcityid = 0;
						int agentcountyid = 0;
						// 获取省级
						whereMap.clear();
						if(address.length > 2 && StringUtils.isNotEmpty(address[2])) {
							String county = address[0].trim();
							Pattern pattern = Pattern.compile("^.*" + county + ".*$", Pattern.CASE_INSENSITIVE);
							whereMap.put("parentId",0L);
							whereMap.put("area",pattern);
							DBObject arealist = baseDao.getMessage(PubConstants.USER_AGENTAREA, whereMap);
							whereMap.clear();
							if(arealist != null) {
								agentprovinceid = Integer.parseInt(arealist.get("_id").toString());
								whereMap.put("agentprovinceid", agentprovinceid);
								whereMap.put("agentLevel", 1);
								DBObject shjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
								if (shjuser != null) {
									// 记录提成
									String price = BaseDecimal.division(BaseDecimal.multiplication(bl_hy + "", sett.getSameProvince() + ""), "100");
									System.out.println(price);
									jltcInfo(price,"区域会员下单","0", shjuser, userdb,"2%");
								}
							}
							
						}
						
						// 获取市级
						whereMap.clear();
						if(address.length > 2 && StringUtils.isNotEmpty(address[2])) {
							String county = address[1].trim();
							Pattern pattern = Pattern.compile("^.*" + county + ".*$", Pattern.CASE_INSENSITIVE);
							whereMap.put("parentId",agentprovinceid);
							whereMap.put("area",pattern);
							DBObject arealist = baseDao.getMessage(PubConstants.USER_AGENTAREA, whereMap);
							whereMap.clear();
							if(arealist != null) {
								agentcityid = Integer.parseInt(arealist.get("_id").toString());
								whereMap.put("agentcityid", agentcityid);
								whereMap.put("agentLevel", 2);
								DBObject sjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
								if (sjuser != null) {
									// 记录提成
									String price = BaseDecimal.division(BaseDecimal.multiplication(bl_hy + "", sett.getSameCity() + ""), "100");
									jltcInfo(price,"区域会员下单","0",sjuser, userdb,"3%");
								}
							}
						}
						
						// 获取县级
						whereMap.clear();
						if(address.length > 2 && StringUtils.isNotEmpty(address[2])) {
							String[] county = address[2].split(" ");
							Pattern pattern = Pattern.compile("^.*" + county[0].trim() + ".*$", Pattern.CASE_INSENSITIVE);
							whereMap.put("parentId",agentcityid);
							whereMap.put("area",pattern);
							DBObject arealist = baseDao.getMessage(PubConstants.USER_AGENTAREA, whereMap);
							whereMap.clear();
							if(arealist != null) {
								agentcountyid = Integer.parseInt(arealist.get("_id").toString());
								whereMap.put("agentcountyid", agentcountyid);
								whereMap.put("agentLevel", 3);
								DBObject xjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
								if (xjuser != null) {
									// 记录提成
									String price = BaseDecimal.division(BaseDecimal.multiplication(bl_hy + "", sett.getSameCounty() + ""), "100");
									jltcInfo(price,"区域会员下单","0", xjuser, userdb,"5%");
								}
							}
						}
						whereMap.clear();
						// 获取异地县域
						String p = null;
						String c = null;
						String q = null;
						if(null != userdb.get("province")) {
							p = userdb.get("province").toString().trim();
						}
						if(null != userdb.get("city")) {
							c = userdb.get("city").toString().trim();
						}
						if(null != userdb.get("county")) {
							q = userdb.get("county").toString().trim();
						}
						
						if(null != p ) {
							Pattern pattern = Pattern.compile("^.*" + p + ".*$", Pattern.CASE_INSENSITIVE);
							whereMap.put("parentId",0L);
							whereMap.put("area",pattern);
							DBObject arealist = baseDao.getMessage(PubConstants.USER_AGENTAREA, whereMap);
							if(arealist != null && agentprovinceid != Integer.parseInt(arealist.get("_id").toString())) {
								whereMap.clear();
								//省
								int agentprovinceidYD = Integer.parseInt(arealist.get("_id").toString());
								int agentcityidYD = 0;
								whereMap.put("agentprovinceid", agentprovinceidYD);
								whereMap.put("agentLevel", 1);
								DBObject shjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
								if (shjuser != null) {
									// 记录提成
									String price = BaseDecimal.division(BaseDecimal.multiplication(bl_hy + "", sett.getDiffProvince() + ""), "100");
									jltcInfo(price,"区域会员异地下单","2", shjuser, userdb,"0.5%");
								}
								whereMap.clear();
								//市
								Pattern patternC = Pattern.compile("^.*" + c + ".*$", Pattern.CASE_INSENSITIVE);
								whereMap.put("parentId",agentprovinceidYD);
								whereMap.put("area",patternC);
								DBObject areaClist = baseDao.getMessage(PubConstants.USER_AGENTAREA, whereMap);
								if(null != areaClist) {
									whereMap.clear(); 
									agentcityidYD = Integer.parseInt(areaClist.get("_id").toString());
									whereMap.put("agentcityid", agentcityidYD);
									whereMap.put("agentLevel", 2);
									DBObject sjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
									if (sjuser != null) {
										// 记录提成
										String price = BaseDecimal.division(BaseDecimal.multiplication(bl_hy + "", sett.getDiffCity() + ""), "100");
										jltcInfo(price,"区域会员异地下单","2", sjuser, userdb,"0.5%");
									}
								}
								whereMap.clear(); 
								//区
								
								Pattern patternQ = Pattern.compile("^.*" + q + ".*$", Pattern.CASE_INSENSITIVE);
								whereMap.put("parentId",agentcityidYD);
								whereMap.put("area",patternQ);
								DBObject areaQlist = baseDao.getMessage(PubConstants.USER_AGENTAREA, whereMap);
								if(null != areaQlist) {
									whereMap.clear(); 
									int agentcountyidYD = Integer.parseInt(areaClist.get("_id").toString());
									whereMap.put("agentcountyid", agentcountyidYD);
									whereMap.put("agentLevel", 3);
									DBObject xjuser = baseDao.getMessage(PubConstants.USER_INFO, whereMap);
									if (xjuser != null) {
										// 记录提成
										String price = BaseDecimal.division(BaseDecimal.multiplication(bl_hy + "", sett.getDiffCounty() + ""), "100");
										jltcInfo(price, "区域会员异地下单","2",xjuser, userdb,"1%");
									}
								}
							}
						}
						sub_map.put("state", 0);//操作成功
					}
				}
			}
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
			sub_map.put("state", 0);//操作成功
		}
		String json = JSONArray.fromObject(sub_map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}
	
	public void jltcInfo(String price,String source,String type,DBObject xuser,DBObject userdb,String protion) {
		HashMap<String, String> res = wwzService.addyjhbInfo(xuser.get("_id").toString(),price);
		IntegralBackreord backreord = new IntegralBackreord();
		backreord.set_id(mongoSequence.currval(PubConstants.INTEGRAL_BACKREORD));
		backreord.setToUserVipno(xuser.get("no").toString());
		backreord.setToUserLevel(xuser.get("agentLevel").toString());
		backreord.setFromVipno(userdb.get("no").toString());
		backreord.setSource(source);
		backreord.setOriginalValue(res.get("oldyjhb"));
		backreord.setType(type);
		backreord.setValue(price);
		backreord.setCreateDate(new Date());
		backreord.setProportion(protion);
		wwzService.addRecovery(backreord);
	}

	/**
	 * 生成订单
	 * 
	 * @throws Exception
	 */
	public void COrderFromPro() throws Exception {
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		getLscode();
		DBObject wx = wwzService.getWxUser(fromUserid);
		if (wx.get("_id").equals("notlogin")) {
			params.put("state", 3);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}

		// 支付的价格
		String price = Struts2Utils.getParameter("price");
		// 获取提交的商品名称
		String remark = Struts2Utils.getParameter("remark");
		String deptCode= Struts2Utils.getParameter("deptCode");
		// 商品类型
		int lx = Integer.parseInt(Struts2Utils.getParameter("lx"));
		// 总金额
		double total = Double.parseDouble(Struts2Utils.getParameter("total"));

		Long recordid = 0L;
		// 商品编号
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("recordid"))) {
			recordid = Long.parseLong(Struts2Utils.getParameter("recordid"));// 14
		}
		double remoney = 0f;
		// 商品价格
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("remoney"))) {
			remoney = Double.parseDouble(Struts2Utils.getParameter("remoney"));// 14
		}
		// 地址信息
		String name = Struts2Utils.getParameter("name");
		String tel = Struts2Utils.getParameter("tel");
		String address = Struts2Utils.getParameter("address");
		String no = Struts2Utils.getParameter("no");
		// 店铺编号
		Long comid = 0L;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("comid"))) {
			comid = Long.parseLong(Struts2Utils.getParameter("comid"));// 14
		}
		String nums = Struts2Utils.getParameter("num");
		// 数量
		int num = Integer.parseInt(nums);
		Long proid = 0L;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("proid"))) {
			proid = Long.parseLong(Struts2Utils.getParameter("proid"));// 14
		}
		// 规格
		String spec = Struts2Utils.getParameter("spec");
		String kjid = Struts2Utils.getParameter("kjid");
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 返还
		String jffh = "";
		// 兑换
		String jfdh = Struts2Utils.getParameter("jfdh");
		// 10位序列号,可以自行调整。
		// 限购
		HashMap<String, Object> backMap = new HashMap<String, Object>();
		backMap.put("context", 0);
		backMap.put("summary", 0);
		DBObject pro = baseDao.getMessage(PubConstants.DATA_PRODUCT, recordid, backMap);
		if (pro.get("gmcs") != null && Integer.parseInt(pro.get("gmcs").toString()) > 0) {
			HashMap<String, Object> whereMap = new HashMap<>();
			whereMap.put("pid", Integer.parseInt(pro.get("_id").toString()));
			whereMap.put("fromUserid", fromUserid);
			int ll = 0;
			List<DBObject> listdb = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap, null);
			for (DBObject dbObject : listdb) {
				DBObject order = baseDao.getMessage(PubConstants.WX_ORDERFORM, dbObject.get("orderid").toString());
				if (order != null && Integer.parseInt(order.get("state").toString()) != 1) {
					ll += Integer.parseInt(order.get("count").toString());
				}
			}
			if (ll >= Integer.parseInt(pro.get("gmcs").toString())) {
				// 购买次数已完
				params.put("state", 10);
				String json = JSONArray.fromObject(params).toString();
				Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
				return;
			}
		}

		String orderno = DateFormat.getDate() + strRandom + mongoSequence.currval("orderno");
		OrderForm entity = new OrderForm();
		entity.set_id(orderno);
		entity.setState(1);
		entity.setNo(no);
		entity.setLx(lx);

		entity.setFromUserid(fromUserid);
		entity.setCustid(custid);
		entity.setName(name);
		entity.setTel(tel);
		entity.setAddress(address);
		entity.setInsDate(new Date());

		entity.setComid(comid);// 14
		entity.setCount(num);// 15
		entity.setTotal(total);// 6
		entity.setKjid(kjid);
		if (StringUtils.isNotEmpty(jfdh)) {

			entity.setJfdh(Double.parseDouble(jfdh));
		}
		entity.setZfmoney(Double.parseDouble(price));// 7
		entity.setRecordid(recordid);
		entity.setRemoney(remoney);
		if (StringUtils.isNotEmpty(deptCode)) {
			entity.setDeptCode(Long.parseLong(deptCode));
		} 

		entity.setRemark(remark + "-" + spec);
		// 验证
		if (StringUtils.isNotEmpty(jffh)) {
			entity.setJffh(Double.parseDouble(jffh));
		}
		baseDao.insert(PubConstants.WX_ORDERFORM, entity);
		if (pro != null) {
			OrderFormpro o = new OrderFormpro();
			o.set_id(mongoSequence.currval(PubConstants.SHOP_ODERFORMPRO));
			o.setCount(num);
			o.setOrderid(orderno);
			o.setPro(pro);
			o.setSpec(spec);
			o.setFromUserid(fromUserid);
			o.setPid(Long.parseLong(pro.get("_id").toString()));
			baseDao.insert(PubConstants.SHOP_ODERFORMPRO, o);
			if (StringUtils.isNotEmpty(deptCode)) {
				o.setDeptCode(Long.parseLong(deptCode));
			} 
		}

		params.put("state", 0);
		params.put("orderno", orderno);
		String json = JSONArray.fromObject(params).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 生成购物车订单
	 * 
	 * @throws Exception
	 */
	public void COrderFromCar() throws Exception {
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		getLscode();
		DBObject wx = wwzService.getWxUser(fromUserid);
		if (wx.get("_id").equals("notlogin")) {
			params.put("state", 3);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
		// 支付的价格
		String price = "";
		// 获取提交的商品名称
		String remark = Struts2Utils.getParameter("remark");
		// 商品类型
		int lx = Integer.parseInt(Struts2Utils.getParameter("lx"));// 0 商品 1选号 2扫码付3优惠劵4砍价
		// 总金额
		float total = 0f;

		String recordid = null;
		// 商品编号
		if (StringUtils.isNotEmpty(Struts2Utils.getParameter("recordid"))) {
			recordid = Struts2Utils.getParameter("recordid");// 14
		}
		String remoney = null;
		// 商品价格
		if (StringUtils.isNotEmpty(Struts2Utils.getParameter("remoney"))) {
			remoney = Struts2Utils.getParameter("remoney");// 14
		}
		String spec = null;
		if (StringUtils.isNotEmpty(Struts2Utils.getParameter("spec"))) {
			spec = Struts2Utils.getParameter("spec");
		}

		// 地址信息
		String name = Struts2Utils.getParameter("name");
		String tel = Struts2Utils.getParameter("tel");
		String address = Struts2Utils.getParameter("address");
		String deptCode = Struts2Utils.getParameter("deptCode");
		String no = Struts2Utils.getParameter("no"); 
		String zflx = Struts2Utils.getParameter("zflx"); 
		double ssj=0;
		double btcp=wwzService.getBTCSprice();
		double ethp=wwzService.getETHSprice();
		double ppp=wwzService.getPPBSprice();
//		if(StringUtils.isNotEmpty(zflx)){
//			int zf=Integer.parseInt(zflx);
//			if(zf==0){ 
//			}else if(zf==1){
//				ssj=wwzService.getBTCSprice();
//			}else if(zf==2){
//				ssj=wwzService.getETHSprice();
//			}else if(zf==3){
//				ssj=wwzService.getPPBSprice();
//			}
//		} 
		String money="0";
		 
		
		// 店铺编号
		Long comid = 0L;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("comid"))) {
			comid = Long.parseLong(Struts2Utils.getParameter("comid"));// 14
		}
		// 数量
		String num = Struts2Utils.getParameter("num");
		Long proid = 0L;
		if (org.apache.commons.lang.StringUtils.isNotEmpty(Struts2Utils.getParameter("proid"))) {
			proid = Long.parseLong(Struts2Utils.getParameter("proid"));// 14
		}

		// 返还
		double jffh = 0;

		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String orderno = DateFormat.getDate() + strRandom + mongoSequence.currval("orderno");

		OrderForm entity = new OrderForm();
		entity.set_id(orderno);
		entity.setState(1);
		entity.setNo(no);
		entity.setLx(lx); 
		entity.setFromUserid(fromUserid);
		entity.setCustid(custid);
		entity.setName(name);
		entity.setTel(tel); 
		entity.setAddress(address);
		if(StringUtils.isNotEmpty(zflx)){
			entity.setZflx(Integer.parseInt(zflx));
		} 
		entity.setInsDate(new Date());
		if (StringUtils.isNotEmpty(deptCode)) {
			entity.setDeptCode(Long.parseLong(deptCode));
		}
		
		
		entity.setComid(comid);// 14
		entity.setCounts(num);// 15
		entity.setTotal(total);// 6

		// entity.setZfmoney(Double.parseDouble(price));// 7
		entity.setIds(recordid);
		entity.setRemark(remark);

		String cost = "0";
		String zfmoney = "0";
		// 大众区支付金额
		String public_money = "0";
		// 特约区支付金额
		String contri_money = "0";
		// 会员区支付金额
		String members_money = "0";
		String kd_money = "0";

		String[] ids = recordid.split(",");
		String[] nums = num.split(",");
		String[] specs = spec.split(",");
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isNotEmpty(ids[i])) {
				HashMap<String, Object> backMap = new HashMap<String, Object>();
				backMap.put("context", 0);
				backMap.put("summary", 0);
				DBObject pro = null;
				if (Struts2Utils.getParameter("isgwc").equals("1")) {
					pro = baseDao.getMessage(PubConstants.DATA_PRODUCT, Long.parseLong(ids[i]), backMap);
				} else {
					System.out.println(ids[i]);
					DBObject shop = baseDao.getMessage(PubConstants.SUC_SHOPPINGCART, Long.parseLong(ids[i]));

					System.out.println(shop.get("pid"));
					pro = baseDao.getMessage(PubConstants.DATA_PRODUCT, Long.parseLong(shop.get("pid").toString()),
							backMap);
					
				   baseDao.delete(PubConstants.SUC_SHOPPINGCART, Long.parseLong(ids[i]));
				}

				/*if (pro.get("gmcs") != null && Integer.parseInt(pro.get("gmcs").toString()) > 0) {
					HashMap<String, Object> whereMap = new HashMap<>();
					whereMap.put("pid", Integer.parseInt(pro.get("_id").toString()));
					whereMap.put("fromUserid", fromUserid);
					int ll = 0;
					List<DBObject> listdb = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap, null);
					for (DBObject dbObject : listdb) {
						DBObject order = baseDao.getMessage(PubConstants.WX_ORDERFORM,
								dbObject.get("orderid").toString());
						if (order != null && Integer.parseInt(order.get("state").toString()) != 1) {
							ll += Integer.parseInt(order.get("count").toString());
						}
					}
					if (ll >= Integer.parseInt(pro.get("gmcs").toString())) {
						// 购买次数已完
						params.put("state", 10);
						String json = JSONArray.fromObject(params).toString();
						Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
						return;
					}
				}*/
                String moneys="0";
				if (pro.get("price") != null) {
					String zfmoneys = zfmoney;
					System.out.println("赋值的钱数---》" + zfmoneys);
					// 商品价格
					zfmoney = BaseDecimal.multiplication(pro.get("price").toString(), nums[i]);
					  
//					moneys=BaseDecimal.multiplication(BaseDecimal.division(pro.get("price").toString(),ssj+"", 20), nums[i]);
//					money = BaseDecimal.add(money, moneys);
					
					System.out.println("当前商品的价格---》" + zfmoney);
					zfmoney = BaseDecimal.add(zfmoney, zfmoneys);
					System.out.println("当前商品的价格1---》" + zfmoney);
					if (pro.get("cost") != null) {
						String costs = cost;
						System.out.println("赋值的成本---》" + costs);
						// 成本
						cost = BaseDecimal.multiplication(pro.get("cost").toString(), nums[i]);
						System.out.println("当前商品的成本---》" + cost);
						cost = BaseDecimal.add(cost, costs);
						System.out.println("当前商品的成本1---》" + cost);
					}
					if (pro.get("goodstype") != null) {
						if (pro.get("goodstype").toString().equals("3")) {
							// 大众区
							public_money = BaseDecimal.add(public_money,
									BaseDecimal.multiplication(pro.get("price").toString(), nums[i]));

						} else if (pro.get("goodstype").toString().equals("4")) {
							// 特约区
							contri_money = BaseDecimal.add(contri_money,
									BaseDecimal.multiplication(pro.get("price").toString(), nums[i]));
						} else if (pro.get("goodstype").toString().equals("5")) {
							// 会员区
							members_money = BaseDecimal.add(members_money,
									BaseDecimal.multiplication(pro.get("price").toString(), nums[i]));
						}
					}
				} 
				
				// 生成信息
				if (pro != null) {
					List<Long>lscom=new ArrayList<>();
					if(entity.getComids()!=null) {
						lscom=entity.getComids();
					}
					if(!lscom.contains(Long.parseLong(pro.get("comid").toString()))) {
						lscom.add(Long.parseLong(pro.get("comid").toString()));
					} 
					entity.setComids(lscom);
					if (pro.get("jffh") != null) {
						jffh = jffh + Double.parseDouble(pro.get("jffh").toString());
					}
					
					OrderFormpro ord = new OrderFormpro();
					ord.set_id(mongoSequence.currval(PubConstants.SHOP_ODERFORMPRO));
					ord.setMoney(Double.parseDouble(moneys));  
					ord.setOrderid(orderno);
					ord.setCount(Integer.parseInt(nums[i]));
					ord.setPro(pro);
					ord.setPid(Long.parseLong(pro.get("_id").toString()));
					ord.setSpec(specs[i]);
					if(pro.get("kdprice")!=null){
						kd_money=BaseDecimal.add(kd_money, pro.get("kdprice").toString());
						ord.setKdprice(Double.parseDouble(pro.get("kdprice").toString()));
					}
					if (StringUtils.isNotEmpty(deptCode)) {
						ord.setDeptCode(Long.parseLong(deptCode));
					}
					
					if (pro.get("goodstype").toString().equals("3")) {
						// 大众区
						ord.setPublic_money(Double.parseDouble(BaseDecimal.multiplication(pro.get("price").toString(), nums[i])));
						 
					} else if (pro.get("goodstype").toString().equals("4")) {
						// 特约区
					    ord.setContri_money(Double.parseDouble(BaseDecimal.multiplication(pro.get("price").toString(), nums[i])));
					} else if (pro.get("goodstype").toString().equals("5")) {
						// 会员区
						ord.setMembers_money(Double.parseDouble(BaseDecimal.multiplication(pro.get("price").toString(), nums[i])));
					} 

					baseDao.insert(PubConstants.SHOP_ODERFORMPRO, ord);
				}

			}
		}
		entity.setMoney(Double.parseDouble(money));
		entity.setKdprice(Double.parseDouble(kd_money));
		
				
		entity.setEth_money(Double.parseDouble(BaseDecimal.division(zfmoney,ethp+"", 20)));	
		entity.setBtc_money(Double.parseDouble(BaseDecimal.division(zfmoney,btcp+"", 20)));	
		entity.setPpb_money(Double.parseDouble(BaseDecimal.division(zfmoney,ppp+"", 20)));	
		entity.setZfmoney(Double.parseDouble(zfmoney));
		entity.setCost(Double.parseDouble(cost));
		entity.setPublic_money(Double.parseDouble(public_money));
		entity.setContri_money(Double.parseDouble(contri_money));
		entity.setMembers_money(Double.parseDouble(members_money));
		entity.setProfit(Double.parseDouble(BaseDecimal.subtract(zfmoney, cost)));
		entity.setJffh(jffh);
		baseDao.insert(PubConstants.WX_ORDERFORM, entity);

		params.put("state", 0);
		params.put("orderno", orderno);
		String json = JSONArray.fromObject(params).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}

	/**
	 * 订单支付() 接口返回说明：1其他错误，2乐乐币不足，3库存不足，4商品已下架，5订单不存在，6订单编号错误
	 */
	public void OrderPayJf() {
		getLscode();
		HashMap<String, Object> map = new HashMap<>();
		map.put("state", 1);
		//订单编号
		String oid = Struts2Utils.getParameter("orid");
		String zflx = Struts2Utils.getParameter("zflx");
		System.out.println("===>"+oid);
		int qylx=0;
		if (StringUtils.isNotEmpty(oid)) {
			//根据订单id查询
			DBObject db = baseDao.getMessage(PubConstants.WX_ORDERFORM, oid);
			if (db != null && db.get("fromUserid").toString().equals(fromUserid)) {
				OrderForm entity = (OrderForm) UniObject.DBObjectToObject(db, OrderForm.class);

				HashMap<String, Object> whereMap = new HashMap<>();
				whereMap.put("orderid", oid);
				List<DBObject> list = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap, null);
				String  zfmoney="0";
				for (DBObject dbObject : list) {
					OrderFormpro orderFormpro = (OrderFormpro) UniObject.DBObjectToObject(dbObject, OrderFormpro.class);
					DBObject pro = baseDao.getMessage(PubConstants.DATA_PRODUCT,
							Long.parseLong(dbObject.get("pid").toString()));
					// 验证库存
					if (pro != null) {
						
						ProductInfo obj = (ProductInfo) UniObject.DBObjectToObject(pro, ProductInfo.class);
						zfmoney=BaseDecimal.add(zfmoney, BaseDecimal.add(BaseDecimal.multiplication(obj.getPrice()+"",orderFormpro.getCount()+""), obj.getKdprice()+""));
						qylx=obj.getGoodstype();
						//obj.setNum(obj.getNum() - obj.getGmnum() - orderFormpro.getCount());
						
						if (obj.getNum() - obj.getGmnum() - orderFormpro.getCount() <=0) {
							//库存不足 
						   map.put("state", 3);
						   String json = JSONArray.fromObject(map).toString();
						   Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
						   return;
						} else{
							obj.setGmnum(obj.getGmnum() + orderFormpro.getCount());//销售数量增加
							baseDao.insert(PubConstants.DATA_PRODUCT, obj);
						}
					} else {
						// 商品已下架
						map.put("state", 4);
					}
				}
				
				// 开始支付 
				if(StringUtils.isNotEmpty(zflx)){
					if(zflx.equals("3")){
						zfmoney=entity.getPpb_money()+"";
						if(entity.getKdprice() != 0){
							zfmoney=BaseDecimal.add(entity.getPpb_money()+"",BaseDecimal.division(entity.getKdprice()+"",wwzService.getPPBSprice()+"",10));
							
						}
					}
				}
				if (wwzService.deljfoid(zfmoney, fromUserid, "shop_jfdh", SysConfig.getProperty("custid"), 0, 1, 0,oid,"取消商品下单")) {
					 
					/*
					entity.set_id(oid);
					entity.setState(2);
					baseDao.insert(PubConstants.WX_ORDERFORM, entity);*/
					//支付成功并更新订单状态
					// 更新库存状态
					
					
					// 支付成功 
//				    String uskd=SysConfig.getProperty("uskd_dzq");
//					if(qylx==3){
//						uskd=SysConfig.getProperty("uskd_dzq");
//					}else if(qylx==4){
//						uskd=SysConfig.getProperty("uskd_tyq");
//					}else if(qylx==5){
//						uskd=SysConfig.getProperty("uskd_hyq");
//					}
//					
					
					
//					//提币到交易所
//					SortedMap<Object, Object> parameters = new TreeMap<Object, Object>(); 
//					parameters.put("eth", uskd);
//			    	parameters.put("num",zfmoney);
//			    	parameters.put("username","admin");
//			    	parameters.put("orderid",oid);
//			    	String sign = PayCommonUtil.createKey("UTF-8",uskd+zfmoney+"admin"+oid, SysConfig.getProperty("jyskey"));
//			    	parameters.put("key", sign); 
//		            String result =HttpClient.doHttpPost(SysConfig.getProperty("jysurl"),JSONObject.fromObject(parameters).toString());
//		            JSONObject obj=JSONObject.fromObject(result);
//		            if(obj.getString("code").equals("1000")) {
//		            	//提现成功； 
//		            }else {
//		            	//提现失败开始返回 
//		            }
					
					
					//结账到各个账号
					String  account=SysConfig.getProperty("dzq_account"); 
					String remark = "";
					if(qylx==3){ 
						account=SysConfig.getProperty("dzq_account");
						remark = "大众区消费";
						
				    }else if(qylx==4){ 
				    	account=SysConfig.getProperty("tyq_account");
				    	remark = "特约区消费";
				    }else if(qylx==5){ 
				    	account=SysConfig.getProperty("hyq_account");
				    	remark = "会员区消费";
				    }
					
					
					if(wwzService.addjfoid(zfmoney, account, "shop_jfsr", SysConfig.getProperty("custid"), 0, 1, 0,oid,remark)){
						System.out.println("支付一次----------"); 
						for (DBObject dbObject2 : list) {
							OrderFormpro orderFormpro = (OrderFormpro) UniObject.DBObjectToObject(dbObject2, OrderFormpro.class);
							DBObject pro = baseDao.getMessage(PubConstants.DATA_PRODUCT,
									Long.parseLong(dbObject2.get("pid").toString()));
							ProductInfo obj = (ProductInfo) UniObject.DBObjectToObject(pro, ProductInfo.class);
							if(StringUtils.isNotEmpty(zflx)){
								orderFormpro.setZflx(Integer.parseInt(zflx));
							}
							obj.setGmnum(obj.getGmnum() + entity.getCount());
							obj.setNum(obj.getNum() - entity.getCount());
							baseDao.insert(PubConstants.DATA_PRODUCT, obj);
							
							orderFormpro.set_id(Long.parseLong(dbObject2.get("_id").toString()));
							
							orderFormpro.setGoodstate(7);
							baseDao.insert(PubConstants.SHOP_ODERFORMPRO, orderFormpro);
						} 
						wwzService.update_user_prostore(String.valueOf(entity.getContri_money()),fromUserid,"order_cashback",null);
						entity.setZflx(Integer.parseInt(zflx));
						entity.setState(7);
						System.out.println("----------------------订单状态"+entity.getState());
						baseDao.insert(PubConstants.WX_ORDERFORM, entity);
						DBObject remind=baseDao.getMessage(PubConstants.SHOP_REMIND, SysConfig.getProperty("custid"));
						if(remind!=null){
							ShopRemind  tx=(ShopRemind) UniObject.DBObjectToObject(remind, ShopRemind.class);
							if(StringUtils.isNotEmpty(tx.getZftxTel())){ 
								String content="您有一条新订单，请尽快确认收款！";
								if(StringUtils.isNotEmpty(tx.getZftxContent())){
									content=tx.getZftxContent();
								}
								wwzService.sendSMS(tx.getZftxTel(),content);
							}
						}
						map.put("state", 0);
						
					}else{
						//支付失败返还积分
						//wwzService.addjfoid(zfmoney, fromUserid, "shop_jffh", SysConfig.getProperty("custid"), 0, 1, 0,oid);
						//系统未开通支付功能，请联系客服
						map.put("state", 8);
					}
					  
				} else {
					// 乐乐币不足
					map.put("state", 2);
				}

			} else {
				// 订单不存在
				map.put("state", 5);
			}
		} else {
			// 订单编号错误
			map.put("state", 6);
		}
		String json = JSONArray.fromObject(map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);

	}
	
	
	
	
	

	/**
	 * 微信H5支付
	 */
	 
	public void WXOrderPay() {
		
		HashMap<String, Object> map =new HashMap<>();
		try {
			
		String lscode=Struts2Utils.getParameter("lscode");
		//订单编号
		getLscode();
		String oid = Struts2Utils.getParameter("orid");
		String zflx = Struts2Utils.getParameter("zflx");
		String returnip = Struts2Utils.getParameter("returnip");
		
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		 
		DBObject wx = wwzService.getWxUser(fromUserid);
		//判断用户信心是否包含openid
		if (wx.get("_id").equals("notlogin")) {
			params.put("state", 3);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
		
		//获取订单信息
		DBObject db = baseDao.getMessage(PubConstants.WX_ORDERFORM, oid);
		
		//根据订单信息验证订单库存
		int qylx=0;
		HashMap<String, Object> whereMap = new HashMap<>();
		whereMap.put("orderid", oid);
		List<DBObject> list = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap, null);
		String  zfmoney="0";
		for (DBObject dbObject : list) {
			OrderFormpro orderFormpro = (OrderFormpro) UniObject.DBObjectToObject(dbObject, OrderFormpro.class);
			DBObject pro = baseDao.getMessage(PubConstants.DATA_PRODUCT,
					Long.parseLong(dbObject.get("pid").toString()));
			// 验证库存
			if (pro != null) {
				
				ProductInfo obj = (ProductInfo) UniObject.DBObjectToObject(pro, ProductInfo.class);
				zfmoney=BaseDecimal.add(zfmoney, BaseDecimal.add(BaseDecimal.multiplication(obj.getPrice()+"",orderFormpro.getCount()+""), obj.getKdprice()+""));
				qylx=obj.getGoodstype();
				//obj.setNum(obj.getNum() - obj.getGmnum() - orderFormpro.getCount());
				
				if (obj.getNum() - obj.getGmnum() - orderFormpro.getCount() <=0) {
					//库存不足 
				   map.put("state", 3);
				   String json = JSONArray.fromObject(map).toString();
				   Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
				   return;
				} else{
					obj.setGmnum(obj.getGmnum() + orderFormpro.getCount());//销售数量增加
					baseDao.insert(PubConstants.DATA_PRODUCT, obj);
				}
			} else {
				// 商品已下架
				map.put("state", 4);
				 String json = JSONArray.fromObject(map).toString();
				Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
				return;
			}
		}
		
		if (db != null && db.get("fromUserid").toString().equals(fromUserid)) {
			OrderForm entity = (OrderForm) UniObject.DBObjectToObject(db, OrderForm.class);
			
			 String appid = WXCommonUtil.AppID; //微信分配的公众账号ID（企业号corpid即为此appId）
		     String mch_id = WXCommonUtil.mchID; //微信支付分配的商户号
		     String appsecret=WXCommonUtil.AppSecret;
		     String partnerkey=WXCommonUtil.partnerkey;
		     double money =entity.getZfmoney();
		     
		     String device_info="WEB";         //终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" 
		    
		   
		    String prepayidString =WXCommonUtil.getRandomStringByLength(32);  //生成支付订单记录用
 	        String body="熊猫商城订单支付:"+money+"元"; // 商品或支付单简要描述
 	        
 	        //String attach=prepayidString; //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据  生成支付订单记录用
 	        String out_trade_no=TenpayUtil.getCurrTime()+TenpayUtil.buildRandom(6); //商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
 	        String  totalFee = (int)(money *  100)+"" ;  //单位分
 	        //String  spbill_create_ip =IpUtils.getClientIP(Struts2Utils.getRequest());
 	        
 	        if(returnip==null || returnip.length()<0) {
 	        	returnip = IpUtils.getinterip();
 	        }
 	         
 	        String  spbill_create_ip  =returnip;
 	        
 	        System.out.println("-----------000000000---------spbill_create_ip:" +spbill_create_ip);
 
 	        String time_start=TenpayUtil.getCurrTime(); //订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
 	        //这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
 	        String notify_url ="http://xmshop365.com/shop/shop!wxpaycallback.action";
 	        String trade_type = "MWEB"; //H5支付的交易类型为MWEB
 	        
 	        String nonce_str = WXCommonUtil.getRandomStringByLength(32);
 	        
 	        String openId  =wx.get("fromUser").toString();
 	        
 	        String  scene_info ="{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://www.xmshop365.com\",\"wap_name\": \"熊猫商城\"}}";
 	        
 	        
 	 
 	        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
 	        packageParams.put("appid", appid);  
 	        packageParams.put("mch_id", mch_id);  
 	        packageParams.put("nonce_str", nonce_str);  
 	        packageParams.put("body",  body);  
 	        packageParams.put("attach", oid);  
 	        packageParams.put("out_trade_no", out_trade_no); 
 	        //这里写的金额为1 分到时修改
 	        //packageParams.put("total_fee", "1");  
 	        packageParams.put("total_fee", totalFee);  
 	        
 	        packageParams.put("spbill_create_ip", spbill_create_ip);  
 	        packageParams.put("notify_url", notify_url);  
 	        packageParams.put("trade_type", trade_type);  
 	        packageParams.put("openid", openId); 
 	        packageParams.put("scene_info", scene_info);
 
 	        RequestHandler reqHandler = new RequestHandler(Struts2Utils.getRequest(), Struts2Utils.getResponse());
 	        reqHandler.init(appid, appsecret, partnerkey);
 	        String sign = reqHandler.createSign(packageParams); //MD5 加密
 	        packageParams.put("sign", sign);

			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			System.out.println("-------------------------"+requestXML);

			String result = CommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);

			Map<String, String> resmap = XMLUtil.doXMLParse(result);
			//统一下单反悔的结果：{result_code=SUCCESS, sign=902C0D9ECA35E00C3CDDF465727D3E96, mch_id=1509032041, prepay_id=wx24195416883317873a2aa8464267535485, return_msg=OK, appid=wx521568b6b7d32768, nonce_str=mAZOlsApjvj1de4l, return_code=SUCCESS, mweb_url=https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx24195416883317873a2aa8464267535485&package=3527240808, trade_type=MWEB}

			//{return_msg=缺少参数, return_code=FAIL}
			
			System.out.println("统一下单反悔的结果："+resmap.toString());
			String  result_code = resmap.get("result_code");
			if(result_code.equals("SUCCESS")) {
				
				//确认支付过后跳的地址,需要经过urlencode处理  成功后跳转到订单列表
				String  url  ="http://xmshop365.com/shop/shop!orderform.action?lscode="+lscode;
				String urlString = URLEncoder.encode(url, "GBK");
				String mweb_url = resmap.get("mweb_url")+"&redirect_url="+urlString;
				
				//生成微信支付订单
				
				WxPayDetail paydetail =new  WxPayDetail();
				long id = mongoSequence.currval(PubConstants.WXPAYDETAIL);
				paydetail.set_id(id);
				paydetail.setCreatedate(new Date());
				paydetail.setFromuserid(this.fromUserid);
				paydetail.setPaymoney(money);
				paydetail.setStatus(0);
				paydetail.setOrderid(oid);
				paydetail.setType(1);
				
				
				baseDao.insert(PubConstants.WXPAYDETAIL, paydetail);
				map.put("mweb_url", mweb_url);
				map.put("state", 0);
			}else {
				map.put("state", 5);
			} 
			
		}else {
			map.put("state", 5);
			 
		}
		 
		
		} catch (Exception e) {
			// TODO: handle exception
			map.put("status", 5);
			
		} 
		String json = JSONArray.fromObject(map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
		
		
	}
	
	
	
	/**
	 * 微信公众号支付
	 */
	 
	public void WXPay() {
		
		HashMap<String, Object> map =new HashMap<>();
		try {
			
		String lscode=Struts2Utils.getParameter("lscode");
		//订单编号
		getLscode();
		String oid = Struts2Utils.getParameter("orid");
		String zflx = Struts2Utils.getParameter("zflx");
		String returnip = Struts2Utils.getParameter("returnip");
		
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		 
		DBObject wx = wwzService.getWxUser(fromUserid);
		//判断用户信心是否包含openid
		if (wx.get("_id").equals("notlogin")) {
			params.put("state", 3);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
		
		//获取订单信息
		DBObject db = baseDao.getMessage(PubConstants.WX_ORDERFORM, oid);
		
		
		//根据订单信息验证订单库存
				int qylx=0;
				HashMap<String, Object> whereMap = new HashMap<>();
				whereMap.put("orderid", oid);
				List<DBObject> list = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap, null);
				String  zfmoney="0";
				for (DBObject dbObject : list) {
					OrderFormpro orderFormpro = (OrderFormpro) UniObject.DBObjectToObject(dbObject, OrderFormpro.class);
					DBObject pro = baseDao.getMessage(PubConstants.DATA_PRODUCT,
							Long.parseLong(dbObject.get("pid").toString()));
					// 验证库存
					if (pro != null) {
						
						ProductInfo obj = (ProductInfo) UniObject.DBObjectToObject(pro, ProductInfo.class);
						zfmoney=BaseDecimal.add(zfmoney, BaseDecimal.add(BaseDecimal.multiplication(obj.getPrice()+"",orderFormpro.getCount()+""), obj.getKdprice()+""));
						qylx=obj.getGoodstype();
						//obj.setNum(obj.getNum() - obj.getGmnum() - orderFormpro.getCount());
						
						if (obj.getNum() - obj.getGmnum() - orderFormpro.getCount() <=0) {
							//库存不足 
						   map.put("state", 3);
						   String json = JSONArray.fromObject(map).toString();
						   Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
						   return;
						} else{
							obj.setGmnum(obj.getGmnum() + orderFormpro.getCount());//销售数量增加
							baseDao.insert(PubConstants.DATA_PRODUCT, obj);
						}
					} else {
						// 商品已下架
						map.put("state", 4);
						 String json = JSONArray.fromObject(map).toString();
						Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
						return;
					}
				}
				
		if (db != null && db.get("fromUserid").toString().equals(fromUserid)) {
			OrderForm entity = (OrderForm) UniObject.DBObjectToObject(db, OrderForm.class);
			
			 String appid = WXCommonUtil.AppID; //微信分配的公众账号ID（企业号corpid即为此appId）
		     String mch_id = WXCommonUtil.mchID; //微信支付分配的商户号
		     String appsecret=WXCommonUtil.AppSecret;
		     String partnerkey=WXCommonUtil.partnerkey;
		     double money =entity.getZfmoney();
		     
		     String device_info="WEB";         //终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" 
		    
		   
		    String prepayidString =WXCommonUtil.getRandomStringByLength(32);  //生成支付订单记录用
 	        String body="熊猫商城订单支付:"+money+"元"; // 商品或支付单简要描述
 	        
 	        //String attach=prepayidString; //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据  生成支付订单记录用
 	        String out_trade_no=TenpayUtil.getCurrTime()+TenpayUtil.buildRandom(6); //商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
 	        String  totalFee = (int)(money *  100)+"" ;  //单位分
 	        //String  spbill_create_ip =IpUtils.getClientIP(Struts2Utils.getRequest());
 	        
 	        if(returnip==null || returnip.length()<0) {
 	        	returnip = IpUtils.getinterip();
 	        }
 	         
 	        String  spbill_create_ip  =returnip;
 	        
 	        System.out.println("-----------000000000---------spbill_create_ip:" +spbill_create_ip);
 
 	        String time_start=TenpayUtil.getCurrTime(); //订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
 	        //这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
 	        String notify_url ="http://xmshop365.com/shop/shop!wxpaycallback.action"; //正式环境回调地址
 	        // String notify_url ="http://wx.xmshop365.com/shop/shop!wxpaycallback.action"; //测试环境回调地址
 	      
 	        String trade_type = "JSAPI"; //公众号支付
 	        
 	        String nonce_str = WXCommonUtil.getRandomStringByLength(32);
 	        
 	        String openId  =wx.get("fromUser").toString();
 	        
 	        //String  scene_info ="{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://www.xmshop365.com\",\"wap_name\": \"熊猫商城\"}}";
 	        
 	        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
 	        packageParams.put("appid", appid);  
 	        packageParams.put("mch_id", mch_id);  
 	        packageParams.put("nonce_str", nonce_str);  
 	        packageParams.put("body",  body);  
 	        packageParams.put("attach", oid);  
 	        packageParams.put("out_trade_no", out_trade_no); 
 	        //这里写的金额为1 分到时修改
 	        //packageParams.put("total_fee", "1");  
 	        packageParams.put("total_fee", totalFee);  
 	        
 	        packageParams.put("spbill_create_ip", spbill_create_ip);  
 	        packageParams.put("notify_url", notify_url);  
 	        packageParams.put("trade_type", trade_type);  
 	        packageParams.put("openid", openId); 
 	        //packageParams.put("scene_info", scene_info);
 
 	        RequestHandler reqHandler = new RequestHandler(Struts2Utils.getRequest(), Struts2Utils.getResponse());
 	        reqHandler.init(appid, appsecret, partnerkey);
 	        String sign = reqHandler.createSign(packageParams); //MD5 加密
 	        packageParams.put("sign", sign);

			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			System.out.println("-------------------------"+requestXML);

			String result = CommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);

			Map<String, String> resmap = XMLUtil.doXMLParse(result);
			//统一下单反悔的结果：{result_code=SUCCESS, sign=902C0D9ECA35E00C3CDDF465727D3E96, mch_id=1509032041, prepay_id=wx24195416883317873a2aa8464267535485, return_msg=OK, appid=wx521568b6b7d32768, nonce_str=mAZOlsApjvj1de4l, return_code=SUCCESS, mweb_url=https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx24195416883317873a2aa8464267535485&package=3527240808, trade_type=MWEB}

			//{return_msg=缺少参数, return_code=FAIL}
			
			System.out.println("统一下单反悔的结果："+resmap.toString());
			String  result_code = resmap.get("result_code");
			if(result_code.equals("SUCCESS")) {
				
				 
				//生成微信支付订单
				
				WxPayDetail paydetail =new  WxPayDetail();
				long id = mongoSequence.currval(PubConstants.WXPAYDETAIL);
				paydetail.set_id(id);
				paydetail.setCreatedate(new Date());
				paydetail.setFromuserid(this.fromUserid);
				paydetail.setPaymoney(money);
				paydetail.setStatus(0);
				paydetail.setOrderid(oid);
				paydetail.setOuttradeno(out_trade_no);
				paydetail.setOpenid(openId);
				paydetail.setType(1);
				
				
				baseDao.insert(PubConstants.WXPAYDETAIL, paydetail);
			 
				map.put("state", 0);
				
				String prepay_id =resmap.get("prepay_id");
				
				
			    SortedMap<Object, Object> finalpackage = new TreeMap<Object, Object>();
    	        String appid2 = appid;
    	        String timestamp = Sha1Util.getTimeStamp();
    	        String nonceStr2 = nonce_str;
    	        String prepay_id2 = "prepay_id="+prepay_id;
    	        String packages = prepay_id2;
    	        finalpackage.put("appId", appid2);  
    	        finalpackage.put("timeStamp", timestamp);  
    	        finalpackage.put("nonceStr", nonceStr2);  
    	        finalpackage.put("package", packages);  
    	        finalpackage.put("signType", "MD5");
    	        String finalsign = reqHandler.createSign(finalpackage);
    	        
    	        map.put("appId", appid2); //公众号名称，由商户传入     
    	        map.put("timeStamp", timestamp);  //时间戳，自1970年以来的秒数     
    	        map.put("nonceStr", nonceStr2); //随机串  
    	        map.put("packages", packages);
    	        map.put("signType", "MD5");  //微信签名方式：     
    	        map.put("paySign", finalsign);  //微信签名 
	    	        
			}else {
				map.put("state", 5);
			} 
			
		}else {
			map.put("state", 5);
			 
		}
		 
		
		} catch (Exception e) {
			// TODO: handle exception
			map.put("status", 5);
			
		} 
		String json = JSONArray.fromObject(map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
		
		
	}
	
	
	
	
	//微信支付回调  - 熊猫商城订单
	public void  wxpaycallback() throws JDOMException, IOException {
		String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[充值失败]]></return_msg>" + "</xml> ";;
		
		try {
		
		System.out.println("----------------------------进入支付回调=====================");
		String xmlMsg = readData(Struts2Utils.getRequest());
		System.out.println("pay notice---------"+xmlMsg);
		Map params = XMLUtil.doXMLParse(xmlMsg);
		
		// 账号信息
		if(params!=null) {
		//System.out.println("---------支付回调参数列表："+params.toString()); 
		
		/////////////////////////////以下是附加参数///////////////////////////////////
		
		String attach      = params.get("attach")+""; // ---调试注释
		//String attach ="2018082770582220";
		
		System.out.println(" ----- attach ---- :"+attach);
  
		
 
			// 过滤空 设置 TreeMap
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				String parameter = (String) it.next();
				String parameterValue = params.get(parameter)+"";
				String v = "";
				if (null != parameterValue) {
					v = parameterValue.trim();
				}
				//System.out.println("value==============="+v);
				packageParams.put(parameter, v);
			}
			 
			
			// ------------------------------
			// 处理业务开始
			// ------------------------------
			OrderForm entity =null;
			DBObject db ,db1 =null;
			WxPayDetail payDetail =null;
			String result_code =String.valueOf(packageParams.get("result_code"));  //调试注释 ---
			String transaction_id =String.valueOf(packageParams.get("transaction_id"));  // 微信支付单号
			
			
			//String result_code ="SUCCESS"; 
			if ("SUCCESS".equals(result_code)) {
				// 这里是支付成功
				
				//查询订单信息
				if(attach!="") {
					//查询微信支付的记录
					
				      HashMap<String, Object>whereMap=new HashMap<>();
					  whereMap.put("orderid", attach);
					  db = baseDao.getMessage(PubConstants.WX_ORDERFORM, attach);
					  db1 =baseDao.getMessage(PubConstants.WXPAYDETAIL, whereMap); 
						//根据订单商品的支付信息
						 
						HashMap<String, Object> whereMap1 = new HashMap<>();
						whereMap1.put("orderid", attach);
						List<DBObject> list = baseDao.getList(PubConstants.SHOP_ODERFORMPRO, whereMap1, null);
						for (DBObject dbObject : list) {
							OrderFormpro orderFormpro = (OrderFormpro) UniObject.DBObjectToObject(dbObject, OrderFormpro.class);
							if(orderFormpro!=null && "SUCCESS".equals(result_code) ) {
								orderFormpro.set_id(dbObject.get("_id"));
								orderFormpro.setGoodstate(2);
								baseDao.insert(PubConstants.SHOP_ODERFORMPRO, orderFormpro);
							}else {
								orderFormpro.setGoodstate(99);
								orderFormpro.set_id(dbObject.get("_id"));
								baseDao.insert(PubConstants.SHOP_ODERFORMPRO, orderFormpro);
								
							}
							
						}
					  if (db != null  ) {
						    entity = (OrderForm) UniObject.DBObjectToObject(db, OrderForm.class);
						    entity.setState(2);
						    entity.setZflx(4);
						    
						    
					  }
					  if(db1!=null) {
						    payDetail =(WxPayDetail) UniObject.DBObjectToObject(db1, WxPayDetail.class);
						    payDetail.setStatus(1);
						    payDetail.setTransactionid(transaction_id);
						    
						    //处理业务逻辑，增加用户消费累积、
						    String type ="wx_order";
						    boolean res = wwzService.update_user_prostore(String.valueOf(payDetail.getPaymoney()),payDetail.getFromuserid(),type,"");
					  }
					  
					  System.out.println("---------------------支付回调支付成功============"); 
					
				}
				 
			 
                ////////// 执行自己的业务逻辑////////////////
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
				
			} else {
				if(entity!=null) {
					entity.setState(99); //99订单支付失败
				}
				if(payDetail!=null) {
					payDetail.setStatus(2);
				}
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[充值失败]]></return_msg>" + "</xml> ";
			}
			//更新订单状态到数据库
			if(entity!=null) {
				baseDao.insert(PubConstants.WX_ORDERFORM, entity);
			}
			if(payDetail!=null) {
				baseDao.insert(PubConstants.WXPAYDETAIL, payDetail);
			}
			
		   } 
		} catch (Exception e) {
			 
			
		}finally {
			BufferedOutputStream out = new BufferedOutputStream(Struts2Utils.getResponse().getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		}
		
 
	}
	
	
	
	/**
	 * 我的消费充值
	 */
	public String recharge(){
		getLscode();
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("lscode", lscode);
		
		return "recharge";
	}
	/**
	 * 我的消费充值记录
	 */
	public String recdetail(){
		getLscode();
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("lscode", lscode);
		
		
		HashMap<String, Object> sortMap =new HashMap<String, Object>();
		HashMap<String, Object> whereMap =new HashMap<String, Object>();
		HashMap<String, Object> backMap =new HashMap<String, Object>();

		sortMap.put("createdate", -1); 
	  
		whereMap.put("fromuserid",  fromUserid); 
		whereMap.put("type",  2); 
		  
		List<DBObject> list=baseDao.getList(PubConstants.WXPAYDETAIL,whereMap,fypage,15,sortMap,backMap);
		fycount=baseDao.getCount(PubConstants.WXPAYDETAIL,whereMap);
		Struts2Utils.getRequest().setAttribute("paylist", list); 
		Struts2Utils.getRequest().setAttribute("fycount", fycount);
		return "recdetail";
	}
	
	
	
	/**
	 * 微信公众号支付  ---矿机商城 个人消费充值
	 */
	 
	public void KJWXPay() {
		
		HashMap<String, Object> map =new HashMap<>();
		try {
			
		String lscode=Struts2Utils.getParameter("lscode");
		//订单编号
		getLscode();
		String paymoney = Struts2Utils.getParameter("paymoney"); 
		String returnip = Struts2Utils.getParameter("returnip");
		
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();
		 
		DBObject wx = wwzService.getWxUser(fromUserid);
		//判断用户信心是否包含openid
		if (wx.get("_id").equals("notlogin")) {
			params.put("state", 3);
			String json = JSONArray.fromObject(params).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
			return;
		}
			
			 String appid = WXCommonUtil.AppID; //微信分配的公众账号ID（企业号corpid即为此appId）
		     String mch_id = WXCommonUtil.mchID; //微信支付分配的商户号
		     String appsecret=WXCommonUtil.AppSecret;
		     String partnerkey=WXCommonUtil.partnerkey;
		     double money = Double.valueOf(paymoney);
		     
		    String device_info="WEB";         //终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" 
		    String prepayidString =WXCommonUtil.getRandomStringByLength(32);  //生成支付订单记录用
 	        String body="熊猫矿机商城订单支付:"+money+"元"; // 商品或支付单简要描述
 	        
 	        //String attach=prepayidString; //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据  生成支付订单记录用
 	        String out_trade_no=TenpayUtil.getCurrTime()+TenpayUtil.buildRandom(6); //商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
 	        String  totalFee = (int)(money *  100)+"" ;  //单位分
 	        //String  spbill_create_ip =IpUtils.getClientIP(Struts2Utils.getRequest());
 	        
 	        if(returnip==null || returnip.length()<0) {
 	        	returnip = IpUtils.getinterip();
 	        }
 	        String  spbill_create_ip  =returnip;
 	        System.out.println("-----------000000000---------spbill_create_ip:" +spbill_create_ip);
 	        String time_start=TenpayUtil.getCurrTime(); //订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
 	        //这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
 	        String notify_url ="http://xmshop365.com/shop/shop!kjwxpaycallback.action"; //正式环境回调地址
 	        //String notify_url ="http://wx.xmshop365.com/shop/shop!kjwxpaycallback.action"; //测试环境回调地址
 	        String trade_type = "JSAPI"; //公众号支付
 	        String nonce_str = WXCommonUtil.getRandomStringByLength(32);
 	        String openId  =wx.get("fromUser").toString();
 	        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
 	        long id = mongoSequence.currval(PubConstants.WXPAYDETAIL);
 	        packageParams.put("appid", appid);  
 	        packageParams.put("mch_id", mch_id);  
 	        packageParams.put("nonce_str", nonce_str);  
 	        packageParams.put("body",  body);  
 	        packageParams.put("attach", id+"");  
 	        packageParams.put("out_trade_no", out_trade_no); 
 	        //这里写的金额为1 分到时修改
 	        //packageParams.put("total_fee", "1");  
 	        packageParams.put("total_fee", totalFee);  
 	        packageParams.put("spbill_create_ip", spbill_create_ip);  
 	        packageParams.put("notify_url", notify_url);  
 	        packageParams.put("trade_type", trade_type);  
 	        packageParams.put("openid", openId); 
 	        //packageParams.put("scene_info", scene_info);
 
 	        RequestHandler reqHandler = new RequestHandler(Struts2Utils.getRequest(), Struts2Utils.getResponse());
 	        reqHandler.init(appid, appsecret, partnerkey);
 	        String sign = reqHandler.createSign(packageParams); //MD5 加密
 	        packageParams.put("sign", sign);

			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			System.out.println("-------------------------"+requestXML);

			String result = CommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);

			Map<String, String> resmap = XMLUtil.doXMLParse(result);
			//统一下单反悔的结果：{result_code=SUCCESS, sign=902C0D9ECA35E00C3CDDF465727D3E96, mch_id=1509032041, prepay_id=wx24195416883317873a2aa8464267535485, return_msg=OK, appid=wx521568b6b7d32768, nonce_str=mAZOlsApjvj1de4l, return_code=SUCCESS, mweb_url=https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx24195416883317873a2aa8464267535485&package=3527240808, trade_type=MWEB}

			//{return_msg=缺少参数, return_code=FAIL}
			
			System.out.println("统一下单反悔的结果："+resmap.toString());
			String  result_code = resmap.get("result_code");
			if(result_code.equals("SUCCESS")) {
				
				//生成微信支付订单
				WxPayDetail paydetail =new  WxPayDetail();
				paydetail.set_id(id);
				paydetail.setCreatedate(new Date());
				paydetail.setFromuserid(this.fromUserid);
				paydetail.setPaymoney(money);
				paydetail.setStatus(0); 
				paydetail.setOuttradeno(out_trade_no);
				paydetail.setOpenid(openId);
				paydetail.setType(2);
				
				
				baseDao.insert(PubConstants.WXPAYDETAIL, paydetail);
			 
				map.put("state", 0);
				
				String prepay_id =resmap.get("prepay_id");
				
				
			    SortedMap<Object, Object> finalpackage = new TreeMap<Object, Object>();
    	        String appid2 = appid;
    	        String timestamp = Sha1Util.getTimeStamp();
    	        String nonceStr2 = nonce_str;
    	        String prepay_id2 = "prepay_id="+prepay_id;
    	        String packages = prepay_id2;
    	        finalpackage.put("appId", appid2);  
    	        finalpackage.put("timeStamp", timestamp);  
    	        finalpackage.put("nonceStr", nonceStr2);  
    	        finalpackage.put("package", packages);  
    	        finalpackage.put("signType", "MD5");
    	        String finalsign = reqHandler.createSign(finalpackage);
    	        
    	        map.put("appId", appid2); //公众号名称，由商户传入     
    	        map.put("timeStamp", timestamp);  //时间戳，自1970年以来的秒数     
    	        map.put("nonceStr", nonceStr2); //随机串  
    	        map.put("packages", packages);
    	        map.put("signType", "MD5");  //微信签名方式：     
    	        map.put("paySign", finalsign);  //微信签名 
	    	        
			}else {
				map.put("state", 5);
			} 
			
		 
		 
		
		} catch (Exception e) {
			// TODO: handle exception
			map.put("status", 5);
			
		} 
		String json = JSONArray.fromObject(map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
		
		
	}
	
	
	//微信支付回调  - 熊猫商城订单
		public void  kjwxpaycallback() throws JDOMException, IOException {
			String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[充值失败]]></return_msg>" + "</xml> ";;
			try {
			System.out.println("----------------------------进入支付回调=====================");
			String xmlMsg = readData(Struts2Utils.getRequest());
			System.out.println("pay notice---------"+xmlMsg);
			Map params = XMLUtil.doXMLParse(xmlMsg);
			// 账号信息
			if(params!=null) {
 
			//String attach ="118";
			
			//System.out.println(" ----- attach ---- :"+attach);
				// 过滤空 设置 TreeMap
				SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
				Iterator it = params.keySet().iterator();
				while (it.hasNext()) {
					String parameter = (String) it.next();
					String parameterValue = params.get(parameter)+"";
					String v = "";
					if (null != parameterValue) {
						v = parameterValue.trim();
					}
					//System.out.println("value==============="+v);
					packageParams.put(parameter, v);
				}
				// ------------------------------
				// 处理业务开始
				// ------------------------------ 
				DBObject db   =null;
				WxPayDetail payDetail =null;
				String result_code =String.valueOf(packageParams.get("result_code"));  //调试注释 ---
				String transaction_id =String.valueOf(packageParams.get("transaction_id"));  // 微信支付单号
				String attach =String.valueOf(packageParams.get("attach"));  //  携带的订单编号
				System.out.println(" ----- attach ---- :"+attach);
				//String transaction_id ="000000";
				db =baseDao.getMessage(PubConstants.WXPAYDETAIL, Integer.parseInt(attach.trim())); 
				payDetail =(WxPayDetail) UniObject.DBObjectToObject(db, WxPayDetail.class);
				//String result_code ="SUCCESS"; 
				if ("SUCCESS".equals(result_code)) {
					// 这里是支付成功
					//查询订单信息
					if(attach!="") {
						//查询微信支付的记录
						 
						  if(db!=null) {
							  	//更新订单状态到数据库
							    payDetail.setStatus(1);
							    payDetail.setTransactionid(transaction_id);
							    baseDao.insert(PubConstants.WXPAYDETAIL, payDetail);
							    //处理业务逻辑，增加用户消费累积、
							    String type ="wx_recharge";
							    boolean res = wwzService.update_user_prostore(String.valueOf(payDetail.getPaymoney()),payDetail.getFromuserid(),type,"");
						  }else {
							    System.out.println("--------------------- 根据微信参数获取信息有误 无法充值 ============"); 
						  }
						  System.out.println("---------------------支付回调支付成功============"); 
						
					}
	                ////////// 执行自己的业务逻辑////////////////
					// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
					resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
					
				} else {
				 
					if(payDetail!=null) {
						payDetail.setStatus(2);
						baseDao.insert(PubConstants.WXPAYDETAIL, payDetail);
					}
				}
			   } 
			} catch (Exception e) {
				System.out.println("-----微信充值返回失败 ----");
			}finally {
				BufferedOutputStream out = new BufferedOutputStream(Struts2Utils.getResponse().getOutputStream());
				out.write(resXml.getBytes());
				out.flush();
				out.close();
			}
		}
		
	
	 

	public static String readData(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			StringBuilder result = new StringBuilder();
			br = request.getReader();
			for (String line; (line=br.readLine())!=null;) {
				if (result.length() > 0) {
					result.append("\n");
				}
				result.append(line);
			}
	
			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (br != null)
				try {br.close();} catch (IOException e) {e.printStackTrace();}
		}
	}
		
	
	
	

	public String productlist() throws Exception {
		getLscode();
		String goodstype = Struts2Utils.getParameter("goodstype");
		String ptitle = Struts2Utils.getParameter("ptitle");
		Struts2Utils.getRequest().setAttribute("custid", custid);
		Struts2Utils.getRequest().setAttribute("lscode", lscode);
		Struts2Utils.getRequest().setAttribute("goodstype", goodstype);
		return "productlist";
	}
	 

	/**
	 * 交易测试 笑我一世沉沦: 传入参数的格式 {"data":
	 * 
	 * {"eth":"0xed188d6257ab4adbaf266cd5be3b563e11696882","num":1,"username":
	 * 
	 * "admin","key":"82bd7f241cc6d151de1c0aa26713c60e","orderid":"A1245649846
	 * 
	 * 57846"}} 笑我一世沉沦: key是eth+num+username+orderid+密钥
	 */
	public void testjy() {//2018070417180011

		SortedMap<Object, Object> parameters = new

		TreeMap<Object, Object>();
		parameters.put("eth", "0x063d0aa3160fdcc85214761e2bfaa43e3e82cf5d");
		parameters.put("num", 1);
		parameters.put("username","admin");
		parameters.put("orderid", Struts2Utils.getParameter("orderid"));
	
		String sign = PayCommonUtil.createKey("UTF-8","0x063d0aa3160fdcc85214761e2bfaa43e3e82cf5d1admin"+Struts2Utils.getParameter("orderid"), "uskdpro6623");
		parameters.put("key", sign);
		HashMap<String,Object>map=new HashMap<>();
		map.put("data", parameters);

		
		String result =HttpClient.doHttpPost("http://test.zzfzf.com/home/api/api",JSONObject.fromObject(parameters).toString());
		System.out.println(result);

	}

	/**
	 * 交易测试 笑我一世沉沦: 传入参数的格式 {"data":
	 * 
	 * {"eth":"0xed188d6257ab4adbaf266cd5be3b563e11696882","num":1,"username":
	 * 
	 * "admin","key":"82bd7f241cc6d151de1c0aa26713c60e","orderid":"A1245649846
	 * 
	 * 57846"}} 笑我一世沉沦: key是eth+num+username+orderid+密钥
	 * 
	 * @throws IOException
	 */
	public void testjr() throws IOException {

		InputStream input = Struts2Utils.getRequest

		().getInputStream();
		BufferedReader br = new BufferedReader(new

		InputStreamReader(input, "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes(), "utf-8");
			sb.append(line);
		}
		JSONObject jsonObject = JSONObject.fromObject

		(URLDecoder.decode(sb.toString(), "utf-8"));

		SortedMap<Object, Object> parameters = new

		TreeMap<Object, Object>();
		parameters.put("eth", jsonObject.get("eth"));
		parameters.put("num", jsonObject.get("num"));
		parameters.put("username", jsonObject.get("username"));
		parameters.put("orderid", jsonObject.get("orderid"));
		// 验证签名
		String sign = PayCommonUtil.createSign("UTF-8",

				parameters, "uskdpro6623");
		System.out.println(sign);
		System.out.println(jsonObject.get("key"));
		if (sign.equals(jsonObject.get("key"))) {
			Struts2Utils.getResponse().getWriter().print

			(URLEncoder.encode("ok", "utf-8"));
		} else {
			Struts2Utils.getResponse().getWriter().print

			(URLEncoder.encode("error", "utf-8"));
		}

	}
	public void   getJf() {
		HashMap<String, Object>map=new HashMap<>();
		map.put("state", 1);
		getLscode();
		HashMap<String, Object>whereMap=new HashMap<>();
		whereMap.put("fromUserid",fromUserid);
		DBObject db = baseDao.getMessage(PubConstants.SUC_INTEGRALRECORD, whereMap);
		if(db!=null) {
			map.put("data", db);
			map.put("state", 0);
		}
		String json = JSONArray.fromObject(map).toString();
		Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
		
	}
	
	public void   changedf() {
		String custid = Struts2Utils.getParameter("custid");
		String shopid = Struts2Utils.getParameter("shopid");
		DBObject db = baseDao.getMessage(PubConstants.SHOP_SHOPMB, Long.parseLong(shopid));
		
		if(db!=null) {
			ShopMb info = (ShopMb) UniObject.DBObjectToObject(db, ShopMb.class);
			info.setCustid(custid);
			baseDao.insert(PubConstants.SHOP_SHOPMB, info);
		}	
	}

	
	public String backreord() throws Exception{
		getLscode();
		HashMap<String, Object> sortMap =new HashMap<String, Object>();
		HashMap<String, Object> whereMap =new HashMap<String, Object>();
		String type = Struts2Utils.getParameter("sel_type");
		String vip_no = Struts2Utils.getParameter("vip_no");
		if (StringUtils.isNotEmpty(type)) {
			whereMap.put("type", type);
			Struts2Utils.getRequest().setAttribute("type", type);
		}
		if (StringUtils.isNotEmpty(vip_no)) {
			whereMap.put("no", vip_no);
			Struts2Utils.getRequest().setAttribute("vip_no", vip_no);
		}
		if (Struts2Utils.getParameter("fypage") != null) {
			fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		sortMap.put("createdate", -1);
		BigDecimal sum = new BigDecimal(0.00);
		BasicDBList dblist = new BasicDBList();
		dblist.add(new BasicDBObject("agentLevel", 1));
		dblist.add(new BasicDBObject("agentLevel", 2));
		dblist.add(new BasicDBObject("agentLevel", 3));
		whereMap.put("$or", dblist);
		List<DBObject> userList = baseDao.getList(PubConstants.USER_INFO, whereMap,fypage,10, sortMap);
		fycount=baseDao.getCount(PubConstants.USER_INFO,whereMap);
		whereMap.clear();
		for (DBObject dbObject : userList) {
			double userSum = 0;//总矿机产出钱数
			/**
			 * 开通账户矿机产出
			 */
			whereMap.put("fromUserid", dbObject.get("_id").toString());
			whereMap.put("type", "ps_account");
			DBObject prostore = baseDao.getMessage(PubConstants.INTEGRAL_PROSTORE, whereMap);
			int level =Integer.parseInt(dbObject.get("agentLevel").toString());
			if(null != prostore) {
				int time = Integer.parseInt(prostore.get("time").toString());
				whereMap.put("fid", new BasicDBObject("$ne",null));
				List<DBObject> infoList = baseDao.getList(PubConstants.INTEGRAL_INFO, whereMap, sortMap);//矿机产币明细
				if(null != infoList && infoList.size() > 0) {
					int day = infoList.size();
					switch (level) {
					case 1:
						userSum = 4500000/time*day;
						break;
					case 2:
						userSum = 1500000/time*day;
						break;
					case 3:
						userSum = 450000/time*day;
						break;
					}
				}
			}
			whereMap.clear();
			/**
			 * 商城收益  消费兑换  业绩赠送  矿机产出
			 */
			whereMap.put("fromUserid", dbObject.get("_id").toString());
			BasicDBList typelist = new BasicDBList();
			typelist.add(new BasicDBObject("type", "shop_bmzt"));//商城收益
			typelist.add(new BasicDBObject("type", "shop_exchange"));//消费兑换
			typelist.add(new BasicDBObject("type", "recommend_give"));//业绩赠送
			typelist.add(new BasicDBObject("type", "ps_recovery"));//回本后矿机
			
			whereMap.put("$or", typelist);
			List<DBObject> prostoreList = baseDao.getList(PubConstants.INTEGRAL_PROSTORE, whereMap,sortMap);
			if(null != prostoreList && prostoreList.size() > 0) {
				whereMap.put("fid", new BasicDBObject("$ne",null));
				for (DBObject object : prostoreList) {//矿机
					List<DBObject> infoList = baseDao.getList(PubConstants.INTEGRAL_INFO, whereMap, sortMap);//矿机产币明细
					if(null != infoList && infoList.size() > 0) {
						int time = Integer.parseInt(object.get("time").toString());
						double monery = Double.parseDouble(object.get("monery").toString());
						int day = infoList.size();
						userSum += monery/time*day;
					}
				}
			}
			whereMap.clear();
			sortMap.clear();
			/**
			 * 回本记录表数据
			 */
			whereMap.put("toUserVipno", dbObject.get("no").toString());
			sortMap.put("createDate", -1);
			List<DBObject> backreordList = baseDao.getList(PubConstants.INTEGRAL_BACKREORD, whereMap, sortMap);
			if(null != backreordList && backreordList.size() > 0) {
				for (DBObject backreord : backreordList) {
						userSum += Double.parseDouble(backreord.get("value").toString());
				}
			}
			dbObject.put("remark", userSum+"");
			sum = sum.add(new BigDecimal(userSum));
			whereMap.clear();
			sortMap.clear();
		}
		Struts2Utils.getRequest().setAttribute("lists", userList); 
		Struts2Utils.getRequest().setAttribute("sum", sum); 
		Struts2Utils.getRequest().setAttribute("fycount", fycount);
		return "backreord";
		
	}
	
	public  void  wxshare() {
		  HashMap<String, Object>map=new HashMap<>();
		  getLscode();  
		  HashMap<String, Object>whereMap=new HashMap<String, Object>();
		  String url = Struts2Utils.getParameter("url");
		  System.out.println("get -----url："+url);
		  Struts2Utils.getRequest().setAttribute("custid",custid );
		  //WxToken token=GetAllFunc.wxtoken.get(custid);
		  WxToken token = null;
			if (StringUtils.isNotEmpty(custid)) {
				token = GetAllFunc.wxtoken.get(custid);
			} else {
				token = GetAllFunc.wxtoken.get(SysConfig.getProperty("custid"));
			}
			if (token.getSqlx() > 0) {
				token = GetAllFunc.wxtoken.get(wwzService.getparentcustid(custid));
			}
		    map.put("token", WeiXinUtil.getwxSignature(token,Struts2Utils.getRequest() ));
		    String json = JSONArray.fromObject(map).toString();
			Struts2Utils.renderJson(json.substring(1, json.length() - 1), new String[0]);
	}
	
	/**
	 * 矿机回本记录明细
	 * @return
	 * @throws Exception
	 */
	public String backreordInfo() throws Exception{
		getLscode();
		HashMap<String, Object> sortMap =new HashMap<String, Object>();
		HashMap<String, Object> whereMap =new HashMap<String, Object>();
		String userId = Struts2Utils.getParameter("id");
		String page = Struts2Utils.getParameter("fypage");
		System.out.println(page);
		if (Struts2Utils.getParameter("fypage") != null) {
			fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		
		/**
		 * 矿机产出
		 */
		BasicDBList typelist = new BasicDBList();
		typelist.add(new BasicDBObject("type", "shop_bmzt"));//商城收益
		typelist.add(new BasicDBObject("type", "shop_exchange"));//消费兑换
		typelist.add(new BasicDBObject("type", "recommend_give"));//业绩赠送
		typelist.add(new BasicDBObject("type", "ps_account"));//开通账户
		typelist.add(new BasicDBObject("type", "ps_recovery"));//回本后矿机
		whereMap.put("$or", typelist);
		whereMap.put("fid", new BasicDBObject("$ne",null));
		sortMap.put("createdate", -1);
		whereMap.put("fromUserid", userId);
		List<DBObject> infoList = baseDao.getList(PubConstants.INTEGRAL_INFO, whereMap,fypage,10, sortMap);//矿机产币明细
		fycount=baseDao.getCount(PubConstants.INTEGRAL_INFO,whereMap);
		for (DBObject dbObject : infoList) {
			dbObject.put("custid", wwzService.getVipNo(dbObject.get("custid").toString()));
			dbObject.put("fromUser", wwzService.getVipNo(dbObject.get("fromUserid").toString()));
		}
		/**
		 * 回本记录表数据
		 *//*
		whereMap.clear();
		whereMap.put("toUserVipno", wwzService.getVipNo(userId));
		sortMap.put("createDate", -1);
		List<DBObject> backreordList = baseDao.getList(PubConstants.INTEGRAL_BACKREORD, whereMap,fypage,10, sortMap);
		if(null != backreordList && backreordList.size() > 0) {
			for (DBObject backreord : backreordList) {
				backreord.put("custid", backreord.get("fromVipno").toString());
				backreord.put("fromUser",backreord.get("toUserVipno").toString());
				backreord.put("remark", backreord.get("source").toString()+" "+backreord.get("proportion").toString());
				infoList.add(backreord);
			}
		}*/
		whereMap.clear();
		sortMap.clear();
		Struts2Utils.getRequest().setAttribute("id", userId); 
		Struts2Utils.getRequest().setAttribute("lists", infoList); 
		Struts2Utils.getRequest().setAttribute("fycount", fycount);
		return "backreordInfo";
	}
	/**
	 * 消费、新增下级代理回本明细
	 * @return
	 * @throws Exception
	 */
	public String backreordXfInfo() throws Exception{
		getLscode();
		HashMap<String, Object> sortMap =new HashMap<String, Object>();
		HashMap<String, Object> whereMap =new HashMap<String, Object>();
		String userId = Struts2Utils.getParameter("id");
		String page = Struts2Utils.getParameter("fypage");
		System.out.println(page);
		if (Struts2Utils.getParameter("fypage") != null) {
			fypage = Integer.parseInt(Struts2Utils.getParameter("fypage"));
		}
		/**
		 * 回本记录表数据
		 */
		whereMap.put("toUserVipno", wwzService.getVipNo(userId));
		sortMap.put("createDate", -1);
		List<DBObject> backreordList = baseDao.getList(PubConstants.INTEGRAL_BACKREORD, whereMap,fypage,10, sortMap);
		fycount=baseDao.getCount(PubConstants.INTEGRAL_BACKREORD,whereMap);
		if(null != backreordList && backreordList.size() > 0) {
			for (DBObject backreord : backreordList) {
				backreord.put("remark", backreord.get("source").toString()+" "+backreord.get("proportion").toString());
			}
		}
		whereMap.clear();
		sortMap.clear();
		Struts2Utils.getRequest().setAttribute("id", userId); 
		Struts2Utils.getRequest().setAttribute("lists", backreordList); 
		Struts2Utils.getRequest().setAttribute("fycount", fycount);
		return "backreordXfInfo";
	}
}