package lq.service;

import lq.model.HrPerson;

public interface HrPersonDataService {

	// 员工基础信息
	HrPerson personBasData(String personId,HrPerson hrperson);
	// 员工现任职务
	HrPerson personCurrPost(String personId,HrPerson hrperson);
	//专业技术职务
	HrPerson personSpeTechPos(String personId,HrPerson hrperson);	
	//家庭成员
	HrPerson personFamilyMembers(String personId,HrPerson hrperson);	
	//学历、职位简历
	HrPerson personJl(String personId,HrPerson hrperson);	
	//奖惩情况
	HrPerson personJcqk(String personId,HrPerson hrperson);	
	//考核情况
	HrPerson personKhqk(String personId,HrPerson hrperson);	
}
