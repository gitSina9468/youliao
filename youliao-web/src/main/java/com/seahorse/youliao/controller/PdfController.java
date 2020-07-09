package com.seahorse.youliao.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import com.seahorse.youliao.utils.PDFUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: pdf 预览下载
 * @author: Mr.Song
 * @create: 2020-02-01 19:55
 **/
@Api(value = "PdfController", tags = "pdf 预览下载")
@RequestMapping("freemarker")
@RestController
public class PdfController {


    private static Configuration freemarkerCfg = null;

    @ApiOperation(value = "预览pdf")
    @GetMapping(value = "viewPdf")
    public void viewPdf(HttpServletResponse response) throws DocumentException ,CssResolverException{
        //需要填充的数据
        Map<String, Object> data = new HashMap<>(16);
        data.put("name", "李白");

        String content = freeMarkerRender(data,"freemarker.ftl");
        // 读取pdf
        readBytePdf(content,response);

    }

    @ApiOperation(value = "下载pdf")
    @GetMapping(value = "printPdf")
    public void printPdf(HttpServletResponse response) throws DocumentException ,CssResolverException{
        //需要填充的数据
        Map<String, Object> data = new HashMap<>(16);
        data.put("name", "张三");

        String content = freeMarkerRender(data,"freemarker.ftl");

        //下载pdf
        createPdf(content,response);

    }



    /**
     * freemarker渲染html
     */
    public  String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();

        try {
            // 获取模板,并设置编码方式
            setFreemarkerCfg();
            Template template = freemarkerCfg.getTemplate(htmlTmp);
            //将合并后的数据和模板写入到流中，这里使用的字符流
            template.process(data, out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 设置freemarkerCfg
     */
    private void setFreemarkerCfg() {
        freemarkerCfg = new Configuration(Configuration.VERSION_2_3_25);
        freemarkerCfg.setDefaultEncoding("UTF-8");
        //freemarker的模板目录
        try {
            freemarkerCfg.setDirectoryForTemplateLoading(new ClassPathResource("templates").getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取本地pdf,这里设置的是预览
     */
    private void createPdf(String content,HttpServletResponse response) throws DocumentException,CssResolverException{
        response.reset();
        response.setContentType("application/pdf");
        try {
            OutputStream outputStream = response.getOutputStream();
//            强制浏览器下载
            response.setHeader("content-disposition", "attachment;filename=" + "html.pdf");
            IOUtils.write(PDFUtils.html2pdf(content), outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取本地pdf,这里设置的是预览
     */
    private void readBytePdf(String content,HttpServletResponse response)throws DocumentException,CssResolverException {
        response.reset();
        response.setContentType("application/pdf");
        try {
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(PDFUtils.html2pdf(content), outputStream);
            //浏览器预览
            response.setHeader("Content-Disposition",
                    "inline; filename= file");
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
