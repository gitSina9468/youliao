package com.seahorse.youliao.service;


import com.seahorse.youliao.service.entity.PageDTO;

import java.util.List;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.service
 * @ClassName: BaseService
 * @Description: 基础service
 * @author:songqiang
 * @Date:2020-01-03 10:15
 **/
public interface BaseService<T> {


    boolean insert(T dto);

    boolean insertInBatch(List<T> dtoList);

    boolean update(T dto);

    boolean delete(T dto);

    <PK> boolean deleteByIds(List<PK> ids);

    List<T> getList(T query);

    PageDTO<T> getList(T dto, int pageNum, int pageSize);

    T get(T dto);

    <PK> T getById(PK pk);

    <PK> List<T> getListByIds(List<PK> pkList);
}
