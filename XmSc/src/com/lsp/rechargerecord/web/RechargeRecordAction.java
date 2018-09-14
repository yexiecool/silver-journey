package com.lsp.rechargerecord.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lsp.pub.web.GeneralAction;
import com.lsp.rechargerecord.entity.RechargeRecord;
import com.lsp.shop.web.ShopproAction;

@Namespace("/recharge")
@Results( { @Result(name = ShopproAction.RELOAD, location = "recharge.action",params={"comid", "%{comid}","fypage", "%{fypage}"}, type = "redirect") })
public class RechargeRecordAction extends GeneralAction<RechargeRecord> {

	private RechargeRecord rechargeRecord = new RechargeRecord();
	@Override
	public RechargeRecord getModel() {
		return rechargeRecord;
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

}
