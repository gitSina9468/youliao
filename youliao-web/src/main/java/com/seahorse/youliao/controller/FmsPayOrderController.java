package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.common.ResponseEntity;
import com.seahorse.youliao.enums.PayStatusEnum;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.FmsPayOrderService;
import com.seahorse.youliao.service.FmsRefundOrderService;
import com.seahorse.youliao.service.entity.FmsPayOrderDTO;
import com.seahorse.youliao.service.entity.FmsRefundOrderDTO;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.utils.QrCodeUtil;
import com.seahorse.youliao.vo.request.FmsPayOrderQueryVO;
import com.seahorse.youliao.vo.request.FmsPayOrderRefundVO;
import com.seahorse.youliao.vo.request.FmsPayOrderUpdateVO;
import com.seahorse.youliao.vo.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* describe: 支付订单
* @author : songtqiang
* @date: 2020-03-18 02:20:25.891
**/
@RestController
@Api(value = "FmsPayOrderController",tags = "支付订单管理")
@RequestMapping("fmsPayOrder")
@Validated
public class FmsPayOrderController {
	
    private static final Logger logger = LoggerFactory.getLogger(FmsPayOrderController.class);
    
	@Autowired
    private FmsPayOrderService fmsPayOrderService;

	@Autowired
    private FmsRefundOrderService fmsRefundOrderService;

    /**
     * 分页查询
     * @param fmsPayOrderQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("getPageList")
    public FmsPayOrderPageInfoVO selectPageList(@RequestBody @Valid FmsPayOrderQueryVO fmsPayOrderQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", fmsPayOrderQueryVO.toString());
        logger.info(info);
        FmsPayOrderDTO fmsPayOrder = BeanUtil.convert(fmsPayOrderQueryVO, FmsPayOrderDTO.class);

        Page<FmsPayOrderResponseVO> page = PageHelper.startPage(fmsPayOrderQueryVO.getPageNum(), fmsPayOrderQueryVO.getPageSize());
        List<FmsPayOrderDTO> fmsPayOrderList = fmsPayOrderService.getList(fmsPayOrder);
        FmsPayOrderPageInfoVO fmsPayOrderPageInfo = new FmsPayOrderPageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), fmsPayOrderPageInfo);
        List<FmsPayOrderResponseVO> voList = BeanUtil.convert(fmsPayOrderList, FmsPayOrderResponseVO.class);
        fmsPayOrderPageInfo.setList(voList);

        return fmsPayOrderPageInfo;
    }

    /**
     * 商品扫码支付下单
     * 生成支付下单记录
     * 返回生成的系统订单单号
     * @param payOrderUpdateVO
     * @return
     */
    @Log(type = Log.OperationType.ADD,button = "商品扫码支付下单生成记录",menu = "支付订单")
    @PreAuthorize("hasAuthority('order:unified')")
    @ApiOperation(value = "商品统一下单")
    @PostMapping("unifiedOrder")
    public ResponseEntity unifiedOrder(@RequestBody @Valid FmsPayOrderUpdateVO payOrderUpdateVO) throws Exception{

        FmsPayOrderDTO payOrderDTO = BeanUtil.convert(payOrderUpdateVO,FmsPayOrderDTO.class);
        payOrderDTO.setCreateTime(new Date());
        payOrderDTO.setCreateBy(SecurityUtils.getUsername());
        //返回系统订单号
        return ResponseEntity.ok("系统订单号",fmsPayOrderService.unifiedOrder(payOrderDTO));
    }

    /**
     * 根据系统订单号获取到下单记录
     * 将扫码支付路径转成二维码的base64形式
     * @param orderNo
     * @return
     */
    @Log(type = Log.OperationType.PAY,button = "订单扫码支付",menu = "支付订单")
    @ApiOperation(value = "订单扫码支付")
    @GetMapping("payOrder")
    public ResponseEntity payOrder(@RequestParam String orderNo){

        //查询订单记录
        FmsPayOrderDTO payOrderDTO = new FmsPayOrderDTO();
        payOrderDTO.setOrderNo(orderNo);
        FmsPayOrderDTO order = fmsPayOrderService.get(payOrderDTO);
        if(order == null){
            throw new BusinessException("订单不存在!");
        }
        if(!PayStatusEnum.UNIFIED_ORDER.getValue().equals(order.getPayStatus())){
            logger.info("订单已经"+PayStatusEnum.getNameByValue(order.getPayStatus()));
            throw new BusinessException("订单已经"+PayStatusEnum.getNameByValue(order.getPayStatus()));
        }
        //将下单二维码路径转为图片base64
        return ResponseEntity.ok("支付二维码",QrCodeUtil.qrCodeToBase64(order.getQrCodeUrl()));
    }

    /**
     * 查询支付是否回调更新订单状态为成功
     * @param id
     * @return
     */
    @ApiOperation(value = "查询订单状态")
    @GetMapping("getOrderStatus")
    public Integer getOrderStatus(@RequestParam String id){

        FmsPayOrderDTO order = fmsPayOrderService.getById(id);
        if(order == null){
            throw new BusinessException("没有查询到此订单");
        }
        return order.getPayStatus();
    }

    /**
     * 订单退款: 可以多次退款
     * 同一笔支付交易可以多次退款
     * 退款的总额不能超出交易总额
     * @param payOrderRefundVO 退款信息
     * @return
     */
    @Log(type = Log.OperationType.REFUND,button = "订单退款",menu = "支付订单")
    @PreAuthorize("hasAuthority('order:refund')")
    @ApiOperation(value = "订单退款")
    @PostMapping("/tradeRefund")
    public ResponseEntity tradeRefund(@RequestBody @Valid FmsPayOrderRefundVO payOrderRefundVO) {

        try {
            //对象转换
            FmsRefundOrderDTO refundOrderDTO = BeanUtil.convert(payOrderRefundVO,FmsRefundOrderDTO.class);
            refundOrderDTO.setRefundUser(SecurityUtils.getUsername());
            refundOrderDTO.setCreateTime(new Date());
            refundOrderDTO.setCreateBy(SecurityUtils.getUsername());
            fmsPayOrderService.tradeRefund(refundOrderDTO);
        } catch (Exception e) {
            logger.error(e.toString());
            return ResponseEntity.error(e.getMessage());
        }
        return ResponseEntity.ok("成功");
    }

    /**
     * 删除商品下单
     * @param id
     * @return
     */
    @Log(type = Log.OperationType.DELETE,button = "删除商品下单",menu = "支付订单")
    @PreAuthorize("hasAuthority('order:delete')")
    @ApiOperation(value = "删除商品下单")
    @DeleteMapping("delete")
    public boolean delete(@ApiParam(name = "id",value = "id",required = true)
                              @RequestParam  Long id){

        return fmsPayOrderService.deleteById(id);
    }

    /**
     * 查看商品支付明细
     * @param id
     * @return
     */
    @ApiOperation(value = "查看商品支付明细")
    @PostMapping("getPayOrderDetail")
    public FmsPayOrderDetailResponseVO getPayOrderDetail(@ApiParam(name = "id",value = "id",required = true)
                                         @RequestParam Long id){

        //查询支付记录
        FmsPayOrderDTO payOrder = fmsPayOrderService.getById(id);
        if(payOrder == null){
            throw new BusinessException("无此订单");
        }
        FmsRefundOrderDTO refundOrderDTO = new FmsRefundOrderDTO();
        refundOrderDTO.setPayId(id);
        //查询退款明细
        List<FmsRefundOrderDTO> list = fmsRefundOrderService.getList(refundOrderDTO);

        FmsPayOrderDetailResponseVO responseVO = BeanUtil.convert(payOrder,FmsPayOrderDetailResponseVO.class);
        List<FmsRefundOrderResponseVO> voList = BeanUtil.convert(list, FmsRefundOrderResponseVO.class);
        responseVO.setList(voList);
        return responseVO;
    }

    /**
     * 商品支付统计信息
     * @return
     */
    @ApiOperation(value = "商品支付统计信息")
    @GetMapping("getPayOrderCount")
    public FmsPayOrderCountResponseVO getPayOrderCount(){

        FmsPayOrderCountResponseVO responseVO = new FmsPayOrderCountResponseVO();
        //查询所有支付成功记录
        FmsPayOrderDTO query = new FmsPayOrderDTO();
        query.setPayStatus(PayStatusEnum.SUCCESS.getValue());
        List<FmsPayOrderDTO> list = fmsPayOrderService.getList(query);
        //支付笔数
        responseVO.setSize(list.size());
        //支付总金额
        responseVO.setPayTotalFee(list.stream().map(FmsPayOrderDTO::getPayFee).reduce(BigDecimal.ZERO,BigDecimal::add));
        //退款总金额
        responseVO.setRefundTotalFee(list.stream().map(FmsPayOrderDTO::getRefundFee).reduce(BigDecimal.ZERO,BigDecimal::add));
        return responseVO;
    }

}
