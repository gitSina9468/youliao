package com.seahorse.youliao.controller.base;

import com.seahorse.youliao.common.ResponseEntity;
import com.seahorse.youliao.config.WeChatPayConfigs;
import com.seahorse.youliao.constant.PayResultConstants;
import com.seahorse.youliao.constant.WeChatRefundConstants;
import com.seahorse.youliao.entity.wechat.WeChatPayNotifyEntity;
import com.seahorse.youliao.entity.wechat.WeChatResultEntity;
import com.seahorse.youliao.enums.PayStatusEnum;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.FmsPayOrderService;
import com.seahorse.youliao.service.FmsRefundOrderService;
import com.seahorse.youliao.service.WeChatPayService;
import com.seahorse.youliao.service.entity.FmsPayOrderDTO;
import com.seahorse.youliao.service.entity.FmsRefundOrderDTO;
import com.seahorse.youliao.utils.AESUtil;
import com.seahorse.youliao.utils.WeChatXmlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @description: 微信支付
 * @author: Mr.Song
 * @create: 2020-03-07 16:34
 **/
@Api(value = "WeChatPayController", tags = "支付-微信接口")
@RestController
@RequestMapping("/weChatPay")
public class WeChatPayController {

    private static Logger logger = LoggerFactory.getLogger(WeChatPayController.class);

    @Autowired
    private WeChatPayService weChatPayService;

    @Autowired
    private FmsPayOrderService fmsPayOrderService;

    @Autowired
    private FmsRefundOrderService fmsRefundOrderService;


    /**
     * 微信第三方查询扫码支付结果
     * @param outTradeNo 商户订单号
     * @return
     */
    @ApiOperation(value = "微信第三方查询扫码支付结果")
    @PostMapping("/tradeQuery/{outTradeNo}")
    public ResponseEntity tradeQuery(@PathVariable String outTradeNo) {

        logger.info("查询微信扫码支付结果 outTradeNo = " + outTradeNo);
        try {
            //查询支付订单
            FmsPayOrderDTO payOrderDTO = new FmsPayOrderDTO();
            payOrderDTO.setOutTradeNo(outTradeNo);
            FmsPayOrderDTO orderDTO = fmsPayOrderService.get(payOrderDTO);
            if(orderDTO == null){
                throw new BusinessException("订单不存在");
            }
            weChatPayService.tradeQuery(orderDTO);
        } catch (Exception e) {
            logger.error(e.toString());
            return ResponseEntity.error(e.getMessage());
        }
        return ResponseEntity.ok("成功");
    }


    /**
     * 微信支付成功异步通知
     * SUCCESS/FAIL
     * SUCCESS表示商户接收通知成功并校验成功
     * @param request
     * @return
     */
    @Log(type = Log.OperationType.UPDATE,button = "微信支付回调接口",menu = "微信支付")
    @ApiOperation(value = "微信支付成功异步通知")
    @PostMapping(value = "callback")
    public String callback(HttpServletRequest request) throws Exception{

        logger.info("微信支付成功异步通知  --》 callback");

        //将结果转换为字符
        StringBuilder builder = getResultCallback(request);
        logger.info("getResultCallback -> 微信成功异步回调，结果：" + builder);

        //处理支付成功异步通知
        WeChatResultEntity result = this.wxPayNotifyCallback(builder);
        return WeChatXmlUtil.beanToXml(WeChatResultEntity.class,result);
    }


    /**
     * 微信支付-退款 回调通知
     * @param request
     * @return
     */
    @Log(type = Log.OperationType.UPDATE,button = "微信退款回调接口",menu = "微信支付")
    @ApiOperation(value = "微信支付-退款 回调通知")
    @PostMapping(value = "/refundCallBack")
    public String refundCallBack(HttpServletRequest request) throws Exception{

        logger.info("微信支付-退款  --》 refundCallBack ");

        //将结果转换为字符
        StringBuilder builder = getResultCallback(request);
        logger.info("webChatPayActionCallback -> 微信支付-退款 回调通知，结果：" + builder);

        //微信支付-退款 回调通知
        WeChatResultEntity result = this.refundCallBack(builder);
        return WeChatXmlUtil.beanToXml(WeChatResultEntity.class,result);
    }


    /**
     * 获取返回结果并转换为字段串
     *
     * @param request http
     * @return 返回解析结果
     */
    protected StringBuilder getResultCallback(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        try {
            //验证 HTTP 是否为空
            if (request == null){
                return builder;
            }
            //获取流数据
            InputStream stream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            //读取数据
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            //返回结果
            return builder;
        } catch (Exception ex) {
            logger.error(ex.toString());
            return builder;
        }
    }


    /**
     * 微信支付异步回调通知
     *
     * @param builder
     * @return
     */
    public WeChatResultEntity wxPayNotifyCallback(StringBuilder builder) throws Exception {

        logger.info("XML：" + builder.toString());
        WeChatResultEntity resultEntity = new WeChatResultEntity();

        //更新业务记录支付状态
        FmsPayOrderDTO payOrderDTO = new FmsPayOrderDTO();
        //将数据转换为实体
        WeChatPayNotifyEntity entity = WeChatXmlUtil.xmlToBean(WeChatPayNotifyEntity.class, builder.toString());

        //验证是否成功 如果失败则直接跳过
        if (PayResultConstants.SUCCESS.equals(entity.getResultCode())) {

            //验证签名
            SortedMap<String, String> data = new TreeMap<>();
            data.put("appid", WeChatPayConfigs.APP_ID);
            data.put("mch_id", WeChatPayConfigs.MCH_ID);
            data.put("device_info", entity.getDeviceInfo());
            data.put("nonce_str", entity.getNonceStr());
            data.put("bank_type", entity.getBankType());
            data.put("cash_fee", entity.getCashFee());
            data.put("fee_type", entity.getFeeType());
            data.put("is_subscribe", entity.getIsSubscribe());
            data.put("openid", entity.getOpenId());
            data.put("out_trade_no", entity.getOutTradeNo());
            data.put("trade_type", entity.getTradeType());
            data.put("return_code", entity.getReturnCode());
            data.put("result_code", entity.getResultCode());
            data.put("time_end", entity.getTimeEnd());
            data.put("total_fee", entity.getTotalFee());
            data.put("transaction_id", entity.getTransactionId());
            if (!StringUtils.isEmpty(entity.getCouponFee0())) {
                data.put("coupon_fee_0", entity.getCouponFee0());
            }

            if (!StringUtils.isEmpty(entity.getCouponId0())) {
                data.put("coupon_id_0", entity.getCouponId0());
            }

            if (!StringUtils.isEmpty(entity.getCouponCount())) {
                data.put("coupon_count", entity.getCouponCount());
            }

            if (!StringUtils.isEmpty(entity.getCouponFee())) {
                data.put("coupon_fee", entity.getCouponFee());
            }

            //生成签名信息
            String sign = WeChatXmlUtil.generateSignature(data, WeChatPayConfigs.API_KEY);

            //验证签名
            logger.info("验证签名：" + sign + "，getSign：" + entity.getSign());
            if (!sign.equals(entity.getSign())) {
                logger.info("签名效验失败！");
                throw new BusinessException("签名效验失败！");
            }
            //支付成功  通知
            payOrderDTO.setPayStatus(PayStatusEnum.SUCCESS.getValue());
        }else{
            //支付失败
            payOrderDTO.setPayStatus(PayStatusEnum.FAILURE.getValue());
            resultEntity.setReturnCode("FAIL");
        }
        //更新业务记录支付状态
        payOrderDTO.setOutTradeNo(entity.getOutTradeNo());
        payOrderDTO.setTransactionId(entity.getTransactionId());
        payOrderDTO.setUpdateBy("微信回调");
        payOrderDTO.setUpdateTime(new Date());
        logger.info("更新业务记录支付状态 payOrderDTO = {}",payOrderDTO);
        fmsPayOrderService.updateByOutTradeNo(payOrderDTO);
        return resultEntity;
    }


    /**
     * 微信退款回调
     *
     * @param builder
     * @return
     */
    public WeChatResultEntity refundCallBack(StringBuilder builder) throws Exception{

        WeChatResultEntity resultEntity = new WeChatResultEntity();
        //将数据转换为map
        Map<String,String> map = WeChatXmlUtil.xmlToMap(builder.toString());

        //验证是否成功 如果失败则直接跳过
        if (WeChatRefundConstants.SUCCESS.equals(map.get("return_code"))) {
            logger.info("退款通知回调成功");
            //解密参数 req_info
            String reqInfo = map.get("req_info");
            logger.info("解密参数 req_info");
            String decodeStr = AESUtil.decryptData(reqInfo,WeChatPayConfigs.API_KEY);
            Map<String,String> param = WeChatXmlUtil.xmlToMap(decodeStr);
            logger.info("param = {}",param);

            //查询订单记录
            String outTradeNo = param.get("out_trade_no");
            FmsPayOrderDTO orderDTO = new FmsPayOrderDTO();
            orderDTO.setOutTradeNo(outTradeNo);
            FmsPayOrderDTO payOrderDTO = fmsPayOrderService.get(orderDTO);
            if(payOrderDTO == null){
                logger.info("订单不存在 outTradeNo = " + outTradeNo);
            }
            //退款记录 状态修改
            FmsRefundOrderDTO refundOrderDTO = new FmsRefundOrderDTO();

            //订单退款总额修改
            FmsPayOrderDTO dto = new FmsPayOrderDTO();
            switch (param.get("refund_status")){
                case WeChatRefundConstants.SUCCESS:
                    logger.info("微信退款成功");
                    refundOrderDTO.setRefundStatus(true);

                    //退款成功 计算已退款总额
                    dto.setId(payOrderDTO.getId());
                    BigDecimal orderRefundFee = fmsRefundOrderService.getOrderRefundFeeById(payOrderDTO.getId());
                    dto.setOrderRefundStatus(true);
                    dto.setRefundFee(orderRefundFee);
                    fmsPayOrderService.update(dto);
                    break;
                case WeChatRefundConstants.CHANGE:
                    logger.info("微信退款异常");
                    refundOrderDTO.setRefundStatus(false);
                    break;
                case WeChatRefundConstants.REFUNDCLOSE:
                    logger.info("退款关闭");
                    refundOrderDTO.setRefundStatus(false);
                    break;
                default:
                    break;
            }

            //更新退款记录状态
            String outRefundNo = param.get("out_refund_no");
            refundOrderDTO.setOutRefundNo(outRefundNo);
            fmsRefundOrderService.updateStatusByOutRefundNo(refundOrderDTO);

        }else{
            //支付回调失败
            resultEntity.setReturnCode("FAIL");
        }
        return resultEntity;
    }
}
