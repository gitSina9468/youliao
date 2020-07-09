package com.seahorse.youliao.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 数据字典
 * @author: Mr.Song
 * @create: 2019-12-15 20:31
 **/
@Getter
@Setter
@ToString
public class BaseDictionaryDO implements Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 字典编码
     */
    private String code;
    /**
     * 字典名称
     */
    private String name;
    /**
     * 字典类型编码
     */
    private String typeCode;
    /**
     * 字典类型名称
     */
    private String typeName;
    /**
     * 删除状态
     */
    private Boolean deleted;
    /**
     * 使用状态
     */
    private Boolean status;
    /**
     * 系统字典状态
     */
    private Boolean systemStatus;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 数据创建时间
     */
    private Date createTime;
    /**
     * 数据更新时间
     */
    private Date updateTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 更新人
     */
    private String updateBy;
}
