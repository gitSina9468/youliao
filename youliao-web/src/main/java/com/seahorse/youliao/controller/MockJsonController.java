package com.seahorse.youliao.controller;

import com.seahorse.youliao.common.ResponseHandler;
import com.seahorse.youliao.config.SqlJob;
import com.seahorse.youliao.util.JsonResourceUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * @description: 读取json数据控制器
 * @author: Mr.Song
 * @create: 2020-01-27 19:38
 **/
@Api(value = "MockJsonController", tags = "mock json数据")
@RequestMapping("/mock")
@RestController
public class MockJsonController {

    @ApiOperation(value = "cpu数据")
    @GetMapping(value = {"/cpu-data.json"})
    public Object cpuData() {
        return JsonResourceUtils.getJsonArrayFromResource("/static/json/cpu-data.json");
    }

    @ApiOperation(value = "词云数据")
    @GetMapping(value = {"/world-pop.json"})
    public Object wordPop() {
        return JsonResourceUtils.getJsonArrayFromResource("/static/json/world-population.json");
    }

    @ApiOperation(value = "系统树形 数据")
    @GetMapping(value = {"/system.json"})
    @ResponseHandler(handler = false)
    public Object system() {
        return JsonResourceUtils.getJsonObjFromResource("/static/json/system.json");
    }

    @GetMapping(value = {"/list/search/projects"})
    @ResponseHandler(handler = false)
    public Object projects() {
        return JsonResourceUtils.getJsonObjFromResource("/static/json/projects.json");
    }
    @GetMapping(value = {"/workplace/activity"})
    @ResponseHandler(handler = false)
    public Object activity() {
        return JsonResourceUtils.getJsonObjFromResource("/static/json/activity.json");
    }
    @GetMapping(value = {"/workplace/teams"})
    @ResponseHandler(handler = false)
    public Object teams() {
        return JsonResourceUtils.getJsonObjFromResource("/static/json/teams.json");
    }
    @GetMapping(value = {"/workplace/radar"})
    @ResponseHandler(handler = false)
    public Object radar() {
        return JsonResourceUtils.getJsonObjFromResource("/static/json/radar.json");
    }
    @GetMapping(value = {"/service"})
    @ResponseHandler(handler = false)
    public Object service() {
        return JsonResourceUtils.getJsonObjFromResource("/static/json/service.json");
    }
    @GetMapping(value = {"/article"})
    @ResponseHandler(handler = false)
    public Object article() {
        return JsonResourceUtils.getJsonObjFromResource("/static/json/article.json");
    }
    @GetMapping(value = {"/sqlExecute"})
    public Boolean sqlExecute() {
        try {
            SqlJob.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
