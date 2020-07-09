package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.FmsPayOrderDao;
import com.seahorse.youliao.dao.entity.FmsPayOrderDO;
import com.seahorse.youliao.enums.PayStatusEnum;
import com.seahorse.youliao.enums.PayTypeEnum;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.service.AliPayService;
import com.seahorse.youliao.service.FmsPayOrderService;
import com.seahorse.youliao.service.FmsRefundOrderService;
import com.seahorse.youliao.service.WeChatPayService;
import com.seahorse.youliao.service.entity.FmsPayOrderDTO;
import com.seahorse.youliao.service.entity.FmsRefundOrderDTO;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
* describe: 支付订单
* @author : songtqiang
* @date: 2020-03-18 02:20:25.891
**/
@Service
public class FmsPayOrderServiceImpl extends BaseServiceImpl<FmsPayOrderDTO> implements FmsPayOrderService {

	private static final Logger logger = LoggerFactory.getLogger(FmsPayOrderServiceImpl.class);

	@Autowired
	private FmsPayOrderDao fmsPayOrderDao;

	@Autowired
	private FmsRefundOrderService fmsRefundOrderService;

	@Autowired
    private WeChatPayService weChatPayService;

	@Autowired
    private AliPayService aliPayService;

	/**
	 * 商品下单扫码支付生成二维码
	 * @param payOrderDTO
	 * @return 系统订单号
	 * @throws Exception
	 */
	@Override
	public String unifiedOrder(FmsPayOrderDTO payOrderDTO) throws Exception{

		//生成订单号
		String orderNo = DateUtils.dateToString(new Date(), DateUtils.PatternEnum.DATE_FOR_HOUR_MILLIONS) + (long) (Math.random() * 100L);
		payOrderDTO.setOrderNo(orderNo);
		if(PayTypeEnum.WECHAT.getValue().equals(payOrderDTO.getPayType())){
			//微信下单
			weChatPayService.createTradeQRCode(payOrderDTO);

		}else if(PayTypeEnum.ALIPAY.getValue().equals(payOrderDTO.getPayType())){
			//支付宝下单
			aliPayService.createTradeQRCode(payOrderDTO);
		}
		//订单状态 支付下单
		payOrderDTO.setPayStatus(PayStatusEnum.UNIFIED_ORDER.getValue());
		payOrderDTO.setOrderRefundStatus(false);
		payOrderDTO.setRefundFee(BigDecimal.ZERO);
        fmsPayOrderDao.insert(BeanUtil.convert(payOrderDTO,FmsPayOrderDO.class));

        return orderNo;
	}

	/**
	 * 订单退款
	 * @param refundOrderDTO
	 * @throws Exception
	 */
	@Override
	public void tradeRefund(FmsRefundOrderDTO refundOrderDTO) throws Exception{

		//查询订单信息
		FmsPayOrderDO order = fmsPayOrderDao.getById(refundOrderDTO.getPayId());
		if(order == null){
			throw new BusinessException("没有查询到订单信息");
		}
		FmsPayOrderDTO orderDTO = BeanUtil.convert(order,FmsPayOrderDTO.class);
		//校验退款金额是否满足条件
		if(refundOrderDTO.getRefundFee().compareTo(orderDTO.getPayFee().subtract(orderDTO.getRefundFee())) == 1){
			//退款金额大于 剩余未退款金额
			throw new BusinessException("退款金额不能超出可退款金额");
		}

		//退款单号
		String outRefundNo = String.valueOf(System.currentTimeMillis()) + (long) (Math.random() * 10000000L);

		if(PayTypeEnum.WECHAT.getValue().equals(orderDTO.getPayType())){
			//微信退款 通过回调更改退款状态
			weChatPayService.tradeRefund(outRefundNo,orderDTO,refundOrderDTO.getRefundFee());
		}else if(PayTypeEnum.ALIPAY.getValue().equals(orderDTO.getPayType())){
			//支付宝退款 退款状态是即时获取的
			aliPayService.tradeRefund(outRefundNo,orderDTO.getOutTradeNo(),refundOrderDTO);
			//更新订单退款数据
			FmsPayOrderDO orderDO = new FmsPayOrderDO();
			orderDO.setId(order.getId());
			orderDO.setOrderRefundStatus(true);
			orderDO.setRefundFee(refundOrderDTO.getRefundFee().add(orderDTO.getRefundFee()));
			fmsPayOrderDao.update(orderDO);
		}

		refundOrderDTO.setOutRefundNo(outRefundNo);
		//退款记录
		fmsRefundOrderService.insert(refundOrderDTO);
	}

	/**
	 * 删除订单 只有下单未付款
	 * 的订单才可以删除
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteById(Long id) {

        //查询订单
        FmsPayOrderDO order = fmsPayOrderDao.getById(id);

        if(order == null){
            logger.info("没有查询到订单 id = "+id);
            throw new BusinessException("没有查询到订单");
        }
        if(!PayStatusEnum.UNIFIED_ORDER.getValue().equals(order.getPayStatus())){
            logger.info("订单"+PayStatusEnum.getNameByValue(order.getPayStatus())+"不能删除");
            throw new BusinessException("订单"+PayStatusEnum.getNameByValue(order.getPayStatus())+"不能删除");
        }
        FmsPayOrderDTO fmsPayOrderDTO = new FmsPayOrderDTO();
        fmsPayOrderDTO.setId(id);

		return fmsPayOrderDao.delete(fmsPayOrderDTO) > 0;
	}

	/**
	 * 根据商户订单号修改订单信息
	 *
	 * @param payOrderDTO
	 */
	@Override
	public void updateByOutTradeNo(FmsPayOrderDTO payOrderDTO) {

		fmsPayOrderDao.updateByOutTradeNo(BeanUtil.convert(payOrderDTO,FmsPayOrderDO.class));
	}
}
