package lq.model;

import java.util.List;

public class HrPerson {
	//姓名
	private String name;
	//性别
	private String gender;
	//出生年月
	private String birthday;
	//民族
	private String nation;
	//籍贯
	private String nativeBirth;
	//出生地
	private String birthAddress;
	//入党时间
	private String rdTime;
	//参加工作时间
	private String startWorkTime;
	//健康状况
	private String healthy;
	//专业技术职务
	private String speTechPos;
	//熟悉专业有何特长
	private String specialty;
	//全日制教育
	private String fulltimeEdu;
	//全日制毕业院校系及专业
	private String fulltimeSpe;
	//在职教育学历
	private String jobEdu;
	//在职教育毕业院校系及专业
	private String jobSpe;
	//现任职务
	private String currPost;
	//家庭成员
	private List<FamilyMembers> familyMembers;
	//学历、职位简历
	private String jl;
	//奖惩情况
	private String jcqk;
	//考核情况
	private String khqk;
		
	public String getKhqk() {
		return khqk;
	}
	public void setKhqk(String khqk) {
		this.khqk = khqk;
	}
	public String getJcqk() {
		return jcqk;
	}
	public void setJcqk(String jcqk) {
		this.jcqk = jcqk;
	}
	public String getJl() {
		return jl;
	}
	public void setJl(String jl) {
		this.jl = jl;
	}
	public List<FamilyMembers> getFamilyMembers() {
		return familyMembers;
	}
	public void setFamilyMembers(List<FamilyMembers> familyMembers) {
		this.familyMembers = familyMembers;
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getNativeBirth() {
		return nativeBirth;
	}
	public void setNativeBirth(String nativeBirth) {
		this.nativeBirth = nativeBirth;
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
	public String getStartWorkTime() {
		return startWorkTime;
	}
	public void setStartWorkTime(String startWorkTime) {
		this.startWorkTime = startWorkTime;
	}
	public String getHealthy() {
		return healthy;
	}
	public void setHealthy(String healthy) {
		this.healthy = healthy;
	}
	public String getSpeTechPos() {
		return speTechPos;
	}
	public void setSpeTechPos(String speTechPos) {
		this.speTechPos = speTechPos;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getFulltimeEdu() {
		return fulltimeEdu;
	}
	public void setFulltimeEdu(String fulltimeEdu) {
		this.fulltimeEdu = fulltimeEdu;
	}
	public String getFulltimeSpe() {
		return fulltimeSpe;
	}
	public void setFulltimeSpe(String fulltimeSpe) {
		this.fulltimeSpe = fulltimeSpe;
	}
	public String getJobEdu() {
		return jobEdu;
	}
	public void setJobEdu(String jobEdu) {
		this.jobEdu = jobEdu;
	}
	public String getJobSpe() {
		return jobSpe;
	}
	public void setJobSpe(String jobSpe) {
		this.jobSpe = jobSpe;
	}
	public String getCurrPost() {
		return currPost;
	}
	public void setCurrPost(String currPost) {
		this.currPost = currPost;
	}
	
}
