package com.seahorse.youliao.dao;

import java.util.List;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.dao
 * @ClassName: BaseDao
 * @Description: baseDao
 * @author:songqiang
 * @Date:2020-01-03 10:34
 **/
public interface BaseDao<T,PK> {


    int insert(T t);

    int insertInBatch(List<T> list);

    int update(T t);

    int updateInBatch(List<T> list);

    int delete(T t);

    int deleteByIds(List<PK> listIds);

    List<T> getList(T condition);

    T get(T condition);

    T getById(PK id);

    List<T> getListByIds(List<PK> idList);

    <C, R> List<R> getListByCondition(C condition);
}
