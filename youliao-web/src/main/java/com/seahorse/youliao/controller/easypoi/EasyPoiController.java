//package com.seahorse.youliao.controller.easypoi;
//
//import cn.afterturn.easypoi.excel.entity.ExportParams;
//import com.seahorse.youliao.service.excel.CourseEntity;
//import com.seahorse.youliao.service.excel.Person;
//import com.seahorse.youliao.service.excel.StudentEntity;
//import com.seahorse.youliao.service.excel.TeacherEntity;
//import com.seahorse.youliao.utils.ExcelUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.*;
//
///**
// * @description: easypoi 控制器
// * @author: Mr.Song
// * @create: 2020-02-06 12:11
// **/
//@Api(value = "EasyPoiController", tags = "easypoi导出案例")
//@RestController
//@RequestMapping("/easypoi")
//public class EasyPoiController {
//
//
//    private static final Logger logger = LoggerFactory.getLogger(EasyPoiController.class);
//
//
//    /**
//     * 导出excel
//     * @param response
//     * @throws Exception
//     */
//    @ApiOperation(value = "excel导出")
//    @GetMapping("/export")
//    public void export2(HttpServletResponse response) throws Exception{
//        //模拟从数据库获取需要导出的数据
//        List<Person> personList = new ArrayList<>();
//        Person person1 = new Person("路飞", "1", new Date(), "篮球", "地址", "188888888888");
//        Person person2 = new Person("娜美", "2", new Date(), "篮球", "地址", "188888888888");
//        Person person3 = new Person("索隆", "1", new Date(), "篮球", "地址", "188888888888");
//        Person person4 = new Person("小狸猫", "1", new Date(), "篮球", "地址", "188888888888");
//        personList.add(person1);
//        personList.add(person2);
//        personList.add(person3);
//        personList.add(person4);
//        //导出操作
//        ExcelUtils.exportExcel(personList, "花名册", "草帽一伙", Person.class, "海贼王.xls", response);
//    }
//
//
//    /**
//     * excel组合表头导出
//     * @param response
//     * @throws Exception
//     */
//    @ApiOperation(value = "excel组合表头导出")
//    @GetMapping("exportOneToMany")
//    public void export3(HttpServletResponse response) throws Exception{
//        //模拟从数据库获取需要导出的数据
//
//        CourseEntity courseEntity = new CourseEntity();
//        courseEntity.setName("物理");
//        TeacherEntity teacherEntity = new TeacherEntity();
//        teacherEntity.setName("老王");
//        courseEntity.setMathTeacher(teacherEntity);
//        List<StudentEntity> studentList = new ArrayList<>();
//        StudentEntity entity1 = new StudentEntity("张三", 1, new Date(), new Date());
//        StudentEntity entity2 = new StudentEntity("小花",2,new Date(),new Date());
//        StudentEntity entity3 = new StudentEntity("李四",1,new Date(),new Date());
//        studentList.add(entity1);
//        studentList.add(entity2);
//        studentList.add(entity3);
//        courseEntity.setStudents(studentList);
//
//
//        CourseEntity courseEntity2 = new CourseEntity();
//        courseEntity2.setName("化学");
//        TeacherEntity teacherEntity2 = new TeacherEntity();
//        teacherEntity2.setName("小李");
//        courseEntity2.setMathTeacher(teacherEntity2);
//        List<StudentEntity> studentList2 = new ArrayList<>();
//        studentList2.add(entity1);
//        studentList2.add(entity2);
//        studentList2.add(entity3);
//        courseEntity2.setStudents(studentList2);
//
//        List<CourseEntity> courseEntityList = new ArrayList<>();
//        courseEntityList.add(courseEntity);
//        courseEntityList.add(courseEntity2);
//
//        //导出操作
//        ExcelUtils.exportExcel(courseEntityList, "课程安排", "课程表", CourseEntity.class, "课程表.xls", response);
//    }
//
//
//    /**
//     * 多sheet导出
//     * @param response
//     */
//    @ApiOperation(value = "excel多sheet导出")
//    @GetMapping("/multiSheet")
//    public void exportMultiSheet(HttpServletResponse response) {
//
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> map1 = new HashMap<>();
//        Map<String, Object> map2 = new HashMap<>();
//
//
//        ExportParams exportParams1 = new ExportParams();
//        exportParams1.setSheetName("sheet1");
//        ExportParams exportParams2 = new ExportParams();
//        exportParams2.setSheetName("sheet2");
//
//        map1.put("title",exportParams1);
//        map1.put("entity",Person.class);
//
//        map2.put("title",exportParams2);
//        map2.put("entity",Person.class);
//
//        //模拟从数据库获取需要导出的数据
//        List<Person> personList = new ArrayList<>();
//        Person person1 = new Person("路飞1", "1", new Date(), "篮球", "地址", "188888888888");
//        Person person2 = new Person("娜美1", "2", new Date(), "篮球", "地址", "188888888888");
//        Person person3 = new Person("索隆1", "1", new Date(), "篮球", "地址", "188888888888");
//        Person person4 = new Person("小狸猫1", "1", new Date(), "篮球", "地址", "188888888888");
//        personList.add(person1);
//        personList.add(person2);
//        personList.add(person3);
//        personList.add(person4);
//
//        //模拟从数据库获取需要导出的数据
//        List<Person> personList2 = new ArrayList<>();
//        Person person11 = new Person("路飞2", "1", new Date(), "足球", "地址", "188888888888");
//        Person person21 = new Person("娜美2", "2", new Date(), "足球", "地址", "188888888888");
//        Person person31 = new Person("索隆2", "1", new Date(), "足球", "地址", "188888888888");
//        Person person41 = new Person("小狸猫2", "1", new Date(), "足球", "地址", "188888888888");
//        personList2.add(person11);
//        personList2.add(person21);
//        personList2.add(person31);
//        personList2.add(person41);
//
//        map1.put("data",personList);
//        map2.put("data",personList2);
//
//        list.add(map1);
//        list.add(map2);
//        //导出操作
//        ExcelUtils.exportExcel(list,"多sheet.xls",response);
//
//
//    }
//}
