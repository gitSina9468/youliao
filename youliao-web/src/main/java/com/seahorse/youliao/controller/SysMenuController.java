package com.seahorse.youliao.controller;

import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.SysMenuService;
import com.seahorse.youliao.service.SysRoleMenuService;
import com.seahorse.youliao.service.entity.SysMenuDTO;
import com.seahorse.youliao.service.entity.SysRoleMenuDTO;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.util.TreeUtil;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.SysMenuUpdateVO;
import com.seahorse.youliao.vo.response.SysMenuResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* describe: 菜单权限表
* @author : songqiang
* @date: 2020-01-17 09:23:45.259
**/
@RestController
@Api(value = "SysMenuController",tags = "系统-菜单管理")
@RequestMapping("/sys/menu")
@Validated
public class SysMenuController {
	
    private static final Logger logger = LoggerFactory.getLogger(SysMenuController.class);
    
	@Autowired
    private SysMenuService sysMenuService;

	@Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 菜单树形展示
     * @return
     */
    @Cacheable(value="menuCache")
    @ApiOperation(value = "菜单树形展示")
    @GetMapping("list")
    public List<SysMenuResponseVO> selectPageList() {

        List<SysMenuDTO> sysMenuList = sysMenuService.getList(null);
        List<SysMenuResponseVO> voList = BeanUtil.convert(sysMenuList, SysMenuResponseVO.class);
        //处理成树形结构菜单
        return TreeUtil.buildMenuTree(voList);
    }

    /**
     * 新增菜单
     * @param menuUpdateVO
     */
    @CacheEvict(value="menuCache",allEntries=true)
    @Log(type = Log.OperationType.ADD,button = "新增菜单",menu = "菜单管理")
    @PreAuthorize("hasAuthority('menu:add')")
    @ApiOperation(value = "新增菜单")
    @PostMapping("insert")
    public boolean insert(@RequestBody @Valid SysMenuUpdateVO menuUpdateVO){

        SysMenuDTO menuDTO = BeanUtil.convert(menuUpdateVO,SysMenuDTO.class);
        menuDTO.setCreateBy(SecurityUtils.getUsername());
        menuDTO.setCreateTime(new Date());
        return sysMenuService.insertMenu(menuDTO);
    }

    /**
     * 编辑菜单
     * @param menuUpdateVO
     */
    @CacheEvict(value="menuCache",allEntries=true)
    @Log(type = Log.OperationType.UPDATE,button = "编辑菜单",menu = "菜单管理")
    @PreAuthorize("hasAuthority('menu:update')")
    @ApiOperation(value = "编辑菜单")
    @PutMapping("update")
    public boolean update(@RequestBody @Valid SysMenuUpdateVO menuUpdateVO){

        if(menuUpdateVO.getId() == null){
            throw new BusinessException("菜单id不能为空");
        }
        return sysMenuService.updateMenu(BeanUtil.convert(menuUpdateVO,SysMenuDTO.class));
    }

    /**
     * 删除菜单
     * 默认只删除节点菜单；
     * 如果为父节点存在子节点 则不删除
     * @param id
     */
    @CacheEvict(value="menuCache",allEntries=true)
    @Log(type = Log.OperationType.DELETE,button = "删除菜单",menu = "菜单管理")
    @PreAuthorize("hasAuthority('menu:delete')")
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("delete")
    public boolean delete(@ApiParam(name = "id",value = "id",required = true)
                              @RequestParam Integer id){

      return sysMenuService.deleteById(id);
    }

    /**
     * 批量删除菜单
     * 默认只删除节点菜单；
     * 如果为父节点存在子节点 则不删除
     * @param ids
     */
    @CacheEvict(value="menuCache",allEntries=true)
    @Log(type = Log.OperationType.DELETE,button = "批量删除菜单",menu = "菜单管理")
    @PreAuthorize("hasAuthority('menu:batch:delete')")
    @ApiOperation(value = "批量删除菜单")
    @DeleteMapping("deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){

      return sysMenuService.deleteBatch(ids);
    }


    /**
     * 查询角色授权
     * @return
     */
    @ApiOperation(value = "查询角色授权")
    @GetMapping(value = "/queryRolePermission")
    public List<String> queryRolePermission(@RequestParam(name = "roleId") Integer roleId) {

        SysRoleMenuDTO roleMenuDTO = new SysRoleMenuDTO();
        roleMenuDTO.setRoleId(roleId);
        List<SysRoleMenuDTO> list = sysRoleMenuService.getList(roleMenuDTO);
        List<String> idList = new ArrayList<>();
        for (SysRoleMenuDTO sysRoleMenuDTO : list) {
            idList.add(sysRoleMenuDTO.getMenuId().toString());
        }
        return idList;
    }

}
