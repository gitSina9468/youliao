package com.seahorse.youliao.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.SysMenuService;
import com.seahorse.youliao.service.SysRoleMenuService;
import com.seahorse.youliao.service.SysRoleService;
import com.seahorse.youliao.service.entity.SysMenuDTO;
import com.seahorse.youliao.service.entity.SysRoleDTO;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.SysRoleQueryVO;
import com.seahorse.youliao.vo.request.SysRoleUpdateVO;
import com.seahorse.youliao.vo.response.SysRolePageInfoVO;
import com.seahorse.youliao.vo.response.SysRoleResponseVO;
import com.seahorse.youliao.vo.response.base.TreeData;
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
import java.util.*;
import java.util.stream.Collectors;

/**
* describe: 角色信息表
* @author : songqiang
* @date: 2020-01-17 09:23:45.558
**/
@RestController
@Api(value = "SysRoleController",tags = "系统-角色管理")
@RequestMapping("/sys/role")
@Validated
public class SysRoleController {
	
    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);
    
	@Autowired
    private SysRoleService sysRoleService;

	@Autowired
    private SysMenuService sysMenuService;

	@Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 分页查询
     * @param sysRoleQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("getPageList")
    public SysRolePageInfoVO selectPageList(@RequestBody @Valid SysRoleQueryVO sysRoleQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", sysRoleQueryVO.toString());
        logger.info(info);
        SysRoleDTO sysRole = BeanUtil.convert(sysRoleQueryVO, SysRoleDTO.class);

        Page<SysRoleResponseVO> page = PageHelper.startPage(sysRoleQueryVO.getPageNum(), sysRoleQueryVO.getPageSize());
        List<SysRoleDTO> sysRoleList = sysRoleService.getList(sysRole);
        SysRolePageInfoVO sysRolePageInfo = new SysRolePageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), sysRolePageInfo);
        List<SysRoleResponseVO> voList = BeanUtil.convert(sysRoleList, SysRoleResponseVO.class);
        sysRolePageInfo.setList(voList);

        return sysRolePageInfo;
    }

    /**
     * 查询所有角色集合
     * @return
     */
    @ApiOperation(value = "查询所有角色集合")
    @GetMapping("getAllList")
    public List<SysRoleResponseVO> selectPageList() {

        List<SysRoleDTO> sysRoleList = sysRoleService.getList(null);
        List<SysRoleResponseVO> voList = BeanUtil.convert(sysRoleList, SysRoleResponseVO.class);

        return voList;
    }


    /**
     * 新增角色
     * @param roleUpdateVO
     */
    @Log(type = Log.OperationType.ADD,button = "新增角色",menu = "角色管理")
    @PreAuthorize("hasAuthority('role:add')")
    @ApiOperation(value = "新增角色")
    @PostMapping("insert")
    public boolean insert(@RequestBody @Valid SysRoleUpdateVO roleUpdateVO){

        SysRoleDTO roleDTO = BeanUtil.convert(roleUpdateVO,SysRoleDTO.class);
        roleDTO.setCreateBy(SecurityUtils.getUsername());
        roleDTO.setCreateTime(new Date());

        return sysRoleService.insert(roleDTO);
    }

    /**
     * 编辑角色
     * @param roleUpdateVO
     */
    @Log(type = Log.OperationType.UPDATE,button = "编辑角色",menu = "角色管理")
    @PreAuthorize("hasAuthority('role:update')")
    @ApiOperation(value = "编辑角色")
    @PutMapping("update")
    public boolean update(@RequestBody @Valid SysRoleUpdateVO roleUpdateVO){

        if(roleUpdateVO.getId() == null){
            throw new BusinessException("角色id不能为空");
        }
        return sysRoleService.update(BeanUtil.convert(roleUpdateVO,SysRoleDTO.class));
    }

    /**
     * 删除角色
     * @param id
     */
    @Log(type = Log.OperationType.DELETE,button = "删除角色",menu = "角色管理")
    @PreAuthorize("hasAuthority('role:delete')")
    @ApiOperation(value = "删除角色")
    @DeleteMapping("delete")
    public boolean delete(@ApiParam(name = "id",value = "id",required = true)
                              @RequestParam Integer id){

        SysRoleDTO roleDTO = new SysRoleDTO();
        roleDTO.setId(id);
        return sysRoleService.delete(roleDTO);
    }

    /**
     * 批量删除角色
     * @param ids
     */
    @Log(type = Log.OperationType.DELETE,button = "批量删除角色",menu = "角色管理")
    @PreAuthorize("hasAuthority('role:batch;delete')")
    @ApiOperation(value = "批量删除角色")
    @DeleteMapping("deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){

        return sysRoleService.deleteByIds(ids);
    }

    /**
     * 用户角色授权功能，查询菜单权限树
     * @return
     */
    @ApiOperation(value = "用户角色授权功能，查询菜单权限树")
    @GetMapping(value = "/queryTreeList")
    public Map<String,Object> queryTreeList() {

        Map<String,Object> resMap = new HashMap<>(2);
        //全部权限ids
        List<String> ids = new ArrayList<>();
        List<TreeData> treeList = new ArrayList<>();
        List<SysMenuDTO> list = sysMenuService.getList(null);
        for(SysMenuDTO dto : list) {
            ids.add(dto.getId().toString());
            treeList.add(new TreeData(dto.getId().toString(),dto.getId(),dto.getMenu(),dto.getPid(),dto.getSort()));
        }

        //全部树节点数据
        resMap.put("treeList", buildMenuTree(treeList));
        //全部树ids
        resMap.put("ids", ids);
        return resMap;
    }

    /**
     * 保存角色授权
     *
     * @return
     */
    @Log(type = Log.OperationType.ADD,button = "角色授权",menu = "角色管理")
    @PreAuthorize("hasAuthority('role:auth')")
    @ApiOperation(value = "保存角色授权")
    @PostMapping(value = "/saveRolePermission")
    public Boolean saveRolePermission(@RequestBody JSONObject json) {

        long start = System.currentTimeMillis();
        Integer roleId = json.getInteger("roleId");
        String permissionIds = json.getString("permissionIds");
        sysRoleMenuService.saveRolePermission(roleId, permissionIds);
        logger.info("======角色授权成功=====耗时:" + (System.currentTimeMillis() - start) + "毫秒");
        return true;
    }


    /**
     * 构建菜单树表
     * @param list
     * @return
     */
   private List<TreeData> buildMenuTree(List<TreeData> list) {
        //排序 parentId分组
        Map<Integer,List<TreeData>> map =list.stream()
                .sorted(Comparator.comparing(TreeData::getSort))
                .collect(Collectors.groupingBy(TreeData::getPid));

        //组合上下级关系
        list.forEach(menu ->{
            List<TreeData> children = map.get(menu.getValue());
            if(children!=null){
                menu.setChildren(children);
            }else{
                menu.setChildren(new ArrayList<>(0));
            }
        });
        //返回根节点
        return map.get(0);
    }

}
