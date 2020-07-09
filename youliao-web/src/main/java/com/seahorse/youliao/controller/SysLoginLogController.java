package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.service.SysLoginLogService;
import com.seahorse.youliao.service.entity.SysLoginLogDTO;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.SysLoginLogQueryVO;
import com.seahorse.youliao.vo.response.SysLoginLogPageInfoVO;
import com.seahorse.youliao.vo.response.SysLoginLogResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
* describe: 登录日志
* @author : songqiang
* @date: 2020-01-17 09:23:45.040
**/
@RestController
@Api(value = "SysLoginLogController",tags = "系统-登录日志")
@RequestMapping("/login/log")
@Validated
public class SysLoginLogController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(SysLoginLogController.class);
    
	@Autowired
    private SysLoginLogService sysLoginLogService;
    /**
     * 分页查询
     * @param sysLoginLogQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("getPageList")
    public SysLoginLogPageInfoVO selectPageList(@RequestBody @Valid SysLoginLogQueryVO sysLoginLogQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", sysLoginLogQueryVO.toString());
        LOGGER.info(info);
        SysLoginLogDTO sysLoginLog = BeanUtil.convert(sysLoginLogQueryVO, SysLoginLogDTO.class);

        Page<SysLoginLogResponseVO> page = PageHelper.startPage(sysLoginLogQueryVO.getPageNum(), sysLoginLogQueryVO.getPageSize());
        List<SysLoginLogDTO> sysLoginLogList = sysLoginLogService.getList(sysLoginLog);
        SysLoginLogPageInfoVO sysLoginLogPageInfo = new SysLoginLogPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), sysLoginLogPageInfo);
        List<SysLoginLogResponseVO> voList = BeanUtil.convert(sysLoginLogList, SysLoginLogResponseVO.class);
        sysLoginLogPageInfo.setList(voList);

        return sysLoginLogPageInfo;
    }


}
