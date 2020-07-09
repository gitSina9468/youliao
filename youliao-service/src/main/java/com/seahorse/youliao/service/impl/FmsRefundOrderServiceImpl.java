package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.FmsRefundOrderDao;
import com.seahorse.youliao.dao.entity.FmsRefundOrderDO;
import com.seahorse.youliao.service.FmsRefundOrderService;
import com.seahorse.youliao.service.entity.FmsRefundOrderDTO;
import com.seahorse.youliao.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
* describe: 订单退费明细
* @author : songtqiang
* @date: 2020-03-18 02:20:26.714
**/
@Service
public class FmsRefundOrderServiceImpl extends BaseServiceImpl<FmsRefundOrderDTO> implements FmsRefundOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FmsRefundOrderServiceImpl.class);
	
	@Autowired
	private FmsRefundOrderDao fmsRefundOrderDao;

	/**
	 * 根据退款单号修改退款状态
	 *
	 * @param refundOrderDTO
	 */
	@Override
	public void updateStatusByOutRefundNo(FmsRefundOrderDTO refundOrderDTO) {

		fmsRefundOrderDao.updateStatusByOutRefundNo(BeanUtil.convert(refundOrderDTO,FmsRefundOrderDO.class));

	}

	/**
	 * 根据支付订单id查询退款总额
	 *
	 * @param payId
	 * @return
	 */
	@Override
	public BigDecimal getOrderRefundFeeById(Long payId) {

		return fmsRefundOrderDao.getOrderRefundFeeById(payId);
	}
}
