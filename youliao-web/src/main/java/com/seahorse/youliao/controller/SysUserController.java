package com.seahorse.youliao.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.SysUserRoleService;
import com.seahorse.youliao.service.SysUserService;
import com.seahorse.youliao.service.entity.SysUserDTO;
import com.seahorse.youliao.service.entity.SysUserRoleDTO;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.SysUserChangePassword;
import com.seahorse.youliao.vo.request.SysUserQueryVO;
import com.seahorse.youliao.vo.request.SysUserUpdateVO;
import com.seahorse.youliao.vo.response.SysUserPageInfoVO;
import com.seahorse.youliao.vo.response.SysUserResponseVO;
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
import java.util.stream.Collectors;

/**
* describe: 用户信息表
* @author : songqiang
* @date: 2020-01-17 09:23:46.152
**/
@RestController
@Api(value = "SysUserController",tags = "系统-用户管理")
@RequestMapping("/sys/user")
@Validated
public class SysUserController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);
    
	@Autowired
    private SysUserService sysUserService;

	@Autowired
    private SysUserRoleService userRoleService;


    /**
     * 分页查询
     * @param sysUserQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("getPageList")
    public SysUserPageInfoVO selectPageList(@RequestBody @Valid SysUserQueryVO sysUserQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", sysUserQueryVO.toString());
        LOGGER.info(info);
        SysUserDTO sysUser = BeanUtil.convert(sysUserQueryVO, SysUserDTO.class);

        Page<SysUserResponseVO> page = PageHelper.startPage(sysUserQueryVO.getPageNum(), sysUserQueryVO.getPageSize());
        List<SysUserDTO> sysUserList = sysUserService.getList(sysUser);
        SysUserPageInfoVO sysUserPageInfo = new SysUserPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), sysUserPageInfo);
        List<SysUserResponseVO> voList = BeanUtil.convert(sysUserList, SysUserResponseVO.class);
        sysUserPageInfo.setList(voList);

        return sysUserPageInfo;
    }

    /**
     * 查询用户角色
     * @param userid
     */
    @ApiOperation(value = "查询用户角色")
    @GetMapping("queryUserRole")
    public List<Integer> queryUserRole(@ApiParam(name = "userid",value = "userid",required = true)
                                           @RequestParam(value = "userid") Integer userid){

        SysUserRoleDTO userRoleDTO = new SysUserRoleDTO();
        userRoleDTO.setUserId(userid);
        List<SysUserRoleDTO> list = userRoleService.getList(userRoleDTO);
        return list.stream().map(SysUserRoleDTO::getRoleId).collect(Collectors.toList());
    }

    /**
     * 用户绑定到部门
     * @param json
     */
    @Log(type = Log.OperationType.UPDATE,button = "用户绑定到部门",menu = "用户管理")
    @PreAuthorize("hasAuthority('user:dept:add')")
    @ApiOperation(value = "用户绑定到部门")
    @PostMapping("bindSysDepartWithUser")
    public boolean bindSysDepartWithUser(@RequestBody JSONObject json){

        Integer deptId = json.getInteger("depId");
        List<String> userIdList = (List)json.get("userIdList");
        return sysUserService.updateDeptIdByUserIds(deptId,userIdList);
    }

    /**
     * 前期用户跟部门多对一 后期会单独从用户中提出deptId单独维护
     * 部门用户 解除关系
     * @param userId
     * @return
     */
    @Log(type = Log.OperationType.UPDATE,button = "用户部门解除关系",menu = "用户管理")
    @PreAuthorize("hasAuthority('user:dept:delete')")
    @ApiOperation(value = "部门用户 解除关系")
    @DeleteMapping("deleteUserInDepart")
    public boolean deleteUserInDepart(@RequestParam Integer userId){

        return sysUserService.deleteUserDept(userId);
    }

    /**
     * 部门用户 批量解除关系
     * @param userIds
     * @return
     */
    @Log(type = Log.OperationType.UPDATE,button = "用户部门批量解除关系",menu = "用户管理")
    @PreAuthorize("hasAuthority('user:dept:delete')")
    @ApiOperation(value = "部门用户 批量解除关系")
    @DeleteMapping("deleteUserInDepartBatch")
    public boolean deleteUserInDepartBatch(@RequestParam String userIds){

        return sysUserService.deleteUserDeptBatch(userIds);
    }

    /**
     * 新增用户
     * @param userUpdateVO
     */
    @Log(type = Log.OperationType.ADD,button = "新增用户",menu = "用户管理")
    @PreAuthorize("hasAuthority('user:add')")
    @ApiOperation(value = "新增用户")
    @PostMapping("insert")
    public boolean insert(@RequestBody @Valid SysUserUpdateVO userUpdateVO){

        SysUserDTO userDTO = BeanUtil.convert(userUpdateVO,SysUserDTO.class);
        userDTO.setCreateBy(SecurityUtils.getUsername());
        userDTO.setCreateTime(new Date());
        //默认新增用户启用状态
        userDTO.setEnabled(true);

        return sysUserService.insertUser(userDTO,userUpdateVO.getSelectedroles());
    }

    /**
     * 编辑用户
     * @param userUpdateVO
     */
    @Log(type = Log.OperationType.UPDATE,button = "编辑用户",menu = "用户管理")
    @PreAuthorize("hasAuthority('user:update')")
    @ApiOperation(value = "编辑用户")
    @PutMapping("update")
    public boolean update(@RequestBody @Valid SysUserUpdateVO userUpdateVO){

        //用户id不能为空
        if(userUpdateVO.getId() == null){
            throw new BusinessException("用户id不能为空");
        }
        if(userUpdateVO.getUserName().equals("admin")){
            throw new BusinessException("超级管理员不可编辑");
        }
        SysUserDTO userDTO = BeanUtil.convert(userUpdateVO,SysUserDTO.class);
        return sysUserService.updateUser(userDTO,userUpdateVO.getSelectedroles());
    }

    /**
     * 修改密码
     * @param userChangePassword
     */
    @Log(type = Log.OperationType.UPDATE,button = "修改密码",menu = "用户管理")
    @PreAuthorize("hasAuthority('user:password:update')")
    @ApiOperation(value = "修改密码")
    @PutMapping("changPassword")
    public boolean changPassword(@RequestBody @Valid SysUserChangePassword userChangePassword){

        //判断两次密码输入一致
        if(!userChangePassword.getPassword().equals(userChangePassword.getConfirmPassword())){
            throw new BusinessException("两次密码输入不一样");
        }
        return sysUserService.changePassword(userChangePassword.getUserName(),userChangePassword.getPassword());
    }

    /**
     * 批量冻结用户 解冻用户
     * @param json
     */
    @Log(type = Log.OperationType.UPDATE,button = "批量冻结用户 解冻用户",menu = "用户管理")
    @PreAuthorize("hasAuthority('user:batch:frozen')")
    @ApiOperation(value = "批量冻结用户 解冻用户")
    @PutMapping("frozenBatch")
    public boolean frozenBatch(@RequestBody JSONObject json){

        boolean status = json.getInteger("status") == 1?true:false;
        String ids = json.getString("ids");
        return sysUserService.frozenBatch(status,ids);
    }

    /**
     * 删除用户
     * @param id
     */
    @Log(type = Log.OperationType.DELETE,button = "删除用户",menu = "用户管理")
    @PreAuthorize("hasAuthority('user:delete')")
    @ApiOperation(value = "删除用户")
    @DeleteMapping("delete")
    public boolean delete(@ApiParam(name = "id",value = "id",required = true)
                              @RequestParam Integer id){

        SysUserDTO userDTO = new SysUserDTO();
        userDTO.setId(id);
        return sysUserService.delete(userDTO);
    }

    /**
     * 批量删除用户
     * @param ids
     */
    @Log(type = Log.OperationType.DELETE,button = "批量删除用户",menu = "用户管理")
    @PreAuthorize("hasAuthority('user:batch:delete')")
    @ApiOperation(value = "批量删除用户")
    @DeleteMapping("deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){

        return sysUserService.deleteByIds(ids);
    }

}
