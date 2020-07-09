package com.seahorse.youliao.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.controller
 * @ClassName: BarQrCodeController
 * @Description: 参考网址:
 *     https://blog.csdn.net/arrowzz/article/details/80656510
 *     https://blog.csdn.net/weixin_40375156/article/details/103153291
 *     这里是作者的Github，有更详细的内容可以学习：
 *      * https://github.com/lindell/JsBarcode
 *      * 这里是作者的Github，有更详细的内容可以学习：
 *      * https://github.com/davidshimjs/qrcodejs
 * @author:songqiang
 * @Date:2019-12-27 10:46
 **/
@Controller
@Api(value = "BarQrCodeController", tags = "各类pdf小票 模板")
@RequestMapping("/qrCode")
public class BarQrCodeController {


    /**
     * js 展示 条形码 二维码
     * @return barCode视图
     */
    @ApiOperation(value = "js 展示 条形码 二维码")
    @GetMapping("/barCode")
    public ModelAndView barCode() {

        ModelAndView modelAndView = new ModelAndView("/bill/barCode");
        return modelAndView;
    }

    /**
     * xdoc 生成图片数据
     * @param request
     * @return
     */
    @ApiOperation(value = " xdoc 生成图片数据")
    @GetMapping("/img")
    public ModelAndView xdoc(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("/bill/img");
        return modelAndView;
    }

    /**
     * xdoc 销售小票
     * @return  receipt视图
     */
    @ApiOperation(value = "销售小票")
    @GetMapping("/receipt")
    public ModelAndView receipt() {

        ModelAndView modelAndView = new ModelAndView("/bill/receipt");
        return modelAndView;
    }

    /**
     * xdoc 销售凭证
     * @return  bill视图
     */
    @ApiOperation(value = "销售凭证")
    @GetMapping("/bill")
    public ModelAndView bill() {

        ModelAndView modelAndView = new ModelAndView("/bill/bill");
        return modelAndView;
    }

    /**
     * xdoc 销售凭证2
     * @return  bill视图
     */
    @ApiOperation(value = "销售凭证2")
    @GetMapping("/bill2")
    public ModelAndView bill2() {

        ModelAndView modelAndView = new ModelAndView("/bill/bill2");
        return modelAndView;
    }

    /**
     * xdoc 采血回执单
     * @return  bill视图
     */
    @ApiOperation(value = "采血回执单")
    @GetMapping("/receipt2")
    public ModelAndView receipt2() {

        ModelAndView modelAndView = new ModelAndView("/bill/receipt2");
        return modelAndView;
    }

}
