package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.utils.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.beans.BeanUtils;
import com.seahorse.youliao.service.EsDocService;
import com.seahorse.youliao.vo.response.EsDocResponseVO;
import com.seahorse.youliao.vo.request.EsDocQueryVO;
import com.seahorse.youliao.vo.response.EsDocPageInfoVO;
import com.seahorse.youliao.service.entity.EsDocDTO;

/**
* es 文档索引
* @author  gitsina
* @date    2020-06-23 03:09:37.134
**/
@RestController
@Api(tags = "es 文档索引")
@RequestMapping("esDoc")
public class EsDocController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsDocController.class);

	@Autowired
    private EsDocService esDocService;
    /**
     * 分页查询
     * @param esDocQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation("分页查询")
    @PostMapping("getPageList")
    public EsDocPageInfoVO selectPageList(@RequestBody @Valid EsDocQueryVO esDocQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", esDocQueryVO.toString());
        LOGGER.info(info);
        EsDocDTO esDoc = BeanUtil.convert(esDocQueryVO, EsDocDTO.class);

        Page<EsDocResponseVO> page = PageHelper.startPage(esDocQueryVO.getPageNum(), esDocQueryVO.getPageSize());
        List<EsDocDTO> esDocList = esDocService.getList(esDoc);
        EsDocPageInfoVO esDocPageInfo = new EsDocPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), esDocPageInfo);
        List<EsDocResponseVO> voList = BeanUtil.convert(esDocList, EsDocResponseVO.class);
        esDocPageInfo.setList(voList);

        return esDocPageInfo;
    }


}
