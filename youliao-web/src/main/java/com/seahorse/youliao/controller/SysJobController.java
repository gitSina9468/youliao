package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.SysJobService;
import com.seahorse.youliao.service.entity.SysJobDTO;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.SysJobQueryVO;
import com.seahorse.youliao.vo.request.SysJobUpdateVO;
import com.seahorse.youliao.vo.response.SysJobPageInfoVO;
import com.seahorse.youliao.vo.response.SysJobResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
* describe: 岗位信息表
* @author : songqiang
* @date: 2020-01-17 09:23:44.813
**/
@RestController
@Api(value = "SysJobController",tags = "系统-岗位管理")
@RequestMapping("/sys/job")
@Validated
public class SysJobController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(SysJobController.class);
    
	@Autowired
    private SysJobService sysJobService;
    /**
     * 分页查询
     * @param sysJobQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("getPageList")
    public SysJobPageInfoVO selectPageList(@RequestBody @Valid SysJobQueryVO sysJobQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", sysJobQueryVO.toString());
        LOGGER.info(info);
        SysJobDTO sysJob = BeanUtil.convert(sysJobQueryVO, SysJobDTO.class);

        Page<SysJobResponseVO> page = PageHelper.startPage(sysJobQueryVO.getPageNum(), sysJobQueryVO.getPageSize());
        List<SysJobDTO> sysJobList = sysJobService.getList(sysJob);
        SysJobPageInfoVO sysJobPageInfo = new SysJobPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), sysJobPageInfo);
        List<SysJobResponseVO> voList = BeanUtil.convert(sysJobList, SysJobResponseVO.class);
        sysJobPageInfo.setList(voList);

        return sysJobPageInfo;
    }

    /**
     * 查询所有岗位集合
     * 新增用户时选择岗位
     * @return
     */
    @ApiOperation(value = "查询所有岗位集合")
    @GetMapping("getAllList")
    public List<SysJobResponseVO> selectPageList() {

        List<SysJobDTO> sysJobList = sysJobService.getList(null);
        List<SysJobResponseVO> voList = BeanUtil.convert(sysJobList, SysJobResponseVO.class);

        return voList;
    }

    /**
     * 新增岗位
     * @param jobUpdateVO
     */
    @Log(type = Log.OperationType.ADD,button = "新增岗位",menu = "岗位管理")
    @PreAuthorize("hasAuthority('job:add')")
    @ApiOperation(value = "新增岗位")
    @PostMapping("insert")
    public boolean insert(@RequestBody @Valid SysJobUpdateVO jobUpdateVO){

        SysJobDTO jobDTO = BeanUtil.convert(jobUpdateVO,SysJobDTO.class);
        jobDTO.setEnabled(true);
        jobDTO.setCreateBy(SecurityUtils.getUsername());
        jobDTO.setCreateTime(new Date());

        return sysJobService.insert(jobDTO);
    }

    /**
     * 编辑岗位
     * @param jobUpdateVO
     */
    @Log(type = Log.OperationType.UPDATE,button = "编辑岗位",menu = "岗位管理")
    @PreAuthorize("hasAuthority('job:update')")
    @ApiOperation(value = "编辑岗位")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysJobUpdateVO jobUpdateVO){

        if(jobUpdateVO.getId() == null){
            throw new BusinessException("岗位id不能为空");
        }
        return sysJobService.update(BeanUtil.convert(jobUpdateVO,SysJobDTO.class));
    }

    /**
     * 删除岗位
     * @param id
     */
    @Log(type = Log.OperationType.DELETE,button = "删除岗位",menu = "岗位管理")
    @PreAuthorize("hasAuthority('job:delete')")
    @ApiOperation(value = "删除岗位")
    @DeleteMapping("delete")
    public boolean delete(@ApiParam(name = "id",value = "id",required = true)
                              @RequestParam Integer id){

        SysJobDTO jobDTO = new SysJobDTO();
        jobDTO.setId(id);
        return sysJobService.delete(jobDTO);
    }

    /**
     * 批量删除岗位
     * @param ids
     */
    @Log(type = Log.OperationType.DELETE,button = "批量删除岗位",menu = "岗位管理")
    @PreAuthorize("hasAuthority('job:batch;delete')")
    @ApiOperation(value = "批量删除岗位")
    @DeleteMapping("deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){

        return sysJobService.deleteByIds(ids);
    }


}
