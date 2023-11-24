package lq.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lq.model.QjlbInfo;
import lq.util.C3P0Utils;

public class GetQjlbInfo {
	public ArrayList<QjlbInfo> getQjlbInfo() {
		ArrayList<QjlbInfo> list=new ArrayList<QjlbInfo>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer empString =new StringBuffer("");
		empString.append("select p.fname_l2 name,o.fname_l2 bm,pos.fname_l2 gw,htp.FNAME_L2 qjlb,\n");
		empString.append("le.frealbegintime qjsj,le.FRealLeaveLength qjts,hl.FREMAINLIMIT sysj\n");
		empString.append("from T_HR_ATS_LeaveBill lb inner join T_HR_ATS_LeaveBillEntry le on le.fbillid=lb.fid\n");
		empString.append("inner join t_bd_person p on p.fid=lb.FProposer\n");
		empString.append("left join T_ORG_Position pos on pos.fid=le.FPositionID\n");
		empString.append("left join t_org_admin o on o.fid=lb.FADMINORGUNITID\n");
		empString.append("left join T_HR_ATS_HolidayPolicy hp on hp.fid=le.FPolicyID\n");
		empString.append("left join T_HR_ATS_HolidayType htp on htp.fid=hp.FHolidayTypeID\n");
		empString.append("left join T_HR_ATS_HolidayLimit hl on hl.FProposerID=p.fid\n");
		empString.append("where o.fdisplayname_l2 like '%四川蜀道高速公路集团有限公司%'"); 
		empString.append("order by p.FNUMBER");
		PreparedStatement ddpst=null;
		ResultSet ddrs=null;
		Connection dbconnection = C3P0Utils.getConnection();
		try {
			ddpst = dbconnection.prepareStatement(empString.toString());
			ddrs=ddpst.executeQuery();
			int i=1;
			while (ddrs.next()) {
				QjlbInfo info=new QjlbInfo();
				info.setIndex(i);	
				info.setName(ddrs.getString("name"));
				info.setBm(ddrs.getString("bm"));
				info.setGw(ddrs.getString("gw"));
				info.setQjlb(ddrs.getString("qjlb"));
				info.setQjcs("1");
				info.setQjsj(sdf.format(ddrs.getDate("qjsj")));
				info.setQjts(ddrs.getBigDecimal("qjts"));
				info.setSysj(ddrs.getBigDecimal("sysj"));
				list.add(info);
				i++;
			}									
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			C3P0Utils.closeAll(ddpst,ddrs,dbconnection);
		}					
		return list;		
	}
	
}
