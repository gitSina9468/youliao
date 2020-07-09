 package com.seahorse.youliao.service;

 import com.seahorse.youliao.service.entity.FmsRefundOrderDTO;

 import java.math.BigDecimal;

 /**
* describe: 订单退费明细
* @author : songtqiang
* @date: 2020-03-18 02:20:26.714
**/
public interface FmsRefundOrderService  extends BaseService<FmsRefundOrderDTO> {


    /**
     * 根据退款单号修改退款状态
     * @param refundOrderDTO
     */
    void updateStatusByOutRefundNo(FmsRefundOrderDTO refundOrderDTO);

     /**
      * 根据支付订单id查询退款总额
      * @param payId
      * @return
      */
    BigDecimal getOrderRefundFeeById(Long payId);
}
