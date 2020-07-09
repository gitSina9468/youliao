package com.seahorse.youliao.service;

import com.seahorse.youliao.service.entity.BaseDictionaryDTO;

import java.util.List;

/**
 * @description: 数据字典
 * @author: Mr.Song
 * @create: 2019-12-15 20:37
 **/
public interface BaseDictionaryService extends BaseService<BaseDictionaryDTO> {


    /**
     * 根据typeCode查询数据字典
     * @param typeCode
     * @return
     */
    List<BaseDictionaryDTO> listByType(String typeCode);


}
