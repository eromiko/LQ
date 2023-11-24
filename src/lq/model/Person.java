package lq.model;

public final class Person {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 出生年月
	 */
	private String birthday;
	/**
	 * 名族
	 */
	private String mz;
	/**
	 * 籍贯
	 */
	private String jg;
	/**
	 * 出生地
	 */
	private String birthAddress;
	/**
	 * 入党时间
	 */
	private String rdTime;
	/**
	 * 参加工作时间
	 */
	private String workTime;
	/**
	 * 健康状况
	 */
	private String health;
	/**
	 * 专业技术职务
	 */
	private String zyPosition;
	/**
	 * 熟悉专业有何专长
	 */
	private String tc;
	/**
	 * 全日制教育
	 */
	private String qrjy;
	/**
	 * 全日制毕业院校及专业
	 */
	private String qrSchool;
	/**
	 * 在  职教  育
	 */
	private String zzjy;
	/**
	 * 在职教育院校及专业
	 */
	private String zzSchool;
	/**
	 * 现 任 职 务
	 */
	private String nowPosition;
	/**
	 * 拟 任 职 务
	 */
	private String nrPosition;
	/**
	 * 拟 免 职 务
	 */
	private String nmPosition;
	/**
	 * 简历
	 */
	private StringBuffer cv;
	/**
	 * 奖惩情况
	 */
	private String jc;
	/**
	 * 年度考核结果
	 */
	private String approve;
	/**
	 * 任免理由
	 */
	private String rmly;
	/**
	 * 家庭成员称谓1
	 */
	private String longName1;
	/**
	 * 家庭成员姓名1
	 */
	private String name1;
	/**
	 * 家庭成员年龄1
	 */
	private String age1;
	/**
	 * 家庭成员政治面貌1
	 */
	private String zzmm1;
	/**
	 * 家庭成员工作单位及职务1
	 */
	private String position1;
	/**
	 * 家庭成员称谓2
	 */
	private String longName2;
	/**
	 * 家庭成员姓名2
	 */
	private String name2;
	/**
	 * 家庭成员年龄2
	 */
	private String age2;
	/**
	 * 家庭成员政治面貌2
	 */
	private String zzmm2;
	/**
	 * 家庭成员工作单位及职务2
	 */
	private String position2;
	/**
	 * 家庭成员称谓3
	 */
	private String longName3;
	/**
	 * 家庭成员姓名3
	 */
	private String name3;
	/**
	 * 家庭成员年龄3
	 */
	private String age3;
	/**
	 * 家庭成员政治面貌3
	 */
	private String zzmm3;
	/**
	 * 家庭成员工作单位及职务3
	 */
	private String position3;
	/**
	 * 家庭成员称谓4
	 */
	private String longName4;
	/**
	 * 家庭成员年龄4
	 */
	private String age4;
	/**
	 * 家庭成员姓名4
	 */
	private String name4;
	/**
	 * 家庭成员政治面貌4
	 */
	private String zzmm4;
	/**
	 * 家庭成员工作单位及职务4
	 */
	private String position4;
	/**
	 * 家庭成员称谓5
	 */
	private String longName5;
	/**
	 * 家庭成员姓名5
	 */
	private String name5;
	/**
	 * 家庭成员年龄5
	 */
	private String age5;
	/**
	 * 家庭成员政治面貌5
	 */
	private String zzmm5;
	/**
	 * 家庭成员工作单位及职务5
	 */
	private String position5;
	/**
	 * 计算年龄时间
	 */
	private String jsnlsj;
	/**
	 * 填表时间
	 */
	private String tbsj;
	/**
	 * 填表人
	 */
	private String tbr;
	/**
	 * 照片
	 */
	private String img;
	//政治面貌
	private String zzmm;
	//职称/技能等级
	private String zc;
	//所在公司
	private String company;
	//部门/职务
	private String bmzw;
	//联系电话
	private String cell;
	//身份证号码
	private String idcard;
	//调动工作经历-工作经历和员工变动记录
	private StringBuffer ddcv;
	
	
	public StringBuffer getDdcv() {
		return ddcv;
	}


	public void setDdcv(StringBuffer ddcv) {
		this.ddcv = ddcv;
	}


	public String getZzmm() {
		return zzmm;
	}


	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}


	public String getZc() {
		return zc;
	}


	public void setZc(String zc) {
		this.zc = zc;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getBmzw() {
		return bmzw;
	}


	public void setBmzw(String bmzw) {
		this.bmzw = bmzw;
	}


	public String getCell() {
		return cell;
	}


	public void setCell(String cell) {
		this.cell = cell;
	}


	public String getIdcard() {
		return idcard;
	}


	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getMz() {
		return mz;
	}


	public void setMz(String mz) {
		this.mz = mz;
	}


	public String getJg() {
		return jg;
	}


	public void setJg(String jg) {
		this.jg = jg;
	}


	public String getBirthAddress() {
		return birthAddress;
	}


	public void setBirthAddress(String birthAddress) {
		this.birthAddress = birthAddress;
	}


	public String getRdTime() {
		return rdTime;
	}


	public void setRdTime(String rdTime) {
		this.rdTime = rdTime;
	}


	public String getWorkTime() {
		return workTime;
	}


	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}


	public String getHealth() {
		return health;
	}


	public void setHealth(String health) {
		this.health = health;
	}


	public String getZyPosition() {
		return zyPosition;
	}


	public void setZyPosition(String zyPosition) {
		this.zyPosition = zyPosition;
	}


	public String getTc() {
		return tc;
	}


	public void setTc(String tc) {
		this.tc = tc;
	}


	public String getQrjy() {
		return qrjy;
	}


	public void setQrjy(String qrjy) {
		this.qrjy = qrjy;
	}


	public String getQrSchool() {
		return qrSchool;
	}


	public void setQrSchool(String qrSchool) {
		this.qrSchool = qrSchool;
	}


	public String getZzjy() {
		return zzjy;
	}


	public void setZzjy(String zzjy) {
		this.zzjy = zzjy;
	}


	public String getZzSchool() {
		return zzSchool;
	}


	public void setZzSchool(String zzSchool) {
		this.zzSchool = zzSchool;
	}


	public String getNowPosition() {
		return nowPosition;
	}


	public void setNowPosition(String nowPosition) {
		this.nowPosition = nowPosition;
	}


	public String getNrPosition() {
		return nrPosition;
	}


	public void setNrPosition(String nrPosition) {
		this.nrPosition = nrPosition;
	}


	public String getNmPosition() {
		return nmPosition;
	}


	public void setNmPosition(String nmPosition) {
		this.nmPosition = nmPosition;
	}


	public StringBuffer getCv() {
		return cv;
	}


	public void setCv(StringBuffer cv) {
		this.cv = cv;
	}


	public String getJc() {
		return jc;
	}


	public void setJc(String jc) {
		this.jc = jc;
	}


	public String getApprove() {
		return approve;
	}


	public void setApprove(String approve) {
		this.approve = approve;
	}


	public String getRmly() {
		return rmly;
	}


	public void setRmly(String rmly) {
		this.rmly = rmly;
	}


	public String getLongName1() {
		return longName1;
	}


	public void setLongName1(String longName1) {
		this.longName1 = longName1;
	}


	public String getName1() {
		return name1;
	}


	public void setName1(String name1) {
		this.name1 = name1;
	}


	public String getAge1() {
		return age1;
	}


	public void setAge1(String age1) {
		this.age1 = age1;
	}


	public String getZzmm1() {
		return zzmm1;
	}


	public void setZzmm1(String zzmm1) {
		this.zzmm1 = zzmm1;
	}


	public String getPosition1() {
		return position1;
	}


	public void setPosition1(String position1) {
		this.position1 = position1;
	}


	public String getLongName2() {
		return longName2;
	}


	public void setLongName2(String longName2) {
		this.longName2 = longName2;
	}


	public String getName2() {
		return name2;
	}


	public void setName2(String name2) {
		this.name2 = name2;
	}


	public String getAge2() {
		return age2;
	}


	public void setAge2(String age2) {
		this.age2 = age2;
	}


	public String getZzmm2() {
		return zzmm2;
	}


	public void setZzmm2(String zzmm2) {
		this.zzmm2 = zzmm2;
	}


	public String getPosition2() {
		return position2;
	}


	public void setPosition2(String position2) {
		this.position2 = position2;
	}


	public String getLongName3() {
		return longName3;
	}


	public void setLongName3(String longName3) {
		this.longName3 = longName3;
	}


	public String getName3() {
		return name3;
	}


	public void setName3(String name3) {
		this.name3 = name3;
	}


	public String getAge3() {
		return age3;
	}


	public void setAge3(String age3) {
		this.age3 = age3;
	}


	public String getZzmm3() {
		return zzmm3;
	}


	public void setZzmm3(String zzmm3) {
		this.zzmm3 = zzmm3;
	}


	public String getPosition3() {
		return position3;
	}


	public void setPosition3(String position3) {
		this.position3 = position3;
	}


	public String getLongName4() {
		return longName4;
	}


	public void setLongName4(String longName4) {
		this.longName4 = longName4;
	}


	public String getAge4() {
		return age4;
	}


	public void setAge4(String age4) {
		this.age4 = age4;
	}


	public String getName4() {
		return name4;
	}


	public void setName4(String name4) {
		this.name4 = name4;
	}


	public String getZzmm4() {
		return zzmm4;
	}


	public void setZzmm4(String zzmm4) {
		this.zzmm4 = zzmm4;
	}


	public String getPosition4() {
		return position4;
	}


	public void setPosition4(String position4) {
		this.position4 = position4;
	}


	public String getLongName5() {
		return longName5;
	}


	public void setLongName5(String longName5) {
		this.longName5 = longName5;
	}


	public String getName5() {
		return name5;
	}


	public void setName5(String name5) {
		this.name5 = name5;
	}


	public String getAge5() {
		return age5;
	}


	public void setAge5(String age5) {
		this.age5 = age5;
	}


	public String getZzmm5() {
		return zzmm5;
	}


	public void setZzmm5(String zzmm5) {
		this.zzmm5 = zzmm5;
	}


	public String getPosition5() {
		return position5;
	}


	public void setPosition5(String position5) {
		this.position5 = position5;
	}


	public String getJsnlsj() {
		return jsnlsj;
	}


	public void setJsnlsj(String jsnlsj) {
		this.jsnlsj = jsnlsj;
	}


	public String getTbsj() {
		return tbsj;
	}


	public void setTbsj(String tbsj) {
		this.tbsj = tbsj;
	}


	public String getTbr() {
		return tbr;
	}


	public void setTbr(String tbr) {
		this.tbr = tbr;
	}


	public Person(){
		this.name = "";
		this.sex = "";
		this.birthday = "";
		this.mz = "";
		this.jg = "";
		this.birthAddress = "";
		this.rdTime = "";
		this.workTime = "";
		this.health = "";
		this.zyPosition = "";
		this.tc = "";
		this.qrjy = "";
		this.qrSchool = "";
		this.zzjy = "";
		this.zzSchool = "";
		this.nowPosition = "";
		this.nrPosition = "";
		this.nmPosition = "";
		this.cv = new StringBuffer("");
		this.jc = "";
		this.approve = "";
		this.rmly = "";
		this.longName1 = "";
		this.name1 = "";
		this.age1 = "";
		this.zzmm1 = "";
		this.position1 = "";
		this.longName2 = "";
		this.name2 = "";
		this.age2 = "";
		this.zzmm2 = "";
		this.position2 = "";
		this.longName3 = "";
		this.name3 = "";
		this.age3 = "";
		this.zzmm3 = "";
		this.position3 = "";
		this.longName4 = "";
		this.age4 = "";
		this.name4 = "";
		this.zzmm4 = "";
		this.position4 = "";
		this.longName5 = "";
		this.name5 = "";
		this.age5 = "";
		this.zzmm5 = "";
		this.position5 = "";
		this.jsnlsj = "";
		this.tbsj = "";
		this.tbr = "";
		this.img = "";
		this.zzmm= "";
		this.zc= "";
		this.company= "";
		this.bmzw= "";
		this.cell= "";
		this.idcard= "";
		this.ddcv= new StringBuffer("");
	}
}
