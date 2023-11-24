package lq.model;

import java.util.Objects;

public class gzjlInfo {

	private  String bengindate;
	private  String enddate;
	private  String comp;
	private  String org;
	private  String pos;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		gzjlInfo gzjlInfo = (gzjlInfo) o;
		return Objects.equals(bengindate, gzjlInfo.bengindate) && Objects.equals(enddate, gzjlInfo.enddate) && Objects.equals(comp, gzjlInfo.comp) && Objects.equals(org, gzjlInfo.org) && Objects.equals(pos, gzjlInfo.pos);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bengindate, enddate, comp, org, pos);
	}

	public String getBengindate() {
		return bengindate;
	}
	public void setBengindate(String bengindate) {
		this.bengindate = bengindate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getComp() {
		return comp;
	}
	public void setComp(String comp) {
		this.comp = comp;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}

}
