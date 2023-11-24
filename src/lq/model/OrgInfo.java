package lq.model;

public class OrgInfo {
    private String orgTypeid;
    private String orgName;
	public String getOrgTypeid() {
		return orgTypeid;
	}
	public void setOrgTypeid(String orgTypeid) {
		this.orgTypeid = orgTypeid;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Override
	public String toString() {
		return "OrgInfo [orgTypeid=" + orgTypeid + ", orgName=" + orgName + "]";
	}
}
