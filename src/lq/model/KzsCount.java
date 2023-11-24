package lq.model;

import java.util.ArrayList;

public class KzsCount {
private int count;
private ArrayList<KzsInfoList> KzsInfoList=new ArrayList<KzsInfoList>();
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
public ArrayList<KzsInfoList> getKzsInfoList() {
	return KzsInfoList;
}
public void setKzsInfoList(ArrayList<KzsInfoList> kzsInfoList) {
	KzsInfoList = kzsInfoList;
}
}
