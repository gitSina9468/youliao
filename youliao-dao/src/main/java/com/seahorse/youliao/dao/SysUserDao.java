package com.seahorse.youliao.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.seahorse.youliao.dao.entity.SysUserDO;

import java.util.List;

/**
* describe: 用户信息表
* @author : songqiang
* @date: 2020-01-17 09:23:46.152
**/
@Repository
public interface SysUserDao extends BaseDao<SysUserDO, Integer> {


    /**
     * 更新用户部门id
     * @param deptId
     * @param list
     * @return
     */
    int updateDeptIdByUserIds(@Param("deptId") Integer deptId,
                              @Param("list") List<String> list);


    /**
     * 解冻 冻结
     * @param status
     * @param list
     * @return
     */
    int frozenBatch(@Param("status") boolean status,
                    @Param("list") List<String> list);

    /**
     * 查询用户信息
     * @param userName
     * @return
     */
    SysUserDO findByName(@Param("userName") String userName);
}