package com.seahorse.youliao.utils;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

/**
 * @description: freemarker工具类
 * @author: Mr.Song
 * @create: 2019-12-24 21:56
 **/
public class FreeMarkerUtil {


    private static Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);

    /**
     * 模板解析
     *
     * @param root         数据原
     * @param inputStream  模板目录
     * @param templateFile 模板文件
     * @return 返回结果
     */
    public static String templateAnalysis(Map<String, Object> root, InputStream inputStream, String templateFile) {

        String content = "";

        try {
            //创建配置实例 Configuration
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
            cfg.setDefaultEncoding("utf-8");

            //读取到根目录
            InputStreamReader isr = null;
            BufferedReader br = null;

            StringBuffer stringBuffer = new StringBuffer();
            try {
                isr = new InputStreamReader(inputStream,"utf-8");
                br = new BufferedReader(isr);

                String lineStr = "";
                while ((lineStr = br.readLine()) != null) {
                    stringBuffer.append(lineStr);
                }
            } catch (Exception e) {
                logger.error(e.toString());
            } finally {
                br.close();
                isr.close();
            }
            StringTemplateLoader stl = new StringTemplateLoader();
            String fileName = templateFile.replace("ftl", "");
            stl.putTemplate(fileName, stringBuffer.toString());

            cfg.setTemplateLoader(stl);

            //工程目录下的目录
//            cfg.setDirectoryForTemplateLoading(file);

            //获取模板（template）
            Template template = cfg.getTemplate(fileName);

            //获取输出流（指定到控制台（标准输出））
            StringWriter out = new StringWriter();

            //数据与模板合并（数据+模板=输出）
            template.process(root, out);

            content = out.toString();

            out.flush();
        } catch (TemplateException ex) {
            logger.error(ex.toString());
        } catch (IOException ex) {
            logger.error(ex.toString());
        }

        //返回结果
        return content;
    }
}
