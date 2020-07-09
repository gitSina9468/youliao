package com.seahorse.youliao.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.controller
 * @ClassName: ScreenController
 * @Description: 大屏数据实时推送
 * @author:songqiang
 * @Date:2020-01-10 17:25
 **/
@Api(value = "ScreenController", tags = "公共 司机办单排号大厅")
@Controller
@RequestMapping("/screen")
public class ScreenController {



    @GetMapping("/LED")
    public ModelAndView LED(ModelAndView model){

        model.setViewName("/screen/monitor");
        return model;
    }
}
