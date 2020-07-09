package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.service.EsUploadFileService;
import com.seahorse.youliao.service.entity.EsUploadFileDTO;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.EsUploadFileQueryVO;
import com.seahorse.youliao.vo.response.EsUploadFilePageInfoVO;
import com.seahorse.youliao.vo.response.EsUploadFileResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
* es 文档上传
* @author  gitsina
* @date    2020-06-23 03:09:37.684
**/
@RestController
@Api(tags = "es 文档上传")
@RequestMapping("esUploadFile")
public class EsUploadFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsUploadFileController.class);

	@Autowired
    private EsUploadFileService esUploadFileService;
    /**
     * 分页查询
     * @param esUploadFileQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation("分页查询")
    @PostMapping("getPageList")
    public EsUploadFilePageInfoVO selectPageList(@RequestBody @Valid EsUploadFileQueryVO esUploadFileQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", esUploadFileQueryVO.toString());
        LOGGER.info(info);
        EsUploadFileDTO esUploadFile = BeanUtil.convert(esUploadFileQueryVO, EsUploadFileDTO.class);

        Page<EsUploadFileResponseVO> page = PageHelper.startPage(esUploadFileQueryVO.getPageNum(), esUploadFileQueryVO.getPageSize());
        List<EsUploadFileDTO> esUploadFileList = esUploadFileService.getList(esUploadFile);
        EsUploadFilePageInfoVO esUploadFilePageInfo = new EsUploadFilePageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), esUploadFilePageInfo);
        List<EsUploadFileResponseVO> voList = BeanUtil.convert(esUploadFileList, EsUploadFileResponseVO.class);
        esUploadFilePageInfo.setList(voList);

        return esUploadFilePageInfo;
    }


}
