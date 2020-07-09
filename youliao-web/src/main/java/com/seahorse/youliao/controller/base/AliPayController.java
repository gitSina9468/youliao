package com.seahorse.youliao.controller.base;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.seahorse.youliao.common.ResponseEntity;
import com.seahorse.youliao.config.AliPayConfigs;
import com.seahorse.youliao.constant.AliPayConstants;
import com.seahorse.youliao.entity.alipay.AlipayNotifyEntity;
import com.seahorse.youliao.enums.PayStatusEnum;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.AliPayService;
import com.seahorse.youliao.service.FmsPayOrderService;
import com.seahorse.youliao.service.entity.FmsPayOrderDTO;
import com.seahorse.youliao.utils.GsonFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: 支付宝支付
 *  第三方支付结果通知   实现方案: 1:轮训  2:websocket长连接
 *  由于轮训消耗很多无用的请求消耗流量 后台压力也会加大
 *  我们在支付回调接口通知的时候采用websocket 技术
 *  因此在下单的时候通过订单号orderNo(系统内唯一)绑定前段ws连接后台通信时生成的 session的id
 *  通过回调校验参数.请求认证通过后 使用outTradeNo查询出系统内的 订单号 orderNo再推送到指定连接的 sessionId
 *  实现支付结果实时通知
 * @author: Mr.Song
 * @create: 2020-03-07 16:34
 **/
@Api(value = "AliPayController", tags = "支付-支付宝接口")
@RestController
@RequestMapping("/aliPay")
public class AliPayController {

    private static Logger logger = LoggerFactory.getLogger(AliPayController.class);

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private FmsPayOrderService fmsPayOrderService;

    /**
     * 支付宝第三方查询扫码支付结果
     * @param outTradeNo 商户订单号
     * @return
     */
    @ApiOperation(value = "支付宝第三方查询扫码支付结果")
    @PostMapping("/tradeQuery/{outTradeNo}")
    public ResponseEntity tradeQuery(@PathVariable String outTradeNo) {

        logger.info("查询支付宝扫码支付结果 outTradeNo = " + outTradeNo);
        try {
            aliPayService.tradeQuery(outTradeNo);
        } catch (BusinessException e) {
            logger.error(e.toString());
           return ResponseEntity.error(e.getMessage());
        }
        return ResponseEntity.ok("成功");
    }

    /**
     * 支付宝支付回调
     * 由于接口返回参数限定为 成功 success;失败 failure
     * @param request
     * @return
     */
    @Log(type = Log.OperationType.UPDATE,button = "支付宝支付回调接口",menu = "支付宝支付")
    @ApiOperation(value = "支付宝支付回调")
    @PostMapping("callback")
    public String callback(HttpServletRequest request) {

        logger.info("支付宝支付异步回调通知");
        // 将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String> params = convertRequestParamsToMap(request);
        String paramsJson = GsonFactory.getGson().toJson(params);
        logger.info("支付宝回调:" + paramsJson);

        AlipayNotifyEntity param = buildAlipayNotifyParam(params);
        //更新业务记录支付状态
        FmsPayOrderDTO payOrderDTO = new FmsPayOrderDTO();

        try {
            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfigs.getAlipayPublicKey(), "utf-8", AliPayConfigs.getSignType());
            if (signVerified) {
                logger.info("支付宝回调签名认证成功");

                // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，
                // 校验成功后在response中返回success，校验失败返回failure
                this.check(params);
                //支付状态
                String trade_status = param.getTradeStatus();
                if (AliPayConstants.WAIT_BUYER_PAY.equals(trade_status)) {
                    logger.info("扫码成功！可以处理业务逻辑了！！！");

                }else if (AliPayConstants.TRADE_SUCCESS.equals(trade_status) || AliPayConstants.TRADE_FINISHED.equals(trade_status)) {
                    logger.info("支付成功！可以处理业务逻辑了！！！");

                    payOrderDTO.setPayStatus(PayStatusEnum.SUCCESS.getValue());
                }else if(AliPayConstants.TRADE_CLOSED.equals(trade_status)){
                    logger.info("未付款交易超时关闭，或支付完成后全额退款");
                    payOrderDTO.setPayStatus(PayStatusEnum.FAILURE.getValue());

                }
            } else {
                logger.info("支付宝回调签名认证失败");
                return "failure";
            }

            //更新业务记录支付状态为成功
            logger.info("更新业务记录支付状态为成功！payOrderDTO = {}",payOrderDTO);

            payOrderDTO.setOutTradeNo(param.getOutTradeNo());
            payOrderDTO.setTransactionId(param.getTradeNo());
            payOrderDTO.setUpdateBy("支付宝回调");
            payOrderDTO.setUpdateTime(new Date());
            fmsPayOrderService.updateByOutTradeNo(payOrderDTO);

            return "success";
        } catch (AlipayApiException e) {
            logger.error("支付宝回调签名认证失败"+e.toString());
            return "failure";
        }
    }

    /**
     * 返回参数封装AlipayNotifyParam对象
     *
     * @param params
     * @return
     */
    private AlipayNotifyEntity buildAlipayNotifyParam(Map<String, String> params) {
        String json = GsonFactory.getGson().toJson(params);
        return GsonFactory.getGson().fromJson(json, AlipayNotifyEntity.class);
    }

    /**
     * 将request中的参数转换成Map
     *
     * @param request
     * @return
     */
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>(50);

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;
            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }


    /**
     * 回调校验参数:
     * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
     * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
     * 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
     * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
     * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
     * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
     *
     * @param params
     * @throws AlipayApiException
     */
    private void check(Map<String, String> params) throws AlipayApiException  {
        String outTradeNo = params.get("out_trade_no");

        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        logger.info("商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号 outTradeNo = " + outTradeNo);


        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）


        // 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
        // 第三步可根据实际情况省略

        // 4、验证app_id是否为该商户本身。


    }


}
