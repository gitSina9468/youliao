package com.seahorse.youliao.dao;

import com.seahorse.youliao.dao.entity.BaseDictionaryDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 数据字典
 * @author: Mr.Song
 * @create: 2019-12-15 20:30
 **/
@Repository
public interface BaseDictionaryDao extends BaseDao<BaseDictionaryDO, String> {


    /**
     * 根据类型code 查找
     * @param typeCode
     * @return
     */
    List<BaseDictionaryDO> listByType(@Param("typeCode") String typeCode);


    /**
     * 根据类型和code查询是否存在
     * @param dictionaryDO
     * @return
     */
    int findIfExist(BaseDictionaryDO dictionaryDO);

    /**
     * 查询数据字典类型
     * @return
     */
    List<BaseDictionaryDO> getTypes();

    /**
     * 查询类型下单个数据字典
     * @param code
     * @param typeCode
     * @return
     */
    BaseDictionaryDO getDictionaryByCode(@Param("code") String code,
                                         @Param("typeCode") String typeCode);

    /**
     * 根据类型code 查询是否是系统字典
     * @param typeCode
     * @return
     */
    int getSystemDictionaryByType(@Param("typeCode") String typeCode);
}
