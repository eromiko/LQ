package lq.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import lq.model.KzsInfo;
import lq.model.KzsInfoList;
import lq.util.C3P0Utils;

public class GetKzsInfo {
	public ArrayList<KzsInfo> getKzsInfo(String rylbIds,String iskzs) {
		ArrayList<KzsInfo> list=new ArrayList<KzsInfo>();
		StringBuffer empString =new StringBuffer("");
		empString.append("select p.FName_l2 name,case when p.FGender=1 then '男' when p.FGender=2 then '女' end gender,floor(((months_between(sysdate,p.FBirthday))/12)) age,\n");
		empString.append("ry.FNAME_L2 rylb,qrzxl.FName_l2 qrz,qrz.FGraduateSchool||qrz.FSpecialty qrzby,zzxl.FName_l2 zz,zz.FGraduateSchool||zz.FSpecialty zzby,\n");
		empString.append("ht.FName_l2 zczs,lz.fsimplename lzs,lz.fsimplename||'/'||lc.FName_l2||lo.fname_l2||lp.fname_l2 lgzjl,nz.fsimplename nzs,nz.fnumber nzsNo,nc.FName_l2 ngzjl,\n");
		empString.append("be.CFGZSZD lgzszd,be.cfngzszd ngzszd\n");
		empString.append("from T_HR_FlucOutBizBillEntry be inner join T_HR_FlucOutBizBill bill on bill.fid=be.FBILLID\n");
		empString.append("inner join T_BD_Person p on p.fid=be.FPersonID\n");
		empString.append("inner join t_WFR_ProcinstRef bizRef on bill.fid=bizRef.FrefID\n");
		empString.append("inner join t_wfr_actInst act on bizRef.FProcInstID=act.FProcInstID\n");
		empString.append("left join t_wfr_Assigndetail detail on detail.FActdefID=act.FActdefID and detail.FProcInstID=act.FProcInstID\n");
		empString.append("left join T_BAS_MultiApprove approve on approve.FASSIGNMENTID=detail.FAssignID\n");		
		empString.append("left join CT_MP_Rylbbas ry on ry.fid=p.CFRylbID\n");
		empString.append("left join T_HR_PersonDegree qrz on qrz.FPersonID=p.FID and qrz.FIsHighestBefore=1\n");
		empString.append("left join T_BD_HRDiploma qrzxl on qrzxl.fid=qrz.FDiploma\n");
		empString.append("left join T_HR_PersonDegree zz on zz.fpersonid=p.FID and zz.FIsHighWorkDip=1\n");
		empString.append("left join T_BD_HRDiploma zzxl on zzxl.fid=zz.FDiploma\n");
		empString.append("left join T_HR_PersonTechPost tp on tp.FPersonID=p.FID and tp.FIsHighTechnical=1\n");
		empString.append("left join T_HR_BDTechnicalPost ht on ht.fid=tp.FTechnicalPostID\n"); 
		empString.append("left join T_ORG_Admin lc on lc.fid=be.FOldCompanyID\n");
		empString.append("left join T_ORG_Admin nc on nc.fid=be.FCompanyID\n");
		empString.append("left join T_ORG_Admin lo on lo.fid=be.FOldAdminOrgID\n");
		empString.append("left join T_ORG_Admin no on no.fid=be.FAdminOrgID\n");
		empString.append("left join T_ORG_Position lp on lp.fid=be.FOldPositionID\n");
		empString.append("left join T_ORG_Position np on np.fid=be.FPositionID\n");
		empString.append("left join T_ORG_Admin lz on lz.fid=lc.FOfficeID\n");
		empString.append("left join T_ORG_Admin nz on nz.fid=nc.FOfficeID\n");
		empString.append("where act.FActDefName_l2='蜀道集团复审' and act.FSTATE='open.not_running.not_started' and bill.FBILLSTATE in (1,2) and \n");
		if (rylbIds.equals("null")) {
			empString.append("be.CFIskzs="+Integer.parseInt(iskzs)+" order by nz.FSortCode\n");
		} else {
			empString.append("be.CFIskzs="+Integer.parseInt(iskzs)+" and\n");
			List<String> asList = Arrays.asList(rylbIds.split(","));
			if (asList.size()==1) {
				empString.append("ry.fid='"+asList.get(0)+"' \n");
			} else {
				for (int i = 0; i < asList.size(); i++) {
					if (i==0) {
						empString.append("(ry.fid='"+asList.get(i)+"' or ");
					}else if (i==asList.size()-1) {
						empString.append("ry.fid='"+asList.get(i)+"')");
					}else{
						empString.append("ry.fid='"+asList.get(i)+"' or ");
					}				
				}
			}	
			empString.append(" order by nz.FSortCode");
		}				
		PreparedStatement ddpst=null;
		ResultSet ddrs=null;
		Connection dbconnection = C3P0Utils.getConnection();
		try {
			ddpst = dbconnection.prepareStatement(empString.toString());
			ddrs=ddpst.executeQuery();
			int i=1;
			while (ddrs.next()) {
				KzsInfo info=new KzsInfo();
				info.setNzsNo(ddrs.getString("nzsNo"));
				info.setIndex(i);
				info.setName(ddrs.getString("name"));
				info.setGender(ddrs.getString("gender"));
				info.setAge(ddrs.getInt("age"));
				info.setRylb(ddrs.getString("rylb"));
				info.setQrz(ddrs.getString("qrz"));
				info.setQrzby(ddrs.getString("qrzby"));
				info.setZz(ddrs.getString("zz"));
				info.setZzby(ddrs.getString("zzby"));
				info.setZczs(ddrs.getString("zczs"));
				info.setLgzjl(ddrs.getString("lgzjl"));
				info.setNgzjl(ddrs.getString("ngzjl"));
				info.setLgzszd(ddrs.getString("lgzszd"));
				info.setNgzszd(ddrs.getString("ngzszd"));
				info.setNzs(ddrs.getString("nzs"));				
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
	
	public ArrayList<KzsInfoList> getdata(ArrayList<KzsInfo> kzslist){
		ArrayList<KzsInfoList> list=new ArrayList<KzsInfoList>();			
		for (int i = 0; i < kzslist.size(); i++) {
			KzsInfoList kzsInfoList=new KzsInfoList();
			KzsInfo kzsInfo = kzslist.get(i);
			kzsInfoList.setNzs(kzsInfo.getNzs());
			kzsInfoList.setNzsNo(kzsInfo.getNzsNo());			
			kzsInfoList.getKzsinfos().add(kzsInfo);
			list.add(kzsInfoList);
		}			
		LinkedHashMap<String,KzsInfoList> tempMap = new  LinkedHashMap<String,KzsInfoList>();
        //去掉重复的key
		for (int j = 0; j < list.size(); j++) {
			KzsInfoList kzsinfo = list.get(j);
            String nzsNo = kzsinfo.getNzsNo();
            String nzs = kzsinfo.getNzs();
            if(tempMap.containsKey(nzsNo)){
            	KzsInfoList newDataReport= new KzsInfoList();
                newDataReport.setNzsNo(nzsNo);
                newDataReport.setNzs(nzs);
                //合并相同key的value
                newDataReport.setKzsinfos(tempMap.get(nzsNo).getKzsinfos());
                newDataReport.getKzsinfos().add(kzsinfo.getKzsinfos().get(0)); 
                newDataReport.setCount(newDataReport.getKzsinfos().size());
                //HashMap不允许key重复，当有key重复时，前面key对应的value值会被覆盖
                tempMap.put(nzsNo,newDataReport);
            }
            else{
            	kzsinfo.setCount(1);
                tempMap.put(nzsNo,kzsinfo);
            }
        }
      //去除重复key的list
        ArrayList<KzsInfoList> list2=new ArrayList<KzsInfoList>();	
        for(String temp:tempMap.keySet()){
        	list2.add(tempMap.get(temp));
        }
        return list2;
	}
}
