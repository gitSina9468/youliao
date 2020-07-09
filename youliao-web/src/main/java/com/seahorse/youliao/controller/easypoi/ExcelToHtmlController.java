//package com.seahorse.youliao.controller.easypoi;
//
//import cn.afterturn.easypoi.cache.manager.POICacheManager;
//import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
//import cn.afterturn.easypoi.excel.entity.ExcelToHtmlParams;
//import io.swagger.annotations.Api;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @description: excel 转 html
// * @author: Mr.Song
// * @create: 2020-02-06 15:23
// **/
//@Api(value = "ExcelToHtmlController", tags = "easypoi excel 转 html")
//@Controller
//@RequestMapping("/easypoi/tohtml")
//public class ExcelToHtmlController {
//
//    /**
//     * 03 版本EXCEL预览
//     */
//    @GetMapping("03")
//    public void toHtmlOf03Base(HttpServletResponse response) throws IOException, InvalidFormatException {
//        ExcelToHtmlParams params = new ExcelToHtmlParams(WorkbookFactory.create(POICacheManager.getFile("exceltohtml/testExportTitleExcel.xls")));
//        response.getOutputStream().write(ExcelXorHtmlUtil.excelToHtml(params).getBytes());
//    }
//    /**
//     * 07 版本EXCEL预览
//     */
//    @GetMapping("07")
//    public void toHtmlOf07Base(HttpServletResponse response) throws IOException, InvalidFormatException {
//        ExcelToHtmlParams params = new ExcelToHtmlParams(WorkbookFactory.create(POICacheManager.getFile("exceltohtml/testExportTitleExcel.xlsx")));
//        response.getOutputStream().write(ExcelXorHtmlUtil.excelToHtml(params).getBytes());
//    }
//    /**
//     * 03 版本EXCEL预览
//     */
//    @GetMapping("03img")
//    public void toHtmlOf03Img(HttpServletResponse response) throws IOException, InvalidFormatException {
//        ExcelToHtmlParams params = new ExcelToHtmlParams(WorkbookFactory.create(POICacheManager.getFile("exceltohtml/exporttemp_img.xls")),true,"yes");
//        response.getOutputStream().write(ExcelXorHtmlUtil.excelToHtml(params).getBytes());
//    }
//    /**
//     * 03 版本EXCEL预览
//     */
//    @GetMapping("03user")
//    public void toHtmlOf03User(HttpServletResponse response) throws IOException, InvalidFormatException {
//        ExcelToHtmlParams params = new ExcelToHtmlParams(WorkbookFactory.create(POICacheManager.getFile("exceltohtml/user.xls")),true,"yes");
//        response.getOutputStream().write(ExcelXorHtmlUtil.excelToHtml(params).getBytes());
//    }
//    /**
//     * 07 版本EXCEL预览
//     */
//    @GetMapping("07img")
//    public void toHtmlOf07Img(HttpServletResponse response) throws IOException, InvalidFormatException {
//        ExcelToHtmlParams params = new ExcelToHtmlParams(WorkbookFactory.create(POICacheManager.getFile("exceltohtml/exportTemp_image.xlsx")),true,"yes");
//        response.getOutputStream().write(ExcelXorHtmlUtil.excelToHtml(params).getBytes());
//    }
//    /**
//     * 年度财务报表
//     */
//    @GetMapping("financial")
//    public void financialReport(HttpServletResponse response) throws IOException, InvalidFormatException {
//        ExcelToHtmlParams params = new ExcelToHtmlParams(WorkbookFactory.create(POICacheManager.getFile("exceltohtml/年度财务报表.xlsx")),true,"yes");
//        response.getOutputStream().write(ExcelXorHtmlUtil.excelToHtml(params).getBytes());
//    }
//
//
//}