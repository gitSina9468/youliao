//package com.seahorse.youliao.controller.easypoi;
//
//import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
//import cn.afterturn.easypoi.excel.entity.ExcelToHtmlParams;
//import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.springframework.util.ResourceUtils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.util.Scanner;
//
///**
// * @description: html to excel
// * @author: Mr.Song
// * @create: 2020-02-06 18:39
// **/
//public class ExcelXorHtmlUtilTest {
//
//
//
//    public void testToTableHtmlWorkbook() throws Exception {
////        Workbook wb = new HSSFWorkbook(new FileInputStream(new File(FileUtilTest
////                .getWebRootPath("WEB-INF/doc/专项支出用款申请书.xls"))));
//        Workbook wb = new HSSFWorkbook(new FileInputStream( ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"exceltohtml/专项支出用款申请书.xls")));
//        String     html = ExcelXorHtmlUtil.toTableHtml(wb);
//        FileWriter fw   = new FileWriter("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/专项支出用款申请书_table.html");
//        fw.write(html);
//        fw.close();
//    }
//
//    public void testToTableHtmlWorkbookRowNum() throws Exception {
////        Workbook wb = new HSSFWorkbook(new FileInputStream(new File(FileUtilTest
////                .getWebRootPath("WEB-INF/doc/专项支出用款申请书.xls"))));
//
//        Workbook wb = new HSSFWorkbook(new FileInputStream( ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"exceltohtml/专项支出用款申请书.xls")));
//        ExcelToHtmlParams params = new ExcelToHtmlParams(wb, false, 0, null);
//        String            html   = ExcelXorHtmlUtil.excelToHtml(params);
//        FileWriter        fw     = new FileWriter("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/专项支出用款申请书_table_RowNum.html");
//        fw.write(html);
//        fw.close();
//    }
//
//    public void testToTableHtmlWorkbookInt() throws Exception {
////        Workbook wb = new HSSFWorkbook(new FileInputStream(new File(FileUtilTest
////                .getWebRootPath("doc/exportTemp.xls"))));
//
//        Workbook wb = new HSSFWorkbook(new FileInputStream( ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"exceltohtml/专项支出用款申请书.xls")));
//        String     html = ExcelXorHtmlUtil.excelToHtml(new ExcelToHtmlParams(wb, 1));
//        FileWriter fw   = new FileWriter("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/exportTemp_table.html");
//        fw.write(html);
//        fw.close();
//    }
//
//    public void testToAllHtmlWorkbookAndImage() throws Exception {
//
//        //Workbook wb = new HSSFWorkbook(new FileInputStream(new File("html/exportTemp_image.xls")));
////        Workbook wb = new HSSFWorkbook(new FileInputStream(new File(
////                FileUtilTest.getWebRootPath("html/exportTemp_image.xls"))));
//        Workbook wb = new HSSFWorkbook(new FileInputStream( ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"exceltohtml/专项支出用款申请书.xls")));
//        long       d    = System.nanoTime();
//        String     html = ExcelXorHtmlUtil.excelToHtml(new ExcelToHtmlParams(wb, true, "yes"));
//        FileWriter fw   = new FileWriter("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/exportTemp_image_all.html");
//        fw.write(html);
//        fw.close();
//
//        System.err.println(System.nanoTime() - d);
//        d = System.nanoTime();
//        html = ExcelXorHtmlUtil.excelToHtml(new ExcelToHtmlParams(wb, true, "D:/home/excel/"));
//        fw = new FileWriter("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/exportTemp_image_all_cache.html");
//        fw.write(html);
//        fw.close();
//        System.err.println(System.nanoTime() - d);
//    }
//
//    public void testToAllHtmlWorkbook() throws Exception {
//
//        Workbook wb = new HSSFWorkbook(new FileInputStream(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"exceltohtml/专项支出用款申请书.xls")));
//        //            Workbook wb = new HSSFWorkbook(new FileInputStream(
//        //                new File(
//        //                    PoiPublicUtil
//        //                    .getWebRootPath("doc/专项支出用款申请书.xls"))));
//        String     html = ExcelXorHtmlUtil.toAllHtml(wb);
//        FileWriter fw   = new FileWriter("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/专项支出用款申请书_all.html");
//        fw.write(html);
//        fw.close();
//    }
//
//    public void testToAllHtmlWorkbookInt() throws Exception {
////        Workbook wb = new HSSFWorkbook(new FileInputStream(new File(FileUtilTest.getWebRootPath("doc/exportTemp.xls"))));
//        Workbook wb = new HSSFWorkbook(new FileInputStream( ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"exceltohtml/专项支出用款申请书.xls")));
//        String     html = ExcelXorHtmlUtil.excelToHtml(new ExcelToHtmlParams(wb, true, 1, null));
//        FileWriter fw   = new FileWriter("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/exportTemp_all.html");
//        fw.write(html);
//        fw.close();
//    }
//
//
//    public static void main(String[] args) throws Exception{
//
//        ExcelXorHtmlUtilTest test = new ExcelXorHtmlUtilTest();
//        test.htmlToExcelByStr();
//    }
//
//
//    public void htmlToExcelByStr() throws Exception {
//        StringBuilder html = new StringBuilder();
//        Scanner s = new Scanner(getClass().getResourceAsStream("/html/qwe.ftl"), "utf-8");
//        while (s.hasNext()) {
//            html.append(s.nextLine());
//        }
//        s.close();
//        Workbook workbook = ExcelXorHtmlUtil.htmlToExcel(html.toString(), ExcelType.XSSF);
//        File savefile = new File("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse");
//        if (!savefile.exists()) {
//            savefile.mkdirs();
//        }
//        FileOutputStream fos = new FileOutputStream("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/htmlToExcelByStr.xlsx");
//        workbook.write(fos);
//        fos.close();
//        workbook = ExcelXorHtmlUtil.htmlToExcel(html.toString(), ExcelType.HSSF);
//        fos = new FileOutputStream("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/htmlToExcelByStr.xls");
//        workbook.write(fos);
//        fos.close();
//    }
//
//    public void htmlToExcelByIs() throws Exception {
//        Workbook workbook = ExcelXorHtmlUtil.htmlToExcel(getClass().getResourceAsStream("/html/sample.html"), ExcelType.XSSF);
//        File savefile = new File("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse");
//        if (!savefile.exists()) {
//            savefile.mkdirs();
//        }
//        FileOutputStream fos = new FileOutputStream("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/htmlToExcelByIs.xlsx");
//        workbook.write(fos);
//        fos.close();
//        workbook = ExcelXorHtmlUtil.htmlToExcel(getClass().getResourceAsStream("/html/sample.html"), ExcelType.HSSF);
//        fos = new FileOutputStream("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/htmlToExcelByIs.xls");
//        workbook.write(fos);
//        fos.close();
//    }
//}
