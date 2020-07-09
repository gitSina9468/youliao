package com.seahorse.youliao.util;

import com.seahorse.youliao.vo.response.SysDeptResponseVO;
import com.seahorse.youliao.vo.response.SysMenuResponseVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @description: 树形数据生成工具类
 * @author: Mr.Song
 * @create: 2020-01-26 18:45
 **/
public class TreeUtil {


    /**
     * 生成树形数据菜单
     * @return
     */
    public static List<SysMenuResponseVO> buildMenuTree(List<SysMenuResponseVO> allMenu){

        //根节点
        List<SysMenuResponseVO> rootMenu = new ArrayList<>(16);
        for (SysMenuResponseVO nav : allMenu) {
            //父节点是0的，为根节点
            if(nav.getPid().equals(0)){
                nav.setKey(nav.getId().toString());
                rootMenu.add(nav);
            }
        }
        //根据Menu类的order排序
        Collections.sort(rootMenu, order());
        //为根菜单设置子菜单，getClild是递归调用的
        for (SysMenuResponseVO nav : rootMenu) {
            //获取根节点下的所有子节点 使用getChild方法
            List<SysMenuResponseVO> childList = getMenuChild(nav.getId(), allMenu);
            //给根节点设置子节点
            nav.setChildren(childList);
        }
        return rootMenu;
    }

    /**
     * 获取子节点
     * @param id 父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public static List<SysMenuResponseVO> getMenuChild(Integer id,List<SysMenuResponseVO> allMenu){
        //子菜单
        List<SysMenuResponseVO> childList = new ArrayList<>(16);
        for (SysMenuResponseVO nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getPid().equals(id)){
                nav.setKey(nav.getId().toString());
                childList.add(nav);
            }
        }
        //递归
        for (SysMenuResponseVO nav : childList) {
            nav.setChildren(getMenuChild(nav.getId(), allMenu));
        }
        //排序
        Collections.sort(childList,order());
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return null;
        }
        return childList;
    }

    /**
     * 排序,根据order排序
     * @return
     */
    public static Comparator<SysMenuResponseVO> order(){
        Comparator<SysMenuResponseVO> comparator = new Comparator<SysMenuResponseVO>() {
            @Override
            public int compare(SysMenuResponseVO o1, SysMenuResponseVO o2) {
                if(!o1.getSort().equals(o2.getSort())){
                    return o1.getSort() - o2.getSort();
                }
                return 0;
            }
        };
        return comparator;
    }

    /**
     * 生成树形部门
     * @param voList
     * @return
     */
    public static List<SysDeptResponseVO> buildDeptTree(List<SysDeptResponseVO> voList) {

        //根节点
        List<SysDeptResponseVO> rootDept = new ArrayList<>(16);
        for (SysDeptResponseVO dept : voList) {
            //父节点是0的，为根节点
            if(dept.getPid().equals(0)){
                dept.setKey(dept.getId().toString());
                dept.setTitle(dept.getDeptName());
                dept.setValue(dept.getId().toString());
                rootDept.add(dept);
            }
        }
        //为根菜单设置子菜单，getClild是递归调用的
        for (SysDeptResponseVO dept : rootDept) {
            //  获取根节点下的所有子节点 使用getChild方法
            List<SysDeptResponseVO> childList = getDeptChild(dept.getId(), voList);
            //给根节点设置子节点
            dept.setChildren(childList);
        }
        return rootDept;

    }

    /**
     * 获取子节点
     * @param id 父节点id
     * @param allDept 所有部门
     * @return 每个根节点下，所有子菜单列表
     */
    public static List<SysDeptResponseVO> getDeptChild(Integer id,List<SysDeptResponseVO> allDept){
        //子部门
        List<SysDeptResponseVO> childList = new ArrayList<>(16);
        for (SysDeptResponseVO nav : allDept) {
            // 遍历所有节点，将所有部门的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getPid().equals(id)){
                nav.setKey(nav.getId().toString());
                nav.setTitle(nav.getDeptName());
                nav.setValue(nav.getId().toString());
                childList.add(nav);
            }
        }
        //递归
        for (SysDeptResponseVO nav : childList) {
            nav.setChildren(getDeptChild(nav.getId(), allDept));
        }
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return null;
        }
        return childList;
    }

}
