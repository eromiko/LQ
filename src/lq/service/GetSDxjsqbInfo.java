package lq.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import lq.model.SDxjsqbInfo;
import lq.util.C3P0Utils;

public class GetSDxjsqbInfo {
	public SDxjsqbInfo getSdInfo(String billid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SDxjsqbInfo info=new SDxjsqbInfo();
		StringBuffer empString =new StringBuffer("");		
		empString.append("select p.fname_l2 name,o.fname_l2 bm,pos.fname_l2 zw,pm.FNCell ncell,pm.FEmail email,htp.FName_l2 qjlb,le.FBeginTime,le.FEndTime,\n");
		empString.append("hl.FYear,hl.FRealLimit,hl.FUsedLimit,hl.FRemainLimit,le.FLeaveLength\n");
		empString.append("from T_HR_ATS_LeaveBill lb inner join T_HR_ATS_LeaveBillEntry le on le.fbillid=lb.fid\n");
		empString.append("inner join t_bd_person p on p.fid=le.FPersonID\n");
		empString.append("left join T_ORG_Position pos on pos.fid=le.FPositionID\n");
		empString.append("left join t_org_admin o on o.fid=le.FADMINORGUNITID\n");
		empString.append("left join T_HR_ATS_HolidayPolicy hp on hp.fid=le.FPolicyID\n");
		empString.append("left join T_HR_ATS_HolidayType htp on htp.fid=hp.FHolidayTypeID\n");
		empString.append("left join T_HR_ATS_HolidayLimit hl on hl.FProposerID=p.fid\n");
		empString.append("left join T_HR_PersonContactMethod pm on pm.FPersonID=p.fid\n");
		empString.append("where lb.fid='"+billid+"'");
		PreparedStatement ddpst=null;
		ResultSet ddrs=null;
		PreparedStatement spygpst=null;
		ResultSet spygrs=null;
		PreparedStatement spzzpst=null;
		ResultSet spzzrs=null;
		PreparedStatement spfzpst=null;
		ResultSet spfzrs=null;
		PreparedStatement spldpst=null;
		ResultSet spldrs=null;
		Connection dbconnection = C3P0Utils.getConnection();
		try {
			//获得当前年
			Calendar cal = Calendar.getInstance();
			int nyear = cal.get(Calendar.YEAR);			
			ddpst = dbconnection.prepareStatement(empString.toString());
			ddrs=ddpst.executeQuery();
			int i=1;
			while (ddrs.next()) {
				if(i==1){
					info.setName(ddrs.getString("name"));
					info.setBmzw(ddrs.getString("bm")+ddrs.getString("zw"));
					info.setNcell("");
					if (ddrs.getString("ncell")!=null) {
						info.setNcell(ddrs.getString("ncell"));
					}
					info.setEmail("");
					if (ddrs.getString("email")!=null) {
						info.setEmail(ddrs.getString("email"));
					}					
					//请假类别
					String qjlbstring="□年休假 □婚假 □产假（护理假）□病假<w:br />□丧假  □事假 □子女护理假   □其他___";
					if (ddrs.getString("qjlb").equals("年假")) {
						qjlbstring="☑年休假 口婚假 □产假（护理假）□病假<w:br />□丧假  □事假 □子女护理假   □其他___";
					}else if(ddrs.getString("qjlb").equals("婚假")){
						qjlbstring="□年休假 ☑婚假 □产假（护理假）□病假<w:br />□丧假  □事假 □子女护理假   □其他___";
					}else if(ddrs.getString("qjlb").equals("产假")){
						qjlbstring="□年休假 □婚假 ☑产假（护理假）□病假<w:br />□丧假  □事假 □子女护理假   □其他___";
					}else if(ddrs.getString("qjlb").equals("病假")){
						qjlbstring="□年休假 □婚假 □产假（护理假）☑病假<w:br />□丧假  □事假 □子女护理假   □其他___";
					}else if(ddrs.getString("qjlb").equals("丧假")){
						qjlbstring="□年休假 □婚假 □产假（护理假）□病假<w:br />☑丧假  □事假 □子女护理假   □其他___";
					}else if(ddrs.getString("qjlb").equals("事假")){
						qjlbstring="□年休假 □婚假 □产假（护理假）□病假<w:br />□丧假  ☑事假 □子女护理假   □其他___";
					}else if(ddrs.getString("qjlb").equals("子女护理假")){
						qjlbstring="□年休假 □婚假 □产假（护理假）□病假<w:br />□丧假  □事假 ☑子女护理假   □其他___";
					}else{
						qjlbstring="□年休假 □婚假 □产假（护理假）□病假<w:br />□丧假  □事假 □子女护理假   ☑其他"+ddrs.getString("qjlb");
					}
					info.setQjlb(qjlbstring);
					//请休假时间
					String start="____年___月___日";
					String end="____年___月___日共__日";
					if (ddrs.getDate("FBeginTime")!=null) {
						start = sdf.format(ddrs.getDate("FBeginTime"));
					}
					if (ddrs.getDate("FEndTime")!=null) {
						end=sdf.format(ddrs.getDate("FEndTime"))+"共"+ddrs.getInt("FLeaveLength")+"日";
					}

					info.setQjsj(start+"至"+end);
				}
				//年
				int year = ddrs.getInt("FYear");
				if (year==nyear) {
					info.setNyx(Integer.toString(ddrs.getInt("FRealLimit")));
					info.setNyyx(Integer.toString(ddrs.getInt("FUsedLimit")));
					info.setNyy(Integer.toString(ddrs.getInt("FRemainLimit")));
				} else if(nyear-1==year){
					info.setLyy(Integer.toString(ddrs.getInt("FRemainLimit")));
				}
				i++;
			}
			//审批信息--员工子流程 
			StringBuffer spxxygString =new StringBuffer("");
			spxxygString.append("select u.FName_l2 szbmqz,case when approve.FIsPass='true' then '同意' when approve.FIsPass='false' then '不同意' end sybmyj,approve.FCreateTime szbmrq,act.FActDefName_l2 jdmc\n");
			spxxygString.append("from T_HR_ATS_LeaveBill lb inner join T_HR_ATS_LeaveBillEntry le on le.fbillid=lb.fid\n");
			spxxygString.append("inner join T_BD_Person p on p.fid=le.FPersonID\n");
			spxxygString.append("inner join t_WFR_ProcinstRef bizRef on lb.fid=bizRef.FrefID\n");
			spxxygString.append("inner join t_wfr_actInst act on bizRef.FProcInstID=act.FProcInstID\n");
			spxxygString.append("left join t_wfr_Assigndetail detail on detail.FActdefID=act.FActdefID and detail.FProcInstID=act.FProcInstID\n");
			spxxygString.append("left join T_BAS_MultiApprove approve on approve.FASSIGNMENTID=detail.FAssignID\n");
			spxxygString.append("left join T_PM_User u on approve.FCreatorID = u.FID\n");
			spxxygString.append("where act.FActDefName_l2 in ('集团人力核查','部门正职','部门副职') and lb.fid='"+billid+"'");			
			spygpst = dbconnection.prepareStatement(spxxygString.toString());
			spygrs=spygpst.executeQuery();
			while (spygrs.next()) {	
				if (spygrs.getString("jdmc").equals("集团人力核查")) {
					if (spygrs.getString("szbmqz")!=null) {
						info.setRlzyshr(spygrs.getString("szbmqz"));
					}
				}
				if (spygrs.getString("jdmc").equals("部门正职")) {			
					if (spygrs.getString("sybmyj")!=null) {
						info.setSzbmyj(spygrs.getString("sybmyj"));
					}
					if (spygrs.getString("szbmqz")!=null) {
						info.setSzbmqm(spygrs.getString("szbmqz"));
					}
					info.setSzbmrq("  年     月     日");
					if (spygrs.getDate("szbmrq")!=null) {
						info.setSzbmrq(sdf.format(spygrs.getDate("szbmrq")));
					}
				}				
				if (spygrs.getString("jdmc").equals("部门副职")) {
					
					if (spygrs.getString("sybmyj")!=null) {
						info.setFgyj(spygrs.getString("sybmyj"));
					}
					if (spygrs.getString("szbmqz")!=null) {
						info.setFgqm(spygrs.getString("szbmqz"));
					}
					info.setSzbmrq("  年     月     日");
					if (spygrs.getDate("szbmrq")!=null) {
						info.setFgrq(sdf.format(spygrs.getDate("szbmrq")));
					}
				
				}
			}
			//审批信息--正职子流程 
			StringBuffer spxxzzString =new StringBuffer("");
			spxxzzString.append("select u.FName_l2 szbmqz,case when approve.FIsPass='true' then '同意' when approve.FIsPass='false' then '不同意' end sybmyj,approve.FCreateTime szbmrq,act.FActDefName_l2 jdmc\n");
			spxxzzString.append("from T_HR_ATS_LeaveBill lb inner join T_HR_ATS_LeaveBillEntry le on le.fbillid=lb.fid\n");
			spxxzzString.append("inner join T_BD_Person p on p.fid=le.FPersonID\n");
			spxxzzString.append("inner join t_WFR_ProcinstRef bizRef on lb.fid=bizRef.FrefID\n");
			spxxzzString.append("inner join t_wfr_actInst act on bizRef.FProcInstID=act.FProcInstID\n");
			spxxzzString.append("left join t_wfr_Assigndetail detail on detail.FActdefID=act.FActdefID and detail.FProcInstID=act.FProcInstID\n");
			spxxzzString.append("left join T_BAS_MultiApprove approve on approve.FASSIGNMENTID=detail.FAssignID\n");
			spxxzzString.append("left join T_PM_User u on approve.FCreatorID = u.FID\n");
			spxxzzString.append("where act.FActDefName_l2 in ('分管领导','总经理','董事长') and lb.fid='"+billid+"'");			
			spzzpst = dbconnection.prepareStatement(spxxzzString.toString());
			spzzrs=spzzpst.executeQuery();
			while (spzzrs.next()) {	
				if (spzzrs.getString("jdmc").equals("分管领导")) {			
					if (spzzrs.getString("sybmyj")!=null) {
						info.setFgldyj(spzzrs.getString("sybmyj"));
					}
					if (spzzrs.getString("szbmqz")!=null) {
						info.setFgldqz(spzzrs.getString("szbmqz"));
					}
					info.setFgldrq("  年     月     日");
					if (spzzrs.getDate("szbmrq")!=null) {
						info.setFgldrq(sdf.format(spzzrs.getDate("szbmrq")));
					}
				}				
				if (spzzrs.getString("jdmc").equals("总经理")) {					
					if (spzzrs.getString("sybmyj")!=null) {
						info.setZjlyj(spzzrs.getString("sybmyj"));
					}
					if (spzzrs.getString("szbmqz")!=null) {
						info.setZjlqz(spzzrs.getString("szbmqz"));
					}
					info.setZjlrq("  年     月     日");
					if (spzzrs.getDate("szbmrq")!=null) {
						info.setZjlrq(sdf.format(spzzrs.getDate("szbmrq")));
					}
				
				}
				if (spzzrs.getString("jdmc").equals("董事长")) {					
					if (spzzrs.getString("sybmyj")!=null) {
						info.setDszyj(spzzrs.getString("sybmyj"));
					}
					if (spzzrs.getString("szbmqz")!=null) {
						info.setDszqz(spzzrs.getString("szbmqz"));
					}
					info.setDszrq("  年     月     日");
					if (spzzrs.getDate("szbmrq")!=null) {
						info.setDszrq(sdf.format(spzzrs.getDate("szbmrq")));
					}
				
				}
			}
			//审批信息--副职子流程 
			StringBuffer spxxfzString =new StringBuffer("");
			spxxfzString.append("select u.FName_l2 szbmqz,case when approve.FIsPass='true' then '同意' when approve.FIsPass='false' then '不同意' end sybmyj,approve.FCreateTime szbmrq,act.FActDefName_l2 jdmc\n");
			spxxfzString.append("from T_HR_ATS_LeaveBill lb inner join T_HR_ATS_LeaveBillEntry le on le.fbillid=lb.fid\n");
			spxxfzString.append("inner join T_BD_Person p on p.fid=le.FPersonID\n");
			spxxfzString.append("inner join t_WFR_ProcinstRef bizRef on lb.fid=bizRef.FrefID\n");
			spxxfzString.append("inner join t_wfr_actInst act on bizRef.FProcInstID=act.FProcInstID\n");
			spxxfzString.append("left join t_wfr_Assigndetail detail on detail.FActdefID=act.FActdefID and detail.FProcInstID=act.FProcInstID\n");
			spxxfzString.append("left join T_BAS_MultiApprove approve on approve.FASSIGNMENTID=detail.FAssignID\n");
			spxxfzString.append("left join T_PM_User u on approve.FCreatorID = u.FID\n");
			spxxfzString.append("where act.FActDefName_l2 in ('分管领导','部门正职') and lb.fid='"+billid+"'");			
			spfzpst = dbconnection.prepareStatement(spxxfzString.toString());
			spfzrs=spfzpst.executeQuery();
			while (spfzrs.next()) {					
				if (spfzrs.getString("jdmc").equals("部门正职")) {			
					if (spfzrs.getString("sybmyj")!=null) {
						info.setSzbmyj(spfzrs.getString("sybmyj"));
					}
					if (spfzrs.getString("szbmqz")!=null) {
						info.setSzbmqm(spfzrs.getString("szbmqz"));
					}
					info.setSzbmrq("  年     月     日");
					if (spfzrs.getDate("szbmrq")!=null) {
						info.setSzbmrq(sdf.format(spfzrs.getDate("szbmrq")));
					}
				}				
				if (spfzrs.getString("jdmc").equals("分管领导")) {					
					if (spfzrs.getString("sybmyj")!=null) {
						info.setFgldyj(spfzrs.getString("sybmyj"));
					}
					if (spfzrs.getString("szbmqz")!=null) {
						info.setFgldqz(spfzrs.getString("szbmqz"));
					}
					info.setFgldrq("  年     月     日");
					if (spfzrs.getDate("szbmrq")!=null) {
						info.setFgldrq(sdf.format(spfzrs.getDate("szbmrq")));
					}
				
				}
			}
			//审批信息--领导子流程 
			StringBuffer spxxldString =new StringBuffer("");
			spxxldString.append("select u.FName_l2 szbmqz,case when approve.FIsPass='true' then '同意' when approve.FIsPass='false' then '不同意' end sybmyj,approve.FCreateTime szbmrq,act.FActDefName_l2 jdmc\n");
			spxxldString.append("from T_HR_ATS_LeaveBill lb inner join T_HR_ATS_LeaveBillEntry le on le.fbillid=lb.fid\n");
			spxxldString.append("inner join T_BD_Person p on p.fid=le.FPersonID\n");
			spxxldString.append("inner join t_WFR_ProcinstRef bizRef on lb.fid=bizRef.FrefID\n");
			spxxldString.append("inner join t_wfr_actInst act on bizRef.FProcInstID=act.FProcInstID\n");
			spxxldString.append("left join t_wfr_Assigndetail detail on detail.FActdefID=act.FActdefID and detail.FProcInstID=act.FProcInstID\n");
			spxxldString.append("left join T_BAS_MultiApprove approve on approve.FASSIGNMENTID=detail.FAssignID\n");
			spxxldString.append("left join T_PM_User u on approve.FCreatorID = u.FID\n");
			spxxldString.append("where act.FActDefName_l2 in ('总经理','董事长') and lb.fid='"+billid+"'");			
			spldpst = dbconnection.prepareStatement(spxxldString.toString());
			spldrs=spldpst.executeQuery();
			while (spldrs.next()) {					
				if (spldrs.getString("jdmc").equals("总经理")) {			
					if (spldrs.getString("sybmyj")!=null) {
						info.setZjlyj(spldrs.getString("sybmyj"));
					}
					if (spldrs.getString("szbmqz")!=null) {
						info.setZjlqz(spldrs.getString("szbmqz"));
					}
					info.setZjlrq("  年     月     日");
					if (spldrs.getDate("szbmrq")!=null) {
						info.setZjlrq(sdf.format(spldrs.getDate("szbmrq")));
					}
				}				
				if (spldrs.getString("jdmc").equals("董事长")) {					
					if (spldrs.getString("sybmyj")!=null) {
						info.setDszyj(spldrs.getString("sybmyj"));
					}
					if (spldrs.getString("szbmqz")!=null) {
						info.setDszqz(spldrs.getString("szbmqz"));
					}
					info.setDszrq("  年     月     日");
					if (spldrs.getDate("szbmrq")!=null) {
						info.setDszrq(sdf.format(spldrs.getDate("szbmrq")));
					}
				
				}
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			C3P0Utils.closeAll(ddpst,ddrs,dbconnection,spygpst,spygrs,spzzpst,spzzrs,spfzpst,spfzrs,spldpst,spldrs);
		}		
		return info;		
	}
}
