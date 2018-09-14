package com.lsp.suc.entity;

import java.util.Date;

import com.mongodb.ReflectionDBObject;

/**
 * LL
 * @author lsp
 *
 */
public class IntegralLlInfo extends ReflectionDBObject { 
	private String fromUserid;
	private String custid;
	/**
	 * ps_account 开通账户
	 * tj_account 推荐管理员
	 * ps_recovery 回本后待返
	 * shop_bmzt 商城收益
	 * shop_jfdh 下单使用
	 * jfcz 充值
	 * jf_withdraw 提现
	 * shop_order 订单收益
	 * 
	 * shop_zz 转账
	 * shop_tx 提现
	 */
	private String type;
	private String value;
	 
    private Date createdate;
    private String summary;
    /**
     * 0为收入，1为支出
     */
    private int    state;
    /**
     * 0-待返
     * 1-可使用
     * 2-冻结
     **/
    private int  jfstate;
    private String fid;
    /**
     * 订单ID
     */
    private String oid;
    
    private String vipno;//会员编号
    
    private String remark;

	public String getFromUserid() {
		return fromUserid;
	}

	public void setFromUserid(String fromUserid) {
		this.fromUserid = fromUserid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getJfstate() {
		return jfstate;
	}

	public void setJfstate(int jfstate) {
		this.jfstate = jfstate;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getVipno() {
		return vipno;
	}

	public void setVipno(String vipno) {
		this.vipno = vipno;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    
}