package lq.model;

import java.util.ArrayList;

public class KzsInfoList {
	private String nzs;
	private String nzsNo;
	private int count;
	private ArrayList<KzsInfo> kzsinfos=new ArrayList<KzsInfo>();
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getNzs() {
		return nzs;
	}
	public void setNzs(String nzs) {
		this.nzs = nzs;
	}
	public String getNzsNo() {
		return nzsNo;
	}
	public void setNzsNo(String nzsNo) {
		this.nzsNo = nzsNo;
	}
	public ArrayList<KzsInfo> getKzsinfos() {
		return kzsinfos;
	}
	public void setKzsinfos(ArrayList<KzsInfo> kzsinfos) {
		this.kzsinfos = kzsinfos;
	}
	@Override
	public String toString() {
		return "KzsInfoList [nzs=" + nzs + ", nzsNo=" + nzsNo + ", count=" + count + ", kzsinfos=" + kzsinfos + "]";
	}
	
}
