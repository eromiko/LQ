package lq.template.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import freemarker.template.Configuration;
import lq.model.DdtzInfo;
import lq.service.GetDdtzInfo;
import lq.util.DataMapUtil;
import lq.util.DdtzWordGenerator;
/**
 * 
 * 导出调动通知表
 *
 */

@WebServlet("/DdtzServlet")
public class Ddtzexport extends HttpServlet {
	private Configuration configure = null;
	private static final long serialVersionUID = 1L;
	public Ddtzexport() {
		this.configure = new Configuration();
		this.configure.setDefaultEncoding("utf-8");
	}
	GetDdtzInfo getddtzInfo=new GetDdtzInfo();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String billid=request.getParameter("billid");
		try {
			Map map = getDataMap("ddtz", billid);		
			String name =map.get("person").toString();
			File file = null;
			InputStream fin = null;
			ServletOutputStream out = null;
			try {
				file = DdtzWordGenerator.createDoc(map, "调动通知表");
	            fin = new FileInputStream(file);			    
	            response.setCharacterEncoding("utf-8");
	            response.setContentType("application/msword");
	            response.addHeader("Content-Disposition", "attachment;filename="
	                    + new String((name+"_调动通知表.doc").getBytes("GB2312"), "ISO_8859_1"));
				out = response.getOutputStream();
	            byte[] buffer = new byte[512]; // 缓冲区
	            int b = -1;
	            // 通过循环将读入的Word文件的内容输出到浏览器中
	            while ((b = fin.read(buffer)) != -1) {
	                out.write(buffer, 0, b);
	            }	            
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fin != null)
					fin.close();
				if (out != null)
					out.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}		
	}
	
	private Map<String, Object> getDataMap(String downloadType, String billid)
			throws SQLException, ParseException {
		Map dataMap = new HashMap();
		if ("ddtz".equals(downloadType)) {
			DdtzInfo person = getddtzInfo.getDdtzInfo(billid);			
			dataMap = DataMapUtil.setObjToMap(person);
		}
		return dataMap;
	}
}
