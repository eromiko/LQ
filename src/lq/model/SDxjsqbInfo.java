package lq.model;

public class SDxjsqbInfo {
	private String name;//姓名
	private String bmzw;//部门及职务
	private String ncell;//联系电话
	private String email;//电子邮件
	private String qjlb;//请假类别
	private String qjsy;//请假事由
	private String qjsj;//请假时间
	private String lyy;//年休假上年剩余天数
	private String nyx;//当年应休天数
	private String nyyx;//当年已休天数
	private String nyy;//当年剩余天数
	private String rlzyshr;//人力资源审核人  -	员工、正职、副职、领导
	private String fgyj;//分管副部长意见--员工
	private String fgqm;//分管副部长签字  
	private String fgrq;//分管副部长日期    
	private String szbmyj;//部门负责人意见    员工  、副职
	private String szbmqm;//部门负责人签字   
	private String szbmrq;//部门负责人日期	
	private String fgldyj; //分管领导意见     正职、副职
	private String fgldqz; //分管领导签字    
	private String fgldrq; //分管领导日期   
	private String zjlyj;//总经理意见  正职、领导
	private String zjlqz;//总经理签字  
	private String zjlrq;//总经理日期  
	private String dszyj;//董事长意见 正职、领导
	private String dszqz;//董事长签字  
	private String dszrq;//董事长日期  
	
	
	public String getFgldyj() {
		return fgldyj;
	}


	public void setFgldyj(String fgldyj) {
		this.fgldyj = fgldyj;
	}


	public String getFgldqz() {
		return fgldqz;
	}


	public void setFgldqz(String fgldqz) {
		this.fgldqz = fgldqz;
	}


	public String getFgldrq() {
		return fgldrq;
	}


	public void setFgldrq(String fgldrq) {
		this.fgldrq = fgldrq;
	}


	public String getZjlyj() {
		return zjlyj;
	}


	public void setZjlyj(String zjlyj) {
		this.zjlyj = zjlyj;
	}


	public String getZjlqz() {
		return zjlqz;
	}


	public void setZjlqz(String zjlqz) {
		this.zjlqz = zjlqz;
	}


	public String getZjlrq() {
		return zjlrq;
	}


	public void setZjlrq(String zjlrq) {
		this.zjlrq = zjlrq;
	}


	public String getDszyj() {
		return dszyj;
	}


	public void setDszyj(String dszyj) {
		this.dszyj = dszyj;
	}


	public String getDszqz() {
		return dszqz;
	}


	public void setDszqz(String dszqz) {
		this.dszqz = dszqz;
	}


	public String getDszrq() {
		return dszrq;
	}


	public void setDszrq(String dszrq) {
		this.dszrq = dszrq;
	}


	public String getRlzyshr() {
		return rlzyshr;
	}


	public void setRlzyshr(String rlzyshr) {
		this.rlzyshr = rlzyshr;
	}


	public String getFgyj() {
		return fgyj;
	}


	public void setFgyj(String fgyj) {
		this.fgyj = fgyj;
	}


	public String getFgqm() {
		return fgqm;
	}


	public void setFgqm(String fgqm) {
		this.fgqm = fgqm;
	}


	public String getFgrq() {
		return fgrq;
	}


	public void setFgrq(String fgrq) {
		this.fgrq = fgrq;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBmzw() {
		return bmzw;
	}


	public void setBmzw(String bmzw) {
		this.bmzw = bmzw;
	}


	public String getNcell() {
		return ncell;
	}


	public void setNcell(String ncell) {
		this.ncell = ncell;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getQjlb() {
		return qjlb;
	}


	public void setQjlb(String qjlb) {
		this.qjlb = qjlb;
	}


	public String getQjsy() {
		return qjsy;
	}


	public void setQjsy(String qjsy) {
		this.qjsy = qjsy;
	}


	public String getQjsj() {
		return qjsj;
	}


	public void setQjsj(String qjsj) {
		this.qjsj = qjsj;
	}


	public String getLyy() {
		return lyy;
	}


	public void setLyy(String lyy) {
		this.lyy = lyy;
	}


	public String getNyx() {
		return nyx;
	}


	public void setNyx(String nyx) {
		this.nyx = nyx;
	}


	public String getNyyx() {
		return nyyx;
	}


	public void setNyyx(String nyyx) {
		this.nyyx = nyyx;
	}


	public String getNyy() {
		return nyy;
	}


	public void setNyy(String nyy) {
		this.nyy = nyy;
	}


	public String getSzbmyj() {
		return szbmyj;
	}


	public void setSzbmyj(String szbmyj) {
		this.szbmyj = szbmyj;
	}


	public String getSzbmqm() {
		return szbmqm;
	}


	public void setSzbmqm(String szbmqm) {
		this.szbmqm = szbmqm;
	}


	public String getSzbmrq() {
		return szbmrq;
	}


	public void setSzbmrq(String szbmrq) {
		this.szbmrq = szbmrq;
	}


	public SDxjsqbInfo(){
		this.name=" ";//姓名
		this.bmzw=" ";//部门及职务
		this.ncell=" ";//联系电话
		this.email=" ";//电子邮件
		this.qjlb="□年休假 □婚假 □产假（护理假）□病假<w:br />□丧假  □事假 □子女护理假   □其他___";//请假类别
		this.qjsy=" ";//请假事由
		this.qjsj=" ";//请假时间
		this.lyy=" ";//年休假上年剩余天数
		this.nyx=" ";//当年应休天数
		this.nyyx=" ";//当年已休天数
		this.nyy=" ";//当年剩余天数		
		this.rlzyshr=" ";//人力资源审核人		
		this.fgyj=" ";//分管副部长意见
		this.fgqm=" ";//分管副部长签字
		this.fgrq="  年     月     日";//分管副部长日期
		this.szbmyj=" ";//部门负责人意见
		this.szbmqm=" ";//部门负责人签字
		this.szbmrq="  年     月     日";//部门负责人日期
		this.fgldyj=" ";//分管领导意见
		this.fgldqz=" ";//分管领导签字
		this.fgldrq="  年     月     日";//分管领导日期
		this.zjlyj=" ";//总经理意见
		this.zjlqz=" ";//总经理签字
		this.zjlrq="  年     月     日";//总经理日期
		this.dszyj=" ";//董事长意见
		this.dszqz=" ";//董事长签字
		this.dszrq="  年     月     日";//董事长日期
	}
}
