//package com.seahorse.youliao.controller.easypoi;
//
//import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
//import cn.afterturn.easypoi.excel.entity.ExportParams;
//import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
//import cn.afterturn.easypoi.view.PoiBaseView;
//import com.seahorse.youliao.service.excel.MsgClient;
//import com.seahorse.youliao.service.excel.MsgClientGroup;
//import io.swagger.annotations.Api;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * @description: 单sheet 查看
// * @author: Mr.Song
// * @create: 2020-02-06 14:56
// **/
//@Api(value = "EasypoiSingleExcelViewController", tags = "easypoi 单sheet 查看")
//@Controller
//@RequestMapping("/easypoi/EasypoiSingleExcelView")
//public class EasypoiSingleExcelViewController {
//
//
//    @GetMapping()
//    public String download(ModelMap map) {
//        List<MsgClient> list = new ArrayList<MsgClient>();
//        for (int i = 0; i < 100; i++) {
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
//        ExportParams params = new ExportParams("2412312", "测试", ExcelType.XSSF);
//        params.setFreezeCol(2);
//        map.put(NormalExcelConstants.DATA_LIST, list);
//        map.put(NormalExcelConstants.CLASS, MsgClient.class);
//        map.put(NormalExcelConstants.PARAMS, params);
//        return NormalExcelConstants.EASYPOI_EXCEL_VIEW;
//
//    }
//
//    /**如果上面的方法不行,可以使用下面的用法
//     * 同样的效果,只不过是直接问输出了,不经过view了
//     * @param map
//     * @param request
//     * @param response
//     */
//
//    @GetMapping("load")
//    public void downloadByPoiBaseView(ModelMap map, HttpServletRequest request,
//                                      HttpServletResponse response) {
//        List<MsgClient> list = new ArrayList<MsgClient>();
//        for (int i = 0; i < 100; i++) {
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
//        ExportParams params = new ExportParams("2412312", "测试", ExcelType.XSSF);
//        params.setFreezeCol(2);
//        map.put(NormalExcelConstants.DATA_LIST, list);
//        map.put(NormalExcelConstants.CLASS, MsgClient.class);
//        map.put(NormalExcelConstants.PARAMS, params);
//        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
//
//    }
//}
