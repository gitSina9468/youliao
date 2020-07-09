package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.config.WeChatPayConfigs;
import com.seahorse.youliao.constant.PayResultConstants;
import com.seahorse.youliao.constant.PayTypeConstants;
import com.seahorse.youliao.constant.WeChatPayApiURL;
import com.seahorse.youliao.entity.wechat.*;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.service.WeChatPayService;
import com.seahorse.youliao.service.entity.FmsPayOrderDTO;
import com.seahorse.youliao.utils.DateUtils;
import com.seahorse.youliao.utils.WeChatRequestUtil;
import com.seahorse.youliao.utils.WeChatXmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @description: 微信支付
 * @author: Mr.Song
 * @create: 2020-03-07 17:23
 **/
@Service
public class WeChatPayServiceImpl implements WeChatPayService {


    private static Logger logger = LoggerFactory.getLogger(WeChatPayServiceImpl.class);

    /**
     * 下单生成二维码
     *
     * @param payOrderDTO
     * @return
     */
    @Override
    public void createTradeQRCode(FmsPayOrderDTO payOrderDTO) throws Exception{

        logger.info("创建支付下单记录");
        String nonceStr = WeChatXmlUtil.generateNonceStr();
        payOrderDTO.setNonceStr(nonceStr);
        //商户交易号 （用于 第三方 退款 查询状态）
        String outTradeNo = String.valueOf(System.currentTimeMillis()) + (long) (Math.random() * 10000000L);
        payOrderDTO.setOutTradeNo(outTradeNo);
        WeChatPayConfigEntity payConfigEntity = buildWebChatPayConfig(payOrderDTO);

        //以下为调用支付接口及校验逻辑  发送请求 并返回请求结果
        logger.info("发送请求 并返回请求结果");
        String xml = WeChatRequestUtil.request(WeChatPayApiURL.UNIFIED_ORDER_URL, payConfigEntity, PayTypeConstants.WECHAT);
        if(StringUtils.isEmpty(xml)){
            logger.error("微信支付下单 异常");
            throw new BusinessException("微信支付下单 异常");
        }

        //将返回结果解析为实体
        WeChatPayResultEntity resultsEntity = WeChatXmlUtil.xmlToBean(WeChatPayResultEntity.class, xml);
        //验证返回结果
        if (!PayResultConstants.SUCCESS.equals(resultsEntity.getResultCode())) {
            logger.error("ReturnMsg：" + resultsEntity.getReturnMsg());
            throw new BusinessException(resultsEntity.getReturnMsg());
        }

        //验证签名
        SortedMap<String, String> data = new TreeMap<>();
        data.put("appid", payConfigEntity.getAppId());
        data.put("mch_id", payConfigEntity.getMchId());
        data.put("device_info", resultsEntity.getDeviceInfo());
        data.put("nonce_str", resultsEntity.getNonceStr());
        data.put("prepay_id", resultsEntity.getPrepayId());
        data.put("trade_type", resultsEntity.getTradeType());
        data.put("return_code", resultsEntity.getReturnCode());
        data.put("return_msg", resultsEntity.getReturnMsg());
        data.put("result_code", resultsEntity.getResultCode());
        data.put("code_url", resultsEntity.getCodeUrl());
        String sign = WeChatXmlUtil.generateSignature(data, WeChatPayConfigs.API_KEY);
        if (!resultsEntity.getSign().equals(sign)) {
            logger.info("Sign：" + "签名验证失败，可能存在拦截串改数据！");
            throw new BusinessException("支付签名失败");
        }

        //生成二维码是否成功验证
        if (!PayResultConstants.SUCCESS.equals(resultsEntity.getReturnCode())) {
            logger.error("ErrCodeDes：" + resultsEntity.getErrCodeDes());
            throw new BusinessException("生成支付二维码失败，请重试！");
        }

        String codeUrl = resultsEntity.getCodeUrl();
        logger.info("微信下单 成功 codeUrl = " + codeUrl);

        payOrderDTO.setQrCodeUrl(codeUrl);

    }

    /**
     * 微信退款
     * @param outRefundNo
     * @param orderDTO
     * @param refundFee 退款金额
     * @throws Exception
     */
    @Override
    public void tradeRefund(String outRefundNo,FmsPayOrderDTO orderDTO,BigDecimal refundFee) throws Exception{

        //调用微信退款
        WeChatRefundEntity refundEntity = buildRefundEntity(outRefundNo,orderDTO,refundFee);

        String xmlStr = WeChatXmlUtil.beanToXml(WeChatRefundEntity.class,refundEntity);
        //微信退款
        logger.info("微信退款申请 xmlStr = " + xmlStr);
        String respXml = WeChatRequestUtil.refund(WeChatPayApiURL.REFUND_URL, xmlStr);

        //将返回结果解析为实体
//        WeChatRefundResultEntity refundResultEntity = WeChatXmlUtil.xmlToBean(WeChatRefundResultEntity.class, respXml);
        Map<String,String> map = WeChatXmlUtil.xmlToMap(respXml);
        //验证返回结果
        if (!PayResultConstants.SUCCESS.equals(map.get("result_code"))) {
            logger.error("ReturnMsg：" + map.get("return_msg"));
            throw new BusinessException(map.get("return_msg"));
        }

        //验证签名
        SortedMap<String, String> data = new TreeMap<>();
        data.put("appid", WeChatPayConfigs.APP_ID);
        data.put("mch_id", WeChatPayConfigs.MCH_ID);
        data.put("nonce_str", map.get("nonce_str"));
        data.put("transaction_id", map.get("transaction_id"));
        data.put("out_trade_no", map.get("out_trade_no"));
        data.put("out_refund_no", map.get("out_refund_no"));
        data.put("refund_id", map.get("refund_id"));
        data.put("refund_fee", map.get("refund_fee"));
        data.put("coupon_refund_fee", map.get("coupon_refund_fee"));
        data.put("coupon_refund_count", map.get("coupon_refund_count"));
        data.put("total_fee", map.get("total_fee"));
        data.put("cash_fee", map.get("cash_fee"));
        data.put("cash_refund_fee", map.get("cash_refund_fee"));
        data.put("result_code", map.get("result_code"));
        data.put("return_code", map.get("return_code"));
        data.put("return_msg", map.get("return_msg"));
        if(!StringUtils.isEmpty(map.get("refund_channel"))){
            data.put("refund_channel", map.get("refund_channel"));
        }
        if(!StringUtils.isEmpty(map.get("settlement_refund_fee"))){
            data.put("settlement_refund_fee", map.get("settlement_refund_fee"));
        }
        String sign = WeChatXmlUtil.generateSignature(data, WeChatPayConfigs.API_KEY);
        if (!map.get("sign").equals(sign)) {
            logger.info("Sign：" + "签名验证失败，可能存在拦截串改数据！");
            throw new BusinessException("支付签名失败");
        }
    }

    /**
     * 微信支付查询
     *
     * @param payOrderDTO
     * @throws Exception
     */
    @Override
    public Boolean tradeQuery(FmsPayOrderDTO payOrderDTO) throws Exception {

        WeChatQueryEntity webChatQueryEntity = new WeChatQueryEntity();
        webChatQueryEntity.setAppId(WeChatPayConfigs.APP_ID);
        webChatQueryEntity.setMchId(WeChatPayConfigs.MCH_ID);
        webChatQueryEntity.setNonceStr(payOrderDTO.getNonceStr());
        webChatQueryEntity.setOutTradeNo(payOrderDTO.getOutTradeNo());
        webChatQueryEntity.setSignType(WeChatPayConfigs.SIGN_TYPE);

        //签名
        SortedMap<String, String> data = new TreeMap<>();
        data.put("appid", webChatQueryEntity.getAppId());
        data.put("mch_id", webChatQueryEntity.getMchId());
        data.put("nonce_str", webChatQueryEntity.getNonceStr());
        data.put("out_trade_no", webChatQueryEntity.getOutTradeNo());
        data.put("sign_type", webChatQueryEntity.getSignType());

        boolean result = true;
        webChatQueryEntity.setSign(WeChatXmlUtil.generateSignature(data, WeChatPayConfigs.API_KEY));

        //发送请求 并返回请求结果
        String xml = WeChatRequestUtil.request(WeChatPayApiURL.CHECK_ORDER_URL, webChatQueryEntity, PayTypeConstants.WECHATQUERY);

        logger.info("resultsEntity xml：" + xml);

        //将返回结果解析为实体
        WeChatQueryResultEntity resultsEntity = WeChatXmlUtil.xmlToBean(WeChatQueryResultEntity.class, xml);

        logger.info("查询微信支付信息 resultsEntity = {}",resultsEntity);
        //验证返回结果
        if (!PayResultConstants.SUCCESS.equals(resultsEntity.getTrade_state())) {
            logger.info("ReturnMsg：" + resultsEntity.getTrade_state_desc());
            throw new BusinessException(resultsEntity.getReturnMsg());
        }
        //返回结果
        return result;
    }


    /**
     * 创建扫码支付下单
     *
     * @return
     */
    private WeChatPayConfigEntity buildWebChatPayConfig(FmsPayOrderDTO payOrderDTO) throws Exception {


        WeChatPayConfigEntity payConfigEntity = new WeChatPayConfigEntity();
        //商户配置
        payConfigEntity.setAppId(WeChatPayConfigs.APP_ID);
        payConfigEntity.setMchId(WeChatPayConfigs.MCH_ID);

        //商户订单号 系统自定义生成
        payConfigEntity.setOutTradeNo(payOrderDTO.getOutTradeNo());
        //随机字符串
        payConfigEntity.setNonceStr(payOrderDTO.getNonceStr());
        //支付总金额 微信转换为分
        int total = payOrderDTO.getPayFee().multiply(new BigDecimal("100")).intValue();
        payConfigEntity.setTotalFee(total);
        //交易起始时间
        payConfigEntity.setTimeStart(DateUtils.dateToString(new Date(), DateUtils.PatternEnum.DATE_FOR_HOUR_MILLIONS));
        //交易结束时间
        payConfigEntity.setTimeExpire(DateUtils.dateToStringPass2Hour(new Date()));
        //交易回调地址
        payConfigEntity.setNotifyUrl(WeChatPayConfigs.PAY_NOTIFY_URL);
        payConfigEntity.setTradeType("NATIVE");
        payConfigEntity.setProductId(payOrderDTO.getOrderNo());
        payConfigEntity.setBody(payOrderDTO.getProductName());
        payConfigEntity.setDeviceInfo("WEB");
        payConfigEntity.setSpbillCreateIp("0.0.0.0");

        //签名
        SortedMap<String, String> data = new TreeMap<>();
        data.put("appid", payConfigEntity.getAppId());
        data.put("mch_id", payConfigEntity.getMchId());
        data.put("nonce_str", payConfigEntity.getNonceStr());
        data.put("sign_type", payConfigEntity.getSignType());
        data.put("product_id", payConfigEntity.getProductId());
        data.put("body", payConfigEntity.getBody());
        data.put("notify_url", WeChatPayConfigs.PAY_NOTIFY_URL);
        data.put("device_info", payConfigEntity.getDeviceInfo());
        data.put("out_trade_no", payConfigEntity.getOutTradeNo());
        data.put("total_fee", String.valueOf(payConfigEntity.getTotalFee()));
        data.put("spbill_create_ip", payConfigEntity.getSpbillCreateIp());
        data.put("time_start", payConfigEntity.getTimeStart());
        data.put("time_expire", payConfigEntity.getTimeExpire());
        data.put("trade_type", payConfigEntity.getTradeType());
        payConfigEntity.setSign(WeChatXmlUtil.generateSignature(data, WeChatPayConfigs.API_KEY));

        return payConfigEntity;
    }

    /**
     * 构建退款参数
     * @return
     */
    private WeChatRefundEntity buildRefundEntity(String outRefundNo,FmsPayOrderDTO orderDTO,BigDecimal refundFee) throws Exception{

        //随机字符串 总额  退款额
        String nonceStr = WeChatXmlUtil.generateNonceStr();

        WeChatRefundEntity refundEntity = new WeChatRefundEntity();
        refundEntity.setAppId(WeChatPayConfigs.APP_ID);
        refundEntity.setMchId(WeChatPayConfigs.MCH_ID);
        refundEntity.setNonceStr(nonceStr);
        refundEntity.setNotify_url(WeChatPayConfigs.REFUND_NOTIFY_URL);
        refundEntity.setOutTradeNo(orderDTO.getOutTradeNo());
        refundEntity.setOutRefundNo(outRefundNo);
        //微信支付要将金额转为分
        refundEntity.setTotalFee(orderDTO.getPayFee().multiply(new BigDecimal("100")).intValue());
        refundEntity.setRefundFee(refundFee.multiply(new BigDecimal("100")).intValue());

        //签名
        SortedMap<String, String> data = new TreeMap<>();
        data.put("appid", WeChatPayConfigs.APP_ID);
        data.put("mch_id", WeChatPayConfigs.MCH_ID);
        data.put("sign_type", refundEntity.getSignType());
        data.put("nonce_str", nonceStr);
        data.put("out_trade_no", orderDTO.getOutTradeNo());
        data.put("out_refund_no", outRefundNo);
        data.put("total_fee", refundEntity.getTotalFee().toString());
        data.put("refund_fee", refundEntity.getRefundFee().toString());
        data.put("notify_url", WeChatPayConfigs.REFUND_NOTIFY_URL);

        refundEntity.setSign(WeChatXmlUtil.generateSignature(data, WeChatPayConfigs.API_KEY));
        return refundEntity;
    }
}
