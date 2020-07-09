package com.seahorse.youliao.controller;

import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.SysDeptService;
import com.seahorse.youliao.service.entity.SysDeptDTO;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.util.TreeUtil;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.SysDeptUpdateVO;
import com.seahorse.youliao.vo.response.SysDeptResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
* describe: 部门表
* @author : songqiang
* @date: 2020-01-17 09:23:44.242
**/
@RestController
@Api(value = "SysDeptController",tags = "系统-部门管理")
@RequestMapping("/sys/dept")
@Validated
public class SysDeptController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(SysDeptController.class);
    
	@Autowired
    private SysDeptService sysDeptService;
    /**
     * 树形查询部门
     */
    @ApiOperation(value = "树形查询部门")
    @GetMapping("queryTreeList")
    public List<SysDeptResponseVO> selectPageList() {

        List<SysDeptDTO> sysDeptList = sysDeptService.getList(null);
        List<SysDeptResponseVO> voList = BeanUtil.convert(sysDeptList, SysDeptResponseVO.class);

        return  TreeUtil.buildDeptTree(voList);
    }

    /**
     * 关键字查询部门
     */
    @ApiOperation(value = "关键字查询部门")
    @GetMapping("searchByKeywords")
    public List<SysDeptResponseVO> searchByKeywords(@RequestParam(value = "keyWord")
                                                        @ApiParam(name = "keyWord",value = "keyWord") String keyWord ) {

        SysDeptDTO deptDTO = new SysDeptDTO();
        deptDTO.setDeptName(keyWord);
        List<SysDeptDTO> sysDeptList = sysDeptService.getList(deptDTO);
        List<SysDeptResponseVO> voList = BeanUtil.convert(sysDeptList, SysDeptResponseVO.class);

        return  TreeUtil.buildDeptTree(voList);
    }

    /**
     * 新增部门
     * @param deptUpdateVO
     */
    @Log(type = Log.OperationType.ADD,button = "新增部门",menu = "部门管理")
    @PreAuthorize("hasAuthority('dept:add')")
    @ApiOperation(value = "新增部门")
    @PostMapping("insert")
    public boolean insert(@RequestBody @Valid SysDeptUpdateVO deptUpdateVO){

        SysDeptDTO deptDTO = BeanUtil.convert(deptUpdateVO,SysDeptDTO.class);
        //判断是否为一级部门
        if(deptDTO.getPid() == null){
            LOGGER.info("一级部门添加 pid默认为0");
            deptDTO.setPid(0);
        }
        deptDTO.setEnabled(true);
        deptDTO.setCreateBy(SecurityUtils.getUsername());
        deptDTO.setCreateTime(new Date());
        return sysDeptService.insert(deptDTO);
    }

    /**
     * 编辑部门
     * @param deptUpdateVO
     */
    @Log(type = Log.OperationType.UPDATE,button = "编辑部门",menu = "部门管理")
    @PreAuthorize("hasAuthority('dept:update')")
    @ApiOperation(value = "编辑部门")
    @PutMapping("update")
    public boolean update(@RequestBody @Valid SysDeptUpdateVO deptUpdateVO){

        if(deptUpdateVO.getId() == null){
            throw new BusinessException("部门id不能为空");
        }
        return sysDeptService.update(BeanUtil.convert(deptUpdateVO,SysDeptDTO.class));
    }

    /**
     * 删除部门
     * @param id
     */
    @Log(type = Log.OperationType.DELETE,button = "删除部门",menu = "部门管理")
    @PreAuthorize("hasAuthority('dept:delete')")
    @ApiOperation(value = "删除部门")
    @DeleteMapping("delete")
    public boolean delete(@ApiParam(name = "id",value = "id",required = true)
                              @RequestParam Integer id){

        SysDeptDTO deptDTO = new SysDeptDTO();
        deptDTO.setId(id);
        return sysDeptService.delete(deptDTO);
    }

    /**
     * 批量删除部门
     * @param ids
     */
    @Log(type = Log.OperationType.DELETE,button = "批量删除部门",menu = "部门管理")
    @PreAuthorize("hasAuthority('dept:batch:delete')")
    @ApiOperation(value = "批量删除部门")
    @DeleteMapping("deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){

        return sysDeptService.deleteByIds(ids);
    }

}
