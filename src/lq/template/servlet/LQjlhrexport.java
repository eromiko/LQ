package lq.template.servlet;

import freemarker.template.Configuration;
import lq.model.Person;
import lq.service.GetPersonInfo;
import lq.util.DataMapUtil;
import lq.util.GrjlWordGenerator;
import lq.util.WordGenerator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 导出个人简历
 *
 */

@WebServlet("/JLHrPersonServlet")
public class LQjlhrexport extends HttpServlet {
	private Configuration configure = null;
	private static final long serialVersionUID = 1L;

	public LQjlhrexport() {
		this.configure = new Configuration();
		this.configure.setDefaultEncoding("utf-8");
	}
     
	GetPersonInfo personInfo=new GetPersonInfo();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String personId=request.getParameter("personId");
		//type: 1=需要变动记录   2=不需要变动记录
		String type=request.getParameter("type");
		try {	
			Map map = getDataMap("grjl", personId,type);
			String personImg = personInfo.getPersonImg(personId);			
			map.put("img",personImg);
			String name =map.get("name").toString();
//			ImageUtil.generateImage(personImg, "xxx", request);
			//测试
			File file = null;
		    //测试
			InputStream fin = null;
			ServletOutputStream out = null;
			try {
//				Template template = null;
//				this.configure.setClassForTemplateLoading(this.getClass(),
//						"/sdig/template");
//				this.configure.setObjectWrapper(new DefaultObjectWrapper());
//				this.configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
//				template = this.configure.getTemplate("testxml1.xml");
				//测试
				file = GrjlWordGenerator.createDoc(map, "个人简历");
	            fin = new FileInputStream(file);
			    //测试
//				String fileName = "干部任免审批表.doc";
//				response.reset();
//				response.setCharacterEncoding("UTF-8");
//				response.setContentType("application/msword");
//				String filename02=new String(fileName.getBytes("utf-8"),"ISO-8859-1");
//				response.setHeader("Content-Disposition", "attachment;filename="+filename02);
				//测试
	            response.setCharacterEncoding("utf-8");
	            response.setContentType("application/msword");
	            response.addHeader("Content-Disposition", "attachment;filename="
	                    + new String((name+"_个人简历.doc").getBytes("UTF-8"), "ISO_8859_1"));
				out = response.getOutputStream();
	            byte[] buffer = new byte[512]; // 缓冲区
	            int b = -1;
	            // 通过循环将读入的Word文件的内容输出到浏览器中
	            while ((b = fin.read(buffer)) != -1) {
	                out.write(buffer, 0, b);
	            }
	            //测试
//				Writer out = response.getWriter();
//				template.process(map, out);
//				out.close();
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
	
	private Map<String, Object> getDataMap(String downloadType, String personId,String type)
			throws SQLException {
		Map dataMap = new HashMap();
		if ("grjl".equals(downloadType)) {
			Person person = personInfo.GrjladdPerson(personId,type);
			dataMap = DataMapUtil.setObjToMap(person);
		}

		return dataMap;
	}
}
