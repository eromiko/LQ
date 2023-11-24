package lq.template.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import freemarker.template.Configuration;
import lq.model.Person;
import lq.service.GetPersonInfo;
import lq.util.DataMapUtil;
import lq.util.DdspWordGenerator;
/**
 * 
 * 导出员工调动审批表
 *
 */

@WebServlet("/DdspServlet")
public class Ddspexport extends HttpServlet {
	private Configuration configure = null;
	private static final long serialVersionUID = 1L;
	public Ddspexport() {
		this.configure = new Configuration();
		this.configure.setDefaultEncoding("utf-8");
	}
	GetPersonInfo personInfo=new GetPersonInfo();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String personId=request.getParameter("personId");
		try {			
			Map map = getDataMap("ddsp", personId);
			String personImg = personInfo.getPersonImg(personId);			
			map.put("img",personImg);
			String name =map.get("name").toString();
			File file = null;
			InputStream fin = null;
			ServletOutputStream out = null;
			try {
				file = DdspWordGenerator.createDoc(map, "员工调动审批表");
	            fin = new FileInputStream(file);			    
	            response.setCharacterEncoding("utf-8");
	            response.setContentType("application/msword");
	            response.addHeader("Content-Disposition", "attachment;filename="
	                    + new String((name+"_员工调动审批表.doc").getBytes("UTF-8"), "ISO_8859_1"));
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
		}		
	}
	
	private Map<String, Object> getDataMap(String downloadType, String personId)
			throws SQLException {
		Map dataMap = new HashMap();
		if ("ddsp".equals(downloadType)) {
			Person person = personInfo.addPerson(personId,"1");
			dataMap = DataMapUtil.setObjToMap(person);
		}

		return dataMap;
	}
}
