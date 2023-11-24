package lq.model;

public class KzsInfo {
	private int index; //序号
	private String name;//姓名
	private String gender;//性别
	private int age;//年龄
	private String rylb;//人员类别
	private String qrz;//全日制
	private String qrzby;//全日制学校专业
	private String zz;//在职
	private String zzby;//在职学校专业
	private String zczs;//职称或证书
	private String lgzjl;//原单位/部门/职务
	private String ngzjl;//拟调入单位/部门/职务
	private String lgzszd;//目前工作所在地
	private String ngzszd;//拟调入工作所在地
	private String nzs;//拟调入直属企业
	private String nzsNo;//拟调入直属企业编码
	
	public String getNzsNo() {
		return nzsNo;
	}
	public void setNzsNo(String nzsNo) {
		this.nzsNo = nzsNo;
	}
	public String getNzs() {
		return nzs;
	}
	public void setNzs(String nzs) {
		this.nzs = nzs;
	}
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getRylb() {
		return rylb;
	}
	public void setRylb(String rylb) {
		this.rylb = rylb;
	}
	public String getQrz() {
		return qrz;
	}
	public void setQrz(String qrz) {
		this.qrz = qrz;
	}
	public String getQrzby() {
		return qrzby;
	}
	public void setQrzby(String qrzby) {
		this.qrzby = qrzby;
	}
	public String getZz() {
		return zz;
	}
	public void setZz(String zz) {
		this.zz = zz;
	}
	public String getZzby() {
		return zzby;
	}
	public void setZzby(String zzby) {
		this.zzby = zzby;
	}
	public String getZczs() {
		return zczs;
	}
	public void setZczs(String zczs) {
		this.zczs = zczs;
	}
	public String getLgzjl() {
		return lgzjl;
	}
	public void setLgzjl(String lgzjl) {
		this.lgzjl = lgzjl;
	}
	public String getNgzjl() {
		return ngzjl;
	}
	public void setNgzjl(String ngzjl) {
		this.ngzjl = ngzjl;
	}
	public String getLgzszd() {
		return lgzszd;
	}
	public void setLgzszd(String lgzszd) {
		this.lgzszd = lgzszd;
	}
	public String getNgzszd() {
		return ngzszd;
	}
	public void setNgzszd(String ngzszd) {
		this.ngzszd = ngzszd;
	}
	@Override
	public String toString() {
		return "KzsInfo [index=" + index + ", name=" + name + ", gender=" + gender + ", age=" + age + ", rylb=" + rylb
				+ ", qrz=" + qrz + ", qrzby=" + qrzby + ", zz=" + zz + ", zzby=" + zzby + ", zczs=" + zczs + ", lgzjl="
				+ lgzjl + ", ngzjl=" + ngzjl + ", lgzszd=" + lgzszd + ", ngzszd=" + ngzszd + ", nzs=" + nzs + ", nzsNo="
				+ nzsNo + "]";
	}
		
}
