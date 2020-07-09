package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.Objects;

/**
* describe: 菜单权限表
* @author : songqiang
* @date: 2020-01-17 09:23:45.259
**/
@Getter
@Setter
@ToString
public class SysMenuDO {
    /**
     * 菜单ID
     */
    private Integer id;
    /**
     * 菜单名称
     */
    private String menu;
    /**
     * 是否外链
     */
    private Boolean isFrame;
    /**
     * 组件
     */
    private String component;
    /**
     * 组件名称
     */
    private String componentName;
    /**
     * 请求地址
     */
    private String path;
    /**
     * 重定向
     */
    private String redirect;
    /**
     * 父级id
     */
    private Integer pid;
    /**
     * 类型:1目录,2菜单,3按钮
     */
    private Integer type;
    /**
     * 菜单状态:显示,隐藏
     */
    private Boolean visible;
    /**
     * 权限标识
     */
    private String permission;
    /**
     * 缓存
     */
    private Boolean cache;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String remark;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final SysMenuDO menuDO = (SysMenuDO) o;
        return menu.equals(menuDO.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMenu());

    }
}