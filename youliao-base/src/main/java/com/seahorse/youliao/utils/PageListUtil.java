package com.seahorse.youliao.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.utils
 * @ClassName: PageListUtil
 * @Description: list集合分页封装
 * @author:songqiang
 * @Date:2020-04-08 17:20
 **/
public class PageListUtil {

    /**
     * 开始分页
     * @param list
     * @param pageNum 页码
     * @param pageSize 每页多少条数据
     * @return
     */
    public static List startPage(List list, Integer pageNum,
                                 Integer pageSize) {
        if (list == null || list.size() == 0) {
            return new ArrayList();
        }

        // 记录总数
        Integer count = list.size();
        // 页数
        Integer pageCount = 0;
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        // 开始索引页
        int fromIndex = 0;
        // 结束索引页
        int toIndex = 0;
        if(pageNum <= 0){
            //页数小于0
            return new ArrayList();
        }else if(pageNum < pageCount){
            //页数小于总页数
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
            return list.subList(fromIndex, toIndex);
        }else if(pageNum.equals(pageCount)){
            //最后一页
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
            return list.subList(fromIndex, toIndex);
        }else{
            //页数大于总页数
            return new ArrayList();
        }
    }
}
