package com.seahorse.youliao.service;

import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.service.entity.FmsPayOrderDTO;
import com.seahorse.youliao.service.entity.FmsRefundOrderDTO;

/**
 * @description: 支付宝支付service
 * @author: Mr.Song
 * @create: 2020-03-07 17:22
 **/
public interface AliPayService {


    /**
     * 下单生成二维码
     * @param payOrderDTO
     * @return 二维码地址
     * @throws Exception
     */
    void createTradeQRCode(FmsPayOrderDTO payOrderDTO);


    /**
     * 支付宝退款
     * @param outRefundNo
     * @param outTradeNo
     * @param refundOrderDTO
     * @return
     * @throws BusinessException
     */
    void tradeRefund(String outRefundNo,String outTradeNo,FmsRefundOrderDTO refundOrderDTO) throws BusinessException;


    /**
     * 第三方支付结果查询
     * @param outTradeNo
     * @throws BusinessException
     */
    void tradeQuery(String outTradeNo) throws BusinessException;
}
