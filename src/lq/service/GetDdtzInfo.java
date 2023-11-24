package lq.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import lq.model.DdtzInfo;
import lq.util.C3P0Utils;

public class GetDdtzInfo {
	public DdtzInfo getDdtzInfo(String billid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		DdtzInfo info=new DdtzInfo();
		StringBuffer empString =new StringBuffer("");		
		empString.append("select distinct u.FName_l2 jdspr,nz.fsimplename office,lz.fsimplename oldoffice,lc.fname_l2 oldcompany,p.fname_l2 person,be.fbizdate startdate\n");
		empString.append(",act.FActDefName_l2 jdmc,pcm.FNCell cell,bill.FApplyDate createtime\n");
		empString.append("from T_HR_FlucOutBizBillEntry be inner join T_HR_FlucOutBizBill bill on bill.fid=be.FBILLID\n");
		empString.append("inner join T_BD_Person p on p.fid=be.FPersonID\n");
		empString.append("inner join t_WFR_ProcinstRef bizRef on bill.fid=bizRef.FrefID\n");
		empString.append("inner join t_wfr_actInst act on bizRef.FProcInstID=act.FProcInstID\n");
		empString.append("left join t_wfr_Assigndetail detail on detail.FActdefID=act.FActdefID and detail.FProcInstID=act.FProcInstID\n");
		empString.append("left join T_BAS_MultiApprove approve on approve.FASSIGNMENTID=detail.FAssignID\n");	
		empString.append("left join T_PM_User u on approve.FCreatorID = u.FID\n");
		empString.append("left join T_ORG_Admin lc on lc.fid=be.FOldCompanyID\n");
		empString.append("left join T_ORG_Admin nc on nc.fid=be.FCompanyID\n");
		empString.append("left join T_ORG_Admin lz on lz.fid=lc.FOfficeID\n");
		empString.append("left join T_ORG_Admin nz on nz.fid=nc.FOfficeID\n");
		empString.append("left join T_HR_PersonContactMethod pcm on pcm.FPersonID=u.FPersonId\n");
		empString.append("where act.FActDefName_l2 in ('跨直属企业调动-直属企业人力经办','调入单位直属企业人力经办') and u.FName_l2 is not null\n"); 
		empString.append("and bill.fid='"+billid+"'");
		PreparedStatement ddpst=null;
		ResultSet ddrs=null;
		Connection dbconnection = C3P0Utils.getConnection();
		try {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			String y = Integer.toString(year);
			y = new DecimalFormat().parse(y).toString();
			ddpst = dbconnection.prepareStatement(empString.toString());
			ddrs=ddpst.executeQuery();
			int i=1;
			while (ddrs.next()) {				
				if (i==1) {
					info.setOffice(ddrs.getString("office"));
					info.setOldoffice(ddrs.getString("oldoffice"));
					info.setOldcompany(ddrs.getString("oldcompany"));
					info.setPerson(ddrs.getString("person"));
					info.setStartdate(sdf.format(ddrs.getDate("startdate")));
					info.setCreatetime(sdf.format(ddrs.getDate("createtime")));
					info.setYear(y);
				}
				if (ddrs.getString("jdmc").equals("跨直属企业调动-直属企业人力经办")) {
					info.setOldofficelxr(ddrs.getString("jdspr"));
					info.setOldofficephone(ddrs.getString("cell"));
				} else if(ddrs.getString("jdmc").equals("调入单位直属企业人力经办")){
					info.setOfficelxr(ddrs.getString("jdspr"));
					info.setOfficephone(ddrs.getString("cell"));
				}
				i++;
			}									
		} catch (SQLException e) {			
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
			C3P0Utils.closeAll(ddpst,ddrs,dbconnection);
		}					
		return info;		
	}
	
	public String getjc(String qc){
		String jc=qc;
		if(qc.equals("四川高速公路建设开发集团有限公司")){
			jc="川高公司";
		}if(qc.equals("四川路桥建设集团股份有限公司")){
			jc="四川路桥";
		}if(qc.equals("四川成渝高速公路股份有限公司")){
			jc="成渝公司";
		}if(qc.equals("四川蜀道高速公路集团有限公司")){
			jc="蜀道高速集团";
		}if(qc.equals("四川藏区高速公路有限责任公司")){
			jc="藏高公司";
		}if(qc.equals("四川蜀道铁路投资集团有限责任公司")){
			jc="蜀道铁路投资集团";
		}if(qc.equals("四川蜀道新制式轨道集团有限责任公司")){
			jc="蜀道新制式轨道集团";
		}if(qc.equals("四川蜀道铁路运营管理集团有限责任公司")){
			jc="蜀道铁路运营集团";
		}if(qc.equals("蜀道城乡投资集团有限责任公司")){
			jc="蜀道城乡集团";
		}if(qc.equals("四川蜀道物流集团有限公司")){
			jc="蜀道物流集团";
		}if(qc.equals("蜀道交通服务集团有限责任公司")){
			jc="蜀道交通服务集团";
		}if(qc.equals("蜀道资本控股集团有限公司")){
			jc="蜀道资本集团";
		}if(qc.equals("四川蜀道智慧交通集团有限公司")){
			jc="蜀道智慧交通集团";
		}if(qc.equals("四川交投设计咨询研究院有限责任公司")){
			jc="设计咨询研究院";
		}
		return jc;
	}
}
