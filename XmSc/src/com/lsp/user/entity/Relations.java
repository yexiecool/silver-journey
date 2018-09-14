package com.lsp.user.entity;

/***
 * 存储二叉树的数据
 * 2018-08-31
 * @author  朱攀
 *
 */
public class Relations   {
	
    private  String  pnumber;   //父级会员编号
	private  String  number ;   // 会员编号
	private  int     count;     // 当前会员编号包含的人员的数量
	private  Double  money;     // 累计消费
	private  int     hierarchy; //层级 0 1 2 3 
	private  int     position ; //位置，1 左 2右  
 
	
	/**
	 * 代理商   类型
	 * 1-省  2-市  3-县   4-部门        矿机会员类型 100-微型矿机  - 字体白色    101-小型矿机字体绿色    102-中型矿机 字体黄色          103-大型矿机 字体红色
	 * 2018/6/20
	 */
	private int agentLevel; 
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(int hierarchy) {
		this.hierarchy = hierarchy;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getPnumber() {
		return pnumber;
	}
	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}
	public int getAgentLevel() {
		return agentLevel;
	}
	public void setAgentLevel(int agentLevel) {
		this.agentLevel = agentLevel;
	}
	
	
 
	
}