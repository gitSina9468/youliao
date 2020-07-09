package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.service.entity.BaseDictionaryDTO;
import com.seahorse.youliao.service.BaseDictionaryService;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.BaseDictionaryQueryVO;
import com.seahorse.youliao.vo.response.BaseDictionaryPageInfoVO;
import com.seahorse.youliao.vo.response.BaseDictionaryResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: 数据字典
 * @author: Mr.Song
 * @create: 2019-12-15 20:46
 **/
@RestController
@Api(value = "BaseDictionaryController",tags="系统-数据字典管理")
@RequestMapping("baseDictionary")
@Validated
public class BaseDictionaryController {


    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDictionaryController.class);

    @Autowired
    private BaseDictionaryService baseDictionaryService;

    /**
     * 分页查询
     * @param baseDictionaryQueryVO 分页查询参数|必填
     * @see BaseDictionaryQueryVO
     * @return 分页参数
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("getPageList")
    public BaseDictionaryPageInfoVO selectPageList(@RequestBody @Valid BaseDictionaryQueryVO baseDictionaryQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", baseDictionaryQueryVO.toString());
        LOGGER.info(info);
        BaseDictionaryDTO baseDictionary = BeanUtil.convert(baseDictionaryQueryVO, BaseDictionaryDTO.class);

        Page<BaseDictionaryResponseVO> page = PageHelper.startPage(baseDictionaryQueryVO.getPageNum(), baseDictionaryQueryVO.getPageSize());
        baseDictionary.setDeleted(false);
        List<BaseDictionaryDTO> baseDictionaryList = baseDictionaryService.getList(baseDictionary);
        BaseDictionaryPageInfoVO baseDictionaryPageInfo = BeanUtil.convert(page.toPageInfo(),BaseDictionaryPageInfoVO.class);
        List<BaseDictionaryResponseVO> voList = BeanUtil.convert(baseDictionaryList, BaseDictionaryResponseVO.class);
        baseDictionaryPageInfo.setList(voList);

        return baseDictionaryPageInfo;
    }


    /**
     * 根据字典类型查询数据字典
     * @param typeCode 类型code|必填
     * @return
     */
    @ApiOperation(value = "根据字典类型查询数据字典")
    @PostMapping("listByType/{typeCode}")
    public List<BaseDictionaryResponseVO> listByType(@PathVariable String typeCode){


        LOGGER.info("根据字典类型查询数据字典 typeCode = "+typeCode);
        List<BaseDictionaryDTO> dtoList = baseDictionaryService.listByType(typeCode);
        return  BeanUtil.convert(dtoList, BaseDictionaryResponseVO.class);

    }


}
