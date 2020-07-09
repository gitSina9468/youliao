package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.service.FmsRefundOrderService;
import com.seahorse.youliao.service.entity.FmsRefundOrderDTO;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.FmsRefundOrderQueryVO;
import com.seahorse.youliao.vo.response.FmsRefundOrderPageInfoVO;
import com.seahorse.youliao.vo.response.FmsRefundOrderResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
* describe: 订单退费明细
* @author : songtqiang
* @date: 2020-03-18 02:20:26.714
**/
@RestController
@Api(value = "FmsRefundOrderController",tags = "支付订单退费明细")
@RequestMapping("fmsRefundOrder")
@Validated
public class FmsRefundOrderController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(FmsRefundOrderController.class);
    
	@Autowired
    private FmsRefundOrderService fmsRefundOrderService;
    /**
     * 分页查询
     * @param fmsRefundOrderQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("getPageList")
    public FmsRefundOrderPageInfoVO selectPageList(@RequestBody @Valid FmsRefundOrderQueryVO fmsRefundOrderQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", fmsRefundOrderQueryVO.toString());
        LOGGER.info(info);
        FmsRefundOrderDTO fmsRefundOrder = BeanUtil.convert(fmsRefundOrderQueryVO, FmsRefundOrderDTO.class);

        Page<FmsRefundOrderResponseVO> page = PageHelper.startPage(fmsRefundOrderQueryVO.getPageNum(), fmsRefundOrderQueryVO.getPageSize());
        List<FmsRefundOrderDTO> fmsRefundOrderList = fmsRefundOrderService.getList(fmsRefundOrder);
        FmsRefundOrderPageInfoVO fmsRefundOrderPageInfo = new FmsRefundOrderPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), fmsRefundOrderPageInfo);
        List<FmsRefundOrderResponseVO> voList = BeanUtil.convert(fmsRefundOrderList, FmsRefundOrderResponseVO.class);
        fmsRefundOrderPageInfo.setList(voList);

        return fmsRefundOrderPageInfo;
    }




}
