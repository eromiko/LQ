package lq.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lq.util.C3P0Utils;

public class DdtzCheckJieDian {
	public boolean DdtzJD(String billId) {
		boolean result=false;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) fexist\n");
		sql.append("from T_HR_FlucOutBizBill bill\n");
		sql.append("inner join t_WFR_ProcinstRef bizRef on bill.fid=bizRef.FrefID\n");
		sql.append("inner join t_wfr_actInst act on bizRef.FProcInstID=act.FProcInstID\n");
		sql.append("left join t_wfr_Assigndetail detail on detail.FActdefID=act.FActdefID and detail.FProcInstID=act.FProcInstID\n");
		sql.append("left join T_BAS_MultiApprove approve on approve.FASSIGNMENTID=detail.FAssignID\n");
		sql.append("left join T_PM_User u on approve.FCreatorID = u.FID\n");
		sql.append("where act.FACTDEFNAME_L2='集团调令签发' and act.FSTATE='open.not_running.not_started' and bill.fid='"+billId+"'");
		PreparedStatement pst=null;
		ResultSet rs=null;
		Connection dbconnection = C3P0Utils.getConnection();
		try {
			pst = dbconnection.prepareStatement(sql.toString());
			rs=pst.executeQuery();
			while (rs.next()) {									
				int s = rs.getInt("fexist");
				if (s>0) {
					result=true;
					break;
				}		
			}									
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			C3P0Utils.closeAll(pst,rs,dbconnection);
		}					
		return result;		
	}
}
