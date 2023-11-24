package lq.model;

import java.math.BigDecimal;

public class QjlbInfo {
	private int index;//序号
	private String name;//姓名
	private String bm;//部门
	private String gw;//职务岗位
	private String qjlb;//请假类别
	private String qjsj;//请假时间
	private BigDecimal qjts=new BigDecimal("0");//请假天数
	private BigDecimal sysj=new BigDecimal("0");//剩余天数
	private String qjcs;//请假次数
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBm() {
		return bm;
	}
	public void setBm(String bm) {
		this.bm = bm;
	}
	public String getGw() {
		return gw;
	}
	public void setGw(String gw) {
		this.gw = gw;
	}
	public String getQjlb() {
		return qjlb;
	}
	public void setQjlb(String qjlb) {
		this.qjlb = qjlb;
	}
	public String getQjsj() {
		return qjsj;
	}
	public void setQjsj(String qjsj) {
		this.qjsj = qjsj;
	}
	public BigDecimal getQjts() {
		return qjts;
	}
	public void setQjts(BigDecimal qjts) {
		this.qjts = qjts;
	}
	public BigDecimal getSysj() {
		return sysj;
	}
	public void setSysj(BigDecimal sysj) {
		this.sysj = sysj;
	}
	public String getQjcs() {
		return qjcs;
	}
	public void setQjcs(String qjcs) {
		this.qjcs = qjcs;
	}
	@Override
	public String toString() {
		return "QjlbInfo [index=" + index + ", name=" + name + ", bm=" + bm + ", gw=" + gw + ", qjlb=" + qjlb
				+ ", qjsj=" + qjsj + ", qjts=" + qjts + ", sysj=" + sysj + ", qjcs=" + qjcs + "]";
	}
	
}
