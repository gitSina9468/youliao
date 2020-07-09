package com.seahorse.youliao.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
* describe: 订单退费明细
* @author : songtqiang
* @date: 2020-03-18 02:20:26.714
**/
@ApiModel
@Getter
@Setter
@ToString
public class FmsRefundOrderQueryVO {
	
    /**
     * 支付id
     */
    @ApiModelProperty("支付id")
	private Long payId;

    /**
     * 商户退款单号
     */
    @ApiModelProperty("商户退款单号")
	private String outRefundNo;

    /**
     * 退款金额
     */
    @ApiModelProperty("退款金额")
	private BigDecimal refundFee;

    /**
     * 退款状态 0 失败  1 成功
     */
    @ApiModelProperty("退款状态 0 失败  1 成功")
	private Boolean refundStatus;

    /**
     * 退款人
     */
    @ApiModelProperty("退款人")
	private String refundUser;

    /**
     * 当前页
     */
	@ApiModelProperty("当前页")
    @Min(value = 1,message="当前页不小于1")
    private Integer pageNum;
    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    @Min(value = 1,message="每页条数不能小于1")
    private Integer pageSize;
}
