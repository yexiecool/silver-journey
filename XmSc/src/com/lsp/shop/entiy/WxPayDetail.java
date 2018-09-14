package com.lsp.shop.entiy;

import java.util.Date;

import com.mongodb.ReflectionDBObject;

/***
 * 微信支付记录
 * @author lsp
 *
 */
public class WxPayDetail  extends ReflectionDBObject{
	//订单编号
	private String orderid;
    //来自那个用户
	private String  fromuserid;
	//支付状态 0-已支付  1-支付成功 2-支付失败
	private int  status;
	//支付金额
	private Double paymoney ;  
	//创建时间
	private Date createdate ;
	//微信商户订单号
	private String outtradeno;
	//下单的人的openid
	private String openid;
	//微信支付编号
	private String transactionid;
	//类型
	private  int type ;  // 1 订单支付   2矿机商城个人消费充值
	
	
	
	
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getFromuserid() {
		return fromuserid;
	}
	public void setFromuserid(String fromuserid) {
		this.fromuserid = fromuserid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Double getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(Double paymoney) {
		this.paymoney = paymoney;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getOuttradeno() {
		return outtradeno;
	}
	public void setOuttradeno(String outtradeno) {
		this.outtradeno = outtradeno;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
