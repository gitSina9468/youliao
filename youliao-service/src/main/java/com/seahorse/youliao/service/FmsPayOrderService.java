 package com.seahorse.youliao.service;

 import com.seahorse.youliao.service.entity.FmsPayOrderDTO;
 import com.seahorse.youliao.service.entity.FmsRefundOrderDTO;

 /**
* describe: 支付订单
* @author : songtqiang
* @date: 2020-03-18 02:20:25.891
**/
public interface FmsPayOrderService  extends BaseService<FmsPayOrderDTO> {

    /**
     * 商品下单扫码支付生成二维码
     * @param payOrderDTO
     * @return 系统订单号
     * @throws Exception
     */
    String unifiedOrder(FmsPayOrderDTO payOrderDTO) throws Exception;

    /**
     * 订单退款
     * @param refundOrderDTO
     * @throws Exception
     */
    void tradeRefund(FmsRefundOrderDTO refundOrderDTO) throws Exception;

    /**
     * 删除订单 只有未付款的才可以删除
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 根据商户订单号修改订单信息
     * @param payOrderDTO
     */
    void updateByOutTradeNo(FmsPayOrderDTO payOrderDTO);
}
