package lq.template.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jxls.transformer.XLSTransformer;
import lq.model.QjlbInfo;
import lq.service.GetQjlbInfo;
/**
 * 
 * 导出调动汇总表
 *
 */
@WebServlet("/qjlbServletExcel")
public class QjlbexportExcel extends HttpServlet {
    
	public QjlbexportExcel() {
	}
	GetQjlbInfo getQjlbInfo=new GetQjlbInfo();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//获取数据
		ArrayList<QjlbInfo> resultdata = getQjlbInfo.getQjlbInfo();
		//数据分类
		
		//获得模版
		String tempFileName = Thread.currentThread().getContextClassLoader().getResource("").getPath(); 
		tempFileName=tempFileName+ "lq/template";
		Map<String, ArrayList<QjlbInfo>> beans = new HashMap();
		tempFileName += "/qjlb.xlsx"; 
		//导出名		
		String fileName ="蜀道高速集团休假明细表";
		beans.put("qjlblist", resultdata);
        //文件名称统一编码格式
		fileName = URLEncoder.encode(fileName, "utf-8");		
		//生成的导出文件
		File destFile = File.createTempFile(fileName,".xlsx");		
		//transformer转到Excel
		XLSTransformer transformer = new XLSTransformer();		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
            //将数据添加到模版中生成新的文件
			transformer.transformXLS(tempFileName, beans, destFile.getAbsolutePath());
            //将文件输入
			InputStream inputStream = new FileInputStream(destFile);
			// 设置response参数，可以打开下载页面
			response.reset();
            //设置响应文本格式
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
			//将文件输出到页面
            ServletOutputStream out = response.getOutputStream();
			bis = new BufferedInputStream(inputStream);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// 根据读取并写入
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {			
			e.printStackTrace();
		} finally {
            //使用完成后关闭流
			try {
				if (bis != null)
				    bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				
			}
		}
	}
}
