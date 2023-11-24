package lq.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GrjlWordGenerator {
    private static Configuration configuration = null;
    private static Map<String, Template> allTemplates = null;

    static {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(GrjlWordGenerator.class, "/lq/template");
        allTemplates = new HashMap<>(); // Java 7 钻石语法
        try {
            allTemplates.put("个人简历",configuration.getTemplate("grjl.xml"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private GrjlWordGenerator() {
        throw new AssertionError();
    }

    public static File createDoc(Map<?, ?> dataMap, String filename)
            throws TemplateException, IOException {
        String name = "temp" + (int) (Math.random() * 100000) + ".doc";
        File f = new File(name);
        Template t = allTemplates.get(filename);

        // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
        Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
        t.process(dataMap, w);
        w.close();

        return f;
    }

    // 将图片转换成BASE64字符串
    public static String getImageString(String imgname) throws IOException {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgname);
            data = new byte[in.available()];
            in.read(data);


            in.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null)
                in.close();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return data != null ? encoder.encode(data) : "";
    }

}

