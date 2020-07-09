package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.service.EsDocSearchRecordService;
import com.seahorse.youliao.service.entity.EsDocSearchRecordDTO;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.EsDocSearchRecordQueryVO;
import com.seahorse.youliao.vo.response.EsDocSearchRecordPageInfoVO;
import com.seahorse.youliao.vo.response.EsDocSearchRecordResponseVO;
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
* es 文档搜索
* @author  gitsina
* @date    2020-06-23 03:09:37.330
**/
@RestController
@Api(tags = "es 文档搜索")
@RequestMapping("esDocSearchRecord")
public class EsDocSearchRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsDocSearchRecordController.class);

	@Autowired
    private EsDocSearchRecordService esDocSearchRecordService;
    /**
     * 分页查询
     * @param esDocSearchRecordQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation("分页查询")
    @PostMapping("getPageList")
    public EsDocSearchRecordPageInfoVO selectPageList(@RequestBody @Valid EsDocSearchRecordQueryVO esDocSearchRecordQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", esDocSearchRecordQueryVO.toString());
        LOGGER.info(info);
        EsDocSearchRecordDTO esDocSearchRecord = BeanUtil.convert(esDocSearchRecordQueryVO, EsDocSearchRecordDTO.class);

        Page<EsDocSearchRecordResponseVO> page = PageHelper.startPage(esDocSearchRecordQueryVO.getPageNum(), esDocSearchRecordQueryVO.getPageSize());
        List<EsDocSearchRecordDTO> esDocSearchRecordList = esDocSearchRecordService.getList(esDocSearchRecord);
        EsDocSearchRecordPageInfoVO esDocSearchRecordPageInfo = new EsDocSearchRecordPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), esDocSearchRecordPageInfo);
        List<EsDocSearchRecordResponseVO> voList = BeanUtil.convert(esDocSearchRecordList, EsDocSearchRecordResponseVO.class);
        esDocSearchRecordPageInfo.setList(voList);

        return esDocSearchRecordPageInfo;
    }


}
