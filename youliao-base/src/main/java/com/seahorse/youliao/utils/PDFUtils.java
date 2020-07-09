package com.seahorse.youliao.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.ElementHandlerPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @description: PDF工具类
 * @author: Mr.Song
 * @create: 2020-02-03 10:01
 **/
public class PDFUtils {


    /**
     * 把html转换成pdf，以字节数组的形式返回pdf文件
     * @param html
     * @return pdf字节数组
     * @throws IOException
     * @throws DocumentException
     * @throws CssResolverException
     */
    public static byte[] html2pdf(String html) throws IOException, DocumentException,CssResolverException {
        Document document = new Document(PageSize.A4);
        //document.setPageCount(40);
        //document.setPageSize(PageSize.A4);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document,os);
        document.open();

        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(){
            @Override
            public Font getFont(String fontname, String encoding, float size, int style) {
                return super.getFont(fontname == null ? "宋体" : fontname, encoding, size, style);
            }
        };
        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);

        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);

        CSSResolver cssResolver = new StyleAttrCSSResolver();
        ElementList elements = new ElementList();
        ElementHandlerPipeline end = new ElementHandlerPipeline(elements, null);
        HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, end);
//    CssResolverPipeline cssPipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
        CssResolverPipeline cssPipeline = new CssResolverPipeline(cssResolver, new PdfWriterPipeline(document,writer));
        // XML Worker
        XMLWorkerHelper worker =  XMLWorkerHelper.getInstance();
        //html = html.replace('\"','\'');
        worker.parseXHtml(writer, document, new ByteArrayInputStream(html.getBytes("UTF-8")),new ByteArrayInputStream(html.getBytes()),Charset.forName("UTF-8"),fontProvider);
        document.close();
        return os.toByteArray();
    }

    /**
     * 生成pdf文件
     * @param content
     * @param dest
     * @throws IOException
     * @throws DocumentException
     */
    public static void createPdf(String content,String dest) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        // step 3
        document.open();
        // step 4
//        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
//        fontImp.register("/static/font/fangsong.ttf");
//        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
//                new ByteArrayInputStream(content.getBytes("UTF-8")), null, Charset.forName("UTF-8"), fontImp);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new StringReader(content));
        // step 5
        document.close();

    }
}
