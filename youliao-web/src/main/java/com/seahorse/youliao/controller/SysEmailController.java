package com.seahorse.youliao.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.SysEmailService;
import com.seahorse.youliao.service.entity.SysEmailDTO;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.SysEmailQueryVO;
import com.seahorse.youliao.vo.request.SysEmailUpdateVO;
import com.seahorse.youliao.vo.response.SysEmailPageInfoVO;
import com.seahorse.youliao.vo.response.SysEmailResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
* 邮件发送
* @author  sq
* @date    2020-05-09 03:24:02.125
**/
@RestController
@Api(tags = "邮件管理")
@RequestMapping("/sys/mail")
public class SysEmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysEmailController.class);

	@Autowired
    private SysEmailService sysEmailService;
    /**
     * 分页查询
     * @param query 分页查询参数
     * @return 分页参数
     */
    @ApiOperation("分页查询")
    @PostMapping("getPageList")
    public SysEmailPageInfoVO selectPageList(@RequestBody @Valid SysEmailQueryVO query) {
        LOGGER.debug("查询参数：{}", query);
        SysEmailDTO condition = BeanUtil.convert(query, SysEmailDTO.class);

        Page<SysEmailResponseVO> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<SysEmailDTO> list = sysEmailService.getList(condition);
        SysEmailPageInfoVO pageVo = new SysEmailPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), pageVo);
        List<SysEmailResponseVO> voList = BeanUtil.convert(list, SysEmailResponseVO.class);
        pageVo.setList(voList);

        return pageVo;
    }


    /**
     * 新增邮件
     * @param emailUpdateVO
     */
    @Log(type = Log.OperationType.ADD,button = "新增邮件",menu = "邮件管理")
    @PreAuthorize("hasAuthority('mail:add')")
    @ApiOperation(value = "新增邮件")
    @PostMapping("insert")
    public boolean insert(@RequestBody @Valid SysEmailUpdateVO emailUpdateVO){

        SysEmailDTO emailDTO = BeanUtil.convert(emailUpdateVO,SysEmailDTO.class);
        emailDTO.setCreateBy(SecurityUtils.getUsername());
        emailDTO.setCreateTime(new Date());

        if(!CollectionUtils.isEmpty(emailUpdateVO.getList())){
            //处理附件
            emailDTO.setAttachFiles(JSON.toJSONString(emailUpdateVO.getList()));
        }
        return sysEmailService.insert(emailDTO);
    }

    /**
     * 发送邮件
     * @param id
     */
    @Log(type = Log.OperationType.ADD,button = "发送邮件",menu = "邮件管理")
    @PreAuthorize("hasAuthority('mail:send')")
    @ApiOperation(value = "发送邮件")
    @GetMapping("send")
    public boolean send(@ApiParam(name = "id",value = "id",required = true)
                            @RequestParam Integer id){

        return sysEmailService.send(id);
    }

    /**
     * 编辑邮件
     * @param emailUpdateVO
     */
    @Log(type = Log.OperationType.UPDATE,button = "编辑邮件",menu = "邮件管理")
    @PreAuthorize("hasAuthority('mail:update')")
    @ApiOperation(value = "编辑邮件")
    @PutMapping("update")
    public boolean update(@RequestBody @Valid SysEmailUpdateVO emailUpdateVO){

        if(emailUpdateVO.getId() == null){
            throw new BusinessException("邮件id不能为空");
        }
        SysEmailDTO emailDTO = BeanUtil.convert(emailUpdateVO,SysEmailDTO.class);
        if(!CollectionUtils.isEmpty(emailUpdateVO.getList())){
            //处理附件
            emailDTO.setAttachFiles(JSON.toJSONString(emailUpdateVO.getList()));
        }
        return sysEmailService.update(BeanUtil.convert(emailUpdateVO,SysEmailDTO.class));
    }

    /**
     * 删除邮件
     * @param id
     */
    @Log(type = Log.OperationType.DELETE,button = "删除邮件",menu = "邮件管理")
    @PreAuthorize("hasAuthority('mail:delete')")
    @ApiOperation(value = "删除邮件")
    @DeleteMapping("delete")
    public boolean delete(@ApiParam(name = "id",value = "id",required = true)
                          @RequestParam Integer id){

        SysEmailDTO emailDTO = new SysEmailDTO();
        emailDTO.setId(id);
        return sysEmailService.delete(emailDTO);
    }

}
