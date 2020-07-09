package com.seahorse.youliao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.dao.BaseDao;
import com.seahorse.youliao.service.entity.PageDTO;
import com.seahorse.youliao.service.BaseService;
import com.seahorse.youliao.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.impl
 * @ClassName: BaseServiceImpl
 * @Description: 基础service实现类
 * @author:songqiang
 * @Date:2020-01-03 10:17
 **/
public class BaseServiceImpl<T> implements BaseService<T> {



    private BaseDao dao;

    private Class<T> serviceEntityCls;

    private Class<?> daoEntityCls;

    private void init() {
        if (dao == null || daoEntityCls == null) {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if(field.getAnnotation(Autowired.class) != null) {
                    field.setAccessible(true);
                    try {
                        dao = (BaseDao) field.get(this);

                        Type[] types = field.getType().getGenericInterfaces();
                        ParameterizedType type = (ParameterizedType)types[0];
                        daoEntityCls = (Class<T>) type.getActualTypeArguments()[0];

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    private Class<?> getDaoEntityCls() {
        if(daoEntityCls == null) {
            init();
        }
        if(daoEntityCls == null) {
            throw new IllegalArgumentException("请在Dao上使用Dao注解");
        }
        return daoEntityCls;
    }

    private BaseDao getBaseDao() {
        if (dao == null) {
            init();
        }
        return dao;
    }

    private Class<T> getServiceEntityClass() {
        if(serviceEntityCls == null) {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            try {
                Class<T> cls = (Class<T>) type.getActualTypeArguments()[0];
                serviceEntityCls = cls;
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return serviceEntityCls;
    }

    @Override
    public boolean insert(T dto) {
        Object doEntity = BeanUtil.convert(dto, getDaoEntityCls());
        boolean success = getBaseDao().insert(doEntity) > 0;
        if(success) {
            BeanUtils.copyProperties(doEntity, dto);
        }
        return success;
    }

    @Override
    public boolean insertInBatch(List<T> dtoList) {
        List<?> doEntitys = BeanUtil.convert(dtoList, getDaoEntityCls());
        return getBaseDao().insertInBatch(doEntitys) > 0;
    }

    @Override
    public boolean update(T dto) {
        Object doEntity = BeanUtil.convert(dto, getDaoEntityCls());
        return getBaseDao().update(doEntity) > 0;
    }

    @Override
    public boolean delete(T dto) {
        Object daoEntity = BeanUtil.convert(dto, getDaoEntityCls());
        return getBaseDao().delete(daoEntity) > 0;
    }

    @Override
    public <PK> boolean  deleteByIds(List<PK> ids) {
        return getBaseDao().deleteByIds(ids) > 0;
    }

    @Override
    public List<T> getList(T dto) {
        Object doEntity = BeanUtil.convert(dto, getDaoEntityCls());
        List list = getBaseDao().getList(doEntity);
        return BeanUtil.convert(list, getServiceEntityClass());
    }

    @Override
    public PageDTO<T> getList(T dto, int pageNum, int pageSize) {
        Page<T> page = PageHelper.startPage(pageNum, pageSize);
        List<T> list = getList(dto);

        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setList(list);
        pageDTO.setPageNum(page.getPageNum());
        pageDTO.setPageSize(page.getPageSize());
        pageDTO.setTotal(page.getTotal());
        return pageDTO;
    }

    @Override
    public T get(T dto) {
        Object doEntity = BeanUtil.convert(dto, getDaoEntityCls());
        Object rs = getBaseDao().get(doEntity);
        return BeanUtil.convert(rs, getServiceEntityClass());
    }

    @Override
    public <PK> T getById(PK pk) {
        Object doEntity = getBaseDao().getById(pk);
        return BeanUtil.convert(doEntity, getServiceEntityClass());
    }

    @Override
    public <PK> List<T> getListByIds(List<PK> pks) {
        List list = getBaseDao().getListByIds(pks);
        return BeanUtil.convert(list, getServiceEntityClass());
    }

    public <C, R> List<R> getListByCondition(C condition) {
        List list = getBaseDao().getListByCondition(condition);
        return list;
    }
}
