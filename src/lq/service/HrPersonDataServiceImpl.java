package lq.service;

import lq.dao.HrPersonDataImpl;
import lq.model.HrPerson;

public class HrPersonDataServiceImpl implements HrPersonDataService {

	HrPersonDataImpl dao=new HrPersonDataImpl();
	@Override
	public HrPerson personBasData(String personId,HrPerson hrperson) {
		HrPerson person=dao.personBasData(personId,hrperson);
		return person;
	}
	@Override
	public HrPerson personCurrPost(String personId,HrPerson hrperson) {
		HrPerson currPosts=dao.personCurrPost(personId,hrperson);
		return currPosts;
	}
	@Override
	public HrPerson personSpeTechPos(String personId, HrPerson hrperson) {
		HrPerson SpeTechPos=dao.personSpeTechPos(personId,hrperson);
		return SpeTechPos;
	}
	@Override
	public HrPerson personFamilyMembers(String personId, HrPerson hrperson) {
		HrPerson FamilyMembers=dao.personFamilyMembers(personId,hrperson);
		return FamilyMembers;
	}
	@Override
	public HrPerson personJl(String personId, HrPerson hrperson) {
		HrPerson Jl=dao.personJl(personId,hrperson);
		return Jl;
	}
	@Override
	public HrPerson personJcqk(String personId, HrPerson hrperson) {
		HrPerson Jcqk=dao.personJcqk(personId,hrperson);
		return Jcqk;
	}
	@Override
	public HrPerson personKhqk(String personId, HrPerson hrperson) {
		HrPerson Khqk=dao.personKhqk(personId,hrperson);
		return Khqk;
	}

}
