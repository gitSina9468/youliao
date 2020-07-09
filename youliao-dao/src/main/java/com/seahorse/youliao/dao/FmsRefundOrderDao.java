package com.seahorse.youliao.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.seahorse.youliao.dao.entity.FmsRefundOrderDO;

import java.math.BigDecimal;

/**
* describe: 订单退费明细
* @author : songtqiang
* @date: 2020-03-18 02:20:26.714
**/
@Repository
public interface FmsRefundOrderDao extends BaseDao<FmsRefundOrderDO, Long> {


    /**
     * 根据退款单号修改
     * @param refundOrderDO
     * @return
     */
    int updateStatusByOutRefundNo(FmsRefundOrderDO refundOrderDO);

    /**
     * 根据支付订单id查询退款总额
     * @param payId
     * @return
     */
    BigDecimal getOrderRefundFeeById(@Param("payId") Long payId);
}