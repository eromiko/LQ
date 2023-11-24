package lq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import lq.model.FamilyMembers;
import lq.model.HrPerson;
import lq.util.C3P0Utils;

public class HrPersonDataImpl implements HrPersonData {

	@Override
	public HrPerson personBasData(String personId,HrPerson hrperson) {
		SimpleDateFormat rdwt = new SimpleDateFormat("yyyy年MM月");
		StringBuffer sql = new StringBuffer();
		sql.append("select p.fname_l2 name,CASE p.FGENDER WHEN 1 THEN '男' WHEN 2 THEN '女' END gender,p.FBIRTHDAY birthday,mz.fname_l2 nation,\n");
		sql.append("jg.fname_l2 nativeBirth,csd.fname_l2 birthAddress,rd.CFCJDPSJ rdTime,p.CFSxzyyhtc specialty,he.FName_l2 healthy,\n");
		sql.append("po.FJOBSTARTDATE startWorkTime,hpz.FName_l2 jobEdu,p.cfzzyxzy jobSpe,hpq.fname_l2 fulltimeEdu,p.cfqrzyxzy fulltimeSpe,rd.cfzzmmid\n");
		sql.append("from t_bd_person p left join T_BD_HRFolk mz on p.FFolkID=mz.fid\n");
		sql.append("left join CT_MP_Hujdz jg on jg.fid=p.cfjgid left join CT_MP_Hujdz csd on csd.fid=p.cfcsdid\n");
		sql.append("left join ct_mp_zzmmyqx rd on rd.FPERSONID=p.fid left join T_bd_hrhealth he on he.fid=p.FHealthID\n");
		sql.append("left join T_HR_PersonPosition po on po.fpersonid=p.fid\n");
		sql.append("left join T_BD_HRDiploma hpz on p.cfzzjyid=hpz.fid\n");
		sql.append("left join T_BD_HRDiploma hpq on p.cfqrzjyid=hpq.fid\n");
		sql.append("where p.fid='"+personId+"'\n");
		Connection dbconnection=null;	
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			dbconnection = C3P0Utils.getConnection();
			pst = dbconnection.prepareStatement(sql.toString());				
			rs=pst.executeQuery();
			while (rs.next()) {
				hrperson.setName(rs.getString("name"));
				hrperson.setGender(rs.getString("gender"));
				Date bStart = rs.getDate("birthday");
				if (bStart != null) {
					Date end = new Date();
					Calendar c1 = Calendar.getInstance();
					Calendar c2 = Calendar.getInstance();
					c1.setTime(bStart);
					c2.setTime(end);
					int year1 = c1.get(1);
					int year2 = c2.get(1);
					int month1 = c1.get(2);
					int month2 = c2.get(2);
					int day1 = c1.get(5);
					int day2 = c2.get(5);
					Integer year = Integer.valueOf(Math.abs(year1 - year2));
					if (month2 <= month1) {
						if (month2 == month1) {
							if (day2 < day1)
								year = Integer.valueOf(year.intValue() - 1);
						} else {
							year = Integer.valueOf(year.intValue() - 1);
						}
					}
					hrperson.setBirthday(bStart.toString() + "<br/>（"
							+ year.toString() + "岁）");
				}
				hrperson.setNation(rs.getString("nation"));
				hrperson.setNativeBirth(rs.getString("nativeBirth"));
				hrperson.setBirthAddress(rs.getString("birthAddress"));
				String zzmm = rs.getString("cfzzmmid");
				if (("5a5df0ce-0101-1000-e000-7b4ac0a83c7f791DBAAB".toString()
						.equalsIgnoreCase(zzmm))
						&& (rs.getDate("rdTime") != null)) {
					String rdsj=rdwt.format(rs.getDate("rdTime"));					
					hrperson.setRdTime(rdsj);
				}
				hrperson.setSpecialty(rs.getString("specialty"));
				hrperson.setHealthy(rs.getString("healthy"));
				if(rs.getDate("startWorkTime")!=null){
					hrperson.setStartWorkTime(rdwt.format(rs.getDate("startWorkTime")));
				}
				hrperson.setJobEdu(rs.getString("jobEdu"));
				hrperson.setJobSpe(rs.getString("jobSpe"));
				hrperson.setFulltimeEdu(rs.getString("fulltimeEdu"));
				hrperson.setFulltimeSpe(rs.getString("fulltimeSpe"));		
			}
		} catch (SQLException e) {	
			System.out.println(e);
			System.out.println("....取人力资源员工基础数据异常....");
		}finally {
			C3P0Utils.closeAll(dbconnection,pst,rs);	
		}
		return hrperson;
	}

	@Override
	public HrPerson personCurrPost(String personId,HrPerson hrperson) {
		StringBuffer nPosition=new StringBuffer();
		StringBuffer Xrzwsb=new StringBuffer();
		Xrzwsb.append("select t5.fname_l2 o1name,t4.fname_l2 o2name,t3.fname_l2 o3name,t2.FName_l2 pname from T_HR_EmpOrgRelation t1  \n");
		Xrzwsb.append("left join T_ORG_Position t2 on t1.FPositionID=t2.FID \n");
		Xrzwsb.append("left join t_org_admin t3 on t3.fid=t1.fadminorgid left join t_org_admin t4 on t4.fid=t3.fparentid \n");
		Xrzwsb.append("left join t_org_admin t5 on t5.fid=t4.fparentid \n");
		Xrzwsb.append("where t1.FLEFFDT=to_date('2199-12-31','yyyy-mm-dd') and t1.fpersonid='"+personId+"'");		
		Connection dbconnection=null;	
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			dbconnection = C3P0Utils.getConnection();
			pst = dbconnection.prepareStatement(Xrzwsb.toString());				
			rs=pst.executeQuery();
			hrperson.setCurrPost("");
			while (rs.next()) {
				//在党委、董事会、监事会、经营班子、纪委、工会的人员只显示公司+职位
				if(rs.getString("o3name").equals("党委")||rs.getString("o3name").equals("董事会")||rs.getString("o3name").equals("监事会")||
						rs.getString("o3name").equals("经营班子")||rs.getString("o3name").equals("纪委")||rs.getString("o3name").equals("工会")){
					String str1 = "";
					if (rs.getString("o1name") != null){
						str1 = rs.getString("o1name");
					}		
					if (rs.getString("pname") != null) {
						nPosition.append(
								str1+rs.getString("pname")).append("<br/>");
					}	
				}else{
					//普通职员显示公司+部门+职位   公司含有"机关"两个字的，公司名称不显示机关
					String strsub=rs.getString("o2name");
					if(strsub.length()>2){
						String strsub02=strsub.substring(strsub.length()-2);							
						if(strsub02.equals("机关")){
							strsub=strsub.substring(0,strsub.length()- 2);								
						}
					}
					String str3 = "";
					if (rs.getString("o3name") != null){
						str3 = rs.getString("o3name");
					}		
					if (rs.getString("pname") != null) {
						nPosition.append(
								strsub+str3+rs.getString("pname")).append("<br/>");
					}	
				}
			}
			hrperson.setCurrPost(nPosition.toString());
		} catch (SQLException e) {	
			System.out.println(e);
			System.out.println("....取现任职务数据异常....");
		}finally {
			C3P0Utils.closeAll(dbconnection,pst,rs);	
		}
		return hrperson;
	}

	@Override
	public HrPerson personSpeTechPos(String personId, HrPerson hrperson) {
		StringBuffer Zyjszfsb=new StringBuffer();
		Zyjszfsb.append("select mz.fname_l2 from T_HR_PersonTechPost tp left join CT_MP_Zyjszwzg03 mz on tp.CFZyjszwzgmcID=mz.fid \n");
		Zyjszfsb.append("where tp.FisHighTechnical=1 and tp.fpersonid='"+personId+"'");
		Connection dbconnection=null;	
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			dbconnection = C3P0Utils.getConnection();
			pst = dbconnection.prepareStatement(Zyjszfsb.toString());				
			rs=pst.executeQuery();
			hrperson.setSpeTechPos("");
			while (rs.next()) {
				hrperson.setSpeTechPos(rs.getString("fname_l2"));
			}
		} catch (SQLException e) {	
			System.out.println(e);
			System.out.println("....取专业技术职务数据异常....");
		}finally {
			C3P0Utils.closeAll(dbconnection,pst,rs);	
		}
		return hrperson;
	}

	@Override
	public HrPerson personFamilyMembers(String personId, HrPerson hrperson) {
		StringBuffer Shgxsb=new StringBuffer();
		Shgxsb.append("select t2.FName_l2 Fpe,t1.FName_l2 Fxm,t1.Fbirthday,t3.FName_l2 Fzzmm,t1.FworkUnit from T_HR_PersonFamily t1 \n");
		Shgxsb.append("left join T_HR_BDRelation t2 on t1.FRelationID=t2.FID left join T_BD_HRPolitical t3 on t1.FpoliticalFaceID=t3.FID\n");
		Shgxsb.append("where t1.fpersonid='"+personId+"'\n");
		Shgxsb.append("order by t2.FNumber");
		Connection dbconnection=null;	
		PreparedStatement pst=null;
		ResultSet rs=null;
		List<FamilyMembers> fms=new ArrayList<FamilyMembers>();
		try {
			dbconnection = C3P0Utils.getConnection();
			pst = dbconnection.prepareStatement(Shgxsb.toString());				
			rs=pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				FamilyMembers familyMember=new FamilyMembers();
				if (i==1||i==2||i==3||i==4||i==5) {
					familyMember.setCw(rs.getString("Fpe"));
					familyMember.setName(rs.getString("Fxm"));
					familyMember.setPoltical(rs.getString("Fzzmm"));
					familyMember.setWorkCompanyPos(rs.getString("FworkUnit"));
					Date start = rs.getDate("Fbirthday");
					if (start != null) {
						Date end = new Date();
						Calendar c1 = Calendar.getInstance();
						Calendar c2 = Calendar.getInstance();
						c1.setTime(start);
						c2.setTime(end);
						int year1 = c1.get(1);
						int year2 = c2.get(1);
						Integer year = Integer.valueOf(Math.abs(year1 - year2));
						familyMember.setAge(year.toString());
					} else {
						familyMember.setAge("");
					}   
				}
				fms.add(familyMember);
				++i;
			}
			hrperson.setFamilyMembers(fms);
		} catch (SQLException e) {	
			System.out.println(e);
			System.out.println("....取家庭成员数据异常....");
		}finally {
			C3P0Utils.closeAll(dbconnection,pst,rs);	
		}
		return hrperson;
	}

	@Override
	public HrPerson personJl(String personId, HrPerson hrperson) {
		StringBuffer buffer =new StringBuffer("");
		//学历
		String sqlCv1 = "select t1.FenrollDate,t1.FgraduateDate,t1.FdegreeUnit,t1.fgraduateSchool,t1.Fspecialty,t3.fname_l2 "
				+ "from T_HR_PersonDegree t1 left join t_bd_hrdiploma t3 on t1.fdiploma=t3.fid left join T_BD_HRDegree t4 "
				+ "on t1.fdegree=t4.fid where t1.FPersonID='"+personId+"' "
				+ "and (t1.FIsHighestBefore=1 or t1.FIsHighWorkDip=1) order by t3.FNumber asc";
		Connection dbconnection=null;	
		PreparedStatement pst=null;
		ResultSet rs=null;
		PreparedStatement pst02=null;
		ResultSet rs02=null;
		PreparedStatement pst03=null;
		ResultSet rs03=null;
		try {
			dbconnection = C3P0Utils.getConnection();
			pst = dbconnection.prepareStatement(sqlCv1);				
			rs=pst.executeQuery();
			while (rs.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
				SimpleDateFormat sdfFilter = new SimpleDateFormat(
						"yyyy-MM-dd");
				String endString = "";
				String strStart = "1900-01-01";
				String strEnd = "1900-01-01";
				if (rs.getDate("FenrollDate") != null) {
					strStart = sdfFilter.format(rs.getDate("FenrollDate"));
				}
				if (rs.getDate("FgraduateDate") != null) {
					strEnd = sdfFilter.format(rs.getDate("FgraduateDate"));
					endString = sdf.format(rs.getDate("FgraduateDate"));
					if ("2199.12".equals(endString)) {
						endString = "至今";
					}
				}
				String str4 = "";
				if (rs.getString("fgraduateSchool") != null)
					str4 = rs.getString("fgraduateSchool");
				String str5 = "";
				if (rs.getString("Fspecialty") != null)
					str5 = rs.getString("Fspecialty");
				String str6 = "";
//				if (set.getString(6) != null)
//					str6 = set.getString(6);
				if (rs.getDate("FenrollDate") != null) {
					buffer.append(
							sdf.format(rs.getDate("FenrollDate")) + "-" + endString
									+ "  " + str4 + str5
									+ "；").append("<br/>");
				}				
				String sqlCv2qzjy = "select t1.FenrollDate,t1.FgraduateDate,t1.FgraduateSchool,t1.Fspecialty,t2.FName_l2 from T_HR_PersonDegree t1 inner join T_BD_HRDiplomaModality t2 on t1.FdiplomaModality=t2.fid where  t1.FPersonID='"
						+ personId
						+ "' and to_char(t1.FenrollDate,'yyyy-mm-dd')>='"
						+ strStart
						+ "' "
						+ "and to_char(t1.FgraduateDate,'yyyy-mm-dd')<='"
						+ strEnd + "' " + "order by t1.FenrollDate";
				pst02 = dbconnection.prepareStatement(sqlCv2qzjy);				
				rs02=pst02.executeQuery();					
					while (rs02.next()) {
						buffer.append("    （期间：").append("<br/>");
						SimpleDateFormat sdfQj = new SimpleDateFormat(
								"yyyy.MM");
						String endStringQj = "";
						if (rs02.getDate("FgraduateDate") != null) {
							endStringQj = sdfQj.format(rs02.getDate("FgraduateDate"));
							if ("2199.12".equals(endStringQj)) {
								endStringQj = "至今";
							}
						}
						String str3Qj = "";
						if (rs02.getString("FgraduateSchool") != null)
							str3Qj = rs02.getString("FgraduateSchool");
						String str4Qj = "";
						if (rs02.getString("Fspecialty") != null)
							str4Qj = rs02.getString("Fspecialty");
						String str5Qj = "";
						if (rs02.getString("FName_l2") != null)
							str5Qj = rs02.getString("FName_l2");
						if (rs02.getDate("FenrollDate") == null)
							continue;
						buffer.append(
								"    " + sdfQj.format(rs02.getDate("FenrollDate"))
										+ "-" + endStringQj + "  " + str3Qj
										+ str4Qj + "，" + str5Qj + "；")
								.append("<br/>");
					}								
			}
			//职位简历
			
			String sqlCv2 = "select t1.FbeginDate,t1.FendDate,t1.FunitName,t1.FworkDept,t1.FPosition from T_HR_PersonWorkExp t1 where t1.FPersonID='"
					+ personId + "' order by t1.FbeginDate";
			pst03 = dbconnection.prepareStatement(sqlCv2);				
			rs03=pst03.executeQuery();
				while (rs03.next()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
					SimpleDateFormat sdfFilter = new SimpleDateFormat(
							"yyyy-MM-dd");
					String endString = "";
					String strStart = "1900-01-01";
					String strEnd = "1900-01-01";
					if (rs03.getDate("FbeginDate") != null) {
						strStart = sdfFilter.format(rs03.getDate("FbeginDate"));
					}
					if (rs03.getDate("FendDate") != null) {
						strEnd = sdfFilter.format(rs03.getDate("FendDate"));
						endString = sdf.format(rs03.getDate("FendDate"));
						if ("2199.12".equals(endString)) {
							endString = "至今";
						}
					}else{
						endString = "至今";
					}
					String str3 = "";
					if (rs03.getString("FunitName") != null)
						str3 = rs03.getString("FunitName");
					String str4 = "";
					if (rs03.getString("FworkDept") != null)
						str4 = rs03.getString("FworkDept");
					String str5 = "";
					if (rs03.getString("FPosition") != null)
						str5 = rs03.getString("FPosition");
					if (rs03.getDate("FbeginDate") == null)
						continue;
					buffer.append(
							sdf.format(rs03.getDate("FbeginDate")) + "-" + endString + "  "
									+ str3 + str4  + str5 + "；").append(
							"<br/>");
				}
				hrperson.setJl(buffer.toString());	
		} catch (SQLException e) {	
			System.out.println(e);
			System.out.println("....取简历数据异常....");
		}finally {
			C3P0Utils.closeAll(dbconnection,pst,rs,pst02,pst03,rs02,rs03);	
		}
		return hrperson;
	}

	@Override
	public HrPerson personJcqk(String personId, HrPerson hrperson) {
		StringBuffer jcString =new StringBuffer("");
		//奖励情况
		String sqlj = "select CFJlsj,cfjlmc from CT_MP_SHJLQK where FPersonID='"+personId+"' order by CFJlsj asc";
		Connection dbconnection=null;	
		PreparedStatement pst=null;
		ResultSet rs=null;
		PreparedStatement pst02=null;
		ResultSet rs02=null;
		try {
			dbconnection = C3P0Utils.getConnection();
			pst = dbconnection.prepareStatement(sqlj);				
			rs=pst.executeQuery();
			while (rs.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
				SimpleDateFormat sdfFilter = new SimpleDateFormat(
						"yyyy-MM-dd");					
				String strStart = "1900-01-01";
				String strEnd = "1900-01-01";
				if (rs.getDate("CFJlsj") != null) {
					strStart = sdfFilter.format(rs.getDate("CFJlsj"));
				}									
				String str2 = "";
				if (rs.getString("cfjlmc") != null)
					str2 = rs.getString("cfjlmc");
				jcString.append(str2  + "；").append(
						"<br/>");			
			}
			//处分信息表
			String sqlc = "select CFCfsj,cfcfmc from CT_MP_SSCFQK where FPersonID='"+personId+"' order by CFCfsj asc";
			pst02 = dbconnection.prepareStatement(sqlc);				
			rs02=pst02.executeQuery();
			while (rs02.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
				SimpleDateFormat sdfFilter = new SimpleDateFormat(
						"yyyy-MM-dd");					
				String strStart = "1900-01-01";
				String strEnd = "1900-01-01";
				if (rs02.getDate("CFCfsj") != null) {
					strStart = sdfFilter.format(rs02.getDate("CFCfsj"));
				}									
				String str2 = "";
				if (rs02.getString("cfcfmc") != null)
					str2 = rs02.getString("cfcfmc");
				jcString.append(str2  + "；").append(
						"<br/>");
			}
			hrperson.setJcqk(jcString.toString());
		} catch (SQLException e) {	
			System.out.println(e);
			System.out.println("....取奖惩数据异常....");
		}finally {
			C3P0Utils.closeAll(dbconnection,pst,rs,pst02,rs02);	
		}
		return hrperson;
	}

	@Override
	public HrPerson personKhqk(String personId, HrPerson hrperson) {
		StringBuffer approveString =new StringBuffer("");
		String sqlapprove = "select t1.CFKpnd,t2.FName_l2 from CT_MP_NDKPQK t1 left join CT_BD_NDKPKHJG t2 on t1.cfkhjgid=t2.fid "
				+ "where FPersonID='"+personId+"' order by t1.CFKpnd asc";
		Connection dbconnection=null;	
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			dbconnection = C3P0Utils.getConnection();
			pst = dbconnection.prepareStatement(sqlapprove);				
			rs=pst.executeQuery();
			while (rs.next()) {				
				int str1 = 0;
				if (rs.getString("CFKpnd") != null)
					str1 = rs.getInt("CFKpnd");
				if (rs.getString("FName_l2") == null)
					continue;
				approveString.append(str1+"年考核结果为"+rs.getString("FName_l2")  
											+ "；").append(
						"<br/>");
			}
			hrperson.setKhqk(approveString.toString());
		} catch (SQLException e) {	
			System.out.println(e);
			System.out.println("....取考核数据异常....");
		}finally {
			C3P0Utils.closeAll(dbconnection,pst,rs);	
		}
		return hrperson;
	}
}
