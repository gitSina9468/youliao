//package com.seahorse.youliao.controller.easypoi;
//
//import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
//import cn.afterturn.easypoi.excel.annotation.Excel;
//import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
//import cn.afterturn.easypoi.pdf.PdfExportUtil;
//import cn.afterturn.easypoi.pdf.entity.PdfExportParams;
//import cn.afterturn.easypoi.view.PoiBaseView;
//import cn.afterturn.easypoi.word.WordExportUtil;
//import com.itextpdf.text.Document;
//import com.seahorse.youliao.service.excel.*;
//import io.swagger.annotations.Api;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.lang.reflect.Field;
//import java.util.*;
//
///**
// * @description: 模板导出
// * @author: Mr.Song
// * @create: 2020-02-06 15:52
// **/
//@Api(value = "EasypoiTemplateExcelViewController", tags = "easypoi 模板导出")
//@Controller
//@RequestMapping("/easypoi/EasypoiTemplateExcelView")
//public class EasypoiTemplateExcelViewController {
//
//
//    @GetMapping()
//    public String download(ModelMap modelMap) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        TemplateExportParams params = new TemplateExportParams(
//                "doc/foreach.xlsx");
//        List<TemplateExcelExportEntity> list = new ArrayList<TemplateExcelExportEntity>();
//
//        for (int i = 0; i < 4; i++) {
//            TemplateExcelExportEntity entity = new TemplateExcelExportEntity();
//            entity.setIndex(i + 1 + "");
//            entity.setAccountType("开源项目");
//            entity.setProjectName("EasyPoi " + i + "期");
//            entity.setAmountApplied(i * 10000 + "");
//            entity.setApprovedAmount((i + 1) * 10000 - 100 + "");
//            list.add(entity);
//        }
//        map.put("entitylist", list);
//        map.put("manmark", "1");
//        map.put("letest", "12345678");
//        map.put("fntest", "12345678.2341234");
//        map.put("fdtest", null);
//        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < 1; i++) {
//            Map<String, Object> testMap = new HashMap<String, Object>();
//
//            testMap.put("id", "xman");
//            testMap.put("name", "小明" + i);
//            testMap.put("sex", "1");
//            mapList.add(testMap);
//        }
//        map.put("maplist", mapList);
//
//        mapList = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < 6; i++) {
//            Map<String, Object> testMap = new HashMap<String, Object>();
//
//            testMap.put("si", "xman");
//            mapList.add(testMap);
//        }
//        map.put("sitest", mapList);
//        modelMap.put(TemplateExcelConstants.FILE_NAME, "用户信息");
//        modelMap.put(TemplateExcelConstants.PARAMS, params);
//        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
//        return TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW;
//
//    }
//
//    /**如果上面的方法不行,可以使用下面的用法
//     * 同样的效果,只不过是直接问输出了,不经过view了
//     * @param modelMap
//     * @param request
//     * @param response
//     */
//
//    @RequestMapping("load")
//    public void downloadByPoiBaseView(ModelMap modelMap, HttpServletRequest request,
//                                      HttpServletResponse response) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        TemplateExportParams params = new TemplateExportParams(
//                "exceltohtml/foreach.xlsx",true);
//        List<TemplateExcelExportEntity> list = new ArrayList<TemplateExcelExportEntity>();
//
//        for (int i = 0; i < 4; i++) {
//            TemplateExcelExportEntity entity = new TemplateExcelExportEntity();
//            entity.setIndex(i + 1 + "");
//            entity.setAccountType("开源项目");
//            entity.setProjectName("EasyPoi " + i + "期");
//            entity.setAmountApplied(i * 10000 + "");
//            entity.setApprovedAmount((i + 1) * 10000 - 100 + "");
//            list.add(entity);
//        }
//        map.put("entitylist", list);
//        map.put("manmark", "1");
//        map.put("letest", "12345678");
//        map.put("fntest", "12345678.2341234");
//        map.put("fdtest", null);
//        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < 1; i++) {
//            Map<String, Object> testMap = new HashMap<String, Object>();
//
//            testMap.put("id", "xman");
//            testMap.put("name", "小明" + i);
//            testMap.put("sex", "1");
//            mapList.add(testMap);
//        }
//        map.put("maplist", mapList);
//
//        mapList = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < 6; i++) {
//            Map<String, Object> testMap = new HashMap<String, Object>();
//
//            testMap.put("si", "xman");
//            mapList.add(testMap);
//        }
//        map.put("sitest", mapList);
//        modelMap.put(TemplateExcelConstants.FILE_NAME, "用户信息");
//        modelMap.put(TemplateExcelConstants.PARAMS, params);
//        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
//        PoiBaseView.render(modelMap, request, response,
//                TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
//
//    }
//
//    public void testExportPdf2() {
//
//        List<CourseEntity> list = new ArrayList<CourseEntity>();
//        CourseEntity       courseEntity;
//        for (int i = 0; i < 50; i++) {
//            courseEntity = new CourseEntity();
//            courseEntity.setId("1131");
//            courseEntity.setName("海贼王必修(" + (i + 1) + ")");
//
//            TeacherEntity teacherEntity = new TeacherEntity();
//            teacherEntity.setId("12131231");
//            teacherEntity.setName("路飞");
////            courseEntity.setChineseTeacher(teacherEntity);
//
//            teacherEntity = new TeacherEntity();
//            teacherEntity.setId("121312314312421131");
//            teacherEntity.setName("老王");
//            courseEntity.setMathTeacher(teacherEntity);
//
//            StudentEntity studentEntity = new StudentEntity();
//            studentEntity.setId("11231");
//            studentEntity.setName("撒旦法司法局");
//            studentEntity.setBirthday(new Date());
//            studentEntity.setSex(1);
//            List<StudentEntity> studentList = new ArrayList<StudentEntity>();
//            studentList.add(studentEntity);
//            studentList.add(studentEntity);
//            courseEntity.setStudents(studentList);
//            list.add(courseEntity);
//        }
//
//        PdfExportParams params = new PdfExportParams("绝月的测试", "作者绝月");
//        Document document = null;
//        try {
//            File file = new File("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/youliao.testExportPdf2.pdf");
//            file.createNewFile();
//            document = PdfExportUtil.exportPdf(params, CourseEntity.class, list,
//                    new FileOutputStream(file));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            document.close();
//        }
//    }
//
//    public void testExportPdf() {
//
//        Field[] fields =  MsgClient.class.getFields();
//        for (int i = 0; i < fields.length; i++) {
//            Excel excel = fields[i].getAnnotation(Excel.class);
//            System.out.println(excel);
//        }
//
//        List<MsgClient> list = new ArrayList<MsgClient>();
//        for (int i = 0; i < 10; i++) {
//            MsgClient client = new MsgClient();
//            client.setBirthday(new Date());
//            client.setClientName("小明" + i);
//            client.setClientPhone("18797" + i);
//            client.setCreateBy("JueYue");
//            client.setId("1" + i);
//            client.setRemark("测试" + i);
//            MsgClientGroup group = new MsgClientGroup();
//            group.setGroupName("测试" + i);
//            client.setGroup(group);
//            list.add(client);
//        }
//        Date start = new Date();
//        PdfExportParams params = new PdfExportParams("2412312",null);
//        try {
//            File file = new File("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/youliao.testExportPdf.pdf");
//            file.createNewFile();
//            Document document =  PdfExportUtil.exportPdf(params, MsgClient.class, list,new FileOutputStream(file));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 简单导出没有图片和Excel
//     */
//    public void SimpleWordExport() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("department", "Jee\r\ncg");
//        map.put("person", "Jue\r\nYue");
//        map.put("auditPerson", "JueYue");
//        map.put("time", "2020-02-06 19:10:10");
//        map.put("date", new Date());
//        List<Person> list = new ArrayList<Person>();
//        Person p = new Person();
//        p.setName("小明");
//        p.setTel("18711111111");
//        p.setEmail("18711111111@\\r\\n139.com");
//        list.add(p);
//        p = new Person();
//        p.setName("小红");
//        p.setTel("18711111112");
//        p.setEmail("18711111112@\r\n139.com");
//        list.add(p);
//        map.put("pList", list);
//        try {
//            XWPFDocument doc = WordExportUtil.exportWord07(
//                    "exceltohtml/SimpleExcel.docx", map);
//            FileOutputStream fos = new FileOutputStream("E:/Projects/IdeaProject/youliao/youliao-web/src/main/java/com/seahorse/youliao.docx");
//            doc.write(fos);
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void main(String[] args) {
//        EasypoiTemplateExcelViewController c = new EasypoiTemplateExcelViewController();
////        c.testExportPdf();
////        c.testExportPdf2();
//        c.SimpleWordExport();
//    }
//
//}
