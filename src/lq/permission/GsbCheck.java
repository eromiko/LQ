package lq.permission;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import lq.util.C3P0Utils;
/**
 * 干部任免审批表预览和导出权限
 * 未是使用此权限校验，使用标准功能权限校验
 *
 */
@WebServlet("/userPermissionServlet")
public class GsbCheck extends HttpServlet {
	
	public GsbCheck() {
		
	}
	private static ObjectMapper MAPPER =  new ObjectMapper();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		super.doGet(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId=req.getParameter("userId");
		boolean result = getUserPermission(userId);
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.getWriter().print(result);
	}
	
	private boolean getUserPermission(String userId) {
		boolean result=false;
		String persql = "select a.fnumber uno,a.fname_l2 uan,b.fname_l2 pna,b.fnumber pno from T_PM_User a inner join T_PM_UserRoleOrg c "
				+ "on c.FUserID=a.fid inner join T_PM_Role b on c.FRoleID=b.fid "
				+ "where a.fid='"+userId+"'";
		PreparedStatement pst=null;
		ResultSet rs=null;
		Connection dbconnection = C3P0Utils.getConnection();
		try {
			pst = dbconnection.prepareStatement(persql);
			rs=pst.executeQuery();
			while (rs.next()) {									
				String pna = rs.getString("pno");
				if (pna.equals("999")) {
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
