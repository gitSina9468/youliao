package com.seahorse.youliao.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeRefundRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.model.result.AlipayF2FRefundResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.utils.Utils;
import com.seahorse.youliao.config.AliPayConfigs;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.service.AliPayService;
import com.seahorse.youliao.service.entity.FmsPayOrderDTO;
import com.seahorse.youliao.service.entity.FmsRefundOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 支付宝支付
 * @author: Mr.Song
 * @create: 2020-03-07 17:22
 **/
@Slf4j
@Service
public class AliPayServiceImpl implements AliPayService {


    /**
     * 下单生成二维码
     * @param payOrderDTO
     * @return 二维码地址
     * @throws Exception
     */
    @Override
    public void createTradeQRCode(FmsPayOrderDTO payOrderDTO) {

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = String.valueOf(System.currentTimeMillis())
                + (long) (Math.random() * 10000000L);

        payOrderDTO.setOutTradeNo(outTradeNo);
        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = payOrderDTO.getProductName();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = String.valueOf(payOrderDTO.getPayFee());

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
//        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
//                .setUndiscountableAmount(undiscountableAmount)
                .setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setNotifyUrl(AliPayConfigs.getNotify_url());
//                .setGoodsDetailList(goodsDetailList);

        //获取tradeService
        AlipayTradeService tradeService = AliPayConfigs.getAlipayTradeService();
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                //下单地址
                String codeUrl = response.getQrCode();
                log.info("支付宝下单 成功 codeUrl = " + codeUrl);
                payOrderDTO.setQrCodeUrl(codeUrl);
                break;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                throw new BusinessException("支付宝预下单失败!!!");
            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                throw new BusinessException("系统异常，预下单状态未知!!!");
            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                throw new BusinessException("不支持的交易状态，交易返回异常!!!");
        }
    }

    /**
     * 支付宝退款
     * @param outRefundNo
     * @param outTradeNo
     * @param refundOrderDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public void tradeRefund(String outRefundNo,String outTradeNo,FmsRefundOrderDTO refundOrderDTO) throws BusinessException{

        // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
        String refundAmount = String.valueOf(refundOrderDTO.getRefundFee());

        // (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
        // 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
        String outRequestNo =  String.valueOf(System.currentTimeMillis())
                + (long) (Math.random() * 10000000L);

        // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
        String refundReason = refundOrderDTO.getRefundReason();

        // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
        String storeId = "test_store_id";

        // 创建退款请求builder，设置请求参数
        AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
                .setOutTradeNo(outTradeNo).setRefundAmount(refundAmount).setRefundReason(refundReason)
                .setOutRequestNo(outRequestNo).setStoreId(storeId);

        AlipayTradeService tradeService = AliPayConfigs.getAlipayTradeService();
        AlipayF2FRefundResult result = tradeService.tradeRefund(builder);

        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝退款成功: )");
                refundOrderDTO.setRefundStatus(true);
                break;

            case FAILED:
                log.error("支付宝退款失败!!!");
                throw new BusinessException("支付宝退款失败!!!");

            case UNKNOWN:
                log.error("系统异常，订单退款状态未知!!!");
                throw new BusinessException("系统异常，订单退款状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                throw new BusinessException("不支持的交易状态，交易返回异常!!!");
        }

    }

    /**
     * 第三方支付结果查询
     * 业务待完善
     * @param outTradeNo 商户订单号，通过此商户订单号查询当面付的交易状态
     * @throws BusinessException
     */
    @Override
    public void tradeQuery(String outTradeNo) throws BusinessException {

        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(outTradeNo);

        AlipayTradeService tradeService = AliPayConfigs.getAlipayTradeService();
        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");

                AlipayTradeQueryResponse response = result.getResponse();
                dumpResponse(response);

                log.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        log.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                break;

            case FAILED:
                log.error("查询返回该订单支付失败或被关闭!!!");
                throw new BusinessException("查询返回该订单支付失败或被关闭!!!");

            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!!!");
                throw new BusinessException("系统异常，订单支付状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                throw new BusinessException("不支持的交易状态，交易返回异常!!!");
        }

    }


    /**
     * 简单打印应答
      */
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

}
