package com.seahorse.youliao.vo.response.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @description: 树形数据展示
 * @author: Mr.Song
 * @create: 2020-02-20 18:05
 **/
@Getter
@Setter
@ToString
public class TreeData {

    /**
     * 树形节点key
     */
    private String key;

    /**
     * 树形节点value
     */
    private Integer value;

    /**
     * 树形节点名称
     */
    private String title;

    /**
     * 父节点
     */
    private Integer pid;

    /**
     * 排序
     */
    private Integer sort;

    public TreeData(String key, Integer value, String title, Integer pid, Integer sort) {
        this.key = key;
        this.value = value;
        this.title = title;
        this.pid = pid;
        this.sort = sort;
    }

    private List<TreeData> children;
}
