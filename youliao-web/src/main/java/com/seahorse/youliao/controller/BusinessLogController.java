package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.service.BusinessLogService;
import com.seahorse.youliao.service.entity.BusinessLogDTO;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.BusinessLogQueryVO;
import com.seahorse.youliao.vo.response.BusinessLogPageInfoVO;
import com.seahorse.youliao.vo.response.BusinessLogResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: 业务日志
 * @author: Mr.Song
 * @create: 2020-01-09 22:15
 **/
@Api(value = "BaseDictionaryController",tags="系统-业务日志")
@RestController
@RequestMapping("/sys/log")
@Validated
public class BusinessLogController {

    private static Logger logger = LoggerFactory.getLogger(BusinessLogController.class);

    @Autowired
    private BusinessLogService businessLogService;

    /**
     * 日志
     * @return
     */
    @ApiOperation(value = "日志页面实时查看")
    @GetMapping("/view")
    public ModelAndView logView() {

        logger.info("日志页面实时查看");
        ModelAndView modelAndView = new ModelAndView("/websocket/log");
        return modelAndView;
    }


    /**
     * 分页查询
     * @param businessLogQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("getPageList")
    public BusinessLogPageInfoVO selectPageList(@RequestBody @Valid BusinessLogQueryVO businessLogQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", businessLogQueryVO.toString());
        logger.info(info);
        BusinessLogDTO businessLog = BeanUtil.convert(businessLogQueryVO, BusinessLogDTO.class);

        Page<BusinessLogResponseVO> page = PageHelper.startPage(businessLogQueryVO.getPageNum(), businessLogQueryVO.getPageSize());
        List<BusinessLogDTO> businessLogList = businessLogService.getList(businessLog);
        BusinessLogPageInfoVO businessLogPageInfo = new BusinessLogPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), businessLogPageInfo);
        List<BusinessLogResponseVO> voList = BeanUtil.convert(businessLogList, BusinessLogResponseVO.class);
        businessLogPageInfo.setList(voList);

        return businessLogPageInfo;
    }
}
