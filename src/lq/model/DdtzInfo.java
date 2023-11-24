package lq.model;

public class DdtzInfo {
	private String year;//年
	private String office;//调动后集团
	private String officelxr;//调动后联系人
	private String officephone;//调动后联系人手机号
	private String oldoffice;//调动前集团
	private String oldcompany;//调动前公司
	private String oldofficelxr;//调动前联系人
	private String oldofficephone;//调动前联系人手机号
	private String person;//调动人
	private String startdate;//调动日期	
	private String createtime;//制单日期
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getOldoffice() {
		return oldoffice;
	}
	public void setOldoffice(String oldoffice) {
		this.oldoffice = oldoffice;
	}
	public String getOldcompany() {
		return oldcompany;
	}
	public void setOldcompany(String oldcompany) {
		this.oldcompany = oldcompany;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getOfficelxr() {
		return officelxr;
	}
	public void setOfficelxr(String officelxr) {
		this.officelxr = officelxr;
	}
	public String getOldofficelxr() {
		return oldofficelxr;
	}
	public void setOldofficelxr(String oldofficelxr) {
		this.oldofficelxr = oldofficelxr;
	}
	public String getOfficephone() {
		return officephone;
	}
	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}
	public String getOldofficephone() {
		return oldofficephone;
	}
	public void setOldofficephone(String oldofficephone) {
		this.oldofficephone = oldofficephone;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public DdtzInfo(){
		this.office="";
		this.oldoffice="";
		this.oldcompany="";
		this.person="";
		this.startdate="";
		this.officelxr="";
		this.oldofficelxr="";
		this.officephone="";
		this.oldofficephone="";
		this.createtime="";
	}
}
