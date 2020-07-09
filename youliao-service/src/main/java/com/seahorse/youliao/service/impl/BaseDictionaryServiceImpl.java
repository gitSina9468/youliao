package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.BaseDictionaryDao;
import com.seahorse.youliao.dao.entity.BaseDictionaryDO;
import com.seahorse.youliao.service.entity.BaseDictionaryDTO;
import com.seahorse.youliao.service.BaseDictionaryService;
import com.seahorse.youliao.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 数据字典
 * @author: Mr.Song
 * @create: 2019-12-15 20:37
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseDictionaryServiceImpl extends BaseServiceImpl<BaseDictionaryDTO> implements BaseDictionaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDictionaryServiceImpl.class);

    @Autowired
    private BaseDictionaryDao baseDictionaryDao;



    /**
     * 根据typeCode查询数据字典
     *
     * @param typeCode
     * @return
     */
    @Override
    public List<BaseDictionaryDTO> listByType(String typeCode) {

        //查询数据库
        List<BaseDictionaryDO> doList = baseDictionaryDao.listByType(typeCode);
        List<BaseDictionaryDTO> dtoList = BeanUtil.convert(doList,BaseDictionaryDTO.class);
        return dtoList;
    }




}

