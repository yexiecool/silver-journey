package com.lsp.user.entity;

import java.util.Date;

import com.mongodb.ReflectionDBObject;
/***
 * 用户关系表
 * 2018-08-28
 * @author  朱攀
 *
 */
public class UserRelation extends ReflectionDBObject {
	
	private  String    parentnumber ;  		//父级节点会员编号
	
	private  String   leftnumber; 			//左区会员编号  
	
	private  String   rightnumber;  		//右区会员编号
	
	private  Date   createtime ; 			//记录时间
	
	private double leftConsumption; 		//左区消费总计
	
	private double rightConsumption; 		//右区消费总计
	
	private double leftAchievementReduce; 	//左区业剩余业绩
	
	private double rightAchievementReduce;	//右区业剩余业绩
	
	private double agentMoney;				//代理金额

	public String getParentnumber() {
		return parentnumber;
	}

	public void setParentnumber(String parentnumber) {
		this.parentnumber = parentnumber;
	}

	public String getLeftnumber() {
		return leftnumber;
	}

	public void setLeftnumber(String leftnumber) {
		this.leftnumber = leftnumber;
	}

	public String getRightnumber() {
		return rightnumber;
	}

	public void setRightnumber(String rightnumber) {
		this.rightnumber = rightnumber;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public double getLeftConsumption() {
		return leftConsumption;
	}

	public void setLeftConsumption(double leftConsumption) {
		this.leftConsumption = leftConsumption;
	}

	public double getRightConsumption() {
		return rightConsumption;
	}

	public void setRightConsumption(double rightConsumption) {
		this.rightConsumption = rightConsumption;
	}

	public double getLeftAchievementReduce() {
		return leftAchievementReduce;
	}

	public void setLeftAchievementReduce(double leftAchievementReduce) {
		this.leftAchievementReduce = leftAchievementReduce;
	}

	public double getRightAchievementReduce() {
		return rightAchievementReduce;
	}

	public void setRightAchievementReduce(double rightAchievementReduce) {
		this.rightAchievementReduce = rightAchievementReduce;
	}

	public double getAgentMoney() {
		return agentMoney;
	}

	public void setAgentMoney(double agentMoney) {
		this.agentMoney = agentMoney;
	}

	 

	
}