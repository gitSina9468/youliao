package com.seahorse.youliao.dao;

import org.springframework.stereotype.Repository;
import com.seahorse.youliao.dao.entity.FmsPayOrderDO;

/**
* describe: 支付订单
* @author : songtqiang
* @date: 2020-03-18 02:20:25.891
**/
@Repository
public interface FmsPayOrderDao extends BaseDao<FmsPayOrderDO, Long> {


    /**
     * 根据商户订单号修改
     * @param orderDO
     * @return
     */
    int updateByOutTradeNo(FmsPayOrderDO orderDO);
}