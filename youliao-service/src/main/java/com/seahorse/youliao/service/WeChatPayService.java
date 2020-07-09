package com.seahorse.youliao.service;

import com.seahorse.youliao.service.entity.FmsPayOrderDTO;

import java.math.BigDecimal;

/**
 * @description: 微信支付service
 * @author: Mr.Song
 * @create: 2020-03-07 17:23
 **/
public interface WeChatPayService {


    /**
     * 下单生成二维码
     * @param payOrderDTO
     * @return 二维码地址
     * @throws Exception
     */
    void createTradeQRCode(FmsPayOrderDTO payOrderDTO) throws Exception;

    /**
     * 微信退款
     * @param outRefundNo
     * @param orderDTO
     * @param refundFee 退款金额
     * @throws Exception
     */
    void tradeRefund(String outRefundNo, FmsPayOrderDTO orderDTO, BigDecimal refundFee) throws Exception;

    /**
     * 微信支付查询
     * @param payOrderDTO
     * @return
     * @throws Exception
     */
    Boolean tradeQuery(FmsPayOrderDTO payOrderDTO) throws Exception;

}
