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
import com.seahorse.youliao.service.EsShareDocService;
import com.seahorse.youliao.vo.response.EsShareDocResponseVO;
import com.seahorse.youliao.vo.request.EsShareDocQueryVO;
import com.seahorse.youliao.vo.response.EsShareDocPageInfoVO;
import com.seahorse.youliao.service.entity.EsShareDocDTO;

/**
* es  分享文档
* @author  gitsina
* @date    2020-06-23 03:09:37.503
**/
@RestController
@Api(tags = "es  分享文档")
@RequestMapping("esShareDoc")
public class EsShareDocController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsShareDocController.class);

	@Autowired
    private EsShareDocService esShareDocService;
    /**
     * 分页查询
     * @param esShareDocQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation("分页查询")
    @PostMapping("getPageList")
    public EsShareDocPageInfoVO selectPageList(@RequestBody @Valid EsShareDocQueryVO esShareDocQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", esShareDocQueryVO.toString());
        LOGGER.info(info);
        EsShareDocDTO esShareDoc = BeanUtil.convert(esShareDocQueryVO, EsShareDocDTO.class);

        Page<EsShareDocResponseVO> page = PageHelper.startPage(esShareDocQueryVO.getPageNum(), esShareDocQueryVO.getPageSize());
        List<EsShareDocDTO> esShareDocList = esShareDocService.getList(esShareDoc);
        EsShareDocPageInfoVO esShareDocPageInfo = new EsShareDocPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), esShareDocPageInfo);
        List<EsShareDocResponseVO> voList = BeanUtil.convert(esShareDocList, EsShareDocResponseVO.class);
        esShareDocPageInfo.setList(voList);

        return esShareDocPageInfo;
    }


}
