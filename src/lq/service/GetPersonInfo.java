package lq.service;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import lq.model.OrgInfo;
import lq.model.Person;
import lq.model.gzjlInfo;
import lq.util.BlobAndBase64Util;
import lq.util.C3P0Utils;

public class GetPersonInfo {

	public Person addPerson(String personId,String type){
		Person person = new Person();
		Connection dbconnection=null;	
		PreparedStatement pst=null;
		ResultSet rs=null;
		PreparedStatement zzmmpst=null;
		ResultSet zzmmrs=null;
		PreparedStatement sxzytcpst=null;
		ResultSet sxzytcrs=null;
		PreparedStatement csdjgsbpst=null;
		ResultSet csdjgsbrs=null;
		PreparedStatement qrzjypst=null;
		ResultSet qrzjyrs=null;
		PreparedStatement zzjypst=null;
		ResultSet zzjyrs=null;
		PreparedStatement Zyjszfsbpst=null;
		ResultSet Zyjszfsbrs=null;
		PreparedStatement Xrzwsbpst=null;
		ResultSet Xrzwsbrs=null;
		PreparedStatement Shgxsbpst=null;
		ResultSet Shgxsbrs=null;
		PreparedStatement sqlCv1pst=null;
		ResultSet sqlCv1rs=null;
		PreparedStatement sqlCv2pst=null;
		ResultSet sqlCv2rs=null;
		PreparedStatement sqljpst=null;
		ResultSet sqljrs=null;
		PreparedStatement sqcfpst=null;
		ResultSet sqcfrs=null;
		PreparedStatement sqlapprovepst=null;
		ResultSet sqlapprovers=null;
		Statement sqlCv2Statement=null;
		Statement xrzwStatement=null;
		try {
			SimpleDateFormat shgx = new SimpleDateFormat("yyyyMM");
			//姓名、性别、出生日期、民族、健康状况
			StringBuffer pbas=new StringBuffer();
			pbas.append("select ptm.fncell,p.FIDCardNO,p.fname_l2 pname,case when p.FGENDER=1 then '男' when p.FGENDER=2 then '女' end gender,p.fbirthday bir,t2.FName_L2 Fmz,t4.FName_l2 Fjk\n");
			pbas.append("from t_bd_person p left join T_bd_hrhealth t4 on p.FHealthID=t4.FID left join T_BD_HRFolk t2 on p.FFolkID=t2.FID left join T_HR_PersonContactMethod ptm on ptm.fpersonid=p.fid\n");
			pbas.append("where p.fid='"+personId+"'");			
			dbconnection = C3P0Utils.getConnection();
			pst = dbconnection.prepareStatement(pbas.toString());				
			rs=pst.executeQuery();
			while (rs.next()) {
				person.setIdcard(rs.getString("FIDCardNO"));
				person.setCell(rs.getString("fncell"));
				if(rs.getString("Fmz")!=null){
					person.setMz(rs.getString("Fmz"));
				}
				if(rs.getString("Fjk")!=null){
					person.setHealth(rs.getString("Fjk"));
				}
				person.setName(rs.getString("pname"));
				person.setSex(rs.getString("gender"));
				java.util.Date bStart = rs.getDate("bir");
				if (bStart != null) {
					java.util.Date end = new java.util.Date();
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
					person.setBirthday(shgx.format(bStart) + "<w:br />（"
							+ year.toString() + "岁）");
				}
			}
			//政治面貌			
			StringBuffer zzmmsb=new StringBuffer();
			zzmmsb.append("select t3.CFCjdpsj,t3.CFZzmmID,hl.fname_l2\n");
			zzmmsb.append("from T_BD_Person t1 \n");
			zzmmsb.append("left join CT_MP_YGZZMMYQ t3 on t1.fid=t3.FPERSONID \n");
			zzmmsb.append("left join T_BD_HRPolitical hl on hl.fid=t3.CFZzmmID \n");
			zzmmsb.append("where t1.fid='"+personId+"'");		
			dbconnection = C3P0Utils.getConnection();
			zzmmpst = dbconnection.prepareStatement(zzmmsb.toString());				
			zzmmrs=zzmmpst.executeQuery();
			while (zzmmrs.next()) {	
				//员工调动审批表使用：zzmm
				person.setZzmm(zzmmrs.getString("fname_l2"));
				String zzmm = zzmmrs.getString("CFZzmmID");
				if (("5a5df0ce-0101-1000-e000-7b4ac0a83c7f791DBAAB".toString()	 
						.equalsIgnoreCase(zzmm))
						&& (zzmmrs.getDate("CFCjdpsj") != null)) {
					String rdsj=shgx.format(zzmmrs.getDate("CFCjdpsj"));
					person.setRdTime(rdsj);
				}							
			}
			//熟悉专业特长
			StringBuffer sxzytc=new StringBuffer();
			sxzytc.append("select CFSxzyyhtc from t_bd_person\n");
			sxzytc.append("where fid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			sxzytcpst = dbconnection.prepareStatement(sxzytc.toString());				
			sxzytcrs=sxzytcpst.executeQuery();
			StringBuffer mutc=new StringBuffer();
			while (sxzytcrs.next()) {
				mutc.append(sxzytcrs.getString("CFSxzyyhtc"));
			}
			person.setTc(mutc.toString());
			//出生地和籍贯
			StringBuffer csdjgsb=new StringBuffer();			
			csdjgsb.append("select jg.fname_l2 jg,csd.fname_l2 csd,pp.FJobStartDate from t_bd_person p left join T_HR_PersonOtherInfo pp on pp.fpersonid=p.fid left join CT_MP_Hujdz jg on p.cfjgid=jg.fid \n");
			csdjgsb.append("left join CT_MP_Hujdz csd on csd.fid=p.cfcsdid where p.fid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			csdjgsbpst = dbconnection.prepareStatement(csdjgsb.toString());				
			csdjgsbrs=csdjgsbpst.executeQuery();
			while (csdjgsbrs.next()) {
				if(csdjgsbrs.getString("jg")!=null){
					person.setJg(csdjgsbrs.getString("jg"));
				}
				if(csdjgsbrs.getString("csd")!=null){
					person.setBirthAddress(csdjgsbrs.getString("csd"));
				}
				if (csdjgsbrs.getDate("FJobStartDate") != null) {
					person.setWorkTime(shgx.format(csdjgsbrs.getDate("FJobStartDate")));
				}
			}			
			//全日制教育ֵ
			StringBuffer qrzjy=new StringBuffer();
			qrzjy.append("select t2.FName_l2 xz,nvl(t1.cfqrzyxzy,'无') yx,nvl(t1.cfqrzzy,'无') zy from t_bd_person t1\n");
			qrzjy.append("left join T_BD_HRDiploma t2 on t1.cfqrzjyid=t2.fid\n");
			qrzjy.append("where t1.fid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			qrzjypst = dbconnection.prepareStatement(qrzjy.toString());				
			qrzjyrs=qrzjypst.executeQuery();
			String xz=" ";
			String yx=" ";
			String zy=" ";
			while (qrzjyrs.next()) {
				if (qrzjyrs.getString("xz")!=null){
					xz=qrzjyrs.getString("xz");
				}
				if (qrzjyrs.getString("yx").equals("无")){
					yx=" ";
				}else{
					yx=qrzjyrs.getString("yx");
				}
				if(qrzjyrs.getString("zy").equals("无")){
					zy=" ";
				}else{
					zy=qrzjyrs.getString("zy");
				}
			}
			person.setQrjy(xz);
			person.setQrSchool(yx+zy);
			//在职教育
			StringBuffer zzjy=new StringBuffer();
			zzjy.append("select t2.FName_l2 xz,nvl(t1.cfzzyxzy,'无') yx,nvl(t1.cfzzzy,'无') zy from t_bd_person t1  \n");
			zzjy.append("left join T_BD_HRDiploma t2 on t1.cfzzjyid=t2.fid \n");
			zzjy.append("where t1.fid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			zzjypst = dbconnection.prepareStatement(zzjy.toString());				
			zzjyrs=zzjypst.executeQuery();
			String zzxz=" ";
			String zzyx=" ";
			String zzzy=" ";
			while (zzjyrs.next()) {
				if (zzjyrs.getString("xz")!=null){
					xz=zzjyrs.getString("xz");
				}
				if (zzjyrs.getString("yx").equals("无")){
					yx=" ";
				}else{
					yx=zzjyrs.getString("yx");
				}
				if(zzjyrs.getString("zy").equals("无")){
					zy=" ";
				}else{
					zy=zzjyrs.getString("zy");
				}
			}
			person.setZzjy(zzxz);
			person.setZzSchool(zzyx+zzzy);
			//专业技术职务
			String zyP=" ";
			StringBuffer Zyjszfsb=new StringBuffer();
			Zyjszfsb.append("select mz.fname_l2 zy from T_HR_PersonTechPost tp left join CT_MP_Zyjszwzg03 mz on tp.CFZyjszwzgmcID=mz.fid\n");
			Zyjszfsb.append("where tp.FisHighTechnical=1 and tp.fpersonid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			Zyjszfsbpst = dbconnection.prepareStatement(Zyjszfsb.toString());				
			Zyjszfsbrs=Zyjszfsbpst.executeQuery();
			person.setZyPosition(" ");
			while (Zyjszfsbrs.next()) {
				if (!Zyjszfsbrs.getString("zy").equals("其他_无职称")) {
					zyP=Zyjszfsbrs.getString("zy");
				}			
			}
			person.setZyPosition(zyP);
			//现任职务 FLEFFDT小于等于2199-12-31的所有职位
			StringBuffer nPosition=new StringBuffer();
			StringBuffer Xrzwsb=new StringBuffer();
			Xrzwsb.append("select t7.fname_l2 o1name,t7.FLayerTypeID o1fid,t6.fname_l2 o2name,t6.FLayerTypeID o2fid,\n"); 
			Xrzwsb.append("t5.fname_l2 o3name,t5.FLayerTypeID o3fid,t4.fname_l2 o4name,t4.FLayerTypeID o4fid,t3.fname_l2 o5name,t3.FLayerTypeID o5fid,cany.fname_l2 company,t1.FAssignType,\n"); 
			Xrzwsb.append("t2.FName_l2 pname from T_HR_EmpOrgRelation t1\n"); 
			Xrzwsb.append("left join T_ORG_Position t2 on t1.FPositionID=t2.FID\n"); 
			Xrzwsb.append("left join t_org_admin t3 on t3.fid=t1.fadminorgid\n"); 
			Xrzwsb.append("left join t_org_admin t4 on t4.fid=t3.fparentid\n"); 
			Xrzwsb.append("left join t_org_admin t5 on t5.fid=t4.fparentid\n"); 
			Xrzwsb.append("left join t_org_admin t6 on t6.fid=t5.fparentid\n"); 
			Xrzwsb.append("left join t_org_admin t7 on t7.fid=t6.fparentid\n"); 
			Xrzwsb.append("left join t_org_admin cany on cany.fid=t3.fcompanyid\n"); 
			Xrzwsb.append("where TO_CHAR(t1.FLEFFDT, 'YYYY-MM-DD')>To_Char(SYSDATE,'YYYY-MM-DD')\n");
			Xrzwsb.append("and t1.fpersonid='"+personId+"'");			
			dbconnection = C3P0Utils.getConnection();
			xrzwStatement = dbconnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Xrzwsbrs=xrzwStatement.executeQuery(Xrzwsb.toString());
//			Xrzwsbpst = dbconnection.prepareStatement(Xrzwsb.toString());				
//			Xrzwsbrs=Xrzwsbpst.executeQuery();
			person.setNowPosition("");
			int xrrow=1;
			if (Xrzwsbrs!=null) {
				Xrzwsbrs.last();
				int row = Xrzwsbrs.getRow();
				Xrzwsbrs.beforeFirst();			
				while (Xrzwsbrs.next()) {				
					if(Xrzwsbrs.getInt("FAssignType")==1){
						if (Xrzwsbrs.getString("company")!=null) {
							if (Xrzwsbrs.getString("company").contains("本部机关")) {							
								String company=Xrzwsbrs.getString("company").substring(0, Xrzwsbrs.getString("company").length()-4);
								person.setCompany(company);
								person.setBmzw(Xrzwsbrs.getString("o5name")+Xrzwsbrs.getString("pname"));
							}else if(Xrzwsbrs.getString("company").contains("机关")){
								String company=Xrzwsbrs.getString("company").substring(0, Xrzwsbrs.getString("company").length()-2);
								person.setCompany(company);
								person.setBmzw(Xrzwsbrs.getString("o5name")+Xrzwsbrs.getString("pname"));
							}						
						}					
					}
					ArrayList<OrgInfo> arrayList = new ArrayList<OrgInfo>();
					OrgInfo orgInfo5 = new OrgInfo();
					orgInfo5.setOrgTypeid(Xrzwsbrs.getString("o5fid"));
					orgInfo5.setOrgName(Xrzwsbrs.getString("o5name"));
					arrayList.add(orgInfo5);
					OrgInfo orgInfo4 = new OrgInfo();
					orgInfo4.setOrgTypeid(Xrzwsbrs.getString("o4fid"));
					orgInfo4.setOrgName(Xrzwsbrs.getString("o4name"));
					arrayList.add(orgInfo4);
					OrgInfo orgInfo3 = new OrgInfo();
					orgInfo3.setOrgTypeid(Xrzwsbrs.getString("o3fid"));
					orgInfo3.setOrgName(Xrzwsbrs.getString("o3name"));
					arrayList.add(orgInfo3);
					OrgInfo orgInfo2 = new OrgInfo();
					orgInfo2.setOrgTypeid(Xrzwsbrs.getString("o2fid"));
					orgInfo2.setOrgName(Xrzwsbrs.getString("o2name"));
					arrayList.add(orgInfo2);
					OrgInfo orgInfo1 = new OrgInfo();
					orgInfo1.setOrgTypeid(Xrzwsbrs.getString("o1fid"));
					orgInfo1.setOrgName(Xrzwsbrs.getString("o1name"));
					arrayList.add(orgInfo1);
					for (int i = 0; i < arrayList.size(); i++) {
						OrgInfo orgInfo = arrayList.get(i);
						if (!orgInfo.getOrgTypeid().equals("00000000-0000-0000-0000-00000000000362824988")&&!orgInfo.getOrgTypeid().equals("AlkAAAAABA5igkmI")
								&&!orgInfo.getOrgTypeid().equals("AlkAAAAABBVigkmI")) {
							if (orgInfo.getOrgName().contains("机关")) {
								continue;
							}
							if (row==1) {
//								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("o5name")+Xrzwsbrs.getString("pname"));
								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("pname"));
							}else if(xrrow==row){
//								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("o5name")+Xrzwsbrs.getString("pname"));
								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("pname"));
							}else{
//								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("o5name")+Xrzwsbrs.getString("pname")).append("<w:br />");
								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("pname")).append("<w:br />");
							}							
							break;
						}
					}	
					xrrow++;
				}
			}
			person.setNowPosition(nPosition.toString());
			//社会关系			
			StringBuffer Shgxsb=new StringBuffer();
			Shgxsb.append("select t2.FName_l2 Fpe,t1.FName_l2 Fxm,t1.Fbirthday,t3.FName_l2 Fzzmm,t1.FworkUnit from T_HR_PersonFamily t1 \n");
			Shgxsb.append("left join T_HR_BDRelation t2 on t1.FRelationID=t2.FID left join T_BD_HRPolitical t3 on t1.FpoliticalFaceID=t3.FID\n");
			Shgxsb.append("where t1.fpersonid='"+personId+"'\n");
			Shgxsb.append("order by t2.FNumber");			
			dbconnection = C3P0Utils.getConnection();
			Shgxsbpst = dbconnection.prepareStatement(Shgxsb.toString());				
			Shgxsbrs=Shgxsbpst.executeQuery();
			int i = 1;
			while (Shgxsbrs.next()) {
				if (i == 1) {
					person.setLongName1(Shgxsbrs.getString("Fpe"));
					person.setName1(Shgxsbrs.getString("Fxm"));
					person.setZzmm1(Shgxsbrs.getString("Fzzmm"));
					if(Shgxsbrs.getString("FworkUnit")==null){
						person.setPosition1(" ");
					}else{
						person.setPosition1(Shgxsbrs.getString("FworkUnit"));
					}
					java.util.Date start = Shgxsbrs.getDate("Fbirthday");
					if (start != null) {
						java.util.Date end = new java.util.Date();
						Calendar c1 = Calendar.getInstance();
						Calendar c2 = Calendar.getInstance();
						c1.setTime(start);
						c2.setTime(end);
						int year1 = c1.get(1);
						int year2 = c2.get(1);
						Integer year = Integer.valueOf(Math.abs(year1 - year2));
						person.setAge1(shgx.format(start));
					} else {
						person.setAge1("");
					}
				} else if (i == 2) {
					person.setLongName2(Shgxsbrs.getString("Fpe"));
					person.setName2(Shgxsbrs.getString("Fxm"));
					person.setZzmm2(Shgxsbrs.getString("Fzzmm"));
					if(Shgxsbrs.getString("FworkUnit")==null){
						person.setPosition2(" ");
					}else{
						person.setPosition2(Shgxsbrs.getString("FworkUnit"));
					}
					java.util.Date start = Shgxsbrs.getDate("Fbirthday");
					if (start != null) {
						java.util.Date end = new java.util.Date();
						Calendar c1 = Calendar.getInstance();
						Calendar c2 = Calendar.getInstance();
						c1.setTime(start);
						c2.setTime(end);
						int year1 = c1.get(1);
						int year2 = c2.get(1);
						Integer year = Integer.valueOf(Math.abs(year1 - year2));
						person.setAge2(shgx.format(start));
					} else {
						person.setAge2("");
					}
				} else if (i == 3) {
					person.setLongName3(Shgxsbrs.getString("Fpe"));
					person.setName3(Shgxsbrs.getString("Fxm"));
					person.setZzmm3(Shgxsbrs.getString("Fzzmm"));
					if(Shgxsbrs.getString("FworkUnit")==null){
						person.setPosition3(" ");
					}else{
						person.setPosition3(Shgxsbrs.getString("FworkUnit"));
					}
					java.util.Date start = Shgxsbrs.getDate("Fbirthday");
					if (start != null) {
						java.util.Date end = new java.util.Date();
						Calendar c1 = Calendar.getInstance();
						Calendar c2 = Calendar.getInstance();
						c1.setTime(start);
						c2.setTime(end);
						int year1 = c1.get(1);
						int year2 = c2.get(1);
						Integer year = Integer.valueOf(Math.abs(year1 - year2));
						person.setAge3(shgx.format(start));
					} else {
						person.setAge3("");
					}
				} else if (i == 4) {
					person.setLongName4(Shgxsbrs.getString("Fpe"));
					person.setName4(Shgxsbrs.getString("Fxm"));
					person.setZzmm4(Shgxsbrs.getString("Fzzmm"));
					if(Shgxsbrs.getString("FworkUnit")==null){
						person.setPosition4(" ");
					}else{
						person.setPosition4(Shgxsbrs.getString("FworkUnit"));
					}
					java.util.Date start = Shgxsbrs.getDate("Fbirthday");
					if (start != null) {
						java.util.Date end = new java.util.Date();
						Calendar c1 = Calendar.getInstance();
						Calendar c2 = Calendar.getInstance();
						c1.setTime(start);
						c2.setTime(end);
						int year1 = c1.get(1);
						int year2 = c2.get(1);
						Integer year = Integer.valueOf(Math.abs(year1 - year2));
						person.setAge4(shgx.format(start));
					} else {
						person.setAge4("");
					}
				} else if (i == 5) {
					person.setLongName5(Shgxsbrs.getString("Fpe"));
					person.setName5(Shgxsbrs.getString("Fxm"));
					person.setZzmm5(Shgxsbrs.getString("Fzzmm"));
					if(Shgxsbrs.getString("FworkUnit")==null){
						person.setPosition5(" ");
					}else{
						person.setPosition5(Shgxsbrs.getString("FworkUnit"));
					}
					java.util.Date start = Shgxsbrs.getDate("Fbirthday");
					if (start != null) {
						java.util.Date end = new java.util.Date();
						Calendar c1 = Calendar.getInstance();
						Calendar c2 = Calendar.getInstance();
						c1.setTime(start);
						c2.setTime(end);
						int year1 = c1.get(1);
						int year2 = c2.get(1);
						Integer year = Integer.valueOf(Math.abs(year1 - year2));
						person.setAge5(shgx.format(start));
					} else {
						person.setAge5("");
					}   
				}
				++i;
			}
			//学历简历
			ArrayList<gzjlInfo> gzjlList = new ArrayList<gzjlInfo>();
			StringBuffer buffer =new StringBuffer("");
			String sqlCv1 = "select t1.FenrollDate,t1.FgraduateDate,t1.FdegreeUnit,t1.fgraduateSchool,t1.Fspecialty,t3.fname_l2 "
					+ "from T_HR_PersonDegree t1 left join t_bd_hrdiploma t3 on t1.fdiploma=t3.fid left join T_BD_HRDegree t4 "
					+ "on t1.fdegree=t4.fid where t1.FPersonID='"+personId+"' "
					+ "order by t1.FgraduateDate ";			
			dbconnection = C3P0Utils.getConnection();
			sqlCv1pst = dbconnection.prepareStatement(sqlCv1);				
			sqlCv1rs=sqlCv1pst.executeQuery();
				while (sqlCv1rs.next()) {
					if (sqlCv1rs.getDate("FenrollDate") != null&&sqlCv1rs.getDate("FgraduateDate") != null) {
					gzjlInfo xlInfo=new gzjlInfo();
					xlInfo.setPos("");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
					String endString = "";
					String strStart = "190001";
					if (sqlCv1rs.getDate("FenrollDate") != null) {
						strStart = sdf.format(sqlCv1rs.getDate("FenrollDate"));
						xlInfo.setBengindate(strStart);
					}
					if (sqlCv1rs.getDate("FgraduateDate") != null) {
						endString = sdf.format(sqlCv1rs.getDate("FgraduateDate"));
						xlInfo.setEnddate(endString);
						if ("2199.12".equals(endString)) {
							endString = "至今";
							xlInfo.setEnddate(endString);
						}
					}
					String str4 = "";
					if (sqlCv1rs.getString("fgraduateSchool") != null)
						str4 = sqlCv1rs.getString("fgraduateSchool");
						xlInfo.setComp(str4);
					String str5 = "";
					if (sqlCv1rs.getString("Fspecialty") != null)
						str5 = sqlCv1rs.getString("Fspecialty");
						xlInfo.setOrg(str5);
					String str6 = "";
					if (sqlCv1rs.getDate("FenrollDate") != null) {
						buffer.append(
								sdf.format(sqlCv1rs.getDate("FenrollDate")) + "-" + endString
										+ " " + str4 + str5).append("<w:br />");						
					}
					gzjlList.add(xlInfo);
					}
				}
				
			StringBuffer ddcv2=new StringBuffer("");
//			ddcv2.append(buffer.toString());
			//员工变动记录  type: 1=需要变动记录   2=不需要变动记录
			if (type.equals("1")) {
				gzjlList = getEmpOrgRelation(personId,gzjlList);
			}			
			StringBuffer ddcv=new StringBuffer("");	
//			ddcv=getEmpOrgRelation(personId);
			//ְ职位简历
			String sqlCv2 = "select t1.FbeginDate,t1.FendDate,t1.FunitName,t1.FworkDept,t1.FPosition from T_HR_PersonWorkExp t1 where t1.FPersonID='"
					+ personId + "' order by t1.FbeginDate ";			
			dbconnection = C3P0Utils.getConnection();			
//			sqlCv2pst = dbconnection.prepareStatement(sqlCv2);
//			sqlCv2rs=sqlCv2pst.executeQuery();
			sqlCv2Statement = dbconnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			sqlCv2rs=sqlCv2Statement.executeQuery(sqlCv2);
			sqlCv2rs.last();
			int cd = sqlCv2rs.getRow();
			int k=1;
			sqlCv2rs.beforeFirst();
				while (sqlCv2rs.next()) {
					gzjlInfo info=new gzjlInfo();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
					String endString = "";
					String strStart = "190001";
					if (sqlCv2rs.getDate("FbeginDate") != null) {
						strStart = sdf.format(sqlCv2rs.getDate("FbeginDate"));
						info.setBengindate(strStart);
					}
					if (sqlCv2rs.getDate("FendDate") != null) {
						endString = sdf.format(sqlCv2rs.getDate("FendDate"));
						info.setEnddate(endString);
						if ("2199.12".equals(endString)) {
							endString = "至今";
							info.setEnddate(endString);
						}
					}else{
						endString = "至今";
						info.setEnddate(endString);
					}
					String str3 = "";
					if (sqlCv2rs.getString("FunitName") != null)
					 	str3 = sqlCv2rs.getString("FunitName");
					    info.setComp(str3);
					String str4 = "";
					if (sqlCv2rs.getString("FworkDept") != null)
						str4 = sqlCv2rs.getString("FworkDept");
					    info.setOrg(str4);
					String str5 = "";
					if (sqlCv2rs.getString("FPosition") != null)
						str5 = sqlCv2rs.getString("FPosition");
					    info.setPos(str5);
					if (sqlCv2rs.getDate("FbeginDate") == null)
						continue;
					
					gzjlList.add(info);
					if (k==cd) {
						buffer.append(sdf.format(sqlCv2rs.getDate("FbeginDate")) + "-" + endString + " "
								+ str3 + str4  + str5+"；");	
						ddcv.append(sdf.format(sqlCv2rs.getDate("FbeginDate")) + "-" + endString + " "
									+ str3 + str4  + str5+"；");
					}else{
						buffer.append(sdf.format(sqlCv2rs.getDate("FbeginDate")) + "-" + endString + " "
								+ str3 + str4  + str5+"；").append(
								"<w:br/>");	
						ddcv.append(sdf.format(sqlCv2rs.getDate("FbeginDate")) + "-" + endString + " "
									+ str3 + str4  + str5+"；").append(
									"<w:br/>");
					}					
					k++;
				}
			//相同职位合并日期
			ArrayList<gzjlInfo> tempgzjlList = gzjlList;
//			for (int a = 0; a < tempgzjlList.size() - 1; a++) {
//		            for (int b = a+1; b < tempgzjlList.size(); b++) {
//		            	gzjlInfo gzjlInfo = tempgzjlList.get(a);
//		            	gzjlInfo gzjlInfo2 = tempgzjlList.get(b);
//		            	if ((gzjlInfo.getComp()+gzjlInfo.getOrg()+gzjlInfo.getPos()).equals(
//		            			(gzjlInfo2.getComp()+gzjlInfo2.getOrg()+gzjlInfo2.getPos()))) {
//		            		int compareTo = gzjlInfo.getBengindate().compareTo(gzjlInfo2.getBengindate());
//		            		if (compareTo>0) {
//		            			tempgzjlList.get(a).setBengindate(gzjlInfo2.getBengindate());
//							}else if(compareTo<0){
//								tempgzjlList.get(b).setBengindate(gzjlInfo.getBengindate());
//							}
//		            		int compareTo2 = gzjlInfo.getEnddate().compareTo(gzjlInfo2.getEnddate());
//		            		if (compareTo2>0) {
//		            			tempgzjlList.get(b).setEnddate(gzjlInfo.getEnddate());
//							}else if(compareTo2<0){
//								tempgzjlList.get(a).setEnddate(gzjlInfo2.getEnddate());
//							}
//		                }
//		            }
//		     }
			//按开始日期排序
			if (tempgzjlList.size()>1) {
				tempgzjlList.sort(Comparator.comparing(gzjlInfo::getBengindate));
			}
//			ArrayList resultList=new ArrayList();              //创建新集合
//			Iterator iterator=tempgzjlList.iterator();         //获取原集合的迭代器
//			while(iterator.hasNext()){                      //遍历原集合
//				Object obj=iterator.next();
//				if(!resultList.contains(obj)){                 //若不存在，则加入新集合
//					resultList.add(obj);
//				}
//			}
	        gzjlList=tempgzjlList;
			int p=1;
			int le=gzjlList.size();
			for (int j = 0; j < gzjlList.size(); j++) {
				gzjlInfo gzjlInfo = gzjlList.get(j);
				if (p==le) {
					ddcv2.append(gzjlInfo.getBengindate()+"-"+gzjlInfo.getEnddate()+" "+gzjlInfo.getComp()+gzjlInfo.getOrg()+gzjlInfo.getPos());
				} else {
					ddcv2.append(gzjlInfo.getBengindate()+"-"+gzjlInfo.getEnddate()+" "+gzjlInfo.getComp()+gzjlInfo.getOrg()+gzjlInfo.getPos()+"<w:br/>");
				}
				p++;
			}
			person.setCv(ddcv2);
			person.setDdcv(ddcv2);
		//奖励信息
			StringBuffer jcString =new StringBuffer("");
			String sqlj = "select CFJlsj,CFJlmc from CT_MP_SHJLQK where FPersonID='"+personId+"' order by CFJlsj asc";
			dbconnection = C3P0Utils.getConnection();
			sqljpst = dbconnection.prepareStatement(sqlj);				
			sqljrs=sqljpst.executeQuery();
				while (sqljrs.next()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
					SimpleDateFormat sdfFilter = new SimpleDateFormat(
							"yyyyMM");					
					String strStart = "190001";
					String strEnd = "190001";
					if (sqljrs.getDate("CFJlsj") != null) {
						strStart = sdf.format(sqljrs.getDate("CFJlsj"));
					}									
					String str2 = "";
					if (sqljrs.getString("CFJlmc") != null)
						str2 = sqljrs.getString("CFJlmc");
					jcString.append(strStart+" ").append(str2  + "；").append(
							"<w:br />");
				}
			//处分信息
			String sqcf = "select CFcfsj,CFcfmc from CT_MP_SSCFQK where FPersonID='"+personId+"' order by CFcfsj asc";
			dbconnection = C3P0Utils.getConnection();
			sqcfpst = dbconnection.prepareStatement(sqcf);
			sqcfrs=sqcfpst.executeQuery();
			while (sqcfrs.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
				SimpleDateFormat sdfFilter = new SimpleDateFormat(
						"yyyyMM");
				String strStart = "190001";
				String strEnd = "190001";
				if (sqcfrs.getDate("CFcfsj") != null) {
					strStart = sdf.format(sqcfrs.getDate("CFcfsj"));
				}
				String str2 = "";
				if (sqcfrs.getString("CFcfmc") != null&&!sqcfrs.getString("CFcfmc").equals("无"))
					str2 = sqcfrs.getString("CFcfmc");
				jcString.append(strStart+" ").append(str2  + "；").append(
						"<w:br />");
			}
			person.setJc(jcString.toString());
		
			//考核情况
			StringBuffer approveString =new StringBuffer("");
			String sqlapprove = "select t1.CFKpnd,t2.FName_l2 from CT_MP_NDKPQK t1 left join CT_BD_NDKPKHJG t2 on t1.cfkhjgid=t2.fid\n" +
					"where FPersonID='"+personId+"' order by t1.CFKpnd asc";
			dbconnection = C3P0Utils.getConnection();
			sqlapprovepst = dbconnection.prepareStatement(sqlapprove);				
			sqlapprovers=sqlapprovepst.executeQuery();
				while (sqlapprovers.next()) {									
					String str1 = "";
					if (sqlapprovers.getString("CFKpnd") != null)
						str1 = sqlapprovers.getString("CFKpnd");
					if (sqlapprovers.getString("FName_l2") == null)
						continue;
					approveString.append(str1+"年考核结果为"+sqlapprovers.getString("FName_l2")  
												+ "；").append(
							"<w:br />");
				}

			person.setApprove(approveString.toString());
			SimpleDateFormat ywsj = new SimpleDateFormat("yyyyMMdd");
			person.setJsnlsj(ywsj.format(new Date()));
			person.setTbsj(ywsj.format(new Date()));
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}finally {
			C3P0Utils.closeAll(xrzwStatement,sqlCv2Statement,dbconnection,pst,rs,zzmmpst,zzmmrs,sxzytcpst,sxzytcrs,csdjgsbpst,csdjgsbrs,qrzjypst,qrzjyrs,zzjypst,zzjyrs,Zyjszfsbpst,Zyjszfsbrs,Xrzwsbpst,Xrzwsbrs,Shgxsbpst,Shgxsbrs,sqlCv1pst,sqlCv1rs,sqlCv2pst,sqlCv2rs,sqcfpst,sqcfrs,sqljpst,sqljrs,sqlapprovepst,sqlapprovers);
		}
		return person;
	
	}
	
	public String getPersonImg(String personid) {
		StringBuffer imgString =new StringBuffer("");
		String imgsql = "select fimagedatasource from T_HR_PersonPhoto where fpersonid='"+personid+"'";
		PreparedStatement imgpst=null;
		ResultSet imgrs=null;
		Connection dbconnection = C3P0Utils.getConnection();
		try {
			imgpst = dbconnection.prepareStatement(imgsql);
			imgrs=imgpst.executeQuery();
			while (imgrs.next()) {									
				Blob blob = imgrs.getBlob("fimagedatasource");
				if (blob==null) {
					break;
				}
				String base64InBlob = BlobAndBase64Util.getBase64InBlob(blob);			
				imgString.append(base64InBlob);
			}									
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			C3P0Utils.closeAll(imgpst,imgrs,dbconnection);
		}					
		return imgString.toString();		
	}
	
	public ArrayList<gzjlInfo> getEmpOrgRelation(String personId,ArrayList<gzjlInfo> gzjllist) {
		StringBuffer ddcv =new StringBuffer("");
		StringBuffer empString =new StringBuffer("");
		empString.append("select t7.fname_l2 o1name,t7.FLayerTypeID o1fid,t6.fname_l2 o2name,t6.FLayerTypeID o2fid,\n"); 
		empString.append("t5.fname_l2 o3name,t5.FLayerTypeID o3fid,t4.fname_l2 o4name,t4.FLayerTypeID o4fid,t3.fname_l2 o5name,t3.FLayerTypeID o5fid,cany.fname_l2 company,t1.FAssignType,\n"); 
		empString.append("t2.FName_l2 pname,t1.FEFFDT,t1.FLEFFDT from T_HR_EmpOrgRelation t1\n"); 
		empString.append("left join T_ORG_Position t2 on t1.FPositionID=t2.FID\n"); 
		empString.append("left join t_org_admin t3 on t3.fid=t1.fadminorgid\n"); 
		empString.append("left join t_org_admin t4 on t4.fid=t3.fparentid\n"); 
		empString.append("left join t_org_admin t5 on t5.fid=t4.fparentid\n"); 
		empString.append("left join t_org_admin t6 on t6.fid=t5.fparentid\n"); 
		empString.append("left join t_org_admin t7 on t7.fid=t6.fparentid\n"); 
		empString.append("left join t_org_admin cany on cany.fid=t3.fcompanyid\n"); 
		empString.append("where t1.fpersonid='"+personId+"' order by t1.FEFFDT");
		PreparedStatement ddpst=null;
		ResultSet ddrs=null;
		Connection dbconnection = C3P0Utils.getConnection();
		try {
			ddpst = dbconnection.prepareStatement(empString.toString());
			ddrs=ddpst.executeQuery();
			while (ddrs.next()) {
			    gzjlInfo info=new gzjlInfo();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
				String endString = "";
				String strStart = "190001";
				String strEnd = "190001";
				if (ddrs.getDate("FEFFDT") != null) {
					strStart = sdf.format(ddrs.getDate("FEFFDT"));
					info.setBengindate(strStart);
				}
				if (ddrs.getDate("FLEFFDT") != null) {
					endString = sdf.format(ddrs.getDate("FLEFFDT"));
					info.setEnddate(endString);
					if ("2199.12".equals(endString)) {
						endString = "至今";
						info.setEnddate(endString);
					}
				}else{
					endString = "至今";
					info.setEnddate(endString);
				}
				ArrayList<OrgInfo> arrayList = new ArrayList<OrgInfo>();
				OrgInfo orgInfo5 = new OrgInfo();
				orgInfo5.setOrgTypeid(ddrs.getString("o5fid"));
				orgInfo5.setOrgName(ddrs.getString("o5name"));
				arrayList.add(orgInfo5);
				OrgInfo orgInfo4 = new OrgInfo();
				orgInfo4.setOrgTypeid(ddrs.getString("o4fid"));
				orgInfo4.setOrgName(ddrs.getString("o4name"));
				arrayList.add(orgInfo4);
				OrgInfo orgInfo3 = new OrgInfo();
				orgInfo3.setOrgTypeid(ddrs.getString("o3fid"));
				orgInfo3.setOrgName(ddrs.getString("o3name"));
				arrayList.add(orgInfo3);
				OrgInfo orgInfo2 = new OrgInfo();
				orgInfo2.setOrgTypeid(ddrs.getString("o2fid"));
				orgInfo2.setOrgName(ddrs.getString("o2name"));
				arrayList.add(orgInfo2);
				OrgInfo orgInfo1 = new OrgInfo();
				orgInfo1.setOrgTypeid(ddrs.getString("o1fid"));
				orgInfo1.setOrgName(ddrs.getString("o1name"));
				arrayList.add(orgInfo1);
				for (int i = 0; i < arrayList.size(); i++) {
					OrgInfo orgInfo = arrayList.get(i);
					if (!orgInfo.getOrgTypeid().equals("00000000-0000-0000-0000-00000000000362824988")&&!orgInfo.getOrgTypeid().equals("AlkAAAAABA5igkmI")
							&&!orgInfo.getOrgTypeid().equals("AlkAAAAABBVigkmI")) {
						if (orgInfo.getOrgName().contains("机关")) {
							continue;
						}
						info.setComp(orgInfo.getOrgName().toString());
						info.setOrg(ddrs.getString("o5name"));
						info.setPos(ddrs.getString("pname"));
						gzjllist.add(info);
						ddcv.append( strStart+ "-" + endString + " "+orgInfo.getOrgName().toString()+ddrs.getString("o5name")+ddrs.getString("pname")).append("<w:br/>");
						break;
					}
				}																											
			}									
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			C3P0Utils.closeAll(ddpst,ddrs,dbconnection);
		}					
		return gzjllist;		
	}

	public Person GrjladdPerson(String personId,String type){
		Person person = new Person();
		Connection dbconnection=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		PreparedStatement zzmmpst=null;
		ResultSet zzmmrs=null;
		PreparedStatement sxzytcpst=null;
		ResultSet sxzytcrs=null;
		PreparedStatement csdjgsbpst=null;
		ResultSet csdjgsbrs=null;
		PreparedStatement qrzjypst=null;
		ResultSet qrzjyrs=null;
		PreparedStatement zzjypst=null;
		ResultSet zzjyrs=null;
		PreparedStatement Zyjszfsbpst=null;
		ResultSet Zyjszfsbrs=null;
		PreparedStatement Xrzwsbpst=null;
		ResultSet Xrzwsbrs=null;
		PreparedStatement Shgxsbpst=null;
		ResultSet Shgxsbrs=null;
		PreparedStatement sqlCv1pst=null;
		ResultSet sqlCv1rs=null;
		PreparedStatement sqlCv2pst=null;
		ResultSet sqlCv2rs=null;
		PreparedStatement sqljpst=null;
		ResultSet sqljrs=null;
		PreparedStatement sqcfpst=null;
		ResultSet sqcfrs=null;
		PreparedStatement sqlapprovepst=null;
		ResultSet sqlapprovers=null;
		Statement sqlCv2Statement=null;
		Statement xrzwStatement=null;
		try {
			SimpleDateFormat shgx = new SimpleDateFormat("yyyyMM");
			//姓名、性别、出生日期、民族、健康状况
			StringBuffer pbas=new StringBuffer();
			pbas.append("select ptm.fncell,p.FIDCardNO,p.fname_l2 pname,case when p.FGENDER=1 then '男' when p.FGENDER=2 then '女' end gender,p.fbirthday bir,t2.FName_L2 Fmz,t4.FName_l2 Fjk\n");
			pbas.append("from t_bd_person p left join T_bd_hrhealth t4 on p.FHealthID=t4.FID left join T_BD_HRFolk t2 on p.FFolkID=t2.FID left join T_HR_PersonContactMethod ptm on ptm.fpersonid=p.fid\n");
			pbas.append("where p.fid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			pst = dbconnection.prepareStatement(pbas.toString());
			rs=pst.executeQuery();
			while (rs.next()) {
				person.setIdcard(rs.getString("FIDCardNO"));
				person.setCell(rs.getString("fncell"));
				if(rs.getString("Fmz")!=null){
					person.setMz(rs.getString("Fmz"));
				}
				if(rs.getString("Fjk")!=null){
					person.setHealth(rs.getString("Fjk"));
				}
				person.setName(rs.getString("pname"));
				person.setSex(rs.getString("gender"));
				java.util.Date bStart = rs.getDate("bir");
				if (bStart != null) {
					java.util.Date end = new java.util.Date();
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
					person.setBirthday(shgx.format(bStart) + "<w:br />（"
							+ year.toString() + "岁）");
				}
			}
			//政治面貌
			StringBuffer zzmmsb=new StringBuffer();
			zzmmsb.append("select t3.CFCjdpsj,t3.CFZzmmID,hl.fname_l2\n");
			zzmmsb.append("from T_BD_Person t1 \n");
			zzmmsb.append("left join CT_MP_YGZZMMYQ t3 on t1.fid=t3.FPERSONID \n");
			zzmmsb.append("left join T_BD_HRPolitical hl on hl.fid=t3.CFZzmmID \n");
			zzmmsb.append("where t1.fid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			zzmmpst = dbconnection.prepareStatement(zzmmsb.toString());
			zzmmrs=zzmmpst.executeQuery();
			while (zzmmrs.next()) {
				//员工调动审批表使用：zzmm
				person.setZzmm(zzmmrs.getString("fname_l2"));
				String zzmm = zzmmrs.getString("CFZzmmID");
				if (("5a5df0ce-0101-1000-e000-7b4ac0a83c7f791DBAAB".toString()
						.equalsIgnoreCase(zzmm))
						&& (zzmmrs.getDate("CFCjdpsj") != null)) {
					String rdsj=shgx.format(zzmmrs.getDate("CFCjdpsj"));
					person.setRdTime(rdsj);
				}
			}
			//熟悉专业特长
			StringBuffer sxzytc=new StringBuffer();
			sxzytc.append("select CFSxzyyhtc from t_bd_person\n");
			sxzytc.append("where fid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			sxzytcpst = dbconnection.prepareStatement(sxzytc.toString());
			sxzytcrs=sxzytcpst.executeQuery();
			StringBuffer mutc=new StringBuffer();
			while (sxzytcrs.next()) {
				mutc.append(sxzytcrs.getString("CFSxzyyhtc"));
			}
			person.setTc(mutc.toString());
			//出生地和籍贯
			StringBuffer csdjgsb=new StringBuffer();
			csdjgsb.append("select jg.fname_l2 jg,csd.fname_l2 csd,pp.FJobStartDate from t_bd_person p left join T_HR_PersonOtherInfo pp on pp.fpersonid=p.fid left join CT_MP_Hujdz jg on p.cfjgid=jg.fid \n");
			csdjgsb.append("left join CT_MP_Hujdz csd on csd.fid=p.cfcsdid where p.fid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			csdjgsbpst = dbconnection.prepareStatement(csdjgsb.toString());
			csdjgsbrs=csdjgsbpst.executeQuery();
			while (csdjgsbrs.next()) {
				if(csdjgsbrs.getString("jg")!=null){
					person.setJg(csdjgsbrs.getString("jg"));
				}
				if(csdjgsbrs.getString("csd")!=null){
					person.setBirthAddress(csdjgsbrs.getString("csd"));
				}
				if (csdjgsbrs.getDate("FJobStartDate") != null) {
					person.setWorkTime(shgx.format(csdjgsbrs.getDate("FJobStartDate")));
				}
			}
			//全日制教育ֵ
			StringBuffer qrzjy=new StringBuffer();
			qrzjy.append("select t2.FName_l2 xz,nvl(t1.cfqrzyxzy,'无') yx,nvl(t1.cfqrzzy,'无') zy from t_bd_person t1\n");
			qrzjy.append("left join T_BD_HRDiploma t2 on t1.cfqrzjyid=t2.fid\n");
			qrzjy.append("where t1.fid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			qrzjypst = dbconnection.prepareStatement(qrzjy.toString());
			qrzjyrs=qrzjypst.executeQuery();
			String xz=" ";
			String yx=" ";
			String zy=" ";
			while (qrzjyrs.next()) {
				if (qrzjyrs.getString("xz")!=null){
					xz=qrzjyrs.getString("xz");
				}
				if (qrzjyrs.getString("yx").equals("无")){
					yx=" ";
				}else{
					yx=qrzjyrs.getString("yx");
				}
				if(qrzjyrs.getString("zy").equals("无")){
					zy=" ";
				}else{
					zy=qrzjyrs.getString("zy");
				}
			}
			person.setQrjy(xz);
			person.setQrSchool(yx+zy);
			//在职教育
			StringBuffer zzjy=new StringBuffer();
			zzjy.append("select t2.FName_l2 xz,nvl(t1.cfzzyxzy,'无') yx,nvl(t1.cfzzzy,'无') zy from t_bd_person t1  \n");
			zzjy.append("left join T_BD_HRDiploma t2 on t1.cfzzjyid=t2.fid \n");
			zzjy.append("where t1.fid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			zzjypst = dbconnection.prepareStatement(zzjy.toString());
			zzjyrs=zzjypst.executeQuery();
			String zzxz=" ";
			String zzyx=" ";
			String zzzy=" ";
			while (zzjyrs.next()) {
				if (zzjyrs.getString("xz")!=null){
					xz=zzjyrs.getString("xz");
				}
				if (zzjyrs.getString("yx").equals("无")){
					yx=" ";
				}else{
					yx=zzjyrs.getString("yx");
				}
				if(zzjyrs.getString("zy").equals("无")){
					zy=" ";
				}else{
					zy=zzjyrs.getString("zy");
				}
			}
			person.setZzjy(zzxz);
			person.setZzSchool(zzyx+zzzy);
			//专业技术职务
			String zyP=" ";
			StringBuffer Zyjszfsb=new StringBuffer();
			Zyjszfsb.append("select mz.fname_l2 zy from T_HR_PersonTechPost tp left join CT_MP_Zyjszwzg03 mz on tp.CFZyjszwzgmcID=mz.fid\n");
			Zyjszfsb.append("where tp.FisHighTechnical=1 and tp.fpersonid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			Zyjszfsbpst = dbconnection.prepareStatement(Zyjszfsb.toString());
			Zyjszfsbrs=Zyjszfsbpst.executeQuery();
			person.setZyPosition(" ");
			while (Zyjszfsbrs.next()) {
				if (!Zyjszfsbrs.getString("zy").equals("其他_无职称")) {
					zyP=Zyjszfsbrs.getString("zy");
				}
			}
			person.setZyPosition(zyP);
			//现任职务 FLEFFDT小于等于2199-12-31的所有职位
			StringBuffer nPosition=new StringBuffer();
			StringBuffer Xrzwsb=new StringBuffer();
			Xrzwsb.append("select t7.fname_l2 o1name,t7.FLayerTypeID o1fid,t6.fname_l2 o2name,t6.FLayerTypeID o2fid,\n");
			Xrzwsb.append("t5.fname_l2 o3name,t5.FLayerTypeID o3fid,t4.fname_l2 o4name,t4.FLayerTypeID o4fid,t3.fname_l2 o5name,t3.FLayerTypeID o5fid,cany.fname_l2 company,t1.FAssignType,\n");
			Xrzwsb.append("t2.FName_l2 pname from T_HR_EmpOrgRelation t1\n");
			Xrzwsb.append("left join T_ORG_Position t2 on t1.FPositionID=t2.FID\n");
			Xrzwsb.append("left join t_org_admin t3 on t3.fid=t1.fadminorgid\n");
			Xrzwsb.append("left join t_org_admin t4 on t4.fid=t3.fparentid\n");
			Xrzwsb.append("left join t_org_admin t5 on t5.fid=t4.fparentid\n");
			Xrzwsb.append("left join t_org_admin t6 on t6.fid=t5.fparentid\n");
			Xrzwsb.append("left join t_org_admin t7 on t7.fid=t6.fparentid\n");
			Xrzwsb.append("left join t_org_admin cany on cany.fid=t3.fcompanyid\n");
			Xrzwsb.append("where TO_CHAR(t1.FLEFFDT, 'YYYY-MM-DD')>To_Char(SYSDATE,'YYYY-MM-DD')\n");
			Xrzwsb.append("and t1.fpersonid='"+personId+"'");
			dbconnection = C3P0Utils.getConnection();
			xrzwStatement = dbconnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Xrzwsbrs=xrzwStatement.executeQuery(Xrzwsb.toString());
//			Xrzwsbpst = dbconnection.prepareStatement(Xrzwsb.toString());
//			Xrzwsbrs=Xrzwsbpst.executeQuery();
			person.setNowPosition("");
			int xrrow=1;
			if (Xrzwsbrs!=null) {
				Xrzwsbrs.last();
				int row = Xrzwsbrs.getRow();
				Xrzwsbrs.beforeFirst();
				while (Xrzwsbrs.next()) {
					if(Xrzwsbrs.getInt("FAssignType")==1){
						if (Xrzwsbrs.getString("company")!=null) {
							if (Xrzwsbrs.getString("company").contains("本部机关")) {
								String company=Xrzwsbrs.getString("company").substring(0, Xrzwsbrs.getString("company").length()-4);
								person.setCompany(company);
								person.setBmzw(Xrzwsbrs.getString("o5name")+Xrzwsbrs.getString("pname"));
							}else if(Xrzwsbrs.getString("company").contains("机关")){
								String company=Xrzwsbrs.getString("company").substring(0, Xrzwsbrs.getString("company").length()-2);
								person.setCompany(company);
								person.setBmzw(Xrzwsbrs.getString("o5name")+Xrzwsbrs.getString("pname"));
							}
						}
					}
					ArrayList<OrgInfo> arrayList = new ArrayList<OrgInfo>();
					OrgInfo orgInfo5 = new OrgInfo();
					orgInfo5.setOrgTypeid(Xrzwsbrs.getString("o5fid"));
					orgInfo5.setOrgName(Xrzwsbrs.getString("o5name"));
					arrayList.add(orgInfo5);
					OrgInfo orgInfo4 = new OrgInfo();
					orgInfo4.setOrgTypeid(Xrzwsbrs.getString("o4fid"));
					orgInfo4.setOrgName(Xrzwsbrs.getString("o4name"));
					arrayList.add(orgInfo4);
					OrgInfo orgInfo3 = new OrgInfo();
					orgInfo3.setOrgTypeid(Xrzwsbrs.getString("o3fid"));
					orgInfo3.setOrgName(Xrzwsbrs.getString("o3name"));
					arrayList.add(orgInfo3);
					OrgInfo orgInfo2 = new OrgInfo();
					orgInfo2.setOrgTypeid(Xrzwsbrs.getString("o2fid"));
					orgInfo2.setOrgName(Xrzwsbrs.getString("o2name"));
					arrayList.add(orgInfo2);
					OrgInfo orgInfo1 = new OrgInfo();
					orgInfo1.setOrgTypeid(Xrzwsbrs.getString("o1fid"));
					orgInfo1.setOrgName(Xrzwsbrs.getString("o1name"));
					arrayList.add(orgInfo1);
					for (int i = 0; i < arrayList.size(); i++) {
						OrgInfo orgInfo = arrayList.get(i);
						if (!orgInfo.getOrgTypeid().equals("00000000-0000-0000-0000-00000000000362824988")&&!orgInfo.getOrgTypeid().equals("AlkAAAAABA5igkmI")
								&&!orgInfo.getOrgTypeid().equals("AlkAAAAABBVigkmI")) {
							if (orgInfo.getOrgName().contains("机关")) {
								continue;
							}
							if (row==1) {
//								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("o5name")+Xrzwsbrs.getString("pname"));
								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("pname"));
							}else if(xrrow==row){
//								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("o5name")+Xrzwsbrs.getString("pname"));
								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("pname"));
							}else{
//								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("o5name")+Xrzwsbrs.getString("pname")).append("<w:br />");
								nPosition.append(orgInfo.getOrgName().toString()+Xrzwsbrs.getString("pname")).append("<w:br />");
							}
							break;
						}
					}
					xrrow++;
				}
			}
			person.setNowPosition(nPosition.toString());
			//社会关系
//			StringBuffer Shgxsb=new StringBuffer();
//			Shgxsb.append("select t2.FName_l2 Fpe,t1.FName_l2 Fxm,t1.Fbirthday,t3.FName_l2 Fzzmm,t1.FworkUnit from T_HR_PersonFamily t1 \n");
//			Shgxsb.append("left join T_HR_BDRelation t2 on t1.FRelationID=t2.FID left join T_BD_HRPolitical t3 on t1.FpoliticalFaceID=t3.FID\n");
//			Shgxsb.append("where t1.fpersonid='"+personId+"'\n");
//			Shgxsb.append("order by t2.FNumber");
//			dbconnection = C3P0Utils.getConnection();
//			Shgxsbpst = dbconnection.prepareStatement(Shgxsb.toString());
//			Shgxsbrs=Shgxsbpst.executeQuery();
//			int i = 1;
//			while (Shgxsbrs.next()) {
//				if (i == 1) {
//					person.setLongName1(Shgxsbrs.getString("Fpe"));
//					person.setName1(Shgxsbrs.getString("Fxm"));
//					person.setZzmm1(Shgxsbrs.getString("Fzzmm"));
//					if(Shgxsbrs.getString("FworkUnit")==null){
//						person.setPosition1(" ");
//					}else{
//						person.setPosition1(Shgxsbrs.getString("FworkUnit"));
//					}
//					java.util.Date start = Shgxsbrs.getDate("Fbirthday");
//					if (start != null) {
//						java.util.Date end = new java.util.Date();
//						Calendar c1 = Calendar.getInstance();
//						Calendar c2 = Calendar.getInstance();
//						c1.setTime(start);
//						c2.setTime(end);
//						int year1 = c1.get(1);
//						int year2 = c2.get(1);
//						Integer year = Integer.valueOf(Math.abs(year1 - year2));
//						person.setAge1(shgx.format(start));
//					} else {
//						person.setAge1("");
//					}
//				} else if (i == 2) {
//					person.setLongName2(Shgxsbrs.getString("Fpe"));
//					person.setName2(Shgxsbrs.getString("Fxm"));
//					person.setZzmm2(Shgxsbrs.getString("Fzzmm"));
//					if(Shgxsbrs.getString("FworkUnit")==null){
//						person.setPosition2(" ");
//					}else{
//						person.setPosition2(Shgxsbrs.getString("FworkUnit"));
//					}
//					java.util.Date start = Shgxsbrs.getDate("Fbirthday");
//					if (start != null) {
//						java.util.Date end = new java.util.Date();
//						Calendar c1 = Calendar.getInstance();
//						Calendar c2 = Calendar.getInstance();
//						c1.setTime(start);
//						c2.setTime(end);
//						int year1 = c1.get(1);
//						int year2 = c2.get(1);
//						Integer year = Integer.valueOf(Math.abs(year1 - year2));
//						person.setAge2(shgx.format(start));
//					} else {
//						person.setAge2("");
//					}
//				} else if (i == 3) {
//					person.setLongName3(Shgxsbrs.getString("Fpe"));
//					person.setName3(Shgxsbrs.getString("Fxm"));
//					person.setZzmm3(Shgxsbrs.getString("Fzzmm"));
//					if(Shgxsbrs.getString("FworkUnit")==null){
//						person.setPosition3(" ");
//					}else{
//						person.setPosition3(Shgxsbrs.getString("FworkUnit"));
//					}
//					java.util.Date start = Shgxsbrs.getDate("Fbirthday");
//					if (start != null) {
//						java.util.Date end = new java.util.Date();
//						Calendar c1 = Calendar.getInstance();
//						Calendar c2 = Calendar.getInstance();
//						c1.setTime(start);
//						c2.setTime(end);
//						int year1 = c1.get(1);
//						int year2 = c2.get(1);
//						Integer year = Integer.valueOf(Math.abs(year1 - year2));
//						person.setAge3(shgx.format(start));
//					} else {
//						person.setAge3("");
//					}
//				} else if (i == 4) {
//					person.setLongName4(Shgxsbrs.getString("Fpe"));
//					person.setName4(Shgxsbrs.getString("Fxm"));
//					person.setZzmm4(Shgxsbrs.getString("Fzzmm"));
//					if(Shgxsbrs.getString("FworkUnit")==null){
//						person.setPosition4(" ");
//					}else{
//						person.setPosition4(Shgxsbrs.getString("FworkUnit"));
//					}
//					java.util.Date start = Shgxsbrs.getDate("Fbirthday");
//					if (start != null) {
//						java.util.Date end = new java.util.Date();
//						Calendar c1 = Calendar.getInstance();
//						Calendar c2 = Calendar.getInstance();
//						c1.setTime(start);
//						c2.setTime(end);
//						int year1 = c1.get(1);
//						int year2 = c2.get(1);
//						Integer year = Integer.valueOf(Math.abs(year1 - year2));
//						person.setAge4(shgx.format(start));
//					} else {
//						person.setAge4("");
//					}
//				} else if (i == 5) {
//					person.setLongName5(Shgxsbrs.getString("Fpe"));
//					person.setName5(Shgxsbrs.getString("Fxm"));
//					person.setZzmm5(Shgxsbrs.getString("Fzzmm"));
//					if(Shgxsbrs.getString("FworkUnit")==null){
//						person.setPosition5(" ");
//					}else{
//						person.setPosition5(Shgxsbrs.getString("FworkUnit"));
//					}
//					java.util.Date start = Shgxsbrs.getDate("Fbirthday");
//					if (start != null) {
//						java.util.Date end = new java.util.Date();
//						Calendar c1 = Calendar.getInstance();
//						Calendar c2 = Calendar.getInstance();
//						c1.setTime(start);
//						c2.setTime(end);
//						int year1 = c1.get(1);
//						int year2 = c2.get(1);
//						Integer year = Integer.valueOf(Math.abs(year1 - year2));
//						person.setAge5(shgx.format(start));
//					} else {
//						person.setAge5("");
//					}
//				}
//				++i;
//			}
			//学历简历
			ArrayList<gzjlInfo> gzjlList = new ArrayList<gzjlInfo>();
			StringBuffer buffer =new StringBuffer("");
			String sqlCv1 = "select t1.FenrollDate,t1.FgraduateDate,t1.FdegreeUnit,t1.fgraduateSchool,t1.Fspecialty,t3.fname_l2 "
					+ "from T_HR_PersonDegree t1 left join t_bd_hrdiploma t3 on t1.fdiploma=t3.fid left join T_BD_HRDegree t4 "
					+ "on t1.fdegree=t4.fid where t1.FPersonID='"+personId+"' "
					+ "order by t1.FgraduateDate ";
			dbconnection = C3P0Utils.getConnection();
			sqlCv1pst = dbconnection.prepareStatement(sqlCv1);
			sqlCv1rs=sqlCv1pst.executeQuery();
			while (sqlCv1rs.next()) {
				if (sqlCv1rs.getDate("FenrollDate") != null&&sqlCv1rs.getDate("FgraduateDate") != null) {
					gzjlInfo xlInfo=new gzjlInfo();
					xlInfo.setPos("");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
					String endString = "";
					String strStart = "190001";
					if (sqlCv1rs.getDate("FenrollDate") != null) {
						strStart = sdf.format(sqlCv1rs.getDate("FenrollDate"));
						xlInfo.setBengindate(strStart);
					}
					if (sqlCv1rs.getDate("FgraduateDate") != null) {
						endString = sdf.format(sqlCv1rs.getDate("FgraduateDate"));
						xlInfo.setEnddate(endString);
						if ("2199.12".equals(endString)) {
							endString = "至今";
							xlInfo.setEnddate(endString);
						}
					}
					String str4 = "";
					if (sqlCv1rs.getString("fgraduateSchool") != null)
						str4 = sqlCv1rs.getString("fgraduateSchool");
					xlInfo.setComp(str4);
					String str5 = "";
					if (sqlCv1rs.getString("Fspecialty") != null)
						str5 = sqlCv1rs.getString("Fspecialty");
					xlInfo.setOrg(str5);
					String str6 = "";
					if (sqlCv1rs.getDate("FenrollDate") != null) {
						buffer.append(
								sdf.format(sqlCv1rs.getDate("FenrollDate")) + "-" + endString
										+ " " + str4 + str5).append("<w:br />");
					}
					gzjlList.add(xlInfo);
				}
			}

			StringBuffer ddcv2=new StringBuffer("");
//			ddcv2.append(buffer.toString());
			//员工变动记录  type: 1=需要变动记录   2=不需要变动记录
			if (type.equals("1")) {
				gzjlList = getEmpOrgRelation(personId,gzjlList);
			}
			StringBuffer ddcv=new StringBuffer("");
//			ddcv=getEmpOrgRelation(personId);
			//ְ职位简历
			String sqlCv2 = "select t1.FbeginDate,t1.FendDate,t1.FunitName,t1.FworkDept,t1.FPosition from T_HR_PersonWorkExp t1 where t1.FPersonID='"
					+ personId + "' order by t1.FbeginDate ";
			dbconnection = C3P0Utils.getConnection();
//			sqlCv2pst = dbconnection.prepareStatement(sqlCv2);
//			sqlCv2rs=sqlCv2pst.executeQuery();
			sqlCv2Statement = dbconnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			sqlCv2rs=sqlCv2Statement.executeQuery(sqlCv2);
			sqlCv2rs.last();
			int cd = sqlCv2rs.getRow();
			int k=1;
			sqlCv2rs.beforeFirst();
			while (sqlCv2rs.next()) {
				gzjlInfo info=new gzjlInfo();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
				String endString = "";
				String strStart = "190001";
				if (sqlCv2rs.getDate("FbeginDate") != null) {
					strStart = sdf.format(sqlCv2rs.getDate("FbeginDate"));
					info.setBengindate(strStart);
				}
				if (sqlCv2rs.getDate("FendDate") != null) {
					endString = sdf.format(sqlCv2rs.getDate("FendDate"));
					info.setEnddate(endString);
					if ("2199.12".equals(endString)) {
						endString = "至今";
						info.setEnddate(endString);
					}
				}else{
					endString = "至今";
					info.setEnddate(endString);
				}
				String str3 = "";
				if (sqlCv2rs.getString("FunitName") != null)
					str3 = sqlCv2rs.getString("FunitName");
				info.setComp(str3);
				String str4 = "";
				if (sqlCv2rs.getString("FworkDept") != null)
					str4 = sqlCv2rs.getString("FworkDept");
				info.setOrg(str4);
				String str5 = "";
				if (sqlCv2rs.getString("FPosition") != null)
					str5 = sqlCv2rs.getString("FPosition");
				info.setPos(str5);
				if (sqlCv2rs.getDate("FbeginDate") == null)
					continue;

				gzjlList.add(info);
				if (k==cd) {
					buffer.append(sdf.format(sqlCv2rs.getDate("FbeginDate")) + "-" + endString + " "
							+ str3 + str4  + str5+"；");
					ddcv.append(sdf.format(sqlCv2rs.getDate("FbeginDate")) + "-" + endString + " "
							+ str3 + str4  + str5+"；");
				}else{
					buffer.append(sdf.format(sqlCv2rs.getDate("FbeginDate")) + "-" + endString + " "
							+ str3 + str4  + str5+"；").append(
							"<w:br/>");
					ddcv.append(sdf.format(sqlCv2rs.getDate("FbeginDate")) + "-" + endString + " "
							+ str3 + str4  + str5+"；").append(
							"<w:br/>");
				}
				k++;
			}
			//相同职位合并日期
			ArrayList<gzjlInfo> tempgzjlList = gzjlList;
//			for (int a = 0; a < tempgzjlList.size() - 1; a++) {
//		            for (int b = a+1; b < tempgzjlList.size(); b++) {
//		            	gzjlInfo gzjlInfo = tempgzjlList.get(a);
//		            	gzjlInfo gzjlInfo2 = tempgzjlList.get(b);
//		            	if ((gzjlInfo.getComp()+gzjlInfo.getOrg()+gzjlInfo.getPos()).equals(
//		            			(gzjlInfo2.getComp()+gzjlInfo2.getOrg()+gzjlInfo2.getPos()))) {
//		            		int compareTo = gzjlInfo.getBengindate().compareTo(gzjlInfo2.getBengindate());
//		            		if (compareTo>0) {
//		            			tempgzjlList.get(a).setBengindate(gzjlInfo2.getBengindate());
//							}else if(compareTo<0){
//								tempgzjlList.get(b).setBengindate(gzjlInfo.getBengindate());
//							}
//		            		int compareTo2 = gzjlInfo.getEnddate().compareTo(gzjlInfo2.getEnddate());
//		            		if (compareTo2>0) {
//		            			tempgzjlList.get(b).setEnddate(gzjlInfo.getEnddate());
//							}else if(compareTo2<0){
//								tempgzjlList.get(a).setEnddate(gzjlInfo2.getEnddate());
//							}
//		                }
//		            }
//		     }
			//按开始日期排序
			if (tempgzjlList.size()>1) {
				tempgzjlList.sort(Comparator.comparing(gzjlInfo::getBengindate));
			}
//			ArrayList resultList=new ArrayList();              //创建新集合
//			Iterator iterator=tempgzjlList.iterator();         //获取原集合的迭代器
//			while(iterator.hasNext()){                      //遍历原集合
//				Object obj=iterator.next();
//				if(!resultList.contains(obj)){                 //若不存在，则加入新集合
//					resultList.add(obj);
//				}
//			}
			gzjlList=tempgzjlList;
			int p=1;
			int le=gzjlList.size();
			for (int j = 0; j < gzjlList.size(); j++) {
				gzjlInfo gzjlInfo = gzjlList.get(j);
				if (p==le) {
					ddcv2.append(gzjlInfo.getBengindate()+"-"+gzjlInfo.getEnddate()+" "+gzjlInfo.getComp()+gzjlInfo.getOrg()+gzjlInfo.getPos());
				} else {
					ddcv2.append(gzjlInfo.getBengindate()+"-"+gzjlInfo.getEnddate()+" "+gzjlInfo.getComp()+gzjlInfo.getOrg()+gzjlInfo.getPos()+"<w:br/>");
				}
				p++;
			}
			person.setCv(ddcv2);
			person.setDdcv(ddcv2);
			//奖励信息
			StringBuffer jcString =new StringBuffer("");
			String sqlj = "select CFJlsj,CFJlmc from CT_MP_SHJLQK where FPersonID='"+personId+"' order by CFJlsj asc";
			dbconnection = C3P0Utils.getConnection();
			sqljpst = dbconnection.prepareStatement(sqlj);
			sqljrs=sqljpst.executeQuery();
			while (sqljrs.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
				SimpleDateFormat sdfFilter = new SimpleDateFormat(
						"yyyyMM");
				String strStart = "190001";
				String strEnd = "190001";
				if (sqljrs.getDate("CFJlsj") != null) {
					strStart = sdf.format(sqljrs.getDate("CFJlsj"));
				}
				String str2 = "";
				if (sqljrs.getString("CFJlmc") != null)
					str2 = sqljrs.getString("CFJlmc");
				jcString.append(strStart+" ").append(str2  + "；").append(
						"<w:br />");
			}
			//处分信息
			String sqcf = "select CFcfsj,CFcfmc from CT_MP_SSCFQK where FPersonID='"+personId+"' order by CFcfsj asc";
			dbconnection = C3P0Utils.getConnection();
			sqcfpst = dbconnection.prepareStatement(sqcf);
			sqcfrs=sqcfpst.executeQuery();
			while (sqcfrs.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
				SimpleDateFormat sdfFilter = new SimpleDateFormat(
						"yyyyMM");
				String strStart = "190001";
				String strEnd = "190001";
				if (sqcfrs.getDate("CFcfsj") != null) {
					strStart = sdf.format(sqcfrs.getDate("CFcfsj"));
				}
				String str2 = "";
				if (sqcfrs.getString("CFcfmc") != null&&!sqcfrs.getString("CFcfmc").equals("无"))
					str2 = sqcfrs.getString("CFcfmc");
				jcString.append(strStart+" ").append(str2  + "；").append(
						"<w:br />");
			}
			person.setJc(jcString.toString());

			//考核情况
			StringBuffer approveString =new StringBuffer("");
			String sqlapprove = "select t1.CFKpnd,t2.FName_l2 from CT_MP_NDKPQK t1 left join CT_BD_NDKPKHJG t2 on t1.cfkhjgid=t2.fid\n" +
					"where FPersonID='"+personId+"' order by t1.CFKpnd asc";
			dbconnection = C3P0Utils.getConnection();
			sqlapprovepst = dbconnection.prepareStatement(sqlapprove);
			sqlapprovers=sqlapprovepst.executeQuery();
			while (sqlapprovers.next()) {
				String str1 = "";
				if (sqlapprovers.getString("CFKpnd") != null)
					str1 = sqlapprovers.getString("CFKpnd");
				if (sqlapprovers.getString("FName_l2") == null)
					continue;
				approveString.append(str1+"年考核结果为"+sqlapprovers.getString("FName_l2")
						+ "；").append(
						"<w:br />");
			}

			person.setApprove(approveString.toString());
			SimpleDateFormat ywsj = new SimpleDateFormat("yyyyMMdd");
			person.setJsnlsj(ywsj.format(new Date()));
			person.setTbsj(ywsj.format(new Date()));
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}finally {
			C3P0Utils.closeAll(xrzwStatement,sqlCv2Statement,dbconnection,pst,rs,zzmmpst,zzmmrs,sxzytcpst,sxzytcrs,csdjgsbpst,csdjgsbrs,qrzjypst,qrzjyrs,zzjypst,zzjyrs,Zyjszfsbpst,Zyjszfsbrs,Xrzwsbpst,Xrzwsbrs,Shgxsbpst,Shgxsbrs,sqlCv1pst,sqlCv1rs,sqlCv2pst,sqlCv2rs,sqcfpst,sqcfrs,sqljpst,sqljrs,sqlapprovepst,sqlapprovers);
		}
		return person;

	}
}
